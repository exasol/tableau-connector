#!/usr/bin/env bash

#for executing this script, run `./execute_script_remotely.sh <user> <host> <bash_script>`

execute_set_up_script_on_remote_server() {
    echo "# Executing script in remote server"
    ssh $1@$2 'bash -s' < $3
}

execute_set_up_script_on_remote_server $1 $2 $3