package com.exasol.tableau;

import static com.exasol.tableau.TableauServerConfiguration.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.*;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.exasol.containers.ExasolContainer;
import com.github.dockerjava.api.model.*;

@Testcontainers
class TableauServerGUITestIT {
    private static final String CONNECTOR_NAME = "Exasol by Exasol";
    private static final String DEFAULT_DOCKER_DB_REFERENCE = "7.1.1";
    public static final String DOCKER_NETWORK_ADDRESS = "172.17.0.1";
    private static TableauServerGUIGateway tableauServerGateway;

    @Container
    protected static final ExasolContainer<? extends ExasolContainer<?>> EXASOL = new ExasolContainer<>(
            DEFAULT_DOCKER_DB_REFERENCE)//
                    .withCreateContainerCmdModifier(cmd -> cmd.getHostConfig().withPortBindings(List.of( //
                            new PortBinding(Ports.Binding.bindPort(EXASOL_MAPPED_PORT), new ExposedPort(EXASOL_PORT)), //
                            new PortBinding(Ports.Binding.bindPort(EXASOL_BUCKETFS_MAPPED_PORT),
                                    new ExposedPort(EXASOL_BUCKETFS_PORT)) //
                    ))) //
                    .withExposedPorts(EXASOL_PORT, EXASOL_BUCKETFS_PORT) //
                    .withReuse(true);

    @BeforeAll
    static void beforeAll() throws UnsupportedOperationException, IOException, InterruptedException {
        TableauServerSetUp.setUpServer();
        prepareExasolDatabase();
    }

    private static void prepareExasolDatabase() {
        try (final Statement statement = EXASOL.createConnection().createStatement()) {
            final String sqlFile = Files.readString(Path.of("src", "test", "resources", "populate_table.sql"));
            final String[] sqlCommands = sqlFile.split(";");
            for (final String command : sqlCommands) {
                statement.execute(command);
            }
        } catch (final SQLException | IOException exception) {
            throw new AssertionError("Error preparing Exasol DB", exception);
        }
    }

    @BeforeEach
    public void beforeEach() {
        tableauServerGateway = TableauServerGUIGateway.connectTo(TableauServerSetUp.getTableauServerConnectionURL());
        tableauServerGateway.login(TABLEAU_USERNAME, TABLEAU_PASSWORD);
    }

    @AfterEach
    public void afterEach() {
        tableauServerGateway.logout();
        tableauServerGateway.closeConnection();
    }

    @Test
    void connectToExasolDatasource() {
        this.createWorkbook();
        assertThat(tableauServerGateway.getEstablishedConnectionName(), equalTo(DOCKER_NETWORK_ADDRESS));
        tableauServerGateway.renameConnection("Renamed_connection");
        assertThat(tableauServerGateway.getEstablishedConnectionName(), equalTo("Renamed_connection"));
    }

    private void createWorkbook() {
        final Workbook workbook = createWorkbookWithPassword(EXASOL.getPassword());
        final Optional<String> errorMessage = tableauServerGateway.createWorkbook(workbook);
        assertTrue(errorMessage.isEmpty());
    }

    private Workbook createWorkbookWithPassword(final String password) {
        return Workbook.builder().workbookName("Test_workbook").connectorName(CONNECTOR_NAME)
                .hostname(DOCKER_NETWORK_ADDRESS).port(EXASOL.getMappedPort(EXASOL_PORT).toString())
                .username(EXASOL.getUsername()).password(password).build();
    }

    @Test
    void duplicateExasolDatasource() {
        this.createWorkbook();
        final String duplicateName = tableauServerGateway.duplicateDataSource();
        assertThat(duplicateName, equalTo(DOCKER_NETWORK_ADDRESS + " (copy)"));
    }

    @Test
    void createAndRefreshExtract() {
        this.createWorkbook();
        tableauServerGateway.openSchema("TESTV1");
        tableauServerGateway.openTable("Calcs");
        final String message = tableauServerGateway.createExtract();
        assertThat(message, containsString("Success"));
        tableauServerGateway.switchSheet("Sheet 1");
        tableauServerGateway.updateExtract("TESTV1", "Calcs");
    }

    @Test
    void connectToExasolDatasourceWithWrongCredentials() {
        final Workbook workbook = this.createWorkbookWithPassword("Wrong Password");
        final Optional<String> errorMessage = tableauServerGateway.createWorkbook(workbook);
        assertAll(() -> assertThat(errorMessage.isPresent(), equalTo(true)),
                () -> assertThat(errorMessage.get(), containsString("authentication failed")));
    }

    @Test
    void saveWorkbook() {
        final Workbook workbook = this.createWorkbookWithPassword(EXASOL.getPassword());
        tableauServerGateway.deleteWorkbookIfExists(workbook);
        tableauServerGateway.createWorkbook(workbook);
        tableauServerGateway.openSchema("TESTV1");
        tableauServerGateway.openTable("Calcs");
        tableauServerGateway.switchSheet("Sheet 1");
        tableauServerGateway.addToSheet("Num2", "Str1");
        tableauServerGateway.saveWorkbook(workbook);
        assertTrue(tableauServerGateway.workbookExists(workbook));
    }
}