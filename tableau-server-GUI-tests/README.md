# Tableau Server Integration Test

## How to Run Locally

To run the test locally you need to specify the following environment variable:

```
TABLEAU_HOSTNAME=http://<host>:33445/
TABLEAU_USERNAME=<username>
TABLEAU_PASSWORD=<password>
TABLEAU_LICENSE_KEY=<license>
EXASOL_USERNAME=username>
EXASOL_PASSWORD=<password>
```

## Hot to Run Remotely

You can also connect to the docker daemon that runs on a remote machine. You need to specify a docker host and also disable the container Ryuk:

```
TESTCONTAINERS_RYUK_DISABLED=true
DOCKER_HOST=tcp://<localhost>:<port>
```

You can establish an SSH connection and forward the port via SSH tunnel:

`ssh <remote machine address> -L 33444:<host>:2375 -L 33445:<host>:33445 -L 33446:<host>:33446`