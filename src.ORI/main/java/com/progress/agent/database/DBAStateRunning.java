// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.agent.database;

import com.progress.juniper.admin.StateException;

class DBAStateRunning extends DBAState
{
    static DBAStateRunning singleInstance;
    
    DBAStateRunning() {
        super(DBAStateShuttingDown.class);
    }
    
    public static DBAStateRunning get() {
        return DBAStateRunning.singleInstance;
    }
    
    public void enter(final Object o) throws StateException {
        super.enter(o);
        ((AgentDatabase)o).blockStarts(true);
    }
    
    public String displayName() {
        return DBAState.resources.getTranString("Running");
    }
    
    static {
        DBAStateRunning.singleInstance = new DBAStateRunning();
    }
}
