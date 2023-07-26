// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

class ARStateIdle extends ARState
{
    static ARStateIdle singleInstance;
    
    ARStateIdle() {
        super(ARStateStarting.class);
    }
    
    public static ARStateIdle get() {
        return ARStateIdle.singleInstance;
    }
    
    public void enter(final Object o) throws StateException {
        super.enter(o);
        ((JAAgent)o).blockStarts(false);
    }
    
    public String displayName() {
        return ARState.resources.getTranString("Not Running");
    }
    
    static {
        ARStateIdle.singleInstance = new ARStateIdle();
    }
}
