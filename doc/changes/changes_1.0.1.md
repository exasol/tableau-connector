# Tableau Connector 1.0.1, released 2022-09-14

Code name: New certificate

## Summary

Starting with this release the connector is now signed with a certificate that is valid until 2023-09-11. The timestamp service certificate is valid until 2033-08-11.

## Features

* #55: Sign connectors with new certificate

## Dependency Updates

### JDBC Kerberos Setup Tests

#### Plugin Dependency Updates

* Updated `com.exasol:project-keeper-maven-plugin:2.6.2` to `2.7.0`
* Removed `org.apache.maven.plugins:maven-failsafe-plugin:3.0.0-M6`

### Tableau Server GUI Tests

#### Plugin Dependency Updates

* Updated `com.exasol:project-keeper-maven-plugin:2.6.2` to `2.7.0`
* Removed `org.apache.maven.plugins:maven-failsafe-plugin:3.0.0-M6`

### Exasol Connector for Tableau

#### Plugin Dependency Updates

* Updated `com.exasol:project-keeper-maven-plugin:2.6.2` to `2.7.0`
* Updated `org.apache.maven.plugins:maven-compiler-plugin:3.8.1` to `3.10.1`
* Updated `org.apache.maven.plugins:maven-enforcer-plugin:3.0.0` to `3.1.0`
* Removed `org.apache.maven.plugins:maven-failsafe-plugin:3.0.0-M6`
* Updated `org.apache.maven.plugins:maven-surefire-plugin:3.0.0-M6` to `3.0.0-M5`
* Updated `org.codehaus.mojo:versions-maven-plugin:2.8.1` to `2.10.0`
