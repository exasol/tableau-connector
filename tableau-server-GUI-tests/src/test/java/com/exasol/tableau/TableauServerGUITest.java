package com.exasol.tableau;

import org.junit.jupiter.api.*;

class TableauServerGUITest {
    private static TableauServerGateway tableauServerGateway;

    @BeforeEach
    public void beforeEach() {
        tableauServerGateway = TableauServerGateway.connectTo("<HOST>");
        tableauServerGateway.login("<USERNAME>", "<PASS>");
    }

    @AfterEach
    public void afterEach() {
        tableauServerGateway.logout();
        tableauServerGateway.closeConnection();
    }

    @Test
    public void connectToExasolDatasource() {
        tableauServerGateway.createWorkbookForConnector("Exasol by Exasol");
        // verify
    }

}
