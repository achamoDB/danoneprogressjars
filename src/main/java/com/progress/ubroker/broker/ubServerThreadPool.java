// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.broker;

import com.progress.ubroker.util.ubThread;
import com.progress.common.ehnlog.IAppLogger;
import com.progress.ubroker.util.ubProperties;
import com.progress.ubroker.util.IubThreadFactory;
import com.progress.ubroker.util.ubConstants;

public class ubServerThreadPool extends ubThreadPool implements ubConstants, IubThreadFactory
{
    private ubProperties properties;
    private IAppLogger serverLog;
    private int threadId;
    
    public ubServerThreadPool(final String s, final int n, final int n2, final int n3, final int n4, final ubProperties properties, final IAppLogger serverLog, final IAppLogger appLogger) {
        super(s, n, n2, n3, n4, appLogger, properties);
        this.properties = properties;
        this.serverLog = serverLog;
        this.threadId = 0;
    }
    
    public synchronized ubThread createThread(final int n) {
        if (this.numThreadsAllowed() < 1) {
            return null;
        }
        ubConstants ubConstants = null;
        switch (this.properties.serverType) {
            case 4:
            case 6:
            case 7: {
                ubConstants = new ubJVserverThread(++this.threadId, n, this.properties, this, this.serverLog, super.log);
                break;
            }
            case 0: {
                ubConstants = new ubASserverThread(++this.threadId, n, this.properties, this, this.serverLog, super.log);
                break;
            }
            case 1: {
                ubConstants = new ubWSserverThread(++this.threadId, n, this.properties, this, this.serverLog, super.log);
                break;
            }
            case 2:
            case 3:
            case 5: {
                ubConstants = new ubDSserverThread(++this.threadId, n, this.properties, this, this.serverLog, super.log);
                break;
            }
            default: {
                super.log.logError(7665689515738013582L, new Object[] { new Integer(this.properties.serverType) });
                ubConstants = null;
                break;
            }
        }
        if (ubConstants != null) {
            ((Thread)ubConstants).setPriority(this.properties.getValueAsInt("serverThreadPriority"));
            this.addThread((ubThread)ubConstants);
        }
        return (ubThread)ubConstants;
    }
    
    public synchronized ubServerThread getServerThreadByCookie(final String s) {
        if (super.log.ifLogBasic(2L, 1)) {
            super.log.logBasic(1, "getServerThreadByCookie key= (" + s + ")");
        }
        if (s == null || s.length() == 0) {
            return null;
        }
        final ubThread[] enumerateThreads = this.enumerateThreads();
        if (super.log.ifLogBasic(2L, 1)) {
            super.log.logBasic(1, this + " threads= (" + enumerateThreads + ")");
        }
        if (enumerateThreads == null) {
            return null;
        }
        int i = 0;
        ubServerThread ubServerThread = null;
        while (i < enumerateThreads.length) {
            final String serverCookie = ((ubServerThread)enumerateThreads[i]).getServerCookie();
            if (super.log.ifLogBasic(2L, 1)) {
                super.log.logBasic(1, "pool[" + i + "] = (" + enumerateThreads[i].getFullName() + ") cookie = (" + serverCookie + ")");
            }
            if (serverCookie != null && serverCookie.compareTo(s) == 0) {
                ubServerThread = (ubServerThread)enumerateThreads[i];
                ubServerThread.setServerCookie(" ");
                break;
            }
            ++i;
        }
        return ubServerThread;
    }
}
