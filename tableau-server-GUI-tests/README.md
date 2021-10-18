# Tableau Server GUI Integration Tests

## Setup

1. Follow the [instructions](https://help.tableau.com/current/server-linux/en-us/server-in-container_setup-tool.htm) to create a container. This should result in an image `tableau_server_image:<version>`, e.g. `tableau_server_image:20213.21.0917.1006`.
2. Update `tableau-server-GUI-tests/set_up_scripts/Dockerfile`:
   * Use the same image in the `FROM` line, e.g. `tableau_server_image:20213.21.0917.1006`
   * Update the download urls to the latest Exasol release.
4. Run

    ```bash
    cd tableau-server-GUI-tests/set_up_scripts
    docker build . --tag tablau_server_with_exasol_drivers
    ```

## Run the Tests

To run the test you need to create the `/src/test/resources/credentials.properties` file with the following content (replace the placeholders for real values):

```properties
TABLEAU_USERNAME=<tableau-server-username>
TABLEAU_PASSWORD=<tableau-server-password>
TABLEAU_LICENSE_KEY=<tableau-server-license-key>
```

Start the tests with

```sh
cd tableau-server-GUI-tests
mvn integration-test
```

## Run the Tests Remotely

Running the Tableau Server GUI tests in your local machine might be impractical as you need a running Tableau Server container which might require more resources than your machine can provide.

One solution for this is running the Tableau Server container on a remote (more powerful) machine, but still being able to trigger the tests from your local IDE. For this, you need to connect to specify the host where the docker daemon is running, as follows:

```sh
export DOCKER_HOST=tcp://<localhost>:<port>
```

You also need to disable the Ryuk container, as follows:

```sh
export TESTCONTAINERS_RYUK_DISABLED=true
```

Have mind that during the execution of the tests you will need to connect to the Exasol and Tableau Server running containers through the `33445` and `33446` ports, as well to the docker daemon running on the remote machine.

One way to do this is to establish an SSH connection and forward the ports via SSH tunnel to the machine running the docker containers (including the docker daemon port):

```sh
ssh <remote machine address> -L <docker_daemon_mapped_port>:localhost:2375 -L 33445:localhost:33445 -L 33446:localhost:33446
```
