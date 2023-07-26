// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.agent.smdatabase;

import java.util.ResourceBundle;
import com.progress.chimera.common.NameContext;
import com.progress.common.property.PropertyManager;
import com.progress.chimera.common.Tools;
import java.rmi.server.RemoteStub;
import com.progress.common.log.ProLog;
import com.progress.common.networkevents.IEventObject;
import java.rmi.RemoteException;
import com.progress.chimera.log.ConnectionManagerLog;
import com.progress.international.resources.ProgressResources;
import com.progress.agent.database.AgentDatabase;
import com.progress.chimera.common.Database;

public class SMDatabase extends Database implements ISMDatabase
{
    protected String m_displayName;
    private AgentDatabase m_agent;
    private String m_hostNameOnConnect;
    private String m_hostIPOnConnect;
    private String m_adminserverPort;
    private String m_brokerPort;
    private boolean m_fileSysCS;
    private String m_name;
    static SMPlugIn m_smPlugIn;
    static SMDatabase m_self;
    private String m_aiWriterStatus;
    private String m_apWriterStatus;
    private String m_biWriterStatus;
    private String m_watchDogStatus;
    private boolean m_aiEnabled;
    private boolean m_replEnabled;
    private boolean m_clustEnabled;
    static ProgressResources resources;
    private SMProperties m_smPropMgr;
    private String stateUnknown;
    private String stateIdle;
    private String stateStarting;
    private String stateInitializing;
    private String stateRunning;
    private String stateShuttingDown;
    private String lastKnownState;
    public static final int AIW_STATUS = 1;
    public static final int BIW_STATUS = 2;
    public static final int APW_STATUS = 3;
    public static final int WDOG_STATUS = 4;
    public static final int ALL_STATUS = 5;
    private static ConnectionManagerLog theLog;
    
    public SMDatabase(final String s, final String s2, final String s3) throws RemoteException {
        final String adjustName = adjustName("_SMDatabase_" + s);
        SMPlugIn.get();
        super(adjustName, s2, SMPlugIn.normalizeDatabaseName(s3));
        this.m_displayName = "";
        this.m_agent = null;
        this.m_hostNameOnConnect = null;
        this.m_hostIPOnConnect = null;
        this.m_adminserverPort = "0";
        this.m_brokerPort = "0";
        this.m_fileSysCS = true;
        this.m_name = null;
        this.m_aiWriterStatus = null;
        this.m_apWriterStatus = null;
        this.m_biWriterStatus = null;
        this.m_watchDogStatus = null;
        this.m_aiEnabled = false;
        this.m_replEnabled = false;
        this.m_clustEnabled = false;
        this.m_smPropMgr = null;
        this.stateUnknown = "Unknown";
        this.stateIdle = SMDatabase.resources.getTranString("Not Running");
        this.stateStarting = SMDatabase.resources.getTranString("Starting");
        this.stateInitializing = SMDatabase.resources.getTranString("Initializing");
        this.stateRunning = SMDatabase.resources.getTranString("Running");
        this.stateShuttingDown = SMDatabase.resources.getTranString("Shutting Down");
        this.lastKnownState = this.stateIdle;
        SMDatabase.m_smPlugIn = SMPlugIn.get();
        this.m_displayName = s;
        SMDatabase.m_self = this;
        this.m_agent = new AgentDatabase(SMDatabase.m_self, this.getDisplayName(true));
        this.m_name = super.name();
        this.m_smPropMgr = (SMProperties)SMDatabase.m_smPlugIn.getPlugInPropertyManager();
    }
    
    static SMDatabase get() {
        return SMDatabase.m_self;
    }
    
    private String groupName() {
        return "SMDatabase." + this.m_displayName;
    }
    
    public IEventObject getEventStartupCompleted() throws RemoteException {
        return new ESMDStartupCompleted(this.evThis());
    }
    
    public IEventObject getEventShutdownCompleted() throws RemoteException {
        return new ESMDShutdownCompleted(this.evThis());
    }
    
    public IEventObject getEventCrash() throws RemoteException {
        return new ESMDCrash(this.evThis());
    }
    
    public void handleCrash() {
        try {
            this.setIdle();
            SMPlugIn.get().getEventBroker().postEvent(this.getEventCrash());
        }
        catch (RemoteException ex) {
            ProLog.logX(ex);
        }
    }
    
    public void handleStop() {
        try {
            this.setIdle();
            SMPlugIn.get().getEventBroker().postEvent(this.getEventShutdownCompleted());
        }
        catch (RemoteException ex) {
            ProLog.logX(ex);
        }
    }
    
    public void handleRunning() {
        try {
            this.setRunning();
            SMPlugIn.get().getEventBroker().postEvent(this.getEventStartupCompleted());
        }
        catch (RemoteException ex) {
            ProLog.logX(ex);
        }
    }
    
    protected RemoteStub remoteStub() {
        return this.stub();
    }
    
    public String getLastKnownStatus() {
        return this.lastKnownState;
    }
    
    private void setIdle() {
        this.lastKnownState = this.stateIdle;
    }
    
    private void setStarting() {
        this.lastKnownState = this.stateStarting;
    }
    
    private void setShuttingDown() {
        this.lastKnownState = this.stateShuttingDown;
    }
    
    private void setRunning() {
        this.lastKnownState = this.stateRunning;
    }
    
    public boolean isIdle() {
        return this.lastKnownState.equals(this.stateIdle);
    }
    
    public boolean isRunning() {
        return this.lastKnownState.equals(this.stateRunning);
    }
    
    public void delete(final Object o) {
        synchronized (SMDatabase.m_smPlugIn) {
            this.delete(true, o);
        }
    }
    
    public void delete(final boolean b, final Object o) {
        synchronized (SMDatabase.m_smPlugIn) {
            final String displayName = this.getDisplayName();
            final String string = "smdatabase." + displayName;
            try {
                super.delete();
                this.m_agent.delete(SMDatabase.m_smPlugIn.getEventBroker());
                SMDatabase.m_smPlugIn.getSMDatabases().removeElement(this);
                final SMPlugIn smPlugIn = SMDatabase.m_smPlugIn;
                SMPlugIn.properties.removeGroup(string);
                if (b) {
                    try {
                        this.m_smPropMgr.save(SMDatabase.m_smPlugIn.getPropertyFileName(), "Remote Database Properties File");
                    }
                    catch (PropertyManager.SaveIOException ex) {
                        Tools.px(ex, "Failed to complete deletion of remote database: " + displayName + "; error saving properties file.");
                    }
                }
            }
            catch (RemoteException ex2) {
                Tools.px(ex2, "Failed to complete deletion of remote database: " + displayName + "; remote error encountered.");
            }
        }
    }
    
    ISMDatabase evThis() {
        return (ISMDatabase)this.remoteStub();
    }
    
    public void stop() throws RemoteException {
        synchronized (SMPlugIn.get()) {
            try {
                this.setIdle();
                SMPlugIn.get().getEventBroker().postEvent(new ESMDShutdownCompleted(this.evThis()));
            }
            catch (RemoteException ex) {
                ProLog.logdErr("Fathom", ex.getMessage());
            }
        }
    }
    
    public void setHostNameOnConnect(final String hostNameOnConnect) {
        this.m_hostNameOnConnect = hostNameOnConnect;
    }
    
    public void setHostIPOnConnect(final String hostIPOnConnect) {
        this.m_hostIPOnConnect = hostIPOnConnect;
    }
    
    public void setAdminserverPort(final String adminserverPort) {
        if (adminserverPort != null) {
            this.m_adminserverPort = adminserverPort;
        }
    }
    
    public void setBrokerPort(final String brokerPort) {
        if (brokerPort != null) {
            this.m_brokerPort = brokerPort;
        }
    }
    
    public void setFileSysCS(final boolean fileSysCS) {
        this.m_fileSysCS = fileSysCS;
    }
    
    public String getHostNameOnConnect() {
        return this.m_hostNameOnConnect;
    }
    
    public String getHostIPOnConnect() {
        return this.m_hostIPOnConnect;
    }
    
    public String getAdminserverPort() {
        return this.m_adminserverPort;
    }
    
    public String getBrokerPort() {
        return this.m_brokerPort;
    }
    
    public boolean getFileSysCS() {
        return this.m_fileSysCS;
    }
    
    public AgentDatabase getDatabaseAgent() {
        return this.m_agent;
    }
    
    private static String adjustName(final String s) {
        return s.trim().toLowerCase();
    }
    
    public static SMDatabase findSMDatabase(final String str) {
        try {
            return (SMDatabase)Database.find(adjustName("_SMDatabase_" + str));
        }
        catch (Throwable t) {
            return null;
        }
    }
    
    public String getDisplayName() {
        return this.getDisplayName(false);
    }
    
    public String getDisplayName(final boolean b) {
        String s = this.m_displayName;
        if (b) {
            s = "_SMDatabase_" + this.m_displayName;
        }
        return s;
    }
    
    public boolean getMonitorLicensed() {
        return true;
    }
    
    public void setMonitorLicensed(final boolean b) throws PropertyManager.PropertyException {
        this.setMonitorLicensed(b, false);
    }
    
    public void setMonitorLicensed(final boolean b, final boolean b2) throws PropertyManager.PropertyException {
        this.m_smPropMgr.putBooleanProperty(this.groupName() + ".MonitorLicensed", b);
        if (b2) {
            this.m_smPropMgr.save(SMDatabase.m_smPlugIn.getPropertyFileName(), "Remote Database Properties File");
        }
    }
    
    public static ConnectionManagerLog getLog() {
        if (SMDatabase.theLog == null) {
            SMDatabase.theLog = ConnectionManagerLog.get();
        }
        return SMDatabase.theLog;
    }
    
    public String[] getIpAddresses() {
        String[] arrayProperty;
        try {
            arrayProperty = SMPlugIn.get().properties().getArrayProperty("SMDatabase." + this.getDisplayName() + ".hostIpAddresses");
        }
        catch (NullPointerException ex) {
            arrayProperty = null;
        }
        return arrayProperty;
    }
    
    public String name() {
        return this.m_name;
    }
    
    public void rename(final String str, final String displayName) {
        synchronized (this.m_name) {
            final NameContext nameContext = this.getNameContext();
            this.m_name = displayName;
            nameContext.remove(adjustName("_SMDatabase_" + str));
            nameContext.put(adjustName("_SMDatabase_" + displayName), this);
            this.m_displayName = displayName;
            this.m_agent.rename(str, displayName);
        }
    }
    
    public void setDatabasePath(final String physicalName) {
        this.setPhysicalName(physicalName);
    }
    
    public void setDatabaseHost(final String hostName) {
        super.hostName = hostName;
    }
    
    public String getAuxProcessStatus(final int n) {
        String s = this.stateUnknown;
        switch (n) {
            case 1: {
                if (this.m_aiWriterStatus == null && this.isRunning()) {
                    this.m_agent.sendPacket("S_AIW_REQ");
                    break;
                }
                s = this.m_aiWriterStatus;
                break;
            }
            case 3: {
                if (this.m_apWriterStatus == null && this.isRunning()) {
                    this.m_agent.sendPacket("S_APW_REQ");
                    break;
                }
                s = this.m_apWriterStatus;
                break;
            }
            case 2: {
                if (this.m_biWriterStatus == null && this.isRunning()) {
                    this.m_agent.sendPacket("S_BIW_REQ");
                    break;
                }
                s = this.m_biWriterStatus;
                break;
            }
            case 4: {
                if (this.m_watchDogStatus == null && this.isRunning()) {
                    this.m_agent.sendPacket("S_WDOG_REQ");
                    break;
                }
                s = this.m_watchDogStatus;
                break;
            }
        }
        if (s == null) {
            s = this.stateUnknown;
        }
        return s;
    }
    
    public boolean setAuxProcessStatus(final int n, final String s) {
        boolean b = true;
        int int1 = -1;
        String s2 = null;
        if (s != null) {
            int1 = Integer.parseInt(s);
            if (int1 > 0) {
                s2 = this.stateRunning;
            }
            else {
                s2 = this.stateIdle;
            }
        }
        switch (n) {
            case 1: {
                this.m_aiWriterStatus = s2;
                break;
            }
            case 3: {
                if (int1 > 0) {
                    s2 = this.stateRunning + ": " + int1;
                }
                this.m_apWriterStatus = s2;
                break;
            }
            case 2: {
                this.m_biWriterStatus = s2;
                break;
            }
            case 4: {
                this.m_watchDogStatus = s2;
                break;
            }
            case 5: {
                this.m_aiWriterStatus = null;
                this.m_apWriterStatus = null;
                this.m_watchDogStatus = null;
                this.m_biWriterStatus = null;
                break;
            }
            default: {
                b = false;
                break;
            }
        }
        return b;
    }
    
    public void setAIEnabled(final boolean aiEnabled) {
        this.m_aiEnabled = aiEnabled;
    }
    
    public boolean isAIEnabled() {
        return this.m_aiEnabled;
    }
    
    public void setReplEnabled(final boolean replEnabled) {
        this.m_replEnabled = replEnabled;
    }
    
    public boolean isReplEnabled() {
        return this.m_replEnabled;
    }
    
    public void setClustEnabled(final boolean clustEnabled) {
        this.m_clustEnabled = clustEnabled;
    }
    
    public boolean isClustEnabled() {
        return this.m_clustEnabled;
    }
    
    static {
        SMDatabase.m_smPlugIn = null;
        SMDatabase.m_self = null;
        SMDatabase.resources = (ProgressResources)ResourceBundle.getBundle("com.progress.international.messages.JAGenericBundle");
        SMDatabase.theLog = null;
    }
}
