// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

class SStateInitializing extends SState
{
    static SStateInitializing singleInstance;
    
    SStateInitializing() {
        super(SStateRunning.class);
    }
    
    static SStateInitializing get() {
        return SStateInitializing.singleInstance;
    }
    
    public String displayName() {
        return JAService.resources.getTranString("Initializing");
    }
    
    static {
        SStateInitializing.singleInstance = new SStateInitializing();
    }
}
