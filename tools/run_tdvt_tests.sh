#!/usr/bin/env bash
set -euo pipefail

project_dir="$( cd "$(dirname "$0")/.." >/dev/null 2>&1 ; pwd -P )"
readonly project_dir
readonly target_dir="$project_dir/target"
readonly venv_dir="$target_dir/tdvt-venv"

skip_generate=false

verify_no_connector_installed() {
    readonly connector_dir=$(cygpath "C:\Program Files\Tableau\Connectors")
    echo "$connector_dir"
    if [ "$(ls -A "$connector_dir")" ]; then
        echo "ERROR: Connectors are installed in $connector_dir that might override code under test: $(ls -A "$connector_dir")"
        echo "ERROR: Please delete all files in $connector_dir"
        exit 1
    fi
}

activate_venv() {
    if [ ! -d "$venv_dir" ]; then
        echo "ERROR: Python venv not found at $venv_dir. Please run tools\setup_tdvt.sh"
        exit 1
    fi
    echo "Activating venv at $venv_dir"
    source "$venv_dir/Scripts/activate"
}

get_version() {
    type="$1"
    grep "plugin-version" < "$project_dir/src/exasol_$type/manifest.xml" | sed 's/^.*plugin-version="\([^"]*\)".*$/\1/'
}

run_tests () {
    type="$1"
    echo "Running $type tests"

    cd "$project_dir/tdvt_$type"
    echo "Cleanup results in $(pwd)"
    rm -f tabquery_logs.zip tdvt_actuals_combined.zip tdvt_output_combined.json test_results_combined.csv tdvt.log_combined.txt

    if [ "$skip_generate" == "true" ] ; then
        echo "Starting tests without --generate..."
        python -m tdvt.tdvt run "exasol_$type"
    else
        echo "Starting tests..."
        python -m tdvt.tdvt run "exasol_$type" --generate
    fi

    version=$(get_version "$type")
    test_results_dir_name="tdvt_results_${type}_${version}"
    test_results_archive="$target_dir/${test_results_dir_name}.tar.gz"
    test_results_dir="$target_dir/$test_results_dir_name"
    rm -rf "$test_results_dir"
    rm -f "$test_results_archive"
    echo "Copy test results to $test_results_dir"
    mkdir -p "$test_results_dir"
    cp tabquery_logs.zip tdvt_actuals_combined.zip tdvt_output_combined.json test_results_combined.csv "$test_results_dir"
    cd "$test_results_dir/.."
    tar -czf "${test_results_archive}" "$test_results_dir_name"
    echo "Created test result archive $test_results_archive"
}

verify_no_connector_installed

activate_venv

test_type=${1-}

if [[ -z "${test_type}" ]] ; then
    echo "Running all tests"
    run_tests jdbc
    run_tests odbc
else
    run_tests "$test_type"
fi
