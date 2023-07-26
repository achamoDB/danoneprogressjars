// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.util;

import java.net.MalformedURLException;
import com.progress.common.util.PscURLParser;

public class SocketConnectionInfoEx extends PscURLParser
{
    public static final String DEF_HOST = "localhost";
    public static final String[] PROTOCOL_NAMES;
    public static final int FILE_PROTOCOL = 0;
    public static final int APPSERVER_PROTOCOL = 1;
    public static final int HTTP_PROTOCOL = 2;
    public static final int HTTPS_PROTOCOL = 3;
    public static final int APPSERVERDC_PROTOCOL = 4;
    public static final int APPSERVERS_PROTOCOL = 5;
    public static final int APPSERVERDCS_PROTOCOL = 6;
    public static final String DEF_PROTOCOL = "AppServer";
    public static final int DEF_PORT_NAMESERVER = 5162;
    public static final int DEF_PORT_APPSERVER = 3090;
    public static final int DEF_PORT_WEBSPEED = 3090;
    public static final int DEF_PORT_HTTP = 80;
    public static final int DEF_PORT_HTTPS = 443;
    
    public SocketConnectionInfoEx(final String s) throws MalformedURLException {
        super.parseURL(s);
    }
    
    public void setProtocolType(final int n) {
        if (n < SocketConnectionInfoEx.PROTOCOL_NAMES.length) {
            super.setScheme(SocketConnectionInfoEx.PROTOCOL_NAMES[n]);
        }
    }
    
    public void setPort(final int i) {
        super.setPort(Integer.toString(i));
    }
    
    public void setProtocol(final String scheme) {
        super.setScheme(scheme);
    }
    
    public String getProtocol() {
        return super.getScheme();
    }
    
    public int getProtocolType() {
        final String scheme = super.getScheme();
        int n = -1;
        for (int i = 0; i < SocketConnectionInfoEx.PROTOCOL_NAMES.length; ++i) {
            if (scheme.equalsIgnoreCase(SocketConnectionInfoEx.PROTOCOL_NAMES[i])) {
                n = i;
                break;
            }
        }
        return n;
    }
    
    static {
        PROTOCOL_NAMES = new String[] { "file", "appserver", "http", "https", "appserverDC", "AppServerS", "AppServerDCS" };
    }
}
