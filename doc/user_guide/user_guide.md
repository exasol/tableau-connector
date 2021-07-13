# User Guide

The Exasol Tableau Connector is distributed together with Tableau Desktop and Tableau Server applications. We recommend using the latest available version of Tableau products to access the connector.
If you want to use the currently developed version of connector, you can follow the guide below and install the connector disabling sign verification.

## Testing In-Development Connector

## With Tableau Desktop

* Clone the Tableau-Connector repository. You can execute the following command from [Git Bash](https://gitforwindows.org/) terminal:

```
git clone https://github.com/exasol/tableau-connector.git
```

* Open Windows cmd and start Tableau Desktop providing the path to the connector directory and also disabling the signature check.

```
"<path to tableau.exe file>" -DConnectPluginsPath=<path to connector> -DDisableVerifyConnectorPluginSignature
```

Example:

```
"C:\Program Files\Tableau\Tableau <version>\bin\tableau.exe" -DConnectPluginsPath=C:\Users\user\git\tableau-connector\src -DDisableVerifyConnectorPluginSignature
```

## With Tableau Server

* Clone the Tableau-Connector repository:

```
git clone https://github.com/exasol/tableau-connector.git
```

* Package the connector with the script we provide (requires python installed):

```
/bin/bash <path to connector directory>/tableau-connector/tableau-server-GUI-tests/set_up_scripts/package_connector.sh
```

* Copy the `exasol_odbc.taco` file to the following path inside Tableau Server:

```
/var/opt/tableau/tableau_server/data/tabsvc/vizqlserver/Connectors/exasol_odbc.taco"
```

* Disable the sign verification on the Tableau Server and apply changes:

```
tsm configuration set -k native_api.disable_verify_connector_plugin_signature -v true --force-keys
tsm pending-changes apply --ignore-prompt
```