# Tableau Connector 1.0.4, released 2023-??-??

Code name:

## Summary

This release fixes CVE-2022-45688 in test dependency `org.json:json`.

## Features

## Refactoring

* #66: Configured JavaScript test module for Project Keeper

## Security

* #69: Fixed vulnerability in test dependency

## Dependency Updates

### JDBC Kerberos Setup Tests

#### Runtime Dependency Updates

* Updated `com.exasol:exasol-jdbc:7.1.16` to `7.1.20`

#### Test Dependency Updates

* Updated `org.junit.jupiter:junit-jupiter:5.9.1` to `5.10.1`

#### Plugin Dependency Updates

* Updated `com.exasol:error-code-crawler-maven-plugin:1.2.2` to `1.3.1`
* Updated `com.exasol:project-keeper-maven-plugin:2.9.3` to `2.9.17`
* Updated `org.apache.maven.plugins:maven-compiler-plugin:3.10.1` to `3.11.0`
* Updated `org.apache.maven.plugins:maven-enforcer-plugin:3.1.0` to `3.4.1`
* Updated `org.apache.maven.plugins:maven-surefire-plugin:3.0.0-M8` to `3.2.2`
* Added `org.basepom.maven:duplicate-finder-maven-plugin:2.0.1`
* Updated `org.codehaus.mojo:flatten-maven-plugin:1.3.0` to `1.5.0`
* Updated `org.codehaus.mojo:versions-maven-plugin:2.14.2` to `2.16.2`
* Updated `org.jacoco:jacoco-maven-plugin:0.8.8` to `0.8.11`
* Updated `org.sonarsource.scanner.maven:sonar-maven-plugin:3.9.1.2184` to `3.10.0.2594`

### Tableau Server GUI Tests

#### Test Dependency Updates

* Updated `com.exasol:exasol-testcontainers:6.3.1` to `7.0.0`
* Updated `com.exasol:test-db-builder-java:3.4.1` to `3.5.3`
* Added `com.fasterxml.jackson.core:jackson-databind:2.16.0`
* Added `commons-io:commons-io:2.15.1`
* Updated `io.github.bonigarcia:webdrivermanager:5.3.1` to `5.6.2`
* Added `org.bouncycastle:bcprov-jdk15on:1.70`
* Updated `org.json:json:20220924` to `20231013`
* Updated `org.junit.jupiter:junit-jupiter:5.9.1` to `5.10.1`
* Updated `org.mockito:mockito-junit-jupiter:4.9.0` to `5.8.0`
* Updated `org.seleniumhq.selenium:selenium-java:4.7.0` to `4.16.0`
* Updated `org.testcontainers:junit-jupiter:1.17.6` to `1.19.3`

#### Plugin Dependency Updates

* Updated `com.exasol:error-code-crawler-maven-plugin:1.2.2` to `1.3.1`
* Updated `com.exasol:project-keeper-maven-plugin:2.9.3` to `2.9.17`
* Updated `org.apache.maven.plugins:maven-compiler-plugin:3.10.1` to `3.11.0`
* Updated `org.apache.maven.plugins:maven-enforcer-plugin:3.1.0` to `3.4.1`
* Updated `org.apache.maven.plugins:maven-surefire-plugin:3.0.0-M8` to `3.2.2`
* Added `org.basepom.maven:duplicate-finder-maven-plugin:2.0.1`
* Updated `org.codehaus.mojo:exec-maven-plugin:3.0.0` to `3.1.0`
* Updated `org.codehaus.mojo:flatten-maven-plugin:1.3.0` to `1.5.0`
* Updated `org.codehaus.mojo:versions-maven-plugin:2.14.2` to `2.16.2`
* Updated `org.jacoco:jacoco-maven-plugin:0.8.8` to `0.8.11`
* Updated `org.sonarsource.scanner.maven:sonar-maven-plugin:3.9.1.2184` to `3.10.0.2594`

### Exasol Connector for Tableau

#### Plugin Dependency Updates

* Updated `com.exasol:error-code-crawler-maven-plugin:1.2.2` to `1.3.1`
* Updated `com.exasol:project-keeper-maven-plugin:2.9.3` to `2.9.17`
* Updated `org.apache.maven.plugins:maven-compiler-plugin:3.10.1` to `3.11.0`
* Updated `org.apache.maven.plugins:maven-enforcer-plugin:3.1.0` to `3.4.1`
* Updated `org.apache.maven.plugins:maven-surefire-plugin:3.0.0-M8` to `3.2.2`
* Added `org.basepom.maven:duplicate-finder-maven-plugin:2.0.1`
* Updated `org.codehaus.mojo:flatten-maven-plugin:1.3.0` to `1.5.0`
* Updated `org.codehaus.mojo:versions-maven-plugin:2.14.2` to `2.16.2`
* Updated `org.jacoco:jacoco-maven-plugin:0.8.8` to `0.8.11`
* Updated `org.sonarsource.scanner.maven:sonar-maven-plugin:3.9.1.2184` to `3.10.0.2594`

### Javascript-test

#### Development Dependency Updates

* Updated `jest:^29.5.0` to `^29.7.0`
