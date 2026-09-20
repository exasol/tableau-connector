# Tableau Connector 1.0.13, released 2026-??-??

Code name: Fixed vulnerabilities CVE-2026-68498, CVE-2026-71888, CVE-2026-71889, CVE-2026-71892, CVE-2026-17508, CVE-2026-18036, CVE-2026-18040, CVE-2026-71889, CVE-2026-71891, CVE-2026-91776, CVE-2026-91777, CVE-2026-83557

## Summary

This release fixes the following 12 vulnerabilities:

### CVE-2026-68498 (CWE-770) in dependency `com.fasterxml.jackson.core:jackson-core:jar:2.22.1:test`
jackson-core - Allocation of Resources Without Limits or Throttling

Sonatype's research suggests that this CVE's details differ from those defined at NVD. See https://guide.sonatype.com/vulnerability/CVE-2026-68498 for details
#### References
* https://guide.sonatype.com/vulnerability/CVE-2026-68498?component-type=maven&component-name=com.fasterxml.jackson.core%2Fjackson-core&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2026-68498
* https://github.com/FasterXML/jackson-core/security/advisories/GHSA-649p-m576-vr99

### CVE-2026-71888 (CWE-354) in dependency `org.bouncycastle:bcpkix-jdk18on:jar:1.85:test`
Bouncy Castle for Java - CMS AuthenticatedData exposes attacker-inserted authAttrs when digestAlgorithm is absent.
#### References
* https://guide.sonatype.com/vulnerability/CVE-2026-71888?component-type=maven&component-name=org.bouncycastle%2Fbcpkix-jdk18on&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2026-71888
* https://github.com/bcgit/bc-java/discussions/2449

### CVE-2026-71889 (CWE-295) in dependency `org.bouncycastle:bcpkix-jdk18on:jar:1.85:test`
Bouncy Castle for Java - PKIXCertPathReviewer does not apply X.509 name constraints to the target certificate.
#### References
* https://guide.sonatype.com/vulnerability/CVE-2026-71889?component-type=maven&component-name=org.bouncycastle%2Fbcpkix-jdk18on&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2026-71889
* https://github.com/bcgit/bc-java/discussions/2449

### CVE-2026-71892 (CWE-325) in dependency `org.bouncycastle:bcpkix-jdk18on:jar:1.85:test`
Bouncy Castle for Java - CMS key-transport recipient key-size validation never runs for RFC 9709 HKDF-derived keys.
#### References
* https://guide.sonatype.com/vulnerability/CVE-2026-71892?component-type=maven&component-name=org.bouncycastle%2Fbcpkix-jdk18on&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2026-71892
* https://github.com/bcgit/bc-java/discussions/2449

### CVE-2026-17508 (CWE-770) in dependency `org.bouncycastle:bcprov-jdk18on:jar:1.85:test`
Bouncy Castle for Java - Password-based KDF cost parameters honoured unbounded from untrusted input across the remaining PBE entry points.
#### References
* https://guide.sonatype.com/vulnerability/CVE-2026-17508?component-type=maven&component-name=org.bouncycastle%2Fbcprov-jdk18on&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2026-17508
* https://github.com/bcgit/bc-java/discussions/2449

### CVE-2026-18036 (CWE-208) in dependency `org.bouncycastle:bcprov-jdk18on:jar:1.85:test`
Bouncy Castle for Java - NTRU leaks private key information by reducing secret values with a non-constant-time integer division
#### References
* https://guide.sonatype.com/vulnerability/CVE-2026-18036?component-type=maven&component-name=org.bouncycastle%2Fbcprov-jdk18on&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2026-18036
* https://github.com/bcgit/bc-java/discussions/2449

### CVE-2026-18040 (CWE-203) in dependency `org.bouncycastle:bcprov-jdk18on:jar:1.85:test`
Bouncy Castle for Java - HQC leaks private key information through secret-indexed GF(2^8) tables and a secret-dependent fixed-weight sampler.
#### References
* https://guide.sonatype.com/vulnerability/CVE-2026-18040?component-type=maven&component-name=org.bouncycastle%2Fbcprov-jdk18on&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2026-18040
* https://github.com/bcgit/bc-java/discussions/2449

### CVE-2026-71889 (CWE-295) in dependency `org.bouncycastle:bcprov-jdk18on:jar:1.85:test`
Bouncy Castle for Java - PKIXCertPathReviewer does not apply X.509 name constraints to the target certificate.
#### References
* https://guide.sonatype.com/vulnerability/CVE-2026-71889?component-type=maven&component-name=org.bouncycastle%2Fbcprov-jdk18on&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2026-71889
* https://github.com/bcgit/bc-java/discussions/2449

### CVE-2026-71891 (CWE-347) in dependency `org.bouncycastle:bcprov-jdk18on:jar:1.85:test`
Bouncy Castle for Java - BLS12-381 key validation accepts a public key built on a foreign curve.
#### References
* https://guide.sonatype.com/vulnerability/CVE-2026-71891?component-type=maven&component-name=org.bouncycastle%2Fbcprov-jdk18on&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2026-71891
* https://github.com/bcgit/bc-java/discussions/2449

### CVE-2026-91776 (CWE-770) in dependency `com.fasterxml.jackson.core:jackson-databind:jar:2.22.1:test`
Jackson-databind - Unbounded growth of type id cache in TypeDeserializer
#### References
* https://guide.sonatype.com/vulnerability/CVE-2026-91776?component-type=maven&component-name=com.fasterxml.jackson.core%2Fjackson-databind&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2026-91776
* https://github.com/FasterXML/jackson-databind/pull/6203

### CVE-2026-91777 (CWE-407) in dependency `com.fasterxml.jackson.core:jackson-databind:jar:2.22.1:test`
Jackson-databind - Denial of Service (DoS)
#### References
* https://guide.sonatype.com/vulnerability/CVE-2026-91777?component-type=maven&component-name=com.fasterxml.jackson.core%2Fjackson-databind&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2026-91777
* https://github.com/FasterXML/jackson-databind/pull/6204

### CVE-2026-83557 (CWE-502) in dependency `com.fasterxml.jackson.core:jackson-databind:jar:2.22.1:test`
jackson-databind - Deserialization of untrusted data [CVE-2026-83557]
#### References
* https://guide.sonatype.com/vulnerability/CVE-2026-83557?component-type=maven&component-name=com.fasterxml.jackson.core%2Fjackson-databind&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2026-83557
* https://github.com/FasterXML/jackson-databind/issues/6156
* https://github.com/FasterXML/jackson-databind/security/advisories/GHSA-gx83-3vf8-gh7j

## Security

* #125: Fixed vulnerability CVE-2026-68498 in dependency `com.fasterxml.jackson.core:jackson-core:jar:2.22.1:test`
* #126: Fixed vulnerability CVE-2026-71888 in dependency `org.bouncycastle:bcpkix-jdk18on:jar:1.85:test`
* #127: Fixed vulnerability CVE-2026-71889 in dependency `org.bouncycastle:bcpkix-jdk18on:jar:1.85:test`
* #128: Fixed vulnerability CVE-2026-71892 in dependency `org.bouncycastle:bcpkix-jdk18on:jar:1.85:test`
* #129: Fixed vulnerability CVE-2026-17508 in dependency `org.bouncycastle:bcprov-jdk18on:jar:1.85:test`
* #130: Fixed vulnerability CVE-2026-18036 in dependency `org.bouncycastle:bcprov-jdk18on:jar:1.85:test`
* #131: Fixed vulnerability CVE-2026-18040 in dependency `org.bouncycastle:bcprov-jdk18on:jar:1.85:test`
* #132: Fixed vulnerability CVE-2026-71889 in dependency `org.bouncycastle:bcprov-jdk18on:jar:1.85:test`
* #133: Fixed vulnerability CVE-2026-71891 in dependency `org.bouncycastle:bcprov-jdk18on:jar:1.85:test`
* #134: Fixed vulnerability CVE-2026-91776 in dependency `com.fasterxml.jackson.core:jackson-databind:jar:2.22.1:test`
* #135: Fixed vulnerability CVE-2026-91777 in dependency `com.fasterxml.jackson.core:jackson-databind:jar:2.22.1:test`
* #136: Fixed vulnerability CVE-2026-83557 in dependency `com.fasterxml.jackson.core:jackson-databind:jar:2.22.1:test`

## Dependency Updates

### JDBC Kerberos Setup Tests

#### Runtime Dependency Updates

* Updated `com.exasol:exasol-jdbc:26.2.8` to `26.2.9`

#### Test Dependency Updates

* Updated `org.junit.jupiter:junit-jupiter-api:6.1.2` to `6.1.3`

#### Plugin Dependency Updates

* Updated `com.exasol:project-keeper-maven-plugin:5.7.4` to `5.7.6`

### Tableau Server GUI Tests

#### Test Dependency Updates

* Updated `com.exasol:exasol-testcontainers:8.0.1` to `8.0.2`
* Updated `com.exasol:test-db-builder-java:4.0.2` to `4.0.3`
* Updated `com.fasterxml.jackson.core:jackson-databind:2.22.1` to `2.22.2`
* Updated `org.bouncycastle:bcpkix-jdk18on:1.85` to `1.86`
* Updated `org.json:json:20260719` to `20260814`
* Updated `org.junit.jupiter:junit-jupiter-api:6.1.2` to `6.1.3`
* Updated `org.seleniumhq.selenium:selenium-java:4.46.0` to `4.49.0`

#### Plugin Dependency Updates

* Updated `com.exasol:project-keeper-maven-plugin:5.7.4` to `5.7.6`

### Exasol Connector for Tableau

#### Plugin Dependency Updates

* Updated `com.exasol:project-keeper-maven-plugin:5.7.4` to `5.7.6`

### Javascript-test

#### Development Dependency Updates

* Added `fast-xml-parser:^5.3.3`
* Removed `xml2json:^0.7.1`
