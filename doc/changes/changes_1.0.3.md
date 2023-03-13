# Tableau Connector 1.0.3, released 2022-12-??

Code name: Update Exasol JDBC driver Maven repo

## Summary

The integration tests download the Exasol JDBC driver from the deprecated Exasol Maven repository. Instead the JDBC driver is now available on Maven Central. We updated the build scripts to use the new location.

## Features

* #62: Updated Maven repository for Exasol JDBC driver

## Documentation

* #64: Added note about Tableau Exchange
* #61: Added note to use JDBC driver 7.1.16 or later

## Dependency Updates

### JDBC Kerberos Setup Tests

#### Runtime Dependency Updates

* Updated `com.exasol:exasol-jdbc:7.1.11` to `7.1.16`

#### Test Dependency Updates

* Updated `org.junit.jupiter:junit-jupiter:5.9.0` to `5.9.1`

#### Plugin Dependency Updates

* Updated `com.exasol:error-code-crawler-maven-plugin:1.1.2` to `1.2.2`
* Updated `com.exasol:project-keeper-maven-plugin:2.8.0` to `2.9.3`
* Updated `io.github.zlika:reproducible-build-maven-plugin:0.15` to `0.16`
* Updated `org.apache.maven.plugins:maven-surefire-plugin:3.0.0-M5` to `3.0.0-M8`
* Updated `org.codehaus.mojo:flatten-maven-plugin:1.2.7` to `1.3.0`
* Updated `org.codehaus.mojo:versions-maven-plugin:2.10.0` to `2.14.2`

### Tableau Server GUI Tests

#### Test Dependency Updates

* Updated `com.exasol:exasol-testcontainers:6.2.0` to `6.3.1`
* Updated `com.exasol:test-db-builder-java:3.3.4` to `3.4.1`
* Updated `io.github.bonigarcia:webdrivermanager:5.3.0` to `5.3.1`
* Updated `org.json:json:20220320` to `20220924`
* Updated `org.junit.jupiter:junit-jupiter:5.9.0` to `5.9.1`
* Updated `org.mockito:mockito-junit-jupiter:4.7.0` to `4.9.0`
* Updated `org.seleniumhq.selenium:selenium-java:4.4.0` to `4.7.0`
* Updated `org.testcontainers:junit-jupiter:1.17.3` to `1.17.6`

#### Plugin Dependency Updates

* Updated `com.exasol:error-code-crawler-maven-plugin:1.1.2` to `1.2.2`
* Updated `com.exasol:project-keeper-maven-plugin:2.8.0` to `2.9.3`
* Updated `io.github.zlika:reproducible-build-maven-plugin:0.15` to `0.16`
* Updated `org.apache.maven.plugins:maven-surefire-plugin:3.0.0-M5` to `3.0.0-M8`
* Updated `org.codehaus.mojo:flatten-maven-plugin:1.2.7` to `1.3.0`
* Updated `org.codehaus.mojo:versions-maven-plugin:2.10.0` to `2.14.2`

### Exasol Connector for Tableau

#### Plugin Dependency Updates

* Updated `com.exasol:error-code-crawler-maven-plugin:1.1.2` to `1.2.2`
* Updated `com.exasol:project-keeper-maven-plugin:2.8.0` to `2.9.3`
* Updated `io.github.zlika:reproducible-build-maven-plugin:0.15` to `0.16`
* Updated `org.apache.maven.plugins:maven-surefire-plugin:3.0.0-M5` to `3.0.0-M8`
* Updated `org.codehaus.mojo:flatten-maven-plugin:1.2.7` to `1.3.0`
* Updated `org.codehaus.mojo:versions-maven-plugin:2.10.0` to `2.14.2`
