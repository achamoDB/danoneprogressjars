// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

public class GStateIdle extends GState
{
    public static GStateIdle singleInstance;
    
    public GStateIdle() {
        super(GStateStarting.class);
    }
    
    public static GStateIdle get() {
        return GStateIdle.singleInstance;
    }
    
    public String displayName() {
        return GState.resources.getTranString("Not Running");
    }
    
    static {
        GStateIdle.singleInstance = new GStateIdle();
    }
}
