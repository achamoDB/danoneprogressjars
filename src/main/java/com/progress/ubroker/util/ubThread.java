// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.util;

import com.progress.chimera.adminserver.IAdminServerConnection;
import com.progress.chimera.adminserver.IAdministrationServer;
import java.rmi.RMISecurityManager;
import com.progress.common.networkevents.IEventBroker;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.net.MalformedURLException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import com.progress.common.util.PscURLParser;
import java.rmi.Remote;
import com.progress.common.ehnlog.IAppLogger;
import java.text.DecimalFormat;

public abstract class ubThread extends Thread
{
    public static final int UBTHREAD_STATE_IDLE = 0;
    public static final int UBTHREAD_STATE_READY = 1;
    public static final int UBTHREAD_STATE_BUSY = 2;
    public static final int UBTHREAD_STATE_BOUND = 3;
    public static final int UBTHREAD_STATE_DEAD = 4;
    public static final String[] DESC_POOL_STATE;
    private static DecimalFormat fmt4;
    protected ubThreadStats stats;
    protected IAppLogger log;
    protected RequestQueue rcvQueue;
    private int threadId;
    private String poolId;
    private String fullName;
    private int threadPoolState;
    protected ubMsgTrace trace;
    
    public ubThread(final String s, final int threadId, final IAppLogger log) {
        this.poolId = s;
        this.threadId = threadId;
        this.log = log;
        this.threadPoolState = 0;
        this.rcvQueue = null;
        this.setName(this.fullName = new String(s + "-" + ubThread.fmt4.format(threadId)));
        this.stats = new ubThreadStats(this.fullName);
    }
    
    public abstract int getConnState();
    
    public abstract String getFSMState();
    
    public abstract String getRemoteSocketDesc();
    
    public String toString() {
        return new String(this.fullName);
    }
    
    public synchronized int getPoolState() {
        return this.threadPoolState;
    }
    
    public String getPoolStateDesc() {
        return new String(ubThread.DESC_POOL_STATE[this.threadPoolState]);
    }
    
    public synchronized int setPoolState(final int threadPoolState) {
        final int threadPoolState2 = this.threadPoolState;
        this.threadPoolState = threadPoolState;
        return threadPoolState2;
    }
    
    public int getThreadId() {
        return this.threadId;
    }
    
    public String getFullName() {
        return this.fullName;
    }
    
    public RequestQueue getRcvQueue() {
        return this.rcvQueue;
    }
    
    public ubThreadStats getLastStats() {
        return this.stats;
    }
    
    public void setLastStats(final ubThreadStats stats) {
        this.stats = stats;
    }
    
    public synchronized ubThreadStats getStats() {
        final ubThreadStats ubThreadStats = new ubThreadStats(this.stats);
        ubThreadStats.setMaxQueueDepth(this.rcvQueue.getMaxQueueDepth());
        ubThreadStats.setEnqueueWaits(this.rcvQueue.getEnqueueWaits());
        return ubThreadStats;
    }
    
    public static Remote lookupService(final String s) throws MalformedURLException, RemoteException, NotBoundException {
        final PscURLParser pscURLParser = new PscURLParser(s);
        pscURLParser.setScheme(null);
        final String host = pscURLParser.getHost();
        final int port = pscURLParser.getPort();
        final String service = pscURLParser.getService();
        final Registry registry = LocateRegistry.getRegistry(host, port);
        return (service == null) ? registry : registry.lookup(service);
    }
    
    public static void rebindService(final String s, final Remote remote) throws MalformedURLException, RemoteException {
        final PscURLParser pscURLParser = new PscURLParser(s);
        pscURLParser.setScheme(null);
        LocateRegistry.getRegistry(pscURLParser.getHost(), pscURLParser.getPort()).rebind(pscURLParser.getService(), remote);
    }
    
    public static void unbindService(final String s) throws MalformedURLException, RemoteException, NotBoundException {
        final PscURLParser pscURLParser = new PscURLParser(s);
        pscURLParser.setScheme(null);
        LocateRegistry.getRegistry(pscURLParser.getHost(), pscURLParser.getPort()).unbind(pscURLParser.getService());
    }
    
    public ubMsgTrace getMsgTrace() {
        return this.trace;
    }
    
    public IEventBroker findAdminServerEventBroker(final String s, final String s2) {
        return findAdminServerEventBroker(s, s2, this.log);
    }
    
    public static IEventBroker findAdminServerEventBroker(final String s, final String str, final IAppLogger appLogger) {
        IEventBroker eventBroker = null;
        try {
            System.setSecurityManager(new RMISecurityManager());
            eventBroker = ((IAdministrationServer)lookupService(s.substring(0, s.lastIndexOf(str)) + "Chimera")).getEventBroker();
        }
        catch (Exception ex) {
            if (s != null && appLogger != null) {
                appLogger.logStackTrace("Cannot locate AdminServer's EventBroker", ex);
            }
        }
        return eventBroker;
    }
    
    static {
        DESC_POOL_STATE = new String[] { "POOLSTATE_IDLE", "POOLSTATE_READY", "POOLSTATE_BUSY", "POOLSTATE_BOUND", "POOLSTATE_DEAD" };
        ubThread.fmt4 = new DecimalFormat("0000");
    }
}
