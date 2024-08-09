#!/usr/bin/env bash
set -euo pipefail

project_dir="$( cd "$(dirname "$0")/.." >/dev/null 2>&1 ; pwd -P )"
readonly project_dir
readonly target_dir="$project_dir/target"

readonly sdk_dir="$target_dir/connector-plugin-sdk"
readonly venv_dir="$target_dir/tdvt-venv"

if [ ! -d "$sdk_dir" ]; then
    echo "Cloning Tableau Connector Plugin SDK repository to $sdk_dir..."
    mkdir -p "$sdk_dir"
    git clone https://github.com/tableau/connector-plugin-sdk.git "$sdk_dir"
else
    echo "Tableau Connector Plugin SDK already exists at $sdk_dir, no need to clone it."
fi

echo "Deleting venv at $venv_dir..."
rm -rf "$venv_dir"

echo "Creating venv at $venv_dir..."
python -m venv "$venv_dir"
source "$venv_dir/Scripts/activate"

echo "Upgrading pip..."
python -m pip install --upgrade pip

echo "Install setuptools"
python -m pip install setuptools

echo "Installing TDVT..."
cd "$sdk_dir/tdvt"
python -m pip install -e .
python -m pip list
