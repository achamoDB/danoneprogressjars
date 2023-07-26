// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools;

import java.rmi.RemoteException;
import com.progress.common.networkevents.IEventListener;
import com.progress.common.networkevents.IEventStream;
import com.progress.common.networkevents.EventBroker;
import com.progress.common.log.IFileRef;
import com.progress.common.log.RemoteLogFileReader;

public class UBRemoteLogReader extends RemoteLogFileReader
{
    String m_logFileFullSpec;
    String m_displayName;
    IFileRef m_logRefObj;
    EventBroker m_evtBrkr;
    IEventStream m_evtStream;
    IEventListener m_callback;
    
    public UBRemoteLogReader(final String logDisplayName, final String logFileFullSpec, final EventBroker evtBrkr, final IEventStream evtStream, final IEventListener callback) throws RemoteException {
        super(evtBrkr, evtStream);
        this.m_logFileFullSpec = null;
        this.m_displayName = null;
        this.m_logRefObj = null;
        this.m_evtBrkr = null;
        this.m_evtStream = null;
        this.m_callback = null;
        this.setLogDisplayName(logDisplayName);
        this.setLogFileFullSpec(logFileFullSpec);
        this.m_evtBrkr = evtBrkr;
        this.m_evtStream = evtStream;
        this.m_callback = callback;
    }
    
    public void setLogFileFullSpec(final String logFileFullSpec) {
        this.m_logFileFullSpec = logFileFullSpec;
    }
    
    public String getLogFileFullSpec() {
        return this.m_logFileFullSpec;
    }
    
    public void setLogDisplayName(final String displayName) {
        this.m_displayName = displayName;
    }
    
    public String getLogDisplayName(final String s) {
        return this.m_displayName;
    }
    
    public IFileRef openFile() throws RemoteException {
        try {
            return this.m_logRefObj = super.openFile(this.m_logFileFullSpec, this.m_callback);
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    public IFileRef getLogRefObj() {
        try {
            return this.openFile();
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    public void closeFile(final IFileRef fileRef) {
        try {
            super.closeFile(fileRef);
            if (this.m_evtStream != null) {
                this.m_evtBrkr.closeEventStream(this.m_evtStream);
            }
            this.m_evtBrkr = null;
            this.m_evtStream = null;
        }
        catch (Exception ex) {
            UBToolsMsg.logException("Exception while closing log file " + this.m_logFileFullSpec + ": " + ex.toString());
        }
    }
    
    public void closeFile() {
        if (this.m_logRefObj != null) {
            this.closeFile(this.m_logRefObj);
            this.m_logRefObj = null;
        }
    }
}
