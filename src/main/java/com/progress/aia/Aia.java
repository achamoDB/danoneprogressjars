// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.aia;

import com.actional.lg.interceptor.sdk.FlowStub;
import com.actional.lg.interceptor.sdk.SiteStub;
import com.actional.lg.interceptor.sdk.InteractionStub;
import javax.servlet.GenericServlet;
import java.io.OutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.ServletRequest;
import java.util.Hashtable;
import com.actional.lg.interceptor.sdk.helpers.InterHelpJ2ee;
import com.progress.open4gl.ConnectException;
import com.progress.nameserver.client.NameServerClient;
import java.io.InterruptedIOException;
import com.actional.lg.interceptor.sdk.helpers.InterHelpBase;
import java.net.MalformedURLException;
import javax.servlet.http.HttpUtils;
import java.util.Enumeration;
import javax.servlet.ServletOutputStream;
import java.io.PrintWriter;
import java.io.IOException;
import com.progress.ubroker.util.ubMsg;
import com.actional.lg.interceptor.sdk.Interaction;
import com.actional.lg.interceptor.sdk.ClientInteraction;
import com.progress.ubroker.util.ubAppServerMsg;
import java.io.InputStream;
import com.progress.ubroker.util.MsgInputStream;
import com.progress.ubroker.util.ObjectHolder;
import com.actional.lg.interceptor.sdk.ServerInteraction;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletException;
import java.util.Properties;
import com.progress.common.licensemgr.LicenseMgr;
import javax.servlet.Servlet;
import javax.servlet.UnavailableException;
import javax.servlet.ServletConfig;
import com.progress.ubroker.util.ubWatchDog;
import com.progress.common.ehnlog.IAppLogger;
import com.progress.ubroker.util.IWatchable;
import com.progress.ubroker.util.ubConstants;
import javax.servlet.http.HttpServlet;

public class Aia extends HttpServlet implements ubConstants, IWatchable
{
    public static final String AIA_VER = "v102b (07-Jun-09)";
    private static final String CONNHDL_PARAM_NAME = "CONNHDL";
    private static final int PKT_DATA_PACKET = 0;
    private static final int PKT_FIRST_PACKET = 1;
    private static final int PKT_LAST_PACKET = 2;
    private static final int PKT_ONLY_PACKET = 3;
    private static final int FLDX_USERNAME = 1;
    private static final int FLDX_ARBSTRING = 3;
    private static final int FLDX_APPSERVICE = 5;
    private static final String FIRSTCONNSTR = "__firstconn";
    private static final String SECONDCONNSTR = "__secondconn";
    private static final int FLDX_SECONDCONN = 0;
    private static final int FLDX_HOST = 1;
    private static final int FLDX_PORT = 3;
    static final byte ASKSTATE_INIT = 0;
    static final byte ASKSTATE_ACTIVITY_TIMEOUT = 1;
    static final byte ASKSTATE_RESPONSE_TIMEOUT = 2;
    static final String[] DESC_ASKSTATE;
    private static final short ACT_DT_OEGROUP = 681;
    private static final short ACT_DT_APPSERVER = 686;
    private static final short ACT_DT_AIA = 687;
    IAppLogger log;
    AiaProperties properties;
    ubWatchDog connectionWatchdog;
    AiaLicenseMgr licenseMgr;
    ServerConnectionPool connPool;
    AiaMgt m_mgtObj;
    Object nsLock;
    boolean m_ASKrqstACKsent;
    byte m_ASKstate;
    int m_clientASKActivityTimeoutMs;
    int m_clientASKResponseTimeoutMs;
    String CIServiceName;
    
    public Aia() {
        this.log = null;
        this.m_mgtObj = null;
    }
    
    public void init(final ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        try {
            final String initParameter = servletConfig.getInitParameter("InstallDir");
            if (initParameter == null) {
                throw new UnavailableException((Servlet)this, "InstallDir argument is not specified.");
            }
            final Properties properties = System.getProperties();
            ((Hashtable<String, String>)properties).put("Install.Dir", initParameter);
            System.setProperties(properties);
            this.properties = new AiaProperties();
            this.log = this.properties.processArgs((Servlet)this, servletConfig);
            if (this.log.ifLogBasic(1L, 0)) {
                this.log.logBasic(0, "AIA v102b (07-Jun-09)");
            }
            if (this.log.ifLogBasic(2L, 1)) {
                this.properties.print(this.log);
            }
            this.logInitParams(servletConfig);
            this.licenseMgr = new AiaLicenseMgr(this.properties.httpsEnabled, this.log);
            (this.connPool = new ServerConnectionPool(this.log)).startASKWatchdog(this.properties);
            this.startConnectionWatchdog();
            this.nsLock = new Object();
            this.m_ASKstate = 0;
            this.m_ASKrqstACKsent = false;
            this.m_clientASKActivityTimeoutMs = this.properties.clientASKActivityTimeout * 1000;
            this.m_clientASKResponseTimeoutMs = this.properties.clientASKResponseTimeout * 1000;
        }
        catch (UnavailableException ex) {
            this.processInitException("UnavailableException in init().", (Throwable)ex);
        }
        catch (LicenseMgr.CannotContactLicenseMgr cannotContactLicenseMgr) {
            this.processInitException("Unable to initialize license mgr.", cannotContactLicenseMgr);
        }
        catch (LicenseMgr.NotLicensed notLicensed) {
            this.processInitException("AIA_HTTP is not licensed to run in this configuration.", notLicensed);
        }
        catch (Throwable t) {
            this.processInitException("Unexpected exception in init().", t);
        }
    }
    
    public void doPost(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse) throws ServletException, IOException {
        ServerInteraction serverInteraction = ServerInteraction.get();
        ubAppServerMsg ubAppServerMsg = null;
        String parameter = null;
        final ObjectHolder objectHolder = new ObjectHolder();
        final ObjectHolder objectHolder2 = new ObjectHolder();
        int intValue = 0;
        int getubRq = 0;
        ServerConnection serverConnection = null;
        try {
            if (this.log.ifLogBasic(4L, 2)) {
                this.log.logBasic(2, "Start: doPost()");
            }
            this.logRequest(httpServletRequest, httpServletResponse);
            final MsgInputStream msgInputStream = new MsgInputStream((InputStream)((ServletRequest)httpServletRequest).getInputStream(), 10240, 0, this.log, 3, 8L, 3);
            ubAppServerMsg = (ubAppServerMsg)msgInputStream.readMsg();
            parameter = ((ServletRequest)httpServletRequest).getParameter("CONNHDL");
            if (this.log.ifLogBasic(4L, 2)) {
                this.log.logBasic(2, "connHdl= " + parameter);
            }
            this.licenseMgr.checkRequest(((ServletRequest)httpServletRequest).getScheme());
            boolean b = true;
            ubMsg ubMsg = null;
            int n = 0;
            switch (getubRq = ubAppServerMsg.getubRq()) {
                case 3: {
                    b = false;
                    if (null == serverInteraction) {
                        serverInteraction = this.actionalSetup(httpServletRequest);
                    }
                }
                case 2: {
                    ubMsg = this.processConnect(ubAppServerMsg, objectHolder, b);
                    serverConnection = (ServerConnection)objectHolder.getObject();
                    parameter = ((serverConnection == null) ? null : serverConnection.getConnHdl());
                    this.setContentLength(httpServletResponse, ubMsg);
                    n = 3;
                    break;
                }
                case 6: {
                    serverConnection = this.connPool.getConnection(parameter);
                    if (serverConnection == null) {
                        this.log.logError("Aia ERROR : invalid connection handle");
                        ubMsg = this.newDisconnectErrorRsp(1, "Aia ERROR : invalid connection handle");
                    }
                    else {
                        if (!serverConnection.isStateFree()) {
                            serverInteraction = this.actionalSetup(httpServletRequest);
                        }
                        ubMsg = this.processDisconnect(serverConnection, ubAppServerMsg);
                    }
                    parameter = null;
                    this.setContentLength(httpServletResponse, ubMsg);
                    n = 3;
                    break;
                }
                default: {
                    if (this.properties.actionalEnabled && serverInteraction == null) {
                        serverInteraction = this.actionalSetup(httpServletRequest);
                    }
                    serverConnection = this.connPool.getConnection(parameter);
                    if (serverConnection == null) {
                        this.log.logError("Aia ERROR : invalid connection handle");
                        ubMsg = this.newClientRsp(1, "Aia ERROR : invalid connection handle");
                        parameter = null;
                        n = 3;
                        break;
                    }
                    ubMsg = this.processRequest(msgInputStream, httpServletResponse, serverConnection, ubAppServerMsg, objectHolder2);
                    if (objectHolder2.getObject() != null) {
                        intValue = (int)objectHolder2.getObject();
                    }
                    if (ubMsg != null && ubMsg.getubRsp() != 0) {
                        this.logerrorConn(serverConnection, "disconnected.");
                        serverConnection.disconnect();
                        this.connPool.remConnection(parameter);
                    }
                    n = (intValue | 0x2);
                    break;
                }
            }
            if (this.properties.actionalEnabled && serverInteraction != null && getubRq == 5) {
                final ClientInteraction value = ClientInteraction.get();
                this.logInteraction((Interaction)value);
                ((Interaction)value).end();
            }
            this.sendRsp(httpServletResponse, ubMsg, serverConnection, parameter, n);
            this.connPool.logPool("Connection Pool");
            if (this.log.ifLogBasic(4L, 2)) {
                this.log.logBasic(2, "End:   doPost()");
            }
        }
        catch (AiaLicenseMgr.RedirectException ex) {
            this.log.logStackTrace("URL Redirection", ex);
            this.processRedirection(httpServletRequest, httpServletResponse, parameter, serverConnection);
        }
        catch (AiaLicenseMgr.AiaLicenseException ex2) {
            this.processPostException(ubAppServerMsg, httpServletResponse, "Aia License Error", ex2, parameter, serverConnection);
        }
        catch (Throwable t) {
            this.processPostException(ubAppServerMsg, httpServletResponse, "Unexpected error", t, parameter, serverConnection);
        }
        if (this.properties.actionalEnabled && serverInteraction != null && getubRq == 5) {
            ((Interaction)serverInteraction).end();
        }
    }
    
    public void doGet(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse) throws ServletException, IOException {
        final String queryString = httpServletRequest.getQueryString();
        if (this.log.ifLogBasic(4L, 2)) {
            this.log.logBasic(2, "Start: doGet()");
        }
        this.logRequest(httpServletRequest, httpServletResponse);
        final String parameter = ((ServletRequest)httpServletRequest).getParameter("CONNHDL");
        if (parameter != null) {
            if (this.connPool.getConnection(parameter) == null) {
                httpServletResponse.sendError(410);
            }
            return;
        }
        ((ServletResponse)httpServletResponse).setContentType("text/html");
        final PrintWriter writer = ((ServletResponse)httpServletResponse).getWriter();
        if (this.m_mgtObj == null) {
            this.m_mgtObj = new AiaMgt(this.properties, this.connPool, this.log);
        }
        try {
            this.licenseMgr.checkRequest(((ServletRequest)httpServletRequest).getScheme());
            if (httpServletRequest.getMethod().equals("HEAD")) {
                if (this.log.ifLogBasic(4L, 2)) {
                    this.log.logBasic(2, "End:   doHead()");
                }
                return;
            }
            final String remoteAddr = ((ServletRequest)httpServletRequest).getRemoteAddr();
            if (this.log.ifLogBasic(4L, 2)) {
                this.log.logBasic(2, "rmtIP= " + remoteAddr);
            }
            if (!this.m_mgtObj.authorizedCommand(remoteAddr)) {
                writer.println("<html><body><h1>Internal commands restricted: access denied.\n</h1>");
                writer.println("</body></html>");
                this.log.logError("Unauthorized admin access from " + remoteAddr);
                if (this.log.ifLogBasic(4L, 2)) {
                    this.log.logBasic(2, "End:   doGet()");
                }
                return;
            }
            if (queryString != null) {
                this.m_mgtObj.processCommand(queryString, writer);
            }
            else {
                writer.println("<html><body><h1>" + this.properties.instanceName + " OK\n</h1>");
                writer.println("<pre>" + this.connPool.displayPoolInfo("Connection Pool") + "</pre>");
                writer.println("</body></html>");
            }
            writer.close();
            if (this.log.ifLogBasic(4L, 2)) {
                this.log.logBasic(2, "End:   doGet()");
            }
        }
        catch (AiaLicenseMgr.RedirectException ex4) {
            this.processRedirection(httpServletRequest, httpServletResponse, null, null);
        }
        catch (AiaLicenseMgr.HTTPSNotLicensedException ex) {
            this.processGetException(httpServletResponse, "Aia License Error : HTTPS is not licensed.", ex);
        }
        catch (AiaLicenseMgr.HTTPSNotEnabledException ex2) {
            this.processGetException(httpServletResponse, "Aia License Error : HTTPS is not enabled.", ex2);
        }
        catch (AiaLicenseMgr.AiaLicenseException ex3) {
            this.processGetException(httpServletResponse, "Aia License Error : " + ex3.toString(), ex3);
        }
        catch (Throwable t) {
            this.processGetException(httpServletResponse, "Unexpected error : " + t.toString(), t);
        }
    }
    
    public String getServletInfo() {
        return "A servlet that interfaces to Progress AppServer";
    }
    
    public void destroy() {
        this.connPool.remAllConnections();
        this.log.logClose();
    }
    
    public void watchEvent() {
        this.connPool.remMoldyConnections(this.properties.connTrimTimeout);
    }
    
    private void print(final PrintWriter printWriter, final String str, final String s) throws IOException {
        printWriter.print(" " + str + ": ");
        printWriter.println((s == null) ? "&lt;none&gt;" : s);
    }
    
    private void print(final PrintWriter printWriter, final String str, final int x) throws IOException {
        printWriter.print(" " + str + ": ");
        if (x == -1) {
            printWriter.println("&lt;none&gt;");
        }
        else {
            printWriter.println(x);
        }
    }
    
    private void sendRsp(final ubAppServerMsg ubAppServerMsg, final HttpServletResponse httpServletResponse, final String s) throws IOException {
        ubAppServerMsg ubAppServerMsg2 = null;
        if (ubAppServerMsg != null) {
            switch (ubAppServerMsg.getubRq()) {
                case 3: {
                    ubAppServerMsg2 = this.newConnectErrorRsp(1, s);
                    break;
                }
                case 6: {
                    ubAppServerMsg2 = this.newDisconnectErrorRsp(1, s);
                    break;
                }
                default: {
                    ubAppServerMsg2 = this.newClientRsp(1, s);
                    break;
                }
            }
        }
        else {
            ubAppServerMsg2 = this.newClientRsp(1, s);
        }
        this.setContentLength(httpServletResponse, ubAppServerMsg2);
        this.sendRsp(httpServletResponse, ubAppServerMsg2, null, null, 3);
    }
    
    private void sendRsp(final HttpServletResponse httpServletResponse, final ubMsg ubMsg, final ServerConnection serverConnection, String s, final int n) throws IOException {
        if (s == null) {
            s = "";
        }
        if ((n & 0x1) > 0) {
            ((ServletResponse)httpServletResponse).setContentType("application/octet-stream");
            httpServletResponse.setHeader("CONNHDL", s);
        }
        if (ubMsg == null && this.log.ifLogBasic(4L, 2)) {
            this.log.logBasic(2, "sent HTTP reply");
        }
        final ServletOutputStream outputStream = ((ServletResponse)httpServletResponse).getOutputStream();
        if (ubMsg == null) {
            ((OutputStream)outputStream).flush();
            return;
        }
        final byte[] serializeMsg = ubMsg.serializeMsg();
        if (this.log.ifLogBasic(8L, 3)) {
            this.logtxtConn(serverConnection, 2, "sent client response " + ubMsg.getubRqDesc() + " [" + serializeMsg.length + "]");
        }
        if (this.log.ifLogVerbose(8L, 3)) {
            this.log.logDump(3, 3, "[" + ((serverConnection == null) ? "" : serverConnection.toString()) + "] " + "sent to client: [" + serializeMsg.length + "]", serializeMsg, serializeMsg.length);
        }
        ((OutputStream)outputStream).write(serializeMsg, 0, serializeMsg.length);
        if ((n & 0x2) > 0) {
            ((OutputStream)outputStream).flush();
        }
    }
    
    private void logerrorConn(final ServerConnection serverConnection, final String str) {
        this.log.logError("[" + ((serverConnection == null) ? "" : serverConnection.toString()) + "] " + str);
    }
    
    private void logtxtConn(final ServerConnection serverConnection, final int n, final String s) {
        if (n == 2) {
            this.log.logBasic(3, "[" + ((serverConnection == null) ? "" : serverConnection.toString()) + "] " + s);
        }
        else if (n == 3) {
            this.log.logVerbose(3, "[" + ((serverConnection == null) ? "" : serverConnection.toString()) + "] " + s);
        }
    }
    
    public void logInitParams(final ServletConfig servletConfig) {
        if (!this.log.ifLogBasic(1L, 0)) {
            return;
        }
        final Enumeration initParameterNames = servletConfig.getInitParameterNames();
        if (initParameterNames != null) {
            while (initParameterNames.hasMoreElements()) {
                final String str = initParameterNames.nextElement();
                this.log.logBasic(0, str + ": " + ((GenericServlet)this).getInitParameter(str));
            }
        }
    }
    
    public void logRequest(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse) {
        if (!this.log.ifLogBasic(4L, 2)) {
            return;
        }
        this.log.logBasic(2, "Requested URL: " + HttpUtils.getRequestURL(httpServletRequest).toString());
        this.log.logBasic(2, "Request information:");
        this.log.logBasic(2, "Request method : " + httpServletRequest.getMethod());
        this.log.logBasic(2, "Request URI : " + httpServletRequest.getRequestURI());
        this.log.logBasic(2, "Request protocol : " + ((ServletRequest)httpServletRequest).getProtocol());
        this.log.logBasic(2, "Request scheme : " + ((ServletRequest)httpServletRequest).getScheme());
        this.log.logBasic(2, "Servlet path : " + httpServletRequest.getServletPath());
        this.log.logBasic(2, "Path info : " + httpServletRequest.getPathInfo());
        this.log.logBasic(2, "Path translated : " + httpServletRequest.getPathTranslated());
        this.log.logBasic(2, "Query string : " + httpServletRequest.getQueryString());
        this.log.logBasic(2, "Content length : " + ((ServletRequest)httpServletRequest).getContentLength());
        this.log.logBasic(2, "Content type : " + ((ServletRequest)httpServletRequest).getContentType());
        this.log.logBasic(2, "Server name : " + ((ServletRequest)httpServletRequest).getServerName());
        this.log.logBasic(2, "Server port : " + ((ServletRequest)httpServletRequest).getServerPort());
        this.log.logBasic(2, "Remote user : " + httpServletRequest.getRemoteUser());
        this.log.logBasic(2, "Remote address : " + ((ServletRequest)httpServletRequest).getRemoteAddr());
        this.log.logBasic(2, "Remote host : " + ((ServletRequest)httpServletRequest).getRemoteHost());
        this.log.logBasic(2, "Authorization scheme : " + httpServletRequest.getAuthType());
        final Enumeration headerNames = httpServletRequest.getHeaderNames();
        if (headerNames.hasMoreElements()) {
            this.log.logBasic(2, "Request headers:");
            while (headerNames.hasMoreElements()) {
                final String str = headerNames.nextElement();
                this.log.logBasic(2, str + ": " + httpServletRequest.getHeader(str));
            }
        }
        final Enumeration parameterNames = ((ServletRequest)httpServletRequest).getParameterNames();
        if (parameterNames.hasMoreElements()) {
            this.log.logBasic(2, "Servlet parameters (Single Value style):");
            while (parameterNames.hasMoreElements()) {
                final String str2 = parameterNames.nextElement();
                this.log.logBasic(2, str2 + " = " + ((ServletRequest)httpServletRequest).getParameter(str2));
            }
        }
        final Enumeration parameterNames2 = ((ServletRequest)httpServletRequest).getParameterNames();
        if (parameterNames2.hasMoreElements()) {
            this.log.logBasic(2, "Servlet parameters (Multiple Value style):");
            while (parameterNames2.hasMoreElements()) {
                final String str3 = parameterNames2.nextElement();
                final String[] array = ((ServletRequest)httpServletRequest).getParameterValues(str3);
                if (array != null) {
                    this.log.logBasic(2, str3 + " = " + array[0]);
                    for (int i = 1; i < array.length; ++i) {
                        this.log.logBasic(2, "           " + array[i]);
                    }
                }
            }
        }
        final String characterEncoding = ((ServletResponse)httpServletResponse).getCharacterEncoding();
        this.log.logBasic(2, "Response Information:");
        this.log.logBasic(2, "MIME character encoding: " + characterEncoding);
    }
    
    private String extractCookie(final String s, final String str) {
        if (s == null || s == "") {
            return null;
        }
        final int length = s.length();
        final int index = s.indexOf(str + "=");
        if (index == -1) {
            return null;
        }
        final int n = index + str.length() + 1;
        if (n > length) {
            return null;
        }
        int index2 = s.indexOf(";", n);
        if (index2 == -1) {
            index2 = length;
        }
        return s.substring(n, index2);
    }
    
    private void processInitException(final String x, final Throwable t) throws ServletException {
        System.err.println(x);
        t.printStackTrace();
        if (this.log != null) {
            this.log.logStackTrace(x, t);
            this.log.logClose();
        }
        if (t instanceof ServletException) {
            throw (ServletException)t;
        }
        throw new UnavailableException((Servlet)this, t.toString());
    }
    
    private void processPostException(final ubAppServerMsg ubAppServerMsg, final HttpServletResponse httpServletResponse, final String str, final Throwable t, final String s, final ServerConnection serverConnection) throws IOException {
        this.log.logStackTrace(str, t);
        if (s != null) {
            if (serverConnection != null) {
                serverConnection.disconnect();
            }
            this.connPool.remConnection(s);
        }
        this.sendRsp(ubAppServerMsg, httpServletResponse, str + ":" + t.toString());
        ((OutputStream)((ServletResponse)httpServletResponse).getOutputStream()).close();
    }
    
    private void processGetException(final HttpServletResponse httpServletResponse, final String str, final Throwable t) throws IOException {
        this.log.logStackTrace(str, t);
        ((ServletResponse)httpServletResponse).setContentType("text/html");
        final PrintWriter writer = ((ServletResponse)httpServletResponse).getWriter();
        writer.println("<html><body><h1>" + this.properties.instanceName + "</h1>");
        writer.println(str + "\n");
        writer.println("</body></html>");
        writer.close();
    }
    
    private void processRedirection(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse, final String s, final ServerConnection serverConnection) throws IOException {
        if (s != null) {
            if (serverConnection != null) {
                serverConnection.disconnect();
            }
            this.connPool.remConnection(s);
        }
        String str = "https://" + ((ServletRequest)httpServletRequest).getServerName() + ":" + this.properties.securePort + httpServletRequest.getRequestURI();
        final String queryString = httpServletRequest.getQueryString();
        if (queryString != null && queryString.length() > 0) {
            str = str + "?" + queryString;
        }
        if (this.log.ifLogBasic(4L, 2)) {
            this.log.logBasic(2, "redirect url= " + str);
        }
        httpServletResponse.sendRedirect(str);
        if (this.log.ifLogBasic(4L, 2)) {
            this.log.logBasic(2, "HTTP request redirected to " + str);
        }
    }
    
    private ubMsg processConnect(final ubAppServerMsg ubAppServerMsg, final ObjectHolder objectHolder, final boolean stateFree) {
        final ObjectHolder objectHolder2 = new ObjectHolder();
        final ObjectHolder objectHolder3 = new ObjectHolder();
        final ObjectHolder objectHolder4 = new ObjectHolder();
        final ObjectHolder objectHolder5 = new ObjectHolder();
        final ObjectHolder objectHolder6 = new ObjectHolder();
        this.parseConnectMsg(ubAppServerMsg, objectHolder3, objectHolder4, objectHolder2);
        final String s = (String)objectHolder3.getObject();
        final String str = (String)objectHolder4.getObject();
        final String s2 = (String)objectHolder2.getObject();
        if (this.log.ifLogVerbose(8L, 3)) {
            this.log.logVerbose(3, "appUserid= " + s);
            this.log.logVerbose(3, "arbString= " + str);
            this.log.logVerbose(3, "appService= " + s2);
        }
        String str2;
        if (this.properties.nameServer.length() == 0 || !this.properties.regNameServer) {
            this.CIServiceName = this.properties.bHost;
            if (this.isIPv6AddrAndNeedsBraces(this.properties.bHost)) {
                str2 = "http://[" + this.properties.bHost + "]:" + this.properties.bPortnum + "/" + s2;
            }
            else {
                str2 = "http://" + this.properties.bHost + ":" + this.properties.bPortnum + "/" + s2;
            }
        }
        else {
            this.CIServiceName = this.properties.nsHost;
            if (this.isIPv6AddrAndNeedsBraces(this.properties.nsHost)) {
                str2 = "http://[" + this.properties.nsHost + "]:" + this.properties.nsPortnum + "/" + s2;
            }
            else {
                str2 = "http://" + this.properties.nsHost + ":" + this.properties.nsPortnum + "/" + s2;
            }
        }
        if (this.log.ifLogBasic(8L, 3)) {
            this.log.logBasic(3, "processConnect() url= " + str2);
        }
        ServerConnection serverConnection;
        try {
            serverConnection = this.connPool.newServerConnection(str2, s2, s, this.properties.soReadTimeout, this.log);
        }
        catch (MalformedURLException ex) {
            this.log.logError("Error connecting " + s + " to " + s2 + " : " + ex.toString() + ":" + ex.getMessage());
            objectHolder.setObject(null);
            return this.newConnectErrorRsp(8, "MalformedURLException on processConnect() : " + ex.toString());
        }
        if (this.parseArbString(str, objectHolder5, objectHolder6)) {
            if (this.log.ifLogVerbose(8L, 3)) {
                this.log.logVerbose(3, "arbstring host= " + (String)objectHolder5.getObject());
                this.log.logVerbose(3, "arbstring host= " + (int)objectHolder6.getObject());
            }
            serverConnection.setHost((String)objectHolder5.getObject());
            serverConnection.setPort((int)objectHolder6.getObject());
        }
        else if (this.properties.nameServer.length() != 0 && this.properties.regNameServer) {
            final ubAppServerMsg nameserverLookup = this.nameserverLookup(serverConnection, (str.indexOf("__firstconn") != -1) ? "AD" : "AS");
            if (nameserverLookup != null) {
                objectHolder.setObject(null);
                return nameserverLookup;
            }
        }
        if (this.properties.clntAskCaps != 0) {
            switch (ubAppServerMsg.getubVer()) {
                case 108: {
                    break;
                }
                default: {
                    if (this.askValidateProtocolType(serverConnection)) {
                        try {
                            ubAppServerMsg.appendTlvField((short)10, String.valueOf(65536));
                            ubAppServerMsg.appendTlvField((short)11, AiaProperties.formatAskCapabilities(this.properties.clntAskCaps));
                            if (this.log.ifLogVerbose(16L, 4)) {
                                this.log.logVerbose(4, "      Requesting ASK Capabilities= " + AiaProperties.formatAskCapabilities(this.properties.clntAskCaps));
                            }
                        }
                        catch (Exception ex2) {
                            this.log.logError("appendTlvField Exception: " + ex2.getMessage());
                        }
                        break;
                    }
                    break;
                }
            }
        }
        else if (this.log.ifLogVerbose(16L, 4)) {
            this.log.logVerbose(4, "      ASK disabled");
        }
        serverConnection.setStateFree(stateFree);
        if (this.properties.actionalEnabled && !serverConnection.isStateFree()) {
            final ClientInteraction value = ClientInteraction.get();
            ((InteractionStub)value).setUrl(serverConnection.getActionalUrl());
            ((InteractionStub)value).setPeerAddr(serverConnection.getHost());
            ((InteractionStub)value).setServiceName(this.CIServiceName);
            ((SiteStub)value).setSvcType((short)686);
            ((InteractionStub)value).setOpName("_connect");
            value.requestAnalyzed();
            try {
                final String writeHeader = InterHelpBase.writeHeader(value);
                if (ubAppServerMsg.getubVer() >= 109 && writeHeader != null) {
                    ubAppServerMsg.appendTlvField((short)13, writeHeader);
                }
            }
            catch (ubMsg.TlvFieldAlreadyExistsException ex4) {}
            catch (Exception ex5) {}
        }
        ubAppServerMsg ubAppServerMsg2 = this.processConnectRq(serverConnection, ubAppServerMsg, true);
        if (ubAppServerMsg2.getubRsp() == 0 && serverConnection.getServerPort() != 0) {
            serverConnection.disconnect();
            serverConnection.setPort(serverConnection.getServerPort());
            this.trimConnectionID(serverConnection, ubAppServerMsg);
            this.appendConnectionID(serverConnection.getConnID(), ubAppServerMsg);
            switch (ubAppServerMsg.getubVer()) {
                case 108: {
                    break;
                }
                default: {
                    ubAppServerMsg.remTlvField_NoThrow((short)10);
                    ubAppServerMsg.remTlvField_NoThrow((short)11);
                    if (this.askValidateProtocolType(serverConnection) && serverConnection.getASKCaps() != 0) {
                        try {
                            ubAppServerMsg.appendTlvField((short)10, String.valueOf(serverConnection.getASKVersion()));
                            ubAppServerMsg.appendTlvField((short)11, AiaProperties.formatAskCapabilities(serverConnection.getASKCaps()));
                        }
                        catch (Exception ex3) {
                            this.log.logError("appendTlvField Exception: " + ex3.getMessage());
                        }
                        break;
                    }
                    break;
                }
            }
            ubAppServerMsg2 = this.processConnectRq(serverConnection, ubAppServerMsg, false);
        }
        if (this.properties.actionalEnabled && !serverConnection.isStateFree()) {
            final ClientInteraction value2 = ClientInteraction.get();
            this.logInteraction((Interaction)value2);
            ((Interaction)value2).end();
        }
        if (ubAppServerMsg2.getubRsp() == 0) {
            this.connPool.addConnection(serverConnection);
            if (this.properties.clntAskCaps != 0) {
                if (this.log.ifLogBasic(1L, 0)) {
                    this.log.logBasic(0, serverConnection.toString() + "      Negotiated ASK Version= " + this.formatAskVersion(serverConnection.getASKVersion()) + "  Capabilities= " + AiaProperties.formatAskCapabilities(serverConnection.getASKCaps()));
                }
                if (serverConnection.getClientASKEnabled() && this.log.ifLogBasic(1L, 0)) {
                    this.log.logBasic(0, serverConnection.toString() + "      ClientASKActivityTimeout= " + this.properties.clientASKActivityTimeout + "  ClientASKResponseTimeout= " + this.properties.clientASKResponseTimeout);
                }
            }
        }
        else {
            serverConnection = null;
        }
        objectHolder.setObject(serverConnection);
        if (this.properties.actionalEnabled && !serverConnection.isStateFree()) {
            ((Interaction)ServerInteraction.get()).end();
        }
        return ubAppServerMsg2;
    }
    
    private ubAppServerMsg processDisconnect(final ServerConnection obj, final ubAppServerMsg ubAppServerMsg) {
        if (this.log.ifLogVerbose(8L, 3)) {
            this.log.logVerbose(3, "processDisconnect() dest= " + obj);
        }
        if (this.properties.actionalEnabled && !obj.isStateFree()) {
            final ClientInteraction value = ClientInteraction.get();
            ((InteractionStub)value).setUrl(obj.getActionalUrl());
            ((InteractionStub)value).setPeerAddr(obj.getHost());
            ((InteractionStub)value).setServiceName(this.CIServiceName);
            ((SiteStub)value).setSvcType((short)686);
            ((InteractionStub)value).setOpName("_disconnect");
            value.requestAnalyzed();
            try {
                final String writeHeader = InterHelpBase.writeHeader(value);
                if (ubAppServerMsg.getubVer() >= 109 && writeHeader != null) {
                    ubAppServerMsg.appendTlvField((short)13, writeHeader);
                }
            }
            catch (ubMsg.TlvFieldAlreadyExistsException ex) {}
            catch (Exception ex2) {}
        }
        final ubAppServerMsg processDisconnectRq = this.processDisconnectRq(obj, ubAppServerMsg);
        this.connPool.remConnection(obj.getConnHdl());
        if (this.properties.actionalEnabled && !obj.isStateFree()) {
            final ClientInteraction value2 = ClientInteraction.get();
            this.logInteraction((Interaction)value2);
            ((Interaction)value2).end();
            ((Interaction)ServerInteraction.get()).end();
        }
        return processDisconnectRq;
    }
    
    private ubAppServerMsg processRequest(final MsgInputStream msgInputStream, final HttpServletResponse httpServletResponse, final ServerConnection serverConnection, final ubAppServerMsg ubAppServerMsg, final ObjectHolder objectHolder) {
        ubMsg ubMsg = null;
        if (this.log.ifLogVerbose(8L, 3)) {
            this.log.logVerbose(3, "processRequest()");
        }
        try {
            if (this.properties.actionalEnabled) {
                final ClientInteraction value = ClientInteraction.get();
                ((InteractionStub)value).setUrl(serverConnection.getActionalUrl());
                ((InteractionStub)value).setServiceName(this.CIServiceName);
                ((InteractionStub)value).setOpName("RunProcedure");
                ((InteractionStub)value).setPeerAddr(serverConnection.getHost());
                try {
                    final String writeHeader = InterHelpBase.writeHeader(value);
                    if (ubAppServerMsg.getubVer() >= 109 && writeHeader != null) {
                        ubAppServerMsg.appendTlvField((short)13, writeHeader);
                    }
                }
                catch (ubMsg.TlvFieldAlreadyExistsException ex2) {}
                catch (Exception ex3) {}
                value.requestAnalyzed();
            }
            if (ubAppServerMsg.getubVer() >= 109) {
                this.addASKRequest(ubAppServerMsg, ubAppServerMsg.getubRq() != 5, serverConnection);
            }
            serverConnection.lock(true);
            serverConnection.write(ubAppServerMsg, true);
            if (ubAppServerMsg.getubRq() == 7) {
                ubMsg = this.newClientEmptyRsp(0, "Sending an empty client rsp message in processRequest() ");
                this.setContentLength(httpServletResponse, ubMsg);
                objectHolder.setObject(new Integer(3));
                return (ubAppServerMsg)ubMsg;
            }
            if (ubAppServerMsg.getubRq() == 4) {
                ubMsg = this.checkStop(serverConnection);
                if (ubMsg != null) {
                    this.setContentLength(httpServletResponse, ubMsg);
                }
                else {
                    ubMsg = this.newClientEmptyRsp(0, "Sending an empty client rsp message in processRequest(2) ");
                    this.setContentLength(httpServletResponse, ubMsg);
                    objectHolder.setObject(new Integer(3));
                }
                return (ubAppServerMsg)ubMsg;
            }
            ubMsg = this.readRspMsg(httpServletResponse, serverConnection);
            if (ubMsg.getubRq() == 13) {
                this.setContentLength(httpServletResponse, ubMsg);
            }
            int value2 = 1;
            while (ubMsg.getubRq() == 12 || ubMsg.getubRq() == 7) {
                this.sendRsp(httpServletResponse, ubMsg, serverConnection, serverConnection.getConnHdl(), value2);
                value2 = 0;
                ubMsg = this.readRspMsg(httpServletResponse, serverConnection);
            }
            objectHolder.setObject(new Integer(value2));
            return (ubAppServerMsg)ubMsg;
        }
        catch (ubMsg.MsgFormatException ex) {
            this.logerrorConn(serverConnection, "Improperly formatted response received:  " + ex.toString());
            objectHolder.setObject(new Integer(3));
            ubMsg = this.newClientRsp(8, "Error on processRequest() : " + ex.toString());
        }
        catch (IOException obj) {
            this.logerrorConn(serverConnection, "Error processing request:  " + obj);
            objectHolder.setObject(new Integer(3));
            ubMsg = this.newClientRsp(4, "Error on processRequest() : " + obj.toString());
        }
        finally {
            serverConnection.unlock();
        }
        return (ubAppServerMsg)ubMsg;
    }
    
    private ubAppServerMsg processConnectRq(final ServerConnection serverConnection, final ubAppServerMsg ubAppServerMsg, final boolean b) {
        ubAppServerMsg ubAppServerMsg2;
        try {
            serverConnection.connect(this.properties);
            ubAppServerMsg2 = this.connectRq(serverConnection, ubAppServerMsg, b);
        }
        catch (IOException obj) {
            this.logerrorConn(serverConnection, "IOException on processConnectRq() : " + obj + obj.getMessage());
            ubAppServerMsg2 = this.newConnectErrorRsp(4, "Error on processConnectRq() : " + obj.toString());
        }
        if (ubAppServerMsg2.getubRsp() != 0) {
            serverConnection.disconnect();
        }
        return ubAppServerMsg2;
    }
    
    private ubAppServerMsg processDisconnectRq(final ServerConnection serverConnection, final ubAppServerMsg ubAppServerMsg) {
        ubAppServerMsg ubAppServerMsg2;
        try {
            ubAppServerMsg2 = this.disconnectRq(serverConnection, ubAppServerMsg);
        }
        catch (IOException obj) {
            this.logerrorConn(serverConnection, "Error processing Disconnect request : " + obj + " : " + obj.getMessage());
            ubAppServerMsg2 = this.newDisconnectErrorRsp(4, "Error on processDisconnectRq() : " + obj.toString());
        }
        serverConnection.disconnect();
        return ubAppServerMsg2;
    }
    
    private ubAppServerMsg connectRq(final ServerConnection serverConnection, final ubAppServerMsg ubAppServerMsg, final boolean b) throws IOException {
        ubAppServerMsg ubAppServerMsg2;
        try {
            serverConnection.write(ubAppServerMsg, true);
            ubAppServerMsg2 = null;
            while (ubAppServerMsg2 == null) {
                try {
                    ubAppServerMsg2 = (ubAppServerMsg)serverConnection.read();
                    this.m_ASKstate = 1;
                    if (ubAppServerMsg2 != null && ubAppServerMsg2.getubRq() == 22) {
                        if (this.log.ifLogVerbose(16L, 4)) {
                            this.log.logVerbose(4, serverConnection.toString() + " detected ASKPing request");
                        }
                        this.askPingPacket(23, serverConnection);
                        ubAppServerMsg2 = null;
                    }
                    if (ubAppServerMsg2 == null || ubAppServerMsg2.getubRq() != 23) {
                        continue;
                    }
                    if (this.log.ifLogVerbose(16L, 4)) {
                        this.log.logVerbose(4, serverConnection.toString() + " detected ASKPing reply");
                    }
                    ubAppServerMsg2 = null;
                }
                catch (InterruptedIOException ex2) {}
            }
        }
        catch (ubMsg.MsgFormatException ex) {
            this.logerrorConn(serverConnection, "CONNECT MsgFormatException:  " + ex.getMessage() + ":" + ex.getDetail());
            return this.newConnectErrorRsp(8, "Error on connectRq() : " + ex.toString());
        }
        final int getubRsp = ubAppServerMsg2.getubRsp();
        if (getubRsp == 0 && ubAppServerMsg2.getMsgcode() == 11) {
            if (ubAppServerMsg2.get4GLCondCode() != 0) {
                this.logerrorConn(serverConnection, "CONNECT failure(Open4GLCondCode)= " + ubAppServerMsg2.get4GLCondCode());
                ubAppServerMsg2.setubRsp(11);
                if (this.properties.actionalEnabled && !serverConnection.isStateFree()) {
                    final ClientInteraction value = ClientInteraction.get();
                    ((InteractionStub)value).setFailure("CONNECT failure(Open4GLCondCode)= " + ubAppServerMsg2.get4GLCondCode());
                    this.logInteraction((Interaction)value);
                    ((Interaction)value).end();
                }
                return ubAppServerMsg2;
            }
            if (b) {
                final int buflen = ubAppServerMsg2.getBuflen();
                final int n = ((buflen < 5) ? 0 : ubMsg.getNetShort(ubAppServerMsg2.getMsgbuf(), 3)) + 14;
                serverConnection.setConnID((buflen > n) ? ubAppServerMsg.getNetString(ubAppServerMsg2.getMsgbuf(), n) : null);
                serverConnection.setServerPort(ubAppServerMsg2.getubRspExt() >> 16 & 0xFFFF);
                if (serverConnection.getServerPort() == 0) {
                    ubAppServerMsg2.set4GLConnAckFlags((byte)(ubAppServerMsg2.get4GLConnAckFlags() & 0xFFFFFFFD));
                }
                serverConnection.negotiateAskCapabilities(ubAppServerMsg2);
                ubAppServerMsg2.remTlvField_NoThrow((short)10);
                ubAppServerMsg2.remTlvField_NoThrow((short)11);
            }
            else {
                ubAppServerMsg2.set4GLConnAckFlags((byte)(ubAppServerMsg2.get4GLConnAckFlags() & 0xFFFFFFFD));
            }
        }
        else {
            this.logerrorConn(serverConnection, "CONNECT failure= (" + getubRsp + "," + ubAppServerMsg2.getMsgcode() + ")");
        }
        return ubAppServerMsg2;
    }
    
    private ubAppServerMsg disconnectRq(final ServerConnection serverConnection, final ubAppServerMsg ubAppServerMsg) throws IOException {
        ubMsg disconnectErrorRsp = null;
        try {
            if (this.properties.actionalEnabled && !serverConnection.isStateFree()) {
                final ClientInteraction value = ClientInteraction.get();
                ((InteractionStub)value).setUrl(serverConnection.getActionalUrl());
                ((InteractionStub)value).setServiceName(this.CIServiceName);
                ((InteractionStub)value).setPeerAddr(serverConnection.getHost());
                try {
                    final String writeHeader = InterHelpBase.writeHeader(value);
                    if (ubAppServerMsg.getubVer() >= 109 && writeHeader != null) {
                        ubAppServerMsg.appendTlvField((short)13, writeHeader);
                    }
                }
                catch (ubMsg.TlvFieldAlreadyExistsException ex2) {}
                catch (Exception ex3) {}
                value.requestAnalyzed();
            }
            serverConnection.lock(true);
            serverConnection.write(ubAppServerMsg, true);
            disconnectErrorRsp = null;
            while (disconnectErrorRsp == null) {
                try {
                    disconnectErrorRsp = serverConnection.read();
                    this.m_ASKstate = 1;
                    if (disconnectErrorRsp != null && disconnectErrorRsp.getubRq() == 22) {
                        if (this.log.ifLogVerbose(16L, 4)) {
                            this.log.logVerbose(4, serverConnection.toString() + " detected ASKPing request");
                        }
                        this.askPingPacket(23, serverConnection);
                        disconnectErrorRsp = null;
                    }
                    if (disconnectErrorRsp == null || disconnectErrorRsp.getubRq() != 23) {
                        continue;
                    }
                    if (this.log.ifLogVerbose(16L, 4)) {
                        this.log.logVerbose(4, serverConnection.toString() + " detected ASKPing reply");
                    }
                    disconnectErrorRsp = null;
                }
                catch (InterruptedIOException ex4) {}
            }
        }
        catch (ubMsg.MsgFormatException ex) {
            this.logerrorConn(serverConnection, "Improperly formatted Disconnect response received:  " + ex.getMessage() + ":" + ex.getDetail());
            disconnectErrorRsp = this.newDisconnectErrorRsp(8, "Error on disconnectRq() : " + ex.toString());
        }
        finally {
            serverConnection.unlock();
        }
        return (ubAppServerMsg)disconnectErrorRsp;
    }
    
    private ubAppServerMsg readRspMsg(final HttpServletResponse httpServletResponse, final ServerConnection serverConnection) throws IOException, ubMsg.MsgFormatException {
        ubAppServerMsg ubAppServerMsg = null;
        while (ubAppServerMsg == null) {
            try {
                ubAppServerMsg = (ubAppServerMsg)serverConnection.read();
                this.m_ASKstate = 1;
                if (ubAppServerMsg != null && ubAppServerMsg.getubRq() == 22) {
                    if (this.log.ifLogVerbose(16L, 4)) {
                        this.log.logVerbose(4, serverConnection.toString() + " detected ASKPing request");
                    }
                    this.askPingPacket(23, serverConnection);
                    ubAppServerMsg = null;
                }
                if (ubAppServerMsg == null || ubAppServerMsg.getubRq() != 23) {
                    continue;
                }
                if (this.log.ifLogVerbose(16L, 4)) {
                    this.log.logVerbose(4, serverConnection.toString() + " detected ASKPing reply");
                }
                ubAppServerMsg = null;
                this.m_ASKrqstACKsent = false;
            }
            catch (InterruptedIOException ex) {
                if (this.log.ifLogVerbose(8L, 3)) {
                    this.log.logVerbose(3, "readRspMsg() timed out : " + ex.toString());
                }
                final ubAppServerMsg keepAliveRsp = this.newKeepAliveRsp(0);
                if (this.log.ifLogVerbose(8L, 3)) {
                    this.log.logVerbose(3, "readRspMsg() sending keepAlive packet");
                }
                this.sendRsp(httpServletResponse, keepAliveRsp, serverConnection, serverConnection.getConnHdl(), 2);
                this.newAskEvent(serverConnection);
            }
        }
        return ubAppServerMsg;
    }
    
    private ubAppServerMsg nameserverLookup(final ServerConnection serverConnection, final String s) {
        ubAppServerMsg ubAppServerMsg;
        if (this.properties.minNSClientPort == 0 || this.properties.minNSClientPort < this.properties.maxNSClientPort) {
            ubAppServerMsg = this.nsLookup(serverConnection, s);
        }
        else {
            synchronized (this.nsLock) {
                ubAppServerMsg = this.nsLookup(serverConnection, s);
            }
        }
        return ubAppServerMsg;
    }
    
    private ubAppServerMsg nsLookup(final ServerConnection serverConnection, final String s) {
        final ubAppServerMsg ubAppServerMsg = null;
        NameServerClient.Broker broker;
        try {
            broker = new NameServerClient(serverConnection.getHost(), serverConnection.getPort(), s, this.properties.minNSClientPort, this.properties.maxNSClientPort, this.properties.nsClientPortRetryInterval, this.properties.nsClientPortRetry).getBroker(serverConnection.getAppService());
        }
        catch (ConnectException ex) {
            this.logerrorConn(serverConnection, "Error looking up " + serverConnection.getAppService() + " : " + ex + " : " + ex.getMessage());
            return this.newConnectErrorRsp(1, "Error looking up " + serverConnection.getAppService() + " : " + ex + " : " + ex.getMessage());
        }
        if (broker != null) {
            serverConnection.setHost(broker.getHost());
            serverConnection.setPort(broker.getPort());
            if (this.log.ifLogVerbose(8L, 3)) {
                this.logtxtConn(serverConnection, 3, "nameserverLookup() : brokerhost= " + broker.getHost() + " brokerport= " + broker.getPort());
            }
        }
        return ubAppServerMsg;
    }
    
    private ubAppServerMsg newConnectErrorRsp(final int n, final String s) {
        final ubAppServerMsg ubAppServerMsg = new ubAppServerMsg((short)108, 11, 0, 0, 0);
        ubAppServerMsg.setubSrc(6);
        ubAppServerMsg.setubRq(14);
        ubAppServerMsg.setubRsp(n);
        final byte[] csmssgRspbuf = com.progress.ubroker.util.ubAppServerMsg.csmssgRspbuf(1, 0, s);
        ubAppServerMsg.setMsgbuf(csmssgRspbuf, csmssgRspbuf.length);
        ubAppServerMsg.setCsHeaders(0, ubAppServerMsg.getBuflen(), 11);
        return ubAppServerMsg;
    }
    
    private ubAppServerMsg newDisconnectErrorRsp(final int n, final String s) {
        final ubAppServerMsg ubAppServerMsg = new ubAppServerMsg((short)108, 21, 0, 0, 0);
        ubAppServerMsg.setubSrc(6);
        ubAppServerMsg.setubRq(15);
        ubAppServerMsg.setubRsp(n);
        final byte[] csmssgRspbuf = com.progress.ubroker.util.ubAppServerMsg.csmssgRspbuf(1, 0, s);
        ubAppServerMsg.setMsgbuf(csmssgRspbuf, csmssgRspbuf.length);
        ubAppServerMsg.setCsHeaders(0, ubAppServerMsg.getBuflen(), 21);
        return ubAppServerMsg;
    }
    
    private ubAppServerMsg newClientRsp(final int n, final String s) {
        final ubAppServerMsg ubAppServerMsg = new ubAppServerMsg((short)108, 70, 0, 0, 0);
        ubAppServerMsg.setubSrc(6);
        ubAppServerMsg.setubRq(13);
        if (s != null) {
            final byte[] csmssgRspbuf = com.progress.ubroker.util.ubAppServerMsg.csmssgRspbuf(0, 0, s);
            ubAppServerMsg.setMsgbuf(csmssgRspbuf, csmssgRspbuf.length);
            ubAppServerMsg.setCsHeaders(0, csmssgRspbuf.length, 70);
        }
        ubAppServerMsg.setubRsp(n);
        return ubAppServerMsg;
    }
    
    private ubAppServerMsg newClientEmptyRsp(final int n, final String s) {
        final ubAppServerMsg ubAppServerMsg = new ubAppServerMsg((short)108, 70, 0, 0, 0);
        ubAppServerMsg.setubSrc(6);
        if (s != null) {
            final byte[] csmssgRspbuf = com.progress.ubroker.util.ubAppServerMsg.csmssgRspbuf(0, 0, s);
            ubAppServerMsg.setMsgbuf(csmssgRspbuf, csmssgRspbuf.length);
            ubAppServerMsg.setCsHeaders(0, csmssgRspbuf.length, 70);
        }
        ubAppServerMsg.setubRq(20);
        ubAppServerMsg.setubRsp(n);
        return ubAppServerMsg;
    }
    
    private ubAppServerMsg newKeepAliveRsp(final int n) {
        final ubAppServerMsg ubAppServerMsg = new ubAppServerMsg((short)108, 70, 0, 0, 0);
        ubAppServerMsg.setubSrc(6);
        ubAppServerMsg.setubRq(12);
        ubAppServerMsg.setubRsp(n);
        return ubAppServerMsg;
    }
    
    void trimConnectionID(final ServerConnection serverConnection, final ubAppServerMsg ubAppServerMsg) {
        final byte[] msgbuf = ubAppServerMsg.getMsgbuf();
        final int buflen = ubAppServerMsg.getBuflen() - 2;
        if (buflen > 1 && msgbuf[buflen] == 0 && msgbuf[buflen + 1] == 0) {
            ubAppServerMsg.setBuflen(buflen);
            ubAppServerMsg.setMsglen(buflen + 4);
        }
        else {
            this.logerrorConn(serverConnection, "Improperly formatted Connection request");
            if (this.log.ifLogVerbose(8L, 3)) {
                this.log.logDump(3, 3, "trimConnectionID() error : ", ubAppServerMsg.getMsgbuf(), ubAppServerMsg.getBuflen());
            }
        }
    }
    
    void appendConnectionID(final String s, final ubAppServerMsg ubAppServerMsg) {
        final byte[] netByteArray = ubAppServerMsg.newNetByteArray(s);
        ubAppServerMsg.appendMsgbuf(netByteArray, netByteArray.length);
        ubAppServerMsg.setMsglen(ubAppServerMsg.getBuflen() + 4);
    }
    
    private String trimAppServiceName(final ubAppServerMsg ubAppServerMsg) {
        final byte[] msgbuf = ubAppServerMsg.getMsgbuf();
        int skipNetString = 0;
        for (int i = 0; i < 5; ++i) {
            skipNetString = ubAppServerMsg.skipNetString(msgbuf, skipNetString);
        }
        final String netString = ubAppServerMsg.getNetString(msgbuf, skipNetString);
        ubAppServerMsg.setBuflen(skipNetString);
        ubAppServerMsg.setMsglen(skipNetString + 4);
        return netString;
    }
    
    private String extractUserName(final ubAppServerMsg ubAppServerMsg) {
        final byte[] msgbuf = ubAppServerMsg.getMsgbuf();
        String netString = ubAppServerMsg.getNetString(msgbuf, ubAppServerMsg.skipNetString(msgbuf, 0));
        if (netString == null) {
            netString = "";
        }
        return netString;
    }
    
    private void parseConnectMsg(final ubAppServerMsg ubAppServerMsg, final ObjectHolder objectHolder, final ObjectHolder objectHolder2, final ObjectHolder objectHolder3) {
        final byte[] msgbuf = ubAppServerMsg.getMsgbuf();
        int skipNetString = 0;
        for (int i = 0; i < 6; ++i) {
            String netString = ubAppServerMsg.getNetString(msgbuf, skipNetString);
            if (netString == null) {
                netString = "";
            }
            switch (i) {
                case 1: {
                    objectHolder.setObject(netString);
                    break;
                }
                case 3: {
                    objectHolder2.setObject(netString);
                    break;
                }
                case 5: {
                    objectHolder3.setObject(netString);
                    ubAppServerMsg.setBuflen(skipNetString);
                    ubAppServerMsg.setMsglen(skipNetString + 4);
                    break;
                }
            }
            skipNetString = ubAppServerMsg.skipNetString(msgbuf, skipNetString);
        }
    }
    
    private boolean parseArbString(final String s, final ObjectHolder objectHolder, final ObjectHolder objectHolder2) {
        boolean b = false;
        final int length = s.length();
        if (s == null) {
            return false;
        }
        final int index = s.indexOf(":", 0);
        if (index == -1) {
            return false;
        }
        if (!s.substring(0, index).equals("__secondconn")) {
            return false;
        }
        int index2;
        for (int n = index + 1, n2 = 1; n2 <= 3 && n < length; ++n2, n = index2 + 2) {
            index2 = s.indexOf("::", n);
            if (index2 == -1) {
                return false;
            }
            String substring = s.substring(n, index2);
            if (substring == null) {
                substring = "";
            }
            switch (n2) {
                case 0: {
                    if (substring.equals("__secondconn")) {
                        break;
                    }
                    return false;
                }
                case 1: {
                    objectHolder.setObject(substring);
                    break;
                }
                case 3: {
                    try {
                        objectHolder2.setObject(new Integer(substring));
                        b = true;
                    }
                    catch (NumberFormatException ex) {
                        b = false;
                    }
                    break;
                }
            }
        }
        return b;
    }
    
    private void setContentLength(final HttpServletResponse httpServletResponse, final ubMsg ubMsg) {
        ((ServletResponse)httpServletResponse).setContentLength((ubMsg == null) ? 0 : ubMsg.serializeMsg().length);
    }
    
    private void startConnectionWatchdog() {
        if (this.properties.connTrimTimeout > 0) {
            (this.connectionWatchdog = new ubWatchDog("connectionWatchdog", this, this.properties.connTrimTimeout, 6, this.log)).start();
        }
    }
    
    private ubAppServerMsg checkStop(final ServerConnection serverConnection) throws ubMsg.MsgFormatException, IOException {
        ubMsg ubMsg = null;
        if (serverConnection.available() > 0) {
            ubMsg = serverConnection.read();
            this.m_ASKstate = 1;
            if (ubMsg.getubRq() == 22) {
                if (this.log.ifLogVerbose(16L, 4)) {
                    this.log.logVerbose(4, serverConnection.toString() + " Server sent an ASKPING request");
                }
                this.askPingPacket(23, serverConnection);
                return null;
            }
            if (ubMsg.getubRq() == 23) {
                if (this.log.ifLogVerbose(16L, 4)) {
                    this.log.logVerbose(4, serverConnection.toString() + " Server sent an ASKPing reply");
                }
                this.m_ASKrqstACKsent = false;
                return null;
            }
            if (this.log.ifLogVerbose(8L, 3)) {
                this.log.logVerbose(3, "Server sent an unexpected " + ubMsg.getubRqDesc() + " response.");
            }
        }
        if (this.log.ifLogVerbose(8L, 3)) {
            this.log.logVerbose(3, "checkStop returned a " + ((ubMsg == null) ? "null" : ubMsg.getubRqDesc()) + " response.");
        }
        return (ubAppServerMsg)ubMsg;
    }
    
    private boolean isIPv6AddrAndNeedsBraces(final String s) {
        return s.indexOf(58) != s.lastIndexOf(58) && s.charAt(0) != '[';
    }
    
    private boolean askValidateProtocolType(final ServerConnection serverConnection) {
        if (serverConnection.isSSLEnabled()) {
            this.log.logError("ASK Protocol is not supported with SSL protocol.");
            return false;
        }
        if ((this.properties.clntAskCaps & 0x2) != 0x0) {
            this.properties.clntAskCaps &= 0xFFFFFFFD;
            this.log.logError("ClientASK Protocol is not supported - ClientASK disabled.");
        }
        return true;
    }
    
    private String formatAskVersion(final int n) {
        return (n >> 16 & 0xFFFF) + "." + (n & 0xFFFF);
    }
    
    private void askPingPacket(final int i, final ServerConnection serverConnection) {
        final ubAppServerMsg ubAppServerMsg = new ubAppServerMsg((short)108, 70, 0, 0, 0);
        final String str = (i == 22) ? " sending ASKPing request" : ((i == 23) ? " sending ASKPing response" : (" (rqCode=" + i + ")"));
        ubAppServerMsg.setubSrc(3);
        ubAppServerMsg.setubRq(i);
        try {
            if (this.log.ifLogVerbose(16L, 4)) {
                this.log.logVerbose(4, serverConnection.toString() + str);
            }
            serverConnection.write(ubAppServerMsg, true);
        }
        catch (IOException ex) {
            this.log.logError(ex.getMessage());
        }
    }
    
    private void addASKRequest(final ubAppServerMsg ubAppServerMsg, final boolean asKrqstACKsent, final ServerConnection serverConnection) {
        final long n = System.currentTimeMillis() - serverConnection.getTsLastAccess();
        if (serverConnection.getClientASKEnabled() && !this.m_ASKrqstACKsent && n > this.m_clientASKActivityTimeoutMs) {
            if (this.log.ifLogVerbose(16L, 4)) {
                this.log.logVerbose(4, serverConnection.toString() + " ASKPING request added to message");
            }
            try {
                ubAppServerMsg.appendTlvField((short)12, Integer.toString(this.m_clientASKResponseTimeoutMs / 1000));
                this.m_ASKstate = 2;
            }
            catch (Exception ex) {
                this.log.logError("appendTlvField Exception: " + ex.getMessage());
            }
        }
        this.m_ASKrqstACKsent = asKrqstACKsent;
    }
    
    private boolean newAskEvent(final ServerConnection serverConnection) {
        boolean b = false;
        if (serverConnection.getClientASKEnabled()) {
            final long n = System.currentTimeMillis() - serverConnection.getTsLastAccess();
            switch (this.m_ASKstate) {
                case 1: {
                    if (n > this.m_clientASKActivityTimeoutMs) {
                        b = this.processASKActivityTimeout(serverConnection);
                        break;
                    }
                    break;
                }
                case 2: {
                    if (n > this.m_clientASKResponseTimeoutMs) {
                        b = this.processASKResponseTimeout(serverConnection);
                        break;
                    }
                    break;
                }
            }
        }
        return b;
    }
    
    private boolean processASKActivityTimeout(final ServerConnection serverConnection) {
        if (this.log.ifLogVerbose(16L, 4)) {
            this.log.logVerbose(4, serverConnection.toString() + " No messages have been received from the server within " + "the clientASK Activity Timeout period ... issuing an " + "ASKPing request to the server.");
        }
        this.m_ASKstate = 2;
        this.m_ASKrqstACKsent = true;
        this.askPingPacket(22, serverConnection);
        return true;
    }
    
    private boolean processASKResponseTimeout(final ServerConnection serverConnection) {
        serverConnection.disconnect();
        this.connPool.remConnection(serverConnection.getConnHdl());
        this.m_ASKstate = 1;
        this.m_ASKrqstACKsent = false;
        if (this.log.ifLogVerbose(16L, 4)) {
            this.log.logVerbose(4, serverConnection.toString() + " No messages were received from the server within the " + "clientASK Response Timeout interval.  The connection " + " to the server has been terminated.");
        }
        return true;
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
        sb.append("OpenEdge AIA Interceptor ").append(str);
        if (this.log.ifLogVerbose(64L, 6)) {
            sb.append("(").append(((InteractionStub)interaction).getInteractionID()).append(")");
        }
        sb.append(" on thread: ").append(Thread.currentThread().getName());
        sb.append(" ==>");
        sb.append(" PeerAddr: [ " + ((SiteStub)interaction).getPeerAddr());
        sb.append(" ] URL: [ " + ((SiteStub)interaction).getUrl());
        sb.append(" ] Type: [ " + ((SiteStub)interaction).getSvcType());
        if (this.log.ifLogVerbose(64L, 6)) {
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
        if (this.log.ifLogBasic(64L, 6)) {
            this.log.logBasic(6, sb.toString());
        }
    }
    
    private ServerInteraction actionalSetup(final HttpServletRequest httpServletRequest) {
        if (this.properties.actionalEnabled) {
            if (this.log.ifLogVerbose(64L, 6)) {
                this.log.logVerbose(6, "Setting up Actional Interaction");
            }
            final ServerInteraction begin = ServerInteraction.begin();
            ((InteractionStub)begin).setGroupName(this.properties.actionalGroup);
            ((SiteStub)begin).setAppType((short)681);
            ((InteractionStub)begin).setServiceName(this.properties.instanceName);
            ((SiteStub)begin).setSvcType((short)687);
            ((InteractionStub)begin).setOpName("POST");
            ((InteractionStub)begin).setUrl("OpenEdge://" + ((ServletRequest)httpServletRequest).getServerName() + "/AIA/" + this.properties.instanceName);
            ((InteractionStub)begin).setPeerAddr(((ServletRequest)httpServletRequest).getRemoteAddr());
            ((InteractionStub)begin).setSize((long)((ServletRequest)httpServletRequest).getContentLength());
            try {
                InterHelpJ2ee.extractHttpHeaders(begin, httpServletRequest);
            }
            catch (Exception ex) {
                if (this.log.ifLogBasic(64L, 6)) {
                    this.log.logBasic(6, "Exception etracting Actional HTTP header: " + ex.getMessage());
                }
            }
            InterHelpJ2ee.extractTransportHeaders(begin, httpServletRequest);
            ((Interaction)begin).requestAnalyzed();
            this.logInteraction((Interaction)begin);
            final ClientInteraction begin2 = ClientInteraction.begin();
            ((InteractionStub)begin2).setGroupName(this.properties.actionalGroup);
            ((SiteStub)begin2).setAppType((short)681);
            return begin;
        }
        return null;
    }
    
    static {
        DESC_ASKSTATE = new String[] { "ASKSTATE_INIT", "ASKSTATE_ACTIVITY_TIMEOUT", "ASKSTATE_RESPONSE_TIMEOUT" };
    }
}
