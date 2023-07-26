// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools;

import com.progress.common.networkevents.IEventObject;
import com.progress.ubroker.management.events.EAbnormalShutdownEvent;
import com.progress.ubroker.management.events.COpenEdgeManagementContent;
import com.progress.common.util.Environment;

public class BrokerAbnormalShutdownWatcher extends Thread
{
    private String m_bName;
    private String m_fullPropPath;
    private String m_cName;
    private Environment m_Env;
    private int m_pid;
    private boolean isWatching;
    private KeepWatchingFlag watchFlag;
    
    public BrokerAbnormalShutdownWatcher(final String s, final String bName, final String s2, final String cName) {
        this.m_pid = 0;
        this.watchFlag = new KeepWatchingFlag();
        this.setName("BrokerAbnormalShutdownWatcher: " + s);
        this.m_Env = new Environment();
        this.m_fullPropPath = s;
        this.m_bName = bName;
        this.m_cName = cName;
        try {
            this.m_pid = new Integer(s2);
        }
        catch (Exception ex) {}
        this.isWatching = false;
    }
    
    public boolean isWatching() {
        return this.isWatching;
    }
    
    public int getWatchingPid() {
        return this.m_pid;
    }
    
    public void setKeepWatchingFlag(final boolean keepWatching) {
        UBToolsMsg.logMsg(5, this.toString() + " (PID " + this.m_pid + ")" + " keepWatching changed from " + this.watchFlag.isKeepWatching() + "..");
        this.watchFlag.setKeepWatching(keepWatching);
        this.reset();
        UBToolsMsg.logMsg(5, "..to " + this.watchFlag.isKeepWatching());
    }
    
    public void run() {
        this.isWatching = true;
        while (this.m_Env != null) {
            if (this.m_pid <= 0) {
                break;
            }
            if (!this.watchFlag.isKeepWatching()) {
                break;
            }
            if (this.m_Env.query_PID(this.m_pid, false) <= 0) {
                this.postBrokerAbnormalShutdownEvent();
            }
            try {
                Thread.sleep(8000L);
            }
            catch (Exception ex) {
                if (this.m_Env.query_PID(this.m_pid, false) > 0) {
                    continue;
                }
                this.postBrokerAbnormalShutdownEvent();
            }
        }
    }
    
    private void postBrokerAbnormalShutdownEvent() {
        try {
            final EAbnormalShutdownEvent eAbnormalShutdownEvent = new EAbnormalShutdownEvent(AbstractUbrokerPlugin.m_iAdministrationServer, new COpenEdgeManagementContent(this.m_bName, new Object[] { this.m_fullPropPath }));
            eAbnormalShutdownEvent.setSource(this.m_cName);
            UBToolsMsg.logMsg(5, "About to post abnormal shutdown event ## keepWatching = " + this.watchFlag.isKeepWatching());
            if (this.watchFlag.isKeepWatching()) {
                AbstractGuiPlugin.getEventBroker().postEvent(eAbnormalShutdownEvent);
            }
            UBToolsMsg.logMsg(5, "EAbnormalShutdownEvent for " + this.m_bName + " is posted..");
            this.clearAll();
        }
        catch (Exception ex) {
            UBToolsMsg.logException("Failed to post EAbnormalShutdownEvent event for " + this.m_bName + "(" + ex.toString() + ")");
            this.clearAll();
        }
    }
    
    private void clearAll() {
        this.watchFlag.setKeepWatching(false);
    }
    
    private void reset() {
    }
}
