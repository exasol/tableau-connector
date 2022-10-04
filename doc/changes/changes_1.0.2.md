# Tableau Connector 1.0.2, released 2022-10-04

Code name: Fix Kerberos authentication

## Summary

In this release we updated the connector to use TableauServerUser only when integrated authentication is active.

## Bugfixes

* #59: Updated connector to use TableauServerUser only when integrated authentication is active

## Dependency Updates

### JDBC Kerberos Setup Tests

#### Plugin Dependency Updates

* Updated `com.exasol:project-keeper-maven-plugin:2.7.0` to `2.8.0`

### Tableau Server GUI Tests

#### Plugin Dependency Updates

* Updated `com.exasol:project-keeper-maven-plugin:2.7.0` to `2.8.0`

### Exasol Connector for Tableau

#### Plugin Dependency Updates

* Updated `com.exasol:project-keeper-maven-plugin:2.7.0` to `2.8.0`
