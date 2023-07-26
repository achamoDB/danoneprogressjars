// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.util.ResourceBundle;
import com.progress.common.log.ProLog;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.Enumeration;
import com.progress.chimera.common.Database;
import com.progress.common.log.LogSystem;
import com.progress.chimera.common.Tools;
import java.rmi.server.RemoteStub;
import com.progress.common.networkevents.IEventObject;
import java.rmi.RemoteException;
import com.progress.chimera.log.ConnectionManagerLog;
import com.progress.international.resources.ProgressResources;
import com.progress.chimera.common.NameContext;
import com.progress.common.networkevents.IEventBroker;
import com.progress.chimera.common.ChimeraNamedObject;

public class JAAgent extends ChimeraNamedObject implements IJAAgent
{
    String m_jaDatabaseName;
    Object syncObject;
    JADatabase m_jaDatabase;
    IEventBroker m_eventBroker;
    private String m_name;
    static NameContext m_nameTable;
    boolean m_startedByMe;
    boolean m_startsAreBlocked;
    JAAgentConnect m_agentConnect;
    Thread m_agentConnectThread;
    static ProgressResources resources;
    StateObject state;
    private static ConnectionManagerLog theLog;
    
    public JAAgent(final JADatabase jaDatabase) throws RemoteException {
        super(adjustName(jaDatabase.getDisplayName(false)));
        this.m_jaDatabaseName = "";
        this.syncObject = new Object();
        this.m_jaDatabase = null;
        this.m_eventBroker = null;
        this.m_name = null;
        this.m_startedByMe = false;
        this.m_startsAreBlocked = false;
        this.m_agentConnect = null;
        this.m_agentConnectThread = null;
        this.state = new StateObject(ARStateIdle.get());
        this.m_jaDatabase = jaDatabase;
        this.m_jaDatabaseName = jaDatabase.getDisplayName(false);
        this.m_eventBroker = JAPlugIn.get().getEventBroker();
        this.m_name = super.name();
    }
    
    protected NameContext getNameContext() {
        return JAAgent.m_nameTable;
    }
    
    private static String adjustName(final String s) {
        return s.trim().toLowerCase();
    }
    
    public static JAAgent findJAAgent(final String s) {
        try {
            return JAAgent.m_nameTable.get(adjustName(s));
        }
        catch (Throwable t) {
            return null;
        }
    }
    
    IEventObject getCrashEvent() throws RemoteException {
        return this.getCrashEvent(null);
    }
    
    IEventObject getCrashEvent(final String s) throws RemoteException {
        return new EARCrash(this.evThis(), s);
    }
    
    public RemoteStub remoteStub() {
        return this.stub();
    }
    
    IJAAgent evThis() {
        return (IJAAgent)this.remoteStub();
    }
    
    public IJAPlugIn getPlugIn() {
        return JAPlugIn.get();
    }
    
    static ProgressResources resources() {
        return JAAgent.resources;
    }
    
    public JAStatusObject getStatus() throws RemoteException {
        final JAStatusObjectAR jaStatusObjectAR = new JAStatusObjectAR();
        jaStatusObjectAR.displayName = this.getDisplayName();
        jaStatusObjectAR.stateDescriptor = this.getStateDescriptor();
        jaStatusObjectAR.isStartable = this.isStartable();
        jaStatusObjectAR.isStopable = this.isStopable();
        jaStatusObjectAR.isIdle = this.isIdle();
        jaStatusObjectAR.isStarting = this.isStarting();
        jaStatusObjectAR.isInitializing = this.isInitializing();
        jaStatusObjectAR.isRunning = this.isRunning();
        jaStatusObjectAR.isShuttingDown = this.isShuttingDown();
        return jaStatusObjectAR;
    }
    
    public String getStateDescriptor() {
        return this.state.get().displayName();
    }
    
    void blockStarts(final boolean startsAreBlocked) {
        this.m_startsAreBlocked = startsAreBlocked;
    }
    
    boolean startsAreBlocked() {
        return this.m_startsAreBlocked;
    }
    
    void setState(final State state) throws StateException {
        if (state == this.state.get()) {
            return;
        }
        synchronized (this.syncObject) {
            synchronized (this.state) {
                try {
                    this.state.changeState(this, state);
                    this.m_eventBroker.postEvent(new EARStateChanged(this.evThis(), this.getStatus()));
                }
                catch (StateException ex) {
                    Tools.px(ex, "Error changing states on auxiliary.");
                    throw ex;
                }
                catch (RemoteException ex2) {
                    Tools.px(ex2, "Can't post event to mark auxiliary state change.");
                }
            }
        }
    }
    
    public void setIdle() throws StateException {
        this.setState(ARStateIdle.get());
    }
    
    public void setStarting() throws StateException {
        this.setState(ARStateStarting.get());
    }
    
    public void setInitializing() throws StateException {
        this.setState(ARStateInitializing.get());
    }
    
    public void setRunning() throws StateException {
        this.setState(ARStateRunning.get());
    }
    
    public void setShuttingDown() throws StateException {
        this.setState(ARStateShuttingDown.get());
    }
    
    public boolean isIdle() {
        synchronized (this.syncObject) {
            return this.state.get() == ARStateIdle.get();
        }
    }
    
    public boolean isStarting() {
        synchronized (this.syncObject) {
            return this.state.get() == ARStateStarting.get();
        }
    }
    
    public boolean isInitializing() {
        synchronized (this.syncObject) {
            return this.state.get() == ARStateInitializing.get();
        }
    }
    
    protected void sendRunningQuery() {
        final JAConfiguration jaConfiguration = (JAConfiguration)this.m_jaDatabase.getCurrentConfiguration();
        final MProservJuniper mProservJuniper = (MProservJuniper)((jaConfiguration != null) ? jaConfiguration.getRemoteJuniper() : null);
        if (mProservJuniper != null) {
            mProservJuniper.sendPacket("DAU?");
        }
    }
    
    public boolean isRunning() {
        synchronized (this.syncObject) {
            return this.state.get() == ARStateRunning.get();
        }
    }
    
    public boolean isShuttingDown() {
        synchronized (this.syncObject) {
            return this.state.get() == ARStateShuttingDown.get();
        }
    }
    
    public static ConnectionManagerLog getLog() {
        if (JAAgent.theLog == null) {
            JAAgent.theLog = ConnectionManagerLog.get();
        }
        return JAAgent.theLog;
    }
    
    void handleRunning() {
        synchronized (this.state) {
            try {
                if (this.isShuttingDown()) {
                    return;
                }
                if (this.m_jaDatabase.isShuttingDown()) {
                    this.setShuttingDown();
                }
                else {
                    this.setRunning();
                }
            }
            catch (StateException ex) {
                final ConnectionManagerLog theLog = JAAgent.theLog;
                LogSystem.logIt(ex.toString(), "Can't set state of remote agent process for database: " + this.m_jaDatabaseName + ".");
            }
        }
    }
    
    void handleCrash() {
        this.handleCrash(null);
    }
    
    void handleCrash(final String s) {
        try {
            this.setIdle();
            this.m_eventBroker.postEvent(this.getCrashEvent(s));
        }
        catch (StateException ex) {
            Tools.px(ex);
        }
        catch (RemoteException ex2) {
            Tools.px(ex2);
        }
    }
    
    void handleShutdown() {
        try {
            this.setIdle();
        }
        catch (StateException ex) {
            Tools.px(ex);
        }
    }
    
    public Database getDatabase() {
        return this.m_jaDatabase;
    }
    
    public Enumeration getChildren() throws RemoteException {
        return null;
    }
    
    public String getMMCClientClass() throws RemoteException {
        return null;
    }
    
    public String getDisplayName() throws RemoteException {
        return this.getDisplayName(false);
    }
    
    public String getDisplayName(final boolean b) {
        return this.m_jaDatabaseName;
    }
    
    public String toString() {
        return this.m_jaDatabaseName;
    }
    
    public boolean isStartable() {
        synchronized (this.syncObject) {
            return !this.startsAreBlocked() && this.isIdle();
        }
    }
    
    public void start() throws RemoteException {
        synchronized (this.syncObject) {
            if (!(this.m_jaDatabase instanceof JADatabase)) {
                return;
            }
            final JAConfiguration jaConfiguration = (JAConfiguration)this.m_jaDatabase.getCurrentConfiguration();
            if (!this.isStartable() || !this.m_jaDatabase.isRunning()) {
                return;
            }
            final JuniperProperties juniperProperties = (JuniperProperties)JAPlugIn.get().properties();
            final String property = System.getProperty("Install.Dir");
            String e = juniperProperties.getProperty("AgentRemoteConnection.AgentExe");
            if (e == null || e.length() == 0) {
                e = property + System.getProperty("file.separator") + "bin" + System.getProperty("file.separator") + "_dbagent";
            }
            final String displayName = jaConfiguration.getDisplayName(false);
            final String property2 = juniperProperties.getProperty("Configuration." + this.m_jaDatabaseName + "." + displayName + ".AgentRemotePort");
            final String property3 = juniperProperties.getProperty("Configuration." + this.m_jaDatabaseName + "." + displayName + ".AgentRemoteHost");
            final String property4 = juniperProperties.getProperty("AgentRemoteConnection.DebugArgs");
            String e2 = juniperProperties.getProperty("Database." + this.m_jaDatabaseName + ".databasename");
            if (e2.endsWith(".db")) {
                e2 = e2.substring(0, e2.lastIndexOf(".db"));
            }
            final Vector<String> vector = new Vector<String>();
            vector.add(e);
            vector.add("-installdir");
            vector.add(property);
            vector.add("-db");
            vector.add(e2);
            vector.add("-S");
            vector.add(property2);
            vector.add("-H");
            vector.add(property3);
            if (property4 != null && property4.length() > 0) {
                final StringTokenizer stringTokenizer = new StringTokenizer(property4);
                while (stringTokenizer.hasMoreTokens()) {
                    vector.add(stringTokenizer.nextToken());
                }
            }
            final String[] array = vector.toArray(new String[0]);
            this.m_agentConnect = new JAAgentConnect(this, array);
            (this.m_agentConnectThread = new Thread(this.m_agentConnect)).setName("AgentConnectThread");
            try {
                this.setStarting();
                this.m_startedByMe = true;
                this.m_agentConnectThread.start();
            }
            catch (StateException ex) {
                Tools.px(ex, "Could not start database agent.");
            }
            String string = "";
            for (int i = 0; i < array.length; ++i) {
                string = string + array[i] + " ";
            }
            ProLog.logd("DatabaseAgent", string);
        }
    }
    
    public boolean isStopable() {
        synchronized (this.syncObject) {
            return this.isRunning();
        }
    }
    
    public void stop() throws RemoteException {
        synchronized (this.syncObject) {
            try {
                if (this.isStopable()) {
                    this.setShuttingDown();
                    final JAConfiguration jaConfiguration = (JAConfiguration)this.m_jaDatabase.getCurrentConfiguration();
                    final MProservJuniper mProservJuniper = (MProservJuniper)((jaConfiguration != null) ? jaConfiguration.getRemoteJuniper() : null);
                    if (mProservJuniper != null) {
                        mProservJuniper.sendPacket("DARS");
                    }
                }
            }
            catch (StateException ex) {
                Tools.px(ex, "Could not stop database agent.");
            }
        }
    }
    
    public boolean isDeleteable() {
        return false;
    }
    
    public void delete(final Object o) {
    }
    
    public void setStartedByMe(final boolean startedByMe) {
        this.m_startedByMe = startedByMe;
    }
    
    public IJAHierarchy getParent() {
        return null;
    }
    
    public String makeNewChildName() throws RemoteException {
        synchronized (this.syncObject) {
            throw new RemoteException("Children of database agents not supported.");
        }
    }
    
    public boolean childNameUsed(final String s) {
        return false;
    }
    
    public IJAHierarchy createChild(final String s, final Object o, final Object o2) throws RemoteException {
        synchronized (this.syncObject) {
            throw new RemoteException("Children of database agents not supported.");
        }
    }
    
    public Enumeration getChildrenObjects() {
        return null;
    }
    
    public void exiting() {
        synchronized (this.syncObject) {
            try {
                this.setIdle();
            }
            catch (StateException ex) {
                Tools.px(ex, "Database agent exception while trying to exit.");
            }
        }
    }
    
    static {
        JAAgent.m_nameTable = new NameContext();
        JAAgent.resources = (ProgressResources)ResourceBundle.getBundle("com.progress.international.messages.JAGenericBundle");
        JAAgent.theLog = null;
    }
}
