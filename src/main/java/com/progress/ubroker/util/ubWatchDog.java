// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.util;

import com.progress.common.ehnlog.IAppLogger;

public class ubWatchDog extends Thread
{
    public static final int DEF_PRIORITY = 6;
    public static final int DEF_INTERVAL = 60000;
    String threadname;
    IWatchable watcher;
    long interval;
    int priority;
    Logger log;
    IAppLogger applog;
    
    public ubWatchDog(final String s, final IWatchable watcher, final long interval, final int priority, final Logger log) {
        this.threadname = new String(s);
        this.watcher = watcher;
        this.interval = interval;
        this.priority = priority;
        this.log = log;
        this.applog = null;
        this.setName(s);
        this.setDaemon(true);
    }
    
    public ubWatchDog(final String s, final IWatchable watcher, final long interval, final int priority, final IAppLogger applog) {
        this.threadname = new String(s);
        this.watcher = watcher;
        this.interval = interval;
        this.priority = priority;
        this.applog = applog;
        this.log = null;
        this.setName(s);
        this.setDaemon(true);
    }
    
    public ubWatchDog() {
        this("", null, 60000L, 6, new Logger());
    }
    
    public ubWatchDog(final String s, final IWatchable watchable, final long n) {
        this(s, watchable, n, 6, new Logger());
    }
    
    public void run() {
        Thread.currentThread().setPriority(this.priority);
        while (this.interval > 0L) {
            try {
                Thread.sleep(this.interval);
                this.watcher.watchEvent();
                continue;
            }
            catch (Exception ex) {
                if (this.interval == 0L) {
                    return;
                }
                if (this.log != null) {
                    this.log.LogMsgN(2, 1, true, 7665689515738013574L, new Object[] { ex.toString(), ex.getMessage() });
                }
                else if (this.applog != null) {
                    this.applog.logError(7665689515738013574L, new Object[] { ex.toString(), ex.getMessage() });
                }
                return;
            }
            break;
        }
    }
    
    public String getThreadname() {
        return new String(this.threadname);
    }
    
    public synchronized long getInterval() {
        return this.interval;
    }
    
    public synchronized void setInterval(final long interval) {
        this.interval = interval;
        if (interval == 0L) {
            this.interrupt();
        }
    }
}
