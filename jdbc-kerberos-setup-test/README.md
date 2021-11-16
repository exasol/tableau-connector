# jdbc-kerberos-setup-test

This directory contains JUnit integration tests that verify Kerberos delegation works in your setup. We recommend using this if Kerberos authentication does not work with Tableau Server.

The code is based on project [constrained-delegation-jdbc](https://github.com/devanshsoni9/constrained-delegation-jdbc) by [@devanshsoni9](https://github.com/devanshsoni9).

## Initial configuration

For running the tests you will need to install JDK 11 or later e.g. from [Adoptium](https://adoptium.net/) and [Apache Maven](https://maven.apache.org/).

Setup the Kerberos environment and create user accounts as described in the [user guide](../doc/user_guide/user_guide.md) and [Tableau Server documentation](https://help.tableau.com/current/server/en-us/kerberos_delegation_jdbc.htm).

Create file `jdbc-kerberos-setup-test/test.properties` with the following content:

```properties
# Hostname of the Exasol database
db_hostname = exasoldb.example.com
# Port of the Exasol database
db_port = 8563
# Fingerprint of the TLS certificate used by the database, e.g. ABD591342466880A16A4443DEEFF44A78A26E47514BE4D5E1C4CB712345F69CA
db_certificate_fingerprint = <fingerprint>

# Enable verbose log messages for Kerberos
kerberos_debug = true

# The RunAs user, e.g. tableauuser
runas_user = <username>
# The user to impersonate
impersonated_user = <username>
# The kerberos password of the impersonated db user
impersonated_user_kerberos_password = ****
# Optional: The impersonated user's database user name if it is different from the Kerberos username
impersonated_user_db_name = <username>

# Path to the Kerberos config file, e.g. C:/ProgramData/Tableau/krb5.ini
kerberos_config_file = C:/path/to/krb5.ini
# Path to the keytab file for the delegating user, e.g. C:/ProgramData/Tableau/tableauuser-delegation.keytab
keytab_file = C:/path/to/kt.keytab
```

Some tests require valid credentials in the local credential cache. Run `klist` to check if the credentials are still valid. Run `kinit` to retrieve new credentials. The Maven build will hang if no valid credentials are available.

## Run Tests

```sh
cd jdbc-kerberos-setup-test
mvn integration-test
```

## Troubleshooting

Use Exasol JDBC driver 7.1.3 or later. Else the `impersonate` test will fail with error `SQLInvalidAuthorizationSpecException: No LoginModules configured for exasol`.

Tests should finish in around 10 seconds. If they hang for a longer time, verify that your Kerberos credentials are valide. See above for using `klist` and `kinit`.
