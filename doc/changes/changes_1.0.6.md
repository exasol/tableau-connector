# Tableau Connector 1.0.6, released 2024-??-??

Code name: Upgrade dependencies, fixed vulnerabilities CVE-2023-33201, CVE-2023-33202, CVE-2024-29857, CVE-2024-30171, CVE-2024-34447, CVE-2024-29857, CVE-2024-30171, CVE-2024-30172, CVE-2024-34447

## Summary

This release upgrades dependencies.

This release fixes the following 9 vulnerabilities:

### CVE-2023-33201 (CWE-295) in dependency `org.bouncycastle:bcprov-jdk15on:jar:1.70:test`
Bouncy Castle For Java before 1.74 is affected by an LDAP injection vulnerability. The vulnerability only affects applications that use an LDAP CertStore from Bouncy Castle to validate X.509 certificates. During the certificate validation process, Bouncy Castle inserts the certificate's Subject Name into an LDAP search filter without any escaping, which leads to an LDAP injection vulnerability.
#### References
* https://ossindex.sonatype.org/vulnerability/CVE-2023-33201?component-type=maven&component-name=org.bouncycastle%2Fbcprov-jdk15on&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2023-33201
* https://github.com/bcgit/bc-java/wiki/CVE-2023-33201

### CVE-2023-33202 (CWE-400) in dependency `org.bouncycastle:bcprov-jdk15on:jar:1.70:test`
Bouncy Castle for Java before 1.73 contains a potential Denial of Service (DoS) issue within the Bouncy Castle org.bouncycastle.openssl.PEMParser class. This class parses OpenSSL PEM encoded streams containing X.509 certificates, PKCS8 encoded keys, and PKCS7 objects. Parsing a file that has crafted ASN.1 data through the PEMParser causes an OutOfMemoryError, which can enable a denial of service attack. (For users of the FIPS Java API: BC-FJA 1.0.2.3 and earlier are affected; BC-FJA 1.0.2.4 is fixed.)
#### References
* https://ossindex.sonatype.org/vulnerability/CVE-2023-33202?component-type=maven&component-name=org.bouncycastle%2Fbcprov-jdk15on&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2023-33202
* https://github.com/bcgit/bc-java/wiki/CVE-2023-33202

### CVE-2024-29857 (CWE-400) in dependency `org.bouncycastle:bcprov-jdk15on:jar:1.70:test`
An issue was discovered in ECCurve.java and ECCurve.cs in Bouncy Castle Java (BC Java) before 1.78, BC Java LTS before 2.73.6, BC-FJA before 1.0.2.5, and BC C# .Net before 2.3.1. Importing an EC certificate with crafted F2m parameters can lead to excessive CPU consumption during the evaluation of the curve parameters.
#### References
* https://ossindex.sonatype.org/vulnerability/CVE-2024-29857?component-type=maven&component-name=org.bouncycastle%2Fbcprov-jdk15on&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2024-29857
* https://www.bouncycastle.org/releasenotes.html#:~:text=the%20following%20CVEs%3A-,CVE%2D2024%2D29857,-%2D%20Importing%20an%20EC

### CVE-2024-30171 (CWE-208) in dependency `org.bouncycastle:bcprov-jdk15on:jar:1.70:test`
An issue was discovered in Bouncy Castle Java TLS API and JSSE Provider before 1.78. Timing-based leakage may occur in RSA based handshakes because of exception processing.
#### References
* https://ossindex.sonatype.org/vulnerability/CVE-2024-30171?component-type=maven&component-name=org.bouncycastle%2Fbcprov-jdk15on&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2024-30171
* https://github.com/bcgit/bc-java/issues/1528
* https://www.bouncycastle.org/releasenotes.html#:~:text=during%20parameter%20evaluation.-,CVE%2D2024%2D30171,-%2D%20Possible%20timing%20based

### CVE-2024-34447 (CWE-297) in dependency `org.bouncycastle:bcprov-jdk15on:jar:1.70:test`
bouncycastle - Improper Validation of Certificate with Host Mismatch

The software communicates with a host that provides a certificate, but the software does not properly ensure that the certificate is actually associated with that host.
#### References
* https://ossindex.sonatype.org/vulnerability/CVE-2024-34447?component-type=maven&component-name=org.bouncycastle%2Fbcprov-jdk15on&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* https://www.bouncycastle.org/releasenotes.html#:~:text=CVE%2D2024%2D301XX%20%2D%20When%20endpoint%20identification%20is%20enabled%20in%20the%20BCJSSE%20and%20an%20SSL%20socket%20is%20not%20created%20with%20an%20explicit%20hostname%20(as%20happens%20with%20HttpsURLConnection)%2C%20hostname%20verification%20could%20be%20performed%20against%20a%20DNS%2Dresolved%20IP%20address.%20This%20has%20been%20fixed.

### CVE-2024-29857 (CWE-400) in dependency `org.bouncycastle:bcprov-jdk18on:jar:1.76:test`
An issue was discovered in ECCurve.java and ECCurve.cs in Bouncy Castle Java (BC Java) before 1.78, BC Java LTS before 2.73.6, BC-FJA before 1.0.2.5, and BC C# .Net before 2.3.1. Importing an EC certificate with crafted F2m parameters can lead to excessive CPU consumption during the evaluation of the curve parameters.
#### References
* https://ossindex.sonatype.org/vulnerability/CVE-2024-29857?component-type=maven&component-name=org.bouncycastle%2Fbcprov-jdk18on&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2024-29857
* https://www.bouncycastle.org/releasenotes.html#:~:text=the%20following%20CVEs%3A-,CVE%2D2024%2D29857,-%2D%20Importing%20an%20EC

### CVE-2024-30171 (CWE-208) in dependency `org.bouncycastle:bcprov-jdk18on:jar:1.76:test`
An issue was discovered in Bouncy Castle Java TLS API and JSSE Provider before 1.78. Timing-based leakage may occur in RSA based handshakes because of exception processing.
#### References
* https://ossindex.sonatype.org/vulnerability/CVE-2024-30171?component-type=maven&component-name=org.bouncycastle%2Fbcprov-jdk18on&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2024-30171
* https://github.com/bcgit/bc-java/issues/1528
* https://www.bouncycastle.org/releasenotes.html#:~:text=during%20parameter%20evaluation.-,CVE%2D2024%2D30171,-%2D%20Possible%20timing%20based

### CVE-2024-30172 (CWE-835) in dependency `org.bouncycastle:bcprov-jdk18on:jar:1.76:test`
An issue was discovered in Bouncy Castle Java Cryptography APIs before 1.78. An Ed25519 verification code infinite loop can occur via a crafted signature and public key.
#### References
* https://ossindex.sonatype.org/vulnerability/CVE-2024-30172?component-type=maven&component-name=org.bouncycastle%2Fbcprov-jdk18on&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2024-30172
* https://www.bouncycastle.org/releasenotes.html#:~:text=exception%20processing%20eliminated.-,CVE%2D2024%2D30172,-%2D%20Crafted%20signature%20and

### CVE-2024-34447 (CWE-297) in dependency `org.bouncycastle:bcprov-jdk18on:jar:1.76:test`
bouncycastle - Improper Validation of Certificate with Host Mismatch

The software communicates with a host that provides a certificate, but the software does not properly ensure that the certificate is actually associated with that host.
#### References
* https://ossindex.sonatype.org/vulnerability/CVE-2024-34447?component-type=maven&component-name=org.bouncycastle%2Fbcprov-jdk18on&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* https://www.bouncycastle.org/releasenotes.html#:~:text=CVE%2D2024%2D301XX%20%2D%20When%20endpoint%20identification%20is%20enabled%20in%20the%20BCJSSE%20and%20an%20SSL%20socket%20is%20not%20created%20with%20an%20explicit%20hostname%20(as%20happens%20with%20HttpsURLConnection)%2C%20hostname%20verification%20could%20be%20performed%20against%20a%20DNS%2Dresolved%20IP%20address.%20This%20has%20been%20fixed.

## Security

* #79: Fixed vulnerability CVE-2023-33201 in dependency `org.bouncycastle:bcprov-jdk15on:jar:1.70:test`
* #80: Fixed vulnerability CVE-2023-33202 in dependency `org.bouncycastle:bcprov-jdk15on:jar:1.70:test`
* #81: Fixed vulnerability CVE-2024-29857 in dependency `org.bouncycastle:bcprov-jdk15on:jar:1.70:test`
* #82: Fixed vulnerability CVE-2024-30171 in dependency `org.bouncycastle:bcprov-jdk15on:jar:1.70:test`
* #83: Fixed vulnerability CVE-2024-34447 in dependency `org.bouncycastle:bcprov-jdk15on:jar:1.70:test`
* #84: Fixed vulnerability CVE-2024-29857 in dependency `org.bouncycastle:bcprov-jdk18on:jar:1.76:test`
* #85: Fixed vulnerability CVE-2024-30171 in dependency `org.bouncycastle:bcprov-jdk18on:jar:1.76:test`
* #86: Fixed vulnerability CVE-2024-30172 in dependency `org.bouncycastle:bcprov-jdk18on:jar:1.76:test`
* #87: Fixed vulnerability CVE-2024-34447 in dependency `org.bouncycastle:bcprov-jdk18on:jar:1.76:test`

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
* Updated `com.exasol:project-keeper-maven-plugin:3.0.0` to `4.3.1`
* Updated `org.apache.maven.plugins:maven-compiler-plugin:3.11.0` to `3.13.0`
* Updated `org.apache.maven.plugins:maven-surefire-plugin:3.2.3` to `3.2.5`
* Updated `org.apache.maven.plugins:maven-toolchains-plugin:3.1.0` to `3.2.0`
* Updated `org.codehaus.mojo:flatten-maven-plugin:1.5.0` to `1.6.0`
* Updated `org.jacoco:jacoco-maven-plugin:0.8.11` to `0.8.12`
* Updated `org.sonarsource.scanner.maven:sonar-maven-plugin:3.10.0.2594` to `3.11.0.3922`

### Tableau Server GUI Tests

#### Test Dependency Updates

* Updated `com.exasol:exasol-testcontainers:7.0.0` to `7.1.0`
* Updated `com.exasol:test-db-builder-java:3.5.3` to `3.5.4`
* Updated `com.fasterxml.jackson.core:jackson-databind:2.16.1` to `2.17.1`
* Updated `commons-io:commons-io:2.15.1` to `2.16.1`
* Updated `io.github.bonigarcia:webdrivermanager:5.6.3` to `5.8.0`
* Updated `org.json:json:20231013` to `20240303`
* Updated `org.junit.jupiter:junit-jupiter:5.10.1` to `5.10.2`
* Updated `org.mockito:mockito-junit-jupiter:5.9.0` to `5.12.0`
* Updated `org.seleniumhq.selenium:selenium-java:4.16.1` to `4.21.0`
* Updated `org.testcontainers:junit-jupiter:1.19.3` to `1.19.8`

#### Plugin Dependency Updates

* Updated `com.exasol:error-code-crawler-maven-plugin:1.3.1` to `2.0.3`
* Updated `com.exasol:project-keeper-maven-plugin:3.0.0` to `4.3.1`
* Updated `org.apache.maven.plugins:maven-compiler-plugin:3.11.0` to `3.13.0`
* Updated `org.apache.maven.plugins:maven-surefire-plugin:3.2.3` to `3.2.5`
* Updated `org.apache.maven.plugins:maven-toolchains-plugin:3.1.0` to `3.2.0`
* Updated `org.codehaus.mojo:flatten-maven-plugin:1.5.0` to `1.6.0`
* Updated `org.jacoco:jacoco-maven-plugin:0.8.11` to `0.8.12`
* Updated `org.sonarsource.scanner.maven:sonar-maven-plugin:3.10.0.2594` to `3.11.0.3922`

### Exasol Connector for Tableau

#### Plugin Dependency Updates

* Updated `com.exasol:error-code-crawler-maven-plugin:1.3.1` to `2.0.3`
* Updated `com.exasol:project-keeper-maven-plugin:3.0.0` to `4.3.1`
* Updated `org.apache.maven.plugins:maven-compiler-plugin:3.11.0` to `3.13.0`
* Updated `org.apache.maven.plugins:maven-surefire-plugin:3.2.3` to `3.2.5`
* Updated `org.apache.maven.plugins:maven-toolchains-plugin:3.1.0` to `3.2.0`
* Updated `org.codehaus.mojo:flatten-maven-plugin:1.5.0` to `1.6.0`
* Updated `org.jacoco:jacoco-maven-plugin:0.8.11` to `0.8.12`
* Updated `org.sonarsource.scanner.maven:sonar-maven-plugin:3.10.0.2594` to `3.11.0.3922`
