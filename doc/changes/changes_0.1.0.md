# tableau-connector 0.1.0, released 2021-??-??
 
Code name: Green original tests

## Summary

Version 0.1.0 of the Tableau Connector brings the connector embedded into Exasol's standard project layout. Also the existing test with the TDVT test suite provided by Tableau can now be executed. Test failures have been fixed.

Note that this version is not considered production-ready, since it has only been tested using Tableau's TDVT test suite. Tests on Tableau server have not yet been conducted.

## Bug Fixes
 
* #1: Fixed conversion from pure-time type to Tableau-compatible timestamp.
* #7: Exclude calcs_data test.
* #11: Fixed a few functions and enabled tests for them.
 
## Documentation
 
* #1: Added changelog and README
