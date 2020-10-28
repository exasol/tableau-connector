(function dsbuilder(attr)
{
    var params = {};

    params["EXAHOST"] = attr[connectionHelper.attributeServer];
    params["EXAPORT"] = attr[connectionHelper.attributePort];

    if (attr["schema"] != "")
        params["EXASCHEMA"] = attr["schema"];

    params["UID"] = attr[connectionHelper.attributeUsername];
    params["PWD"] = attr[connectionHelper.attributePassword];

    var formattedParams = [];

    formattedParams.push(connectionHelper.FormatKeyValuePair(driverLocator.keywordDriver, driverLocator.LocateDriver(attr)));

    for (var key in params)
    {
        formattedParams.push(connectionHelper.FormatKeyValuePair(key, params[key]));
    }

    return formattedParams;
})
