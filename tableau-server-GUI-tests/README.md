# Tableau Server GUI Integration Tests

## Setup

1. Follow the [instructions](https://help.tableau.com/current/server-linux/en-us/server-in-container_setup-tool.htm) to create a container. This should result in an image `tableau_server_image:<version>`, e.g. `tableau_server_image:20213.21.0917.1006`.
2. Update `tableau-server-GUI-tests/set_up_scripts/Dockerfile`:
   * Use the same image created before in the `FROM` line, e.g. `tableau_server_image:20213.21.0917.1006`.
   * Update the download urls to the latest Exasol release if available.
3. Update constant `DEFAULT_DOCKER_DB_REFERENCE` in class `TableauServerGUITestIT` to the latest Exasol release.
4. Build a new container including the Exasol ODBC and JDBC drivers by running

    ```bash
    cd tableau-server-GUI-tests/set_up_scripts
    docker build . --tag tablau_server_with_exasol_drivers
    ```

## Run the Tests

To run the test you need to create the `tableau-server-GUI-tests/src/test/resources/credentials.properties` file with the following content (replace the placeholders for real values):

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

Startup of the Tableau Server takes a long time. We recommend using an AWS instance running "Amazon Linux 2" with 16 CPUs, e.g. `c5a.4xlarge`. With this instance type, startup will take about 15 to 20 minutes instead of 30 minutes.

You can either run the tests completely on the remote machine or start them on your local machine.

### Start tests on remote machine

1. Setup the instance with a UI as described [here](https://aws.amazon.com/premiumsupport/knowledge-center/ec2-linux-2-install-gui/).
2. Install a Chrome browser as this will be used for running the UI tests.
3. Install Java 11 (`yum install java-11-amazon-corretto`) and the latest Maven version.
4. Start the VNC server on the instance with `vncserver -geometry 1920x1080`.
5. Tunnel the VNC server port via `ssh <host> -L 5901:localhost:5901`.
6. Start a local VNC client and connect to `localhost:5901`.
7. In the VNC session make sure you can start the `chrome` browser.
8. Run the tests via `mvn integration-test`.


### Start tests on local machine

If you want to trigger the tests from your local IDE, you need to forward the required ports and configure your local environment:

```sh
ssh ec2-user@<remote machine address> -i <ssh-key> -L 2375:localhost:2375 -L 33445:localhost:33445 -L 33446:localhost:33446
```

Specify the host where the docker daemon is running and disable the Ryuk container:

```sh
export DOCKER_HOST=tcp://localhost:2375
export TESTCONTAINERS_RYUK_DISABLED=true
```

## Troubleshooting

### Tableau Server Container Startup Fails

When the Tableau server container stops after some minutes, you can start the container with environment variable `TSM_ONLY=1`, attach to the container and start the server with `"${DOCKER_CONFIG}"/config/tsm-commands`. See [detailed instructions](https://help.tableau.com/current/server-linux/en-gb/server-in-container_troubleshoot.htm).

One possible root cause is an invalid license key. To check if the license is valid, run `tsm licenses list` in the container. You can try to get a trial license by running `tsm licenses activate --trial`.
