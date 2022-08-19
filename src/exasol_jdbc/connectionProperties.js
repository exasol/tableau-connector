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

    function runningOnServer() {
        return !isEmpty(getServerUser());
    }


    function useKerberos() {
        return attr[connectionHelper.attributeAuthentication] === connectionHelper.valueAuthIntegrated
    }


    const props = {};

    if(useKerberos()) {
        const hostName = attr[connectionHelper.attributeServer]
        props["kerberoshostname"] = hostName
        props["kerberosservicename"] = "exasol"
    }
    
    if (runningOnServer()) {
        props["user"] = getServerUser();
        props["loginType"] = "2";
        props["logintype"] = "gss";
    } else {
        props["user"] = attr[connectionHelper.attributeUsername];
        props["password"] = attr[connectionHelper.attributePassword];
    }
    props['clientname'] = connectionHelper.GetProductName();
    props['clientversion'] = connectionHelper.GetProductVersion();
    props['feedbackinterval'] = 1;
    return props;
})
