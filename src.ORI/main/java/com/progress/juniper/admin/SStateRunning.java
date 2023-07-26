// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

class SStateRunning extends SState
{
    static SStateRunning singleInstance;
    
    SStateRunning() {
        super(SStateShuttingDown.class);
    }
    
    static SStateRunning get() {
        return SStateRunning.singleInstance;
    }
    
    public String displayName() {
        return JAService.resources.getTranString("Running");
    }
    
    static {
        SStateRunning.singleInstance = new SStateRunning();
    }
}
