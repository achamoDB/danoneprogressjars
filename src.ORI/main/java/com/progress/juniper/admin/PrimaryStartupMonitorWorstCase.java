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

class PrimaryStartupMonitorWorstCase extends Thread implements jpMsg
{
    JAConfiguration config;
    TryIt monitor;
    int timeout;
    com.progress.juniper.admin.State state;
    static int extent;
    
    protected PrimaryStartupMonitorWorstCase(final com.progress.juniper.admin.State state, final JAConfiguration config, final int timeout) {
        this.config = null;
        this.config = config;
        this.timeout = timeout;
        this.state = state;
        ++PrimaryStartupMonitorWorstCase.extent;
    }
    
    protected void finalize() {
        --PrimaryStartupMonitorWorstCase.extent;
    }
    
    public static void printInstanceCount(final PrintStream printStream) {
        printStream.println("There are " + PrimaryStartupMonitorWorstCase.extent + " instances of PrimaryStartupMonitorWorstCase");
    }
    
    void continuation(final int n) {
        this.monitor.continuation(this.timeout);
    }
    
    public void run() {
        final JAConfiguration config = this.config;
        JAConfiguration.getLog().log(5, 7669630165411963167L, new Object[] { this.config.name(), this.state.name(), new Integer(this.timeout) });
        this.setName("PrimaryStartupMonitorWorstCase/" + this.config.name());
        this.config.iT(this.getName());
        boolean b = false;
        try {
            (this.monitor = new TryIt(new PrimaryStartupMonitorCallback(this.config), this.state)).callIt(this.timeout * 1000);
            b = false;
        }
        catch (TimedOut timedOut) {
            final JAConfiguration config2 = this.config;
            JAConfiguration.getLog().logErr(7669630165411963172L);
            try {
                this.config.plugIn.getEventBroker().postEvent(new EStartupProcessTimedOut(this.config.database));
                this.config.plugIn.getEventBroker().postEvent(new EStartupProcessCompleted(this.config.database));
            }
            catch (RemoteException ex3) {
                Excp.print("Can't post event marking timeout of startup process,");
            }
            b = true;
        }
        catch (Exception ex) {
            Excp.print(ex);
        }
        this.config.worstCaseMonitor = null;
        if (b) {
            synchronized (this.config.plugIn) {
                try {
                    final JAConfiguration config3 = this.config;
                    JAConfiguration.getLog().log(4, "wtc monitor timed out after " + this.timeout + " seconds; " + this.config.name() + " : " + this.config.getStateDescriptor() + " resetting to idle.");
                    this.config.setState(CStateIdle.get());
                }
                catch (StateException ex2) {
                    Excp.print(ex2);
                }
            }
        }
        this.config.worstCaseMonitor = null;
        this.config.dT(this.getName());
    }
    
    void destroyThread() {
        final JAConfiguration config = this.config;
        JAConfiguration.getLog().log(5, 7669630165411963173L, new Object[] { this.config.name() });
        if (this.monitor != null) {
            this.monitor.destroyThread();
            this.monitor = null;
        }
        this.config.worstCaseMonitor = null;
    }
    
    static {
        PrimaryStartupMonitorWorstCase.extent = 0;
    }
}
