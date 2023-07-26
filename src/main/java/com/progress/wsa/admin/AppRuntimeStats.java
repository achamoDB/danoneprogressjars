// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa.admin;

import java.util.Hashtable;
import com.progress.wsa.open4gl.proxy.WSAProxyPool;
import com.progress.wsa.RuntimeStats;

public class AppRuntimeStats extends RuntimeStats
{
    public static final String[] m_appStatisticNames;
    public static final int REQUESTS = 0;
    public static final int FAULTS = 1;
    public static final int ACTIVE_REQUESTS = 2;
    public static final int OBJ_NOT_FOUND = 3;
    public static final int CLIENT_ERROR = 4;
    public static final int PROVIDER_ERROR = 5;
    public static final int CONNECT_ERROR = 6;
    public static final int FGL_CLIENT_ERROR = 7;
    public static final int FGL_SERVER_ERROR = 8;
    public static final int FGL_APP_ERROR = 9;
    public static final int NAMESERVER_ERROR = 10;
    public static final int OBJ_POOL_FULL = 11;
    public static final int OBJ_POOL_EXPIRED = 12;
    public static final int APPSERVER_CONNECTIONS = 13;
    public static final int APPOBJECTS = 14;
    public static final int SUBAPPOBJECTS = 15;
    public static final int PROCOBJECTS = 16;
    
    public AppRuntimeStats() {
        super("app-stats", AppRuntimeStats.m_appStatisticNames);
    }
    
    public Hashtable getStatistics(final WSAProxyPool wsaProxyPool) {
        final Hashtable statistics = super.getStatistics();
        if (null != wsaProxyPool) {
            statistics.put("appserverConnections", new Integer(wsaProxyPool.appserverConnectionCount()));
            statistics.put("appObjects", new Integer(wsaProxyPool.appObjectCount()));
            statistics.put("subAppObjects", new Integer(wsaProxyPool.subappObjectCount()));
            statistics.put("procObjects", new Integer(wsaProxyPool.procObjectCount()));
        }
        return statistics;
    }
    
    static {
        m_appStatisticNames = new String[] { "requests", "faults", "active-requests", "obj-not-found", "client-error", "provider-error", "connect-error", "4gl-client-error", "4gl-server-error", "4gl-app-error", "nameserver-error", "obj-pool-full", "obj-pool-expired", "appserverConnections", "appObjects", "subAppObjects", "procObjects" };
    }
}
