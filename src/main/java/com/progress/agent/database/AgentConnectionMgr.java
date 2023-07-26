// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.agent.database;

import java.util.Enumeration;
import java.util.Hashtable;

public class AgentConnectionMgr extends Hashtable
{
    private int m_licenseCount;
    private boolean m_isLicenseCountUnlimited;
    
    public AgentConnectionMgr() {
        this.m_licenseCount = 0;
        this.m_isLicenseCountUnlimited = false;
    }
    
    public int getConnectionCount() {
        int n = 0;
        final Enumeration agentDatabases = AgentPlugIn.getAgentDatabases();
        while (agentDatabases.hasMoreElements()) {
            final AgentDatabase agentDatabase = agentDatabases.nextElement();
            if (!agentDatabase.getDisplayName(false).equalsIgnoreCase("FathomTrendDatabase") && agentDatabase.isRunning()) {
                ++n;
            }
        }
        return n;
    }
    
    public int getLicenseCountReserved() {
        int n = 0;
        final Enumeration agentDatabases = AgentPlugIn.getAgentDatabases();
        while (agentDatabases.hasMoreElements()) {
            final AgentDatabase agentDatabase = agentDatabases.nextElement();
            if (agentDatabase.getMonitorLicensed() && !agentDatabase.getDisplayName(false).equalsIgnoreCase("FathomTrendDatabase")) {
                ++n;
            }
        }
        return n;
    }
    
    public boolean getConnectionsAvail(final AgentDatabase agentDatabase) {
        boolean b = false;
        if (this.m_isLicenseCountUnlimited) {
            b = true;
        }
        else if (agentDatabase == null) {
            b = false;
        }
        else if (agentDatabase.getDisplayName(false).equalsIgnoreCase("FathomTrendDatabase")) {
            b = true;
        }
        else if (agentDatabase.getMonitorLicensed() && this.getConnectionCount() < this.getLicenseCount()) {
            b = true;
        }
        return b;
    }
    
    public boolean isLicenseCountUnlimited() {
        return this.m_isLicenseCountUnlimited;
    }
    
    public int getLicenseCount() {
        return this.m_licenseCount;
    }
    
    public void setLicenseCount(final int licenseCount) {
        if (licenseCount == -1) {
            this.m_isLicenseCountUnlimited = true;
            this.m_licenseCount = Integer.MAX_VALUE;
        }
        else {
            this.m_licenseCount = licenseCount;
        }
    }
}
