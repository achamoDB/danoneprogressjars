// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa.tools;

import java.io.Serializable;

public class WsaLoginInfo implements Serializable
{
    String asUsername;
    String asPwd;
    String wsUsername;
    String wsPwd;
    String wsaUsername;
    String wsaPwd;
    boolean bypassPropChecking;
    
    public WsaLoginInfo() {
        this.asUsername = null;
        this.asPwd = null;
        this.wsUsername = null;
        this.wsPwd = null;
        this.wsaUsername = null;
        this.wsaPwd = null;
        this.bypassPropChecking = false;
    }
    
    public WsaLoginInfo(final String asUsername, final String asPwd, final String wsUsername, final String wsPwd) {
        this.asUsername = null;
        this.asPwd = null;
        this.wsUsername = null;
        this.wsPwd = null;
        this.wsaUsername = null;
        this.wsaPwd = null;
        this.bypassPropChecking = false;
        this.bypassPropChecking = false;
        this.asUsername = asUsername;
        this.asPwd = asPwd;
        this.wsUsername = wsUsername;
        this.wsPwd = wsPwd;
    }
    
    public void setAsLoginInfo(final String asUsername, final String asPwd) {
        this.bypassPropChecking = false;
        this.asUsername = asUsername;
        this.asPwd = asPwd;
    }
    
    public void setWsLoginInfo(final String wsUsername, final String wsPwd) {
        this.bypassPropChecking = false;
        this.wsUsername = wsUsername;
        this.wsPwd = wsPwd;
    }
    
    public void setWsaLoginInfo(final String wsaUsername, final String wsaPwd) {
        this.bypassPropChecking = true;
        this.wsaUsername = wsaUsername;
        this.wsaPwd = wsaPwd;
    }
    
    public boolean getBypassPropChecking() {
        return this.bypassPropChecking;
    }
    
    public String getAsUsername() {
        return this.asUsername;
    }
    
    public String getAsPwd() {
        return this.asPwd;
    }
    
    public String getWsUsername() {
        return this.wsUsername;
    }
    
    public String getWsPwd() {
        return this.wsPwd;
    }
    
    public String getWsaUsername() {
        return this.wsaUsername;
    }
    
    public String getWsaPwd() {
        return this.wsaPwd;
    }
}
