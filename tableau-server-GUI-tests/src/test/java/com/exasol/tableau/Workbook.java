package com.exasol.tableau;

public class Workbook {
    private final String workbookName;
    private final String connectorName;
    private final String hostname;
    private final String username;
    private final String password;
    private final String port;
    private final String fingerprint;
    private final Boolean validateServerCertificate;
    private final String databaseName;

    private Workbook(final Builder builder) {
        this.workbookName = builder.workbookName;
        this.connectorName = builder.connectorName;
        this.hostname = builder.hostname;
        this.username = builder.username;
        this.password = builder.password;
        this.port = builder.port;
        this.fingerprint = builder.fingerprint;
        this.validateServerCertificate = builder.validateServerCertificate;
        this.databaseName = builder.databaseName;
    }

    public String getWorkbookName() {
        return this.workbookName;
    }

    public String getConnectorName() {
        return this.connectorName;
    }

    public String getHostname() {
        return this.hostname;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getPort() {
        return this.port;
    }

    public String getFingerprint() {
        return this.fingerprint;
    }

    public Boolean getValidateServerCertificate() {
        return this.validateServerCertificate;
    }

    public String getDatabaseName() {
        return this.databaseName;
    }

    public static Builder builder() {
        return new Builder();
    }

    static class Builder {
        private String workbookName;
        private String connectorName;
        private String hostname;
        private String username;
        private String password;
        private String port;
        private String fingerprint = null;
        private Boolean validateServerCertificate = null;
        private String databaseName = null;

        private Builder() {
        }

        public Builder workbookName(final String name) {
            this.workbookName = name;
            return this;
        }

        public Builder connectorName(final String connectorName) {
            this.connectorName = connectorName;
            return this;
        }

        public Builder hostname(final String hostname) {
            this.hostname = hostname;
            return this;
        }

        public Builder username(final String username) {
            this.username = username;
            return this;
        }

        public Builder password(final String password) {
            this.password = password;
            return this;
        }

        public Builder port(final String port) {
            this.port = port;
            return this;
        }

        public Builder fingerprint(final String fingerprint) {
            this.fingerprint = fingerprint;
            return this;
        }

        public Builder validateServerCertificate(final Boolean validateServerCertificate) {
            this.validateServerCertificate = validateServerCertificate;
            return this;
        }

        public Builder databaseName(final String databaseName) {
            this.databaseName = databaseName;
            return this;
        }

        public Workbook build() {
            return new Workbook(this);
        }
    }
}