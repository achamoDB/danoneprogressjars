// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa.admin;

import java.util.Map;
import java.io.OutputStream;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.File;
import org.apache.soap.server.DeploymentDescriptor;
import com.progress.wsa.WsaSOAPEngine;
import com.progress.wsa.tools.WsaStatusInfo;
import com.progress.wsa.open4gl.proxy.WSAProxyPool;
import com.progress.wsa.tools.StatusInfo;
import com.progress.wsa.tools.QueryInfo;
import com.progress.wsa.WsaSOAPException;
import com.progress.wsa.WsaProperties;
import com.progress.common.ehnlog.AppLogger;
import java.util.Enumeration;
import com.progress.wsa.WsaSOAPEngineContext;
import com.progress.wsa.tools.ListInfo;
import java.util.Hashtable;
import org.apache.soap.util.ConfigManager;
import org.apache.soap.server.BaseConfigManager;

public class PscConfigManager extends BaseConfigManager implements ConfigManager
{
    protected String m_filename;
    protected String m_instanceName;
    protected String m_instancePath;
    protected Hashtable m_friendlyApps;
    protected WsaParser m_parser;
    protected ListInfo[] m_serviceNamesCache;
    protected WsaSOAPEngineContext m_context;
    
    public PscConfigManager() {
        this.m_filename = "DeployedServices.ds";
        this.m_instanceName = null;
        this.m_instancePath = null;
        this.m_friendlyApps = new Hashtable();
        this.m_parser = null;
        this.m_serviceNamesCache = null;
        this.m_context = null;
    }
    
    public WsaParser getWsaParser() {
        return this.m_parser;
    }
    
    public WsaSOAPEngineContext getContext() {
        return this.m_context;
    }
    
    public void setContext(final WsaSOAPEngineContext context) {
        this.m_context = context;
    }
    
    public AppContainer queryApp(final String key) {
        return super.dds.get(key);
    }
    
    public AppContainer queryFriendlyApp(final String key) {
        return this.m_friendlyApps.get(key);
    }
    
    public Enumeration getApplications() {
        return super.dds.elements();
    }
    
    public ListInfo pscdeploy(String appObjectName, final WSAD wsad) throws WsaSOAPException {
        if (appObjectName == null) {
            appObjectName = wsad.getPGAppObj().getAppObjectName();
        }
        final AppLogger appLogger = (AppLogger)this.m_context.get("psc.wsa.log");
        if (appLogger.ifLogBasic(8L, 3)) {
            appLogger.logBasic(3, 8607504787811871293L, new Object[] { appObjectName });
        }
        final String wsdlAppURL = ((WsaProperties)this.m_context.get("psc.wsa.params")).wsdlAppURL;
        if (this.m_instancePath == null) {
            final StringBuffer sb = new StringBuffer();
            sb.append("/");
            sb.append(this.m_instanceName);
            sb.append("/");
            this.m_instancePath = sb.toString();
        }
        final AppContainer appContainer = super.dds.get("urn:services-progress-com:wsa-default:0001");
        if (appContainer == null) {
            appLogger.logError(8607504787811871294L, null);
            throw new WsaSOAPException("SOAP-ENV:Server.Admin", 8607504787811871294L, null);
        }
        final AppContainer application = AppContainer.createApplication(wsad, appContainer.getRuntimeProperties(), this.m_parser);
        application.setWSAUrl(wsdlAppURL);
        application.setFriendlyName(appObjectName);
        application.setContext(this.m_context);
        AppContainer appContainer2;
        if ((appContainer2 = super.dds.get(application.getId())) != null || (appContainer2 = this.m_friendlyApps.get(application.getFriendlyName())) != null) {
            appLogger.logError(8607504787811871295L, new Object[] { appContainer2.getFriendlyName(), appContainer2.getId() });
            throw new WsaSOAPException("SOAP-ENV:Server.Admin", 8607504787811871295L, new Object[] { appContainer2.getFriendlyName(), appContainer2.getId() });
        }
        this.deployApplication(appLogger, application);
        final ListInfo listInfo = new ListInfo();
        listInfo.setFriendlyName(application.getFriendlyName());
        listInfo.setTargetURI(application.getId());
        listInfo.setStatus(application.getAppStatus());
        return listInfo;
    }
    
    public QueryInfo pscquery(final String s) throws WsaSOAPException {
        final AppLogger appLogger = (AppLogger)this.m_context.get("psc.wsa.log");
        if (appLogger.ifLogVerbose(8L, 3)) {
            appLogger.logVerbose(3, 8607504787811871340L, new Object[] { s });
        }
        final AppContainer app = this.getApp(s);
        if (app == null) {
            appLogger.logError(8607504787811871338L, new Object[] { s });
            throw new WsaSOAPException("SOAP-ENV:Server.Admin", 8607504787811871338L, new Object[] { s });
        }
        final AppRuntimeProps runtimeProperties = app.getRuntimeProperties();
        final QueryInfo queryInfo = new QueryInfo();
        final ListInfo listInfo = new ListInfo();
        listInfo.setFriendlyName(app.getFriendlyName());
        listInfo.setTargetURI(app.getId());
        listInfo.setStatus(app.getAppStatus());
        queryInfo.setListInfo(listInfo);
        queryInfo.setAppServerInfo(app.getConnectionURL());
        if ((int)runtimeProperties.getProperty("PROGRESS.Session.sessionModel") == 0) {
            queryInfo.setSessionFree(false);
        }
        else {
            queryInfo.setSessionFree(true);
        }
        switch (app.getCurrentEncodingInt()) {
            case 1: {
                queryInfo.setStyle(1);
                queryInfo.setEncoding(1);
                break;
            }
            case 2: {
                queryInfo.setStyle(1);
                queryInfo.setEncoding(2);
                break;
            }
            case 3: {
                queryInfo.setStyle(2);
                queryInfo.setEncoding(2);
                break;
            }
        }
        try {
            queryInfo.setAppProps(runtimeProperties.getAppProperties(true));
        }
        catch (Exception ex) {
            appLogger.logError(8607504787811871339L, new Object[] { s, ex.getMessage() });
            throw new WsaSOAPException("SOAP-ENV:Server.Admin", 8607504787811871339L, new Object[] { s, ex.getMessage() }, ex);
        }
        return queryInfo;
    }
    
    public AppContainer pscundeploy(final String s) throws WsaSOAPException {
        final AppLogger appLogger = (AppLogger)this.m_context.get("psc.wsa.log");
        if (appLogger.ifLogBasic(8L, 3)) {
            appLogger.logBasic(3, 8607504787811871341L, new Object[] { s });
        }
        final AppContainer app = this.getApp(s);
        if (app == null) {
            appLogger.logError(8607504787811871338L, new Object[] { s });
            throw new WsaSOAPException("SOAP-ENV:Server.Admin", 8607504787811871338L, new Object[] { s });
        }
        super.dds.remove(app.getId());
        this.m_friendlyApps.remove(app.getFriendlyName());
        this.saveRegistry();
        this.m_serviceNamesCache = null;
        if (app != null) {
            this.cleanupFiles(app);
            if (appLogger.ifLogBasic(8L, 3)) {
                appLogger.logBasic(3, 8607504787811871342L, null);
            }
        }
        return app;
    }
    
    public ListInfo[] psclist() throws WsaSOAPException {
        int n = 0;
        final AppLogger appLogger = (AppLogger)this.m_context.get("psc.wsa.log");
        if (appLogger.ifLogVerbose(8L, 3)) {
            appLogger.logVerbose(3, 8607504787811871343L, null);
        }
        if (this.m_serviceNamesCache != null) {
            return this.m_serviceNamesCache;
        }
        final Enumeration<AppContainer> elements = super.dds.elements();
        final int n2 = super.dds.size() - 1;
        if (n2 <= 0) {
            return this.m_serviceNamesCache;
        }
        this.m_serviceNamesCache = new ListInfo[n2];
        for (int i = 0; i <= n2; ++i) {
            final AppContainer appContainer = elements.nextElement();
            String id = appContainer.getId();
            if (id == null) {
                id = "UNKNOWN";
            }
            final String friendlyName = appContainer.getFriendlyName();
            if (!id.equals("urn:services-progress-com:wsa-default:0001")) {
                final ListInfo listInfo = new ListInfo();
                listInfo.setFriendlyName(friendlyName);
                listInfo.setTargetURI(id);
                listInfo.setStatus(appContainer.getAppStatus());
                this.m_serviceNamesCache[n++] = listInfo;
            }
        }
        return this.m_serviceNamesCache;
    }
    
    public StatusInfo appstatus(final String s) throws WsaSOAPException {
        final AppLogger appLogger = (AppLogger)this.m_context.get("psc.wsa.log");
        if (appLogger.ifLogVerbose(8L, 3)) {
            appLogger.logVerbose(3, 8607504787811871344L, new Object[] { s });
        }
        final AppContainer app = this.getApp(s);
        if (app == null) {
            appLogger.logError(8607504787811871338L, new Object[] { s });
            throw new WsaSOAPException("SOAP-ENV:Server.Admin", 8607504787811871338L, new Object[] { s });
        }
        final StatusInfo statusInfo = new StatusInfo(app.getRuntimeStats());
        final WSAProxyPool poolManager = app.getPoolManager();
        if (null != poolManager) {
            statusInfo.setAppserverConnections(poolManager.appserverConnectionCount());
            statusInfo.setAppObjects(poolManager.appObjectCount());
            statusInfo.setSubappObjects(poolManager.subappObjectCount());
            statusInfo.setProcObjects(poolManager.procObjectCount());
        }
        return statusInfo;
    }
    
    public void resetappstatus(final String s) throws WsaSOAPException {
        final AppLogger appLogger = (AppLogger)this.m_context.get("psc.wsa.log");
        if (appLogger.ifLogBasic(8L, 3)) {
            appLogger.logBasic(3, 8607504787811871345L, new Object[] { s });
        }
        final AppContainer app = this.getApp(s);
        if (app == null) {
            appLogger.logError(8607504787811871338L, new Object[] { s });
            throw new WsaSOAPException("SOAP-ENV:Server.Admin", 8607504787811871338L, new Object[] { s });
        }
        app.getRuntimeStats().reset();
    }
    
    public WsaStatusInfo wsastatus() throws WsaSOAPException {
        final AppLogger appLogger = (AppLogger)this.m_context.get("psc.wsa.log");
        if (appLogger.ifLogVerbose(8L, 3)) {
            appLogger.logVerbose(3, 8607504787811871346L, null);
        }
        return new WsaStatusInfo(((WsaSOAPEngine)this.m_context.get("psc.wsa.engine")).getStatistics());
    }
    
    public void resetwsastatus() throws WsaSOAPException {
        final AppLogger appLogger = (AppLogger)this.m_context.get("psc.wsa.log");
        if (appLogger.ifLogBasic(8L, 3)) {
            appLogger.logBasic(3, 8607504787811871347L, null);
        }
        ((WsaSOAPEngine)this.m_context.get("psc.wsa.engine")).getStatistics().reset();
    }
    
    public AppContainer exportApp(final String s) throws WsaSOAPException {
        final AppLogger appLogger = (AppLogger)this.m_context.get("psc.wsa.log");
        if (appLogger.ifLogBasic(8L, 3)) {
            appLogger.logBasic(3, 8607504787811871348L, new Object[] { s });
        }
        final AppContainer app = this.getApp(s);
        if (app == null) {
            appLogger.logError(8607504787811871338L, new Object[] { s });
            throw new WsaSOAPException("SOAP-ENV:Server.Admin", 8607504787811871338L, new Object[] { s });
        }
        return app;
    }
    
    public ListInfo importApp(final AppContainer appContainer) throws WsaSOAPException {
        final String friendlyName = appContainer.getFriendlyName();
        final String id = appContainer.getId();
        final AppLogger appLogger = (AppLogger)this.m_context.get("psc.wsa.log");
        if (appLogger.ifLogBasic(8L, 3)) {
            appLogger.logBasic(3, 8607504787811871351L, new Object[] { friendlyName });
        }
        if (this.getApp(friendlyName) != null) {
            appLogger.logError(8607504787811871349L, new Object[] { friendlyName });
            throw new WsaSOAPException("SOAP-ENV:Server.Admin", 8607504787811871349L, new Object[] { friendlyName });
        }
        if (this.getApp(id) != null) {
            appLogger.logError(8607504787811871350L, new Object[] { id });
            throw new WsaSOAPException("SOAP-ENV:Server.Admin", 8607504787811871350L, new Object[] { id });
        }
        final AppContainer application = AppContainer.createApplication(appContainer.getWSAD(), appContainer.getRuntimeProperties(), this.m_parser);
        application.setWSAUrl(((WsaProperties)this.m_context.get("psc.wsa.params")).wsdlAppURL);
        application.setFriendlyName(friendlyName);
        application.setContext(this.m_context);
        this.deployApplication(appLogger, application);
        final ListInfo listInfo = new ListInfo();
        listInfo.setFriendlyName(application.getFriendlyName());
        listInfo.setTargetURI(application.getId());
        listInfo.setStatus(application.getAppStatus());
        return listInfo;
    }
    
    public ListInfo update(String appObjectName, final WSAD wsad) throws WsaSOAPException {
        if (appObjectName == null) {
            appObjectName = wsad.getPGAppObj().getAppObjectName();
        }
        final AppLogger appLogger = (AppLogger)this.m_context.get("psc.wsa.log");
        if (appLogger.ifLogBasic(8L, 3)) {
            appLogger.logBasic(3, 8607504787811871352L, new Object[] { appObjectName });
        }
        final String wsdlAppURL = ((WsaProperties)this.m_context.get("psc.wsa.params")).wsdlAppURL;
        final AppContainer app = this.getApp(appObjectName);
        if (app == null) {
            appLogger.logError(8607504787811871338L, new Object[] { appObjectName });
            throw new WsaSOAPException("SOAP-ENV:Server.Admin", 8607504787811871338L, new Object[] { appObjectName });
        }
        final AppRuntimeProps runtimeProperties = app.getRuntimeProperties();
        if (runtimeProperties.isAvailable()) {
            appLogger.logError(8607504787811871353L, new Object[] { appObjectName });
            throw new WsaSOAPException("SOAP-ENV:Server.Admin", 8607504787811871353L, new Object[] { appObjectName });
        }
        final String id = app.getId();
        final AppContainer application = AppContainer.createApplication(wsad, runtimeProperties, this.m_parser);
        application.setFriendlyName(appObjectName);
        application.setWSAUrl(wsdlAppURL);
        application.setContext(this.m_context);
        super.dds.remove(id);
        this.cleanupFiles(app);
        this.deployApplication(appLogger, application);
        final ListInfo listInfo = new ListInfo();
        listInfo.setFriendlyName(application.getFriendlyName());
        listInfo.setTargetURI(application.getId());
        listInfo.setStatus(application.getAppStatus());
        return listInfo;
    }
    
    public boolean enableApp(final String s) throws WsaSOAPException {
        final AppLogger appLogger = (AppLogger)this.m_context.get("psc.wsa.log");
        if (appLogger.ifLogBasic(8L, 3)) {
            appLogger.logBasic(3, 8607504787811871354L, new Object[] { s });
        }
        final AppContainer app = this.getApp(s);
        if (app == null) {
            appLogger.logError(8607504787811871338L, new Object[] { s });
            throw new WsaSOAPException("SOAP-ENV:Server.Admin", 8607504787811871338L, new Object[] { s });
        }
        app.enableApp();
        if (app.saveChanges()) {
            this.saveRegistry();
        }
        this.m_serviceNamesCache = null;
        return true;
    }
    
    public boolean disableApp(final String s) throws WsaSOAPException {
        final AppLogger appLogger = (AppLogger)this.m_context.get("psc.wsa.log");
        if (appLogger.ifLogBasic(8L, 3)) {
            appLogger.logBasic(3, 8607504787811871355L, new Object[] { s });
        }
        final AppContainer app = this.getApp(s);
        if (app == null) {
            appLogger.logError(8607504787811871338L, new Object[] { s });
            throw new WsaSOAPException("SOAP-ENV:Server.Admin", 8607504787811871338L, new Object[] { s });
        }
        app.disableApp();
        if (app.saveChanges()) {
            this.saveRegistry();
        }
        this.m_serviceNamesCache = null;
        return false;
    }
    
    public void resetRuntimeProperties(final String s) throws WsaSOAPException {
        final AppLogger appLogger = (AppLogger)this.m_context.get("psc.wsa.log");
        if (s.equals("urn:services-progress-com:wsa-default:0001")) {
            if (appLogger.ifLogBasic(8L, 3)) {
                appLogger.logBasic(3, 8607504787811871357L, null);
            }
        }
        else if (appLogger.ifLogBasic(8L, 3)) {
            appLogger.logBasic(3, 8607504787811871356L, new Object[] { s });
        }
        final AppContainer app = this.getApp(s);
        if (app == null) {
            appLogger.logError(8607504787811871338L, new Object[] { s });
            throw new WsaSOAPException("SOAP-ENV:Server.Admin", 8607504787811871338L, new Object[] { s });
        }
        final AppRuntimeProps runtimeProperties = app.getRuntimeProperties();
        if (s.compareTo("urn:services-progress-com:wsa-default:0001") != 0) {
            final AppContainer appContainer = super.dds.get("urn:services-progress-com:wsa-default:0001");
            if (appContainer == null) {
                appLogger.logError(8607504787811871294L, null);
                throw new WsaSOAPException("SOAP-ENV:Server.Admin", 8607504787811871294L, null);
            }
            runtimeProperties.resetProperties(appContainer.getRuntimeProperties());
        }
        else {
            runtimeProperties.resetProperties(null);
        }
    }
    
    public DeploymentDescriptor query(final String s) throws WsaSOAPException {
        String key;
        if (s.toLowerCase().startsWith("urn:", 0)) {
            final int lastIndex = s.lastIndexOf(58);
            if (lastIndex == -1) {
                throw new WsaSOAPException("SOAP-ENV:Server.SOAP", 8607504787811871358L, new Object[] { s });
            }
            key = s.substring(0, lastIndex);
        }
        else {
            final int lastIndex2 = s.lastIndexOf(47);
            if (lastIndex2 == -1) {
                throw new WsaSOAPException("SOAP-ENV:Server.SOAP", 8607504787811871358L, new Object[] { s });
            }
            key = s.substring(0, lastIndex2);
        }
        final AppContainer appContainer = super.dds.get(key);
        if (appContainer == null) {
            throw new WsaSOAPException("SOAP-ENV:Server.Admin", 8607504787811871338L, new Object[] { s });
        }
        final PscDeploymentDescriptor query = appContainer.query(s);
        if (query == null) {
            throw new WsaSOAPException("SOAP-ENV:Server.Admin", 8607504787811871338L, new Object[] { s });
        }
        return query;
    }
    
    public void shutdown() {
        final Enumeration<AppContainer> elements = super.dds.elements();
        final int n = super.dds.size() - 1;
        if (n <= 0) {
            return;
        }
        for (int i = 0; i <= n; ++i) {
            final AppContainer appContainer = elements.nextElement();
            String id = appContainer.getId();
            if (id == null) {
                id = "UNKNOWN";
            }
            if (!id.equals("urn:services-progress-com:wsa-default:0001")) {
                appContainer.shutdown();
            }
        }
    }
    
    private void createRegistry(final String str, final AppLogger appLogger) throws WsaSOAPException {
        if (appLogger.ifLogVerbose(8L, 3)) {
            appLogger.logVerbose(0, 8607504787811871359L, new Object[] { this.m_instanceName });
        }
        boolean b;
        try {
            final File file = new File(this.m_context.getRealPath(str));
            if (!(b = file.exists())) {
                if (appLogger.ifLogVerbose(1L, 0)) {
                    appLogger.logVerbose(0, 8607504787811871360L, new Object[] { file.getAbsolutePath() });
                }
                b = file.mkdir();
            }
        }
        catch (Exception ex) {
            final Object[] array = { ex.getMessage() };
            appLogger.logError(8607504787811871361L, array);
            throw new WsaSOAPException("SOAP-ENV:Server.Wsa", 8607504787811871361L, array, ex);
        }
        if (!b) {
            final Object[] array2 = { this.m_context.getRealPath(str.toString()) };
            appLogger.logError(8607504787811871361L, array2);
            throw new WsaSOAPException("SOAP-ENV:Server.Wsa", 8607504787811871361L, array2);
        }
        final StringBuffer sb = new StringBuffer(str);
        sb.append("default.props");
        final String realPath = this.m_context.getRealPath(sb.toString());
        super.dds = new Hashtable();
        final AppContainer default1 = AppContainer.createDefault(this.m_parser, (String)this.m_context.get("psc.wsa.install.dir") + "/java");
        default1.initRuntime(this.m_context, this.m_parser);
        super.dds.put("urn:services-progress-com:wsa-default:0001", default1);
        try {
            default1.saveRuntimeProps(realPath);
            default1.getRuntimeProperties().setIsDefaultProps(true);
            this.saveRegistry();
        }
        catch (WsaSOAPException ex2) {
            super.dds = null;
            throw ex2;
        }
    }
    
    public void loadRegistry() throws WsaSOAPException {
        final AppLogger appLogger = (AppLogger)this.m_context.get("psc.wsa.log");
        if (appLogger.ifLogVerbose(1L, 0)) {
            appLogger.logVerbose(0, 8607504787811871362L, null);
        }
        this.m_instanceName = (String)this.m_context.get("psc.wsa.instance.name");
        this.m_parser = new WsaParser(this.m_context);
        if (this.m_instancePath == null) {
            final StringBuffer sb = new StringBuffer(256);
            sb.append("/");
            sb.append(this.m_instanceName);
            sb.append("/");
            this.m_instancePath = sb.toString();
        }
        super.dds = null;
        this.m_friendlyApps = null;
        this.m_friendlyApps = new Hashtable();
        ObjectInputStream objectInputStream;
        try {
            final StringBuffer sb2 = new StringBuffer(this.m_instancePath);
            sb2.append(this.m_filename);
            objectInputStream = new ObjectInputStream(new FileInputStream(this.m_context.getRealPath(sb2.toString())));
        }
        catch (Exception ex4) {
            this.createRegistry(this.m_instancePath, appLogger);
            return;
        }
        try {
            super.dds = (Hashtable)objectInputStream.readObject();
            objectInputStream.close();
            final Enumeration<AppContainer> elements = super.dds.elements();
            while (elements.hasMoreElements()) {
                final AppContainer value = elements.nextElement();
                value.setRuntimeStats(null);
                try {
                    value.initRuntime(this.m_context, this.m_parser);
                    this.m_friendlyApps.put(value.getFriendlyName(), value);
                    if (!value.getFriendlyName().equals("default")) {
                        continue;
                    }
                    value.getRuntimeProperties().setIsDefaultProps(true);
                }
                catch (WsaSOAPException ex) {
                    appLogger.logError(8607504787811871337L, new Object[] { ex.getMessage() });
                    throw ex;
                }
                catch (Exception ex2) {
                    ex2.printStackTrace();
                    final Object[] array = { ex2.getMessage() };
                    appLogger.logError(8607504787811871337L, array);
                    throw new WsaSOAPException("SOAP-ENV:Server.Wsa", 8607504787811871337L, array, ex2);
                }
            }
        }
        catch (Exception ex3) {
            final Object[] array2 = { ex3.getMessage() };
            appLogger.logError(8607504787811871363L, array2);
            throw new WsaSOAPException("SOAP-ENV:Server.Wsa", 8607504787811871363L, array2, ex3);
        }
    }
    
    public void saveRegistry() throws WsaSOAPException {
        final StringBuffer sb = new StringBuffer(256);
        sb.append("/");
        sb.append(this.m_instanceName);
        sb.append("/");
        sb.append(this.m_filename);
        final AppLogger appLogger = (AppLogger)this.m_context.get("psc.wsa.log");
        try {
            if (appLogger.ifLogVerbose(1L, 0)) {
                appLogger.logVerbose(0, "Saving deployed application registry");
            }
            final ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(this.m_context.getRealPath(sb.toString())));
            objectOutputStream.writeObject(super.dds);
            objectOutputStream.close();
        }
        catch (Exception ex) {
            appLogger.logError("Error saving deployed application registry: %s", new Object[] { ex.getMessage() });
            throw new WsaSOAPException("SOAP-ENV:Server.SOAP", "Error saving deployed application registry", ex);
        }
    }
    
    public void setOptions(final Hashtable hashtable) {
        if (hashtable == null) {
            return;
        }
        final String s = hashtable.get("filename");
        if (s != null && !"".equals(s)) {
            this.m_filename = s;
        }
    }
    
    public String getWsdlDocPath(final String s) throws WsaSOAPException {
        final AppContainer app = this.getApp(s);
        if (app == null) {
            throw new WsaSOAPException("SOAP-ENV:Server.SOAP", "Web Service not found");
        }
        if (this.m_instancePath == null) {
            final StringBuffer sb = new StringBuffer(256);
            sb.append("/");
            sb.append(this.m_instanceName);
            sb.append("/");
            this.m_instancePath = sb.toString();
        }
        final StringBuffer sb2 = new StringBuffer(this.m_instancePath);
        sb2.append(app.getWSDLFileName());
        return this.m_context.getRealPath(sb2.toString());
    }
    
    public void resetStatistics(final String anObject) throws WsaSOAPException {
        final AppLogger appLogger = (AppLogger)this.m_context.get("psc.wsa.log");
        if (null != anObject && 0 < anObject.length()) {
            if ("urn:services-progress-com:wsa-admin:0002".equals(anObject)) {
                ((WsaSOAPEngine)this.m_context.get("psc.wsa.engine")).getStatistics().reset();
                appLogger.logError("Statistic counters reset for %s", new Object[] { "WSA" });
            }
            else {
                final AppContainer queryApp = this.queryApp(anObject);
                if (null == queryApp) {
                    throw new WsaSOAPException("SOAP-ENV:Server.Admin", 8607504787811871404L, new Object[] { anObject });
                }
                queryApp.getRuntimeStats().reset();
                appLogger.logError("Statistic counters reset for %s", new Object[] { anObject });
            }
            return;
        }
        throw new WsaSOAPException("SOAP-ENV:Server.Admin", "Invalid arguments to %s", new Object[] { "resetStatistics" });
    }
    
    public Hashtable getRuntimeStatistics(final String anObject) throws WsaSOAPException {
        final AppLogger appLogger = (AppLogger)this.m_context.get("psc.wsa.log");
        if (null != anObject && 0 < anObject.length()) {
            Hashtable hashtable;
            if ("urn:services-progress-com:wsa-admin:0002".equals(anObject)) {
                hashtable = ((WsaSOAPEngine)this.m_context.get("psc.wsa.engine")).getStatistics().getStatistics();
            }
            else {
                final AppContainer queryApp = this.queryApp(anObject);
                if (null == queryApp) {
                    throw new WsaSOAPException("SOAP-ENV:Server.Admin", 8607504787811871404L, new Object[] { anObject });
                }
                hashtable = queryApp.getRuntimeStats().getStatistics(queryApp.getPoolManager());
            }
            return hashtable;
        }
        throw new WsaSOAPException("SOAP-ENV:Server.Admin", 8607504787811871406L, new Object[] { "getRuntimeStatistics" }, new NullPointerException());
    }
    
    public Hashtable getRuntimeProperties(final String key) throws WsaSOAPException {
        final AppLogger appLogger = (AppLogger)this.m_context.get("psc.wsa.log");
        if (null != key && 0 < key.length()) {
            Hashtable hashtable;
            if ("urn:services-progress-com:wsa-admin:0002".equals(key)) {
                final WsaProperties wsaProperties = (WsaProperties)this.m_context.get("psc.wsa.params");
                try {
                    hashtable = wsaProperties.getRuntimeProperties();
                }
                catch (Exception ex) {
                    final Object[] array = { key, ex.getMessage() };
                    final String s = "Error in getting runtime properties for %s: %s";
                    appLogger.logError(s, array);
                    throw new WsaSOAPException("SOAP-ENV:Server.Admin", s, array, ex);
                }
            }
            else {
                final String lowerCase = key.toLowerCase();
                AppContainer appContainer;
                if (lowerCase.startsWith("urn:") || lowerCase.startsWith("http:")) {
                    appContainer = super.dds.get(key);
                }
                else {
                    appContainer = this.m_friendlyApps.get(key);
                }
                if (null == appContainer) {
                    throw new WsaSOAPException("SOAP-ENV:Server.Admin", 8607504787811871404L, new Object[] { key });
                }
                try {
                    hashtable = appContainer.getRuntimeProperties().getRuntimeProperties();
                }
                catch (Exception ex2) {
                    throw new WsaSOAPException("SOAP-ENV:Server.Admin", 8607504787811871406L, new Object[] { "getRuntimeProperties" }, ex2);
                }
            }
            return hashtable;
        }
        throw new WsaSOAPException("SOAP-ENV:Server.Admin", 8607504787811871406L, new Object[] { "getRuntimeProperties" }, new NullPointerException());
    }
    
    public void setRuntimeProperties(final String key, final Hashtable hashtable) throws WsaSOAPException {
        final AppLogger appLogger = (AppLogger)this.m_context.get("psc.wsa.log");
        if (null != key && 0 < key.length()) {
            if ("urn:services-progress-com:wsa-admin:0002".equals(key)) {
                final WsaProperties wsaProperties = (WsaProperties)this.m_context.get("psc.wsa.params");
                try {
                    wsaProperties.setRuntimeProperties(hashtable);
                    appLogger.logError("Runtime properties set for %s", new Object[] { "WSA" });
                }
                catch (Exception ex) {
                    final Object[] array = { key, ex.getMessage() };
                    final String s = "Error in setting runtime properties for %s: %s";
                    appLogger.logError(s, array);
                    throw new WsaSOAPException("SOAP-ENV:Server.Admin", s, array, ex);
                }
            }
            else {
                final String lowerCase = key.toLowerCase();
                AppContainer appContainer;
                if (lowerCase.startsWith("urn:") || lowerCase.startsWith("http:")) {
                    appContainer = super.dds.get(key);
                }
                else {
                    appContainer = this.m_friendlyApps.get(key);
                }
                if (null == appContainer) {
                    throw new WsaSOAPException("SOAP-ENV:Server.Admin", 8607504787811871404L, new Object[] { key });
                }
                try {
                    appContainer.setRuntimeProperties(hashtable);
                    this.m_serviceNamesCache = null;
                    appLogger.logError("Runtime properties set for %s", new Object[] { key });
                }
                catch (Exception ex2) {
                    throw new WsaSOAPException("SOAP-ENV:Server.Admin", 8607504787811871406L, new Object[] { "setRuntimeProperties" }, ex2);
                }
            }
            return;
        }
        throw new WsaSOAPException("SOAP-ENV:Server.Admin", 8607504787811871406L, new Object[] { "setRuntimeProperties" }, new NullPointerException());
    }
    
    protected AppContainer getApp(final String s) {
        final String lowerCase = s.toLowerCase();
        AppContainer appContainer;
        if (lowerCase.startsWith("urn:") || lowerCase.startsWith("http:")) {
            appContainer = super.dds.get(s);
        }
        else {
            appContainer = this.m_friendlyApps.get(s);
        }
        return appContainer;
    }
    
    private void deployApplication(final AppLogger appLogger, final AppContainer appContainer) throws WsaSOAPException {
        final StringBuffer sb = new StringBuffer(this.m_instancePath);
        sb.append(appContainer.getWSADFileName());
        final String realPath = this.m_context.getRealPath(sb.toString());
        if (appLogger.ifLogVerbose(8L, 3)) {
            appLogger.logVerbose(3, 8607504787811871296L, new Object[] { realPath });
        }
        appContainer.createWSADFile(realPath, this.m_parser);
        final StringBuffer sb2 = new StringBuffer(this.m_instancePath);
        sb2.append(appContainer.getWSDLFileName());
        final String realPath2 = this.m_context.getRealPath(sb2.toString());
        if (appLogger.ifLogVerbose(8L, 3)) {
            appLogger.logVerbose(3, 8607504787811871297L, new Object[] { realPath2 });
        }
        appContainer.createWSDLFile(realPath2);
        final StringBuffer sb3 = new StringBuffer(this.m_instancePath);
        sb3.append(appContainer.getFriendlyName());
        sb3.append(".props");
        final String realPath3 = this.m_context.getRealPath(sb3.toString());
        if (appLogger.ifLogVerbose(8L, 3)) {
            appLogger.logVerbose(3, 8607504787811871298L, new Object[] { realPath3 });
        }
        appContainer.createPropFile(realPath3, this.m_parser);
        try {
            if (appLogger.ifLogVerbose(8L, 3)) {
                appLogger.logVerbose(3, "Initializing application");
            }
            appContainer.initRuntime(this.m_context, this.m_parser);
        }
        catch (Exception ex) {
            this.cleanupFiles(appContainer);
            appLogger.logError(8607504787811871337L, new Object[] { ex.getMessage() });
            throw new WsaSOAPException("SOAP-ENV:Server.Admin", "Unable to initialize application", new Object[] { ex.getMessage() }, ex);
        }
        super.dds.put(appContainer.getId(), appContainer);
        this.m_friendlyApps.put(appContainer.getFriendlyName(), appContainer);
        this.saveRegistry();
        this.m_serviceNamesCache = null;
    }
    
    private void cleanupFiles(final AppContainer appContainer) {
        new File(this.m_context.getRealPath(this.m_instancePath + appContainer.getWSADFileName())).delete();
        new File(this.m_context.getRealPath(this.m_instancePath + appContainer.getWSDLFileName())).delete();
        new File(this.m_context.getRealPath(this.m_instancePath + appContainer.getFriendlyName() + ".props")).delete();
        final File file = new File(this.m_context.getRealPath(this.m_instancePath + appContainer.getFriendlyName() + ".dd"));
        if (file.exists()) {
            file.delete();
        }
    }
}
