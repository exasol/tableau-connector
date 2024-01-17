# Tableau Connector 1.0.5, released 2024-01-17

Code name: Fix `DATEADD` function for `DATE` argument

## Summary

This release fixes the return type of the `DATEADD` when using a `DATE` as argument. Before, the function returned a `DATE` type when adding an hour, minute or second. This is fixed now and `DATEADD` always returns a `TIMESTAMP` when adding an hour, minute or second even when a `DATE` was used as argument.

## Bugfixes

* #74: Fixed return type of `DATEADD` function

## Dependency Updates

### JDBC Kerberos Setup Tests

#### Plugin Dependency Updates

* Updated `com.exasol:project-keeper-maven-plugin:2.9.17` to `3.0.0`
* Updated `org.apache.maven.plugins:maven-surefire-plugin:3.2.2` to `3.2.3`
* Added `org.apache.maven.plugins:maven-toolchains-plugin:3.1.0`

### Tableau Server GUI Tests

#### Test Dependency Updates

* Updated `com.fasterxml.jackson.core:jackson-databind:2.16.0` to `2.16.1`
* Updated `io.github.bonigarcia:webdrivermanager:5.6.2` to `5.6.3`
* Updated `org.mockito:mockito-junit-jupiter:5.8.0` to `5.9.0`
* Updated `org.seleniumhq.selenium:selenium-java:4.16.0` to `4.16.1`

#### Plugin Dependency Updates

* Updated `com.exasol:project-keeper-maven-plugin:2.9.17` to `3.0.0`
* Updated `org.apache.maven.plugins:maven-surefire-plugin:3.2.2` to `3.2.3`
* Added `org.apache.maven.plugins:maven-toolchains-plugin:3.1.0`

### Exasol Connector for Tableau

#### Plugin Dependency Updates

* Updated `com.exasol:project-keeper-maven-plugin:2.9.17` to `3.0.0`
* Updated `org.apache.maven.plugins:maven-surefire-plugin:3.2.2` to `3.2.3`
* Added `org.apache.maven.plugins:maven-toolchains-plugin:3.1.0`
