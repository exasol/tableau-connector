const { defineGlobalObjects, evalFile } = require("./common");

defineGlobalObjects({ loggingEnabled: true });

const dsbuilder = evalFile("../src/exasol_odbc/connectionBuilder.js");

function createDefaultOdbcAttr(customAttributes) {
    const defaultAttr = {
        port: '8563',
        class: 'odbc',
        server: 'exasoldb.example.com',
        authentication: 'auth-integrated',
        schema: '',
        name: 'exasol_odbc.0vzu1ng0thcngw18ovqlb0f51mic',
        username: 'user',
        password: 'passwd',
        ':thread-session': '2',
        ':subclass': 'exasol_odbc',
        'v-fingerprint': '15F9CA9',
        'v-validateservercertificate': '1'
    };
    customAttributes = customAttributes || {};
    return { ...defaultAttr, ...customAttributes };
}

function getParameters(attr) {
    return dsbuilder(createDefaultOdbcAttr(attr));
}

test('default parameters', () => {
    expect(getParameters({})).toEqual(["driver=LocateDriver result", "EXAHOST=exasoldb.example.com", "EXAPORT=8563", "UID=user", "PWD=passwd"]);
});

test('schema defined', () => {
    expect(getParameters({ schema: "dbSchema" })).toEqual(["driver=LocateDriver result", "EXAHOST=exasoldb.example.com", "EXAPORT=8563", "EXASCHEMA=dbSchema", "UID=user", "PWD=passwd"]);
});

test('non-default server', () => {
    expect(getParameters({ server: "myserver" })).toEqual(["driver=LocateDriver result", "EXAHOST=myserver", "EXAPORT=8563", "UID=user", "PWD=passwd"]);
});

test('non-default port', () => {
    expect(getParameters({ port: 1234 })).toEqual(["driver=LocateDriver result", "EXAHOST=exasoldb.example.com", "EXAPORT=1234", "UID=user", "PWD=passwd"]);
});

test('non-default user', () => {
    expect(getParameters({ username: 'otheruser' })).toEqual(["driver=LocateDriver result", "EXAHOST=exasoldb.example.com", "EXAPORT=8563", "UID=otheruser", "PWD=passwd"]);
});

test('non-default password', () => {
    expect(getParameters({ password: 'otherpassword' })).toEqual(["driver=LocateDriver result", "EXAHOST=exasoldb.example.com", "EXAPORT=8563", "UID=user", "PWD=otherpassword"]);
});