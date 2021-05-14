package com.tenable.io.api;


import com.tenable.io.api.groups.models.Group;
import com.tenable.io.api.users.models.User;
import com.tenable.io.api.users.models.UserRole;

import com.tenable.io.core.exceptions.TenableIoException;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;


/**
 * Copyright (c) 2017 Tenable Network Security, Inc.
 */
public class GroupsApiClientTest extends TestBase {
    @Before
    public void preChecks() throws TenableIoException {
        deleteTestData();
    }


    @Test
    public void testGroup() throws Exception {
        //create new group
        String testGroupsName = getNewTestGroupName();
        apiClient.getUserGroupsApi().create( testGroupsName );

        //get list of groups
        List<Group> result = apiClient.getUserGroupsApi().list();
        int groupsCount = result.size();
        assertNotNull( result );
        assertTrue( groupsCount > 0 );

        //get my created group
        Group myGroup = null;
        for( Group group : result ) {
            if( group.getName().equals( testGroupsName ) ) {
                myGroup = group;
                break;
            }
        }
        assertNotNull( myGroup );

        //create new user
        User newUser = apiClient.getUsersApi().create( getTestUsername(0 ), "Password#12345",
                UserRole.BASIC, "Tenableio Test", getTestUsername(0 ), "local" );

        assertNotNull( newUser );

        //add user to group
        apiClient.getUserGroupsApi().addUserToGroup( myGroup.getId(), newUser.getId() );

        //verify user is added to group
        List<User> groupUsers = apiClient.getUserGroupsApi().listUsers( myGroup.getId() );
        assertNotNull( groupUsers );
        assertTrue( groupUsers.size() > 0 );

        boolean userAdded = false;
        for( User user : groupUsers ) {
            if( user.getId() == newUser.getId() ) {
                userAdded = true;
            }
        }
        assertTrue( userAdded );

        //remove user from group
        apiClient.getUserGroupsApi().removeUserFromGroup( myGroup.getId(), newUser.getId() );

        //verify user is remove from group
        groupUsers = apiClient.getUserGroupsApi().listUsers( myGroup.getId() );
        if( groupUsers != null ) {
            boolean userRemoved = true;
            for( User user : groupUsers ) {
                if( user.getId() == newUser.getId() ) {
                    userRemoved = false;
                }
            }
            assertTrue( userRemoved );
        }

        //delete group
        apiClient.getUserGroupsApi().delete( myGroup.getId() );

        //delete user
        apiClient.getUsersApi().delete( ( newUser.getId() ) );

    }


    @Test
    public void testGroupEdit() throws Exception {
        //create new group
        String testGroupsName = getNewTestGroupName();
        apiClient.getUserGroupsApi().create( testGroupsName );

        //get list of groups
        List<Group> result = apiClient.getUserGroupsApi().list();
        int groupsCount = result.size();
        assertNotNull( result );
        assertTrue( groupsCount > 0 );

        //get my created group
        Group myGroup = null;
        for( Group group : result ) {
            if( group.getName().equals( testGroupsName ) ) {
                myGroup = group;
                break;
            }
        }
        assertNotNull( myGroup );

        //edit Group name
        String newTestGroupsName = getNewTestGroupName();
        apiClient.getUserGroupsApi().edit( myGroup.getId(), newTestGroupsName );

        //get list of groups
        result = apiClient.getUserGroupsApi().list();

        //get my created group
        myGroup = null;
        for( Group group : result ) {
            if( group.getName().equals( newTestGroupsName ) ) {
                myGroup = group;
                break;
            }
        }
        assertNotNull( myGroup );

        //delete group
        apiClient.getUserGroupsApi().delete( myGroup.getId() );

        //verify group is deleted
        result = apiClient.getUserGroupsApi().list();
        if( result != null ) {
            boolean groupRemoved = true;
            for( Group group : result ) {
                if( group.getId() == myGroup.getId() ) {
                    groupRemoved = false;
                    break;
                }
            }
            assertTrue( groupRemoved );
        }

    }


    private void deleteTestData() throws TenableIoException {
        deleteTestGroups( apiClient );
    }
}
