const { defineGlobalObjects, evalFile, createDefaultJdbcAttr } = require("./common");
const { describe, expect, test } = require("@jest/globals");

defineGlobalObjects();

const propertiesBuilder = evalFile("../src/exasol_jdbc/connectionProperties.js");

function getProperties(attr) {
    return propertiesBuilder(createDefaultJdbcAttr(attr));
}

describe('Tableau Desktop', () => {
    test('Username & Password auth', () => {
        const actual = getProperties({
            authentication: 'auth-user-pass',
            username: 'exauser',
            password: 'exapassword'
        });
        expect(actual).toMatchObject({ user: 'exauser', password: 'exapassword' });
        expect(actual.kerberoshostname).toBeUndefined();
        expect(actual.kerberosservicename).toBeUndefined();
    });

    test('Kerberos auth', () => {
        const actual = getProperties({
            authentication: 'auth-integrated',
            username: ''
        });
        expect(actual).toMatchObject({ user: '', kerberoshostname: 'exasoldb.example.com', kerberosservicename: 'exasol' });
        expect(actual.password).toBeUndefined();
        expect(actual.logintype).toBeUndefined();
        expect(actual.loginType).toBeUndefined();
    });
});

describe('Tableau Server', () => {
    test('Username & password auth', () => {
        const actual = getProperties({
            authentication: 'auth-user-pass',
            'workgroup-auth-mode': 'prompt',
            username: 'exauser',
            ':tableau-server-user': undefined,
            password: 'exapassword'
        });
        expect(actual).toMatchObject({ user: 'exauser', password: 'exapassword' });
        expect(actual.kerberoshostname).toBeUndefined();
        expect(actual.kerberosservicename).toBeUndefined();
    });

    test('RunAs auth', () => {
        const actual = getProperties({
            authentication: 'auth-integrated',
            'workgroup-auth-mode': 'as-is',
            username: '',
            ':tableau-server-user': 'tabuser',
            password: 'exapassword'
        });
        expect(actual).toMatchObject({ user: 'tabuser', logintype: 'gss', loginType: '2', kerberoshostname: 'exasoldb.example.com', kerberosservicename: 'exasol' });
        expect(actual.password).toBeUndefined();
    });

    test('Viewer Credentials auth', () => {
        const actual = getProperties({
            authentication: 'auth-integrated',
            'workgroup-auth-mode': 'kerberos-impersonate',
            username: '',
            ':tableau-server-user': 'normaluser',
            password: 'exapassword'
        });
        expect(actual).toMatchObject({ user: 'normaluser', logintype: 'gss', loginType: '2', kerberoshostname: 'exasoldb.example.com', kerberosservicename: 'exasol' });
        expect(actual.password).toBeUndefined();
    });
});


describe('Static properties are always present', () => {
    test('client name', () => {
        const props = getProperties({})
        expect(props.clientname).toEqual("GetProductName()")
    })
    test('client version', () => {
        const props = getProperties({})
        expect(props.clientversion).toEqual("GetProductVersion()")
    })
    test('feedback interval', () => {
        const props = getProperties({})
        expect(props.feedbackinterval).toEqual(1)
    })
})