// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa.admin;

import java.util.Hashtable;
import org.apache.soap.rpc.RPCMessage;
import org.apache.soap.server.TypeMapping;
import com.progress.wsa.tools.WsaStatusInfo;
import com.progress.wsa.tools.StatusInfo;
import com.progress.wsa.tools.QueryInfo;
import com.progress.wsa.tools.ListInfo;
import org.apache.soap.server.DeploymentDescriptor;
import HTTPClient.HTTPResponse;
import com.progress.ubroker.util.NetworkProtocolOptions;
import HTTPClient.ModuleException;
import HTTPClient.NVPair;
import java.net.InetAddress;
import HTTPClient.Codecs;
import java.io.EOFException;
import java.util.Enumeration;
import org.w3c.dom.Document;
import org.apache.soap.encoding.SOAPMappingRegistry;
import java.io.IOException;
import org.xml.sax.SAXException;
import org.w3c.dom.Node;
import org.apache.soap.Envelope;
import org.apache.soap.SOAPException;
import org.apache.soap.Constants;
import java.io.Reader;
import org.xml.sax.InputSource;
import java.io.StringReader;
import org.apache.soap.util.xml.XMLParserUtils;
import org.apache.soap.util.xml.XMLJavaMappingRegistry;
import java.io.Writer;
import java.io.StringWriter;
import org.apache.soap.rpc.SOAPContext;
import org.apache.soap.server.TypeMappingSerializer;
import com.progress.wsa.xmr.AppCntrSerializer;
import com.progress.wsa.xmr.WSADSerializer;
import org.apache.soap.util.xml.Serializer;
import org.apache.soap.util.xml.Deserializer;
import org.apache.soap.util.xml.QName;
import org.apache.soap.rpc.Call;
import org.apache.soap.encoding.soapenc.BeanSerializer;
import org.apache.soap.rpc.Response;
import java.io.OutputStream;
import com.progress.ubroker.util.NetworkProtocolException;
import com.progress.ubroker.ssl.SSLSocketUtilsFull;
import com.progress.ubroker.ssl.SSLParamsFull;
import com.progress.common.util.crypto;
import com.progress.ubroker.util.ISSLSocketUtils;
import com.progress.ubroker.util.ISSLParams;
import com.progress.ubroker.util.Logger;
import com.progress.common.util.PasswordString;
import java.util.Properties;
import java.util.Vector;
import HTTPClient.AuthorizationInfo;
import com.progress.common.util.PscURLParser;
import HTTPClient.HTTPConnection;

public class WsaAdminClient
{
    public static final int HTTP_STATE_IDLE = 0;
    public static final int HTTP_STATE_GET = 1;
    public static final int HTTP_STATE_POST = 2;
    public static final int HTTP_STATE_GET_RESPONSE_HEADERS = 3;
    public static final int HTTP_STATE_GET_RESPONSE_DATA = 4;
    public static final String SOAP_ACTION_HEADER_OPTION = "psc.soapheader";
    protected HTTPConnection a;
    protected PscURLParser b;
    protected AuthorizationInfo c;
    protected Vector d;
    protected MessageResponse e;
    protected Properties f;
    protected String g;
    protected PasswordString h;
    protected int i;
    protected Logger j;
    protected int k;
    protected int l;
    protected boolean m;
    protected ISSLParams n;
    protected ISSLSocketUtils o;
    protected ISSLSocketUtils.ISSLInfo p;
    protected Thread q;
    protected String r;
    protected boolean s;
    protected boolean t;
    protected boolean u;
    protected String v;
    protected static final String w = "0";
    
    public WsaAdminClient() {
        this.a = null;
        this.b = null;
        this.c = null;
        this.d = new Vector();
        this.e = null;
        this.f = null;
        this.g = null;
        this.h = null;
        this.i = 0;
        this.j = null;
        this.k = 0;
        this.l = 2;
        this.m = false;
        this.n = null;
        this.o = null;
        this.p = null;
        this.q = null;
        this.r = "http";
        this.s = false;
        this.t = false;
        this.u = false;
        this.v = null;
    }
    
    protected void finalize() throws Throwable {
        try {
            this.disconnect();
        }
        catch (Exception ex) {}
    }
    
    public void setWsaURL(final String v) {
        this.v = v;
    }
    
    public String getWsaURL() {
        return this.v;
    }
    
    public String getConnectPath() {
        if (this.b != null) {
            return this.b.getPath();
        }
        return null;
    }
    
    public void init(final Properties defaults, final Logger j, final int k, final int l) throws Exception {
        this.f = new Properties(defaults);
        this.k = k;
        this.l = l;
        if (null == j) {
            this.j = new Logger();
        }
        else {
            this.j = j;
        }
    }
    
    public String connect(final String s, final String s2, final String s3) throws Exception {
        if (null == s || 0 == s.length()) {
            throw new NullPointerException("Cannot connect with a blank WSA URL");
        }
        this.b = new PscURLParser(s);
        if (this.b.getScheme().equalsIgnoreCase("https")) {
            this.m = true;
            this.r = this.b.getScheme();
        }
        if (null != s2) {
            this.g = new String(new crypto().decrypt(s2));
            if (null != s3) {
                this.h = new PasswordString(new crypto().decrypt(s3));
            }
        }
        this.a = this.newHttpConnection(s);
        if (this.m) {
            try {
                this.n = new SSLParamsFull();
                this.o = new SSLSocketUtilsFull();
            }
            catch (Throwable t) {
                this.j.LogStackTrace(this.k, 1, true, "Missing the SSLParamsFull and/or SSLSocketUtilsFull classes", t);
                throw new NetworkProtocolException(3L, this.r, "");
            }
            this.a.setSSLParams(this.n);
            this.a.setSSLSocketUtils(this.o);
            try {
                this.n.init(this.f, null, 6);
            }
            catch (NetworkProtocolException ex) {
                throw ex;
            }
            catch (Exception ex2) {
                this.j.LogStackTrace(this.k, 1, true, "SSLParamsFull init error.", ex2);
                throw new NetworkProtocolException(2L, this.r, ex2.toString());
            }
            this.b();
        }
        String a;
        try {
            a = this.a();
        }
        catch (Exception ex3) {
            try {
                this.closeHttpConnection(this.a, this);
            }
            catch (Exception ex4) {}
            finally {
                this.a = null;
            }
            throw ex3;
        }
        return a;
    }
    
    public void disconnect() throws Exception {
    }
    
    public boolean isConnected() throws Exception {
        return null != this.a;
    }
    
    public MessageResponse lastHTTPResponse() {
        return this.e;
    }
    
    public Response invokeAdminMethod(final String s, final Vector vector) throws Exception {
        if (this.b.getPath().endsWith("/")) {
            this.v = this.b.getPath() + "admin";
        }
        else {
            this.v = this.b.getPath() + "/admin";
        }
        return this.invokeSOAPMethod(s, vector, "urn:services-progress-com:wsa-admin:0002");
    }
    
    public Response invokeSOAPMethod(final String methodName, final Vector params, final String targetObjectURI) throws Exception {
        final BeanSerializer beanSerializer = new BeanSerializer();
        final Call call = new Call();
        final SOAPMappingRegistry soapMappingRegistry = call.getSOAPMappingRegistry();
        ((XMLJavaMappingRegistry)soapMappingRegistry).mapTypes("http://schemas.xmlsoap.org/soap/encoding/", new QName("http://xml.apache.org/xml-soap", "DeploymentDescriptor"), DeploymentDescriptor.class, (Serializer)beanSerializer, (Deserializer)beanSerializer);
        ((XMLJavaMappingRegistry)soapMappingRegistry).mapTypes("http://schemas.xmlsoap.org/soap/encoding/", new QName("urn:schemas-progress-com:wsa-admin:0002", "ListInfo"), ListInfo.class, (Serializer)beanSerializer, (Deserializer)beanSerializer);
        ((XMLJavaMappingRegistry)soapMappingRegistry).mapTypes("http://schemas.xmlsoap.org/soap/encoding/", new QName("urn:schemas-progress-com:wsa-admin:0002", "QueryInfo"), QueryInfo.class, (Serializer)beanSerializer, (Deserializer)beanSerializer);
        ((XMLJavaMappingRegistry)soapMappingRegistry).mapTypes("http://schemas.xmlsoap.org/soap/encoding/", new QName("urn:schemas-progress-com:wsa-admin:0002", "StatusInfo"), StatusInfo.class, (Serializer)beanSerializer, (Deserializer)beanSerializer);
        ((XMLJavaMappingRegistry)soapMappingRegistry).mapTypes("http://schemas.xmlsoap.org/soap/encoding/", new QName("urn:schemas-progress-com:wsa-admin:0002", "WsaStatusInfo"), WsaStatusInfo.class, (Serializer)beanSerializer, (Deserializer)beanSerializer);
        final WSADSerializer wsadSerializer = new WSADSerializer();
        ((XMLJavaMappingRegistry)soapMappingRegistry).mapTypes("http://schemas.xmlsoap.org/soap/encoding/", new QName("urn:schemas-progress-com:WSAD:0009", "WSADType"), WSAD.class, (Serializer)wsadSerializer, (Deserializer)wsadSerializer);
        final AppCntrSerializer appCntrSerializer = new AppCntrSerializer();
        ((XMLJavaMappingRegistry)soapMappingRegistry).mapTypes("http://schemas.xmlsoap.org/soap/encoding/", new QName("urn:schemas-progress-com:WSAD:0009", "AppContainerType"), AppContainer.class, (Serializer)appCntrSerializer, (Deserializer)appCntrSerializer);
        final TypeMappingSerializer typeMappingSerializer = new TypeMappingSerializer();
        ((XMLJavaMappingRegistry)soapMappingRegistry).mapTypes("http://schemas.xmlsoap.org/soap/encoding/", new QName("http://xml.apache.org/xml-soap", "TypeMapping"), TypeMapping.class, (Serializer)typeMappingSerializer, (Deserializer)typeMappingSerializer);
        ((RPCMessage)call).setTargetObjectURI(targetObjectURI);
        ((RPCMessage)call).setMethodName(methodName);
        ((RPCMessage)call).setEncodingStyleURI("http://schemas.xmlsoap.org/soap/encoding/");
        ((RPCMessage)call).setParams(params);
        Response fromEnvelope;
        try {
            final SOAPContext soapContext = new SOAPContext();
            final SOAPContext soapContext2 = new SOAPContext();
            final Envelope buildEnvelope = call.buildEnvelope();
            final StringWriter stringWriter = new StringWriter();
            buildEnvelope.marshall((Writer)stringWriter, (XMLJavaMappingRegistry)soapMappingRegistry, soapContext);
            this.sendMessage(stringWriter.toString());
            final String s = new String(this.e.m_httpResponseData);
            this.e.m_httpResponseData = null;
            final Document parse = XMLParserUtils.getXMLDocBuilder().parse(new InputSource(new StringReader(s)));
            if (parse == null) {
                throw new SOAPException(Constants.FAULT_CODE_CLIENT, "Parsing error, response was:\n" + s);
            }
            fromEnvelope = Response.extractFromEnvelope(Envelope.unmarshall((Node)parse.getDocumentElement(), soapContext2), soapMappingRegistry, soapContext2);
        }
        catch (IllegalArgumentException ex) {
            throw new SOAPException(Constants.FAULT_CODE_CLIENT, ex.getMessage(), (Throwable)ex);
        }
        catch (SAXException ex2) {
            throw new SOAPException(Constants.FAULT_CODE_CLIENT, "Parsing error, response was:\n" + ex2.getMessage(), (Throwable)ex2);
        }
        catch (IOException ex3) {
            throw new SOAPException(Constants.FAULT_CODE_PROTOCOL, ex3.getMessage(), (Throwable)ex3);
        }
        if (!fromEnvelope.generatedFault() && this.e.m_server_internal_error) {
            final Vector vector = new Vector<String>();
            vector.addElement(new String(this.r));
            vector.addElement(new String(this.b.getHost() + Integer.toString(this.b.getPort())));
            vector.addElement(new String("HTTP server error (500): Internal error"));
            this.e.m_sendMsgExceptionMsgCode = 8;
            vector.copyInto(this.e.m_sendMsgExceptionErrorDetail = new Object[vector.size()]);
            this.i = 0;
            this.q = null;
            final NetworkProtocolException ex4 = new NetworkProtocolException(this.e.m_sendMsgExceptionMsgCode, this.e.m_sendMsgExceptionErrorDetail);
            this.j.LogStackTrace(this.k, 1, true, "", ex4);
            throw ex4;
        }
        return fromEnvelope;
    }
    
    public boolean pingWSA() throws Exception {
        return false;
    }
    
    public boolean pingApplication(final String s) throws Exception {
        return false;
    }
    
    public boolean testConnection() throws Exception {
        boolean b = false;
        try {
            this.a();
            b = true;
        }
        catch (Exception ex) {}
        return b;
    }
    
    public void closeHttpConnection(final HTTPConnection httpConnection, final Object o) throws Exception {
        final HTTPConnection httpConnection2 = (null == httpConnection) ? this.a : httpConnection;
        if (null != o && !this.d.isEmpty()) {
            final Enumeration<AuthorizationInfo> elements = this.d.elements();
            while (elements.hasMoreElements()) {
                AuthorizationInfo.removeAuthorization(elements.nextElement(), o);
            }
        }
        if (null != this.c) {
            AuthorizationInfo.removeAuthorization(this.c, o);
        }
        if (null != httpConnection2) {
            try {
                httpConnection2.stop();
            }
            catch (Exception ex) {
                this.j.LogMsgln(this.k, 5, false, "Error stopping http connection: " + ex.toString());
                throw ex;
            }
        }
    }
    
    public synchronized void sendMessage(final String s) throws IOException, NetworkProtocolException {
        if (null == this.a) {
            throw new IOException("No HTTP connection to send message to.");
        }
        try {
            this.e = this.a(this.a, s);
            this.a(this.a, this.e);
        }
        catch (EOFException ex2) {
            try {
                this.closeHttpConnection(this.a, this);
            }
            catch (Exception ex8) {}
            final NetworkProtocolException ex = new NetworkProtocolException(8L, this.r, ex2.getMessage());
            this.j.LogStackTrace(this.k, 1, true, "", ex);
            throw ex;
        }
        catch (psc_b psc_b) {
            final int a = this.a(psc_b.b());
            if (-1 != a) {
                this.u = true;
                final NetworkProtocolException ex3 = new NetworkProtocolException(a, this.r, psc_b.a(psc_b.b()));
                this.j.LogStackTrace(this.k, 3, true, "", ex3);
                try {
                    this.closeHttpConnection(this.a, this);
                }
                catch (Exception ex9) {}
                throw ex3;
            }
            this.j.LogMsgln(this.k, 5, true, "SSL server reported error: " + psc_b.a(psc_b.b()));
        }
        catch (psc_c psc_c) {
            final int b = this.b(psc_c.b());
            if (-1 != b) {
                this.u = true;
                final NetworkProtocolException ex4 = new NetworkProtocolException(b, this.r, psc_c.getMessage());
                this.j.LogStackTrace(this.k, 1, true, "", ex4);
                try {
                    this.closeHttpConnection(this.a, this);
                }
                catch (Exception ex10) {}
                throw ex4;
            }
            this.j.LogMsgln(this.k, 3, true, "SSL reported error: " + psc_c.getMessage());
        }
        catch (IOException ex5) {
            this.j.LogMsgln(this.k, 5, true, "Detected I/O error posting (write) message: " + ex5.getMessage());
            throw ex5;
        }
        catch (NetworkProtocolException ex6) {
            throw ex6;
        }
        catch (Exception ex7) {
            this.j.LogStackTrace(this.k, 1, true, "", ex7);
            throw new NetworkProtocolException(10L, this.r, ex7.toString());
        }
    }
    
    public boolean isProxyAuthFailure() {
        return this.s;
    }
    
    public boolean isWebServerAuthFailure() {
        return this.t;
    }
    
    public boolean isSSLConnectFailure() {
        return this.u;
    }
    
    protected HTTPConnection a(final Object context) throws NetworkProtocolException {
        HTTPConnection httpConnection;
        try {
            if (!this.j.ignore(5)) {
                this.j.LogMsgln(this.k, 5, false, "Connecting to HTTP server: " + this.b.toString());
            }
            httpConnection = new HTTPConnection(this.b.getScheme(), this.b.getHost(), this.b.getPort());
            httpConnection.setContext(context);
            httpConnection.removeModule(Class.forName("HTTPClient.CookieModule"));
        }
        catch (Exception ex2) {
            final NetworkProtocolException ex = new NetworkProtocolException(4L, this.r, ex2.toString());
            this.j.LogStackTrace(this.k, 1, true, "", ex);
            throw ex;
        }
        return httpConnection;
    }
    
    public HTTPConnection newHttpConnection(final Object o) throws NetworkProtocolException {
        HTTPConnection a;
        try {
            a = this.a(o);
            this.a(a, o);
            this.b(a, o);
            this.c(a, o);
        }
        catch (NetworkProtocolException ex2) {
            try {
                this.closeHttpConnection(null, o);
            }
            catch (Exception ex) {
                this.j.LogMsgln(this.k, 5, false, "Error closing http connection: " + ex.toString());
            }
            throw ex2;
        }
        return a;
    }
    
    protected void a(final HTTPConnection httpConnection, final Object o) {
        if (null == this.c) {
            final String str = "";
            String s = this.g;
            StringBuffer obj = (null != this.h) ? new StringBuffer(this.h.toString()) : null;
            final Object o2 = null;
            if (null == s) {
                try {
                    s = this.b.getUserId();
                    if (null != s) {
                        obj = new StringBuffer(this.b.getUserPassword());
                    }
                }
                catch (Exception ex) {
                    this.j.LogMsgln(this.k, 5, false, "Attempt to use a authentication user-id without a password");
                    s = null;
                }
            }
            if (null == o2) {
                this.b.getHost();
            }
            if (null == s && this.f.containsKey("PROGRESS.Session.userId")) {
                s = this.f.getProperty("PROGRESS.Session.userId");
                obj = new StringBuffer(this.f.getProperty("PROGRESS.Session.password"));
            }
            if (null != s) {
                (this.c = new AuthorizationInfo(this.b.getHost(), this.b.getPort(), "Basic", str, Codecs.base64Encode(s + ":" + (Object)obj))).addPath(this.b.getPath());
                if (!this.j.ignore(5)) {
                    this.j.LogMsgln(this.k, 5, false, "Authenticating to HTTP server: " + str + ":" + s + ":xxxxxxx");
                }
                if (obj != null) {
                    for (int i = 0; i < obj.length(); ++i) {
                        obj.setCharAt(i, ' ');
                    }
                }
            }
        }
        if (null != this.c) {
            AuthorizationInfo.addAuthorization(this.c, o);
        }
    }
    
    protected void b(final HTTPConnection httpConnection, final Object o) throws NetworkProtocolException {
        final String property = this.f.getProperty("PROGRESS.Session.proxyHost");
        if (null != property) {
            try {
                final String property2 = this.f.getProperty("PROGRESS.Session.proxyPort");
                if (null == property2) {
                    throw new NetworkProtocolException(2L, this.r, "Bad proxy host:port format");
                }
                if (!this.j.ignore(5)) {
                    this.j.LogMsgln(this.k, 5, false, "Using HTTP Proxy server: " + property + ":" + property2);
                }
                try {
                    InetAddress.getByName(property);
                }
                catch (Exception ex3) {
                    throw new NetworkProtocolException(2L, this.r, "Unknown proxy host " + property);
                }
                httpConnection.setCurrentProxy(property, Integer.parseInt(property2));
                final String property3 = this.f.getProperty("PROGRESS.Session.proxyUserId");
                final String str = "";
                if (null != property3) {
                    final String property4 = this.f.getProperty("PROGRESS.Session.proxyPassword");
                    if (null == property4) {
                        throw new NetworkProtocolException(2L, this.r, "Bad proxy [realm:]uid:pwd format");
                    }
                    final AuthorizationInfo obj = new AuthorizationInfo(property, Integer.parseInt(property2), "Basic", str, Codecs.base64Encode(property3 + ":" + property4));
                    obj.addPath("/");
                    if (!this.j.ignore(5)) {
                        this.j.LogMsgln(this.k, 5, false, "Authenticating to HTTP Proxy server: " + ((null == str) ? "" : (str + ":")) + property3 + ":xxxxxxx");
                    }
                    if (this == o) {
                        this.d.addElement(obj);
                    }
                }
            }
            catch (NetworkProtocolException ex) {
                this.j.LogMsgln(this.k, 5, false, "Invalid proxy option: " + property + " (" + ex.getMessage() + ")");
                throw ex;
            }
        }
        if (!this.d.isEmpty()) {
            final Enumeration<AuthorizationInfo> elements = this.d.elements();
            while (elements.hasMoreElements()) {
                AuthorizationInfo.addAuthorization(elements.nextElement(), o);
            }
        }
        try {
            HTTPConnection.dontProxyFor("localhost");
            HTTPConnection.dontProxyFor("127.0.0.1");
        }
        catch (Exception ex2) {
            throw new NetworkProtocolException(2L, this.r, "Failure removing localhost from proxy list: " + ex2.toString());
        }
    }
    
    protected void c(final HTTPConnection httpConnection, final Object o) throws NetworkProtocolException {
        httpConnection.setAllowUserInteraction(false);
        String property = this.f.getProperty("PROGRESS.Session.httpTimeout");
        if (null == property) {
            property = "0";
        }
        try {
            int timeout = Integer.parseInt(property);
            if (0 > timeout) {
                timeout = Integer.parseInt("0");
                if (!this.j.ignore(5)) {
                    this.j.LogMsgln(this.k, 5, false, "ignoring negative timeout, setting default http timeout option: 0");
                }
            }
            else if (!this.j.ignore(5)) {
                this.j.LogMsgln(this.k, 5, false, "setting http timeout option: " + property);
            }
            if (timeout != 0) {
                timeout *= 1000;
            }
            httpConnection.setTimeout(timeout);
        }
        catch (Exception ex) {
            this.j.LogMsgln(this.k, 5, false, "Invalid http timeout option: " + property);
        }
        final NVPair[] defaultHeaders = new NVPair[4];
        int n = 0;
        defaultHeaders[n++] = new NVPair("User-Agent", "Progress WSA Client");
        defaultHeaders[n++] = new NVPair("Accept", "text/xml, text/html");
        defaultHeaders[n++] = new NVPair("Proxy-Connection", "Keep-Alive");
        defaultHeaders[n++] = new NVPair("Content-Type", "text/xml; charset=utf-8");
        httpConnection.setDefaultHeaders(defaultHeaders);
    }
    
    protected MessageResponse a(final HTTPConnection httpConnection, final String s) throws IOException, NetworkProtocolException {
        final MessageResponse messageResponse = new MessageResponse();
        final byte[] array = (byte[])((null != s) ? s.getBytes() : null);
        try {
            if (null == array) {
                if (!this.j.ignore(5)) {
                    this.j.LogMsgln(this.k, 5, true, "GET message from wsa.");
                }
                this.i = 1;
                this.q = Thread.currentThread();
                messageResponse.m_msgResponse = httpConnection.Get(this.b.getPath());
                if (!this.j.ignore(5)) {
                    this.j.LogMsgln(this.k, 5, true, "GET operation complete.");
                }
            }
            else {
                String s2 = ((Hashtable<K, String>)this.f).get("psc.soapheader");
                if (null == s2) {
                    s2 = "";
                }
                final NVPair[] array2 = { new NVPair("SOAPAction", s2) };
                if (!this.j.ignore(5)) {
                    this.j.LogMsgln(this.k, 5, true, "POST message to wsa, " + array.length + " bytes sent.");
                }
                this.i = 2;
                this.q = Thread.currentThread();
                messageResponse.m_msgResponse = httpConnection.Post(this.v, array, array2);
                if (!this.j.ignore(5)) {
                    this.j.LogMsgln(this.k, 5, true, "POST " + messageResponse.m_httpOperationName + " operation complete.");
                }
            }
            this.i = 3;
        }
        catch (ModuleException ex2) {
            this.i = 0;
            this.q = null;
            final NetworkProtocolException ex = new NetworkProtocolException(0L, this.r, ex2.toString());
            this.j.LogStackTrace(this.k, 1, true, "", ex);
            throw ex;
        }
        catch (Exception ex3) {
            this.j.LogStackTrace(this.k, 1, true, "General Exception in sendHttpMessage()", ex3);
            throw new NetworkProtocolException(0L, this.r, ex3.toString());
        }
        return messageResponse;
    }
    
    protected void a(final HTTPConnection httpConnection, final MessageResponse messageResponse) throws IOException, NetworkProtocolException {
        try {
            messageResponse.m_sendMsgResponseCode = messageResponse.m_msgResponse.getStatusCode();
            if (!this.j.ignore(5)) {
                this.j.LogMsgln(this.k, 5, true, "Obtained " + messageResponse.m_httpOperationName + " response for message from wsa: " + Integer.toString(messageResponse.m_sendMsgResponseCode));
            }
            if (this.a(messageResponse)) {
                this.i = 0;
                this.q = null;
                final NetworkProtocolException ex = new NetworkProtocolException(messageResponse.m_sendMsgExceptionMsgCode, messageResponse.m_sendMsgExceptionErrorDetail);
                this.j.LogStackTrace(this.k, 1, true, "", ex);
                throw ex;
            }
            this.i = 4;
            if (!this.j.ignore(5)) {
                this.j.LogMsgln(this.k, 5, true, "Obtaining " + messageResponse.m_httpOperationName + " response data for message ...");
            }
            messageResponse.m_httpResponseData = messageResponse.m_msgResponse.getData();
            if (null == messageResponse.m_httpResponseData) {
                (messageResponse.m_httpResponseData = new byte[1])[0] = 0;
            }
            this.i = 0;
            this.q = null;
            if (!this.j.ignore(5)) {
                this.j.LogMsgln(this.k, 5, true, "Obtained " + messageResponse.m_httpResponseData.length + " data bytes from wsa");
            }
            if (!this.j.ignore(6)) {
                final Enumeration listHeaders = messageResponse.m_msgResponse.listHeaders();
                if (null != listHeaders) {
                    while (listHeaders.hasMoreElements()) {
                        final String str = listHeaders.nextElement();
                        this.j.LogMsgln(this.k, 6, true, "WSA response message  header: " + str + " = '" + messageResponse.m_msgResponse.getHeader(str) + "'");
                    }
                }
                else {
                    this.j.LogMsgln(this.k, 6, true, "No WSA response headers found in message.");
                }
            }
            final String header = messageResponse.m_msgResponse.getHeader("Connection");
            if (null != header && header.equalsIgnoreCase("stop")) {
                try {
                    if (!this.j.ignore(6)) {
                        this.j.LogMsgln(this.k, 6, true, "Closing HTTP connection due to response message Connection header containing STOP.");
                    }
                    this.closeHttpConnection(httpConnection, httpConnection.getContext());
                }
                catch (Exception ex6) {}
            }
        }
        catch (ModuleException ex3) {
            this.i = 0;
            this.q = null;
            final NetworkProtocolException ex2 = new NetworkProtocolException(0L, this.r, ex3.toString());
            this.j.LogStackTrace(this.k, 1, true, "", ex2);
            throw ex2;
        }
        catch (NetworkProtocolException ex4) {
            throw ex4;
        }
        catch (Exception ex5) {
            this.j.LogStackTrace(this.k, 1, true, "General Exception in sendHttpMessage()", ex5);
            throw new NetworkProtocolException(0L, this.r, ex5.toString());
        }
    }
    
    protected String a() throws IOException, NetworkProtocolException {
        final MessageResponse messageResponse = new MessageResponse();
        String text = null;
        try {
            if (!this.j.ignore(5)) {
                this.j.LogMsgln(this.k, 5, true, "Test Connection message to WSA: " + this.b.getPath());
            }
            messageResponse.m_msgResponse = this.a.Get(this.b.getPath());
            messageResponse.m_sendMsgResponseCode = messageResponse.m_msgResponse.getStatusCode();
            try {
                messageResponse.m_msgResponse.getData();
                text = messageResponse.m_msgResponse.getText();
            }
            catch (Exception ex7) {}
        }
        catch (psc_b psc_b) {
            final int a = this.a(psc_b.b());
            if (-1 != a) {
                this.u = true;
                final NetworkProtocolException ex = new NetworkProtocolException(a, this.r, psc_b.a(psc_b.b()));
                this.j.LogStackTrace(this.k, 3, true, "", ex);
                throw ex;
            }
            this.j.LogMsgln(this.k, 5, true, "SSL server reported error: " + psc_b.a(psc_b.b()));
        }
        catch (psc_c psc_c) {
            final int b = this.b(psc_c.b());
            if (-1 != b) {
                this.u = true;
                final NetworkProtocolException ex2 = new NetworkProtocolException(b, this.r, psc_c.getMessage());
                this.j.LogStackTrace(this.k, 1, true, "", ex2);
                throw ex2;
            }
            this.j.LogMsgln(this.k, 3, true, "SSL reported error: " + psc_c.getMessage());
        }
        catch (IOException ex3) {
            String s = ex3.toString();
            this.j.LogMsgln(this.k, 5, true, "Detected I/O error on Get (testConnection) message: " + ex3.toString());
            if (s.startsWith("java.net.")) {
                s = s.substring(9);
            }
            else if (s.startsWith("java.io.")) {
                s = s.substring(8);
            }
            throw new NetworkProtocolException(4L, this.r, s);
        }
        catch (Exception ex4) {
            this.j.LogStackTrace(this.k, 1, true, "", ex4);
            throw new NetworkProtocolException(10L, this.r, ex4.toString());
        }
        if (this.a(messageResponse)) {
            final NetworkProtocolException ex5 = new NetworkProtocolException(messageResponse.m_sendMsgExceptionMsgCode, messageResponse.m_sendMsgExceptionErrorDetail);
            this.j.LogStackTrace(this.k, 1, true, "", ex5);
            throw ex5;
        }
        if (!this.j.ignore(5)) {
            this.j.LogMsgln(this.k, 5, true, "Test Connection message to aia succeeded.");
        }
        if (!this.j.ignore(5)) {
            this.j.LogMsgln(this.k, 5, true, "Getting HTTPS server information...");
        }
        if (this.m) {
            this.p = this.a.getSSLSocketInfo();
            if (null == this.n) {
                final NetworkProtocolException ex6 = new NetworkProtocolException(5L, this.r, "Unable to establish server identity");
                this.j.LogStackTrace(this.k, 1, true, "", ex6);
                throw ex6;
            }
            if (!this.j.ignore(5)) {
                final StringBuffer sb = new StringBuffer("SSL connection params\n");
                sb.append("Protocol version: ");
                sb.append(Integer.toString(this.p.getVersion()));
                sb.append("\n");
                sb.append("Protocol cipher suite: ");
                sb.append(this.p.getCipherSuite());
                sb.append("\n");
                sb.append("Protocol session-id: ");
                sb.append(this.p.getSessionId());
                sb.append("\n");
                sb.append("Protocol peer: ");
                sb.append(this.p.getPeerName());
                sb.append("\n");
                final ISSLSocketUtils.IPeerCertificateInfo[] peerCertificateInfo = this.p.getPeerCertificateInfo();
                if (null != peerCertificateInfo) {
                    for (int i = 0; i < peerCertificateInfo.length; ++i) {
                        sb.append("Cert #");
                        sb.append(Integer.toString(i));
                        sb.append("\n");
                        sb.append("     serial: ");
                        sb.append(peerCertificateInfo[i].getSerialNumber());
                        sb.append("\n");
                        sb.append("     subject: ");
                        sb.append(peerCertificateInfo[i].getSubjectName());
                        sb.append("\n");
                        sb.append("     issuer: ");
                        sb.append(peerCertificateInfo[i].getIssuerName());
                        sb.append("\n");
                        sb.append("     from: ");
                        sb.append(peerCertificateInfo[i].getFromDate());
                        sb.append("\n");
                        sb.append("     to: ");
                        sb.append(peerCertificateInfo[i].getToDate());
                        sb.append("\n");
                    }
                }
                else {
                    sb.append("WARNING: No peer certificates found");
                }
                this.j.LogMsgln(this.k, 5, true, sb.toString());
            }
        }
        return text;
    }
    
    protected boolean a(final MessageResponse messageResponse) {
        boolean b = false;
        final Vector vector = new Vector<String>();
        vector.addElement(new String(this.r));
        int sendMsgExceptionMsgCode = 10;
        switch (messageResponse.m_sendMsgResponseCode) {
            case 100: {
                break;
            }
            case 101: {
                vector.addElement(new String("Invalid HTTP information (101): Switch protocols"));
                break;
            }
            case 200: {
                break;
            }
            case 201: {
                vector.addElement(new String("Invalid HTTP response (201): Resource Created"));
                break;
            }
            case 202: {
                vector.addElement(new String("Invalid HTTP response (202): Request Accepted"));
                break;
            }
            case 203: {
                vector.addElement(new String("Invalid HTTP response (203): Non-Authoritive Information"));
                break;
            }
            case 204: {
                vector.addElement(new String("Invalid HTTP response (204): No content"));
                break;
            }
            case 205: {
                vector.addElement(new String("Invalid HTTP response (205): Reset content"));
                break;
            }
            case 206: {
                vector.addElement(new String("Invalid HTTP response (206): Partial content"));
                break;
            }
            case 300: {
                vector.addElement(new String("Invalid HTTP Redirection (300): Multiple choice"));
                break;
            }
            case 301: {
                vector.addElement(new String("Invalid HTTP redirection (301): Moved permanently"));
                break;
            }
            case 302: {
                vector.addElement(new String("Invalid HTTP redirection (302): Moved temporarily"));
                break;
            }
            case 303: {
                vector.addElement(new String("Invalid HTTP redirection (303): See other"));
                break;
            }
            case 304: {
                vector.addElement(new String("Invalid HTTP redirection (304): Not modified"));
                break;
            }
            case 305: {
                vector.addElement(new String("Invalid HTTP redirection (305): Use proxy"));
                break;
            }
            case 400: {
                vector.addElement(new String("HTTP client error (400): Bad requrest"));
                break;
            }
            case 401: {
                vector.addElement(new String("client"));
                vector.addElement(new String("HTTP server returned Unauthorized (401)"));
                sendMsgExceptionMsgCode = 9;
                this.t = true;
                break;
            }
            case 402: {
                vector.addElement(new String("HTTP client error (402): Payment required"));
                break;
            }
            case 403: {
                vector.addElement(new String("HTTP client error (403): Forbidden"));
                break;
            }
            case 404: {
                vector.addElement(new String("HTTP client error (404): Not found"));
                break;
            }
            case 405: {
                vector.addElement(new String("HTTP client error (405): Method not allowed"));
                break;
            }
            case 406: {
                vector.addElement(new String("HTTP client error (406): Not acceptable"));
                break;
            }
            case 407: {
                vector.addElement(new String("HTTP proxy server returned authentication required (407)"));
                sendMsgExceptionMsgCode = 13;
                this.s = true;
                break;
            }
            case 408: {
                vector.addElement(new String(this.b.getHost() + Integer.toString(this.b.getPort())));
                sendMsgExceptionMsgCode = 7;
                break;
            }
            case 409: {
                vector.addElement(new String("HTTP client error (409): Conflict"));
                break;
            }
            case 410: {
                vector.addElement(new String("HTTP client error (410): Gone"));
                break;
            }
            case 411: {
                vector.addElement(new String("HTTP client error (411): Length required"));
                break;
            }
            case 412: {
                vector.addElement(new String("HTTP client error (412): Precondition failed"));
                break;
            }
            case 413: {
                vector.addElement(new String("HTTP client error (413): Request entity too large"));
                break;
            }
            case 414: {
                vector.addElement(new String("HTTP client error (414): Request-URI too long"));
                break;
            }
            case 415: {
                vector.addElement(new String("HTTP client error (415): Unsupported media type"));
                break;
            }
            case 500: {
                this.e.m_server_internal_error = true;
                break;
            }
            case 501: {
                vector.addElement(new String(this.b.getHost() + Integer.toString(this.b.getPort())));
                vector.addElement(new String("HTTP server error (501): Not implemented"));
                sendMsgExceptionMsgCode = 8;
                break;
            }
            case 502: {
                vector.addElement(new String(this.b.getHost() + Integer.toString(this.b.getPort())));
                vector.addElement(new String("HTTP server error (502): Bad gateway"));
                sendMsgExceptionMsgCode = 8;
                break;
            }
            case 503: {
                vector.addElement(new String(this.b.getHost() + Integer.toString(this.b.getPort())));
                vector.addElement(new String("HTTP server error (503): Service unavailable"));
                sendMsgExceptionMsgCode = 8;
                break;
            }
            case 504: {
                vector.addElement(new String(this.b.getHost() + Integer.toString(this.b.getPort())));
                vector.addElement(new String("HTTP server error (504): Gateway timeout"));
                sendMsgExceptionMsgCode = 8;
                break;
            }
            case 505: {
                vector.addElement(new String("HTTP server error (505): HTTP version not supported"));
                break;
            }
            default: {
                vector.addElement(new String("HTTP status response code: " + Integer.toString(messageResponse.m_sendMsgResponseCode)));
                break;
            }
        }
        if (1 < vector.size()) {
            messageResponse.m_sendMsgExceptionMsgCode = sendMsgExceptionMsgCode;
            vector.copyInto(messageResponse.m_sendMsgExceptionErrorDetail = new Object[vector.size()]);
            b = true;
        }
        return b;
    }
    
    private int a(final int n) {
        int n2 = 0;
        switch (n) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 48:
            case 49:
            case 60: {
                n2 = 6;
                break;
            }
            default: {
                n2 = 10;
                break;
            }
        }
        return n2;
    }
    
    private int b(final int n) {
        int n2 = 0;
        switch (n) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 48:
            case 49:
            case 60: {
                n2 = 5;
                break;
            }
            default: {
                n2 = 10;
                break;
            }
        }
        return n2;
    }
    
    protected void b() throws NetworkProtocolException {
        int loadAuthenticationCertificates;
        try {
            String property = this.f.getProperty("PROGRESS.Session.certificateStore");
            if (null == property || 0 == property.length()) {
                if (-1 != System.getProperty("java.vendor").indexOf("Microsoft")) {
                    property = "psccerts.zip";
                }
                else {
                    property = "psccerts.jar";
                }
            }
            final StringBuffer sb = new StringBuffer();
            sb.append("-i ");
            if (NetworkProtocolOptions.getBooleanProperty(null, "psc.certloader.debug", false)) {
                sb.append("-d ");
            }
            sb.append(property);
            if (!this.j.ignore(5)) {
                this.j.LogMsgln(this.k, 5, true, "Loading certificates: " + sb.toString());
            }
            loadAuthenticationCertificates = this.n.loadAuthenticationCertificates(sb.toString());
        }
        catch (Exception ex) {
            this.j.LogStackTrace(this.k, 1, true, "", ex);
            final NetworkProtocolException ex2 = new NetworkProtocolException(2L, this.r, ex.toString());
            try {
                this.disconnect();
            }
            catch (Exception ex4) {}
            throw ex2;
        }
        if (loadAuthenticationCertificates == 0) {
            final NetworkProtocolException ex3 = new NetworkProtocolException(2L, this.r, "Cannot proceed without Digital Certificates to authenticate the server");
            this.j.LogStackTrace(this.k, 1, true, "", ex3);
            try {
                this.disconnect();
            }
            catch (Exception ex5) {}
            throw ex3;
        }
    }
    
    protected class MessageResponse
    {
        public HTTPResponse m_msgResponse;
        public int m_sendMsgResponseCode;
        public int m_sendMsgExceptionMsgCode;
        public Object[] m_sendMsgExceptionErrorDetail;
        public String m_httpOperationName;
        public boolean m_queResponse;
        public byte[] m_httpResponseData;
        public boolean m_server_internal_error;
        
        public MessageResponse() {
            this.m_msgResponse = null;
            this.m_sendMsgResponseCode = -1;
            this.m_sendMsgExceptionMsgCode = -1;
            this.m_sendMsgExceptionErrorDetail = null;
            this.m_httpOperationName = null;
            this.m_queResponse = true;
            this.m_httpResponseData = null;
            this.m_server_internal_error = false;
        }
        
        public MessageResponse(final HTTPResponse msgResponse) {
            this.m_msgResponse = null;
            this.m_sendMsgResponseCode = -1;
            this.m_sendMsgExceptionMsgCode = -1;
            this.m_sendMsgExceptionErrorDetail = null;
            this.m_httpOperationName = null;
            this.m_queResponse = true;
            this.m_httpResponseData = null;
            this.m_server_internal_error = false;
            this.m_msgResponse = msgResponse;
        }
        
        protected void finalize() throws Throwable {
            if (null != this.m_httpResponseData) {
                this.m_httpResponseData = null;
            }
            if (null != this.m_msgResponse) {
                this.m_msgResponse = null;
            }
        }
    }
}
