// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.agent.database;

import com.progress.juniper.admin.StateException;

class DBAStateStarting extends DBAState
{
    static DBAStateStarting singleInstance;
    
    DBAStateStarting() {
        super(DBAStateInitializing.class);
    }
    
    public static DBAStateStarting get() {
        return DBAStateStarting.singleInstance;
    }
    
    public void enter(final Object o) throws StateException {
        super.enter(o);
        ((AgentDatabase)o).blockStarts(true);
    }
    
    public String displayName() {
        return DBAState.resources.getTranString("Starting");
    }
    
    static {
        DBAStateStarting.singleInstance = new DBAStateStarting();
    }
}
