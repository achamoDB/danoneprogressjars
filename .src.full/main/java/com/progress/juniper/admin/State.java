// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import com.progress.international.resources.ProgressResources;
import com.progress.chimera.log.ConnectionManagerLog;

public abstract class State
{
    Class nextNormalState;
    static ConnectionManagerLog theLog;
    
    public State(final Class nextNormalState) {
        this.nextNormalState = null;
        this.nextNormalState = nextNormalState;
    }
    
    public String name() {
        final String name = this.getClass().getName();
        return name.substring(name.lastIndexOf(".") + 1);
    }
    
    public String displayName() {
        return this.name();
    }
    
    public boolean transitionOK(final Object o, final State state) throws StateException {
        return true;
    }
    
    public void transition(final Object o, final State state) throws StateException {
    }
    
    public void cleanup(final Object o, final State state) {
    }
    
    public void during(final Object o) {
    }
    
    public void exit(final Object o) throws StateException {
        State.theLog.log(4, ProgressResources.retrieveTranString("com.progress.international.messages.ADMMsgBundle", "Leaving state:", new Object[] { this.name() }));
    }
    
    public void enter(final Object o) throws StateException {
        State.theLog.log(4, ProgressResources.retrieveTranString("com.progress.international.messages.ADMMsgBundle", "Entering state:", new Object[] { this.name() }));
    }
    
    static {
        State.theLog = ConnectionManagerLog.get();
    }
}
