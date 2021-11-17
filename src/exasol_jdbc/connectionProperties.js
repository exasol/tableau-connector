(function propertiesbuilder(attr) {
    "use strict";
    const enableDebugging = false;

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

    if(enableDebugging) {
        props["jdbc-driver-debug"] = "Authentication=" + authentication + ", authmode='" + serverAuthMode
        + "', user='" + user + "', serveruser='" + serverUser + "'";
    }

    if (!isEmpty(serverUser)) {
        props["user"] = serverUser;
        props["loginType"] = "2";
    } else {
        props["user"] = user;
    }

    return props;
})
