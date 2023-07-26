// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.util;

import com.progress.common.networkevents.IEventBroker;
import java.rmi.RemoteException;
import com.progress.common.networkevents.IEventObject;
import com.progress.ubroker.management.events.EUbrokerLogFileNameChanged;
import com.progress.common.ehnlog.IAppLogger;
import com.progress.common.ehnlog.ILogEvntHandler;

public class LogEvntHandler implements ILogEvntHandler
{
    private ubProperties m_properties;
    private IAppLogger m_log;
    private String m_logType;
    
    public LogEvntHandler(final ubProperties properties, final IAppLogger log) {
        this.m_properties = properties;
        this.m_log = log;
        this.m_logType = EUbrokerLogFileNameChanged.MAIN_LOG_FILE;
    }
    
    public void setMainLog(final boolean b) {
        if (b) {
            this.m_logType = EUbrokerLogFileNameChanged.MAIN_LOG_FILE;
        }
        else {
            this.m_logType = EUbrokerLogFileNameChanged.SERVER_LOG_FILE;
        }
    }
    
    public void sendFileNameChangedEvent(final String s) {
        final IEventBroker adminServerEventBroker = ubThread.findAdminServerEventBroker(this.m_properties.rmiURL, this.m_properties.brokerName, this.m_log);
        if (adminServerEventBroker != null) {
            try {
                adminServerEventBroker.postEvent(new EUbrokerLogFileNameChanged(this.m_properties.brokerName, s, this.m_logType, this.m_properties.canonicalName));
                if (this.m_log.ifLogVerbose(1L, 0)) {
                    this.m_log.logVerbose(0, "Posted EUbrokerLogFileNameChanged for broker: " + this.m_properties.brokerName + " (" + s + ")");
                }
            }
            catch (RemoteException ex) {
                this.m_log.logError("Failed to post EUbrokerLogFileNameChanged event for " + this.m_properties.brokerName + " file: " + s + " (" + ex.toString() + ")");
            }
        }
    }
}
