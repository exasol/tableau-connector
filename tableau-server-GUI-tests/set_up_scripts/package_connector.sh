#!/usr/bin/env bash
set -euxo pipefail

set_up_environment () {
    clone_tableau_connector_plugin_sdk_repository
    change_to_connector_packager_directory
    create_virtual_environment
    activate_virtual_environment
    install_packaging_module
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
    echo "# NOTE: Make sure 'python3-venv' is installed ('sudo apt-get install python3-venv' in Ubuntu)"
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

package_connectors () {
    #the connector is created in ./packaged-connector/exasol_odbc.taco
    echo "# Packaging odbc connector"
    python -m connector_packager.package ../../../src/exasol_odbc/
    echo "# Packaging jdbc connector"
    python -m connector_packager.package ../../../src/exasol_jdbc/
}

copy_packaged_connectors_to_target_folder () {
    echo "# Copying packaged odbc connector to target folder"
    cp ./packaged-connector/exasol_odbc.taco ../../target/
    echo "# Copying packaged jdbc connector to target folder"
    cp ./packaged-connector/exasol_jdbc.taco ../../target/
}

clean () {
    cd ../..
    remove_tableau_connector_plugin_sdk_directory
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
package_connectors
copy_packaged_connectors_to_target_folder
clean
