#!/usr/bin/env bash

cd /opt

echo "# Downloading Exasol ODBC driver"
curl -L -o EXASOL_ODBC.tar.gz https://www.exasol.com/support/secure/attachment/119101/EXASOL_ODBC-7.0.4.tar.gz\

echo "# Extracting Exasol ODBC driver"
mkdir exasol
tar -xzf EXASOL_ODBC.tar.gz -C exasol --strip-components 1

echo >> /etc/odbcinst.ini
echo "[EXASolution Driver]" >> /etc/odbcinst.ini
echo "Driver=/opt/exasol/lib/linux/x86_64/libexaodbc-uo2214lv2.so" >> /etc/odbcinst.ini