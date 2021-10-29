(function dsbuilder(attr) {
    const jdbcDriverDebugEnabled = false;
    const jdbcDriverLogDir = "C:\\tmp";

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

    log("input args: authentication='" + authentication
        + "', fingerprint='" + fingerprint
        + "', validateServerCertificate='" + validateServerCertificate + "'");

    const fingerprintArg = !isEmpty(fingerprint) ? ("/" + fingerprint.trim()) : "";
    const debugArg = jdbcDriverDebugEnabled ? (";debug=1;logdir=" + jdbcDriverLogDir) : "";
    // See https://docs.exasol.com/connect_exasol/drivers/jdbc.htm
    const url = "jdbc:exa:"
        + hostName
        + fingerprintArg
        + ":"
        + attr[connectionHelper.attributePort]
        + ";validateservercertificate=" + validateServerCertificate
        + ";feedbackinterval=1"
        + ";clientname=TableauDesktop"
        // Required to activate Kerberos authentication
        // https://www.exasol.com/support/browse/SUPPORT-26947
        + ";kerberoshostname=" + hostName
        //+ ";kerberosservicename=exasol"
        + debugArg;
    log("JDBC URL: '" + url + "'");
    return [url];
})
