// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera.clu.dbMan;

import com.progress.agent.database.EDBAStateChanged;
import com.progress.juniper.admin.EConfigurationStateChanged;
import com.progress.agent.database.EDBACrash;
import com.progress.agent.database.EDBAStartupCompleted;
import com.progress.juniper.admin.EConfigurationCrashEvent;
import com.progress.juniper.admin.EPrimaryStartupFailed;
import com.progress.juniper.admin.EStartupProcessCompleted;
import java.util.ResourceBundle;
import com.progress.common.util.NetInfo;
import com.progress.juniper.admin.IJARmiComponent;
import com.progress.juniper.admin.IJAExecutableObject;
import java.util.Hashtable;
import com.progress.common.util.Port;
import com.progress.common.util.Getopt;
import com.progress.chimera.adminserver.IAdminServer;
import com.progress.common.util.crypto;
import com.progress.chimera.adminserver.IAdministrationServer;
import com.progress.chimera.adminserver.IAdminServerConnection;
import com.progress.common.util.IMessageCallback;
import com.progress.juniper.admin.JAStatusObject;
import com.progress.common.networkevents.IEventInterestObject;
import com.progress.common.networkevents.IEventListener;
import java.rmi.server.RemoteStub;
import java.rmi.RemoteException;
import com.progress.agent.database.IAgentDatabase;
import java.util.Enumeration;
import com.progress.juniper.admin.IJAConfiguration;
import java.util.Vector;
import com.progress.juniper.admin.IJAPlugIn;
import com.progress.juniper.admin.IJADatabase;
import com.progress.common.util.acctAuthenticate;
import java.rmi.RMISecurityManager;
import com.progress.international.resources.ProgressResources;
import com.progress.common.networkevents.IEventStream;
import com.progress.common.networkevents.IEventBroker;
import com.progress.common.rmiregistry.RegistryManager;
import com.progress.common.util.ICmdConst;

public class dbMan implements ICmdConst
{
    private final String UNKNOWN = " ";
    private boolean m_start;
    private boolean m_stop;
    private boolean m_query;
    private boolean m_help;
    private boolean m_brief;
    private boolean m_processAllDB;
    private boolean m_isAgent;
    private String m_host;
    private String m_port;
    private String m_user;
    private String m_pwd;
    private String m_db;
    private String m_vst;
    private String m_config;
    private RegistryManager m_regMan;
    private int m_timeout;
    private int m_msgwait;
    private int m_exitStatus;
    private boolean m_isRemote;
    private IEventBroker m_eventBroker;
    private IEventStream m_eventStream;
    private static ProgressResources m_ccResources;
    private static final String m_bundleClass = "com.progress.international.messages.DBManBundle";
    private static final String NEWLINE;
    private static final int FAILURE = 1;
    private static final int SUCCESS = 0;
    
    public dbMan() {
        this.m_start = false;
        this.m_stop = false;
        this.m_query = false;
        this.m_help = false;
        this.m_brief = false;
        this.m_processAllDB = false;
        this.m_isAgent = false;
        this.m_host = null;
        this.m_port = null;
        this.m_user = null;
        this.m_pwd = null;
        this.m_db = " ";
        this.m_vst = " ";
        this.m_config = null;
        this.m_regMan = null;
        this.m_timeout = 240;
        this.m_msgwait = 20;
        this.m_exitStatus = 0;
        this.m_isRemote = false;
        this.m_eventBroker = null;
        this.m_eventStream = null;
        try {
            this.jbInit();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static void main(final String[] array) {
        new dbMan(array);
    }
    
    dbMan(final String[] array) {
        this.m_start = false;
        this.m_stop = false;
        this.m_query = false;
        this.m_help = false;
        this.m_brief = false;
        this.m_processAllDB = false;
        this.m_isAgent = false;
        this.m_host = null;
        this.m_port = null;
        this.m_user = null;
        this.m_pwd = null;
        this.m_db = " ";
        this.m_vst = " ";
        this.m_config = null;
        this.m_regMan = null;
        this.m_timeout = 240;
        this.m_msgwait = 20;
        this.m_exitStatus = 0;
        this.m_isRemote = false;
        this.m_eventBroker = null;
        this.m_eventStream = null;
        if (array.length == 0) {
            this.printUsage();
            System.exit(1);
        }
        System.setSecurityManager(new RMISecurityManager());
        int processCommandLineArguments = this.processCommandLineArguments(array);
        if (processCommandLineArguments != 0 && !this.m_help) {
            System.exit(processCommandLineArguments);
        }
        else if (this.m_help) {
            this.printUsage();
            System.exit(processCommandLineArguments);
        }
        if (this.m_user == null) {
            this.m_user = System.getProperty("user.name");
        }
        if (this.m_user != null && this.m_pwd == null) {
            if (!this.m_isRemote && this.m_user.equals(System.getProperty("user.name"))) {
                this.m_pwd = new acctAuthenticate().generatePassword(this.m_user);
            }
            else {
                this.m_pwd = this.promptForUP(this.m_user)[1];
            }
        }
        final IJAPlugIn juniperPlugin = this.getJuniperPlugin();
        if (juniperPlugin != null) {
            final Vector database = this.getDatabase(juniperPlugin);
            if (database.isEmpty()) {
                this.printDatabaseNotFound();
                processCommandLineArguments = 1;
            }
            else {
                for (int i = 0; i < database.size(); ++i) {
                    final int executeDBCommand = this.executeDBCommand(database.elementAt(i));
                    if (executeDBCommand != 0) {
                        processCommandLineArguments = 1;
                    }
                    if (executeDBCommand != 0) {
                        System.err.println(dbMan.NEWLINE + dbMan.m_ccResources.getTranString("DBProcessErr", new Object[] { this.m_db }) + dbMan.NEWLINE);
                    }
                }
            }
        }
        else {
            processCommandLineArguments = 1;
        }
        System.exit(processCommandLineArguments);
    }
    
    private IJAConfiguration getDBConfiguration(final IJADatabase ijaDatabase) {
        IJAConfiguration ijaConfiguration = null;
        try {
            IJAConfiguration ijaConfiguration2;
            for (Enumeration childrenObjects = ijaDatabase.getChildrenObjects(); ijaConfiguration == null && childrenObjects.hasMoreElements(); ijaConfiguration = ijaConfiguration2) {
                ijaConfiguration2 = childrenObjects.nextElement();
                String displayName = null;
                try {
                    displayName = ijaConfiguration2.getDisplayName();
                }
                catch (Throwable t) {}
                if (displayName.equalsIgnoreCase(this.m_config)) {}
            }
        }
        catch (Throwable t2) {}
        if (ijaConfiguration == null) {
            System.err.println(dbMan.NEWLINE + dbMan.m_ccResources.getTranString("ConfigErr", new Object[] { this.m_config }) + dbMan.NEWLINE);
        }
        return ijaConfiguration;
    }
    
    private void displayProcessStats(final IJADatabase ijaDatabase, final IJAConfiguration ijaConfiguration) throws RemoteException {
        final IAgentDatabase agentDatabase = (IAgentDatabase)ijaDatabase.getAgent();
        if (ijaConfiguration == null) {
            System.out.println(dbMan.m_ccResources.getTranString("NoDefaultConfig", new Object[0]));
            return;
        }
        final String str = (agentDatabase != null) ? agentDatabase.getStateDescriptor() : dbMan.m_ccResources.getTranString("Not running");
        final String s = (ijaConfiguration.getAIWriter() != null) ? ijaConfiguration.getAIWriter().getStateDescriptor() : dbMan.m_ccResources.getTranString("Not running");
        final String s2 = (ijaConfiguration.getAPWriter() != null) ? ijaConfiguration.getAPWriter().getStateDescriptor() : dbMan.m_ccResources.getTranString("Not running");
        final String s3 = (ijaConfiguration.getBIWriter() != null) ? ijaConfiguration.getBIWriter().getStateDescriptor() : dbMan.m_ccResources.getTranString("Not running");
        final String s4 = (ijaConfiguration.getWatchdog() != null) ? ijaConfiguration.getWatchdog().getStateDescriptor() : dbMan.m_ccResources.getTranString("Not running");
        System.out.println("Database <" + this.m_db + "> agent: " + str);
        System.out.println();
        System.out.println(dbMan.m_ccResources.getTranString("AuxStatus", new Object[] { this.m_db, s, s2, s3, s4 }));
        System.out.println();
    }
    
    private int startDatabase(final IJADatabase ijaDatabase) throws RemoteException {
        int n = 0;
        final IJAConfiguration ijaConfiguration = (this.m_config != null) ? this.getDBConfiguration(ijaDatabase) : ijaDatabase.getDefaultConfiguration();
        if (!ijaDatabase.isStartable()) {
            System.err.println(dbMan.NEWLINE + dbMan.m_ccResources.getTranString("DBRunErr", new Object[] { this.m_db, ijaDatabase.getCurrentConfiguration().getDisplayName() + "" }) + dbMan.NEWLINE);
            n = 1;
        }
        else if (ijaConfiguration == null) {
            System.err.println(dbMan.NEWLINE + dbMan.m_ccResources.getTranString("NoDefaultConfig") + dbMan.NEWLINE);
            n = 1;
        }
        else {
            try {
                String str = dbMan.m_ccResources.getTranString("DBStarting", new Object[] { this.m_db, ijaConfiguration.getDisplayName() });
                System.out.println(dbMan.NEWLINE + str);
                final DBStartedListener dbStartedListener = new DBStartedListener();
                synchronized (dbStartedListener) {
                    final IEventInterestObject expressInterest = this.m_eventBroker.expressInterest(EStartupProcessCompleted.class, dbStartedListener, (RemoteStub)ijaDatabase, this.m_eventStream);
                    final IEventInterestObject expressInterest2 = this.m_eventBroker.expressInterest(EPrimaryStartupFailed.class, dbStartedListener, (RemoteStub)ijaDatabase, this.m_eventStream);
                    final IEventInterestObject expressInterest3 = this.m_eventBroker.expressInterest(EConfigurationCrashEvent.class, dbStartedListener, (RemoteStub)ijaDatabase, this.m_eventStream);
                    ijaDatabase.setUser(this.m_user);
                    ijaConfiguration.start();
                    dbStartedListener.wait(this.m_timeout * 1000);
                    if ((ijaConfiguration.isStarting() || ijaConfiguration.isInitializing()) && dbStartedListener.isStarted()) {
                        dbStartedListener.wait(this.m_timeout * 1000);
                    }
                    expressInterest.revokeInterest();
                    expressInterest2.revokeInterest();
                    expressInterest3.revokeInterest();
                    if (dbStartedListener.isTimedout()) {
                        str = dbMan.m_ccResources.getTranString("DBStartTimeout", new Object[] { this.m_db, ijaConfiguration.getDisplayName() });
                        n = 1;
                    }
                    if (dbStartedListener.isStarted()) {
                        str = dbMan.m_ccResources.getTranString("DBStarted", new Object[] { this.m_db, ijaConfiguration.getDisplayName() });
                    }
                    else {
                        str = dbMan.m_ccResources.getTranString("DBStartErr", new Object[] { this.m_db, ijaConfiguration.getDisplayName() });
                        n = 1;
                    }
                }
                if (n == 1) {
                    System.err.println(str + dbMan.NEWLINE);
                }
                else {
                    System.out.println(str + dbMan.NEWLINE);
                    this.displayProcessStats(ijaDatabase, ijaConfiguration);
                }
            }
            catch (Throwable t) {
                n = 1;
            }
        }
        return n;
    }
    
    private int startAgent(final IJADatabase ijaDatabase) throws RemoteException {
        int n = 0;
        final IAgentDatabase agentDatabase = (IAgentDatabase)ijaDatabase.getAgent();
        ijaDatabase.getStatus();
        if (!agentDatabase.isStartable()) {
            System.err.println(dbMan.NEWLINE + ("Database agent " + this.m_db + " is already running") + dbMan.NEWLINE);
            n = 1;
        }
        else if (!ijaDatabase.getStatus().isRunning()) {
            System.err.println(dbMan.NEWLINE + "Database must be running before agent can be started" + dbMan.NEWLINE);
            n = 1;
        }
        else {
            try {
                String str = "Database agent " + this.m_db + " is starting...";
                System.out.println(dbMan.NEWLINE + str);
                final DBAgentStartedListener dbAgentStartedListener = new DBAgentStartedListener();
                synchronized (dbAgentStartedListener) {
                    final IEventInterestObject expressInterest = this.m_eventBroker.expressInterest(EDBAStartupCompleted.class, dbAgentStartedListener, (RemoteStub)agentDatabase, this.m_eventStream);
                    final IEventInterestObject expressInterest2 = this.m_eventBroker.expressInterest(EDBACrash.class, dbAgentStartedListener, (RemoteStub)agentDatabase, this.m_eventStream);
                    agentDatabase.start();
                    dbAgentStartedListener.wait(this.m_timeout * 1000);
                    expressInterest.revokeInterest();
                    expressInterest2.revokeInterest();
                    if (dbAgentStartedListener.isTimedout()) {
                        str = "Database agent " + this.m_db + " start timed out";
                        n = 1;
                    }
                    if (dbAgentStartedListener.isStarted()) {
                        str = "Database agent " + this.m_db + " is started";
                    }
                    else {
                        str = "Database agent " + this.m_db + " failed to start";
                        n = 1;
                    }
                }
                if (n == 1) {
                    System.err.println(str + dbMan.NEWLINE);
                }
                else {
                    System.out.println(str + dbMan.NEWLINE);
                }
            }
            catch (Throwable t) {
                n = 1;
            }
        }
        return n;
    }
    
    private int stopDatabase(final IJADatabase ijaDatabase, final IJAConfiguration ijaConfiguration) throws RemoteException {
        int n = 0;
        final JAStatusObject status = ijaDatabase.getStatus();
        final String displayName = ijaConfiguration.getDisplayName();
        if (!status.isStopable()) {
            System.err.println(dbMan.NEWLINE + dbMan.m_ccResources.getTranString("DBNotRunning", new Object[] { this.m_db }) + dbMan.NEWLINE);
            n = 1;
        }
        else if (this.m_config != null && !ijaConfiguration.getDisplayName().equalsIgnoreCase(this.m_config)) {
            System.err.println(dbMan.NEWLINE + dbMan.m_ccResources.getTranString("DBNotRunningConfig", new Object[] { this.m_db, this.m_config }) + dbMan.NEWLINE);
            n = 1;
        }
        else {
            try {
                String s = dbMan.m_ccResources.getTranString("DBStopping", new Object[] { this.m_db, displayName + "" });
                System.out.println(dbMan.NEWLINE + s);
                final DBIdleListener dbIdleListener = new DBIdleListener();
                synchronized (dbIdleListener) {
                    final IEventInterestObject expressInterest = this.m_eventBroker.expressInterest(EConfigurationStateChanged.class, dbIdleListener, (RemoteStub)ijaDatabase, this.m_eventStream);
                    final IEventInterestObject expressInterest2 = this.m_eventBroker.expressInterest(EConfigurationCrashEvent.class, dbIdleListener, (RemoteStub)ijaDatabase, this.m_eventStream);
                    ijaDatabase.setUser(this.m_user);
                    ijaConfiguration.stop();
                    dbIdleListener.wait(this.m_timeout * 1000);
                    expressInterest.revokeInterest();
                    expressInterest2.revokeInterest();
                    if (dbIdleListener.isIdle()) {
                        s = dbMan.m_ccResources.getTranString("DBStopped", new Object[] { this.m_db, displayName + "" });
                    }
                    else {
                        s = dbMan.m_ccResources.getTranString("DBStopTimeout", new Object[] { this.m_db, displayName + "" });
                        n = 1;
                    }
                }
                System.out.println(s + dbMan.NEWLINE);
                this.displayProcessStats(ijaDatabase, ijaConfiguration);
            }
            catch (Throwable t) {}
        }
        return n;
    }
    
    private int stopAgent(final IJADatabase ijaDatabase) throws RemoteException {
        int n = 0;
        final IAgentDatabase agentDatabase = (IAgentDatabase)ijaDatabase.getAgent();
        ijaDatabase.getStatus();
        if (!agentDatabase.isStopable()) {
            System.err.println(dbMan.NEWLINE + ("Database agent " + this.m_db + " is not running") + dbMan.NEWLINE);
            n = 1;
        }
        else {
            try {
                String s = "Database agent " + this.m_db + " is stopping...";
                System.out.println(dbMan.NEWLINE + s);
                final DBAgentIdleListener dbAgentIdleListener = new DBAgentIdleListener();
                synchronized (dbAgentIdleListener) {
                    final IEventInterestObject expressInterest = this.m_eventBroker.expressInterest(EDBAStateChanged.class, dbAgentIdleListener, (RemoteStub)agentDatabase, this.m_eventStream);
                    final IEventInterestObject expressInterest2 = this.m_eventBroker.expressInterest(EDBACrash.class, dbAgentIdleListener, (RemoteStub)agentDatabase, this.m_eventStream);
                    agentDatabase.stop();
                    dbAgentIdleListener.wait(this.m_timeout * 1000);
                    expressInterest.revokeInterest();
                    expressInterest2.revokeInterest();
                    if (dbAgentIdleListener.isIdle()) {
                        s = "Database agent " + this.m_db + " is stopped";
                    }
                    else {
                        s = "Database agent " + this.m_db + " stop timed out";
                        n = 1;
                    }
                }
                System.out.println(s + dbMan.NEWLINE);
            }
            catch (Throwable t) {}
        }
        return n;
    }
    
    private int queryDatabase(final IJADatabase ijaDatabase, final IJAConfiguration ijaConfiguration) throws RemoteException {
        int n = 0;
        final JAStatusObject status = ijaDatabase.getStatus();
        String s = "";
        if (this.m_brief) {
            if (this.m_isAgent) {
                System.out.println(this.m_db + " agent: \"" + ((IAgentDatabase)ijaDatabase.getAgent()).getStateDescriptor() + "\"");
            }
            else {
                System.out.println(this.m_db + " : \"" + status.stateDescriptor() + "\"");
            }
        }
        else {
            try {
                final Object o = null;
                final Enumeration childrenObjects = ijaDatabase.getChildrenObjects();
                while (o == null && childrenObjects.hasMoreElements()) {
                    final IJAConfiguration ijaConfiguration2 = childrenObjects.nextElement();
                    try {
                        if (s.length() > 0) {
                            s += ", ";
                        }
                        s += ijaConfiguration2.getDisplayName();
                    }
                    catch (Throwable t) {
                        n = 1;
                    }
                }
                System.out.println(dbMan.NEWLINE + dbMan.m_ccResources.getTranString("QUERY", new Object[] { this.m_db, status.stateDescriptor(), ijaDatabase.getDefaultConfiguration().getDisplayName() + "", ijaConfiguration.getDisplayName() + "", s }) + dbMan.NEWLINE);
                this.displayProcessStats(ijaDatabase, ijaConfiguration);
            }
            catch (Throwable t2) {
                n = 1;
            }
        }
        return n;
    }
    
    private int executeDBCommand(final IJADatabase ijaDatabase) {
        int n = 0;
        try {
            this.m_db = ijaDatabase.getDisplayName();
            final IJAConfiguration ijaConfiguration = (ijaDatabase.getCurrentConfiguration() == null) ? ijaDatabase.getDefaultConfiguration() : ijaDatabase.getCurrentConfiguration();
            if (this.m_start) {
                if (this.m_isAgent) {
                    n = this.startAgent(ijaDatabase);
                }
                else {
                    n = this.startDatabase(ijaDatabase);
                }
            }
            else if (this.m_stop) {
                if (this.m_isAgent) {
                    n = this.stopAgent(ijaDatabase);
                }
                else {
                    n = this.stopDatabase(ijaDatabase, ijaConfiguration);
                }
            }
            else if (this.m_query) {
                n = this.queryDatabase(ijaDatabase, ijaConfiguration);
            }
            if (this.m_vst != " ") {
                n |= this.queryVst(ijaDatabase, this.m_vst.toLowerCase());
            }
        }
        catch (RemoteException ex) {
            this.printNoDBInfo();
            n = 1;
        }
        return n;
    }
    
    private Vector getDatabase(final IJAPlugIn ijaPlugIn) {
        final Vector<IJADatabase> vector = new Vector<IJADatabase>();
        if (ijaPlugIn != null) {
            try {
                final Enumeration childrenObjects = ijaPlugIn.getChildrenObjects();
                while (childrenObjects.hasMoreElements()) {
                    final IJADatabase ijaDatabase = childrenObjects.nextElement();
                    try {
                        if (this.m_processAllDB) {
                            vector.addElement(ijaDatabase);
                        }
                        else {
                            if (ijaDatabase.getDisplayName().equalsIgnoreCase(this.m_db)) {
                                vector.addElement(ijaDatabase);
                                break;
                            }
                            continue;
                        }
                    }
                    catch (RemoteException ex) {}
                }
            }
            catch (RemoteException ex2) {}
        }
        return vector;
    }
    
    private IJAPlugIn getJuniperPlugin() {
        IJAPlugIn ijaPlugIn = null;
        IAdminServerConnection adminServerConnection = null;
        if (this.m_user == null || this.m_pwd == null) {
            return null;
        }
        if (this.m_regMan == null) {
            Integer value = null;
            if (this.m_port != null) {
                value = Integer.valueOf(this.m_port);
            }
            this.m_regMan = new RegistryManager(null, this.m_host, value);
        }
        if (this.m_regMan != null) {
            try {
                adminServerConnection = (IAdminServerConnection)this.m_regMan.lookup("Chimera");
                final IAdministrationServer administrationServer = (IAdministrationServer)adminServerConnection;
                this.m_eventBroker = administrationServer.getEventBroker();
                this.m_eventStream = administrationServer.getEventStream();
            }
            catch (Throwable t) {}
            if (adminServerConnection != null) {
                try {
                    final crypto crypto = new crypto();
                    final IAdminServer connect = adminServerConnection.connect(crypto.encrypt(this.m_user), crypto.encrypt(this.m_pwd));
                    if (connect == null) {
                        System.err.println(dbMan.NEWLINE + dbMan.m_ccResources.getTranString("UserAuthErr", new Object[] { this.m_user }) + dbMan.NEWLINE);
                        return ijaPlugIn;
                    }
                    final Vector plugins = connect.getPlugins("com.progress.juniper.admin.JAPlugIn");
                    if (plugins != null && plugins.size() == 1 && plugins.elementAt(0) instanceof IJAPlugIn) {
                        ijaPlugIn = plugins.elementAt(0);
                    }
                }
                catch (Throwable t2) {
                    ijaPlugIn = null;
                }
            }
        }
        if (ijaPlugIn == null) {
            this.printJuniperPluginNotFound();
        }
        return ijaPlugIn;
    }
    
    private String[] promptForUP(final String s) {
        final acctAuthenticate acctAuthenticate = new acctAuthenticate();
        final byte[] array = new byte[50];
        final String[] array2 = new String[2];
        if (s == null) {
            System.out.print(dbMan.m_ccResources.getTranString("EnterUserNamePrompt"));
            try {
                System.in.read(array);
                array2[0] = new String(array).trim();
            }
            catch (Exception ex) {
                System.err.println(dbMan.m_ccResources.getTranString("InputError", new Object[] { ex.getMessage() }));
            }
        }
        else {
            array2[0] = s;
        }
        final String tranString = dbMan.m_ccResources.getTranString("PasswordPrompt", new Object[] { array2[0] });
        try {
            array2[1] = acctAuthenticate.passwdPromptJNI(tranString).trim();
        }
        catch (Exception ex2) {
            System.err.println(dbMan.m_ccResources.getTranString("InputError", new Object[] { ex2.getMessage() }));
        }
        return array2;
    }
    
    private int processCommandLineArguments(final String[] array) {
        final Getopt.GetoptList[] array2 = { new Getopt.GetoptList("database:", 160), new Getopt.GetoptList("db:", 160), new Getopt.GetoptList("agent:", 540), new Getopt.GetoptList("config:", 170), new Getopt.GetoptList("host:", 90), new Getopt.GetoptList("H:", 90), new Getopt.GetoptList("port:", 100), new Getopt.GetoptList("user:", 60), new Getopt.GetoptList("u:", 60), new Getopt.GetoptList("p:", 220), new Getopt.GetoptList("password:", 220), new Getopt.GetoptList("help", 40), new Getopt.GetoptList("start", 20), new Getopt.GetoptList("stop", 30), new Getopt.GetoptList("query", 10), new Getopt.GetoptList("timeout:", 190), new Getopt.GetoptList("brief", 200), new Getopt.GetoptList("verbose", 230), new Getopt.GetoptList("all", 210), new Getopt.GetoptList("vst:", 550), new Getopt.GetoptList("", 0) };
        final Getopt getopt = new Getopt(array);
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        Object optArg = null;
        String optArg2 = null;
        int opt;
        while ((opt = getopt.getOpt(array2)) != -1 && n == 0) {
            switch (opt) {
                case 40: {
                    this.m_help = true;
                    n = 0;
                    continue;
                }
                case 10: {
                    n2 = 10;
                    continue;
                }
                case 20: {
                    n2 = 20;
                    continue;
                }
                case 210: {
                    this.m_processAllDB = true;
                    this.m_db = " ";
                    continue;
                }
                case 550: {
                    n2 = 550;
                    this.m_vst = getopt.getOptArg();
                    continue;
                }
                case 30: {
                    n2 = 30;
                    continue;
                }
                case 200: {
                    this.m_brief = true;
                    continue;
                }
                case 60: {
                    this.m_user = getopt.getOptArg();
                    continue;
                }
                case 220: {
                    this.m_pwd = getopt.getOptArg();
                    continue;
                }
                case 90: {
                    this.m_host = getopt.getOptArg();
                    this.m_isRemote = this.isRemoteSystem(this.m_host);
                    continue;
                }
                case 100: {
                    this.m_port = getopt.getOptArg();
                    continue;
                }
                case 540: {
                    this.m_isAgent = true;
                }
                case 160: {
                    this.m_db = getopt.getOptArg();
                    this.m_processAllDB = false;
                    continue;
                }
                case 190: {
                    optArg2 = getopt.getOptArg();
                    continue;
                }
                case 170: {
                    this.m_config = getopt.getOptArg();
                    continue;
                }
                case 63: {
                    if (this.m_db == " " && !getopt.getOptArg().startsWith("-")) {
                        this.m_db = getopt.getOptArg();
                        continue;
                    }
                    if (n3 == 0) {
                        n3 = 1;
                        optArg = getopt.getOptArg();
                        continue;
                    }
                    continue;
                }
            }
        }
        if (n == 0) {
            switch (n2) {
                case 20: {
                    this.m_start = true;
                    break;
                }
                case 30: {
                    this.m_stop = true;
                    break;
                }
                case 10: {
                    this.m_query = true;
                    break;
                }
                case 550: {
                    break;
                }
                default: {
                    if (!this.m_help) {
                        this.printNoOperationError();
                        n = 1;
                        break;
                    }
                    break;
                }
            }
        }
        if (!this.m_help) {
            if (n3 != 0) {
                System.err.println(dbMan.NEWLINE + dbMan.m_ccResources.getTranString("InvOpt", new Object[] { optArg }) + dbMan.NEWLINE);
                n = 1;
            }
            if (this.m_db == " " && !this.m_processAllDB && n == 0) {
                n = 1;
                this.printNoDB();
            }
            else if (n == 0) {
                if (this.m_port != null) {
                    try {
                        this.m_port = new Port(this.m_port).getPortInt() + "";
                    }
                    catch (Port.PortException ex) {
                        System.err.println(dbMan.NEWLINE + dbMan.m_ccResources.getTranString("InvPortOpt", new Object[] { this.m_port, String.valueOf(1), String.valueOf(65535) }) + dbMan.NEWLINE);
                        n = 1;
                    }
                }
                if (optArg2 != null && n == 0) {
                    try {
                        this.m_timeout = new Integer(optArg2);
                    }
                    catch (NumberFormatException ex2) {
                        System.err.println(dbMan.NEWLINE + dbMan.m_ccResources.getTranString("InvTimeoutOpt", new Object[] { optArg2 }) + dbMan.NEWLINE);
                        n = 1;
                    }
                }
            }
        }
        return n;
    }
    
    private int queryVst(final IJADatabase ijaDatabase, final String s) {
        final String string = "Cannot query vst table " + s + ".";
        IJAExecutableObject agent = null;
        if (ijaDatabase == null) {
            System.err.println(string + "  Unknown database.");
            return 1;
        }
        try {
            agent = ijaDatabase.getAgent();
        }
        catch (RemoteException ex2) {}
        if (agent == null) {
            System.err.println(string + "  Unknown database agent.");
            return 1;
        }
        boolean running = false;
        boolean running2 = false;
        try {
            running = ijaDatabase.isRunning();
            running2 = agent.isRunning();
        }
        catch (RemoteException ex3) {}
        if (!running2) {
            if (!running) {
                System.err.println(string + "  Database broker and agent must be running.");
            }
            else {
                System.err.println(string + "  Database agent must be running.");
            }
            return 1;
        }
        int n2;
        try {
            final IJARmiComponent jaRmiComponent = ijaDatabase.getPlugIn().getJARmiComponent();
            final String displayName = ijaDatabase.getDisplayName();
            final Object[] array = jaRmiComponent.getStatistics(displayName, new String[] { s }).get(s);
            final Object[] tableSchema = jaRmiComponent.getTableSchema(displayName, s);
            final int n = (array != null && array.length > 0) ? ((String[])array[0]).length : 0;
            if (n == 0) {
                for (int i = 0; i < tableSchema.length; ++i) {
                    System.out.println((String)((Hashtable)tableSchema[i]).get("columnName") + "[0] = N/A");
                }
                System.out.println();
            }
            else {
                for (int j = 0; j < n; ++j) {
                    for (int k = 0; k < tableSchema.length; ++k) {
                        System.out.println((String)((Hashtable)tableSchema[k]).get("columnName") + "[" + j + "] = " + ((String[])array[k])[j]);
                    }
                    System.out.println();
                }
            }
            n2 = 0;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            n2 = 1;
        }
        return n2;
    }
    
    public boolean isRemoteSystem(final String s) {
        return !NetInfo.isLocalHost(s);
    }
    
    private void printGeneralUsage() {
        final String string = Integer.toString(20931);
        System.out.println("Usage: dbman [-start|-query|-stop] [-help]  [-all|[-database|-db]] [options]");
        System.out.println("");
        System.out.println("-start                     Starts database configuration(s).");
        System.out.println("-query                     Queries database configuration(s).");
        System.out.println("-stop                      Stops database configuration(s).");
        System.out.println("");
        System.out.println("-database|db <db name>     Logical name of database to start/stop/query.");
        System.out.println("-agent <db name>           Logical name of database agent to start/stop/query.");
        System.out.println("-all                       Switch indicating to perform action on all databases.");
        System.out.println("Other Options:");
        System.out.println("-help                      Prints this message");
        System.out.println("-port <port>               Port where AdminServer Process resides.");
        System.out.println("                           Default is " + string + ".");
        System.out.println("-config <config name>      Configuration name to start/stop/query.");
        System.out.println("-timeout <time in sec>     Time (in seconds) for dbMan to wait for a response.");
        System.out.println("-host|H <hostname>         Host name where the Adminserver process resides.");
        System.out.println("-user|u <username>         User name on machine where the AdminServer process ");
        System.out.println("                           resides.");
        System.out.println("-password|p <password>    *The password associated with the username specified.");
        System.out.println("");
        System.out.println("* Note: use of this parameter may allow others to see your password in clear ");
        System.out.println("     text.");
        System.out.println("");
        System.out.println("Type \"dbman -[start|query|stop] -help\" for more information on dbman options.");
        System.out.println("");
    }
    
    private void printStartUsage() {
        final String string = Integer.toString(20931);
        System.out.println();
        System.out.println("Usage: dbman -start [options]");
        System.out.println("");
        System.out.println("One of the following options is required:");
        System.out.println("-database|db <db name>      Logical name of database to start.");
        System.out.println("-agent <db name>            Logical name of database agent to start.");
        System.out.println("-all                        Perform action on all databases.");
        System.out.println("");
        System.out.println("Other options:");
        System.out.println("");
        System.out.println("-help                       Print this message.");
        System.out.println("-port <port>                Port where AdminServer Process resides.");
        System.out.println("                            Default is " + string + ".");
        System.out.println("-config <config name>       Configuration name to start/stop/query.");
        System.out.println("-timeout <time in sec>      Time (in seconds) for dbMan to wait for a response.");
        System.out.println("-host|H <hostname>          Host name where the Adminserver process resides.");
        System.out.println("-user|u <username>          User name on machine where the AdminServer process");
        System.out.println("                            resides.");
        System.out.println("-password|p <password>     *The password associated with the username specified.");
        System.out.println("");
        System.out.println("* Note: use of this parameter may allow others to see your password in clear");
        System.out.println("        text.");
        System.out.println("");
    }
    
    private void printStopUsage() {
        Integer.toString(20931);
        System.out.println("Usage: dbman -stop [options]");
        System.out.println("");
        System.out.println("One of the following options is required:");
        System.out.println("");
        System.out.println("-database|db <db name>       Logical name of database to stop.");
        System.out.println("-agent <db name>             Logical name of database agent to stop.");
        System.out.println("-all                         Perform action on all databases.");
        System.out.println("");
        System.out.println("");
        System.out.println("Other options:");
        System.out.println("");
        System.out.println("-help                        Print this message");
        System.out.println("-port <port>                 Port where AdminServer Process resides");
        System.out.println("                             Default is <AdminServerGetopt.MIN_PORT_VALUE>");
        System.out.println("-config <config name>        Configuration name to start/stop/query.");
        System.out.println("-timeout <time in sec>       Time (in seconds) for dbMan to wait for a response");
        System.out.println("-host|H <hostname>           Host name where the Adminserver process resides.");
        System.out.println("-user|u <username>           User name on machine where the AdminServer process resides.");
        System.out.println("-password|p <password>      *The password associated with the username specified.");
        System.out.println("");
        System.out.println("* Note: use of this parameter may allow others to see your password in clear text.");
    }
    
    private void printQueryUsage() {
        final String string = Integer.toString(20931);
        System.out.println("Usage: dbman -query [options]");
        System.out.println("");
        System.out.println("One of the following options is required:");
        System.out.println("-database|db <db name>      Logical name of database to query.");
        System.out.println("-agent <db name>            Logical name of database agent to query.");
        System.out.println("-all                        Perform action on all databases.");
        System.out.println("");
        System.out.println("");
        System.out.println("Other options:");
        System.out.println("");
        System.out.println("-help                       Print this message.");
        System.out.println("-brief                      List a shortened status of the database(s).");
        System.out.println("-port <port>                Port where AdminServer Process resides.");
        System.out.println("                            Default is " + string + ".");
        System.out.println("-config <config name>       Configuration name to start/stop/query.");
        System.out.println("-timeout <time in sec>      Time (in seconds) for dbMan to wait for a response.");
        System.out.println("-host|H <hostname>          Host name where the Adminserver process resides.");
        System.out.println("-user|u <username>          User name on machine where the AdminServer process ");
        System.out.println("                            resides.");
        System.out.println("-password|p <password>     *The password associated with the username specified.");
        System.out.println("");
        System.out.println("* Note: use of this parameter may allow others to see your password in clear ");
        System.out.println("        text.");
    }
    
    private void printUsage() {
        if (this.m_start) {
            this.printStartUsage();
        }
        else if (this.m_stop) {
            this.printStopUsage();
        }
        else if (this.m_query) {
            this.printQueryUsage();
        }
        else {
            this.printGeneralUsage();
        }
    }
    
    private void printNoOperationError() {
        System.err.println(dbMan.NEWLINE + dbMan.m_ccResources.getTranString("NoOpError"));
    }
    
    private void printNoDB() {
        System.err.println(dbMan.NEWLINE + dbMan.m_ccResources.getTranString("NoDatabaseError"));
    }
    
    private void printJuniperPluginNotFound() {
        System.err.println(dbMan.NEWLINE + dbMan.m_ccResources.getTranString("NoPluginFound") + dbMan.NEWLINE);
    }
    
    private void printDatabaseNotFound() {
        System.err.println(dbMan.NEWLINE + dbMan.m_ccResources.getTranString("DBNotFound", new Object[] { this.m_db }) + dbMan.NEWLINE);
    }
    
    private void printNoDBInfo() {
        System.err.println(dbMan.NEWLINE + dbMan.m_ccResources.getTranString("NoDBInfo", new Object[] { this.m_db }) + dbMan.NEWLINE);
    }
    
    private void printNoHostInfo(final String s) {
        System.err.println(dbMan.NEWLINE + dbMan.m_ccResources.getTranString("NoHostInfo", new Object[] { this.m_db }) + dbMan.NEWLINE);
    }
    
    private void printValues() {
        dbMan.m_ccResources.getTranString("OptionValues", new Object[] { new Boolean(this.m_start), new Boolean(this.m_stop), new Boolean(this.m_query), this.m_host, this.m_port, this.m_user, this.m_pwd, this.m_db, this.m_config });
    }
    
    private void jbInit() throws Exception {
    }
    
    static {
        dbMan.m_ccResources = null;
        NEWLINE = System.getProperty("line.separator");
        dbMan.m_ccResources = (ProgressResources)ResourceBundle.getBundle("com.progress.international.messages.DBManBundle");
    }
}
