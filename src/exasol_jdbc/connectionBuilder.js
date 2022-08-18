(function dsbuilder(attr) {
    "use strict";
    const kerberosServiceName = "exasol";

    function isEmpty(str) {
        return (!str || 0 === str.trim().length);
    }

    const hostName = attr[connectionHelper.attributeServer];
    const port = attr[connectionHelper.attributePort];
    const authentication = attr[connectionHelper.attributeAuthentication];
    const serverAuthMode = attr[connectionHelper.attributeTableauServerAuthMode];
    const fingerprint = attr["v-fingerprint"];
    const validateServerCertificate = attr["v-validateservercertificate"];
    const clientVersion = attr["v-clientversion"];
    const user = attr[connectionHelper.attributeUsername];
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
    const clientName = runningOnServer ? "Tableau Server" : "Tableau Desktop";
    const clientVersionArg = !isEmpty(clientVersion) ? ";clientversion=" + clientVersion : "";

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
        + kerberosArg;
    return [url];
})
