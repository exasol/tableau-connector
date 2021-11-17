(function dsbuilder(attr) {
    "use strict";
    const jdbcDriverDebugEnabled = false;
    const jdbcDriverLogDir = "C:\\tmp"; // Windows
    //const jdbcDriverLogDir = "/tmp"; // Linux
    const kerberosServiceName = "exasol";

    function log(str) {
        logging.Log("connectionBuilder.js: " + str)
    }

    function isEmpty(str) {
        return (!str || 0 === str.trim().length);
    }

    function logProperties(objectName, object) {
        if (!object) {
            log("Object '" + objectName + "' is not defined");
        }
        log("Properties of object '" + objectName + "':");
        for (var prop in object) {
            if (Object.prototype.hasOwnProperty.call(object, prop)) {
                const value = object[prop];
                log("  - " + objectName + "['" + prop + "'] = '" + value + "' type: " + (typeof value));
            }
        }
    }

    //logProperties("connectionHelper", connectionHelper);
    logProperties("attr", attr);
    //logProperties("logging", logging);
    //logProperties("process", process);
    //logProperties("global", global);
    //logProperties("window", window);
    //logProperties("globalThis", globalThis);
    //log("platform: "+connectionHelper.GetPlatform());
    //log("rtk: "+connectionHelper.GetRTK());
    //log("__filename: "+__filename);


    const hostName = attr[connectionHelper.attributeServer];
    const port = attr[connectionHelper.attributePort];
    const authentication = attr[connectionHelper.attributeAuthentication];
    const fingerprint = attr["v-fingerprint"];
    const validateServerCertificate = attr["v-validateservercertificate"];
    const clientVersion = attr["v-clientversion"];
    const serverUser = attr[connectionHelper.attributeTableauServerUser];
    const runningOnServer = !isEmpty(serverUser);

    const fingerprintArg = !isEmpty(fingerprint) ? (";fingerprint=" + fingerprint.trim()) : "";

    const useKerberos = authentication === connectionHelper.valueAuthIntegrated;
    let kerberosArg = "";
    if (useKerberos) {
        // Required to activate Kerberos authentication
        // https://www.exasol.com/support/browse/SUPPORT-26947
        kerberosArg = ";kerberoshostname=" + hostName + ";kerberosservicename=" + kerberosServiceName;
    }

    log("input args: authentication='" + authentication
        + " -> use kerberos = " + useKerberos
        + ", fingerprint='" + fingerprint
        + "', validateServerCertificate='" + validateServerCertificate + "'");

    const clientName = runningOnServer ? "Tableau Server" : "Tableau Desktop";
    const clientVersionArg = !isEmpty(clientVersion) ? ";clientversion=" + clientVersion : "";

    const debugArg = (jdbcDriverDebugEnabled || attr['v-debug']) ? (";debug=1;logdir=" + jdbcDriverLogDir) : "";
    const portArg = isEmpty(port) ? "" : ":" + port;
    // See https://docs.exasol.com/connect_exasol/drivers/jdbc.htm
    const url = "jdbc:exa:"
        + hostName
        + portArg
        + ";validateservercertificate=" + validateServerCertificate
        + fingerprintArg
        + ";feedbackinterval=1"
        + ";clientname=" + clientName
        + clientVersionArg
        + kerberosArg
        + debugArg;
    log("JDBC URL: '" + url + "'");
    return [url];
})
