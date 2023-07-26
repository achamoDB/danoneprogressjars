// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools;

import com.progress.ubroker.management.IRemoteManager;
import java.util.Hashtable;
import com.progress.nameserver.INSRemote;
import com.progress.nameserver.NameServer;

public class NSControlCmd extends SvcControlCmd implements IBTMsgCodes
{
    public NSControlCmd(final SvcStartArgsPkt svcStartArgsPkt, final String s, final String s2, final String s3) {
        super(svcStartArgsPkt, s, s2, s3);
    }
    
    public NameServer.AppService[] getDetailStatus() {
        try {
            this.fetchSvcRMIConnection(SvcControlCmd.m_getStatus_retries);
            if (super.m_state == 1L) {
                return ((INSRemote)super.m_svcAdminRemote).getDetailStatus();
            }
            UBToolsMsg.logMsg(UBToolsMsg.getMsg(7094295313015381979L, new Object[] { "NSControlCmd" }));
        }
        catch (Exception ex) {
            UBToolsMsg.logException(UBToolsMsg.getMsg(7094295313015381980L, new Object[] { "NSControlCmd", super.m_svcName, ex.toString() }));
        }
        return null;
    }
    
    public Object[][] getSummaryStatus() {
        try {
            this.fetchSvcRMIConnection(SvcControlCmd.m_getStatus_retries);
            if (super.m_state == 1L) {
                return ((INSRemote)super.m_svcAdminRemote).getSummaryStatus();
            }
        }
        catch (Exception ex) {
            UBToolsMsg.logException(UBToolsMsg.getMsg(7094295313015381981L, new Object[] { "NSControlCmd", ex.toString() }));
        }
        return null;
    }
    
    public Hashtable getData(final String[] array) {
        Hashtable hashtable = null;
        try {
            this.fetchSvcRMIConnection();
            if (super.m_state == 1L) {
                hashtable = (Hashtable)((IRemoteManager)super.m_svcAdminRemote).getData(array);
            }
        }
        catch (Exception ex) {
            UBToolsMsg.logException(UBToolsMsg.getMsg(7094295313015381981L, new Object[] { "NSControlCmd", ex.toString() }));
        }
        return hashtable;
    }
}
