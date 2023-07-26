// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera.clu.dbMan;

import com.progress.agent.database.EDBACrash;
import com.progress.agent.database.EDBAStartupCompleted;
import com.progress.agent.database.EDBAStartupCompleted_Stub;
import com.progress.common.networkevents.IEventObject;
import com.progress.common.networkevents.EventListener;

class DBAgentStartedListener extends EventListener
{
    boolean started;
    boolean timedout;
    
    DBAgentStartedListener() {
        this.started = false;
        this.timedout = true;
    }
    
    public synchronized void processEvent(final IEventObject eventObject) {
        if (eventObject instanceof EDBAStartupCompleted_Stub || eventObject instanceof EDBAStartupCompleted) {
            this.started = true;
            this.timedout = false;
            this.notify();
        }
        else if (eventObject instanceof EDBACrash) {
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
