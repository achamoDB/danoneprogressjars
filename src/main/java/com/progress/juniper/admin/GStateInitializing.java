// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

public class GStateInitializing extends GState
{
    public static GStateInitializing singleInstance;
    
    public GStateInitializing() {
        super(GStateRunning.class);
    }
    
    public static GStateInitializing get() {
        return GStateInitializing.singleInstance;
    }
    
    public String displayName() {
        return GState.resources.getTranString("Initializing");
    }
    
    static {
        GStateInitializing.singleInstance = new GStateInitializing();
    }
}
