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

* Added `com.exasol:exasol-jdbc:7.1.11`

#### Test Dependency Updates

* Added `org.hamcrest:hamcrest:2.2`
* Added `org.junit.jupiter:junit-jupiter:5.9.0`

#### Plugin Dependency Updates

* Added `com.exasol:error-code-crawler-maven-plugin:1.1.2`
* Added `com.exasol:project-keeper-maven-plugin:2.6.2`
* Added `io.github.zlika:reproducible-build-maven-plugin:0.15`
* Added `org.apache.maven.plugins:maven-clean-plugin:2.5`
* Added `org.apache.maven.plugins:maven-compiler-plugin:3.10.1`
* Added `org.apache.maven.plugins:maven-deploy-plugin:2.7`
* Added `org.apache.maven.plugins:maven-enforcer-plugin:3.1.0`
* Added `org.apache.maven.plugins:maven-failsafe-plugin:3.0.0-M6`
* Added `org.apache.maven.plugins:maven-install-plugin:2.4`
* Added `org.apache.maven.plugins:maven-jar-plugin:2.4`
* Added `org.apache.maven.plugins:maven-resources-plugin:2.6`
* Added `org.apache.maven.plugins:maven-site-plugin:3.3`
* Added `org.apache.maven.plugins:maven-surefire-plugin:3.0.0-M5`
* Added `org.codehaus.mojo:flatten-maven-plugin:1.2.7`
* Added `org.codehaus.mojo:versions-maven-plugin:2.10.0`
* Added `org.jacoco:jacoco-maven-plugin:0.8.8`
* Added `org.sonarsource.scanner.maven:sonar-maven-plugin:3.9.1.2184`
* Added `org.sonatype.ossindex.maven:ossindex-maven-plugin:3.2.0`

### Tableau Server GUI Tests

#### Test Dependency Updates

* Added `com.exasol:exasol-testcontainers:6.2.0`
* Added `com.exasol:test-db-builder-java:3.3.4`
* Added `io.github.bonigarcia:webdrivermanager:5.3.0`
* Added `org.hamcrest:hamcrest:2.2`
* Added `org.json:json:20220320`
* Added `org.junit.jupiter:junit-jupiter:5.9.0`
* Added `org.mockito:mockito-junit-jupiter:4.7.0`
* Added `org.seleniumhq.selenium:selenium-java:4.4.0`
* Added `org.testcontainers:junit-jupiter:1.17.3`

#### Plugin Dependency Updates

* Added `com.exasol:error-code-crawler-maven-plugin:1.1.2`
* Added `com.exasol:project-keeper-maven-plugin:2.6.2`
* Added `io.github.zlika:reproducible-build-maven-plugin:0.15`
* Added `org.apache.maven.plugins:maven-clean-plugin:2.5`
* Added `org.apache.maven.plugins:maven-compiler-plugin:3.10.1`
* Added `org.apache.maven.plugins:maven-deploy-plugin:2.7`
* Added `org.apache.maven.plugins:maven-enforcer-plugin:3.1.0`
* Added `org.apache.maven.plugins:maven-failsafe-plugin:3.0.0-M6`
* Added `org.apache.maven.plugins:maven-install-plugin:2.4`
* Added `org.apache.maven.plugins:maven-jar-plugin:2.4`
* Added `org.apache.maven.plugins:maven-resources-plugin:2.6`
* Added `org.apache.maven.plugins:maven-site-plugin:3.3`
* Added `org.apache.maven.plugins:maven-surefire-plugin:3.0.0-M5`
* Added `org.codehaus.mojo:exec-maven-plugin:3.0.0`
* Added `org.codehaus.mojo:flatten-maven-plugin:1.2.7`
* Added `org.codehaus.mojo:versions-maven-plugin:2.10.0`
* Added `org.jacoco:jacoco-maven-plugin:0.8.8`
* Added `org.sonarsource.scanner.maven:sonar-maven-plugin:3.9.1.2184`
* Added `org.sonatype.ossindex.maven:ossindex-maven-plugin:3.2.0`

### Exasol Connector for Tableau

#### Plugin Dependency Updates

* Added `com.exasol:error-code-crawler-maven-plugin:1.1.2`
* Added `com.exasol:project-keeper-maven-plugin:2.6.2`
* Added `io.github.zlika:reproducible-build-maven-plugin:0.15`
* Added `org.apache.maven.plugins:maven-clean-plugin:2.5`
* Added `org.apache.maven.plugins:maven-compiler-plugin:3.8.1`
* Added `org.apache.maven.plugins:maven-deploy-plugin:2.7`
* Added `org.apache.maven.plugins:maven-enforcer-plugin:3.0.0`
* Added `org.apache.maven.plugins:maven-failsafe-plugin:3.0.0-M6`
* Added `org.apache.maven.plugins:maven-install-plugin:2.4`
* Added `org.apache.maven.plugins:maven-site-plugin:3.3`
* Added `org.apache.maven.plugins:maven-surefire-plugin:3.0.0-M6`
* Added `org.codehaus.mojo:flatten-maven-plugin:1.2.7`
* Added `org.codehaus.mojo:versions-maven-plugin:2.8.1`
* Added `org.jacoco:jacoco-maven-plugin:0.8.8`
* Added `org.sonarsource.scanner.maven:sonar-maven-plugin:3.9.1.2184`
* Added `org.sonatype.ossindex.maven:ossindex-maven-plugin:3.2.0`
