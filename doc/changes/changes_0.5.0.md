# Exasol Tableau Connector 0.5.0, released 2022-02-02
 
Code name: TLS certificate fingerprint support for ODBC connector

## Summary

This release allows the user to enter a TLS certificate fingerprint for an ODBC connection. Entering a fingerprint is required when the Exasol database uses a self-signed TLS certificate.

## Features

* #43: Added TLS certificate fingerprint support for ODBC connector

## Tests

* #42: Added unit tests for ODBC Connector
