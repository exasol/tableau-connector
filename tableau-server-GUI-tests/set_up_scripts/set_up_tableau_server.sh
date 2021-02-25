#!/usr/bin/env bash

clone_tableau_connector_repository () {
    echo "# Cloning Tableau Connector repository"
    git clone https://github.com/exasol/tableau-connector.git
}

reset_connectors_directory () {
    echo "# Resetting connectors directory"
    remove_directory_if_exists "/var/tmp/plugins"
}

copy_to_connectors_directory () {
    echo "# Copying connector to connectors directory"
    cp -ar tableau-connector/src/exasol_odbc /var/tmp/plugins
}

remove_directory_if_exists () {
    if [ -d "$1" ]; then
        rm -rf $1
    fi
}

set_connectors_directory_path () {
    echo "# Setting connectors directory path"
    tsm configuration set -k native_api.connect_plugins_path -v /var/tmp/plugins --force-keys
}

apply_changes () {
    echo "# Applying changes on Tableau Server"
    tsm pending-changes apply
}

clean () {
    echo "# Removing tableau-connector directory"
    remove_directory_if_exists "./tableau-connector"
}

clone_tableau_connector_repository
reset_connectors_directory
copy_to_connectors_directory
set_connectors_directory_path
apply_changes
clean