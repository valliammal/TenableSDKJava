CHANGE LOG
=========

1.4.1
==========

* Added: Support for asset_id and uuid fields in ScanHost Object

1.4.0
==========

* Added: Support for dynamic tagging.
* Fixed: Can not set agent_group_id during scan creation/update.
* Fixed: Updated Editor Input defaultValue property to be type Object due to value variability.

1.3.3
==========

* Fixed: Chapters were not included in ScanRef download requests which caused empty exports
* Fixed: ScanRef getLastHistory method was returning the oldest history rather than the most recent
* Fixed: NPE could occur when doing asset/vuln exports without tags
* Added: Scan Manager and Scan Operator roles added to UserRole

1.3.2
==========

* Fixed: MismatchedInputException for pluginOutput

1.3.1
==========

* Fixed: Compile issues for Jackson.core 2.10.x
* Fixed: MismatchedInputException for ScanHostDetails and ScanHostDetailsInfo

1.3.0
==========

* Added: Support for tagging API.
* Added: Support for Access-Groups API.
* Added: Support for new Export API filters.
* Fixed: Bug where group object was not returned upon new group creation.

1.2.0
==========

* Added: Support for retrieving additional plugin families.

1.1.0
==========

* Added: Support for new Vuln Export API
* Added: Support for new Asset Export API
* Added: Route to get a scan's latest status (/latest-status).
* Added: X-Tio-Retry-Count header sent with each retry.
* Added: User-Agent header is customizable.

1.0.0
==========

* Support for the Tenable.io public API - https://cloud.tenable.com/api
