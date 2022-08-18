# Developer Guide

This developer guide describes how to

* Test the connectors manually
* Run Tableau's TDVT test suite
* Run UI tests for Tableau Server

## Manually Testing Connectors 

### Tableau Desktop

To manually test the connectors in Tableau Desktop without packaging, add the following arguments when starting Tableau Desktop:

```bat
"C:\Program Files\Tableau\Tableau 2022.2\bin\tableau.exe" -DConnectPluginsPath=%USERPROFILE%\git\tableau-connector\src -DLogLevel=Debug
```

* `-DConnectPluginsPath=path\to\tableau-connector\src`: Path to the `src` directory of this cloned repository.
* `-DLogLevel=Debug`: enable log output of `logging.Log()` in JavaScript files.

After starting Tableau Desktop, click `More...` in the left bar under "To a Server", then click `Exasol JDBC by Exasol AG` or `Exasol ODBC by Exasol AG` to open the database connection dialog for JDBC resp. ODBC.

Restart Tableau after modifying any connector file to reload changes.

### Tableau Server

To allow using unsigned connectors, run

```bash
tsm configuration set -k native_api.disable_verify_connector_plugin_signature -v true --force-keys
tsm pending-changes apply --ignore-prompt
```

Package the connectors as described below then copy them to `C:\Program Files\Tableau\Connectors` (Windows) or `/var/opt/tableau/tableau_server/data/tabsvc/vizqlserver/Connectors/` (Linux) and restart Tableau Server:

```bash
./tools/package_connector.sh
# Linux:
cp -v target/*.taco /var/opt/tableau/tableau_server/data/tabsvc/vizqlserver/Connectors/
# Windows:
cp -v target/*.taco "/c/Program Files/Tableau/Connectors"
tsm restart
```

### Verify Connected User

To verify which user account Tableau is using for connecting to Exasol, create the following view and check it from Tableau:

```sql
CREATE SCHEMA IF NOT EXISTS META;
CREATE OR REPLACE VIEW META.MYSESSION AS
  SELECT USER_NAME AS SESSION_USER_NAME, CURRENT_USER
  FROM SYS.EXA_ALL_SESSIONS
  WHERE SESSION_ID=CURRENT_SESSION;
```

Then add view `META.MYSESSION` to a Tableau data source and add it to a workbook sheet.

## Packaging the Connectors

### Initial Setup

#### Prequisites for Linux

`python3-venv` and JDK 11 are required under Linux.

```sh
# Ubuntu:
sudo apt-get install python3-venv openjdk-11-jdk
```

#### Prerequisites for Windows

Download and install the following packages:
* Git for Windows and bash: [Git for Windows](https://git-scm.com/download/win)
* JDK 11, e.g. from [Adoptium](https://adoptium.net/?variant=openjdk11&jvmVariant=hotspot)
* [Python 3](https://www.python.org/downloads/windows/)

### Building the Connector

To package the JDBC and ODBC connectors, execute the following command in a `bash` shell:

```bash
./tools/package_connector.sh
```

This validates the connectors and creates the `.taco` files at

```
target/exasol_jdbc.taco
target/exasol_odbc.taco
```

To use the connectors, copy them to
* `C:\Users\[Windows User]\Documents\My Tableau Repository\Connectors` (Windows, Tableau Desktop)
* `C:\Program Files\Tableau\Connectors` (Windows, Tableau Server)
* `/Users/[user]/Documents/My Tableau Repository/Connectors` (macOS, Tableau Desktop)
* `/var/opt/tableau/tableau_server/data/tabsvc/vizqlserver/Connectors/` (Linux, Tableau Server)

As the connectors are not signed, you need to start Tableau Desktop with argument `-DDisableVerifyConnectorPluginSignature`.

### Signing the Connectors

To sign the built connectors you will need the certificate as a `.pfx` file and the keystore password.

Run the following command after building the connectors:

```bash
./tools/sign_connector.sh /path/to/certificate.pfx
```

This will ask you to enter the keystore password and write the signed connector files to

```
target/tableau-exasol-connector-jdbc-<version>.taco
target/tableau-exasol-connector-odbc-<version>.taco
```

## Running TDVT Tests

You can run TDVT tests under Windows and macOS. This guide describes the setup for Windows. The setup for macOS is similar.

### Initial Setup

* Create a new Exasol database running on port `8563`.
* Prepare database schema by running [tools/load_tvdt_test_data.sql](../../tools/load_tvdt_test_data.sql).
* Configure hostname of the Exasol database: Add an entry to `C:\Windows\System32\Drivers\etc\hosts` (adapt the IP to your database):

    ```
    10.0.0.2    exasol.example.com
    ```

* Install TDVT as described in the [TDVT documentation](https://tableau.github.io/connector-plugin-sdk/docs/tdvt#set-up).
* Update the Exasol certificate fingerprint in the four `*.tds` files:
  * [tdvt_jdbc/tds/cast_calcs.exasol_jdbc.tds](../../tdvt_jdbc/tds/cast_calcs.exasol_jdbc.tds)
  * [tdvt_jdbc/tds/Staples.exasol_jdbc.tds](../../tdvt_jdbc/tds/Staples.exasol_jdbc.tds)
  * [tdvt_odbc/tds/Staples.exasol_odbc.tds](../../tdvt_odbc/tds/Staples.exasol_odbc.tds)
  * [tdvt_odbc/tds/cast_calcs.exasol_odbc.tds](../../tdvt_odbc/tds/cast_calcs.exasol_odbc.tds)

* Update the path to `tabquerytool.exe` (e.g. `C:\Program Files\Tableau\Tableau 2022.2\bin\tabquerytool.exe`) in
  * [tdvt_jdbc/config/tdvt/tdvt_override.ini](../../tdvt_jdbc/config/tdvt/tdvt_override.ini)
  * [tdvt_odbc/config/tdvt/tdvt_override.ini](../../tdvt_odbc/config/tdvt/tdvt_override.ini)

### Configure Test Suites

You can configure the tests suites to run in files
* [tdvt_jdbc/config/exasol_jdbc.ini](../../tdvt_jdbc/config/exasol_jdbc.ini)
* [tdvt_odbc/config/exasol_odbc.ini](../../tdvt_odbc/config/exasol_odbc.ini)

After modifying these files you need to re-generate the test suite by adding the `--generate` argument to the `tdvt.tdvt run` command.

See the [manual](https://tableau.github.io/connector-plugin-sdk/docs/tdvt#ini-file-structure) for details about the available tests.

### Run JavaScript Tests

```bash
cd javascript-test
npm install
npm test
```

### Run Tests

To run TDVT tests for the JDBC and ODBC connectors, run

```bash
./tools/run_tdvt_tests.sh
```

This will collect test results in `target/tdvt_results_jdbc/` resp. `target/tdvt_results_odbc/`. Alternatively you can run the tests manually:

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

### Troubleshooting TDVT Tests

Log files of Tableau Desktop: `%USERPROFILE%\Documents\My Tableau Repository\Logs\`:
* `log.txt`
* `jprotocolserver.log`

Also see the [FAQ and troubleshooting section of the manual](https://tableau.github.io/connector-plugin-sdk/docs/tdvt#frequently-found-issues-and-troubleshooting).

If tests fail, check file `test_results_combined.csv`.

#### Smoke Test Fail With Message `Package signature verification failed during connection creation.`

Error message in `test_results_combined.csv`:

```
LoadDatasource DataSourceException: Package signature verification failed during connection creation.
```

This could mean that a connector is already installed as a `.taco` file. Make sure to remove all `.taco` files from `C:\Program Files\Tableau\Connectors`.

#### Smoke Test Fail With Message `Can't start FlexNet Licensing Service`

Error message in `test_results_combined.csv`:

```
Can't start FlexNet Licensing Service (try setting automatic start)
LoadDatasource TableauException: Unable to establish connection: Data source 'Exasol JDBC by Exasol AG' has not been licensed.
```

This could mean that you are connecting the test machine via SSH. Start the tests by logging in to the machine directly.

## Tableau Server UI Tests

The UI tests verify that the Exasol connector works with Tableau Server.

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

### Run the UI Tests

To run the tests you need to create the `tableau-server-GUI-tests/src/test/resources/credentials.properties` file with the following content (replace the placeholders for real values):

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

### Troubleshooting UI Tests

#### Tableau Server Startup Fails

Run `tsm status -v` to see the detailed status of all Tableau services.

#### Tableau Server Docker Container Startup Fails

When the Tableau server container stops after some minutes, you can start the container with environment variable `TSM_ONLY=1`, attach to the container and start the server with `"${DOCKER_CONFIG}"/config/tsm-commands`:

```shell
docker run --env TABLEAU_USERNAME=user --env TABLEAU_PASSWORD=password --env LICENSE_KEY=$license --env REQUESTED_LEASE_TIME=60 --env TSM_ONLY=1 --publish 8080:8080 --detach tablau_server_with_exasol_drivers:latest
```

See [detailed troubleshooting instructions](https://help.tableau.com/current/server-linux/en-gb/server-in-container_troubleshoot.htm).

One possible root cause is an invalid license key. To check if the license is valid, run `tsm licenses list` in the container. You can try to activate a license with `tsm licenses activate  -k $license_key` or get a trial license by running `tsm licenses activate --trial`.

## Manually Testing Kerberos Authentication

Testing that Kerberos authentication works with the connector requires setting up a Windows Domain Controller and Active Directory. You can use the [JDBC Kerberos setup test](../../jdbc-kerberos-setup-test/README.md) to verify Kerberos delegation works in your setup. We recommend using this if Kerberos authentication does not work with Tableau Server.

## Enable Debugging for the JDBC Connector

To see log messages from the connector JavaScript files in Tableau Desktop's log, start Tableau with command line argument `-DLogLevel=Debug`. Then open file `%USERPROFILE%\Documents\My Tableau Repository\Logs\log.txt` and search for string `JavascriptLoggingHelper::Log`.

Enable JDBC driver debugging and configure a log directory by editing `connectionBuilder.js`:

```js
const jdbcDriverDebugEnabled = true;
const jdbcDriverLogDir = "C:\\tmp"; // Windows
//const jdbcDriverLogDir = "/tmp"; // Linux
```

The JDBC driver will write log files to the specified directory.

To enable debugging of JDBC driver properties, edit file `connectionProperties.js`:

```js
const enableDebugging = true;
```

This will add value `jdbc-driver-debug` with a debug message to the JDBC driver properties. The JDBC driver will write this to its log file when debugging is enabled in `connectionBuilder.js`.

## Building a Release

To build a release of the JDBC and ODBC connectors follow these steps:

1. Update version number of the `plugin-version` element in the manifest files for JDBC and ODBC connectors:
  * [src\exasol_jdbc\manifest.xml](../../src/exasol_jdbc/manifest.xml)
  * [src\exasol_odbc\manifest.xml](../../src/exasol_odbc/manifest.xml)
2. [Run the TDVT tests](#running-tdvt-tests) for JDBC and ODBC connectors.
3. [Package](#packaging-the-connectors) and [sign](#signing-the-connectors) the JDBC and ODBC connectors.
4. [Create a new GitHub release](https://github.com/exasol/tableau-connector/releases/new) and upload files
  * `target/tableau-exasol-connector-jdbc-<version>.taco`
  * `target/tableau-exasol-connector-odbc-<version>.taco`
