package com.tenable.io.api.policies.models;


import java.util.ArrayList;
import java.util.List;


/**
 * Copyright (c) 2017 Tenable Network Security, Inc.
 */
public class PolicyAuditFeed {
    private List<PolicyAudit> add;


    /**
     * Gets list of policy audits.
     *
     * @return the list of policy audits
     */
    public List<PolicyAudit> getAdd() {
        return add;
    }


    /**
     * Sets the list of policy audits.
     *
     * @param add the list of policy audits
     */
    public void setAdd( List<PolicyAudit> add ) {
        this.add = add;
    }


    /**
     * Adds a policy audits to the list.
     *
     * @param policyAudit the PolicyAudit object to add to the list
     */
    public void addPolicyAudit( PolicyAudit policyAudit ) {
        if( add == null ) {
            add = new ArrayList<>();
        }

        add.add( policyAudit );
    }
}
