// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera.clu.dbMan;

import com.progress.juniper.admin.EConfigurationCrashEvent;
import com.progress.juniper.admin.EPrimaryStartupFailed;
import com.progress.juniper.admin.EPrimaryStartupFailed_Stub;
import com.progress.juniper.admin.EStartupProcessCompleted;
import com.progress.juniper.admin.EStartupProcessCompleted_Stub;
import com.progress.common.networkevents.IEventObject;
import com.progress.common.networkevents.EventListener;

class DBStartedListener extends EventListener
{
    boolean started;
    boolean timedout;
    
    DBStartedListener() {
        this.started = false;
        this.timedout = true;
    }
    
    public synchronized void processEvent(final IEventObject eventObject) {
        if (eventObject instanceof EStartupProcessCompleted_Stub || eventObject instanceof EStartupProcessCompleted) {
            this.started = true;
            this.timedout = false;
            this.notify();
        }
        else if (eventObject instanceof EPrimaryStartupFailed_Stub || eventObject instanceof EPrimaryStartupFailed) {
            this.started = false;
            this.timedout = false;
            this.notify();
        }
        else if (eventObject instanceof EConfigurationCrashEvent) {
            this.started = false;
            this.timedout = false;
            this.notify();
        }
    }
    
    public boolean isStarted() {
        return this.started;
    }
    
    public boolean isTimedout() {
        return this.timedout;
    }
}
