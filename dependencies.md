
# Dependencies

## Runtime Dependencies

To use the Exasol Tableau Connector, you need [Tableau Desktop](https://www.tableau.com/products/desktop) or [Tableau Server](https://www.tableau.com/products/server), both in version 2021.1 or later. See the respective websites for transitive dependencies.

## Build Time Dependencies

Building the Exasol Tableau Connector using [package_connector.sh](./tools/package_connector.sh) has the following dependencies:

* Bash: [GNU General Public License 3.0][GPL3]
* Python 3: [PSF License](https://docs.python.org/3/license.html#psf-license-agreement-for-python-release)
* The [connector-packager](https://github.com/tableau/connector-plugin-sdk/tree/master/connector-packager) provided by Tableau. See the [repository](https://github.com/tableau/connector-plugin-sdk) for transitive dependencies. License: [MIT](https://github.com/tableau/connector-plugin-sdk/blob/master/connector-packager/LICENSE)

Signing the Exasol Tableau Connector using [sign_connector.sh](./tools/sign_connector.sh) has the following dependencies:

* Bash: [GNU General Public License 3.0][GPL3]
* `jarsigner` from a Java Development Kit, e.g. [Adoptium](https://adoptium.net/). License of Adoptium: [Eclipse Public License - v 2.0][EPL2]

## Test Dependencies

### Tableau Connector TDVT Tests

Running the TDVT tests for the connectors has the following dependencies:

* Python 3.9.0 or later: [PSF License](https://docs.python.org/3/license.html#psf-license-agreement-for-python-release)
* [TDVT Test framework](https://github.com/tableau/connector-plugin-sdk/tree/master/tdvt) 2.6.1 or later. See the [repository](https://github.com/tableau/connector-plugin-sdk) for transitive dependencies. License: [MIT](https://github.com/tableau/connector-plugin-sdk/blob/master/tdvt/LICENSE.txt)

### Tableau Connector JavaScript Tests

Running the tests located in the [javascript-test](./javascript-test/) folder has the following dependencies:

* Node.js 16 or later: [Node.js License](https://raw.githubusercontent.com/nodejs/node/master/LICENSE)
* Npm: [The Artistic License 2.0](https://github.com/npm/cli/blob/latest/LICENSE)
* jest: [MIT](https://github.com/facebook/jest/blob/main/LICENSE)

See [package.json](./javascript-test/package.json) for detailed version numbers.

### Common Dependencies for Maven Test Modules

Maven Test Modules [tableau-server-GUI-tests](./tableau-server-GUI-tests/) and [jdbc-kerberos-setup-test](./jdbc-kerberos-setup-test/) have the following common dependencies:

* Apache Maven 3.6.3 or later: [Apache License Version 2.0][Apache2]
* Plugins:
    * `org.apache.maven.plugins:maven-compiler-plugin`: [Apache License Version 2.0][Apache2]
    * `org.apache.maven.plugins:maven-surefire-plugin`: [Apache License Version 2.0][Apache2]
    * `org.apache.maven.plugins:maven-failsafe-plugin`: [Apache License Version 2.0][Apache2]
    * `org.apache.maven.pluginsmaven-enforcer-plugin`: [Apache License Version 2.0][Apache2]
    * `org.codehaus.mojo:versions-maven-plugin`: [Apache License Version 2.0][Apache2]

See [pom.xml](./pom.xml) for detailed version numbers.

### Tableau Server GUI Tests

Running the UI tests located in the [tableau-server-GUI-tests](./tableau-server-GUI-tests/) folder has the following dependencies:

* Test compile dependencies:
    * `org.seleniumhq.selenium:selenium-java`: [Apache License Version 2.0](https://github.com/SeleniumHQ/selenium/blob/trunk/LICENSE)
    * `org.hamcrest:hamcrest`: [BSD License 3][BSD3]
    * `org.junit.jupiter:junit-jupiter`: [Eclipse Public License v2.0][EPL2]
    * `io.github.bonigarcia:webdrivermanager`: [Apache License 2.0](https://github.com/bonigarcia/webdrivermanager/blob/master/LICENSE)
    * `org.mockito:mockito-junit-jupiter`: [MIT](https://github.com/mockito/mockito/blob/main/LICENSE)
    * `org.json:json`: [The JSON License](https://json.org/license.html) (The Software shall be used for Good, not Evil.)
    * `org.testcontainers:junit-jupiter`: [MIT](https://github.com/testcontainers/testcontainers-java/blob/master/LICENSE)
    * `com.exasol:exasol-testcontainers`: [MIT](https://github.com/exasol/exasol-testcontainers/blob/main/LICENSE)
    * `com.exasol:test-db-builder-java`: [MIT](https://github.com/exasol/test-db-builder-java/blob/main/LICENSE)
* Plugins
    * `org.codehaus.mojo:exec-maven-plugin`: [Apache License 2](https://www.mojohaus.org/exec-maven-plugin/license.html)

See files [pom.xml](./pom.xml) and [tableau-server-GUI-tests/pom.xml](./tableau-server-GUI-tests/pom.xml) for detailed version numbers.

### JDBC Kerberos Test Setup

Running the JDBC Kerberos setup tests located in [jdbc-kerberos-setup-test](./jdbc-kerberos-setup-test/) has the following dependencies:

* Test compile dependencies:
    * `org.hamcrest:hamcrest`: [BSD License 3][BSD3]
    * `org.junit.jupiter:junit-jupiter`: [Eclipse Public License v2.0][EPL2]
    * `com.exasol:exasol-jdbc`: [EXAClient License](https://docs.exasol.com/connect_exasol/drivers/jdbc.htm)

See files [pom.xml](./pom.xml) and [jdbc-kerberos-setup-test/pom.xml](./jdbc-kerberos-setup-test/pom.xml) for detailed version numbers.

[Apache2]: https://www.apache.org/licenses/LICENSE-2.0
[BSD3]: http://opensource.org/licenses/BSD-3-Clause
[EPL2]: https://www.eclipse.org/legal/epl-2.0/
[GPL3]: https://www.gnu.org/licenses/gpl-3.0