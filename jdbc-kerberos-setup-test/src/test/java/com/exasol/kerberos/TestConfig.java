package com.exasol.kerberos;

import java.io.*;
import java.nio.file.*;
import java.util.Optional;
import java.util.Properties;

public class TestConfig {
    private final Properties properties;

    private TestConfig(final Properties properties) {
        this.properties = properties;
    }

    public static TestConfig load() {
        return new TestConfig(loadConfig(Paths.get("test.properties")));
    }

    private static Properties loadConfig(final Path path) {
        final Properties properties = new Properties();
        try (InputStream stream = Files.newInputStream(path)) {
            properties.load(stream);
        } catch (final IOException e) {
            throw new UncheckedIOException("Error loading config from " + path, e);
        }
        return properties;
    }

    public String getJdbcUrl() {
        return "jdbc:exa:" + getDbHostName()
                + ":" + getDbPort()
                + ";fingerprint=" + getDbCertificateFingerPrint()
                + ";kerberosservicename=exasol;kerberoshostname=" + getDbHostName() + ";debug=1;logdir="
                + getLogDir().toString();
    }

    private String getDbCertificateFingerPrint() {
        return getMandatoryProperty("db_certificate_fingerprint");
    }

    private String getDbPort() {
        return getMandatoryProperty("db_port");
    }

    private String getDbHostName() {
        return getMandatoryProperty("db_hostname");
    }

    public String getImpersonatedUser() {
        return getMandatoryProperty("impersonated_user");
    }

    public Optional<String> getImpersonatedUserDbName() {
        return getProperty("impersonated_user_db_name");
    }

    public Optional<String> getRunAsUser() {
        return getProperty("runas_user");
    }

    public Optional<Path> getKerberosConfigFile() {
        return getProperty("kerberos_config_file").map(Paths::get);
    }

    public Optional<Path> getKeytabFile() {
        return getProperty("keytab_file").map(Paths::get);
    }

    public Optional<Path> getJaasFile() {
        return getProperty("jaas_file").map(Paths::get);
    }

    public Path getGssConfigFile() {
        return getProperty("gss_config_file").map(Paths::get).get();
    }

    public Path getLogDir() {
        return Paths.get("logs").toAbsolutePath();
    }

    private String getMandatoryProperty(final String key) {
        return getProperty(key)
                .orElseThrow(() -> new IllegalStateException("Missing property '" + key + "' in test.properties"));
    }

    private Optional<String> getProperty(final String key) {
        return Optional.ofNullable((String) this.properties.get(key));
    }

}