package com.tenable.io.examples;


import com.tenable.io.api.TenableIoClient;

import com.tenable.io.api.TestBase;
import com.tenable.io.api.folders.FolderRef;
import com.tenable.io.api.scans.ScanActivity;
import com.tenable.io.api.scans.ScanRef;
import com.tenable.io.api.scans.interfaces.RunnableScan;
import com.tenable.io.api.scans.interfaces.ScanBaseOp;
import com.tenable.io.api.scans.models.*;
import com.tenable.io.api.workbenches.models.FilterAssetField;
import com.tenable.io.core.exceptions.TenableIoErrorCode;
import com.tenable.io.core.exceptions.TenableIoException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.*;


/**
 * Copyright (c) 2017 Tenable Network Security, Inc.
 */
public class ScanWorkflowsExamples extends TestBase {
    @Test
    public void testScanWorkflows() throws Exception {
        String scanName = getNewTestScanName();

        TenableIoClient client = new TenableIoClient();

        //Create a scan.
        RunnableScan scan = client.getScanHelper().createScan( scanName, getScanTextTargets(), getScanTemplateName() );
        assertNotNull( scan );
        assertTrue( scanName.equals( scan.getName() ) );

        //retrieve a scan by id
        ScanRef scanB = client.getScanHelper().getScan( scan.getId() );
        assertNotNull( scanB );
        assertNotEquals( scan, scanB );
        assertTrue( scanB.getName().equals( scan.getName() ) );

        //Select scans by name.
        List<ScanRef> scansByName = client.getScanHelper().getScansByName( scanName );
        assertNotNull( scansByName );
        assertTrue( scansByName.size() > 0 );

        //Select scans by name with regular expression.
        List<ScanRef> scansByRegex = client.getScanHelper().getScansByRegex( "tioTestScan_.*?" );
        assertNotNull( scansByRegex );
        assertTrue( scansByRegex.size() > 0 );

        //Launch a scan, then download when scan is completed.
        //Note: The `download` method blocks until the scan is completed and the report is downloaded.
        File file = new File( "src/test/resources/test.pdf" );
        scan.launch().download( file, FileFormat.PDF );
        assertTrue( file.exists() );
        file.delete();

        //Launch a scan, pause it, resume it, then stop it.
        scan.launch().pause();
        assertTrue( scan.getStatus() == ScanStatus.PAUSED );
        scan.resume().stop();
        assertTrue( scan.getStatus() == ScanStatus.CANCELED );

        //Stop a running scan if it does not complete within a specific duration.
        long startTime = System.currentTimeMillis();
        scan.launch( false ).waitOrCancelAfter( 10 );
        assertTrue( System.currentTimeMillis() - startTime > 10000 );

        //Retrieve the history of a scan since a specific date or all.
        List<History> histories = scan.getHistory();
        assertNotNull( histories );
        assertTrue( histories.size() > 0 );
        SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy" );
        Date date = sdf.parse( "01/01/2017" );
        histories = scan.getHistory( date );
        assertNotNull( histories );
        assertTrue( histories.size() > 0 );

        //Download the report for a specific scan in history.
        File file2 = new File( "src/test/resources/test2.pdf" );
        scan.download( file2, histories.get( 0 ).getHistoryId(), FileFormat.PDF );
        assertTrue( file2.exists() );
        file2.delete();

        //Create a new scan by copying a scan.
        ScanBaseOp copy = scan.copy();
        assertTrue( copy.getId() != scan.getId() );
        assertTrue( copy.getStatus() == ScanStatus.EMPTY );

        //launch a scan later on a future date and time
        Date futureStartTime = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        futureStartTime = sdf1.parse("27/01/2018 15:35:00");
        scan.launch(futureStartTime, "US/pacific", getScanTextTargets());

        //Delete scans
        scan.delete();
        copy.delete();

        try {
            scan.getDetails();
            assertTrue( false );
        } catch( TenableIoException ex ) {
            assertTrue( ex.getErrorCode() == TenableIoErrorCode.NotFound );
        }

        try {
            copy.getDetails();
            assertTrue( false );
        } catch( TenableIoException ex ) {
            assertTrue( ex.getErrorCode() == TenableIoErrorCode.NotFound );
        }

        //Stop all scans
        //Note: Use with caution as this will stop all ongoing scans (including any automated test).
        //client.getScanHelper().stopAll();

    }


    @Test
    public void testRecentlyRunScans() throws Exception {
        TenableIoClient client = new TenableIoClient();
        Map<String, FilterAssetField> assets = new HashMap<String, FilterAssetField>() {{
            put( getScanTextTargets(), FilterAssetField.FQDN );
        }};

        // create a new scan and pause
        String scanName = getNewTestScanName();
        RunnableScan scan = client.getScanHelper().createScan( scanName, getScanTextTargets(), getScanTemplateName() );
        scan.launch().pause();
        ScanRef scanRef = client.getScanHelper().getScan( scan.getId() );
        History pausedScanHistory = scanRef.getLastHistory();

        // assert paused scan shows up in scan activities
        List<ScanActivity> scanActivities = client.getScanHelper().getActivities( assets, 1 );
        Boolean isPausedScanHistoryFound = false;
        for( ScanActivity scanActivity : scanActivities ) {
            if( scanActivity.getHistoryUuid().equals( pausedScanHistory.getUuid() ) ) {
                isPausedScanHistoryFound = true;
                break;
            }
        }
        assert( isPausedScanHistoryFound );

        // complete the paused scan
        scan.resume().waitUntilStopped();
        scanRef = client.getScanHelper().getScan( scan.getId() );
        History completedScanHistory = scanRef.getLastHistory();

        // assert the completed scan shows up in scan activities
        scanActivities = client.getScanHelper().getActivities( assets, 1 );
        Boolean isCompletedScanHistoryFound = false;
        for( ScanActivity scanActivity : scanActivities ) {
            if( scanActivity.getHistoryUuid().equals( completedScanHistory.getUuid() ) ) {
                isCompletedScanHistoryFound = true;
                break;
            }
        }
        assert( isCompletedScanHistoryFound );
    }


    @Test
    public void testFolderWorkflows() throws Exception {
        String scanName = getNewTestScanName();

        // Instantiate an instance of the NessusClient.
        //TenableIoClient apiClient = new TenableIoClient( "Your access key", "Your secret key" );

        // TODO remove next and uncomment previous in published version
        TenableIoClient client = new TenableIoClient();

        //create a folder
        String testFolderName = getNewTestFolderName();
        FolderRef folder = client.getFolderHelper().create( testFolderName );
        assertTrue( folder.name().equals( testFolderName ) );
        assertTrue( folder.type().equals( client.getFolderHelper().TYPE_CUSTOM ) );
        assertNotNull( folder.info() );

        //create a scan
        RunnableScan scan = client.getScanHelper().createScan( scanName, getScanTextTargets(), getScanTemplateName() );

        //move scan to new folder
        assertTrue( folder.getId() != scan.getFolder().getId() );
        scan.moveTo( folder );
        assertTrue( folder.getId() == scan.getFolder().getId() );

        //move scan to trash
        scan.trash();
        assertTrue( folder.getId() != scan.getFolder().getId() );

        //move scan back to folder using a different method
        folder.add( scan );
        assertTrue( folder.getId() == scan.getFolder().getId() );

        //move scan to trash using a different method
        FolderRef trash = client.getFolderHelper().trashFolder();
        trash.add( scan );
        assertTrue( trash.getId() == scan.getFolder().getId() );

        //Stop all scans in folder.
        folder.add( scan );
        assertTrue( folder.getId() == scan.getFolder().getId() );
        scan.launch();
        assertTrue( !scan.isStopped() );
        folder.stopScans();
        assertTrue( scan.isStopped() );

        //add a scan to a folder using id
        scan.trash();
        folder.add( scan.getId() );
        assertTrue( folder.getId() == scan.getFolder().getId() );

        scan.delete();
        try {
            scan.getDetails();
            assertTrue( false );
        } catch( TenableIoException ex ) {
            assertTrue( ex.getErrorCode() == TenableIoErrorCode.NotFound );
        }

        folder.delete();
        assertNull( client.getFolderHelper().id( folder.getId() ) );
    }


    @Before
    @After
    public void cleanup() throws TenableIoException {
        TenableIoClient apiClient = new TenableIoClient();

        deleteTestFolders( apiClient );
    }
}
