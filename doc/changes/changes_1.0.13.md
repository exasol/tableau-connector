# Tableau Connector 1.0.13, released 2026-??-??

Code name: Fixed vulnerabilities CVE-2026-40542, CVE-2026-71290

## Summary

This release fixes the following 2 vulnerabilities:

### CVE-2026-40542 (CWE-304) in dependency `org.apache.httpcomponents.client5:httpclient5:jar:5.6:test`
Missing critical step in authentication in Apache HttpClient 5.6 allows an attacker to cause the client to accept SCRAM-SHA-256 authentication without proper mutual authentication verification. Users are recommended to upgrade to version 5.6.1, which fixes this issue.
#### References
* https://guide.sonatype.com/vulnerability/CVE-2026-40542?component-type=maven&component-name=org.apache.httpcomponents.client5%2Fhttpclient5&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2026-40542
* https://lists.apache.org/thread/tfmgv86xr0z1y096vs3z0y315t1v3o97

### CVE-2026-71290 (CWE-295) in dependency `org.apache.httpcomponents.client5:httpclient5:jar:5.6:test`
Improper TLS hostname verification vulnerability in Apache HttpComponents Client 5.4 or newer.Â HostnameVerificationPolicy#BUILTIN setting has no effect when used with the async version of HttpClient. An attacker that can intercept and modify traffic between the client and the server can impersonate the server by presenting a valid certificate for a different domain.Â 

Please note the classic version of HttpClient is not affected by this vulnerability.Â 

Affected users are recommended to upgrade to at least version 5.6.4, which fixes the issue.
#### References
* https://guide.sonatype.com/vulnerability/CVE-2026-71290?component-type=maven&component-name=org.apache.httpcomponents.client5%2Fhttpclient5&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2026-71290
* https://lists.apache.org/thread/bhf7g2zwpom2ohvwjjjlonc93br2s8vq
* https://github.com/advisories/GHSA-72q8-9rgw-5g6j

## Security

* #120: Fixed vulnerability CVE-2026-40542 in dependency `org.apache.httpcomponents.client5:httpclient5:jar:5.6:test`
* #121: Fixed vulnerability CVE-2026-71290 in dependency `org.apache.httpcomponents.client5:httpclient5:jar:5.6:test`

## Dependency Updates

### JDBC Kerberos Setup Tests

#### Test Dependency Updates

* Updated `org.junit.jupiter:junit-jupiter-api:6.1.2` to `6.1.3`

### Tableau Server GUI Tests

#### Test Dependency Updates

* Updated `com.fasterxml.jackson.core:jackson-databind:2.22.1` to `2.22.2`
* Updated `org.json:json:20260719` to `20260814`
* Updated `org.junit.jupiter:junit-jupiter-api:6.1.2` to `6.1.3`
* Updated `org.seleniumhq.selenium:selenium-java:4.46.0` to `4.48.0`

### Javascript-test

#### Development Dependency Updates

* Added `fast-xml-parser:^5.3.3`
* Removed `xml2json:^0.7.1`
