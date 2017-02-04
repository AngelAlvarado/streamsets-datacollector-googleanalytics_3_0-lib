# Google Analytics Streamsets DataCollector Origin.

SCD Origin which connects to Google Analytics via Java client library v3.0.

## Requirements

* A Google Application and .p12 file are needed to authenticate.

## Description

This library lets you connect to the Google Reporting API via a SDC Origin

[![Pipeline Origin](images/APIConfiguration.png?raw=true)](images/APIConfiguration.png)

Only some parameters from the Google Analytics [library](https://developers.google.com/analytics/devguides/reporting/core/v3/reference#ids) are exposed.


You'll see data coming from Google Analytics as any other origin:

[![Pipeline Results](images/GAResults.png?raw=true)](images/GAResults.png)
