// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa.admin;

import java.io.PushbackReader;
import java.io.IOException;
import java.io.Writer;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import com.progress.common.util.crypto;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import com.progress.wsa.tools.WsaPluginLog;
import com.progress.ubroker.tools.UBToolsMsg;
import com.progress.wsa.tools.PropElement;
import java.util.Hashtable;
import com.progress.wsa.tools.WsaStatusInfo;
import com.progress.wsa.tools.StatusInfo;
import com.progress.wsa.tools.QueryInfo;
import org.apache.soap.rpc.Response;
import com.progress.ubroker.util.Logger;
import java.util.Vector;
import org.apache.soap.rpc.Parameter;
import com.progress.wsa.tools.AdminStatus;
import com.progress.wsa.tools.ListInfo;
import java.util.Enumeration;
import com.progress.common.util.InstallPath;
import java.util.Map;
import java.util.Properties;

public class WsaAdminPlugin
{
    public static final int STATUS_SUCCESS = 0;
    public static final int STATUS_UNKOWN = -1;
    public static final int STATUS_SOAP_FAULT = -2;
    public static final int STATUS_CONNECT_FAILURE = -3;
    public static final int STATUS_REQUEST_FAILURE = -4;
    public static final int STATUS_INIT_FAILURE = -5;
    public static final int STATUS_DISCONNECT_FAILURE = -6;
    public static final int STATUS_WSA_NOT_ADMIN_ENABLED = -7;
    public static final int STATUS_WEBSERVER_AUTH_FAILURE = -8;
    public static final int STATUS_PROXYSERVER_AUTH_FAILURE = -9;
    public static final int STATUS_SSL_CONNECT_FAILURE = -10;
    public static final String SOAP_FAULT_STR = " SOAP FAULT ";
    public static final String CONNECT_FAILURE_STR = " CONNECT FAILURE ";
    public static final String REQUEST_FAILURE_STR = " REQUEST FAILURE ";
    public static final String INIT_FAILURE_STR = " INIT FAILURE ";
    public static final String DISCONNECT_FAILURE_STR = " DISCONNECT FAILURE ";
    public static final String WSA_NOT_ADMIN_ENABLED_STR = " WSA ADMINISTRATION DISABLED ";
    public static final String PROXYSERVER_AUTH_FAILURE_STR = " PROXY SERVER AUTHENTICATION FAILURE ";
    public static final String WEBSERVER_AUTH_FAILURE_STR = " WEB SERVER AUTHENTICATION FAILURE ";
    public static final String SSL_CONNECT_FAILURE_STR = " SSL CONNECTION FAILURE ";
    public static final String INVALID_PROPERTY_STR = " Invalid property ";
    public static final String DEPLOY_REQ_STR = "deploy";
    public static final String UNDEPLOY_REQ_STR = "undeploy";
    public static final String QUERY_REQ_STR = "query";
    public static final String LIST_REQ_STR = "list";
    public static final String GETSTATS_REQ_STR = "get statistics";
    public static final String WSASTATS_REQ_STR = "get WSA statistics";
    public static final String RESETSTATS_REQ_STR = "reset stats";
    public static final String RESETWSASTATS_REQ_STR = "reset WSA statistics";
    public static final String ENABLE_REQ_STR = "enable";
    public static final String DISABLE_REQ_STR = "disable";
    public static final String SETPROPS_REQ_STR = "set properties";
    public static final String GETPROPS_REQ_STR = "get properties";
    public static final String RESETPROPS_REQ_STR = "reset properties";
    public static final String PINGWSA_REQ_STR = "pingwsa";
    public static final String IMPORT_REQ_STR = "import";
    public static final String EXPORT_REQ_STR = "export";
    public static final String UPDATE_REQ_STR = "update";
    public static final long INIT_FAILURE_MSGCODE = 8607504787811871249L;
    public static final long CONNECT_FAILURE_MSGCODE = 8607504787811871250L;
    public static final long DISCONNECT_FAILURE_MSGCODE = 8607504787811871251L;
    public static final long NOT_ADMIN_ENABLED_FAILURE_MSGCODE = 8607504787811871252L;
    public static final long REQUEST_FAILURE_MSGCODE = 8607504787811871253L;
    public static final long SOAP_FAULT_FAILURE_MSGCODE = 8607504787811871254L;
    public static final long INVALID_PROPERTY_MSGCODE = 8607504787811871323L;
    public static final long WEBSERVER_AUTH_FAILURE_MSGCODE = 8607504787811871291L;
    public static final long PROXYSERVER_AUTH_FAILURE_MSGCODE = 8607504787811871291L;
    protected String m_wsaURL;
    protected String m_username;
    protected String m_password;
    protected String m_installDir;
    protected String m_certsStore;
    protected WebServerLoginContext context;
    protected String m_certsjar;
    protected Properties m_protocolOptions;
    private static Map STANDARD_ENTITIES;
    
    public WsaAdminPlugin(final String wsaURL) {
        this.context = null;
        this.m_certsjar = null;
        this.m_protocolOptions = null;
        this.m_wsaURL = wsaURL;
        this.m_username = null;
        this.m_password = null;
        this.m_installDir = new InstallPath().getInstallPath();
        this.m_certsStore = System.getProperty("PscCertsStore");
        if (this.m_certsStore != null) {
            this.m_certsjar = this.m_certsStore;
        }
        else {
            this.m_certsjar = this.m_installDir + "/certs/psccerts.jar";
        }
        this.m_protocolOptions = new Properties();
    }
    
    public void setWsaUrl(final String wsaURL) {
        this.m_wsaURL = wsaURL;
    }
    
    public String getWsaUrl() {
        return this.m_wsaURL;
    }
    
    public void setInstallDir(final String installDir) {
        this.m_installDir = installDir;
    }
    
    public String getInstallDir() {
        return this.m_installDir;
    }
    
    public void setCertsjar(final String certsjar) {
        this.m_certsjar = certsjar;
    }
    
    public String getCertsjar() {
        return this.m_certsjar;
    }
    
    public void setProtocolOptions(final Properties properties) {
        if (null != properties) {
            final Enumeration<?> propertyNames = properties.propertyNames();
            while (propertyNames.hasMoreElements()) {
                final String s = (String)propertyNames.nextElement();
                final String property = properties.getProperty(s);
                if (null != property) {
                    ((Hashtable<String, String>)this.m_protocolOptions).put(s, property);
                }
            }
        }
    }
    
    public void setProtocolOptions(final String key, final String value) {
        ((Hashtable<String, String>)this.m_protocolOptions).put(key, value);
    }
    
    public Properties getProtocolOptions() {
        return this.m_protocolOptions;
    }
    
    public void setWebServerLogin(final String username, final String password) {
        this.m_username = username;
        this.m_password = password;
    }
    
    public String getWebServerUserName() {
        return this.m_username;
    }
    
    public String getWebServerPwd() {
        return this.m_password;
    }
    
    public void setConnectContext(final WebServerLoginContext context) {
        this.context = context;
        this.m_username = this.context.getWsUsername();
        this.m_password = this.context.getWsPwd();
    }
    
    public AdminStatus deploy(final WSAD wsad, final ListInfo listInfo) {
        return this.deploy(wsad, listInfo, null);
    }
    
    public AdminStatus deploy(final WSAD wsad, final ListInfo listInfo, final String s) {
        final Parameter obj = new Parameter("name", String.class, (Object)s, "http://schemas.xmlsoap.org/soap/encoding/");
        final Parameter obj2 = new Parameter("WSAD", (Class)wsad.getClass(), (Object)wsad, "http://schemas.xmlsoap.org/soap/encoding/");
        final Vector<Parameter> vector = new Vector<Parameter>();
        vector.addElement(obj);
        vector.addElement(obj2);
        final WsaAdminClient wsaAdminClient = new WsaAdminClient();
        final Properties protocolOptions = this.createProtocolOptions();
        try {
            wsaAdminClient.init(protocolOptions, null, 0, 0);
        }
        catch (Exception ex) {
            this.logError(8607504787811871249L, new Object[] { "deploy", ex.getMessage() });
            return new AdminStatus(-5, " INIT FAILURE ");
        }
        PingResponse pingResponse;
        try {
            final String connect = wsaAdminClient.connect(this.m_wsaURL, this.m_username, this.m_password);
            pingResponse = new PingResponse();
            pingResponse.convertMsg(connect);
        }
        catch (Exception ex2) {
            if (wsaAdminClient.isWebServerAuthFailure()) {
                this.logError(8607504787811871291L, new Object[] { this.m_wsaURL });
                return new AdminStatus(-8, " WEB SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isProxyAuthFailure()) {
                this.logError(8607504787811871291L, null);
                return new AdminStatus(-9, " PROXY SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isSSLConnectFailure()) {
                this.logError(8607504787811871250L, new Object[] { this.m_wsaURL, "query", ex2.getMessage() }, 3);
                return new AdminStatus(-10, " SSL CONNECTION FAILURE ");
            }
            this.logError(8607504787811871250L, new Object[] { this.m_wsaURL, "deploy", ex2.getMessage() }, 3);
            return new AdminStatus(-3, " CONNECT FAILURE ");
        }
        if (pingResponse.getAdminEnabled() == 0) {
            this.logError(8607504787811871252L, new Object[] { "deploy", this.m_wsaURL });
            return new AdminStatus(-7, " WSA ADMINISTRATION DISABLED ");
        }
        Response invokeAdminMethod;
        try {
            invokeAdminMethod = wsaAdminClient.invokeAdminMethod("pscdeploy", vector);
        }
        catch (Exception ex3) {
            if (wsaAdminClient.isWebServerAuthFailure()) {
                this.logError(8607504787811871291L, new Object[] { this.m_wsaURL });
                return new AdminStatus(-8, " WEB SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isProxyAuthFailure()) {
                this.logError(8607504787811871291L, null);
                return new AdminStatus(-9, " PROXY SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isSSLConnectFailure()) {
                this.logError(8607504787811871250L, new Object[] { this.m_wsaURL, "query", ex3.getMessage() }, 3);
                return new AdminStatus(-10, " SSL CONNECTION FAILURE ");
            }
            this.logError(8607504787811871253L, new Object[] { this.m_wsaURL, "deploy", ex3.getMessage() });
            return new AdminStatus(-4, " REQUEST FAILURE ");
        }
        try {
            wsaAdminClient.disconnect();
        }
        catch (Exception ex4) {
            this.logError(8607504787811871251L, new Object[] { this.m_wsaURL, "deploy", ex4.getMessage() });
            return new AdminStatus(-6, " DISCONNECT FAILURE ");
        }
        if (invokeAdminMethod.generatedFault()) {
            this.logError(8607504787811871254L, new Object[] { "deploy", this.m_wsaURL, this.logFaultStr(invokeAdminMethod) });
            return new AdminStatus(-2, this.faultErrorStr(invokeAdminMethod));
        }
        final ListInfo listInfo2 = (ListInfo)invokeAdminMethod.getReturnValue().getValue();
        listInfo.setFriendlyName(listInfo2.getFriendlyName());
        listInfo.setStatus(listInfo2.getStatus());
        listInfo.setTargetURI(listInfo2.getTargetURI());
        return new AdminStatus(0);
    }
    
    public AdminStatus undeploy(final String s) {
        final Parameter obj = new Parameter("name", String.class, (Object)s, "http://schemas.xmlsoap.org/soap/encoding/");
        final Vector<Parameter> vector = new Vector<Parameter>();
        vector.addElement(obj);
        final WsaAdminClient wsaAdminClient = new WsaAdminClient();
        final Properties protocolOptions = this.createProtocolOptions();
        try {
            wsaAdminClient.init(protocolOptions, null, 0, 0);
        }
        catch (Exception ex) {
            this.logError(8607504787811871249L, new Object[] { "undeploy", ex.getMessage() });
            return new AdminStatus(-5, " INIT FAILURE ");
        }
        PingResponse pingResponse;
        try {
            final String connect = wsaAdminClient.connect(this.m_wsaURL, this.m_username, this.m_password);
            pingResponse = new PingResponse();
            pingResponse.convertMsg(connect);
        }
        catch (Exception ex2) {
            if (wsaAdminClient.isWebServerAuthFailure()) {
                this.logError(8607504787811871291L, new Object[] { this.m_wsaURL });
                return new AdminStatus(-8, " WEB SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isProxyAuthFailure()) {
                this.logError(8607504787811871291L, null);
                return new AdminStatus(-9, " PROXY SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isSSLConnectFailure()) {
                this.logError(8607504787811871250L, new Object[] { this.m_wsaURL, "query", ex2.getMessage() }, 3);
                return new AdminStatus(-10, " SSL CONNECTION FAILURE ");
            }
            this.logError(8607504787811871250L, new Object[] { this.m_wsaURL, "undeploy", ex2.getMessage() }, 3);
            return new AdminStatus(-3, " CONNECT FAILURE ");
        }
        if (pingResponse.getAdminEnabled() == 0) {
            this.logError(8607504787811871252L, new Object[] { "undeploy", this.m_wsaURL });
            return new AdminStatus(-7, " WSA ADMINISTRATION DISABLED ");
        }
        Response invokeAdminMethod;
        try {
            invokeAdminMethod = wsaAdminClient.invokeAdminMethod("pscundeploy", vector);
        }
        catch (Exception ex3) {
            if (wsaAdminClient.isWebServerAuthFailure()) {
                this.logError(8607504787811871291L, new Object[] { this.m_wsaURL });
                return new AdminStatus(-8, " WEB SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isProxyAuthFailure()) {
                this.logError(8607504787811871291L, null);
                return new AdminStatus(-9, " PROXY SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isSSLConnectFailure()) {
                this.logError(8607504787811871250L, new Object[] { this.m_wsaURL, "query", ex3.getMessage() }, 3);
                return new AdminStatus(-10, " SSL CONNECTION FAILURE ");
            }
            this.logError(8607504787811871253L, new Object[] { this.m_wsaURL, "undeploy", ex3.getMessage() });
            return new AdminStatus(-4, " REQUEST FAILURE ");
        }
        try {
            wsaAdminClient.disconnect();
        }
        catch (Exception ex4) {
            this.logError(8607504787811871251L, new Object[] { this.m_wsaURL, "undeploy", ex4.getMessage() });
            return new AdminStatus(-6, " DISCONNECT FAILURE ");
        }
        if (invokeAdminMethod.generatedFault()) {
            this.logError(8607504787811871254L, new Object[] { "undeploy", this.m_wsaURL, invokeAdminMethod.getFault().getDetailEntries() });
            return new AdminStatus(-2, this.faultErrorStr(invokeAdminMethod));
        }
        return new AdminStatus(0);
    }
    
    public AdminStatus list(final Vector vector) {
        final WsaAdminClient wsaAdminClient = new WsaAdminClient();
        final Properties protocolOptions = this.createProtocolOptions();
        try {
            wsaAdminClient.init(protocolOptions, null, 0, 0);
        }
        catch (Exception ex) {
            this.logError(8607504787811871249L, new Object[] { "list", ex.getMessage() });
            return new AdminStatus(-5, " INIT FAILURE ");
        }
        PingResponse pingResponse;
        try {
            final String connect = wsaAdminClient.connect(this.m_wsaURL, this.m_username, this.m_password);
            pingResponse = new PingResponse();
            pingResponse.convertMsg(connect);
        }
        catch (Exception ex2) {
            if (wsaAdminClient.isWebServerAuthFailure()) {
                this.logError(8607504787811871291L, new Object[] { this.m_wsaURL });
                return new AdminStatus(-8, " WEB SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isProxyAuthFailure()) {
                this.logError(8607504787811871291L, null);
                return new AdminStatus(-9, " PROXY SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isSSLConnectFailure()) {
                this.logError(8607504787811871250L, new Object[] { this.m_wsaURL, "query", ex2.getMessage() }, 3);
                return new AdminStatus(-10, " SSL CONNECTION FAILURE ");
            }
            this.logError(8607504787811871250L, new Object[] { this.m_wsaURL, "list", ex2.getMessage() }, 3);
            return new AdminStatus(-3, " CONNECT FAILURE ");
        }
        if (pingResponse.getAdminEnabled() == 0) {
            this.logError(8607504787811871252L, new Object[] { "list", this.m_wsaURL });
            return new AdminStatus(-7, " WSA ADMINISTRATION DISABLED ");
        }
        Response invokeAdminMethod;
        try {
            invokeAdminMethod = wsaAdminClient.invokeAdminMethod("psclist", null);
        }
        catch (Exception ex3) {
            if (wsaAdminClient.isWebServerAuthFailure()) {
                this.logError(8607504787811871291L, new Object[] { this.m_wsaURL });
                return new AdminStatus(-8, " WEB SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isProxyAuthFailure()) {
                this.logError(8607504787811871291L, null);
                return new AdminStatus(-9, " PROXY SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isSSLConnectFailure()) {
                this.logError(8607504787811871250L, new Object[] { this.m_wsaURL, "query", ex3.getMessage() }, 3);
                return new AdminStatus(-10, " SSL CONNECTION FAILURE ");
            }
            this.logError(8607504787811871253L, new Object[] { this.m_wsaURL, "list", ex3.getMessage() });
            return new AdminStatus(-4, " REQUEST FAILURE ");
        }
        try {
            wsaAdminClient.disconnect();
        }
        catch (Exception ex4) {
            this.logError(8607504787811871251L, new Object[] { this.m_wsaURL, "list", ex4.getMessage() });
            return new AdminStatus(-6, " DISCONNECT FAILURE ");
        }
        if (!invokeAdminMethod.generatedFault()) {
            final ListInfo[] array = (ListInfo[])invokeAdminMethod.getReturnValue().getValue();
            if (array != null) {
                for (int i = 0; i < array.length; ++i) {
                    vector.add(array[i]);
                }
            }
            return new AdminStatus(0);
        }
        this.logError(8607504787811871254L, new Object[] { "list", this.m_wsaURL, this.logFaultStr(invokeAdminMethod) });
        return new AdminStatus(-2, this.faultErrorStr(invokeAdminMethod));
    }
    
    public AdminStatus query(final String s, final QueryInfo queryInfo) {
        final Parameter obj = new Parameter("name", String.class, (Object)s, "http://schemas.xmlsoap.org/soap/encoding/");
        final Vector<Parameter> vector = new Vector<Parameter>();
        vector.addElement(obj);
        final WsaAdminClient wsaAdminClient = new WsaAdminClient();
        final Properties protocolOptions = this.createProtocolOptions();
        try {
            wsaAdminClient.init(protocolOptions, null, 0, 0);
        }
        catch (Exception ex) {
            this.logError(8607504787811871249L, new Object[] { "query", ex.getMessage() });
            return new AdminStatus(-5, " INIT FAILURE ");
        }
        PingResponse pingResponse;
        try {
            final String connect = wsaAdminClient.connect(this.m_wsaURL, this.m_username, this.m_password);
            pingResponse = new PingResponse();
            pingResponse.convertMsg(connect);
        }
        catch (Exception ex2) {
            if (wsaAdminClient.isWebServerAuthFailure()) {
                this.logError(8607504787811871291L, new Object[] { this.m_wsaURL });
                return new AdminStatus(-8, " WEB SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isProxyAuthFailure()) {
                this.logError(8607504787811871291L, null);
                return new AdminStatus(-9, " PROXY SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isSSLConnectFailure()) {
                this.logError(8607504787811871250L, new Object[] { this.m_wsaURL, "query", ex2.getMessage() }, 3);
                return new AdminStatus(-10, " SSL CONNECTION FAILURE ");
            }
            this.logError(8607504787811871250L, new Object[] { this.m_wsaURL, "query", ex2.getMessage() }, 3);
            return new AdminStatus(-3, " CONNECT FAILURE ");
        }
        if (pingResponse.getAdminEnabled() == 0) {
            this.logError(8607504787811871252L, new Object[] { "query", this.m_wsaURL });
            return new AdminStatus(-7, " WSA ADMINISTRATION DISABLED ");
        }
        Response invokeAdminMethod;
        try {
            invokeAdminMethod = wsaAdminClient.invokeAdminMethod("pscquery", vector);
        }
        catch (Exception ex3) {
            if (wsaAdminClient.isWebServerAuthFailure()) {
                this.logError(8607504787811871291L, new Object[] { this.m_wsaURL });
                return new AdminStatus(-8, " WEB SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isProxyAuthFailure()) {
                this.logError(8607504787811871291L, null);
                return new AdminStatus(-9, " PROXY SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isSSLConnectFailure()) {
                this.logError(8607504787811871250L, new Object[] { this.m_wsaURL, "query", ex3.getMessage() }, 3);
                return new AdminStatus(-10, " SSL CONNECTION FAILURE ");
            }
            this.logError(8607504787811871253L, new Object[] { this.m_wsaURL, "query", ex3.getMessage() });
            return new AdminStatus(-4, " REQUEST FAILURE ");
        }
        try {
            wsaAdminClient.disconnect();
        }
        catch (Exception ex4) {
            this.logError(8607504787811871251L, new Object[] { this.m_wsaURL, "query", ex4.getMessage() });
            return new AdminStatus(-6, " DISCONNECT FAILURE ");
        }
        if (!invokeAdminMethod.generatedFault()) {
            final QueryInfo queryInfo2 = (QueryInfo)invokeAdminMethod.getReturnValue().getValue();
            queryInfo.setAppServerInfo(queryInfo2.getAppServerInfo());
            queryInfo.setEncoding(queryInfo2.getEncoding());
            queryInfo.setListInfo(queryInfo2.getListInfo());
            queryInfo.setStyle(queryInfo2.getStyle());
            queryInfo.setSessionFree(queryInfo2.isSessionFree());
            queryInfo.setAppProps(queryInfo2.getAppProps());
            return new AdminStatus(0);
        }
        this.logError(8607504787811871254L, new Object[] { "query", this.m_wsaURL, this.logFaultStr(invokeAdminMethod) });
        return new AdminStatus(-2, this.faultErrorStr(invokeAdminMethod));
    }
    
    public AdminStatus getStats(final String s, final StatusInfo statusInfo) {
        final Parameter obj = new Parameter("name", String.class, (Object)s, "http://schemas.xmlsoap.org/soap/encoding/");
        final Vector<Parameter> vector = new Vector<Parameter>();
        vector.addElement(obj);
        final WsaAdminClient wsaAdminClient = new WsaAdminClient();
        final Properties protocolOptions = this.createProtocolOptions();
        try {
            wsaAdminClient.init(protocolOptions, null, 0, 0);
        }
        catch (Exception ex) {
            this.logError(8607504787811871249L, new Object[] { "get statistics", ex.getMessage() });
            return new AdminStatus(-5, " INIT FAILURE ");
        }
        PingResponse pingResponse;
        try {
            final String connect = wsaAdminClient.connect(this.m_wsaURL, this.m_username, this.m_password);
            pingResponse = new PingResponse();
            pingResponse.convertMsg(connect);
        }
        catch (Exception ex2) {
            if (wsaAdminClient.isWebServerAuthFailure()) {
                this.logError(8607504787811871291L, new Object[] { this.m_wsaURL });
                return new AdminStatus(-8, " WEB SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isProxyAuthFailure()) {
                this.logError(8607504787811871291L, null);
                return new AdminStatus(-9, " PROXY SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isSSLConnectFailure()) {
                this.logError(8607504787811871250L, new Object[] { this.m_wsaURL, "query", ex2.getMessage() }, 3);
                return new AdminStatus(-10, " SSL CONNECTION FAILURE ");
            }
            this.logError(8607504787811871250L, new Object[] { this.m_wsaURL, "get statistics", ex2.getMessage() }, 3);
            return new AdminStatus(-3, " CONNECT FAILURE ");
        }
        if (pingResponse.getAdminEnabled() == 0) {
            this.logError(8607504787811871252L, new Object[] { "get statistics", this.m_wsaURL });
            return new AdminStatus(-7, " WSA ADMINISTRATION DISABLED ");
        }
        Response invokeAdminMethod;
        try {
            invokeAdminMethod = wsaAdminClient.invokeAdminMethod("appstatus", vector);
        }
        catch (Exception ex3) {
            if (wsaAdminClient.isWebServerAuthFailure()) {
                this.logError(8607504787811871291L, new Object[] { this.m_wsaURL });
                return new AdminStatus(-8, " WEB SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isProxyAuthFailure()) {
                this.logError(8607504787811871291L, null);
                return new AdminStatus(-9, " PROXY SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isSSLConnectFailure()) {
                this.logError(8607504787811871250L, new Object[] { this.m_wsaURL, "query", ex3.getMessage() }, 3);
                return new AdminStatus(-10, " SSL CONNECTION FAILURE ");
            }
            this.logError(8607504787811871253L, new Object[] { this.m_wsaURL, "get statistics", ex3.getMessage() });
            return new AdminStatus(-4, " REQUEST FAILURE ");
        }
        try {
            wsaAdminClient.disconnect();
        }
        catch (Exception ex4) {
            this.logError(8607504787811871251L, new Object[] { this.m_wsaURL, "get statistics", ex4.getMessage() });
            return new AdminStatus(-6, " DISCONNECT FAILURE ");
        }
        if (!invokeAdminMethod.generatedFault()) {
            statusInfo.copyContentFrom((StatusInfo)invokeAdminMethod.getReturnValue().getValue());
            return new AdminStatus(0);
        }
        this.logError(8607504787811871254L, new Object[] { "get statistics", this.m_wsaURL, this.logFaultStr(invokeAdminMethod) });
        return new AdminStatus(-2, this.faultErrorStr(invokeAdminMethod));
    }
    
    public AdminStatus wsaStats(final WsaStatusInfo wsaStatusInfo) {
        final WsaAdminClient wsaAdminClient = new WsaAdminClient();
        final Properties protocolOptions = this.createProtocolOptions();
        try {
            wsaAdminClient.init(protocolOptions, null, 0, 0);
        }
        catch (Exception ex) {
            this.logError(8607504787811871249L, new Object[] { "get WSA statistics", ex.getMessage() });
            return new AdminStatus(-5, " INIT FAILURE ");
        }
        PingResponse pingResponse;
        try {
            final String connect = wsaAdminClient.connect(this.m_wsaURL, this.m_username, this.m_password);
            pingResponse = new PingResponse();
            pingResponse.convertMsg(connect);
        }
        catch (Exception ex2) {
            if (wsaAdminClient.isWebServerAuthFailure()) {
                this.logError(8607504787811871291L, new Object[] { this.m_wsaURL });
                return new AdminStatus(-8, " WEB SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isProxyAuthFailure()) {
                this.logError(8607504787811871291L, null);
                return new AdminStatus(-9, " PROXY SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isSSLConnectFailure()) {
                this.logError(8607504787811871250L, new Object[] { this.m_wsaURL, "query", ex2.getMessage() }, 3);
                return new AdminStatus(-10, " SSL CONNECTION FAILURE ");
            }
            this.logError(8607504787811871250L, new Object[] { this.m_wsaURL, "get WSA statistics", ex2.getMessage() }, 3);
            return new AdminStatus(-3, " CONNECT FAILURE ");
        }
        if (pingResponse.getAdminEnabled() == 0) {
            this.logError(8607504787811871252L, new Object[] { "get WSA statistics", this.m_wsaURL });
            return new AdminStatus(-7, " WSA ADMINISTRATION DISABLED ");
        }
        Response invokeAdminMethod;
        try {
            invokeAdminMethod = wsaAdminClient.invokeAdminMethod("wsastatus", null);
        }
        catch (Exception ex3) {
            if (wsaAdminClient.isWebServerAuthFailure()) {
                this.logError(8607504787811871291L, new Object[] { this.m_wsaURL });
                return new AdminStatus(-8, " WEB SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isProxyAuthFailure()) {
                this.logError(8607504787811871291L, null);
                return new AdminStatus(-9, " PROXY SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isSSLConnectFailure()) {
                this.logError(8607504787811871250L, new Object[] { this.m_wsaURL, "query", ex3.getMessage() }, 3);
                return new AdminStatus(-10, " SSL CONNECTION FAILURE ");
            }
            this.logError(8607504787811871253L, new Object[] { this.m_wsaURL, "get WSA statistics", ex3.getMessage() });
            return new AdminStatus(-4, " REQUEST FAILURE ");
        }
        try {
            wsaAdminClient.disconnect();
        }
        catch (Exception ex4) {
            this.logError(8607504787811871251L, new Object[] { this.m_wsaURL, "get WSA statistics", ex4.getMessage() });
            return new AdminStatus(-6, " DISCONNECT FAILURE ");
        }
        if (!invokeAdminMethod.generatedFault()) {
            wsaStatusInfo.copyContentFrom((WsaStatusInfo)invokeAdminMethod.getReturnValue().getValue());
            return new AdminStatus(0);
        }
        this.logError(8607504787811871254L, new Object[] { "get WSA statistics", this.m_wsaURL, this.logFaultStr(invokeAdminMethod) });
        return new AdminStatus(-2, this.faultErrorStr(invokeAdminMethod));
    }
    
    public AdminStatus resetStats(final String s) {
        final Parameter obj = new Parameter("name", String.class, (Object)s, "http://schemas.xmlsoap.org/soap/encoding/");
        final Vector<Parameter> vector = new Vector<Parameter>();
        vector.addElement(obj);
        final WsaAdminClient wsaAdminClient = new WsaAdminClient();
        final Properties protocolOptions = this.createProtocolOptions();
        try {
            wsaAdminClient.init(protocolOptions, null, 0, 0);
        }
        catch (Exception ex) {
            this.logError(8607504787811871249L, new Object[] { "reset stats", ex.getMessage() });
            return new AdminStatus(-5, " INIT FAILURE ");
        }
        PingResponse pingResponse;
        try {
            final String connect = wsaAdminClient.connect(this.m_wsaURL, this.m_username, this.m_password);
            pingResponse = new PingResponse();
            pingResponse.convertMsg(connect);
        }
        catch (Exception ex2) {
            if (wsaAdminClient.isWebServerAuthFailure()) {
                this.logError(8607504787811871291L, new Object[] { this.m_wsaURL });
                return new AdminStatus(-8, " WEB SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isProxyAuthFailure()) {
                this.logError(8607504787811871291L, null);
                return new AdminStatus(-9, " PROXY SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isSSLConnectFailure()) {
                this.logError(8607504787811871250L, new Object[] { this.m_wsaURL, "query", ex2.getMessage() }, 3);
                return new AdminStatus(-10, " SSL CONNECTION FAILURE ");
            }
            this.logError(8607504787811871250L, new Object[] { this.m_wsaURL, "reset stats", ex2.getMessage() }, 3);
            return new AdminStatus(-3, " CONNECT FAILURE ");
        }
        if (pingResponse.getAdminEnabled() == 0) {
            this.logError(8607504787811871252L, new Object[] { "reset stats", this.m_wsaURL });
            return new AdminStatus(-7, " WSA ADMINISTRATION DISABLED ");
        }
        Response invokeAdminMethod;
        try {
            invokeAdminMethod = wsaAdminClient.invokeAdminMethod("resetappstatus", vector);
        }
        catch (Exception ex3) {
            if (wsaAdminClient.isWebServerAuthFailure()) {
                this.logError(8607504787811871291L, new Object[] { this.m_wsaURL });
                return new AdminStatus(-8, " WEB SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isProxyAuthFailure()) {
                this.logError(8607504787811871291L, null);
                return new AdminStatus(-9, " PROXY SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isSSLConnectFailure()) {
                this.logError(8607504787811871250L, new Object[] { this.m_wsaURL, "query", ex3.getMessage() }, 3);
                return new AdminStatus(-10, " SSL CONNECTION FAILURE ");
            }
            this.logError(8607504787811871253L, new Object[] { this.m_wsaURL, "reset stats", ex3.getMessage() });
            return new AdminStatus(-4, " REQUEST FAILURE ");
        }
        try {
            wsaAdminClient.disconnect();
        }
        catch (Exception ex4) {
            this.logError(8607504787811871251L, new Object[] { this.m_wsaURL, "reset stats", ex4.getMessage() });
            return new AdminStatus(-6, " DISCONNECT FAILURE ");
        }
        if (invokeAdminMethod.generatedFault()) {
            this.logError(8607504787811871254L, new Object[] { "reset stats", this.m_wsaURL, this.logFaultStr(invokeAdminMethod) });
            return new AdminStatus(-2, this.faultErrorStr(invokeAdminMethod));
        }
        return new AdminStatus(0);
    }
    
    public AdminStatus resetWsaStats() {
        final WsaAdminClient wsaAdminClient = new WsaAdminClient();
        final Properties protocolOptions = this.createProtocolOptions();
        try {
            wsaAdminClient.init(protocolOptions, null, 0, 0);
        }
        catch (Exception ex) {
            this.logError(8607504787811871249L, new Object[] { "reset WSA statistics", ex.getMessage() });
            return new AdminStatus(-5, " INIT FAILURE ");
        }
        PingResponse pingResponse;
        try {
            final String connect = wsaAdminClient.connect(this.m_wsaURL, this.m_username, this.m_password);
            pingResponse = new PingResponse();
            pingResponse.convertMsg(connect);
        }
        catch (Exception ex2) {
            if (wsaAdminClient.isWebServerAuthFailure()) {
                this.logError(8607504787811871291L, new Object[] { this.m_wsaURL });
                return new AdminStatus(-8, " WEB SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isProxyAuthFailure()) {
                this.logError(8607504787811871291L, null);
                return new AdminStatus(-9, " PROXY SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isSSLConnectFailure()) {
                this.logError(8607504787811871250L, new Object[] { this.m_wsaURL, "query", ex2.getMessage() }, 3);
                return new AdminStatus(-10, " SSL CONNECTION FAILURE ");
            }
            this.logError(8607504787811871250L, new Object[] { this.m_wsaURL, "reset WSA statistics", ex2.getMessage() }, 3);
            return new AdminStatus(-3, " CONNECT FAILURE ");
        }
        if (pingResponse.getAdminEnabled() == 0) {
            this.logError(8607504787811871252L, new Object[] { "reset WSA statistics", this.m_wsaURL });
            return new AdminStatus(-7, " WSA ADMINISTRATION DISABLED ");
        }
        Response invokeAdminMethod;
        try {
            invokeAdminMethod = wsaAdminClient.invokeAdminMethod("resetwsastatus", null);
        }
        catch (Exception ex3) {
            if (wsaAdminClient.isWebServerAuthFailure()) {
                this.logError(8607504787811871291L, new Object[] { this.m_wsaURL });
                return new AdminStatus(-8, " WEB SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isProxyAuthFailure()) {
                this.logError(8607504787811871291L, null);
                return new AdminStatus(-9, " PROXY SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isSSLConnectFailure()) {
                this.logError(8607504787811871250L, new Object[] { this.m_wsaURL, "query", ex3.getMessage() }, 3);
                return new AdminStatus(-10, " SSL CONNECTION FAILURE ");
            }
            this.logError(8607504787811871253L, new Object[] { this.m_wsaURL, "reset WSA statistics", ex3.getMessage() });
            return new AdminStatus(-4, " REQUEST FAILURE ");
        }
        try {
            wsaAdminClient.disconnect();
        }
        catch (Exception ex4) {
            this.logError(8607504787811871251L, new Object[] { this.m_wsaURL, "reset WSA statistics", ex4.getMessage() });
            return new AdminStatus(-6, " DISCONNECT FAILURE ");
        }
        if (invokeAdminMethod.generatedFault()) {
            this.logError(8607504787811871254L, new Object[] { "reset stats", this.m_wsaURL, this.logFaultStr(invokeAdminMethod) });
            return new AdminStatus(-2, this.faultErrorStr(invokeAdminMethod));
        }
        return new AdminStatus(0);
    }
    
    public AdminStatus enable(final String s) {
        final Parameter obj = new Parameter("name", String.class, (Object)s, "http://schemas.xmlsoap.org/soap/encoding/");
        final Vector<Parameter> vector = new Vector<Parameter>();
        vector.addElement(obj);
        final WsaAdminClient wsaAdminClient = new WsaAdminClient();
        final Properties protocolOptions = this.createProtocolOptions();
        try {
            wsaAdminClient.init(protocolOptions, null, 0, 0);
        }
        catch (Exception ex) {
            this.logError(8607504787811871249L, new Object[] { "enable", ex.getMessage() });
            return new AdminStatus(-5, " INIT FAILURE ");
        }
        PingResponse pingResponse;
        try {
            final String connect = wsaAdminClient.connect(this.m_wsaURL, this.m_username, this.m_password);
            pingResponse = new PingResponse();
            pingResponse.convertMsg(connect);
        }
        catch (Exception ex2) {
            if (wsaAdminClient.isWebServerAuthFailure()) {
                this.logError(8607504787811871291L, new Object[] { this.m_wsaURL });
                return new AdminStatus(-8, " WEB SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isProxyAuthFailure()) {
                this.logError(8607504787811871291L, null);
                return new AdminStatus(-9, " PROXY SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isSSLConnectFailure()) {
                this.logError(8607504787811871250L, new Object[] { this.m_wsaURL, "query", ex2.getMessage() }, 3);
                return new AdminStatus(-10, " SSL CONNECTION FAILURE ");
            }
            this.logError(8607504787811871250L, new Object[] { this.m_wsaURL, "enable", ex2.getMessage() }, 3);
            return new AdminStatus(-3, " CONNECT FAILURE ");
        }
        if (pingResponse.getAdminEnabled() == 0) {
            this.logError(8607504787811871252L, new Object[] { "enable", this.m_wsaURL });
            return new AdminStatus(-7, " WSA ADMINISTRATION DISABLED ");
        }
        Response invokeAdminMethod;
        try {
            invokeAdminMethod = wsaAdminClient.invokeAdminMethod("enableApp", vector);
        }
        catch (Exception ex3) {
            if (wsaAdminClient.isWebServerAuthFailure()) {
                this.logError(8607504787811871291L, new Object[] { this.m_wsaURL });
                return new AdminStatus(-8, " WEB SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isProxyAuthFailure()) {
                this.logError(8607504787811871291L, null);
                return new AdminStatus(-9, " PROXY SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isSSLConnectFailure()) {
                this.logError(8607504787811871250L, new Object[] { this.m_wsaURL, "query", ex3.getMessage() }, 3);
                return new AdminStatus(-10, " SSL CONNECTION FAILURE ");
            }
            this.logError(8607504787811871253L, new Object[] { this.m_wsaURL, "enable", ex3.getMessage() });
            return new AdminStatus(-4, " REQUEST FAILURE ");
        }
        try {
            wsaAdminClient.disconnect();
        }
        catch (Exception ex4) {
            this.logError(8607504787811871251L, new Object[] { this.m_wsaURL, "enable", ex4.getMessage() });
            return new AdminStatus(-6, " DISCONNECT FAILURE ");
        }
        if (invokeAdminMethod.generatedFault()) {
            this.logError(8607504787811871254L, new Object[] { "enable", this.m_wsaURL, this.logFaultStr(invokeAdminMethod) });
            return new AdminStatus(-2, this.faultErrorStr(invokeAdminMethod));
        }
        return new AdminStatus(0);
    }
    
    public AdminStatus disable(final String s) {
        final Parameter obj = new Parameter("name", String.class, (Object)s, "http://schemas.xmlsoap.org/soap/encoding/");
        final Vector<Parameter> vector = new Vector<Parameter>();
        vector.addElement(obj);
        final WsaAdminClient wsaAdminClient = new WsaAdminClient();
        final Properties protocolOptions = this.createProtocolOptions();
        try {
            wsaAdminClient.init(protocolOptions, null, 0, 0);
        }
        catch (Exception ex) {
            this.logError(8607504787811871249L, new Object[] { "disable", ex.getMessage() });
            return new AdminStatus(-5, " INIT FAILURE ");
        }
        PingResponse pingResponse;
        try {
            final String connect = wsaAdminClient.connect(this.m_wsaURL, this.m_username, this.m_password);
            pingResponse = new PingResponse();
            pingResponse.convertMsg(connect);
        }
        catch (Exception ex2) {
            if (wsaAdminClient.isWebServerAuthFailure()) {
                this.logError(8607504787811871291L, new Object[] { this.m_wsaURL });
                return new AdminStatus(-8, " WEB SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isProxyAuthFailure()) {
                this.logError(8607504787811871291L, null);
                return new AdminStatus(-9, " PROXY SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isSSLConnectFailure()) {
                this.logError(8607504787811871250L, new Object[] { this.m_wsaURL, "query", ex2.getMessage() }, 3);
                return new AdminStatus(-10, " SSL CONNECTION FAILURE ");
            }
            this.logError(8607504787811871250L, new Object[] { this.m_wsaURL, "disable", ex2.getMessage() }, 3);
            return new AdminStatus(-3, " CONNECT FAILURE ");
        }
        if (pingResponse.getAdminEnabled() == 0) {
            this.logError(8607504787811871252L, new Object[] { "disable", this.m_wsaURL });
            return new AdminStatus(-7, " WSA ADMINISTRATION DISABLED ");
        }
        Response invokeAdminMethod;
        try {
            invokeAdminMethod = wsaAdminClient.invokeAdminMethod("disableApp", vector);
        }
        catch (Exception ex3) {
            if (wsaAdminClient.isWebServerAuthFailure()) {
                this.logError(8607504787811871291L, new Object[] { this.m_wsaURL });
                return new AdminStatus(-8, " WEB SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isProxyAuthFailure()) {
                this.logError(8607504787811871291L, null);
                return new AdminStatus(-9, " PROXY SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isSSLConnectFailure()) {
                this.logError(8607504787811871250L, new Object[] { this.m_wsaURL, "query", ex3.getMessage() }, 3);
                return new AdminStatus(-10, " SSL CONNECTION FAILURE ");
            }
            this.logError(8607504787811871253L, new Object[] { this.m_wsaURL, "disable", ex3.getMessage() });
            return new AdminStatus(-4, " REQUEST FAILURE ");
        }
        try {
            wsaAdminClient.disconnect();
        }
        catch (Exception ex4) {
            this.logError(8607504787811871251L, new Object[] { this.m_wsaURL, "disable", ex4.getMessage() });
            return new AdminStatus(-6, " DISCONNECT FAILURE ");
        }
        if (invokeAdminMethod.generatedFault()) {
            this.logError(8607504787811871254L, new Object[] { "disable", this.m_wsaURL, this.logFaultStr(invokeAdminMethod) });
            return new AdminStatus(-2, this.faultErrorStr(invokeAdminMethod));
        }
        return new AdminStatus(0);
    }
    
    public AdminStatus setProperties(final String s, final Vector vector) {
        final Hashtable hashtable = new Hashtable();
        final AdminStatus properties = this.getProperties(s, hashtable);
        if (properties.getStatus() == 0) {
            return this.setAppProps(s, this.convertV2H(hashtable, vector));
        }
        return properties;
    }
    
    public AdminStatus setWsaProperties(final Vector vector) {
        return this.setProperties("urn:services-progress-com:wsa-admin:0002", vector);
    }
    
    public AdminStatus setProperties(final String s, final String s2, final String s3) {
        final Hashtable hashtable = new Hashtable();
        final Hashtable<String, Object> hashtable2 = new Hashtable<String, Object>();
        final AdminStatus properties = this.getProperties(s, hashtable);
        if (properties.getStatus() != 0) {
            return properties;
        }
        final Object convertValue = this.convertValue(hashtable, s2, s3);
        if (convertValue != null) {
            hashtable2.put(s2, convertValue);
            return this.setAppProps(s, hashtable2);
        }
        this.logError(8607504787811871323L, new Object[] { s2 }, 3);
        return new AdminStatus(-4, " Invalid property " + s2 + " ");
    }
    
    public AdminStatus setWSAProperties(final String s, final String s2) {
        return this.setProperties("urn:services-progress-com:wsa-admin:0002", s, s2);
    }
    
    public AdminStatus setdefaults(final String s, final String s2) {
        return this.setProperties("urn:services-progress-com:wsa-default:0001", s, s2);
    }
    
    public AdminStatus setdefaults(final Vector vector) {
        return this.setProperties("urn:services-progress-com:wsa-default:0001", vector);
    }
    
    public AdminStatus getProperties(final String s, final Hashtable hashtable) {
        final Parameter obj = new Parameter("name", String.class, (Object)s, "http://schemas.xmlsoap.org/soap/encoding/");
        final Vector<Parameter> vector = new Vector<Parameter>();
        vector.addElement(obj);
        final WsaAdminClient wsaAdminClient = new WsaAdminClient();
        final Properties protocolOptions = this.createProtocolOptions();
        try {
            wsaAdminClient.init(protocolOptions, null, 0, 0);
        }
        catch (Exception ex) {
            this.logError(8607504787811871249L, new Object[] { "get properties", ex.getMessage() });
            return new AdminStatus(-5, " INIT FAILURE ");
        }
        PingResponse pingResponse;
        try {
            final String connect = wsaAdminClient.connect(this.m_wsaURL, this.m_username, this.m_password);
            pingResponse = new PingResponse();
            pingResponse.convertMsg(connect);
        }
        catch (Exception ex2) {
            if (wsaAdminClient.isWebServerAuthFailure()) {
                this.logError(8607504787811871291L, new Object[] { this.m_wsaURL });
                return new AdminStatus(-8, " WEB SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isProxyAuthFailure()) {
                this.logError(8607504787811871291L, null);
                return new AdminStatus(-9, " PROXY SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isSSLConnectFailure()) {
                this.logError(8607504787811871250L, new Object[] { this.m_wsaURL, "query", ex2.getMessage() }, 3);
                return new AdminStatus(-10, " SSL CONNECTION FAILURE ");
            }
            this.logError(8607504787811871250L, new Object[] { this.m_wsaURL, "get properties", ex2.getMessage() }, 3);
            return new AdminStatus(-3, " CONNECT FAILURE ");
        }
        if (pingResponse.getAdminEnabled() == 0) {
            this.logError(8607504787811871252L, new Object[] { "get properties", this.m_wsaURL });
            return new AdminStatus(-7, " WSA ADMINISTRATION DISABLED ");
        }
        Response invokeAdminMethod;
        try {
            invokeAdminMethod = wsaAdminClient.invokeAdminMethod("getRuntimeProperties", vector);
        }
        catch (Exception ex3) {
            if (wsaAdminClient.isWebServerAuthFailure()) {
                this.logError(8607504787811871291L, new Object[] { this.m_wsaURL });
                return new AdminStatus(-8, " WEB SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isProxyAuthFailure()) {
                this.logError(8607504787811871291L, null);
                return new AdminStatus(-9, " PROXY SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isSSLConnectFailure()) {
                this.logError(8607504787811871250L, new Object[] { this.m_wsaURL, "query", ex3.getMessage() }, 3);
                return new AdminStatus(-10, " SSL CONNECTION FAILURE ");
            }
            this.logError(8607504787811871253L, new Object[] { this.m_wsaURL, "get properties", ex3.getMessage() });
            return new AdminStatus(-4, " REQUEST FAILURE ");
        }
        try {
            wsaAdminClient.disconnect();
        }
        catch (Exception ex4) {
            this.logError(8607504787811871251L, new Object[] { this.m_wsaURL, "get properties", ex4.getMessage() });
            return new AdminStatus(-6, " DISCONNECT FAILURE ");
        }
        if (!invokeAdminMethod.generatedFault()) {
            hashtable.putAll((Map)invokeAdminMethod.getReturnValue().getValue());
            return new AdminStatus(0);
        }
        this.logError(8607504787811871254L, new Object[] { "get properties", this.m_wsaURL, this.logFaultStr(invokeAdminMethod) });
        return new AdminStatus(-2, this.faultErrorStr(invokeAdminMethod));
    }
    
    public AdminStatus getWSAProperties(final Hashtable hashtable) {
        return this.getProperties("urn:services-progress-com:wsa-admin:0002", hashtable);
    }
    
    public AdminStatus getDefaults(final Hashtable hashtable) {
        return this.getProperties("urn:services-progress-com:wsa-default:0001", hashtable);
    }
    
    public AdminStatus resetRTProps(final String s) {
        final Parameter obj = new Parameter("name", String.class, (Object)s, "http://schemas.xmlsoap.org/soap/encoding/");
        final Vector<Parameter> vector = new Vector<Parameter>();
        vector.addElement(obj);
        final WsaAdminClient wsaAdminClient = new WsaAdminClient();
        final Properties protocolOptions = this.createProtocolOptions();
        try {
            wsaAdminClient.init(protocolOptions, null, 0, 0);
        }
        catch (Exception ex) {
            this.logError(8607504787811871249L, new Object[] { "reset properties", ex.getMessage() });
            return new AdminStatus(-5, " INIT FAILURE ");
        }
        PingResponse pingResponse;
        try {
            final String connect = wsaAdminClient.connect(this.m_wsaURL, this.m_username, this.m_password);
            pingResponse = new PingResponse();
            pingResponse.convertMsg(connect);
        }
        catch (Exception ex2) {
            if (wsaAdminClient.isWebServerAuthFailure()) {
                this.logError(8607504787811871291L, new Object[] { this.m_wsaURL });
                return new AdminStatus(-8, " WEB SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isProxyAuthFailure()) {
                this.logError(8607504787811871291L, null);
                return new AdminStatus(-9, " PROXY SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isSSLConnectFailure()) {
                this.logError(8607504787811871250L, new Object[] { this.m_wsaURL, "query", ex2.getMessage() }, 3);
                return new AdminStatus(-10, " SSL CONNECTION FAILURE ");
            }
            this.logError(8607504787811871250L, new Object[] { this.m_wsaURL, "reset properties", ex2.getMessage() }, 3);
            return new AdminStatus(-3, " CONNECT FAILURE ");
        }
        if (pingResponse.getAdminEnabled() == 0) {
            this.logError(8607504787811871252L, new Object[] { "reset properties", this.m_wsaURL });
            return new AdminStatus(-7, " WSA ADMINISTRATION DISABLED ");
        }
        Response invokeAdminMethod;
        try {
            invokeAdminMethod = wsaAdminClient.invokeAdminMethod("resetRuntimeProperties", vector);
        }
        catch (Exception ex3) {
            if (wsaAdminClient.isWebServerAuthFailure()) {
                this.logError(8607504787811871291L, new Object[] { this.m_wsaURL });
                return new AdminStatus(-8, " WEB SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isProxyAuthFailure()) {
                this.logError(8607504787811871291L, null);
                return new AdminStatus(-9, " PROXY SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isSSLConnectFailure()) {
                this.logError(8607504787811871250L, new Object[] { this.m_wsaURL, "query", ex3.getMessage() }, 3);
                return new AdminStatus(-10, " SSL CONNECTION FAILURE ");
            }
            this.logError(8607504787811871253L, new Object[] { this.m_wsaURL, "reset properties", ex3.getMessage() });
            return new AdminStatus(-4, " REQUEST FAILURE ");
        }
        try {
            wsaAdminClient.disconnect();
        }
        catch (Exception ex4) {
            this.logError(8607504787811871251L, new Object[] { this.m_wsaURL, "reset properties", ex4.getMessage() });
            return new AdminStatus(-6, " DISCONNECT FAILURE ");
        }
        if (invokeAdminMethod.generatedFault()) {
            this.logError(8607504787811871254L, new Object[] { "reset properties", this.m_wsaURL, this.logFaultStr(invokeAdminMethod) });
            return new AdminStatus(-2, this.faultErrorStr(invokeAdminMethod));
        }
        return new AdminStatus(0);
    }
    
    public AdminStatus resetDefaults() {
        return this.resetRTProps("urn:services-progress-com:wsa-default:0001");
    }
    
    public AdminStatus pingWSA(final PingResponse pingResponse) {
        final WsaAdminClient wsaAdminClient = new WsaAdminClient();
        final Properties protocolOptions = this.createProtocolOptions();
        try {
            wsaAdminClient.init(protocolOptions, null, 0, 0);
        }
        catch (Exception ex) {
            this.logError(8607504787811871249L, new Object[] { "query", ex.getMessage() });
            return new AdminStatus(-5, " INIT FAILURE ");
        }
        try {
            pingResponse.convertMsg(wsaAdminClient.connect(this.m_wsaURL, this.m_username, this.m_password));
        }
        catch (Exception ex2) {
            if (wsaAdminClient.isWebServerAuthFailure()) {
                this.logError(8607504787811871291L, new Object[] { this.m_wsaURL });
                return new AdminStatus(-8, " WEB SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isProxyAuthFailure()) {
                this.logError(8607504787811871291L, null);
                return new AdminStatus(-9, " PROXY SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isSSLConnectFailure()) {
                this.logError(8607504787811871250L, new Object[] { this.m_wsaURL, "query", ex2.getMessage() }, 3);
                return new AdminStatus(-10, " SSL CONNECTION FAILURE ");
            }
            this.logError(8607504787811871250L, new Object[] { this.m_wsaURL, "query", ex2.getMessage() }, 5);
            return new AdminStatus(-3, " CONNECT FAILURE ");
        }
        try {
            wsaAdminClient.disconnect();
        }
        catch (Exception ex3) {
            this.logError(8607504787811871251L, new Object[] { this.m_wsaURL, "query", ex3.getMessage() });
            return new AdminStatus(-6, " DISCONNECT FAILURE ");
        }
        return new AdminStatus(0);
    }
    
    public AdminStatus importApp(final AppContainer appContainer, final ListInfo listInfo) {
        final Parameter obj = new Parameter("AppContainer", (Class)appContainer.getClass(), (Object)appContainer, "http://schemas.xmlsoap.org/soap/encoding/");
        final Vector<Parameter> vector = new Vector<Parameter>();
        vector.addElement(obj);
        final WsaAdminClient wsaAdminClient = new WsaAdminClient();
        final Properties protocolOptions = this.createProtocolOptions();
        try {
            wsaAdminClient.init(protocolOptions, null, 0, 0);
        }
        catch (Exception ex) {
            this.logError(8607504787811871249L, new Object[] { "import", ex.getMessage() });
            return new AdminStatus(-5, " INIT FAILURE ");
        }
        PingResponse pingResponse;
        try {
            final String connect = wsaAdminClient.connect(this.m_wsaURL, this.m_username, this.m_password);
            pingResponse = new PingResponse();
            pingResponse.convertMsg(connect);
        }
        catch (Exception ex2) {
            if (wsaAdminClient.isWebServerAuthFailure()) {
                this.logError(8607504787811871291L, new Object[] { this.m_wsaURL });
                return new AdminStatus(-8, " WEB SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isProxyAuthFailure()) {
                this.logError(8607504787811871291L, null);
                return new AdminStatus(-9, " PROXY SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isSSLConnectFailure()) {
                this.logError(8607504787811871250L, new Object[] { this.m_wsaURL, "query", ex2.getMessage() }, 3);
                return new AdminStatus(-10, " SSL CONNECTION FAILURE ");
            }
            this.logError(8607504787811871250L, new Object[] { this.m_wsaURL, "import", ex2.getMessage() }, 3);
            return new AdminStatus(-3, " CONNECT FAILURE ");
        }
        if (pingResponse.getAdminEnabled() == 0) {
            this.logError(8607504787811871252L, new Object[] { "import", this.m_wsaURL });
            return new AdminStatus(-7, " WSA ADMINISTRATION DISABLED ");
        }
        Response invokeAdminMethod;
        try {
            invokeAdminMethod = wsaAdminClient.invokeAdminMethod("importApp", vector);
        }
        catch (Exception ex3) {
            if (wsaAdminClient.isWebServerAuthFailure()) {
                this.logError(8607504787811871291L, new Object[] { this.m_wsaURL });
                return new AdminStatus(-8, " WEB SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isProxyAuthFailure()) {
                this.logError(8607504787811871291L, null);
                return new AdminStatus(-9, " PROXY SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isSSLConnectFailure()) {
                this.logError(8607504787811871250L, new Object[] { this.m_wsaURL, "query", ex3.getMessage() }, 3);
                return new AdminStatus(-10, " SSL CONNECTION FAILURE ");
            }
            this.logError(8607504787811871253L, new Object[] { this.m_wsaURL, "import", ex3.getMessage() });
            return new AdminStatus(-4, " REQUEST FAILURE ");
        }
        try {
            wsaAdminClient.disconnect();
        }
        catch (Exception ex4) {
            this.logError(8607504787811871251L, new Object[] { this.m_wsaURL, "import", ex4.getMessage() });
            return new AdminStatus(-6, " DISCONNECT FAILURE ");
        }
        if (invokeAdminMethod.generatedFault()) {
            this.logError(8607504787811871254L, new Object[] { "import", this.m_wsaURL, this.logFaultStr(invokeAdminMethod) });
            return new AdminStatus(-2, this.faultErrorStr(invokeAdminMethod));
        }
        final ListInfo listInfo2 = (ListInfo)invokeAdminMethod.getReturnValue().getValue();
        listInfo.setFriendlyName(listInfo2.getFriendlyName());
        listInfo.setStatus(listInfo2.getStatus());
        listInfo.setTargetURI(listInfo2.getTargetURI());
        return new AdminStatus(0);
    }
    
    public AdminStatus exportApp(final String s, final AppContainer appContainer) {
        final Parameter obj = new Parameter("name", String.class, (Object)s, "http://schemas.xmlsoap.org/soap/encoding/");
        final Vector<Parameter> vector = new Vector<Parameter>();
        vector.addElement(obj);
        final WsaAdminClient wsaAdminClient = new WsaAdminClient();
        final Properties protocolOptions = this.createProtocolOptions();
        try {
            wsaAdminClient.init(protocolOptions, null, 0, 0);
        }
        catch (Exception ex) {
            this.logError(8607504787811871249L, new Object[] { "export", ex.getMessage() });
            return new AdminStatus(-5, " INIT FAILURE ");
        }
        PingResponse pingResponse;
        try {
            final String connect = wsaAdminClient.connect(this.m_wsaURL, this.m_username, this.m_password);
            pingResponse = new PingResponse();
            pingResponse.convertMsg(connect);
        }
        catch (Exception ex2) {
            if (wsaAdminClient.isWebServerAuthFailure()) {
                this.logError(8607504787811871291L, new Object[] { this.m_wsaURL });
                return new AdminStatus(-8, " WEB SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isProxyAuthFailure()) {
                this.logError(8607504787811871291L, null);
                return new AdminStatus(-9, " PROXY SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isSSLConnectFailure()) {
                this.logError(8607504787811871250L, new Object[] { this.m_wsaURL, "query", ex2.getMessage() }, 3);
                return new AdminStatus(-10, " SSL CONNECTION FAILURE ");
            }
            this.logError(8607504787811871250L, new Object[] { this.m_wsaURL, "export", ex2.getMessage() }, 3);
            return new AdminStatus(-3, " CONNECT FAILURE ");
        }
        if (pingResponse.getAdminEnabled() == 0) {
            this.logError(8607504787811871252L, new Object[] { "export", this.m_wsaURL });
            return new AdminStatus(-7, " WSA ADMINISTRATION DISABLED ");
        }
        Response invokeAdminMethod;
        try {
            invokeAdminMethod = wsaAdminClient.invokeAdminMethod("exportApp", vector);
        }
        catch (Exception ex3) {
            if (wsaAdminClient.isWebServerAuthFailure()) {
                this.logError(8607504787811871291L, new Object[] { this.m_wsaURL });
                return new AdminStatus(-8, " WEB SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isProxyAuthFailure()) {
                this.logError(8607504787811871291L, null);
                return new AdminStatus(-9, " PROXY SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isSSLConnectFailure()) {
                this.logError(8607504787811871250L, new Object[] { this.m_wsaURL, "query", ex3.getMessage() }, 3);
                return new AdminStatus(-10, " SSL CONNECTION FAILURE ");
            }
            this.logError(8607504787811871253L, new Object[] { this.m_wsaURL, "export", ex3.getMessage() });
            return new AdminStatus(-4, " REQUEST FAILURE ");
        }
        try {
            wsaAdminClient.disconnect();
        }
        catch (Exception ex4) {
            this.logError(8607504787811871251L, new Object[] { this.m_wsaURL, "export", ex4.getMessage() });
            return new AdminStatus(-6, " DISCONNECT FAILURE ");
        }
        if (!invokeAdminMethod.generatedFault()) {
            final AppContainer appContainer2 = (AppContainer)invokeAdminMethod.getReturnValue().getValue();
            appContainer.setWSAD(appContainer2.getWSAD());
            appContainer.setFriendlyName(appContainer2.getFriendlyName());
            appContainer.setRuntimeProperties(appContainer2.getRuntimeProperties());
            return new AdminStatus(0);
        }
        this.logError(8607504787811871254L, new Object[] { "export", this.m_wsaURL, this.logFaultStr(invokeAdminMethod) });
        return new AdminStatus(-2, this.faultErrorStr(invokeAdminMethod));
    }
    
    public AdminStatus update(final WSAD wsad, final String s, final ListInfo listInfo) {
        final Parameter obj = new Parameter("name", String.class, (Object)s, "http://schemas.xmlsoap.org/soap/encoding/");
        final Parameter obj2 = new Parameter("WSAD", (Class)wsad.getClass(), (Object)wsad, "http://schemas.xmlsoap.org/soap/encoding/");
        final Vector<Parameter> vector = new Vector<Parameter>();
        vector.addElement(obj);
        vector.addElement(obj2);
        final WsaAdminClient wsaAdminClient = new WsaAdminClient();
        final Properties protocolOptions = this.createProtocolOptions();
        try {
            wsaAdminClient.init(protocolOptions, null, 0, 0);
        }
        catch (Exception ex) {
            this.logError(8607504787811871249L, new Object[] { "update", ex.getMessage() });
            return new AdminStatus(-5, " INIT FAILURE ");
        }
        PingResponse pingResponse;
        try {
            final String connect = wsaAdminClient.connect(this.m_wsaURL, this.m_username, this.m_password);
            pingResponse = new PingResponse();
            pingResponse.convertMsg(connect);
        }
        catch (Exception ex2) {
            if (wsaAdminClient.isWebServerAuthFailure()) {
                this.logError(8607504787811871291L, new Object[] { this.m_wsaURL });
                return new AdminStatus(-8, " WEB SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isProxyAuthFailure()) {
                this.logError(8607504787811871291L, null);
                return new AdminStatus(-9, " PROXY SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isSSLConnectFailure()) {
                this.logError(8607504787811871250L, new Object[] { this.m_wsaURL, "query", ex2.getMessage() }, 3);
                return new AdminStatus(-10, " SSL CONNECTION FAILURE ");
            }
            this.logError(8607504787811871250L, new Object[] { this.m_wsaURL, "update", ex2.getMessage() }, 3);
            return new AdminStatus(-3, " CONNECT FAILURE ");
        }
        if (pingResponse.getAdminEnabled() == 0) {
            this.logError(8607504787811871252L, new Object[] { "update", this.m_wsaURL });
            return new AdminStatus(-7, " WSA ADMINISTRATION DISABLED ");
        }
        Response invokeAdminMethod;
        try {
            invokeAdminMethod = wsaAdminClient.invokeAdminMethod("update", vector);
        }
        catch (Exception ex3) {
            if (wsaAdminClient.isWebServerAuthFailure()) {
                this.logError(8607504787811871291L, new Object[] { this.m_wsaURL });
                return new AdminStatus(-8, " WEB SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isProxyAuthFailure()) {
                this.logError(8607504787811871291L, null);
                return new AdminStatus(-9, " PROXY SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isSSLConnectFailure()) {
                this.logError(8607504787811871250L, new Object[] { this.m_wsaURL, "query", ex3.getMessage() }, 3);
                return new AdminStatus(-10, " SSL CONNECTION FAILURE ");
            }
            this.logError(8607504787811871253L, new Object[] { this.m_wsaURL, "update", ex3.getMessage() });
            return new AdminStatus(-4, " REQUEST FAILURE ");
        }
        try {
            wsaAdminClient.disconnect();
        }
        catch (Exception ex4) {
            this.logError(8607504787811871251L, new Object[] { this.m_wsaURL, "update", ex4.getMessage() });
            return new AdminStatus(-6, " DISCONNECT FAILURE ");
        }
        if (invokeAdminMethod.generatedFault()) {
            this.logError(8607504787811871254L, new Object[] { "update", this.m_wsaURL, this.logFaultStr(invokeAdminMethod) });
            return new AdminStatus(-2, this.faultErrorStr(invokeAdminMethod));
        }
        final ListInfo listInfo2 = (ListInfo)invokeAdminMethod.getReturnValue().getValue();
        listInfo.setFriendlyName(listInfo2.getFriendlyName());
        listInfo.setStatus(listInfo2.getStatus());
        listInfo.setTargetURI(listInfo2.getTargetURI());
        return new AdminStatus(0);
    }
    
    public AdminStatus test(final String s, final String s2) {
        final String s3 = "Loopback test data";
        final QueryInfo queryInfo = new QueryInfo();
        final AdminStatus query = this.query(s, queryInfo);
        if (query.getStatus() != 0) {
            return query;
        }
        final String targetURI = queryInfo.getListInfo().getTargetURI();
        String s4;
        if (targetURI.startsWith("urn:")) {
            s4 = targetURI + ":__WSTest";
        }
        else {
            s4 = targetURI + "/__WSTest";
        }
        final Parameter obj = new Parameter("target", String.class, (Object)s2, "http://schemas.xmlsoap.org/soap/encoding/");
        final Parameter obj2 = new Parameter("loopback", String.class, (Object)s3, "http://schemas.xmlsoap.org/soap/encoding/");
        final Vector<Parameter> vector = new Vector<Parameter>();
        vector.addElement(obj);
        vector.addElement(obj2);
        final WsaAdminClient wsaAdminClient = new WsaAdminClient();
        final Properties protocolOptions = this.createProtocolOptions();
        try {
            wsaAdminClient.init(protocolOptions, null, 0, 0);
        }
        catch (Exception ex) {
            this.logError(8607504787811871249L, new Object[] { "get statistics", ex.getMessage() });
            return new AdminStatus(-5, " INIT FAILURE ");
        }
        PingResponse pingResponse;
        try {
            final String connect = wsaAdminClient.connect(this.m_wsaURL, this.m_username, this.m_password);
            pingResponse = new PingResponse();
            pingResponse.convertMsg(connect);
        }
        catch (Exception ex2) {
            if (wsaAdminClient.isWebServerAuthFailure()) {
                this.logError(8607504787811871291L, new Object[] { this.m_wsaURL });
                return new AdminStatus(-8, " WEB SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isProxyAuthFailure()) {
                this.logError(8607504787811871291L, null);
                return new AdminStatus(-9, " PROXY SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isSSLConnectFailure()) {
                this.logError(8607504787811871250L, new Object[] { this.m_wsaURL, "query", ex2.getMessage() }, 3);
                return new AdminStatus(-10, " SSL CONNECTION FAILURE ");
            }
            this.logError(8607504787811871250L, new Object[] { this.m_wsaURL, "get statistics", ex2.getMessage() }, 3);
            return new AdminStatus(-3, " CONNECT FAILURE ");
        }
        if (pingResponse.getAdminEnabled() == 0) {
            this.logError(8607504787811871252L, new Object[] { "get statistics", this.m_wsaURL });
            return new AdminStatus(-7, " WSA ADMINISTRATION DISABLED ");
        }
        Response invokeSOAPMethod;
        try {
            wsaAdminClient.setWsaURL(wsaAdminClient.getConnectPath());
            invokeSOAPMethod = wsaAdminClient.invokeSOAPMethod("__test", vector, s4);
        }
        catch (Exception ex3) {
            if (wsaAdminClient.isWebServerAuthFailure()) {
                this.logError(8607504787811871291L, new Object[] { this.m_wsaURL });
                return new AdminStatus(-8, " WEB SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isProxyAuthFailure()) {
                this.logError(8607504787811871291L, null);
                return new AdminStatus(-9, " PROXY SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isSSLConnectFailure()) {
                this.logError(8607504787811871250L, new Object[] { this.m_wsaURL, "query", ex3.getMessage() }, 3);
                return new AdminStatus(-10, " SSL CONNECTION FAILURE ");
            }
            this.logError(8607504787811871253L, new Object[] { this.m_wsaURL, "get statistics", ex3.getMessage() });
            return new AdminStatus(-4, " REQUEST FAILURE ");
        }
        try {
            wsaAdminClient.disconnect();
        }
        catch (Exception ex4) {
            this.logError(8607504787811871251L, new Object[] { this.m_wsaURL, "get statistics", ex4.getMessage() });
            return new AdminStatus(-6, " DISCONNECT FAILURE ");
        }
        if (!invokeSOAPMethod.generatedFault()) {
            invokeSOAPMethod.getReturnValue();
            return new AdminStatus(0);
        }
        this.logError(8607504787811871254L, new Object[] { "get statistics", this.m_wsaURL, this.logFaultStr(invokeSOAPMethod) });
        return new AdminStatus(-2, this.faultErrorStr(invokeSOAPMethod));
    }
    
    private AdminStatus setAppProps(final String s, final Hashtable hashtable) {
        final Parameter obj = new Parameter("name", String.class, (Object)s, "http://schemas.xmlsoap.org/soap/encoding/");
        final Parameter obj2 = new Parameter("properties", Hashtable.class, (Object)hashtable, "http://schemas.xmlsoap.org/soap/encoding/");
        final Vector<Parameter> vector = new Vector<Parameter>();
        vector.addElement(obj);
        vector.addElement(obj2);
        final WsaAdminClient wsaAdminClient = new WsaAdminClient();
        final Properties protocolOptions = this.createProtocolOptions();
        try {
            wsaAdminClient.init(protocolOptions, null, 0, 0);
        }
        catch (Exception ex) {
            this.logError(8607504787811871249L, new Object[] { "set properties", ex.getMessage() });
            return new AdminStatus(-5, " INIT FAILURE ");
        }
        PingResponse pingResponse;
        try {
            final String connect = wsaAdminClient.connect(this.m_wsaURL, this.m_username, this.m_password);
            pingResponse = new PingResponse();
            pingResponse.convertMsg(connect);
        }
        catch (Exception ex2) {
            if (wsaAdminClient.isWebServerAuthFailure()) {
                this.logError(8607504787811871291L, new Object[] { this.m_wsaURL });
                return new AdminStatus(-8, " WEB SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isProxyAuthFailure()) {
                this.logError(8607504787811871291L, null);
                return new AdminStatus(-9, " PROXY SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isSSLConnectFailure()) {
                this.logError(8607504787811871250L, new Object[] { this.m_wsaURL, "query", ex2.getMessage() }, 3);
                return new AdminStatus(-10, " SSL CONNECTION FAILURE ");
            }
            this.logError(8607504787811871250L, new Object[] { this.m_wsaURL, "set properties", ex2.getMessage() }, 3);
            return new AdminStatus(-3, " CONNECT FAILURE ");
        }
        if (pingResponse.getAdminEnabled() == 0) {
            this.logError(8607504787811871252L, new Object[] { "set properties", this.m_wsaURL });
            return new AdminStatus(-7, " WSA ADMINISTRATION DISABLED ");
        }
        Response invokeAdminMethod;
        try {
            invokeAdminMethod = wsaAdminClient.invokeAdminMethod("setRuntimeProperties", vector);
        }
        catch (Exception ex3) {
            if (wsaAdminClient.isWebServerAuthFailure()) {
                this.logError(8607504787811871291L, new Object[] { this.m_wsaURL });
                return new AdminStatus(-8, " WEB SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isProxyAuthFailure()) {
                this.logError(8607504787811871291L, null);
                return new AdminStatus(-9, " PROXY SERVER AUTHENTICATION FAILURE ");
            }
            if (wsaAdminClient.isSSLConnectFailure()) {
                this.logError(8607504787811871250L, new Object[] { this.m_wsaURL, "query", ex3.getMessage() }, 3);
                return new AdminStatus(-10, " SSL CONNECTION FAILURE ");
            }
            this.logError(8607504787811871253L, new Object[] { this.m_wsaURL, "set properties", ex3.getMessage() });
            return new AdminStatus(-4, " REQUEST FAILURE ");
        }
        try {
            wsaAdminClient.disconnect();
        }
        catch (Exception ex4) {
            this.logError(8607504787811871251L, new Object[] { this.m_wsaURL, "set properties", ex4.getMessage() });
            return new AdminStatus(-6, " DISCONNECT FAILURE ");
        }
        if (invokeAdminMethod.generatedFault()) {
            this.logError(8607504787811871254L, new Object[] { "set properties", this.m_wsaURL, this.logFaultStr(invokeAdminMethod) });
            return new AdminStatus(-2, this.faultErrorStr(invokeAdminMethod));
        }
        return new AdminStatus(0);
    }
    
    private Object convertValue(final Hashtable hashtable, final String key, final String s) {
        Object o = null;
        Object value = null;
        try {
            value = hashtable.get(key);
        }
        catch (Exception ex) {}
        if (value != null) {
            if (value.getClass().getName().equals("java.lang.Integer")) {
                try {
                    o = new Integer(s);
                }
                catch (Exception ex2) {}
            }
            else if (value.getClass().getName().equals("java.lang.Long")) {
                try {
                    o = new Long(s);
                }
                catch (Exception ex3) {}
            }
            else if (value.getClass().getName().equals("java.lang.String")) {
                o = s;
            }
            else if (value.getClass().getName().equals("java.lang.Boolean")) {
                try {
                    o = new Boolean(s);
                }
                catch (Exception ex4) {}
            }
        }
        return o;
    }
    
    private Hashtable convertV2H(final Hashtable hashtable, final Vector vector) {
        final Hashtable<Object, Object> hashtable2 = new Hashtable<Object, Object>();
        for (int i = 0; i < vector.size(); ++i) {
            final PropElement propElement = vector.elementAt(i);
            hashtable2.put(propElement.key(), this.convertValue(hashtable, (String)propElement.key(), (String)propElement.value()));
        }
        return hashtable2;
    }
    
    private void logError(final long n, final Object[] array) {
        this.logError(n, array, 5);
    }
    
    private void logError(final long n, final Object[] array, final int n2) {
        String s;
        if (array != null) {
            s = UBToolsMsg.getMsg(n, array);
        }
        else {
            s = UBToolsMsg.getMsg(n);
        }
        WsaPluginLog.logMsg(n2, s);
    }
    
    private String faultErrorStr(final Response response) {
        String decode = "";
        final Vector detailEntries = response.getFault().getDetailEntries();
        for (int i = 0; i < detailEntries.size(); ++i) {
            decode = this.decode(detailEntries.elementAt(i).getFirstChild().getFirstChild().getNodeValue());
        }
        return decode;
    }
    
    private String logFaultStr(final Response response) {
        final StringBuffer sb = new StringBuffer();
        final Vector detailEntries = response.getFault().getDetailEntries();
        for (int i = 0; i < detailEntries.size(); ++i) {
            final NodeList childNodes = detailEntries.elementAt(i).getChildNodes();
            for (int j = 0; j < childNodes.getLength(); ++j) {
                final Node firstChild = childNodes.item(j).getFirstChild();
                sb.append("(");
                sb.append(firstChild.getParentNode().getNodeName());
                sb.append("): ");
                sb.append(this.decode(firstChild.getNodeValue()));
                sb.append("  ");
            }
        }
        return sb.toString();
    }
    
    private Properties createProtocolOptions() {
        final Properties properties = new Properties();
        properties.setProperty("PROGRESS.Session.certificateStore", this.m_certsjar);
        if (this.context != null && this.context.getUseProxy()) {
            ((Hashtable<String, String>)properties).put("PROGRESS.Session.proxyHost", this.context.getProxyHost());
            ((Hashtable<String, String>)properties).put("PROGRESS.Session.proxyPort", this.context.getProxyPort());
            ((Hashtable<String, String>)properties).put("PROGRESS.Session.proxyUserId", this.context.getProxyUsername());
            ((Hashtable<String, String>)properties).put("PROGRESS.Session.proxyPassword", new crypto().decrypt(this.context.getProxyPwd()));
        }
        final Enumeration<?> propertyNames = this.m_protocolOptions.propertyNames();
        while (propertyNames.hasMoreElements()) {
            final String s = (String)propertyNames.nextElement();
            properties.setProperty(s, this.m_protocolOptions.getProperty(s));
        }
        return properties;
    }
    
    public String decode(final String s) {
        if (s == null) {
            return null;
        }
        try {
            final StringWriter stringWriter = new StringWriter();
            this.decode(new StringReader(s), stringWriter);
            return stringWriter.toString();
        }
        catch (IOException ex) {
            return s;
        }
    }
    
    public void decode(final Reader reader, final Writer writer) throws IOException {
        int i = -1;
        try {
            final StringBuffer sb = new StringBuffer();
            int read;
            while ((read = new PushbackReader(reader).read()) != -1) {
                ++i;
                if (sb.length() > 0) {
                    if (read == 59) {
                        sb.append((char)read);
                        writer.write(this.resolveReference(sb.toString()));
                        sb.setLength(0);
                    }
                    else {
                        sb.append((char)read);
                    }
                }
                else if (read == 38 && sb.length() == 0) {
                    sb.append((char)read);
                }
                else {
                    writer.write((char)read);
                }
            }
            writer.flush();
        }
        catch (IOException ex) {
            throw new IOException("Failed to decode the given string \"" + reader + "\" at index " + i + ": " + ex.getMessage());
        }
    }
    
    protected String resolveReference(final String s) {
        final String s2 = this.isCharacterReference(s) ? this.resolveCharacterReference(s) : this.resolveEntityReference(s);
        return (s2 == null) ? s : s2;
    }
    
    protected boolean isCharacterReference(final String s) {
        return s.startsWith("&#");
    }
    
    protected boolean isHexCharacterReference(final String s) {
        return s.startsWith("&#x");
    }
    
    protected String resolveCharacterReference(final String s) {
        try {
            final boolean hexCharacterReference = this.isHexCharacterReference(s);
            final String substring = s.substring(hexCharacterReference ? 3 : 2);
            return new String(new char[] { (char)Integer.parseInt(substring.substring(0, substring.length() - 1), hexCharacterReference ? 16 : 10) });
        }
        catch (NumberFormatException ex) {
            return s;
        }
    }
    
    protected String resolveEntityReference(final String s) {
        return WsaAdminPlugin.STANDARD_ENTITIES.get(s);
    }
    
    static {
        (WsaAdminPlugin.STANDARD_ENTITIES = new Hashtable()).put("&amp;", "&");
        WsaAdminPlugin.STANDARD_ENTITIES.put("&quot;", "\"");
        WsaAdminPlugin.STANDARD_ENTITIES.put("&apos;", "'");
        WsaAdminPlugin.STANDARD_ENTITIES.put("&lt;", "<");
        WsaAdminPlugin.STANDARD_ENTITIES.put("&gt;", ">");
    }
}
