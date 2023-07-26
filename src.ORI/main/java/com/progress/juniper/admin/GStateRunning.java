// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

public class GStateRunning extends GState
{
    public static GStateRunning singleInstance;
    
    public GStateRunning() {
        super(GStateShuttingDown.class);
    }
    
    public static GStateRunning get() {
        return GStateRunning.singleInstance;
    }
    
    public String displayName() {
        return GState.resources.getTranString("Running");
    }
    
    static {
        GStateRunning.singleInstance = new GStateRunning();
    }
}
