// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import com.progress.common.rmiregistry.TimedOut;
import java.rmi.RemoteException;
import com.progress.common.log.Excp;
import com.progress.common.networkevents.IEventObject;
import com.progress.common.rmiregistry.ICallable;
import java.io.PrintStream;
import com.progress.common.rmiregistry.TryIt;
import com.progress.message.jpMsg;

class PrimaryStartupMonitorReasonable extends Thread implements jpMsg
{
    JAConfiguration config;
    TryIt monitor;
    int defaultContinuation;
    int timeout;
    com.progress.juniper.admin.State state;
    static int extent;
    
    PrimaryStartupMonitorReasonable(final com.progress.juniper.admin.State state, final JAConfiguration config, final int timeout, final int defaultContinuation) {
        this.config = null;
        this.config = config;
        this.defaultContinuation = defaultContinuation;
        this.timeout = timeout;
        this.state = state;
        ++PrimaryStartupMonitorReasonable.extent;
    }
    
    protected void finalize() {
        --PrimaryStartupMonitorReasonable.extent;
    }
    
    public static void printInstanceCount(final PrintStream printStream) {
        printStream.println("There are " + PrimaryStartupMonitorReasonable.extent + " instances of PrimaryStartupMonitorReasonable");
    }
    
    void continuation() {
        this.monitor.continuation(this.defaultContinuation);
    }
    
    void continuation(final int n) {
        this.monitor.continuation(n);
    }
    
    public void run() {
        final JAConfiguration config = this.config;
        JAConfiguration.getLog().log(5, 7669630165411963166L, new Object[] { this.config.name(), this.state.name(), new Integer(this.timeout) });
        this.setName("PrimaryStartupMonitorReasonable/" + this.config.name());
        this.config.iT(this.getName());
        try {
            (this.monitor = new TryIt(new PrimaryStartupMonitorCallback(this.config), this.state)).callIt(this.timeout * 1000);
        }
        catch (TimedOut timedOut) {
            final JAConfiguration config2 = this.config;
            JAConfiguration.getLog().log(5, 7669630165411963168L, new Object[] { this.config.name(), this.state.name() });
            this.config.isStopableNow = true;
            try {
                this.config.plugIn.getEventBroker().postEvent(new EConfigurationStateChanged(this.config.database.evThis(), this.config, this.config.getStatus()));
            }
            catch (RemoteException ex) {
                Excp.print(ex, "Can't post event");
            }
        }
        catch (Exception ex2) {
            Excp.print(ex2);
        }
        this.config.reasonableTimeMonitor = null;
        this.config.dT(this.getName());
    }
    
    void destroyThread() {
        final JAConfiguration config = this.config;
        JAConfiguration.getLog().log(5, 7669630165411963169L, new Object[] { this.config.name() });
        if (this.monitor != null) {
            this.monitor.destroyThread();
            this.monitor = null;
        }
        this.config.reasonableTimeMonitor = null;
    }
    
    static {
        PrimaryStartupMonitorReasonable.extent = 0;
    }
}
