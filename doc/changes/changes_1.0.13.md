# Tableau Connector 1.0.13, released 2026-10-21

Code name: Publish only signed connector artifacts

## Summary

This release ensures that GitHub releases expose only signed, versioned Tableau connector artifacts, preventing users from downloading connector packages that Tableau rejects because of invalid signatures (#138).

## Bugfixes

* #138: Remove unsigned connectors from GitHub release

## Security

* #120: Fixed vulnerability CVE-2026-40542 in dependency `org.apache.httpcomponents.client5:httpclient5:jar:5.6:test`
* #116: Fixed vulnerability CVE-2026-64607 in dependency `org.apache.httpcomponents.client5:httpclient5:jar:5.6:test`
* #121: Fixed vulnerability CVE-2026-71290 in dependency `org.apache.httpcomponents.client5:httpclient5:jar:5.6:test`
* #129: Fixed vulnerability CVE-2026-17508 in dependency `org.bouncycastle:bcprov-jdk18on:jar:1.85:test`
* #130: Fixed vulnerability CVE-2026-18036 in dependency `org.bouncycastle:bcprov-jdk18on:jar:1.85:test`
* #131: Fixed vulnerability CVE-2026-18040 in dependency `org.bouncycastle:bcprov-jdk18on:jar:1.85:test`
* #132: Fixed vulnerability CVE-2026-71889 in dependency `org.bouncycastle:bcprov-jdk18on:jar:1.85:test`
* #133: Fixed vulnerability CVE-2026-71891 in dependency `org.bouncycastle:bcprov-jdk18on:jar:1.85:test`
* #142: Fixed vulnerability CVE-2026-97873 in dependency `org.bouncycastle:bcprov-jdk18on:jar:1.85:test`
* #125: Fixed vulnerability CVE-2026-68498 in dependency `com.fasterxml.jackson.core:jackson-core:jar:2.22.1:test`
* #139: Fixed vulnerability CVE-2026-89407 in dependency `com.fasterxml.jackson.core:jackson-core:jar:2.22.1:test`
* #140: Fixed vulnerability CVE-2026-89425 in dependency `com.fasterxml.jackson.core:jackson-core:jar:2.22.1:test`
* #123: Fixed vulnerability CVE-2026-86231 in dependency `com.github.mwiede:jsch:jar:2.28.5:test`
* #130: Fixed vulnerability CVE-2026-18036 in dependency `org.bouncycastle:bcpkix-jdk18on:jar:1.85:test`
* #126: Fixed vulnerability CVE-2026-71888 in dependency `org.bouncycastle:bcpkix-jdk18on:jar:1.85:test`
* #127: Fixed vulnerability CVE-2026-71889 in dependency `org.bouncycastle:bcpkix-jdk18on:jar:1.85:test`
* #128: Fixed vulnerability CVE-2026-71892 in dependency `org.bouncycastle:bcpkix-jdk18on:jar:1.85:test`
* #144: Fixed vulnerability CVE-2026-102554 in dependency `com.google.guava:guava:jar:33.6.0-jre:test`
* Fixed vulnerability CVE-2026-110879 in dependency `com.fasterxml.jackson.core:jackson-databind:jar:2.22.1:test`
* #117: Fixed vulnerability CVE-2026-19032 in dependency `com.fasterxml.jackson.core:jackson-databind:jar:2.22.1:test`
* #118: Fixed vulnerability CVE-2026-68497 in dependency `com.fasterxml.jackson.core:jackson-databind:jar:2.22.1:test`
* #134: Fixed vulnerability CVE-2026-91776 in dependency `com.fasterxml.jackson.core:jackson-databind:jar:2.22.1:test`
* #135: Fixed vulnerability CVE-2026-91777 in dependency `com.fasterxml.jackson.core:jackson-databind:jar:2.22.1:test`
* #136: Fixed vulnerability CVE-2026-83557 in dependency `com.fasterxml.jackson.core:jackson-databind:jar:2.22.1:test`

## Features

* ISSUE_NUMBER: description

## Dependency Updates

### JDBC Kerberos Setup Tests

#### Runtime Dependency Updates

* Updated `com.exasol:exasol-jdbc:26.2.8` to `26.2.10`

#### Test Dependency Updates

* Updated `org.junit.jupiter:junit-jupiter-api:6.1.2` to `6.1.3`

#### Plugin Dependency Updates

* Updated `com.exasol:error-code-crawler-maven-plugin:2.1.0` to `2.1.2`
* Updated `com.exasol:project-keeper-maven-plugin:5.7.4` to `5.8.0`
* Updated `io.github.git-commit-id:git-commit-id-maven-plugin:10.0.0` to `10.0.1`
* Updated `org.apache.maven.plugins:maven-artifact-plugin:3.6.1` to `3.7.0`
* Updated `org.apache.maven.plugins:maven-compiler-plugin:3.15.0` to `3.16.0`
* Updated `org.apache.maven.plugins:maven-install-plugin:3.1.4` to `3.2.0`
* Updated `org.apache.maven.plugins:maven-surefire-plugin:3.5.6` to `3.6.0`
* Updated `org.apache.maven.plugins:maven-toolchains-plugin:3.2.0` to `3.3.0`
* Updated `org.codehaus.mojo:flatten-maven-plugin:1.7.3` to `1.8.0`
* Updated `org.codehaus.mojo:versions-maven-plugin:2.21.0` to `2.23.0`
* Updated `org.sonarsource.scanner.maven:sonar-maven-plugin:5.7.0.6970` to `5.8.0.7211`

### Tableau Server GUI Tests

#### Test Dependency Updates

* Updated `com.exasol:exasol-testcontainers:8.0.1` to `8.0.2`
* Updated `com.exasol:test-db-builder-java:4.0.2` to `4.0.3`
* Updated `com.fasterxml.jackson.core:jackson-databind:2.22.1` to `2.22.4`
* Updated `io.github.bonigarcia:webdrivermanager:6.3.4` to `6.4.0`
* Updated `org.apache.httpcomponents.core5:httpcore5-h2:5.4.3` to `5.4.4`
* Updated `org.apache.httpcomponents.core5:httpcore5:5.4.3` to `5.4.4`
* Updated `org.bouncycastle:bcpkix-jdk18on:1.85` to `1.86`
* Updated `org.json:json:20260719` to `20260814`
* Updated `org.junit.jupiter:junit-jupiter-api:6.1.2` to `6.1.3`
* Updated `org.mockito:mockito-junit-jupiter:5.23.0` to `5.24.0`
* Updated `org.seleniumhq.selenium:selenium-java:4.46.0` to `4.51.0`

#### Plugin Dependency Updates

* Updated `com.exasol:error-code-crawler-maven-plugin:2.1.0` to `2.1.2`
* Updated `com.exasol:project-keeper-maven-plugin:5.7.4` to `5.8.0`
* Updated `io.github.git-commit-id:git-commit-id-maven-plugin:10.0.0` to `10.0.1`
* Updated `org.apache.maven.plugins:maven-artifact-plugin:3.6.1` to `3.7.0`
* Updated `org.apache.maven.plugins:maven-compiler-plugin:3.15.0` to `3.16.0`
* Updated `org.apache.maven.plugins:maven-install-plugin:3.1.4` to `3.2.0`
* Updated `org.apache.maven.plugins:maven-surefire-plugin:3.5.6` to `3.6.0`
* Updated `org.apache.maven.plugins:maven-toolchains-plugin:3.2.0` to `3.3.0`
* Updated `org.codehaus.mojo:flatten-maven-plugin:1.7.3` to `1.8.0`
* Updated `org.codehaus.mojo:versions-maven-plugin:2.21.0` to `2.23.0`
* Updated `org.sonarsource.scanner.maven:sonar-maven-plugin:5.7.0.6970` to `5.8.0.7211`

### Exasol Connector for Tableau

#### Plugin Dependency Updates

* Updated `com.exasol:error-code-crawler-maven-plugin:2.1.0` to `2.1.2`
* Updated `com.exasol:project-keeper-maven-plugin:5.7.4` to `5.8.0`
* Updated `io.github.git-commit-id:git-commit-id-maven-plugin:10.0.0` to `10.0.1`
* Updated `org.apache.maven.plugins:maven-artifact-plugin:3.6.1` to `3.7.0`
* Updated `org.apache.maven.plugins:maven-compiler-plugin:3.15.0` to `3.16.0`
* Updated `org.apache.maven.plugins:maven-install-plugin:3.1.4` to `3.2.0`
* Updated `org.apache.maven.plugins:maven-surefire-plugin:3.5.6` to `3.6.0`
* Updated `org.apache.maven.plugins:maven-toolchains-plugin:3.2.0` to `3.3.0`
* Updated `org.codehaus.mojo:flatten-maven-plugin:1.7.3` to `1.8.0`
* Updated `org.codehaus.mojo:versions-maven-plugin:2.21.0` to `2.23.0`
* Updated `org.sonarsource.scanner.maven:sonar-maven-plugin:5.7.0.6970` to `5.8.0.7211`

### Javascript-test

#### Development Dependency Updates

* Updated `fast-xml-parser:^5.3.3` to `^5.11.2`
* Updated `jest:^30.4.2` to `^30.5.2`
