(function dsbuilder(attr) {
    "use strict";

    const odbcDriverDebugEnabled = false;
    const odbcDriverLogFile = "C:\\tmp\\exa-odbc.log"; // Windows
    //const odbcDriverLogFile = "/tmp/exa-odbc.log"; // Linux

    function log(str) {
        logging.Log("[connectionBuilder.js] " + str)
    }

    const params = {};

    if (odbcDriverDebugEnabled) {
        log("ODBC Driver log enabled. Log file: " + odbcDriverLogFile);
        params["EXALOGFILE"] = odbcDriverLogFile;
        params["LOGMODE"] = "DEFAULT";
    }

    params["EXAHOST"] = attr[connectionHelper.attributeServer];
    params["EXAPORT"] = attr[connectionHelper.attributePort];

    if (attr["schema"] && attr["schema"] != "") {
        params["EXASCHEMA"] = attr["schema"];
    }

    params["UID"] = attr[connectionHelper.attributeUsername];
    params["PWD"] = attr[connectionHelper.attributePassword];

    const formattedParams = [];

    const driverLocation = driverLocator.LocateDriver(attr);
    log("Driver location: " + driverLocation);
    formattedParams.push(connectionHelper.FormatKeyValuePair(driverLocator.keywordDriver, driverLocation));

    for (var key in params) {
        formattedParams.push(connectionHelper.FormatKeyValuePair(key, params[key]));
    }

    return formattedParams;
})
