// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

class SStateShuttingDown extends SState
{
    static SStateShuttingDown singleInstance;
    
    SStateShuttingDown() {
        super(SStateIdle.class);
    }
    
    static SStateShuttingDown get() {
        return SStateShuttingDown.singleInstance;
    }
    
    public String displayName() {
        return JAService.resources.getTranString("Shutting Down");
    }
    
    static {
        SStateShuttingDown.singleInstance = new SStateShuttingDown();
    }
}
