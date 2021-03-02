#!/usr/bin/env bash

set_up_environment () {
    clone_tableau_connector_repository
    clone_tableau_connector_plugin_sdk_repository
    change_to_connector_packager_directory
    create_virtual_environment
    activate_virtual_environment
    install_packaging_module
}

clone_tableau_connector_repository () {
    echo "# Cloning Tableau Connector repository"
    git clone https://github.com/exasol/tableau-connector.git
}

clone_tableau_connector_plugin_sdk_repository () {
    echo "# Cloning Tableau Connector Plugin SDK repository"
    git clone https://github.com/tableau/connector-plugin-sdk.git
}

change_to_connector_packager_directory () {
    echo "# Changing to Connector Packager directory"
    cd connector-plugin-sdk/connector-packager/
}

create_virtual_environment () {
    echo "# Creating virtual environment"
    python3 -m venv .venv
}

activate_virtual_environment () {
    echo "# Activating virtual environment"
    source ./.venv/bin/activate
}

install_packaging_module () {
    echo "# Installing packaging module"
    python setup.py install
}

package_connector () {
    #to execute still in the virtual environment(.venv)
    #optional(validates the XML files) -> python -m connector_packager.package --validate-only [path_to_plugin_folder]
    #python -m connector_packager.package --validate-only ../../tableau-connector/src/exasol_odbc/
    #the connector is created in ./packaged-connector/exasol_odbc.taco
    echo "# Packaging connector"
    python -m connector_packager.package ../../tableau-connector/src/exasol_odbc/
}

sign_packaged_connector () {
    echo "# Signing connector"
    #TODO:
}

clean () {
    cd ../..
    remove_tableau_connector_directory
    remove_tableau_connector_plugin_sdk_directory
}

remove_tableau_connector_directory () {
    echo "# Removing Tableau Connector directory"
    remove_directory_if_exists "./tableau-connector"
}

remove_tableau_connector_plugin_sdk_directory () {
    echo "# Removing Tableau Connector Plugin SDK directory"
    remove_directory_if_exists "./connector-plugin-sdk"
}

remove_directory_if_exists () {
    if [ -d "$1" ]; then
        rm -rf $1
    fi
}
    
set_up_environment
package_connector
sign_packaged_connector
clean