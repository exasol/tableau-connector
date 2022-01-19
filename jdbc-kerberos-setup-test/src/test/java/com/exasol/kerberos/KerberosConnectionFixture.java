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
import java.util.logging.Level;
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

    private static final boolean KERBEROS_DEBUGGING_ENABLED = true;
    private final TestConfig config;

    KerberosConnectionFixture(final TestConfig config) {
        this.config = config;
        System.setProperty("java.security.krb5.conf", this.config.getKerberosConfigFile().toString());
        System.setProperty("sun.security.krb5.debug", String.valueOf(KERBEROS_DEBUGGING_ENABLED));
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
            throw new IllegalStateException("Could not impersonate user: " + exception.getMessage(), exception);
        }
    }

    private static Subject getServiceSubject(final String user, final Path keytabFile) {
        LOGGER.info("Getting subject for user " + user + " and keytab file " + keytabFile);
        final Map<String, String> options = new HashMap<>();
        options.put("principal", user);
        options.put("useKeyTab", "true");
        options.put("doNotPrompt", "true");
        options.put("keyTab", keytabFile.toString());
        options.put("isInitiator", "true");
        options.put("refreshKrb5Config", String.valueOf(KERBEROS_DEBUGGING_ENABLED));
        options.put("debug", "true");
        return login(options, null);
    }

    private static Subject getServiceSubject(final String user, final String password) {
        LOGGER.info("Getting subject for user " + user + " with password " + password.replaceAll(".", "*"));
        final Map<String, String> options = new HashMap<>();
        options.put("principal", user);
        options.put("useKeyTab", "true");
        options.put("doNotPrompt", "false");
        options.put("isInitiator", "true");
        options.put("refreshKrb5Config", "true");
        options.put("storeKey", "true");
        options.put("debug", String.valueOf(KERBEROS_DEBUGGING_ENABLED));
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
        LOGGER.info("Initializing Kerberos login module with subject...");
        krb5Module.initialize(serviceSubject, callbackHandler, null, options);
        try {
            LOGGER.info("Logging in to Kerberos module...");
            assertThat(krb5Module.login(), is(true));
            LOGGER.info("Committing Kerberos module...");
            assertThat(krb5Module.commit(), is(true));
        } catch (final LoginException exception) {
            try {
                krb5Module.abort();
            } catch (final LoginException loginException) {
                LOGGER.log(Level.WARNING, "Error aborting Kerberos authentication: " + loginException.getMessage(),
                        loginException);
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
                this.config.getImpersonatedUserKerberosPassword());
        return createPriviligedConnection(LoginType.GSSAPI, subject, this.config.getImpersonatedUser());
    }

    Connection createConnectionWithSspi(final String user) {
        return createPriviligedConnection(LoginType.SSPI, null, user);
    }

    Connection createPriviligedConnection(final LoginType loginType, final Subject subject,
            final String connectionUser) {
        LOGGER.info("Creating connection using subject " + subject + " and connection user " + connectionUser);
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
        createJdbcDriverLogDir();
        try {
            return DriverManager.getConnection(jdbcUrl, driverProperties);
        } catch (final SQLException exception) {
            throw new IllegalStateException("Error connecting to DB using URL " + jdbcUrl, exception);
        }
    }

    private void createJdbcDriverLogDir() {
        try {
            if (!Files.exists(this.config.getLogDir())) {
                Files.createDirectories(this.config.getLogDir());
            }
        } catch (final IOException exception) {
            throw new UncheckedIOException("Unable to create log dir", exception);
        }
    }
}
