package com.exasol.tableau;

class TableauServerConfiguration {
    static final int TABLEAU_PORT = 8080;
    static final int TABLEAU_MAPPED_PORT = 33445;
    static final int EXASOL_PORT = 8563;
    static final int EXASOL_MAPPED_PORT = 33446;
    static final String TABLEAU_SERVER_DOCKER_IMAGE = "tablau_server_with_exasol_drivers:latest";
    static final String TABLEAU_USERNAME = System.getProperty("TABLEAU_USERNAME");
    static final String TABLEAU_PASSWORD = System.getProperty("TABLEAU_PASSWORD");
    static final String TABLEAU_LICENSE_KEY = System.getProperty("TABLEAU_LICENSE_KEY");
}
