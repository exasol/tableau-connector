# Tableau Connector 1.0.13, released 2026-??-??

Code name: Fixed vulnerability CVE-2026-102554 in com.google.guava:guava:jar:33.6.0-jre:test

## Summary

This release fixes the following vulnerability:

### CVE-2026-102554 (CWE-502) in dependency `com.google.guava:guava:jar:33.6.0-jre:test`
Allocation of resources without limits or throttling (CWE-770) during Java object deserialization in Google Guava versions 4.0 through 33.7.1 allows an attacker to cause a Denial of Service via OutOfMemoryError. When deserializing CompactHashMap, CompactHashSet, or MapMakerInternalMap instances, Guava eagerly allocates an array based on a caller-specified size parameter without throttling, permitting memory exhaustion from crafted serialization streams.
#### References
* https://guide.sonatype.com/vulnerability/CVE-2026-102554?component-type=maven&component-name=com.google.guava%2Fguava&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2026-102554
* https://github.com/google/guava/security/advisories/GHSA-xxph-c9ww-hj94

## Security

* #144: Fixed vulnerability CVE-2026-102554 in dependency `com.google.guava:guava:jar:33.6.0-jre:test`

## Dependency Updates

### JDBC Kerberos Setup Tests

#### Runtime Dependency Updates

* Updated `com.exasol:exasol-jdbc:26.2.8` to `26.2.10`

#### Test Dependency Updates

* Updated `org.junit.jupiter:junit-jupiter-api:6.1.2` to `6.1.3`

#### Plugin Dependency Updates

* Updated `com.exasol:project-keeper-maven-plugin:5.7.4` to `5.7.7`

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

* Updated `com.exasol:project-keeper-maven-plugin:5.7.4` to `5.7.7`

### Exasol Connector for Tableau

#### Plugin Dependency Updates

* Updated `com.exasol:project-keeper-maven-plugin:5.7.4` to `5.7.7`

### Javascript-test

#### Development Dependency Updates

* Added `fast-xml-parser:^5.3.3`
* Removed `xml2json:^0.7.1`
