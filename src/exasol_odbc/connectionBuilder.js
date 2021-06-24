(function dsbuilder(attr)
{
    var params = {};

    params["EXAHOST"] = attr[connectionHelper.attributeServer];
    params["EXAPORT"] = attr[connectionHelper.attributePort];

    if (attr["schema"] != "")
        params["EXASCHEMA"] = attr["schema"];

    // Adding properties for Kerberos authentication
    if (attr[connectionHelper.attributeAuthentication] == "auth-integrated") {
	var tableauServerUser = attr[connectionHelper.attributeTableauServerUser];
        params["KERBEROSSERVICENAME"] = "exasol";
        params["KERBEROSHOSTNAME"] = attr[connectionHelper.attributeServer];
        params["KERBEROSREALM"] = "";

        // tableauServerUser is not empty means this is a Tableau Server Environment	    
        if (!isEmpty(tableauServerUser)) {
            params["user"] = tableauServerUser;
            params["gsslib"] = "gssapi";	 
            params["jaasLogin"] = "false";    
        } else {
        // properties for Kerberos SSO on Tableau Desktop    
            params["gsslib"] = "gssapi";	 
            params["jaasLogin"] = "false";  
            params["jaasApplicationName"] = "com.sun.security.jgss.krb5.initiate";
        }                              
    // username-password auth     
    } else if (attr[connectionHelper.attributeAuthentication] == "auth-user-pass"){
        params["UID"] = attr[connectionHelper.attributeUsername];
        params["PWD"] = attr[connectionHelper.attributePassword];
    }
    
    var formattedParams = [];

    formattedParams.push(connectionHelper.FormatKeyValuePair(driverLocator.keywordDriver, driverLocator.LocateDriver(attr)));

    for (var key in params)
    {
        formattedParams.push(connectionHelper.FormatKeyValuePair(key, params[key]));
    }

    return formattedParams;
})
