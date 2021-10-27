(function propertiesbuilder(attr) {
    logging.Log("Running connectionProperties.js")
    var props = {};
    props["user"] = attr[connectionHelper.attributeUsername];
    props["password"] = attr[connectionHelper.attributePassword];
    return props;
})
