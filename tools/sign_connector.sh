#!/usr/bin/env bash
set -euo pipefail

project_version="0.4.2"

project_dir="$( cd "$(dirname "$0")/.." >/dev/null 2>&1 ; pwd -P )"
target_dir="$project_dir/target"

timestamp_authority_server="http://ts.ssl.com"
key_alias="1"

if [[ -z "${1+x}" ]] ; then
    echo "Path to keystore not specifified. Usage:"
    echo "  $0 </path/to/keystore>"
    exit 1
fi

keystore="$1"

if [[ ! -f "$keystore" ]] ; then
    echo "Keystore file does not exist: $keystore"
    exit 1
fi

echo "Signing JDBC and ODBC connectors using keystore $keystore"
echo -n "Enter password for keystore:"
read -rs storepass
echo

verify_signature() {
    signed_jar="$1"
    echo "Verifying connector $signed_jar"
    result=$(jarsigner -verify "$signed_jar" | xargs)

    if [[ "$result" == "jar verified." ]] ; then
        echo "Signature is valid, result: $result"
    else
        echo "Signature of $signed_jar is not valid, result: '$result'"
        jarsigner -verify "$signed_jar" -verbose
        exit 1
    fi
}

sign_jar() {
    jar_file="$1"
    signed_jar="$2"

    echo "Signing connector $jar_file"
    jarsigner "$jar_file" $key_alias \
      -keystore "$keystore" -storepass "$storepass" \
      -signedjar "$signed_jar" \
      -tsa "$timestamp_authority_server" \
      -strict

      verify_signature "$signed_jar"
}

sign_jar "$target_dir/exasol_jdbc.taco" "$target_dir/tableau-exasol-connector-jdbc-$project_version.taco"
sign_jar "$target_dir/exasol_odbc.taco" "$target_dir/tableau-exasol-connector-odbc-$project_version.taco"
