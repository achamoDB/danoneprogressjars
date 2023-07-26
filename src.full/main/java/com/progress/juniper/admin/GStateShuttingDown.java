// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

public class GStateShuttingDown extends GState
{
    public static GStateShuttingDown singleInstance;
    
    public GStateShuttingDown() {
        super(GStateIdle.class);
    }
    
    public static GStateShuttingDown get() {
        return GStateShuttingDown.singleInstance;
    }
    
    public String displayName() {
        return GState.resources.getTranString("Shutting Down");
    }
    
    static {
        GStateShuttingDown.singleInstance = new GStateShuttingDown();
    }
}
