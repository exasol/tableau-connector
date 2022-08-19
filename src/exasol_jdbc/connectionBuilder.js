(function dsbuilder(attr) {
    "use strict";

    function isEmpty(str) {
        return (!str || 0 === str.trim().length);
    }

    function getHostName() {
        const hostName = attr[connectionHelper.attributeServer];
        if (isEmpty(hostName) || isEmpty(hostName.trim())) {
            return "";
        }
        return hostName.trim();
    }

    function getPort() {
        const port = attr[connectionHelper.attributePort];
        if (isEmpty(port) || isEmpty(port.trim())) {
            return "";
        }
        return ":" + port.trim();
    }

    // See https://docs.exasol.com/connect_exasol/drivers/jdbc.htm
    const url = "jdbc:exa:" + getHostName() + getPort();
    return [url];
})
