package com.exasol.tableau;

import static com.exasol.tableau.TableauServerConfiguration.EXASOL_PORT;

import com.exasol.tableau.Workbook.Builder;

public class OdbcConnectorIT extends TableauServerUiBaseIT {

    @Override
    protected Builder createWorkbookBuilder() {
        return Workbook.builder().workbookName("Test_workbook")
                .connectorName("Exasol ODBC by Exasol AG")
                .hostname(DOCKER_NETWORK_ADDRESS)
                .port(EXASOL.getMappedPort(EXASOL_PORT).toString())
                .username(EXASOL.getUsername())
                .password(EXASOL.getPassword())
                .fingerprint(null)
                .validateServerCertificate(null)
                .databaseName(null);
    }
}
