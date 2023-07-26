// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa.admin;

import org.apache.xml.serialize.BaseMarkupSerializer;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;
import java.util.Enumeration;
import java.io.Writer;
import org.xml.sax.Attributes;
import org.apache.xml.serialize.XMLSerializer;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import org.apache.xml.serialize.OutputFormat;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import com.progress.common.ehnlog.IAppLogger;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import com.progress.wsa.WsaVersionInfo;
import java.security.MessageDigest;
import com.progress.wsa.WsaSOAPException;
import com.progress.wsa.WsaProperties;
import com.progress.wsa.WsaConstants;
import com.progress.open4gl.wsdlgen.DWGenInfo;
import java.io.IOException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import com.progress.open4gl.proxygen.PGGenInfo;
import com.progress.open4gl.proxygen.PGAppObj;
import com.progress.common.ehnlog.AppLogger;
import com.progress.wsa.open4gl.proxy.WSAProxyPool;
import com.progress.wsa.WsaSOAPEngineContext;
import java.util.Hashtable;
import com.progress.wsa.open4gl.XMLSerializableRoot;
import java.io.Serializable;

public class AppContainer implements Serializable, XMLSerializableRoot
{
    private static final int _CONTAINER_VERSION = 1;
    private static final long serialVersionUID = 2003701988232369104L;
    private static final String ROOT = "AppContainer";
    private static final String XML_TYPE = "AppContainerType";
    protected AppRuntimeProps m_runtimeProps;
    protected String m_WSADFileName;
    protected String m_WSDLFileName;
    protected String m_friendlyName;
    protected byte[] m_WSADDigest;
    protected transient AppRuntimeStats m_runtimeStats;
    protected transient WSAD m_WSAD;
    protected transient boolean m_dirty;
    protected transient Hashtable m_dds;
    protected transient WsaSOAPEngineContext m_context;
    protected transient WSAProxyPool m_poolManager;
    protected transient String m_instanceName;
    protected transient String m_schemaLocation;
    protected transient WsaParser m_parser;
    protected transient String m_prefix;
    protected transient String m_namespace;
    protected transient boolean m_appDamaged;
    protected transient AppLogger m_appLog;
    protected transient boolean m_inContainer;
    private int m_version;
    
    public static AppContainer createDefault(final WsaParser wsaParser, final String s) {
        final AppContainer appContainer = new AppContainer(wsaParser);
        appContainer.setRuntimeProperties(new AppRuntimeProps());
        appContainer.setFriendlyName("default");
        return appContainer;
    }
    
    public static AppContainer createApplication(final WSAD wsad, AppRuntimeProps runtimeProperties, final WsaParser parser) {
        final AppContainer appContainer = new AppContainer();
        final PGAppObj pgAppObj = wsad.getPGAppObj();
        final PGGenInfo pgGenInfo = pgAppObj.getWSInfo().getPGGenInfo();
        wsad.setSchemaLocation(null);
        wsad.setPrefix(null);
        wsad.setParser(parser);
        final String appObjectName = pgAppObj.getAppObjectName();
        appContainer.setRuntimeProperties(runtimeProperties);
        appContainer.setRuntimeStats(null);
        appContainer.setWSAD(wsad);
        appContainer.setWSADFileName(appObjectName + ".wsad");
        appContainer.setWSDLFileName(appObjectName + ".wsdl");
        appContainer.setFriendlyName(appObjectName);
        runtimeProperties = appContainer.getRuntimeProperties();
        if (runtimeProperties != null) {
            if (pgGenInfo.getConnectionFree()) {
                runtimeProperties.setProperty("PROGRESS.Session.sessionModel", new Integer(1));
            }
            else {
                runtimeProperties.setProperty("PROGRESS.Session.sessionModel", new Integer(0));
            }
            runtimeProperties.setProperty("PROGRESS.Session.appServiceName", new String(pgGenInfo.getServiceName()));
            runtimeProperties.setProperty("PROGRESS.Session.serviceAvailable", new Integer(0));
        }
        return appContainer;
    }
    
    public static AppContainer loadWSDFile(final String s, final String s2) throws IOException {
        final WsaParser wsaParser = new WsaParser(s, null);
        final AppContainer appContainer = new AppContainer();
        final Document file = wsaParser.parseFile(s2, 0);
        if (file == null) {
            return null;
        }
        appContainer.readXML(file.getDocumentElement());
        return appContainer;
    }
    
    public static AppContainer loadWSD(final String s, final String s2) throws IOException {
        final WsaParser wsaParser = new WsaParser(s, null);
        final AppContainer appContainer = new AppContainer();
        final Document str = wsaParser.parseStr(s2, 0);
        if (str == null) {
            return null;
        }
        appContainer.readXML(str.getDocumentElement());
        return appContainer;
    }
    
    public AppContainer() {
        this.m_runtimeProps = null;
        this.m_WSADFileName = null;
        this.m_WSDLFileName = null;
        this.m_friendlyName = null;
        this.m_WSADDigest = null;
        this.m_runtimeStats = null;
        this.m_WSAD = null;
        this.m_dirty = false;
        this.m_dds = null;
        this.m_context = null;
        this.m_poolManager = null;
        this.m_instanceName = null;
        this.m_schemaLocation = null;
        this.m_parser = null;
        this.m_prefix = null;
        this.m_namespace = "urn:schemas-progress-com:WSAD:0009";
        this.m_appDamaged = false;
        this.m_appLog = null;
        this.m_inContainer = false;
        this.m_version = 1;
    }
    
    public AppContainer(final WsaParser parser) {
        this.m_runtimeProps = null;
        this.m_WSADFileName = null;
        this.m_WSDLFileName = null;
        this.m_friendlyName = null;
        this.m_WSADDigest = null;
        this.m_runtimeStats = null;
        this.m_WSAD = null;
        this.m_dirty = false;
        this.m_dds = null;
        this.m_context = null;
        this.m_poolManager = null;
        this.m_instanceName = null;
        this.m_schemaLocation = null;
        this.m_parser = null;
        this.m_prefix = null;
        this.m_namespace = "urn:schemas-progress-com:WSAD:0009";
        this.m_appDamaged = false;
        this.m_appLog = null;
        this.m_inContainer = false;
        this.m_version = 1;
        this.m_parser = parser;
    }
    
    public AppRuntimeProps getRuntimeProperties() {
        return this.m_runtimeProps;
    }
    
    public void setRuntimeProperties(final AppRuntimeProps appRuntimeProps) {
        if (appRuntimeProps != null) {
            this.m_runtimeProps = new AppRuntimeProps(appRuntimeProps);
            this.m_dirty = true;
        }
    }
    
    public AppRuntimeStats getRuntimeStats() {
        return this.m_runtimeStats;
    }
    
    public void setRuntimeStats(final AppRuntimeStats runtimeStats) {
        if (runtimeStats == null) {
            this.m_runtimeStats = new AppRuntimeStats();
        }
        else {
            this.m_runtimeStats = null;
            this.m_runtimeStats = runtimeStats;
        }
    }
    
    public String getWSADFileName() {
        return this.m_WSADFileName;
    }
    
    public void setWSADFileName(final String wsadFileName) {
        if (wsadFileName != null) {
            this.m_WSADFileName = wsadFileName;
            this.m_dirty = true;
        }
    }
    
    public String getWSDLFileName() {
        return this.m_WSDLFileName;
    }
    
    public void setWSDLFileName(final String wsdlFileName) {
        if (wsdlFileName != null) {
            this.m_WSDLFileName = wsdlFileName;
            this.m_dirty = true;
        }
    }
    
    public String getFriendlyName() {
        return this.m_friendlyName;
    }
    
    public void setFriendlyName(final String friendlyName) {
        if (friendlyName != null) {
            this.m_friendlyName = friendlyName;
            this.m_WSADFileName = this.m_friendlyName + ".wsad";
            this.m_WSDLFileName = this.m_friendlyName + ".wsdl";
            this.m_dirty = true;
        }
    }
    
    public String getId() {
        if (this.m_friendlyName.equals("default")) {
            return "urn:services-progress-com:wsa-default:0001";
        }
        if (this.m_WSAD != null) {
            final DWGenInfo dwGenInfo = this.m_WSAD.getPGAppObj().getWSInfo().getDWGenInfo();
            if (dwGenInfo != null) {
                return dwGenInfo.getWebServiceNameSpace();
            }
        }
        return null;
    }
    
    public void setId(final String webServiceNameSpace) {
        if (!this.m_friendlyName.equals("default") && this.m_WSAD != null) {
            final DWGenInfo dwGenInfo = this.m_WSAD.getPGAppObj().getWSInfo().getDWGenInfo();
            if (dwGenInfo != null) {
                dwGenInfo.setWebServiceNameSpace(webServiceNameSpace);
            }
        }
    }
    
    public WSAProxyPool getPoolManager() {
        return this.m_poolManager;
    }
    
    public String getCurrentEncoding() {
        return WsaConstants.WSA_SERVICE_ENCODING[this.m_WSAD.getPGAppObj().getWSInfo().getDWGenInfo().getEncoding()];
    }
    
    public int getCurrentEncodingInt() {
        return this.m_WSAD.getPGAppObj().getWSInfo().getDWGenInfo().getEncoding();
    }
    
    public void setCurrentEncodingInt(final int encoding) {
        this.m_WSAD.getPGAppObj().getWSInfo().getDWGenInfo().setEncoding(encoding);
    }
    
    public int getCurrentESBEncodingInt() {
        return this.m_WSAD.getPGAppObj().getWSInfo().getDWGenInfo().getESBEncoding();
    }
    
    public void setCurrentESBEncodingInt(final int esbEncoding) {
        this.m_WSAD.getPGAppObj().getWSInfo().getDWGenInfo().setESBEncoding(esbEncoding);
    }
    
    public String getConnectionURL() {
        final StringBuffer sb = new StringBuffer(256);
        sb.append((String)this.m_runtimeProps.getProperty("PROGRESS.Session.appServiceProtocol"));
        sb.append("://");
        sb.append((String)this.m_runtimeProps.getProperty("PROGRESS.Session.appServiceHost"));
        final Integer n = (Integer)this.m_runtimeProps.getProperty("PROGRESS.Session.appServicePort");
        if (n > 0) {
            sb.append(":");
            sb.append(n.toString());
        }
        final String str = (String)this.m_runtimeProps.getProperty("PROGRESS.Session.appServiceName");
        if (!str.equals("[default]")) {
            sb.append("/");
            sb.append(str);
        }
        return sb.toString();
    }
    
    public WSAD getWSAD() {
        return this.m_WSAD;
    }
    
    public void setWSAD(final WSAD wsad) {
        this.m_WSAD = wsad;
    }
    
    public int getAppStatus() {
        if (this.m_appDamaged) {
            return 2;
        }
        return ((int)this.m_runtimeProps.getProperty("PROGRESS.Session.serviceAvailable") != 1) ? 1 : 0;
    }
    
    public void setWSAUrl(final String soapEndPointURL) {
        if (this.m_WSAD != null) {
            final DWGenInfo dwGenInfo = this.m_WSAD.getPGAppObj().getWSInfo().getDWGenInfo();
            if (dwGenInfo != null) {
                dwGenInfo.setSoapEndPointURL(soapEndPointURL);
            }
        }
    }
    
    public String getWSAUrl() {
        String soapEndPointURL = null;
        if (this.m_WSAD != null) {
            final DWGenInfo dwGenInfo = this.m_WSAD.getPGAppObj().getWSInfo().getDWGenInfo();
            if (dwGenInfo != null) {
                soapEndPointURL = dwGenInfo.getSoapEndPointURL();
            }
        }
        return soapEndPointURL;
    }
    
    public void setContext(final WsaSOAPEngineContext context) {
        this.m_context = context;
        final WsaProperties wsaProperties = (WsaProperties)this.m_context.get("psc.wsa.params");
        if (null == wsaProperties.actionalGroup) {
            this.m_runtimeProps.setProperty("PROGRESS.Session.actionalGroupName", "OpenEdge");
        }
        else {
            this.m_runtimeProps.setProperty("PROGRESS.Session.actionalGroupName", wsaProperties.actionalGroup);
        }
    }
    
    public void createWSADFile(final String s, final WsaParser wsaParser) throws WsaSOAPException {
        if (this.m_WSAD == null) {
            throw new WsaSOAPException("SOAP-ENV:Server.SOAP", 8607504787811871223L, null);
        }
        this.m_WSAD.saveWSMFile(s);
        try {
            final MessageDigest instance = MessageDigest.getInstance("MD5");
            wsaParser.parseFileDigest(s, -1, instance);
            this.m_WSADDigest = instance.digest();
        }
        catch (Exception ex) {
            throw new WsaSOAPException("SOAP-ENV:Server.SOAP", 8607504787811871224L, new Object[] { new String(ex.getMessage()) }, ex);
        }
    }
    
    public void createWSDLFile(final String s) throws WsaSOAPException {
        if (this.m_WSAD == null) {
            throw new WsaSOAPException("SOAP-ENV:Server.SOAP", 8607504787811871223L, null);
        }
        String str;
        String str2;
        if (this.m_context != null) {
            str = (String)this.m_context.get("psc.wsa.version");
            str2 = (String)this.m_context.get("psc.wsa.build");
        }
        else {
            final WsaVersionInfo wsaVersionInfo = new WsaVersionInfo();
            wsaVersionInfo.getClass();
            str = "10.2B07";
            wsaVersionInfo.getClass();
            str2 = "Wed Aug 29 18:15:16 EDT 2012";
        }
        if (str == null) {
            str = "Progress 9.1D";
        }
        if (str2 == null) {
            str2 = "1-1-80";
        }
        try {
            this.m_WSAD.getPGAppObj().buildWSDL(s, "WSA_Product", str + " - " + str2);
        }
        catch (Exception ex) {
            String message = ex.getMessage();
            if (message == null) {
                message = "";
            }
            throw new WsaSOAPException("SOAP-ENV:Server.SOAP", 8607504787811874786L, new Object[] { message });
        }
    }
    
    public void createWSDLFile(final String s, final int n) throws WsaSOAPException {
        if (this.m_WSAD == null) {
            throw new WsaSOAPException("SOAP-ENV:Server.SOAP", 8607504787811871223L, null);
        }
        String str;
        String str2;
        if (this.m_context != null) {
            str = (String)this.m_context.get("psc.wsa.version");
            str2 = (String)this.m_context.get("psc.wsa.build");
        }
        else {
            final WsaVersionInfo wsaVersionInfo = new WsaVersionInfo();
            wsaVersionInfo.getClass();
            str = "10.2B07";
            wsaVersionInfo.getClass();
            str2 = "Wed Aug 29 18:15:16 EDT 2012";
        }
        if (str == null) {
            str = "Progress 9.1D";
        }
        if (str2 == null) {
            str2 = "1-1-80";
        }
        try {
            this.m_WSAD.getPGAppObj().buildWSDL(s, WsaConstants.WSDL_PRODUCT_NAME[n], str + " - " + str2);
        }
        catch (Exception ex) {
            String message = ex.getMessage();
            if (message == null) {
                message = "";
            }
            throw new WsaSOAPException("SOAP-ENV:Server.SOAP", 8607504787811874786L, new Object[] { message });
        }
    }
    
    public void createPropFile(final String s, final WsaParser parser) throws WsaSOAPException {
        if (this.m_runtimeProps == null) {
            throw new WsaSOAPException("SOAP-ENV:Server.SOAP", 8607504787811871225L, null);
        }
        this.m_parser = parser;
        this.saveRuntimeProps(s);
    }
    
    public PscDeploymentDescriptor query(final String key) {
        if (this.m_dds != null) {
            return this.m_dds.get(key);
        }
        return null;
    }
    
    public boolean isDirty() {
        return this.m_dirty || this.m_runtimeProps.isDirty();
    }
    
    public void initRuntime(final WsaSOAPEngineContext context, final WsaParser wsaParser) throws WsaSOAPException {
        final StringBuffer sb = new StringBuffer(256);
        this.m_context = context;
        final AppLogger appLogger = (AppLogger)this.m_context.get("psc.wsa.log");
        if (appLogger.ifLogVerbose(8L, 3)) {
            appLogger.logVerbose(3, 8607504787811871226L, new Object[] { this.m_friendlyName });
        }
        this.m_instanceName = (String)this.m_context.get("psc.wsa.instance.name");
        if (((String)this.m_context.get("psc.wsa.engine.type")).equals("Container")) {
            this.m_inContainer = true;
        }
        this.m_parser = wsaParser;
        sb.append("/");
        sb.append(this.m_instanceName);
        sb.append("/");
        if (!this.m_inContainer) {
            final StringBuffer sb2 = new StringBuffer(sb.toString());
            sb2.append(this.m_friendlyName);
            sb2.append(".props");
            final String realPath = this.m_context.getRealPath(sb2.toString());
            this.loadRuntimeProps(realPath);
            if (appLogger.ifLogVerbose(8L, 3)) {
                appLogger.logVerbose(3, 8607504787811871227L, new Object[] { realPath });
            }
            if (!this.m_friendlyName.equals("default")) {
                final StringBuffer sb3 = new StringBuffer(sb.toString());
                sb3.append(this.m_WSADFileName);
                final String realPath2 = this.m_context.getRealPath(sb3.toString());
                if (appLogger.ifLogVerbose(8L, 3)) {
                    appLogger.logVerbose(3, 8607504787811871228L, new Object[] { realPath2 });
                }
                try {
                    final MessageDigest instance = MessageDigest.getInstance("MD5");
                    final Document fileDigest = this.m_parser.parseFileDigest(realPath2, 3, instance);
                    if (fileDigest == null) {
                        appLogger.logError(8607504787811871229L, new Object[] { realPath2 });
                        this.m_appDamaged = true;
                    }
                    else {
                        final AppContainer appContainer = new AppContainer(wsaParser);
                        appContainer.readXML(fileDigest.getDocumentElement());
                        (this.m_WSAD = appContainer.getWSAD()).setParser(wsaParser);
                    }
                    if (!this.m_appDamaged) {
                        final byte[] digest = instance.digest();
                        if (digest.length != this.m_WSADDigest.length || !Arrays.equals(digest, this.m_WSADDigest)) {
                            appLogger.logError(8607504787811871232L, new Object[] { realPath2 });
                            this.m_appDamaged = true;
                        }
                    }
                }
                catch (NoSuchAlgorithmException ex) {
                    throw new WsaSOAPException("SOAP-ENV:Server.SOAP", 8607504787811871233L, null, ex);
                }
                catch (IOException ex2) {
                    throw new WsaSOAPException("SOAP-ENV:Server.SOAP", 8607504787811871233L, null, ex2);
                }
            }
        }
        if (!this.m_friendlyName.equals("default")) {
            if (appLogger.ifLogVerbose(8L, 3)) {
                appLogger.logVerbose(3, 8607504787811871230L, null);
            }
            this.m_dds = this.m_WSAD.getPGAppObj().generateDeploymentDescriptors(this.m_context, this);
            final Boolean b = (Boolean)this.m_context.get("psc.wsa.dump.descriptors");
            if (null != b && b) {
                if (appLogger.ifLogVerbose(8L, 3)) {
                    appLogger.logVerbose(3, 8607504787811871231L, null);
                }
                this.dumpDescriptor();
            }
        }
        if (this.m_appDamaged) {
            throw new WsaSOAPException("SOAP-ENV:Server.SOAP", 8607504787811871233L, null);
        }
        if (!this.m_friendlyName.equals("default")) {
            if (appLogger.ifLogVerbose(8L, 3)) {
                appLogger.logVerbose(3, 8607504787811871234L, new Object[] { this.m_friendlyName });
            }
            final int intValue = (int)this.m_runtimeProps.getProperty("PROGRESS.Session.sessionModel");
            this.initCertStorePath();
            this.m_poolManager = new WSAProxyPool(this.m_friendlyName, this.m_runtimeProps, this.getApplicationLog(), intValue);
        }
    }
    
    public void loadRuntimeProps(final String s) {
        final AppLogger appLogger = (AppLogger)this.m_context.get("psc.wsa.log");
        try {
            final Document file = this.m_parser.parseFile(s, 9);
            if (file == null) {
                appLogger.logError("Error parsing %s. Using cached copy of runtime properties and disabling service.", new Object[] { s });
                this.m_runtimeProps.setProperty("PROGRESS.Session.serviceAvailable", new Boolean(false));
            }
            else {
                this.m_runtimeProps.readXML(file.getDocumentElement());
            }
        }
        catch (IOException ex) {
            appLogger.logError(8607504787811871235L, new Object[] { s });
            try {
                this.saveRuntimeProps(s);
            }
            catch (WsaSOAPException ex2) {}
        }
        if (null != this.m_context) {
            final WsaProperties wsaProperties = (WsaProperties)this.m_context.get("psc.wsa.params");
            if (null == wsaProperties.actionalGroup) {
                this.m_runtimeProps.setProperty("PROGRESS.Session.actionalGroupName", "OpenEdge");
            }
            else {
                this.m_runtimeProps.setProperty("PROGRESS.Session.actionalGroupName", wsaProperties.actionalGroup);
            }
        }
    }
    
    public void saveRuntimeProps(final String s) throws WsaSOAPException {
        AppLogger appLogger = null;
        if (this.m_context != null) {
            appLogger = (AppLogger)this.m_context.get("psc.wsa.log");
        }
        if (this.m_runtimeProps == null) {
            throw new WsaSOAPException("SOAP-ENV:Server.Wsa", "Unable to save runtime properties");
        }
        if (this.m_parser == null) {
            throw new WsaSOAPException("SOAP-ENV:Server.Wsa", "Unable to create XML parser");
        }
        if (appLogger != null && appLogger.ifLogVerbose(8L, 3)) {
            appLogger.logVerbose(3, 8607504787811871236L, new Object[] { this.m_friendlyName });
        }
        try {
            this.m_parser.serializeObject(this.m_runtimeProps, s);
        }
        catch (Exception ex) {
            throw new WsaSOAPException("SOAP-ENV:Server.Wsa", "Exception saving runtime properties", null, ex);
        }
    }
    
    public boolean saveChanges() throws WsaSOAPException {
        boolean b = false;
        if (this.m_runtimeProps != null && this.m_runtimeProps.isDirty()) {
            final StringBuffer sb = new StringBuffer();
            sb.append("/");
            sb.append(this.m_instanceName);
            sb.append("/");
            sb.append(this.m_friendlyName);
            sb.append(".props");
            this.saveRuntimeProps(this.m_context.getRealPath(sb.toString()));
            b = true;
        }
        return b;
    }
    
    public void shutdown() {
        if (this.m_poolManager != null) {
            this.m_poolManager.shutdown();
            this.m_poolManager = null;
        }
    }
    
    public void enableApp() {
        final AppLogger appLogger = (AppLogger)this.m_context.get("psc.wsa.log");
        final int appStatus = this.getAppStatus();
        this.getRuntimeProperties().setProperty("PROGRESS.Session.serviceAvailable", new Integer(1));
        if (this.getAppStatus() != appStatus && !this.m_friendlyName.equals("default")) {
            if (appLogger.ifLogVerbose(8L, 3)) {
                appLogger.logVerbose(3, 8607504787811871234L, new Object[] { this.m_friendlyName });
            }
            final int intValue = (int)this.m_runtimeProps.getProperty("PROGRESS.Session.sessionModel");
            this.initCertStorePath();
            this.m_poolManager = new WSAProxyPool(this.m_friendlyName, this.m_runtimeProps, this.getApplicationLog(), intValue);
        }
    }
    
    public void disableApp() {
        final int appStatus = this.getAppStatus();
        this.getRuntimeProperties().setProperty("PROGRESS.Session.serviceAvailable", new Integer(0));
        if (this.getAppStatus() != appStatus && this.m_poolManager != null && !this.m_friendlyName.equals("default")) {
            this.m_poolManager.shutdown();
            this.m_poolManager = null;
        }
    }
    
    public AppLogger getApplicationLog() {
        if (null == this.m_appLog) {
            final AppLogger appLog = (AppLogger)this.m_context.get("psc.wsa.log");
            if (this.m_friendlyName.equals("default")) {
                this.m_appLog = appLog;
            }
            else {
                (this.m_appLog = new AppLogger(appLog)).setExecEnvId((null != this.m_friendlyName) ? this.m_friendlyName : "WEB_SERVICE");
                if (null != this.m_runtimeProps) {
                    this.m_appLog.setLoggingLevel((int)this.m_runtimeProps.getProperty("PROGRESS.Session.serviceLoggingLevel"));
                    this.m_appLog.setLogEntries((String)this.m_runtimeProps.getProperty("PROGRESS.Session.serviceLoggingEntryTypes"));
                }
            }
        }
        return this.m_appLog;
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        if (this.m_version < 1) {
            this.m_version = 1;
            this.m_dirty = true;
        }
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        this.m_dirty = false;
    }
    
    private void dumpDescriptor() {
        final StringBuffer sb = new StringBuffer();
        sb.append("/");
        sb.append(this.m_instanceName);
        sb.append("/");
        final StringBuffer sb2 = new StringBuffer(sb.toString());
        sb2.append(this.m_friendlyName);
        sb2.append(".dd");
        final String realPath = this.m_context.getRealPath(sb2.toString());
        Writer writer = null;
        final OutputFormat outputFormat = new OutputFormat();
        try {
            writer = new OutputStreamWriter(new FileOutputStream(realPath), "UTF-8");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        final XMLSerializer xmlSerializer = new XMLSerializer(writer, outputFormat);
        try {
            ((BaseMarkupSerializer)xmlSerializer).startDocument();
            xmlSerializer.startElement("", "ProgressDeploymentDescriptors", "ProgressDeploymentDescriptors", (Attributes)null);
            final Enumeration<PscDeploymentDescriptor> elements = this.m_dds.elements();
            while (elements.hasMoreElements()) {
                xmlSerializer.startElement("", "DeploymentDescriptor", "DeploymentDescriptor", (Attributes)null);
                elements.nextElement().writeXML(xmlSerializer, "", null);
                xmlSerializer.endElement("", "DeploymentDescriptor", "DeploymentDescriptor");
            }
            xmlSerializer.endElement("", "ProgressDeploymentDescriptors", "ProgressDeploymentDescriptors");
            ((BaseMarkupSerializer)xmlSerializer).endDocument();
            writer.close();
        }
        catch (Exception ex2) {
            ex2.printStackTrace();
        }
    }
    
    public void setRuntimeProperties(final Hashtable runtimeProperties) throws WsaSOAPException {
        final AppLogger appLogger = (AppLogger)this.m_context.get("psc.wsa.log");
        final int appStatus = this.getAppStatus();
        this.m_runtimeProps.setRuntimeProperties(runtimeProperties);
        if (!this.m_friendlyName.equals("default")) {
            final AppLogger applicationLog = this.getApplicationLog();
            if (runtimeProperties.containsKey("serviceLoggingLevel")) {
                applicationLog.setLoggingLevel(runtimeProperties.get("serviceLoggingLevel"));
            }
            if (runtimeProperties.containsKey("serviceLoggingEntryTypes")) {
                applicationLog.resetLogEntries((String)runtimeProperties.get("serviceLoggingEntryTypes"));
            }
        }
        final int appStatus2;
        if ((appStatus2 = this.getAppStatus()) != appStatus) {
            if (!this.m_friendlyName.equals("default") && appStatus2 == 0) {
                if (appLogger.ifLogVerbose(8L, 3)) {
                    appLogger.logVerbose(3, 8607504787811871234L, new Object[] { this.m_friendlyName });
                }
                final int intValue = (int)this.m_runtimeProps.getProperty("PROGRESS.Session.sessionModel");
                this.initCertStorePath();
                this.m_poolManager = new WSAProxyPool(this.m_friendlyName, this.m_runtimeProps, this.getApplicationLog(), intValue);
            }
            else {
                this.m_poolManager.shutdown();
                this.m_poolManager = null;
            }
        }
        if (!this.m_inContainer && this.m_runtimeProps.isDirty()) {
            final StringBuffer sb = new StringBuffer(256);
            sb.append("/");
            sb.append(this.m_instanceName);
            sb.append("/");
            final StringBuffer sb2 = new StringBuffer(sb.toString());
            sb2.append(this.m_friendlyName);
            sb2.append(".props");
            this.saveRuntimeProps(this.m_context.getRealPath(sb2.toString()));
        }
    }
    
    public void setSchemaLocation(final String s) {
    }
    
    public String getSchemaLocation() {
        return this.m_schemaLocation;
    }
    
    public void setTargetNamespace(final String namespace) {
        this.m_namespace = namespace;
    }
    
    public String getTargetNamespace() {
        return this.m_namespace;
    }
    
    public void setRoot(final String s) {
    }
    
    public String getRoot() {
        return "AppContainer";
    }
    
    public void setPrefix(final String prefix) {
        this.m_prefix = prefix;
    }
    
    public String getPrefix() {
        return this.m_prefix;
    }
    
    public void setParser(final WsaParser parser) {
        this.m_parser = parser;
    }
    
    public void setXMLType(final String s) {
    }
    
    public String getXMLType() {
        return "AppContainerType";
    }
    
    public void writeXML(final XMLSerializer xmlSerializer, final String s, final String str) throws SAXException {
        boolean b = false;
        if (str != null && str.length() > 0) {
            b = true;
        }
        String string;
        if (b) {
            string = str + ":FriendlyName";
        }
        else {
            string = "FriendlyName";
        }
        if (this.m_friendlyName != null) {
            xmlSerializer.startElement(s, "FriendlyName", string, (Attributes)null);
            ((BaseMarkupSerializer)xmlSerializer).characters(this.m_friendlyName.toCharArray(), 0, this.m_friendlyName.length());
            xmlSerializer.endElement(s, "FriendlyName", string);
        }
        else {
            final AttributesImpl attributesImpl = new AttributesImpl();
            attributesImpl.addAttribute("http://www.w3.org/2001/XMLSchema-instance", "nil", "xsi:nil", "CDATA", "true");
            xmlSerializer.startElement(s, "FriendlyName", string, (Attributes)attributesImpl);
            xmlSerializer.endElement(s, "FriendlyName", string);
        }
        String string2;
        if (b) {
            string2 = str + ":ApplicationRuntimeProperties";
        }
        else {
            string2 = "ApplicationRuntimeProperties";
        }
        if (this.m_runtimeProps != null) {
            xmlSerializer.startElement(s, "ApplicationRuntimeProperties", string2, (Attributes)null);
            this.m_runtimeProps.writeXML(xmlSerializer, s, str);
            xmlSerializer.endElement(s, "ApplicationRuntimeProperties", string2);
        }
        else {
            final AttributesImpl attributesImpl2 = new AttributesImpl();
            attributesImpl2.addAttribute("http://www.w3.org/2001/XMLSchema-instance", "nil", "xsi:nil", "CDATA", "true");
            xmlSerializer.startElement(s, "ApplicationRuntimeProperties", string2, (Attributes)attributesImpl2);
            xmlSerializer.endElement(s, "ApplicationRuntimeProperties", string2);
        }
        if (this.m_WSAD != null) {
            String string3;
            if (b) {
                string3 = str + ":WSAD";
            }
            else {
                string3 = "WSAD";
            }
            xmlSerializer.startElement(s, "WSAD", string3, (Attributes)null);
            this.m_WSAD.writeXML(xmlSerializer, s, str);
            xmlSerializer.endElement(s, "WSAD", string3);
        }
    }
    
    public void readXML(final Node node) {
        final NodeList childNodes = node.getChildNodes();
        for (int length = childNodes.getLength(), i = 0; i < length; ++i) {
            final Node item = childNodes.item(i);
            if (item.getNodeType() == 1) {
                final String localName = item.getLocalName();
                final String nodeValue = WsaParser.extractNodeValue(item);
                if (localName.equals("FriendlyName")) {
                    this.m_friendlyName = nodeValue;
                }
                else if (localName.equals("ApplicationRuntimeProperties")) {
                    (this.m_runtimeProps = new AppRuntimeProps()).readXML(item);
                }
                else if (localName.equals("WSAD")) {
                    (this.m_WSAD = new WSAD()).readXML(item);
                }
            }
        }
    }
    
    public void saveWSDFile(final String s) throws IOException {
        boolean b = false;
        if (this.m_WSAD == null) {
            throw new IOException("Unable to save export file due to corrupt application descriptor");
        }
        if (this.m_parser == null) {
            this.m_parser = new WsaParser(null, null);
            b = true;
        }
        this.m_parser.serializeObject(this, s);
        if (b) {
            this.m_parser = null;
        }
    }
    
    public String toString() {
        String serializeObject = null;
        if (this.m_parser == null) {
            this.m_parser = new WsaParser(null, null);
        }
        try {
            serializeObject = this.m_parser.serializeObject(this, (String)null);
        }
        catch (IOException ex) {}
        return serializeObject;
    }
    
    public String toString(final boolean b) {
        String serializeObject = null;
        if (this.m_parser == null) {
            this.m_parser = new WsaParser(null, null);
        }
        try {
            serializeObject = this.m_parser.serializeObject(this, null, b);
        }
        catch (IOException ex) {}
        return serializeObject;
    }
    
    private void initCertStorePath() {
        String s = (String)this.m_context.get("psc.wsa.certstore.path");
        if (s == null) {
            s = "certs";
        }
        this.m_runtimeProps.setStringProperty("PROGRESS.Session.certificateStore", s);
    }
}
