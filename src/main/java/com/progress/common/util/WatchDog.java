// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.util;

import com.progress.common.ehnlog.AppLogger;
import com.progress.common.ehnlog.IAppLogger;

public class WatchDog extends Thread
{
    public static final int DEF_PRIORITY = 6;
    String threadname;
    IWatchable watcher;
    long interval;
    int priority;
    IAppLogger log;
    
    public WatchDog(final String s, final IWatchable watcher, final long interval, final int priority, final IAppLogger log) {
        this.threadname = new String(s);
        this.watcher = watcher;
        this.interval = interval;
        this.priority = priority;
        this.log = log;
        this.setName(s);
        this.setDaemon(true);
    }
    
    public WatchDog(final String s, final IWatchable watchable, final long n) {
        this(s, watchable, n, 6, new AppLogger());
    }
    
    public WatchDog(final String s, final IWatchable watchable, final long n, final IAppLogger appLogger) {
        this(s, watchable, n, 6, appLogger);
    }
    
    public void run() {
        Thread.currentThread().setPriority(this.priority);
        this.log.ehnLogWrite(2, 4, "watchdog", "start", "watchdog thread " + this.threadname + " started.");
        while (this.interval > 0L) {
            try {
                Thread.sleep(this.interval);
                this.watcher.watchEvent();
            }
            catch (Exception obj) {
                this.log.ehnLogWrite(2, 4, "watchdog", "end", "watchdog thread " + this.threadname + " exception= " + obj);
                this.interval = 0L;
            }
        }
        this.log.ehnLogWrite(2, 4, "watchdog", "end", "watchdog thread " + this.threadname + " done.");
    }
    
    public String getThreadname() {
        return new String(this.threadname);
    }
    
    public synchronized long getInterval() {
        return this.interval;
    }
    
    public synchronized void setInterval(final long interval) {
        this.interval = interval;
    }
}
