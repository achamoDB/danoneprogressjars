// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

import com.progress.open4gl.Open4GLException;
import java.util.Iterator;
import com.progress.open4gl.RunTimeProperties;
import com.progress.common.util.WatchDog;
import com.progress.common.ehnlog.IAppLogger;
import java.util.ArrayList;
import com.progress.common.util.IWatchable;

public class ASKWatchDog implements IWatchable
{
    public static int INITIAL_LIST_SIZE;
    public static int DEF_TIMEOUT;
    private static Object s_watchDogLock;
    private static ASKWatchDog s_watchDog;
    private ArrayList m_sessionList;
    private IAppLogger m_log;
    private WatchDog m_watchDog;
    
    public static void register(final Session session) {
        synchronized (ASKWatchDog.s_watchDogLock) {
            if (ASKWatchDog.s_watchDog == null) {
                ASKWatchDog.s_watchDog = new ASKWatchDog(null, RunTimeProperties.tracer, ASKWatchDog.INITIAL_LIST_SIZE);
            }
            ASKWatchDog.s_watchDog.registerSession(session);
        }
    }
    
    public static void deregister(final Session session) {
        synchronized (ASKWatchDog.s_watchDogLock) {
            if (ASKWatchDog.s_watchDog != null) {
                ASKWatchDog.s_watchDog.deregisterSession(session);
                if (ASKWatchDog.s_watchDog.empty()) {
                    ASKWatchDog.s_watchDog.stopWatchDog();
                    ASKWatchDog.s_watchDog = null;
                }
            }
        }
    }
    
    public static void stop() {
        if (ASKWatchDog.s_watchDog != null) {
            ASKWatchDog.s_watchDog.stopWatchDog();
        }
    }
    
    protected ASKWatchDog(String string, final IAppLogger log, final int initialCapacity) {
        if (string == null) {
            string = "ASKWatchDog@" + Integer.toHexString(this.hashCode());
        }
        this.m_sessionList = new ArrayList(initialCapacity);
        this.m_log = log;
        (this.m_watchDog = new WatchDog(string, this, ASKWatchDog.DEF_TIMEOUT, 6, log)).start();
    }
    
    private ASKWatchDog registerSession(final Session session) {
        if (session == null) {
            throw new NullPointerException("Session reference is null");
        }
        synchronized (this.m_sessionList) {
            if (this.m_sessionList.contains(session)) {
                RunTimeProperties.tracer.print(session.toString() + " is already registered.", 4);
            }
            else {
                this.m_sessionList.add(session);
            }
        }
        return this;
    }
    
    private void deregisterSession(final Session o) {
        synchronized (this.m_sessionList) {
            this.m_sessionList.remove(o);
        }
    }
    
    private void stopWatchDog() {
        this.m_watchDog.setInterval(0L);
    }
    
    private boolean empty() {
        final boolean b;
        synchronized (this.m_sessionList) {
            b = (this.m_sessionList.size() == 0);
        }
        return b;
    }
    
    public void watchEvent() {
        try {
            final Iterator iterator = this.m_sessionList.iterator();
            while (iterator.hasNext()) {
                this.checkSession(iterator.next());
            }
        }
        catch (Exception obj) {
            RunTimeProperties.tracer.print("ASKWatchDog caught exception: " + obj + " (" + obj.getMessage() + ")", 3);
            RunTimeProperties.tracer.print(obj, 4);
        }
    }
    
    private void checkSession(final Session session) throws Open4GLException {
        session.manageASKPingRequest();
    }
    
    static {
        ASKWatchDog.INITIAL_LIST_SIZE = 10;
        ASKWatchDog.DEF_TIMEOUT = 3000;
        ASKWatchDog.s_watchDogLock = new Object();
        ASKWatchDog.s_watchDog = null;
    }
}
