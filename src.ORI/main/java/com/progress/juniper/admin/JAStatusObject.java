// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.io.Serializable;

public class JAStatusObject implements Serializable
{
    public String displayName;
    public String stateDescriptor;
    public boolean isStartable;
    public boolean isStopable;
    public boolean isIdle;
    public boolean isStarting;
    public boolean isInitializing;
    public boolean isRunning;
    public boolean isShuttingDown;
    
    public JAStatusObject() {
        this.displayName = "NONAME";
        this.stateDescriptor = "STATE-UNKNOWN";
        this.isStartable = false;
        this.isStopable = false;
        this.isIdle = false;
        this.isStarting = false;
        this.isInitializing = false;
        this.isRunning = false;
        this.isShuttingDown = false;
    }
    
    public String displayName() {
        return this.displayName;
    }
    
    public String stateDescriptor() {
        return this.stateDescriptor;
    }
    
    public boolean isStartable() {
        return this.isStartable;
    }
    
    public boolean isStopable() {
        return this.isStopable;
    }
    
    public boolean isIdle() {
        return this.isIdle;
    }
    
    public boolean isStarting() {
        return this.isStarting;
    }
    
    public boolean isInitializing() {
        return this.isInitializing;
    }
    
    public boolean isRunning() {
        return this.isRunning;
    }
    
    public boolean isShuttingDown() {
        return this.isShuttingDown;
    }
}
