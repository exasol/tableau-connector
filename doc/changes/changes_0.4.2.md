# Exasol Tableau Connector 0.4.2, released 2022-01-25
 
Code name: Sign Connectors

## Summary

Starting with version 0.4.2 we now sign the JDBC and ODBC connectors with the official Exasol certificate. This will allow you to omit the `-DDisableVerifyConnectorPluginSignature` argument for Tableau Desktop and set `native_api.disable_verify_connector_plugin_signature` to `false` for Tableau Server.

## Features

* #39: Signed connectors
