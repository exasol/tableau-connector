#!/usr/bin/env bash
set -euo pipefail

project_dir="$( cd "$(dirname "$0")/.." >/dev/null 2>&1 ; pwd -P )"
readonly project_dir
target_dir="$project_dir/target/"
readonly target_dir

skip_generate=false

run_tests () {
    type="$1"
    echo "Running $type tests"

    cd "$project_dir/tdvt_$type"
    echo "Cleanup results in $(pwd)"
    rm -vf tabquery_logs.zip tdvt_actuals_combined.zip tdvt_output_combined.json test_results_combined.csv

    if [ "$skip_generate" == "true" ] ; then
        echo "Starting tests without --generate..."
        python -m tdvt.tdvt run "exasol_$type"
    else
        echo "Starting tests..."
        python -m tdvt.tdvt run "exasol_$type" --generate
    fi

    test_results_dir="$target_dir/tdvt_results_$type"
    rm -rf "$test_results_dir"
    echo "Copy test results to $test_results_dir"
    mkdir -vp "$test_results_dir"
    cp -v tabquery_logs.zip tdvt_actuals_combined.zip tdvt_output_combined.json test_results_combined.csv "$test_results_dir"
}

test_type=${1-}

if [[ -z "${test_type}" ]] ; then
    echo "Running all tests"
    run_tests jdbc
    run_tests odbc
else
    run_tests $test_type
fi
