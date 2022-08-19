(function propertiesbuilder(attr) {
    "use strict";

    function isEmpty(str) {
        return (!str || 0 === str.length);
    }

    function getServerUser() {
        return attr[connectionHelper.attributeTableauServerUser];
    }

    function runningOnServer() {
        return !isEmpty(getServerUser());
    }

    function useKerberos() {
        return attr[connectionHelper.attributeAuthentication] === connectionHelper.valueAuthIntegrated;
    }

    function getValidateServerCertificate() {
        const value = attr["v-validateservercertificate"];
        if (value === 0 || value === "0") {
            return "0";
        } else {
            return "1";
        }
    }

    function getCertificateFingerprint() {
        const fingerprint = attr["v-fingerprint"];
        if (isEmpty(fingerprint) || isEmpty(fingerprint.trim())) {
            return undefined;
        }

        return fingerprint.trim();
    }

    // See https://docs.exasol.com/db/latest/connect_exasol/drivers/jdbc.htm#SupportedDriverProperties
    const props = {};

    if (useKerberos()) {
        const hostName = attr[connectionHelper.attributeServer];
        props["kerberoshostname"] = hostName;
        props["kerberosservicename"] = "exasol";
    }

    if (runningOnServer()) {
        props["user"] = getServerUser();
        props["loginType"] = "2";
        props["logintype"] = "gss";
    } else {
        props["user"] = attr[connectionHelper.attributeUsername];
        props["password"] = attr[connectionHelper.attributePassword];
    }

    props['validateservercertificate'] = getValidateServerCertificate();
    props['fingerprint'] = getCertificateFingerprint();
    props['clientname'] = connectionHelper.GetProductName();
    props['clientversion'] = connectionHelper.GetProductVersion();
    props['feedbackinterval'] = 1;
    return props;
})
