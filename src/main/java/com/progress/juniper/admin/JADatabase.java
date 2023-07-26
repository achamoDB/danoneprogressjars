// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import com.progress.chimera.adminserver.IAdminServerConst;
import java.util.ResourceBundle;
import java.io.IOException;
import com.progress.common.log.IFileRef;
import com.progress.common.networkevents.IEventListener;
import com.progress.common.log.LogSystem;
import com.progress.common.property.EPropertiesChanged;
import com.progress.common.log.ProLog;
import java.lang.reflect.InvocationTargetException;
import com.progress.common.log.Excp;
import com.progress.common.property.PropertyManager;
import com.progress.chimera.util.SerializableEnumeration;
import java.util.Enumeration;
import com.progress.chimera.common.Tools;
import com.progress.common.networkevents.IEventObject;
import com.progress.common.log.ELogFileNameChange;
import java.rmi.server.RemoteStub;
import java.rmi.RemoteException;
import com.progress.common.log.RemoteLogFileReader;
import java.util.Vector;
import com.progress.chimera.common.NameContext;
import com.progress.chimera.log.ConnectionManagerLog;
import com.progress.international.resources.ProgressResources;
import com.progress.message.jpMsg;
import com.progress.common.log.IRemoteFile;
import com.progress.chimera.common.Database;

public class JADatabase extends Database implements IJADatabase, IJAParameterizedObject, IRemoteFile, jpMsg
{
    static ProgressResources resources;
    static ProgressResources resourcesG;
    public static String NEWLINE;
    public static String FILE_SEPARATOR;
    public static String OS_NAME;
    public static String INSTALL_DIR;
    public static boolean IS_WINDOWS;
    public static String DBUTIL;
    JAPlugIn plugIn;
    static ConnectionManagerLog jaLog;
    String displayName;
    protected static NameContext nameTable;
    Vector configurations;
    JAConfigsCategory configsCategory;
    IAgentDatabaseHandle dbAgent;
    JAAgent agentRemote;
    IReplDatabaseHandle replDatabaseHandle;
    static int sessionCounter;
    JAConfiguration runningConfiguration;
    JAConfiguration currentConfiguration;
    String m_user;
    boolean startsAreBlocked;
    private RemoteLogFileReader rlfr;
    
    public JAStatusObject getStatus() throws RemoteException {
        return this.getStatus(null);
    }
    
    public JAStatusObject getStatus(IJAConfiguration ijaConfiguration) throws RemoteException {
        if (ijaConfiguration == null) {
            ijaConfiguration = this.getCurrentConfiguration();
            if (ijaConfiguration == null) {
                ijaConfiguration = this.getDefaultConfiguration();
            }
        }
        return ijaConfiguration.getStatus();
    }
    
    public RemoteStub remoteStub() {
        return this.stub();
    }
    
    IJADatabase evThis() {
        return (IJADatabase)this.remoteStub();
    }
    
    public void propertiesChanged() {
        final String property = this.getProperty("DatabaseName");
        this.setPhysicalName(property);
        try {
            this.plugIn.getEventBroker().postEvent(new ELogFileNameChange(this.evThis()));
            this.plugIn.getEventBroker().postEvent(new EDatabaseNameChanged(this.evThis(), property));
        }
        catch (RemoteException ex) {
            Tools.px(ex);
        }
    }
    
    public String getDisplayName() {
        return this.getDisplayName(false);
    }
    
    public String getDisplayName(final boolean b) {
        synchronized (this.plugIn) {
            return this.displayName;
        }
    }
    
    protected NameContext getNameContext() {
        return JADatabase.nameTable;
    }
    
    public Enumeration getChildrenObjects() {
        synchronized (this.plugIn) {
            return this.getConfigurations();
        }
    }
    
    public SerializableEnumeration getConfigurations() {
        synchronized (this.plugIn) {
            return new SerializableEnumeration(this.configurations);
        }
    }
    
    protected void removeConfiguration(final JAConfiguration jaConfiguration, final Object o) {
        try {
            if (this.configurations.contains(jaConfiguration)) {
                this.configurations.removeElement(jaConfiguration);
                this.plugIn.getEventBroker().postEvent(new EConfigurationRemoved(this.evThis(), jaConfiguration, o));
            }
        }
        catch (RemoteException ex) {
            Tools.px(ex);
        }
    }
    
    public void setDefaultConfiguration(final IJAConfiguration ijaConfiguration) throws DatabaseException {
        this.setDefaultConfiguration(ijaConfiguration, true, true);
    }
    
    public IJAConfiguration getDefaultConfiguration() throws RemoteException {
        try {
            return this.getDefaultConfiguration(true);
        }
        catch (DatabaseException ex) {
            throw new RemoteException(ProgressResources.retrieveTranString("com.progress.international.messages.ADMMsgBundle", "Error getting default configuration:", (Object)ex));
        }
    }
    
    public IJAConfiguration getDefaultConfiguration(final boolean b) throws RemoteException, DatabaseException {
        synchronized (this.plugIn) {
            final String property = this.properties().getProperty(this.groupName() + ".DefaultConfiguration");
            if (property == null) {
                this.pickArbitraryDefaultConfiguration(b);
            }
            return JAConfiguration.findConfiguration(property);
        }
    }
    
    void setDefaultConfiguration(final IJAConfiguration obj, final boolean b, final boolean b2) throws DatabaseException {
        if (!this.contains((JAConfiguration)obj)) {
            throw new DatabaseException("setDefaultConfiguration: Config " + obj + " not in database " + this.getDisplayName());
        }
        try {
            this.properties().putProperty(this.groupName() + ".DefaultConfiguration", ((JAConfiguration)obj).name());
            if (b) {
                JATools.writeOutProperties(this.plugIn, this.remoteStub());
            }
        }
        catch (PropertyManager.PropertyException ex) {
            throw new DatabaseException(ex.getMessage());
        }
    }
    
    public void removeDefaultConfiguration() throws RemoteException {
        this.removeDefaultConfiguration(true);
    }
    
    void removeDefaultConfiguration(final boolean b) throws RemoteException {
        this.properties().removeProperty(this.groupName() + ".DefaultConfiguration");
        if (b) {
            JATools.writeOutProperties(this.plugIn, this.remoteStub());
        }
    }
    
    void pickArbitraryDefaultConfiguration(final boolean b) throws DatabaseException {
        if (!this.configurations.isEmpty()) {
            this.setDefaultConfiguration(this.configurations.elementAt(0), b, false);
        }
    }
    
    boolean contains(final JAConfiguration jaConfiguration) {
        final Enumeration<JAConfiguration> elements = this.configurations.elements();
        while (elements.hasMoreElements()) {
            if (elements.nextElement() == jaConfiguration) {
                return true;
            }
        }
        return false;
    }
    
    public IJAHierarchy getParent() {
        synchronized (this.plugIn) {
            return this.getPlugIn();
        }
    }
    
    public IJAPlugIn getPlugIn() {
        synchronized (this.plugIn) {
            return this.plugIn;
        }
    }
    
    public String toString() {
        return this.getDisplayName();
    }
    
    String getProperty(final String str) {
        return this.plugIn.properties().getProperty(this.groupName() + "." + str);
    }
    
    public boolean getBooleanProperty(final String str) {
        return this.plugIn.properties().getBooleanProperty(this.groupName() + "." + str);
    }
    
    void setProperty(final String str, final String s) {
        try {
            this.plugIn.properties().putProperty(this.groupName() + "." + str, s);
        }
        catch (PropertyManager.PropertyException ex) {
            Tools.px(ex);
        }
    }
    
    public Enumeration getChildren() throws RemoteException {
        synchronized (this.plugIn) {
            final Vector<JAConfigsCategory> vector = new Vector<JAConfigsCategory>();
            if (this.configsCategory == null) {
                this.configsCategory = new JAConfigsCategory(this, JADatabase.resources.getTranString("Configurations"), this.configurations);
            }
            vector.addElement(this.configsCategory);
            if (this.plugIn.isReplLicensed() && this.replDatabaseHandle != null) {
                final Enumeration children = this.replDatabaseHandle.getChildren();
                if (children != null && children.hasMoreElements()) {
                    vector.addElement(children.nextElement());
                }
            }
            return new SerializableEnumeration(vector);
        }
    }
    
    String configFile() {
        return this.plugIn.configFile();
    }
    
    PropertyManager properties() {
        return this.plugIn.properties();
    }
    
    static Enumeration getAllDatabases() {
        return JADatabase.nameTable.elements();
    }
    
    public IJAHierarchy createChild(final String s, final Object o, final Object o2) {
        synchronized (this.plugIn) {
            return this.createConfiguration(s, o2);
        }
    }
    
    public IJAConfiguration createConfiguration(final String s, final Object o) {
        synchronized (this.plugIn) {
            return JAConfiguration.instantiateNew(this.plugIn, this, s, o);
        }
    }
    
    private static String adjustName(final String s) {
        return s.trim().toLowerCase();
    }
    
    public static JADatabase findDatabase(final String s) {
        return JADatabase.nameTable.get(adjustName(s));
    }
    
    public IJAConfiguration findConfiguration(final String s) {
        return JAConfiguration.findConfiguration(JAConfiguration.makeLongName(s, this));
    }
    
    public String getDatabaseFileName() throws RemoteException {
        return this.getProperty("DatabaseName");
    }
    
    public boolean getMonitorLicensed() {
        return true;
    }
    
    public void setMonitorLicensed(final boolean b) throws PropertyManager.PropertyException {
        this.setMonitorLicensed(b, false);
    }
    
    public void setMonitorLicensed(final boolean b, final boolean b2) throws PropertyManager.PropertyException {
        this.properties().putBooleanProperty(this.groupName() + ".MonitorLicensed", b);
        if (b2) {
            JATools.writeOutProperties(this.plugIn, this.remoteStub());
        }
    }
    
    public static JADatabase instantiateNew(final JAPlugIn jaPlugIn, final String s, final String s2, final boolean b, final Object o) throws DatabaseException, RemoteException {
        final JADatabase instantiateX = instantiateX(jaPlugIn, s, s, s2, o);
        try {
            jaPlugIn.properties().putProperty(instantiateX.groupName() + ".DisplayName", s);
            jaPlugIn.properties().putProperty(instantiateX.groupName() + ".DatabaseName", s2);
            if (b) {
                jaPlugIn.properties().putBooleanProperty(instantiateX.groupName() + ".Autostart", true);
            }
        }
        catch (PropertyManager.PropertyException ex) {
            Tools.px(ex);
        }
        instantiateX.setDefaultConfiguration(JAConfiguration.instantiateNew(jaPlugIn, instantiateX, "defaultConfiguration", o), false, false);
        JATools.writeOutProperties(jaPlugIn);
        return instantiateX;
    }
    
    static JADatabase instantiateExisting(final JAPlugIn jaPlugIn, final String s) throws DatabaseException, RemoteException {
        final String string = "Database." + s + ".DisplayName";
        String property = jaPlugIn.properties().getProperty(string);
        String property2;
        try {
            if (property == null) {
                property = s;
                jaPlugIn.properties().putProperty(string, property);
                JATools.writeOutProperties(jaPlugIn);
            }
            final String string2 = "Database." + s + ".DatabaseName";
            property2 = jaPlugIn.properties().getProperty(string2);
            if (property2 == null) {
                property2 = ".";
                jaPlugIn.properties().putProperty(string2, property2);
                JATools.writeOutProperties(jaPlugIn);
            }
        }
        catch (PropertyManager.PropertyException ex) {
            throw new DatabaseException(ex.getMessage());
        }
        final JADatabase instantiateX = instantiateX(jaPlugIn, s, property, property2, null);
        if (instantiateX != null) {
            instantiateX.getDefaultConfiguration(true);
        }
        return instantiateX;
    }
    
    private static JADatabase instantiateX(final JAPlugIn jaPlugIn, final String s, final String s2, final String s3, final Object o) throws RemoteException {
        final String adjustName = adjustName(s);
        JADatabase database = findDatabase(adjustName);
        if (database == null) {
            database = new JADatabase(jaPlugIn, s3, adjustName, s2);
        }
        jaPlugIn.addDatabase(database, o);
        final String property = jaPlugIn.properties().getProperty("Database." + adjustName + ".DefaultConfiguration");
        if (property != null && !property.equals("")) {
            JAConfiguration.findConfiguration(property);
        }
        return database;
    }
    
    protected JADatabase(final JAPlugIn plugIn, final String s, final String s2, final String displayName) throws RemoteException {
        super(s2, null, s);
        this.displayName = "";
        this.configurations = new Vector();
        this.configsCategory = null;
        this.dbAgent = null;
        this.agentRemote = null;
        this.replDatabaseHandle = null;
        this.runningConfiguration = null;
        this.currentConfiguration = null;
        this.m_user = System.getProperty("user.name");
        this.startsAreBlocked = false;
        this.rlfr = null;
        this.displayName = displayName;
        this.plugIn = plugIn;
        this.loadConfigurations();
        this.makeAgent();
        this.makeAgentRemote();
        this.makeReplDatabase();
        final PropChangeListenerObject propChangeListenerObject = new PropChangeListenerObject(this, this.remoteStub(), EConfigurationAdded.class, EConfigurationRemoved.class);
    }
    
    void makeAgent() {
        if (!this.plugIn.isAgentLicensed()) {
            return;
        }
        try {
            this.dbAgent = (IAgentDatabaseHandle)Class.forName("com.progress.agent.database.AgentDatabase").getConstructor(this.plugIn.getClass(), this.getClass()).newInstance(this.plugIn, this);
        }
        catch (ClassNotFoundException ex) {
            Excp.print(ex);
        }
        catch (NoSuchMethodException ex2) {
            Excp.print(ex2);
        }
        catch (InstantiationException ex3) {
            Excp.print(ex3);
        }
        catch (IllegalAccessException ex4) {
            Excp.print(ex4);
        }
        catch (InvocationTargetException ex5) {
            Excp.print(ex5);
        }
    }
    
    public IAgentDatabaseHandle getAgent() throws RemoteException {
        return this.dbAgent;
    }
    
    void makeAgentRemote() {
        if (!this.plugIn.isAgentRemoteSupported()) {
            return;
        }
        try {
            this.agentRemote = new JAAgent(this);
        }
        catch (RemoteException ex) {
            Tools.px(ex);
            this.agentRemote = null;
        }
    }
    
    public IJAAgent getAgentRemote() throws RemoteException {
        return this.agentRemote;
    }
    
    void makeReplDatabase() {
        try {
            if (!this.plugIn.isReplLicensed()) {
                return;
            }
            this.replDatabaseHandle = (IReplDatabaseHandle)Class.forName("com.progress.database.replication.ReplDatabase").getConstructor(this.plugIn.getClass(), this.getClass()).newInstance(this.plugIn, this);
        }
        catch (Throwable t) {
            ProLog.logd("Database", 4, t.getMessage());
        }
    }
    
    public IReplDatabaseHandle getReplDatabaseHandle() throws RemoteException {
        return this.replDatabaseHandle;
    }
    
    public static IReplicationPluginComponent getReplicationPluginComponent(final String s) {
        IReplDatabaseHandle replDatabaseHandle;
        try {
            replDatabaseHandle = findDatabase(s).getReplDatabaseHandle();
        }
        catch (Exception ex) {
            replDatabaseHandle = null;
        }
        return replDatabaseHandle;
    }
    
    protected void refreshConfigurations() {
        final PropertyManager properties = this.plugIn.properties();
        final Vector<String> vector = new Vector<String>();
        final Vector configurations = this.configurations;
        final Vector vector2 = new Vector<String>();
        try {
            final String[] arrayProperty = properties.getArrayProperty(this.groupName() + ".configurations");
            if (arrayProperty != null) {
                for (int i = 0; i < arrayProperty.length; ++i) {
                    vector.add(arrayProperty[i].toLowerCase());
                }
            }
            for (int j = 0; j < configurations.size(); ++j) {
                vector2.add(configurations.elementAt(j).getDisplayName(true).toLowerCase());
            }
            for (int k = 0; k < vector2.size(); ++k) {
                final JAConfiguration jaConfiguration = configurations.elementAt(k);
                if (vector.contains(vector2.elementAt(k))) {
                    JAPlugIn.get().getEventBroker().postEvent(new EPropertiesChanged(jaConfiguration.remoteStub(), null));
                    jaConfiguration.refreshServices();
                }
                else {
                    jaConfiguration.delete(null, false);
                }
            }
            for (int l = 0; l < vector.size(); ++l) {
                final String s = vector.elementAt(l);
                if (!vector2.contains(s)) {
                    JAConfiguration.instantiateExisting(this.plugIn, this, properties.getProperty("Configuration." + s + ".displayname"));
                }
            }
        }
        catch (Exception ex) {
            final ConnectionManagerLog jaLog = JADatabase.jaLog;
            LogSystem.logIt("ConnectionMgr", 3, "Unable to refresh database configurations.");
        }
    }
    
    void loadConfigurations() {
        final String[] arrayProperty = this.plugIn.properties().getArrayProperty(this.groupName() + ".configurations");
        if (arrayProperty == null || arrayProperty.length == 0) {
            return;
        }
        try {
            for (int i = 0; i < arrayProperty.length; ++i) {
                final String trim = arrayProperty[i].trim();
                final String substring = trim.substring(trim.indexOf(".") + 1);
                if (!substring.equals("")) {
                    JAConfiguration.instantiateExisting(this.plugIn, this, substring);
                }
            }
        }
        catch (JAConfiguration.ConfigurationException ex) {
            Tools.px(ex, "Juniper: Failed loading configs for database: " + this.name());
        }
    }
    
    protected void addConfiguration(final JAConfiguration jaConfiguration, final Object o) {
        try {
            if (!this.configurations.contains(jaConfiguration)) {
                this.configurations.addElement(jaConfiguration);
                this.plugIn.getEventBroker().postEvent(new EConfigurationAdded(this.evThis(), jaConfiguration, o));
            }
        }
        catch (RemoteException ex) {
            Tools.px(ex);
        }
    }
    
    public String getHelpMapFile() throws RemoteException {
        return null;
    }
    
    static String makeNewName() {
        String string;
        do {
            ++JADatabase.sessionCounter;
            string = JADatabase.resourcesG.getTranString("Database-") + JADatabase.sessionCounter;
        } while (nameUsed(string));
        return string;
    }
    
    public static boolean nameUsed(final String s) {
        return findDatabase(s) != null;
    }
    
    public String makeNewChildName() {
        synchronized (this.plugIn) {
            return this.makeNewConfigName();
        }
    }
    
    public String makeNewConfigName() {
        synchronized (this.plugIn) {
            return JAConfiguration.makeNewName(this);
        }
    }
    
    public boolean childNameUsed(final String s) {
        synchronized (this.plugIn) {
            return this.configNameUsed(s);
        }
    }
    
    public boolean configNameUsed(final String s) {
        synchronized (this.plugIn) {
            return JAConfiguration.nameUsed(this, s);
        }
    }
    
    void setCurrentConfiguration(final JAConfiguration currentConfiguration) {
        this.currentConfiguration = currentConfiguration;
    }
    
    public boolean isStartable() {
        synchronized (this.plugIn) {
            if (this.getCurrentConfiguration() != null) {
                return false;
            }
            try {
                final IJAConfiguration defaultConfiguration = this.getDefaultConfiguration();
                return defaultConfiguration != null && defaultConfiguration.isStartable();
            }
            catch (RemoteException ex) {
                Tools.px(ex);
                return false;
            }
        }
    }
    
    public void setUser(final String user) throws RemoteException {
        this.m_user = user;
    }
    
    public String getUser() throws RemoteException {
        return this.m_user;
    }
    
    protected String getUserS() {
        return this.m_user;
    }
    
    public int getDatabaseEnablement() throws RemoteException {
        int n;
        try {
            final String[] cmdarray = { JADatabase.IS_WINDOWS ? (JADatabase.DBUTIL + ".exe") : JADatabase.DBUTIL, this.getDatabaseFileName(), "-C", "replstatus" };
            String string = "";
            for (int i = 0; i < cmdarray.length; ++i) {
                string = string + cmdarray[i] + " ";
            }
            String[] envp = null;
            if (!JADatabase.IS_WINDOWS) {
                envp = new String[] { "DLC=" + JADatabase.INSTALL_DIR };
            }
            final Process exec = Runtime.getRuntime().exec(cmdarray, envp);
            try {
                n = ((exec == null) ? -1 : exec.waitFor());
            }
            catch (InterruptedException ex) {
                n = 2;
            }
        }
        catch (Throwable t) {
            n = 2;
        }
        return n;
    }
    
    public void refresh() throws RemoteException {
        if (this.plugIn.isReplLicensed() && this.replDatabaseHandle != null) {
            final String databaseFileName = this.getDatabaseFileName();
            final String str = databaseFileName.endsWith(".db") ? databaseFileName.substring(0, databaseFileName.length() - 3) : databaseFileName;
            final String string = str + ".db";
            try {
                this.replDatabaseHandle.updateChildren(string, str, true);
            }
            catch (Exception ex) {
                throw new RemoteException(ex.getMessage());
            }
        }
    }
    
    public void start() {
        synchronized (this.plugIn) {
            try {
                final IJAConfiguration defaultConfiguration;
                if ((defaultConfiguration = this.getDefaultConfiguration()) == null) {
                    throw new RemoteException(ProgressResources.retrieveTranString("com.progress.international.messages.ADMMsgBundle", "Can't start database", (Object)this.name()));
                }
                defaultConfiguration.start();
            }
            catch (RemoteException ex) {
                Tools.px(ex);
            }
        }
    }
    
    public boolean isStopable() {
        synchronized (this.plugIn) {
            try {
                final IJAConfiguration currentConfiguration = this.getCurrentConfiguration();
                return currentConfiguration != null && currentConfiguration.isStopable();
            }
            catch (RemoteException ex) {
                Tools.px(ex);
                return false;
            }
        }
    }
    
    public void stop() throws RemoteException {
        synchronized (this.plugIn) {
            final IJAConfiguration currentConfiguration = this.getCurrentConfiguration();
            if (currentConfiguration != null) {
                currentConfiguration.stop();
            }
        }
    }
    
    public IJAConfiguration getCurrentConfiguration() {
        return this.currentConfiguration;
    }
    
    public IJAConfiguration getRunningConfiguration() {
        synchronized (this.plugIn) {
            if (this.currentConfiguration != null && this.currentConfiguration.isRunning()) {
                return this.currentConfiguration;
            }
            return null;
        }
    }
    
    public boolean isStarting() {
        synchronized (this.plugIn) {
            return this.currentConfiguration != null && this.currentConfiguration.isStarting();
        }
    }
    
    public boolean isIdle() {
        synchronized (this.plugIn) {
            return this.currentConfiguration == null || this.currentConfiguration.isIdle();
        }
    }
    
    public boolean isInitializing() {
        synchronized (this.plugIn) {
            return this.currentConfiguration != null && this.currentConfiguration.isInitializing();
        }
    }
    
    public boolean isShuttingDown() {
        synchronized (this.plugIn) {
            return this.currentConfiguration != null && this.currentConfiguration.isShuttingDown();
        }
    }
    
    public String getStateDescriptor() {
        synchronized (this.plugIn) {
            if (this.currentConfiguration == null) {
                return CStateIdle.get().displayName();
            }
            return this.currentConfiguration.getStateDescriptor();
        }
    }
    
    void blockStarts(final boolean startsAreBlocked) {
        this.startsAreBlocked = startsAreBlocked;
        this.updatePanel();
    }
    
    boolean startsAreBlocked() {
        return this.startsAreBlocked;
    }
    
    void updatePanel() {
    }
    
    public boolean isDeleteable() {
        synchronized (this.plugIn) {
            final Enumeration<JAConfiguration> elements = this.configurations.elements();
            while (elements.hasMoreElements()) {
                if (!elements.nextElement().isIdle()) {
                    return false;
                }
            }
            return true;
        }
    }
    
    public boolean isRunning() throws RemoteException {
        synchronized (this.plugIn) {
            final IJAConfiguration currentConfiguration = this.getCurrentConfiguration();
            return currentConfiguration != null && currentConfiguration.isRunning();
        }
    }
    
    String groupName() {
        return "Database." + this.name();
    }
    
    public void writeOutProperties() {
        synchronized (this.plugIn) {
            JATools.writeOutProperties(this.plugIn);
        }
    }
    
    public void delete(final Object o) {
        synchronized (this.plugIn) {
            this.delete(true, o);
        }
    }
    
    public void delete(final boolean b, final Object o) {
        synchronized (this.plugIn) {
            final String name = this.name();
            try {
                final String groupName = this.groupName();
                super.delete();
                final JAPlugIn plugIn = this.plugIn;
                JAPlugIn.databases.removeElement(this);
                final JAPlugIn plugIn2 = this.plugIn;
                final String[] arrayProperty = JAPlugIn.properties.getArrayProperty(groupName + ".configurations");
                if (arrayProperty != null) {
                    for (int i = 0; i < arrayProperty.length; ++i) {
                        JAConfiguration.findConfiguration(arrayProperty[i]).delete(o, false);
                    }
                }
                final JAPlugIn plugIn3 = this.plugIn;
                JAPlugIn.properties.removeGroup(groupName);
                if (b) {
                    JATools.writeOutProperties(this.plugIn);
                }
                this.plugIn.getEventBroker().postEvent(new EDatabaseDeleted(this.evThis(), o));
                this.plugIn.postContainerDeletedEvent(this, o);
            }
            catch (RemoteException ex) {
                Tools.px(ex, "Failed to complete deletion of database: " + name + "; remote error encountered.");
            }
        }
    }
    
    public String getMMCClientClass() {
        return "com.progress.vj.juniper.JuniperMMCDatabaseClient";
    }
    
    private void initRemoteLogFileReader() throws RemoteException {
        this.rlfr = new RemoteLogFileReader(this.plugIn.getEventBrokerLocal(), this.plugIn.getEventStream());
    }
    
    public IFileRef openFile(final String s, final IEventListener eventListener) throws RemoteException {
        if (this.rlfr == null) {
            this.initRemoteLogFileReader();
        }
        final String databaseFileName = this.getDatabaseFileName();
        final int lastIndex = databaseFileName.toLowerCase().lastIndexOf(".db");
        String substring;
        if (lastIndex > 0) {
            substring = databaseFileName.substring(0, lastIndex);
        }
        else {
            substring = databaseFileName;
        }
        try {
            return this.rlfr.openFile(substring + ".lg", eventListener);
        }
        catch (IOException ex) {
            Excp.print(ex);
            return null;
        }
    }
    
    static {
        JADatabase.resources = (ProgressResources)ResourceBundle.getBundle("com.progress.international.messages.MMCMsgBundle");
        JADatabase.resourcesG = (ProgressResources)ResourceBundle.getBundle("com.progress.international.messages.JAGenericBundle");
        JADatabase.NEWLINE = System.getProperty("line.separator");
        JADatabase.FILE_SEPARATOR = System.getProperty("file.separator");
        JADatabase.OS_NAME = System.getProperty("os.name");
        JADatabase.INSTALL_DIR = IAdminServerConst.INSTALL_DIR;
        JADatabase.IS_WINDOWS = JADatabase.OS_NAME.startsWith("Windows");
        JADatabase.DBUTIL = JADatabase.INSTALL_DIR + JADatabase.FILE_SEPARATOR + "bin" + JADatabase.FILE_SEPARATOR + "_dbutil";
        JADatabase.jaLog = ConnectionManagerLog.get();
        JADatabase.nameTable = new NameContext();
        JADatabase.sessionCounter = 0;
    }
    
    public static class DatabaseException extends Exception
    {
        public DatabaseException(final String message) {
            super(message);
        }
    }
}
