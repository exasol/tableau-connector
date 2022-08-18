const { defineGlobalObjects, evalFile, createDefaultJdbcAttr } = require("./common");
const { describe, expect, test } = require("@jest/globals");

defineGlobalObjects({ loggingEnabled: false });

const propertiesBuilder = evalFile("../src/exasol_jdbc/connectionProperties.js");

function getProperties(attr) {
    return propertiesBuilder(createDefaultJdbcAttr(attr));
}

describe('Tableau Desktop', () => {
    test('Username & Password auth', () => {
        expect(getProperties({
            authentication: 'auth-user-pass',
            username: 'exauser',
            password: 'exapassword'
        })).toMatchObject({ user: 'exauser', password: 'exapassword' });
    });

    test('Kerberos auth', () => {
        expect(getProperties({
            authentication: 'auth-integrated',
            username: ''
        })).toMatchObject({ user: '', password: undefined });
    });
});

describe('Tableau Server', () => {
    test('Username & password auth', () => {
        expect(getProperties({
            authentication: 'auth-user-pass',
            'workgroup-auth-mode': 'prompt',
            username: 'exauser',
            ':tableau-server-user': undefined,
            password: 'exapassword'
        })).toMatchObject({ user: 'exauser', password: 'exapassword' });
    });

    test('RunAs auth', () => {
        expect(getProperties({
            authentication: 'auth-integrated',
            'workgroup-auth-mode': 'as-is',
            username: '',
            ':tableau-server-user': 'tabuser',
            password: 'exapassword'
        })).toMatchObject({ user: 'tabuser', logintype: 'gss', loginType: '2' });
    });

    test('Viewer Credentials auth', () => {
        expect(getProperties({
            authentication: 'auth-integrated',
            'workgroup-auth-mode': 'kerberos-impersonate',
            username: '',
            ':tableau-server-user': 'normaluser',
            password: 'exapassword'
        })).toMatchObject({ user: 'normaluser', logintype: 'gss', loginType: '2' });
    });
});


describe('Client details', () => {
    test('client name', () => {
        const props = getProperties({})
        expect(props.clientname).toEqual("GetProductName()")
    })
    test('client version', () => {
        const props = getProperties({})
        expect(props.clientversion).toEqual("GetProductVersion()")
    })
})