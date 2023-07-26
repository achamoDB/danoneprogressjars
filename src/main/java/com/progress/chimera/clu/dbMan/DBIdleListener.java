// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera.clu.dbMan;

import java.rmi.RemoteException;
import com.progress.juniper.admin.IStateProvider;
import com.progress.juniper.admin.JAStatusObjectDB;
import com.progress.juniper.admin.EConfigurationStateChanged;
import com.progress.juniper.admin.EConfigurationStateChanged_Stub;
import com.progress.juniper.admin.EConfigurationCrashEvent;
import com.progress.common.networkevents.IEventObject;
import com.progress.common.networkevents.EventListener;

class DBIdleListener extends EventListener
{
    boolean idle;
    boolean crashed;
    boolean timedout;
    
    DBIdleListener() {
        this.idle = false;
        this.crashed = false;
        this.timedout = true;
    }
    
    public synchronized void processEvent(final IEventObject eventObject) throws RemoteException {
        if (eventObject instanceof EConfigurationCrashEvent) {
            this.timedout = false;
            this.crashed = true;
            this.notify();
        }
        else if ((eventObject instanceof EConfigurationStateChanged_Stub || eventObject instanceof EConfigurationStateChanged) && ((JAStatusObjectDB)((IStateProvider)eventObject).getStatus()).isIdle()) {
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
