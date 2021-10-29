# User Guide

The Exasol Tableau Connector is distributed together with Tableau Desktop and Tableau Server applications. We recommend using the latest available version of Tableau products to access the connector.

![JDBC connection dialog](../images/jdbc_connection_dialog.png "JDBC connection dialog")

If you want to use the currently developed version of connector, you can follow the guide below and install the connector disabling sign verification.

## Testing In-Development Connector

### Install the JDBC Driver

* [Download the JDBC driver](https://www.exasol.com/portal/display/DOWNLOAD/)
* Copy the driver to the Tableau installation directory:
  * Windows: `C:\Program Files\Tableau\Drivers`
  * Mac: `~/Library/Tableau/Drivers`
  * Linux: `/opt/tableau/tableau_driver/jdbc`

**Important note for Windows:** Make sure to download and install file `EXASOL_JDBC-<version>.msi`. The JDBC driver will be installed to `C:\Program Files\Exasol\EXASolution-7.1\JDBC\exajdbc.jar`. Only the JDBC driver for Windows supports Kerberos under Windows.

### With Tableau Desktop

* Clone the Tableau-Connector repository. You can execute the following command from [Git Bash](https://gitforwindows.org/) terminal:

    ```shell
    git clone https://github.com/exasol/tableau-connector.git
    ```

* Open Windows cmd and start Tableau Desktop providing the path to the connector directory and also disabling the signature check.

    ```shell
    "<path to tableau.exe file>" -DConnectPluginsPath=<path to connector>
    ```

    Example:

    ```shell
    "C:\Program Files\Tableau\Tableau <version>\bin\tableau.exe" -DConnectPluginsPath=C:\Users\user\git\tableau-connector\src
    ```

### With Tableau Server

* Clone the Tableau-Connector repository:

    ```shell
    git clone https://github.com/exasol/tableau-connector.git
    ```

* Package the connector with the script we provide (requires python installed):

    ```shell
    cd <path to connector directory>/tableau-connector
    /bin/bash tools/package_connector.sh
    ```
    
    Note that we run the command above in the [Git Bash](https://gitforwindows.org/) terminal.

* Copy the `exasol_odbc.taco` file to the following path inside Tableau Server:

    ```shell
    /var/opt/tableau/tableau_server/data/tabsvc/vizqlserver/Connectors/exasol_odbc.taco"
    ```

* Disable the sign verification on the Tableau Server and apply changes:

    ```shell
    tsm configuration set -k native_api.disable_verify_connector_plugin_signature -v true --force-keys
    tsm pending-changes apply --ignore-prompt
    ```

## Using the JDBC Connector

After installing the JDBC Connector, you can use it by selecting connector "EXASOL JDBC by Exasol AG". This will open a connection dialog where you can enter details for connecting to your Exasol database.

### TLS Certificate Validation

The JDBC connector will always create a TLS encrypted connection to the database.

Using the "Validate Server Certificate" checkbox you can configure if the connector should verify the database server's TLS certificate. This is on by default and we recommend to keep it on.

If your server does not have a valid TLS certificate with the correct hostname (e.g. the default self-signed certificate), you will need to enter the fingerprint of the servers certificate into text box "Server Certificate Fingerprint". This ensures that you connect to the correct server and there is no person-in-the-middle attack going on.

You can find the fingerprint via the EXAoperation user interface.

### Authentication

For authentication against the database you have three options:

1. Normal username and password
2. Kerberos/Active Directory username and password
3. Kerberos/Active Directory without username or password

#### Normal Username and Password

This is for database users created with a password:

```sql
CREATE USER <user> IDENTIFIED BY "<password>";
```

To use this, select "Username and Password" in the "Authentication" drop-down-list and enter username and password.

#### Kerberos/Active Directory Username and Password

This is for database users created with Kerberos authentication:

```sql
CREATE USER <user> IDENTIFIED BY KERBEROS PRINCIPAL "<user@EXAMPLE.COM>";
```

To use this, select "Username and Password" in the "Authentication" drop-down-list and enter username and password.

This requires that Kerberos uses service name `exasol` for the database.

#### Kerberos/Active Directory Without Username or Password

This is for database users created with Kerberos authentication:

```sql
CREATE USER <user> IDENTIFIED BY KERBEROS PRINCIPAL "<user@EXAMPLE.COM>";
```

To use this, select "Kerberos" in the "Authentication" drop-down-list. The connector will use your operating system's credentials to connect to Exasol.
