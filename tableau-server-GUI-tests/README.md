# Tableau Server GUI Integration Tests

## Run the Tests

To run the test you need to create the `/src/test/resources/credentials.properties` file with the following content (replace the placeholders for real values):

```
TABLEAU_USERNAME=<tableau-server-username>
TABLEAU_PASSWORD=<tableau-server-password>
TABLEAU_LICENSE_KEY=<tableau-server-license-key>
```

## Run the Tests Remotely

Running the Tableau Server GUI tests in your local machine might impractical as you need a running Tableau Server container which might require more resources than your machine can provide.

One solution for this is running the Tableau Server container in a remote(more powerful) machine, but still being able to trigger the tests from your local IDE. For this, you need to connect to spceify the host where the docker daemon is running, as follows:

```
EXPORT DOCKER_HOST=tcp://<localhost>:<port>
```

You also need to disable the Ryuk container, as follows:

```
EXPORT TESTCONTAINERS_RYUK_DISABLED=true
```

Have mind that during the execution of the tests you will need to connect to the Exasol and Tableau Server running containers through the `33445` and `33446` ports, as well to the docker daemon running on the remote machine.

One way to do this is to establish an SSH connection and forward the ports via SSH tunnel to the machine running the docker containers (including the docker daemon port):

`ssh <remote machine address> -L <docker_daemon_mapped_port>:<docker_host>:2375 -L 33445:<docker_host>:33445 -L 33446:<docker_host>:33446`