// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

class ARStateRunning extends ARState
{
    static ARStateRunning singleInstance;
    
    ARStateRunning() {
        super(ARStateShuttingDown.class);
    }
    
    public static ARStateRunning get() {
        return ARStateRunning.singleInstance;
    }
    
    public void enter(final Object o) throws StateException {
        super.enter(o);
        ((JAAgent)o).blockStarts(true);
    }
    
    public String displayName() {
        return ARState.resources.getTranString("Running");
    }
    
    static {
        ARStateRunning.singleInstance = new ARStateRunning();
    }
}
