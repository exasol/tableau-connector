#!/usr/bin/env bash
set -euo pipefail

project_dir="$( cd "$(dirname "$0")/.." >/dev/null 2>&1 ; pwd -P )"
readonly project_dir
readonly target_dir="$project_dir/target"

readonly sdk_dir="$target_dir/connector-plugin-sdk"
readonly venv_dir="$target_dir/tdvt-venv"

# Check if python or python3 is available
if command -v python3 &>/dev/null; then
    python_cmd="python3"
elif command -v python &>/dev/null; then
    python_cmd="python"
else
    echo "Python is not installed. Please install Python 3"
    exit 1
fi

readonly python_cmd

if [ ! -d "$sdk_dir" ]; then
    echo "Cloning Tableau Connector Plugin SDK repository to $sdk_dir..."
    mkdir -p "$sdk_dir"
    git clone https://github.com/tableau/connector-plugin-sdk.git "$sdk_dir"
else
    echo "Tableau Connector Plugin SDK already exists at $sdk_dir, pull latest changes."
    cd "$sdk_dir"
    git pull
    cd -
fi

echo "Deleting venv at $venv_dir..."
rm -rf "$venv_dir"

echo "Creating venv at $venv_dir..."
$python_cmd -m venv "$venv_dir"

if [ -d "$venv_dir/Scripts" ]; then
    activate_script="$venv_dir/Scripts/activate"
else
    activate_script="$venv_dir/bin/activate"
fi
echo "Activating virtual environment using $activate_script"
# shellcheck source=/dev/null # file only exists at runtime
source "$activate_script"

echo "Upgrading pip..."
$python_cmd -m pip install --upgrade pip

echo "Install setuptools"
$python_cmd -m pip install setuptools

echo "Installing TDVT..."
cd "$sdk_dir/tdvt"
$python_cmd -m pip install -e .
$python_cmd -m pip list
