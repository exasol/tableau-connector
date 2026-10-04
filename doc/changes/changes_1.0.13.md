# Tableau Connector 1.0.13, released 2026-??-??

Code name: Fixed vulnerability CVE-2026-97873 in org.bouncycastle:bcprov-jdk18on:jar:1.85:test

## Summary

This release fixes the following vulnerability:

### CVE-2026-97873 (CWE-770) in dependency `org.bouncycastle:bcprov-jdk18on:jar:1.85:test`
In Bouncy Castle for Java before 1.86, the raw JCA provider's legacy PBES1 (PKCS#5 scheme 1) and PKCS#12 PBE families ran their password-based key derivation with an iteration count taken from untrusted input without bounding it, so a small input could dictate an arbitrary amount of work before anything could be verified. The AlgorithmParameters implementations (PKCS12PBE and its object identifier aliases, and PBKDF1) accepted any count from an encoded PKCS12PBEParams or PBEParameter, narrowing a value beyond the int range with intValue(), and every Cipher, Mac and SecretKeyFactory in these families derived with whatever count it was given, including one decoded by another provider's AlgorithmParameters, as when javax.crypto.EncryptedPrivateKeyInfo.getKeySpec() decrypts a PKCS#12 PBE-protected private key with BC. Both the parameter parse and the derivations now reject a negative or over-limit count under the org.bouncycastle.pbe.max_iteration_count property (default 10,000,000) that already bounded PBKDF2 (CVE-2026-17508), and the parse rejects a count beyond the int range rather than narrowing it. This issue also affects Bouncy Castle for Java LTS before 2.73.13.
#### References
* https://guide.sonatype.com/vulnerability/CVE-2026-97873?component-type=maven&component-name=org.bouncycastle%2Fbcprov-jdk18on&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2026-97873
* https://github.com/CVEProject/cvelistV5/blob/main/cves/2026/97xxx/CVE-2026-97873.json

## Security

* #142: Fixed vulnerability CVE-2026-97873 in dependency `org.bouncycastle:bcprov-jdk18on:jar:1.85:test`

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
* Updated `com.fasterxml.jackson.core:jackson-databind:2.22.1` to `2.22.3`
* Updated `io.github.bonigarcia:webdrivermanager:6.3.4` to `6.4.0`
* Updated `org.apache.httpcomponents.core5:httpcore5-h2:5.4.3` to `5.4.4`
* Updated `org.apache.httpcomponents.core5:httpcore5:5.4.3` to `5.4.4`
* Updated `org.bouncycastle:bcpkix-jdk18on:1.85` to `1.86`
* Updated `org.json:json:20260719` to `20260814`
* Updated `org.junit.jupiter:junit-jupiter-api:6.1.2` to `6.1.3`
* Updated `org.mockito:mockito-junit-jupiter:5.23.0` to `5.24.0`
* Updated `org.seleniumhq.selenium:selenium-java:4.46.0` to `4.50.0`

#### Plugin Dependency Updates

* Updated `com.exasol:project-keeper-maven-plugin:5.7.4` to `5.7.6`

### Exasol Connector for Tableau

#### Plugin Dependency Updates

* Updated `com.exasol:project-keeper-maven-plugin:5.7.4` to `5.7.6`

### Javascript-test

#### Development Dependency Updates

* Added `fast-xml-parser:^5.3.3`
* Removed `xml2json:^0.7.1`
