// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools.adapter;

import com.progress.ubroker.tools.UBToolsMsg;
import com.progress.ubroker.tools.IUBRemote;
import com.progress.ubroker.tools.SvcControlCmd;
import java.util.Enumeration;
import com.progress.ubroker.tools.SvcStartArgsPkt;
import com.progress.ubroker.tools.IBTMsgCodes;

public class AdapterControlCmd extends AdapterSvcControlCmd implements IBTMsgCodes
{
    public AdapterControlCmd(final SvcStartArgsPkt svcStartArgsPkt, final String s, final String s2, final String s3) {
        super(svcStartArgsPkt, s, s2, s3);
    }
    
    public Enumeration getDetailStatusRSColumnCaptions() {
        Enumeration detailStatusRSFieldLabels = null;
        try {
            this.fetchSvcRMIConnection(SvcControlCmd.m_getStatus_retries);
            if (super.m_state == 1L) {
                detailStatusRSFieldLabels = ((IUBRemote)super.m_svcAdminRemote).getDetailStatusRSFieldLabels();
            }
        }
        catch (Exception ex) {
            this.logException(7094295313015382003L, super.m_svcName, ex);
        }
        return detailStatusRSFieldLabels;
    }
    
    public Enumeration getDetailStatusRS() {
        Enumeration statusArray = null;
        try {
            this.fetchSvcRMIConnection(SvcControlCmd.m_getStatus_retries);
            if (super.m_state == 1L) {
                statusArray = ((IUBRemote)super.m_svcAdminRemote).getStatusArray(1);
            }
            else {
                UBToolsMsg.logMsg(UBToolsMsg.getMsg(7094295313015382002L, new Object[] { "JavaSvcControlCmd" }));
            }
        }
        catch (Exception ex) {
            this.logException(7094295313015382001L, super.m_svcName, ex);
        }
        return statusArray;
    }
    
    public Enumeration getSummaryStatusRSFieldLabels() {
        Enumeration summaryStatusRSFieldLabels = null;
        try {
            this.fetchSvcRMIConnection(SvcControlCmd.m_getStatus_retries);
            if (super.m_state == 1L) {
                summaryStatusRSFieldLabels = ((IUBRemote)super.m_svcAdminRemote).getSummaryStatusRSFieldLabels();
            }
        }
        catch (Exception ex) {
            this.logException(7094295313015381995L, "getSummaryStatusRSFieldLabels", ex);
        }
        return summaryStatusRSFieldLabels;
    }
    
    public Enumeration getSummaryStatusRS() {
        Enumeration summaryStatusRSData = null;
        try {
            this.fetchSvcRMIConnection(SvcControlCmd.m_getStatus_retries);
            if (super.m_state == 1L) {
                summaryStatusRSData = ((IUBRemote)super.m_svcAdminRemote).getSummaryStatusRSData();
            }
        }
        catch (Exception ex) {
            this.logException(7094295313015381995L, "getSummaryStatusRS", ex);
        }
        return summaryStatusRSData;
    }
    
    public int trimSrvrBy(final int n) {
        int trimBy = -1;
        try {
            this.fetchSvcRMIConnection();
            if (super.m_state == 1L) {
                trimBy = ((IUBRemote)super.m_svcAdminRemote).trimBy(n);
                if (trimBy == 1) {
                    trimBy = -1;
                }
            }
        }
        catch (Exception ex) {
            this.logException(7094295313015381995L, "trimSrvrBy", ex);
        }
        return trimBy;
    }
    
    public int addNewSrvrs(final int n) {
        int startServers = -1;
        try {
            this.fetchSvcRMIConnection();
            if (super.m_state == 1L) {
                startServers = ((IUBRemote)super.m_svcAdminRemote).startServers(n);
                if (startServers == 1) {
                    startServers = -1;
                }
            }
        }
        catch (Exception ex) {
            this.logException(7094295313015381995L, "addNewSrvrs", ex);
        }
        return startServers;
    }
    
    private void logException(final long n, final String s, final Throwable t) {
        UBToolsMsg.logException(UBToolsMsg.getMsg(n, new Object[] { "JavaSvcControlCmd", s, t.toString() }));
    }
}
