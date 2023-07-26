// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.util;

import java.net.MalformedURLException;
import com.progress.common.ehnlog.IAppLogger;

public final class NetworkProtocolFactory
{
    private static String m_HttpsLitePackage;
    private static String m_HttpsFullPackage;
    private static String[] m_clientClasses;
    private static String[] m_serverClasses;
    private static String[] m_listenerClasses;
    
    public static INetworkProtocol create(final int n, final SocketConnectionInfoEx socketConnectionInfoEx, final IAppLogger appLogger, final int n2) throws MalformedURLException, NullPointerException, NetworkProtocolException {
        if (null == socketConnectionInfoEx) {
            throw new NullPointerException("Must supply a non-null SocketConnectionInfo object");
        }
        if (null == socketConnectionInfoEx.getProtocol()) {
            throw new MalformedURLException("The URL must include a network protocol value");
        }
        return create(n, socketConnectionInfoEx.getProtocol(), appLogger, n2);
    }
    
    public static INetworkProtocol create(final int n, final String s, final IAppLogger appLogger, final int n2) throws NetworkProtocolException {
        INetworkProtocol networkProtocol = null;
        int n3 = -1;
        int n4 = -1;
        String s2 = INetworkProtocol.m_endPointTypeNames[3];
        String s3 = INetworkProtocol.m_protocolTypeNames[7];
        String className = null;
        String anotherString = s;
        if (0 <= n && 2 >= n) {
            n3 = n;
            s2 = INetworkProtocol.m_endPointTypeNames[n];
        }
        if (null == s) {
            anotherString = "tcp";
        }
        for (int i = 0; i <= 6; ++i) {
            if (INetworkProtocol.m_protocolTypeNames[i].equalsIgnoreCase(anotherString)) {
                n4 = i;
                s3 = INetworkProtocol.m_protocolTypeNames[i];
                break;
            }
        }
        if (-1 == n3 || -1 == n4) {
            if (null != appLogger) {
                appLogger.logError("NetworkProtocolFactory detected invalid end-point(" + n3 + ")/target-protocol(" + n4 + ") types.");
            }
            throw new NetworkProtocolException(1L, s3, s2);
        }
        if (n3 == 0) {
            className = NetworkProtocolFactory.m_clientClasses[n4];
        }
        else if (n3 != 0) {
            className = NetworkProtocolFactory.m_serverClasses[n4];
        }
        else if (2 == n3) {
            className = NetworkProtocolFactory.m_listenerClasses[n4];
        }
        if (null == className || 0 == className.length()) {
            if (null != appLogger) {
                appLogger.logError("NetworkProtocolFactory could not find a XxxxClientProtocol.class file for the end-point(" + n3 + ")/target-protocol(" + n4 + ") types.");
            }
            throw new NetworkProtocolException(1L, s3, s2);
        }
        try {
            if (className.startsWith("Https")) {
                try {
                    networkProtocol = (INetworkProtocol)Class.forName(NetworkProtocolFactory.m_HttpsFullPackage).newInstance();
                }
                catch (Throwable t3) {}
                if (null != networkProtocol) {
                    return networkProtocol;
                }
                try {
                    networkProtocol = (INetworkProtocol)Class.forName(NetworkProtocolFactory.m_HttpsLitePackage).newInstance();
                    return networkProtocol;
                }
                catch (Throwable t) {
                    throw t;
                }
            }
            networkProtocol = (INetworkProtocol)Class.forName(className).newInstance();
        }
        catch (Throwable t2) {
            if (null != appLogger) {
                appLogger.logStackTrace("NetworkProtcolFactory.create()", t2);
            }
            throw new NetworkProtocolException(3L, s3, t2.toString());
        }
        return networkProtocol;
    }
    
    static {
        NetworkProtocolFactory.m_HttpsLitePackage = "com.progress.ubroker.ssllite.HttpsLiteClientProtocol";
        NetworkProtocolFactory.m_HttpsFullPackage = "com.progress.ubroker.ssl.HttpsClientProtocol";
        NetworkProtocolFactory.m_clientClasses = new String[] { "com.progress.ubroker.client.TcpClientProtocol", "com.progress.ubroker.client.TcpClientProtocol", "com.progress.ubroker.client.HttpClientProtocol", "HttpsClientProtocol", "com.progress.ubroker.client.TcpClientProtocol", "com.progress.ubroker.ssl.SSLClientProtocol", "com.progress.ubroker.ssl.SSLClientProtocol", "", "", "" };
        NetworkProtocolFactory.m_serverClasses = new String[] { "", "", "", "", "", "", "", "", "", "" };
        NetworkProtocolFactory.m_listenerClasses = new String[] { "", "", "", "", "", "", "", "", "", "" };
    }
}
