// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import com.progress.international.resources.ProgressResources;

class AuxiliaryList
{
    boolean bi;
    boolean ai;
    boolean watchdog;
    int apwsSoFar;
    int apwsTotal;
    JAConfiguration config;
    boolean done;
    
    AuxiliaryList(final JAConfiguration config, final boolean bi, final boolean ai, final boolean watchdog, final int apwsTotal) {
        this.bi = false;
        this.ai = false;
        this.watchdog = false;
        this.apwsSoFar = 0;
        this.apwsTotal = 0;
        this.config = null;
        this.done = false;
        this.bi = bi;
        this.ai = ai;
        this.watchdog = watchdog;
        this.apwsTotal = apwsTotal;
        this.config = config;
    }
    
    synchronized boolean isDone() {
        return !this.isNotDone();
    }
    
    synchronized boolean isNotDone() {
        return this.ai || this.bi || this.watchdog || this.apwsRemaining() > 0;
    }
    
    synchronized void removeBI() {
        if (!this.bi) {
            return;
        }
        this.bi = false;
        this.testIfDone();
    }
    
    synchronized void removeAI() {
        if (!this.ai) {
            return;
        }
        this.ai = false;
        this.testIfDone();
    }
    
    synchronized void removeAPW() {
        ++this.apwsSoFar;
        this.testIfDone();
    }
    
    synchronized void removeWatchdog() {
        if (!this.watchdog) {
            return;
        }
        this.watchdog = false;
        this.testIfDone();
    }
    
    synchronized void setAPWs(final int apwsSoFar) {
        this.apwsSoFar = apwsSoFar;
        if (this.apwsRemaining() == 0) {
            this.testIfDone();
        }
    }
    
    int remaining() {
        return ((this.bi + this.ai + this.watchdog) ? 1 : 0) + this.apwsRemaining();
    }
    
    int apwsRemaining() {
        return this.apwsTotal - this.apwsSoFar;
    }
    
    void testIfDone() {
        if (this.done) {
            return;
        }
        if (this.isDone()) {
            this.done = true;
            final JAConfiguration config = this.config;
            JAConfiguration.getLog().log(ProgressResources.retrieveTranString("com.progress.international.messages.ADMMsgBundle", "All auxiliary processes for configuration are accounted for", new Object[] { this.config.name() }));
            if (this.config.externalStartupMonitor != null) {
                this.config.externalStartupMonitor.testIfDone();
            }
        }
    }
}
