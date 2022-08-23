# Exasol Tableau Connector 1.0.0, released 2022-08-23
 
Code name: Prepare for Tableau Extension Gallery

## Summary

In this release Exasol developers prepared the JDBC connector for submitting it to the Tableau Extension Gallery. Exasol developers also extended the Troubleshooting Guide.

**Important:** Version 1.0.0 requires Tableau version 2022.1 or later because the connector now uses the new `connectionHelper.GetProductName()` and `connectionHelper.GetProductVersion()` API to report the correct client information to the Exasol database.

**Important:** The certificate used to sign this version is valid until 2023-09-11. In [#55](https://github.com/exasol/tableau-connector/issues/55) we will update the certificate.

## Refactoring

* #53: Prepared for Extension Gallery

## Documentation

* Extended the troubleshooting guide for Tableau Server and improved error handling in JDBC Kerberos integration tests

## Dependency Updates

### Test Dependencies

* Upgraded JavaScript test library `jest ^27.4.7` to `^27.5.1` to fix [CVE-2021-44906](https://github.com/advisories/GHSA-xvch-5gv4-984h) in `minimist`
