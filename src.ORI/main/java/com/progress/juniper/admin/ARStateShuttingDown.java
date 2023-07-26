// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

class ARStateShuttingDown extends ARState
{
    static ARStateShuttingDown singleInstance;
    
    ARStateShuttingDown() {
        super(ARStateIdle.class);
    }
    
    public static ARStateShuttingDown get() {
        return ARStateShuttingDown.singleInstance;
    }
    
    public void enter(final Object o) throws StateException {
        super.enter(o);
        ((JAAgent)o).blockStarts(true);
    }
    
    public String displayName() {
        return ARState.resources.getTranString("Shutting Down");
    }
    
    static {
        ARStateShuttingDown.singleInstance = new ARStateShuttingDown();
    }
}
