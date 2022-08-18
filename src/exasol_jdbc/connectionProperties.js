(function propertiesbuilder(attr) {
    "use strict";

    function isEmpty(str) {
        return (!str || 0 === str.length);
    }

    const authentication = attr[connectionHelper.attributeAuthentication];
    const serverAuthMode = attr[connectionHelper.attributeTableauServerAuthMode];

    function getServerUser() {
        return attr[connectionHelper.attributeTableauServerUser];
    }

    function useKerberos() {
        return !isEmpty(getServerUser());
    }

    const props = {};

    if (useKerberos()) {
        props["user"] = getServerUser();
        props["loginType"] = "2";
        props["logintype"] = "gss";
    } else {
        props["user"] = attr[connectionHelper.attributeUsername];
        props["password"] = attr[connectionHelper.attributePassword];
    }
    props['clientname'] = connectionHelper.GetProductName();
    props['clientversion'] = connectionHelper.GetProductVersion();
    return props;
})
