// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import com.progress.common.networkevents.IEventBroker;
import java.rmi.RemoteException;
import com.progress.common.networkevents.IEventObject;
import com.progress.international.resources.ProgressResources;
import java.util.Enumeration;
import com.progress.common.log.Excp;
import java.io.PrintStream;
import java.util.Vector;
import com.progress.message.jpMsg;

class ExternalStartupMonitor extends Thread implements jpMsg
{
    JAConfiguration config;
    Vector secondaries;
    AuxiliaryList auxiliaries;
    static int extent;
    ExternalStartupMonitor2 sm2;
    boolean secondariesDone;
    boolean auxiliariesDone;
    
    ExternalStartupMonitor(final JAConfiguration config, final Vector secondaries, final AuxiliaryList auxiliaries) {
        this.config = null;
        this.sm2 = null;
        this.secondariesDone = false;
        this.auxiliariesDone = false;
        this.config = config;
        this.secondaries = secondaries;
        this.auxiliaries = auxiliaries;
        ++ExternalStartupMonitor.extent;
    }
    
    protected void finalize() {
        --ExternalStartupMonitor.extent;
    }
    
    public static void printInstanceCount(final PrintStream printStream) {
        printStream.println("There are " + ExternalStartupMonitor.extent + " instances of ExternalStartupMonitor");
    }
    
    void idleAll() {
        try {
            final Enumeration<JAService> elements = this.secondaries.elements();
            while (elements.hasMoreElements()) {
                elements.nextElement().setIdle();
            }
            if (this.auxiliaries.bi) {
                this.config.biWriter.setIdle();
            }
            if (this.auxiliaries.ai) {
                this.config.aiWriter.setIdle();
            }
            if (this.auxiliaries.watchdog) {
                this.config.watchdog.setIdle();
            }
        }
        catch (StateException ex) {
            Excp.print(ex);
        }
    }
    
    void destroyThread() {
        synchronized (this.sm2) {
            synchronized (this.secondaries) {
                final JAConfiguration config = this.config;
                JAConfiguration.getLog().log(5, ProgressResources.retrieveTranString("com.progress.international.messages.ADMMsgBundle", "Killing monitor for external processes on configuration", new Object[] { this.config.name() }));
                if (this.secondaries.size() > 0) {
                    final JAConfiguration config2 = this.config;
                    JAConfiguration.getLog().log("   " + ProgressResources.retrieveTranString("com.progress.international.messages.ADMMsgBundle", "secondaries are unaccounted for", new Object[] { new Integer(this.secondaries.size()) }));
                }
            }
            synchronized (this.auxiliaries) {
                if (this.auxiliaries.isNotDone()) {
                    String s = " ";
                    if (this.auxiliaries.bi) {
                        s += " BI";
                    }
                    if (this.auxiliaries.ai) {
                        s += " AI";
                    }
                    if (this.auxiliaries.watchdog) {
                        s += " watchdog";
                    }
                    if (this.auxiliaries.apwsRemaining() > 0) {
                        s = s + " APWs(" + this.auxiliaries.apwsRemaining() + ")";
                    }
                    final JAConfiguration config3 = this.config;
                    JAConfiguration.getLog().log("   " + ProgressResources.retrieveTranString("com.progress.international.messages.ADMMsgBundle", "The following auxiliaries are unaccounted for:", (Object)s));
                }
            }
            this.sm2.stopMe = true;
        }
    }
    
    void testIfDone() {
        synchronized (this.sm2) {
            if (this.secondaries.size() == 0 && this.auxiliaries.isDone()) {
                this.destroyThread();
                this.config.externalStartupMonitor = null;
            }
        }
    }
    
    public void run() {
        this.setName("ExternalStartupMonitor/" + this.config.name());
        this.config.iT(this.getName());
        final JAConfiguration config = this.config;
        JAConfiguration.getLog().log(5, 7669630165411963164L, new Object[] { this.config.name(), new Integer(15000) });
        final IEventBroker eventBroker = this.config.plugIn.getEventBroker();
        try {
            (this.sm2 = new ExternalStartupMonitor2(this.config, this.secondaries, this.auxiliaries)).start();
            synchronized (this.sm2) {
                this.sm2.wait(60000L);
                this.destroyThread();
                synchronized (this.secondaries) {
                    if (this.secondaries.size() > 0 && this.config.isRunning()) {
                        for (int i = 0; i < this.secondaries.size(); ++i) {
                            final EServerGroupStartupTimedOut eServerGroupStartupTimedOut = new EServerGroupStartupTimedOut(this.secondaries.elementAt(i), "Timed out");
                            this.config.plugIn.getEventBroker().postEvent(eServerGroupStartupTimedOut);
                            this.config.plugIn.getEventBroker().postEvent(new ESecondaryStartupFailed(this.config.database, eServerGroupStartupTimedOut));
                        }
                    }
                }
                synchronized (this.auxiliaries) {
                    if (this.auxiliaries.isNotDone() && this.config.isRunning()) {
                        if (this.auxiliaries.bi) {
                            final EBIWriterStartupFailed ebiWriterStartupFailed = new EBIWriterStartupFailed(this.config.biWriter, "timed out", "Timed out");
                            eventBroker.postEvent(ebiWriterStartupFailed);
                            eventBroker.postEvent(new EAuxiliaryProcessStartupFailed(this.config.database, ebiWriterStartupFailed));
                        }
                        if (this.auxiliaries.ai) {
                            final EAIWriterStartupFailed eaiWriterStartupFailed = new EAIWriterStartupFailed(this.config.aiWriter, "timed out", "Timed out");
                            eventBroker.postEvent(eaiWriterStartupFailed);
                            eventBroker.postEvent(new EAuxiliaryProcessStartupFailed(this.config.database, eaiWriterStartupFailed));
                        }
                        if (this.auxiliaries.watchdog) {
                            final EWatchdogStartupFailed eWatchdogStartupFailed = new EWatchdogStartupFailed(this.config.watchdog, "timed out", "Timed out");
                            eventBroker.postEvent(eWatchdogStartupFailed);
                            eventBroker.postEvent(new EAuxiliaryProcessStartupFailed(this.config.database, eWatchdogStartupFailed));
                        }
                        if (this.auxiliaries.apwsRemaining() > 0) {
                            final EAPWStartupFailed eapwStartupFailed = new EAPWStartupFailed(this.config.apWriter, "timed out, " + this.auxiliaries.apwsRemaining() + " remaining", "Timed out");
                            eventBroker.postEvent(eapwStartupFailed);
                            eventBroker.postEvent(new EAuxiliaryProcessStartupFailed(this.config.database, eapwStartupFailed));
                        }
                    }
                }
            }
            this.idleAll();
        }
        catch (InterruptedException ex2) {
            final JAConfiguration config2 = this.config;
            JAConfiguration.getLog().logErr(7669630165411963165L, new Object[] { this.config.name() });
            synchronized (this.config.plugIn) {
                Excp.print("Monitor thread interupted.  WE SHOULDN'T GET HERE");
                this.idleAll();
            }
        }
        catch (RemoteException ex) {
            Excp.print(ex);
        }
        try {
            eventBroker.postEvent(new EStartupProcessCompleted(this.config.database));
        }
        catch (RemoteException ex3) {
            Excp.print("Can't post event marking end of startup process,");
        }
        this.sm2 = null;
        this.config.externalStartupMonitor = null;
        this.config.dT(this.getName());
    }
    
    static {
        ExternalStartupMonitor.extent = 0;
    }
}
