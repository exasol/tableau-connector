const { defineGlobalObjects, evalFile, createDefaultJdbcAttr } = require("./common");

defineGlobalObjects();

const dsbuilder = evalFile("../src/exasol_jdbc/connectionBuilder.js");

function getJdbcUrl(attr) {
    const result = dsbuilder(createDefaultJdbcAttr(attr));
    expect(result).toHaveLength(1);
    return result[0];
}

test('default url', () => {
    expect(getJdbcUrl({})).toEqual("jdbc:exa:exasoldb.example.com:8563;validateservercertificate=1;fingerprint=15F9CA9");
});

test('missing port', () => {
    expect(getJdbcUrl({port: undefined})).toEqual("jdbc:exa:exasoldb.example.com;validateservercertificate=1;fingerprint=15F9CA9");
});

test('do not validate certificate', () => {
    expect(getJdbcUrl({'v-validateservercertificate': 0})).toEqual("jdbc:exa:exasoldb.example.com:8563;validateservercertificate=0;fingerprint=15F9CA9");
});

test('no fingerprint', () => {
    expect(getJdbcUrl({'v-fingerprint': undefined})).toEqual("jdbc:exa:exasoldb.example.com:8563;validateservercertificate=1");
});

test('empty fingerprint', () => {
    expect(getJdbcUrl({'v-fingerprint': ""})).toEqual("jdbc:exa:exasoldb.example.com:8563;validateservercertificate=1");
});

test('blank fingerprint', () => {
    expect(getJdbcUrl({'v-fingerprint': " "})).toEqual("jdbc:exa:exasoldb.example.com:8563;validateservercertificate=1");
});

test('fingerprint is trimmed', () => {
    expect(getJdbcUrl({'v-fingerprint': " abc "})).toEqual("jdbc:exa:exasoldb.example.com:8563;validateservercertificate=1;fingerprint=abc");
});

test('custom hostname', () => {
    expect(getJdbcUrl({server: "db"})).toEqual("jdbc:exa:db:8563;validateservercertificate=1;fingerprint=15F9CA9");
});

test('no kerberos', () => {
    expect(getJdbcUrl({authentication: 'auth-user-pass'})).toEqual("jdbc:exa:exasoldb.example.com:8563;validateservercertificate=1;fingerprint=15F9CA9");
});

test('running on Server', () => {
    expect(getJdbcUrl({':tableau-server-user': 'user'})).toEqual("jdbc:exa:exasoldb.example.com:8563;validateservercertificate=1;fingerprint=15F9CA9");
});
