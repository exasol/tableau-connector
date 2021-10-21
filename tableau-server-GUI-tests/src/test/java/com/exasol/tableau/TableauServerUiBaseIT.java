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
import com.exasol.containers.ExasolService;
import com.exasol.tableau.Workbook.Builder;
import com.github.dockerjava.api.model.*;

@Testcontainers
abstract class TableauServerUiBaseIT {

    private static final String DEFAULT_DOCKER_DB_REFERENCE = "7.1.1";
    public static final String DOCKER_NETWORK_ADDRESS = "172.17.0.1";
    protected static TableauServerGUIGateway tableauServerGateway;

    @Container
    protected static final ExasolContainer<? extends ExasolContainer<?>> EXASOL = new ExasolContainer<>(
            DEFAULT_DOCKER_DB_REFERENCE)//
                    .withCreateContainerCmdModifier(cmd -> cmd.getHostConfig().withPortBindings(List.of( //
                            new PortBinding(Ports.Binding.bindPort(EXASOL_MAPPED_PORT), new ExposedPort(EXASOL_PORT))))) //
                    .withExposedPorts(EXASOL_PORT) //
                    .withRequiredServices(ExasolService.JDBC) //
                    .withReuse(true);

    @BeforeAll
    static void beforeAll() throws UnsupportedOperationException, IOException, InterruptedException {
        TableauServerSetUp.setUpServer();
        prepareExasolDatabase();
    }

    private static void prepareExasolDatabase() {
        try (final Statement statement = EXASOL.createConnection().createStatement()) {
            final String sqlFile = Files.readString(Path.of("src/test/resources/populate_table.sql"));
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
        final Workbook workbook = createWorkbookBuilder().build();
        assertCreatingWorkbookSucceeds(workbook);
    }

    protected void assertCreatingWorkbookSucceeds(final Workbook workbook) {
        final Optional<String> errorMessage = tableauServerGateway.createWorkbook(workbook);
        if (errorMessage.isPresent()) {
            throw new AssertionError("Expected no error but got '" + errorMessage.get() + "'");
        }
    }

    protected abstract Builder createWorkbookBuilder();

    @Test
    void duplicateExasolDatasource() {
        final Workbook workbook = this.createWorkbookBuilder().build();
        this.assertCreatingWorkbookSucceeds(workbook);
        assertDuplicateWorkbookName(workbook);
    }

    private void assertDuplicateWorkbookName(final Workbook workbook) {
        final String expectedDbName = workbook.getDatabaseName() != null ? workbook.getDatabaseName()
                : DOCKER_NETWORK_ADDRESS;
        final String duplicateName = tableauServerGateway.duplicateDataSource();
        assertThat(duplicateName, equalTo(expectedDbName + " (copy)"));
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
        final Workbook workbook = this.createWorkbookBuilder().password("Wrong Password").build();
        assertCreatingWorkbookFails(workbook, "authentication failed");
    }

    protected void assertCreatingWorkbookFails(final Workbook configuration, final String expectedErrorMessage) {
        final Optional<String> errorMessage = tableauServerGateway.createWorkbook(configuration);
        assertAll(() -> assertThat(errorMessage.isPresent(), equalTo(true)),
                () -> assertThat(errorMessage.get(), containsString(expectedErrorMessage)));
    }

    @Test
    void saveWorkbook() {
        final Workbook workbook = this.createWorkbookBuilder().build();
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