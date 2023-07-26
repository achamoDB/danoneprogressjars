// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.util.ResourceBundle;
import java.util.Enumeration;
import com.progress.chimera.common.Tools;
import com.progress.common.networkevents.IEventObject;
import java.rmi.server.RemoteStub;
import java.rmi.RemoteException;
import com.progress.chimera.log.ConnectionManagerLog;
import com.progress.international.resources.ProgressResources;
import com.progress.chimera.common.ChimeraRemoteObject;

abstract class JAAuxiliary extends ChimeraRemoteObject implements IJAAuxiliary
{
    static ProgressResources resources;
    StateObject state;
    JAPlugIn plugIn;
    JAConfiguration configuration;
    private static ConnectionManagerLog theLog;
    String displayName;
    
    static ProgressResources resources() {
        return JAAuxiliary.resources;
    }
    
    public JAStatusObject getStatus() throws RemoteException {
        final JAStatusObject jaStatusObject = new JAStatusObject();
        jaStatusObject.displayName = this.getDisplayName();
        jaStatusObject.stateDescriptor = this.getStateDescriptor();
        jaStatusObject.isStartable = this.isStartable();
        jaStatusObject.isStopable = this.isStopable();
        jaStatusObject.isIdle = this.isIdle();
        jaStatusObject.isStarting = this.isStarting();
        jaStatusObject.isInitializing = this.isInitializing();
        jaStatusObject.isRunning = this.isRunning();
        jaStatusObject.isShuttingDown = this.isShuttingDown();
        return jaStatusObject;
    }
    
    public RemoteStub remoteStub() {
        return this.stub();
    }
    
    IJAAuxiliary evThis() {
        return (IJAAuxiliary)this.remoteStub();
    }
    
    public String getStateDescriptor() {
        return this.state.get().displayName();
    }
    
    void setState(final State state) throws StateException {
        if (state == this.state.get()) {
            return;
        }
        synchronized (this.plugIn) {
            synchronized (this.state) {
                try {
                    this.state.changeState(this, state);
                    this.plugIn.getEventBroker().postEvent(new EAuxiliaryStateChanged(this, this.getStatus()));
                }
                catch (StateException ex) {
                    Tools.px(ex, "Error changing states on auxiliary.");
                    throw ex;
                }
                catch (RemoteException ex2) {
                    Tools.px(ex2, "Can't post event to mark auxiliary state change.");
                }
            }
        }
    }
    
    public void setIdle() throws StateException {
        this.setState(GStateIdle.get());
    }
    
    public void setStarting() throws StateException {
        this.setState(GStateStarting.get());
    }
    
    public void setInitializing() throws StateException {
        this.setState(GStateInitializing.get());
    }
    
    public void setRunning() throws StateException {
        this.setState(GStateRunning.get());
    }
    
    public void setShuttingDown() throws StateException {
        this.setState(GStateShuttingDown.get());
    }
    
    public boolean isIdle() {
        synchronized (this.plugIn) {
            return this.state.get() == GStateIdle.get();
        }
    }
    
    public boolean isStarting() {
        synchronized (this.plugIn) {
            return this.state.get() == GStateStarting.get();
        }
    }
    
    public boolean isInitializing() {
        synchronized (this.plugIn) {
            return this.state.get() == GStateInitializing.get();
        }
    }
    
    public boolean isRunning() {
        synchronized (this.plugIn) {
            return this.state.get() == GStateRunning.get();
        }
    }
    
    public boolean isShuttingDown() {
        synchronized (this.plugIn) {
            return this.state.get() == GStateShuttingDown.get();
        }
    }
    
    public static ConnectionManagerLog getLog() {
        if (JAAuxiliary.theLog == null) {
            JAAuxiliary.theLog = ConnectionManagerLog.get();
        }
        return JAAuxiliary.theLog;
    }
    
    void handleCrash() {
        try {
            this.setIdle();
            this.plugIn.getEventBroker().postEvent(this.getCrashEvent());
        }
        catch (Throwable t) {
            Tools.px(t, "Can't set state of auxiliary: " + this.displayName + " to idle.");
        }
    }
    
    abstract IEventObject getCrashEvent();
    
    public IJAPlugIn getPlugIn() {
        return this.plugIn;
    }
    
    public IJAConfiguration getConfiguration() {
        return this.configuration;
    }
    
    public String getDisplayName() throws RemoteException {
        return this.getDisplayName(false);
    }
    
    public String getDisplayName(final boolean b) throws RemoteException {
        synchronized (this.plugIn) {
            if (!b) {
                return this.displayName;
            }
            return this.getConfiguration().getDisplayName(true) + "." + this.displayName;
        }
    }
    
    public String toString() {
        return this.displayName;
    }
    
    protected JAAuxiliary(final JAPlugIn plugIn, final JAConfiguration configuration, final String displayName) throws RemoteException {
        this.state = new StateObject(GStateIdle.get());
        this.displayName = "";
        this.displayName = displayName;
        this.plugIn = plugIn;
        this.configuration = configuration;
    }
    
    public boolean isStartable() {
        return false;
    }
    
    public void start() throws RemoteException {
        throw new RemoteException("Can't start auxiliary.  Not supported");
    }
    
    public boolean isStopable() {
        return false;
    }
    
    public void stop() throws RemoteException {
        throw new RemoteException("Can't stop auxiliary.  Not supported");
    }
    
    public void delete(final Object o) throws RemoteException {
        throw new RemoteException("Can't delete auxiliary.  Not supported");
    }
    
    public boolean isDeleteable() {
        return false;
    }
    
    public IJAHierarchy getParent() {
        synchronized (this.plugIn) {
            return this.getConfiguration();
        }
    }
    
    public String makeNewChildName() throws RemoteException {
        synchronized (this.plugIn) {
            throw new RemoteException("Children of auxiliaries not supported.");
        }
    }
    
    public boolean childNameUsed(final String s) {
        return false;
    }
    
    public IJAHierarchy createChild(final String s, final Object o, final Object o2) throws RemoteException {
        synchronized (this.plugIn) {
            throw new RemoteException("Children of auxiliaries not supported.");
        }
    }
    
    public Enumeration getChildrenObjects() {
        return null;
    }
    
    static {
        JAAuxiliary.resources = (ProgressResources)ResourceBundle.getBundle("com.progress.international.messages.JAGenericBundle");
        JAAuxiliary.theLog = null;
    }
}
