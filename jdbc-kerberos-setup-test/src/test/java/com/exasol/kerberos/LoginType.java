package com.exasol.kerberos;

public enum LoginType {
    SSPI(1), GSSAPI(2);

    private final int code;

    private LoginType(final int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
}