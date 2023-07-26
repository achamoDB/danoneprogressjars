// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.ssl;

import java.util.Hashtable;
import java.net.InetAddress;
import com.progress.ubroker.util.ISSLSocketUtils;
import java.io.PrintStream;
import com.progress.common.ehnlog.ExtendedLogStream;
import com.progress.ubroker.util.ISSLParams;
import com.progress.ubroker.util.IubMsgOutputStream;
import java.io.IOException;
import com.progress.ubroker.util.IubMsgInputStream;
import com.progress.common.ehnlog.IAppLogger;
import com.progress.ubroker.util.NetworkProtocolException;
import java.util.Properties;
import com.progress.ubroker.util.INetworkProtocol;
import com.progress.ubroker.util.SocketConnectionInfoEx;
import java.net.Socket;
import com.progress.ubroker.client.TcpClientProtocol;

public class SSLClientProtocol extends TcpClientProtocol
{
    private Socket a;
    private String b;
    private int c;
    private SSLParamsFull d;
    private SSLSocketUtilsFull.SSLInfo e;
    private SocketConnectionInfoEx f;
    private SSLSocketUtilsFull g;
    private String h;
    private String i;
    
    public SSLClientProtocol() {
        this.a = null;
        this.b = null;
        this.c = 0;
        this.d = null;
        this.e = null;
        this.f = null;
        this.g = null;
        this.h = null;
        this.i = "";
        super.m_protocolType = 5;
        super.m_protocolTypeName = INetworkProtocol.m_protocolTypeNames[5];
        this.g = new SSLSocketUtilsFull();
    }
    
    public void openConnection(final SocketConnectionInfoEx f, final int n, final Properties properties, final Object o, final String s) throws Exception, NetworkProtocolException {
        super.openConnection(f, n, properties, o, s);
        this.f = f;
        this.a = this.rawSocket();
        this.c = this.a.getPort();
        this.b = this.a.getInetAddress().getHostName();
        this.setRawSocket(this.a(this.a));
    }
    
    public void closeConnection(final boolean b) throws Exception, NetworkProtocolException {
        super.closeConnection(b);
        if (this.d.isReusingSessions()) {
            SessionCache.getInstance().release(this.b, this.c);
        }
        if (!this.a.isClosed()) {
            this.a.close();
        }
    }
    
    public void init(final Properties properties, final IAppLogger appLogger, final int n) throws Exception, NetworkProtocolException {
        super.init(properties, appLogger, n);
        this.initSSLParams(properties);
    }
    
    public IubMsgInputStream getMsgInputStream(final int value) throws Exception {
        try {
            final IubMsgInputStream msgInputStream = super.getMsgInputStream(value);
            this.b();
            return msgInputStream;
        }
        catch (IOException ex) {
            super.m_loggingObj.logStackTrace(8318992936683450153L, new Object[] { new Integer(value), "(input stream)" }, ex);
            throw new NetworkProtocolException(4L, "SSL", ex.getMessage());
        }
    }
    
    public IubMsgOutputStream getMsgOutputStream(final int value) throws Exception {
        try {
            final IubMsgOutputStream msgOutputStream = super.getMsgOutputStream(value);
            this.b();
            return msgOutputStream;
        }
        catch (IOException ex) {
            super.m_loggingObj.logStackTrace(8318992936683450153L, new Object[] { new Integer(value), "(output stream)" }, ex);
            throw new NetworkProtocolException(4L, "SSL", ex.getMessage());
        }
    }
    
    private Socket a(final Socket socket) throws IOException {
        final SSLSocketUtilsFull sslSocketUtilsFull = new SSLSocketUtilsFull();
        if (this.d.isReusingSessions()) {
            final SSLSocketUtilsFull.SSLInfo value = SessionCache.getInstance().get(this.b, this.c);
            this.d.removeSession(this.b);
            if (value != null) {
                this.d.cacheSession(value.getVendorSession());
            }
        }
        return sslSocketUtilsFull.createSSLSocket(socket, this.d);
    }
    
    public void initSSLParams(final Properties properties) throws NetworkProtocolException {
        this.i = ((Hashtable<K, Object>)properties).get("PROGRESS.Session.poolName").toString();
        final PrintStream printStream = new ExtendedLogStream(super.m_loggingObj, super.m_debugLogEntries, super.m_debugLogIndex).getPrintStream();
        try {
            this.d = ClientParams.getInstance(this.i, properties, printStream);
            SessionCache.getInstance().setDebugStream(printStream);
        }
        catch (InvalidCertificateException ex) {
            super.m_loggingObj.logError(8318992936683450152L, new Object[] { "(" + ex.getMessage() + ")" });
            throw new NetworkProtocolException(2L, super.m_protocolTypeName, ex.getMessage());
        }
        catch (IOException ex2) {
            super.m_loggingObj.logStackTrace("ClientParams Initialization", ex2);
            super.m_loggingObj.logError(8318992936683450151L, new Object[] { ex2.toString() });
            throw new NetworkProtocolException(2L, super.m_protocolTypeName, ex2.getMessage());
        }
    }
    
    private void b() throws IOException, NetworkProtocolException {
        this.e = this.d();
        if (this.d.isReusingSessions()) {
            SessionCache.getInstance().put(this.b, this.c, this.e);
        }
        this.h = this.g.getSubjectName(this.e);
        this.a();
    }
    
    protected void a() throws IOException, NetworkProtocolException {
        if (this.c()) {
            return;
        }
        if (this.g.isDefaultCertificate(this.e)) {
            return;
        }
        this.a(this.g.getCommonNameField(this.h), InetAddress.getByName(this.f.getHost()).getCanonicalHostName());
    }
    
    private boolean c() {
        boolean b = false;
        try {
            if ("1".equals(this.getProtocolProperties().getProperty("PROGRESS.Session.noHostVerify", "0"))) {
                b = true;
            }
        }
        catch (Exception ex) {
            b = false;
        }
        return b;
    }
    
    private void a(final String anotherString, final String str) throws NetworkProtocolException {
        if (super.m_loggingObj.ifLogBasic(super.m_debugLogEntries, super.m_debugLogIndex)) {
            super.m_loggingObj.logBasic(super.m_debugLogIndex, "Testing server certificate domain name.");
        }
        if (anotherString == null || str == null || !str.equalsIgnoreCase(anotherString)) {
            final NetworkProtocolException ex = new NetworkProtocolException(5L, super.m_protocolTypeName, "The server domain 'CN=" + str + "' was not in the server certificate subject field.");
            super.m_loggingObj.logStackTrace(8318992936683450152L, new Object[] { "(host verify)" }, ex);
            throw ex;
        }
    }
    
    private SSLSocketUtilsFull.SSLInfo d() throws IOException {
        final SSLSocketUtilsFull.SSLInfo sslInfo = (SSLSocketUtilsFull.SSLInfo)new SSLSocketUtilsFull().getSocketSSLInfo(this.rawSocket());
        if (super.m_loggingObj.ifLogBasic(super.m_debugLogEntries, super.m_debugLogIndex)) {
            super.m_loggingObj.logBasic(super.m_debugLogIndex, sslInfo.toString(), null);
        }
        return sslInfo;
    }
    
    public String getSSLSubjectName() {
        return this.h;
    }
    
    public void release() throws Exception {
        super.release();
        ClientParams.release(this.i);
    }
}
