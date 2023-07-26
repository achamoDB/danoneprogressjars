// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.ssl;

import com.oroinc.text.regex.Pattern;
import com.oroinc.text.regex.Perl5Matcher;
import java.util.StringTokenizer;
import com.oroinc.text.regex.Perl5Compiler;
import com.progress.ubroker.util.NetworkProtocolOptions;
import java.io.IOException;
import java.io.EOFException;
import com.progress.ubroker.client.HttpClientMsgOutputStream;
import HTTPClient.HTTPConnection;
import com.progress.ubroker.util.SocketConnectionInfoEx;
import java.io.OutputStream;
import com.progress.ubroker.util.NetworkProtocolException;
import com.progress.common.ehnlog.IAppLogger;
import java.util.Properties;
import com.progress.ubroker.util.INetworkProtocol;
import com.progress.ubroker.util.ISSLSocketUtils;
import com.progress.ubroker.util.ISSLParams;
import com.progress.ubroker.client.HttpClientProtocol;

public final class HttpsClientProtocol extends HttpClientProtocol
{
    public String m_ISSLParamsClassName;
    public String m_ISSLSocketUtilsClassName;
    private ISSLParams a;
    private ISSLSocketUtils b;
    private ISSLSocketUtils.ISSLInfo c;
    private boolean d;
    private boolean e;
    
    public HttpsClientProtocol() {
        this.m_ISSLParamsClassName = null;
        this.m_ISSLSocketUtilsClassName = null;
        this.a = null;
        this.b = null;
        this.c = null;
        this.d = false;
        this.e = false;
        super.m_protocolType = 3;
        super.m_protocolTypeName = INetworkProtocol.m_protocolTypeNames[3];
    }
    
    public void init(final Properties properties, final IAppLogger appLogger, final int n) throws Exception, NetworkProtocolException {
        super.init(properties, appLogger, n);
        this.d = false;
        try {
            this.a = new SSLParamsFull();
            this.b = new SSLSocketUtilsFull();
        }
        catch (Throwable t) {
            super.m_loggingObj.logStackTrace("Missing the SSLParamsFull and/or SSLSocketUtilsFull classes", t);
            throw new NetworkProtocolException(3L, super.m_protocolTypeName, "");
        }
        this.a();
        try {
            this.a.init(properties, null, 6);
        }
        catch (Exception ex) {
            super.m_loggingObj.logStackTrace("SSLParamsFull init error.", ex);
            throw new NetworkProtocolException(2L, super.m_protocolTypeName, ex.toString());
        }
        this.b();
    }
    
    public void openConnection(final SocketConnectionInfoEx socketConnectionInfoEx, final int n, final Properties properties, final Object o, final String s) throws Exception, NetworkProtocolException {
        super.openConnection(socketConnectionInfoEx, n, properties, o, s);
        super.m_httpClient.setSSLParams(this.a);
        super.m_httpClient.setSSLSocketUtils(this.b);
        try {
            this.c();
        }
        catch (Exception ex) {
            try {
                this.closeHttpConnection(super.m_httpClient, this);
            }
            catch (Exception ex2) {}
            throw ex;
        }
    }
    
    public void closeHttpConnection(final HTTPConnection httpConnection, final Object o) throws Exception {
        super.closeHttpConnection(httpConnection, o);
        this.c = null;
    }
    
    public synchronized void sendUbMessage(final HttpClientMsgOutputStream httpClientMsgOutputStream) throws IOException, NetworkProtocolException {
        if (super.m_logicalConnectionOpen && null == super.m_aiaConnectionId) {
            throw new NetworkProtocolException(10L, super.m_protocolTypeName, "No " + super.m_protocolTypeName + " connection exists");
        }
        if (null == super.m_httpClient) {
            super.m_httpClient = this.newHttpConnection(this);
        }
        try {
            boolean b = true;
            boolean b2 = false;
            final UBMessageResponse sendHttpRequest = this.sendHttpRequest(super.m_httpClient, httpClientMsgOutputStream, "AppServer", super.m_aiaMsgId++);
            if (4 == sendHttpRequest.m_ubMsgRequestCode || 5 == sendHttpRequest.m_ubMsgRequestCode || 6 == sendHttpRequest.m_ubMsgRequestCode || 3 == sendHttpRequest.m_ubMsgRequestCode) {
                b2 = true;
            }
            if (b2) {
                this.getHttpResponse(super.m_httpClient, sendHttpRequest);
                final String header = sendHttpRequest.m_ubMsgResponse.getHeader("Content-Length");
                if (null != header && 0 == header.compareTo("0")) {
                    b = false;
                }
            }
            if (b) {
                synchronized (super.m_ubMsgResponses) {
                    super.m_ubMsgResponses.addElement(sendHttpRequest);
                }
            }
            else {
                sendHttpRequest.m_httpInputStream.close();
                sendHttpRequest.m_httpInputStream = null;
                sendHttpRequest.m_ubMsgResponse = null;
            }
        }
        catch (EOFException ex2) {
            try {
                this.closeHttpConnection(super.m_httpClient, this);
            }
            catch (Exception ex7) {}
            final NetworkProtocolException ex = new NetworkProtocolException(8L, super.m_protocolTypeName, ex2.getMessage());
            super.m_loggingObj.logStackTrace("", ex);
            throw ex;
        }
        catch (psc_b psc_b) {
            final int a = this.a(psc_b.b());
            if (-1 != a) {
                final NetworkProtocolException ex3 = new NetworkProtocolException(a, super.m_protocolTypeName, psc_b.a(psc_b.b()));
                super.m_loggingObj.logStackTrace("", ex3);
                try {
                    this.closeHttpConnection(super.m_httpClient, this);
                }
                catch (Exception ex8) {}
                throw ex3;
            }
            if (super.m_loggingObj.ifLogBasic(2L, 1)) {
                super.m_loggingObj.logBasic(1, "SSL server reported error: " + psc_b.a(psc_b.b()));
            }
        }
        catch (psc_c psc_c) {
            final int b3 = this.b(psc_c.b());
            if (-1 != b3) {
                final NetworkProtocolException ex4 = new NetworkProtocolException(b3, super.m_protocolTypeName, psc_c.getMessage());
                super.m_loggingObj.logStackTrace("", ex4);
                try {
                    this.closeHttpConnection(super.m_httpClient, this);
                }
                catch (Exception ex9) {}
                throw ex4;
            }
            if (super.m_loggingObj.ifLogVerbose(1L, 0)) {
                super.m_loggingObj.logVerbose(0, "SSL reported error: " + psc_c.getMessage());
            }
        }
        catch (IOException ex5) {
            if (super.m_loggingObj.ifLogBasic(2L, 1)) {
                super.m_loggingObj.logBasic(1, "Detected I/O error posting (write) message: " + ex5.getMessage());
            }
            throw ex5;
        }
        catch (Exception ex6) {
            super.m_loggingObj.logStackTrace("", ex6);
            throw new NetworkProtocolException(10L, super.m_protocolTypeName, ex6.toString());
        }
    }
    
    public void sendStopMessage(final HttpClientMsgOutputStream httpClientMsgOutputStream) throws IOException, NetworkProtocolException {
        if (null == super.m_aiaConnectionId) {
            throw new NetworkProtocolException(10L, super.m_protocolTypeName, "No " + super.m_protocolTypeName + " connection exists");
        }
        final Long n = new Long(System.currentTimeMillis());
        HTTPConnection httpConnection;
        try {
            httpConnection = this.newHttpConnection(n);
        }
        catch (NetworkProtocolException ex) {
            if (super.m_loggingObj.ifLogBasic(2L, 1)) {
                super.m_loggingObj.logBasic(1, "Error creating new http stop connection: " + ex.toString());
            }
            throw ex;
        }
        try {
            final Thread thread = new Thread(new SendStopThread(this, httpConnection, httpClientMsgOutputStream, super.m_aiaStopMsgId--));
            if (super.m_loggingObj.ifLogBasic(2L, 1)) {
                super.m_loggingObj.logBasic(1, "Starting Http stop thread operation.");
            }
            thread.start();
            thread.join();
            if (super.m_loggingObj.ifLogBasic(2L, 1)) {
                super.m_loggingObj.logBasic(1, "Http stop thread operation finished.");
            }
            if (null != super.m_msgInputStream) {
                super.m_msgInputStream.setStop();
            }
        }
        catch (Exception ex2) {
            super.m_loggingObj.logStackTrace("", ex2);
            throw new NetworkProtocolException(10L, super.m_protocolTypeName, ex2.toString());
        }
        finally {
            if (null != httpConnection) {
                try {
                    this.closeHttpConnection(httpConnection, n);
                }
                catch (Exception ex3) {
                    if (super.m_loggingObj.ifLogBasic(2L, 1)) {
                        super.m_loggingObj.logBasic(1, "Error closing http connection: " + ex3.toString());
                    }
                }
            }
        }
    }
    
    protected void a() throws NetworkProtocolException {
        try {
            Class.forName("java.applet");
            this.e = true;
        }
        catch (Throwable t) {}
        if (super.m_loggingObj.ifLogBasic(2L, 1)) {
            final StringBuffer sb = new StringBuffer("HTTPS environment: SSLLite = ");
            sb.append(this.d ? "true" : "false");
            sb.append("; run as servlet = ");
            sb.append(this.e ? "true" : "false");
            super.m_loggingObj.logBasic(1, sb.toString());
        }
    }
    
    protected void b() throws NetworkProtocolException {
        int loadAuthenticationCertificates;
        try {
            String property = super.m_protocolProperties.getProperty("PROGRESS.Session.certificateStore");
            if (null == property || 0 == property.length()) {
                if (this.e) {
                    property = "psccerts.dcl";
                }
                else if (-1 != System.getProperty("java.vendor").indexOf("Microsoft")) {
                    property = "psccerts.zip";
                }
                else {
                    property = "psccerts.jar";
                }
            }
            final StringBuffer sb = new StringBuffer();
            if (this.e) {
                sb.append("-a -p ");
            }
            sb.append("-i ");
            if (NetworkProtocolOptions.getBooleanProperty(null, "psc.certloader.debug", false)) {
                sb.append("-d ");
            }
            sb.append(property);
            if (super.m_loggingObj.ifLogBasic(2L, 1)) {
                super.m_loggingObj.logBasic(1, "Loading certificates: " + sb.toString());
            }
            loadAuthenticationCertificates = this.a.loadAuthenticationCertificates(sb.toString());
        }
        catch (Exception ex) {
            super.m_loggingObj.logStackTrace("", ex);
            final NetworkProtocolException ex2 = new NetworkProtocolException(2L, super.m_protocolTypeName, ex.toString());
            try {
                this.closeConnection(true);
            }
            catch (Exception ex4) {}
            throw ex2;
        }
        if (loadAuthenticationCertificates == 0) {
            final NetworkProtocolException ex3 = new NetworkProtocolException(2L, super.m_protocolTypeName, "Cannot proceed without Digital Certificates to authenticate the server");
            super.m_loggingObj.logStackTrace("", ex3);
            try {
                this.closeConnection(true);
            }
            catch (Exception ex5) {}
            throw ex3;
        }
    }
    
    protected void c() throws IOException, NetworkProtocolException {
        final UBMessageResponse ubMessageResponse = new UBMessageResponse();
        try {
            if (super.m_loggingObj.ifLogBasic(2L, 1)) {
                super.m_loggingObj.logBasic(1, "Test Connection message to aia: " + super.m_connectURL.getPath());
            }
            ubMessageResponse.m_ubMsgResponse = super.m_httpClient.Get(super.m_connectURL.getPath());
            ubMessageResponse.m_sendMsgResponseCode = ubMessageResponse.m_ubMsgResponse.getStatusCode();
            try {
                ubMessageResponse.m_ubMsgResponse.getData();
            }
            catch (Exception ex9) {}
        }
        catch (psc_b psc_b) {
            final int a = this.a(psc_b.b());
            if (-1 != a) {
                final NetworkProtocolException ex = new NetworkProtocolException(a, super.m_protocolTypeName, psc_b.a(psc_b.b()));
                super.m_loggingObj.logStackTrace("", ex);
                throw ex;
            }
            if (super.m_loggingObj.ifLogBasic(2L, 1)) {
                super.m_loggingObj.logBasic(1, "SSL server reported error: " + psc_b.a(psc_b.b()));
            }
        }
        catch (psc_c psc_c) {
            final int b = this.b(psc_c.b());
            if (-1 == b) {
                if (super.m_loggingObj.ifLogVerbose(1L, 0)) {
                    super.m_loggingObj.logVerbose(0, "SSL reported error: " + psc_c.getMessage());
                }
            }
            else {
                super.m_loggingObj.logStackTrace("", new NetworkProtocolException(b, super.m_protocolTypeName, psc_c.getMessage()));
            }
        }
        catch (IOException ex2) {
            String s = ex2.toString();
            if (super.m_loggingObj.ifLogBasic(2L, 1)) {
                super.m_loggingObj.logBasic(1, "Detected I/O error on Get (testConnection) message: " + ex2.toString());
            }
            if (s.startsWith("java.net.")) {
                s = s.substring(9);
            }
            else if (s.startsWith("java.io.")) {
                s = s.substring(8);
            }
            throw new NetworkProtocolException(4L, super.m_protocolTypeName, s);
        }
        catch (Exception ex3) {
            super.m_loggingObj.logStackTrace("", ex3);
            throw new NetworkProtocolException(10L, super.m_protocolTypeName, ex3.toString());
        }
        if (this.evaluateResponseCode(ubMessageResponse)) {
            final NetworkProtocolException ex4 = new NetworkProtocolException(ubMessageResponse.m_sendMsgExceptionMsgCode, ubMessageResponse.m_sendMsgExceptionErrorDetail);
            super.m_loggingObj.logStackTrace("", ex4);
            throw ex4;
        }
        if (super.m_loggingObj.ifLogBasic(2L, 1)) {
            super.m_loggingObj.logBasic(1, "Test Connection message to aia succeeded.");
        }
        if (super.m_loggingObj.ifLogBasic(2L, 1)) {
            super.m_loggingObj.logBasic(1, "Getting HTTPS server information...");
        }
        this.c = super.m_httpClient.getSSLSocketInfo();
        if (null == this.a) {
            final NetworkProtocolException ex5 = new NetworkProtocolException(5L, super.m_protocolTypeName, "Unable to establish server identity");
            super.m_loggingObj.logStackTrace("", ex5);
            throw ex5;
        }
        if (super.m_loggingObj.ifLogBasic(2L, 1)) {
            final StringBuffer sb = new StringBuffer("SSL connection params\n");
            sb.append("Protocol version: ");
            sb.append(Integer.toString(this.c.getVersion()));
            sb.append("\n");
            sb.append("Protocol cipher suite: ");
            sb.append(this.c.getCipherSuite());
            sb.append("\n");
            sb.append("Protocol session-id: ");
            sb.append(this.c.getSessionId());
            sb.append("\n");
            sb.append("Protocol peer: ");
            sb.append(this.c.getPeerName());
            sb.append("\n");
            final ISSLSocketUtils.IPeerCertificateInfo[] peerCertificateInfo = this.c.getPeerCertificateInfo();
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
            super.m_loggingObj.logBasic(1, sb.toString());
        }
        if (!this.d()) {
            if (super.m_loggingObj.ifLogBasic(2L, 1)) {
                super.m_loggingObj.logBasic(1, "Testing server's certificate domain name.");
            }
            final String subjectName = this.c.getPeerCertificateInfo()[0].getSubjectName();
            final String host = super.m_connectURL.getHost();
            final Perl5Compiler perl5Compiler = new Perl5Compiler();
            String str = super.m_protocolProperties.getProperty("psc.https.auth.domain");
            String s2 = null;
            final StringTokenizer stringTokenizer = new StringTokenizer(subjectName, ",");
            Label_1146: {
                if (null != str && 0 != str.length()) {
                    break Label_1146;
                }
                str = "CN=" + host;
                try {
                    String nextToken;
                    do {
                        nextToken = stringTokenizer.nextToken();
                    } while (!nextToken.startsWith("CN=") && !nextToken.startsWith("cn="));
                    s2 = new String(nextToken);
                }
                catch (Exception ex10) {}
            }
            if (null == s2) {
                final NetworkProtocolException ex6 = new NetworkProtocolException(5L, super.m_protocolTypeName, "The server domain 'CN=" + host + "' was not in the server certificate subject field.");
                super.m_loggingObj.logStackTrace("", ex6);
                throw ex6;
            }
            Pattern compile;
            try {
                compile = perl5Compiler.compile(str, 32785);
            }
            catch (Exception ex7) {
                super.m_loggingObj.logStackTrace("", ex7);
                throw new NetworkProtocolException(5L, super.m_protocolTypeName, "Internal failure in the server domain authentication.");
            }
            if (!new Perl5Matcher().matches(s2, compile)) {
                final NetworkProtocolException ex8 = new NetworkProtocolException(5L, super.m_protocolTypeName, "The server domain '" + str + "' was not in the server certificate subject field.");
                super.m_loggingObj.logStackTrace("", ex8);
                throw ex8;
            }
        }
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
    
    private boolean d() {
        return NetworkProtocolOptions.getIntProperty(super.m_protocolProperties, "PROGRESS.Session.noHostVerify", 0) == 1;
    }
}
