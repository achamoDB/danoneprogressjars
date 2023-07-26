// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.client;

import java.util.Hashtable;
import java.io.InputStream;
import HTTPClient.HTTPResponse;
import HTTPClient.ModuleException;
import HTTPClient.NVPair;
import java.net.InetAddress;
import HTTPClient.Codecs;
import java.io.IOException;
import java.io.EOFException;
import com.progress.ubroker.util.IubMsgOutputStream;
import com.progress.ubroker.util.IubMsgInputStream;
import java.net.Socket;
import java.util.Enumeration;
import com.progress.ubroker.util.NetworkProtocolException;
import java.net.MalformedURLException;
import com.progress.common.ehnlog.IAppLogger;
import java.util.Properties;
import com.progress.ubroker.util.INetworkProtocol;
import HTTPClient.AuthorizationInfo;
import java.util.Vector;
import com.progress.ubroker.util.SocketConnectionInfoEx;
import HTTPClient.HTTPConnection;

public class HttpClientProtocol extends NetworkClientProtocol
{
    protected static final String CONNECT_ID_IN_HEADER = "CONNHDL";
    protected static final String HTTP_DEFAULT_TIMEOUT = "0";
    protected static final String AIA_MSG_ID = "AIAMSGID";
    public static final int HTTP_STATE_IDLE = 0;
    public static final int HTTP_STATE_GET = 1;
    public static final int HTTP_STATE_POST = 2;
    public static final int HTTP_STATE_GET_RESPONSE_HEADERS = 3;
    public static final int HTTP_STATE_GET_RESPONSE_DATA = 4;
    protected static boolean m_HTTPClientInitialized;
    protected HTTPConnection m_httpClient;
    protected HttpClientMsgInputStream m_msgInputStream;
    protected HttpClientMsgOutputStream m_msgOutputStream;
    protected int m_msgInputStreamRefCount;
    protected int m_msgOutputStreamRefCount;
    protected SocketConnectionInfoEx m_connectURL;
    protected Vector m_ubMsgResponses;
    protected String m_aiaConnectionId;
    protected AuthorizationInfo m_serverAuthObject;
    protected Vector m_proxyAuthObjects;
    protected long m_aiaMsgId;
    protected long m_aiaStopMsgId;
    protected String m_connectionIdQuery;
    protected String m_userId;
    protected String m_password;
    protected boolean m_logicalConnectionOpen;
    protected int m_httpState;
    protected Thread m_httpThread;
    
    public HttpClientProtocol() {
        this.m_httpClient = null;
        this.m_msgInputStream = null;
        this.m_msgOutputStream = null;
        this.m_msgInputStreamRefCount = 0;
        this.m_msgOutputStreamRefCount = 0;
        this.m_connectURL = null;
        this.m_ubMsgResponses = new Vector();
        this.m_aiaConnectionId = null;
        this.m_serverAuthObject = null;
        this.m_proxyAuthObjects = new Vector();
        this.m_aiaMsgId = 0L;
        this.m_aiaStopMsgId = -1L;
        this.m_connectionIdQuery = null;
        this.m_userId = null;
        this.m_password = null;
        this.m_logicalConnectionOpen = false;
        this.m_httpState = 0;
        this.m_httpThread = null;
        super.m_protocolType = 2;
        super.m_protocolTypeName = INetworkProtocol.m_protocolTypeNames[2];
    }
    
    protected void finalize() throws Throwable {
        try {
            this.release();
        }
        catch (Exception ex) {}
    }
    
    public synchronized void init(final Properties properties, final IAppLogger appLogger, final int n) throws Exception {
        super.init(properties, appLogger, n);
        if (!HttpClientProtocol.m_HTTPClientInitialized) {
            HttpClientProtocol.m_HTTPClientInitialized = true;
        }
    }
    
    public void release() throws Exception {
        if (super.m_loggingObj.ifLogBasic(super.m_debugLogEntries, super.m_debugLogIndex)) {
            super.m_loggingObj.logBasic(super.m_debugLogIndex, "Releasing (closing) HTTPClientProtocol.");
        }
        try {
            this.closeHttpConnection(this.m_httpClient, this);
        }
        catch (Exception ex) {}
        finally {
            this.m_httpClient = null;
        }
        this.closeConnection(true);
    }
    
    public void resolveConnectionInfo(final SocketConnectionInfoEx socketConnectionInfoEx) throws Exception {
        if (!socketConnectionInfoEx.getProtocol().equalsIgnoreCase("http") && !socketConnectionInfoEx.getProtocol().equalsIgnoreCase("http")) {
            throw new MalformedURLException("The URL's network protocol must be http");
        }
    }
    
    public synchronized void openConnection(final SocketConnectionInfoEx connectURL, final int n, final Properties properties, final Object o, final String original) throws Exception {
        if (null != this.m_aiaConnectionId) {
            throw new NetworkProtocolException(0L, connectURL.getProtocol(), "An AppServer connection already exists.");
        }
        if (!connectURL.getProtocol().equalsIgnoreCase("http") && !connectURL.getProtocol().equalsIgnoreCase("https")) {
            throw new MalformedURLException("The URL's network protocol must be http or https");
        }
        this.m_connectURL = connectURL;
        if (null != properties) {
            final Enumeration<?> propertyNames = properties.propertyNames();
            while (propertyNames.hasMoreElements()) {
                final String s = (String)propertyNames.nextElement();
                final String property = properties.getProperty(s);
                if (null != property) {
                    ((Hashtable<String, String>)super.m_protocolProperties).put(s, property);
                }
            }
        }
        if (null != o) {
            this.m_userId = new String((String)o);
            if (null != original) {
                this.m_password = new String(original);
            }
        }
        this.m_connectionIdQuery = new String(this.m_connectURL.getPath());
        this.m_aiaMsgId = 0L;
        this.m_httpClient = this.newHttpConnection(this);
        if (2 == super.m_protocolType) {
            try {
                this.testProtocolConnection();
            }
            catch (Exception ex) {
                try {
                    this.closeHttpConnection(this.m_httpClient, this);
                }
                catch (Exception ex2) {}
                finally {
                    this.m_httpClient = null;
                }
                throw ex;
            }
        }
    }
    
    public void closeConnection(final boolean b) throws Exception {
        if (null != this.m_httpClient) {
            if (0 < this.m_ubMsgResponses.size()) {
                for (int i = 0; i < this.m_ubMsgResponses.size(); ++i) {
                    final UBMessageResponse ubMessageResponse = this.m_ubMsgResponses.elementAt(i);
                    ubMessageResponse.m_httpInputStream.close();
                    ubMessageResponse.m_httpInputStream = null;
                    ubMessageResponse.m_ubMsgResponse = null;
                }
            }
            try {
                if (super.m_loggingObj.ifLogBasic(super.m_debugLogEntries, super.m_debugLogIndex)) {
                    super.m_loggingObj.logBasic(super.m_debugLogIndex, "Closing the HTTP connection to: " + this.m_connectURL.getHost() + ":" + Integer.toString(this.m_connectURL.getPort()));
                }
                try {
                    this.closeHttpConnection(this.m_httpClient, this);
                }
                catch (Exception ex2) {}
                finally {
                    this.m_httpClient = null;
                }
            }
            catch (Exception ex) {
                throw ex;
            }
            finally {
                this.m_connectURL = null;
                this.m_aiaConnectionId = null;
                this.m_connectionIdQuery = null;
                this.m_ubMsgResponses.removeAllElements();
                this.m_proxyAuthObjects.removeAllElements();
                this.m_serverAuthObject = null;
                this.m_password = null;
                this.m_userId = null;
                this.m_logicalConnectionOpen = false;
                this.m_msgInputStream = null;
                this.m_msgOutputStream = null;
                this.m_msgInputStreamRefCount = 0;
                this.m_msgOutputStreamRefCount = 0;
            }
        }
    }
    
    public Socket rawSocket() {
        return null;
    }
    
    public IubMsgInputStream getMsgInputStream(final int n) throws Exception {
        switch (n) {
            case 0: {
                if (null != this.m_httpClient && null == this.m_msgInputStream) {
                    this.m_msgInputStream = new HttpClientMsgInputStream(this);
                }
                ++this.m_msgInputStreamRefCount;
                return this.m_msgInputStream;
            }
            default: {
                final NetworkProtocolException ex = new NetworkProtocolException(0L, "TCP", "Unsupported Input Stream type requested.");
                super.m_loggingObj.logStackTrace("", ex);
                throw ex;
            }
        }
    }
    
    public IubMsgOutputStream getMsgOutputStream(final int n) throws Exception {
        switch (n) {
            case 0: {
                if (null != this.m_httpClient && null == this.m_msgOutputStream) {
                    this.m_msgOutputStream = new HttpClientMsgOutputStream(this);
                }
                ++this.m_msgOutputStreamRefCount;
                return this.m_msgOutputStream;
            }
            default: {
                final NetworkProtocolException ex = new NetworkProtocolException(0L, "TCP", "Unsupported Output Stream type requested.");
                super.m_loggingObj.logStackTrace("", ex);
                throw ex;
            }
        }
    }
    
    public void setDynamicProtocolProperty(final String s, final String s2) throws Exception {
        super.setDynamicProtocolProperty(s, s2);
        if (s.equalsIgnoreCase("PROGRESS.Session.httpTimeout") && null != s2 && 0 < s2.length()) {
            try {
                final int int1 = Integer.parseInt(s2);
                if (0 <= int1 && null != this.m_httpClient) {
                    this.m_httpClient.setTimeout(int1);
                }
                throw new Exception("Invalid timeout value: " + s2);
            }
            catch (Exception ex) {
                if (super.m_loggingObj.ifLogBasic(super.m_debugLogEntries, super.m_debugLogIndex)) {
                    super.m_loggingObj.logBasic(super.m_debugLogIndex, "Error setting property PROGRESS.Session.httpTimeout: " + ex.getMessage());
                }
            }
        }
    }
    
    public void sendUbMessage(final HttpClientMsgOutputStream httpClientMsgOutputStream) throws IOException, NetworkProtocolException {
        if (this.m_logicalConnectionOpen && null == this.m_aiaConnectionId) {
            throw new NetworkProtocolException(10L, super.m_protocolTypeName, "No " + super.m_protocolTypeName + " connection exists");
        }
        if (null == this.m_httpClient) {
            this.m_httpClient = this.newHttpConnection(this);
        }
        try {
            boolean b = true;
            boolean b2 = false;
            final UBMessageResponse sendHttpRequest = this.sendHttpRequest(this.m_httpClient, httpClientMsgOutputStream, "AppServer", this.m_aiaMsgId++);
            if (4 == sendHttpRequest.m_ubMsgRequestCode || 5 == sendHttpRequest.m_ubMsgRequestCode || 6 == sendHttpRequest.m_ubMsgRequestCode || 3 == sendHttpRequest.m_ubMsgRequestCode) {
                b2 = true;
            }
            if (b2) {
                this.getHttpResponse(this.m_httpClient, sendHttpRequest);
                final String header = sendHttpRequest.m_ubMsgResponse.getHeader("Content-Length");
                if (null != header && 0 == header.compareTo("0")) {
                    b = false;
                }
            }
            if (b) {
                synchronized (this.m_ubMsgResponses) {
                    this.m_ubMsgResponses.addElement(sendHttpRequest);
                }
            }
            else {
                sendHttpRequest.m_httpInputStream.close();
                sendHttpRequest.m_httpInputStream = null;
                sendHttpRequest.m_ubMsgResponse = null;
            }
        }
        catch (EOFException ex2) {
            final NetworkProtocolException ex = new NetworkProtocolException(8L, super.m_protocolTypeName, ex2.getMessage());
            super.m_loggingObj.logStackTrace("", ex);
            throw ex;
        }
        catch (IOException ex3) {
            if (super.m_loggingObj.ifLogBasic(super.m_debugLogEntries, super.m_debugLogIndex)) {
                super.m_loggingObj.logBasic(super.m_debugLogIndex, "Detected I/O error posting (write) message: " + ex3.getMessage());
            }
            throw ex3;
        }
        catch (Exception ex5) {
            final NetworkProtocolException ex4 = new NetworkProtocolException(10L, super.m_protocolTypeName, ex5.toString());
            super.m_loggingObj.logStackTrace("", ex4);
            throw ex4;
        }
    }
    
    public UBMessageResponse getUbResponseMessage() throws IOException, NetworkProtocolException {
        UBMessageResponse ubMessageResponse = null;
        if (null == this.m_httpClient) {
            throw new NetworkProtocolException(10L, super.m_protocolTypeName, "No " + super.m_protocolTypeName + " connection exists");
        }
        Label_0052: {
            break Label_0052;
            try {
                do {
                    synchronized (this.m_ubMsgResponses) {
                        try {
                            ubMessageResponse = this.m_ubMsgResponses.firstElement();
                            this.m_ubMsgResponses.removeElementAt(0);
                        }
                        catch (Exception ex4) {
                            final EOFException ex = new EOFException("A HTTP response is not available");
                            super.m_loggingObj.logStackTrace("", ex);
                            throw ex;
                        }
                    }
                    this.getHttpResponse(this.m_httpClient, ubMessageResponse);
                    final String header = ubMessageResponse.m_ubMsgResponse.getHeader("Content-Length");
                    if (null != header && 0 == header.compareTo("0")) {
                        ubMessageResponse.m_httpInputStream.close();
                        ubMessageResponse.m_httpInputStream = null;
                        ubMessageResponse.m_ubMsgResponse = null;
                        ubMessageResponse = null;
                    }
                } while (null == ubMessageResponse);
                final String header2 = ubMessageResponse.m_ubMsgResponse.getHeader("Content-Type");
                if (null != header2) {
                    if (0 != header2.compareTo("application/octet-stream") && super.m_loggingObj.ifLogBasic(super.m_debugLogEntries, super.m_debugLogIndex)) {
                        super.m_loggingObj.logBasic(super.m_debugLogIndex, "Unexpected HTTP Content-Type header: " + header2);
                    }
                }
                else if (super.m_loggingObj.ifLogBasic(super.m_debugLogEntries, super.m_debugLogIndex)) {
                    super.m_loggingObj.logBasic(super.m_debugLogIndex, "Warning, missing Content-Type header.");
                }
                final String header3 = ubMessageResponse.m_ubMsgResponse.getHeader("CONNHDL");
                if (null != header3 && 0 < header3.length()) {
                    if (null == this.m_aiaConnectionId) {
                        this.m_aiaConnectionId = new String(header3);
                        final StringBuffer sb = new StringBuffer(this.m_connectURL.getPath());
                        sb.append('?');
                        sb.append("CONNHDL");
                        sb.append('=');
                        sb.append(this.m_aiaConnectionId);
                        this.m_connectionIdQuery = sb.toString();
                        this.m_logicalConnectionOpen = true;
                    }
                }
                else {
                    if (super.m_loggingObj.ifLogBasic(super.m_debugLogEntries, super.m_debugLogIndex)) {
                        super.m_loggingObj.logBasic(super.m_debugLogIndex, "Detected aia resetting connection id to null.");
                    }
                    if (!this.m_ubMsgResponses.isEmpty() && super.m_loggingObj.ifLogBasic(super.m_debugLogEntries, super.m_debugLogIndex)) {
                        super.m_loggingObj.logBasic(super.m_debugLogIndex, "Detected null connection id with responses pending.");
                    }
                }
            }
            catch (Exception ex3) {
                final NetworkProtocolException ex2 = new NetworkProtocolException(0L, super.m_protocolTypeName, ex3.toString());
                super.m_loggingObj.logStackTrace("", ex2);
                throw ex2;
            }
        }
        return ubMessageResponse;
    }
    
    public int available() {
        int n = 0;
        try {
            int n2 = 0;
            final Enumeration<UBMessageResponse> elements = (Enumeration<UBMessageResponse>)this.m_ubMsgResponses.elements();
            while (elements.hasMoreElements()) {
                n2 += elements.nextElement().m_httpInputStream.available();
            }
            n = n2;
        }
        catch (Exception ex) {}
        return n;
    }
    
    public void sendStopMessage(final HttpClientMsgOutputStream httpClientMsgOutputStream) throws IOException, NetworkProtocolException {
        if (null == this.m_aiaConnectionId) {
            throw new NetworkProtocolException(10L, super.m_protocolTypeName, "No " + super.m_protocolTypeName + " connection exists");
        }
        final Long n = new Long(System.currentTimeMillis());
        HTTPConnection httpConnection;
        try {
            if (super.m_loggingObj.ifLogBasic(super.m_debugLogEntries, super.m_debugLogIndex)) {
                super.m_loggingObj.logBasic(super.m_debugLogIndex, "Creating new Http connection to send stop message.");
            }
            httpConnection = this.newHttpConnection(n);
        }
        catch (NetworkProtocolException ex) {
            if (super.m_loggingObj.ifLogBasic(super.m_debugLogEntries, super.m_debugLogIndex)) {
                super.m_loggingObj.logBasic(super.m_debugLogIndex, "Error creating new http stop connection: " + ex.toString());
            }
            throw ex;
        }
        try {
            final Thread thread = new Thread(new SendStopThread(this, httpConnection, httpClientMsgOutputStream, this.m_aiaStopMsgId--));
            if (super.m_loggingObj.ifLogBasic(super.m_debugLogEntries, super.m_debugLogIndex)) {
                super.m_loggingObj.logBasic(super.m_debugLogIndex, "Starting Http stop thread operation.");
            }
            thread.start();
            thread.join();
            if (super.m_loggingObj.ifLogBasic(super.m_debugLogEntries, super.m_debugLogIndex)) {
                super.m_loggingObj.logBasic(super.m_debugLogIndex, "Http stop thread operation finished.");
            }
            if (null != this.m_msgInputStream) {
                this.m_msgInputStream.setStop();
            }
        }
        catch (Exception ex2) {
            super.m_loggingObj.logStackTrace("", ex2);
            final NetworkProtocolException ex3 = new NetworkProtocolException(10L, super.m_protocolTypeName, ex2.toString());
            super.m_loggingObj.logStackTrace("", ex3);
            throw ex3;
        }
        finally {
            if (null != httpConnection) {
                try {
                    this.closeHttpConnection(httpConnection, n);
                }
                catch (Exception ex4) {
                    if (super.m_loggingObj.ifLogBasic(super.m_debugLogEntries, super.m_debugLogIndex)) {
                        super.m_loggingObj.logBasic(super.m_debugLogIndex, "Error closing http connection: " + ex4.toString());
                    }
                }
            }
        }
    }
    
    public void releaseMsgInputStream() {
        if (null != this.m_msgInputStream) {
            if (0 < this.m_msgInputStreamRefCount) {
                --this.m_msgInputStreamRefCount;
            }
            if (0 == this.m_msgInputStreamRefCount) {
                this.m_msgInputStream = null;
            }
        }
    }
    
    public void releaseMsgOutputStream() {
        if (null != this.m_msgOutputStream) {
            if (0 < this.m_msgOutputStreamRefCount) {
                --this.m_msgOutputStreamRefCount;
            }
            if (0 == this.m_msgOutputStreamRefCount) {
                this.m_msgOutputStream = null;
            }
        }
    }
    
    protected boolean evaluateResponseCode(final UBMessageResponse ubMessageResponse) {
        boolean b = false;
        final Vector vector = new Vector<String>();
        vector.addElement(new String(super.m_protocolTypeName));
        int sendMsgExceptionMsgCode;
        if (null == this.m_aiaConnectionId) {
            sendMsgExceptionMsgCode = 4;
        }
        else {
            sendMsgExceptionMsgCode = 10;
        }
        switch (ubMessageResponse.m_sendMsgResponseCode) {
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
                break;
            }
            case 408: {
                vector.addElement(new String(this.m_connectURL.getHost() + Integer.toString(this.m_connectURL.getPort())));
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
                vector.addElement(new String(this.m_connectURL.getHost() + Integer.toString(this.m_connectURL.getPort())));
                vector.addElement(new String("HTTP server error (500): Internal error"));
                sendMsgExceptionMsgCode = 8;
                break;
            }
            case 501: {
                vector.addElement(new String(this.m_connectURL.getHost() + Integer.toString(this.m_connectURL.getPort())));
                vector.addElement(new String("HTTP server error (501): Not implemented"));
                sendMsgExceptionMsgCode = 8;
                break;
            }
            case 502: {
                vector.addElement(new String(this.m_connectURL.getHost() + Integer.toString(this.m_connectURL.getPort())));
                vector.addElement(new String("HTTP server error (502): Bad gateway"));
                sendMsgExceptionMsgCode = 8;
                break;
            }
            case 503: {
                vector.addElement(new String(this.m_connectURL.getHost() + Integer.toString(this.m_connectURL.getPort())));
                vector.addElement(new String("HTTP server error (503): Service unavailable"));
                sendMsgExceptionMsgCode = 8;
                break;
            }
            case 504: {
                vector.addElement(new String(this.m_connectURL.getHost() + Integer.toString(this.m_connectURL.getPort())));
                vector.addElement(new String("HTTP server error (504): Gateway timeout"));
                sendMsgExceptionMsgCode = 8;
                break;
            }
            case 505: {
                vector.addElement(new String("HTTP server error (505): HTTP version not supported"));
                break;
            }
            default: {
                vector.addElement(new String("HTTP status response code: " + Integer.toString(ubMessageResponse.m_sendMsgResponseCode)));
                break;
            }
        }
        if (1 < vector.size()) {
            ubMessageResponse.m_sendMsgExceptionMsgCode = sendMsgExceptionMsgCode;
            vector.copyInto(ubMessageResponse.m_sendMsgExceptionErrorDetail = new Object[vector.size()]);
            b = true;
        }
        return b;
    }
    
    protected void setupBasicAuthentication(final HTTPConnection httpConnection, final Object o) {
        if (null == this.m_serverAuthObject) {
            final String str = "";
            String s = this.m_userId;
            StringBuffer obj = (null != this.m_password) ? new StringBuffer(this.m_password) : null;
            final Object o2 = null;
            if (null == s) {
                try {
                    s = this.m_connectURL.getUserId();
                    if (null != s) {
                        obj = new StringBuffer(this.m_connectURL.getUserPassword());
                    }
                }
                catch (Exception ex) {
                    if (super.m_loggingObj.ifLogBasic(super.m_debugLogEntries, super.m_debugLogIndex)) {
                        super.m_loggingObj.logBasic(super.m_debugLogIndex, "Attempt to use a authentication user-id without a password");
                    }
                    s = null;
                }
            }
            if (null == o2) {
                this.m_connectURL.getHost();
            }
            if (null == s) {
                s = super.m_protocolProperties.getProperty("PROGRESS.Session.userId");
                obj = new StringBuffer(super.m_protocolProperties.getProperty("PROGRESS.Session.password"));
            }
            if (null != s) {
                (this.m_serverAuthObject = new AuthorizationInfo(this.m_connectURL.getHost(), this.m_connectURL.getPort(), "Basic", str, Codecs.base64Encode(s + ":" + (Object)obj))).addPath(this.m_connectURL.getPath());
                if (super.m_loggingObj.ifLogBasic(super.m_debugLogEntries, super.m_debugLogIndex)) {
                    super.m_loggingObj.logBasic(super.m_debugLogIndex, "Authenticating to HTTP server: " + str + ":" + s + ":xxxxxxx");
                }
                for (int i = 0; i < obj.length(); ++i) {
                    obj.setCharAt(i, ' ');
                }
            }
        }
        if (null != this.m_serverAuthObject) {
            AuthorizationInfo.addAuthorization(this.m_serverAuthObject, o);
        }
    }
    
    protected void setupProxyServer(final HTTPConnection httpConnection, final Object o) throws NetworkProtocolException {
        final String property = super.m_protocolProperties.getProperty("PROGRESS.Session.proxyHost");
        if (null != property) {
            try {
                final String property2 = super.m_protocolProperties.getProperty("PROGRESS.Session.proxyPort");
                if (null == property2) {
                    throw new NetworkProtocolException(2L, super.m_protocolTypeName, "Bad proxy host:port format");
                }
                if (super.m_loggingObj.ifLogBasic(super.m_debugLogEntries, super.m_debugLogIndex)) {
                    super.m_loggingObj.logBasic(super.m_debugLogIndex, "Using HTTP Proxy server: " + property + ":" + property2);
                }
                try {
                    InetAddress.getByName(property);
                }
                catch (Exception ex3) {
                    throw new NetworkProtocolException(2L, super.m_protocolTypeName, "Unknown proxy host " + property);
                }
                httpConnection.setCurrentProxy(property, Integer.parseInt(property2));
                final String property3 = super.m_protocolProperties.getProperty("PROGRESS.Session.proxyUserId");
                final String str = "";
                if (null != property3) {
                    final String property4 = super.m_protocolProperties.getProperty("PROGRESS.Session.proxyPassword");
                    if (null == property4) {
                        throw new NetworkProtocolException(2L, super.m_protocolTypeName, "Bad proxy [realm:]uid:pwd format");
                    }
                    final AuthorizationInfo obj = new AuthorizationInfo(property, Integer.parseInt(property2), "Basic", str, Codecs.base64Encode(property3 + ":" + property4));
                    obj.addPath("/");
                    if (super.m_loggingObj.ifLogBasic(super.m_debugLogEntries, super.m_debugLogIndex)) {
                        super.m_loggingObj.logBasic(super.m_debugLogIndex, "Authenticating to HTTP Proxy server: " + ((null == str) ? "" : (str + ":")) + property3 + ":xxxxxxx");
                    }
                    if (this == o) {
                        this.m_proxyAuthObjects.addElement(obj);
                    }
                }
            }
            catch (NetworkProtocolException ex) {
                if (super.m_loggingObj.ifLogBasic(super.m_debugLogEntries, super.m_debugLogIndex)) {
                    super.m_loggingObj.logBasic(super.m_debugLogIndex, "Invalid proxy option: " + property + " (" + ex.getMessage() + ")");
                }
                throw ex;
            }
        }
        if (!this.m_proxyAuthObjects.isEmpty()) {
            final Enumeration<AuthorizationInfo> elements = this.m_proxyAuthObjects.elements();
            while (elements.hasMoreElements()) {
                AuthorizationInfo.addAuthorization(elements.nextElement(), o);
            }
        }
        try {
            HTTPConnection.dontProxyFor("localhost");
            HTTPConnection.dontProxyFor("127.0.0.1");
        }
        catch (Exception ex2) {
            throw new NetworkProtocolException(2L, super.m_protocolTypeName, "Failure removing localhost from proxy list: " + ex2.toString());
        }
    }
    
    protected void setupDefaults(final HTTPConnection httpConnection, final Object o) throws NetworkProtocolException {
        httpConnection.setAllowUserInteraction(false);
        String property = super.m_protocolProperties.getProperty("PROGRESS.Session.httpTimeout");
        if (null == property) {
            property = "0";
        }
        try {
            int timeout = Integer.parseInt(property);
            if (0 > timeout) {
                timeout = Integer.parseInt("0");
                if (super.m_loggingObj.ifLogBasic(super.m_debugLogEntries, super.m_debugLogIndex)) {
                    super.m_loggingObj.logBasic(super.m_debugLogIndex, "ignoring negative timeout, setting default http timeout option: 0");
                }
            }
            else if (super.m_loggingObj.ifLogBasic(super.m_debugLogEntries, super.m_debugLogIndex)) {
                super.m_loggingObj.logBasic(super.m_debugLogIndex, "setting http timeout option: " + property);
            }
            if (timeout != 0) {
                timeout *= 1000;
            }
            httpConnection.setTimeout(timeout);
        }
        catch (Exception ex) {
            if (super.m_loggingObj.ifLogBasic(super.m_debugLogEntries, super.m_debugLogIndex)) {
                super.m_loggingObj.logBasic(super.m_debugLogIndex, "Invalid http timeout option: " + property);
            }
        }
        final NVPair[] defaultHeaders = new NVPair[3];
        int n = 0;
        defaultHeaders[n++] = new NVPair("User-Agent", "Progress ASIA Client");
        defaultHeaders[n++] = new NVPair("Accept", "*/*");
        defaultHeaders[n++] = new NVPair("Proxy-Connection", "Keep-Alive");
        httpConnection.setDefaultHeaders(defaultHeaders);
    }
    
    protected void testProtocolConnection() throws IOException, NetworkProtocolException {
        final UBMessageResponse ubMessageResponse = new UBMessageResponse();
        try {
            if (super.m_loggingObj.ifLogBasic(super.m_debugLogEntries, super.m_debugLogIndex)) {
                super.m_loggingObj.logBasic(super.m_debugLogIndex, "Test Connection message to aia: " + this.m_connectURL.getPath());
            }
            ubMessageResponse.m_ubMsgResponse = this.m_httpClient.Get(this.m_connectURL.getPath());
            ubMessageResponse.m_sendMsgResponseCode = ubMessageResponse.m_ubMsgResponse.getStatusCode();
            try {
                ubMessageResponse.m_ubMsgResponse.getData();
            }
            catch (Exception ex5) {}
        }
        catch (IOException ex) {
            String str = ex.toString();
            if (super.m_loggingObj.ifLogBasic(super.m_debugLogEntries, super.m_debugLogIndex)) {
                super.m_loggingObj.logBasic(super.m_debugLogIndex, "Detected I/O error on Get (testConnection) message: " + str);
            }
            if (str.startsWith("java.net.")) {
                str = str.substring(9);
            }
            else if (str.startsWith("java.io.")) {
                str = str.substring(8);
            }
            throw new NetworkProtocolException(4L, super.m_protocolTypeName, str);
        }
        catch (Exception ex3) {
            final NetworkProtocolException ex2 = new NetworkProtocolException(10L, super.m_protocolTypeName, ex3.toString());
            super.m_loggingObj.logStackTrace("", ex2);
            throw ex2;
        }
        if (this.evaluateResponseCode(ubMessageResponse)) {
            final NetworkProtocolException ex4 = new NetworkProtocolException(ubMessageResponse.m_sendMsgExceptionMsgCode, ubMessageResponse.m_sendMsgExceptionErrorDetail);
            super.m_loggingObj.logStackTrace("", ex4);
            throw ex4;
        }
        if (super.m_loggingObj.ifLogBasic(super.m_debugLogEntries, super.m_debugLogIndex)) {
            super.m_loggingObj.logBasic(super.m_debugLogIndex, "Test Connection message to aia succeeded.");
        }
    }
    
    protected HTTPConnection newHTTPClient(final Object context) throws NetworkProtocolException {
        HTTPConnection httpConnection;
        try {
            if (super.m_loggingObj.ifLogBasic(super.m_debugLogEntries, super.m_debugLogIndex)) {
                super.m_loggingObj.logBasic(super.m_debugLogIndex, "Connecting to HTTP server: " + this.m_connectURL.toString());
            }
            httpConnection = new HTTPConnection(this.m_connectURL.getProtocol(), this.m_connectURL.getHost(), this.m_connectURL.getPort());
            httpConnection.setContext(context);
            httpConnection.removeModule(Class.forName("HTTPClient.CookieModule"));
        }
        catch (Exception ex2) {
            final NetworkProtocolException ex = new NetworkProtocolException(4L, super.m_protocolTypeName, ex2.toString());
            super.m_loggingObj.logStackTrace("", ex);
            throw ex;
        }
        return httpConnection;
    }
    
    public HTTPConnection newHttpConnection(final Object o) throws NetworkProtocolException {
        HTTPConnection httpClient;
        try {
            httpClient = this.newHTTPClient(o);
            this.setupBasicAuthentication(httpClient, o);
            this.setupProxyServer(httpClient, o);
            this.setupDefaults(httpClient, o);
        }
        catch (NetworkProtocolException ex2) {
            try {
                this.closeHttpConnection(null, o);
            }
            catch (Exception ex) {
                if (super.m_loggingObj.ifLogBasic(super.m_debugLogEntries, super.m_debugLogIndex)) {
                    super.m_loggingObj.logBasic(super.m_debugLogIndex, "Error closing http connection: " + ex.toString());
                }
            }
            throw ex2;
        }
        return httpClient;
    }
    
    public void closeHttpConnection(final HTTPConnection httpConnection, final Object o) throws Exception {
        final HTTPConnection httpConnection2 = (null == httpConnection) ? this.m_httpClient : httpConnection;
        if (null != o && !this.m_proxyAuthObjects.isEmpty()) {
            final Enumeration<AuthorizationInfo> elements = this.m_proxyAuthObjects.elements();
            while (elements.hasMoreElements()) {
                AuthorizationInfo.removeAuthorization(elements.nextElement(), o);
            }
        }
        if (null != this.m_serverAuthObject) {
            AuthorizationInfo.removeAuthorization(this.m_serverAuthObject, o);
        }
        if (null != httpConnection2) {
            try {
                httpConnection2.stop();
            }
            catch (Exception ex) {
                if (super.m_loggingObj.ifLogBasic(super.m_debugLogEntries, super.m_debugLogIndex)) {
                    super.m_loggingObj.logBasic(super.m_debugLogIndex, "Error stopping http connection: " + ex.toString());
                }
                throw ex;
            }
        }
    }
    
    protected UBMessageResponse processHttpRequest(final HTTPConnection httpConnection, final HttpClientMsgOutputStream httpClientMsgOutputStream, final String s, final long n) throws IOException, NetworkProtocolException {
        final UBMessageResponse sendHttpRequest = this.sendHttpRequest(httpConnection, httpClientMsgOutputStream, s, n);
        this.getHttpResponse(httpConnection, sendHttpRequest);
        return sendHttpRequest;
    }
    
    protected UBMessageResponse sendHttpRequest(final HTTPConnection httpConnection, final HttpClientMsgOutputStream httpClientMsgOutputStream, final String httpOperationName, final long aiaMsgId) throws IOException, NetworkProtocolException {
        final UBMessageResponse ubMessageResponse = new UBMessageResponse();
        ubMessageResponse.m_ubMsgRequestCode = ((null != httpClientMsgOutputStream) ? httpClientMsgOutputStream.lastUbMsgRequestCode() : 0);
        ubMessageResponse.m_httpOperationName = httpOperationName;
        ubMessageResponse.m_aiaMsgId = aiaMsgId;
        final byte[] array = (byte[])((null != httpClientMsgOutputStream) ? httpClientMsgOutputStream.toByteArray() : null);
        try {
            if (null == array) {
                if (super.m_loggingObj.ifLogBasic(super.m_debugLogEntries, super.m_debugLogIndex)) {
                    super.m_loggingObj.logBasic(super.m_debugLogIndex, "GET (" + ubMessageResponse.m_httpOperationName + ") message" + " from aia.");
                }
                this.m_httpState = 1;
                this.m_httpThread = Thread.currentThread();
                ubMessageResponse.m_ubMsgResponse = httpConnection.Get(this.m_connectURL.getPath());
                if (super.m_loggingObj.ifLogBasic(super.m_debugLogEntries, super.m_debugLogIndex)) {
                    super.m_loggingObj.logBasic(super.m_debugLogIndex, "GET " + ubMessageResponse.m_httpOperationName + " operation complete.");
                }
            }
            else {
                final NVPair[] array2 = { new NVPair("AIAMSGID", Long.toHexString(ubMessageResponse.m_aiaMsgId)), new NVPair("CONNHDL", (null == this.m_aiaConnectionId) ? "" : this.m_aiaConnectionId) };
                if (super.m_loggingObj.ifLogBasic(super.m_debugLogEntries, super.m_debugLogIndex)) {
                    super.m_loggingObj.logBasic(super.m_debugLogIndex, "POST " + ubMessageResponse.m_httpOperationName + " message " + Long.toHexString(ubMessageResponse.m_aiaMsgId) + " to aia, " + array.length + " bytes sent.");
                }
                this.m_httpState = 2;
                this.m_httpThread = Thread.currentThread();
                ubMessageResponse.m_ubMsgResponse = httpConnection.Post(this.m_connectionIdQuery, array, array2);
                if (super.m_loggingObj.ifLogBasic(super.m_debugLogEntries, super.m_debugLogIndex)) {
                    super.m_loggingObj.logBasic(super.m_debugLogIndex, "POST " + ubMessageResponse.m_httpOperationName + " operation complete.");
                }
            }
            this.m_httpState = 3;
        }
        catch (ModuleException ex2) {
            this.m_httpState = 0;
            this.m_httpThread = null;
            final NetworkProtocolException ex = new NetworkProtocolException(0L, super.m_protocolTypeName, ex2.toString());
            super.m_loggingObj.logStackTrace("", ex);
            throw ex;
        }
        catch (Exception ex3) {
            super.m_loggingObj.logStackTrace("General Exception in sendHttpMessage()", ex3);
            throw new NetworkProtocolException(0L, super.m_protocolTypeName, ex3.toString());
        }
        return ubMessageResponse;
    }
    
    protected void getHttpResponse(final HTTPConnection httpConnection, final UBMessageResponse ubMessageResponse) throws IOException, NetworkProtocolException {
        try {
            ubMessageResponse.m_sendMsgResponseCode = ubMessageResponse.m_ubMsgResponse.getStatusCode();
            if (super.m_loggingObj.ifLogBasic(super.m_debugLogEntries, super.m_debugLogIndex)) {
                super.m_loggingObj.logBasic(super.m_debugLogIndex, "Obtained " + ubMessageResponse.m_httpOperationName + " response for message " + ubMessageResponse.m_aiaMsgId + " from aia: " + Integer.toString(ubMessageResponse.m_sendMsgResponseCode));
            }
            if (this.evaluateResponseCode(ubMessageResponse)) {
                this.m_httpState = 0;
                this.m_httpThread = null;
                final NetworkProtocolException ex = new NetworkProtocolException(ubMessageResponse.m_sendMsgExceptionMsgCode, ubMessageResponse.m_sendMsgExceptionErrorDetail);
                super.m_loggingObj.logStackTrace("", ex);
                throw ex;
            }
            this.m_httpState = 4;
            if (super.m_loggingObj.ifLogBasic(super.m_debugLogEntries, super.m_debugLogIndex)) {
                super.m_loggingObj.logBasic(super.m_debugLogIndex, "Obtaining " + ubMessageResponse.m_httpOperationName + " response data for message " + ubMessageResponse.m_aiaMsgId + " ...");
            }
            ubMessageResponse.m_ubMsgResponse.getData();
            this.m_httpState = 0;
            this.m_httpThread = null;
            ubMessageResponse.m_httpInputStream = ubMessageResponse.m_ubMsgResponse.getInputStream();
            if (super.m_loggingObj.ifLogBasic(super.m_debugLogEntries, super.m_debugLogIndex)) {
                super.m_loggingObj.logBasic(super.m_debugLogIndex, "Obtained " + ubMessageResponse.m_httpInputStream.available() + " data bytes from aia for message " + ubMessageResponse.m_aiaMsgId + " .");
            }
            final String header = ubMessageResponse.m_ubMsgResponse.getHeader("Connection");
            if (null != header && header.equalsIgnoreCase("stop")) {
                try {
                    this.closeHttpConnection(httpConnection, httpConnection.getContext());
                }
                catch (Exception ex5) {}
            }
        }
        catch (ModuleException ex3) {
            this.m_httpState = 0;
            this.m_httpThread = null;
            final NetworkProtocolException ex2 = new NetworkProtocolException(0L, super.m_protocolTypeName, ex3.toString());
            super.m_loggingObj.logStackTrace("", ex2);
            throw ex2;
        }
        catch (Exception ex4) {
            super.m_loggingObj.logStackTrace("General Exception in sendHttpMessage()", ex4);
            throw new NetworkProtocolException(0L, super.m_protocolTypeName, ex4.toString());
        }
    }
    
    public String getSSLSubjectName() {
        return null;
    }
    
    static {
        HttpClientProtocol.m_HTTPClientInitialized = false;
    }
    
    protected class UBMessageResponse
    {
        public HTTPResponse m_ubMsgResponse;
        public int m_sendMsgResponseCode;
        public int m_sendMsgExceptionMsgCode;
        public Object[] m_sendMsgExceptionErrorDetail;
        public String m_httpOperationName;
        public long m_aiaMsgId;
        public boolean m_queResponse;
        public int m_ubMsgRequestCode;
        public InputStream m_httpInputStream;
        
        public UBMessageResponse() {
            this.m_ubMsgResponse = null;
            this.m_sendMsgResponseCode = -1;
            this.m_sendMsgExceptionMsgCode = -1;
            this.m_sendMsgExceptionErrorDetail = null;
            this.m_httpOperationName = null;
            this.m_aiaMsgId = 0L;
            this.m_queResponse = true;
            this.m_ubMsgRequestCode = 0;
            this.m_httpInputStream = null;
        }
        
        public UBMessageResponse(final HTTPResponse ubMsgResponse) {
            this.m_ubMsgResponse = null;
            this.m_sendMsgResponseCode = -1;
            this.m_sendMsgExceptionMsgCode = -1;
            this.m_sendMsgExceptionErrorDetail = null;
            this.m_httpOperationName = null;
            this.m_aiaMsgId = 0L;
            this.m_queResponse = true;
            this.m_ubMsgRequestCode = 0;
            this.m_httpInputStream = null;
            this.m_ubMsgResponse = ubMsgResponse;
        }
        
        protected void finalize() throws Throwable {
            if (null != this.m_httpInputStream) {
                this.m_httpInputStream.close();
                this.m_httpInputStream = null;
            }
            if (null != this.m_ubMsgResponse) {
                this.m_ubMsgResponse = null;
            }
        }
    }
    
    public class SendStopThread implements Runnable
    {
        private HTTPConnection m_httpConnection;
        private HttpClientMsgOutputStream m_msgOutputStream;
        private long m_aiaMsgId;
        private HttpClientProtocol m_httpClient;
        
        public SendStopThread(final HttpClientProtocol httpClient, final HTTPConnection httpConnection, final HttpClientMsgOutputStream msgOutputStream, final long aiaMsgId) {
            this.m_httpConnection = null;
            this.m_msgOutputStream = null;
            this.m_aiaMsgId = -1L;
            this.m_httpClient = null;
            this.m_httpClient = httpClient;
            this.m_httpConnection = httpConnection;
            this.m_msgOutputStream = msgOutputStream;
            this.m_aiaMsgId = aiaMsgId;
        }
        
        public void run() {
            UBMessageResponse processHttpRequest = null;
            try {
                processHttpRequest = this.m_httpClient.processHttpRequest(this.m_httpConnection, this.m_msgOutputStream, "stop", this.m_aiaMsgId);
            }
            catch (Exception ex) {}
            finally {
                if (null != processHttpRequest) {
                    try {
                        processHttpRequest.m_httpInputStream.close();
                        processHttpRequest.m_httpInputStream = null;
                        processHttpRequest.m_ubMsgResponse = null;
                    }
                    catch (Exception ex2) {}
                }
            }
        }
    }
}
