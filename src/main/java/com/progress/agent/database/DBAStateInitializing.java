// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.agent.database;

import com.progress.juniper.admin.StateException;

class DBAStateInitializing extends DBAState
{
    static DBAStateInitializing singleInstance;
    
    DBAStateInitializing() {
        super(DBAStateRunning.class);
    }
    
    public static DBAStateInitializing get() {
        return DBAStateInitializing.singleInstance;
    }
    
    public void exit(final Object o) throws StateException {
        super.enter(o);
        ((AgentDatabase)o).blockStarts(true);
    }
    
    public String displayName() {
        return DBAState.resources.getTranString("Initializing");
    }
    
    static {
        DBAStateInitializing.singleInstance = new DBAStateInitializing();
    }
}
