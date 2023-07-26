// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

class ARStateInitializing extends ARState
{
    static ARStateInitializing singleInstance;
    
    ARStateInitializing() {
        super(ARStateRunning.class);
    }
    
    public static ARStateInitializing get() {
        return ARStateInitializing.singleInstance;
    }
    
    public void exit(final Object o) throws StateException {
        super.enter(o);
        ((JAAgent)o).blockStarts(true);
    }
    
    public String displayName() {
        return ARState.resources.getTranString("Initializing");
    }
    
    static {
        ARStateInitializing.singleInstance = new ARStateInitializing();
    }
}
