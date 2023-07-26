// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.util.ResourceBundle;
import com.progress.international.resources.ProgressResources;
import com.progress.chimera.common.Tools;
import com.progress.common.networkevents.IEventObject;
import java.rmi.RemoteException;

class JAAPWriter extends JAAuxiliary
{
    int startingCountInitial;
    int initializingCount;
    int runningCount;
    int shuttingDownCount;
    
    JAAPWriter(final JAPlugIn jaPlugIn, final JAConfiguration jaConfiguration, final String s) throws RemoteException {
        super(jaPlugIn, jaConfiguration, s);
        this.startingCountInitial = 0;
        this.initializingCount = 0;
        this.runningCount = 0;
        this.shuttingDownCount = 0;
    }
    
    public void setStarting() throws StateException {
        ++this.startingCountInitial;
        this.postEvent();
    }
    
    public void setRunning(final int runningCount) throws StateException {
        this.runningCount = runningCount;
        this.postEvent();
    }
    
    public void setIdle() throws StateException {
        final int n = 0;
        this.shuttingDownCount = n;
        this.runningCount = n;
        this.initializingCount = n;
        this.startingCountInitial = n;
        this.postEvent();
    }
    
    public void setShuttingDown() throws StateException {
        this.shuttingDownCount = this.runningCount;
        final int startingCountInitial = 0;
        this.runningCount = startingCountInitial;
        this.initializingCount = startingCountInitial;
        this.startingCountInitial = startingCountInitial;
        this.postEvent();
    }
    
    void postEvent() {
        try {
            super.plugIn.getEventBroker().postEvent(new EAuxiliaryStateChanged(this, this.getStatus()));
        }
        catch (RemoteException ex) {
            Tools.px(ex, "Can't post event to mark auxiliary state change.");
        }
    }
    
    public boolean isIdle() {
        return this.startingCountInitial + this.initializingCount + this.runningCount == 0;
    }
    
    public boolean isStarting() {
        return this.startingCountInitial - this.runningCount > 0;
    }
    
    public boolean isInitializing() {
        return this.initializingCount > 0;
    }
    
    public boolean isRunning() {
        return this.runningCount > 0;
    }
    
    public boolean isShuttingDown() {
        return this.shuttingDownCount > 0;
    }
    
    public String getStateDescriptor() {
        final ProgressResources progressResources = (ProgressResources)ResourceBundle.getBundle("com.progress.international.messages.ADMMsgBundle");
        if (this.shuttingDownCount > 0) {
            return "" + progressResources.getTranString("Shutting Down:", (Object)new Integer(this.shuttingDownCount));
        }
        String s;
        if (this.runningCount > 0) {
            s = progressResources.getTranString("Running:", (Object)new Integer(this.runningCount));
        }
        else {
            s = progressResources.getTranString("None running");
        }
        final int value = this.startingCountInitial - this.runningCount;
        if (value > 0) {
            s = s + ", " + progressResources.getTranString("Starting:", (Object)new Integer(value));
        }
        if (this.initializingCount > 0) {
            s = s + ", " + progressResources.getTranString("Initializing:", (Object)new Integer(this.initializingCount));
        }
        return s;
    }
    
    IEventObject getCrashEvent() {
        try {
            return new EAPWCrashEvent((IJAAuxiliary)this.stub());
        }
        catch (RemoteException ex) {
            Tools.px(ex);
            return null;
        }
    }
}
