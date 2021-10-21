package com.exasol.tableau;

import static com.exasol.tableau.TableauServerConfiguration.EXASOL_PORT;

import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

import com.exasol.tableau.Workbook.Builder;

class JdbcConnectorIT extends TableauServerUiBaseIT {

    @Override
    protected Builder createWorkbookBuilder() {
        return Workbook.builder().workbookName("Test_workbook")
                .connectorName("Exasol JDBC by Exasol AG")
                .hostname(DOCKER_NETWORK_ADDRESS)
                .port(EXASOL.getMappedPort(EXASOL_PORT).toString())
                .username(EXASOL.getUsername())
                .password(EXASOL.getPassword())
                .fingerprint(getActualFingerprint())
                .validateServerCertificate(true)
                .databaseName("EXA_DB");
    }

    private String getActualFingerprint() {
        return extractFingerprint(EXASOL.getJdbcUrl());
    }

    private static String extractFingerprint(final String jdbcUrl) {
        if (jdbcUrl.contains("validateservercertificate=0")) {
            throw new AssertionError("Jdbc url '" + jdbcUrl + "' does not validate certificate");
        }
        final java.util.regex.Matcher matcher = Pattern.compile("jdbc:exa:[^/]+/([^:]+):.*").matcher(jdbcUrl);
        if (!matcher.matches()) {
            throw new IllegalStateException("Error extracting fingerprint from '" + jdbcUrl + "'");
        }
        return matcher.group(1);
    }

    @Test
    void connectToExasolDatasourceWithoutFingerprint() {
        final Workbook workbook = createWorkbookBuilder().fingerprint("").validateServerCertificate(true).build();
        assertCreatingWorkbookFails(workbook, "unable to find valid certification path to requested target");
    }

    @Test
    void connectToExasolDatasourceWrongFingerprint() {
        final Workbook workbook = createWorkbookBuilder().fingerprint("wrongFingerprint")
                .validateServerCertificate(true).build();
        assertCreatingWorkbookFails(workbook, "Fingerprint did not match");
    }

    @Test
    void connectToExasolDatasourceIgnoringCertificateSucceeds() {
        final Workbook workbook = createWorkbookBuilder().validateServerCertificate(false).build();
        assertCreatingWorkbookSucceeds(workbook);
    }
}
