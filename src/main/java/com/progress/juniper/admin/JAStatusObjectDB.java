// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

public class JAStatusObjectDB extends JAStatusObject
{
    protected IJAConfiguration currConfig;
    boolean allServerGroupsRunning;
    boolean someServerGroupsRunning;
    boolean aBIRunning;
    boolean anAIRunning;
    boolean auxiliariesAreRunning;
    
    JAStatusObjectDB() {
        this.currConfig = null;
        this.allServerGroupsRunning = false;
        this.someServerGroupsRunning = false;
        this.aBIRunning = false;
        this.anAIRunning = false;
        this.auxiliariesAreRunning = false;
    }
    
    public IJAConfiguration currConfig() {
        return this.currConfig;
    }
    
    public void setCurrConfig(final IJAConfiguration currConfig) {
        this.currConfig = currConfig;
    }
    
    public boolean allServerGroupsRunning() {
        return this.allServerGroupsRunning;
    }
    
    public boolean someServerGroupsRunning() {
        return this.someServerGroupsRunning;
    }
    
    public boolean aBIRunning() {
        return this.aBIRunning;
    }
    
    public boolean anAIRunning() {
        return this.anAIRunning;
    }
    
    public boolean auxiliariesAreRunning() {
        return this.auxiliariesAreRunning;
    }
}
