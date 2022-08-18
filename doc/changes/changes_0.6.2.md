# Exasol Tableau Connector 0.6.1, released 2022-08-18
 
Code name: Prepare for Extension Gallery

## Summary

In this release we prepared the JDBC connector for submitting it to the Tableau Extension Gallery. We also extended the Troubleshooting Guide.

## Refactoring

* #53: Prepared for Extension Gallery

## Documentation

* Extended the troubleshooting guide for Tableau Server and improved error handling in JDBC Kerberos integration tests

## Dependency Updates

### Test Dependencies

* Upgraded JavaScript test library `jest ^27.4.7` to `^27.5.1` to fix [CVE-2021-44906](https://github.com/advisories/GHSA-xvch-5gv4-984h) in `minimist`
