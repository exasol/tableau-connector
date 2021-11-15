(function propertiesbuilder(attr) {

    function log(str) {
        logging.Log("connectionProperties.js: " + str)
    }
    function isEmpty(str) {
        return (!str || 0 === str.length);
    }

    const authentication = attr[connectionHelper.attributeAuthentication];
    const user = attr[connectionHelper.attributeUsername];
    const serverUser = attr[connectionHelper.attributeTableauServerUser];
    const serverAuthMode = attr[connectionHelper.attributeTableauServerAuthMode];

    const props = {};
    props["password"] = attr[connectionHelper.attributePassword];

    props["jdbc-driver-debug"] = "Authentication=" + authentication + ", authmode='" + serverAuthMode
        + "', impersonateMode='" + connectionHelper.valueAuthModeDBImpersonate
        + "', user='" + user + "', serveruser='" + serverUser + "'";
    // Authentication=auth-user-pass, authmode='prompt', impersonateMode='db-impersonate', user=sys, serveruser=undefined, 
    //Authentication=auth-integrated, authmode='as-is', impersonateMode='db-impersonate', user=, serveruser=tableauuser,
    //Authentication=auth-integrated, authmode='kerberos-impersonate', impersonateMode='db-impersonate', user=, serveruser=john.doe,

    if (!isEmpty(serverUser)) {
        props["user"] = serverUser;
        props["loginType"] = "2";
    } else {
        props["user"] = user;
    }

    return props;
})
