// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools;

import com.progress.common.networkevents.EventBroker;
import com.progress.common.log.IFileRef;
import com.progress.common.networkevents.IEventListener;
import com.progress.common.networkevents.IEventStream;
import java.rmi.server.RemoteStub;
import com.progress.common.networkevents.IEventBroker;
import com.progress.common.property.IPropertyManagerRemote;
import com.progress.chimera.util.SerializableEnumeration;
import java.util.Vector;
import java.util.Enumeration;
import java.rmi.RemoteException;
import java.util.Hashtable;
import com.progress.common.log.IRemoteFile;
import com.progress.chimera.common.IChimeraHierarchy;
import com.progress.chimera.common.ChimeraNamedObject;

public abstract class AbstractGuiToolRemObj extends ChimeraNamedObject implements IChimeraHierarchy, IYodaSharedResources, IRemoteFile
{
    public String m_svcName;
    public String m_propGroupPath;
    public String m_rmiHost;
    public String m_rmiPort;
    public String m_adminSrvrURL;
    public String m_osName;
    public String m_logInUser;
    public String m_loginUserPwd;
    public IYodaRMI m_yodaRMIGlue;
    public Hashtable m_logFileReaders;
    
    public AbstractGuiToolRemObj(final IYodaRMI yodaRMIGlue, final String svcName, final String propGroupPath, final String logInUser, final String loginUserPwd, final String rmiHost, final String rmiPort, final String adminSrvrURL) throws RemoteException {
        super(makeLongName(propGroupPath, svcName));
        this.m_svcName = null;
        this.m_propGroupPath = null;
        this.m_rmiHost = null;
        this.m_rmiPort = null;
        this.m_adminSrvrURL = null;
        this.m_osName = null;
        this.m_logInUser = null;
        this.m_loginUserPwd = null;
        this.m_yodaRMIGlue = null;
        this.m_logFileReaders = null;
        this.m_yodaRMIGlue = yodaRMIGlue;
        this.m_svcName = svcName;
        this.m_propGroupPath = propGroupPath;
        this.m_osName = this.getOsName();
        this.m_rmiHost = rmiHost;
        this.m_rmiPort = rmiPort;
        this.m_adminSrvrURL = adminSrvrURL;
        this.m_logInUser = logInUser;
        this.m_loginUserPwd = loginUserPwd;
        this.m_logFileReaders = new Hashtable();
    }
    
    static String makeLongName(final String s, final String s2) {
        return adjustName(s) + "." + adjustName(s2);
    }
    
    public static String adjustName(final String s) {
        return s.trim().toLowerCase();
    }
    
    public Enumeration getChildren() throws RemoteException {
        return new SerializableEnumeration(new Vector());
    }
    
    public String getHelpMapFile() throws RemoteException {
        return null;
    }
    
    public String getApplicationName() throws RemoteException {
        return null;
    }
    
    private String getOsName() {
        return System.getProperty("os.name");
    }
    
    public String getMMCClientClass() throws RemoteException {
        return null;
    }
    
    public IPropertyManagerRemote getRPM() throws RemoteException {
        return AbstractGuiPlugin.getRPM();
    }
    
    public String getPropertyFilename() throws RemoteException {
        return AbstractGuiPlugin.getPropertyFilename();
    }
    
    public String getSchemaFilename() throws RemoteException {
        return AbstractGuiPlugin.getSchemaFilename();
    }
    
    public String[] getSchemaPropFnList() throws RemoteException {
        return AbstractGuiPlugin.getSchemaPropFnList();
    }
    
    public IEventBroker getEventBroker() throws RemoteException {
        return AbstractGuiPlugin.getEventBroker();
    }
    
    public IYodaRMI getRemoteManageObject() throws RemoteException {
        return this.m_yodaRMIGlue;
    }
    
    public String getPropGroupPath() throws RemoteException {
        return this.m_propGroupPath;
    }
    
    public String getSvcName() throws RemoteException {
        return this.m_svcName;
    }
    
    public String getAdminSrvrOSName() throws RemoteException {
        return this.m_osName;
    }
    
    public String getAdminSrvrHost() throws RemoteException {
        return this.m_rmiHost;
    }
    
    public String getAdminSrvrPort() throws RemoteException {
        return this.m_rmiPort;
    }
    
    public String getAdminSrvrURL() throws RemoteException {
        return this.m_adminSrvrURL;
    }
    
    public String[] getLoginUserInfo() throws RemoteException {
        return new String[] { this.m_logInUser, this.m_loginUserPwd };
    }
    
    public RemoteStub getRemStub() throws RemoteException {
        return null;
    }
    
    public String getAdminServerIPAddr() throws RemoteException {
        return AbstractGuiPlugin.getAdminServerIPAddr();
    }
    
    public boolean getRscLookedUp(final String s) throws RemoteException {
        return AbstractGuiPlugin.getRscLookedUp(s);
    }
    
    public void regRscLookedUp(final String s) throws RemoteException {
        AbstractGuiPlugin.regRscLookedUp(s);
    }
    
    public Hashtable getLogFiles(final String str) throws RemoteException {
        if (str == null || str.length() == 0) {
            return null;
        }
        try {
            return ((IYodaSharedResources)this.m_yodaRMIGlue).getLogFiles(str);
        }
        catch (Exception ex) {
            this.logException("getLogFiles", "fail to get log filename for " + str + "( " + ex.toString() + ")");
            return null;
        }
    }
    
    public IEventStream getEventStream(final String s) throws RemoteException {
        try {
            return this.getEventBroker().openEventStream("EventStream_For_LogReader_For" + s);
        }
        catch (Exception ex) {
            UBToolsMsg.logException("Failed to open event stream for " + s + ": " + ex.toString());
            return null;
        }
    }
    
    public IFileRef openFile(final String s, final IEventListener eventListener) throws RemoteException {
        return this.openFileGetRefObj(s, eventListener);
    }
    
    public abstract String getDisplayName() throws RemoteException;
    
    private void logException(final String str) {
        UBToolsMsg.logException("Exception in AbstractGuiToolRemObj." + str);
    }
    
    private void logException(final String str, final String str2) {
        UBToolsMsg.logException("Exception in AbstractGuiToolRemObj." + str + ":" + str2);
    }
    
    private synchronized IFileRef openFileGetRefObj(final String s, final IEventListener eventListener) {
        IFileRef logRefObj = null;
        String str = null;
        if (s == null || s.equals(this.m_svcName)) {
            return null;
        }
        try {
            str = this.getLogFiles(this.m_propGroupPath + "." + this.m_svcName).get(s);
            final UBRemoteLogReader ubRemoteLogReader = this.m_logFileReaders.get(s);
            if (ubRemoteLogReader != null) {
                try {
                    ubRemoteLogReader.closeFile();
                }
                catch (Exception ex) {
                    this.logException("openFileGetRefObj", "problem closing the previously opened log file handle");
                }
            }
            final UBRemoteLogReader value = new UBRemoteLogReader(s, str, (EventBroker)this.getEventBroker(), this.getEventStream(str), eventListener);
            this.m_logFileReaders.put(s, value);
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
