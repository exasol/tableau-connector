#!/usr/bin/env bash
set -euxo pipefail

project_dir="$( cd "$(dirname "$0")/.." >/dev/null 2>&1 ; pwd -P )"
readonly project_dir
target_dir="$project_dir/target/"
readonly target_dir

skip_generate=false

run_tests () {
    type="$1"
    cd "$project_dir/tdvt_$type"
    rm -vf tabquery_logs.zip tdvt_actuals_combined.zip tdvt_output_combined.json test_results_combined.csv

    if [ "$skip_generate" == "true" ] ; then
        echo "Skipping --generate"
        python -m tdvt.tdvt run "exasol_$type"
    else
        python -m tdvt.tdvt run "exasol_$type" --generate
    fi

    test_results_dir="$target_dir/tdvt_results_$type"
    rm -rf "$test_results_dir"
    mkdir -vp "$test_results_dir"
    cp -v tabquery_logs.zip tdvt_actuals_combined.zip tdvt_output_combined.json test_results_combined.csv "$test_results_dir"
}

run_tests jdbc
run_tests odbc