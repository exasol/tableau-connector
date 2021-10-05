(function propertiesbuilder(attr) {
    var props = {};
    props["user"] = attr[connectionHelper.attributeUsername];
    props["password"] = attr[connectionHelper.attributePassword];

    props["fingerprint"] = attr['v-fingerprint'];
    props["validateservercertificate"] = attr['v-validateservercertificate'];

    return props;
})
