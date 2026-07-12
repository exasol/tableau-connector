# Tableau Connector 1.0.12, released 2026-??-??

Code name: Fixed vulnerabilities CVE-2026-54428, CVE-2026-54515, CVE-2026-59889, CVE-2026-9563, CVE-2026-54399

## Summary

This release fixes the following 5 vulnerabilities:

### CVE-2026-54428 (CWE-400) in dependency `org.apache.httpcomponents.core5:httpcore5-h2:jar:5.4:test`
Allocation of resources without limits or throttling in the HTTP/2 HPACK decoder in Apache HttpComponents Core (5.4.2 and earlier, 5.5-beta1 and earlier) allows an remote attacker to cause a denial of service through memory exhaustion by sending oversized compressed header blocks before the HTTP/2 SETTINGS acknowledgement causes the configured header list size limit to be applied.
#### References
* https://guide.sonatype.com/vulnerability/CVE-2026-54428?component-type=maven&component-name=org.apache.httpcomponents.core5%2Fhttpcore5-h2&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2026-54428
* https://lists.apache.org/thread/5zjp8vczvxq19pw2rvhs21q446bhl0sd

### CVE-2026-54515 (CWE-915) in dependency `com.fasterxml.jackson.core:jackson-databind:jar:2.22.0:test`
jackson-databind contains the general-purpose data-binding functionality and tree-model for Jackson Data Processor. From 2.8.0 until 2.18.9, 2.21.5, and 3.1.4, in BeanDeserializerBase.createContextual(), per-property @JsonIgnoreProperties exclusions are applied by _handleByNameInclusion(), producing a contextual deserializer whose BeanPropertyMap has the ignored properties removed. The subsequent per-property case-insensitivity block (triggered by @JsonFormat(ACCEPT_CASE_INSENSITIVE_PROPERTIES)) rebuilds from this._beanProperties (the original, unfiltered map) instead of contextual._beanProperties, then overwrites the filtered map â restoring every property _handleByNameInclusion had just removed. The ignored property becomes writable again. This vulnerability is fixed in 2.18.9, 2.21.5, and 3.1.4.
#### References
* https://guide.sonatype.com/vulnerability/CVE-2026-54515?component-type=maven&component-name=com.fasterxml.jackson.core%2Fjackson-databind&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2026-54515
* https://github.com/FasterXML/jackson-databind/security/advisories/GHSA-5jmj-h7xm-6q6v

### CVE-2026-59889 (CWE-863) in dependency `com.fasterxml.jackson.core:jackson-databind:jar:2.22.0:test`
Jackson Databind -  Authorization bypass on JsonView Setter/Field
#### References
* https://guide.sonatype.com/vulnerability/CVE-2026-59889?component-type=maven&component-name=com.fasterxml.jackson.core%2Fjackson-databind&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2026-59889
* https://github.com/FasterXML/jackson-databind/issues/6060
* https://github.com/FasterXML/jackson-databind/pull/6056

### CVE-2026-9563 (CWE-400) in dependency `org.eclipse.parsson:parsson:jar:1.1.7:test`
In Eclipse Parsson published Maven Central artifacts before version 1.1.8, the JSON parser did not enforce a default maximum on the number of characters consumed while parsing a single JSON document. Applications that parse attacker- controlled JSON can be forced to consume excessive CPU and memory by processing very large documents, including large arrays, objects, strings, numbers, whitespace, or nested structures, resulting in a denial of service. Eclipse Parsson 1.1.8 introduces a configurable maximum parsing limit with a default limit of 15 million parser-consumed characters.
#### References
* https://guide.sonatype.com/vulnerability/CVE-2026-9563?component-type=maven&component-name=org.eclipse.parsson%2Fparsson&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2026-9563
* https://github.com/eclipse-ee4j/parsson/pull/169
* https://gitlab.eclipse.org/security/vulnerability-reports/-/work_items/444

### CVE-2026-54399 (CWE-400) in dependency `org.apache.httpcomponents.core5:httpcore5:jar:5.4:test`
Uncontrolled Resource Consumption vulnerability in the HTTP/1.1 message parserÂ in Apache HttpComponents Core (5.4.2 and earlier, 5.5-beta1 and earlier) allowsÂ an remote attacker to cause a denial of service through memory exhaustion by sending messages with excessive number of headers / excessive header length

Sonatype's research suggests that this CVE's details differ from those defined at NVD. See https://guide.sonatype.com/vulnerability/CVE-2026-54399 for details
#### References
* https://guide.sonatype.com/vulnerability/CVE-2026-54399?component-type=maven&component-name=org.apache.httpcomponents.core5%2Fhttpcore5&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2026-54399
* https://lists.apache.org/thread/zmxh1pl2zohov5ntdh4lt85gfrlchgpy
* http://www.openwall.com/lists/oss-security/2026/07/01/4

## Security

* #110: Fixed vulnerability CVE-2026-54428 in dependency `org.apache.httpcomponents.core5:httpcore5-h2:jar:5.4:test`
* #111: Fixed vulnerability CVE-2026-54515 in dependency `com.fasterxml.jackson.core:jackson-databind:jar:2.22.0:test`
* #112: Fixed vulnerability CVE-2026-59889 in dependency `com.fasterxml.jackson.core:jackson-databind:jar:2.22.0:test`
* #113: Fixed vulnerability CVE-2026-9563 in dependency `org.eclipse.parsson:parsson:jar:1.1.7:test`
* #114: Fixed vulnerability CVE-2026-54399 in dependency `org.apache.httpcomponents.core5:httpcore5:jar:5.4:test`

## Dependency Updates

### JDBC Kerberos Setup Tests

#### Runtime Dependency Updates

* Updated `com.exasol:exasol-jdbc:26.2.7` to `26.2.8`

#### Test Dependency Updates

* Updated `org.junit.jupiter:junit-jupiter-api:6.1.0` to `6.1.2`

#### Plugin Dependency Updates

* Updated `com.exasol:project-keeper-maven-plugin:5.6.2` to `5.7.3`

### Tableau Server GUI Tests

#### Test Dependency Updates

* Updated `com.exasol:test-db-builder-java:4.0.0` to `4.0.1`
* Updated `com.fasterxml.jackson.core:jackson-databind:2.22.0` to `2.22.1`
* Updated `org.bouncycastle:bcpkix-jdk18on:1.84` to `1.85`
* Updated `org.junit.jupiter:junit-jupiter-api:6.1.0` to `6.1.2`
* Updated `org.seleniumhq.selenium:selenium-java:4.44.0` to `4.46.0`

#### Plugin Dependency Updates

* Updated `com.exasol:project-keeper-maven-plugin:5.6.2` to `5.7.3`

### Exasol Connector for Tableau

#### Plugin Dependency Updates

* Updated `com.exasol:project-keeper-maven-plugin:5.6.2` to `5.7.3`

### Javascript-test

#### Development Dependency Updates

* Updated `jest:^30.2.0` to `^30.4.2`
