package com.exasol.tableau;

class TableauServerConfiguration {
//    static final String DOCKER_IP_ADDRESS = "172.17.0.1";
    static final int PORT = 8080;
    static final String TABLEAU_SERVER_DOCKER_IMAGE = "tableau_odbc";// "tableau_server_image:20211.21.0208.1940";
    static final String TABLEAU_USERNAME = System.getenv("TABLEAU_USERNAME");
    static final String TABLEAU_PASSWORD = System.getenv("TABLEAU_PASSWORD");
    static final String TABLEAU_LICENSE_KEY = System.getenv("TABLEAU_LICENSE_KEY");
//    static final String EXASOL_HOSTNAME = DOCKER_IP_ADDRESS;
    static final String EXASOL_USERNAME = System.getenv("EXASOL_USERNAME");
    static final String EXASOL_PASSWORD = System.getenv("EXASOL_PASSWORD");
}
