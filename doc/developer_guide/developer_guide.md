# Developer Guide

This developer guide describes how to

* Test the connectors manually
* Run Tableau's TDVT test suite
* Run UI tests for Tableau Server

## Manually Testing Connectors 

To manually test the connectors in Tableau Desktop without packaging, add the following arguments when starting `tableau.exe`:

* `-DConnectPluginsPath=path\to\tableau-connector\src`: Path to the `src` directory of this cloned repository.
* `-DLogLevel=Debug`: enable log output of `logging.Log()` in JavaScript files.

Start Tableau Desktop:

```bat
"C:\Program Files\Tableau\Tableau 2021.3\bin\tableau.exe" -DConnectPluginsPath=%USERPROFILE%\git\tableau-connector\src -DLogLevel=Debug
```

In the left bar under "To a Server" click `More...`, then click `Exasol JDBC by Exasol AG` or `Exasol ODBC by Exasol AG` to open the database connection dialog.

Restart Tableau after modifying any connector file to reload changes.

## Packaging the Connectors

This requires `python3-venv` to be installed.

To package the JDBC and ODBC connectors, execute

```sh
cd tableau-server-GUI-tests
./set_up_scripts/package_connector.sh
```

This validates the connectors and creates the connectors at

```
tableau-server-GUI-tests/target/exasol_jdbc.taco
tableau-server-GUI-tests/target/exasol_odbc.taco
```

To use the connectors, copy them to `C:\Program Files\Tableau\Connectors`.

As the connectors are not signed, you need to start Tableau Desktop with argument `-DDisableVerifyConnectorPluginSignature`.

## Running TDVT Tests

You can run TDVT tests under Windows and macOS. This guide describes the setup for Windows. The setup for macOS is similar.

### Initial Setup

* Create a new Exasol database running on port `8563`.
* Prepare database schema by running [tools/load_tvdt_test_data.sql](../../tools/load_tvdt_test_data.sql).
* Configure hostname of the Exasol database: Add an entry to `c:\Windows\System32\Drivers\etc\hosts`. Adapt the IP to your database:

    ```
    10.0.0.2    exasol.test.lan
    ```

* Install TDVT as described in the [TDVT documentation](https://tableau.github.io/connector-plugin-sdk/docs/tdvt#set-up).
* Update the Exasol certificate fingerprint in the four `*.tds` files.
* Update the path to `tabquerytool.exe` (e.g. `C:\Program Files\Tableau\Tableau 2021.3\bin\tabquerytool.exe`) in
  * [tdvt_jdbc/config/tdvt/tdvt_override.ini](../../tdvt_jdbc/config/tdvt/tdvt_override.ini)
  * [tdvt_odbc/config/tdvt/tdvt_override.ini](../../tdvt_odbc/config/tdvt/tdvt_override.ini)

### Configure Test Suites

You can configure the tests suites to run in files
* [tdvt_jdbc/config/exasol_jdbc.ini](../../tdvt_jdbc/config/exasol_jdbc.ini)
* [tdvt_odbc/config/exasol_odbc.ini](../../tdvt_odbc/config/exasol_odbc.ini)

After modifying these files you need to re-generate the test suite by adding the `--generate` argument to the `tdvt.tdvt run` command.

See the [manual](https://tableau.github.io/connector-plugin-sdk/docs/tdvt#ini-file-structure) for details about the available tests.

### Run Tests

* JDBC Connector:

    ```bash
    cd tdvt_jdbc
    python -m tdvt.tdvt run exasol_jdbc --generate
    python -m tdvt.tdvt run exasol_jdbc
    ```

* ODBC Connector:

    ```bash
    cd tdvt_odbc
    python -m tdvt.tdvt run exasol_odbc --generate
    python -m tdvt.tdvt run exasol_odbc
    ```

See the [manual](https://tableau.github.io/connector-plugin-sdk/docs/tdvt#about-running-tdvt) for details.

### Viewing Test Results

TDVT writes the test result to `test_results_combined.csv`. You can view it by opening the `TDVT Results.twb` workbook with Tableau Desktop. After re-running the tests type `F5` to refresh the results. See [detailed instructions](https://tableau.github.io/connector-plugin-sdk/docs/tdvt#review-results).

### Troubleshooting

Log files of Tableau Desktop: `%USERPROFILE%\Documents\My Tableau Repository\Logs\`:
* `log.txt`
* `jprotocolserver.log`

Also see the [FAQ and troubleshooting section of the manual](https://tableau.github.io/connector-plugin-sdk/docs/tdvt#frequently-found-issues-and-troubleshooting).

## Tableau Server UI Tests

### Setup

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

### Run the Tests

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

### Run the Tests Remotely

Startup of the Tableau Server takes a long time. We recommend using an AWS instance running "Amazon Linux 2" with 16 CPUs, e.g. `c5a.4xlarge`. With this instance type, startup will take about 15 to 20 minutes instead of 30 minutes.

You can either run the tests completely on the remote machine or start them on your local machine.

#### Start tests on remote machine

This assumes you are using an AWS instance running Amazon Linux 2.

1. Setup the instance with a UI as described [here](https://aws.amazon.com/premiumsupport/knowledge-center/ec2-linux-2-install-gui/).
2. Install a Chrome browser as this will be used for running the UI tests.
3. Install Java 11 (`yum install java-11-amazon-corretto`) and the latest Maven version.
4. Start the VNC server on the instance with `vncserver -geometry 1920x1080`.
5. Tunnel the VNC server port via `ssh <host> -L 5901:localhost:5901`.
6. Start a local VNC client and connect to `localhost:5901`.
7. In the VNC session make sure you can start the `chrome` browser.
8. Run the tests via `mvn integration-test`.

#### Start tests on local machine

If you want to trigger the tests from your local IDE, you need to forward the required ports and configure your local environment:

```sh
ssh ec2-user@<remote machine address> -i <ssh-key> -L 2375:localhost:2375 -L 33445:localhost:33445 -L 33446:localhost:33446
```

Specify the host where the docker daemon is running and disable the Ryuk container:

```sh
export DOCKER_HOST=tcp://localhost:2375
export TESTCONTAINERS_RYUK_DISABLED=true
```

### Troubleshooting

#### Tableau Server Container Startup Fails

When the Tableau server container stops after some minutes, you can start the container with environment variable `TSM_ONLY=1`, attach to the container and start the server with `"${DOCKER_CONFIG}"/config/tsm-commands`. See [detailed instructions](https://help.tableau.com/current/server-linux/en-gb/server-in-container_troubleshoot.htm).

One possible root cause is an invalid license key. To check if the license is valid, run `tsm licenses list` in the container. You can try to get a trial license by running `tsm licenses activate --trial`.
