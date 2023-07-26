// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.agent.database;

import java.util.ResourceBundle;
import com.progress.common.comsockagent.ComMsgAgent;
import com.progress.juniper.admin.IJAHierarchy;
import java.util.StringTokenizer;
import com.progress.juniper.admin.JuniperProperties;
import java.util.Enumeration;
import com.progress.juniper.admin.StateException;
import com.progress.chimera.common.Tools;
import com.progress.juniper.admin.JAStatusObject;
import com.progress.juniper.admin.IJAPlugIn;
import java.rmi.server.RemoteStub;
import com.progress.common.networkevents.IEventObject;
import com.progress.common.property.PropertyManager;
import com.progress.common.log.ProLog;
import java.util.Vector;
import com.progress.agent.smdatabase.SMPlugIn;
import com.progress.agent.smdatabase.SMDatabase;
import com.progress.juniper.admin.State;
import java.rmi.RemoteException;
import com.progress.juniper.admin.JADatabase;
import com.progress.juniper.admin.JAPlugIn;
import com.progress.chimera.log.ConnectionManagerLog;
import com.progress.juniper.admin.StateObject;
import com.progress.international.resources.ProgressResources;
import com.progress.chimera.common.NameContext;
import com.progress.chimera.common.Database;
import com.progress.chimera.common.IChimeraHierarchy;
import com.progress.chimera.common.ChimeraNamedObject;

public class AgentDatabase extends ChimeraNamedObject implements IAgentDatabase, IChimeraHierarchy, IAgentAPI
{
    String m_databaseName;
    AgentPlugIn m_agentPlugIn;
    Database m_database;
    public String m_queryResultsId;
    public int m_queryRowCount;
    public byte[] m_queryResultsData;
    private String m_name;
    private boolean m_isRemoteDb;
    public boolean m_queryResultsDone;
    public Object m_queryResultsLock;
    Integer m_id;
    static AgentDatabase m_self;
    static NameContext m_nameTable;
    boolean m_startedByMe;
    boolean m_startsAreBlocked;
    AgentConnect m_agentConnect;
    Thread m_agentConnectThread;
    boolean m_is64BitSchema;
    boolean m_is64BitRecID;
    String m_schemaVersion;
    static ProgressResources resources;
    StateObject state;
    private static ConnectionManagerLog theLog;
    
    int getIdValue() {
        return (this.m_id != null) ? this.m_id : -1;
    }
    
    void setIdValue(final int value) {
        this.m_id = new Integer(value);
    }
    
    public void clearIdValue() {
        this.m_id = null;
    }
    
    public AgentDatabase(final JAPlugIn jaPlugIn, final JADatabase jaDatabase) throws RemoteException {
        this(jaDatabase, jaDatabase.getDisplayName());
    }
    
    public AgentDatabase(final Database database, final String databaseName) throws RemoteException {
        super(adjustName(databaseName));
        this.m_databaseName = "";
        this.m_agentPlugIn = AgentPlugIn.get();
        this.m_database = null;
        this.m_queryResultsId = "";
        this.m_queryRowCount = 0;
        this.m_queryResultsData = null;
        this.m_name = null;
        this.m_isRemoteDb = false;
        this.m_queryResultsDone = true;
        this.m_queryResultsLock = new Object();
        this.m_id = null;
        this.m_startedByMe = false;
        this.m_startsAreBlocked = false;
        this.m_agentConnect = null;
        this.m_agentConnectThread = null;
        this.m_is64BitSchema = true;
        this.m_is64BitRecID = true;
        this.m_schemaVersion = "102b";
        this.state = new StateObject(DBAStateIdle.get());
        if (database instanceof SMDatabase) {
            this.m_isRemoteDb = true;
        }
        this.m_database = database;
        this.m_databaseName = databaseName;
        AgentPlugIn.addAgentDatabase(this.m_name = super.name(), this);
        AgentDatabase.m_self = this;
    }
    
    static AgentDatabase get() {
        return AgentDatabase.m_self;
    }
    
    protected NameContext getNameContext() {
        return AgentDatabase.m_nameTable;
    }
    
    private static String adjustName(final String s) {
        return s.trim().toLowerCase();
    }
    
    public static AgentDatabase findAgentDatabase(final String s) {
        try {
            return AgentDatabase.m_nameTable.get(adjustName(s));
        }
        catch (Throwable t) {
            return null;
        }
    }
    
    public void set64BitSchema(final boolean is64BitSchema) {
        this.m_is64BitSchema = is64BitSchema;
    }
    
    public boolean is64BitSchema() {
        return this.m_is64BitSchema;
    }
    
    public void set64BitRecID(final boolean is64BitRecID) {
        this.m_is64BitRecID = is64BitRecID;
    }
    
    public boolean is64BitRecID() {
        return this.m_is64BitRecID;
    }
    
    public void setSchemaVersion(final String schemaVersion) {
        try {
            this.m_schemaVersion = schemaVersion;
            if (!this.m_schemaVersion.equals("9") && !this.m_schemaVersion.equals("10") && !this.m_schemaVersion.equals("101") && !this.m_schemaVersion.equals("101c") && !this.m_schemaVersion.equals("102b")) {
                this.m_schemaVersion = "102b";
            }
        }
        catch (Exception ex) {
            this.m_schemaVersion = "102b";
        }
    }
    
    public String getSchemaVersion() {
        return this.m_schemaVersion;
    }
    
    public static AgentDatabase findAgentDatabase(String normalizeDatabaseName, final String hostNameOnConnect, final String s, final boolean b) {
        try {
            AgentPlugIn.get();
            normalizeDatabaseName = AgentPlugIn.normalizeDatabaseName(normalizeDatabaseName);
            final Vector smDatabases = SMPlugIn.get().getSMDatabases();
            AgentDatabase databaseAgent = null;
            int n = 1;
            int index = 0;
            while (n != 0 && index < smDatabases.size()) {
                final SMDatabase smDatabase = smDatabases.elementAt(index);
                final String physicalName = smDatabase.physicalName();
                AgentPlugIn.get();
                final String normalizeDatabaseName2 = AgentPlugIn.normalizeDatabaseName(physicalName);
                if ((!b && normalizeDatabaseName2.equalsIgnoreCase(normalizeDatabaseName)) || (b && normalizeDatabaseName2.equals(normalizeDatabaseName))) {
                    final String[] ipAddresses = smDatabase.getIpAddresses();
                    if (ipAddresses == null) {
                        ++index;
                        continue;
                    }
                    int n2 = 0;
                    while (n != 0 && n2 < ipAddresses.length) {
                        if (ipAddresses[n2].equals(s)) {
                            n = 0;
                            smDatabase.setHostNameOnConnect(hostNameOnConnect);
                            smDatabase.setHostIPOnConnect(s);
                            databaseAgent = smDatabase.getDatabaseAgent();
                        }
                        else {
                            ++n2;
                        }
                    }
                }
                ++index;
            }
            return databaseAgent;
        }
        catch (Throwable t) {
            return null;
        }
    }
    
    public boolean getMonitorLicensed() {
        return true;
    }
    
    public void setMonitorLicensed(final boolean b) {
        this.setMonitorLicensed(b, true);
    }
    
    public void setMonitorLicensed(final boolean b, final boolean b2) {
        try {
            final Database database = this.getDatabase();
            if (database instanceof JADatabase) {
                final JADatabase jaDatabase = (JADatabase)database;
                jaDatabase.setMonitorLicensed(b, true);
                if (b2 && b && jaDatabase.isRunning() && this.isStartable()) {
                    this.start();
                }
            }
            else if (database instanceof SMDatabase) {
                ((SMDatabase)database).setMonitorLicensed(b, true);
            }
            if (b2 && !b && this.isRunning()) {
                this.stop();
            }
        }
        catch (PropertyManager.PropertyException ex2) {
            ProLog.logd("Fathom", 3, "Unable to set licensing to " + b + " for database agent " + this.getDisplayName(false));
        }
        catch (RemoteException ex) {
            ProLog.logd("Fathom", 3, "Database agent: " + this.getDisplayName(false) + ex.getMessage());
        }
    }
    
    IEventObject getCrashEvent() throws RemoteException {
        return this.getCrashEvent(null);
    }
    
    IEventObject getCrashEvent(final String s) throws RemoteException {
        final EDBACrash edbaCrash = new EDBACrash(this.evThis(), s);
        if (this.isRemoteDb()) {
            edbaCrash.setSource(SMPlugIn.getCanonicalName());
        }
        return edbaCrash;
    }
    
    IEventObject getStateChangedEvent() throws RemoteException {
        final EDBAStateChanged edbaStateChanged = new EDBAStateChanged(this.evThis(), this.getStatus());
        if (this.isRemoteDb()) {
            edbaStateChanged.setSource(SMPlugIn.getCanonicalName());
        }
        return edbaStateChanged;
    }
    
    IEventObject getStartupCompletedEvent() throws RemoteException {
        final EDBAStartupCompleted edbaStartupCompleted = new EDBAStartupCompleted(this.evThis());
        if (this.isRemoteDb()) {
            edbaStartupCompleted.setSource(SMPlugIn.getCanonicalName());
        }
        return edbaStartupCompleted;
    }
    
    public RemoteStub remoteStub() {
        return this.stub();
    }
    
    IAgentDatabase evThis() {
        return (IAgentDatabase)this.remoteStub();
    }
    
    public IJAPlugIn getPlugIn() {
        return JAPlugIn.get();
    }
    
    static ProgressResources resources() {
        return AgentDatabase.resources;
    }
    
    public JAStatusObject getStatus() throws RemoteException {
        final JAStatusObject jaStatusObject = new JAStatusObject();
        jaStatusObject.displayName = this.getDisplayName();
        jaStatusObject.stateDescriptor = this.getStateDescriptor();
        jaStatusObject.isStartable = this.isStartable();
        jaStatusObject.isStopable = this.isStopable();
        jaStatusObject.isIdle = this.isIdle();
        jaStatusObject.isStarting = this.isStarting();
        jaStatusObject.isInitializing = this.isInitializing();
        jaStatusObject.isRunning = this.isRunning();
        jaStatusObject.isShuttingDown = this.isShuttingDown();
        return jaStatusObject;
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
        synchronized (this.m_agentPlugIn) {
            synchronized (this.state) {
                try {
                    this.state.changeState(this, state);
                    AgentPlugIn.get().getEventBroker().postEvent(this.getStateChangedEvent());
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
        this.setState(DBAStateIdle.get());
    }
    
    public void setStarting() throws StateException {
        this.setState(DBAStateStarting.get());
    }
    
    public void setInitializing() throws StateException {
        this.setState(DBAStateInitializing.get());
    }
    
    public void setRunning() throws StateException {
        this.setState(DBAStateRunning.get());
    }
    
    public void setShuttingDown() throws StateException {
        this.setState(DBAStateShuttingDown.get());
    }
    
    public boolean isIdle() {
        synchronized (this.m_agentPlugIn) {
            return this.state.get() == DBAStateIdle.get();
        }
    }
    
    public boolean isStarting() {
        synchronized (this.m_agentPlugIn) {
            return this.state.get() == DBAStateStarting.get();
        }
    }
    
    public boolean isInitializing() {
        synchronized (this.m_agentPlugIn) {
            return this.state.get() == DBAStateInitializing.get();
        }
    }
    
    public boolean isRunning() {
        synchronized (this.m_agentPlugIn) {
            return this.state.get() == DBAStateRunning.get();
        }
    }
    
    public boolean isShuttingDown() {
        synchronized (this.m_agentPlugIn) {
            return this.state.get() == DBAStateShuttingDown.get();
        }
    }
    
    public static ConnectionManagerLog getLog() {
        if (AgentDatabase.theLog == null) {
            AgentDatabase.theLog = ConnectionManagerLog.get();
        }
        return AgentDatabase.theLog;
    }
    
    void handleCrash() {
        this.handleCrash(null);
    }
    
    void handleCrash(final String s) {
        try {
            this.setIdle();
            AgentPlugIn.get().getEventBroker().postEvent(this.getCrashEvent(s));
        }
        catch (StateException ex) {
            Tools.px(ex);
        }
        catch (RemoteException ex2) {
            Tools.px(ex2);
        }
    }
    
    public Database getDatabase() {
        return this.m_database;
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
        if (this.isRemoteDb()) {
            return ((SMDatabase)this.m_database).getDisplayName(b);
        }
        return this.m_databaseName;
    }
    
    public String toString() {
        return this.m_databaseName;
    }
    
    public boolean isStartable() {
        synchronized (this.m_agentPlugIn) {
            return !this.startsAreBlocked() && this.isIdle();
        }
    }
    
    public boolean isRemoteDb() {
        return this.m_isRemoteDb;
    }
    
    public void start() throws RemoteException {
        try {
            synchronized (this.m_agentPlugIn) {
                if (!(this.m_database instanceof JADatabase)) {
                    return;
                }
                final JADatabase jaDatabase = (JADatabase)this.m_database;
                if (!this.isStartable() || !jaDatabase.isRunning() || !this.getMonitorLicensed()) {
                    return;
                }
                final AgentProperties agentProperties = (AgentProperties)AgentPlugIn.get().properties();
                final String property = System.getProperty("Install.Dir");
                String e = agentProperties.getProperty("agent.agentexe");
                if (e == null || e.length() == 0) {
                    e = property + System.getProperty("file.separator") + "bin" + System.getProperty("file.separator") + "_dbagent";
                }
                final String propertyFileName = AgentPlugIn.propertyFileName;
                String e2 = agentProperties.getProperty("agent.port");
                if (e2 == null || e2.length() == 0) {
                    e2 = IAgentAPI.DEFAULT_AGENT_PORT + "";
                }
                String property2 = agentProperties.getProperty("agent.username");
                if (property2 == null || property2.length() == 0) {
                    property2 = "DB_Agent";
                }
                final String property3 = agentProperties.getProperty("agent.debugargs");
                final JuniperProperties juniperProperties = (JuniperProperties)JAPlugIn.get().properties();
                String e3 = juniperProperties.getProperty("Database." + this.m_databaseName + ".databasename");
                if (e3.endsWith(".db")) {
                    e3 = e3.substring(0, e3.lastIndexOf(".db"));
                }
                final String property4 = juniperProperties.getProperty("ServerGroup." + juniperProperties.getArrayProperty("Configuration." + juniperProperties.getProperty("Database." + this.m_databaseName + ".defaultConfiguration") + ".servergroups")[0] + ".ipver");
                final String property5 = agentProperties.getProperty("agent.host");
                final String displayName = this.getDisplayName(true);
                final Vector<String> vector = new Vector<String>();
                vector.add(e);
                vector.add("-db");
                vector.add(e3);
                vector.add("-port");
                vector.add(e2);
                vector.add("-u");
                vector.add(property2);
                vector.add("-propertyfile");
                vector.add(propertyFileName);
                vector.add("-config");
                vector.add(displayName);
                vector.add("-installdir");
                vector.add(property);
                vector.add("-ipver");
                vector.add(property4);
                if (property5 != null && property5.length() > 0) {
                    vector.add("-H");
                    vector.add(property5);
                }
                final String property6 = juniperProperties.getProperty("configuration." + this.m_databaseName + ".defaultConfiguration" + ".otherargs");
                String string = null;
                if (property6.contains("-AdminMsgTimeout")) {
                    final StringTokenizer stringTokenizer = new StringTokenizer(property6);
                    while (stringTokenizer.hasMoreTokens()) {
                        if (stringTokenizer.nextToken().toString().contains("-AdminMsgTimeout")) {
                            string = stringTokenizer.nextToken().toString();
                        }
                    }
                    vector.add("-msgtimeout");
                    vector.add(string);
                }
                vector.add("-m5");
                if (property3 != null && property3.length() > 0) {
                    final StringTokenizer stringTokenizer2 = new StringTokenizer(property3);
                    while (stringTokenizer2.hasMoreTokens()) {
                        vector.add(stringTokenizer2.nextToken());
                    }
                }
                final String[] array = vector.toArray(new String[0]);
                this.m_agentConnect = new AgentConnect(this, array);
                (this.m_agentConnectThread = new Thread(this.m_agentConnect)).setName("AgentConnectThread");
                try {
                    this.setStarting();
                    this.m_startedByMe = true;
                    this.m_agentConnectThread.start();
                }
                catch (StateException ex) {
                    Tools.px(ex, "Could not start database agent.");
                }
                String string2 = "";
                for (int i = 0; i < array.length; ++i) {
                    string2 = string2 + array[i] + " ";
                }
                ProLog.logd("DatabaseAgent", string2);
            }
        }
        catch (Exception ex2) {
            Tools.px(ex2, "Could not create database agent startup command.");
        }
    }
    
    public boolean isStopable() {
        synchronized (this.m_agentPlugIn) {
            return this.isRunning();
        }
    }
    
    public void stop() throws RemoteException {
        synchronized (this.m_agentPlugIn) {
            try {
                if (this.isStopable()) {
                    this.setShuttingDown();
                    this.sendPacket("SHUTD_REQ");
                }
            }
            catch (StateException ex) {
                Tools.px(ex, "Could not stop database agent.");
            }
        }
    }
    
    public void setStartedByMe(final boolean startedByMe) {
        this.m_startedByMe = startedByMe;
    }
    
    public void rename(final String str, final String databaseName) {
        if (this.isRemoteDb()) {
            synchronized (this.m_name) {
                this.m_name = databaseName;
                AgentDatabase.m_nameTable.remove(str);
                AgentDatabase.m_nameTable.put(databaseName, this);
                this.m_databaseName = databaseName;
                AgentPlugIn.removeAgentDatabase(adjustName("_SMDatabase_" + str));
                AgentPlugIn.addAgentDatabase(adjustName("_SMDatabase_" + str), this);
            }
        }
    }
    
    public String name() {
        if (this.isRemoteDb()) {
            return this.m_name;
        }
        return super.name();
    }
    
    public void delete(final Object o) throws RemoteException {
        AgentPlugIn.removeAgentDatabase(this.name());
        super.delete();
    }
    
    public boolean isDeleteable() {
        return true;
    }
    
    public IJAHierarchy getParent() {
        return null;
    }
    
    public String makeNewChildName() throws RemoteException {
        synchronized (this.m_agentPlugIn) {
            throw new RemoteException("Children of database agents not supported.");
        }
    }
    
    public boolean childNameUsed(final String s) {
        return false;
    }
    
    public IJAHierarchy createChild(final String s, final Object o, final Object o2) throws RemoteException {
        synchronized (this.m_agentPlugIn) {
            throw new RemoteException("Children of database agents not supported.");
        }
    }
    
    public Enumeration getChildrenObjects() {
        return null;
    }
    
    public void exiting() {
        synchronized (this.m_agentPlugIn) {
            try {
                this.setIdle();
            }
            catch (StateException ex) {
                Tools.px(ex, "Database agent exception while trying to exit.");
            }
        }
    }
    
    public void returnQueryResults(final String queryResultsId, final byte[] queryResultsData) {
        synchronized (this.m_queryResultsLock) {
            this.m_queryResultsId = queryResultsId;
            this.setRowCount(this.m_queryResultsData = queryResultsData);
            this.m_queryResultsDone = true;
            this.m_queryResultsLock.notify();
        }
    }
    
    public void stopQuery() {
        synchronized (this.m_queryResultsLock) {
            this.m_queryResultsDone = true;
            this.m_queryResultsLock.notify();
        }
    }
    
    public byte[] doQuery(final String s, final String s2) {
        synchronized (this.m_queryResultsLock) {
            byte[] array = null;
            this.m_queryResultsDone = false;
            if (!this.sendPacket(s, s2)) {
                return null;
            }
            while (!this.m_queryResultsDone) {
                try {
                    this.m_queryResultsLock.wait();
                    if (this.m_queryResultsId.equals("QRY_VST_TBL_ACK")) {
                        array = this.m_queryResultsData;
                    }
                    else {
                        if (!this.m_queryResultsId.equals("QRY_VST_COL_ACK")) {
                            continue;
                        }
                        array = this.m_queryResultsData;
                    }
                    continue;
                }
                catch (InterruptedException ex) {}
                break;
            }
            return array;
        }
    }
    
    protected void setRowCount(final byte[] array) {
        if (array == null || array.length < 4) {
            this.m_queryRowCount = 0;
        }
        else {
            this.m_queryRowCount = (array[0] << 16) + (array[0] << 12) + (array[2] << 8) + array[3];
        }
    }
    
    public int getQueryResultsRowCount() {
        return this.m_queryRowCount;
    }
    
    public byte[] getQueryResultsData() {
        return this.m_queryResultsData;
    }
    
    public String getQueryResultsId() {
        return this.m_queryResultsId;
    }
    
    public boolean sendPacket(final String s) {
        return this.sendPacket(new ComMsgAgent(s));
    }
    
    public boolean sendPacket(final String s, final String s2) {
        return this.sendPacket(new ComMsgAgent(s, s2.getBytes()));
    }
    
    public boolean sendPacket(final ComMsgAgent comMsgAgent) {
        boolean sendPacket = false;
        try {
            final int idValue = this.getIdValue();
            if (idValue >= 0) {
                sendPacket = sendPacket(comMsgAgent, idValue);
            }
        }
        catch (NullPointerException ex) {
            Tools.px(ex, "Unknown client id.  Could not send message: " + comMsgAgent.getMsgName());
            sendPacket = false;
        }
        return sendPacket;
    }
    
    public static boolean sendPacket(final ComMsgAgent comMsgAgent, final int n) {
        return sendPacketX(comMsgAgent, n);
    }
    
    public static boolean sendPacketX(final ComMsgAgent comMsgAgent, final int n) {
        try {
            AgentPlugIn.getServerComSockAgent().send(n, comMsgAgent);
        }
        catch (Throwable t) {
            return false;
        }
        return true;
    }
    
    public static Boolean disconnectUser(final String s, final Integer n) {
        boolean disconnectUser = false;
        try {
            final AgentDatabase agentDatabase = findAgentDatabase(s);
            if (agentDatabase != null && n > 0) {
                disconnectUser = agentDatabase.disconnectUser(n.toString());
            }
        }
        catch (Exception ex) {
            disconnectUser = false;
        }
        return new Boolean(disconnectUser);
    }
    
    public boolean disconnectUser(final String s) {
        boolean sendPacket = false;
        if (this.isRunning()) {
            sendPacket = this.sendPacket("DISC_U_REQ", s);
        }
        return sendPacket;
    }
    
    static {
        AgentDatabase.m_self = null;
        AgentDatabase.m_nameTable = new NameContext();
        AgentDatabase.resources = (ProgressResources)ResourceBundle.getBundle("com.progress.international.messages.JAGenericBundle");
        AgentDatabase.theLog = null;
    }
}
