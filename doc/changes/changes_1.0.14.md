# Tableau Connector 1.0.14, released 2026-??-??

Code name: Fixed vulnerability CVE-2026-118042 in com.google.guava:guava:jar:33.7.2-jre:test

## Summary

This release fixes the following vulnerability:

### CVE-2026-118042 (CWE-407) in dependency `com.google.guava:guava:jar:33.7.2-jre:test`
Google Guava - Hash flooding vulnerability in HashBiMap and LinkedHashMultimap
#### References
* https://guide.sonatype.com/vulnerability/CVE-2026-118042?component-type=maven&component-name=com.google.guava%2Fguava&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2026-118042
* https://github.com/google/guava/security/advisories/GHSA-v5x9-67m3-7m56

## Security

* #149: Fixed vulnerability CVE-2026-118042 in dependency `com.google.guava:guava:jar:33.7.2-jre:test`

## Dependency Updates

### JDBC Kerberos Setup Tests

#### Plugin Dependency Updates

* Updated `com.exasol:project-keeper-maven-plugin:5.8.0` to `5.8.2`

### Tableau Server GUI Tests

#### Test Dependency Updates

* Updated `com.exasol:test-db-builder-java:4.0.3` to `5.0.0`
* Updated `com.fasterxml.jackson.core:jackson-databind:2.22.4` to `2.23.0`
* Updated `org.seleniumhq.selenium:selenium-java:4.51.0` to `4.52.0`

#### Plugin Dependency Updates

* Updated `com.exasol:project-keeper-maven-plugin:5.8.0` to `5.8.2`

### Exasol Connector for Tableau

#### Plugin Dependency Updates

* Updated `com.exasol:project-keeper-maven-plugin:5.8.0` to `5.8.2`

### Javascript-test

#### Development Dependency Updates

* Updated `fast-xml-parser:^5.3.3` to `^5.11.2`
* Updated `jest:^30.4.2` to `^30.5.2`
