(function propertiesbuilder(attr) {

    function log(str) {
        logging.Log("connectionProperties.js: " + str)
    }
    const authentication = attr[connectionHelper.attributeAuthentication];

    var props = {};
    props["user"] = attr[connectionHelper.attributeUsername];
    props["password"] = attr[connectionHelper.attributePassword];

    if (authentication === 'auth-integrated') {
        logging.Log("connectionProperties.js: Using integrated auth")
        props["gsslib"] = "gssapi";
        props["jaasLogin"] = "false";
        props["jaasApplicationName"] = "com.sun.security.jgss.krb5.initiate";
    } else {
        log("Using non-integrated auth '" + authentication + "'")
    }

    return props;
})
