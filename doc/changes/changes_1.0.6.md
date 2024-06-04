# Tableau Connector 1.0.6, released 2024-??-??

Code name: Upgrade dependencies

## Summary

This release upgrades dependencies.

## Features

* #77: Test with Exasol's `TIMESTAMP(9)` data type

## Dependency Updates

### JDBC Kerberos Setup Tests

#### Runtime Dependency Updates

* Updated `com.exasol:exasol-jdbc:7.1.20` to `24.1.0`

#### Test Dependency Updates

* Updated `org.junit.jupiter:junit-jupiter:5.10.1` to `5.10.2`

#### Plugin Dependency Updates

* Updated `com.exasol:error-code-crawler-maven-plugin:1.3.1` to `2.0.3`
* Updated `com.exasol:project-keeper-maven-plugin:3.0.0` to `4.3.2`
* Updated `org.apache.maven.plugins:maven-compiler-plugin:3.11.0` to `3.13.0`
* Updated `org.apache.maven.plugins:maven-enforcer-plugin:3.4.1` to `3.5.0`
* Updated `org.apache.maven.plugins:maven-surefire-plugin:3.2.3` to `3.2.5`
* Updated `org.apache.maven.plugins:maven-toolchains-plugin:3.1.0` to `3.2.0`
* Updated `org.codehaus.mojo:flatten-maven-plugin:1.5.0` to `1.6.0`
* Updated `org.jacoco:jacoco-maven-plugin:0.8.11` to `0.8.12`
* Updated `org.sonarsource.scanner.maven:sonar-maven-plugin:3.10.0.2594` to `4.0.0.4121`

### Tableau Server GUI Tests

#### Test Dependency Updates

* Updated `com.exasol:exasol-testcontainers:7.0.0` to `7.1.0`
* Updated `com.exasol:test-db-builder-java:3.5.3` to `3.5.4`
* Updated `com.fasterxml.jackson.core:jackson-databind:2.16.1` to `2.17.1`
* Updated `commons-io:commons-io:2.15.1` to `2.16.1`
* Updated `io.github.bonigarcia:webdrivermanager:5.6.3` to `5.8.0`
* Added `org.bouncycastle:bcpkix-jdk18on:1.78.1`
* Removed `org.bouncycastle:bcprov-jdk15on:1.70`
* Updated `org.json:json:20231013` to `20240303`
* Updated `org.junit.jupiter:junit-jupiter:5.10.1` to `5.10.2`
* Updated `org.mockito:mockito-junit-jupiter:5.9.0` to `5.12.0`
* Updated `org.seleniumhq.selenium:selenium-java:4.16.1` to `4.21.0`
* Updated `org.testcontainers:junit-jupiter:1.19.3` to `1.19.8`

#### Plugin Dependency Updates

* Updated `com.exasol:error-code-crawler-maven-plugin:1.3.1` to `2.0.3`
* Updated `com.exasol:project-keeper-maven-plugin:3.0.0` to `4.3.2`
* Updated `org.apache.maven.plugins:maven-compiler-plugin:3.11.0` to `3.13.0`
* Updated `org.apache.maven.plugins:maven-enforcer-plugin:3.4.1` to `3.5.0`
* Updated `org.apache.maven.plugins:maven-surefire-plugin:3.2.3` to `3.2.5`
* Updated `org.apache.maven.plugins:maven-toolchains-plugin:3.1.0` to `3.2.0`
* Updated `org.codehaus.mojo:flatten-maven-plugin:1.5.0` to `1.6.0`
* Updated `org.jacoco:jacoco-maven-plugin:0.8.11` to `0.8.12`
* Updated `org.sonarsource.scanner.maven:sonar-maven-plugin:3.10.0.2594` to `4.0.0.4121`

### Exasol Connector for Tableau

#### Plugin Dependency Updates

* Updated `com.exasol:error-code-crawler-maven-plugin:1.3.1` to `2.0.3`
* Updated `com.exasol:project-keeper-maven-plugin:3.0.0` to `4.3.2`
* Updated `org.apache.maven.plugins:maven-compiler-plugin:3.11.0` to `3.13.0`
* Updated `org.apache.maven.plugins:maven-enforcer-plugin:3.4.1` to `3.5.0`
* Updated `org.apache.maven.plugins:maven-surefire-plugin:3.2.3` to `3.2.5`
* Updated `org.apache.maven.plugins:maven-toolchains-plugin:3.1.0` to `3.2.0`
* Updated `org.codehaus.mojo:flatten-maven-plugin:1.5.0` to `1.6.0`
* Updated `org.jacoco:jacoco-maven-plugin:0.8.11` to `0.8.12`
* Updated `org.sonarsource.scanner.maven:sonar-maven-plugin:3.10.0.2594` to `4.0.0.4121`
