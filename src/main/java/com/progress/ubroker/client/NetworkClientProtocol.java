// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.client;

import java.util.Hashtable;
import com.progress.ubroker.util.IubMsgOutputStream;
import com.progress.ubroker.util.IubMsgInputStream;
import java.net.Socket;
import com.progress.ubroker.util.SocketConnectionInfoEx;
import com.progress.ubroker.util.NetworkProtocolException;
import com.progress.common.ehnlog.AppLogger;
import com.progress.common.ehnlog.IAppLogger;
import java.util.Properties;
import com.progress.ubroker.util.INetworkProtocol;

public abstract class NetworkClientProtocol implements INetworkProtocol
{
    protected Properties m_protocolProperties;
    protected int m_endPointType;
    protected int m_protocolType;
    protected String m_protocolTypeName;
    protected String m_endPointTypeName;
    protected IAppLogger m_loggingObj;
    protected int m_loggingDest;
    protected int m_progressServerType;
    protected long m_debugLogEntries;
    protected int m_debugLogIndex;
    
    public NetworkClientProtocol() {
        this.m_protocolProperties = null;
        this.m_endPointType = 0;
        this.m_protocolType = 0;
        this.m_protocolTypeName = INetworkProtocol.m_protocolTypeNames[0];
        this.m_endPointTypeName = INetworkProtocol.m_endPointTypeNames[0];
        this.m_loggingObj = null;
        this.m_loggingDest = 0;
        this.m_progressServerType = 2;
    }
    
    public void init(final Properties defaults, final IAppLogger loggingObj, final int loggingDest) throws Exception, NetworkProtocolException {
        this.m_protocolProperties = new Properties(defaults);
        this.m_loggingDest = loggingDest;
        if (null == loggingObj) {
            this.m_loggingObj = new AppLogger();
        }
        else {
            this.m_loggingObj = loggingObj;
        }
        this.initializeLogging(this.m_loggingObj);
    }
    
    public abstract void release() throws Exception;
    
    public abstract void resolveConnectionInfo(final SocketConnectionInfoEx p0) throws Exception;
    
    public abstract void openConnection(final SocketConnectionInfoEx p0, final int p1, final Properties p2, final Object p3, final String p4) throws Exception, NetworkProtocolException;
    
    public abstract void closeConnection(final boolean p0) throws Exception, NetworkProtocolException;
    
    public abstract Socket rawSocket();
    
    public abstract IubMsgInputStream getMsgInputStream(final int p0) throws Exception;
    
    public abstract IubMsgOutputStream getMsgOutputStream(final int p0) throws Exception;
    
    public int endPointType() {
        return this.m_endPointType;
    }
    
    public int protocolType() {
        return this.m_protocolType;
    }
    
    public String protocolName() {
        return this.m_protocolTypeName;
    }
    
    public String endPointName() {
        return this.m_endPointTypeName;
    }
    
    public IAppLogger loggingObject() {
        return this.m_loggingObj;
    }
    
    public int loggingDestination() {
        return this.m_loggingDest;
    }
    
    public int progressServerType() {
        return this.m_progressServerType;
    }
    
    public Properties getProtocolProperties() throws Exception {
        return this.m_protocolProperties;
    }
    
    public void setDynamicProtocolProperty(final String s, final String value) throws Exception {
        if (null != s && 0 < s.length()) {
            if (null == value) {
                if (this.m_protocolProperties.containsKey(s)) {
                    this.m_protocolProperties.remove(s);
                }
            }
            else if (this.m_protocolProperties.containsKey(s)) {
                if (0 != this.m_protocolProperties.getProperty(s).compareTo(value)) {
                    ((Hashtable<String, String>)this.m_protocolProperties).put(s, value);
                }
            }
            else {
                ((Hashtable<String, String>)this.m_protocolProperties).put(s, value);
            }
            return;
        }
        final NullPointerException ex = new NullPointerException("Cannot set an unnamed runtime property");
        this.m_loggingObj.logStackTrace("", ex);
        throw ex;
    }
    
    protected void initializeLogging(final IAppLogger appLogger) {
        final String logContextName = appLogger.getLogContext().getLogContextName();
        if (logContextName.equals("Wsa")) {
            this.m_debugLogEntries = 8589934592L;
            this.m_debugLogIndex = 33;
        }
        else if (logContextName.equals("O4gl")) {
            this.m_debugLogEntries = 8589934592L;
            this.m_debugLogIndex = 10;
        }
        else if (logContextName.equals("UBroker")) {
            this.m_debugLogEntries = 2L;
            this.m_debugLogIndex = 1;
        }
        else {
            this.m_debugLogEntries = 0L;
            this.m_debugLogIndex = 0;
        }
    }
}
