package com.exasol.tableau;

import static com.exasol.tableau.TableauServerConfiguration.*;

import java.io.IOException;
import java.time.Duration;
import java.util.logging.Logger;

import org.testcontainers.containers.Container.ExecResult;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.MountableFile;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;

public class TableauServerSetUp {
    private static final Logger LOGGER = Logger.getLogger(TableauServerSetUp.class.getName());

    @Container
    public static GenericContainer<?> TABLEAU_SERVER_CONTAINER = new GenericContainer<>(TABLEAU_SERVER_DOCKER_IMAGE)
            .withExposedPorts(TABLEAU_PORT)//
            .withCreateContainerCmdModifier(cmd -> cmd.withHostConfig(new HostConfig().withPortBindings(
                    new PortBinding(Ports.Binding.bindPort(TABLEAU_MAPPED_PORT), new ExposedPort(TABLEAU_PORT))))) //
            .withEnv("TABLEAU_USERNAME", TABLEAU_USERNAME) //
            .withEnv("TABLEAU_PASSWORD", TABLEAU_PASSWORD) //
            .withEnv("LICENSE_KEY", TABLEAU_LICENSE_KEY) //
            .withEnv("REQUESTED_LEASE_TIME", "60") //
//            .withFileSystemBind("target/taco/", "/var/opt/tableau/tableau_server/data/tabsvc/vizqlserver/Connectors",
//                    BindMode.READ_ONLY)
            .withCopyFileToContainer(MountableFile.forHostPath("../src/exasol_odbc"), "/var/tmp/plugins/exasol_odbc")
            .waitingFor(Wait.forLogMessage(".*INFO exited: run-tableau-server.*", 1)) //
            .withStartupTimeout(Duration.ofSeconds(10000)) //
            .withReuse(true);

    public static void setUpServer() throws UnsupportedOperationException, IOException, InterruptedException {
        startContainer();
        setConnectorsDirectoryPath();
        applyChanges();
    }

    public static void teardownServer() throws UnsupportedOperationException {
    }

    private static void startContainer() {
        TABLEAU_SERVER_CONTAINER.start();
    }

    private static void setConnectorsDirectoryPath()
            throws UnsupportedOperationException, IOException, InterruptedException {
        LOGGER.info("Setting connectors directory path on Tableau Server");
        TABLEAU_SERVER_CONTAINER.execInContainer("tsm", "configuration", "set", "-k", "native_api.connect_plugins_path",
                "-v", "/var/tmp/plugins", "--force-keys");
    }

    private static void applyChanges() throws UnsupportedOperationException, IOException, InterruptedException {
        LOGGER.info("Applying changes on Tableau Server");
        final ExecResult result = TABLEAU_SERVER_CONTAINER.execInContainer("tsm", "pending-changes", "apply",
                "--ignore-prompt");
        LOGGER.info("Result of 'tsm pending-changes apply'. StdErr: " + result.getStderr() + " StdOut: "
                + result.getStdout());
    }

    public static String getTableauServerConnectionURL() {
        return "http://" + TABLEAU_SERVER_CONTAINER.getHost() + ":"
                + TABLEAU_SERVER_CONTAINER.getMappedPort(TABLEAU_PORT);
    }
}