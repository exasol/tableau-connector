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

import javax.security.auth.Subject;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import org.ietf.jgss.*;

import com.sun.security.auth.module.Krb5LoginModule;
import com.sun.security.jgss.ExtendedGSSCredential;

public class KerberosConnectionFixture {

    private static final boolean KERBEROS_DEBUGGING_ENABLED = false;
    private final TestConfig config;

    KerberosConnectionFixture(final TestConfig config) {
        this.config = config;
        if (this.config.getJaasFile().isPresent()) {
            System.setProperty("java.security.auth.login.config", this.config.getJaasFile().get().toString());
        }
        System.setProperty("java.security.krb5.conf", this.config.getKerberosConfigFile().get().toString());
        System.setProperty("sun.security.krb5.debug", String.valueOf(KERBEROS_DEBUGGING_ENABLED));
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
        System.out
                .println("Getting impersonation credentials for runAs user '" + runAsUser + "' and '" + impersonatedUser
                        + "'");
        try {
            return Subject.doAs(subject, (PrivilegedExceptionAction<GSSCredential>) () -> {
                final GSSManager manager = GSSManager.getInstance();
                final GSSName selfName = manager.createName(runAsUser, GSSName.NT_USER_NAME);

                final GSSCredential selfCreds = manager.createCredential(selfName, GSSCredential.INDEFINITE_LIFETIME,
                        createKerberosOid(),
                        GSSCredential.INITIATE_ONLY);
                System.out.println("Got self credentials " + selfCreds);

                final GSSName dbUser = manager.createName(impersonatedUser, GSSName.NT_USER_NAME);

                System.out.println("Impersonating user " + dbUser);
                return ((ExtendedGSSCredential) selfCreds).impersonate(dbUser);
            });
        } catch (final PrivilegedActionException exception) {
            throw new IllegalStateException("Could not impersonate user", exception);
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

        System.out.println("Retrieving TGT for runAsUser using keytab");

        final Subject serviceSubject = new Subject();
        final LoginModule krb5Module = new Krb5LoginModule();
        krb5Module.initialize(serviceSubject, null, null, options);
        try {
            assertThat(krb5Module.login(), is(true));
            assertThat(krb5Module.commit(), is(true));
        } catch (final LoginException exception) {
            try {
                krb5Module.abort();
            } catch (final LoginException e1) {
                System.out.println("Error aborting Kerberos authentication:  " + e1);
            }
            throw new IllegalStateException("Error during login", exception);
        }
        assertThat(serviceSubject.getPrincipals(), hasSize(1));
        System.out.println("Logged in as " + serviceSubject.getPrincipals().iterator().next().getName());
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
        final String runAsUser = this.config.getRunAsUser().get();
        final Subject subject = getServiceSubject(runAsUser, this.config.getKeytabFile().get());
        return createPriviligedConnection(subject, runAsUser);
    }

    Connection createConnectionWithImpersonation() {
        final Subject subject = getServiceSubject(this.config.getRunAsUser().get(), this.config.getKeytabFile().get());
        subject.getPrivateCredentials().add(getImpersonationCredentials(subject, this.config.getImpersonatedUser()));
        return createPriviligedConnection(subject, subject);
    }

    Connection createConnectionWithoutUser() {
        return createConnection(null);
    }

    private Connection createPriviligedConnection(final Subject subject, final Object connectionUser) {
        try {
            return Subject.doAs(subject, (PrivilegedExceptionAction<Connection>) () -> {
                return createConnection(connectionUser);
            });
        } catch (final PrivilegedActionException exception) {
            throw new IllegalStateException("Error getting connection", exception);
        }
    }

    private Connection createConnection(final Object user) {
        final Properties driverProperties = new Properties();
        driverProperties.put("gsslib", "gssapi");
        driverProperties.put("jaasLogin", "false");
        if (user != null) {
            driverProperties.put("user", user);
        }
        final String jdbcUrl = this.config.getJdbcUrl();
        System.out.println("Connecting using url " + jdbcUrl);
        System.out.println(" properties: " + driverProperties);
        try {
            return DriverManager.getConnection(jdbcUrl, driverProperties);
        } catch (final SQLException exception) {
            throw new IllegalStateException("Error connecting to DB using URL " + jdbcUrl);
        }
    }
}
