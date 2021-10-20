# Running tests for the Exasol JDBC connector

## Setup

* Create a DB user:

    ```sql
    create user TABLEAU_TEST_USER identified by "TABLEAU_TEST_PASSWORD";
    grant create session to TABLEAU_TEST_USER;
    grant select on schema testv1 to TABLEAU_TEST_USER;
    ```

* Add this to `c:\Windows\System32\Drivers\etc\hosts`:

```
10.0.0.2    exasol.test.lan
```

* Install tdvt


## Run tests

```bash
cd tdvt_jdbc
python -m tdvt.tdvt run exasol_jdbc --generate
python -m tdvt.tdvt run exasol_jdbc
```

## Troubleshooting

Log files of Tableau Desktop: `%USERPROFILE%\Documents\My Tableau Repository\Logs\log.txt`
