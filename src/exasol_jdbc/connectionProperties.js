(function propertiesbuilder(attr) {

    function log(str) {
        logging.Log("connectionProperties.js: " + str)
    }
    function isEmpty(str) {
        return (!str || 0 === str.length);
    }

    const authentication = attr[connectionHelper.attributeAuthentication];

    var props = {};
    props["user"] = attr[connectionHelper.attributeUsername];
    props["password"] = attr[connectionHelper.attributePassword];

    if (authentication === 'auth-integrated') {
        // if attributeTableauServerUser is non-empty, it means the connector plugin is currently being accessed in a Tableau Server environment
        var serverUser = attr[connectionHelper.attributeTableauServerUser];
        if (!isEmpty(serverUser)) {
            log("Running on Server using integrated auth with user '" + serverUser + "'")
            props["user"] = serverUser;
            props["gsslib"] = "gssapi";
            props["jaasLogin"] = "false";
        } else {
            log("Running on Desktop using integrated auth")
            props["gsslib"] = "gssapi";
            props["jaasLogin"] = "false";
            props["jaasApplicationName"] = "com.sun.security.jgss.krb5.initiate";
        }
    } else {
        log("Using non-integrated auth '" + authentication + "'")
    }

    return props;
})
