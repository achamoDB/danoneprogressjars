// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.agent.database;

import com.progress.juniper.admin.StateException;

class DBAStateIdle extends DBAState
{
    static DBAStateIdle singleInstance;
    
    DBAStateIdle() {
        super(DBAStateStarting.class);
    }
    
    public static DBAStateIdle get() {
        return DBAStateIdle.singleInstance;
    }
    
    public void enter(final Object o) throws StateException {
        super.enter(o);
        ((AgentDatabase)o).blockStarts(false);
    }
    
    public String displayName() {
        return DBAState.resources.getTranString("Not Running");
    }
    
    static {
        DBAStateIdle.singleInstance = new DBAStateIdle();
    }
}
