package com.tenable.io.api.workbenches;


import com.fasterxml.jackson.core.type.TypeReference;
import com.tenable.io.api.ApiWrapperBase;
import com.tenable.io.api.scans.models.ScanVulnerability;
import com.tenable.io.api.workbenches.models.*;
import com.tenable.io.core.exceptions.TenableIoException;
import com.tenable.io.core.services.AsyncHttpService;
import com.tenable.io.core.services.HttpFuture;
import com.tenable.io.core.utilities.UriBuilderHelper;

import java.io.File;
import java.util.List;


/**
 * Copyright (c) 2017 Tenable Network Security, Inc.
 */
public class WorkbenchesApi extends ApiWrapperBase {
    /**
     * Instantiates a new Workbenches api.
     *
     * @param asyncHttpService the async http service
     * @param apiScheme        the api scheme
     * @param ApiHost          the api host
     */
    public WorkbenchesApi( AsyncHttpService asyncHttpService, String apiScheme, String ApiHost ) {
        super( asyncHttpService, apiScheme, ApiHost );
    }


    /**
     * List all the vulnerabilities recorded.
     *
     * @param options The vulnerability options
     * @return the list of vulnerabilities recorded
     * @throws TenableIoException the tenable IO exception
     */
    public List<ScanVulnerability> vulnerabilities( ExtendedFilteringOptions options ) throws TenableIoException {
        UriBuilderHelper uri = createBaseUriBuilder( "/workbenches/vulnerabilities" );
        addExtendedFilteringOptions( options, uri );
        HttpFuture httpFuture = asyncHttpService.doGet( uri.build() );
        return httpFuture.getAsType( new TypeReference<List<ScanVulnerability>>() {}, "vulnerabilities" );
    }


    /**
     * Get the details for a plugin.
     *
     * @param pluginId The id of the plugin
     * @param options  The vulnerability options
     * @return the vulnerability info
     * @throws TenableIoException the tenable IO exception
     */
    public WbVulnerabilityInfo vulnerabilityInfo( String pluginId, FilteringOptions options ) throws TenableIoException {
        UriBuilderHelper uri = createBaseUriBuilder( "/workbenches/vulnerabilities/" + pluginId + "/info" );
        addFilteringOptions( options, uri );
        HttpFuture httpFuture = asyncHttpService.doGet( uri.build() );
        return httpFuture.getAsType( WbVulnerabilityInfo.class, "info" );

    }


    /**
     * Get the vulnerability outputs for a plugin.
     *
     * @param pluginId The id of the plugin
     * @param options  The vulnerability options
     * @return the vulnerability output list
     * @throws TenableIoException the tenable IO exception
     */
    public List<WbVulnerabilityOutputResult> vulnerabilityOutput( String pluginId, FilteringOptions options )
            throws TenableIoException {
        UriBuilderHelper uri = createBaseUriBuilder( "/workbenches/vulnerabilities/" + pluginId + "/outputs" );
        addFilteringOptions( options, uri );
        HttpFuture httpFuture = asyncHttpService.doGet( uri.build() );
        return httpFuture.getAsType( new TypeReference<List<WbVulnerabilityOutputResult>>() {}, "outputs" );
    }


    /**
     * List all the assets recorded.
     *
     * @param options The vulnerability options
     * @return the list of vulnerability assets
     * @throws TenableIoException the tenable IO exception
     */
    public List<WbVulnerabilityAsset> assets( FilteringOptions options ) throws TenableIoException {
        UriBuilderHelper uri = createBaseUriBuilder( "/workbenches/assets" );
        addFilteringOptions( options, uri );
        HttpFuture httpFuture = asyncHttpService.doGet( uri.build() );
        return httpFuture.getAsType( new TypeReference<List<WbVulnerabilityAsset>>() {}, "assets" );
    }


    /**
     * List all the assets with vulnerabilities.
     *
     * @param options The vulnerability options
     * @return the list of assets with vulnerabilities
     * @throws TenableIoException the tenable IO exception
     */
    public List<WbVulnerabilityAsset> assetsVulnerabilities( FilteringOptions options ) throws TenableIoException {
        UriBuilderHelper uri = createBaseUriBuilder( "/workbenches/assets/vulnerabilities" );
        addFilteringOptions( options, uri );
        HttpFuture httpFuture = asyncHttpService.doGet( uri.build() );
        return httpFuture.getAsType( new TypeReference<List<WbVulnerabilityAsset>>() {}, "assets" );
    }


    /**
     * Get the general information about the asset.
     *
     * @param assetId The uuid of the asset.
     * @param options The vulnerability options
     * @return the general information about the asset
     * @throws TenableIoException the tenable IO exception
     */
    public WbAssetInfo assetInfo( String assetId, FilteringOptions options ) throws TenableIoException {
        UriBuilderHelper uri = createBaseUriBuilder( "/workbenches/assets/" + assetId + "/info" );
        addFilteringOptions( options, uri );
        HttpFuture httpFuture = asyncHttpService.doGet( uri.build() );
        return httpFuture.getAsType( WbAssetInfo.class, "info" );
    }

    /**
     * List all the assets recorded.
     *
     * @param assetId The uuid of the asset.
     * @param range The date range in days for activity returned.
     * @return the list of activity
     * @throws TenableIoException the tenable IO exception
     */
    public List<WbAssetActivity> assetActivityList( String assetId, int range ) throws TenableIoException {
        UriBuilderHelper uri = createBaseUriBuilder( "/workbenches/assets/" + assetId + "/activity" );

        HttpFuture httpFuture = asyncHttpService.doGet( uri.addParameter( "range", ( (Integer) range ).toString() ).build() );
        return httpFuture.getAsType( new TypeReference<List<WbAssetActivity>>() {}, "activity" );
    }




    /**
     * List all the vulnerabilities recorded for a given asset.
     *
     * @param assetId The uuid of the asset
     * @param options The vulnerability options
     * @return the list of vulnerabilities recorded for a given asset
     * @throws TenableIoException the tenable IO exception
     */
    public List<ScanVulnerability> assetVulnerabilities( String assetId, FilteringOptions options )
            throws TenableIoException {
        UriBuilderHelper uri = createBaseUriBuilder( "/workbenches/assets/" + assetId + "/vulnerabilities" );
        addFilteringOptions( options, uri );
        HttpFuture httpFuture = asyncHttpService.doGet( uri.build() );
        return httpFuture.getAsType( new TypeReference<List<ScanVulnerability>>() {}, "vulnerabilities" );
    }


    /**
     * Get the details for a vulnerability recorded on a given asset.
     *
     * @param assetId  The uuid of the asset
     * @param pluginId The id of the plugin
     * @param options  The vulnerability options
     * @return the details for a vulnerability recorded on a given asset
     * @throws TenableIoException the tenable IO exception
     */
    public WbVulnerabilityInfo vulnerabilityInfo( String assetId, String pluginId, FilteringOptions options )
            throws TenableIoException {
        UriBuilderHelper uri = createBaseUriBuilder( "/workbenches/assets/" + assetId + "/vulnerabilities/" +
                pluginId + "/info" );
        addFilteringOptions( options, uri );
        HttpFuture httpFuture = asyncHttpService.doGet( uri.build() );
        return httpFuture.getAsType( WbVulnerabilityInfo.class, "info" );
    }


    /**
     * Get the vulnerability outputs for a plugin recorded on a given asset.
     *
     * @param assetId  The uuid of the asset
     * @param pluginId The id of the plugin.
     * @param options  The vulnerability options
     * @return the vulnerability outputs for a plugin recorded on a given asset
     * @throws TenableIoException the tenable IO exception
     */
    public List<WbVulnerabilityOutputResult> assetVulnerabilityOutput( String assetId, String pluginId, FilteringOptions options ) throws TenableIoException {
        UriBuilderHelper uri = createBaseUriBuilder( "/workbenches/assets/" + assetId + "/vulnerabilities/" +
                pluginId + "/outputs" );
        addFilteringOptions( options, uri );
        HttpFuture httpFuture = asyncHttpService.doGet( uri.build() );
        return httpFuture.getAsType( new TypeReference<List<WbVulnerabilityOutputResult>>() {}, "outputs" );
    }


    /**
     * Used to download a file that has been prepared for export.
     *
     * @param fileId          The unique identifier of the workbench report being downloaded. The value for this parameter can be obtained from the response of the initial export request
     * @param destinationFile the download file
     * @throws TenableIoException the tenable IO exception
     */
    public void exportDownload( int fileId, File destinationFile ) throws TenableIoException {
        HttpFuture httpFuture = asyncHttpService.doGetDownload( createBaseUriBuilder( "/workbenches/export/" +
                fileId + "/download" ).build(), destinationFile );
        httpFuture.get();
    }


    /**
     * Export the given workbench to a file. Once requested, the file can be downloaded using the export download method upon receiving a "ready" status from the export status method.
     *
     * @param options the export options
     * @return the file id for the download
     * @throws TenableIoException the tenable IO exception
     */
    public int exportRequest( ExportOptions options ) throws TenableIoException {
        UriBuilderHelper uri = createBaseUriBuilder( "/workbenches/export" );
        addExportOptions( options, uri );
        HttpFuture httpFuture = asyncHttpService.doGet( uri.build() );
        return httpFuture.getAsType( Integer.class, "file" );
    }


    /**
     * Used to monitor the status of a pending export. When an export has been requested, it is necessary to poll this endpoint until a "ready" status is returned, at which point the file is complete and can be downloaded using the export download endpoint.
     *
     * @param fileId the file id
     * @return the status string
     * @throws TenableIoException the tenable IO exception
     */
    public String exportStatus( int fileId ) throws TenableIoException {
        HttpFuture httpFuture = asyncHttpService.doGet( createBaseUriBuilder( "/workbenches/export/" + fileId + "/status" ).build() );
        return httpFuture.getAsType( String.class, "status" );
    }


    private void addExportOptions( ExportOptions options, UriBuilderHelper uri ) {
        if( options != null ) {
            if( options.getDateRange() != null ) {
                uri.addParameter( "date_range", options.getDateRange().toString() );
            }
            addFilterParams( options.getFilters(), uri );
            if( options.getSearchType() != null ) {
                uri.addParameter( "filter.search_type", options.getSearchType() );
            }
            if( options.getFormat() != null ) {
                uri.addParameter( "format", options.getFormat().getValue() );
            }
            if( options.getReport() != null ) {
                uri.addParameter( "report", options.getReport().getValue() );
            }
            if( options.getStartDate() != null ) {
                uri.addParameter( "start_date", options.getStartDate().toString() );
            }
            if( options.getChapter() != null ) {
                uri.addParameter( "chapter", options.getChapter() );
            }
            if( options.getMinimumVulnInfo() != null ) {
                uri.addParameter( "minimum_vuln_info", options.getMinimumVulnInfo().toString() );
            }
            if( options.getPluginId() != null ) {
                uri.addParameter( "plugin_id", options.getPluginId().toString() );
            }
            if( options.getAssetId() != null ) {
                uri.addParameter( "asset_id", options.getAssetId().toString() );
            }
        }
    }


    private void addFilteringOptions( FilteringOptions options, UriBuilderHelper uri ) {
        if( options != null ) {
            if( options.getDateRange() != null ) {
                uri.addParameter( "date_range", options.getDateRange().toString() );
            }
            addFilterParams( options.getFilters(), uri );
            if( options.getSearchType() != null ) {
                uri.addParameter( "filter.search_type", options.getSearchType().getValue() );
            }
        }
    }


    private void addExtendedFilteringOptions( ExtendedFilteringOptions options, UriBuilderHelper uri ) {
        if( options != null ) {
            if( options.getAge() != null &&  options.getAge() > 0 ) {
                uri.addParameter( "age", options.getAge().toString() );
            }
            if( options.isAuthenticated() != null ) {
                uri.addParameter( "authenticated", options.isAuthenticated().toString() );
            }
            if( options.getDateRange() != null ) {
                uri.addParameter( "date_range", options.getDateRange().toString() );
            }
            if( options.isExploitable() != null ) {
                uri.addParameter( "exploitable", options.isExploitable().toString() );
            }
            addFilterParams( options.getFilters(), uri );
            if( options.getSearchType() != null ) {
                uri.addParameter( "filter.search_type", options.getSearchType().getValue() );
            }
            if( options.isResolvable() != null ) {
                uri.addParameter( "resolvable", options.isResolvable().toString() );
            }
            if( options.getSeverity() != null ) {
                uri.addParameter( "severity", options.getSeverity().toString() );
            }
        }
    }


    private void addFilterParams( List<Filter> filters, UriBuilderHelper uri ) {
        if( filters != null && filters.size() > 0 ) {
            int count = 0;
            for( Filter filter : filters ) {
                uri.addParameter( "filter." + count + ".quality", filter.getQuality().getValue() );
                uri.addParameter( "filter." + count + ".filter", filter.getFilter() );
                uri.addParameter( "filter." + count + ".value", filter.getValue() );
                count++;
            }
        }
    }

}
