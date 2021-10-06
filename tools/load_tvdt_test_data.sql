DROP USER IF EXISTS "TABLEAU_TEST_USER";
CREATE USER "TABLEAU_TEST_USER" IDENTIFIED BY "TABLEAU_TEST_PASSWORD";
GRANT CREATE SESSION TO "TABLEAU_TEST_USER";

DROP SCHEMA IF EXISTS "TESTV1";
CREATE SCHEMA "TESTV1";
ALTER SCHEMA "TESTV1" CHANGE OWNER "TABLEAU_TEST_USER";
OPEN SCHEMA "TESTV1";

-- The following DLL scripts in this setup script are taken from the Tableau test
-- datasets, published on GitHub by Tableau under the MIT license.
-- https://github.com/tableau/connector-plugin-sdk/tree/master/tests/datasets/TestV1 

CREATE TABLE "Batters"
(
    "Player"  VARCHAR(101),
    "Team"    VARCHAR(50),
    "League"  VARCHAR(2),
    "Year"    SMALLINT,
    "Games"   DOUBLE PRECISION,
    "AB"      DOUBLE PRECISION,
    "R"       DOUBLE PRECISION,
    "H"       DOUBLE PRECISION,
    "Doubles" DOUBLE PRECISION,
    "Triples" DOUBLE PRECISION,
    "HR"      DOUBLE PRECISION,
    "RBI"     DOUBLE PRECISION,
    "SB"      DOUBLE PRECISION,
    "CS"      DOUBLE PRECISION,
    "BB"      DOUBLE PRECISION,
    "SO"      DOUBLE PRECISION,
    "IBB"     DOUBLE PRECISION,
    "HBP"     DOUBLE PRECISION,
    "SH"      DOUBLE PRECISION,
    "SF"      DOUBLE PRECISION, 
    "GIDP"    DOUBLE PRECISION
);

CREATE TABLE "Calcs"
(
    "key"       VARCHAR(255),
    "num0"      DOUBLE PRECISION,
    "num1"      DOUBLE PRECISION,
    "num2"      DOUBLE PRECISION,
    "num3"      DOUBLE PRECISION,
    "num4"      DOUBLE PRECISION,
    "str0"      VARCHAR(255),
    "str1"      VARCHAR(255),
    "str2"      VARCHAR(255),
    "str3"      VARCHAR(255),
    "int0"      INTEGER,
    "int1"      INTEGER,
    "int2"      INTEGER,
    "int3"      INTEGER,
    "bool0"     BOOLEAN,
    "bool1"     BOOLEAN,
    "bool2"     BOOLEAN,
    "bool3"     BOOLEAN,
    "date0"     DATE,
    "date1"     DATE,
    "date2"     DATE,
    "date3"     DATE,
    "time0"     TIMESTAMP,
    "time1"     VARCHAR(20), -- originally type TIME, but not supported by EXASOL
    "datetime0" TIMESTAMP,
    "datetime1" VARCHAR(255),
    "zzz"      VARCHAR(255)
);

CREATE TABLE "Staples"
(
    "Item Count"          INTEGER NOT NULL,
    "Ship Priority"       VARCHAR(14) NOT NULL,
    "Order Priority"      VARCHAR(15) NOT NULL,
    "Order Status"        VARCHAR(13) NOT NULL,
    "Order Quantity"      DOUBLE PRECISION NOT NULL,
    "Sales Total"         DOUBLE PRECISION NOT NULL,
    "Discount"            DOUBLE PRECISION NOT NULL,
    "Tax Rate"            DOUBLE PRECISION NOT NULL,
    "Ship Mode"           VARCHAR(25) NOT NULL,
    "Fill Time"           DOUBLE PRECISION NOT NULL,
    "Gross Profit"        DOUBLE PRECISION NOT NULL,
    "Price"               NUMERIC(18,4) NOT NULL,
    "Ship Handle Cost"    NUMERIC(18,4) NOT NULL,
    "Employee Name"       VARCHAR(50) NOT NULL,
    "Employee Dept"       VARCHAR(4) NOT NULL,
    "Manager Name"        VARCHAR(255) NOT NULL,
    "Employee Yrs Exp"    DOUBLE PRECISION NOT NULL,
    "Employee Salary"     NUMERIC(18,4) NOT NULL,
    "Customer Name"       VARCHAR(50) NOT NULL,
    "Customer State"      VARCHAR(50) NOT NULL,
    "Call Center Region"  VARCHAR(25) NOT NULL,
    "Customer Balance"    DOUBLE PRECISION NOT NULL,
    "Customer Segment"    VARCHAR(25) NOT NULL,
    "Prod Type1"          VARCHAR(50) NOT NULL,
    "Prod Type2"          VARCHAR(50) NOT NULL,
    "Prod Type3"          VARCHAR(50) NOT NULL,
    "Prod Type4"          VARCHAR(50) NOT NULL,
    "Product Name"        VARCHAR(100) NOT NULL,
    "Product Container"   VARCHAR(25) NOT NULL,
    "Ship Promo"          VARCHAR(25) NOT NULL,
    "Supplier Name"       VARCHAR(25) NOT NULL,
    "Supplier Balance"    DOUBLE PRECISION NOT NULL,
    "Supplier Region"     VARCHAR(25) NOT NULL,
    "Supplier State"      VARCHAR(50) NOT NULL,
    "Order ID"            VARCHAR(10) NOT NULL,
    "Order Year"          INTEGER NOT NULL,
    "Order Month"         INTEGER NOT NULL,
    "Order Day"           INTEGER NOT NULL,
    "Order Date"          TIMESTAMP NOT NULL,
    "Order Quarter"       VARCHAR(2) NOT NULL,
    "Product Base Margin" DOUBLE PRECISION NOT NULL,
    "Product ID"          VARCHAR(5) NOT NULL,
    "Receive Time"        DOUBLE PRECISION NOT NULL,
    "Received Date"       TIMESTAMP NOT NULL,
    "Ship Date"           TIMESTAMP NOT NULL,
    "Ship Charge"         NUMERIC(18,4) NOT NULL,
    "Total Cycle Time"    DOUBLE PRECISION NOT NULL,
    "Product In Stock"    VARCHAR(3) NOT NULL,
    "PID"                 INTEGER NOT NULL,
    "Market Segment"      VARCHAR(25) NOT NULL
);

--- The following statements import the CSV test data provided by Tableau on
--- GitHub. Also under MIT license.

IMPORT INTO "Batters"
FROM CSV 
AT 'https://github.com/tableau/connector-plugin-sdk/blob/master/tests/datasets/TestV1/'
FILE 'Batters.csv?raw=true'
NULL = 'NULL';

IMPORT INTO "Calcs"
FROM CSV 
AT 'https://github.com/tableau/connector-plugin-sdk/blob/master/tests/datasets/TestV1/'
FILE 'Calcs.csv?raw=true'
NULL = 'NULL';

IMPORT INTO "Staples"
FROM CSV 
AT 'https://github.com/tableau/connector-plugin-sdk/blob/master/tests/datasets/TestV1/'
FILE 'Staples_utf8.csv?raw=true'
NULL = 'NULL';
