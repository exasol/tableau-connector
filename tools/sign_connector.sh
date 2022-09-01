#!/usr/bin/env bash
set -euo pipefail

project_dir="$( cd "$(dirname "$0")/.." >/dev/null 2>&1 ; pwd -P )"
target_dir="$project_dir/target"

#timestamp_authority_server="http://ts.ssl.com"
timestamp_authority_server="http://timestamp.sectigo.com"
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

storepass=${CODE_SIGNING_CERTIFICATE_PASSWORD-}

if [[ -z "${storepass}" ]] ; then
    echo -n "Enter password for keystore:"
    read -rs storepass
    echo
else
    echo "Using keystore password from environment variable CODE_SIGNING_CERTIFICATE_PASSWORD"
fi

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
    type="$1"
    version=$(get_version "$type")
    jar_file="$target_dir/exasol_$type.taco"
    signed_jar="$target_dir/tableau-exasol-connector-$type-$version.taco"

    echo "Signing connector $jar_file"
    jarsigner "$jar_file" $key_alias \
      -keystore "$keystore" -storepass "$storepass" \
      -signedjar "$signed_jar" \
      -tsa "$timestamp_authority_server" \
      -strict

    verify_signature "$signed_jar"
}

get_version() {
    type="$1"
    grep "plugin-version" < "$project_dir/src/exasol_$type/manifest.xml" | sed 's/^.*plugin-version="\([^"]*\)".*$/\1/'
}

sign_jar jdbc
sign_jar odbc
