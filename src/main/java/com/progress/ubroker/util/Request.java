// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.util;

import com.progress.common.ehnlog.IAppLogger;

public class Request implements ubConstants
{
    private Object msg;
    private Queue rspQueue;
    private long tsCreation;
    private long tsEnqueue;
    private long tsDequeue;
    
    public Request(final Object msg, final Queue rspQueue) {
        this.msg = msg;
        this.rspQueue = rspQueue;
        this.tsCreation = System.currentTimeMillis();
        this.tsEnqueue = -1L;
        this.tsDequeue = -1L;
    }
    
    public Object getMsg() {
        return this.msg;
    }
    
    public void setMsg(final Object msg) {
        this.msg = msg;
    }
    
    public Queue getRspQueue() {
        return this.rspQueue;
    }
    
    public void setRspQueue(final Queue rspQueue) {
        this.rspQueue = rspQueue;
    }
    
    public long gettsCreation() {
        return this.tsCreation;
    }
    
    public long gettsEnqueue() {
        return this.tsEnqueue;
    }
    
    public void settsEnqueue() {
        this.tsEnqueue = System.currentTimeMillis();
    }
    
    public long gettsDequeue() {
        return this.tsDequeue;
    }
    
    public void settsDequeue() {
        this.tsDequeue = System.currentTimeMillis();
    }
    
    public void logStats(final IAppLogger appLogger) {
        if (this.tsDequeue == -1L || this.tsEnqueue == -1L) {
            appLogger.logError(this + ".logStats() ERROR :" + " tsEnqueue= " + this.tsEnqueue + " tsDequeue= " + this.tsDequeue);
            return;
        }
        final long n = this.tsDequeue - this.tsEnqueue;
        if (appLogger.ifLogBasic(2L, 1)) {
            appLogger.logBasic(1, this + ".logStats() : tsEnqueue= " + this.tsEnqueue + " tsDequeue= " + this.tsDequeue + " queue_time= " + n);
        }
        if (appLogger.ifLogVerbose(2048L, 11)) {
            appLogger.logVerbose(11, this + ".logStats(): queue_time= " + n);
        }
    }
}
