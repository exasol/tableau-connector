const { defineGlobalObjects, evalFile } = require("./common");

defineGlobalObjects({ loggingEnabled: false });

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
        ':dialect-xml': '<dialect class="exasol_odbc"...',
        ':driver-bitness': '64',
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
    expect(getParameters({})).toEqual(["DRIVER=LocateDriver result", "EXAHOST=exasoldb.example.com", "EXAPORT=8563", "UID=user", "PWD=passwd"]);
});

test('schema defined', () => {
    expect(getParameters({ schema: "dbSchema" })).toEqual(["DRIVER=LocateDriver result", "EXAHOST=exasoldb.example.com", "EXAPORT=8563", "EXASCHEMA=dbSchema", "UID=user", "PWD=passwd"]);
});

test('schema undefined', () => {
    expect(getParameters({ schema: undefined })).toEqual(["DRIVER=LocateDriver result", "EXAHOST=exasoldb.example.com", "EXAPORT=8563", "UID=user", "PWD=passwd"]);
});

test('schema empty string', () => {
    expect(getParameters({ schema: "" })).toEqual(["DRIVER=LocateDriver result", "EXAHOST=exasoldb.example.com", "EXAPORT=8563", "UID=user", "PWD=passwd"]);
});

test('schema blank', () => {
    expect(getParameters({ schema: " " })).toEqual(["DRIVER=LocateDriver result", "EXAHOST=exasoldb.example.com", "EXAPORT=8563", "EXASCHEMA= ", "UID=user", "PWD=passwd"]);
});

test('non-default server', () => {
    expect(getParameters({ server: "myserver" })).toEqual(["DRIVER=LocateDriver result", "EXAHOST=myserver", "EXAPORT=8563", "UID=user", "PWD=passwd"]);
});

test('non-default port', () => {
    expect(getParameters({ port: 1234 })).toEqual(["DRIVER=LocateDriver result", "EXAHOST=exasoldb.example.com", "EXAPORT=1234", "UID=user", "PWD=passwd"]);
});

test('non-default user', () => {
    expect(getParameters({ username: 'otheruser' })).toEqual(["DRIVER=LocateDriver result", "EXAHOST=exasoldb.example.com", "EXAPORT=8563", "UID=otheruser", "PWD=passwd"]);
});

test('non-default password', () => {
    expect(getParameters({ password: 'otherpassword' })).toEqual(["DRIVER=LocateDriver result", "EXAHOST=exasoldb.example.com", "EXAPORT=8563", "UID=user", "PWD=otherpassword"]);
});