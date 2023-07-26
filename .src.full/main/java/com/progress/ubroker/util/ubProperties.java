// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.util;

import com.progress.ubroker.broker.JavaServices;
import java.lang.reflect.Method;
import java.net.Inet6Address;
import com.progress.common.util.InstallPath;
import java.util.StringTokenizer;
import com.progress.common.licensemgr.LicenseMgr;
import java.net.InetAddress;
import java.io.File;
import java.util.List;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Properties;
import com.progress.common.exception.ProException;
import com.progress.common.message.IProMessage;
import com.progress.common.exception.ExceptionMessageAdapter;
import com.progress.common.util.PromsgsFile;
import java.io.IOException;
import com.progress.common.ehnlog.LogHandler;
import com.progress.common.ehnlog.AppLogger;
import com.progress.common.ehnlog.DefaultLogHandler;
import com.progress.common.util.Getopt;
import com.progress.common.ehnlog.IAppLogger;
import com.progress.common.networkevents.EventBroker;
import com.progress.ubroker.broker.ubListenerThread;
import com.progress.common.util.PropertyFilter;
import java.util.Vector;
import java.util.Hashtable;
import com.progress.ubroker.ssl.ClientParams;
import com.progress.common.util.Environment;
import com.progress.common.property.PropertyManager;

public class ubProperties extends PropertyManager implements ubConstants
{
    public static final String PROPNAME_PORTNUM = "portNumber";
    public static final String PROPNAME_REGMODE = "registrationMode";
    public static final String PROPNAME_HOSTNAME = "hostName";
    public static final String PROPNAME_MINSERVERS = "minSrvrInstance";
    public static final String PROPNAME_MAXSERVERS = "maxSrvrInstance";
    public static final String PROPNAME_INITIALSERVERS = "initialSrvrInstance";
    public static final String PROPNAME_SERVERMODE = "operatingMode";
    public static final String PROPNAME_SERVERPARMS = "srvrStartupParam";
    public static final String PROPNAME_QUEUELIMIT = "queueLimit";
    public static final String PROPNAME_LOGFILENAME = "logFile";
    public static final String PROPNAME_LOGGINGLEVEL = "loggingLevel";
    public static final String PROPNAME_BRKRLOGGINGLEVEL = "brkrLoggingLevel";
    public static final String PROPNAME_BRKRLOGAPPEND = "brkrLogAppend";
    public static final String PROPNAME_BRKRLOGTHRESHOLD = "brkrLogThreshold";
    public static final String PROPNAME_BRKRLOGENTRYTYPES = "brkrLogEntryTypes";
    public static final String PROPNAME_BRKRNUMLOGFILES = "brkrNumLogFiles";
    public static final String PROPNAME_SRVRLOGGINGLEVEL = "srvrLoggingLevel";
    public static final String PROPNAME_SRVRLOGAPPEND = "srvrLogAppend";
    public static final String PROPNAME_SRVRLOGTHRESHOLD = "srvrLogThreshold";
    public static final String PROPNAME_SRVRLOGENTRYTYPES = "srvrLogEntryTypes";
    public static final String PROPNAME_SRVRNUMLOGFILES = "srvrNumLogFiles";
    public static final String PROPNAME_SRVRSELECTIONSCHEME = "srvrSelectionScheme";
    public static final String PROPNAME_SRVRLOGFILEWATCHDOG = "srvrLogWatchdogInterval";
    public static final String PROPNAME_LOGFILEMODE = "logFileMode";
    public static final String PROPNAME_MAXCLIENTS = "maxClientInstance";
    public static final String PROPNAME_USERNAME = "userName";
    public static final String PROPNAME_PASSWORD = "userPassword";
    public static final String PROPNAME_WORKINGDIR = "workDir";
    public static final String PROPNAME_SERVERLOGFILE = "srvrLogFile";
    public static final String PROPNAME_BROKERLOGFILE = "brokerLogFile";
    public static final String PROPNAME_SERVERSTARTUPPARMS = "serverStartupParms";
    public static final String PROPNAME_MINSERVERPORT = "srvrMinPort";
    public static final String PROPNAME_MAXSERVERPORT = "srvrMaxPort";
    public static final String PROPNAME_SERVEREXENAME = "srvrExecFile";
    public static final String PROPNAME_SERVERSTARTUPPROC = "srvrStartupProc";
    public static final String PROPNAME_SERVERSHUTDOWNPROC = "srvrShutdownProc";
    public static final String PROPNAME_SERVERCONNECTIONPROC = "srvrConnectProc";
    public static final String PROPNAME_SERVERDISCONNECTPROC = "srvrDisconnProc";
    public static final String PROPNAME_SERVERACTIVATEPROC = "srvrActivateProc";
    public static final String PROPNAME_SERVERDEACTIVATEPROC = "srvrDeactivateProc";
    public static final String PROPNAME_CONTROLLINGNS = "controllingNameServer";
    public static final String PROPNAME_REGNAMESERVER = "registerNameServer";
    public static final String PROPNAME_UUID = "uuid";
    public static final String PROPNAME_NSWEIGHT = "priorityWeight";
    public static final String PROPNAME_NSTIMEOUT = "registrationRetry";
    public static final String PROPNAME_DEFAULTSERVICE = "defaultService";
    public static final String PROPNAME_APPLSERVICENAMES = "appserviceNameList";
    public static final String PROPNAME_ASKCAPABILITIES = "appServerKeepaliveCapabilities";
    public static final String PROPNAME_SASKACTIVITYTIMEOUT = "serverASKActivityTimeout";
    public static final String PROPNAME_SASKRESPONSETIMEOUT = "serverASKResponseTimeout";
    public static final String PROPNAME_SERVERLIFESPAN = "serverLifespan";
    public static final String PROPNAME_SERVERLIFESPANPADDING = "serverLifespanPadding";
    public static final String PROPNAME_BRKRSPININTERVAL = "brkrSpinInterval";
    public static final String PROPNAME_SRVRSTARTUPTIMEOUT = "srvrStartupTimeout";
    public static final String PROPNAME_REQUESTTIMEOUT = "requestTimeout";
    public static final String PROPNAME_CONNECTINGTIMEOUT = "connectingTimeout";
    public static final String PROPNAME_AUTOTRIMTIMEOUT = "autoTrimTimeout";
    public static final String PROPNAME_IPVER = "ipver";
    private static final String PROPNAME_ALLOWRUNTIMEUPDATES = "allowRuntimeUpdates";
    public static final String PROPNAME_MAXIDLESERVERS = "maxIdleServers";
    public static final String PROPNAME_MINIDLESERVERS = "minIdleServers";
    public static final String PROPNAME_SOTIMEOUT = "soTimeout";
    public static final String PROPNAME_PARENTWATCHDOGINTERVAL = "parentWatchdogInterval";
    public static final String PROPNAME_WATCHDOGTHREADPRIORITY = "watchdogThreadPriority";
    public static final String PROPNAME_CLIENTTHREADPRIORITY = "clientThreadPriority";
    public static final String PROPNAME_SERVERTHREADPRIORITY = "serverThreadPriority";
    public static final String PROPNAME_LISTENERTHREADPRIORITY = "listenerThreadPriority";
    public static final String PROPNAME_SSLENABLE = "sslEnable";
    public static final String PROPNAME_CERTSTOREPATH = "certStorePath";
    public static final String PROPNAME_KEYALIAS = "keyAlias";
    public static final String PROPNAME_KEYALIASPASSWD = "keyAliasPasswd";
    public static final String PROPNAME_KEYSTOREPATH = "keyStorePath";
    public static final String PROPNAME_KEYSTOREPASSWD = "keyStorePasswd";
    public static final String PROPNAME_NOSESSIONCACHE = "noSessionCache";
    public static final String PROPNAME_SESSIONTIMEOUT = "sessionTimeout";
    public static final String PROPNAME_SSLALGORITHMS = "sslAlgorithms";
    public static final String PROPNAME_NSHOST = "hostName";
    public static final String PROPNAME_NSPORTNUM = "portNumber";
    public static final String PROPNAME_MINADPTRTHREADS = "minAdptrThreads";
    public static final String PROPNAME_MAXADPTRTHREADS = "maxAdptrThreads";
    public static final String PROPNAME_INITADPTRTHREADS = "initialAdptrThreads";
    public static final String PROPNAME_CLASSMAIN = "classMain";
    public static final String PROPNAME_MQJVMEXE = "jvmexe";
    public static final String PROPNAME_MQJVMARGS = "jvmargs";
    public static final String PROPNAME_MQSECURITYPOLICY = "policyfile";
    public static final String PROPNAME_MQCLASSPATH = "pluginclasspath";
    public static final String PROPNAME_MQSTARTUPPARMS = "mqStartupParms";
    public static final String PROPNAME_MQPORT = "mqPort";
    public static final String PROPNAME_MQPID = "mqPid";
    public static final String PROPNAME_MQBRKRLOGAPPEND = "mqBrkrLogAppend";
    public static final String PROPNAME_MQENABLE = "mqEnabled";
    public static final String PROPNAME_MQBRKRLOGENTRYTYPES = "mqBrkrLogEntryTypes";
    public static final String PROPNAME_MQBRKRLOGGINGLEVEL = "mqBrkrLoggingLevel";
    public static final String PROPNAME_MQBRKRLOGTHRESHOLD = "mqBrkrLogThreshold";
    public static final String PROPNAME_MQBRKRNUMLOGFILES = "mqBrkrNumLogFiles";
    public static final String PROPNAME_MQBROKERLOGFILE = "mqBrokerLogFile";
    public static final String PROPNAME_MQSRVRLOGAPPEND = "mqSrvrLogAppend";
    public static final String PROPNAME_MQSRVRLOGENTRYTYPES = "mqSrvrLogEntryTypes";
    public static final String PROPNAME_MQSRVRLOGGINGLEVEL = "mqSrvrLoggingLevel";
    public static final String PROPNAME_MQSRVRLOGTHRESHOLD = "mqSrvrLogThreshold";
    public static final String PROPNAME_MQSRVRNUMLOGFILES = "mqSrvrNumLogFiles";
    public static final String PROPNAME_MQSERVERLOGFILE = "mqServerLogFile";
    public static final String PROPNAME_COLLECT_STATS_DATA = "collectStatsData";
    public static final String PROPNAME_ADMRETRY = "admSrvrRegisteredRetry";
    public static final String PROPNAME_ADMRETRY_INTERVAL = "admSrvrRegisteredRetryInterval";
    public static final String PROPNAME_ACTIONALENABLED = "actionalEnabled";
    public static final String PROPNAME_ACTIONALGROUP = "actionalGroup";
    public static final String PROPNAME_SRVRDSLOGFILE = "srvrDSLogFile";
    public static final String NAMESERVER_SECTION_NAME = "NameServer";
    public static final String PREFERENCES_SECTION_NAME = "PreferenceRoot.Preference";
    public static final String UBROKER_SECTION_NAME = "UBroker";
    public static final String ADAPTER_SECTION_NAME = "Adapter";
    public static final String ADMINSERVER_ADAPTER_SECTION_NAME = "PluginPolicy.Progress.SonicMQ";
    public static final int DEF_ADMRETRY = 6;
    public static final int DEF_ADMRETRY_INTERVAL = 3000;
    public static final int DEF_SELECTIONSCHEME = 0;
    public static final int DEF_QUEUE_LIMIT = 0;
    public static final int DEF_SOTIMEOUT = 0;
    public static final int DEF_BRKRSPININTERVAL = 3000;
    public static final int DEF_REQUESTTIMEOUT = 15;
    public static final int DEF_CONNECTINGTIMEOUT = 60;
    public static final int DEF_SRVRSTARTUPTIMEOUT = 3;
    public static final int DEF_LOGFILE_WATCHDOG = 60;
    public static final int DEF_CLIENTTHREADPRIORITY = 5;
    public static final int DEF_SERVERTHREADPRIORITY = 5;
    public static final int DEF_LISTENERTHREADPRIORITY = 6;
    public static final int DEF_WATCHDOGTHREADPRIORITY = 6;
    public static final int DEF_PARENTWATCHDOGINTERVAL = 3000;
    public static final String DEF_ASKCAPABILITIES = "denyClientASK,denyServerASK";
    public static final int DEF_SASKACTIVITYTIMEOUT = 60;
    public static final int DEF_SASKRESPONSETIMEOUT = 60;
    public static final int MIN_SASKACTIVITYTIMEOUT = 30;
    public static final int MIN_SASKRESPONSETIMEOUT = 30;
    public static final int DEF_SERVERLIFESPAN = 0;
    public static final int DEF_SERVERLIFESPANPADDING = 5;
    public static final int DEF_IPVER = 0;
    public static final int DEF_NUMLOGFILES = 3;
    private static final String INVALID_STRING;
    private static final int INVALID_INT = -1;
    private static final int RESTRICTED_SERVER_LICENSE_LIMIT = 2;
    private static final int RESTRICTED_ADAPTER_SERVER_LICENSE_LIMIT = 30;
    private static final String SYSPROP_PREFERIPV4STACK = "java.net.preferIPv4Stack";
    private static final String SYSPROP_PREFERIPV6ADDRS = "java.net.preferIPv6Addresses";
    private static final int[] INT_SERVER_MODES;
    private static final String[] STRING_SERVER_MODES;
    private static final int[] INT_REG_MODES;
    private static final String[] STRING_REG_MODES;
    private static final int[] INT_SERVER_TYPES;
    private static final String[] STRING_SERVER_TYPES;
    private static final int[] INT_IPVERSIONS;
    private static final String[] STRING_IPVERSIONS;
    private static final int LOGFILEMODE_NEWFILE = 0;
    private static final int LOGFILEMODE_APPEND = 1;
    private static final String SEPARATOR = "|";
    public static final char OPT_BROKERNAME = 'i';
    public static final char OPT_PROPFILENAME = 'f';
    public static final char OPT_SERVERTYPE = 't';
    public static final char OPT_PORTNUM = 'p';
    public static final char OPT_INSTALLDIR = 'd';
    public static final char OPT_PARENTPID = 'c';
    public static final char OPT_MQLOGGINGPARAMS = 'm';
    public static final char OPT_RMIURL = 'r';
    public static final String VALID_OPTIONS = "i:f:t:r:p:d:c:m:";
    public String propertiesfilename;
    public String brokerName;
    public int portNum;
    public int regMode;
    public int serverType;
    public int serverMode;
    public String controllingNS;
    public String nsHost;
    public int nsPortnum;
    public String dblquote;
    public Environment env;
    public int brokerPid;
    public String localHost;
    public String canonicalName;
    public int parentPID;
    public int ipver;
    public String rmiURL;
    private String installDir;
    private String osName;
    private String osVersion;
    private String javaVersion;
    private String javaClassPath;
    private String userDirectory;
    private String javaPreferIPv4Stack;
    private String javaPreferIPv6Addresses;
    private ClientParams sslClientParams;
    private Hashtable activeProps;
    private Vector changedPropList;
    private String propGroupName;
    private PropertyFilter propFilter;
    private ubListenerThread listenerThread;
    
    public ubProperties() {
        this(null);
    }
    
    public ubProperties(final EventBroker eventBroker) {
        super(eventBroker, false);
        this.propertiesfilename = ubProperties.INVALID_STRING;
        this.brokerName = ubProperties.INVALID_STRING;
        this.installDir = ubProperties.INVALID_STRING;
        this.portNum = -1;
        this.regMode = -1;
        this.serverType = -1;
        this.serverMode = -1;
        this.rmiURL = ubProperties.INVALID_STRING;
        this.controllingNS = ubProperties.INVALID_STRING;
        this.nsHost = ubProperties.INVALID_STRING;
        this.nsPortnum = -1;
        this.dblquote = (this.isNT() ? "\"" : "");
        this.propFilter = new PropertyFilter(this);
        this.changedPropList = new Vector();
        this.activeProps = this.getHashtable();
        this.listenerThread = null;
        this.env = null;
        this.brokerPid = -1;
        this.localHost = ubProperties.INVALID_STRING;
        this.osName = ubProperties.INVALID_STRING;
        this.osVersion = ubProperties.INVALID_STRING;
        this.javaVersion = ubProperties.INVALID_STRING;
        this.javaClassPath = ubProperties.INVALID_STRING;
        this.javaPreferIPv4Stack = ubProperties.INVALID_STRING;
        this.javaPreferIPv6Addresses = ubProperties.INVALID_STRING;
        this.canonicalName = ubProperties.INVALID_STRING;
        this.sslClientParams = null;
        this.parentPID = -1;
        this.ipver = 0;
    }
    
    private IAppLogger processAdapterArgs(final String[] array, final IAppLogger appLogger, final String str) {
        final Getopt getopt = new Getopt(array);
        if (this.portNum == -1 && (this.portNum = this.getIntProperty(str + "portNumber", -1)) == -1) {
            this.missingProperty(appLogger, "portNumber");
        }
        if ((this.serverMode = this.getEnumProperty(this.getValueAsString("operatingMode"), ubProperties.STRING_SERVER_MODES, ubProperties.INT_SERVER_MODES, -1)) == -1) {
            this.serverMode = 0;
        }
        if (this.serverType == 7) {
            this.putValueAsString("srvrLoggingLevel", this.getValueAsString("mqSrvrLoggingLevel"));
            this.putValueAsString("srvrLogAppend", this.getValueAsString("mqSrvrLogAppend"));
            this.putValueAsString("srvrLogThreshold", this.getValueAsString("mqSrvrLogThreshold"));
            this.putValueAsString("srvrLogEntryTypes", this.getValueAsString("mqSrvrLogEntryTypes"));
            this.putValueAsString("srvrNumLogFiles", this.getValueAsString("mqSrvrNumLogFiles"));
            this.putValueAsString("srvrLogFile", this.getValueAsString("mqServerLogFile"));
        }
        else {
            final String valueAsString = this.getValueAsString("srvrLogFile");
            if (valueAsString.equals(ubProperties.INVALID_STRING)) {
                this.missingProperty(appLogger, "srvrLogFile");
            }
            if (this.serverType == 6 && this.parentPID != -1) {
                this.adjustServerLogFileValue(this.bldCCLogFilename(valueAsString, this.parentPID));
            }
        }
        this.setServerMode(0);
        this.putValueAsInt("srvrSelectionScheme", 0);
        this.putValueAsInt("srvrLogWatchdogInterval", 60);
        this.putValueAsInt("priorityWeight", 0);
        this.putValueAsInt("defaultService", 0);
        this.putValueAsInt("maxIdleServers", this.getValueAsInt("maxAdptrThreads"));
        this.putValueAsInt("minIdleServers", this.getValueAsInt("minAdptrThreads"));
        this.putValueAsInt("maxSrvrInstance", this.getValueAsInt("maxAdptrThreads"));
        this.putValueAsInt("initialSrvrInstance", this.getValueAsInt("initialAdptrThreads"));
        this.putValueAsInt("serverLifespan", 0);
        this.putValueAsInt("serverLifespanPadding", 5);
        this.putValueAsInt("queueLimit", 0);
        this.putValueAsInt("brkrSpinInterval", 3000);
        this.putValueAsInt("srvrStartupTimeout", 3);
        this.putValueAsInt("requestTimeout", 15);
        this.putValueAsInt("connectingTimeout", 60);
        this.canonicalName = System.getProperty("CanonicalName");
        return appLogger;
    }
    
    public IAppLogger processArgs(final String[] array) {
        final Getopt getopt = new Getopt(array);
        String optArg = null;
        AppLogger appLogger;
        try {
            appLogger = new AppLogger(new DefaultLogHandler(), 1, 0L, "UB", "UBroker");
        }
        catch (IOException ex5) {
            appLogger = new AppLogger();
        }
        if (appLogger != null) {
            appLogger.setExecEnvId("UB");
            appLogger.setLoggingLevel(2);
            appLogger.setLogEntries(1L, false, new byte[64]);
        }
        int opt;
        while ((opt = getopt.getOpt("i:f:t:r:p:d:c:m:")) != -1) {
            switch (opt) {
                case 102: {
                    this.propertiesfilename = getopt.getOptArg();
                    continue;
                }
                case 105: {
                    this.brokerName = getopt.getOptArg();
                    continue;
                }
                case 100: {
                    this.installDir = getopt.getOptArg();
                    continue;
                }
                case 116: {
                    optArg = getopt.getOptArg();
                    continue;
                }
                case 114: {
                    this.rmiURL = getopt.getOptArg();
                    continue;
                }
                case 112: {
                    try {
                        this.portNum = Integer.parseInt(getopt.getOptArg());
                    }
                    catch (NumberFormatException ex) {
                        this.fatalError(appLogger, 7665689515738013945L, new Object[] { ex.getMessage() });
                    }
                    continue;
                }
                case 99: {
                    try {
                        this.parentPID = Integer.parseInt(getopt.getOptArg());
                    }
                    catch (NumberFormatException ex2) {
                        this.fatalError(appLogger, "Specified parent PID is not numeric : " + ex2.getMessage());
                    }
                    continue;
                }
                case 109: {
                    this.unpackSCLoggingProperties(getopt.getOptArg());
                    continue;
                }
                default: {
                    this.fatalError(appLogger, 7665689515738013947L, new Object[] { getopt.getOptArg() });
                    continue;
                }
            }
        }
        if (this.installDir != ubProperties.INVALID_STRING) {
            final Properties properties = System.getProperties();
            ((Hashtable<String, String>)properties).put("Install.Dir", this.installDir);
            System.setProperties(properties);
        }
        else {
            this.installDir = System.getProperty("Install.Dir");
        }
        this.canonicalName = System.getProperty("CanonicalName");
        try {
            ExceptionMessageAdapter.setMessageSubsystem(new PromsgsFile());
        }
        catch (PromsgsFile.PromsgsFileIOException obj) {
            this.fatalError("Unable to load promsgs file : " + obj + " : " + obj.getMessage());
        }
        if (this.propertiesfilename == ubProperties.INVALID_STRING) {
            this.propertiesfilename = PropFilename.getFullPath();
        }
        if (this.brokerName == ubProperties.INVALID_STRING) {
            this.fatalError(appLogger, 7665689515738013948L, new Object[0]);
        }
        if (optArg == ubProperties.INVALID_STRING) {
            this.fatalError(appLogger, 7665689515738013949L, new Object[0]);
        }
        if ((this.serverType = this.getEnumProperty(optArg, ubProperties.STRING_SERVER_TYPES, ubProperties.INT_SERVER_TYPES, -1)) == -1) {
            this.fatalError(appLogger, 7665689515738013950L, new Object[] { optArg });
        }
        if (this.serverType == 7 && this.getValueAsString("mqBrokerLogFile") == ubProperties.INVALID_STRING) {
            this.fatalError(appLogger, 7665689515738018161L, new Object[0]);
        }
        this.setGetPropertyFilter(this.propFilter);
        try {
            if (this.serverType != 7 && this.serverType != 6) {
                this.loadSchema("ubroker.schema");
            }
            else {
                this.unrestrictRootGroups();
                this.loadSchema("adapter.schema");
            }
            this.load(this.propertiesfilename);
        }
        catch (ProException ex3) {
            this.fatalError(appLogger, 7665689515738013951L, new Object[] { this.propertiesfilename, ex3.toString(), ex3.getMessage() });
        }
        String str;
        if (optArg.equals("AD")) {
            str = "Adapter";
        }
        else if (optArg.equals("CC") || optArg.equals("SC")) {
            str = "Adapter." + optArg;
        }
        else {
            str = "UBroker." + optArg;
        }
        final String string = str + "." + this.brokerName;
        this.checkValidSection(appLogger, string);
        this.propGroupName = string;
        final String string2 = string + ".";
        this.activeProps = this.cloneProperties(this.propGroupName);
        if (this.activeProps == null) {
            this.fatalError(appLogger, 7665689515738013951L, new Object[] { this.propertiesfilename });
        }
        if (this.serverType == 7) {
            this.putValueAsString("brkrLoggingLevel", this.getValueAsString("mqBrkrLoggingLevel"));
            this.putValueAsString("brkrLogAppend", this.getValueAsString("mqBrkrLogAppend"));
            this.putValueAsString("brkrLogThreshold", this.getValueAsString("mqBrkrLogThreshold"));
            this.putValueAsString("brkrLogEntryTypes", this.getValueAsString("mqBrkrLogEntryTypes"));
            this.putValueAsString("brkrNumLogFiles", this.getValueAsString("mqBrkrNumLogFiles"));
            this.putValueAsString("brokerLogFile", this.getValueAsString("mqBrokerLogFile"));
        }
        else {
            final String valueAsString = this.getValueAsString("brokerLogFile");
            if (valueAsString.equals(ubProperties.INVALID_STRING)) {
                this.missingProperty(appLogger, "brokerLogFile");
            }
            if (this.serverType == 6 && this.parentPID != -1) {
                this.putValueAsString("brokerLogFile", this.bldCCLogFilename(valueAsString, this.parentPID));
            }
        }
        try {
            appLogger = new AppLogger(this.getValueAsString("brokerLogFile"), this.getValueAsInt("brkrLogAppend"), this.getValueAsInt("brkrLoggingLevel"), this.getValueAsInt("brkrLogThreshold"), this.getValueAsInt("brkrNumLogFiles"), this.getValueAsString("brkrLogEntryTypes"), "UB", "UBroker");
        }
        catch (IOException ex4) {
            this.fatalError(appLogger, 7665689515738013952L, new Object[] { this.getValueAsString("brokerLogFile"), ex4.toString(), ex4.getMessage() });
        }
        if (optArg.equals("AD") || optArg.equals("CC") || optArg.equals("SC")) {
            this.processAdapterArgs(array, appLogger, string2);
            if ((this.serverType == 6 || this.serverType == 7) && this.parentPID == -1) {
                this.fatalError(appLogger, 7665689515738018162L, new Object[] { optArg });
            }
            if (this.serverType != 6 && this.serverType != 7 && this.parentPID != -1) {
                this.fatalError(appLogger, 7665689515738018163L, new Object[] { optArg });
            }
            if (this.serverType == 6 || this.serverType == 7) {
                this.putValueAsInt("portNumber", this.portNum = 0);
            }
        }
        else {
            if (this.portNum == -1 && (this.portNum = this.getIntProperty(string2 + "portNumber", -1)) == -1) {
                this.missingProperty(appLogger, "portNumber");
            }
            if ((this.serverMode = this.getEnumProperty(this.getValueAsString("operatingMode"), ubProperties.STRING_SERVER_MODES, ubProperties.INT_SERVER_MODES, -1)) == -1) {
                this.fatalError(appLogger, 7665689515738013953L, new Object[] { "operatingMode", this.getValueAsString("operatingMode") });
            }
            if (this.getValueAsInt("mqEnabled") > 0) {
                this.processAdminServerPluginsArgs(this.bldAdminServerPluginsFilename(this.propertiesfilename), "PluginPolicy.Progress.SonicMQ", appLogger);
                this.putValueAsString("mqStartupParms", this.bldServerConnectArgs(this.propertiesfilename));
            }
            this.putValueAsInt("maxIdleServers", this.getValueAsInt("maxSrvrInstance"));
            this.putValueAsInt("minIdleServers", this.getValueAsInt("minSrvrInstance"));
        }
        if ((this.ipver = this.getEnumProperty(this.getValueAsString("ipver"), ubProperties.STRING_IPVERSIONS, ubProperties.INT_IPVERSIONS, -1)) == -1) {
            this.fatalError(appLogger, 7665689515738020690L, new Object[] { "ipver", this.getValueAsString("ipver") });
        }
        if ((this.regMode = this.getEnumProperty(this.getValueAsString("registrationMode"), ubProperties.STRING_REG_MODES, ubProperties.INT_REG_MODES, -1)) == -1) {
            this.fatalError(appLogger, 7665689515738013953L, new Object[] { "registrationMode", this.getValueAsString("registrationMode") });
        }
        if ((this.controllingNS = this.getProperty(string2 + "controllingNameServer", ubProperties.INVALID_STRING)) == ubProperties.INVALID_STRING) {
            this.missingProperty(appLogger, "controllingNameServer");
        }
        final int intProperty;
        if ((intProperty = this.getIntProperty(string2 + "registerNameServer", -1)) == -1) {
            this.missingProperty(appLogger, "registerNameServer");
        }
        if (this.controllingNS.length() > 0 && intProperty != 0) {
            this.checkValidSection(appLogger, "NameServer." + this.controllingNS);
            final String string3 = "NameServer." + this.controllingNS + ".";
            if ((this.nsHost = this.getProperty(string3 + "hostName", ubProperties.INVALID_STRING)) == ubProperties.INVALID_STRING) {
                this.missingProperty(appLogger, "hostName");
            }
            if ((this.nsPortnum = this.getIntProperty(string3 + "portNumber", -1)) == -1) {
                this.missingProperty(appLogger, "portNumber");
            }
        }
        int intProperty2;
        int intProperty3;
        if (this.serverType == 6 || this.serverType == 7) {
            intProperty2 = 6;
            intProperty3 = 3000;
        }
        else {
            this.checkValidSection(appLogger, "PreferenceRoot.Preference");
            final String s = "PreferenceRoot.Preference.";
            intProperty2 = this.getIntProperty(s + "admSrvrRegisteredRetry", 6);
            intProperty3 = this.getIntProperty(s + "admSrvrRegisteredRetryInterval", 3000);
        }
        this.putValueAsInt("admSrvrRegisteredRetry", intProperty2);
        this.putValueAsInt("admSrvrRegisteredRetryInterval", intProperty3);
        this.osName = System.getProperty("os.name");
        this.osVersion = System.getProperty("os.version");
        this.javaVersion = System.getProperty("java.version");
        this.javaClassPath = System.getProperty("java.class.path");
        this.userDirectory = System.getProperty("user.dir");
        this.javaPreferIPv4Stack = System.getProperty("java.net.preferIPv4Stack");
        this.javaPreferIPv6Addresses = System.getProperty("java.net.preferIPv6Addresses");
        if (this.serverType == 0 || this.serverType == 4) {
            this.checkValidASKTimeouts(appLogger);
        }
        else {
            this.putValueAsString("appServerKeepaliveCapabilities", "denyClientASK,denyServerASK");
            this.putValueAsInt("serverASKActivityTimeout", 60);
            this.putValueAsInt("serverASKResponseTimeout", 60);
        }
        this.putValueAsInt("soTimeout", 0);
        this.putValueAsInt("parentWatchdogInterval", 3000);
        this.putValueAsInt("clientThreadPriority", 5);
        this.putValueAsInt("serverThreadPriority", 5);
        this.putValueAsInt("listenerThreadPriority", 6);
        this.putValueAsInt("watchdogThreadPriority", 6);
        final int adjustMaxServerCnt = this.adjustMaxServerCnt(appLogger, this.getValueAsInt("maxSrvrInstance"));
        this.putValueAsInt("maxSrvrInstance", adjustMaxServerCnt);
        this.putValueAsInt("initialSrvrInstance", this.adjustInitialServerCnt(this.getValueAsInt("initialSrvrInstance"), adjustMaxServerCnt));
        if (this.serverType == 1) {
            this.setServerMode(1);
        }
        if (this.serverType == 2 || this.serverType == 3 || this.serverType == 5) {
            this.setServerMode(1);
        }
        this.env = new Environment();
        this.brokerPid = this.env.getCurrent_PID_JNI(0);
        this.localHost = this.brokerHostName(this.regMode, appLogger);
        this.processSSLArgs(string2, appLogger);
        if (this.serverType != 7 && this.serverType != 6 && this.getIntProperty(string2 + "allowRuntimeUpdates", 0) != 0) {
            this.startPropertyFileMonitor();
        }
        return appLogger;
    }
    
    public void setServerMode(final int serverMode) {
        this.putValueAsInt("operatingMode", this.serverMode = serverMode);
    }
    
    public void setListenerThread(final ubListenerThread listenerThread) {
        this.listenerThread = listenerThread;
    }
    
    public Hashtable list() {
        return this.list(null, null);
    }
    
    public Hashtable list(final String s) {
        return this.list(s, null);
    }
    
    public Hashtable list(final String regex, final String regex2) {
        final Hashtable<String, String> hashtable = new Hashtable<String, String>(this.activeProps.size());
        final Enumeration<String> keys = this.activeProps.keys();
        while (keys.hasMoreElements()) {
            final String s = keys.nextElement();
            String value = this.getValueAsString(s);
            final Property property = this.activeProps.get(s.toLowerCase());
            if (s.equals("registrationMode".toLowerCase())) {
                value = ubProperties.STRING_REG_MODES[this.getEnumProperty(this.getValueAsString("registrationMode"), ubProperties.STRING_REG_MODES, ubProperties.INT_REG_MODES, -1)];
            }
            if (s.equals("brkrLoggingLevel".toLowerCase())) {
                value = this.getValueAsString("brkrLoggingLevel") + " (0x" + Integer.toString(this.getValueAsInt("brkrLoggingLevel"), 16) + ")";
            }
            if (s.equals("defaultService".toLowerCase())) {
                value = Boolean.toString(this.getValueAsBoolean("defaultService"));
            }
            if (s.equals("sslEnable".toLowerCase())) {
                value = Boolean.toString(this.getValueAsBoolean("sslEnable"));
            }
            if (s.equals("noSessionCache".toLowerCase())) {
                value = Boolean.toString(this.getValueAsBoolean("noSessionCache"));
            }
            if (s.equals("registerNameServer".toLowerCase())) {
                value = Boolean.toString(this.getValueAsBoolean("registerNameServer"));
            }
            try {
                if (regex == null && regex2 == null) {
                    hashtable.put(property.getName(), value);
                }
                else if (regex != null && property.getName().matches(regex)) {
                    hashtable.put(property.getName(), value);
                }
                else {
                    if (regex2 == null || !value.matches(regex2)) {
                        continue;
                    }
                    hashtable.put(property.getName(), value);
                }
            }
            catch (Exception ex) {}
        }
        return hashtable;
    }
    
    public void print(final IAppLogger appLogger, final int n, final int n2) {
        final Vector<Comparable> list = new Vector<Comparable>(this.activeProps.size());
        appLogger.logWithThisLevel(n, n2, "**************************************");
        appLogger.logWithThisLevel(n, n2, "Property Name        :  Property Value");
        appLogger.logWithThisLevel(n, n2, "--------------------------------------");
        appLogger.logWithThisLevel(n, n2, "os.name              : " + this.osName);
        appLogger.logWithThisLevel(n, n2, "os.version           : " + this.osVersion);
        appLogger.logWithThisLevel(n, n2, "java.version         : " + this.javaVersion);
        appLogger.logWithThisLevel(n, n2, "java.class.path      : " + this.javaClassPath);
        appLogger.logWithThisLevel(n, n2, "java.net.preferIPv4Stack    : " + this.javaPreferIPv4Stack);
        appLogger.logWithThisLevel(n, n2, "java.net.preferIPv6Addresses : " + this.javaPreferIPv6Addresses);
        appLogger.logWithThisLevel(n, n2, "user.dir             : " + this.userDirectory);
        list.add("installDir           : " + this.installDir);
        list.add("properties file      : " + this.propertiesfilename);
        list.add("broker               : " + this.brokerName);
        list.add("serverType           : " + ubProperties.STRING_SERVER_TYPES[this.serverType]);
        list.add("rmiURL               : " + this.rmiURL);
        list.add("nameServer host      : " + this.nsHost);
        list.add("nameServer port      : " + this.nsPortnum);
        list.add("localHost            : " + this.localHost);
        list.add("mqLoggingParams      : " + this.getMQLoggingParams());
        final Enumeration<String> keys = (Enumeration<String>)this.activeProps.keys();
        while (keys.hasMoreElements()) {
            final String s = keys.nextElement();
            String str = this.getValueAsString(s);
            final Property property = this.activeProps.get(s.toLowerCase());
            final int n3 = 20;
            final StringBuilder sb = new StringBuilder(property.getName());
            sb.ensureCapacity(n3);
            for (int i = n3 - s.length(); i > 0; --i) {
                sb.append(" ");
            }
            sb.append(" : ");
            if (s.equals("registrationMode".toLowerCase())) {
                str = ubProperties.STRING_REG_MODES[this.getEnumProperty(this.getValueAsString("registrationMode"), ubProperties.STRING_REG_MODES, ubProperties.INT_REG_MODES, -1)];
            }
            if (s.equals("brkrLoggingLevel".toLowerCase())) {
                str = this.getValueAsString("brkrLoggingLevel") + " (0x" + Integer.toString(this.getValueAsInt("brkrLoggingLevel"), 16) + ")";
            }
            if (s.equals("defaultService".toLowerCase())) {
                str = Boolean.toString(this.getValueAsBoolean("defaultService"));
            }
            if (s.equals("sslEnable".toLowerCase())) {
                str = Boolean.toString(this.getValueAsBoolean("sslEnable"));
            }
            if (s.equals("noSessionCache".toLowerCase())) {
                str = Boolean.toString(this.getValueAsBoolean("noSessionCache"));
            }
            if (s.equals("registerNameServer".toLowerCase())) {
                str = Boolean.toString(this.getValueAsBoolean("registerNameServer"));
            }
            list.add(sb.toString() + str);
        }
        Collections.sort(list);
        for (int j = 0; j < list.size(); ++j) {
            appLogger.logWithThisLevel(n, n2, list.elementAt(j));
        }
        appLogger.logWithThisLevel(n, n2, "*********************************************");
    }
    
    public String getMQLoggingParams() {
        return this.packSCLoggingProperties();
    }
    
    public String getServerTypeString() {
        return ubProperties.STRING_SERVER_TYPES[this.serverType];
    }
    
    public IAppLogger initServerLog(final IAppLogger appLogger, final boolean b) {
        final String valueAsString = this.getValueAsString("srvrLogFile");
        final int valueAsInt = this.getValueAsInt("srvrLogAppend");
        IAppLogger appLogger2;
        if (this.serverType == 1 || this.serverType == 0) {
            final File file = new File(valueAsString);
            if ((b || valueAsInt == 0) && file.exists() && file.delete()) {
                if (appLogger.ifLogBasic(2L, 1)) {
                    appLogger.logBasic(1, valueAsString + " REMOVED.");
                }
                else if (appLogger.ifLogBasic(2L, 1)) {
                    appLogger.logBasic(1, valueAsString + " NOT REMOVED.");
                }
            }
            try {
                file.createNewFile();
            }
            catch (IOException ex) {
                if (appLogger.ifLogBasic(2L, 1)) {
                    appLogger.logBasic(1, "Exception during creation of " + valueAsString + " " + ex.getMessage());
                }
            }
            appLogger2 = appLogger;
        }
        else if (this.serverType == 4 || this.serverType == 6 || this.serverType == 7) {
            appLogger2 = appLogger;
        }
        else {
            appLogger2 = appLogger;
        }
        return appLogger2;
    }
    
    public String serverTypeString(final int n) {
        return (n >= 0 && n < ubProperties.STRING_SERVER_TYPES.length) ? ubProperties.STRING_SERVER_TYPES[n] : "";
    }
    
    public String serverModeString(final int n) {
        return (this.serverType == 1) ? "Stateless" : ((n >= 0 && n < ubProperties.STRING_SERVER_MODES.length) ? ubProperties.STRING_SERVER_MODES[n] : "");
    }
    
    public String ipverString() {
        return this.ipverString(this.ipver);
    }
    
    public String ipverString(final int n) {
        return (n >= 0 && n < ubProperties.STRING_IPVERSIONS.length) ? ubProperties.STRING_IPVERSIONS[n] : "";
    }
    
    private void adjustIPVerSystemProperties(final IAppLogger appLogger, final int i) {
        final String property = System.getProperty("java.net.preferIPv4Stack");
        final String property2 = System.getProperty("java.net.preferIPv6Addresses");
        switch (i) {
            case 0: {
                String string;
                if (property == null) {
                    System.setProperty("java.net.preferIPv4Stack", "true");
                    string = "java.net.preferIPv4Stack property set to true.";
                }
                else {
                    string = "java.net.preferIPv4Stack property already set to " + property + ".";
                }
                if (appLogger.ifLogBasic(65536L, 16)) {
                    appLogger.logBasic(16, string);
                }
                String string2;
                if (property2 == null) {
                    System.setProperty("java.net.preferIPv6Addresses", "false");
                    string2 = "java.net.preferIPv6Addresses property set to false.";
                }
                else {
                    string2 = "java.net.preferIPv6Addresses property already set to " + property2 + ".";
                }
                if (appLogger.ifLogBasic(65536L, 16)) {
                    appLogger.logBasic(16, string2);
                    break;
                }
                break;
            }
            case 1: {
                String string3;
                if (property == null) {
                    System.setProperty("java.net.preferIPv4Stack", "false");
                    string3 = "java.net.preferIPv4Stack property set to false.";
                }
                else {
                    string3 = "java.net.preferIPv4Stack property already set to " + property + ".";
                }
                if (appLogger.ifLogBasic(65536L, 16)) {
                    appLogger.logBasic(16, string3);
                }
                String string4;
                if (property2 == null) {
                    System.setProperty("java.net.preferIPv6Addresses", "true");
                    string4 = "java.net.preferIPv6Addresses property set to true.";
                }
                else {
                    string4 = "java.net.preferIPv6Addresses property already set to " + property2 + ".";
                }
                if (appLogger.ifLogBasic(65536L, 16)) {
                    appLogger.logBasic(16, string4);
                    break;
                }
                break;
            }
            default: {
                this.fatalError(appLogger, "Unknown ipver (" + i + ") specified.");
                break;
            }
        }
    }
    
    public void adjustServerLogFileValue(final String s) {
        this.putValueAsString("srvrLogFile", s);
    }
    
    public void setSSLClientParams(final ClientParams sslClientParams) {
        this.sslClientParams = sslClientParams;
    }
    
    public ClientParams getSSLClientParams() {
        return this.sslClientParams;
    }
    
    private String brokerHostName(final int n, final IAppLogger appLogger) {
        String hostName = null;
        final String valueAsString = this.getValueAsString("hostName");
        String hostName2 = null;
        try {
            switch (n) {
                case 1: {
                    hostName = (hostName2 = InetAddress.getLocalHost().getHostName());
                    break;
                }
                case 0: {
                    final InetAddress localHost = InetAddress.getLocalHost();
                    hostName = localHost.getHostName();
                    hostName2 = ((this.ipver == 1) ? this.brokerHostIPv6(appLogger) : localHost.getHostAddress());
                    break;
                }
                case 2: {
                    hostName2 = valueAsString;
                    hostName = valueAsString;
                    break;
                }
                default: {
                    this.fatalError(appLogger, "Unable to get localhost name : Invalid REGMODE parameter");
                    hostName2 = null;
                    break;
                }
            }
            if (appLogger.ifLogBasic(65536L, 16)) {
                try {
                    final InetAddress[] allByName = InetAddress.getAllByName(hostName);
                    final int length = allByName.length;
                    appLogger.logBasic(16, length + " local interface(s) for host= " + hostName);
                    for (int i = 0; i < length; ++i) {
                        appLogger.logBasic(16, "address[" + i + "] = " + allByName[i].getHostName() + " / " + allByName[i].getHostAddress());
                    }
                }
                catch (IOException ex) {
                    appLogger.logBasic(16, "unable to get local interface(s) for host= " + hostName + " : " + ex.toString());
                }
            }
        }
        catch (IOException ex2) {
            this.fatalError(appLogger, 7665689515738013921L, new Object[] { ex2.toString(), ex2.getMessage() });
            hostName2 = null;
        }
        return hostName2;
    }
    
    private int getEnumProperty(final String s, final String[] array, final int[] array2, final int n) {
        final int length = array.length;
        if (s == null) {
            return n;
        }
        int n2;
        for (n2 = 0; n2 < length && s.compareTo(array[n2]) != 0; ++n2) {}
        return (n2 < length) ? array2[n2] : n;
    }
    
    private void checkValidSection(final IAppLogger appLogger, final String s) {
        try {
            this.groups(s, true, true, true);
        }
        catch (NoSuchGroupException ex) {
            this.fatalError(appLogger, 7665689515738013954L, new Object[] { s, this.propertiesfilename });
        }
    }
    
    private void checkValidASKTimeouts(final IAppLogger appLogger) {
        final int valueAsInt = this.getValueAsInt("serverASKActivityTimeout");
        final int valueAsInt2 = this.getValueAsInt("serverASKResponseTimeout");
        if (valueAsInt < 30) {
            this.fatalError(appLogger, 7665689515738019281L, new Object[] { new Integer(30) });
        }
        if (valueAsInt2 < 30) {
            this.fatalError(appLogger, 7665689515738019282L, new Object[] { new Integer(30) });
        }
    }
    
    private int adjustMaxServerCnt(final IAppLogger appLogger, int value) {
        int n = 0;
        int value2 = value;
        LicenseMgr licenseMgr;
        try {
            licenseMgr = new LicenseMgr();
        }
        catch (LicenseMgr.CannotContactLicenseMgr cannotContactLicenseMgr) {
            this.fatalError(appLogger, 7665689515738013955L, new Object[] { cannotContactLicenseMgr.getMessage() });
            return -1;
        }
        try {
            if (this.serverType == 2) {
                n = (licenseMgr.checkR2Run(10682369) ? 1 : 0);
            }
            if (this.serverType == 3) {
                n = (licenseMgr.checkR2Run(17432576) ? 1 : 0);
            }
            if (this.serverType == 5) {
                n = (licenseMgr.checkR2Run(18743297) ? 1 : 0);
            }
            if (n != 0) {
                return value;
            }
        }
        catch (LicenseMgr.NotLicensed notLicensed) {
            this.fatalError(appLogger, 7665689515738013956L, new Object[0]);
            return -1;
        }
        try {
            n = (licenseMgr.checkR2Run(7995392) ? 1 : 0);
            return value;
        }
        catch (LicenseMgr.NotLicensed notLicensed2) {
            try {
                n = (licenseMgr.checkR2Run(8060929) ? 1 : 0);
                if (this.serverType != 4 && this.serverType != 6 && this.serverType != 7) {
                    value2 = 2;
                }
            }
            catch (LicenseMgr.NotLicensed notLicensed3) {}
            if (n == 0) {
                try {
                    n = (licenseMgr.checkR2Run(7929857) ? 1 : 0);
                    if (this.serverType != 4 && this.serverType != 6 && this.serverType != 7) {
                        value2 = 2;
                    }
                    else {
                        value2 = 30;
                    }
                }
                catch (LicenseMgr.NotLicensed notLicensed4) {}
            }
            if (this.serverType == 6) {
                n = 1;
            }
            if (n == 0) {
                this.fatalError(appLogger, 7665689515738013956L, new Object[0]);
                return -1;
            }
            if (value2 < value) {
                appLogger.logError(7665689515738013957L, new Object[] { new Integer(value), new Integer(value2) });
                value = value2;
            }
            return value;
        }
    }
    
    private int adjustInitialServerCnt(int n, final int n2) {
        if (n > n2) {
            n = n2;
        }
        return n;
    }
    
    private void missingProperty(final IAppLogger appLogger, final String s) {
        this.fatalError(appLogger, 7665689515738013958L, new Object[] { s, this.brokerName, this.propertiesfilename });
    }
    
    private void missingProperty(final IAppLogger appLogger, final String s, final String s2, final String s3) {
        this.fatalError(appLogger, 7665689515738018164L, new Object[] { s, s2, s3 });
    }
    
    private void fatalError(final String x) {
        System.err.println(x);
        System.exit(1);
    }
    
    private void fatalError(final IAppLogger appLogger, final String s) {
        appLogger.logError(s);
        System.exit(1);
    }
    
    private void fatalError(final IAppLogger appLogger, final long n, final Object[] array) {
        appLogger.logError(n, array);
        System.exit(1);
    }
    
    private boolean isNT() {
        return System.getProperty("os.name").startsWith("Windows");
    }
    
    private void processSSLArgs(final String s, final IAppLogger appLogger) {
        if (!this.getRequiredBoolean(s, "sslEnable", appLogger)) {
            return;
        }
        this.getOptionalString(s, "certStorePath");
        this.getOptionalString(s, "keyAlias");
        this.getOptionalString(s, "keyAliasPasswd");
        this.getOptionalString(s, "keyStorePath");
        this.getOptionalString(s, "keyStorePasswd");
        this.getOptionalBoolean(s, "noSessionCache");
        final int n = this.getOptionalInteger(s, "sessionTimeout") * 1000;
        this.getOptionalString(s, "sslAlgorithms");
    }
    
    private boolean getRequiredBoolean(final String s, final String s2, final IAppLogger appLogger) {
        final int valueAsInt = this.getValueAsInt(s2);
        if (valueAsInt == -1) {
            this.missingProperty(appLogger, s2);
        }
        return valueAsInt != 0;
    }
    
    private boolean getOptionalBoolean(final String s, final String s2) {
        int valueAsInt = this.getValueAsInt(s2);
        if (valueAsInt == -1) {
            valueAsInt = 0;
        }
        return valueAsInt != 0;
    }
    
    private String getOptionalString(final String s, final String s2) {
        return this.getValueAsString(s2);
    }
    
    private int getOptionalInteger(final String s, final String s2) {
        return this.getValueAsInt(s2);
    }
    
    private int getIntValue(final String s, final int n) {
        int int1;
        try {
            int1 = Integer.parseInt(s);
        }
        catch (NumberFormatException ex) {
            int1 = n;
        }
        return int1;
    }
    
    private String packSCLoggingProperties() {
        final StringBuffer sb = new StringBuffer(100);
        sb.append(this.getValueAsString("mqBrkrLogAppend") + "|");
        sb.append(this.getValueAsString("mqBrkrLogEntryTypes") + "|");
        sb.append(this.getValueAsString("mqBrkrLoggingLevel") + "|");
        sb.append(this.getValueAsString("mqBrkrLogThreshold") + "|");
        sb.append(this.getValueAsString("mqBrkrNumLogFiles") + "|");
        sb.append(this.getValueAsString("mqBrokerLogFile") + "|");
        sb.append(this.getValueAsString("mqSrvrLogAppend") + "|");
        sb.append(this.getValueAsString("mqSrvrLogEntryTypes") + "|");
        sb.append(this.getValueAsString("mqSrvrLoggingLevel") + "|");
        sb.append(this.getValueAsString("mqSrvrLogThreshold") + "|");
        sb.append(this.getValueAsString("mqSrvrNumLogFiles") + "|");
        sb.append(this.getValueAsString("mqServerLogFile") + "|");
        return sb.toString();
    }
    
    private void unpackSCLoggingProperties(final String str) {
        final StringTokenizer stringTokenizer = new StringTokenizer(str, "|", false);
        this.putValueAsString("mqBrkrLogAppend", stringTokenizer.nextToken());
        this.putValueAsString("mqBrkrLogEntryTypes", stringTokenizer.nextToken());
        this.putValueAsString("mqBrkrLoggingLevel", stringTokenizer.nextToken());
        this.putValueAsString("mqBrkrLogThreshold", stringTokenizer.nextToken());
        this.putValueAsString("mqBrkrNumLogFiles", stringTokenizer.nextToken());
        this.putValueAsString("mqBrokerLogFile", stringTokenizer.nextToken());
        this.putValueAsString("mqSrvrLogAppend", stringTokenizer.nextToken());
        this.putValueAsString("mqSrvrLogEntryTypes", stringTokenizer.nextToken());
        this.putValueAsString("mqSrvrLoggingLevel", stringTokenizer.nextToken());
        this.putValueAsString("mqSrvrLogThreshold", stringTokenizer.nextToken());
        this.putValueAsString("mqSrvrNumLogFiles", stringTokenizer.nextToken());
        this.putValueAsString("mqServerLogFile", stringTokenizer.nextToken());
    }
    
    private void processAdminServerPluginsArgs(String string, final String s, final IAppLogger appLogger) {
        final PropertyManager propertyManager = new PropertyManager();
        final String installPath = new InstallPath().getInstallPath();
        final String property = System.getProperty("file.separator");
        final String property2 = System.getProperty("os.arch");
        if (string == ubProperties.INVALID_STRING) {
            string = installPath + property + "properties" + property + "AdminServerPlugins.properties";
        }
        propertyManager.setGetPropertyFilter(new PropertyFilter(this));
        try {
            propertyManager.load(string);
        }
        catch (ProException ex) {
            this.fatalError(appLogger, 7665689515738013951L, new Object[] { string, ex.toString(), ex.getMessage() });
        }
        String s2;
        if ((s2 = propertyManager.getProperty(s + "." + "jvmexe", ubProperties.INVALID_STRING)) == ubProperties.INVALID_STRING) {
            if (property2.equals("sparcv9") || property2.equals("IA64W") || property2.equals("PA_RISC2.0W")) {
                s2 = System.getProperty("java.home") + property + "bin" + property + property2 + property + "java";
            }
            else {
                s2 = System.getProperty("java.home") + property + "bin" + property + "java" + (this.isNT() ? ".exe" : "");
            }
        }
        final String property3;
        if ((property3 = propertyManager.getProperty(s + "." + "jvmargs", ubProperties.INVALID_STRING)) == ubProperties.INVALID_STRING) {
            this.missingProperty(appLogger, "jvmargs", s, string);
        }
        final String property4;
        if ((property4 = propertyManager.getProperty(s + "." + "policyfile", ubProperties.INVALID_STRING)) == ubProperties.INVALID_STRING) {
            this.missingProperty(appLogger, "policyfile", s, string);
        }
        final String property5;
        if ((property5 = propertyManager.getProperty(s + "." + "pluginclasspath", ubProperties.INVALID_STRING)) == ubProperties.INVALID_STRING) {
            this.missingProperty(appLogger, "pluginclasspath", s, string);
        }
        final String bldClasspath = this.bldClasspath(property5);
        this.putValueAsString("jvmexe", s2);
        this.putValueAsString("jvmargs", property3);
        this.putValueAsString("policyfile", property4);
        this.putValueAsString("pluginclasspath", bldClasspath);
    }
    
    private String bldClasspath(final String str) {
        final String property = System.getProperty("path.separator");
        final StringTokenizer stringTokenizer = new StringTokenizer(str, ",", false);
        final StringBuffer sb = new StringBuffer(100);
        if (stringTokenizer.hasMoreTokens()) {
            sb.append(stringTokenizer.nextToken());
            while (stringTokenizer.hasMoreTokens()) {
                sb.append(property + stringTokenizer.nextToken());
            }
        }
        return sb.toString();
    }
    
    private String bldAdminServerPluginsFilename(final String pathname) {
        return new File(new File(pathname).getParent(), "AdminServerPlugins.properties").getAbsolutePath();
    }
    
    private String bldServerConnectArgs(final String pathname) {
        return "-t SC -i sc1 -f " + new File(new File(pathname).getParent(), "JavaTools.properties").getAbsolutePath();
    }
    
    private String bldCCLogFilename(final String s, final int n) {
        final File file = new File(s);
        final String parent = file.getParent();
        final String name = file.getName();
        final int lastIndex = name.lastIndexOf(46);
        String child;
        if (lastIndex == -1) {
            child = s + "-" + n;
        }
        else {
            child = name.substring(0, lastIndex) + "-" + n + name.substring(lastIndex);
        }
        return new File(parent, child).getAbsolutePath();
    }
    
    private String brokerHostIPv6(final IAppLogger appLogger) {
        String s = null;
        String hostName = null;
        Inet6Address inet6Address = null;
        try {
            hostName = InetAddress.getLocalHost().getHostName();
            final InetAddress[] allByName = InetAddress.getAllByName(hostName);
            final int length = allByName.length;
            if (appLogger.ifLogVerbose(65536L, 16)) {
                appLogger.logVerbose(16, length + " interface(s) for host= " + hostName);
            }
            if (appLogger.ifLogVerbose(65536L, 16)) {
                for (int i = 0; i < length; ++i) {
                    if (allByName[i] instanceof Inet6Address) {
                        final Inet6Address inet6Address2 = (Inet6Address)allByName[i];
                        appLogger.logVerbose(16, "address[" + i + "] = " + inet6Address2.getHostAddress() + "  INET6" + "  linkLocal=" + inet6Address2.isLinkLocalAddress() + "  siteLocal=" + inet6Address2.isSiteLocalAddress());
                    }
                    else {
                        appLogger.logVerbose(16, "address[" + i + "] = " + allByName[i].getHostAddress() + "  INET4");
                    }
                }
            }
            for (int j = 0; j < length; ++j) {
                if (allByName[j] instanceof Inet6Address) {
                    final Inet6Address inet6Address3 = (Inet6Address)allByName[j];
                    if (inet6Address == null) {
                        inet6Address = inet6Address3;
                    }
                    if (!inet6Address3.isLinkLocalAddress() && !inet6Address3.isSiteLocalAddress()) {
                        inet6Address = inet6Address3;
                        break;
                    }
                }
            }
            s = ((inet6Address == null) ? allByName[0].getHostAddress() : inet6Address.getHostAddress());
        }
        catch (IOException ex) {
            appLogger.logError("unable to get local interface(s) for host= " + hostName + " : " + ex.toString());
        }
        return s;
    }
    
    private Hashtable cloneProperties(final String s) {
        if (this.activeProps == null) {
            this.activeProps = this.getHashtable();
        }
        Hashtable<K, Property> properties = null;
        try {
            properties = (Hashtable<K, Property>)this.properties(s, true, true).getProperties();
        }
        catch (NoSuchGroupException ex) {}
        if (properties == null) {
            return null;
        }
        final Enumeration<String> keys = properties.keys();
        while (keys.hasMoreElements()) {
            final String s2 = keys.nextElement();
            this.activeProps.put(s2, properties.get(s2).clone());
        }
        return this.activeProps;
    }
    
    private boolean putValueAsString(final String s, final String value) {
        if (s == null || value == null) {
            return false;
        }
        if (this.activeProps == null) {
            this.activeProps = this.getHashtable();
        }
        final String lowerCase = s.toLowerCase();
        final Property property = this.activeProps.get(lowerCase);
        try {
            if (property == null) {
                this.activeProps.put(lowerCase, new Property(s, value));
            }
            else {
                property.setValue(value);
            }
        }
        catch (Exception ex) {
            return false;
        }
        return true;
    }
    
    public boolean putValueAsInt(final String s, final int i) {
        return this.putValueAsString(s, Integer.toString(i));
    }
    
    public String getValueAsString(final String s) {
        if (s == null || this.activeProps == null) {
            return ubProperties.INVALID_STRING;
        }
        final Property property = this.activeProps.get(s.toLowerCase());
        if (property == null) {
            return ubProperties.INVALID_STRING;
        }
        String s2 = property.getValueOrDefault();
        if (s2 != null) {
            s2 = this.propFilter.filterValue(this.propGroupName, s, s2);
        }
        return s2;
    }
    
    public int getValueAsInt(final String s) {
        int radix = 10;
        String s2 = this.getValueAsString(s);
        if (s2 == null || s2.length() == 0) {
            return -1;
        }
        int int1;
        try {
            if (s2.toLowerCase().startsWith("0x")) {
                radix = 16;
                s2 = s2.substring(2);
            }
            int1 = Integer.parseInt(s2, radix);
        }
        catch (NumberFormatException ex) {
            return -1;
        }
        return int1;
    }
    
    public boolean getValueAsBoolean(final String s) {
        return this.getValueAsInt(s) != 0;
    }
    
    public String[] getValueAsStringArray(final String s) {
        final String valueAsString = this.getValueAsString(s);
        if (valueAsString == null) {
            return null;
        }
        return PropertyManager.parseArrayProperty(valueAsString, ",");
    }
    
    private void generatePropertyDiffList() {
        this.changedPropList.clear();
        Hashtable properties;
        try {
            properties = this.properties(this.propGroupName, true, true).getProperties();
        }
        catch (NoSuchGroupException ex) {
            return;
        }
        if (properties == null) {
            return;
        }
        final Enumeration<String> keys = (Enumeration<String>)this.activeProps.keys();
        while (keys.hasMoreElements()) {
            final String e = keys.nextElement();
            final Property property = properties.get(e);
            if (property == null) {
                continue;
            }
            final String valueAsString = this.getValueAsString(e);
            String s = property.getValueOrDefault();
            if (s != null) {
                s = this.propFilter.filterValue(this.propGroupName, e, s);
            }
            if (s.equals(valueAsString)) {
                continue;
            }
            if (e.equals("brkrLoggingLevel".toLowerCase())) {
                this.changedPropList.add(0, e);
            }
            else {
                this.changedPropList.add(e);
            }
        }
    }
    
    public boolean updateUbProperties(final IAppLogger appLogger) {
        boolean b = false;
        final Class[] parameterTypes = { String.class, IAppLogger.class };
        final Class<? extends ubProperties> class1 = this.getClass();
        this.generatePropertyDiffList();
        if (this.changedPropList.size() == 0) {
            return false;
        }
        for (int i = 0; i < this.changedPropList.size(); ++i) {
            final String str = this.changedPropList.get(i);
            final String string = "update_" + str;
            Boolean false = Boolean.FALSE;
            try {
                final Method declaredMethod = class1.getDeclaredMethod(string, (Class[])parameterTypes);
                if (declaredMethod != null) {
                    declaredMethod.setAccessible(true);
                    false = (Boolean)declaredMethod.invoke(this, str, appLogger);
                }
                if (false.equals(Boolean.TRUE)) {
                    b = true;
                }
            }
            catch (NoSuchMethodException ex2) {}
            catch (Exception ex) {
                if (appLogger.ifLogVerbose(1L, 0)) {
                    appLogger.logVerbose(0, 7665689515738020691L, new Object[] { str, ex.getMessage() });
                }
            }
        }
        return b;
    }
    
    private String getNewValueAsString(final String s) {
        final String invalid_STRING = ubProperties.INVALID_STRING;
        String valueOrDefault;
        try {
            final Property value = this.properties(this.propGroupName, true, true).get(s);
            if (value == null) {
                return ubProperties.INVALID_STRING;
            }
            valueOrDefault = value.getValueOrDefault();
        }
        catch (NoSuchGroupException ex) {
            return ubProperties.INVALID_STRING;
        }
        return valueOrDefault;
    }
    
    private int getNewValueAsInt(final String s) {
        int radix = 10;
        String s2 = this.getNewValueAsString(s);
        if (s2 == ubProperties.INVALID_STRING || s2.length() == 0) {
            return -1;
        }
        int int1;
        try {
            if (s2.toLowerCase().startsWith("0x")) {
                radix = 16;
                s2 = s2.substring(2);
            }
            int1 = Integer.parseInt(s2, radix);
        }
        catch (NumberFormatException ex) {
            return -1;
        }
        return int1;
    }
    
    private boolean getNewValueAsBoolean(final String s) {
        return this.getNewValueAsInt(s) != 0;
    }
    
    private boolean updateValueProperty(final String s, final IAppLogger appLogger) {
        try {
            final Property property = this.activeProps.get(s.toLowerCase());
            final String valueOrDefault = property.getValueOrDefault();
            final String newValueAsString = this.getNewValueAsString(s);
            property.setValue(newValueAsString);
            if (appLogger.ifLogVerbose(1L, 0)) {
                appLogger.logVerbose(0, 7665689515738020692L, new Object[] { property.getName(), valueOrDefault, newValueAsString });
            }
        }
        catch (PropertyValueException ex) {
            return false;
        }
        return true;
    }
    
    private boolean update_actionalenabled(final String s, final IAppLogger appLogger) {
        return this.updateValueProperty(s, appLogger);
    }
    
    private boolean update_actionalgroup(final String s, final IAppLogger appLogger) {
        return this.updateValueProperty(s, appLogger);
    }
    
    private boolean update_collectstatsdata(final String s, final IAppLogger appLogger) {
        return this.updateValueProperty(s, appLogger);
    }
    
    private boolean update_connectingtimeout(final String s, final IAppLogger appLogger) {
        return this.updateValueProperty(s, appLogger);
    }
    
    private boolean update_flushstatsdata(final String s, final IAppLogger appLogger) {
        return this.updateValueProperty(s, appLogger);
    }
    
    private boolean update_priorityweight(final String s, final IAppLogger appLogger) {
        return this.updateValueProperty(s, appLogger);
    }
    
    private boolean update_requesttimeout(final String s, final IAppLogger appLogger) {
        return this.updateValueProperty(s, appLogger);
    }
    
    private boolean update_srvractivateproc(final String s, final IAppLogger appLogger) {
        return this.updateValueProperty(s, appLogger);
    }
    
    private boolean update_srvrconnectproc(final String s, final IAppLogger appLogger) {
        return this.updateValueProperty(s, appLogger);
    }
    
    private boolean update_srvrdeactivateproc(final String s, final IAppLogger appLogger) {
        return this.updateValueProperty(s, appLogger);
    }
    
    private boolean update_srvrdisconnproc(final String s, final IAppLogger appLogger) {
        return this.updateValueProperty(s, appLogger);
    }
    
    private boolean update_srvrexecfile(final String s, final IAppLogger appLogger) {
        return this.updateValueProperty(s, appLogger);
    }
    
    private boolean update_srvrmaxport(final String s, final IAppLogger appLogger) {
        return this.updateValueProperty(s, appLogger);
    }
    
    private boolean update_srvrminport(final String s, final IAppLogger appLogger) {
        return this.updateValueProperty(s, appLogger);
    }
    
    private boolean update_srvrstartupparam(final String s, final IAppLogger appLogger) {
        return this.updateValueProperty(s, appLogger);
    }
    
    private boolean update_srvrstartupproc(final String s, final IAppLogger appLogger) {
        return this.updateValueProperty(s, appLogger);
    }
    
    private boolean update_srvrstartupprocparam(final String s, final IAppLogger appLogger) {
        return this.updateValueProperty(s, appLogger);
    }
    
    private boolean update_srvrstartuptimeout(final String s, final IAppLogger appLogger) {
        return this.updateValueProperty(s, appLogger);
    }
    
    private boolean update_srvrshutdownproc(final String s, final IAppLogger appLogger) {
        return this.updateValueProperty(s, appLogger);
    }
    
    private boolean update_brkrlogginglevel(final String s, final IAppLogger appLogger) {
        final int valueAsInt = this.getValueAsInt(s);
        final int newValueAsInt = this.getNewValueAsInt(s);
        if (newValueAsInt == -1) {
            return false;
        }
        boolean b;
        if (valueAsInt > newValueAsInt) {
            b = this.updateValueProperty(s, appLogger);
            appLogger.setLoggingLevel(newValueAsInt);
        }
        else {
            appLogger.setLoggingLevel(newValueAsInt);
            b = this.updateValueProperty(s, appLogger);
        }
        return b;
    }
    
    private boolean update_brkrlogentrytypes(final String s, final IAppLogger appLogger) {
        appLogger.resetLogEntries(this.getNewValueAsString(s));
        return this.updateValueProperty(s, appLogger);
    }
    
    private boolean update_srvrlogginglevel(final String s, final IAppLogger appLogger) {
        final int newValueAsInt = this.getNewValueAsInt(s);
        if (newValueAsInt == -1) {
            return false;
        }
        JavaServices.setLoggingLevel(newValueAsInt);
        return this.updateValueProperty(s, appLogger);
    }
    
    private boolean update_srvrlogentrytypes(final String s, final IAppLogger appLogger) {
        return this.updateValueProperty(s, appLogger);
    }
    
    private boolean update_autotrimtimeout(final String s, final IAppLogger appLogger) {
        if (this.serverType == 6 || this.serverType == 7 || this.serverType == 4) {
            return false;
        }
        final int newValueAsInt = this.getNewValueAsInt(s);
        if (newValueAsInt == -1 || this.listenerThread == null) {
            return false;
        }
        this.listenerThread.setAutoTrimTimeout(newValueAsInt);
        return this.updateValueProperty(s, appLogger);
    }
    
    static {
        INVALID_STRING = null;
        INT_SERVER_MODES = new int[] { 0, 1, 2, 3 };
        STRING_SERVER_MODES = new String[] { "Stateless", "State-aware", "State-reset", "State-free" };
        INT_REG_MODES = new int[] { 0, 1, 2 };
        STRING_REG_MODES = new String[] { "Register-IP", "Register-LocalHost", "Register-HostName" };
        INT_SERVER_TYPES = new int[] { 0, 1, 2, 3, 4, 5, 6, 7 };
        STRING_SERVER_TYPES = new String[] { "AS", "WS", "OD", "OR", "AD", "MS", "CC", "SC" };
        INT_IPVERSIONS = new int[] { 0, 1 };
        STRING_IPVERSIONS = new String[] { "IPv4", "IPv6" };
    }
}
