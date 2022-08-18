(function dsbuilder(attr) {
    "use strict";

    const odbcDriverDebugEnabled = false;
    const odbcDriverLogFile = "C:\\tmp\\exa-odbc.log"; // Windows
    //const odbcDriverLogFile = "/tmp/exa-odbc.log"; // Linux

    function isEmpty(str) {
        return (!str || 0 === str.trim().length);
    }

    const params = {};

    params["EXAHOST"] = attr[connectionHelper.attributeServer];
    params["EXAPORT"] = attr[connectionHelper.attributePort];

    if (attr["schema"] && attr["schema"] != "") {
        params["EXASCHEMA"] = attr["schema"];
    }

    params["UID"] = attr[connectionHelper.attributeUsername];
    params["PWD"] = attr[connectionHelper.attributePassword];

    const fingerprint = attr["v-fingerprint"];
    if(!isEmpty(fingerprint)) {
        params["FINGERPRINT"] = fingerprint.trim();
    }


    if (odbcDriverDebugEnabled) {
        params["EXALOGFILE"] = odbcDriverLogFile;
        params["LOGMODE"] = "DEFAULT";
    }

    const formattedParams = [];

    const driverLocation = driverLocator.LocateDriver(attr);
    formattedParams.push(connectionHelper.FormatKeyValuePair(driverLocator.keywordDriver, driverLocation));

    for (var key in params) {
        formattedParams.push(connectionHelper.FormatKeyValuePair(key, params[key]));
    }

    return formattedParams;
})
