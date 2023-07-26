// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera.adminserver;

import com.progress.common.exception.ExceptionMessageAdapter;
import com.progress.common.util.Getopt;
import java.util.StringTokenizer;
import java.net.InetAddress;
import com.progress.common.util.Port;
import com.progress.international.resources.ProgressResources;
import com.progress.message.cfMsg;

public class AdminServerGetopt implements IAdminServerConst, cfMsg
{
    static final String NEWLINE;
    static final int OPT_PLUGINSFILE = 0;
    static final int OPT_RMIREGISTRYPORT = 1;
    static final int OPT_RMIBINDNAME = 2;
    static final int OPT_INTERACTIVEMODE = 3;
    static final int OPT_ADMINPORT = 4;
    static final int OPT_PROPFILE = 5;
    static final int OPT_HELP = 6;
    static final int OPT_USER = 7;
    static final int OPT_PASSWORD = 8;
    static final int OPT_SERVICE = 9;
    static final int OPT_TESTMODE = 10;
    static final int OPT_GROUPS = 11;
    static final int OPT_REQUSERNAME = 12;
    static final int OPT_NOAUDITSUCCAUTH = 13;
    static final int OPT_HOST = 14;
    static final int OPT_H = 15;
    static final int OPT_CLUSTER = 16;
    static final int OPT_NOFATHOM = 17;
    static final int OPT_TIMEOUT = 18;
    static final int OPT_IPVER = 19;
    static final int OPT_DB_PF = 20;
    static final int OPT_SMDB_PF = 21;
    static final int OPT_DBA_PF = 22;
    static final int OPT_UB_PF = 23;
    static final int OPT_MGMT_PF = 24;
    static final int OPT_FATHOM_PF = 25;
    static final int OPT_FATHOM_INIT = 26;
    static final int UNKOPT = 63;
    private String portErrorString;
    private String timeoutS;
    static ProgressResources admBundle;
    static ProgressResources cmnBundle;
    static final String[] m_keys;
    String m_pluginsFile;
    String m_RMIRegistryPort;
    String m_RMIBindName;
    Port m_RMIRegistryPortNumber;
    Port m_adminPortNumber;
    boolean m_interactiveMode;
    boolean m_testMode;
    boolean m_help;
    boolean m_isService;
    boolean m_reqUserName;
    boolean m_noAuditSuccAuth;
    boolean m_isCluster;
    String m_adminPort;
    String m_dbPropFile;
    String m_smdbPropFile;
    String m_dbaPropFile;
    String m_ubPropFile;
    String m_mgmtPropFile;
    String m_hostName;
    String m_ipver;
    String m_userName;
    String m_password;
    String m_groups;
    int m_timeout;
    boolean m_noFathom;
    static String m_fathomPropFile;
    static String m_fathomInitParams;
    private String m_inputArgs;
    private String m_invalidArgs;
    
    public boolean isService() {
        return this.m_isService;
    }
    
    public boolean isReqUserName() {
        return this.m_reqUserName;
    }
    
    public boolean isNoAuditSuccAuth() {
        return this.m_noAuditSuccAuth;
    }
    
    public String getInputArgs() {
        return this.m_inputArgs;
    }
    
    public String getInvalidArgs() {
        return this.m_invalidArgs.trim();
    }
    
    public boolean helpRequested() {
        return this.m_help;
    }
    
    String getPluginsFile() {
        return this.m_pluginsFile;
    }
    
    public String getRMIRegistryPort() {
        return this.m_RMIRegistryPort;
    }
    
    String getAdminPort() {
        return this.m_adminPort;
    }
    
    int getRMIRegistryPortNumber() {
        return this.m_RMIRegistryPortNumber.getPortInt();
    }
    
    int getAdminPortNumber() {
        return this.m_adminPortNumber.getPortInt();
    }
    
    String getDbPropFile() {
        return this.m_dbPropFile;
    }
    
    String getDbaPropFile() {
        return this.m_dbaPropFile;
    }
    
    String getSmdbPropFile() {
        return this.m_smdbPropFile;
    }
    
    String getUbPropFile() {
        return this.m_ubPropFile;
    }
    
    String getMgmtPropFile() {
        return this.m_mgmtPropFile;
    }
    
    public static String getFathomPropFile() {
        return AdminServerGetopt.m_fathomPropFile;
    }
    
    public static String getFathomInitParams() {
        return AdminServerGetopt.m_fathomInitParams;
    }
    
    String getRMIBindName() {
        return this.m_RMIBindName;
    }
    
    boolean getInteractiveMode() {
        return this.m_interactiveMode;
    }
    
    boolean getTestMode() {
        return this.m_testMode;
    }
    
    boolean isCluster() {
        return this.m_isCluster;
    }
    
    boolean bypassManagementFramework() {
        return this.m_noFathom;
    }
    
    int getManagementFrameworkTimeout() {
        return this.m_timeout;
    }
    
    String getHostName() {
        return this.m_hostName;
    }
    
    String initializeHostname() {
        String value = System.getProperty("java.rmi.server.hostname");
        if (value == null) {
            try {
                value = InetAddress.getLocalHost().getHostName();
                System.setProperty("java.rmi.server.hostname", value);
            }
            catch (Exception ex) {}
        }
        return value;
    }
    
    String getHostAddress() {
        String s;
        String hostName = s = this.getHostName();
        try {
            if (hostName == null) {
                hostName = InetAddress.getLocalHost().getHostName();
            }
            s = InetAddress.getByName(hostName).getHostAddress();
        }
        catch (Exception ex) {}
        return s;
    }
    
    String getUserName() {
        return this.m_userName;
    }
    
    String getPassword() {
        return this.m_password;
    }
    
    String getInvalidPortArgs() {
        return this.portErrorString;
    }
    
    String getGroups() {
        return this.m_groups;
    }
    
    public AdminServerGetopt(final String[] array) {
        this.portErrorString = "";
        this.timeoutS = null;
        this.m_pluginsFile = IAdminServerConst.PLUGINS_FULLFILE_NAME;
        this.m_RMIRegistryPort = IAdminServerConst.RMI_REGISTRY_PORT;
        this.m_RMIBindName = "Chimera";
        this.m_RMIRegistryPortNumber = null;
        this.m_adminPortNumber = null;
        this.m_interactiveMode = false;
        this.m_testMode = false;
        this.m_help = false;
        this.m_isService = false;
        this.m_reqUserName = false;
        this.m_noAuditSuccAuth = false;
        this.m_isCluster = false;
        this.m_adminPort = AdminServerType.DB_ADMIN_PORT + "";
        this.m_dbPropFile = AdminServerType.DB_PROPERTIES_FULLFILE;
        this.m_smdbPropFile = null;
        this.m_dbaPropFile = null;
        this.m_ubPropFile = null;
        this.m_mgmtPropFile = null;
        this.m_hostName = this.initializeHostname();
        this.m_ipver = null;
        this.m_userName = null;
        this.m_password = null;
        this.m_groups = null;
        this.m_timeout = 360;
        this.m_noFathom = false;
        this.m_inputArgs = "";
        this.m_invalidArgs = "";
        this.update(array);
    }
    
    public void update(final String str) {
        final StringTokenizer stringTokenizer = new StringTokenizer(str);
        final int countTokens = stringTokenizer.countTokens();
        final String[] array = new String[countTokens];
        for (int i = 0; i < countTokens; ++i) {
            array[i] = stringTokenizer.nextToken();
        }
        this.update(array);
    }
    
    public void update(final String[] array) {
        final Getopt.GetoptList[] array2 = { new Getopt.GetoptList(AdminServerGetopt.m_keys[0] + ":", 0), new Getopt.GetoptList(AdminServerGetopt.m_keys[1] + ":", 1), new Getopt.GetoptList(AdminServerGetopt.m_keys[2] + ":", 2), new Getopt.GetoptList(AdminServerGetopt.m_keys[3] + ":", 3), new Getopt.GetoptList(AdminServerGetopt.m_keys[4] + ":", 4), new Getopt.GetoptList(AdminServerGetopt.m_keys[5] + ":", 5), new Getopt.GetoptList(AdminServerGetopt.m_keys[6], 6), new Getopt.GetoptList(AdminServerGetopt.m_keys[7] + ":", 7), new Getopt.GetoptList(AdminServerGetopt.m_keys[8] + ":", 8), new Getopt.GetoptList(AdminServerGetopt.m_keys[9], 9), new Getopt.GetoptList(AdminServerGetopt.m_keys[10] + ":", 10), new Getopt.GetoptList(AdminServerGetopt.m_keys[11] + ":", 11), new Getopt.GetoptList(AdminServerGetopt.m_keys[12], 12), new Getopt.GetoptList(AdminServerGetopt.m_keys[13], 13), new Getopt.GetoptList(AdminServerGetopt.m_keys[14] + ":", 14), new Getopt.GetoptList(AdminServerGetopt.m_keys[15] + ":", 15), new Getopt.GetoptList(AdminServerGetopt.m_keys[16], 16), new Getopt.GetoptList(AdminServerGetopt.m_keys[17], 17), new Getopt.GetoptList(AdminServerGetopt.m_keys[18] + ":", 18), new Getopt.GetoptList(AdminServerGetopt.m_keys[19] + ":", 19), new Getopt.GetoptList(AdminServerGetopt.m_keys[20] + ":", 20), new Getopt.GetoptList(AdminServerGetopt.m_keys[21] + ":", 21), new Getopt.GetoptList(AdminServerGetopt.m_keys[22] + ":", 22), new Getopt.GetoptList(AdminServerGetopt.m_keys[23] + ":", 23), new Getopt.GetoptList(AdminServerGetopt.m_keys[24] + ":", 24), new Getopt.GetoptList(AdminServerGetopt.m_keys[25] + ":", 25), new Getopt.GetoptList(AdminServerGetopt.m_keys[26] + ":", 26), new Getopt.GetoptList("", 0) };
        final Getopt getopt = new Getopt(array);
        getopt.setIgnoreCase(false);
        int opt;
        while ((opt = getopt.getOpt(array2)) != -1) {
            switch (opt) {
                case 0: {
                    this.m_pluginsFile = getopt.getOptArg();
                    continue;
                }
                case 1: {
                    this.m_RMIRegistryPort = getopt.getOptArg();
                    continue;
                }
                case 2: {
                    this.m_RMIBindName = getopt.getOptArg();
                    continue;
                }
                case 3: {
                    this.m_interactiveMode = getopt.getOptArg().equalsIgnoreCase("true");
                    continue;
                }
                case 10: {
                    this.m_testMode = getopt.getOptArg().equalsIgnoreCase("true");
                    continue;
                }
                case 4: {
                    this.m_adminPort = getopt.getOptArg();
                    continue;
                }
                case 5:
                case 20: {
                    this.m_dbPropFile = getopt.getOptArg();
                    continue;
                }
                case 22: {
                    this.m_dbaPropFile = getopt.getOptArg();
                    continue;
                }
                case 21: {
                    this.m_smdbPropFile = getopt.getOptArg();
                    continue;
                }
                case 23: {
                    this.m_ubPropFile = getopt.getOptArg();
                    continue;
                }
                case 24: {
                    this.m_mgmtPropFile = getopt.getOptArg();
                    continue;
                }
                case 25: {
                    System.setProperty("fathom.propertyfile", AdminServerGetopt.m_fathomPropFile = getopt.getOptArg());
                    continue;
                }
                case 26: {
                    System.setProperty("fathom.init.params", AdminServerGetopt.m_fathomInitParams = getopt.getOptArg());
                    continue;
                }
                case 7: {
                    this.m_userName = getopt.getOptArg();
                    continue;
                }
                case 8: {
                    this.m_password = getopt.getOptArg();
                    continue;
                }
                case 11: {
                    this.m_groups = getopt.getOptArg().replace(':', ',');
                    continue;
                }
                case 6: {
                    this.m_help = true;
                    continue;
                }
                case 9: {
                    this.m_isService = true;
                    continue;
                }
                case 12: {
                    this.m_reqUserName = true;
                    continue;
                }
                case 13: {
                    this.m_noAuditSuccAuth = true;
                    continue;
                }
                case 14:
                case 15: {
                    this.m_hostName = getopt.getOptArg();
                    try {
                        System.setProperty("java.rmi.server.hostname", this.m_hostName);
                    }
                    catch (Exception ex) {}
                    continue;
                }
                case 19: {
                    this.m_ipver = getopt.getOptArg();
                    if (!this.m_ipver.equalsIgnoreCase("IPv4") && !this.m_ipver.equalsIgnoreCase("IPv6")) {
                        this.m_invalidArgs = this.m_invalidArgs + "-ipver " + this.m_ipver + " ";
                        continue;
                    }
                    if (this.m_ipver.equalsIgnoreCase("IPv6")) {
                        System.setProperty("java.net.preferIPv4Stack", "false");
                        System.setProperty("java.net.preferIPv6Stack", "true");
                        System.setProperty("java.net.preferIPv6Addresses", "true");
                        System.setProperty("java.net.preferIPv4Addresses", "false");
                        continue;
                    }
                    System.setProperty("java.net.preferIPv4Stack", "true");
                    System.setProperty("java.net.preferIPv6Stack", "false");
                    System.setProperty("java.net.preferIPv4Addresses", "true");
                    System.setProperty("java.net.preferIPv6Addresses", "false");
                    continue;
                }
                case 16: {
                    this.m_isCluster = true;
                    continue;
                }
                case 17: {
                    this.m_noFathom = true;
                    continue;
                }
                case 18: {
                    this.timeoutS = getopt.getOptArg();
                    continue;
                }
                case 63: {
                    this.m_invalidArgs = this.m_invalidArgs + array[getopt.getOptInd()] + " ";
                    continue;
                }
            }
        }
        this.ValidateArguments();
        for (int i = 0; i < array.length; ++i) {
            this.m_inputArgs = this.m_inputArgs + array[i] + " ";
        }
    }
    
    private boolean ValidateArguments() {
        try {
            this.m_RMIRegistryPortNumber = new Port(this.m_RMIRegistryPort);
        }
        catch (Port.PortException ex) {
            this.portErrorString = this.portErrorString + AdminServerGetopt.admBundle.getTranString("Value provided for argument is outside of valid port range:", new Object[] { "port", this.m_RMIRegistryPort + "" }) + AdminServerGetopt.NEWLINE;
            this.portErrorString = this.portErrorString + ExceptionMessageAdapter.getMessage(7162412257379361826L, "") + AdminServerGetopt.NEWLINE;
        }
        try {
            this.m_adminPortNumber = new Port(this.m_adminPort);
        }
        catch (Port.PortException ex2) {
            this.portErrorString = this.portErrorString + AdminServerGetopt.admBundle.getTranString("Value provided for argument is outside of valid port range:", new Object[] { "adminport", this.m_adminPort + "" }) + AdminServerGetopt.NEWLINE;
            this.portErrorString = this.portErrorString + ExceptionMessageAdapter.getMessage(7162412257379361826L, "") + AdminServerGetopt.NEWLINE;
        }
        if (this.portErrorString == "" && this.m_adminPortNumber.getPortInt() == this.m_RMIRegistryPortNumber.getPortInt()) {
            this.portErrorString += AdminServerGetopt.admBundle.getTranString("Error - port and adminport can not be the same.");
        }
        try {
            if (this.timeoutS != null) {
                this.m_timeout = new Integer(this.timeoutS);
            }
        }
        catch (Exception ex3) {
            this.portErrorString += "Timeout value must be specified in seconds";
        }
        if (this.portErrorString == "") {
            this.m_adminPort = this.m_adminPortNumber.getPortInt() + "";
            this.m_RMIRegistryPort = this.m_RMIRegistryPortNumber.getPortInt() + "";
            return true;
        }
        return false;
    }
    
    static {
        NEWLINE = System.getProperty("line.separator");
        AdminServerGetopt.admBundle = AdminServerType.admBundle;
        AdminServerGetopt.cmnBundle = AdminServerType.cmnBundle;
        m_keys = new String[] { "f", "port", "RMIBindName", "interactive", "adminport", "propertyfile", "help", "user", "password", "service", "test", "admingroup", "requireusername", "noauditsuccauth", "host", "H", "cluster", "nofathom", "timeout", "ipver", "dbproperties", "smdbproperties", "dbaproperties", "ubproperties", "mgmtproperties", "fathomproperties", "fathominitparams" };
        AdminServerGetopt.m_fathomPropFile = null;
        AdminServerGetopt.m_fathomInitParams = null;
    }
}
