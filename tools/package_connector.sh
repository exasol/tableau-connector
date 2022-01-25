#!/usr/bin/env bash
set -euxo pipefail

project_dir="$( cd "$(dirname "$0")/.." >/dev/null 2>&1 ; pwd -P )"
readonly project_dir
target_dir="$project_dir/target/"
readonly target_dir
sdk_dir="$target_dir/sdk"
readonly sdk_dir

set_up_environment () {
    clone_tableau_connector_plugin_sdk_repository
    change_to_connector_packager_directory
    create_virtual_environment
    activate_virtual_environment
    install_packaging_module
}

clone_tableau_connector_plugin_sdk_repository () {
    if [ ! -d "$sdk_dir" ]; then
        echo "# Cloning Tableau Connector Plugin SDK repository"
        mkdir -p "$sdk_dir"
        git clone https://github.com/tableau/connector-plugin-sdk.git "$sdk_dir"
    fi
}

change_to_connector_packager_directory () {
    echo "# Changing to Connector Packager directory"
    cd "$sdk_dir/connector-packager/"
}

create_virtual_environment () {
    echo "# Creating virtual environment"
    echo "# NOTE: Make sure 'python3-venv' is installed ('sudo apt-get install python3-venv' in Ubuntu)"
    if hash python3 2>/dev/null; then
        python3 -m venv .venv
    else
        python -m venv .venv
    fi
}

activate_virtual_environment () {
    echo "# Activating virtual environment"
    venv_dir="$sdk_dir/connector-packager/.venv/"
    if [ -d "$venv_dir/Scripts" ]; then
        activate_script="$venv_dir/Scripts/activate"
    else
        activate_script="$venv_dir/bin/activate"
    fi
    # shellcheck source=/dev/null # file only exists at runtime
    source "$activate_script"
}

install_packaging_module () {
    echo "# Installing packaging module"
    python setup.py install
}

package_connectors () {
    #the connector is created in ./packaged-connector/exasol_odbc.taco
    echo "# Packaging odbc connector"
    python -m connector_packager.package "$project_dir/src/exasol_odbc/"
    echo "# Packaging jdbc connector"
    python -m connector_packager.package "$project_dir/src/exasol_jdbc/"
}

copy_packaged_connectors_to_target_folder () {
    echo "# Copying packaged odbc connector to target folder"
    cp ./packaged-connector/exasol_odbc.taco "$target_dir"
    echo "# Copying packaged jdbc connector to target folder"
    cp ./packaged-connector/exasol_jdbc.taco "$target_dir"
}

set_up_environment
package_connectors
copy_packaged_connectors_to_target_folder
