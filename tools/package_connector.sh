#!/usr/bin/env bash
set -euo pipefail

project_dir="$( cd "$(dirname "$0")/.." >/dev/null 2>&1 ; pwd -P )"
readonly project_dir
readonly target_dir="$project_dir/target/"
readonly sdk_dir="$target_dir/connector-plugin-sdk/"
readonly venv_dir="$sdk_dir/connector-packager/.venv/"
readonly packager_dir="$sdk_dir/connector-packager/"


set_up_environment () {
    clone_tableau_connector_plugin_sdk_repository
    change_to_connector_packager_directory
    create_virtual_environment
    activate_virtual_environment
    install_setuptools
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
    echo "# Changing to Connector Packager directory $packager_dir"
    cd "$packager_dir"
}

create_virtual_environment () {
    echo "# Creating virtual environment at $venv_dir"
    echo "# NOTE: Make sure 'python3-venv' is installed ('sudo apt-get install python3-venv' in Ubuntu)"
    if hash python3 2>/dev/null; then
        python3 -m venv "$venv_dir"
    else
        python -m venv "$venv_dir"
    fi
}

activate_virtual_environment () {
    if [ -d "$venv_dir/Scripts" ]; then
        activate_script="$venv_dir/Scripts/activate"
    else
        activate_script="$venv_dir/bin/activate"
    fi
    echo "# Activating virtual environment using $activate_script"
    # shellcheck source=/dev/null # file only exists at runtime
    source "$activate_script"
}

install_setuptools () {
    echo "# Installing Python setuptools..."
    pip install --upgrade pip setuptools
}

install_packaging_module () {
    echo "# Installing packaging module"
    python setup.py install
}

package_connector () {
    local -r type=$1
    echo "# Packaging $type connector"
    python -m connector_packager.package --verbose --dest "$target_dir" --log "$target_dir/logs_$type" "$project_dir/src/exasol_$type/"

    files=( "$target_dir"/exasol_"$type"-v*.taco )
    if [ "${#files[@]}" -ge 2 ]
    then
        echo "ERROR: Found more than one file when building $type connector:" "${files[@]}"
        exit 1
    elif [ ! -e "${files[0]}" ]
    then
        echo "ERROR: Packaging $type connector failed, .taco file not found in $target_dir. See log output above."
        exit 1
    else
        local -r file="${files[0]}"
        local -r new_name="exasol_$type.taco"
        echo "Successfully created $type connector: $file, rename to $new_name"
        mv -v "$file" "$target_dir/$new_name"
    fi
}

set_up_environment
package_connector odbc
package_connector jdbc
