package com.exasol.tableau;

import static com.exasol.tableau.TableauServerConfiguration.EXASOL_PORT;

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
        return EXASOL.getTlsCertificateFingerprint().get();
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
