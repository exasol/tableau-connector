(function dsbuilder(attr) {
    // See https://docs.exasol.com/connect_exasol/drivers/jdbc.htm
    const url = "jdbc:exa:"
        + attr[connectionHelper.attributeServer] + ":"
        + attr[connectionHelper.attributePort]
        + ";validateservercertificate=0"
        + ";feedbackinterval=1"
        + ";clientname=TableauConnector"
        + ";clientversion=ver";

    return [url];
})
