// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.agent.database;

import com.progress.juniper.admin.StateException;

class DBAStateShuttingDown extends DBAState
{
    static DBAStateShuttingDown singleInstance;
    
    DBAStateShuttingDown() {
        super(DBAStateIdle.class);
    }
    
    public static DBAStateShuttingDown get() {
        return DBAStateShuttingDown.singleInstance;
    }
    
    public void enter(final Object o) throws StateException {
        super.enter(o);
        ((AgentDatabase)o).blockStarts(true);
    }
    
    public String displayName() {
        return DBAState.resources.getTranString("Shutting Down");
    }
    
    static {
        DBAStateShuttingDown.singleInstance = new DBAStateShuttingDown();
    }
}
