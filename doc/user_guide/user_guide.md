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

    ```bat
    "<path to tableau.exe file>" -DConnectPluginsPath=<path to connector>
    ```

    Example:

    ```bat
    "C:\Program Files\Tableau\Tableau <version>\bin\tableau.exe" -DConnectPluginsPath=C:\Users\user\git\tableau-connector\src
    ```

### With Tableau Server

#### Install JDBC and/or ODBC drivers

* Download the latest JDBC and/or ODBC drivers for your operating system from the [Exasol download page](https://www.exasol.com/portal/display/DOWNLOAD/).
* Install the drivers.
* Copy the JDBC driver `exajdbc.jar` to directory `/opt/tableau/tableau_driver/jdbc/`.
* Create or edit file `/etc/odbcinst.ini` and add the following entry. Adapt the path to the ODBC driver if necessary.

```ini
[EXASolution Driver]
Driver=/opt/exasol/odbc/lib/linux/x86_64/libexaodbc-uo2214lv2.so
```

#### Install the Connector

* Clone the Tableau-Connector repository:

    ```shell
    git clone https://github.com/exasol/tableau-connector.git
    ```

* Package the connector with the script we provide (requires python to be installed):

    ```shell
    /bin/bash <path to connector directory>/tableau-connector/tableau-server-GUI-tests/set_up_scripts/package_connector.sh
    ```
    
    Note that we run the command above in the [Git Bash](https://gitforwindows.org/) terminal.

* Copy the `exasol_jdbc.taco` and/or `exasol_odbc.taco` file to the following path inside Tableau Server:

    ```shell
    /var/opt/tableau/tableau_server/data/tabsvc/vizqlserver/Connectors/exasol_jdbc.taco
    /var/opt/tableau/tableau_server/data/tabsvc/vizqlserver/Connectors/exasol_odbc.taco
    ```

* Disable the signature verification on the Tableau Server and apply changes:

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

Prerequisites:

* The Exasol database is configured to use [Kerberos Single Sign-On](https://docs.exasol.com/administration/on-premise/access_management/kerberos_sso.htm).
* Kerberos uses service name `exasol` for the database.
* You use the Kerberos hostname to connect to the database. Using an IP address is not possible.
* The clocks of all machines, especially the Exasol database must be in sync. We recommend to synchronize time by configuring an NTP Server.

#### Kerberos/Active Directory Without Username or Password

This is for database users created with Kerberos authentication:

```sql
CREATE USER <user> IDENTIFIED BY KERBEROS PRINCIPAL "<user@EXAMPLE.COM>";
```

To use this, select "Kerberos" in the "Authentication" drop-down-list. The connector will use your operating system's credentials to connect to Exasol.

See the list above for prerequisites.
