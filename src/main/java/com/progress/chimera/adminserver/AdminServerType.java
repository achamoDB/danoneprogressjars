// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera.adminserver;

import java.util.Hashtable;
import java.util.ResourceBundle;
import com.progress.chimera.log.AdminServerLog;
import java.util.StringTokenizer;
import com.progress.common.util.Environment;
import com.progress.common.log.LogSystem;
import com.progress.mf.runtime.ManagementProperties;
import com.progress.common.log.ProLog;
import com.progress.common.util.crypto;
import com.progress.common.util.acctAuthenticate;
import com.progress.common.util.NetInfo;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;
import java.rmi.AccessException;
import java.rmi.registry.LocateRegistry;
import java.net.Socket;
import com.progress.common.comsock.ServerComSock;
import java.util.Iterator;
import java.util.Enumeration;
import java.util.Properties;
import com.progress.common.util.PropertyFilter;
import com.progress.common.networkevents.EventBroker;
import com.progress.common.util.Getopt;
import java.util.Vector;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.File;
import com.progress.chimera.util.Const;
import com.progress.common.property.PropertyManager;
import com.progress.international.resources.ProgressResources;
import com.progress.common.log.Subsystem;
import com.progress.message.asMsg;

public class AdminServerType implements IAdminServerConst, asMsg
{
    static final int OPT_START = 0;
    static final int OPT_STOP = 1;
    static final int OPT_QUERY = 2;
    static final int OPT_F = 3;
    static final int OPT_HOST = 4;
    static final int OPT_H = 5;
    static final int OPT_IPVER = 6;
    static final int OPT_FATHOM_INIT = 7;
    static final int UNKOPT = 63;
    static final String DLC;
    static final String JVM_EXE;
    public static final String NEWLINE;
    public static Subsystem m_adminServerSubsystem;
    public static ProgressResources admBundle;
    public static ProgressResources cmnBundle;
    public static String DB_PROPERTIES_FILE;
    public static String DB_PROPERTIES_FULLFILE;
    public static String DBA_PROPERTIES_FULLFILE;
    public static String FATHOM_PROPERTIES_FULLFILE;
    public static String FATHOM_INIT_PARAMS;
    public static String SMDB_PROPERTIES_FULLFILE;
    public static String UB_PROPERTIES_FULLFILE;
    public static String MGMT_PROPERTIES_FULLFILE;
    public static int DB_ADMIN_PORT;
    static final String[] m_keys;
    static final String[] m_defaultPropArray;
    public static final int START = 1;
    public static final int STOP = 2;
    public static final int QUERY = 3;
    public static final int F = 4;
    int m_function;
    String m_serverName;
    private String[] m_serverCmd;
    private String[] m_serverArgs;
    private static PropertyManager m_propertyManager;
    private static String m_fathomClasspath;
    public static Boolean m_isFathomEnabled;
    public static String m_fathomInstallDir;
    public static String m_fathomConfigDir;
    
    public String usageString() {
        return AdminServer.usage() + Const.NEWLINE + Const.NEWLINE + AdminServerQuery.usage() + Const.NEWLINE + Const.NEWLINE + AdminServerShutdown.usage();
    }
    
    public String[] getServerCmdAndArgs() {
        return this.m_serverCmd;
    }
    
    public String[] getServerArgs() {
        return this.m_serverArgs;
    }
    
    public String getServerName() {
        return this.m_serverName;
    }
    
    public boolean isStart() {
        return this.m_function == 1;
    }
    
    public boolean isStop() {
        return this.m_function == 2;
    }
    
    public boolean isQuery() {
        return this.m_function == 3;
    }
    
    public static String getFathomInitParams() {
        String s = System.getProperty("fathom.init.params");
        if (s == null) {
            s = AdminServerType.FATHOM_INIT_PARAMS;
        }
        return s;
    }
    
    private static boolean copyFile(final String s, final String str) {
        boolean b = false;
        try {
            final File file = new File(s);
            final File file2 = new File(str);
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            final BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file2)));
            for (String str2 = bufferedReader.readLine(); str2 != null; str2 = bufferedReader.readLine()) {
                bufferedWriter.write(str2);
            }
            bufferedReader.close();
            bufferedWriter.close();
            b = true;
        }
        catch (FileNotFoundException ex) {
            System.out.println("Cannot copy " + s + " to " + str + ";");
            System.out.println(s + " not found: " + ex.getMessage());
        }
        catch (IOException ex2) {
            System.out.println("Cannot copy " + s + " to " + str + ";");
            System.out.println("Error: " + ex2.getMessage());
        }
        return b;
    }
    
    public AdminServerType(final String[] array) {
        this.m_function = 0;
        this.m_serverName = "com.progress.chimera.adminserver.AdminServerStarter";
        this.m_serverCmd = null;
        this.m_serverArgs = null;
        String obj = IAdminServerConst.INSTALL_DIR + IAdminServerConst.FILE_SEPARATOR + "properties" + IAdminServerConst.FILE_SEPARATOR + "AdminServerPlugins.properties";
        final Vector vector = new Vector<String>();
        final Vector vector2 = new Vector<String>();
        final Getopt.GetoptList[] array2 = { new Getopt.GetoptList(AdminServerType.m_keys[0], 0), new Getopt.GetoptList(AdminServerType.m_keys[1], 1), new Getopt.GetoptList(AdminServerType.m_keys[2], 2), new Getopt.GetoptList(AdminServerType.m_keys[3] + ":", 3), new Getopt.GetoptList(AdminServerType.m_keys[4] + ":", 4), new Getopt.GetoptList(AdminServerType.m_keys[5] + ":", 5), new Getopt.GetoptList(AdminServerType.m_keys[6] + ":", 6), new Getopt.GetoptList(AdminServerType.m_keys[7] + ":", 7), new Getopt.GetoptList("", 0) };
        final Getopt getopt = new Getopt(array);
        getopt.setIgnoreCase(true);
        int opt;
        while ((opt = getopt.getOpt(array2)) != -1) {
            switch (opt) {
                case 0: {
                    this.m_function = 1;
                    this.m_serverName = "com.progress.chimera.adminserver.AdminServerStarter";
                    continue;
                }
                case 1: {
                    this.m_function = 2;
                    this.m_serverName = "com.progress.chimera.adminserver.AdminServerShutdown";
                    continue;
                }
                case 2: {
                    this.m_function = 3;
                    this.m_serverName = "com.progress.chimera.adminserver.AdminServerQuery";
                    continue;
                }
                case 3: {
                    obj = getopt.getOptArg();
                    vector.addElement("-f");
                    vector.addElement(obj);
                    continue;
                }
                case 7: {
                    final String optArg = getopt.getOptArg();
                    vector.addElement("-fathominitparams");
                    vector.addElement(optArg);
                    System.setProperty("fathom.init.params", optArg);
                    continue;
                }
                case 4:
                case 5: {
                    final String optArg2 = getopt.getOptArg();
                    try {
                        final Properties properties = System.getProperties();
                        ((Hashtable<String, String>)properties).put("java.rmi.server.hostname", optArg2);
                        System.setProperties(properties);
                    }
                    catch (Exception ex2) {}
                    continue;
                }
                case 6: {
                    final String optArg3 = getopt.getOptArg();
                    if (optArg3.equalsIgnoreCase("IPv6")) {
                        System.setProperty("java.net.preferIPv4Stack", "false");
                        System.setProperty("java.net.preferIPv6Addresses", "true");
                        System.setProperty("java.net.preferIPv4Addresses", "false");
                        continue;
                    }
                    if (optArg3.equalsIgnoreCase("IPv4")) {
                        System.setProperty("java.net.preferIPv4Stack", "false");
                        System.setProperty("java.net.preferIPv4Addresses", "true");
                        System.setProperty("java.net.preferIPv6Addresses", "false");
                        continue;
                    }
                    continue;
                }
                case 63: {
                    vector.addElement(array[getopt.getOptInd()]);
                    continue;
                }
            }
        }
        String s = "";
        String property = "";
        String property2 = "";
        String property3 = "";
        String str = "4999";
        try {
            final String s2 = "PluginPolicy.Progress.AdminServer";
            (AdminServerType.m_propertyManager = new PropertyManager(null, false)).setGetPropertyFilter(new PropertyFilter(AdminServerType.m_propertyManager));
            AdminServerType.m_propertyManager.load(obj, true);
            AdminServerType.m_propertyManager.setCurrentGroup(s2);
            final String property4 = AdminServerType.m_propertyManager.getProperty("jvmDebugPort");
            if (property4 != null && property4.length() > 0) {
                str = property4;
            }
            final String property5 = AdminServerType.m_propertyManager.getProperty("port");
            if (property5 != null && property5.length() > 0) {
                vector2.addElement("-port");
                vector2.addElement(property5);
            }
            final String property6 = AdminServerType.m_propertyManager.getProperty("adminport");
            if (property6 != null && property6.length() > 0) {
                vector2.addElement("-adminport");
                vector2.addElement(property6);
            }
            s = AdminServerType.m_propertyManager.getProperty("classpath");
            if (null == s || 0 == s.length()) {
                System.err.println("Error: Classpath missing for " + s2 + ".");
                System.exit(1);
            }
            property = AdminServerType.m_propertyManager.getProperty("jvmargs");
            if (null == property) {
                property = "";
            }
            property2 = AdminServerType.m_propertyManager.getProperty("policyfile");
            if (null == property2) {
                property2 = "";
            }
            if (null == AdminServerType.m_propertyManager.getProperty("authpolicy")) {}
            if (null == AdminServerType.m_propertyManager.getProperty("loginconfig")) {}
            AdminServerType.m_propertyManager.setCurrentGroup("PluginPolicy.Fathom");
            property3 = AdminServerType.m_propertyManager.getProperty("classpath");
            AdminServerType.m_propertyManager.getProperty("jvmargs");
        }
        catch (Exception ex) {
            if (ex.getMessage() == null) {
                System.err.println(AdminServerType.admBundle.getTranString("Error getting default configuration:", new Object[] { obj }));
            }
            else {
                System.err.println(AdminServerType.admBundle.getTranString("Error getting default configuration:", new Object[] { ex.getMessage() }));
            }
            System.exit(1);
        }
        this.m_serverArgs = new String[vector.size() + vector2.size()];
        final int size = vector2.size();
        for (int i = 0; i < vector2.size(); ++i) {
            this.m_serverArgs[i] = vector2.elementAt(i);
        }
        for (int j = 0; j < vector.size(); ++j) {
            this.m_serverArgs[j + size] = vector.elementAt(j);
        }
        final char char1 = IAdminServerConst.FILE_SEPARATOR.charAt(0);
        final Vector vector3 = new Vector<String>();
        String obj2 = System.getProperty("admsrv.jvm");
        if (obj2 == null) {
            obj2 = AdminServerType.JVM_EXE.replace('\\', char1);
        }
        vector3.addElement(obj2);
        if (property2.length() > 0) {
            System.setProperty("java.security.policy", property2.replace('/', char1));
        }
        final Vector vector4 = new Vector<String>(50);
        for (int k = 0; k < AdminServerType.m_defaultPropArray.length; ++k) {
            vector4.addElement(AdminServerType.m_defaultPropArray[k]);
        }
        final boolean mfEnabled = isMFEnabled();
        final boolean dsEnabled = isDSEnabled();
        String str2 = null;
        String string = null;
        String str3 = System.getProperty("SONICMQ_HOME");
        if (mfEnabled) {
            System.setProperty("sonicsw.mf.commonMFVersion", "true");
            vector3.addElement("-Dsonicsw.mf.commonMFVersion=true");
            if (null != property3 && 0 < property3.length()) {
                if (s.endsWith(",")) {
                    s += property3;
                }
                else {
                    s = s + "," + property3;
                }
            }
            if (str3 == null || str3.length() < 1) {
                if (dsEnabled) {
                    str3 = getFathomInstallDir() + IAdminServerConst.FILE_SEPARATOR + "MQ6.1";
                }
                else {
                    str3 = System.getProperty("Install.Dir");
                    str2 = str3 + IAdminServerConst.FILE_SEPARATOR + "java" + IAdminServerConst.FILE_SEPARATOR + "ext" + IAdminServerConst.FILE_SEPARATOR;
                }
            }
            if (str2 == null) {
                str2 = str3 + IAdminServerConst.FILE_SEPARATOR + "lib" + IAdminServerConst.FILE_SEPARATOR;
            }
            String[] array3 = IAdminServerConst.SONIC_CLIENT_JARS;
            if (dsEnabled) {
                array3 = IAdminServerConst.SONIC_JARS;
            }
            final StringBuffer sb = new StringBuffer(256);
            for (int l = 0; l < array3.length; ++l) {
                sb.append(str2 + array3[l] + IAdminServerConst.PATH_SEPARATOR);
            }
            string = sb.toString();
        }
        String str4 = s.replace(',', IAdminServerConst.PATH_SEPARATOR.charAt(0));
        if (dsEnabled && string != null) {
            if (str4.endsWith(IAdminServerConst.PATH_SEPARATOR)) {
                str4 += string;
            }
            else {
                str4 = str4 + IAdminServerConst.PATH_SEPARATOR + string;
            }
        }
        vector3.addElement("-Djava.class.path=" + str4);
        if (Boolean.getBoolean("debugJVM")) {
            vector3.addElement("-Xdebug");
            vector3.addElement("-Xnoagent");
            vector3.addElement("-Djava.compiler=NONE");
            vector3.addElement("-Xrunjdwp:transport=\"dt_socket,server=y,address=" + str + ",suspend=y\"");
            vector3.addElement("-Dsonicsw.mf.qa=true");
        }
        final Properties properties2 = System.getProperties();
        final Enumeration<?> propertyNames = properties2.propertyNames();
        while (propertyNames.hasMoreElements()) {
            final String o = (String)propertyNames.nextElement();
            final String string2 = "-D" + o + "=" + properties2.getProperty(o);
            if (!vector4.contains(o)) {
                vector3.addElement(string2);
            }
        }
        if (mfEnabled) {
            if (System.getProperty("SONICMQ_HOME") == null) {
                vector3.addElement("-DSONICMQ_HOME=" + str3);
            }
            String obj3 = System.getProperty("SONICMQ_XBOOTPATH");
            if (obj3 == null || obj3.length() < 1) {
                final String string3 = System.getProperty("Install.Dir") + IAdminServerConst.FILE_SEPARATOR + "java" + IAdminServerConst.FILE_SEPARATOR + "ext" + IAdminServerConst.FILE_SEPARATOR;
                obj3 = "-Xbootclasspath/p:" + (string3 + "jmxri.jar" + IAdminServerConst.PATH_SEPARATOR + string3 + "xercesImpl.jar" + IAdminServerConst.PATH_SEPARATOR + string3 + "xmlParserAPIs.jar");
            }
            vector3.addElement(obj3);
            vector3.addElement("-Dsonicsw.mf.containerClass=" + this.m_serverName);
            this.m_serverName = "com.sonicsw.mf.Agent";
        }
        final Iterator iterator = this.getJvmArgs(property).iterator();
        while (iterator.hasNext()) {
            vector3.addElement(iterator.next());
        }
        vector3.addElement(this.m_serverName);
        for (int index = 0; index < vector2.size(); ++index) {
            vector3.addElement(vector2.elementAt(index));
        }
        for (int index2 = 0; index2 < vector.size(); ++index2) {
            vector3.addElement(vector.elementAt(index2));
        }
        this.m_serverCmd = new String[vector3.size()];
        for (int index3 = 0; index3 < vector3.size(); ++index3) {
            this.m_serverCmd[index3] = vector3.elementAt(index3);
        }
    }
    
    static boolean isPortInUse(final int port) {
        boolean b = false;
        if (!System.getProperty("os.name").equalsIgnoreCase("SunOS")) {
            try {
                final ServerComSock serverComSock = new ServerComSock(port);
                if (serverComSock != null) {
                    serverComSock.connect();
                    serverComSock.disconnect();
                    while (serverComSock != null && serverComSock.isAlive()) {
                        try {
                            Thread.sleep(1000L);
                        }
                        catch (Throwable t) {}
                    }
                }
            }
            catch (Exception ex) {
                b = true;
            }
        }
        else {
            try {
                final Socket socket = new Socket("localhost", port);
                if (socket != null && socket.isConnected()) {
                    b = true;
                    socket.close();
                }
            }
            catch (Exception ex2) {
                b = false;
            }
        }
        return b;
    }
    
    static boolean isRegistryPort(final int port, final String s) {
        boolean b = true;
        try {
            LocateRegistry.getRegistry(port).lookup(s);
        }
        catch (AccessException ex) {
            b = false;
        }
        catch (NotBoundException ex2) {
            b = false;
        }
        catch (RemoteException ex3) {
            b = false;
        }
        return b;
    }
    
    private static boolean isWritable(final String s) {
        boolean b = false;
        final File file = new File(s);
        boolean newFile = false;
        try {
            newFile = file.createNewFile();
            if (file.exists() && file.canWrite()) {
                b = true;
            }
        }
        catch (IOException ex) {
            b = false;
        }
        if (newFile) {
            file.delete();
        }
        if (!b) {
            System.err.println("File, " + s + ", must have write privileges in order to run the AdminServer.");
        }
        return b;
    }
    
    public static void main(final String[] array) {
        final boolean writable = isWritable("admserv.log");
        isWritable("ads0.exp");
        if (!writable || !writable) {
            System.exit(1);
        }
        AdminServer.initializeLogs();
        final AdminServerType adminServerType = new AdminServerType(array);
        final String[] serverCmdAndArgs = adminServerType.getServerCmdAndArgs();
        adminServerType.getServerName();
        adminServerType.getServerArgs();
        final AdminServerGetopt adminServerGetopt = new AdminServerGetopt(adminServerType.getServerArgs());
        if (adminServerGetopt.helpRequested()) {
            if (adminServerType.isStart()) {
                System.out.println(AdminServer.usage());
            }
            else if (adminServerType.isStop()) {
                System.out.println(AdminServerShutdown.usage());
            }
            else if (adminServerType.isQuery()) {
                System.out.println(AdminServerQuery.usage());
            }
            else {
                System.out.println(adminServerType.usageString());
            }
            System.exit(0);
        }
        if (adminServerGetopt.getInvalidArgs().length() > 0) {
            System.err.println(AdminServerType.admBundle.getTranString("Invalid arguments specified:", new Object[] { adminServerGetopt.getInvalidArgs() }));
            System.err.println(AdminServerType.admBundle.getTranString("For additional help..."));
            System.exit(1);
        }
        final String invalidPortArgs = adminServerGetopt.getInvalidPortArgs();
        if (invalidPortArgs.length() > 0) {
            System.err.println(invalidPortArgs);
            System.exit(1);
        }
        if (adminServerType.isStart()) {
            final String hostName = adminServerGetopt.getHostName();
            if (hostName != null && !NetInfo.isLocalHost(hostName) && !adminServerGetopt.isCluster() && System.getProperty("forceIPver") == null) {
                System.err.println("Cannot start AdminServer on remote host: " + hostName);
                System.exit(1);
            }
            final acctAuthenticate acctAuthenticate = new acctAuthenticate();
            acctAuthenticate.setNoAuditSuccAuth();
            final String groups = adminServerGetopt.getGroups();
            if (groups != null && !acctAuthenticate.validateGroupList(groups)) {
                System.err.println(AdminServerType.admBundle.getTranString("No valid groups in the group list", new Object[] { groups }));
                System.exit(1);
            }
            String s = adminServerGetopt.getUserName();
            String s2;
            if (s == null) {
                try {
                    s = com.progress.common.util.acctAuthenticate.whoamiJNI();
                }
                catch (Exception ex2) {}
                if (null == s) {
                    s = System.getProperty("user.name");
                }
                s2 = acctAuthenticate.generatePassword(s);
            }
            else {
                s2 = adminServerGetopt.getPassword();
                if (null == s2) {
                    s2 = acctAuthenticate.promptForPassword(s);
                }
            }
            final crypto crypto = new crypto();
            if (!acctAuthenticate.validate(s, s2, adminServerGetopt.getGroups())) {
                System.err.println(AdminServerType.admBundle.getTranString("User is not authorized", new Object[] { s }));
                System.exit(1);
            }
            final int rmiRegistryPortNumber = adminServerGetopt.getRMIRegistryPortNumber();
            final int adminPortNumber = adminServerGetopt.getAdminPortNumber();
            if (isPortInUse(rmiRegistryPortNumber)) {
                System.err.println(AdminServerType.admBundle.getTranString("Port is already in use:", new Object[] { rmiRegistryPortNumber + "", AdminServerGetopt.m_keys[1] }));
                System.exit(1);
            }
            if (isPortInUse(adminPortNumber)) {
                System.err.println(AdminServerType.admBundle.getTranString("Port is already in use:", new Object[] { adminPortNumber + "", AdminServerGetopt.m_keys[4] }));
                System.exit(1);
            }
            if (isMFEnabled() && isDSEnabled()) {
                final ManagementProperties managementProperties = getManagementProperties();
                final int value = (managementProperties != null) ? managementProperties.getPort() : Integer.parseInt("6835");
                if (isPortInUse(value)) {
                    System.err.println(ProLog.format(7021956244000746666L, new Integer(value)));
                    System.err.println(ProLog.format(7021956244000746667L));
                    System.exit(1);
                }
            }
            boolean b = false;
            final String property = System.getProperty("BypassDaemon");
            if (property != null && property.equalsIgnoreCase("true")) {
                b = true;
            }
            if (adminServerGetopt.getTestMode()) {
                OSSpecific.invokeAdminServer("com.progress.chimera.adminserver.test.AdminServerTest", adminServerType.getServerArgs());
            }
            else if (!adminServerGetopt.getInteractiveMode() && !b) {
                try {
                    if (Boolean.getBoolean("debugJVMArgs")) {
                        for (int i = 0; i < serverCmdAndArgs.length; ++i) {
                            System.err.println("serverCmd[" + i + "] = " + serverCmdAndArgs[i]);
                        }
                    }
                    Runtime.getRuntime().exec(serverCmdAndArgs);
                }
                catch (IOException ex) {
                    System.err.println(AdminServerType.admBundle.getTranString("AdminServer failed to start:", new Object[] { ex.getMessage() }));
                    System.exit(1);
                }
            }
            else {
                OSSpecific.invokeAdminServer(IAdminServerConst.ADMIN_SERVER_CLASSNAME, adminServerType.getServerArgs());
            }
        }
        else if (adminServerType.isQuery()) {
            new AdminServerQuery(adminServerType.getServerArgs());
        }
        else if (adminServerType.isStop()) {
            new AdminServerShutdown(adminServerType.getServerArgs());
        }
        else {
            System.err.println(adminServerType.usageString());
            System.exit(1);
        }
        System.exit(0);
    }
    
    public static boolean isDSEnabled() {
        final String property = System.getProperty("ENABLE_DS", "false");
        boolean b = getFathomInstallDir() != null;
        if (property.equalsIgnoreCase("true") && !b) {
            b = true;
        }
        return b;
    }
    
    public static boolean isMFEnabled() {
        boolean booleanValue = false;
        final String property = System.getProperty("ENABLE_MF", "false");
        final String str = "isMonitored";
        String pathname = System.getProperty("Management.ConfigFile");
        if (pathname == null || pathname.length() < 1) {
            pathname = System.getProperty("Install.Dir") + IAdminServerConst.FILE_SEPARATOR + "properties" + IAdminServerConst.FILE_SEPARATOR + "management.properties";
        }
        try {
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(pathname))));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.length() > 0) {
                    if (line.charAt(0) == '#') {
                        continue;
                    }
                    if (line.indexOf(str) <= 0) {
                        continue;
                    }
                    final String substring = line.substring(line.indexOf("=") + 1);
                    if (substring != null) {
                        booleanValue = Boolean.valueOf(substring);
                        break;
                    }
                    break;
                }
            }
            bufferedReader.close();
        }
        catch (IOException ex) {}
        if (!booleanValue && property != null && property.equalsIgnoreCase("true")) {
            booleanValue = true;
        }
        return booleanValue;
    }
    
    public static String getFathomClasspath(final PropertyManager propertyManager) {
        if (AdminServerType.m_fathomClasspath == null && propertyManager != null) {
            propertyManager.setCurrentGroup("PluginPolicy.Fathom");
            AdminServerType.m_fathomClasspath = propertyManager.getProperty("classpath");
        }
        return AdminServerType.m_fathomClasspath;
    }
    
    public static boolean getFathomEnabled() {
        return getFathomEnabled(false);
    }
    
    public static boolean getFathomEnabled(final boolean b) {
        if (AdminServerType.m_isFathomEnabled == null) {
            try {
                if (!FathomInitParams.getInitLoaded()) {
                    if (b) {
                        final Subsystem adminServerSubsystem = AdminServerType.m_adminServerSubsystem;
                        LogSystem.logIt("AdminServer", 3, "Reading environment from file: " + getFathomInitParams());
                    }
                    FathomInitParams.setInitialization(getFathomInitParams());
                }
                AdminServerType.m_isFathomEnabled = new Boolean(FathomInitParams.getFathomEnabled());
            }
            catch (Exception obj) {
                final Subsystem adminServerSubsystem2 = AdminServerType.m_adminServerSubsystem;
                LogSystem.logIt("AdminServer", 5, "Unable to determine whether or not OpenEdge Explorer is enabled: " + obj);
                AdminServerType.m_isFathomEnabled = Boolean.FALSE;
            }
        }
        return AdminServerType.m_isFathomEnabled != null && AdminServerType.m_isFathomEnabled;
    }
    
    public static String getFathomInstallDir() {
        try {
            if (AdminServerType.m_fathomInstallDir == null && getFathomEnabled()) {
                AdminServerType.m_fathomInstallDir = FathomInitParams.getFathomDirShort();
            }
        }
        catch (Exception ex) {
            AdminServerType.m_fathomInstallDir = null;
            final Subsystem adminServerSubsystem = AdminServerType.m_adminServerSubsystem;
            LogSystem.logIt("AdminServer", 5, "Unable to obtain Fathom install path");
        }
        return AdminServerType.m_fathomInstallDir;
    }
    
    public static String getFathomConfigDir() {
        try {
            if (AdminServerType.m_fathomConfigDir == null && getFathomEnabled()) {
                AdminServerType.m_fathomConfigDir = FathomInitParams.getConfigDir();
            }
        }
        catch (Exception ex) {
            AdminServerType.m_fathomConfigDir = null;
            final Subsystem adminServerSubsystem = AdminServerType.m_adminServerSubsystem;
            LogSystem.logIt("AdminServer", 5, "Unable to obtain Fathom config directory path");
        }
        return AdminServerType.m_fathomConfigDir;
    }
    
    public static ManagementProperties getManagementProperties() {
        ManagementProperties managementProperties = null;
        String s = System.getProperty("Management.ConfigFile");
        if (s == null || s.length() < 1) {
            s = System.getProperty("Install.Dir") + IAdminServerConst.FILE_SEPARATOR + "properties" + IAdminServerConst.FILE_SEPARATOR + "management.properties";
        }
        try {
            managementProperties = new ManagementProperties(s, null);
        }
        catch (Exception ex) {
            System.out.println(ProLog.format(7021956244000746668L, s, ex.getMessage()));
            if (AdminServerType.m_adminServerSubsystem.getWriteLevel() > 3) {
                ex.printStackTrace();
            }
        }
        return managementProperties;
    }
    
    private Vector getJvmArgs(final String s) {
        final Vector<String> vector = new Vector<String>();
        String property = s;
        if (IAdminServerConst.IS_NT) {
            String s2 = property;
            final Environment environment = new Environment();
            if ((s2 == null || s2.length() == 0) && environment != null) {
                s2 = environment.expandPropertyValue("@{JAVA\\JVMARGS}");
                if (s2 == null) {
                    s2 = environment.getEnvironmentValue("QA_JVMARGS");
                }
                if (s2 != null) {
                    s2.trim();
                }
            }
            if (s2 != null && s2.length() > 0) {
                final int n = 1024;
                int i = 0;
                int n2 = 0;
                int n3 = 0;
                char[] value = new char[n];
                final char[] dst = new char[s2.length()];
                s2.getChars(0, s2.length(), dst, 0);
                while (i < dst.length) {
                    final char c = dst[i++];
                    switch (c) {
                        case 34: {
                            if (n3 != 0) {
                                vector.addElement(new String(value).trim());
                                value = new char[n];
                                n2 = 0;
                                n3 = 0;
                                continue;
                            }
                            n3 = 1;
                            continue;
                        }
                        case 32: {
                            if (n3 != 0) {
                                value[n2++] = c;
                                continue;
                            }
                            if (n2 > 0) {
                                vector.addElement(new String(value).trim());
                                value = new char[n];
                                n2 = 0;
                                continue;
                            }
                            continue;
                        }
                        default: {
                            value[n2++] = c;
                            continue;
                        }
                    }
                }
                if (value != null) {
                    final String s3 = new String(value);
                    if (s3 != null && s3.trim().length() > 0) {
                        vector.addElement(s3.trim());
                    }
                }
            }
        }
        else {
            if (property == null || property.length() == 0) {
                property = System.getProperty("java.jvmargs");
            }
            if (property != null && property.trim().length() > 0) {
                final StringTokenizer stringTokenizer = new StringTokenizer(property);
                while (stringTokenizer.hasMoreElements()) {
                    vector.addElement(stringTokenizer.nextToken());
                }
            }
        }
        return vector;
    }
    
    static {
        DLC = IAdminServerConst.INSTALL_DIR;
        JVM_EXE = AdminServerType.DLC + "\\jre\\bin\\java";
        NEWLINE = System.getProperty("line.separator");
        AdminServerType.m_adminServerSubsystem = AdminServerLog.get();
        AdminServerType.admBundle = (ProgressResources)ResourceBundle.getBundle("com.progress.international.messages.ADMMsgBundle");
        AdminServerType.cmnBundle = (ProgressResources)ResourceBundle.getBundle("com.progress.international.messages.CMNMsgBundle");
        AdminServerType.DB_PROPERTIES_FILE = ((System.getProperty("java.propertyfile") == null) ? "conmgr.properties" : System.getProperty("java.propertyfile"));
        AdminServerType.DB_PROPERTIES_FULLFILE = System.getProperty("Install.Dir") + Const.FILE_SEPARATOR + "properties" + Const.FILE_SEPARATOR + AdminServerType.DB_PROPERTIES_FILE;
        AdminServerType.DBA_PROPERTIES_FULLFILE = System.getProperty("Install.Dir") + Const.FILE_SEPARATOR + "properties" + Const.FILE_SEPARATOR + "agent.properties";
        AdminServerType.FATHOM_PROPERTIES_FULLFILE = System.getProperty("Install.Dir") + Const.FILE_SEPARATOR + "properties" + Const.FILE_SEPARATOR + "fathom.properties";
        AdminServerType.FATHOM_INIT_PARAMS = System.getProperty("Install.Dir") + Const.FILE_SEPARATOR + "fathom.init.params";
        AdminServerType.SMDB_PROPERTIES_FULLFILE = System.getProperty("Install.Dir") + Const.FILE_SEPARATOR + "properties" + Const.FILE_SEPARATOR + "smdatabase.properties";
        AdminServerType.UB_PROPERTIES_FULLFILE = System.getProperty("Install.Dir") + Const.FILE_SEPARATOR + "properties" + Const.FILE_SEPARATOR + "ubroker.properties";
        AdminServerType.MGMT_PROPERTIES_FULLFILE = System.getProperty("Install.Dir") + Const.FILE_SEPARATOR + "properties" + Const.FILE_SEPARATOR + "management.properties";
        AdminServerType.DB_ADMIN_PORT = 7842;
        m_keys = new String[] { "start", "stop", "query", "f", "H", "host", "ipver", "fathominitparams" };
        m_defaultPropArray = new String[] { "java.runtime.name", "sun.boot.library.path", "java.vm.version", "java.vm.vendor", "java.vendor.url", "path.separator", "java.vm.name", "file.encoding.pkg", "java.vm.specification.name", "user.dir", "java.runtime.version", "java.awt.graphicsenv", "os.arch", "java.io.tmpdir", "line.separator", "java.vm.specification.vendor", "java.awt.fonts", "os.name", "java.library.path", "java.specification.name", "java.class.version", "os.version", "user.home", "user.timezone", "java.awt.printerjob", "java.specification.version", "user.name", "java.class.path", "java.vm.specification.version", "java.home", "user.language", "java.specification.vendor", "awt.toolkit", "java.vm.info", "java.version", "java.ext.dirs", "sun.boot.class.path", "java.vendor", "file.separator", "java.vendor.url.bug", "sun.cpu.endian", "sun.io.unicode.encoding", "user.region", "sun.cpu.isalist", "java.jvmargs" };
        AdminServerType.m_propertyManager = null;
        AdminServerType.m_fathomClasspath = null;
        AdminServerType.m_isFathomEnabled = null;
        AdminServerType.m_fathomInstallDir = null;
        AdminServerType.m_fathomConfigDir = null;
    }
}
