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
        + ";encryption=1"
        + ";feedbackinterval=1"
        + ";clientname=TableauDesktop";

    return [url];
})
