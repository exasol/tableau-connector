(function propertiesbuilder(attr) {
    "use strict";
    let enableDebugging = false;

    function log(str) {
        logging.Log("[connectionProperties.js] " + str)
    }
    function isEmpty(str) {
        return (!str || 0 === str.length);
    }

    const authentication = attr[connectionHelper.attributeAuthentication];
    const user = attr[connectionHelper.attributeUsername];
    const serverUser = attr[connectionHelper.attributeTableauServerUser];
    const serverAuthMode = attr[connectionHelper.attributeTableauServerAuthMode];

    const props = {};

    enableDebugging = enableDebugging || attr['v-debug'];
    const debugMessage = connectionHelper.attributeAuthentication + "=" + authentication + ", "
        + connectionHelper.attributeTableauServerAuthMode + "='" + serverAuthMode + "', "
        + connectionHelper.attributeUsername + "='" + user + "', "
        + connectionHelper.attributeTableauServerUser + "='" + serverUser + "'";
    log(debugMessage);
    if (enableDebugging) {
        props["jdbc-driver-debug"] = debugMessage;
    }

    if (isEmpty(serverUser)) {
        props["user"] = user;
        props["password"] = attr[connectionHelper.attributePassword];
    } else {
        props["user"] = serverUser;
        props["loginType"] = "2";
        props["logintype"] = "gss";
    }

    return props;
})
