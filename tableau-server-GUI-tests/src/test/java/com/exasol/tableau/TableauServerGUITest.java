package com.exasol.tableau;

import static com.exasol.tableau.TableauServerConfiguration.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.*;

class TableauServerGUITest {
    private static TableauServerGateway tableauServerGateway;

    @BeforeEach
    public void beforeEach() {
        tableauServerGateway = TableauServerGateway.connectTo(TABLEAU_HOSTNAME);
        tableauServerGateway.login(TABLEAU_USERNAME, TABLEAU_PASSWORD);
    }

    @AfterEach
    public void afterEach() {
        tableauServerGateway.logout();
        tableauServerGateway.closeConnection();
    }

    @Test
    void connectToExasolDatasource() {
        tableauServerGateway.createWorkbookForConnector("Exasol by Exasol", EXASOL_HOSTNAME, EXASOL_USERNAME,
                EXASOL_PASSWORD);
        assertThat(tableauServerGateway.getEstablishedConnectionName(), equalTo(EXASOL_HOSTNAME));
        tableauServerGateway.renameConnection("Renamed_connection");
        assertThat(tableauServerGateway.getEstablishedConnectionName(), equalTo("Renamed_connection"));
    }

    @Test
    void duplicateExasolDatasource() {
        tableauServerGateway.createWorkbookForConnector("Exasol by Exasol", EXASOL_HOSTNAME, EXASOL_USERNAME,
                EXASOL_PASSWORD);
        final String duplicateName = tableauServerGateway.duplicateDataSource();
        assertThat(duplicateName, equalTo(EXASOL_HOSTNAME + " (copy)"));
    }

    @Test
    void createAndRefreshExtract() {
        tableauServerGateway.createWorkbookForConnector("Exasol by Exasol", EXASOL_HOSTNAME, EXASOL_USERNAME,
                EXASOL_PASSWORD);
        tableauServerGateway.createExtract("TESTV1", "Calcs");
    }
}