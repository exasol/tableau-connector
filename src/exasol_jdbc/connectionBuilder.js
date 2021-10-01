(function dsbuilder(attr) {
    var urlBuilder = "jdbc:exa://" + attr[connectionHelper.attributeServer] + ":" + attr[connectionHelper.attributePort] + "/" + attr[connectionHelper.attributeDatabase] + "?";

    return [urlBuilder];
})
