const { defineGlobalObjects, evalFile ,createDefaultAttr} = require("./common");

defineGlobalObjects({ loggingEnabled: true });

const dsbuilder = evalFile("../src/exasol_jdbc/connectionBuilder.js");

function getJdbcUrl(attr) {
    const result = dsbuilder(createDefaultAttr(attr));
    expect(result).toHaveLength(1);
    return result[0];
}

test('default url', () => {
    expect(getJdbcUrl({})).toEqual("jdbc:exa:exasoldb.example.com:8563;validateservercertificate=1;fingerprint=15F9CA9;feedbackinterval=1;clientname=Tableau Desktop;kerberoshostname=exasoldb.example.com;kerberosservicename=exasol");
});

test('missing port', () => {
    expect(getJdbcUrl({port: undefined})).toEqual("jdbc:exa:exasoldb.example.com;validateservercertificate=1;fingerprint=15F9CA9;feedbackinterval=1;clientname=Tableau Desktop;kerberoshostname=exasoldb.example.com;kerberosservicename=exasol");
});

test('do not validate certificate', () => {
    expect(getJdbcUrl({'v-validateservercertificate': 0})).toEqual("jdbc:exa:exasoldb.example.com:8563;validateservercertificate=0;fingerprint=15F9CA9;feedbackinterval=1;clientname=Tableau Desktop;kerberoshostname=exasoldb.example.com;kerberosservicename=exasol");
});

test('no fingerprint', () => {
    expect(getJdbcUrl({'v-fingerprint': undefined})).toEqual("jdbc:exa:exasoldb.example.com:8563;validateservercertificate=1;feedbackinterval=1;clientname=Tableau Desktop;kerberoshostname=exasoldb.example.com;kerberosservicename=exasol");
});

test('custom hostname', () => {
    expect(getJdbcUrl({server: "db"})).toEqual("jdbc:exa:db:8563;validateservercertificate=1;fingerprint=15F9CA9;feedbackinterval=1;clientname=Tableau Desktop;kerberoshostname=db;kerberosservicename=exasol");
});

test('no kerberos', () => {
    expect(getJdbcUrl({authentication: 'auth-user-pass'})).toEqual("jdbc:exa:exasoldb.example.com:8563;validateservercertificate=1;fingerprint=15F9CA9;feedbackinterval=1;clientname=Tableau Desktop");
});

test('enable debugging', () => {
    expect(getJdbcUrl({'v-debug': true})).toEqual("jdbc:exa:exasoldb.example.com:8563;validateservercertificate=1;fingerprint=15F9CA9;feedbackinterval=1;clientname=Tableau Desktop;kerberoshostname=exasoldb.example.com;kerberosservicename=exasol;debug=1;logdir=C:\\tmp");
});

test('client version available', () => {
    expect(getJdbcUrl({'v-clientversion': '1.2.3'})).toEqual("jdbc:exa:exasoldb.example.com:8563;validateservercertificate=1;fingerprint=15F9CA9;feedbackinterval=1;clientname=Tableau Desktop;clientversion=1.2.3;kerberoshostname=exasoldb.example.com;kerberosservicename=exasol");
});

test('running on Server', () => {
    expect(getJdbcUrl({':tableau-server-user': 'user'})).toEqual("jdbc:exa:exasoldb.example.com:8563;validateservercertificate=1;fingerprint=15F9CA9;feedbackinterval=1;clientname=Tableau Server;kerberoshostname=exasoldb.example.com;kerberosservicename=exasol");
});