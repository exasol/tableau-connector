package com.exasol.tableau;

import static com.exasol.tableau.TableauServerConfiguration.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

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
        this.createWorkbookForConnector();
        assertThat(tableauServerGateway.getEstablishedConnectionName(), equalTo(EXASOL_HOSTNAME));
        tableauServerGateway.renameConnection("Renamed_connection");
        assertThat(tableauServerGateway.getEstablishedConnectionName(), equalTo("Renamed_connection"));
    }

    private Workbook createWorkbookForConnector() {
        final Workbook workbook = Workbook.builder().workbookName("Test_workbook").connectorName("Exasol by Exasol")
                .host(EXASOL_HOSTNAME).username(EXASOL_USERNAME).password(EXASOL_PASSWORD).build();
        final Optional<String> errorMessage = tableauServerGateway.createWorkbookForConnector(workbook);
        assertTrue(errorMessage.isEmpty());
        return workbook;
    }

    @Test
    void duplicateExasolDatasource() {
        this.createWorkbookForConnector();
        final String duplicateName = tableauServerGateway.duplicateDataSource();
        assertThat(duplicateName, equalTo(EXASOL_HOSTNAME + " (copy)"));
    }

    @Test
    void createAndRefreshExtract() {
        this.createWorkbookForConnector();
        tableauServerGateway.openSchema("TESTV1");
        tableauServerGateway.openTable("Calcs");
        final String message = tableauServerGateway.createExtract();
        assertThat(message, containsString("Success"));
        tableauServerGateway.switchSheet("Sheet 1");
        tableauServerGateway.updateExtract("TESTV1", "Calcs");
    }

    @Test
    void connectToExasolDatasourceWithWrongCredentials() {
        final Workbook workbook = Workbook.builder().workbookName("Test_workbook").connectorName("Exasol by Exasol")
                .host(EXASOL_HOSTNAME).username(EXASOL_USERNAME).password("Wrong Password").build();
        final Optional<String> errorMessage = tableauServerGateway.createWorkbookForConnector(workbook);
        assertAll(() -> assertThat(errorMessage.isPresent(), equalTo(true)),
                () -> assertThat(errorMessage.get(), containsString("authentication failed")));
    }

    @Test
    void saveWorkbook() {
        final Workbook workbook = Workbook.builder().workbookName("Test_workbook").connectorName("Exasol by Exasol")
                .host(EXASOL_HOSTNAME).username(EXASOL_USERNAME).password(EXASOL_PASSWORD).build();
        tableauServerGateway.deleteWorkbookIfExists(workbook);
        tableauServerGateway.createWorkbookForConnector(workbook);
        tableauServerGateway.openSchema("TESTV1");
        tableauServerGateway.openTable("Calcs");
        tableauServerGateway.switchSheet("Sheet 1");
        tableauServerGateway.addToSheet("Num2", "Str1");
        tableauServerGateway.saveWorkbook(workbook);
        assertTrue(tableauServerGateway.checkWorkbookExists(workbook));
    }
}