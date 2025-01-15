# Tableau Connector 1.0.7, released 2025-01-15

Code name: Fix CVE-2020-36604 in test dependency `hoek`

## Summary

This release fixes vulnerability CVE-2020-36604 in test dependency `hoek`.

## Security

* #93: Fixed CVE-2020-36604 in test dependency `hoek`

## Dependency Updates

### JDBC Kerberos Setup Tests

#### Runtime Dependency Updates

* Updated `com.exasol:exasol-jdbc:24.1.1` to `24.2.1`

#### Test Dependency Updates

* Updated `org.junit.jupiter:junit-jupiter:5.10.3` to `5.11.4`

#### Plugin Dependency Updates

* Updated `com.exasol:project-keeper-maven-plugin:4.3.3` to `4.5.0`
* Added `com.exasol:quality-summarizer-maven-plugin:0.2.0`
* Updated `io.github.zlika:reproducible-build-maven-plugin:0.16` to `0.17`
* Updated `org.apache.maven.plugins:maven-clean-plugin:2.5` to `3.4.0`
* Updated `org.apache.maven.plugins:maven-install-plugin:2.4` to `3.1.3`
* Updated `org.apache.maven.plugins:maven-resources-plugin:2.6` to `3.3.1`
* Updated `org.apache.maven.plugins:maven-site-plugin:3.3` to `3.21.0`
* Updated `org.apache.maven.plugins:maven-surefire-plugin:3.2.5` to `3.5.2`
* Updated `org.codehaus.mojo:versions-maven-plugin:2.16.2` to `2.18.0`
* Updated `org.sonarsource.scanner.maven:sonar-maven-plugin:4.0.0.4121` to `5.0.0.4389`

### Tableau Server GUI Tests

#### Test Dependency Updates

* Updated `com.exasol:exasol-testcontainers:7.1.1` to `7.1.2`
* Updated `com.exasol:test-db-builder-java:3.5.4` to `3.6.0`
* Updated `com.fasterxml.jackson.core:jackson-databind:2.17.2` to `2.18.2`
* Updated `commons-io:commons-io:2.16.1` to `2.18.0`
* Updated `org.bouncycastle:bcpkix-jdk18on:1.78.1` to `1.79`
* Updated `org.json:json:20240303` to `20250107`
* Updated `org.junit.jupiter:junit-jupiter:5.10.3` to `5.11.4`
* Updated `org.mockito:mockito-junit-jupiter:5.12.0` to `5.15.2`
* Updated `org.seleniumhq.selenium:selenium-java:4.23.0` to `4.27.0`
* Updated `org.testcontainers:junit-jupiter:1.20.1` to `1.20.4`

#### Plugin Dependency Updates

* Updated `com.exasol:project-keeper-maven-plugin:4.3.3` to `4.5.0`
* Added `com.exasol:quality-summarizer-maven-plugin:0.2.0`
* Updated `io.github.zlika:reproducible-build-maven-plugin:0.16` to `0.17`
* Updated `org.apache.maven.plugins:maven-clean-plugin:2.5` to `3.4.0`
* Updated `org.apache.maven.plugins:maven-install-plugin:2.4` to `3.1.3`
* Updated `org.apache.maven.plugins:maven-resources-plugin:2.6` to `3.3.1`
* Updated `org.apache.maven.plugins:maven-site-plugin:3.3` to `3.21.0`
* Updated `org.apache.maven.plugins:maven-surefire-plugin:3.2.5` to `3.5.2`
* Updated `org.codehaus.mojo:versions-maven-plugin:2.16.2` to `2.18.0`
* Updated `org.sonarsource.scanner.maven:sonar-maven-plugin:4.0.0.4121` to `5.0.0.4389`

### Exasol Connector for Tableau

#### Plugin Dependency Updates

* Updated `com.exasol:project-keeper-maven-plugin:4.3.3` to `4.5.0`
* Added `com.exasol:quality-summarizer-maven-plugin:0.2.0`
* Updated `io.github.zlika:reproducible-build-maven-plugin:0.16` to `0.17`
* Updated `org.apache.maven.plugins:maven-clean-plugin:2.5` to `3.4.0`
* Updated `org.apache.maven.plugins:maven-install-plugin:2.4` to `3.1.3`
* Added `org.apache.maven.plugins:maven-resources-plugin:3.3.1`
* Updated `org.apache.maven.plugins:maven-site-plugin:3.3` to `3.21.0`
* Updated `org.apache.maven.plugins:maven-surefire-plugin:3.2.5` to `3.5.2`
* Updated `org.codehaus.mojo:versions-maven-plugin:2.16.2` to `2.18.0`
* Updated `org.sonarsource.scanner.maven:sonar-maven-plugin:4.0.0.4121` to `5.0.0.4389`

### Javascript-test

#### Development Dependency Updates

* Updated `xml2json:^0.12.0` to `^0.7.1`
