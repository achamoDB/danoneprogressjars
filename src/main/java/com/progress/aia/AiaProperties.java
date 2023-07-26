// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.aia;

import javax.servlet.UnavailableException;
import com.progress.common.exception.ProException;
import com.progress.common.util.PropertyFilter;
import com.progress.ubroker.util.PropFilename;
import com.progress.common.message.IProMessage;
import com.progress.common.exception.ExceptionMessageAdapter;
import com.progress.common.util.PromsgsBundle;
import java.io.IOException;
import com.progress.common.ehnlog.LogHandler;
import com.progress.common.ehnlog.AppLogger;
import com.progress.common.ehnlog.DefaultLogHandler;
import com.progress.common.ehnlog.IAppLogger;
import javax.servlet.ServletConfig;
import javax.servlet.Servlet;
import com.progress.ubroker.util.ubConstants;
import com.progress.common.property.PropertyManager;

public class AiaProperties extends PropertyManager implements ubConstants, IAiaDispInfoConst, IAiaDisplayInfo
{
    private static final int LOGFILEMODE_NEWFILE = 0;
    private static final int LOGFILEMODE_APPEND = 1;
    private static final int CMDS_ALLOWED = 1;
    public static final String PROPNAME_INSTANCENAME = "instanceName";
    public static final String PROPNAME_INSTALLDIR = "InstallDir";
    public static final String PROPNAME_PROPFILENAME = "propertyFileName";
    public static final String PROPNAME_HTTPSENABLED = "httpsEnabled";
    public static final String PROPNAME_LOGFILENAME = "logFile";
    public static final String PROPNAME_LOGGINGLEVEL = "loggingLevel";
    public static final String PROPNAME_LOGFILEMODE = "logAppend";
    public static final String PROPNAME_LOGENTRIES = "logEntries";
    public static final String PROPNAME_LOGENTRYTYPES = "logEntryTypes";
    public static final String PROPNAME_LOGTHRESHOLD = "logThreshold";
    public static final String PROPNAME_NUMLOGFILES = "numLogFiles";
    public static final String PROPNAME_NAMESERVER = "controllingNameServer";
    public static final String PROPNAME_REGNAMESERVER = "registerNameServer";
    public static final String PROPNAME_BROKHOSTNAME = "Host";
    public static final String PROPNAME_BROKPORTNUMBER = "Port";
    public static final String PROPNAME_SOTIMEOUT = "soReadTimeout";
    public static final String PROPNAME_CONNTRIMTIMEOUT = "idleConnectionTimeout";
    public static final String PROPNAME_SECUREPORT = "securePort";
    public static final String PROPNAME_MINNSCLIENTPORT = "minNSClientPort";
    public static final String PROPNAME_MAXNSCLIENTPORT = "maxNSClientPort";
    public static final String PROPNAME_NSCLIENTPORT_RETRY_INTERVAL = "nsClientPortRetryInterval";
    public static final String PROPNAME_NSCLIENTPORT_RETRY = "nsClientPortRetry";
    public static final String PROPNAME_ALLOWCMDS = "allowAiaCmds";
    public static final String PROPNAME_ADMINIPLIST = "adminIPList";
    public static final String NAMESERVER_SECTION_NAME = "NameServer";
    public static final String PROPNAME_NSHOST = "hostName";
    public static final String PROPNAME_NSPORTNUM = "portNumber";
    public static final String PROPNAME_CERTSTOREPATH = "certStorePath";
    public static final String PROPNAME_NOHOSTVERIFY = "noHostVerify";
    public static final String PROPNAME_NOSESSIONREUSE = "noSessionReuse";
    public static final String PROPNAME_SSLENABLE = "sslEnable";
    public static final String PROPNAME_ASK_KEEPALIVE = "appServerKeepalive";
    public static final String PROPNAME_CASK_ACTIVITY_TIMEOUT = "clientASKActivityTimeout";
    public static final String PROPNAME_CASK_RESPONSE_TIMEOUT = "clientASKResponseTimeout";
    public static final String PROPNAME_ACTIONAL_ENABLED = "actionalEnabled";
    public static final String PROPNAME_ACTIONAL_GROUP = "actionalGroup";
    public static final String AIA_SECTION_NAME = "Aia";
    public static final String DEF_PROPFILENAME = "ubroker.properties";
    public static final int DEF_LOGGING_LEVEL = 2;
    public static final int DEF_LOGAPPEND = 0;
    public static final int DEF_LOGTHRESHOLD = 0;
    public static final String DEF_LOGENTRYTYPES = "AIADefault";
    public static final int DEF_NUMLOGFILES = 3;
    public static final int DEF_SECUREPORT = 443;
    public static final int DEF_NSCLIENTPORT = 0;
    public static final int DEF_SOTIMEOUT = 240;
    public static final int DEF_ALLOWCMDS = 0;
    private static final String INVALID_STRING;
    private static final int INVALID_INT = -1;
    public Servlet thisServlet;
    public String instanceName;
    public String propertyFileName;
    public String installDir;
    public boolean httpsEnabled;
    public String logfilename;
    public int loggingLevel;
    public int logAppend;
    public int logThreshold;
    public String logEntryTypes;
    public int numLogFiles;
    public String nameServer;
    public String nsHost;
    public int nsPortnum;
    public String bHost;
    public int bPortnum;
    public boolean regNameServer;
    public int connTrimTimeout;
    public int securePort;
    public int minNSClientPort;
    public int maxNSClientPort;
    public int nsClientPortRetryInterval;
    public int nsClientPortRetry;
    public int soReadTimeout;
    public int allowAiaCmds;
    public String adminIPs;
    public String[] adminIPList;
    public String certStorePath;
    public boolean noHostVerify;
    public boolean noSessionReuse;
    public boolean sslEnable;
    public int clntAskCaps;
    public int clientASKActivityTimeout;
    public int clientASKResponseTimeout;
    public boolean actionalEnabled;
    public String actionalGroup;
    
    public AiaProperties() {
        this.instanceName = AiaProperties.INVALID_STRING;
        this.propertyFileName = AiaProperties.INVALID_STRING;
        this.installDir = AiaProperties.INVALID_STRING;
        this.httpsEnabled = false;
        this.logfilename = AiaProperties.INVALID_STRING;
        this.loggingLevel = 2;
        this.logAppend = 0;
        this.logThreshold = 0;
        this.logEntryTypes = "AIADefault";
        this.numLogFiles = -1;
        this.nameServer = AiaProperties.INVALID_STRING;
        this.nsHost = AiaProperties.INVALID_STRING;
        this.nsPortnum = -1;
        this.bHost = AiaProperties.INVALID_STRING;
        this.bPortnum = -1;
        this.regNameServer = true;
        this.connTrimTimeout = -1;
        this.securePort = -1;
        this.minNSClientPort = -1;
        this.minNSClientPort = -1;
        this.nsClientPortRetry = -1;
        this.nsClientPortRetryInterval = -1;
        this.soReadTimeout = -1;
        this.allowAiaCmds = -1;
        this.adminIPs = AiaProperties.INVALID_STRING;
        this.adminIPList = null;
        this.certStorePath = AiaProperties.INVALID_STRING;
        this.noHostVerify = false;
        this.noSessionReuse = false;
        this.sslEnable = false;
        this.clntAskCaps = parseAskCapabilities("denyClientASK,allowServerASK");
        this.clientASKActivityTimeout = 60;
        this.clientASKResponseTimeout = 60;
        this.actionalEnabled = false;
        this.actionalGroup = AiaProperties.INVALID_STRING;
    }
    
    public IAppLogger processArgs(final Servlet thisServlet, final ServletConfig servletConfig) throws UnavailableException {
        AppLogger appLogger;
        try {
            appLogger = new AppLogger(new DefaultLogHandler(), 1, 0L, "AIA", "Aia");
        }
        catch (IOException ex4) {
            appLogger = new AppLogger();
        }
        if (appLogger != null) {
            appLogger.setExecEnvId("AIA");
            appLogger.setLoggingLevel(2);
            appLogger.setLogEntries(3L, false, new byte[64]);
        }
        this.thisServlet = thisServlet;
        try {
            this.instanceName = servletConfig.getInitParameter("instanceName");
            if (this.instanceName == AiaProperties.INVALID_STRING) {
                this.fatalError(appLogger, "instanceName argument is not specified.");
            }
            this.propertyFileName = servletConfig.getInitParameter("propertyFileName");
            this.installDir = servletConfig.getInitParameter("InstallDir");
            if (this.installDir == AiaProperties.INVALID_STRING) {
                this.fatalError(appLogger, "InstallDir argument is not specified.");
            }
            ExceptionMessageAdapter.setMessageSubsystem(new PromsgsBundle());
            if (this.propertyFileName == AiaProperties.INVALID_STRING) {
                this.propertyFileName = PropFilename.getFullPath();
            }
            try {
                this.setGetPropertyFilter(new PropertyFilter(this));
            }
            catch (Throwable t2) {
                appLogger.logError("Failed to new a PropertyFilter. Maybe due to WSA is also running. Ignore and continue.");
            }
            try {
                this.load(this.propertyFileName);
            }
            catch (ProException ex) {
                this.fatalError(appLogger, 7665689515738013951L, new Object[] { this.propertyFileName, ex.toString(), ex.getMessage() });
            }
            this.checkValidSection(appLogger, "Aia." + this.instanceName);
            final String string = "Aia." + this.instanceName + ".";
            this.loggingLevel = this.getIntProperty(string + "loggingLevel", 2);
            this.logAppend = this.getIntProperty(string + "logAppend", 0);
            this.logThreshold = this.getIntProperty(string + "logThreshold", 0);
            this.numLogFiles = this.getIntProperty(string + "numLogFiles", 3);
            this.logEntryTypes = this.getProperty(string + "logEntryTypes", "AIADefault");
            final String property = this.getProperty(string + "logFile", AiaProperties.INVALID_STRING);
            this.logfilename = property;
            if (property == AiaProperties.INVALID_STRING) {
                this.missingProperty(appLogger, "logFile");
            }
            else {
                try {
                    appLogger = new AppLogger(this.logfilename, this.logAppend, this.loggingLevel, this.logThreshold, this.numLogFiles, this.logEntryTypes, "AIA", "Aia");
                }
                catch (IOException ex2) {
                    this.fatalError(appLogger, "Could not open " + this.logfilename + " : " + ex2.toString());
                }
            }
            final int intProperty;
            if ((intProperty = this.getIntProperty(string + "httpsEnabled", -1)) == -1) {
                this.missingProperty(appLogger, "httpsEnabled");
            }
            this.httpsEnabled = (intProperty != 0);
            if ((this.connTrimTimeout = this.getIntProperty(string + "idleConnectionTimeout", -1)) == -1) {
                this.missingProperty(appLogger, "idleConnectionTimeout");
            }
            this.connTrimTimeout *= 1000;
            this.nameServer = this.getProperty(string + "controllingNameServer", AiaProperties.INVALID_STRING);
            final int intProperty2;
            if ((intProperty2 = this.getIntProperty(string + "registerNameServer", -1)) == -1) {
                this.missingProperty(appLogger, "registerNameServer");
            }
            this.regNameServer = (intProperty2 != 0);
            if (this.nameServer.length() > 0 && this.regNameServer) {
                this.checkValidSection(appLogger, "NameServer." + this.nameServer);
                final String string2 = "NameServer." + this.nameServer + ".";
                if ((this.nsHost = this.getProperty(string2 + "hostName", AiaProperties.INVALID_STRING)) == AiaProperties.INVALID_STRING) {
                    this.missingProperty(appLogger, "hostName");
                }
                if ((this.nsPortnum = this.getIntProperty(string2 + "portNumber", -1)) == -1) {
                    this.missingProperty(appLogger, "portNumber");
                }
                this.minNSClientPort = this.getIntProperty(string + "minNSClientPort", 0);
                this.maxNSClientPort = this.getIntProperty(string + "maxNSClientPort", 0);
                this.nsClientPortRetryInterval = this.getIntProperty(string + "nsClientPortRetryInterval", 0);
                this.nsClientPortRetry = this.getIntProperty(string + "nsClientPortRetry", 0);
            }
            else {
                if ((this.bHost = this.getProperty(string + "Host", AiaProperties.INVALID_STRING)) == AiaProperties.INVALID_STRING) {
                    this.missingProperty(appLogger, "Host");
                }
                if ((this.bPortnum = this.getIntProperty(string + "Port", -1)) == -1) {
                    this.missingProperty(appLogger, "Port");
                }
            }
            this.securePort = this.getIntProperty(string + "securePort", 443);
            this.soReadTimeout = this.getIntProperty(string + "soReadTimeout", 240);
            this.soReadTimeout *= 1000;
            this.allowAiaCmds = this.getIntProperty(string + "allowAiaCmds", 0);
            this.adminIPs = this.getProperty(string + "adminIPList", AiaProperties.INVALID_STRING);
            this.adminIPList = this.getArrayProperty(string + "adminIPList");
            this.certStorePath = this.getProperty(string + "certStorePath", AiaProperties.INVALID_STRING);
            this.noHostVerify = (this.getIntProperty(string + "noHostVerify", -1) > 0);
            this.noSessionReuse = (this.getIntProperty(string + "noSessionReuse", -1) > 0);
            this.sslEnable = (this.getIntProperty(string + "sslEnable", -1) > 0);
            this.clientASKActivityTimeout = this.getIntProperty(string + "clientASKActivityTimeout", 60);
            this.clientASKResponseTimeout = this.getIntProperty(string + "clientASKResponseTimeout", 60);
            this.clntAskCaps = parseAskCapabilities(this.getProperty(string + "appServerKeepalive", "denyClientASK,allowServerASK"));
            this.actionalEnabled = (this.getIntProperty(string + "actionalEnabled", -1) > 0);
            this.actionalGroup = this.getProperty(string + "actionalGroup", AiaProperties.INVALID_STRING);
        }
        catch (UnavailableException ex3) {
            appLogger.logStackTrace("UnavailableException in processArgs()", (Throwable)ex3);
            throw ex3;
        }
        catch (Throwable t) {
            appLogger.logStackTrace("unexpected error in processArgs()", t);
            throw new UnavailableException(this.thisServlet, t.toString());
        }
        return appLogger;
    }
    
    public void print(final IAppLogger appLogger) {
        appLogger.logBasic(1, "installDir                : " + this.installDir);
        appLogger.logBasic(1, "properties file           : " + this.propertyFileName);
        appLogger.logBasic(1, "instanceName              : " + this.instanceName);
        appLogger.logBasic(1, "logfilename               : " + this.logfilename);
        appLogger.logBasic(1, "loggingLevel              : " + this.loggingLevel);
        appLogger.logBasic(1, "logAppend                 : " + this.logAppend);
        appLogger.logBasic(1, "logThreshold              : " + this.logThreshold);
        appLogger.logBasic(1, "logEntryTypes             : " + this.logEntryTypes);
        appLogger.logBasic(1, "numLogFiles               : " + this.numLogFiles);
        appLogger.logBasic(1, "ControllingNameServer     : " + this.nameServer);
        appLogger.logBasic(1, "registerNameServer        : " + this.regNameServer);
        appLogger.logBasic(1, "NameServer host           : " + this.nsHost);
        appLogger.logBasic(1, "NameServer port           : " + this.nsPortnum);
        appLogger.logBasic(1, "Broker host               : " + this.bHost);
        appLogger.logBasic(1, "Broker port               : " + this.bPortnum);
        appLogger.logBasic(1, "securePort                : " + this.securePort);
        appLogger.logBasic(1, "minNSClientPort           : " + this.minNSClientPort);
        appLogger.logBasic(1, "maxNSClientPort           : " + this.maxNSClientPort);
        appLogger.logBasic(1, "nsClientPortRetry         : " + this.nsClientPortRetry);
        appLogger.logBasic(1, "nsClientPortRetryInterval : " + this.nsClientPortRetryInterval);
        appLogger.logBasic(1, "soReadTimeout             : " + this.soReadTimeout / 1000);
        appLogger.logBasic(1, "allowAiaCmds              : " + this.allowAiaCmds);
        appLogger.logBasic(1, "adminIPList               : " + this.adminIPs);
        appLogger.logBasic(1, "idle-connection-timeout   : " + this.connTrimTimeout / 1000);
        appLogger.logBasic(1, "HTTPS-enabled             : " + this.httpsEnabled);
        appLogger.logBasic(1, "sslEnable                 : " + this.sslEnable);
        appLogger.logBasic(1, "noHostVerify              : " + this.noHostVerify);
        appLogger.logBasic(1, "noSessionReuse            : " + this.noSessionReuse);
        appLogger.logBasic(1, "certStorePath             : " + this.certStorePath);
        appLogger.logBasic(1, "appServerKeepalive        : " + formatAskCapabilities(this.clntAskCaps));
        appLogger.logBasic(1, "clientASKActivityTimeout  : " + this.clientASKActivityTimeout);
        appLogger.logBasic(1, "clientASKResponseTimeout  : " + this.clientASKResponseTimeout);
        appLogger.logBasic(1, "actionalEnabled           : " + this.actionalEnabled);
        appLogger.logBasic(1, "actionalGroup             : " + this.actionalGroup);
    }
    
    public static int parseAskCapabilities(final String s) {
        int n = 1;
        if (s == null) {
            return n;
        }
        final String upperCase = s.toUpperCase();
        if (upperCase.indexOf("ALLOWSERVERASK") >= 0) {
            n |= 0x1;
        }
        if (upperCase.indexOf("ALLOWCLIENTASK") >= 0) {
            n |= 0x2;
        }
        if (upperCase.indexOf("DENYSERVERASK") >= 0) {
            n &= 0xFFFFFFFE;
        }
        if (upperCase.indexOf("DENYCLIENTASK") >= 0) {
            n &= 0xFFFFFFFD;
        }
        return n;
    }
    
    public static String formatAskCapabilities(final int n) {
        return (((n & 0x1) > 0) ? "allowServerASK" : "denyServerASK") + "," + (((n & 0x2) > 0) ? "allowClientASK" : "denyClientASK");
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
    
    private void checkValidSection(final IAppLogger appLogger, final String s) throws UnavailableException {
        try {
            this.groups(s, true, true, true);
        }
        catch (NoSuchGroupException ex) {
            this.fatalError(appLogger, 7665689515738013954L, new Object[] { s, this.propertyFileName });
        }
    }
    
    private void missingProperty(final IAppLogger appLogger, final String s) throws UnavailableException {
        System.err.println("error missing " + s);
        this.fatalError(appLogger, "ERROR: property " + s + " is not defined for Aia " + this.instanceName + " in propertyfile " + this.propertyFileName);
    }
    
    private void fatalError(final IAppLogger appLogger, final String x) throws UnavailableException {
        System.err.println(x);
        appLogger.logWriteMessage(3, 1, appLogger.getExecEnvId(), appLogger.getLogContext().getEntrytypeName(1), x);
        throw new UnavailableException(this.thisServlet, x);
    }
    
    private void fatalError(final IAppLogger appLogger, final long n, final Object[] array) throws UnavailableException {
        System.err.println("promsgs error=" + n);
        appLogger.logWriteMessage(3, 1, appLogger.getExecEnvId(), appLogger.getLogContext().getEntrytypeName(1), AppLogger.formatMessage(n, new Object[0]));
        throw new UnavailableException(this.thisServlet, "promsgs error=" + n);
    }
    
    public String getDisplayInfoTitle() {
        return "Configuration Information for " + this.instanceName;
    }
    
    public String[] getDisplayInfoLabels() {
        return new String[] { "Property Name", "Current Setting" };
    }
    
    public AiaDisplayInfoDesc[] getDisplayInfoRow() {
        return null;
    }
    
    public AiaDisplayInfoDesc[][] getDisplayInfoTable() {
        final boolean b = this.allowAiaCmds == 1;
        final AiaDisplayInfoDesc[][] array = new AiaDisplayInfoDesc[26][2];
        array[0][0] = new AiaDisplayInfoDesc("Installation Directory", 1);
        array[1][0] = new AiaDisplayInfoDesc("HTTPS Enabled", 1);
        array[2][0] = new AiaDisplayInfoDesc("Log filename", 1);
        array[3][0] = new AiaDisplayInfoDesc("Logging Level", 1);
        array[4][0] = new AiaDisplayInfoDesc("Log Append", 1);
        array[5][0] = new AiaDisplayInfoDesc("Controlling NameServer", 1);
        array[6][0] = new AiaDisplayInfoDesc("NameServer Host/Port", 1);
        array[14][0] = new AiaDisplayInfoDesc("Broker Host/Port", 1);
        array[15][0] = new AiaDisplayInfoDesc("Register NameServer", 1);
        array[7][0] = new AiaDisplayInfoDesc("Time interval before trimming connection (seconds)", 1);
        array[8][0] = new AiaDisplayInfoDesc("Secured Port", 1);
        array[9][0] = new AiaDisplayInfoDesc("Minimum NameServer Client Port", 1);
        array[10][0] = new AiaDisplayInfoDesc("Maximum NameServer Client Port", 1);
        array[11][0] = new AiaDisplayInfoDesc("NameServer Client Port Retry", 1);
        array[12][0] = new AiaDisplayInfoDesc("NameServer Client Port Retry Interval", 1);
        array[13][0] = new AiaDisplayInfoDesc("Internal Administrative Commands Allowed", 1);
        if (b) {
            array[16][0] = new AiaDisplayInfoDesc("Admin. Commands are Authorized at", 1);
        }
        else {
            array[16][0] = null;
        }
        array[17][0] = new AiaDisplayInfoDesc("sslEnable", 1);
        array[18][0] = new AiaDisplayInfoDesc("noHostVerify", 1);
        array[19][0] = new AiaDisplayInfoDesc("noSessionReuse", 1);
        array[20][0] = new AiaDisplayInfoDesc("certStorePath", 1);
        array[21][0] = new AiaDisplayInfoDesc("appServerKeepalive", 1);
        array[22][0] = new AiaDisplayInfoDesc("clientASKActivityTimeout", 1);
        array[23][0] = new AiaDisplayInfoDesc("clientASKResponseTimeout", 1);
        array[24][0] = new AiaDisplayInfoDesc("Actional Enabled", 1);
        array[25][0] = new AiaDisplayInfoDesc("Actional Group", 1);
        array[0][1] = new AiaDisplayInfoDesc(this.installDir, 1);
        array[1][1] = new AiaDisplayInfoDesc(new Boolean(this.httpsEnabled).toString(), 1);
        array[2][1] = new AiaDisplayInfoDesc(this.logfilename, 1);
        array[3][1] = new AiaDisplayInfoDesc(new Integer(this.loggingLevel).toString(), 1);
        array[4][1] = new AiaDisplayInfoDesc((this.logAppend == 1) ? "True" : "False", 1);
        array[5][1] = new AiaDisplayInfoDesc(this.nameServer, 1);
        array[6][1] = new AiaDisplayInfoDesc(this.nsHost + ":" + new Integer(this.nsPortnum).toString(), 1);
        array[7][1] = new AiaDisplayInfoDesc(new Integer(this.connTrimTimeout / 1000).toString(), 1);
        array[8][1] = new AiaDisplayInfoDesc(new Integer(this.securePort).toString(), 1);
        array[9][1] = new AiaDisplayInfoDesc(new Integer(this.minNSClientPort).toString(), 1);
        array[10][1] = new AiaDisplayInfoDesc(new Integer(this.maxNSClientPort).toString(), 1);
        array[11][1] = new AiaDisplayInfoDesc(new Integer(this.nsClientPortRetry).toString(), 1);
        array[12][1] = new AiaDisplayInfoDesc(new Integer(this.nsClientPortRetryInterval).toString(), 1);
        array[13][1] = new AiaDisplayInfoDesc(b ? "Yes" : "No", 1);
        array[14][1] = new AiaDisplayInfoDesc(this.bHost + ":" + new Integer(this.bPortnum).toString(), 1);
        array[15][1] = new AiaDisplayInfoDesc(new Boolean(this.regNameServer).toString(), 1);
        if (b) {
            array[16][1] = new AiaDisplayInfoDesc(this.adminIPs, 1);
        }
        else {
            array[16][1] = null;
        }
        array[17][1] = new AiaDisplayInfoDesc(new Boolean(this.sslEnable).toString(), 1);
        array[18][1] = new AiaDisplayInfoDesc(new Boolean(this.noHostVerify).toString(), 1);
        array[19][1] = new AiaDisplayInfoDesc(new Boolean(this.noSessionReuse).toString(), 1);
        array[20][1] = new AiaDisplayInfoDesc(this.certStorePath, 1);
        array[21][1] = new AiaDisplayInfoDesc(formatAskCapabilities(this.clntAskCaps), 1);
        array[22][1] = new AiaDisplayInfoDesc(new Integer(this.clientASKActivityTimeout).toString(), 1);
        array[23][1] = new AiaDisplayInfoDesc(new Integer(this.clientASKResponseTimeout).toString(), 1);
        array[24][1] = new AiaDisplayInfoDesc(new Boolean(this.actionalEnabled).toString(), 1);
        array[25][1] = new AiaDisplayInfoDesc(this.actionalGroup, 1);
        return array;
    }
    
    static {
        INVALID_STRING = null;
    }
}
