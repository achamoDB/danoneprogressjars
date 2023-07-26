// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera.adminserver;

import com.progress.chimera.log.RegistryManagerLog;
import com.progress.chimera.log.EventManagerLog;
import com.progress.common.networkevents.IEventObject;
import com.progress.common.networkevents.EventListener;
import com.progress.chimera.log.AdminServerLog;
import com.progress.common.log.Excp;
import com.progress.common.log.IFileRef;
import com.progress.common.networkevents.IEventListener;
import com.progress.common.licensemgr.ILicenseMgr;
import com.progress.common.networkevents.IEventBroker;
import com.progress.common.util.Environment;
import com.progress.mf.tools.ConfigurationTool;
import com.progress.mf.runtime.ManagementProperties;
import java.io.FileOutputStream;
import java.io.File;
import com.progress.common.rmiregistry.TimedOut;
import com.progress.common.rmiregistry.ICallable;
import com.progress.common.rmiregistry.TryIt;
import com.sonicsw.mf.framework.container.AbstractMBean;
import com.sonicsw.mf.framework.IFrameworkComponentContext;
import com.progress.mf.AbstractPluginComponent;
import com.progress.mf.IManagedPlugin;
import java.io.FileReader;
import com.progress.chimera.ChimeraException;
import java.io.IOException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import com.progress.common.util.acctAuthenticate;
import com.progress.common.util.crypto;
import java.rmi.Remote;
import com.progress.common.util.ProgressVersion;
import java.util.Enumeration;
import com.progress.common.exception.ExceptionMessageAdapter;
import java.util.StringTokenizer;
import com.progress.common.licensemgr.ProductInfo;
import com.progress.common.util.IMessageCallback;
import com.progress.common.rmiregistry.RMIPortInUse;
import com.progress.common.util.PropertyFilter;
import java.util.ResourceBundle;
import java.io.OutputStream;
import com.progress.common.log.LogOutputStream;
import java.rmi.RemoteException;
import com.progress.chimera.common.Tools;
import com.progress.common.log.LogSystem;
import java.rmi.RMISecurityManager;
import java.rmi.registry.Registry;
import com.progress.common.log.RemoteLogFileReader;
import java.util.HashSet;
import java.io.InputStream;
import com.progress.common.property.PropertyManager;
import com.sonicsw.mf.framework.container.ContainerImpl;
import java.io.PrintStream;
import com.progress.international.resources.ProgressResources;
import java.util.Hashtable;
import java.util.Vector;
import java.nio.channels.FileLock;
import java.io.FileInputStream;
import com.progress.common.licensemgr.LicenseMgr;
import com.progress.common.networkevents.EventBroker;
import com.progress.common.networkevents.IEventStream;
import com.progress.common.rmiregistry.PrimaryRegistryManager;
import com.progress.common.log.Subsystem;
import com.progress.message.asMsg;
import com.progress.common.property.IPropertyProvider;
import com.progress.common.log.IRemoteFile;
import com.progress.chimera.common.IChimeraHierarchy;
import com.progress.common.util.IVersionable;
import com.progress.common.rmiregistry.IPingable;

public abstract class AdminServer extends PlugInMgmtAPI implements IAdminServerConnection, IAdministrationServer, Runnable, IAdminServerConst, IPingable, IVersionable, IChimeraHierarchy, IRemoteFile, IPropertyProvider, asMsg
{
    public String[] m_argsIn;
    public AdminServerGetopt m_args;
    public static Subsystem m_adminServerSubsystem;
    static boolean m_logInitialized;
    static PrimaryRegistryManager m_regMan;
    static IEventStream localEventStream;
    static EventBroker localEventBroker;
    static AdminServer wkAdminServer;
    static boolean EventBrokerTrace;
    static boolean RegistryManagerTrace;
    static LicenseMgr m_licenseMgr;
    Thread m_adminServerThread;
    FileInputStream m_admLockFileInputStream;
    FileLock m_admLockFileLock;
    Vector m_serverPluginInfos;
    Hashtable m_serverPolicyInfos;
    private static final boolean m_enablePluginCmds = true;
    private static int m_maxCount;
    boolean m_shutdownRequested;
    public Vector m_containerList;
    boolean m_securityManagerInitialized;
    boolean m_isRegistered;
    boolean m_pluginsRunning;
    static ProgressResources admBundle;
    static ProgressResources cmnBundle;
    private static PrintStream defaultOut;
    private static PrintStream defaultErr;
    private static ContainerImpl m_container;
    private static boolean m_managementEnabled;
    private PropertyManager m_propertyManager;
    public PrintStream testOutStream;
    public PrintStream testErrStream;
    public InputStream testInStream;
    public HashSet m_sharedClasses;
    private static char COMMENT_CHAR;
    static final String ADMIN_SERVER_LOGFILE_NAME = "admserv.log";
    private RemoteLogFileReader rlfr;
    
    public static IPlugInMgmtAPI getPluginMgr() {
        return AdminServer.wkAdminServer;
    }
    
    public Registry getRMIRegistry() {
        return AdminServer.m_regMan.getRegistry();
    }
    
    protected void initializeSecurityManager() {
        if (this.m_securityManagerInitialized) {
            return;
        }
        System.setSecurityManager(new RMISecurityManager());
        this.m_securityManagerInitialized = true;
    }
    
    protected static void initializeLogs() {
        if (AdminServer.m_logInitialized) {
            return;
        }
        LogSystem.setDefaultLogFileName("admserv.log");
        Tools.setExcpFileName("ads0.exp");
        AdminServer.m_logInitialized = true;
    }
    
    public PropertyManager getPlugInPropertyManager() {
        return this.m_propertyManager;
    }
    
    public AdminServer(final String[] array) throws RemoteException {
        this(array, null, true);
    }
    
    public AdminServer(final String[] array, final HashSet set) throws RemoteException {
        this(array, set, true);
    }
    
    public AdminServer(final String[] array, final HashSet sharedClasses, final boolean b) throws RemoteException {
        this.m_argsIn = null;
        this.m_args = null;
        this.m_adminServerThread = null;
        this.m_admLockFileInputStream = null;
        this.m_admLockFileLock = null;
        this.m_serverPluginInfos = new Vector();
        this.m_serverPolicyInfos = new Hashtable();
        this.m_shutdownRequested = false;
        this.m_containerList = new Vector();
        this.m_securityManagerInitialized = false;
        this.m_isRegistered = false;
        this.m_pluginsRunning = false;
        this.m_propertyManager = null;
        this.testOutStream = System.out;
        this.testErrStream = System.err;
        this.testInStream = System.in;
        this.m_sharedClasses = null;
        this.rlfr = null;
        this.m_sharedClasses = sharedClasses;
        boolean b2 = false;
        this.m_args = new AdminServerGetopt(array);
        initializeLogs();
        this.initializeSecurityManager();
        if (!this.m_args.getInteractiveMode()) {
            final LogOutputStream out = new LogOutputStream(new Subsystem("STDOUT"));
            final LogOutputStream out2 = new LogOutputStream(new Subsystem("STDERR"), 3);
            final PrintStream out3 = new PrintStream(out, true);
            final PrintStream err = new PrintStream(out2, true);
            System.setOut(out3);
            System.setErr(err);
        }
        AdminServer.admBundle = (ProgressResources)ResourceBundle.getBundle("com.progress.international.messages.ADMMsgBundle");
        if (AdminServer.wkAdminServer == null) {
            AdminServer.wkAdminServer = this;
        }
        AdminServerType.getFathomEnabled(true);
        if (Thread.currentThread().getContextClassLoader().getParent().getClass().getName().startsWith("com.sonicsw")) {
            AdminServer.m_managementEnabled = true;
        }
        if (AdminServer.m_managementEnabled || AdminServerType.getFathomInstallDir() != null) {
            final boolean boolean1 = Boolean.getBoolean("BypassLockCheck");
            if (AdminServerType.isDSEnabled() && !boolean1) {
                boolean b3 = this.checkAdminerverLock();
                if (!b3) {
                    if (AdminServer.m_managementEnabled) {
                        b3 = this.checkSonicLocks();
                    }
                    if (!b3) {
                        b3 = this.checkFathomLock();
                        if (!b3 && !this.createAdminerverLock()) {
                            AdminServer.m_adminServerSubsystem.logErr(7021956244000746587L);
                            System.exit(1);
                        }
                    }
                }
                if (b3) {
                    AdminServer.m_adminServerSubsystem.logErr(7021956244000746588L);
                    System.exit(1);
                }
            }
        }
        if (AdminServer.m_managementEnabled) {
            try {
                AdminServer.m_container = this.createSonicContainer();
            }
            catch (Exception ex) {
                AdminServer.m_adminServerSubsystem.log(ex.getMessage());
            }
            if (AdminServer.m_container == null) {
                AdminServer.m_adminServerSubsystem.logErr(7021956244000746589L);
                if (this.m_args.bypassManagementFramework()) {
                    final Subsystem adminServerSubsystem = AdminServer.m_adminServerSubsystem;
                    LogSystem.logIt("AdminServer", 1, "Starting the AdminServer without the management framework");
                    AdminServer.m_managementEnabled = false;
                }
                else {
                    final Subsystem adminServerSubsystem2 = AdminServer.m_adminServerSubsystem;
                    LogSystem.logIt("AdminServer", 1, "Before restarting, ");
                    final Subsystem adminServerSubsystem3 = AdminServer.m_adminServerSubsystem;
                    LogSystem.logIt("AdminServer", 1, "1.  Ensure that remote Fathom AdminServer is running, or");
                    final Subsystem adminServerSubsystem4 = AdminServer.m_adminServerSubsystem;
                    LogSystem.logIt("AdminServer", 1, "2.  Use '-nofathom' start up option");
                    System.exit(1);
                }
            }
        }
        final String pluginsFile = this.m_args.getPluginsFile();
        try {
            (this.m_propertyManager = new PropertyManager(null, false)).setGetPropertyFilter(new PropertyFilter(this.m_propertyManager));
            this.m_propertyManager.load(pluginsFile, true);
            final String property = this.m_propertyManager.getProperty("PluginPolicy.Progress.AdminServer.args");
            if (property != null) {
                this.m_args.update(property);
                AdminServer.m_adminServerSubsystem.log(3, "The following arguments have been read from file " + pluginsFile + ": " + this.m_args.getInputArgs());
                final String invalidArgs = this.m_args.getInvalidArgs();
                final String invalidPortArgs = this.m_args.getInvalidPortArgs();
                if (invalidArgs != null && invalidArgs.length() > 0) {
                    AdminServer.m_adminServerSubsystem.logErr(1, "The following arguments from file, " + pluginsFile + ", are invalid and will be ignored: " + invalidArgs);
                }
                if (invalidPortArgs != null && invalidPortArgs.length() > 0) {
                    AdminServer.m_adminServerSubsystem.logErr(1, "The following arguments from file, " + pluginsFile + ", are invalid: " + invalidPortArgs);
                    AdminServer.m_adminServerSubsystem.logErr(1, "Exiting the AdminServer.");
                    System.exit(1);
                }
            }
        }
        catch (Exception ex7) {
            AdminServer.m_adminServerSubsystem.log(3, "Error loading AdminServer property file:" + pluginsFile);
        }
        try {
            this.openServiceEventObject();
            AdminServer.m_adminServerSubsystem.log(5, AdminServer.admBundle.getTranString("AdminServer started as a NT Service"));
        }
        catch (Exception ex8) {
            AdminServer.m_adminServerSubsystem.log(5, AdminServer.admBundle.getTranString("AdminServer did NOT start as a NT Service"));
        }
        AdminServer.m_adminServerSubsystem.log(1, 7021956244000742639L);
        try {
            if (b) {
                this.setupRMI();
            }
        }
        catch (Throwable t) {
            AdminServer.m_adminServerSubsystem.log(1, "Failed to create RMI registry on " + ((this.m_args.getHostName() == null) ? "localhost" : this.m_args.getHostName()) + ":" + this.m_args.getRMIRegistryPort());
            AdminServer.m_adminServerSubsystem.logErr(7021956244000743771L);
            if (!(t instanceof RMIPortInUse)) {
                Tools.px(t, "Failed to register admin server.");
            }
            try {
                AdminServer.wkAdminServer.signalServiceEventObject();
            }
            catch (Exception ex2) {
                Tools.px("Failed to signal NT Service", ex2);
            }
            System.exit(1);
        }
        try {
            if (AdminServer.localEventBroker == null) {
                AdminServer.localEventBroker = new EventBroker(new EventBrokerMessageHandler());
                AdminServer.localEventStream = AdminServer.localEventBroker.openEventStream("AdminServer Event Stream");
            }
            if (AdminServer.m_licenseMgr == null) {
                AdminServer.m_licenseMgr = new LicenseMgr();
                AdminServer.m_adminServerSubsystem.log(3, "Configured Products:");
                final Enumeration enumProductIDs = AdminServer.m_licenseMgr.EnumProductIDs();
                while (enumProductIDs.hasMoreElements()) {
                    final LicenseMgr.ProductList list = enumProductIDs.nextElement();
                    try {
                        final ProductInfo productInfo = new ProductInfo(list.productID(), AdminServer.m_licenseMgr);
                    }
                    catch (Exception ex9) {}
                    AdminServer.m_adminServerSubsystem.log(3, "  " + list.toString());
                }
            }
            this.setupPlugins();
        }
        catch (Exception ex3) {
            AdminServer.m_adminServerSubsystem.logErr(7021956244000743770L);
            Tools.px(ex3, "Failed to setup plugins.");
            try {
                AdminServer.wkAdminServer.signalServiceEventObject();
            }
            catch (Exception ex4) {
                Tools.px("Failed to signal NT Service", ex4);
            }
            System.exit(1);
        }
        try {
            if (AdminServer.wkAdminServer != null) {
                AdminServer.m_adminServerSubsystem.log(3, 7021956244000742657L);
            }
            else {
                AdminServer.m_adminServerSubsystem.logErr(7021956244000742658L);
                b2 = true;
            }
            if (b2) {
                AdminServer.m_adminServerSubsystem.logErr(7021956244000742659L);
                AdminServer.wkAdminServer.shutdown("SYSTEM");
                Thread.yield();
            }
            else {
                AdminServer.m_adminServerSubsystem.log(1, 7021956244000742660L);
                if (this.m_args.getInteractiveMode()) {
                    AdminServer.m_adminServerSubsystem.log(2, 7021956244000742661L);
                    Thread.sleep(2000L);
                    String command;
                    while (null != (command = getCommand())) {
                        if (command.equalsIgnoreCase("shutdown")) {
                            break;
                        }
                        final StringTokenizer stringTokenizer = new StringTokenizer(command);
                        if (!stringTokenizer.hasMoreTokens()) {
                            continue;
                        }
                        final String nextToken = stringTokenizer.nextToken();
                        if (nextToken.equalsIgnoreCase("help")) {
                            System.out.println("Valid commands are:");
                            System.out.println("   help");
                            System.out.println("   exit or quit or shutdown");
                            System.out.println("   start <plugin name>");
                            System.out.println("   stop <plugin name>");
                            System.out.println("   status [<plugin name>]");
                            System.out.println("   restart <plugin name>");
                            System.out.println("   list <plugin name>");
                        }
                        if (nextToken.equalsIgnoreCase("exit")) {
                            break;
                        }
                        if (nextToken.equalsIgnoreCase("quit")) {
                            break;
                        }
                        if (nextToken.equalsIgnoreCase("restart")) {
                            while (stringTokenizer.hasMoreTokens()) {
                                final String nextToken2 = stringTokenizer.nextToken();
                                this.shutdownPlugin(nextToken2);
                                if (this.startPlugin(nextToken2)) {
                                    System.out.println(nextToken2 + " restarted");
                                }
                                else {
                                    System.out.println(nextToken2 + " not restarted");
                                }
                            }
                        }
                        if (nextToken.equalsIgnoreCase("start")) {
                            while (stringTokenizer.hasMoreTokens()) {
                                final String nextToken3 = stringTokenizer.nextToken();
                                if (this.startPlugin(nextToken3)) {
                                    System.out.println(nextToken3 + " started");
                                }
                                else {
                                    System.out.println(nextToken3 + " not started");
                                }
                            }
                        }
                        if (nextToken.equalsIgnoreCase("stop")) {
                            while (stringTokenizer.hasMoreTokens()) {
                                this.shutdownPlugin(stringTokenizer.nextToken());
                            }
                        }
                        if (nextToken.equalsIgnoreCase("status")) {
                            if (!stringTokenizer.hasMoreTokens()) {
                                for (int i = 0; i < this.m_serverPluginInfos.size(); ++i) {
                                    final ServerPluginInfo serverPluginInfo = this.m_serverPluginInfos.elementAt(i);
                                    if (serverPluginInfo.isRunning()) {
                                        System.out.println(serverPluginInfo.getId() + " started");
                                    }
                                    else {
                                        System.out.println(serverPluginInfo.getId() + " not started");
                                    }
                                }
                                continue;
                            }
                            while (stringTokenizer.hasMoreTokens()) {
                                final String nextToken4 = stringTokenizer.nextToken();
                                final ServerPluginInfo plugin = this.getPlugin(nextToken4);
                                if (plugin != null) {
                                    if (plugin.isRunning()) {
                                        System.out.println(plugin.getId() + " started");
                                    }
                                    else {
                                        System.out.println(plugin.getId() + " not started");
                                    }
                                }
                                else {
                                    System.out.println("Unknown plugin - " + nextToken4);
                                }
                            }
                        }
                        if (!nextToken.equalsIgnoreCase("list")) {
                            continue;
                        }
                        while (stringTokenizer.hasMoreTokens()) {
                            final String nextToken5 = stringTokenizer.nextToken();
                            final ServerPluginInfo plugin2 = this.getPlugin(nextToken5);
                            if (plugin2 != null) {
                                System.out.print(plugin2.toVerboseString());
                                System.out.print(plugin2.getPolicy().toVerboseString());
                            }
                            else {
                                System.out.println("Unknown plugin - " + nextToken5);
                            }
                        }
                    }
                    AdminServer.wkAdminServer.shutdown("INTERACTIVE");
                    Thread.yield();
                }
                else if (this.m_args.getTestMode()) {
                    return;
                }
            }
            AdminServer.wkAdminServer.m_adminServerThread.join();
        }
        catch (Throwable t2) {
            if (t2 instanceof RMIPortInUse) {
                System.setErr(AdminServer.defaultErr);
                final String tranString = AdminServer.admBundle.getTranString("Registry Port in use", new Object[] { new String(Integer.toString(((RMIPortInUse)t2).getPortNumber())) });
                System.err.println(tranString);
                AdminServer.m_adminServerSubsystem.logErr(tranString);
            }
            else {
                Tools.px("Failure in AdminServer - exiting.", t2);
                AdminServer.m_adminServerSubsystem.logErr(7021956244000743774L);
                System.setErr(AdminServer.defaultErr);
                System.err.println(ExceptionMessageAdapter.getMessage(7021956244000743774L, null));
            }
            try {
                AdminServer.wkAdminServer.signalServiceEventObject();
            }
            catch (Exception ex5) {
                Tools.px("Failed to signal NT Service", ex5);
            }
            System.exit(1);
        }
        try {
            AdminServer.wkAdminServer.signalServiceEventObject();
        }
        catch (Exception ex6) {
            Tools.px("Failed to signal NT Service", ex6);
        }
        if (AdminServer.m_managementEnabled || AdminServerType.getFathomInstallDir() != null) {
            final boolean boolean2 = Boolean.getBoolean("BypassLockCheck");
            if (AdminServerType.isDSEnabled() && !boolean2) {
                this.deleteAdminerverLock();
            }
        }
        System.exit(0);
    }
    
    public Vector getServerPluginInfo() {
        return this.m_serverPluginInfos;
    }
    
    public String getVersion() throws RemoteException {
        return ProgressVersion.getVersion();
    }
    
    private void setupRMI() throws Throwable {
        AdminServer.m_adminServerSubsystem.log(2, 7021956244000742642L);
        final String rmiRegistryPort = this.m_args.getRMIRegistryPort();
        int int1;
        try {
            int1 = Integer.parseInt(rmiRegistryPort);
        }
        catch (NumberFormatException ex) {
            AdminServer.m_adminServerSubsystem.logErr(7021956244000743769L, rmiRegistryPort);
            System.setErr(AdminServer.defaultErr);
            System.err.println(ExceptionMessageAdapter.getMessage(7021956244000743769L, rmiRegistryPort));
            throw ex;
        }
        AdminServer.m_regMan = new PrimaryRegistryManager(new RegistryManagerMessageHandler(), this.m_args.getHostName(), int1);
        AdminServer.m_adminServerSubsystem.log(3, 7021956244000742641L, AdminServer.m_regMan);
        AdminServer.m_adminServerSubsystem.log(3, 7021956244000742645L, this.m_args.getRMIBindName());
        try {
            AdminServer.m_regMan.register(this.m_args.getRMIBindName(), this);
        }
        catch (RMIPortInUse rmiPortInUse) {
            AdminServer.m_adminServerSubsystem.logErr(ProgressResources.retrieveTranString("com.progress.international.messages.CMNMsgBundle", "Port already in use", (Object)rmiRegistryPort));
            throw rmiPortInUse;
        }
        this.m_isRegistered = true;
        AdminServer.m_adminServerSubsystem.log(2, 7021956244000743780L);
    }
    
    private synchronized void setupPlugins() throws Exception {
        AdminServer.m_adminServerSubsystem.log(1, 7021956244000742640L);
        this.loadPluginPropertyInfo();
        AdminServer.m_adminServerSubsystem.log(1, 7021956244000742642L);
        this.installPlugins();
        this.m_pluginsRunning = true;
        AdminServer.m_adminServerSubsystem.log(3, 7021956244000742643L);
        (this.m_adminServerThread = new Thread(this)).setName("AdminServer");
        this.m_adminServerThread.start();
        AdminServer.m_adminServerSubsystem.log(3, 7021956244000742644L);
    }
    
    public boolean isAdminServerStartupComplete() {
        boolean b = false;
        if (this.m_pluginsRunning && this.m_adminServerThread != null && this.m_adminServerThread.isAlive()) {
            b = true;
        }
        return b;
    }
    
    public void ping() throws RemoteException {
    }
    
    public IAdminServer connect(String decrypt, final String s) throws RemoteException {
        boolean validate;
        if (decrypt == null || s == null) {
            validate = false;
        }
        else {
            final crypto crypto = new crypto();
            decrypt = crypto.decrypt(decrypt);
            final acctAuthenticate acctAuthenticate = new acctAuthenticate();
            final String groups = this.m_args.getGroups();
            if (this.m_args.isNoAuditSuccAuth()) {
                acctAuthenticate.setNoAuditSuccAuth();
            }
            validate = acctAuthenticate.validate(decrypt, crypto.decrypt(s), groups);
        }
        IAdminServer adminServer = null;
        if (validate) {
            try {
                adminServer = new AdminContext(this, decrypt, s);
            }
            catch (Throwable t) {
                t.printStackTrace();
            }
        }
        else {
            adminServer = null;
        }
        return adminServer;
    }
    
    public IAdminServer connect(final String s, final String s2, final boolean b) throws RemoteException {
        return this.connect(s, s2);
    }
    
    protected synchronized Vector getPlugins(final String s, final String s2) {
        return this.getPlugins(s, s2, "java.lang.Object");
    }
    
    protected synchronized Vector getPlugins(final String s, final String s2, final String s3) {
        Class<?> forName;
        try {
            forName = Class.forName(s3);
        }
        catch (Exception ex) {
            Tools.px(ex, "### Failed to find class " + s3 + ". ###");
            return new Vector();
        }
        final Vector<Remote> vector = new Vector<Remote>(this.m_serverPluginInfos.size());
        for (int i = 0; i < this.m_serverPluginInfos.size(); ++i) {
            final ServerPluginInfo serverPluginInfo = this.m_serverPluginInfos.elementAt(i);
            if (null != serverPluginInfo.getPluginInstance()) {
                final Remote remoteObject = serverPluginInfo.getPluginInstance().getRemoteObject(s, s2);
                if (null != remoteObject && forName.isInstance(remoteObject)) {
                    vector.addElement(remoteObject);
                    AdminServer.m_adminServerSubsystem.log(2, 7021956244000742647L, remoteObject.toString());
                }
            }
        }
        return vector;
    }
    
    public Enumeration getContainerList() {
        return this.m_containerList.elements();
    }
    
    public Thread getAdminServerThread() {
        return this.m_adminServerThread;
    }
    
    public static String usage() {
        return AdminServer.cmnBundle.getTranString("Usage:") + " proadsv -start [options]" + AdminServerType.NEWLINE + AdminServerType.NEWLINE + "where \"options\" include:" + AdminServerType.NEWLINE + AdminServerType.NEWLINE + AdminServer.admBundle.getTranString("AdminServer Usage help") + AdminServerType.NEWLINE + AdminServerType.NEWLINE + AdminServer.admBundle.getTranString("AdminServer Usage plugins", new Object[] { IAdminServerConst.PLUGINS_FULLFILE_NAME }) + AdminServerType.NEWLINE + AdminServerType.NEWLINE + AdminServer.admBundle.getTranString("AdminServer Usage port", new Object[] { IAdminServerConst.RMI_REGISTRY_PORT }) + AdminServerType.NEWLINE + AdminServerType.NEWLINE + AdminServer.admBundle.getTranString("AdminServer Usage adminport", new Object[] { AdminServerType.DB_ADMIN_PORT + "" }) + AdminServerType.NEWLINE + "    -dbproperties <filename>     Database configuration information." + AdminServerType.NEWLINE + "                                 Default is " + AdminServerType.DB_PROPERTIES_FULLFILE + AdminServerType.NEWLINE + "    -dbaproperties <filename>    Database agent configuration information." + AdminServerType.NEWLINE + "                                 Default is " + AdminServerType.DBA_PROPERTIES_FULLFILE + AdminServerType.NEWLINE + "    -fathominitparams <filename> OpenEdge Management/OpenEdge Explorer environment information." + AdminServerType.NEWLINE + "                                 Default is " + AdminServerType.FATHOM_INIT_PARAMS + AdminServerType.NEWLINE + "    -fathomproperties <filename> OpenEdge Management/OpenEdge Explorer configuration information." + AdminServerType.NEWLINE + "                                 Default is " + AdminServerType.FATHOM_PROPERTIES_FULLFILE + AdminServerType.NEWLINE + "    -smdbproperties <filename>   Script managed database configuration information." + AdminServerType.NEWLINE + "                                 Default is " + AdminServerType.SMDB_PROPERTIES_FULLFILE + AdminServerType.NEWLINE + "    -ubproperties <filename>     UBroker configuration information." + AdminServerType.NEWLINE + "                                 Default is " + AdminServerType.UB_PROPERTIES_FULLFILE + AdminServerType.NEWLINE + "    -mgmtproperties <filename>   Management configuration information." + AdminServerType.NEWLINE + "                                 Default is " + AdminServerType.MGMT_PROPERTIES_FULLFILE + AdminServerType.NEWLINE + AdminServer.admBundle.getTranString("AdminServer Usage interactive") + AdminServerType.NEWLINE + AdminServerType.NEWLINE + AdminServer.admBundle.getTranString("AdminServer Usage admingroup") + AdminServerType.NEWLINE + AdminServerType.NEWLINE + AdminServer.admBundle.getTranString("AdminServer Usage requireusername") + AdminServerType.NEWLINE + AdminServerType.NEWLINE;
    }
    
    public abstract void openServiceEventObject() throws Exception;
    
    public abstract void signalServiceEventObject() throws Exception;
    
    public abstract void createDBEventObject(final String p0) throws Exception;
    
    public abstract void closeDBEventObject(final String p0) throws Exception;
    
    static String getCommand() {
        String line = "";
        try {
            final BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));
            bufferedWriter.newLine();
            bufferedWriter.write("AdminServer>");
            bufferedWriter.flush();
            line = new BufferedReader(new InputStreamReader(System.in)).readLine();
        }
        catch (IOException ex) {
            Tools.px(ex, "Exception getting interactive AdminServer command.");
        }
        return line;
    }
    
    protected synchronized boolean getShutdownRequested() {
        return this.m_shutdownRequested;
    }
    
    protected synchronized void setShutdownRequested(final boolean shutdownRequested) {
        this.m_shutdownRequested = shutdownRequested;
    }
    
    private void loadPluginPolicyInfo(final PropertyManager propertyManager) throws Exception {
        final Hashtable serverPolicyInfos = this.m_serverPolicyInfos;
        try {
            final String[] groups = propertyManager.groups("PluginPolicy");
            for (int i = 0; i < groups.length; ++i) {
                final String key = groups[i];
                if (0 != key.length()) {
                    serverPolicyInfos.put(key, this.loadPolicyInfo(propertyManager, key));
                }
            }
        }
        catch (Exception ex) {
            Tools.px(ex, "Error reading policy info from file ");
            throw ex;
        }
    }
    
    private ServerPolicyInfo loadPolicyInfo(final PropertyManager propertyManager, final String s) throws Exception {
        try {
            propertyManager.setCurrentGroup(s);
            String property = propertyManager.getProperty("classloader");
            if (null == property || 0 == property.length()) {
                property = "java.net.URLClassLoader";
            }
            final String property2 = propertyManager.getProperty("classpath");
            if (null == property2 || 0 == property2.length()) {
                throw new ChimeraException("Error: Classpath missing for policy " + s + ".");
            }
            String property3 = propertyManager.getProperty("pluginclasspath");
            if (null == property3 || 0 == property3.length()) {
                property3 = "";
            }
            String property4 = propertyManager.getProperty("jvmargs");
            if (null == property4 || 0 == property4.length()) {
                property4 = "";
            }
            String property5 = propertyManager.getProperty("policyfile");
            if (null == property5 || 0 == property5.length()) {
                property5 = "";
            }
            String property6 = propertyManager.getProperty("authpolicy");
            if (null == property6 || 0 == property6.length()) {
                property6 = "";
            }
            String property7 = propertyManager.getProperty("loginconfig");
            if (null == property7 || 0 == property7.length()) {
                property7 = "";
            }
            String property8 = propertyManager.getProperty("umask");
            if (null == property8 || 0 == property8.length()) {
                property8 = "";
            }
            return new ServerPolicyInfo(s, property, property4, property2, property3, property5, property6, property7, property8);
        }
        catch (Exception ex) {
            Tools.px(ex, "Error reading policy info from file ");
            throw ex;
        }
    }
    
    private void loadPluginPropertyInfo() throws Exception {
        final Vector serverPluginInfos = this.m_serverPluginInfos;
        final String pluginsFile = this.m_args.getPluginsFile();
        final Hashtable<String, ServerPluginInfo> hashtable = new Hashtable<String, ServerPluginInfo>();
        try {
            final PropertyManager propertyManager = new PropertyManager(null, false);
            propertyManager.setGetPropertyFilter(new PropertyFilter(propertyManager));
            propertyManager.load(pluginsFile, true);
            this.loadPluginPolicyInfo(propertyManager);
            final String[] groups = propertyManager.groups("Plugin");
            for (int i = 0; i < groups.length; ++i) {
                final String key = groups[i];
                if (0 != key.length()) {
                    hashtable.put(key, this.loadPluginInfo(propertyManager, key));
                }
            }
        }
        catch (Exception ex) {
            Tools.px(ex, "Error reading plugin info from file " + pluginsFile + ".");
            throw ex;
        }
        try {
            final Enumeration<ServerPluginInfo> elements = hashtable.elements();
            while (elements.hasMoreElements()) {
                final ServerPluginInfo serverPluginInfo = elements.nextElement();
                this.updateDependencies(hashtable, serverPluginInfo, serverPluginInfo);
            }
            for (int j = AdminServer.m_maxCount; j >= 0; --j) {
                final Enumeration<ServerPluginInfo> elements2 = hashtable.elements();
                while (elements2.hasMoreElements()) {
                    final ServerPluginInfo obj = elements2.nextElement();
                    if (obj.getRefCount() == j) {
                        serverPluginInfos.addElement(obj);
                        AdminServer.m_adminServerSubsystem.log(2, 7021956244000742663L, obj.getId());
                    }
                }
            }
        }
        catch (Exception ex2) {
            Tools.px(ex2, "Error setting up dependencies from file " + pluginsFile + ".");
            throw ex2;
        }
    }
    
    private void updateDependencies(final Hashtable hashtable, final ServerPluginInfo serverPluginInfo, final ServerPluginInfo serverPluginInfo2) {
        final String[] dependencyList = serverPluginInfo2.getDependencyList();
        if (dependencyList.length == 0) {
            return;
        }
        for (int i = 0; i < dependencyList.length; ++i) {
            final ServerPluginInfo serverPluginInfo3 = hashtable.get(dependencyList[i]);
            if (serverPluginInfo3 != null) {
                serverPluginInfo3.incrementRefCount();
                if (AdminServer.m_maxCount < serverPluginInfo3.getRefCount()) {
                    AdminServer.m_maxCount = serverPluginInfo3.getRefCount();
                }
                if (serverPluginInfo3.getId().equals(serverPluginInfo2.getId()) || serverPluginInfo3.getId().equals(serverPluginInfo.getId())) {
                    return;
                }
                this.updateDependencies(hashtable, serverPluginInfo, serverPluginInfo3);
            }
        }
    }
    
    private ServerPluginInfo loadPluginInfo(final PropertyManager propertyManager, final String str) throws Exception {
        propertyManager.setCurrentGroup(str);
        String property = propertyManager.getProperty("uniquetype");
        if (null == property || 0 == property.length()) {
            property = null;
        }
        String property2 = propertyManager.getProperty("license");
        if (null == property2 || 0 == property2.length()) {
            property2 = "0";
        }
        final String property3 = propertyManager.getProperty("classname");
        if (null == property3 || 0 == property3.length()) {
            throw new ChimeraException("Error: Class name missing for plugin " + str + ".");
        }
        final String property4 = propertyManager.getProperty("pluginarchive");
        if (null == property4 || 0 == property4.length()) {
            throw new ChimeraException("Error: Archive missing for plugin " + str + ".");
        }
        final String property5 = propertyManager.getProperty("pluginpolicy");
        if (null == property5 || 0 == property5.length()) {
            throw new ChimeraException("Error: Policy missing for plugin " + str + ".");
        }
        final String property6 = propertyManager.getProperty("pluginargs");
        final Vector vector = new Vector<String>();
        if (property6 != null) {
            final StringTokenizer stringTokenizer = new StringTokenizer(property6, ",");
            while (stringTokenizer.hasMoreTokens()) {
                vector.addElement(stringTokenizer.nextToken());
            }
        }
        final String[] anArray = new String[vector.size()];
        vector.copyInto(anArray);
        String str2 = propertyManager.getProperty("dependency");
        if (str2 == null) {
            str2 = propertyManager.getProperty("dependancy");
        }
        final Vector vector2 = new Vector<String>();
        if (str2 != null) {
            final StringTokenizer stringTokenizer2 = new StringTokenizer(str2, ",");
            while (stringTokenizer2.hasMoreTokens()) {
                vector2.addElement(stringTokenizer2.nextToken());
            }
        }
        final String[] anArray2 = new String[vector2.size()];
        vector2.copyInto(anArray2);
        final Vector<String> vector3 = new Vector<String>();
        final StringTokenizer stringTokenizer3 = new StringTokenizer(property2, ",");
        while (stringTokenizer3.hasMoreTokens()) {
            vector3.addElement(stringTokenizer3.nextToken());
        }
        final ServerPolicyInfo serverPolicyInfo = this.m_serverPolicyInfos.get(property5);
        if (null == serverPolicyInfo) {
            throw new ChimeraException("Error: PluginPolicy not found for plugin " + str + ".");
        }
        return new ServerPluginInfo(str, vector3, property3, property4, anArray, serverPolicyInfo, anArray2, property);
    }
    
    void loadPluginInfo() throws Exception {
        final Vector serverPluginInfos = this.m_serverPluginInfos;
        final String pluginsFile = this.m_args.getPluginsFile();
        InputStreamReader in = null;
        BufferedReader bufferedReader = null;
        try {
            in = new FileReader(pluginsFile);
            bufferedReader = new BufferedReader(in);
            String myReadLine;
            while (null != (myReadLine = myReadLine(bufferedReader))) {
                if (0 == myReadLine.length()) {
                    continue;
                }
                final String myReadLine2 = myReadLine(bufferedReader);
                if (null == myReadLine2 || 0 == myReadLine2.length()) {
                    throw new ChimeraException("Product number(s) missing for plugin " + myReadLine + ".");
                }
                final String myReadLine3 = myReadLine(bufferedReader);
                if (null == myReadLine3 || 0 == myReadLine3.length()) {
                    throw new ChimeraException("Error: Class name missing for plugin " + myReadLine + ".");
                }
                final String myReadLine4 = myReadLine(bufferedReader);
                if (null == myReadLine4 || 0 == myReadLine4.length()) {
                    throw new ChimeraException("Error: Archive missing for plugin " + myReadLine + ".");
                }
                final Vector vector = new Vector<String>();
                String myReadLine5;
                while (null != (myReadLine5 = myReadLine(bufferedReader)) && 0 != myReadLine5.length()) {
                    vector.addElement(myReadLine5);
                }
                final Vector<String> vector2 = new Vector<String>();
                final StringTokenizer stringTokenizer = new StringTokenizer(myReadLine2, ",");
                while (stringTokenizer.hasMoreTokens()) {
                    vector2.addElement(stringTokenizer.nextToken());
                }
                final String[] anArray = new String[vector.size()];
                vector.copyInto(anArray);
                final ServerPluginInfo obj = new ServerPluginInfo(myReadLine, vector2, myReadLine3, myReadLine4, anArray);
                serverPluginInfos.addElement(obj);
                AdminServer.m_adminServerSubsystem.log(2, 7021956244000742663L, obj.getId());
            }
        }
        catch (Exception ex) {
            Tools.px(ex, "Error reading plugin info from file " + pluginsFile + ".");
            throw ex;
        }
        finally {
            if (null != in) {
                try {
                    in.close();
                }
                catch (Exception ex2) {}
            }
            if (null != bufferedReader) {
                try {
                    bufferedReader.close();
                }
                catch (Exception ex3) {}
            }
        }
    }
    
    private static boolean isComment(final String s) {
        return s.length() > 0 && AdminServer.COMMENT_CHAR == s.trim().charAt(0);
    }
    
    private static String myReadLine(final BufferedReader bufferedReader) throws IOException {
        String line;
        do {
            line = bufferedReader.readLine();
        } while (null != line && isComment(line));
        return line;
    }
    
    void installPlugins() {
        final Vector vector = new Vector<Integer>();
        int n = 0;
        for (int i = 0; i < this.m_serverPluginInfos.size(); ++i) {
            AdminServer.m_adminServerSubsystem.log(2, 7021956244000742664L, ((ServerPluginInfo)this.m_serverPluginInfos.elementAt(i)).m_id);
            if (!this.installPlugin(i, n)) {
                AdminServer.m_adminServerSubsystem.logErr(7021956244000742665L, ((ServerPluginInfo)this.m_serverPluginInfos.elementAt(i)).m_id);
                vector.addElement(new Integer(i));
            }
            else {
                ++n;
            }
        }
        if (vector.size() > 0) {
            for (int j = vector.size() - 1; j >= 0; --j) {
                this.m_serverPluginInfos.removeElementAt(vector.elementAt(j));
            }
        }
    }
    
    void testR2R(final int n, final LicenseMgr licenseMgr) {
        try {
            licenseMgr.checkR2Run(n);
            System.out.println("### ### R2R " + n + " " + "yes");
        }
        catch (Exception ex) {
            System.out.println("### ### R2R " + n + " " + "no");
        }
    }
    
    private boolean installPlugin(final int index, final int index2) {
        final ServerPluginInfo serverPluginInfo = this.m_serverPluginInfos.elementAt(index);
        serverPluginInfo.setIndex(index2);
        String s;
        boolean testNonCoreLicense;
        try {
            if (Boolean.getBoolean("Chimera.BypassLicense")) {
                s = AdminServer.admBundle.getTranString("Licensing bypassed; plugin installed.");
                testNonCoreLicense = true;
            }
            else {
                final Vector productNumberList = serverPluginInfo.getProductNumberList();
                if (null == productNumberList) {
                    s = AdminServer.admBundle.getTranString("No licensing information for plugin.");
                    testNonCoreLicense = false;
                }
                else {
                    testNonCoreLicense = false;
                    for (int index3 = 0; !testNonCoreLicense && index3 < productNumberList.size(); ++index3) {
                        int intValue;
                        try {
                            intValue = Integer.valueOf(productNumberList.elementAt(index3));
                        }
                        catch (NumberFormatException ex2) {
                            continue;
                        }
                        if (intValue != 0) {
                            try {
                                if (AdminServer.m_licenseMgr.checkR2Run(intValue)) {
                                    testNonCoreLicense = true;
                                }
                            }
                            catch (LicenseMgr.NotLicensed notLicensed) {}
                        }
                    }
                    if (!testNonCoreLicense) {
                        testNonCoreLicense = this.testNonCoreLicense(serverPluginInfo);
                    }
                    if (!testNonCoreLicense) {
                        s = AdminServer.admBundle.getTranString("Plugin not licensed.");
                        AdminServer.m_adminServerSubsystem.logErr(7021956244000742670L, s);
                    }
                    else {
                        s = AdminServer.admBundle.getTranString("Plugin installed.");
                        AdminServer.m_adminServerSubsystem.log(3, 7021956244000742670L, s);
                    }
                }
            }
            if (testNonCoreLicense) {
                try {
                    ClassLoader classloader = null;
                    if (serverPluginInfo.getPolicy() != null) {
                        classloader = serverPluginInfo.getPolicy().getClassloader();
                    }
                    Class<?> pluginClass;
                    if (classloader != null) {
                        pluginClass = Class.forName(serverPluginInfo.m_className, true, classloader);
                    }
                    else {
                        pluginClass = Class.forName(serverPluginInfo.m_className);
                    }
                    final IServerPlugin pluginInstance = (IServerPlugin)pluginClass.newInstance();
                    serverPluginInfo.setPluginClass(pluginClass);
                    serverPluginInfo.setPluginInstance(pluginInstance);
                }
                catch (ClassNotFoundException ex3) {
                    s = ExceptionMessageAdapter.getMessage(7021956244000744666L, serverPluginInfo.m_archive);
                    testNonCoreLicense = false;
                }
            }
            if (testNonCoreLicense) {
                final IServerPlugin pluginInstance2 = serverPluginInfo.getPluginInstance();
                if (pluginInstance2.init(index2, AdminServer.wkAdminServer, serverPluginInfo.m_args)) {
                    if (pluginInstance2 instanceof IManagedPlugin) {
                        if (AdminServer.m_container != null) {
                            final String pluginName = ((IManagedPlugin)pluginInstance2).getPluginName();
                            final AbstractMBean mBean = AdminServer.m_container.getMBean(pluginName);
                            if (mBean != null) {
                                final AbstractPluginComponent abstractPluginComponent = (AbstractPluginComponent)mBean.getManagedComponent();
                                if (abstractPluginComponent != null) {
                                    abstractPluginComponent.init(pluginInstance2);
                                    ((IManagedPlugin)pluginInstance2).initManagedPlugin(abstractPluginComponent);
                                }
                                else {
                                    LogSystem.logIt("ManagementPlugin", 4, "No remote managed component for delegate in plugin: " + pluginName);
                                }
                            }
                            else {
                                LogSystem.logIt("ManagementPlugin", 4, "No remote delegate for plugin: " + pluginName);
                            }
                        }
                        else {
                            final AbstractPluginComponent abstractPluginComponent2 = (AbstractPluginComponent)Class.forName(((IManagedPlugin)pluginInstance2).getComponentClassName()).newInstance();
                            final LocalPluginContext localPluginContext = new LocalPluginContext();
                            abstractPluginComponent2.init(pluginInstance2, (IFrameworkComponentContext)localPluginContext);
                            localPluginContext.init(abstractPluginComponent2);
                            ((IManagedPlugin)pluginInstance2).initManagedPlugin(abstractPluginComponent2);
                        }
                    }
                }
                else {
                    s = AdminServer.admBundle.getTranString("Plugin asked not to be installed.");
                    testNonCoreLicense = false;
                }
            }
        }
        catch (Exception ex) {
            Tools.px("Unable to install server plugin " + serverPluginInfo.m_id + ".", ex);
            serverPluginInfo.setPluginInstance(null);
            return false;
        }
        if (!testNonCoreLicense) {
            AdminServer.m_adminServerSubsystem.logErr(7021956244000742671L, serverPluginInfo.m_id, s);
            serverPluginInfo.setPluginInstance(null);
        }
        else {
            AdminServer.m_adminServerSubsystem.log(3, 7021956244000742672L, serverPluginInfo.m_id, s);
        }
        return testNonCoreLicense;
    }
    
    private boolean testNonCoreLicense(final ServerPluginInfo serverPluginInfo) {
        boolean adminLicenseCheck = false;
        final String className = serverPluginInfo.getClassName();
        final String id = serverPluginInfo.getId();
        final Vector productNumberList = serverPluginInfo.getProductNumberList();
        try {
            final IServerPlugin serverPlugin = (IServerPlugin)Class.forName(className).newInstance();
            if (serverPlugin instanceof IAdminLicenseCheck) {
                adminLicenseCheck = ((IAdminLicenseCheck)serverPlugin).adminLicenseCheck(id, productNumberList);
            }
        }
        catch (ClassNotFoundException ex) {
            adminLicenseCheck = false;
        }
        catch (InstantiationException ex2) {
            adminLicenseCheck = false;
        }
        catch (IllegalAccessException ex3) {
            adminLicenseCheck = false;
        }
        catch (LicenseMgr.LicenseError licenseError) {
            adminLicenseCheck = false;
        }
        catch (LicenseMgr.NotLicensed notLicensed) {
            adminLicenseCheck = false;
        }
        catch (LicenseMgr.CannotContactLicenseMgr cannotContactLicenseMgr) {
            adminLicenseCheck = false;
        }
        catch (LicenseMgr.NoSuchProduct noSuchProduct) {
            adminLicenseCheck = false;
        }
        catch (LicenseMgr.ProductExpired productExpired) {
            adminLicenseCheck = false;
        }
        return adminLicenseCheck;
    }
    
    public void run() {
        this.startPlugins();
        while (!this.m_shutdownRequested) {
            try {
                Thread.currentThread();
                Thread.sleep(2147483647L);
            }
            catch (InterruptedException ex2) {}
            catch (Exception ex) {
                Tools.px("AdminServer died due to exception.", ex);
            }
        }
        this.doShutdown();
    }
    
    public void shutdown(final String s) throws RemoteException {
        new acctAuthenticate();
        final String whoamiJNI = acctAuthenticate.whoamiJNI();
        if (s.equals("INTERACTIVE")) {
            AdminServer.m_adminServerSubsystem.log(3, "Shutting down interactive AdminServer");
        }
        else if (whoamiJNI.equals(s)) {
            AdminServer.m_adminServerSubsystem.log(3, "User name matches. Shutdown permitted.");
        }
        else {
            final String property = System.getProperty("os.name");
            if (property.startsWith("Windows") && s.endsWith("Administrator")) {
                AdminServer.m_adminServerSubsystem.log(2, "Allow Windows Administrator to shut down.");
            }
            else {
                if (property.startsWith("Windows") || !s.equals("root")) {
                    AdminServer.m_adminServerSubsystem.log(2, "User " + s + " tried to shut down Admin Server.");
                    AdminServer.m_adminServerSubsystem.log(1, "User name does not match. Shutdown aborted.");
                    throw new RemoteException("User name does not match. Shutdown aborted.");
                }
                AdminServer.m_adminServerSubsystem.log(2, "Allow the super user to shut down.");
            }
        }
        this.m_shutdownRequested = true;
        AdminServer.m_adminServerSubsystem.log(2, 7021956244000742653L, s);
        synchronized (this) {
            if (this.m_adminServerThread != null) {
                this.m_adminServerThread.interrupt();
            }
        }
    }
    
    private void doShutdown() {
        AdminServer.m_adminServerSubsystem.log(1, 7021956244000742650L);
        this.shutdownPlugins();
        this.removeFromRegistry();
        AdminServer.m_adminServerSubsystem.log(1, 7021956244000742652L);
    }
    
    private synchronized void startPlugins() {
        this.m_pluginsRunning = true;
        for (int index = 0; index < this.m_serverPluginInfos.size() && !this.m_shutdownRequested; ++index) {
            final ServerPluginInfo serverPluginInfo = this.m_serverPluginInfos.elementAt(index);
            AdminServer.m_adminServerSubsystem.log(2, 7021956244000742666L, serverPluginInfo.m_id);
            this.startPlugin(serverPluginInfo);
            AdminServer.m_adminServerSubsystem.log(3, 7021956244000742667L, new Object[] { serverPluginInfo.m_id });
        }
    }
    
    private synchronized void shutdownPlugins() {
        if (!this.m_pluginsRunning) {
            return;
        }
        for (int i = this.m_serverPluginInfos.size() - 1; i > -1; --i) {
            final ServerPluginInfo serverPluginInfo = this.m_serverPluginInfos.elementAt(i);
            AdminServer.m_adminServerSubsystem.log(2, 7021956244000742668L, serverPluginInfo.m_id);
            this.shutdownPlugin(serverPluginInfo);
            AdminServer.m_adminServerSubsystem.log(3, 7021956244000742669L, serverPluginInfo.m_id);
        }
        this.m_pluginsRunning = false;
    }
    
    private void startPlugin(final ServerPluginInfo serverPluginInfo) {
        final IServerPlugin pluginInstance = serverPluginInfo.getPluginInstance();
        if (pluginInstance != null && pluginInstance instanceof Runnable) {
            final Thread thread = new Thread((Runnable)pluginInstance);
            thread.setName("Plug-in of type: " + ((Runnable)pluginInstance).getClass().getName());
            if (serverPluginInfo.getPolicy() != null && serverPluginInfo.getPolicy().getClassloader() != null) {
                thread.setContextClassLoader(serverPluginInfo.getPolicy().getClassloader());
            }
            serverPluginInfo.setThread(thread);
            thread.start();
        }
        serverPluginInfo.setRunning(true);
    }
    
    private boolean startPlugin(final String original) {
        ServerPluginInfo obj = this.getPlugin(original);
        if (obj == null) {
            try {
                String string = new String(original);
                if (!string.startsWith("Plugin")) {
                    string = "Plugin." + string;
                }
                final String pluginsFile = this.m_args.getPluginsFile();
                final PropertyManager propertyManager = new PropertyManager(null, false);
                propertyManager.setGetPropertyFilter(new PropertyFilter(propertyManager));
                propertyManager.load(pluginsFile, true);
                obj = this.loadPluginInfo(propertyManager, string);
                obj.setIndex(this.m_serverPluginInfos.size());
                this.m_serverPluginInfos.addElement(obj);
            }
            catch (Exception ex) {
                return false;
            }
        }
        int n = -1;
        IServerPlugin serverPlugin = obj.getPluginInstance();
        if (serverPlugin == null) {
            for (int i = 0; i < this.m_serverPluginInfos.size(); ++i) {
                if (((ServerPluginInfo)this.m_serverPluginInfos.elementAt(i)).getId().equalsIgnoreCase(obj.getId())) {
                    n = i;
                    break;
                }
            }
            if (n == -1) {
                return false;
            }
            AdminServer.m_adminServerSubsystem.log(2, 7021956244000742664L, obj.getId());
            this.installPlugin(n, obj.getIndex());
            serverPlugin = obj.getPluginInstance();
        }
        if (serverPlugin == null || obj.isRunning()) {
            return false;
        }
        AdminServer.m_adminServerSubsystem.log(2, 7021956244000742666L, obj.m_id);
        this.startPlugin(obj);
        AdminServer.m_adminServerSubsystem.log(3, 7021956244000742667L, new Object[] { obj.m_id });
        return true;
    }
    
    private ServerPluginInfo getPlugin(final String original) {
        String string = new String(original);
        if (!string.startsWith("Plugin")) {
            string = "Plugin." + string;
        }
        ServerPluginInfo serverPluginInfo = null;
        for (int i = 0; i < this.m_serverPluginInfos.size(); ++i) {
            if (string.equalsIgnoreCase(((ServerPluginInfo)this.m_serverPluginInfos.elementAt(i)).getId())) {
                serverPluginInfo = (ServerPluginInfo)this.m_serverPluginInfos.elementAt(i);
                break;
            }
        }
        if (serverPluginInfo == null) {
            return null;
        }
        return serverPluginInfo;
    }
    
    private boolean shutdownPlugin(final String s) {
        final ServerPluginInfo plugin = this.getPlugin(s);
        if (plugin == null || !plugin.isRunning()) {
            return false;
        }
        AdminServer.m_adminServerSubsystem.log(2, 7021956244000742668L, plugin.m_id);
        this.shutdownPlugin(plugin);
        AdminServer.m_adminServerSubsystem.log(3, 7021956244000742669L, plugin.m_id);
        return true;
    }
    
    private void shutdownPlugin(final ServerPluginInfo serverPluginInfo) {
        final IServerPlugin pluginInstance = serverPluginInfo.getPluginInstance();
        if (pluginInstance != null) {
            pluginInstance.shutdown();
        }
        final Thread thread = serverPluginInfo.getThread();
        if (thread != null && thread.isAlive()) {
            thread.interrupt();
            Thread.yield();
        }
        serverPluginInfo.setThread(null);
        serverPluginInfo.setPluginInstance(null);
        serverPluginInfo.setRunning(false);
    }
    
    private void removeFromRegistry() {
        if (this.m_isRegistered) {
            AdminServer.m_adminServerSubsystem.log(2, 7021956244000742651L);
            AdminServer.m_regMan.unregister();
            this.m_isRegistered = false;
        }
    }
    
    private ContainerImpl createSonicContainer() {
        ContainerImpl containerImpl = null;
        final int managementFrameworkTimeout = this.m_args.getManagementFrameworkTimeout();
        try {
            final boolean dsEnabled = AdminServerType.isDSEnabled();
            String s = System.getProperty("SONICMQ_HOME");
            if (s == null || s.length() < 1) {
                if (dsEnabled) {
                    s = AdminServerType.getFathomInstallDir() + IAdminServerConst.FILE_SEPARATOR + "MQ6.1";
                }
                else {
                    s = System.getProperty("Install.Dir");
                }
            }
            final String string = s + IAdminServerConst.FILE_SEPARATOR + System.getProperty("SONICMQ_CONTAINER_FILE", "container.xml");
            final String string2 = s + IAdminServerConst.FILE_SEPARATOR + System.getProperty("SONICMQ_DS_FILE", "ds.xml");
            String[] array;
            if (dsEnabled) {
                array = new String[] { string, string2 };
            }
            else {
                array = new String[] { string };
            }
            final Subsystem adminServerSubsystem = AdminServer.m_adminServerSubsystem;
            LogSystem.logIt("AdminServer", 1, "Instantiating management framework container...");
            containerImpl = (ContainerImpl)new TryIt(new AdminServerRemote(array, this.m_sharedClasses), new Object()).callIt(this.m_args.getManagementFrameworkTimeout() * 1000);
        }
        catch (TimedOut timedOut) {
            final Subsystem adminServerSubsystem2 = AdminServer.m_adminServerSubsystem;
            LogSystem.logItErr("AdminServer", "Instantiation of management framework container timed out after " + managementFrameworkTimeout + " seconds");
        }
        catch (Exception ex) {
            AdminServer.m_adminServerSubsystem.log(ex.getMessage());
        }
        if (containerImpl == null) {
            AdminServer.m_adminServerSubsystem.log("Failed to instantiate the management framework container.");
        }
        return containerImpl;
    }
    
    public static boolean isManagementEnabled() {
        return AdminServer.m_managementEnabled;
    }
    
    private boolean checkAdminerverLock() {
        boolean b = false;
        final File file = new File(AdminServerType.getFathomConfigDir() + IAdminServerConst.FILE_SEPARATOR + "admsvr.lock");
        try {
            if (file.exists()) {
                AdminServer.m_adminServerSubsystem.log(4, 7021956244000746592L, file.getAbsolutePath());
                if (AdminServer.wkAdminServer.m_admLockFileLock != null && AdminServer.wkAdminServer.m_admLockFileLock.isValid()) {
                    b = true;
                }
                else {
                    final FileOutputStream fileOutputStream = new FileOutputStream(file, true);
                    final FileLock tryLock = fileOutputStream.getChannel().tryLock();
                    if (tryLock == null) {
                        b = true;
                    }
                    else {
                        b = false;
                        tryLock.release();
                        fileOutputStream.close();
                    }
                }
                if (b) {
                    AdminServer.m_adminServerSubsystem.logErr(7021956244000746592L, file.getAbsolutePath());
                    AdminServer.m_adminServerSubsystem.logErr(7021956244000746594L);
                }
            }
        }
        catch (Exception ex) {
            b = true;
            AdminServer.m_adminServerSubsystem.logErr("Failed to verify lock status of admmin server lock file.");
            if (AdminServer.m_adminServerSubsystem.getWriteLevel() > 3) {
                ex.printStackTrace();
            }
        }
        return b;
    }
    
    private boolean checkSonicLocks() {
        boolean b = false;
        if (AdminServerType.isDSEnabled()) {
            String str = System.getProperty("SONICMQ_HOME");
            if (str == null) {
                str = AdminServerType.getFathomInstallDir() + IAdminServerConst.FILE_SEPARATOR + "MQ6.1" + IAdminServerConst.FILE_SEPARATOR;
            }
            if (!str.endsWith(IAdminServerConst.FILE_SEPARATOR)) {
                str += IAdminServerConst.FILE_SEPARATOR;
            }
            final ManagementProperties managementProperties = AdminServerType.getManagementProperties();
            final File file = new File(str + ((managementProperties != null) ? managementProperties.getDomainName() : "Fathom1") + IAdminServerConst.FILE_SEPARATOR + "data/_MFSystem/lock");
            final File file2 = new File(str + "SonicMQStore/db.lck");
            boolean b2 = true;
            final File[] array = { file, file2 };
            for (int i = 0; i < array.length; ++i) {
                if (array[i] != null && array[i].exists()) {
                    AdminServer.m_adminServerSubsystem.log(4, 7021956244000746600L, array[i].getAbsolutePath());
                    boolean b3 = false;
                    try {
                        if (!array[i].delete()) {
                            b3 = true;
                            if (!array[i].canWrite()) {
                                AdminServer.m_adminServerSubsystem.logErr(7021956244000746596L);
                            }
                        }
                    }
                    catch (Exception ex) {
                        b3 = true;
                        AdminServer.m_adminServerSubsystem.logErr(7021956244000746598L, ex.getMessage());
                        if (AdminServer.m_adminServerSubsystem.getWriteLevel() > 3) {
                            ex.printStackTrace();
                        }
                    }
                    if (b3) {
                        b2 = false;
                        AdminServer.m_adminServerSubsystem.logErr(7021956244000746601L, array[i].getAbsolutePath());
                    }
                }
            }
            if (!b2) {
                b = true;
            }
        }
        return b;
    }
    
    private boolean checkFathomLock() {
        boolean b = false;
        final File file = new File(AdminServerType.getFathomConfigDir() + IAdminServerConst.FILE_SEPARATOR + "fathom.odx");
        if (file.exists()) {
            AdminServer.m_adminServerSubsystem.log(4, 7021956244000746602L, file.getAbsolutePath());
            if (!ConfigurationTool.deleteDirectory(file, AdminServer.m_adminServerSubsystem.getWriteLevel() > 3)) {
                b = true;
                AdminServer.m_adminServerSubsystem.logErr(7021956244000746603L, file.getAbsolutePath());
            }
        }
        return b;
    }
    
    private int getPidFromLock(final File file) {
        String s = null;
        int int1 = -1;
        final byte[] array = new byte[32];
        if (file != null && file.exists()) {
            try {
                final FileInputStream fileInputStream = new FileInputStream(file);
                final int read = fileInputStream.read(array);
                fileInputStream.close();
                if (read > 0) {
                    s = new String(array);
                    if (s != null) {
                        try {
                            int1 = Integer.parseInt(s.trim());
                        }
                        catch (Exception ex2) {
                            AdminServer.m_adminServerSubsystem.logErr(7021956244000746604L, s);
                        }
                    }
                }
                if (read <= 0 || s == null) {
                    AdminServer.m_adminServerSubsystem.logErr(7021956244000746605L);
                }
            }
            catch (Exception ex) {
                AdminServer.m_adminServerSubsystem.logErr(7021956244000746606L, ex.getMessage());
                if (AdminServer.m_adminServerSubsystem.getWriteLevel() > 3) {
                    ex.printStackTrace();
                }
            }
        }
        return int1;
    }
    
    private synchronized boolean createAdminerverLock() {
        int n = 1;
        boolean b = true;
        int current_PID = -1;
        final File file = new File(AdminServerType.getFathomConfigDir() + IAdminServerConst.FILE_SEPARATOR + "admsvr.lock");
        final Environment environment = new Environment();
        try {
            if (environment != null) {
                current_PID = environment.getCurrent_PID(0);
                if (current_PID <= 0) {
                    n = 0;
                    AdminServer.m_adminServerSubsystem.logErr(7021956244000746607L);
                    AdminServer.m_adminServerSubsystem.logErr(7021956244000746608L);
                }
            }
            else {
                n = 0;
                AdminServer.m_adminServerSubsystem.logErr(7021956244000746590L);
                AdminServer.m_adminServerSubsystem.logErr(7021956244000746608L);
            }
            if (n != 0) {
                if (file.exists()) {
                    AdminServer.m_adminServerSubsystem.log(4, 7021956244000746592L, file.getAbsolutePath());
                    if (AdminServer.wkAdminServer.m_admLockFileLock != null && AdminServer.wkAdminServer.m_admLockFileLock.isValid()) {
                        n = 0;
                        AdminServer.m_adminServerSubsystem.logErr(7021956244000746592L, file.getAbsolutePath());
                        AdminServer.m_adminServerSubsystem.logErr(7021956244000746594L);
                    }
                }
                else if (!file.createNewFile()) {
                    n = 0;
                    AdminServer.m_adminServerSubsystem.logErr("Failed to create admin server lock file");
                }
                if (n != 0) {
                    final FileOutputStream fileOutputStream = new FileOutputStream(file);
                    final byte[] b2 = new byte[32];
                    for (int i = 0; i < b2.length; ++i) {
                        b2[i] = 0;
                    }
                    final byte[] bytes = String.valueOf(current_PID).getBytes();
                    System.arraycopy(bytes, 0, b2, 0, bytes.length);
                    fileOutputStream.write(b2);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    this.m_admLockFileInputStream = new FileInputStream(file);
                    this.m_admLockFileLock = this.m_admLockFileInputStream.getChannel().tryLock(0L, Long.MAX_VALUE, true);
                    if (this.m_admLockFileLock == null) {
                        b = false;
                        AdminServer.m_adminServerSubsystem.logErr("Failed to aquire a lock on admin server lock file");
                    }
                    else {
                        b = true;
                    }
                }
            }
        }
        catch (Exception ex) {
            if (!file.exists()) {
                n = 0;
                AdminServer.m_adminServerSubsystem.logErr("Failed to create admin server lock file");
            }
            if (this.m_admLockFileLock == null) {
                b = false;
                AdminServer.m_adminServerSubsystem.logErr("Failed to aquire a lock on admin server lock file");
            }
            if (AdminServer.m_adminServerSubsystem.getWriteLevel() > 3) {
                ex.printStackTrace();
            }
        }
        return n != 0 && b;
    }
    
    private boolean deleteAdminerverLock() {
        final File file = new File(AdminServerType.getFathomConfigDir() + IAdminServerConst.FILE_SEPARATOR + "admsvr.lock");
        try {
            if (this.m_admLockFileLock != null && this.m_admLockFileLock.isValid()) {
                this.m_admLockFileLock.release();
            }
            if (this.m_admLockFileInputStream != null) {
                this.m_admLockFileInputStream.close();
            }
            if (!file.delete() && file.exists()) {
                if (!file.canWrite()) {
                    AdminServer.m_adminServerSubsystem.logErr(7021956244000746596L);
                }
                else {
                    AdminServer.m_adminServerSubsystem.logErr(7021956244000746597L);
                }
            }
        }
        catch (IOException ex) {
            AdminServer.m_adminServerSubsystem.logErr("File lock could not be released on admsvr.lock file");
            if (AdminServer.m_adminServerSubsystem.getWriteLevel() > 3) {
                ex.printStackTrace();
            }
        }
        catch (Exception ex2) {
            AdminServer.m_adminServerSubsystem.logErr(7021956244000746598L, ex2.getMessage());
            if (AdminServer.m_adminServerSubsystem.getWriteLevel() > 3) {
                ex2.printStackTrace();
            }
        }
        return !file.exists();
    }
    
    public String getOS() {
        return System.getProperty("os.name");
    }
    
    public synchronized String getHost() {
        return AdminServer.m_regMan.host();
    }
    
    public synchronized boolean isReqUserName() {
        return this.m_args.isReqUserName();
    }
    
    public synchronized String getPort() {
        return String.valueOf(AdminServer.m_regMan.port());
    }
    
    public synchronized String getAdminPort() {
        final String adminPort = this.m_args.getAdminPort();
        if (adminPort.length() == 0) {
            return null;
        }
        return adminPort;
    }
    
    public synchronized String getDbPropFile() {
        final String dbPropFile = this.m_args.getDbPropFile();
        if (dbPropFile.length() == 0) {
            return null;
        }
        return dbPropFile;
    }
    
    public synchronized String getDbaPropFile() {
        return this.m_args.getDbaPropFile();
    }
    
    public synchronized String getSmdbPropFile() {
        return this.m_args.getSmdbPropFile();
    }
    
    public synchronized String getUbPropFile() {
        return this.m_args.getUbPropFile();
    }
    
    public synchronized String getMgmtPropFile() {
        return this.m_args.getMgmtPropFile();
    }
    
    public synchronized String getFathomPropFile() {
        final AdminServerGetopt args = this.m_args;
        return AdminServerGetopt.getFathomPropFile();
    }
    
    public synchronized String getFathomInitParams() {
        final AdminServerGetopt args = this.m_args;
        return AdminServerGetopt.getFathomInitParams();
    }
    
    public IEventBroker getEventBroker() {
        return AdminServer.localEventBroker;
    }
    
    public EventBroker getEventBrokerLocal() {
        return AdminServer.localEventBroker;
    }
    
    public IEventStream getEventStream() {
        return AdminServer.localEventStream;
    }
    
    public ILicenseMgr getLicenseManager() throws RemoteException {
        return AdminServer.m_licenseMgr;
    }
    
    public Enumeration getChildren() {
        return null;
    }
    
    public String getDisplayName() {
        return null;
    }
    
    public String getHelpMapFile() {
        return null;
    }
    
    public String getApplicationName() {
        return null;
    }
    
    public String getMMCClientClass() {
        return null;
    }
    
    private void initRemoteLogFileReader() throws RemoteException {
        this.rlfr = new RemoteLogFileReader(this.getEventBrokerLocal(), this.getEventStream());
    }
    
    public IFileRef openFile(final String s, final IEventListener eventListener) throws RemoteException {
        if (this.rlfr == null) {
            this.initRemoteLogFileReader();
        }
        try {
            final IFileRef openFile = this.rlfr.openFile("admserv.log", eventListener);
            System.out.println("openFileHandle = " + openFile);
            return openFile;
        }
        catch (Exception ex) {
            Excp.print(ex);
            return null;
        }
    }
    
    static {
        AdminServer.m_adminServerSubsystem = AdminServerLog.get();
        AdminServer.m_logInitialized = false;
        AdminServer.m_regMan = null;
        AdminServer.localEventStream = null;
        AdminServer.localEventBroker = null;
        AdminServer.wkAdminServer = null;
        AdminServer.EventBrokerTrace = true;
        AdminServer.RegistryManagerTrace = true;
        AdminServer.m_licenseMgr = null;
        AdminServer.m_maxCount = 0;
        AdminServer.admBundle = AdminServerType.admBundle;
        AdminServer.cmnBundle = AdminServerType.cmnBundle;
        AdminServer.defaultOut = System.out;
        AdminServer.defaultErr = System.err;
        AdminServer.m_container = null;
        AdminServer.m_managementEnabled = false;
        AdminServer.COMMENT_CHAR = '#';
    }
    
    class PropFileEditListener extends EventListener
    {
        AdminServer adminServer;
        
        PropFileEditListener(final AdminServer adminServer) {
            this.adminServer = null;
            this.adminServer = adminServer;
        }
        
        public void processEvent(final IEventObject eventObject) {
            System.out.println("Handling ERenegadePropertyFileChange");
            AdminServer.this.removeFromRegistry();
            try {
                final EAdminServerWarmRestartEvent eAdminServerWarmRestartEvent = new EAdminServerWarmRestartEvent(this);
                if (AdminServer.localEventBroker != null) {
                    AdminServer.localEventBroker.postEvent(eAdminServerWarmRestartEvent);
                }
            }
            catch (RemoteException ex) {
                Excp.print(ex);
            }
            AdminServer.this.shutdownPlugins();
            this.adminServer.setShutdownRequested(true);
            OSSpecific.invokeAdminServer(IAdminServerConst.ADMIN_SERVER_CLASSNAME, this.adminServer.m_argsIn);
        }
    }
    
    class EventBrokerMessageHandler implements IMessageCallback
    {
        public void handleMessage(final String s) {
            if (AdminServer.EventBrokerTrace) {
                EventManagerLog.get().log(4, s);
            }
        }
        
        public void handleMessage(final int n, final String s) {
            if (AdminServer.EventBrokerTrace) {
                EventManagerLog.get().log(n, s);
            }
        }
        
        public void handleException(final Throwable t, final String str) {
            if (AdminServer.EventBrokerTrace) {
                Tools.px(t, "EventBroker: " + str);
            }
        }
        
        public void handleException(final int n, final Throwable t, final String str) {
            if (AdminServer.EventBrokerTrace) {
                Tools.px(t, "EventBroker: " + str);
            }
        }
    }
    
    class RegistryManagerMessageHandler implements IMessageCallback
    {
        public void handleMessage(final String s) {
            if (AdminServer.RegistryManagerTrace) {
                RegistryManagerLog.get().log(4, s);
            }
        }
        
        public void handleMessage(final int n, final String s) {
            if (AdminServer.RegistryManagerTrace) {
                RegistryManagerLog.get().log(n, s);
            }
        }
        
        public void handleException(final Throwable t, final String str) {
            if (AdminServer.RegistryManagerTrace) {
                Tools.px(t, "RegistryManager: " + str);
            }
        }
        
        public void handleException(final int n, final Throwable t, final String str) {
            if (AdminServer.RegistryManagerTrace) {
                Tools.px(t, "RegistryManager: " + str);
            }
        }
    }
}
