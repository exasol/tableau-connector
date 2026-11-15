# Tableau Connector 1.0.14, released 2026-??-??

Code name: Fixed vulnerabilities CVE-2026-119078, CVE-2026-103606, CVE-2026-103610, CVE-2026-103615, CVE-2026-103617, CVE-2026-103611, CVE-2026-103612, CVE-2026-103613, CVE-2026-103614, CVE-2026-103616, CVE-2026-103617

## Summary

This release fixes the following 11 vulnerabilities:

### CVE-2026-119078 (CWE-1068) in dependency `commons-codec:commons-codec:jar:1.19.0:test`
Apache Commons Codec's Blake3 class documented the output of its doFinalize methods as depending on any previously finalized bytes, so that consecutive calls would return different output. In fact, doFinalize does not change the hash state: calling it again without supplying more input returns the same bytes. The key-derivation example in the class Javadoc, shipped since 1.16.0, derives two keys with two consecutive doFinalize calls. Code that follows it gets identical keys, so the separation between the derived keys is lost. The same applies to any application that calls doFinalize more than once on one instance and expects new output each time.

This issue affects Apache Commons Codec from 1.16.0 before 1.23.0.

Version 1.23.0 corrects the documentation, but the behavior of doFinalize is unchanged. Upgrading alone does not fix affected code. Users should check their applications for repeated doFinalize calls on the same Blake3 instance and derive multiple keys by requesting the combined length in one call and splitting the output, as the updated Javadoc shows.
#### References
* https://guide.sonatype.com/vulnerability/CVE-2026-119078?component-type=maven&component-name=commons-codec%2Fcommons-codec&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2026-119078
* https://lists.apache.org/thread.html/ol71kdgopfxwlxgqmkd0jrfcw05wdq5z
* http://www.openwall.com/lists/oss-security/2026/11/09/29

### CVE-2026-103606 (CWE-328) in dependency `org.bouncycastle:bcpkix-jdk18on:jar:1.86:test`
org.bouncycastle:bcpkix - Use of Weak Hash
#### References
* https://guide.sonatype.com/vulnerability/CVE-2026-103606?component-type=maven&component-name=org.bouncycastle%2Fbcpkix-jdk18on&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2026-103606
* https://github.com/bcgit/bc-java/blob/r1rv87/docs/releasenotes.md#215-security-advisories

### CVE-2026-103610 (CWE-407) in dependency `org.bouncycastle:bcpkix-jdk18on:jar:1.86:test`
org.bouncycastle:bcpkix - Inefficient Algorithmic Complexity
#### References
* https://guide.sonatype.com/vulnerability/CVE-2026-103610?component-type=maven&component-name=org.bouncycastle%2Fbcpkix-jdk18on&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2026-103610
* https://github.com/bcgit/bc-java/blob/r1rv87/docs/releasenotes.md#215-security-advisories

### CVE-2026-103615 (CWE-1288) in dependency `org.bouncycastle:bcpkix-jdk18on:jar:1.86:test`
org.bouncycastle:bcpkix - Improper Validation of Consistency within Input
#### References
* https://guide.sonatype.com/vulnerability/CVE-2026-103615?component-type=maven&component-name=org.bouncycastle%2Fbcpkix-jdk18on&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2026-103615
* https://github.com/bcgit/bc-java/blob/r1rv87/docs/releasenotes.md#215-security-advisories

### CVE-2026-103617 (CWE-299) in dependency `org.bouncycastle:bcpkix-jdk18on:jar:1.86:test`
Bouncy Castle - Improper Check for Certificate Revocation
#### References
* https://guide.sonatype.com/vulnerability/CVE-2026-103617?component-type=maven&component-name=org.bouncycastle%2Fbcpkix-jdk18on&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2026-103617
* https://github.com/bcgit/bc-java/blob/r1rv87/docs/releasenotes.md#215-security-advisories

### CVE-2026-103611 (CWE-407) in dependency `org.bouncycastle:bcprov-jdk18on:jar:1.86:test`
org.bouncycastle:bcprov - Inefficient Algorithmic Complexity
#### References
* https://guide.sonatype.com/vulnerability/CVE-2026-103611?component-type=maven&component-name=org.bouncycastle%2Fbcprov-jdk18on&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2026-103611
* https://github.com/bcgit/bc-java/blob/r1rv87/docs/releasenotes.md#215-security-advisories

### CVE-2026-103612 (CWE-347) in dependency `org.bouncycastle:bcprov-jdk18on:jar:1.86:test`
org.bouncycastle:bcprov - Improper Verification of Cryptographic Signature
#### References
* https://guide.sonatype.com/vulnerability/CVE-2026-103612?component-type=maven&component-name=org.bouncycastle%2Fbcprov-jdk18on&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2026-103612
* https://github.com/bcgit/bc-java/blob/r1rv87/docs/releasenotes.md#215-security-advisories

### CVE-2026-103613 (CWE-459) in dependency `org.bouncycastle:bcprov-jdk18on:jar:1.86:test`
org.bouncycastle:bcprov - Incomplete Cleanup
#### References
* https://guide.sonatype.com/vulnerability/CVE-2026-103613?component-type=maven&component-name=org.bouncycastle%2Fbcprov-jdk18on&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2026-103613
* https://github.com/bcgit/bc-java/blob/r1rv87/docs/releasenotes.md#215-security-advisories

### CVE-2026-103614 (CWE-203) in dependency `org.bouncycastle:bcprov-jdk18on:jar:1.86:test`
org.bouncycastle:bcprov - Observable Discrepancy
#### References
* https://guide.sonatype.com/vulnerability/CVE-2026-103614?component-type=maven&component-name=org.bouncycastle%2Fbcprov-jdk18on&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2026-103614
* https://github.com/bcgit/bc-java/blob/r1rv87/docs/releasenotes.md#215-security-advisories

### CVE-2026-103616 (CWE-203) in dependency `org.bouncycastle:bcprov-jdk18on:jar:1.86:test`
org.bouncycastle:bcprov - Observable Discrepancy
#### References
* https://guide.sonatype.com/vulnerability/CVE-2026-103616?component-type=maven&component-name=org.bouncycastle%2Fbcprov-jdk18on&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2026-103616
* https://github.com/bcgit/bc-java/blob/r1rv87/docs/releasenotes.md#215-security-advisories

### CVE-2026-103617 (CWE-299) in dependency `org.bouncycastle:bcprov-jdk18on:jar:1.86:test`
Bouncy Castle - Improper Check for Certificate Revocation
#### References
* https://guide.sonatype.com/vulnerability/CVE-2026-103617?component-type=maven&component-name=org.bouncycastle%2Fbcprov-jdk18on&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2026-103617
* https://github.com/bcgit/bc-java/blob/r1rv87/docs/releasenotes.md#215-security-advisories

## Security

* #151: Fixed vulnerability CVE-2026-119078 in dependency `commons-codec:commons-codec:jar:1.19.0:test`
* #152: Fixed vulnerability CVE-2026-103606 in dependency `org.bouncycastle:bcpkix-jdk18on:jar:1.86:test`
* #153: Fixed vulnerability CVE-2026-103610 in dependency `org.bouncycastle:bcpkix-jdk18on:jar:1.86:test`
* #154: Fixed vulnerability CVE-2026-103615 in dependency `org.bouncycastle:bcpkix-jdk18on:jar:1.86:test`
* #155: Fixed vulnerability CVE-2026-103617 in dependency `org.bouncycastle:bcpkix-jdk18on:jar:1.86:test`
* #156: Fixed vulnerability CVE-2026-103611 in dependency `org.bouncycastle:bcprov-jdk18on:jar:1.86:test`
* #157: Fixed vulnerability CVE-2026-103612 in dependency `org.bouncycastle:bcprov-jdk18on:jar:1.86:test`
* #158: Fixed vulnerability CVE-2026-103613 in dependency `org.bouncycastle:bcprov-jdk18on:jar:1.86:test`
* #159: Fixed vulnerability CVE-2026-103614 in dependency `org.bouncycastle:bcprov-jdk18on:jar:1.86:test`
* #160: Fixed vulnerability CVE-2026-103616 in dependency `org.bouncycastle:bcprov-jdk18on:jar:1.86:test`
* #161: Fixed vulnerability CVE-2026-103617 in dependency `org.bouncycastle:bcprov-jdk18on:jar:1.86:test`

## Dependency Updates

### JDBC Kerberos Setup Tests

#### Plugin Dependency Updates

* Updated `com.exasol:project-keeper-maven-plugin:5.8.0` to `5.8.2`

### Tableau Server GUI Tests

#### Test Dependency Updates

* Updated `com.exasol:test-db-builder-java:4.0.3` to `5.0.0`
* Updated `com.fasterxml.jackson.core:jackson-databind:2.22.4` to `2.23.0`
* Updated `org.bouncycastle:bcpkix-jdk18on:1.86` to `1.87`
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
