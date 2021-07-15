# Exasol Tableau Connector 0.1.0, released 2021-07-15
 
Code name: Additional functions support, optimized metadata fetching

## Summary

Version 0.1.0 of the Tableau Connector brings the connector embedded into Exasol's standard project layout. The release contains a few bug fixes and improvements, including optimized metadata fetching, and a few additional functions enabled.
The connector was tested with the TDVT test framework and also with freshly added integration tests for the Tableau Server application.

## Features

* #14: Enabled REGEX_* functions.

## Refactorings

* #6: Optimized metadata fetching.

## Bug Fixes
 
* #1: Fixed conversion from pure-time type to Tableau-compatible timestamp.
* #7: Exclude calcs_data test.
* #11: Fixed a few functions and enabled tests for them.
 
## Documentation
 
* #1: Added changelog and README.
* #20: Added a user guide. 
