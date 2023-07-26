// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa.admin;

import java.io.Serializable;

public class WebServerLoginContext implements Serializable
{
    String wsUsername;
    String wsPwd;
    String wsaUrl;
    String proxyUsername;
    String proxyPwd;
    String proxyHost;
    String proxyPort;
    boolean useProxy;
    
    public WebServerLoginContext() {
        this.wsUsername = null;
        this.wsPwd = null;
        this.wsaUrl = null;
        this.proxyUsername = null;
        this.proxyPwd = null;
        this.proxyHost = null;
        this.proxyPort = null;
        this.useProxy = false;
    }
    
    public WebServerLoginContext(final String wsUsername, final String wsPwd) {
        this.wsUsername = null;
        this.wsPwd = null;
        this.wsaUrl = null;
        this.proxyUsername = null;
        this.proxyPwd = null;
        this.proxyHost = null;
        this.proxyPort = null;
        this.useProxy = false;
        this.wsUsername = wsUsername;
        this.wsPwd = wsPwd;
    }
    
    public void setProxyInfo(final String proxyHost, final String proxyPort, final String proxyUsername, final String proxyPwd, final boolean useProxy) {
        this.useProxy = useProxy;
        this.proxyHost = proxyHost;
        this.proxyPort = proxyPort;
        this.proxyUsername = proxyUsername;
        this.proxyPwd = proxyPwd;
    }
    
    public void setWsLoginInfo(final String wsUsername, final String wsPwd) {
        this.wsUsername = wsUsername;
        this.wsPwd = wsPwd;
    }
    
    public boolean getUseProxy() {
        return this.useProxy;
    }
    
    public String getWsUsername() {
        return this.wsUsername;
    }
    
    public String getWsPwd() {
        return this.wsPwd;
    }
    
    public String getProxyUsername() {
        return this.proxyUsername;
    }
    
    public String getProxyPwd() {
        return this.proxyPwd;
    }
    
    public String getProxyHost() {
        return this.proxyHost;
    }
    
    public String getProxyPort() {
        return this.proxyPort;
    }
}
