const fs = require("fs");

/**
 * Reads and returns a JavaScript function from a file.
 * @param {string} path path of the file
 * @returns {object} the evaluation result
 */
exports.evalFile = function (path) {
    const fileContent = fs.readFileSync(path).toString();
    return eval(fileContent);
}

exports.createDefaultJdbcAttr = function (customAttributes) {
    const defaultAttr = {
        port: '8563',
        class: 'jdbc',
        server: 'exasoldb.example.com',
        authentication: 'auth-integrated',
        dbname: '',
        name: 'exasol_jdbc.0vzu1ng0thcngw18ovqlb0f51mic',
        ':thread-session': '2',
        ':subclass': 'exasol_jdbc',
        'v-fingerprint': '15F9CA9',
        'v-validateservercertificate': '1'
    };
    customAttributes = customAttributes || {};
    return { ...defaultAttr, ...customAttributes };
}

exports.defineGlobalObjects = function () {
    connectionHelper = {
        GetPlatform: () => "win",
        GetRTK: () => "SFTableau_9c9859940b1343bdb7c15d69b37ce1af_v1.0",
        FormatKeyValuePair: (key, value) => `${key}=${value}`,
        SetImpersonateAttributes: () => { },
        GetProductName: () => "GetProductName()",
        GetProductVersion: () => "GetProductVersion()",
        attributeServer: 'server',
        attributeClass: 'class',
        attributeDatabase: 'dbname',
        attributeInitialSQL: 'one-time-sql',
        attributeAuthentication: 'authentication',
        attributeUsername: 'username',
        attributePassword: 'password',
        attributeODBCConnectStringExtras: 'odbc-connect-string-extras',
        attributeWarehouse: 'warehouse',
        attributeService: 'service',
        attributeVendor1: 'vendor1',
        attributeVendor2: 'vendor2',
        attributeVendor3: 'vendor3',
        attributePort: 'port',
        attributeTableauServerUser: ':tableau-server-user',
        attributeTableauServerAuthMode: 'workgroup-auth-mode',
        attributeSSLMode: 'sslmode',
        attributeSSLCert: 'sslcert',
        keywordODBCUsername: 'UID',
        keywordODBCPassword: 'PWD',
        valueAuthIntegrated: 'auth-integrated',
        valueAuthUserPass: 'auth-user-pass',
        valueAuthModeDBImpersonate: 'db-impersonate'
    };
    driverLocator = {
        LocateDriver: (attr) => "LocateDriver result",
        LocateDriverVersion: () => "LocateDriverVersion result",
        keywordDriver: 'DRIVER'
    };
}
