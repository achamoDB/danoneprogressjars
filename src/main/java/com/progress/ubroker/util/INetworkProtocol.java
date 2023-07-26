// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.util;

import java.net.Socket;
import com.progress.common.ehnlog.IAppLogger;
import java.util.Properties;

public interface INetworkProtocol
{
    public static final int END_POINT_CLIENT = 0;
    public static final int END_POINT_SERVER = 1;
    public static final int END_POINT_LISTENER = 2;
    public static final int END_POINT_UNSUPPORTED = 3;
    public static final int END_POINT_MIN = 0;
    public static final int END_POINT_MAX = 2;
    public static final String[] m_endPointTypeNames = { "client", "server", "listener", "unsupported" };
    public static final int PROGRESS_SERVER_BROKER = 0;
    public static final int PROGRESS_SERVER_SERVER = 1;
    public static final int PROGRESS_SERVER_AIA = 2;
    public static final String[] m_progressServerNames = { "uBroker", "Server", "AIA" };
    public static final int PROTOCOL_TCP = 0;
    public static final int PROTOCOL_APPSERVER = 1;
    public static final int PROTOCOL_HTTP_TUNNEL = 2;
    public static final int PROTOCOL_HTTPS_TUNNEL = 3;
    public static final int PROTOCOL_APPSERVERDC = 4;
    public static final int PROTOCOL_APPSERVER_SSL = 5;
    public static final int PROTOCOL_APPSERVERDC_SSL = 6;
    public static final int PROTOCOL_UNSUPPORTED = 7;
    public static final int PROTOCOL_MIN = 0;
    public static final int PROTOCOL_MAX = 6;
    public static final String[] m_protocolTypeNames = { "tcp", "appserver", "http", "https", "appserverDC", "AppServerS", "AppServerDCS", "unsupported" };
    public static final int MSG_STREAM_UB_BINARY = 0;
    public static final int MSG_STREAM_MIN = 0;
    public static final int MSG_STREAM_MAX = 0;
    public static final String[] m_streamTypeNames = { "binary UBroker" };
    
    void init(final Properties p0, final IAppLogger p1, final int p2) throws Exception, NetworkProtocolException;
    
    void release() throws Exception;
    
    void resolveConnectionInfo(final SocketConnectionInfoEx p0) throws Exception;
    
    void openConnection(final SocketConnectionInfoEx p0, final int p1, final Properties p2, final Object p3, final String p4) throws Exception, NetworkProtocolException;
    
    void closeConnection(final boolean p0) throws Exception, NetworkProtocolException;
    
    Socket rawSocket();
    
    IubMsgInputStream getMsgInputStream(final int p0) throws Exception;
    
    IubMsgOutputStream getMsgOutputStream(final int p0) throws Exception;
    
    int endPointType();
    
    int protocolType();
    
    String protocolName();
    
    String endPointName();
    
    IAppLogger loggingObject();
    
    int loggingDestination();
    
    int progressServerType();
    
    Properties getProtocolProperties() throws Exception;
    
    void setDynamicProtocolProperty(final String p0, final String p1) throws Exception;
    
    String getSSLSubjectName();
}
