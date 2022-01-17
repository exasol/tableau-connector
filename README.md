# Exasol Tableau Connector

<!-- add logo and banners here -->

# Overview

[Tableau](https://www.tableau.com/) is a business intelligence tool. You can use Tableau to visualize data in [Exasol](https://www.exasol.com).

The Exasol Tableau Connector is an adapter, that maps Exasol's analytical capabilities to Tableau's external interface, allowing Tableau to use Exasol-specific features.

## Note

**Currently the Tableau connector developed in this repository is not yet signed, certificated and shipped by Tableau and is not recommended for production environments.**

## Features

* Exasol-specific connection dialog
* Maps Tableau functions to Exasol functions


# Table of Contents

## Information for Users

* [User Guide](doc/user_guide/user_guide.md)
* [Tableau in the Exasol Online Documentation](https://docs.exasol.com/connect_exasol/bi_tools/tableau.htm)
* [White paper on Exasol and Tableau](https://www.exasol.com/resource/tableau-exasol-better-together/)

## Information for Developers

* [Developer Guide](doc/developer_guide/developer_guide.md)

# Dependencies

## Runtime Dependencies

To use the Exasol Tableau Connector, you need Tableau Version 2021.3 or later either as desktop or server variant.

## Test Dependencies

To run the included tests, you require Python 3.9.0 or later and the TDVT test framework 2.3.0 or later. This is part of the [Tableau Connector SDK](https://github.com/tableau/connector-plugin-sdk). Check the documentation of TDVT for transitive dependencies.
