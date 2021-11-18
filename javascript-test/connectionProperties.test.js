const { defineGlobalObjects, evalFile, createDefaultAttr } = require("./common");

defineGlobalObjects({ loggingEnabled: true });

const propertiesbuilder = evalFile("../src/exasol_jdbc/connectionProperties.js");

function getProperties(attr) {
    return propertiesbuilder(createDefaultAttr(attr));
}

describe('Tableau Desktop', () => {
    test('Username & Password auth', () => {
        expect(getProperties({
            authentication: 'auth-user-pass',
            username: 'exauser',
            password: 'exapassword'
        })).toEqual({ user: 'exauser', password: 'exapassword' });
    });

    test('Kerberos auth', () => {
        expect(getProperties({
            authentication: 'auth-integrated',
            username: ''
        })).toEqual({ user: '', password: undefined });
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
        })).toEqual({ user: 'exauser', password: 'exapassword' });
    });

    test('RunAs auth', () => {
        expect(getProperties({
            authentication: 'auth-integrated',
            'workgroup-auth-mode': 'as-is',
            username: '',
            ':tableau-server-user': 'tabuser',
            password: 'exapassword'
        })).toEqual({ user: 'tabuser', loginType: '2' });
    });

    test('Viewer Credentials auth', () => {
        expect(getProperties({
            authentication: 'auth-integrated',
            'workgroup-auth-mode': 'kerberos-impersonate',
            username: '',
            ':tableau-server-user': 'normaluser',
            password: 'exapassword'
        })).toEqual({ user: 'normaluser', loginType: '2' });
    });
});

test('Debugging enabled', () => {
    expect(getProperties({
        authentication: 'auth-integrated',
        'workgroup-auth-mode': 'kerberos-impersonate',
        username: 'normaluser',
        ':tableau-server-user': 'serveruser',
        password: 'exapassword',
        'v-debug': 'true'
    })).toEqual({ user: 'serveruser', loginType: '2', 'jdbc-driver-debug': "authentication=auth-integrated, workgroup-auth-mode='kerberos-impersonate', username='normaluser', :tableau-server-user='serveruser'"});
});
