// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.util;

import java.net.MalformedURLException;
import java.net.URL;

public class SocketConnectionInfo
{
    public static final String DEF_HOST = "localhost";
    public static final String DEF_PROTOCOL = "file";
    public static final int DEF_PORT_NAMESERVER = 5162;
    public static final int DEF_PORT_APPSERVER = 3090;
    public static final int DEF_PORT_WEBSPEED = 3090;
    private String url;
    private String host;
    private String service;
    private String protocol;
    private int port;
    
    public SocketConnectionInfo(final String spec) throws MalformedURLException {
        final URL url = new URL(spec);
        this.protocol = "file";
        this.host = url.getHost();
        if (this.host.equals("")) {
            this.host = "localhost";
        }
        this.service = url.getFile().substring(1);
        this.port = url.getPort();
        if (this.port == -1) {
            throw new MalformedURLException("Port number required");
        }
    }
    
    public SocketConnectionInfo(final String spec, final int port) throws MalformedURLException {
        final URL url = new URL(spec);
        this.protocol = "file";
        this.host = url.getHost();
        if (this.host.equals("")) {
            this.host = "localhost";
        }
        final String file = url.getFile();
        if (file.length() == 0 || file.equals("/")) {
            throw new MalformedURLException("Missing service name");
        }
        this.service = file.substring(1);
        this.port = url.getPort();
        if (this.port == -1) {
            this.port = port;
        }
    }
    
    public void setHost(final String host) {
        this.host = host;
    }
    
    public void setService(final String service) {
        this.service = service;
    }
    
    public void setProtocol(final String protocol) {
        this.protocol = protocol;
    }
    
    public void setPort(final int port) {
        this.port = port;
    }
    
    public String getHost() {
        return this.host;
    }
    
    public String getService() {
        return this.service;
    }
    
    public String getProtocol() {
        return this.protocol;
    }
    
    public int getPort() {
        return this.port;
    }
    
    public String getUrl() {
        return this.protocol + "://" + this.host + ":" + this.port + "/" + this.service;
    }
}
