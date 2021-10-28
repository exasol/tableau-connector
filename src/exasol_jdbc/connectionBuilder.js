(function dsbuilder(attr) {

    const authentication = attr[connectionHelper.attributeAuthentication];
    const fingerprint = attr["v-fingerprint"];
    const validateServerCertificate = attr["v-validateservercertificate"];
    
    logging.Log("connectionBuilder.js: input args: authentication='" + authentication
        + "', fingerprint='" + fingerprint
        + "', validateServerCertificate='"+ validateServerCertificate+"'");

    var fingerprintArg = "";
    if(fingerprint && fingerprint.trim().length > 0) {
        fingerprintArg = "/" + fingerprint.trim();
    }

    // See https://docs.exasol.com/connect_exasol/drivers/jdbc.htm
    const url = "jdbc:exa:"
        + attr[connectionHelper.attributeServer]
        + fingerprintArg
        + ":"
        + attr[connectionHelper.attributePort]
        + ";validateservercertificate=" + validateServerCertificate
        + ";feedbackinterval=1"
        + ";clientname=TableauDesktop";
        // Required to activate Kerberos authentication
        // https://www.exasol.com/support/browse/SUPPORT-26947
        + ";kerberoshostname=" + attr[connectionHelper.attributeServer]
        //+ ";kerberosservicename=exasol"
        //+ ";debug=1;logdir=C:\\tmp"
        + "";

    return [url];
})
