// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

class SStateStarting extends SState
{
    static SStateStarting singleInstance;
    
    SStateStarting() {
        super(SStateInitializing.class);
    }
    
    static SStateStarting get() {
        return SStateStarting.singleInstance;
    }
    
    public String displayName() {
        return JAService.resources.getTranString("Starting");
    }
    
    static {
        SStateStarting.singleInstance = new SStateStarting();
    }
}
