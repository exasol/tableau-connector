(function dsbuilder(attr) {
    const jdbcDriverDebugEnabled = true;
    const jdbcDriverLogDir = "C:\\tmp";
    const kerberosServiceName = "exasol";
    const clientVersion = "(none)";

    function log(str) {
        logging.Log("connectionBuilder.js: " + str)
    }

    function isEmpty(str) {
        return (!str || 0 === str.trim().length);
    }

    const hostName = attr[connectionHelper.attributeServer];
    const authentication = attr[connectionHelper.attributeAuthentication];
    const fingerprint = attr["v-fingerprint"];
    const validateServerCertificate = attr["v-validateservercertificate"];

    const fingerprintArg = !isEmpty(fingerprint) ? ("/" + fingerprint.trim()) : "";

    const useKerberos = authentication === 'auth-integrated';
    const kerberosHostName = hostName;
    let kerberosArg = "";
    if (useKerberos) {
        // Required to activate Kerberos authentication
        // https://www.exasol.com/support/browse/SUPPORT-26947
        kerberosArg = ";kerberoshostname=" + kerberosHostName + ";kerberosservicename=" + kerberosServiceName;
    }

    log("input args: authentication='" + authentication
        + " -> use kerberos = " + useKerberos
        + ", fingerprint='" + fingerprint
        + "', validateServerCertificate='" + validateServerCertificate + "'");

    const clientVersionArg = !isEmpty(clientVersion) ? ";clientversion=" + clientVersion : "";

    const debugArg = jdbcDriverDebugEnabled ? (";debug=1;logdir=" + jdbcDriverLogDir) : "";
    // See https://docs.exasol.com/connect_exasol/drivers/jdbc.htm
    const url = "jdbc:exa:"
        + hostName
        + fingerprintArg
        + ":"
        + attr[connectionHelper.attributePort]
        + ";validateservercertificate=" + validateServerCertificate
        + ";feedbackinterval=1"
        + ";clientname=Tableau"
        + clientVersionArg
        + kerberosArg
        + debugArg;
    log("JDBC URL: '" + url + "'");
    return [url];
})
