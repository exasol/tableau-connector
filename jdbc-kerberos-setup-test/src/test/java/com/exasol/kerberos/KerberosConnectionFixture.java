package com.exasol.kerberos;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

import javax.security.auth.Subject;
import javax.security.auth.callback.*;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import org.ietf.jgss.*;

import com.sun.security.auth.module.Krb5LoginModule;
import com.sun.security.jgss.ExtendedGSSCredential;

public class KerberosConnectionFixture {

    private static final Logger LOGGER = Logger.getLogger(KerberosConnectionFixture.class.getName());

    private final TestConfig config;

    KerberosConnectionFixture(final TestConfig config) {
        this.config = config;
        System.setProperty("java.security.krb5.conf", this.config.getKerberosConfigFile().toString());
        System.setProperty("sun.security.krb5.debug", String.valueOf(config.isKerberosDebugEnabled()));
        System.setProperty("java.security.debug", String.valueOf(config.isKerberosDebugEnabled()));
        createJdbcDriverLogDir(config);
    }

    private void createJdbcDriverLogDir(final TestConfig config) {
        LOGGER.info("Using driver log dir " + config.getLogDir());
        try {
            if (!Files.exists(config.getLogDir())) {
                Files.createDirectories(config.getLogDir());
            }
        } catch (final IOException exception) {
            throw new UncheckedIOException("Unable to create log dir", exception);
        }
    }

    private static GSSCredential getImpersonationCredentials(final Subject subject, final String impersonatedUser) {
        assertThat(subject.getPrincipals(), hasSize(1));
        final String runAsUser = subject.getPrincipals().iterator().next().getName();
        LOGGER.info("Getting impersonation credentials for runAs user '" + runAsUser + "' and '" + impersonatedUser
                + "'");
        try {
            return Subject.doAs(subject, (PrivilegedExceptionAction<GSSCredential>) () -> {
                final GSSManager manager = GSSManager.getInstance();
                final GSSName selfName = manager.createName(runAsUser, GSSName.NT_USER_NAME);

                final GSSCredential selfCreds = manager.createCredential(selfName, GSSCredential.INDEFINITE_LIFETIME,
                        createKerberosOid(),
                        GSSCredential.INITIATE_ONLY);
                LOGGER.info("Got self credentials " + selfCreds);

                final GSSName dbUser = manager.createName(impersonatedUser, GSSName.NT_USER_NAME);

                LOGGER.info("Impersonating user " + dbUser);
                return ((ExtendedGSSCredential) selfCreds).impersonate(dbUser);
            });
        } catch (final PrivilegedActionException exception) {
            throw new IllegalStateException(
                    "Could impersonate user '" + impersonatedUser + "' with runAs user '" + runAsUser + "'", exception);
        }
    }

    private static Subject getServiceSubject(final String runAsUser, final Path keytabFile) {
        final Map<String, String> options = new HashMap<>();
        options.put("principal", runAsUser);
        options.put("useKeyTab", "true");
        options.put("doNotPrompt", "true");
        options.put("keyTab", keytabFile.toString());
        options.put("isInitiator", "true");
        options.put("refreshKrb5Config", "true");
        return login(options, null);
    }

    private static Subject getServiceSubject(final String user, final String password) {
        final Map<String, String> options = new HashMap<>();
        options.put("principal", user);
        options.put("useKeyTab", "true");
        options.put("doNotPrompt", "false");
        options.put("isInitiator", "true");
        options.put("refreshKrb5Config", "true");
        options.put("storeKey", "true");
        return login(options, callbacks -> {
            for (final Callback callback : callbacks) {
                if (callback instanceof PasswordCallback) {
                    final PasswordCallback passwordCallback = (PasswordCallback) callback;
                    LOGGER.info("Prompt:" + passwordCallback.getPrompt());
                    passwordCallback.setPassword(password.toCharArray());
                } else {
                    throw new IllegalStateException("Unknown callback type " + callback.getClass().getName());
                }
            }
            LOGGER.info(Arrays.toString(callbacks));
        });
    }

    private static Subject login(final Map<String, String> options, final CallbackHandler callbackHandler) {
        final Subject serviceSubject = new Subject();
        final LoginModule krb5Module = new Krb5LoginModule();
        krb5Module.initialize(serviceSubject, callbackHandler, null, options);
        try {
            assertThat(krb5Module.login(), is(true));
            assertThat(krb5Module.commit(), is(true));
        } catch (final LoginException exception) {
            try {
                krb5Module.abort();
            } catch (final LoginException loginException) {
                LOGGER.info("Error aborting Kerberos authentication: " + loginException.getMessage());
            }
            throw new IllegalStateException("Error during login: " + exception.getMessage(), exception);
        }
        assertThat(serviceSubject.getPrincipals(), hasSize(1));
        LOGGER.info("Logged in as " + serviceSubject.getPrincipals().iterator().next().getName());
        return serviceSubject;
    }

    private static Oid createKerberosOid() {
        try {
            return new Oid("1.2.840.113554.1.2.2");
        } catch (final GSSException e) {
            throw new IllegalStateException();
        }
    }

    Connection createConnectionWithRunAs() {
        final String runAsUser = this.config.getRunAsUser();
        final Subject subject = getServiceSubject(runAsUser, this.config.getKeytabFile());
        return createPriviligedConnection(LoginType.GSSAPI, subject, runAsUser);
    }

    Connection createConnectionWithImpersonation() {
        final Subject subject = getServiceSubject(this.config.getRunAsUser(), this.config.getKeytabFile());
        subject.getPrivateCredentials().add(getImpersonationCredentials(subject, this.config.getImpersonatedUser()));
        return createPriviligedConnection(LoginType.GSSAPI, subject, this.config.getImpersonatedUser());
    }

    Connection createConnectionWithKerberosPassword() {
        final Subject subject = getServiceSubject(this.config.getRunAsUser(),
                this.config.getRunAsUserKerberosPassword());
        return createPriviligedConnection(LoginType.GSSAPI, subject, this.config.getImpersonatedUser());
    }

    Connection createConnectionWithSspi() {
        return createPriviligedConnection(LoginType.SSPI, null, this.config.getImpersonatedUser());
    }

    Connection createPriviligedConnection(final LoginType loginType, final Subject subject,
            final String connectionUser) {
        try {
            return Subject.doAs(subject, (PrivilegedExceptionAction<Connection>) () -> {
                return createConnection(loginType, connectionUser);
            });
        } catch (final PrivilegedActionException exception) {
            throw new IllegalStateException("Error getting connection", exception);
        }
    }

    private Connection createConnection(final LoginType loginType, final String user) {
        final Properties driverProperties = new Properties();
        driverProperties.put("user", user);
        driverProperties.put("loginType", loginType.getCode());

        final String jdbcUrl = this.config.getJdbcUrl();
        LOGGER.info("Connecting using url " + jdbcUrl);
        LOGGER.info(" properties: " + driverProperties);
        try {
            return DriverManager.getConnection(jdbcUrl, driverProperties);
        } catch (final SQLException exception) {
            throw new IllegalStateException("Error connecting to DB using URL " + jdbcUrl, exception);
        }
    }
}
