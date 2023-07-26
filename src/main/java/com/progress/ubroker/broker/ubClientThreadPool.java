// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.broker;

import com.progress.ubroker.util.ubThread;
import com.progress.common.ehnlog.IAppLogger;
import com.progress.ubroker.util.ubProperties;
import com.progress.ubroker.util.IubThreadFactory;
import com.progress.ubroker.util.ubConstants;

public class ubClientThreadPool extends ubThreadPool implements ubConstants, IubThreadFactory
{
    private ubProperties properties;
    private ubThreadPool serverPool;
    private IServerThreadControl serverCntrl;
    private int threadId;
    
    public ubClientThreadPool(final String s, final int n, final int n2, final int n3, final int n4, final ubProperties properties, final ubThreadPool serverPool, final IServerThreadControl serverCntrl, final IAppLogger appLogger) {
        super(s, n, n2, n3, n4, appLogger, properties);
        this.properties = properties;
        this.serverPool = serverPool;
        this.serverCntrl = serverCntrl;
        this.threadId = 0;
    }
    
    public synchronized ubThread createThread(final int n) {
        if (this.numThreadsAllowed() < 1) {
            return null;
        }
        ubConstants ubConstants = null;
        switch (this.properties.serverType) {
            case 0:
            case 4:
            case 6:
            case 7: {
                ubConstants = new ubASclientThread(++this.threadId, this, this.serverPool, this.serverCntrl, this.properties, super.log);
                break;
            }
            case 1: {
                ubConstants = new ubWSclientThread(++this.threadId, this, this.serverPool, this.serverCntrl, this.properties, super.log);
                break;
            }
            case 2:
            case 3:
            case 5: {
                ubConstants = new ubDSclientThread(++this.threadId, this, this.serverPool, this.serverCntrl, this.properties, super.log);
                break;
            }
            default: {
                super.log.logError(7665689515738013582L, new Object[] { new Integer(this.properties.serverType) });
                ubConstants = null;
                break;
            }
        }
        if (ubConstants != null) {
            ((Thread)ubConstants).setPriority(this.properties.getValueAsInt("clientThreadPriority"));
            this.addThread((ubThread)ubConstants);
        }
        return (ubThread)ubConstants;
    }
}
