// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.util.Enumeration;
import com.progress.international.resources.ProgressResources;
import java.io.PrintStream;
import java.util.Vector;

class ExternalStartupMonitor2 extends Thread implements MProservJuniperAPI
{
    JAConfiguration config;
    Vector secondaries;
    AuxiliaryList auxiliaries;
    volatile boolean stopMe;
    static int extent;
    
    ExternalStartupMonitor2(final JAConfiguration config, final Vector secondaries, final AuxiliaryList auxiliaries) {
        this.config = null;
        this.secondaries = null;
        this.stopMe = false;
        this.config = config;
        this.auxiliaries = auxiliaries;
        this.secondaries = secondaries;
        ++ExternalStartupMonitor2.extent;
    }
    
    protected void finalize() {
        --ExternalStartupMonitor2.extent;
    }
    
    public static void printInstanceCount(final PrintStream printStream) {
        printStream.println("There are " + ExternalStartupMonitor2.extent + " instances of ExternalStartupMonitor2");
    }
    
    public void run() {
        this.setName("ExternalStartupMonitor2/" + this.config.name());
        this.config.iT(this.getName());
        while (true) {
            while (!this.stopRequested()) {
                if (!this.secondaries.isEmpty() || !this.auxiliaries.isDone()) {
                    synchronized (this) {
                        if (!this.secondaries.isEmpty()) {
                            synchronized (this.secondaries) {
                                final Enumeration<JAService> elements = this.secondaries.elements();
                                while (elements.hasMoreElements()) {
                                    if (this.stopRequested()) {
                                        break;
                                    }
                                    final JAService jaService = elements.nextElement();
                                    if (!this.config.isRunning()) {
                                        this.stopMe = true;
                                        break;
                                    }
                                    ((MProservJuniper)this.config.rjo).sendPacket("SGR?", jaService.getServiceNameOrPort());
                                }
                                if (this.stopRequested()) {
                                    break;
                                }
                            }
                        }
                        if (!this.auxiliaries.isDone()) {
                            synchronized (this.auxiliaries) {
                                if (this.stopRequested()) {
                                    break;
                                }
                                if (!this.config.isRunning()) {
                                    break;
                                }
                                if (this.config.rjo != null) {
                                    if (this.auxiliaries.bi) {
                                        ((MProservJuniper)this.config.rjo).sendPacket("BIU?");
                                    }
                                    if (this.auxiliaries.ai) {
                                        ((MProservJuniper)this.config.rjo).sendPacket("AIU?");
                                    }
                                    if (this.auxiliaries.watchdog) {
                                        ((MProservJuniper)this.config.rjo).sendPacket("WDU?");
                                    }
                                    if (this.auxiliaries.apwsRemaining() > 0) {
                                        ((MProservJuniper)this.config.rjo).sendPacket("AWU?");
                                    }
                                }
                            }
                        }
                    }
                    try {
                        Thread.sleep(JAConfiguration.getTiming("PollServerGroup") * 1000);
                    }
                    catch (InterruptedException ex) {}
                    continue;
                }
                final JAConfiguration config = this.config;
                JAConfiguration.getLog().log(2, ProgressResources.retrieveTranString("com.progress.international.messages.ADMMsgBundle", "All external processes for configuration are accounted for", new Object[] { this.config.name() }));
                this.config.dT(this.getName());
                return;
            }
            continue;
        }
    }
    
    boolean stopRequested() {
        synchronized (this) {
            if (this.stopMe) {
                final JAConfiguration config = this.config;
                JAConfiguration.getLog().log(4, ProgressResources.retrieveTranString("com.progress.international.messages.ADMMsgBundle", "Secondary broker monitor for configuration is exiting.", new Object[] { this.config.name() }));
                this.notifyAll();
                return true;
            }
            return false;
        }
    }
    
    static {
        ExternalStartupMonitor2.extent = 0;
    }
}
