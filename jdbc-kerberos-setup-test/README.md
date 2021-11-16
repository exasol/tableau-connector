# jdbc-kerberos-setup-test

This directory contains JUnit integrationt tests that verify Kerberos delegation works in your setup. We recommend using this if Kerberos authentication does not work with Tableau Server.

## Initial configuration

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
kerberos_config_file=C:/path/to/krb5.ini
# Path to the keytab file for the delegating user, e.g. C:/ProgramData/Tableau/tableauuser-delegation.keytab
keytab_file=C:/path/to/kt.keytab
````
