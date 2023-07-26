// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import com.progress.chimera.container.events.ContainerDeleteTreeNodeEvent;
import com.progress.chimera.common.IChimeraHierarchy;
import com.progress.chimera.container.events.ContainerAddTreeNodeEvent;
import java.util.Hashtable;
import com.progress.mf.runtime.ManagementPlugin;
import com.progress.mf.AbstractPluginComponent;
import com.progress.common.property.IPropertyManagerRemote;
import com.progress.common.log.ProLog;
import java.rmi.Remote;
import java.util.ResourceBundle;
import com.progress.international.resources.ProgressResources;
import com.progress.common.property.EPropertiesChanged;
import java.lang.reflect.InvocationTargetException;
import com.progress.chimera.adminserver.IAdminServerConst;
import java.io.FileNotFoundException;
import com.progress.common.log.LogSystem;
import java.util.StringTokenizer;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import com.progress.common.comsock.ServerComSockListener;
import java.net.UnknownHostException;
import java.net.InetAddress;
import java.io.IOException;
import java.util.Enumeration;
import com.progress.chimera.util.SerializableEnumeration;
import com.progress.chimera.common.Tools;
import com.progress.common.networkevents.IEventObject;
import com.progress.common.networkevents.IEventStream;
import com.progress.common.networkevents.EventBroker;
import com.progress.common.log.Excp;
import com.progress.common.networkevents.IEventBroker;
import com.progress.common.licensemgr.ILicenseMgr;
import com.progress.chimera.adminserver.ServerPluginInfo;
import com.progress.chimera.adminserver.AdminServer;
import java.rmi.server.RemoteStub;
import java.rmi.RemoteException;
import java.util.Properties;
import com.progress.common.comsock.ServerComSock;
import java.util.Vector;
import com.progress.common.property.PropertyManager;
import com.progress.chimera.log.ConnectionManagerLog;
import com.progress.chimera.adminserver.IAdministrationServer;
import com.progress.chimera.adminserver.ServerPolicyInfo;
import com.progress.mf.IManagedPlugin;
import com.progress.message.jpMsg;
import com.progress.chimera.adminserver.IServerPlugin;
import com.progress.common.property.IPropertyProvider;
import com.progress.chimera.common.ChimeraRemoteObject;

public class JAPlugIn extends ChimeraRemoteObject implements IJAPlugIn, IPropertyProvider, IServerPlugin, jpMsg, MProservJuniperAPI, IManagedPlugin, Runnable
{
    public static String FATHOM_DATABASE_DISPLAYNAME;
    public static String PLUGIN_ID;
    private static JAPlugIn self;
    private static JAPluginComponent m_pluginComponent;
    Boolean m_isAgentRemoteSupported;
    Boolean m_isAgentLicensed;
    Boolean m_isFathomLicensed;
    Boolean m_isReplLicensed;
    private int m_pluginIdx;
    private ServerPolicyInfo m_policyInfo;
    static IAdministrationServer adminServer;
    static ConnectionManagerLog theLog;
    static String configFile;
    static final String juniperCommand = "proserve";
    static final int numberConnectAttempts = 5;
    static final int waitPeriodForStartUp = 1;
    static PropertyManager properties;
    static Vector databases;
    static ServerComSock socketIntf;
    static Integer serverPort;
    static ConfigListener configListener;
    static Properties timings;
    static String[][] defTimings;
    String displayName;
    private boolean m_isShuttingDown;
    boolean autostartsCompleted;
    private static Vector autostartsRemaining;
    private JARmiComponent m_jaRmiComponent;
    
    public static JAPlugIn get() {
        return JAPlugIn.self;
    }
    
    public JAPlugIn() throws RemoteException {
        this.m_isAgentRemoteSupported = null;
        this.m_isAgentLicensed = null;
        this.m_isFathomLicensed = null;
        this.m_isReplLicensed = null;
        this.m_pluginIdx = 0;
        this.m_policyInfo = null;
        this.displayName = null;
        this.m_isShuttingDown = false;
        this.autostartsCompleted = false;
        this.m_jaRmiComponent = null;
        JATools.setIsServer();
        JAPlugIn.self = this;
        this.m_jaRmiComponent = new JARmiComponent(this);
    }
    
    public RemoteStub remoteStub() {
        return this.stub();
    }
    
    public IJAPlugIn evThis() {
        return (IJAPlugIn)this.remoteStub();
    }
    
    public static String getCanonicalName() {
        return (JAPlugIn.m_pluginComponent != null) ? JAPlugIn.m_pluginComponent.getCanonicalName() : "";
    }
    
    private void setLicensing() {
        this.m_isAgentRemoteSupported = new Boolean(false);
        this.m_isAgentLicensed = new Boolean(false);
        this.m_isFathomLicensed = new Boolean(false);
        this.m_isReplLicensed = new Boolean(false);
        final Vector serverPluginInfo = ((AdminServer)JAPlugIn.adminServer).getServerPluginInfo();
        for (int index = 0; serverPluginInfo != null && index < serverPluginInfo.size(); ++index) {
            final ServerPluginInfo serverPluginInfo2 = serverPluginInfo.elementAt(index);
            final String personality = serverPluginInfo2.getPersonality();
            if (personality != null) {
                if (personality.equalsIgnoreCase("DatabaseAgent") && serverPluginInfo2.getPluginInstance() != null) {
                    this.m_isAgentLicensed = new Boolean(true);
                }
                else if (personality.equalsIgnoreCase("Fathom") && serverPluginInfo2.getPluginInstance() != null) {
                    this.m_isFathomLicensed = new Boolean(true);
                }
                else if (personality.equalsIgnoreCase("Replication") && serverPluginInfo2.getPluginInstance() != null) {
                    this.m_isReplLicensed = new Boolean(true);
                }
            }
        }
        if (!this.m_isFathomLicensed) {
            try {
                Class.forName("com.progress.isq.ipqos.Initialization");
                this.m_isFathomLicensed = new Boolean(true);
            }
            catch (ClassNotFoundException ex) {}
        }
        boolean booleanProperty = false;
        if (!this.m_isAgentLicensed && JAPlugIn.properties != null) {
            booleanProperty = JAPlugIn.properties.getBooleanProperty("AgentRemoteConnection.AgentRemoteSupport");
        }
        this.m_isAgentRemoteSupported = new Boolean(booleanProperty);
    }
    
    public boolean isAgentRemoteSupported() {
        if (this.m_isAgentRemoteSupported == null) {
            this.setLicensing();
        }
        return this.m_isAgentRemoteSupported;
    }
    
    public boolean isAgentLicensed() {
        if (this.m_isAgentLicensed == null) {
            this.setLicensing();
        }
        return this.m_isAgentLicensed;
    }
    
    public boolean isFathomLicensed() {
        if (this.m_isFathomLicensed == null) {
            this.setLicensing();
        }
        return this.m_isFathomLicensed;
    }
    
    public boolean isReplLicensed() {
        if (this.m_isReplLicensed == null) {
            this.setLicensing();
        }
        return this.m_isReplLicensed;
    }
    
    public ServerPolicyInfo getPolicy() {
        return this.m_policyInfo;
    }
    
    static IAdministrationServer adminServer() {
        return JAPlugIn.adminServer;
    }
    
    public ILicenseMgr getLicenseManager() throws RemoteException {
        return ((AdminServer)JAPlugIn.adminServer).getLicenseManager();
    }
    
    public String getOS() throws RemoteException {
        return JAPlugIn.adminServer.getOS();
    }
    
    public IEventBroker getEventBroker() {
        try {
            return JAPlugIn.adminServer.getEventBroker();
        }
        catch (RemoteException ex) {
            Excp.print(ex);
            return null;
        }
    }
    
    public EventBroker getEventBrokerLocal() {
        try {
            return JAPlugIn.adminServer.getEventBrokerLocal();
        }
        catch (RemoteException ex) {
            Excp.print(ex);
            return null;
        }
    }
    
    public IEventStream getEventStream() throws RemoteException {
        try {
            return JAPlugIn.adminServer.getEventStream();
        }
        catch (RemoteException ex) {
            Excp.print(ex);
            return null;
        }
    }
    
    IAdministrationServer getAdminServer() throws RemoteException {
        return JAPlugIn.adminServer;
    }
    
    public static ConnectionManagerLog getLog() {
        if (JAPlugIn.theLog == null) {
            JAPlugIn.theLog = ConnectionManagerLog.get();
        }
        return JAPlugIn.theLog;
    }
    
    static String getConfigFile() {
        return JAPlugIn.configFile;
    }
    
    String configFile() {
        return JAPlugIn.configFile;
    }
    
    public PropertyManager properties() {
        return JAPlugIn.properties;
    }
    
    public static PropertyManager propertiesS() {
        return JAPlugIn.properties;
    }
    
    protected void addDatabase(final JADatabase jaDatabase, final Object o) {
        try {
            if (!JAPlugIn.databases.contains(jaDatabase)) {
                JAPlugIn.databases.addElement(jaDatabase);
            }
            this.getEventBroker().postEvent(new EDatabaseAdded(this.evThis(), jaDatabase, o));
            this.postContainerAddedEvent(jaDatabase, o);
        }
        catch (RemoteException ex) {
            Tools.px(ex);
        }
    }
    
    public SerializableEnumeration getDatabases() {
        synchronized (this) {
            return new SerializableEnumeration(JAPlugIn.databases);
        }
    }
    
    public Vector getDatabasesVector() {
        return JAPlugIn.databases;
    }
    
    boolean removeDatabase(final JADatabase obj, final Object o) {
        boolean removeElement = false;
        try {
            removeElement = JAPlugIn.databases.removeElement(obj);
            this.getEventBroker().postEvent(new EDatabaseRemoved(this.evThis(), obj, o));
            this.postContainerDeletedEvent(obj, o);
        }
        catch (RemoteException ex) {
            Tools.px(ex);
        }
        return removeElement;
    }
    
    public synchronized Enumeration getChildrenObjects() throws RemoteException {
        return this.getChildren();
    }
    
    public Enumeration getChildren() throws RemoteException {
        synchronized (this) {
            return this.getDatabases();
        }
    }
    
    public PropertyManager getPlugInPropertyManager() {
        return this.properties();
    }
    
    static String getServerPort() {
        return JAPlugIn.serverPort.toString();
    }
    
    static ServerComSock getSocketIntf() throws RemoteException, IOException {
        if (JAPlugIn.socketIntf == null) {
            initSocket();
        }
        return JAPlugIn.socketIntf;
    }
    
    static void initSocket() throws RemoteException, IOException {
        final String adminPort = JAPlugIn.adminServer.getAdminPort();
        if (adminPort != null) {
            JAPlugIn.serverPort = new Integer(adminPort);
        }
        if (JAPlugIn.serverPort == null) {
            JAPlugIn.serverPort = new Integer(7833);
        }
        InetAddress byName = null;
        try {
            final String property = System.getProperty("java.rmi.server.hostname");
            final String property2 = System.getProperty("forceIPver");
            if (property != null && property2 != null) {
                byName = InetAddress.getByName(property);
            }
        }
        catch (UnknownHostException ex) {}
        if (byName != null) {
            getLog().log(3, "Opening socket connection on port " + (int)JAPlugIn.serverPort + " at address " + byName);
            JAPlugIn.socketIntf = new ServerComSock(JAPlugIn.serverPort, byName);
        }
        else {
            getLog().log(3, 7669630165411963150L, new Object[] { JAPlugIn.serverPort });
            JAPlugIn.socketIntf = new ServerComSock(JAPlugIn.serverPort);
        }
        getLog().log(3, 7669630165411963151L);
        JAPlugIn.socketIntf.connect();
        JAPlugIn.socketIntf.addEventListener(JAPlugIn.configListener = new ConfigListener());
    }
    
    static void closeSocket() throws Exception {
        JAPlugIn.socketIntf.disconnect();
        JAPlugIn.socketIntf.join();
    }
    
    static Properties timings() {
        return JAPlugIn.timings;
    }
    
    void initTimings() {
        JAPlugIn.timings = new Properties();
        if (System.getProperty("BypassDatabaseTimings") != null) {
            for (int i = 0; i < JAPlugIn.defTimings.length; ++i) {
                JAPlugIn.timings.setProperty(JAPlugIn.defTimings[i][0].toLowerCase(), "0");
            }
        }
        else {
            for (int j = 0; j < JAPlugIn.defTimings.length; ++j) {
                JAPlugIn.timings.setProperty(JAPlugIn.defTimings[j][0].toLowerCase(), JAPlugIn.defTimings[j][1]);
            }
        }
        final String property = System.getProperty("ConfigToolTimings");
        try {
            if (property != null) {
                String line;
                while ((line = new BufferedReader(new InputStreamReader(new FileInputStream(property))).readLine()) != null) {
                    final int index = line.indexOf(35);
                    final String s = (index == -1) ? line : line.substring(0, index);
                    if (s.length() == 0) {
                        continue;
                    }
                    final StringTokenizer stringTokenizer = new StringTokenizer(s.trim().toLowerCase(), "=");
                    if (stringTokenizer.countTokens() >= 2) {
                        JAPlugIn.timings.setProperty(stringTokenizer.nextToken(), stringTokenizer.nextToken());
                    }
                    else {
                        getLog();
                        LogSystem.logItErr("ConnectionManager", "Ignoring line from file " + property + ": " + property);
                    }
                }
            }
        }
        catch (FileNotFoundException ex) {
            getLog();
            LogSystem.logItErr("ConnectionManager", "Could not find file: " + property);
        }
        catch (IOException ex2) {
            getLog();
            LogSystem.logItErr("ConnectionManager", "Could not load file: " + property);
        }
        getLog().log(3, 7669630165411963152L);
        final Enumeration<String> keys = ((Hashtable<String, V>)JAPlugIn.timings).keys();
        while (keys.hasMoreElements()) {
            final String key = keys.nextElement();
            getLog().log(3, 7669630165411963153L, new Object[] { key, JAPlugIn.timings.get(key) });
        }
    }
    
    static int getTiming(final String s) {
        final String trim = JAPlugIn.timings.getProperty(s.toLowerCase()).trim();
        int int1 = 1;
        try {
            int1 = Integer.parseInt(trim);
        }
        catch (NumberFormatException ex) {}
        return int1;
    }
    
    static int getProratedTiming(final String s) {
        return getProratedTiming(s, null);
    }
    
    static int getProratedTiming(final String s, final String s2) {
        int n = getTiming(s) * get().getAutostartsRemainingSize();
        if (s2 != null) {
            final int timing = getTiming(s2);
            n = ((timing > n) ? timing : n);
        }
        return n;
    }
    
    public synchronized boolean init(final int pluginIdx, final IAdministrationServer adminServer, final String[] array) {
        getLog().log(4, 7669630165411962832L);
        this.initTimings();
        this.m_pluginIdx = pluginIdx;
        try {
            JAPlugIn.configFile = adminServer.getDbPropFile();
        }
        catch (RemoteException ex9) {}
        if (JAPlugIn.configFile == null) {
            JAPlugIn.configFile = System.getProperty("Juniper.ConfigFile");
        }
        if (JAPlugIn.configFile == null) {
            JAPlugIn.configFile = IAdminServerConst.INSTALL_DIR + IAdminServerConst.FILE_SEPARATOR + array[0].replace('/', IAdminServerConst.FILE_SEPARATOR.charAt(0));
            getLog().log(2, 7669630165411962937L, new Object[] { JAPlugIn.configFile });
        }
        else {
            getLog().log(2, 7669630165411962938L, new Object[] { JAPlugIn.configFile });
        }
        JAPlugIn.adminServer = adminServer;
        try {
            this.load();
        }
        catch (Exception ex) {
            Tools.px(ex, "Juniper: Failed to load.");
        }
        try {
            initSocket();
        }
        catch (RemoteException ex2) {
            Tools.px(ex2);
            return false;
        }
        catch (IOException ex3) {
            Tools.px(ex3, "Failed to establish remote socket connection.");
            return false;
        }
        this.setLicensing();
        try {
            if (this.isAgentLicensed()) {
                Class.forName("com.progress.agent.database.AgentSocketInt").getConstructor((Class<?>[])new Class[0]).newInstance(new Object[0]);
            }
        }
        catch (InvocationTargetException ex4) {
            Tools.px(ex4, "Failed to establish agent socket port.");
            return true;
        }
        catch (ClassNotFoundException ex5) {
            Tools.px(ex5, "Failed to establish agent socket port.");
            return true;
        }
        catch (NoSuchMethodException ex6) {
            Tools.px(ex6, "Failed to establish agent socket port.");
            return true;
        }
        catch (InstantiationException ex7) {
            Tools.px(ex7, "Failed to establish agent socket port.");
            return true;
        }
        catch (IllegalAccessException ex8) {
            Tools.px(ex8, "Failed to establish agent socket port.");
            return true;
        }
        return true;
    }
    
    public void load() throws Exception {
        try {
            JAPlugIn.properties = new JuniperProperties(JAPlugIn.configFile, (EventBroker)this.getEventBroker());
        }
        catch (PropertyManager.GroupNameException ex) {
            Tools.px(ex, "Juniper: Property file contains invalid group names.");
            throw ex;
        }
        catch (PropertyManager.PropertyNameException ex2) {
            Tools.px(ex2, "Juniper: Property file contains invalid property names.");
            throw ex2;
        }
        catch (PropertyManager.PropertyValueException ex3) {
            Tools.px(ex3, "Juniper: Property file contains invalid property values.");
            throw ex3;
        }
        catch (PropertyManager.PropertySyntaxException ex4) {
            Tools.px(ex4, "Juniper: Property file is malformed.");
            throw ex4;
        }
        catch (PropertyManager.LoadIOException ex5) {
            Tools.px(ex5, "Juniper: Property file failed to load.");
            throw ex5;
        }
        catch (PropertyManager.LoadFileNotFoundException ex6) {
            JAPlugIn.properties = this.createEmptyPropsFile();
            return;
        }
        getLog().log(1, 7669630165411962073L);
        this.loadDatabases();
    }
    
    PropertyManager createEmptyPropsFile() {
        try {
            JAPlugIn.properties = new JuniperProperties((EventBroker)this.getEventBroker());
            JATools.writeOutProperties(this);
        }
        catch (PropertyManager.PropertyException ex) {
            Tools.px(ex, "Could not create new property file.");
        }
        return JAPlugIn.properties;
    }
    
    protected void refreshDatabases() {
        final Vector<String> vector = new Vector<String>();
        final Vector databasesVector = this.getDatabasesVector();
        final Vector vector2 = new Vector<String>();
        try {
            final String[] groups = new JuniperProperties(JAPlugIn.properties.getPath()).groups("Database", true, false);
            if (groups != null) {
                for (int i = 0; i < groups.length; ++i) {
                    vector.add(groups[i].toLowerCase());
                }
            }
            for (int j = 0; j < databasesVector.size(); ++j) {
                vector2.add(databasesVector.elementAt(j).getDisplayName(false).toLowerCase());
            }
            for (int k = 0; k < vector2.size(); ++k) {
                final JADatabase jaDatabase = databasesVector.elementAt(k);
                if (vector.contains(vector2.elementAt(k))) {
                    get().getEventBroker().postEvent(new EPropertiesChanged(jaDatabase.remoteStub(), null));
                    jaDatabase.refreshConfigurations();
                }
                else {
                    jaDatabase.delete(false, null);
                }
            }
            for (int l = 0; l < vector.size(); ++l) {
                final String s = vector.elementAt(l);
                if (!vector2.contains(s)) {
                    JADatabase.instantiateExisting(this, JAPlugIn.properties.getProperty("Database." + s + ".displayname"));
                }
            }
        }
        catch (Exception ex) {
            getLog();
            LogSystem.logIt("ConnectionMgr", 3, "Unable to refresh databases.");
        }
    }
    
    protected void loadDatabases() {
        String[] groups;
        try {
            groups = JAPlugIn.properties.groups("Database", true, false);
        }
        catch (PropertyManager.PropertyException ex) {
            Tools.px(ex, "Juniper: Failed to load list of databases.");
            return;
        }
        if (groups == null || groups.length == 0) {
            return;
        }
        try {
            for (int i = 0; i < groups.length; ++i) {
                final String trim = groups[i].trim();
                if (!trim.equals("")) {
                    getLog().log(2, 7669630165411962074L, new Object[] { groups[i] });
                    JADatabase.instantiateExisting(this, trim);
                }
            }
        }
        catch (JADatabase.DatabaseException ex2) {
            Tools.px(ex2, "Juniper: Failed loading databases");
        }
        catch (RemoteException ex3) {
            Tools.px(ex3, "Juniper: Failed loading databases");
        }
    }
    
    public synchronized String getDisplayName() {
        return this.getDisplayName(true);
    }
    
    public synchronized String getDisplayName(final boolean b) {
        if (this.displayName == null) {
            this.displayName = ((ProgressResources)ResourceBundle.getBundle("com.progress.international.messages.JAPlugInGUIBundle")).getTranString("Databases");
        }
        return this.displayName;
    }
    
    public void shutdown() {
        getLog().log(2, 7669630165411962075L);
        this.m_isShuttingDown = true;
        try {
            closeSocket();
            JAPlugIn.m_pluginComponent = null;
        }
        catch (Exception ex) {
            Excp.print(ex);
        }
        JAPlugIn.configListener.ignoreInput();
        final Enumeration<JADatabase> elements = JAPlugIn.databases.elements();
        while (elements.hasMoreElements()) {
            final JAConfiguration jaConfiguration = (JAConfiguration)elements.nextElement().getRunningConfiguration();
            if (jaConfiguration != null) {
                jaConfiguration.unbind();
            }
        }
    }
    
    public Remote getRemoteObject(final String s, final String s2) {
        return this;
    }
    
    public Vector getAutostartsRemaining() {
        return JAPlugIn.autostartsRemaining;
    }
    
    public int getAutostartsRemainingSize() {
        if (JAPlugIn.autostartsRemaining == null) {
            return this.getDatabasesVector().size();
        }
        return JAPlugIn.autostartsRemaining.size();
    }
    
    public boolean initializationComplete() {
        if (this.m_isShuttingDown) {
            if (JAPlugIn.autostartsRemaining != null) {
                JAPlugIn.autostartsRemaining.clear();
            }
            getLog().log(4, "Database plugin is shutting down; autostarts terminated.");
            return true;
        }
        if (JAPlugIn.autostartsRemaining == null) {
            getLog().log(4, "Database plugin autostarting is not begun.");
            return false;
        }
        final Enumeration<JADatabase> elements = JAPlugIn.autostartsRemaining.elements();
        while (elements.hasMoreElements()) {
            final JADatabase obj = elements.nextElement();
            try {
                if (!obj.isRunning() && !obj.isIdle()) {
                    continue;
                }
                JAPlugIn.autostartsRemaining.removeElement(obj);
            }
            catch (RemoteException ex) {
                Tools.px(ex);
            }
        }
        if (JAPlugIn.autostartsRemaining.size() > 0) {
            getLog().log(4, "Database plugin autostarting is not complete; " + JAPlugIn.autostartsRemaining.size() + " remaining.");
            return false;
        }
        getLog().log(4, "Database plugin autostarting is complete");
        return true;
    }
    
    public IJARmiComponent getJARmiComponent() throws RemoteException {
        if (this.m_jaRmiComponent == null) {
            this.m_jaRmiComponent = new JARmiComponent(this);
        }
        return this.m_jaRmiComponent;
    }
    
    public void run() {
        this.m_policyInfo = ((AdminServer)JAPlugIn.adminServer).getServerPluginInfo().elementAt(this.m_pluginIdx).getPolicy();
        int i = getTiming("DbStartupDelayMin");
        int messageCount = 0;
        int n = 1;
        try {
            while (n != 0 && !this.m_isShuttingDown) {
                Thread.sleep(i * 1000);
                final int n2 = messageCount;
                messageCount = JAPlugIn.configListener.getMessageCount();
                if (messageCount > n2) {
                    i = getTiming("DbStartupDelay");
                    getLog().log(4, JAPlugIn.configListener.getConnectionCount() + " database brokers have requested reconnect.");
                    getLog().log(3, "Waiting another " + i + " seconds for database brokers to reconnect; current reconnection count is " + JAPlugIn.configListener.getRunningCount());
                }
                else {
                    n = 0;
                }
            }
            getLog().log(3, JAPlugIn.configListener.getConnectionCount() + " database brokers have been reconnected.");
            getLog().log(2, 7669630165411962077L);
            final Enumeration elements = JAPlugIn.databases.elements();
            while (!this.m_isShuttingDown && elements.hasMoreElements()) {
                final JADatabase jaDatabase = elements.nextElement();
                final JAConfiguration jaConfiguration = (JAConfiguration)jaDatabase.getDefaultConfiguration();
                if (jaConfiguration != null) {
                    this.properties().getBooleanProperty("Database." + jaDatabase.name() + ".Autostart");
                    getLog().log(3, ProgressResources.retrieveTranString("com.progress.international.messages.ADMMsgBundle", "Configuration qualified:", (Object)jaConfiguration.name()));
                }
            }
            getLog().log(3, ProgressResources.retrieveTranString("com.progress.international.messages.ADMMsgBundle", "End list of qualified databases."));
            final Vector<JADatabase> autostartsRemaining = new Vector<JADatabase>();
            final Enumeration elements2 = JAPlugIn.databases.elements();
            while (!this.m_isShuttingDown && elements2.hasMoreElements()) {
                final JADatabase obj = elements2.nextElement();
                final JAConfiguration jaConfiguration2 = (JAConfiguration)obj.getDefaultConfiguration();
                if (jaConfiguration2 != null && this.properties().getBooleanProperty("Database." + obj.name() + ".Autostart") && !obj.isRunning()) {
                    getLog().log(3, ProgressResources.retrieveTranString("com.progress.international.messages.ADMMsgBundle", "Autostarting database", (Object)obj.name()));
                    getLog().log(3, 7669630165411962078L, jaConfiguration2.name());
                    jaConfiguration2.start();
                    final JAService mainServerGroup = jaConfiguration2.getMainServerGroup();
                    if (mainServerGroup == null || !mainServerGroup.isNetworked()) {
                        continue;
                    }
                    autostartsRemaining.addElement(obj);
                }
            }
            JAPlugIn.autostartsRemaining = autostartsRemaining;
            this.getEventBroker().postEvent(new EAutostartCompleted(this.evThis()));
        }
        catch (RemoteException ex) {
            Tools.px(ex, "### Exception when autostarting databases. ###");
        }
        catch (InterruptedException ex2) {
            if (this.m_isShuttingDown) {
                ProLog.logd("ConnectionManager", 3, "Database plugin startup interrupted by shutdown request");
            }
        }
    }
    
    public String getHelpMapFile() throws RemoteException {
        return null;
    }
    
    public synchronized String getApplicationName() throws RemoteException {
        return this.getDisplayName();
    }
    
    public synchronized String makeNewChildName() {
        return this.makeNewDatabaseName();
    }
    
    public String makeNewDatabaseName() {
        synchronized (this) {
            return JADatabase.makeNewName();
        }
    }
    
    public synchronized boolean childNameUsed(final String s) {
        return this.databaseNameUsed(s);
    }
    
    public static Character getInvalidChar(final String str) {
        final StringBuffer sb = new StringBuffer(str);
        for (int i = 0; i < sb.length(); ++i) {
            final char char1 = sb.charAt(i);
            if (!Character.isJavaIdentifierPart(char1) && char1 != '-' && char1 != ':') {
                return new Character(char1);
            }
        }
        return null;
    }
    
    public boolean databaseNameUsed(final String s) {
        synchronized (this) {
            return JADatabase.nameUsed(s) || getInvalidChar(s) != null;
        }
    }
    
    public synchronized IJAHierarchy createChild(final String s, final Object o, final Object o2) throws RemoteException {
        return this.createDatabase(s, (String)o, o2);
    }
    
    public synchronized IJADatabase createDatabase(final String s, final String s2, final Object o) throws RemoteException {
        IJADatabase instantiateNew = null;
        try {
            instantiateNew = JADatabase.instantiateNew(this, s, s2, false, o);
        }
        catch (JADatabase.DatabaseException ex) {
            Tools.px(ex);
        }
        return instantiateNew;
    }
    
    public IJAPlugIn getPlugIn() {
        return this;
    }
    
    public IJAHierarchy getParent() {
        return null;
    }
    
    public IPropertyManagerRemote getPropertyManager() throws RemoteException {
        return this.properties();
    }
    
    public String getMMCClientClass() throws RemoteException {
        return "com.progress.vj.juniper.JuniperMMCPlugInClient";
    }
    
    public String getPropFileName() {
        return getConfigFile();
    }
    
    public void initManagedPlugin(final AbstractPluginComponent abstractPluginComponent) {
        JAPlugIn.m_pluginComponent = (JAPluginComponent)abstractPluginComponent;
        if (JAPlugIn.m_pluginComponent != null) {
            JAPlugIn.m_pluginComponent.postLoadInit();
            this.setComponentState(1);
            JAPlugIn.m_pluginComponent.start();
            this.setComponentState(2);
        }
    }
    
    public AbstractPluginComponent getPluginComponent() {
        return JAPlugIn.m_pluginComponent;
    }
    
    public String getComponentClassName() {
        return JAPluginComponent.class.getName();
    }
    
    public String getPluginName() {
        return JAPlugIn.PLUGIN_ID;
    }
    
    public void setComponentState(final int n) {
        ManagementPlugin.setComponentAdapterState(n, JAPlugIn.m_pluginComponent);
    }
    
    protected void postContainerAddedEvent(final JADatabase jaDatabase, final Object o) {
        try {
            if (JAPlugIn.m_pluginComponent != null) {
                final Hashtable<String, String> hashtable = new Hashtable<String, String>();
                hashtable.put("pluginName", JAPlugIn.PLUGIN_ID);
                hashtable.put("instName", jaDatabase.getDisplayName());
                final ContainerAddTreeNodeEvent containerAddTreeNodeEvent = new ContainerAddTreeNodeEvent(jaDatabase, this, jaDatabase, o, hashtable);
                containerAddTreeNodeEvent.setSource(JAPlugIn.m_pluginComponent.getCanonicalName());
                this.getEventBroker().postEvent(containerAddTreeNodeEvent);
            }
        }
        catch (Exception ex) {
            final ConnectionManagerLog theLog = JAPlugIn.theLog;
            LogSystem.logIt("ConnectionManager", 4, "Unable to post ContainerAddTreeNodeEvent");
        }
    }
    
    protected void postContainerDeletedEvent(final JADatabase jaDatabase, final Object o) {
        try {
            if (JAPlugIn.m_pluginComponent != null) {
                final Hashtable<String, String> hashtable = new Hashtable<String, String>();
                hashtable.put("pluginName", JAPlugIn.PLUGIN_ID);
                hashtable.put("instName", jaDatabase.getDisplayName());
                final ContainerDeleteTreeNodeEvent containerDeleteTreeNodeEvent = new ContainerDeleteTreeNodeEvent(this.evThis(), jaDatabase, o, hashtable);
                containerDeleteTreeNodeEvent.setSource(JAPlugIn.m_pluginComponent.getCanonicalName());
                this.getEventBroker().postEvent(containerDeleteTreeNodeEvent);
            }
        }
        catch (Exception ex) {
            final ConnectionManagerLog theLog = JAPlugIn.theLog;
            LogSystem.logIt("ConnectionManager", 4, "Unable to post ContainerDeletedTreeNodeEvent");
        }
    }
    
    static {
        JAPlugIn.FATHOM_DATABASE_DISPLAYNAME = "FathomTrendDatabase";
        JAPlugIn.PLUGIN_ID = "Database";
        JAPlugIn.self = null;
        JAPlugIn.m_pluginComponent = null;
        JAPlugIn.adminServer = null;
        JAPlugIn.theLog = null;
        JAPlugIn.configFile = null;
        JAPlugIn.properties = null;
        JAPlugIn.databases = new Vector();
        JAPlugIn.socketIntf = null;
        JAPlugIn.serverPort = new Integer(7833);
        JAPlugIn.configListener = null;
        JAPlugIn.timings = null;
        JAPlugIn.defTimings = new String[][] { { "RTStarting", "2" }, { "WTCStartingMin", "300" }, { "WTCStarting", "120" }, { "ContStarting", "2" }, { "RTInitializing", "4" }, { "WTCInitializingMin", "300" }, { "WTCInitializing", "120" }, { "ContInitializing", "4" }, { "RTShuttingDown", "0" }, { "WTCShuttingDownMin", "300" }, { "WTCShuttingDown", "120" }, { "ContShuttingDown", "4" }, { "PollServerGroup", "11" }, { "SGStartupDelay", "2" }, { "AuxStartupDelay", "2" }, { "DbStartupDelayMin", "10" }, { "DbStartupDelay", "5" } };
        JAPlugIn.autostartsRemaining = null;
    }
}
