# Exasol Tableau Connector 0.2.0, released 2021-10-??
 
Code name: JDBC Connector

## Summary

Version 0.2.0 of the Tableau Connector adds a JDBC connector that offers an option for certificate validation and allows entering a certificate fingerprint.

The new release also fixes an issue with the ODBC connector that caused all strings being trimmed by removing capabilities `CAP_ODBC_TRIM_NO_EMPTY_STRING` and `CAP_ODBC_TRIM_VARCHAR_PADDING`. We also fixed some failing TDVT tests for the ODBC connector and improved the documentation for developers.

## Features

* #22: Add JDBC connector
