package com.exasol.tableau;

import static com.exasol.tableau.TableauServerConfiguration.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.*;
import java.time.Duration;
import java.util.logging.Logger;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.MountableFile;

import com.github.dockerjava.api.model.*;

public class TableauServerSetUp {
    private static final Logger LOGGER = Logger.getLogger(TableauServerSetUp.class.getName());
    private static final String REQUESTED_LEASE_TIME_IN_SECONDS = "60";

    private static final Path ODBC_CONNECTOR_FILE = Paths.get("../target/exasol_odbc.taco").toAbsolutePath();
    private static final Path JDBC_CONNECTOR_FILE = Paths.get("../target/exasol_jdbc.taco").toAbsolutePath();

    public static GenericContainer<?> TABLEAU_SERVER_CONTAINER = new GenericContainer<>(TABLEAU_SERVER_DOCKER_IMAGE)
            .withExposedPorts(TABLEAU_PORT)//
            .withCreateContainerCmdModifier(cmd -> cmd.withHostConfig(new HostConfig().withPortBindings(
                    new PortBinding(Ports.Binding.bindPort(TABLEAU_MAPPED_PORT), new ExposedPort(TABLEAU_PORT))))) //
            .withEnv("TABLEAU_USERNAME", TABLEAU_USERNAME) //
            .withEnv("TABLEAU_PASSWORD", TABLEAU_PASSWORD) //
            .withEnv("LICENSE_KEY", TABLEAU_LICENSE_KEY) //
            .withEnv("REQUESTED_LEASE_TIME", REQUESTED_LEASE_TIME_IN_SECONDS) //
            .waitingFor(Wait.forLogMessage(".*INFO exited: run-tableau-server.*", 1)) //
            .withStartupTimeout(Duration.ofMinutes(40)) //
            .withReuse(true);

    public static void setUpServer() throws UnsupportedOperationException, IOException, InterruptedException {
        checkPreconditions();
        startContainer();
        setUpConnector();
    }

    private static void checkPreconditions() {
        assertFileExists(ODBC_CONNECTOR_FILE);
        assertFileExists(JDBC_CONNECTOR_FILE);
    }

    private static void startContainer() {
        TABLEAU_SERVER_CONTAINER.start();
    }

    private static void setUpConnector() throws UnsupportedOperationException, IOException, InterruptedException {
        copyConnectorsToServer();
        disableConnectorSignatureVerification();
        applyChanges();
    }

    private static void copyConnectorsToServer() {
        LOGGER.info("Copying the connectors to Tableau Server");
        TABLEAU_SERVER_CONTAINER.copyFileToContainer(MountableFile.forHostPath(ODBC_CONNECTOR_FILE),
                "/var/opt/tableau/tableau_server/data/tabsvc/vizqlserver/Connectors/exasol_odbc.taco");
        TABLEAU_SERVER_CONTAINER.copyFileToContainer(MountableFile.forHostPath(JDBC_CONNECTOR_FILE),
                "/var/opt/tableau/tableau_server/data/tabsvc/vizqlserver/Connectors/exasol_jdbc.taco");
    }

    private static void assertFileExists(final Path path) {
        assertTrue(Files.exists(path),
                "Connector not found at " + path + ". Run 'package_connector.sh' to generate it.");
    }

    private static void disableConnectorSignatureVerification() throws IOException, InterruptedException {
        LOGGER.info("Disabling connectors signature");
        TABLEAU_SERVER_CONTAINER.execInContainer("tsm", "configuration", "set", "-k",
                "native_api.disable_verify_connector_plugin_signature", "-v", "true", "--force-keys");
    }

    private static void applyChanges() throws UnsupportedOperationException, IOException, InterruptedException {
        LOGGER.info("Applying changes on Tableau Server");
        TABLEAU_SERVER_CONTAINER.execInContainer("tsm", "pending-changes", "apply", "--ignore-prompt");
    }

    public static String getTableauServerConnectionURL() {
        return "http://" + TABLEAU_SERVER_CONTAINER.getHost() + ":"
                + TABLEAU_SERVER_CONTAINER.getMappedPort(TABLEAU_PORT);
    }
}