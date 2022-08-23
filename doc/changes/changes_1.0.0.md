# Exasol Tableau Connector 1.0.0, released 2022-08-23
 
Code name: Prepare for Tableau Extension Gallery

## Summary

In this release Exasol developers prepared the JDBC connector for submitting it to the Tableau Extension Gallery. Exasol developers also extended the Troubleshooting Guide.

**Important:** Version 1.0.0 requires Tableau version 2022.1 or later because the connector now uses the new `connectionHelper.GetProductName()` and `connectionHelper.GetProductVersion()` API to report the correct client information to the Exasol database.

**Important:** The certificate used to sign this version is valid until 2023-09-11. In [#55](https://github.com/exasol/tableau-connector/issues/55) we will update the certificate.

## Features

* #56: Sign connectors during CI build

## Refactoring

* #53: Prepared for Extension Gallery

## Documentation

* Extended the troubleshooting guide for Tableau Server and improved error handling in JDBC Kerberos integration tests

## Dependency Updates

### JDBC Kerberos Setup Tests

#### Runtime Dependency Updates

* Updated `com.exasol:exasol-jdbc:7.1.2` to `7.1.11`

#### Test Dependency Updates

* Updated `org.junit.jupiter:junit-jupiter:5.8.1` to `5.9.0`

#### Plugin Dependency Updates

* Added `com.exasol:error-code-crawler-maven-plugin:1.1.2`
* Added `com.exasol:project-keeper-maven-plugin:2.6.2`
* Added `io.github.zlika:reproducible-build-maven-plugin:0.15`
* Updated `org.apache.maven.plugins:maven-compiler-plugin:3.8.1` to `3.10.1`
* Updated `org.apache.maven.plugins:maven-enforcer-plugin:3.0.0` to `3.1.0`
* Updated `org.apache.maven.plugins:maven-failsafe-plugin:3.0.0-M5` to `3.0.0-M6`
* Added `org.codehaus.mojo:flatten-maven-plugin:1.2.7`
* Updated `org.codehaus.mojo:versions-maven-plugin:2.8.1` to `2.10.0`
* Added `org.jacoco:jacoco-maven-plugin:0.8.8`
* Added `org.sonarsource.scanner.maven:sonar-maven-plugin:3.9.1.2184`
* Added `org.sonatype.ossindex.maven:ossindex-maven-plugin:3.2.0`

### Tableau Server GUI Tests

#### Test Dependency Updates

* Updated `com.exasol:exasol-testcontainers:5.1.1` to `6.2.0`
* Updated `com.exasol:test-db-builder-java:3.2.1` to `3.3.4`
* Updated `io.github.bonigarcia:webdrivermanager:5.0.3` to `5.3.0`
* Updated `org.json:json:20210307` to `20220320`
* Updated `org.junit.jupiter:junit-jupiter:5.8.1` to `5.9.0`
* Updated `org.mockito:mockito-junit-jupiter:4.0.0` to `4.7.0`
* Updated `org.seleniumhq.selenium:selenium-java:4.0.0` to `4.4.0`
* Updated `org.testcontainers:junit-jupiter:1.16.2` to `1.17.3`

#### Plugin Dependency Updates

* Added `com.exasol:error-code-crawler-maven-plugin:1.1.2`
* Added `com.exasol:project-keeper-maven-plugin:2.6.2`
* Added `io.github.zlika:reproducible-build-maven-plugin:0.15`
* Updated `org.apache.maven.plugins:maven-compiler-plugin:3.8.1` to `3.10.1`
* Updated `org.apache.maven.plugins:maven-enforcer-plugin:3.0.0` to `3.1.0`
* Updated `org.apache.maven.plugins:maven-failsafe-plugin:3.0.0-M5` to `3.0.0-M6`
* Added `org.codehaus.mojo:flatten-maven-plugin:1.2.7`
* Updated `org.codehaus.mojo:versions-maven-plugin:2.8.1` to `2.10.0`
* Added `org.jacoco:jacoco-maven-plugin:0.8.8`
* Added `org.sonarsource.scanner.maven:sonar-maven-plugin:3.9.1.2184`
* Added `org.sonatype.ossindex.maven:ossindex-maven-plugin:3.2.0`

### Exasol Connector for Tableau

#### Plugin Dependency Updates

* Added `com.exasol:error-code-crawler-maven-plugin:1.1.2`
* Added `com.exasol:project-keeper-maven-plugin:2.6.2`
* Added `io.github.zlika:reproducible-build-maven-plugin:0.15`
* Updated `org.apache.maven.plugins:maven-failsafe-plugin:3.0.0-M5` to `3.0.0-M6`
* Updated `org.apache.maven.plugins:maven-surefire-plugin:3.0.0-M5` to `3.0.0-M6`
* Added `org.codehaus.mojo:flatten-maven-plugin:1.2.7`
* Added `org.jacoco:jacoco-maven-plugin:0.8.8`
* Added `org.sonarsource.scanner.maven:sonar-maven-plugin:3.9.1.2184`
* Added `org.sonatype.ossindex.maven:ossindex-maven-plugin:3.2.0`
