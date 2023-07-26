// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.client;

import java.io.IOException;
import com.progress.ubroker.util.MsgInputStream;
import com.progress.nameserver.client.NameServerClient;
import com.progress.ubroker.util.SocketConnectionInfoEx;
import com.progress.common.ehnlog.IAppLogger;
import java.util.Properties;
import com.progress.ubroker.util.IubMsgOutputStream;
import com.progress.ubroker.util.NetworkProtocolException;
import com.progress.ubroker.util.IubMsgInputStream;
import com.progress.ubroker.util.MsgReader;
import com.progress.ubroker.util.RequestQueue;
import java.net.Socket;
import com.progress.ubroker.util.ubConstants;
import com.progress.ubroker.util.INetworkProtocol;

public class TcpClientProtocol extends NetworkClientProtocol implements INetworkProtocol, ubConstants
{
    private Socket m_appServerSocket;
    private RequestQueue m_requestQueue;
    private MsgReader m_msgReader;
    
    public TcpClientProtocol() {
        this.m_appServerSocket = null;
        this.m_requestQueue = null;
        this.m_msgReader = null;
    }
    
    public Socket rawSocket() {
        return this.m_appServerSocket;
    }
    
    public IubMsgInputStream getMsgInputStream(final int n) throws Exception {
        IubMsgInputStream iubMsgInputStream = null;
        switch (n) {
            case 0: {
                if (null != this.m_appServerSocket) {
                    iubMsgInputStream = new TcpClientMsgInputStream(this, this.m_appServerSocket.getInputStream(), super.m_progressServerType);
                }
                return iubMsgInputStream;
            }
            default: {
                final NetworkProtocolException ex = new NetworkProtocolException(0L, "TCP", "Unsupported Input Stream type requested.");
                super.m_loggingObj.logStackTrace("", ex);
                throw ex;
            }
        }
    }
    
    public IubMsgOutputStream getMsgOutputStream(final int n) throws Exception {
        IubMsgOutputStream iubMsgOutputStream = null;
        switch (n) {
            case 0: {
                if (null != this.m_appServerSocket) {
                    iubMsgOutputStream = new TcpClientMsgOutputStream(this, this.m_appServerSocket.getOutputStream(), super.m_progressServerType);
                }
                return iubMsgOutputStream;
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
        if (s.equalsIgnoreCase("PROGRESS.Session.socketTimeout") && null != s2 && 0 < s2.length()) {
            try {
                final int int1 = Integer.parseInt(s2);
                if (null != this.m_appServerSocket && int1 > 0) {
                    this.m_appServerSocket.setSoTimeout(int1);
                }
            }
            catch (Exception ex) {
                if (super.m_loggingObj.ifLogBasic(super.m_debugLogEntries, super.m_debugLogIndex)) {
                    super.m_loggingObj.logBasic(super.m_debugLogIndex, "Error setting property psc.tcp.sotimeout: " + ex.getMessage());
                }
            }
        }
    }
    
    protected void finalize() throws Throwable {
        try {
            this.release();
        }
        catch (Exception ex) {}
    }
    
    public void init(final Properties properties, final IAppLogger appLogger, final int n) throws Exception, NetworkProtocolException {
        super.init(properties, appLogger, n);
        if (Integer.parseInt(properties.getProperty("PROGRESS.Session.disableReadThread", "1")) == 0) {
            this.m_requestQueue = new RequestQueue(super.m_protocolTypeName, 0, super.m_loggingObj);
        }
    }
    
    public void release() throws Exception {
        this.closeConnection(true);
        if (this.m_msgReader != null) {
            this.m_msgReader.close();
            this.m_msgReader = null;
        }
        if (this.m_requestQueue != null) {
            this.m_requestQueue.close();
            this.m_requestQueue = null;
        }
        super.m_loggingObj = null;
        super.m_protocolProperties = null;
    }
    
    public void resolveConnectionInfo(final SocketConnectionInfoEx socketConnectionInfoEx) throws Exception {
        if (socketConnectionInfoEx.isDirectConnect()) {
            return;
        }
        final NameServerClient.Broker broker = new NameServerClient(socketConnectionInfoEx.getHost(), socketConnectionInfoEx.getPort(), "AS").getBroker(socketConnectionInfoEx.getService());
        if (broker != null) {
            socketConnectionInfoEx.setHost(broker.getHost());
            socketConnectionInfoEx.setPort(broker.getPort());
        }
    }
    
    public void openConnection(final SocketConnectionInfoEx socketConnectionInfoEx, final int progressServerType, final Properties properties, final Object o, final String s) throws Exception, NetworkProtocolException {
        if (null != this.m_appServerSocket) {
            final NetworkProtocolException ex = new NetworkProtocolException(10L, "TCP", "A connection is already established");
            super.m_loggingObj.logStackTrace("", ex);
            throw ex;
        }
        if (null == socketConnectionInfoEx) {
            final NullPointerException ex2 = new NullPointerException("Cannot initialize with a null Socket Connection Information");
            super.m_loggingObj.logStackTrace("", ex2);
            throw ex2;
        }
        try {
            if (super.m_loggingObj.ifLogBasic(super.m_debugLogEntries, super.m_debugLogIndex)) {
                super.m_loggingObj.logBasic(super.m_debugLogIndex, "Creating new Socket connection...");
            }
            this.m_appServerSocket = new Socket(socketConnectionInfoEx.getHost(), socketConnectionInfoEx.getPort());
            final String property = super.m_protocolProperties.getProperty("PROGRESS.Session.socketTimeout");
            if (null != property) {
                this.setDynamicProtocolProperty("PROGRESS.Session.socketTimeout", property);
            }
            if (this.m_requestQueue != null) {
                this.m_msgReader = MsgReader.newMsgReader(super.m_protocolTypeName, this.m_appServerSocket, new MsgInputStream(this.m_appServerSocket.getInputStream(), 10240, super.m_progressServerType, super.m_loggingObj), this.m_requestQueue, super.m_loggingObj);
            }
        }
        catch (Exception ex3) {
            this.m_appServerSocket = null;
            throw ex3;
        }
        super.m_progressServerType = progressServerType;
    }
    
    public void closeConnection(final boolean b) throws Exception, NetworkProtocolException {
        if (null != this.m_appServerSocket) {
            try {
                if (null != this.m_appServerSocket) {
                    this.m_appServerSocket.close();
                }
            }
            catch (Exception ex) {
                if (!b) {
                    throw ex;
                }
            }
            this.m_appServerSocket = null;
            if (this.m_msgReader != null) {
                this.m_msgReader.close();
                this.m_msgReader = null;
            }
            super.m_endPointType = 0;
            super.m_protocolType = 1;
            super.m_progressServerType = 2;
        }
    }
    
    public String getSSLSubjectName() {
        return null;
    }
    
    public MsgReader getReader() {
        return this.m_msgReader;
    }
    
    public RequestQueue getQueue() {
        return this.m_requestQueue;
    }
    
    protected void setRawSocket(final Socket appServerSocket) {
        this.m_appServerSocket = appServerSocket;
        try {
            if (this.m_requestQueue != null) {
                this.m_msgReader = null;
                this.m_msgReader = MsgReader.newMsgReader(super.m_protocolTypeName, this.m_appServerSocket, new MsgInputStream(this.m_appServerSocket.getInputStream(), 10240, super.m_progressServerType, super.m_loggingObj), this.m_requestQueue, super.m_loggingObj);
            }
        }
        catch (IOException ex) {
            this.m_msgReader = null;
            this.m_requestQueue = null;
        }
    }
}
