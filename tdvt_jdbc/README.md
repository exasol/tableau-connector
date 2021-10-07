# Running tests for the Exasol JDBC connector

## Setup

* Create a DB user:

    ```sql
    create user TABLEAU_TEST_USER identified by "TABLEAU_TEST_PASSWORD";
    grant create session to TABLEAU_TEST_USER;
    grant select on schema testv1 to TABLEAU_TEST_USER;
    ```

* Install tdvt


## Run tests

```bash
cd tdvt_jdbc
python -m tdvt.tdvt run exasol_jdbc --generate --threads 1
python -m tdvt.tdvt run exasol_jdbc --threads 1
```
