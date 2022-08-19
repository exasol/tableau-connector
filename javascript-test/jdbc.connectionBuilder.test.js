const { defineGlobalObjects, evalFile, createDefaultJdbcAttr } = require("./common");

defineGlobalObjects();

const dsbuilder = evalFile("../src/exasol_jdbc/connectionBuilder.js");

function getJdbcUrl(attr) {
    const result = dsbuilder(createDefaultJdbcAttr(attr));
    expect(result).toHaveLength(1);
    return result[0];
}

describe('JDBC URL', () => {

    test('default url', () => {
        expect(getJdbcUrl({})).toEqual("jdbc:exa:exasoldb.example.com:8563");
    });

    describe('Port number', () => {
        test('custom port', () => {
            expect(getJdbcUrl({ port: "1234" })).toEqual("jdbc:exa:exasoldb.example.com:1234");
        });
        test('missing port', () => {
            expect(getJdbcUrl({ port: undefined })).toEqual("jdbc:exa:exasoldb.example.com");
        });
        test('empty port', () => {
            expect(getJdbcUrl({ port: "" })).toEqual("jdbc:exa:exasoldb.example.com");
        });
        test('blank port', () => {
            expect(getJdbcUrl({ port: " \t" })).toEqual("jdbc:exa:exasoldb.example.com");
        });
        test('port is trimmed', () => {
            expect(getJdbcUrl({ port: " 1234 \t" })).toEqual("jdbc:exa:exasoldb.example.com:1234");
        });
    })

    describe('Host name', () => {
        test('custom hostname', () => {
            expect(getJdbcUrl({ server: "db" })).toEqual("jdbc:exa:db:8563");
        });
        test('undefined hostname', () => {
            expect(getJdbcUrl({ server: undefined })).toEqual("jdbc:exa::8563");
        });
        test('hostname trimmed', () => {
            expect(getJdbcUrl({ server: " \tdb\t " })).toEqual("jdbc:exa:db:8563");
        });
        test('hostname blank', () => {
            expect(getJdbcUrl({ server: " \t " })).toEqual("jdbc:exa::8563");
        });
        test('hostname empty', () => {
            expect(getJdbcUrl({ server: "" })).toEqual("jdbc:exa::8563");
        });
        test('hostname trimmed', () => {
            expect(getJdbcUrl({ server: " \thostName\t " })).toEqual("jdbc:exa:hostName:8563");
        });
    });


    test('no kerberos', () => {
        expect(getJdbcUrl({ authentication: 'auth-user-pass' })).toEqual("jdbc:exa:exasoldb.example.com:8563");
    });

    test('running on Server', () => {
        expect(getJdbcUrl({ ':tableau-server-user': 'user' })).toEqual("jdbc:exa:exasoldb.example.com:8563");
    });

});