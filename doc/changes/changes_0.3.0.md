# Exasol Tableau Connector 0.3.0, released 2021-11-09
 
Code name: Kerberos for JDBC Connector

## Summary

Version 0.3.0 of the Tableau Connector adds support for authenticating via Kerberos. This enables single-sign-on to the database without entering username and password. We also improved the user and developer guides in this release.

Please note that Kerberos delegation is not yet supported in this release. See [issue #32](https://github.com/exasol/tableau-connector/issues/32) for details.

## Features

* #15: Kerberos support

## Bug Fixes

* #30: Fixed REGEX functions not being visible in Tableau.
* #33: Fixed behavior of `REGEXP_MATCH()` function: it now returns true if only a substring matches the regular expression.
