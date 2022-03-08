
# Dependencies

## Runtime Dependencies

To use the Exasol Tableau Connector, you need [Tableau Desktop](https://www.tableau.com/products/desktop) or [Tableau Server](https://www.tableau.com/products/server), both in version 2021.1 or later. See the respective websites for transitive dependencies.

## Build Time Dependencies

Building the Exasol Tableau Connector using [package_connector.sh](./tools/package_connector.sh) has the following dependencies:

* Bash
* Python 3
* The [connector-packager](https://github.com/tableau/connector-plugin-sdk/tree/master/connector-packager) provided by Tableau. See the [repository](https://github.com/tableau/connector-plugin-sdk) for transitive dependencies.

Signing the Exasol Tableau Connector using [sign_connector.sh](./tools/sign_connector.sh) has the following dependencies:

* Bash
* `jarsigner` from a Java Development Kit, e.g. [Adoptium](https://adoptium.net/)

## Test Dependencies

### Tableau Connector TDVT Tests

Running the TDVT tests for the connectors has the following dependencies:

* Python 3.9.0 or later
* [TDVT Test framework](https://github.com/tableau/connector-plugin-sdk/tree/master/tdvt) 2.6.1 or later. See the [repository](https://github.com/tableau/connector-plugin-sdk) for transitive dependencies.

### Tableau Connector JavaScript Tests

Running the tests located in the [javascript-test](./javascript-test/) folder has the following dependencies:

* Node.js 16 or later
* Npm
* jest

See [package.json](./javascript-test/package.json) for detailed version numbers.

### Common Dependencies for Maven Test Modules

Maven Test Modules [tableau-server-GUI-tests](./tableau-server-GUI-tests/) and [jdbc-kerberos-setup-test](./jdbc-kerberos-setup-test/) have the following common dependencies:

* Apache Maven 3.6.3 or later
* Plugins:
    * `org.apache.maven.plugins:maven-compiler-plugin`
    * `org.apache.maven.plugins:maven-surefire-plugin`
    * `org.apache.maven.plugins:maven-failsafe-plugin`
    * `org.codehaus.mojo:versions-maven-plugin`
    * `org.apache.maven.pluginsmaven-enforcer-plugin`

See [pom.xml](./pom.xml) for detailed version numbers.

### Tableau Server GUI Tests

Running the UI tests located in the [tableau-server-GUI-tests](./tableau-server-GUI-tests/) folder has the following dependencies:

* Test compile dependencies:
    * `org.seleniumhq.selenium:selenium-java`
    * `org.hamcrest:hamcrest`
    * `org.junit.jupiter:junit-jupiter`
    * `io.github.bonigarcia:webdrivermanager`
    * `org.mockito:mockito-junit-jupiter`
    * `org.json:json`
    * `org.testcontainers:junit-jupiter`
    * `com.exasol:exasol-testcontainers`
    * `com.exasol:test-db-builder-java`
* Plugins
    * `org.codehaus.mojo:exec-maven-plugin`

See files [pom.xml](./pom.xml) and [tableau-server-GUI-tests/pom.xml](./tableau-server-GUI-tests/pom.xml) for detailed version numbers.

### JDBC Kerberos Test Setup

Running the JDBC Kerberos setup tests located in [jdbc-kerberos-setup-test](./jdbc-kerberos-setup-test/) has the following dependencies:

* Test compile dependencies:
    * `org.hamcrest:hamcrest`
    * `org.junit.jupiter:junit-jupiter`
    * `com.exasol:exasol-jdbc`

See files [pom.xml](./pom.xml) and [jdbc-kerberos-setup-test/pom.xml](./jdbc-kerberos-setup-test/pom.xml) for detailed version numbers.
