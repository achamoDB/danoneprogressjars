// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

public class GStateStarting extends GState
{
    public static GStateStarting singleInstance;
    
    public GStateStarting() {
        super(GStateInitializing.class);
    }
    
    public static GStateStarting get() {
        return GStateStarting.singleInstance;
    }
    
    public String displayName() {
        return GState.resources.getTranString("Starting");
    }
    
    static {
        GStateStarting.singleInstance = new GStateStarting();
    }
}
