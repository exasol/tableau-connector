package com.exasol.tableau;

import java.io.*;
import java.util.Properties;

class TableauServerConfiguration {
    static final int TABLEAU_PORT = 8080;
    static final int TABLEAU_MAPPED_PORT = 33445;
    static final int EXASOL_PORT = 8563;
    static final int EXASOL_MAPPED_PORT = 33446;
    static final String TABLEAU_SERVER_DOCKER_IMAGE = "tablau_server_with_exasol_drivers:latest";

    private static final Properties PROPERTIES = readProperties("/credentials.properties");

    static final String TABLEAU_USERNAME = PROPERTIES.getProperty("TABLEAU_USERNAME");
    static final String TABLEAU_PASSWORD = PROPERTIES.getProperty("TABLEAU_PASSWORD");
    static final String TABLEAU_LICENSE_KEY = PROPERTIES.getProperty("TABLEAU_LICENSE_KEY");

    private static Properties readProperties(final String resource) {
        final Properties properties = new Properties();
        try (InputStream stream = TableauServerConfiguration.class.getResourceAsStream(resource)) {
            if (stream == null) {
                throw new AssertionError("Resource '" + resource + "' not found on classpath");
            }
            properties.load(stream);
        } catch (final IOException exception) {
            throw new UncheckedIOException("Error reading resource " + resource + " from classpath", exception);
        }
        return properties;
    }

}
