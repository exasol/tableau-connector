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
        final String workbookName = tableauServerGateway.getEstablishedConnectionName();
        assertThat(workbookName, equalTo(EXASOL_HOSTNAME));
    }
}