package com.exasol.kerberos;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;

import java.sql.*;
import java.util.function.Supplier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class KerberosSetupTest {

    private TestConfig config;
    private KerberosConnectionFixture kerberosConnectionFixture;

    @BeforeEach
    void setup() {
        this.config = TestConfig.load();
        this.kerberosConnectionFixture = new KerberosConnectionFixture(this.config);
    }

    @Test
    void impersonate() {
        final String expectedUser = this.config.getImpersonatedUserDbName().orElse(this.config.getImpersonatedUser());
        assertDbUser(this.kerberosConnectionFixture::createConnectionWithImpersonation,
                expectedUser);
    }

    @Test
    void runAs() {
        assertDbUser(this.kerberosConnectionFixture::createConnectionWithRunAs, this.config.getRunAsUser().get());
    }

    private void assertDbUser(final Supplier<Connection> connectionSupplier, final String expectedUser) {
        try (Connection connection = connectionSupplier.get()) {
            final String user = getCurrentDbUser(connection);
            assertThat(user, equalToIgnoringCase(expectedUser));
        } catch (final SQLException exception) {
            throw new IllegalStateException("Error getting db user", exception);
        }
    }

    private String getCurrentDbUser(final Connection connection) {
        try (ResultSet result = connection.createStatement().executeQuery("SELECT current_user")) {
            if (!result.next()) {
                throw new IllegalStateException("Query did not return a result");
            }
            final String user = result.getString(1);
            System.out.println("Connected to DB as user '" + user + "'");
            return user;
        } catch (final SQLException exception) {
            throw new IllegalStateException("Error executing query", exception);
        }
    }
}
