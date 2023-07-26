// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa;

import java.util.StringTokenizer;
import java.lang.reflect.Field;
import java.util.Iterator;
import com.progress.common.util.PscURLParser;
import java.io.IOException;
import com.progress.common.util.PropertyFilter;
import com.progress.common.util.Environment;
import com.progress.common.exception.ProException;
import com.progress.common.networkevents.EventBroker;
import com.progress.common.ehnlog.AppLogger;
import java.util.Hashtable;
import java.util.Map;
import com.progress.common.property.PropertyManager;
import com.progress.common.ehnlog.IAppLogger;

public class WsaProperties
{
    private static final int LOGFILEMODE_NEWFILE = 0;
    private static final int LOGFILEMODE_APPEND = 1;
    private static final int CMDS_ALLOWED = 1;
    public static final String PROPNAME_INSTANCENAME = "instanceName";
    public static final String PROPNAME_INSTALLDIR = "InstallDir";
    public static final String PROPNAME_PROPFILENAME = "propertyFileName";
    public static final String PROPNAME_DEPLOYMENTDIR = "deploymentDir";
    public static final String PROPNAME_CERTSTOREPATH = "certStorePath";
    public static final String PROPNAME_LOGFILENAME = "logFile";
    public static final String PROPNAME_LOGGINGLEVEL = "loggingLevel";
    public static final String PROPNAME_LOGFILEMODE = "logAppend";
    public static final String PROPNAME_LOGENTRYTYPES = "logEntryTypes";
    public static final String PROPNAME_LOGTHRESHOLD = "logThreshold";
    public static final String PROPNAME_NUMLOGFILES = "numLogFiles";
    public static final String PROPNAME_WEBAPPENABLED = "webAppEnabled";
    public static final String PROPNAME_ADMINENABLED = "adminEnabled";
    public static final String PROPNAME_HTTPERRORPAGE = "httpErrorPage";
    public static final String PROPNAME_ENABLEWSDLLISTINGS = "enableWsdlListings";
    public static final String PROPNAME_ENABLEWSDL = "enableWsdl";
    public static final String PROPNAME_ADMINAUTH = "adminAuth";
    public static final String PROPNAME_WSDLAUTH = "wsdlAuth";
    public static final String PROPNAME_APPAUTH = "appAuth";
    public static final String PROPNAME_ADMINROLES = "adminRoles";
    public static final String PROPNAME_WSDLLISTINGPAGE = "wsdlListingPage";
    public static final String PROPNAME_NOWSDLPAGE = "noWsdlPage";
    public static final String PROPNAME_FAULTLEVEL = "faultLevel";
    public static final String PROPNAME_WSAURL = "wsaUrl";
    public static final String PROPNAME_APPPROTOCOL = "appProtocol";
    public static final String PROPNAME_ACTIONALENABLED = "actionalEnabled";
    public static final String PROPNAME_ACTIONALGROUP = "actionalGroup";
    public static final String PROPNAME_DEBUGCLIENTS = "debugClients";
    public static final String PROPNAME_LOGMSGTHRESHOLD = "logMsgThreshold";
    public static final String PROPNAME_ROLE_DEFINITIONS = "roleDefinitions";
    public static final String PROPNAME_LOGIN_MODULE = "loginModule";
    public static final String PROPNAME_CACHEUSERS = "cacheUsers";
    public static final String PROPNAME_MAXUSERLIFETIME = "maxUserLifetime";
    public static final String LOGINMODULE_NONE = "NONE";
    public static final String LOGINMODULE_JSE = "JSE";
    public static final String LOGINMODULE_WSA = "WSA";
    public static final String DEFAULT_ADMIN_ROLE = "DEFAULTADMIN";
    public static final String DEFAULT_USER_ROLE = "DEFAULTUSER";
    public static final String DEFAULT_WSDL_ROLE = "DEFAULTWSDL";
    private static final String[] m_runtimePropNames;
    public static final String[] rolePermissionNames;
    public static final String WSA_SECTION_NAME = "WSA";
    public static final String AS_SECTION_NAME = "ubroker.as";
    public static final int DEF_LOGGING_LEVEL = 2;
    public static final String DEF_PROPFILENAME = "ubroker.properties";
    public static final int DEF_LOGAPPEND = 0;
    public static final int DEF_NSCLIENTPORT = 0;
    public static final int DEF_SOTIMEOUT = 240;
    public static final int DEF_ALLOWCMDS = 0;
    public static final String DEFAULT_CERTSTOREPATH = "certs";
    private static final String INVALID_STRING;
    private static final int INVALID_INT = -1;
    private static final long INVALID_LONG = -1L;
    private String propGroup;
    private IAppLogger log;
    public String instanceName;
    public String propertyFileName;
    public String installDir;
    private String sectionName;
    private IAppLogger parentLogger;
    private PropertyManager m_propertyManager;
    private Map m_propertyMap;
    public String logFile;
    public int loggingLevel;
    public int logAppend;
    public boolean webAppEnabled;
    public boolean adminEnabled;
    public boolean enableWsdl;
    public String wsaUrl;
    public String deploymentDirectory;
    public String logEntryTypes;
    public long logThreshold;
    public int numLogFiles;
    public String httpErrorPage;
    public boolean enableWsdlListings;
    public String wsdlListingPage;
    public String noWsdlPage;
    public boolean adminAuth;
    public boolean wsdlAuth;
    public boolean appAuth;
    public String adminRoles;
    public int faultLevel;
    public String debugClients;
    public int logMsgThreshold;
    public int appProtocol;
    public boolean actionalEnabled;
    public String actionalGroup;
    public Hashtable roleDefinitions;
    public String loginModule;
    public boolean cacheUsers;
    public long maxUserLifetime;
    public String wsdlAppURL;
    
    public WsaProperties(final String installDir, final String instanceName, final String propertyFileName, final String deploymentDirectory, final IAppLogger parentLogger) {
        this.propGroup = "";
        this.log = new AppLogger();
        this.instanceName = "";
        this.propertyFileName = "";
        this.installDir = "";
        this.sectionName = "WSA";
        this.parentLogger = null;
        this.m_propertyManager = null;
        this.m_propertyMap = null;
        this.logFile = WsaProperties.INVALID_STRING;
        this.loggingLevel = -1;
        this.logAppend = -1;
        this.webAppEnabled = true;
        this.adminEnabled = true;
        this.enableWsdl = true;
        this.wsaUrl = WsaProperties.INVALID_STRING;
        this.deploymentDirectory = "";
        this.logThreshold = 0L;
        this.numLogFiles = 3;
        this.httpErrorPage = "httperror.html";
        this.enableWsdlListings = false;
        this.wsdlListingPage = "WSDLListing.html";
        this.noWsdlPage = "nowsdl.html";
        this.adminAuth = false;
        this.wsdlAuth = false;
        this.appAuth = false;
        this.adminRoles = "";
        this.faultLevel = 2;
        this.debugClients = WsaProperties.INVALID_STRING;
        this.logMsgThreshold = -1;
        this.appProtocol = 0;
        this.actionalEnabled = false;
        this.actionalGroup = "OpenEdge";
        this.roleDefinitions = new Hashtable();
        this.loginModule = "NONE";
        this.cacheUsers = true;
        this.maxUserLifetime = -1L;
        this.wsdlAppURL = "";
        this.installDir = installDir;
        this.instanceName = instanceName;
        this.propertyFileName = propertyFileName;
        this.deploymentDirectory = deploymentDirectory;
        this.parentLogger = parentLogger;
    }
    
    public IAppLogger processArgs(final String sectionName, final Object o) throws WsaSOAPEngineException {
        if (null != sectionName) {
            this.sectionName = sectionName;
        }
        try {
            Label_0226: {
                if (null == o) {
                    try {
                        (this.m_propertyManager = new PropertyManager(null, false)).load(this.propertyFileName);
                        break Label_0226;
                    }
                    catch (ProException ex) {
                        this.fatalError("Error loading property file " + this.propertyFileName + "\r\n Exception: " + ex.toString());
                        throw new WsaSOAPEngineException("", ex);
                    }
                }
                if (!(o instanceof Map)) {
                    if (o instanceof PropertyManager) {
                        try {
                            (this.m_propertyManager = (PropertyManager)o).load(this.propertyFileName);
                            break Label_0226;
                        }
                        catch (ProException ex2) {
                            final String string = "Error loading property file " + this.propertyFileName + "\r\n Exception: " + ex2.toString();
                            this.fatalError(string);
                            throw new WsaSOAPEngineException(string, ex2);
                        }
                    }
                    final String s = "Invalid startup properties object type\r\n.";
                    this.fatalError(s);
                    throw new WsaSOAPEngineException(s, new Object[0]);
                }
                this.setRuntimeProperties(this.m_propertyMap = (Map)o);
            }
            if (null == this.m_propertyMap) {
                try {
                    final Environment environment = new Environment();
                    this.m_propertyManager.setGetPropertyFilter(new PropertyFilter(this.m_propertyManager));
                }
                catch (Throwable t2) {
                    System.err.println("WSA (" + this.instanceName + "): WARNING cannot access library environ.dll.  The ubroker.properties token replacement is disabled.");
                }
            }
            this.checkValidSection(this.sectionName + "." + this.instanceName);
            this.propGroup = this.sectionName + "." + this.instanceName + ".";
            if (this.parentLogger == null) {
                this.setIntField("loggingLevel", true);
                this.setIntField("logAppend", true);
                this.setStringField("logFile", true);
            }
            else {
                this.setIntField("loggingLevel", false);
                if (this.loggingLevel == -1) {
                    this.loggingLevel = 4;
                }
            }
            this.setStringField("logEntryTypes", false);
            if (this.logEntryTypes == WsaProperties.INVALID_STRING) {
                this.logEntryTypes = "WSADefault";
            }
            this.setLongField("logThreshold", false);
            this.setIntField("numLogFiles", false);
            if (this.numLogFiles == -1) {
                this.numLogFiles = 0;
            }
            try {
                if (this.parentLogger == null) {
                    this.log = new AppLogger(this.logFile, this.logAppend, this.loggingLevel, this.logThreshold, this.numLogFiles, this.logEntryTypes, "_WSA", "Wsa");
                }
                else {
                    this.log = new AppLogger(this.parentLogger);
                }
            }
            catch (IOException ex3) {
                this.log.logError("IO error open " + this.logFile + " : " + ex3.getMessage());
                this.fatalError("IO error open " + this.logFile + " : " + ex3.getMessage());
            }
            catch (Exception ex4) {
                this.log.logError("Error opening " + this.logFile + " : " + ex4.toString());
                this.fatalError("Error opening " + this.logFile + " : " + ex4.toString());
            }
            if (this.propertyFileName == null) {
                this.setBooleanField("webAppEnabled", false);
                this.setBooleanField("adminEnabled", false);
                this.setBooleanField("enableWsdl", false);
                this.setStringField("wsaUrl", false);
                if (this.wsaUrl == WsaProperties.INVALID_STRING) {
                    this.wsaUrl = "http://localhost/wsa";
                }
            }
            else {
                this.setBooleanField("webAppEnabled", true);
                this.setBooleanField("adminEnabled", true);
                this.setBooleanField("enableWsdl", true);
                this.setStringField("wsaUrl", true);
            }
            this.setStringField("httpErrorPage", false);
            this.setBooleanField("enableWsdlListings", false);
            this.setBooleanField("enableWsdl", false);
            this.setBooleanField("adminAuth", false);
            this.setBooleanField("wsdlAuth", false);
            this.setBooleanField("appAuth", false);
            this.setStringField("adminRoles", false);
            this.setStringField("wsdlListingPage", false);
            this.setStringField("noWsdlPage", false);
            this.setBooleanField("cacheUsers", false);
            this.setLongField("maxUserLifetime", false);
            this.setStringField("debugClients", false);
            this.setIntField("logMsgThreshold", false);
            this.setIntField("appProtocol", false);
            this.setIntField("faultLevel", false);
            this.setBooleanField("actionalEnabled", false);
            this.setStringField("actionalGroup", false);
            if (-1 == this.faultLevel) {
                this.faultLevel = 2;
            }
            if (this.adminAuth) {
                this.loginModule = "JSE";
            }
            if (0L < this.maxUserLifetime) {
                this.maxUserLifetime *= 1000L;
            }
            this.wsdlAppURL = this.wsaUrl;
            PscURLParser pscURLParser;
            try {
                if (null == this.wsaUrl || 0 == this.wsaUrl.length()) {
                    throw new Exception("missing value");
                }
                pscURLParser = new PscURLParser(this.wsaUrl);
                pscURLParser.getURL();
                if (!pscURLParser.getScheme().equals("http") && !pscURLParser.getScheme().equals("https")) {
                    throw new Exception("unsupported protocol");
                }
                if (pscURLParser.getPath().length() < 2) {
                    throw new Exception("invalid URL path");
                }
            }
            catch (Exception ex5) {
                final Object[] array = { "wsaUrl", this.wsaUrl, ex5.getMessage() };
                final String formatMessage = AppLogger.formatMessage(8607504787811871431L, array);
                this.log.logError(8607504787811871431L, array);
                throw new WsaSOAPEngineException(formatMessage);
            }
            switch (this.appProtocol) {
                case -1: {
                    this.appProtocol = 0;
                }
                case 0: {
                    this.wsdlAppURL = pscURLParser.toString();
                    break;
                }
                case 1: {
                    pscURLParser.setScheme("http");
                    this.wsdlAppURL = pscURLParser.toString();
                    break;
                }
                case 2: {
                    pscURLParser.setScheme("https");
                    this.wsdlAppURL = pscURLParser.toString();
                    break;
                }
                default: {
                    final Object[] array2 = { "appProtocol", Integer.toString(this.appProtocol), "out of range" };
                    final String formatMessage2 = AppLogger.formatMessage(8607504787811871431L, array2);
                    this.log.logError(8607504787811871431L, array2);
                    throw new WsaSOAPEngineException(formatMessage2);
                }
            }
            if (null == o) {
                this.loadRoleDefinitions();
            }
            else {
                this.loadDefaultRoleDefinitions();
            }
        }
        catch (WsaSOAPEngineException ex6) {
            ex6.printStackTrace();
            this.log.logStackTrace(9, "Exception in processArgs()", ex6);
            throw ex6;
        }
        catch (Throwable t) {
            t.printStackTrace();
            this.log.logStackTrace(9, "unexpected error in processArgs()", t);
            throw new WsaSOAPEngineException(t.toString());
        }
        return this.log;
    }
    
    public void print(final int n, final int n2) {
        this.log.logVerbose(9, "installDir                : " + this.installDir);
        this.log.logVerbose(9, "properties file           : " + this.propertyFileName);
        this.log.logVerbose(9, "instanceName              : " + this.instanceName);
        this.log.logVerbose(9, "logfilename               : " + this.logFile);
        this.log.logVerbose(9, "loggingLevel              : " + this.loggingLevel);
        this.log.logVerbose(9, "logAppend                 : " + this.logAppend);
        this.log.logVerbose(9, "webAppEnabled             : " + this.webAppEnabled);
        this.log.logVerbose(9, "adminEnabled              : " + this.adminEnabled);
        this.log.logVerbose(9, "enableWsdl                : " + this.enableWsdl);
        this.log.logVerbose(9, "logEntryTypes             : " + this.logEntryTypes);
        this.log.logVerbose(9, "logThreshold              : " + this.logThreshold);
        this.log.logVerbose(9, "numLogFiles               : " + this.numLogFiles);
        this.log.logVerbose(9, "httpErrorPage             : " + this.httpErrorPage);
        this.log.logVerbose(9, "enableWsdlListings        : " + this.enableWsdlListings);
        this.log.logVerbose(9, "wsdlListingPage           : " + this.wsdlListingPage);
        this.log.logVerbose(9, "noWsdlPage                : " + this.noWsdlPage);
        this.log.logVerbose(9, "adminAuth                 : " + this.adminAuth);
        this.log.logVerbose(9, "wsdlAuth                  : " + this.wsdlAuth);
        this.log.logVerbose(9, "appAuth                   : " + this.appAuth);
        this.log.logVerbose(9, "adminRoles                : " + this.adminRoles);
        this.log.logVerbose(9, "wsaURL                    : " + this.wsaUrl);
        this.log.logVerbose(9, "wsdlAppURL                : " + this.wsdlAppURL);
        this.log.logVerbose(9, "debugClients              : " + this.debugClients);
        this.log.logVerbose(9, "logMsgThreshold           : " + this.logMsgThreshold);
        this.log.logVerbose(9, "appProtocol               : " + this.appProtocol);
        this.log.logVerbose(9, "actionalEnabled           : " + this.actionalEnabled);
        this.log.logVerbose(9, "actionalGroup             : " + this.actionalGroup);
    }
    
    public void setRuntimeProperties(final Map map) throws Exception {
        for (final String name : map.keySet()) {
            map.get(name);
            boolean b = false;
            for (int i = 0; i < WsaProperties.m_runtimePropNames.length; ++i) {
                if (name.equals(WsaProperties.m_runtimePropNames[i])) {
                    b = true;
                    break;
                }
            }
            if (b) {
                Field declaredField = null;
                try {
                    declaredField = this.getClass().getDeclaredField(name);
                }
                catch (Exception ex2) {
                    this.log.logError(8607504787811871422L, new Object[] { name });
                }
                try {
                    declaredField.set(this, map.get(name));
                    if (!name.equals("loggingLevel")) {
                        continue;
                    }
                    this.log.setLoggingLevel(this.loggingLevel);
                }
                catch (Exception ex) {
                    this.log.logError(8607504787811871425L, new Object[] { name, ex.toString() });
                }
            }
            else {
                this.log.logError(8607504787811871423L, new Object[] { name });
            }
        }
    }
    
    public Hashtable getRuntimeProperties() throws Exception {
        final Hashtable<String, String> hashtable = new Hashtable<String, String>();
        Field declaredField = null;
        for (int i = 0; i < WsaProperties.m_runtimePropNames.length; ++i) {
            final String key = WsaProperties.m_runtimePropNames[i];
            try {
                declaredField = this.getClass().getDeclaredField(key);
            }
            catch (Exception ex) {
                this.log.logError(8607504787811871422L, new Object[] { key });
            }
            try {
                hashtable.put(key, (String)declaredField.get(this));
            }
            catch (Exception ex2) {
                hashtable.put(key, "");
            }
        }
        return hashtable;
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
    
    protected void setIntField(final String str, final boolean b) throws WsaSOAPEngineException {
        String property = null;
        if (null == str) {
            throw new WsaSOAPEngineException("Cannot set a field with a null name");
        }
        Field declaredField;
        try {
            declaredField = this.getClass().getDeclaredField(str);
        }
        catch (Exception ex2) {
            throw new WsaSOAPEngineException("Error attempting to set a non-existent field: " + str);
        }
        if (null != this.m_propertyManager) {
            property = this.m_propertyManager.getProperty(this.propGroup + str);
        }
        else if (null != this.m_propertyMap) {
            property = this.m_propertyMap.get(str);
        }
        if (null == property) {
            if (b) {
                this.missingProperty(str);
            }
            else {
                property = "-1";
            }
        }
        try {
            int radix = 10;
            int beginIndex = 0;
            if (3 <= property.length() && (property.startsWith("0x") || property.startsWith("0X"))) {
                radix = 16;
                beginIndex = 2;
            }
            else if (2 <= property.length() && property.startsWith("0")) {
                radix = 8;
                beginIndex = 1;
            }
            declaredField.setInt(this, Integer.parseInt(property.substring(beginIndex), radix));
        }
        catch (Exception ex) {
            throw new WsaSOAPEngineException(ex.toString());
        }
    }
    
    protected void setLongField(final String str, final boolean b) throws WsaSOAPEngineException {
        String property = null;
        if (null == str) {
            throw new WsaSOAPEngineException("Cannot set a field with a null name");
        }
        Field declaredField;
        try {
            declaredField = this.getClass().getDeclaredField(str);
        }
        catch (Exception ex2) {
            throw new WsaSOAPEngineException("Error attempting to set a non-existent field: " + str);
        }
        if (null != this.m_propertyManager) {
            property = this.m_propertyManager.getProperty(this.propGroup + str);
        }
        else if (null != this.m_propertyMap) {
            property = this.m_propertyMap.get(str);
        }
        if (null == property) {
            if (b) {
                this.missingProperty(str);
            }
            else {
                property = "-1";
            }
        }
        try {
            int radix = 10;
            int beginIndex = 0;
            if (3 <= property.length() && (property.startsWith("0x") || property.startsWith("0X"))) {
                radix = 16;
                beginIndex = 2;
            }
            else if (2 <= property.length() && property.startsWith("0")) {
                radix = 8;
                beginIndex = 1;
            }
            declaredField.setLong(this, Long.parseLong(property.substring(beginIndex), radix));
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new WsaSOAPEngineException(ex.toString());
        }
    }
    
    protected void setStringField(final String str, final boolean b) throws WsaSOAPEngineException {
        String value = WsaProperties.INVALID_STRING;
        if (null == str) {
            throw new WsaSOAPEngineException("Cannot set a field with a null name");
        }
        Field declaredField;
        try {
            declaredField = this.getClass().getDeclaredField(str);
        }
        catch (Exception ex2) {
            throw new WsaSOAPEngineException("Error attempting to set a non-existent field: " + str);
        }
        if (null != this.m_propertyManager) {
            value = this.m_propertyManager.getProperty(this.propGroup + str, WsaProperties.INVALID_STRING);
        }
        else if (null != this.m_propertyMap) {
            value = this.m_propertyMap.get(str);
        }
        if (value == WsaProperties.INVALID_STRING && b) {
            this.missingProperty(str);
        }
        try {
            declaredField.set(this, value);
        }
        catch (Exception ex) {
            throw new WsaSOAPEngineException(ex.toString());
        }
    }
    
    protected void setBooleanField(final String str, final boolean b) throws WsaSOAPEngineException {
        int intProperty = -1;
        if (null == str) {
            throw new WsaSOAPEngineException("Cannot set a field with a null name");
        }
        Field declaredField;
        try {
            declaredField = this.getClass().getDeclaredField(str);
        }
        catch (Exception ex2) {
            throw new WsaSOAPEngineException("Error attempting to set a non-existent field: " + str);
        }
        if (null != this.m_propertyManager) {
            intProperty = this.m_propertyManager.getIntProperty(this.propGroup + str, -1);
        }
        else if (null != this.m_propertyMap) {
            final String s = this.m_propertyMap.get(str);
            if (s != null) {
                intProperty = (s.equals("0") ? 0 : 1);
            }
        }
        if (intProperty == -1 && b) {
            this.missingProperty(str);
        }
        try {
            if (-1 != intProperty) {
                declaredField.setBoolean(this, intProperty != 0);
            }
        }
        catch (Exception ex) {
            throw new WsaSOAPEngineException(ex.toString());
        }
    }
    
    protected void loadDefaultRoleDefinitions() {
        final String string = "wsa." + this.instanceName;
        final Hashtable<String, String> value = new Hashtable<String, String>();
        value.put(string + ".*", "read,write,execute,delete");
        this.roleDefinitions.put("DEFAULTADMIN", value);
        final Hashtable<String, String> value2 = new Hashtable<String, String>();
        value2.put(string + ".4GL.*", "execute");
        this.roleDefinitions.put("DEFAULTUSER", value2);
        final Hashtable<String, String> value3 = new Hashtable<String, String>();
        value3.put(string + ".wsdl.*", "read");
        this.roleDefinitions.put("DEFAULTWSDL", value3);
    }
    
    protected void loadRoleDefinitions() {
        try {
            final String string = "wsa." + this.instanceName;
            if (null != this.adminRoles && 0 < this.adminRoles.length()) {
                int count = 0;
                final char[] charArray = this.adminRoles.toCharArray();
                final char[] value = new char[charArray.length];
                for (int i = 0; i < charArray.length; ++i) {
                    if (charArray[i] != ' ' && charArray[i] != '\t') {
                        value[count++] = charArray[i];
                    }
                }
                final StringTokenizer stringTokenizer = new StringTokenizer(new String(value, 0, count), ",");
                while (stringTokenizer.hasMoreTokens()) {
                    final String nextToken = stringTokenizer.nextToken();
                    final String string2 = "AdminRole." + nextToken;
                    if (null != this.m_propertyManager.findGroup(string2)) {
                        this.roleDefinitions.put(nextToken, this.getRolePermissions(string2));
                    }
                    else {
                        this.log.logError(8607504787811871432L, new Object[] { string2 });
                    }
                }
                if (null != this.m_propertyManager.findGroup("AdminRole")) {
                    this.roleDefinitions.put("DEFAULTADMIN", this.getRolePermissions("AdminRole"));
                }
                else {
                    final Hashtable<String, String> value2 = new Hashtable<String, String>();
                    value2.put("wsa.services", "read");
                    this.roleDefinitions.put("DEFAULTADMIN", value2);
                    this.log.logError(8607504787811871432L, new Object[] { "DEFAULT ([AdminRole])" });
                }
            }
            else {
                final Hashtable<String, String> value3 = new Hashtable<String, String>();
                value3.put(string + ".*", "read,write,execute,delete");
                this.roleDefinitions.put("DEFAULTADMIN", value3);
            }
            final Hashtable<String, String> value4 = new Hashtable<String, String>();
            value4.put(string + ".4GL.*", "execute");
            this.roleDefinitions.put("DEFAULTUSER", value4);
            final Hashtable<String, String> value5 = new Hashtable<String, String>();
            value5.put(string + ".wsdl.*", "read");
            this.roleDefinitions.put("DEFAULTWSDL", value5);
        }
        catch (Exception ex) {
            this.log.logError(8607504787811871433L, new Object[] { ex.toString() });
        }
    }
    
    protected Hashtable getRolePermissions(final String str) {
        final Hashtable<String, String> hashtable = new Hashtable<String, String>();
        final String string = "wsa." + this.instanceName;
        for (int i = 0; i < WsaProperties.rolePermissionNames.length; ++i) {
            hashtable.put((string + WsaProperties.rolePermissionNames[i]).replace('_', '.'), this.m_propertyManager.getProperty(str + WsaProperties.rolePermissionNames[i], ""));
        }
        return hashtable;
    }
    
    private void checkValidSection(final String s) throws WsaSOAPEngineException {
        if (this.propertyFileName == null) {
            return;
        }
        try {
            this.m_propertyManager.groups(s, true, true, true);
        }
        catch (Exception ex) {
            this.fatalError(7665689515738013954L, new Object[] { s, this.propertyFileName });
        }
    }
    
    private void missingProperty(final String s) throws WsaSOAPEngineException {
        System.err.println("error missing " + s);
        this.fatalError("ERROR: property " + s + " is not defined for Wsa " + this.instanceName + " in propertyfile " + this.propertyFileName);
    }
    
    private void fatalError(final String x) throws WsaSOAPEngineException {
        System.err.println(x);
        this.log.logError(x);
    }
    
    private void fatalError(final long n, final Object[] array) throws WsaSOAPEngineException {
        System.err.println("promsgs error=" + n);
        this.log.logError(n, array);
        throw new WsaSOAPEngineException("promsgs error=" + n);
    }
    
    static {
        m_runtimePropNames = new String[] { "loggingLevel", "adminEnabled", "webAppEnabled", "enableWsdlListings", "enableWsdl", "debugClients", "logMsgThreshold" };
        rolePermissionNames = new String[] { ".servlet_props", ".servlet_stats", ".servlet_services", ".apps_defaults", ".apps_enable", ".apps_props", ".apps_stats" };
        INVALID_STRING = null;
    }
}
