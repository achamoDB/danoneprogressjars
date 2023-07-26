// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera.clu.dbMan;

import java.rmi.RemoteException;
import com.progress.juniper.admin.IStateProvider;
import com.progress.agent.database.EDBAStateChanged;
import com.progress.agent.database.EDBAStateChanged_Stub;
import com.progress.agent.database.EDBACrash;
import com.progress.common.networkevents.IEventObject;
import com.progress.common.networkevents.EventListener;

class DBAgentIdleListener extends EventListener
{
    boolean idle;
    boolean crashed;
    boolean timedout;
    
    DBAgentIdleListener() {
        this.idle = false;
        this.crashed = false;
        this.timedout = true;
    }
    
    public synchronized void processEvent(final IEventObject eventObject) throws RemoteException {
        if (eventObject instanceof EDBACrash) {
            this.timedout = false;
            this.crashed = true;
            this.notify();
        }
        else if ((eventObject instanceof EDBAStateChanged_Stub || eventObject instanceof EDBAStateChanged) && ((IStateProvider)eventObject).getStatus().isIdle()) {
            this.timedout = false;
            this.idle = true;
            this.notify();
        }
    }
    
    public boolean isIdle() {
        return this.idle;
    }
    
    public boolean isCrashed() {
        return this.crashed;
    }
    
    public boolean isTimedout() {
        return this.timedout;
    }
}
