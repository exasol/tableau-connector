package com.exasol.kerberos;

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

    private final Subject serviceSubject;
    private static Oid krb5Oid;
    private GSSCredential impersonationCredentials;
    private final TestConfig config;

    static {
        try {
            krb5Oid = new Oid("1.2.840.113554.1.2.2");
        } catch (final GSSException e) {
            System.out.println("Error creating Oid: " + e);
            System.exit(-1);
        }
    }

    public static void main(final String[] args) throws Exception {

        final TestConfig config = TestConfig.load();
        System.setProperty("java.security.krb5.conf", config.getKerberosConfigFile().get().toString());
        System.setProperty("sun.security.krb5.debug", "true");

        final KerberosConnectionFixture sample = new KerberosConnectionFixture(config);
        sample.connect();
    }

    KerberosConnectionFixture(final TestConfig config) throws Exception {
        this.config = config;
        if (this.config.getJaasFile().isPresent()) {
            System.setProperty("java.security.auth.login.config", this.config.getJaasFile().get().toString());
        }
        this.serviceSubject = doInitialLogin();
        try {
            this.impersonationCredentials = kerberosImpersonate();
        } catch (final Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }

    }

    void connect() {

        try {
            final Connection con = createConnectionWithRunAs();

            final ResultSet result = con.createStatement().executeQuery(getQuery());
            while (result.next()) {
                System.out.println(" User on DB: " + result.getString(1));
            }
        } catch (final Exception ex) {

            System.out.println(" Exception caught in createConnection ");
            ex.printStackTrace();
        }
    }

    void connectWithImpersonate() {
        try {
            // Create a connection for target service thanks S4U2proxy mechanism
            final Connection con = createConnectionWithImpersonation();

            final ResultSet result = con.createStatement().executeQuery(getQuery());
            while (result.next()) {
                System.out.println(" User on DB: " + result.getString(1));
            }
        } catch (final Exception ex) {

            System.out.println(" Exception caught in createConnection ");
            ex.printStackTrace();
        }
    }

    private Subject doInitialLogin() throws Exception {
        final Subject serviceSubject = new Subject();

        LoginModule krb5Module = null;
        try {
            krb5Module = new Krb5LoginModule();
        } catch (final Exception e) {
            System.out.print("Error loading Krb5LoginModule module: " + e);
            throw e;
        }

        System.setProperty("sun.security.krb5.debug", String.valueOf(true));

        final Map<String, String> options = new HashMap<>();
        options.put("principal", this.config.getRunAsUser().get());
        options.put("useKeyTab", "true");
        options.put("doNotPrompt", "true");
        options.put("keyTab", this.config.getKeytabFile().get().toString());
        options.put("isInitiator", "true");
        options.put("refreshKrb5Config", "true");

        System.out.println("Retrieving TGT for runAsUser using keytab");

        krb5Module.initialize(serviceSubject, null, null, options);
        try {
            krb5Module.login();
            krb5Module.commit();
        } catch (final LoginException e) {
            System.out.println("Error authenticating with Kerberos: " + e);
            try {
                krb5Module.abort();
            } catch (final LoginException e1) {
                System.out.println("Error aborting Kerberos authentication:  " + e1);
            }
            throw e;
        }

        return serviceSubject;
    }

    /**
     * Generate the impersonated user credentials using S4U2self mechanism
     *
     * @return the client impersonated GSSCredential
     * @throws PrivilegedActionException in case of failure
     */
    private GSSCredential kerberosImpersonate() throws PrivilegedActionException {
        return Subject.doAs(this.serviceSubject, (PrivilegedExceptionAction<GSSCredential>) () -> {
            final GSSManager manager = GSSManager.getInstance();
            final GSSName selfName = manager.createName(this.config.getRunAsUser().get(), GSSName.NT_USER_NAME);

            final GSSCredential selfCreds = manager.createCredential(selfName, GSSCredential.INDEFINITE_LIFETIME,
                    krb5Oid,
                    GSSCredential.INITIATE_ONLY);
            System.out.println("Got self credentials " + selfCreds);

            final GSSName dbUser = manager.createName(this.config.getImpersonatedUser(), GSSName.NT_USER_NAME);

            System.out.println("Impersonating user " + dbUser);
            return ((ExtendedGSSCredential) selfCreds).impersonate(dbUser);
        });
    }

    private Connection createConnectionWithRunAs() throws PrivilegedActionException {
        return Subject.doAs(this.serviceSubject, (PrivilegedExceptionAction<Connection>) () -> {
            return getConnection();
        });
    }

    private Connection createConnectionWithImpersonation() throws PrivilegedActionException {
        this.serviceSubject.getPrivateCredentials().add(this.impersonationCredentials);
        return Subject.doAs(this.serviceSubject, (PrivilegedExceptionAction<Connection>) () -> {
            return getConnection();
        });
    }

    private Connection getConnection() throws SQLException {
        final Properties driverProperties = new Properties();
        driverProperties.put("gsslib", "gssapi");
        driverProperties.put("jaasLogin", "false");
        driverProperties.put("user", this.serviceSubject);
        System.out.println("Connecting using url " + this.config.getJdbcUrl());
        System.out.println("   properties: " + driverProperties);
        return DriverManager.getConnection(this.config.getJdbcUrl(), driverProperties);
    }

    protected String getQuery() {
        // the current_user function works in for Presto Server 0.203 but NOT 0.167
        return "SELECT current_user";
    }

}
