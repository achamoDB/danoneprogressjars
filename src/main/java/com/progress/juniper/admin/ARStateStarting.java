// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

class ARStateStarting extends ARState
{
    static ARStateStarting singleInstance;
    
    ARStateStarting() {
        super(ARStateInitializing.class);
    }
    
    public static ARStateStarting get() {
        return ARStateStarting.singleInstance;
    }
    
    public void enter(final Object o) throws StateException {
        super.enter(o);
        ((JAAgent)o).blockStarts(true);
    }
    
    public String displayName() {
        return ARState.resources.getTranString("Starting");
    }
    
    static {
        ARStateStarting.singleInstance = new ARStateStarting();
    }
}
