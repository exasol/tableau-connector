# Exasol Tableau Connector 0.6.1, released 2022-02-23
 
Code name: Support renamed JDBC parameter `logintype`

## Summary

This release updates the JDBC connector and adds support for the renamed parameter `logintype` that will be added with JDBC driver versions 7.1.7 and 8.0.0. The original parameter `loginType` did not follow naming conventions and was undocumented.

The JDBC connector will continue to work with older and newer JDBC drivers by using both variants of the parameter.

## Features

* #48: Added support for renamed JDBC parameter `logintype`
