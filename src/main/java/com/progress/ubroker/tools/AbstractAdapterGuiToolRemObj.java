// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools;

import com.progress.common.networkevents.EventBroker;
import com.progress.common.log.IFileRef;
import com.progress.common.networkevents.IEventListener;
import java.rmi.RemoteException;
import com.progress.common.log.IRemoteFile;
import com.progress.chimera.common.IChimeraHierarchy;

public abstract class AbstractAdapterGuiToolRemObj extends AbstractGuiToolRemObj implements IChimeraHierarchy, IYodaSharedResources, IRemoteFile
{
    public AbstractAdapterGuiToolRemObj(final IYodaRMI yodaRMI, final String s, final String s2, final String s3, final String s4, final String s5, final String s6, final String s7) throws RemoteException {
        super(yodaRMI, s, s2, s3, s4, s5, s6, s7);
    }
    
    public abstract String getDisplayName() throws RemoteException;
    
    private void logException(final String str) {
        UBToolsMsg.logException("Exception in AbstractAdapterGuiToolRemObj." + str);
    }
    
    private void logException(final String str, final String str2) {
        UBToolsMsg.logException("Exception in AbstractAdapterGuiToolRemObj." + str + ":" + str2);
    }
    
    private synchronized IFileRef openFileGetRefObj(final String s, final IEventListener eventListener) {
        IFileRef logRefObj = null;
        String str = null;
        if (s == null || s.equals(super.m_svcName)) {
            return null;
        }
        try {
            str = this.getLogFiles(super.m_propGroupPath + "." + super.m_svcName).get(s);
            final UBRemoteLogReader ubRemoteLogReader = super.m_logFileReaders.get(s);
            if (ubRemoteLogReader != null) {
                try {
                    ubRemoteLogReader.closeFile();
                }
                catch (Exception ex) {
                    this.logException("openFileGetRefObj", "problem closing the previously opened log file handle");
                }
            }
            final UBRemoteLogReader value = new UBRemoteLogReader(s, str, (EventBroker)this.getEventBroker(), this.getEventStream(str), eventListener);
            super.m_logFileReaders.put(s, value);
            if (value != null) {
                logRefObj = value.getLogRefObj();
            }
        }
        catch (Exception ex2) {
            this.logException("openFileGetRefObj", "fail to open log file " + str + " for " + s);
        }
        return logRefObj;
    }
}
