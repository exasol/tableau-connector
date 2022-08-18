(function propertiesbuilder(attr) {
    "use strict";

    function isEmpty(str) {
        return (!str || 0 === str.length);
    }

    const authentication = attr[connectionHelper.attributeAuthentication];
    const user = attr[connectionHelper.attributeUsername];
    const serverUser = attr[connectionHelper.attributeTableauServerUser];
    const serverAuthMode = attr[connectionHelper.attributeTableauServerAuthMode];

    const props = {};

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
