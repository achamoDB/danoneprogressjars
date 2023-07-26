// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

class SStateIdle extends SState
{
    static SStateIdle singleInstance;
    
    SStateIdle() {
        super(SStateStarting.class);
    }
    
    static SStateIdle get() {
        return SStateIdle.singleInstance;
    }
    
    public String displayName() {
        return JAService.resources.getTranString("Not Running");
    }
    
    static {
        SStateIdle.singleInstance = new SStateIdle();
    }
}
