// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import com.progress.common.networkevents.EventListener;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import com.progress.common.text.QuotedString;
import com.progress.chimera.common.Tools;
import java.io.IOException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.File;
import com.progress.common.log.LogSystem;
import com.progress.common.property.EPropertiesChanged;
import com.progress.common.networkevents.IEventListener;
import com.progress.common.property.PropertyManager;
import com.progress.chimera.util.SerializableEnumeration;
import java.util.Enumeration;
import java.rmi.RemoteException;
import com.progress.common.log.Excp;
import com.progress.common.networkevents.IEventObject;
import java.rmi.server.RemoteStub;
import com.progress.common.networkevents.IEventInterestObject;
import com.progress.chimera.common.NameContext;
import java.util.Hashtable;
import java.util.Vector;
import com.progress.chimera.adminserver.IAdministrationServer;
import com.progress.chimera.log.ConnectionManagerLog;
import com.progress.international.resources.ProgressResources;
import com.progress.message.jpMsg;
import com.progress.chimera.adminserver.IAdminServerConst;
import com.progress.chimera.common.ChimeraNamedObject;

public class JAConfiguration extends ChimeraNamedObject implements IJAConfiguration, IAdminJuniper, IAdminServerConst, jpMsg, MProservJuniperAPI, IJAParameterizedObject
{
    static ProgressResources resources;
    int numThreads;
    static int numThreadsS;
    StateObject state;
    boolean startedByMe;
    boolean aiEnabled;
    boolean replEnabled;
    boolean clustEnabled;
    boolean isStopableNow;
    PrimaryStartupMonitorReasonable reasonableTimeMonitor;
    PrimaryStartupMonitorWorstCase worstCaseMonitor;
    Integer id;
    MProservJuniper fauxJuniper;
    IJuniper rjo;
    JAPlugIn plugIn;
    JADatabase database;
    static ConnectionManagerLog theLog;
    String displayName;
    IAdministrationServer refAdminServerInfo;
    static int juniperStartupTimeout;
    Vector services;
    JABIWriter biWriter;
    JAAIWriter aiWriter;
    JAWatchdog watchdog;
    JAAPWriter apWriter;
    Hashtable serviceByPort;
    protected static NameContext nameTable;
    static final String juniperCommand = "proserve";
    static final String juniperMainClass = "defunct";
    static final int numberConnectAttempts = 20;
    static final int waitPeriodForStartUp = 3;
    IEventInterestObject regEieio;
    IEventInterestObject crashEieio;
    static RegistrationHandler registrationHandler;
    static int sessionCounter;
    Vector secondaries;
    AuxiliaryList auxiliaries;
    ExternalStartupMonitor externalStartupMonitor;
    JAService mainServerGroup;
    
    void iT(final String s) {
        ++this.numThreads;
        ++JAConfiguration.numThreadsS;
    }
    
    void dT(final String s) {
        --this.numThreads;
        --JAConfiguration.numThreadsS;
    }
    
    static int getTiming(final String s) {
        JAPlugIn.get();
        return JAPlugIn.getTiming(s);
    }
    
    public JAStatusObject getStatus() {
        final JAStatusObjectDB jaStatusObjectDB = new JAStatusObjectDB();
        jaStatusObjectDB.displayName = this.getDisplayName();
        jaStatusObjectDB.stateDescriptor = this.getStateDescriptor();
        jaStatusObjectDB.isStartable = this.isStartable();
        jaStatusObjectDB.isStopable = this.isStopable();
        jaStatusObjectDB.isIdle = this.isIdle();
        jaStatusObjectDB.isStarting = this.isStarting();
        jaStatusObjectDB.isInitializing = this.isInitializing();
        jaStatusObjectDB.isRunning = this.isRunning();
        jaStatusObjectDB.isShuttingDown = this.isShuttingDown();
        jaStatusObjectDB.currConfig = this;
        jaStatusObjectDB.allServerGroupsRunning = false;
        jaStatusObjectDB.someServerGroupsRunning = true;
        jaStatusObjectDB.aBIRunning = true;
        jaStatusObjectDB.anAIRunning = true;
        jaStatusObjectDB.auxiliariesAreRunning = false;
        return jaStatusObjectDB;
    }
    
    public RemoteStub remoteStub() {
        return this.stub();
    }
    
    IJAConfiguration evThis() {
        return (IJAConfiguration)this.remoteStub();
    }
    
    String getProperty(final String str) {
        return this.plugIn.properties().getProperty(this.groupName() + "." + str);
    }
    
    public boolean getBooleanProperty(final String str) {
        return this.plugIn.properties().getBooleanProperty(this.groupName() + "." + str);
    }
    
    public int getIntegerProperty(final String str) {
        return this.plugIn.properties().getIntProperty(this.groupName() + "." + str);
    }
    
    void setState(final State state) throws StateException {
        if (state == this.state.get()) {
            return;
        }
        synchronized (this.plugIn) {
            synchronized (this.state) {
                try {
                    this.state.changeState(this, state);
                    this.plugIn.getEventBroker().postEvent(new EConfigurationStateChanged(this.database.evThis(), this, this.getStatus()));
                }
                catch (StateException ex) {
                    Excp.print(ex, "Error changing states on configuration.");
                    throw ex;
                }
                catch (RemoteException ex2) {
                    Excp.print(ex2, "Error changing states on configuration.");
                }
            }
        }
    }
    
    public String getStateDescriptor() {
        synchronized (this.plugIn) {
            return this.state.get().displayName();
        }
    }
    
    public boolean isStartable() {
        synchronized (this.plugIn) {
            if (this.database.startsAreBlocked()) {
                return false;
            }
            if (this.database.getRunningConfiguration() != null) {
                return false;
            }
            int n = 0;
            final Enumeration<JAService> elements = (Enumeration<JAService>)this.services.elements();
            while (elements.hasMoreElements()) {
                final JAService jaService = elements.nextElement();
                ++n;
                if (jaService.isNetworked()) {
                    return true;
                }
            }
            return n == 1;
        }
    }
    
    public boolean isNetworked() throws RemoteException {
        final Enumeration<JAService> elements = this.services.elements();
        while (elements.hasMoreElements()) {
            if (elements.nextElement().isNetworked()) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isStopable() {
        return this.isStopableNow;
    }
    
    public boolean isDeleteable() {
        synchronized (this.plugIn) {
            return this.siblingCount() > 1 && this.isIdle();
        }
    }
    
    public boolean isIdle() {
        synchronized (this.plugIn) {
            return this.state.get() == CStateIdle.get();
        }
    }
    
    public boolean isStarting() {
        synchronized (this.plugIn) {
            return this.state.get() == CStateStarting.get();
        }
    }
    
    public boolean isInitializing() {
        synchronized (this.plugIn) {
            return this.state.get() == CStateInitializing.get();
        }
    }
    
    public boolean isRunning() {
        synchronized (this.plugIn) {
            return this.state.get() == CStateRunning.get();
        }
    }
    
    public boolean isShuttingDown() {
        synchronized (this.plugIn) {
            return this.state.get() == CStateShuttingDown.get();
        }
    }
    
    void continuation(final int n) {
        if (this.reasonableTimeMonitor != null) {
            this.reasonableTimeMonitor.continuation(n);
        }
    }
    
    int idValue() {
        return this.id;
    }
    
    public static ConnectionManagerLog getLog() {
        if (JAConfiguration.theLog == null) {
            JAConfiguration.theLog = ConnectionManagerLog.get();
        }
        return JAConfiguration.theLog;
    }
    
    public Enumeration getChildrenObjects() {
        synchronized (this.plugIn) {
            return this.getServices();
        }
    }
    
    public SerializableEnumeration getServices() {
        return new SerializableEnumeration(this.services);
    }
    
    public IJAAuxiliary getBIWriter() {
        return this.biWriter;
    }
    
    public IJAAuxiliary getAIWriter() {
        return this.aiWriter;
    }
    
    public IJAAuxiliary getAPWriter() {
        return this.apWriter;
    }
    
    public IJAAuxiliary getWatchdog() {
        return this.watchdog;
    }
    
    public Enumeration getAuxiliaries() {
        final Vector<JABIWriter> vector = new Vector<JABIWriter>();
        vector.addElement(this.biWriter);
        vector.addElement((JABIWriter)this.aiWriter);
        vector.addElement((JABIWriter)this.apWriter);
        vector.addElement((JABIWriter)this.watchdog);
        return new SerializableEnumeration(vector);
    }
    
    public int getServicesCount() {
        return this.services.size();
    }
    
    Vector getServicesVector() {
        return this.services;
    }
    
    public IJAPlugIn getPlugIn() throws RemoteException {
        return this.plugIn;
    }
    
    public IJAHierarchy getParent() {
        synchronized (this.plugIn) {
            return this.getDatabase();
        }
    }
    
    public IJADatabase getDatabase() {
        return this.database;
    }
    
    public String toString() {
        return this.name();
    }
    
    String groupName() {
        return "Configuration." + this.name();
    }
    
    public void writeOutProperties() {
        synchronized (this.plugIn) {
            JATools.writeOutProperties(this.plugIn);
        }
    }
    
    public int siblingCount() {
        synchronized (this.plugIn) {
            return this.database.configurations.size();
        }
    }
    
    public String getDisplayName() {
        return this.getDisplayName(false);
    }
    
    public String getDisplayName(final boolean b) {
        synchronized (this.plugIn) {
            String s;
            if (!b) {
                s = this.displayName;
            }
            else {
                s = this.database.getDisplayName(true) + "." + this.displayName;
            }
            return s;
        }
    }
    
    protected NameContext getNameContext() {
        return JAConfiguration.nameTable;
    }
    
    public Enumeration getChildren() throws RemoteException {
        synchronized (this.plugIn) {
            return this.getServices();
        }
    }
    
    String configFile() {
        return this.plugIn.configFile();
    }
    
    PropertyManager properties() {
        return this.plugIn.properties();
    }
    
    static String makeLongName(final String s, final JADatabase jaDatabase) {
        return jaDatabase.name() + "." + adjustName(s);
    }
    
    private static String adjustName(final String s) {
        return s.trim().toLowerCase();
    }
    
    public static JAConfiguration findConfiguration(final String s) {
        try {
            return JAConfiguration.nameTable.get(adjustName(s));
        }
        catch (Throwable t) {
            return null;
        }
    }
    
    JAService findServicebyPort(final String key) {
        return this.serviceByPort.get(key);
    }
    
    public boolean portUsed(final String key) throws RemoteException {
        synchronized (this.plugIn) {
            return this.serviceByPort.containsKey(key);
        }
    }
    
    static String makeShortName(final String s, final JADatabase jaDatabase) {
        return s.substring(jaDatabase.name().length() + 1);
    }
    
    static JAConfiguration instantiateNew(final JAPlugIn jaPlugIn, final JADatabase jaDatabase, final String s, final Object o) {
        final JAConfiguration instantiateX = instantiateX(jaPlugIn, jaDatabase, s, s, o);
        try {
            jaPlugIn.properties().putProperty(instantiateX.groupName() + ".DisplayName", s);
            jaPlugIn.properties().putProperty(instantiateX.groupName() + ".Database", jaDatabase.name());
            JAService.instantiateNew(jaPlugIn, instantiateX, "defaultServerGroup", o);
            JATools.addToArrayProp(jaPlugIn, "", instantiateX.groupName() + "." + "ServerGroups");
            JATools.addToArrayProp(jaPlugIn, instantiateX.name(), "Database." + jaDatabase.name() + ".Configurations");
        }
        catch (PropertyManager.PropertyException ex) {
            Excp.print(ex);
        }
        instantiateX.writeOutProperties();
        return instantiateX;
    }
    
    static JAConfiguration instantiateExisting(final JAPlugIn jaPlugIn, final JADatabase jaDatabase, final String str) throws ConfigurationException {
        final String string = "Configuration." + jaDatabase.name() + "." + str + ".DisplayName";
        String property = jaPlugIn.properties().getProperty(string);
        if (property == null) {
            property = str;
            try {
                jaPlugIn.properties().putProperty(string, property);
            }
            catch (PropertyManager.PropertyException ex) {
                throw new ConfigurationException(ex.getMessage());
            }
            JATools.writeOutProperties(jaPlugIn);
        }
        return instantiateX(jaPlugIn, jaDatabase, str, property, null);
    }
    
    static JAConfiguration instantiateX(final JAPlugIn jaPlugIn, final JADatabase jaDatabase, final String s, final String s2, final Object o) {
        final String longName = makeLongName(s, jaDatabase);
        JAConfiguration configuration = findConfiguration(longName);
        if (configuration != null) {
            return configuration;
        }
        try {
            configuration = new JAConfiguration(jaPlugIn, jaDatabase, longName, s2);
        }
        catch (Exception ex) {
            Excp.print(ex, "Juniper: Failed to instantiate configuration: :" + longName);
        }
        if (configuration == null) {
            return configuration;
        }
        jaDatabase.addConfiguration(configuration, o);
        if (!configuration.isIdle()) {
            configuration.database.setCurrentConfiguration(configuration);
        }
        return configuration;
    }
    
    protected JAConfiguration(final JAPlugIn plugIn, final JADatabase database, final String s, final String displayName) throws ConfigurationException, RemoteException {
        super(s);
        this.numThreads = 0;
        this.state = new StateObject(CStateIdle.get());
        this.startedByMe = false;
        this.aiEnabled = false;
        this.replEnabled = false;
        this.clustEnabled = false;
        this.isStopableNow = false;
        this.id = null;
        this.fauxJuniper = null;
        this.rjo = null;
        this.plugIn = null;
        this.database = null;
        this.displayName = "";
        this.refAdminServerInfo = null;
        this.services = new Vector();
        this.biWriter = null;
        this.aiWriter = null;
        this.watchdog = null;
        this.apWriter = null;
        this.serviceByPort = new Hashtable();
        this.regEieio = null;
        this.crashEieio = null;
        this.secondaries = null;
        this.auxiliaries = null;
        this.externalStartupMonitor = null;
        this.mainServerGroup = null;
        this.fauxJuniper = new MProservJuniper(this);
        final IAdministrationServer adminServer = JAPlugIn.adminServer();
        final IAdministrationServer adminServer2 = JAPlugIn.adminServer();
        if (JAConfiguration.registrationHandler == null) {
            JAConfiguration.registrationHandler = new RegistrationHandler();
            try {
                adminServer2.getEventBroker().expressInterest(EConfigurationStarted.class, JAConfiguration.registrationHandler, plugIn.getEventStream());
            }
            catch (RemoteException ex) {
                Excp.print(ex, "Error expressing interest in registration event");
            }
        }
        this.displayName = displayName;
        this.plugIn = plugIn;
        this.database = database;
        this.plugIn.properties();
        if (adminServer != null) {
            this.refAdminServerInfo = adminServer;
        }
        this.loadServices();
        this.makeAuxiliaries();
        final PropChangeListenerObject propChangeListenerObject = new PropChangeListenerObject(this.database, this.remoteStub(), EServerGroupAdded.class, EServerGroupRemoved.class);
    }
    
    void makeAuxiliaries() {
        try {
            this.biWriter = new JABIWriter(this.plugIn, this, "BI Writer");
            this.aiWriter = new JAAIWriter(this.plugIn, this, "AI Writer");
            this.watchdog = new JAWatchdog(this.plugIn, this, "Watchdog");
            this.apWriter = new JAAPWriter(this.plugIn, this, "APW");
        }
        catch (RemoteException ex) {
            Excp.print(ex);
        }
    }
    
    protected void refreshServices() {
        final PropertyManager properties = this.plugIn.properties();
        final Vector<String> vector = new Vector<String>();
        final Vector services = this.services;
        final Vector vector2 = new Vector<String>();
        try {
            final String[] arrayProperty = properties.getArrayProperty(this.groupName() + ".ServerGroups");
            if (arrayProperty != null) {
                for (int i = 0; i < arrayProperty.length; ++i) {
                    vector.add(arrayProperty[i]);
                }
            }
            for (int j = 0; j < services.size(); ++j) {
                vector2.add(services.elementAt(j).getDisplayName(true).toLowerCase());
            }
            for (int k = 0; k < vector2.size(); ++k) {
                final JAService jaService = services.elementAt(k);
                if (vector.contains(vector2.elementAt(k))) {
                    JAPlugIn.get().getEventBroker().postEvent(new EPropertiesChanged(jaService.remoteStub(), null));
                }
                else {
                    jaService.delete(null, false);
                }
            }
            for (int l = 0; l < vector.size(); ++l) {
                final String s = vector.elementAt(l);
                if (!vector2.contains(s)) {
                    JAService.instantiateExisting(this.plugIn, this, properties.getProperty("Servergroup." + s + ".displayname"));
                }
            }
        }
        catch (Exception ex) {
            ConnectionManagerLog.get();
            LogSystem.logIt("ConnectionMgr", 3, "Unable to refresh server groups.");
        }
    }
    
    void loadServices() {
        final PropertyManager properties = this.plugIn.properties();
        final String[] arrayProperty = properties.getArrayProperty(this.groupName() + ".ServerGroups");
        if (arrayProperty == null || arrayProperty.length == 0) {
            return;
        }
        try {
            for (int i = 0; i < arrayProperty.length; ++i) {
                final String trim = arrayProperty[i].trim();
                if (!trim.equals("")) {
                    getLog().log(2, 7669630165411962081L, new Object[] { trim });
                    if (properties.getProperty("ServerGroup." + trim + ".type") == null) {}
                    trim.toLowerCase();
                    JAService.instantiateExisting(this.plugIn, this, trim.substring(trim.lastIndexOf(".") + 1));
                }
            }
        }
        catch (Exception ex) {
            Excp.print(ex, "Juniper: Failed to create all services for config: " + this.name());
        }
    }
    
    IJuniper getRemoteJuniper() {
        return this.rjo;
    }
    
    protected void addService(final JAService jaService, final Object o) {
        try {
            if (!this.services.contains(jaService)) {
                this.services.addElement(jaService);
                this.addPort(jaService, jaService.getServiceNameOrPort());
                this.plugIn.getEventBroker().postEvent(new EServerGroupAdded(this.evThis(), jaService, o));
            }
        }
        catch (RemoteException ex) {
            Excp.print(ex);
        }
    }
    
    void updateServiceByPort() {
        synchronized (this.plugIn) {
            this.serviceByPort = new Hashtable();
            final Enumeration<JAService> elements = this.services.elements();
            while (elements.hasMoreElements()) {
                final JAService jaService = elements.nextElement();
                this.addPort(jaService, jaService.getServiceNameOrPort());
            }
        }
    }
    
    void updateServiceByPort(final JAService jaService) {
        this.updateServiceByPort();
    }
    
    void addPort(final JAService jaService, final String s) {
        if (!jaService.isNetworked()) {
            return;
        }
        if (this.serviceByPort.containsKey(s)) {
            Excp.print(new Exception("Duplicate port on server group: " + this + "/" + jaService));
        }
        else {
            this.serviceByPort.put(s, jaService);
        }
    }
    
    void removePort(final String key) {
        this.serviceByPort.remove(key);
    }
    
    protected void removeService(final JAService jaService, final Object o) {
        try {
            if (this.services.contains(jaService)) {
                this.services.removeElement(jaService);
                this.serviceByPort.remove(jaService.getServiceNameOrPort());
                this.plugIn.getEventBroker().postEvent(new EServerGroupRemoved(this.evThis(), jaService, o));
            }
        }
        catch (RemoteException ex) {
            Excp.print(ex);
        }
    }
    
    public String getHelpMapFile() throws RemoteException {
        return null;
    }
    
    public String getApplicationName() throws RemoteException {
        synchronized (this.plugIn) {
            return this.getDisplayName();
        }
    }
    
    static String makeNewName(final JADatabase jaDatabase) {
        String string;
        do {
            ++JAConfiguration.sessionCounter;
            string = JAConfiguration.resources.getTranString("Config-") + JAConfiguration.sessionCounter;
        } while (nameUsed(jaDatabase, string));
        return string;
    }
    
    static boolean nameUsed(final JADatabase jaDatabase, final String s) {
        return findConfiguration(makeLongName(s, jaDatabase)) != null;
    }
    
    public String makeNewChildName() {
        synchronized (this.plugIn) {
            return this.makeNewServiceName();
        }
    }
    
    public String makeNewServiceName() {
        synchronized (this.plugIn) {
            return JAService.makeNewName(this);
        }
    }
    
    public boolean childNameUsed(final String s) {
        synchronized (this.plugIn) {
            return this.serviceNameUsed(s);
        }
    }
    
    public boolean serviceNameUsed(final String s) {
        synchronized (this.plugIn) {
            return JAService.nameUsed(this, s);
        }
    }
    
    public IJAHierarchy createChild(final String s, final Object o, final Object o2) {
        synchronized (this.plugIn) {
            return this.createService(s, o2);
        }
    }
    
    public IJAService createService(final String s, final Object o) {
        synchronized (this.plugIn) {
            IJAService instantiateNew = null;
            try {
                instantiateNew = JAService.instantiateNew(this.plugIn, this, s, o);
            }
            catch (Exception ex) {
                Excp.print(ex);
            }
            return instantiateNew;
        }
    }
    
    public void exiting() {
        synchronized (this.plugIn) {
            try {
                this.setState(CStateIdle.get());
            }
            catch (StateException ex) {
                Excp.print(ex);
            }
        }
    }
    
    public void delete(final Object o) {
        synchronized (this.plugIn) {
            this.delete(o, true);
        }
    }
    
    public boolean isDefault() {
        synchronized (this.plugIn) {
            try {
                if (this == this.database.getDefaultConfiguration()) {
                    return true;
                }
            }
            catch (RemoteException ex) {}
            return false;
        }
    }
    
    public void toggleDefaultSetting() {
        synchronized (this.plugIn) {
            try {
                if (this == this.database.getDefaultConfiguration()) {
                    this.database.removeDefaultConfiguration(true);
                }
                else {
                    this.database.setDefaultConfiguration(this, true, false);
                }
                this.database.updatePanel();
            }
            catch (Exception ex) {
                Excp.print(ex);
            }
        }
    }
    
    void delete(final Object o, final boolean b) {
        try {
            final String name = this.name();
            final String groupName = this.groupName();
            super.delete();
            final JAPlugIn plugIn = this.plugIn;
            final String[] groups = JAPlugIn.properties.groups("Database", true, false);
            for (int i = 0; i < groups.length; ++i) {
                final String string = "Database." + groups[i] + ".";
                JATools.removeFromArrayProp(this.plugIn, name, string + "Configurations");
                JATools.removeFromArrayProp(this.plugIn, name, string + "DefaultConfiguration");
            }
            final JAPlugIn plugIn2 = this.plugIn;
            final String[] arrayProperty = JAPlugIn.properties.getArrayProperty(groupName + ".serverGroups");
            for (int j = 0; j < arrayProperty.length; ++j) {
                final String s = arrayProperty[j];
                if (s != null) {
                    if (!s.equals("")) {
                        JAService.findService(s).delete(o, false);
                    }
                }
            }
            final JAPlugIn plugIn3 = this.plugIn;
            JAPlugIn.properties.removeGroup(groupName);
            this.database.removeConfiguration(this, o);
            if (this.database.getDefaultConfiguration() == null) {
                this.database.pickArbitraryDefaultConfiguration(b);
            }
            else if (b) {
                JATools.writeOutProperties(this.plugIn);
            }
            this.plugIn.getEventBroker().postEvent(new EConfigurationDeleted(this.evThis(), o));
        }
        catch (PropertyManager.PropertyException ex) {
            Excp.print(ex, "Failed to complete deletion of configuration; property file may be inconsistent!");
        }
        catch (RemoteException ex2) {
            Excp.print(ex2, "Failed to complete deletion of configuration; remote error encountered.");
        }
        catch (JADatabase.DatabaseException ex3) {
            Excp.print(ex3, "Failed to complete deletion of configuration; error while updating database object information.");
        }
    }
    
    public void start() {
        synchronized (this.plugIn) {
            if (!this.isIdle()) {
                return;
            }
            try {
                String s = this.database.getProperty("databasename").trim();
                if (!s.toLowerCase().endsWith(".db")) {
                    s += ".db";
                }
                if (!new File(s).exists()) {
                    getLog().logErr(ProgressResources.retrieveTranString("com.progress.international.messages.ADMMsgBundle", "Startup of configuration failed due to missing file", new Object[] { this.name(), s }));
                    try {
                        this.plugIn.getEventBroker().postEvent(new EMissingDatabaseFile(this.database));
                        this.plugIn.getEventBroker().postEvent(new EStartupProcessCompleted(this.database));
                    }
                    catch (RemoteException ex) {
                        Excp.print(ex, "Start error while sending event.");
                    }
                }
                this.setState(CStateStarting.get());
            }
            catch (StateException ex2) {
                Excp.print(ex2, "Current state = " + this.getStateDescriptor() + "; Could not set config state to starting - reverting to idle");
                try {
                    this.setState(CStateIdle.get());
                }
                catch (StateException ex3) {
                    Excp.print(ex3, "Could not set config state to idle either.");
                }
            }
        }
    }
    
    public void unbind() {
        if (this.externalStartupMonitor != null) {
            this.externalStartupMonitor.destroyThread();
            this.externalStartupMonitor = null;
        }
        if (this.rjo != null) {
            ((MProservJuniper)this.rjo).sendPacket("ADSD");
        }
    }
    
    public void stop(final boolean b) {
        if (this.rjo == null) {
            return;
        }
        if (b) {
            try {
                this.rjo.disconnectAdminServer();
            }
            catch (RemoteException ex) {
                Excp.print(ex);
            }
        }
        this.stop();
    }
    
    public void stop() {
        synchronized (this.plugIn) {
            if (this.isShuttingDown()) {
                return;
            }
            final StringBuffer sb = new StringBuffer(this.database.name());
            if (this.database.getUserS() != null) {
                sb.append(".  User: ");
                sb.append(this.database.getUserS());
            }
            getLog().log(1, 7669630165411962086L, new Object[] { this.displayName, sb.toString() });
            if (this.isStopable()) {
                this.stopI(true);
            }
        }
    }
    
    protected void stopI(final boolean b) {
        synchronized (this.plugIn) {
            synchronized (this.state) {
                if (!this.isStarting()) {
                    if (!this.isShuttingDown()) {
                        if (b && this.rjo != null) {
                            getLog().log(1, ProgressResources.retrieveTranString("com.progress.international.messages.ADMMsgBundle", "Calling broker to request shutdown", new Object[] { this.displayName, this.database.name() }));
                            try {
                                this.rjo.shutDown();
                            }
                            catch (RemoteException ex) {
                                Excp.print(ex);
                            }
                        }
                        return;
                    }
                }
                try {
                    this.setState(CStateIdle.get());
                }
                catch (StateException ex2) {
                    Excp.print(ex2, "Could not reset config state to idle for config: " + this.name());
                }
            }
        }
    }
    
    public void configRequestedShutDown() throws RemoteException {
        getLog().log(2, 7669630165411963155L, new Object[] { this, this.id });
        this.stopI(false);
    }
    
    public void configRequestedAbnormalGracefulShutDown() throws RemoteException {
        getLog().log(2, 7669630165411963156L, new Object[] { this, this.id });
        this.stopI(false);
    }
    
    public void configRequestedAbnormalForcedShutDown() throws RemoteException {
        getLog().log(2, 7669630165411963157L, new Object[] { this, this.id });
        this.stopI(false);
    }
    
    void removeServerGroupFromMonitor(final JAService obj) {
        synchronized (this.secondaries) {
            if (this.secondaries.isEmpty()) {
                return;
            }
            this.secondaries.removeElement(obj);
            if (this.secondaries.isEmpty()) {
                getLog().log(ProgressResources.retrieveTranString("com.progress.international.messages.ADMMsgBundle", "All secondary brokers for configuration are accounted for", new Object[] { this.name() }));
                if (this.externalStartupMonitor != null) {
                    this.externalStartupMonitor.testIfDone();
                }
            }
        }
    }
    
    void secondaryUp(final JAService obj) {
        if (!obj.isRunning()) {
            this.removeServerGroupFromMonitor(obj);
        }
        synchronized (this.plugIn) {
            synchronized (this.state) {
                if (obj.isShuttingDown()) {
                    return;
                }
                try {
                    if (this.isShuttingDown()) {
                        obj.setShuttingDown();
                    }
                    else {
                        obj.setRunning();
                    }
                }
                catch (StateException ex) {
                    Excp.print(ex, "Can't set state of service: " + obj + ".");
                }
            }
        }
    }
    
    void biUp() {
        this.auxiliaries.removeBI();
        synchronized (this.plugIn) {
            synchronized (this.state) {
                if (this.biWriter.isShuttingDown()) {
                    return;
                }
                try {
                    if (this.isShuttingDown()) {
                        this.biWriter.setShuttingDown();
                    }
                    else {
                        this.biWriter.setRunning();
                    }
                }
                catch (StateException ex) {
                    Excp.print(ex, "Can't set state of auxiliary process: " + this.biWriter + ".");
                }
            }
        }
    }
    
    void aiUp() {
        this.auxiliaries.removeAI();
        synchronized (this.plugIn) {
            synchronized (this.state) {
                if (this.aiWriter.isShuttingDown()) {
                    return;
                }
                try {
                    if (this.isShuttingDown()) {
                        this.aiWriter.setShuttingDown();
                    }
                    else {
                        this.aiWriter.setRunning();
                    }
                }
                catch (StateException ex) {
                    Excp.print(ex, "Can't set state of auxiliary process: " + this.aiWriter + ".");
                }
            }
        }
    }
    
    void watchdogUp() {
        this.auxiliaries.removeWatchdog();
        synchronized (this.plugIn) {
            synchronized (this.state) {
                if (this.watchdog.isShuttingDown()) {
                    return;
                }
                try {
                    if (this.isShuttingDown()) {
                        this.watchdog.setShuttingDown();
                    }
                    else {
                        this.watchdog.setRunning();
                    }
                }
                catch (StateException ex) {
                    Excp.print(ex, "Can't set state of auxiliary process: " + this.watchdog + ".");
                }
            }
        }
    }
    
    void apwsUp(final int n) {
        this.auxiliaries.setAPWs(n);
        synchronized (this.plugIn) {
            synchronized (this.state) {
                if (this.apWriter.isShuttingDown()) {
                    return;
                }
                try {
                    if (this.isShuttingDown()) {
                        this.apWriter.setShuttingDown();
                    }
                    else {
                        this.apWriter.setRunning(n);
                    }
                }
                catch (StateException ex) {
                    Excp.print(ex, "Can't set state of auxiliary process: " + this.apWriter + ".");
                }
            }
        }
    }
    
    void agentRemoteUp() {
        try {
            final JAAgent jaAgent = (JAAgent)this.database.getAgentRemote();
            if (jaAgent != null) {
                jaAgent.handleRunning();
            }
        }
        catch (RemoteException ex) {
            Excp.print(ex, "Can't set state of remote agent process to running.");
        }
    }
    
    void secondaryCrash(final JAService jaService) {
        jaService.handleCrash();
    }
    
    void biCrash() {
        this.biWriter.handleCrash();
    }
    
    void aiCrash() {
        this.aiWriter.handleCrash();
    }
    
    void watchdogCrash() {
        this.watchdog.handleCrash();
    }
    
    void apwCrash() {
        this.apWriter.handleCrash();
    }
    
    void agentRemoteCrash() {
        try {
            final JAAgent jaAgent = (JAAgent)this.database.getAgentRemote();
            if (jaAgent != null) {
                jaAgent.handleCrash();
            }
        }
        catch (RemoteException ex) {
            Excp.print(ex, "Can't set state of remote agent process to crashed.");
        }
    }
    
    void agentRemoteShutdown() {
        try {
            final JAAgent jaAgent = (JAAgent)this.database.getAgentRemote();
            if (jaAgent != null) {
                jaAgent.handleShutdown();
            }
        }
        catch (RemoteException ex) {
            Excp.print(ex, "Can't set state of remote agent process to shutting down.");
        }
    }
    
    Process startProcess(final Vector vector, final Object o, final String s) {
        getLog().log(2, 7669630165411963158L, new Object[] { s, this.name() });
        getLog().log(2, 7669630165411962090L, new Object[] { vector });
        try {
            final String[] cmdarray = new String[vector.size()];
            for (int i = 0; i < vector.size(); ++i) {
                cmdarray[i] = vector.elementAt(i);
            }
            final Process exec = Runtime.getRuntime().exec(cmdarray);
            if (Boolean.getBoolean("DontDoReader")) {
                return exec;
            }
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(exec.getInputStream()));
            ProcessReader processReader;
            if (o instanceof JAService) {
                if (s.equals("primary")) {
                    processReader = new PrimaryReader(this, (JAService)o, bufferedReader, exec);
                }
                else {
                    processReader = new SecondaryReader(this, (JAService)o, bufferedReader, exec);
                }
            }
            else {
                processReader = new AuxiliaryReader(this, s, bufferedReader, exec);
            }
            processReader.start();
            return exec;
        }
        catch (IOException ex) {
            Excp.print(ex, "Can't start process");
            return null;
        }
    }
    
    public void propertiesChanged() {
        this.updateServiceByPort();
    }
    
    public boolean isMonitored() {
        return this.properties().getBooleanProperty("Configuration." + this.database.name() + "." + this.getDisplayName() + ".Monitored");
    }
    
    void handleAgent() {
        synchronized (this.plugIn) {
            if (this.isMonitored()) {
                try {
                    if (this.plugIn.isAgentLicensed()) {
                        final IAgentDatabaseHandle agent = this.database.getAgent();
                        if (agent.isStartable()) {
                            agent.start();
                        }
                    }
                    else if (this.plugIn.isAgentRemoteSupported()) {
                        final IJAAgent agentRemote = this.database.getAgentRemote();
                        if (agentRemote.isStartable()) {
                            agentRemote.start();
                        }
                    }
                }
                catch (RemoteException ex) {
                    Tools.px(ex, "Unable to start database agent.");
                }
            }
        }
    }
    
    public JAService getMainServerGroup() {
        return this.mainServerGroup;
    }
    
    void handleRunning() {
        this.secondaries = new Vector();
        final boolean booleanProperty = this.getBooleanProperty("beforeimageprocess");
        final boolean booleanProperty2 = this.getBooleanProperty("afterimageprocess");
        final boolean booleanProperty3 = this.getBooleanProperty("watchdogprocess");
        final int integerProperty = this.getIntegerProperty("asynchronouspagewriters");
        this.auxiliaries = new AuxiliaryList(this, booleanProperty, booleanProperty2, booleanProperty3, integerProperty);
        synchronized (this.plugIn) {
            if (this.rjo != this.rjo) {
                return;
            }
            synchronized (this.state) {
                try {
                    if (this.isShuttingDown()) {
                        return;
                    }
                    this.setState(CStateRunning.get());
                }
                catch (StateException ex) {
                    Excp.print(ex);
                }
                if (this.isIdle()) {
                    return;
                }
            }
        }
        try {
            Thread.sleep(10L);
        }
        catch (InterruptedException ex6) {}
        synchronized (this.plugIn) {
            getLog().log(2, 7669630165411963160L);
            final Enumeration<JAService> elements = (Enumeration<JAService>)this.services.elements();
            while (elements.hasMoreElements()) {
                final JAService obj = elements.nextElement();
                if (!obj.isNetworked()) {
                    continue;
                }
                if (obj == this.mainServerGroup) {
                    continue;
                }
                if (obj == null) {
                    continue;
                }
                this.secondaries.addElement(obj);
            }
            (this.externalStartupMonitor = new ExternalStartupMonitor(this, this.secondaries, this.auxiliaries)).start();
        }
        try {
            Thread.sleep(10L);
        }
        catch (InterruptedException ex7) {}
        synchronized (this.plugIn) {
            if (this.isShuttingDown()) {
                return;
            }
            try {
                if (this.mainServerGroup != null) {
                    this.mainServerGroup.setRunning();
                }
            }
            catch (StateException ex2) {
                Excp.print(ex2);
            }
        }
        if (this.secondaries.size() > 0) {
            try {
                Thread.sleep(getTiming("SGStartupDelay") * 1000);
            }
            catch (InterruptedException ex8) {}
        }
        if (this.startedByMe) {
            final Enumeration elements2 = this.secondaries.elements();
            while (elements2.hasMoreElements()) {
                if (this.isShuttingDown()) {
                    return;
                }
                final JAService jaService = elements2.nextElement();
                final Vector mProservCommand = this.makeMProservCommand(jaService, false);
                try {
                    mProservCommand.addElement("-m3");
                    jaService.setStarting();
                    synchronized (this.plugIn) {
                        synchronized (this.state) {
                            if (this.isShuttingDown()) {
                                return;
                            }
                            this.startProcess(mProservCommand, jaService, "secondary");
                        }
                    }
                }
                catch (StateException ex3) {
                    Excp.print(ex3, "Can't start secondary process");
                }
                try {
                    if (!elements2.hasMoreElements()) {
                        continue;
                    }
                    Thread.sleep(getTiming("SGStartupDelay") * 1000);
                }
                catch (InterruptedException ex9) {}
            }
        }
        if (this.startedByMe) {
            final String property = System.getProperty("Install.Dir");
            final JAPlugIn plugIn = this.plugIn;
            JAPlugIn.adminServer();
            final Vector vector = new Vector<String>(20);
            vector.addElement(property + System.getProperty("file.separator") + "bin" + System.getProperty("file.separator") + "_mprshut");
            vector.addElement(this.database.getProperty("databasename"));
            try {
                vector.addElement("-adminport");
                vector.addElement(JAPlugIn.getServerPort());
                vector.addElement("-cpinternal");
                vector.addElement(this.getProperty("internalcodepage"));
                vector.addElement("-cpcoll");
                vector.addElement(this.getProperty("collationtable").toUpperCase());
                if (booleanProperty) {
                    Thread.sleep(getTiming("AuxStartupDelay") * 1000);
                    vector.addElement("-C");
                    vector.addElement("biw");
                    this.biWriter.setStarting();
                    synchronized (this.plugIn) {
                        synchronized (this.state) {
                            if (this.isShuttingDown()) {
                                return;
                            }
                            this.startProcess(vector, "BI", "BI");
                        }
                    }
                    vector.removeElement("-C");
                    vector.removeElement("biw");
                }
                if (booleanProperty2) {
                    Thread.sleep(getTiming("AuxStartupdelay") * 1000);
                    vector.addElement("-C");
                    vector.addElement("aiw");
                    this.aiWriter.setStarting();
                    synchronized (this.plugIn) {
                        synchronized (this.state) {
                            if (this.isShuttingDown()) {
                                return;
                            }
                            this.startProcess(vector, "AI", "AI");
                        }
                    }
                    vector.removeElement("-C");
                    vector.removeElement("aiw");
                }
                if (booleanProperty3) {
                    Thread.sleep(getTiming("AuxStartupdelay") * 1000);
                    vector.addElement("-C");
                    vector.addElement("watchdog");
                    this.watchdog.setStarting();
                    synchronized (this.plugIn) {
                        synchronized (this.state) {
                            if (this.isShuttingDown()) {
                                return;
                            }
                            this.startProcess(vector, "watchdog", "watchdog");
                        }
                    }
                    vector.removeElement("-C");
                    vector.removeElement("watchdog");
                }
                if (integerProperty > 0) {
                    vector.addElement("-C");
                    vector.addElement("apw");
                    for (int i = 0; i < integerProperty; ++i) {
                        Thread.sleep(getTiming("AuxStartupDelay") * 1000);
                        this.apWriter.setStarting();
                        synchronized (this.plugIn) {
                            synchronized (this.state) {
                                if (this.isShuttingDown()) {
                                    return;
                                }
                                this.startProcess(vector, "apw", "apw");
                            }
                        }
                    }
                    vector.removeElement("-C");
                    vector.removeElement("apw");
                }
            }
            catch (InterruptedException ex4) {
                Excp.print(ex4, "Can't start secondary process");
            }
            catch (StateException ex5) {
                Excp.print(ex5, "Can't start secondary process");
            }
        }
    }
    
    Vector makeConManCommand() {
        final Vector<String> vector = new Vector<String>(20);
        try {
            final String property = System.getProperty("Install.Dir");
            final JAPlugIn plugIn = this.plugIn;
            final IAdministrationServer adminServer = JAPlugIn.adminServer();
            vector.addElement(property + System.getProperty("file.separator") + "bin" + System.getProperty("file.separator") + "jvmStart");
            vector.addElement("-d");
            vector.addElement("-ss 500K");
            vector.addElement("-classpath " + new QuotedString(System.getProperty("java.class.path")));
            vector.addElement("-D juniper.config=" + new QuotedString(this.name()));
            vector.addElement("-D Install.Dir=" + new QuotedString(property));
            vector.addElement("-D StandAlone=false");
            if (!new String(adminServer.getPort()).equals(String.valueOf(20931))) {
                vector.addElement("-D chimera.rmiport=" + adminServer.getPort());
            }
            vector.addElement("chimera.rmihost=" + adminServer.getHost());
            vector.addElement("-D juniper.propertyfile=" + new QuotedString(this.plugIn.configFile()));
            vector.addElement(" -z");
        }
        catch (RemoteException ex) {
            Excp.print(ex, "Failure constructing command to start the connection manager");
        }
        return vector;
    }
    
    void startPrimaryMonitors(final State state, int value, int value2, final int n) {
        this.stopPrimaryMonitors();
        getLog().log(4, 7669630165411963159L, new Object[] { state.name(), new Integer(value), new Integer(value2) });
        if (Boolean.getBoolean("ShortenMonitors")) {
            value = 0;
            value2 = 1;
        }
        else if (value2 < value) {
            value2 = value * n;
        }
        if (value != 0) {
            (this.reasonableTimeMonitor = new PrimaryStartupMonitorReasonable(state, this, value, n)).start();
        }
        if (value2 != 0) {
            (this.worstCaseMonitor = new PrimaryStartupMonitorWorstCase(state, this, value2)).start();
        }
    }
    
    void stopPrimaryMonitors() {
        if (this.reasonableTimeMonitor != null) {
            this.reasonableTimeMonitor.destroyThread();
            this.reasonableTimeMonitor = null;
        }
        if (this.worstCaseMonitor != null) {
            this.worstCaseMonitor.destroyThread();
            this.worstCaseMonitor = null;
        }
    }
    
    void startUp() throws ConfigurationException {
        final Vector vector = new Vector(20);
        if (!Boolean.getBoolean("UsingConman")) {
            this.startMProserv();
        }
        this.startedByMe = true;
    }
    
    public void serviceClosed(final String s) throws RemoteException {
        synchronized (this.plugIn) {
        }
        // monitorexit(this.plugIn)
    }
    
    public void serviceFailed(final String s, final String[] array) throws RemoteException {
        synchronized (this.plugIn) {
        }
        // monitorexit(this.plugIn)
    }
    
    void handleCrash() {
        synchronized (this.plugIn) {
            getLog().log(2, 7669630165411963174L, new Object[] { this.rjo });
            if (this.rjo == null) {
                return;
            }
            try {
                this.setState(CStateIdle.get());
                this.plugIn.getEventBroker().postEvent(new EConfigurationCrashEvent(this.database.evThis(), this));
                final JAAgent jaAgent = (JAAgent)this.database.getAgentRemote();
                if (jaAgent != null) {
                    jaAgent.handleCrash();
                }
            }
            catch (StateException ex) {
                Excp.print(ex, "In crash state but cannot reset state to idle.");
            }
            catch (RemoteException ex2) {
                Excp.print(ex2, "In crash state but cannot post crash event.");
            }
        }
    }
    
    Process startMProserv() throws ConfigurationException {
        int n = 0;
        this.mainServerGroup = null;
        final Enumeration<JAService> elements = (Enumeration<JAService>)this.services.elements();
        while (elements.hasMoreElements()) {
            final JAService mainServerGroup = elements.nextElement();
            if (mainServerGroup.isNetworked()) {
                this.mainServerGroup = mainServerGroup;
                break;
            }
            ++n;
        }
        if (this.mainServerGroup == null) {
            if (n != 1) {
                throw new ConfigurationException("Attempting to start non-networked config with multiple server groups: " + this.name());
            }
            this.mainServerGroup = this.services.elementAt(0);
        }
        final Vector mProservCommand = this.makeMProservCommand(this.mainServerGroup);
        mProservCommand.addElement("-m5");
        try {
            this.mainServerGroup.setStarting();
        }
        catch (StateException ex) {
            throw new ConfigurationException(ex.getMessage());
        }
        Process startProcess = null;
        if (!Boolean.getBoolean("DontStartDB")) {
            startProcess = this.startProcess(mProservCommand, this.mainServerGroup, "primary");
        }
        return startProcess;
    }
    
    Vector makeMProservCommand(final JAService jaService) {
        return this.makeMProservCommand(jaService, true);
    }
    
    Vector makeMProservCommand(final JAService jaService, final boolean b) {
        final Vector<String> vector = new Vector<String>(20);
        final String property = System.getProperty("Install.Dir");
        String obj = jaService.getProperty("serverexe");
        if (obj == null) {
            obj = property + System.getProperty("file.separator") + "bin" + System.getProperty("file.separator") + "_mprosrv";
        }
        vector.addElement(obj);
        if (this.isClustered() && b) {
            this.addClusterArguments(vector);
        }
        else {
            this.addConventionalArguments(vector, jaService);
        }
        return vector;
    }
    
    boolean isClustered() {
        return this.database.getBooleanProperty("cluster");
    }
    
    void addClusterArguments(final Vector vector) {
        final String property = this.database.getProperty("databaseName");
        if (property != null && property.length() > 0) {
            vector.addElement("-db");
            vector.addElement(property);
        }
        vector.addElement("-cluster");
        vector.addElement("startup");
    }
    
    void addConventionalArguments(final Vector vector, final JAService jaService) {
        if (JAPlugIn.get().isReplLicensed()) {
            try {
                final IJADatabase database = this.getDatabase();
                final int databaseEnablement = database.getDatabaseEnablement();
                if (databaseEnablement == 11 || databaseEnablement == 12) {
                    final Vector replDatabaseArgs = database.getReplDatabaseHandle().getReplDatabaseArgs(databaseEnablement);
                    for (int i = 0; i < replDatabaseArgs.size(); ++i) {
                        vector.addElement(replDatabaseArgs.elementAt(i));
                    }
                }
            }
            catch (Exception ex) {
                Tools.px(ex, "Unable to obtain site replication information.");
            }
        }
        vector.addElement("-classpath");
        final int n = 3072;
        String obj = System.getProperty("java.class.path");
        if (obj.length() > n - 2) {
            obj = obj.substring(0, n - 3);
        }
        if (System.getProperty("os.name").startsWith("Windows")) {
            obj = new QuotedString(obj).toString();
        }
        vector.addElement(obj);
        vector.addElement("-properties");
        if (System.getProperty("os.name").startsWith("Windows")) {
            vector.addElement(new QuotedString(this.plugIn.configFile()).toString());
        }
        else {
            vector.addElement(this.plugIn.configFile());
        }
        vector.addElement("-servergroup");
        vector.addElement(jaService.name());
        vector.addElement("-adminport");
        vector.addElement(JAPlugIn.getServerPort());
        final String property = this.getProperty("otherArgs");
        if (property != null && property.length() > 0) {
            final StringTokenizer stringTokenizer = new StringTokenizer(property);
            while (stringTokenizer.hasMoreTokens()) {
                vector.addElement(stringTokenizer.nextToken());
            }
        }
    }
    
    public String getMMCClientClass() throws RemoteException {
        return "com.progress.vj.juniper.JuniperMMCConfigurationClient";
    }
    
    static {
        JAConfiguration.resources = (ProgressResources)ResourceBundle.getBundle("com.progress.international.messages.JAGenericBundle");
        JAConfiguration.numThreadsS = 0;
        JAConfiguration.theLog = null;
        final Integer integer = Integer.getInteger("JuniperStartupTimeout");
        if (integer != null) {
            JAConfiguration.juniperStartupTimeout = integer;
        }
        else {
            JAConfiguration.juniperStartupTimeout = 600;
        }
        JAConfiguration.nameTable = new NameContext();
        JAConfiguration.registrationHandler = null;
        JAConfiguration.sessionCounter = 0;
    }
    
    public static class ConfigurationException extends Exception
    {
        public ConfigurationException(final String message) {
            super(message);
        }
    }
    
    class RegistrationHandler extends EventListener
    {
        JAConfiguration config;
        
        RegistrationHandler() {
            this.config = null;
        }
        
        public void processEvent(final IEventObject eventObject) throws RemoteException {
            synchronized (JAConfiguration.this.plugIn) {
                final IRegistrationEvent registrationEvent = (IRegistrationEvent)eventObject;
                final String configurationName = registrationEvent.configurationName();
                JAConfiguration.getLog().log(2, 7669630165411962088L, new Object[] { configurationName });
                final JAConfiguration configuration = JAConfiguration.findConfiguration(configurationName);
                if (configuration == null) {
                    Excp.print(new Exception("Juniper config not in config file."));
                    return;
                }
                (configuration.rjo = (IJuniper)registrationEvent.issuer()).setAdminServer(configuration);
                try {
                    configuration.setState(CStateInitializing.get());
                }
                catch (StateException ex) {
                    Excp.print(ex);
                }
            }
        }
    }
}
