// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.util;

import com.progress.common.ehnlog.IAppLogger;
import com.progress.common.ehnlog.AppLogger;

public class ubThreadQueue extends Queue
{
    public ubThreadQueue(final String s, final int n, final AppLogger appLogger) {
        super(s, n, appLogger);
    }
    
    public synchronized void enqueueThread(final ubThread ubThread) throws QueueException {
        this.enqueue(ubThread);
    }
    
    public synchronized ubThread dequeueThread() {
        return (ubThread)this.dequeue();
    }
}
