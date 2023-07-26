// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa;

import com.actional.lg.interceptor.sdk.FlowStub;
import com.actional.lg.interceptor.sdk.SiteStub;
import com.actional.lg.interceptor.sdk.InteractionStub;
import org.apache.soap.util.Provider;
import org.apache.soap.rpc.RPCMessage;
import com.actional.lg.interceptor.sdk.ClientInteraction;
import org.apache.soap.Envelope;
import javax.xml.parsers.DocumentBuilder;
import org.apache.soap.rpc.Call;
import org.apache.soap.util.xml.XMLJavaMappingRegistry;
import org.apache.soap.encoding.SOAPMappingRegistry;
import org.apache.soap.Header;
import org.apache.soap.rpc.Response;
import com.actional.lg.interceptor.sdk.Interaction;
import com.actional.lg.interceptor.sdk.helpers.InterHelpBase;
import com.actional.lg.interceptor.sdk.ServerInteraction;
import org.apache.soap.providers.RPCJavaProvider;
import org.apache.soap.server.DeploymentDescriptor;
import org.apache.soap.server.ServiceManager;
import org.apache.soap.server.RPCRouter;
import org.apache.soap.server.ServerUtils;
import org.apache.soap.Constants;
import org.apache.soap.rpc.SOAPContext;
import com.progress.common.util.PasswordString;
import com.progress.auth.PscUser;
import org.apache.soap.transport.TransportMessage;
import java.io.ByteArrayInputStream;
import org.apache.soap.SOAPException;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import java.io.StringWriter;
import org.apache.soap.util.xml.XMLParserUtils;
import org.w3c.dom.Element;
import java.util.Vector;
import com.progress.wsa.admin.PscDeploymentDescriptor;
import org.apache.soap.Fault;
import java.io.FileReader;
import javax.security.auth.login.LoginException;
import javax.security.auth.login.AccountExpiredException;
import javax.security.auth.login.CredentialExpiredException;
import javax.security.auth.login.FailedLoginException;
import java.io.Writer;
import java.io.PrintWriter;
import com.progress.common.ehnlog.LogHandler;
import java.io.InputStream;
import com.progress.wsa.admin.PscConfigManager2;
import com.progress.wsa.admin.PscConfigManager;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import com.progress.common.ehnlog.AppLogger;
import com.progress.common.message.IProMessage;
import com.progress.common.exception.ExceptionMessageAdapter;
import com.progress.common.util.PromsgsBundle;
import com.progress.common.util.InstallPath;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.apache.soap.transport.EnvelopeEditor;
import com.progress.common.util.UUID;
import com.progress.common.property.PropertyManager;
import java.util.Hashtable;
import com.progress.wsa.admin.PscServiceManager;
import com.progress.common.ehnlog.IAppLogger;

public class ApacheSOAPEngine implements WsaSOAPEngine, WsaConstants
{
    protected String m_wsaVersion;
    protected String m_instanceName;
    protected String m_propertiesFilePath;
    protected String m_deploymentDirectory;
    protected String m_certStorePath;
    protected IAppLogger m_log;
    protected IAppLogger m_externalLogger;
    protected String m_installDirectory;
    protected String m_wsaBuildDate;
    protected PscServiceManager m_serviceMgr;
    protected Hashtable m_faultStrings;
    protected ClassLoader m_engineClassLoader;
    protected StringBuffer m_requestIDBuf;
    protected WsaSecurityManagerImpl m_securityManager;
    private WsaProperties m_properties;
    private PropertyManager m_externalPropertyManager;
    private String m_engineType;
    private String m_propertiesSectionName;
    private WsaSOAPEngineContext m_context;
    private UUID m_instanceUUID;
    private WsaStats m_statistics;
    private WsaState m_state;
    protected long m_requestReferenceId;
    private EnvelopeEditor m_editor;
    private int m_refCount;
    private String[] m_roleNames;
    
    public ApacheSOAPEngine() {
        this.m_wsaVersion = "9.1Dxxxx";
        this.m_instanceName = null;
        this.m_propertiesFilePath = null;
        this.m_deploymentDirectory = null;
        this.m_certStorePath = null;
        this.m_log = null;
        this.m_externalLogger = null;
        this.m_installDirectory = null;
        this.m_wsaBuildDate = "N/A";
        this.m_serviceMgr = null;
        this.m_faultStrings = new Hashtable();
        this.m_engineClassLoader = null;
        this.m_requestIDBuf = new StringBuffer();
        this.m_securityManager = null;
        this.m_properties = null;
        this.m_externalPropertyManager = null;
        this.m_engineType = "Adapter";
        this.m_context = new WsaSOAPEngineContext(System.getProperties());
        this.m_instanceUUID = new UUID();
        this.m_statistics = new WsaStats();
        this.m_state = WsaState.UNINITIALIZED;
        this.m_requestReferenceId = 0L;
        this.m_editor = null;
        this.m_refCount = 0;
    }
    
    public ApacheSOAPEngine(final String engineType) throws WsaSOAPEngineException {
        this.m_wsaVersion = "9.1Dxxxx";
        this.m_instanceName = null;
        this.m_propertiesFilePath = null;
        this.m_deploymentDirectory = null;
        this.m_certStorePath = null;
        this.m_log = null;
        this.m_externalLogger = null;
        this.m_installDirectory = null;
        this.m_wsaBuildDate = "N/A";
        this.m_serviceMgr = null;
        this.m_faultStrings = new Hashtable();
        this.m_engineClassLoader = null;
        this.m_requestIDBuf = new StringBuffer();
        this.m_securityManager = null;
        this.m_properties = null;
        this.m_externalPropertyManager = null;
        this.m_engineType = "Adapter";
        this.m_context = new WsaSOAPEngineContext(System.getProperties());
        this.m_instanceUUID = new UUID();
        this.m_statistics = new WsaStats();
        this.m_state = WsaState.UNINITIALIZED;
        this.m_requestReferenceId = 0L;
        this.m_editor = null;
        this.m_refCount = 0;
        this.m_engineType = engineType;
        if ("Adapter".equals(this.m_engineType)) {
            this.m_propertiesSectionName = "WSA";
        }
        else if ("AppServer".equals(this.m_engineType)) {
            this.m_propertiesSectionName = "ubroker.as";
        }
        else {
            if (!"Container".equals(this.m_engineType)) {
                throw new WsaSOAPEngineException("Invalid WsaSOAPEngine type: %d", new Object[] { engineType });
            }
            this.m_propertiesSectionName = "WSA";
        }
    }
    
    protected void finalize() throws Throwable {
        this.shutdown();
    }
    
    public void initialize(final Map m) throws WsaSOAPEngineException {
        final String s = m.get("InstallDir");
        final String s2 = (String)m.get("instanceName");
        final String s3 = (String)m.get("deploymentDir");
        final String s4 = (String)m.get("certStorePath");
        final HashMap hashMap = new HashMap(m);
        hashMap.remove("InstallDir");
        hashMap.remove("instanceName");
        hashMap.remove("deploymentDir");
        hashMap.remove("certStorePath");
        this.initialize(s, s2, null, s3, s4, this.m_externalLogger, hashMap);
    }
    
    public void initialize(final String s, final String s2, final String s3, final String s4, final String s5) throws WsaSOAPEngineException {
        this.initialize(s, s2, s3, s4, s5, this.m_externalLogger, this.m_externalPropertyManager);
    }
    
    private void initialize(final String installDirectory, final String instanceName, final String propertiesFilePath, String string, final String certStorePath, final IAppLogger appLogger, final Object o) throws WsaSOAPEngineException {
        if (WsaState.UNINITIALIZED != this.m_state) {
            throw new WsaSOAPEngineException("Cannot intialize engine from a %s state.", new Object[] { this.m_state.toString() });
        }
        this.m_installDirectory = installDirectory;
        this.m_instanceName = instanceName;
        this.m_propertiesFilePath = propertiesFilePath;
        if (!string.endsWith("/") && !string.endsWith("\\")) {
            string += File.separatorChar;
        }
        this.m_deploymentDirectory = string;
        this.m_certStorePath = certStorePath;
        Label_0201: {
            if (null != this.m_installDirectory && 0 < this.m_installDirectory.length()) {
                Label_0227: {
                    try {
                        if (!new File(this.m_installDirectory).isDirectory()) {
                            this.m_state = WsaState.INITFAILURE;
                            throw new WsaSOAPEngineException("Invalid WsaSOAPEngine init parameter: %s", new Object[] { "Progress installation directory" });
                        }
                        break Label_0227;
                    }
                    catch (Exception ex5) {
                        this.m_state = WsaState.INITFAILURE;
                        throw new WsaSOAPEngineException("Invalid WsaSOAPEngine init parameter: %s", new Object[] { "Progress installation directory" });
                    }
                    break Label_0201;
                }
                if (null != this.m_instanceName && 0 < this.m_instanceName.length()) {
                    Label_0406: {
                        if (null != this.m_propertiesFilePath && 0 < this.m_propertiesFilePath.length()) {
                            try {
                                if (new File(this.m_propertiesFilePath).isDirectory()) {
                                    this.m_state = WsaState.INITFAILURE;
                                    throw new WsaSOAPEngineException("Invalid WsaSOAPEngine init parameter: %s", new Object[] { "properties file path" });
                                }
                                break Label_0406;
                            }
                            catch (Exception ex6) {
                                this.m_state = WsaState.INITFAILURE;
                                throw new WsaSOAPEngineException("Invalid WsaSOAPEngine init parameter: %s", new Object[] { "properties file path" });
                            }
                        }
                        if (!(o instanceof Map)) {
                            this.m_state = WsaState.INITFAILURE;
                            throw new WsaSOAPEngineException("Missing required WsaSOAPEngine init parameter: %s", new Object[] { "properties file path" });
                        }
                    }
                    Label_0503: {
                        if (null != this.m_deploymentDirectory && 0 < this.m_deploymentDirectory.length()) {
                            Label_0529: {
                                try {
                                    if (!new File(this.m_deploymentDirectory).isDirectory()) {
                                        this.m_state = WsaState.INITFAILURE;
                                        throw new WsaSOAPEngineException("Invalid WsaSOAPEngine init parameter: %s", new Object[] { "deployment directory" });
                                    }
                                    break Label_0529;
                                }
                                catch (Exception ex7) {
                                    this.m_state = WsaState.INITFAILURE;
                                    throw new WsaSOAPEngineException("Invalid WsaSOAPEngine init parameter: %s", new Object[] { "deployment directory" });
                                }
                                break Label_0503;
                                try {
                                    try {
                                        new WsaVersionInfo().getClass();
                                        this.m_wsaVersion = "10.2B07";
                                    }
                                    catch (Exception ex8) {
                                        System.err.println("Could not obtain WSA Java version information.");
                                    }
                                    InstallPath.setInstallPath(this.m_installDirectory);
                                    try {
                                        ExceptionMessageAdapter.setMessageSubsystem(new PromsgsBundle());
                                    }
                                    catch (Exception ex) {
                                        this.m_state = WsaState.INITFAILURE;
                                        throw new WsaSOAPEngineException("Unable to load promsgs file : %s", new Object[] { ex.getMessage() }, ex);
                                    }
                                    this.m_properties = new WsaProperties(this.m_installDirectory, this.m_instanceName, this.m_propertiesFilePath, this.m_deploymentDirectory, appLogger);
                                    this.m_log = this.m_properties.processArgs(this.m_propertiesSectionName, o);
                                    if (null == this.m_log) {
                                        try {
                                            if (null == new AppLogger("wsa1.log", 1, 2, 0L, 0, "", this.m_instanceName, "Wsa")) {
                                                throw new WsaSOAPEngineException("Cannot create primary or secondary log files.");
                                            }
                                        }
                                        catch (IOException ex2) {
                                            System.err.println("Could not open logfile  : " + ex2.toString());
                                        }
                                    }
                                    this.m_log.setExecEnvId(this.m_instanceName);
                                    this.m_log.logVerbose(0, 8607504787811871100L, new Object[] { this.m_instanceName, this.m_wsaVersion, this.m_instanceUUID.toString() });
                                    if (this.m_log.ifLogLevel(3)) {
                                        this.m_properties.print(2, 4);
                                    }
                                    this.m_context.put("psc.wsa.engine", this);
                                    this.m_context.put("psc.wsa.engine.type", this.m_engineType);
                                    this.m_context.put("psc.wsa.log", this.m_log);
                                    this.m_context.put("psc.wsa.params", this.m_properties);
                                    this.m_context.put("psc.wsa.engine.state", this.m_state.toString());
                                    this.m_context.put("psc.wsa.uuid", this.getInstanceUUID());
                                    this.m_context.put("psc.wsa.install.dir", this.m_installDirectory);
                                    this.m_context.put("psc.wsa.deployment.path", this.m_deploymentDirectory);
                                    this.m_context.put("psc.wsa.certstore.path", this.m_certStorePath);
                                    this.m_context.put("psc.wsa.instance.name", this.m_instanceName);
                                    this.m_context.put("psc.wsa.version", this.m_wsaVersion);
                                    this.m_context.put("psc.wsa.build", this.m_wsaBuildDate);
                                    synchronized (System.err) {
                                        final PrintStream err = new PrintStream(new ByteArrayOutputStream());
                                        final PrintStream err2 = System.err;
                                        try {
                                            System.setErr(err);
                                        }
                                        catch (Exception ex9) {
                                            this.m_log.logError("Could not set syserr when creating ServiceManager");
                                        }
                                        try {
                                            this.m_serviceMgr = new PscServiceManager(this.m_context, null);
                                        }
                                        catch (Exception ex3) {
                                            System.setErr(err2);
                                            this.m_state = WsaState.INITFAILURE;
                                            final Object[] array = { this.m_instanceName };
                                            this.m_log.logStackTrace(8607504787811871398L, array, ex3);
                                            throw new WsaSOAPEngineException("Unable to create service manager: %s", array, ex3);
                                        }
                                        try {
                                            System.setErr(err2);
                                        }
                                        catch (Exception ex10) {
                                            this.m_log.logError("Could not reset syserr when creating ServiceManager");
                                        }
                                    }
                                    if (null == this.m_serviceMgr) {
                                        this.m_log.logError("Could not create a new PscServiceManager, using default.");
                                    }
                                    else {
                                        this.m_context.put("psc.wsa.service.manager", this.m_serviceMgr);
                                    }
                                    this.m_securityManager = new WsaSecurityManagerImpl(this.m_context);
                                    this.m_context.put("psc.wsa.security", this.m_securityManager);
                                    this.mapFaultStrings();
                                    this.soapInit();
                                }
                                catch (WsaSOAPEngineException ex4) {
                                    throw ex4;
                                }
                                catch (Throwable t) {
                                    t.printStackTrace();
                                    this.m_state = WsaState.INITFAILURE;
                                    throw new WsaSOAPEngineException("Unexpected exception in init().", new Object[0], t);
                                }
                            }
                            this.m_state = WsaState.RUNNING;
                            return;
                        }
                    }
                    this.m_state = WsaState.INITFAILURE;
                    throw new WsaSOAPEngineException("Missing required WsaSOAPEngine init parameter: %s", new Object[] { "deployment directory" });
                }
                this.m_state = WsaState.INITFAILURE;
                throw new WsaSOAPEngineException("Missing required WsaSOAPEngine init parameter: %s", new Object[] { "instance name" });
            }
        }
        this.m_state = WsaState.INITFAILURE;
        throw new WsaSOAPEngineException("Missing required WsaSOAPEngine init parameter: %s", new Object[] { "Progress installation directory" });
    }
    
    public synchronized void incRefCount() {
        ++this.m_refCount;
    }
    
    public void shutdown() throws WsaSOAPEngineException {
        final int refCount = this.m_refCount - 1;
        this.m_refCount = refCount;
        if (refCount <= 0 && WsaState.RUNNING == this.m_state) {
            this.m_state = WsaState.SHUTDOWN;
            if (null != this.m_properties) {
                this.m_properties.webAppEnabled = false;
            }
            if (null != this.m_serviceMgr) {
                final PscConfigManager2 configManager = this.m_serviceMgr.configManager();
                if (null != configManager && configManager instanceof PscConfigManager) {
                    configManager.shutdown();
                }
            }
            this.m_state = WsaState.STOPPED;
        }
    }
    
    public WsaSOAPRequest createRequest(final InputStream inputStream, final int n, final String s) throws WsaSOAPEngineException {
        return this.createRequest(inputStream, n, s, "unknown", "unknown");
    }
    
    public WsaSOAPRequest createRequest(final InputStream inputStream, final int n, final String s, final String s2, final String s3) throws WsaSOAPEngineException {
        final String requestRefId = this.getRequestRefId();
        if (WsaState.RUNNING != this.m_state) {
            throw new WsaSOAPEngineException("Cannot create requests from a %s state.", new Object[] { this.m_state.toString() });
        }
        if (this.m_log.ifLogVerbose(1L, 0)) {
            this.m_log.logVerbose(0, 8607504787811871407L, new Object[] { requestRefId });
        }
        return new ApacheSOAPRequest(requestRefId, inputStream, n, s, s2, s3);
    }
    
    public IAppLogger getLogger() {
        return this.m_log;
    }
    
    public void setLogger(final LogHandler logHandler) throws WsaSOAPEngineException {
        if (WsaState.UNINITIALIZED != this.m_state) {
            throw new WsaSOAPEngineException("Cannot set logger from a %s state.", new Object[] { this.m_state.toString() });
        }
        try {
            this.m_externalLogger = new AppLogger(logHandler, 4, 1152921504606846975L, "_WSA", "Wsa");
        }
        catch (IOException ex) {
            throw new WsaSOAPEngineException("Error registering external logger.", new Object[0], ex);
        }
    }
    
    public void registerStatistics(final RuntimeStats externalStatistics) {
        this.m_statistics.setExternalStatistics(externalStatistics);
    }
    
    public WsaStats getStatistics() {
        return this.m_statistics;
    }
    
    public void getWSDLListing(final PrintWriter printWriter, final String s, final WsaUser wsaUser) throws FailedLoginException, CredentialExpiredException, AccountExpiredException, LoginException, WsaSOAPEngineException {
        if (WsaState.RUNNING != this.m_state) {
            if (this.m_log.ifLogVerbose(128L, 7)) {
                this.m_log.logVerbose(7, "Cannot return WSDL document, the Wsa SOAP Engine is not running.");
            }
            throw new WsaSOAPEngineException("Cannot return WSDL document, the Wsa SOAP Engine is not running.", new Object[0]);
        }
        if (!this.m_properties.enableWsdlListings) {
            final String formatMessage = AppLogger.formatMessage(8607504787811871441L, new Object[0]);
            if (this.m_log.ifLogVerbose(128L, 7)) {
                this.m_log.logVerbose(7, formatMessage);
            }
            throw new WsaSOAPEngineException(formatMessage);
        }
        if (null == printWriter) {
            final Object[] array = { "outHtmlPage", "getWSDLListing()" };
            if (this.m_log.ifLogBasic(128L, 7)) {
                this.m_log.logBasic(7, "The %s method parameter to %s may not be null", array);
            }
            throw new WsaSOAPEngineException("The %s method parameter to %s may not be null", array);
        }
        if (null == s) {
            final Object[] array2 = { "baseURL", "getWSDLListing()" };
            if (this.m_log.ifLogBasic(128L, 7)) {
                this.m_log.logBasic(7, "The %s method parameter to %s may not be null", array2);
            }
            throw new WsaSOAPEngineException("The %s method parameter to %s may not be null", array2);
        }
        if (this.m_properties.wsdlAuth) {
            if (null == wsaUser) {
                final Object[] array3 = { "user", "getWSDLListing()" };
                if (this.m_log.ifLogBasic(128L, 7)) {
                    this.m_log.logBasic(7, "The %s method parameter to %s may not be null", array3);
                }
                throw new WsaSOAPEngineException("The %s method parameter to %s may not be null", array3);
            }
            if (!this.m_securityManager.authorizeWsdlUser(wsaUser, s)) {
                final Object[] array4 = { wsaUser.getName(), "denied", "WSDL Listing", s };
                if (this.m_log.ifLogVerbose(128L, 7)) {
                    this.m_log.logVerbose(7, 8607504787811871452L, array4);
                }
                this.m_statistics.incrementCounter(3);
                throw new WsaSOAPEngineException(8607504787811871452L, array4);
            }
        }
        try {
            this.m_serviceMgr.generateWSDLList(printWriter, s);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            if (this.m_log.ifLogExtended(128L, 7)) {
                this.m_log.logStackTrace(7, "Error during WSDL document retreival", ex);
            }
            throw new WsaSOAPEngineException(ex.getMessage(), new Object[0], ex);
        }
    }
    
    public void getWSDLDocument(final PrintWriter printWriter, final String s, final WsaUser wsaUser) throws FailedLoginException, CredentialExpiredException, AccountExpiredException, LoginException, WsaSOAPEngineException {
        if (WsaState.RUNNING != this.m_state) {
            if (this.m_log.ifLogVerbose(128L, 7)) {
                this.m_log.logVerbose(7, "Cannot return WSDL document, the Wsa SOAP Engine is not running.");
            }
            throw new WsaSOAPEngineException("Cannot return WSDL document, the Wsa SOAP Engine is not running.", new Object[0]);
        }
        this.m_statistics.incrementCounter(0);
        if (null == printWriter) {
            final Object[] array = { "outWsdlDoc", "getWSDLDocument()" };
            if (this.m_log.ifLogBasic(128L, 7)) {
                this.m_log.logBasic(7, "The %s method parameter to %s may not be null", array);
            }
            throw new WsaSOAPEngineException("The %s method parameter to %s may not be null", array);
        }
        if (null == s) {
            final Object[] array2 = { "urn", "getWSDLDocument()" };
            this.m_log.logError("The %s method parameter to %s may not be null", array2);
            throw new WsaSOAPEngineException("The %s method parameter to %s may not be null", array2);
        }
        if (this.m_properties.wsdlAuth) {
            if (null == wsaUser) {
                final Object[] array3 = { "user", "getWSDLDocument()" };
                this.m_log.logError("The %s method parameter to %s may not be null", array3);
                throw new WsaSOAPEngineException("The %s method parameter to %s may not be null", array3);
            }
            if (!this.m_securityManager.authorizeWsdlUser(wsaUser, s)) {
                final Object[] array4 = { wsaUser.getName(), "denied", "WSDL", s };
                if (this.m_log.ifLogVerbose(128L, 7)) {
                    this.m_log.logVerbose(7, 8607504787811871452L, array4);
                }
                throw new WsaSOAPEngineException(8607504787811871452L, array4);
            }
        }
        try {
            final char[] array5 = new char[8192];
            final FileReader fileReader = new FileReader(new File(this.m_serviceMgr.getWsdlDocPath(s)));
            while (true) {
                final int read = fileReader.read(array5, 0, 8192);
                if (read <= 0) {
                    break;
                }
                printWriter.write(array5, 0, read);
            }
            fileReader.close();
        }
        catch (Exception ex) {
            final Object[] array6 = { s };
            final String formatMessage = AppLogger.formatMessage(8607504787811871442L, array6);
            if (this.m_log.ifLogVerbose(128L, 7)) {
                this.m_log.logVerbose(7, 8607504787811871442L, array6);
            }
            if (this.m_log.ifLogExtended(128L, 7)) {
                this.m_log.logStackTrace(7, formatMessage, ex);
            }
            throw new WsaSOAPEngineException(formatMessage);
        }
    }
    
    public long getWSDLDocumentLen(final String s, final WsaUser wsaUser) throws FailedLoginException, CredentialExpiredException, AccountExpiredException, LoginException, WsaSOAPEngineException {
        if (WsaState.RUNNING != this.m_state) {
            if (this.m_log.ifLogVerbose(128L, 7)) {
                this.m_log.logVerbose(7, "Cannot return WSDL document, the Wsa SOAP Engine is not running.");
            }
            throw new WsaSOAPEngineException("Cannot return WSDL document, the Wsa SOAP Engine is not running.", new Object[0]);
        }
        if (null == s) {
            final Object[] array = { "urn", "getWSDLDocument()" };
            this.m_log.logError("The %s method parameter to %s may not be null", array);
            throw new WsaSOAPEngineException("The %s method parameter to %s may not be null", array);
        }
        if (this.m_properties.wsdlAuth) {
            if (null == wsaUser) {
                final Object[] array2 = { "user", "getWSDLDocument()" };
                this.m_log.logError("The %s method parameter to %s may not be null", array2);
                throw new WsaSOAPEngineException("The %s method parameter to %s may not be null", array2);
            }
            if (!this.m_securityManager.authorizeWsdlUser(wsaUser, s)) {
                final Object[] array3 = { wsaUser.getName(), "denied", "WSDL", s };
                if (this.m_log.ifLogVerbose(128L, 7)) {
                    this.m_log.logVerbose(7, 8607504787811871452L, array3);
                }
                throw new WsaSOAPEngineException(8607504787811871452L, array3);
            }
        }
        try {
            return new File(this.m_serviceMgr.getWsdlDocPath(s)).length();
        }
        catch (Exception ex) {
            final Object[] array4 = { s };
            final String formatMessage = AppLogger.formatMessage(8607504787811871442L, array4);
            if (this.m_log.ifLogVerbose(128L, 7)) {
                this.m_log.logVerbose(7, 8607504787811871442L, array4);
            }
            if (this.m_log.ifLogExtended(128L, 7)) {
                this.m_log.logStackTrace(7, formatMessage, ex);
            }
            throw new WsaSOAPEngineException(formatMessage);
        }
    }
    
    public WsaState getState() {
        return this.m_state;
    }
    
    public void setPropertyManager(final PropertyManager externalPropertyManager) throws WsaSOAPEngineException {
        if (WsaState.UNINITIALIZED != this.m_state) {
            throw new WsaSOAPEngineException("Cannot set property manager from a %s state.", new Object[] { this.m_state.toString() });
        }
        this.m_externalPropertyManager = externalPropertyManager;
    }
    
    public Map getProperties() throws WsaSOAPEngineException {
        if (null == this.m_properties) {
            throw new WsaSOAPEngineException("Uninitialized properties.");
        }
        try {
            return this.m_properties.getRuntimeProperties();
        }
        catch (Exception ex) {
            throw new WsaSOAPEngineException("Error getting engine properties.", ex);
        }
    }
    
    public void setProperties(final Map runtimeProperties) throws WsaSOAPEngineException {
        if (null != this.m_properties && null != runtimeProperties) {
            try {
                this.m_properties.setRuntimeProperties(runtimeProperties);
            }
            catch (Exception ex) {
                this.m_log.logStackTrace(1, "Exception setting engine properties.", ex);
                throw new WsaSOAPEngineException(ex.getMessage());
            }
        }
    }
    
    public Object getProperty(final String s) throws WsaSOAPEngineException {
        return this.getProperties().get(s);
    }
    
    public void setProperty(final String key, final Object value) throws WsaSOAPEngineException {
        final HashMap<String, Object> properties = new HashMap<String, Object>();
        properties.put(key, value);
        this.setProperties(properties);
    }
    
    public WsaSecurityManager getSecurityManager() {
        return this.m_securityManager;
    }
    
    public WsaServiceManager getServiceManager() {
        return this.m_serviceMgr;
    }
    
    public void generateHTTPError(final PrintWriter printWriter, final int n, final String s) throws IOException {
        this.m_serviceMgr.generateHTTPError(printWriter, n, s);
    }
    
    protected String getInstanceUUID() {
        return this.m_instanceUUID.toString();
    }
    
    protected void mapFaultStrings() {
        this.m_faultStrings.put("SOAP-ENV:Server", new Long(8607504787811871373L));
        this.m_faultStrings.put("SOAP-ENV:Client", new Long(8607504787811871374L));
        this.m_faultStrings.put("SOAP-ENV:Server.Wsa", new Long(8607504787811871375L));
        this.m_faultStrings.put("SOAP-ENV:Server.SOAP", new Long(8607504787811871376L));
        this.m_faultStrings.put("SOAP-ENV:Server.Provider", new Long(8607504787811871377L));
        this.m_faultStrings.put("SOAP-ENV:Server.Admin", new Long(8607504787811871378L));
        this.m_faultStrings.put("SOAP-ENV:ServerNetwork", new Long(8607504787811871379L));
        this.m_faultStrings.put("SOAP-ENV:Server.Application-Server", new Long(8607504787811871380L));
        this.m_faultStrings.put("SOAP-ENV:Server.Connection", new Long(8607504787811871381L));
        this.m_faultStrings.put("SOAP-ENV:Server.Application", new Long(8607504787811871382L));
        this.m_faultStrings.put("SOAP-ENV:Server.Network", new Long(8607504787811871383L));
        this.m_faultStrings.put("SOAP-ENV:Client.SOAP-Envelope", new Long(8607504787811871384L));
        this.m_faultStrings.put("SOAP-ENV:Client.SOAP-Header", new Long(8607504787811871385L));
        this.m_faultStrings.put("SOAP-ENV:Client.HTTP-Header", new Long(8607504787811871386L));
        this.m_faultStrings.put("SOAP-ENV:Client.HTTP-URL", new Long(8607504787811871387L));
        this.m_faultStrings.put("SOAP-ENV:Client.Security", new Long(8607504787811871388L));
        this.m_faultStrings.put("SOAP-ENV:Client.SOAP-Body", new Long(8607504787811871389L));
        this.m_faultStrings.put("SOAP-ENV:Client.SOAP-Method", new Long(8607504787811871390L));
        this.m_faultStrings.put("SOAP-ENV:Client.SOAP-Parameter", new Long(8607504787811871391L));
    }
    
    protected void setFaultStringFromFaultCode(final Fault fault) {
        if (null != fault) {
            final String faultCode = fault.getFaultCode();
            final Object[] array = new Object[0];
            Long n = this.m_faultStrings.get(faultCode);
            if (null == n && faultCode.startsWith("SOAP-ENV:Client")) {
                n = this.m_faultStrings.get("SOAP-ENV:Client");
            }
            if (null == n && faultCode.startsWith("SOAP-ENV:Server")) {
                n = this.m_faultStrings.get("SOAP-ENV:Server");
            }
            fault.setFaultString(ExceptionMessageAdapter.getMessage(n, array));
        }
    }
    
    protected void soapInit() throws WsaSOAPEngineException {
        this.m_engineClassLoader = this.getClass().getClassLoader();
    }
    
    protected synchronized String getRequestRefId() {
        this.m_requestIDBuf.setLength(0);
        this.m_requestIDBuf.append(Long.toHexString(this.m_requestReferenceId++));
        return this.m_requestIDBuf.toString();
    }
    
    public Vector buildFaultDetailFromException(final int n, final Throwable t, final String str, final PscDeploymentDescriptor pscDeploymentDescriptor) {
        final Vector<Element> vector = new Vector<Element>();
        String defaultEncoding = null;
        if (null != pscDeploymentDescriptor && 1 == pscDeploymentDescriptor.defaultEncodingType()) {
            defaultEncoding = pscDeploymentDescriptor.defaultEncoding();
        }
        final Document document = XMLParserUtils.getXMLDocBuilder().newDocument();
        final Element element = document.createElement("ns1:FaultDetail");
        final Attr attribute = document.createAttribute("xmlns:ns1");
        attribute.setValue("urn:soap-fault:details");
        element.setAttributeNode(attribute);
        if (null != defaultEncoding) {
            final Attr attribute2 = document.createAttribute("SOAP-ENV:encodingStyle");
            attribute2.setValue(defaultEncoding);
            element.setAttributeNode(attribute2);
        }
        vector.addElement(element);
        final StringWriter out = new StringWriter();
        final PrintWriter s = new PrintWriter(out);
        switch (n) {
            case 1:
            case 2: {
                s.print(t.getMessage());
                break;
            }
            case 3: {
                s.print(t.toString());
                break;
            }
            default: {
                t.printStackTrace(s);
                break;
            }
        }
        s.close();
        final String string = out.toString();
        final Element element2 = document.createElement("errorMessage");
        element2.appendChild(document.createTextNode(string));
        final StringBuffer sb = new StringBuffer();
        sb.append(this.getInstanceUUID());
        sb.append("#");
        sb.append(str);
        final Element element3 = document.createElement("requestID");
        element3.appendChild(document.createTextNode(sb.toString()));
        if (null != defaultEncoding) {
            final Attr attribute3 = document.createAttribute("xsi:type");
            attribute3.setValue("xsd:string");
            final Attr attribute4 = document.createAttribute("xsi:type");
            attribute4.setValue("xsd:string");
            element2.setAttributeNode(attribute3);
            element3.setAttributeNode(attribute4);
        }
        element.appendChild(element2);
        element.appendChild(element3);
        return vector;
    }
    
    protected void dumpFullStackTrace(final IAppLogger appLogger, final String s, final Throwable t) {
        int n = 0;
        try {
            this.m_log.logStackTrace(1, 8607504787811871421L, new Object[] { s, Integer.toString(n) }, t);
            ++n;
            if (t instanceof SOAPException) {
                Object o = ((SOAPException)t).getTargetException();
                while (null != o) {
                    this.m_log.logStackTrace(0, 8607504787811871421L, new Object[] { s, Integer.toHexString(n) }, (Throwable)o);
                    ++n;
                    if (o instanceof SOAPException) {
                        o = ((SOAPException)o).getTargetException();
                    }
                    else {
                        o = null;
                    }
                }
            }
        }
        catch (Exception ex) {
            this.m_log.logStackTrace(0, "Error dumping internal stack trace dump", t);
        }
    }
    
    protected InputStream getEnvelopeInputStream(final WsaSOAPRequest wsaSOAPRequest) throws WsaSOAPException, IOException {
        InputStream inputStream = wsaSOAPRequest.getInputStream();
        if (this.shouldDumpRawSOAP()) {
            final int length = wsaSOAPRequest.getLength();
            if (0 < length) {
                int off;
                int read;
                byte[] buf;
                for (off = 0, read = 0, buf = new byte[length]; off < length && read >= 0; read = inputStream.read(buf, off, length - off), off += read) {}
                if (off < length) {
                    throw new WsaSOAPException("SOAP-ENV:Server.Wsa", "Could not read the entire Web Service request.", new Object[0]);
                }
                int min = length;
                if (-1 != this.m_properties.logMsgThreshold) {
                    min = Math.min(this.m_properties.logMsgThreshold, length);
                }
                if (0 < min) {
                    this.m_log.logExtended(14, 8607504787811871444L, new Object[] { new String(buf, 0, min) });
                }
                inputStream = new ByteArrayInputStream(buf);
            }
        }
        return inputStream;
    }
    
    private boolean shouldDumpRawSOAP() {
        return null != this.m_log && this.m_log.ifLogExtended(16384L, 14) && null != this.m_properties.debugClients;
    }
    
    private void dumpRawSOAP(final TransportMessage transportMessage) {
        if (!this.shouldDumpRawSOAP()) {
            return;
        }
        int length;
        final int b = length = transportMessage.getBytes().length;
        if (-1 != this.m_properties.logMsgThreshold) {
            length = Math.min(this.m_properties.logMsgThreshold, b);
        }
        if (0 < length) {
            this.m_log.logExtended(14, 8607504787811871440L, new Object[] { new String(transportMessage.getBytes(), 0, length) });
        }
    }
    
    private class ApacheSOAPRequest implements WsaSOAPRequest
    {
        private String m_serverName;
        private String m_clientIPAddress;
        private String m_webServiceURLPath;
        private PscUser m_userObject;
        private InputStream m_inputStream;
        private boolean m_builtInService;
        private String m_requestID;
        private Hashtable m_options;
        private int m_length;
        private String m_type;
        private String m_user;
        private PasswordString m_password;
        private String m_lgHeader;
        
        public ApacheSOAPRequest(final String requestID) {
            this.m_serverName = null;
            this.m_clientIPAddress = null;
            this.m_webServiceURLPath = null;
            this.m_userObject = null;
            this.m_inputStream = null;
            this.m_builtInService = false;
            this.m_requestID = "";
            this.m_options = new Hashtable();
            this.m_length = 0;
            this.m_type = "text/html";
            this.m_user = "admin";
            this.m_password = null;
            this.m_lgHeader = null;
            this.m_requestID = requestID;
        }
        
        public ApacheSOAPRequest(final String requestID, final boolean builtInService, final InputStream inputStream) {
            this.m_serverName = null;
            this.m_clientIPAddress = null;
            this.m_webServiceURLPath = null;
            this.m_userObject = null;
            this.m_inputStream = null;
            this.m_builtInService = false;
            this.m_requestID = "";
            this.m_options = new Hashtable();
            this.m_length = 0;
            this.m_type = "text/html";
            this.m_user = "admin";
            this.m_password = null;
            this.m_lgHeader = null;
            this.m_requestID = requestID;
            this.m_builtInService = builtInService;
            this.m_inputStream = inputStream;
        }
        
        public ApacheSOAPRequest(final String requestID, final InputStream inputStream, final int length, final String type, final String serverName, final String clientIPAddress) {
            this.m_serverName = null;
            this.m_clientIPAddress = null;
            this.m_webServiceURLPath = null;
            this.m_userObject = null;
            this.m_inputStream = null;
            this.m_builtInService = false;
            this.m_requestID = "";
            this.m_options = new Hashtable();
            this.m_length = 0;
            this.m_type = "text/html";
            this.m_user = "admin";
            this.m_password = null;
            this.m_lgHeader = null;
            this.m_requestID = requestID;
            this.m_inputStream = inputStream;
            this.m_length = length;
            this.m_type = type;
            this.m_serverName = serverName;
            this.m_clientIPAddress = clientIPAddress;
        }
        
        public String requestID() {
            return this.m_requestID;
        }
        
        public InputStream getInputStream() {
            return this.m_inputStream;
        }
        
        public void setInputStream(final InputStream inputStream) {
            this.m_inputStream = inputStream;
        }
        
        public PscUser getUserObject() {
            return this.m_userObject;
        }
        
        public void setUserObject(final PscUser userObject) {
            this.m_userObject = userObject;
        }
        
        public String getWebServiceURLPath() {
            return this.m_webServiceURLPath;
        }
        
        public void setWebServiceURLPath(final String webServiceURLPath) {
            this.m_webServiceURLPath = webServiceURLPath;
        }
        
        public String getClientIPAddress() {
            return this.m_clientIPAddress;
        }
        
        public void setClientIPAddress(final String clientIPAddress) {
            this.m_clientIPAddress = clientIPAddress;
        }
        
        public String getServerName() {
            return this.m_serverName;
        }
        
        public void setServerName(final String serverName) {
            this.m_serverName = serverName;
        }
        
        public boolean getBuiltInService() {
            return this.m_builtInService;
        }
        
        public void setBuiltInService(final boolean builtInService) {
            this.m_builtInService = builtInService;
        }
        
        public Object getOption(final String key) throws WsaSOAPEngineException {
            return this.m_options.get(key);
        }
        
        public void setOption(final String key, final Object value) throws WsaSOAPEngineException {
            this.m_options.put(key, value);
        }
        
        public WsaSOAPResponse execute() throws FailedLoginException, CredentialExpiredException, AccountExpiredException, LoginException, WsaSOAPEngineException {
            Call callFromEnvelope = null;
            PscDeploymentDescriptor pscDeploymentDescriptor = null;
            final DocumentBuilder xmlDocBuilder = WsaDocumentBuilder.getXMLDocBuilder();
            final SOAPContext soapContext = new SOAPContext();
            SOAPContext soapContext2 = new SOAPContext();
            boolean b = true;
            IAppLogger appLogger = ApacheSOAPEngine.this.m_log;
            boolean b2 = false;
            boolean b3 = false;
            WsaSOAPResponse null = WsaSOAPResponse.NULL;
            Object begin = null;
            int n = ApacheSOAPEngine.this.m_properties.faultLevel;
            ApacheSOAPEngine.this.m_statistics.incrementCounter(1);
            this.override();
            try {
                ApacheSOAPEngine.this.m_statistics.incrementCounter(11);
                try {
                    soapContext.setProperty(Constants.BAG_HTTPSERVLET, (Object)ApacheSOAPEngine.this);
                    soapContext.setProperty("psc.wsa.request.refid", (Object)this.requestID());
                    soapContext.setProperty("psc.wsa.uuid", (Object)ApacheSOAPEngine.this.getInstanceUUID());
                    Envelope envelopeFromInputStream;
                    try {
                        envelopeFromInputStream = ServerUtils.readEnvelopeFromInputStream(xmlDocBuilder, ApacheSOAPEngine.this.getEnvelopeInputStream(this), this.getLength(), this.getType(), ApacheSOAPEngine.this.m_editor, soapContext);
                    }
                    catch (IllegalArgumentException ex) {
                        final Object[] array = { ex.getMessage() };
                        if (ApacheSOAPEngine.this.m_log.ifLogVerbose(2L, 1)) {
                            ApacheSOAPEngine.this.m_log.logVerbose(1, 8607504787811871435L, array);
                        }
                        throw new WsaSOAPException("SOAP-ENV:Client.SOAP-Parameter", 8607504787811871394L, array, ex);
                    }
                    catch (Exception ex2) {
                        final Object[] array2 = { ex2.getMessage() };
                        if (ApacheSOAPEngine.this.m_log.ifLogVerbose(2L, 1)) {
                            ApacheSOAPEngine.this.m_log.logVerbose(1, 8607504787811871436L, array2);
                        }
                        throw new WsaSOAPException("SOAP-ENV:Client.SOAP-Envelope", 8607504787811871393L, array2, ex2);
                    }
                    if (envelopeFromInputStream == null) {
                        final Object[] array3 = { "Null Envelope" };
                        if (ApacheSOAPEngine.this.m_log.ifLogVerbose(2L, 1)) {
                            ApacheSOAPEngine.this.m_log.logVerbose(1, 8607504787811871436L, array3);
                        }
                        throw new WsaSOAPException("SOAP-ENV:Client.SOAP-Envelope", 8607504787811871393L, array3);
                    }
                    try {
                        callFromEnvelope = RPCRouter.extractCallFromEnvelope((ServiceManager)ApacheSOAPEngine.this.m_serviceMgr, envelopeFromInputStream, soapContext);
                    }
                    catch (WsaSOAPException ex3) {
                        throw ex3;
                    }
                    catch (Exception ex4) {
                        final Object[] array4 = { ex4.getMessage() };
                        if (ApacheSOAPEngine.this.m_log.ifLogVerbose(2L, 1)) {
                            ApacheSOAPEngine.this.m_log.logVerbose(1, 8607504787811871437L, array4);
                        }
                        throw new WsaSOAPException("SOAP-ENV:Client.SOAP-Body", 8607504787811871395L, array4, ex4);
                    }
                    final String targetObjectURI = ((RPCMessage)callFromEnvelope).getTargetObjectURI();
                    final String fullTargetObjectURI = ((RPCMessage)callFromEnvelope).getFullTargetObjectURI();
                    try {
                        pscDeploymentDescriptor = (PscDeploymentDescriptor)ApacheSOAPEngine.this.m_serviceMgr.query(targetObjectURI);
                        pscDeploymentDescriptor.runtimeStats().incrementCounter(0);
                        pscDeploymentDescriptor.runtimeStats().incrementCounter(2);
                        soapContext2.setProperty("psc.wsa.deployment_desc", (Object)pscDeploymentDescriptor);
                        if (!fullTargetObjectURI.equals("urn:services-progress-com:wsa-admin:0002")) {
                            n = pscDeploymentDescriptor.getProps().get("PROGRESS.Session.serviceFaultLevel");
                        }
                        else {
                            n = ApacheSOAPEngine.this.m_properties.faultLevel;
                        }
                    }
                    catch (Exception ex5) {
                        final Object[] array5 = { targetObjectURI, ex5.getMessage() };
                        if (ApacheSOAPEngine.this.m_log.ifLogVerbose(2L, 1)) {
                            ApacheSOAPEngine.this.m_log.logVerbose(1, 8607504787811871438L, array5);
                        }
                        throw new WsaSOAPException("SOAP-ENV:Client.SOAP-Body", 8607504787811871395L, array5, ex5);
                    }
                    try {
                        appLogger = pscDeploymentDescriptor.getLogSink();
                    }
                    catch (Exception ex16) {}
                    Object loadProvider;
                    try {
                        if (pscDeploymentDescriptor.getProviderType() == 3) {
                            loadProvider = ServerUtils.loadProvider((DeploymentDescriptor)pscDeploymentDescriptor, soapContext);
                        }
                        else {
                            if (pscDeploymentDescriptor.getProviderType() != 0) {
                                final Object[] array6 = new Object[0];
                                if (appLogger.ifLogBasic(2L, 1)) {
                                    appLogger.logBasic(1, 8607504787811871399L, array6);
                                }
                                throw new WsaSOAPException(Constants.FAULT_CODE_CLIENT, 8607504787811871399L, array6);
                            }
                            loadProvider = new RPCJavaProvider();
                        }
                    }
                    catch (Exception ex6) {
                        final Object[] array7 = { targetObjectURI, ex6.getMessage() };
                        final Object[] array8 = new Object[0];
                        if (appLogger.ifLogBasic(2L, 1)) {
                            appLogger.logBasic(1, 8607504787811871439L, array7);
                        }
                        throw new WsaSOAPException("SOAP-ENV:Server.Wsa", 8607504787811871398L, new Object[] { AppLogger.formatMessage(8607504787811871399L, array8) }, ex6);
                    }
                    if (fullTargetObjectURI.equals("urn:services-progress-com:wsa-admin:0002")) {
                        b2 = true;
                        if (!ApacheSOAPEngine.this.m_properties.adminEnabled) {
                            final Object[] array9 = { "Administration" };
                            if (appLogger.ifLogVerbose(2L, 1)) {
                                appLogger.logVerbose(1, 8607504787811871401L, array9);
                            }
                            throw new WsaSOAPException("SOAP-ENV:Client.Security", 8607504787811871401L, array9);
                        }
                        if (ApacheSOAPEngine.this.m_properties.adminAuth) {
                            final WsaUser wsaUser = (WsaUser)this.getUserObject();
                            WsaUser authenticateAdminUser;
                            try {
                                if (wsaUser == null) {
                                    throw new WsaSOAPException("SOAP-ENV:Client.Security", "Authentication failed. Check adminAuth setting.");
                                }
                                authenticateAdminUser = ApacheSOAPEngine.this.m_securityManager.authenticateAdminUser(wsaUser.getName(), this.getPassword(), wsaUser.getUserRoles());
                            }
                            catch (Exception ex7) {
                                ApacheSOAPEngine.this.m_statistics.incrementCounter(3);
                                b = false;
                                if (appLogger.ifLogVerbose(2L, 1)) {
                                    appLogger.logVerbose(1, ex7.getMessage());
                                }
                                throw new WsaSOAPException("SOAP-ENV:Client.Security", ex7.getMessage());
                            }
                            if (!ApacheSOAPEngine.this.m_securityManager.authorizeAdminUser(authenticateAdminUser, callFromEnvelope)) {
                                ApacheSOAPEngine.this.m_statistics.incrementCounter(3);
                                b = false;
                                final Object[] array10 = { "Administration" };
                                if (appLogger.ifLogVerbose(2L, 1)) {
                                    appLogger.logVerbose(1, 8607504787811871402L, array10);
                                }
                                throw new WsaSOAPException("SOAP-ENV:Client.Security", 8607504787811871402L, array10);
                            }
                            soapContext.setProperty("psc.wsa.user.credentials", (Object)authenticateAdminUser);
                        }
                    }
                    else {
                        if (!ApacheSOAPEngine.this.m_properties.webAppEnabled) {
                            final Object[] array11 = { targetObjectURI };
                            if (appLogger.ifLogVerbose(2L, 1)) {
                                appLogger.logVerbose(1, 8607504787811871401L, array11);
                            }
                            throw new WsaSOAPException("SOAP-ENV:Client.Security", 8607504787811871401L, array11);
                        }
                        if (ApacheSOAPEngine.this.m_properties.appAuth) {
                            WsaUser authenticateApplicationUser;
                            try {
                                authenticateApplicationUser = ApacheSOAPEngine.this.m_securityManager.authenticateApplicationUser(this.getUser(), this.getPassword());
                            }
                            catch (LoginException ex8) {
                                ApacheSOAPEngine.this.m_statistics.incrementCounter(3);
                                b = false;
                                if (appLogger.ifLogVerbose(2L, 1)) {
                                    appLogger.logVerbose(1, ex8.getMessage());
                                }
                                throw new WsaSOAPException("SOAP-ENV:Client.Security", ex8.getMessage());
                            }
                            if (!ApacheSOAPEngine.this.m_securityManager.authorizeApplicationUser(authenticateApplicationUser, fullTargetObjectURI)) {
                                ApacheSOAPEngine.this.m_statistics.incrementCounter(3);
                                b = false;
                                final Object[] array12 = { targetObjectURI };
                                if (appLogger.ifLogVerbose(2L, 1)) {
                                    appLogger.logVerbose(1, 8607504787811871402L, array12);
                                }
                                throw new WsaSOAPException("SOAP-ENV:Client.Security", 8607504787811871402L, array12);
                            }
                            soapContext.setProperty("psc.wsa.user.credentials", (Object)authenticateApplicationUser);
                        }
                    }
                    try {
                        ((Provider)loadProvider).locate((DeploymentDescriptor)pscDeploymentDescriptor, envelopeFromInputStream, callFromEnvelope, ((RPCMessage)callFromEnvelope).getMethodName(), fullTargetObjectURI, soapContext);
                    }
                    catch (WsaSOAPException ex9) {
                        throw ex9;
                    }
                    catch (Exception ex10) {
                        final Object[] array13 = { ex10.getMessage() };
                        if (appLogger.ifLogVerbose(2L, 1)) {
                            appLogger.logVerbose(1, 8607504787811871405L, array13);
                        }
                        throw new WsaSOAPException("SOAP-ENV:Server.Provider", 8607504787811871405L, array13, ex10);
                    }
                    if (ApacheSOAPEngine.this.m_properties.actionalEnabled && !b2) {
                        if (appLogger.ifLogVerbose(32768L, 15)) {
                            appLogger.logVerbose(15, "Setting up Actional Interaction");
                        }
                        begin = ServerInteraction.begin();
                        if (null != this.m_lgHeader) {
                            if (appLogger.ifLogVerbose(32768L, 15)) {
                                appLogger.logVerbose(15, "Received Actional Manifest in HTTP request: " + this.m_lgHeader);
                            }
                            InterHelpBase.readHeader(this.m_lgHeader, (ServerInteraction)begin);
                            this.m_lgHeader = null;
                        }
                        final String progressObjectName = pscDeploymentDescriptor.getProgressObjectName();
                        ((InteractionStub)begin).setGroupName(ApacheSOAPEngine.this.m_properties.actionalGroup);
                        ((SiteStub)begin).setAppType((short)681);
                        ((InteractionStub)begin).setServiceName(progressObjectName);
                        ((SiteStub)begin).setSvcType((short)684);
                        ((InteractionStub)begin).setOpName(((RPCMessage)callFromEnvelope).getMethodName());
                        ((InteractionStub)begin).setUrl("OpenEdge://" + this.getServerName() + "/WSA/" + ApacheSOAPEngine.this.m_properties.instanceName + "/" + progressObjectName);
                        ((InteractionStub)begin).setPeerAddr(this.getClientIPAddress());
                        ((Interaction)begin).requestAnalyzed();
                        this.logInteraction((Interaction)begin);
                    }
                    try {
                        ((Provider)loadProvider).invoke(soapContext, soapContext2);
                    }
                    catch (WsaSOAPException ex11) {
                        throw ex11;
                    }
                    catch (Exception ex12) {
                        final Object[] array14 = { ex12.getMessage() };
                        if (appLogger.ifLogVerbose(2L, 1)) {
                            appLogger.logVerbose(1, 8607504787811871406L, array14);
                        }
                        throw new WsaSOAPException("SOAP-ENV:Server.Provider", 8607504787811871406L, array14, ex12);
                    }
                    if (ApacheSOAPEngine.this.m_properties.actionalEnabled && begin != null) {
                        ((Interaction)begin).end();
                    }
                }
                catch (Throwable t) {
                    if (b) {
                        ApacheSOAPEngine.this.m_statistics.incrementCounter(8);
                    }
                    if (null != pscDeploymentDescriptor) {
                        pscDeploymentDescriptor.runtimeStats().incrementCounter(1);
                    }
                    WsaSOAPException ex13;
                    if (t instanceof WsaSOAPException) {
                        ex13 = (WsaSOAPException)t;
                    }
                    else if (t instanceof SOAPException) {
                        final WsaSOAPException ex14 = (WsaSOAPException)t;
                        if (null != ex14.getTargetException()) {
                            ex13 = new WsaSOAPException("SOAP-ENV:Server.SOAP", null, (Throwable)ex14);
                        }
                        else {
                            ex13 = new WsaSOAPException("SOAP-ENV:Server.SOAP", ex14.getMessage());
                        }
                    }
                    else {
                        ex13 = new WsaSOAPException("SOAP-ENV:Server.SOAP", 8607504787811871406L, new Object[] { t.getMessage() }, t);
                    }
                    if (appLogger.ifLogExtended(2L, 1)) {
                        ApacheSOAPEngine.this.dumpFullStackTrace(appLogger, this.requestID(), (Throwable)ex13);
                    }
                    final Fault faultStringFromFaultCode = new Fault((SOAPException)ex13);
                    b3 = true;
                    if (n < 3) {
                        final String faultCode = faultStringFromFaultCode.getFaultCode();
                        if (faultCode.startsWith("SOAP-ENV:Client")) {
                            faultStringFromFaultCode.setFaultCode("SOAP-ENV:Client");
                        }
                        if (faultCode.startsWith("SOAP-ENV:Server")) {
                            faultStringFromFaultCode.setFaultCode("SOAP-ENV:Server");
                        }
                    }
                    ApacheSOAPEngine.this.setFaultStringFromFaultCode(faultStringFromFaultCode);
                    faultStringFromFaultCode.setDetailEntries(ApacheSOAPEngine.this.buildFaultDetailFromException(n, (Throwable)ex13, this.requestID(), pscDeploymentDescriptor));
                    String encodingStyleURI = null;
                    if (callFromEnvelope != null) {
                        encodingStyleURI = ((RPCMessage)callFromEnvelope).getEncodingStyleURI();
                    }
                    if (encodingStyleURI == null) {
                        encodingStyleURI = "http://schemas.xmlsoap.org/soap/encoding/";
                    }
                    soapContext2 = new SOAPContext();
                    final Response response = new Response((String)null, (String)null, faultStringFromFaultCode, (Vector)null, (Header)null, encodingStyleURI, soapContext2);
                    final SOAPMappingRegistry soapMappingRegistry = (callFromEnvelope != null) ? callFromEnvelope.getSOAPMappingRegistry() : new SOAPMappingRegistry();
                    final Envelope buildEnvelope = response.buildEnvelope();
                    final StringWriter stringWriter = new StringWriter();
                    buildEnvelope.marshall((Writer)stringWriter, (XMLJavaMappingRegistry)soapMappingRegistry, ((RPCMessage)response).getSOAPContext());
                    ((RPCMessage)response).getSOAPContext().setRootPart(stringWriter.toString(), "text/xml;charset=utf-8");
                }
                final TransportMessage transportMessage = new TransportMessage((String)null, soapContext2, (Hashtable)null);
                transportMessage.editOutgoing(ApacheSOAPEngine.this.m_editor);
                transportMessage.save();
                ApacheSOAPEngine.this.dumpRawSOAP(transportMessage);
                null = new ApacheSOAPResponse(transportMessage, b3);
                ApacheSOAPEngine.this.m_statistics.decrementCounter(11);
                if (null != pscDeploymentDescriptor) {
                    pscDeploymentDescriptor.runtimeStats().decrementCounter(2);
                }
            }
            catch (Exception ex15) {
                ApacheSOAPEngine.this.m_statistics.decrementCounter(11);
                ApacheSOAPEngine.this.m_statistics.incrementCounter(8);
                if (null != pscDeploymentDescriptor && null != pscDeploymentDescriptor.runtimeStats()) {
                    pscDeploymentDescriptor.runtimeStats().decrementCounter(2);
                }
                ApacheSOAPEngine.this.m_log.logStackTrace(1, "Error building response envelope", ex15);
            }
            return null;
        }
        
        private void override() {
            try {
                final Hashtable runtimeProperties = ApacheSOAPEngine.this.m_serviceMgr.getRuntimeProperties("urn:services-progress-com:wsa-default:0001");
                if (null != runtimeProperties && runtimeProperties.containsKey("PROGRESS.Session.serviceFaultLevel")) {
                    runtimeProperties.get("PROGRESS.Session.serviceFaultLevel");
                }
            }
            catch (Exception ex) {}
        }
        
        public void finish() {
            this.m_clientIPAddress = null;
            this.m_webServiceURLPath = null;
            this.m_userObject = null;
            this.m_inputStream = null;
            this.m_options.clear();
        }
        
        public int getLength() {
            return this.m_length;
        }
        
        public String getType() {
            return this.m_type;
        }
        
        public String getUser() {
            return this.m_user;
        }
        
        public void setUser(final String user) {
            this.m_user = user;
        }
        
        public PasswordString getPassword() {
            return this.m_password;
        }
        
        public void setPassword(final PasswordString password) {
            this.m_password = password;
        }
        
        public String[] getRoleNames() {
            return ApacheSOAPEngine.this.m_roleNames;
        }
        
        public void setRoleNames(final String[] array) {
            ApacheSOAPEngine.this.m_roleNames = array;
        }
        
        public WsaSOAPResponse generateFault(final String faultCode, final String faultString, final Vector detailEntries) throws IOException, WsaSOAPEngineException {
            final Fault fault = new Fault();
            fault.setFaultCode(faultCode);
            fault.setFaultString(faultString);
            fault.setDetailEntries(detailEntries);
            final SOAPContext soapContext = new SOAPContext();
            final SOAPMappingRegistry soapMappingRegistry = new SOAPMappingRegistry();
            final Response response = new Response((String)null, (String)null, fault, (Vector)null, (Header)null, "http://schemas.xmlsoap.org/soap/encoding/", soapContext);
            final Envelope buildEnvelope = response.buildEnvelope();
            final StringWriter stringWriter = new StringWriter();
            buildEnvelope.marshall((Writer)stringWriter, (XMLJavaMappingRegistry)soapMappingRegistry, ((RPCMessage)response).getSOAPContext());
            TransportMessage transportMessage;
            try {
                ((RPCMessage)response).getSOAPContext().setRootPart(stringWriter.toString(), "text/xml;charset=utf-8");
                transportMessage = new TransportMessage((String)null, soapContext, (Hashtable)null);
                transportMessage.editOutgoing(ApacheSOAPEngine.this.m_editor);
                transportMessage.save();
                ApacheSOAPEngine.this.dumpRawSOAP(transportMessage);
            }
            catch (Exception ex) {
                throw new WsaSOAPEngineException("Unable to generate SOAP fault.", new Object[] { ex.getMessage() }, ex);
            }
            return new ApacheSOAPResponse(transportMessage, true);
        }
        
        public void setActionalManifest(final String lgHeader) {
            this.m_lgHeader = lgHeader;
        }
        
        private void logInteraction(final Interaction interaction) {
            if (interaction == null) {
                return;
            }
            String str;
            if (interaction instanceof ClientInteraction) {
                str = "CI";
            }
            else {
                str = "SI";
            }
            final StringBuffer sb = new StringBuffer();
            sb.append("OpenEdge WSA Interceptor ").append(str);
            if (ApacheSOAPEngine.this.m_log.ifLogVerbose(32768L, 15)) {
                sb.append("(").append(((InteractionStub)interaction).getInteractionID()).append(")");
            }
            sb.append(" on thread: ").append(Thread.currentThread().getName());
            sb.append(" ==>");
            sb.append(" PeerAddr: [ " + ((SiteStub)interaction).getPeerAddr());
            sb.append(" ] URL: [ " + ((SiteStub)interaction).getUrl());
            sb.append(" ] Type: [ " + ((SiteStub)interaction).getSvcType());
            if (ApacheSOAPEngine.this.m_log.ifLogVerbose(32768L, 15)) {
                sb.append(" ] Flow ID: [ " + ((FlowStub)interaction).getFlowID());
                sb.append(" ] Op ID: [ " + ((InteractionStub)interaction).getOpID());
                sb.append(" ] Locus ID: [ " + ((InteractionStub)interaction).getParentID());
                sb.append(" ] Chain ID: [ " + ((FlowStub)interaction).getChainID());
            }
            sb.append(" ] OneWay: [ " + ((InteractionStub)interaction).getOneWay());
            sb.append(" ] isFault: [ " + (((InteractionStub)interaction).getFailure() != null));
            sb.append(" ] G: " + ((SiteStub)interaction).getGroupName());
            sb.append(" S: " + ((SiteStub)interaction).getServiceName());
            sb.append(" O: " + ((SiteStub)interaction).getOpName());
            if (ApacheSOAPEngine.this.m_log.ifLogBasic(32768L, 15)) {
                ApacheSOAPEngine.this.m_log.logBasic(15, sb.toString());
            }
        }
    }
}
