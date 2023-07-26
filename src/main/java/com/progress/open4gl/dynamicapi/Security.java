// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

import java.util.StringTokenizer;
import com.progress.ubroker.util.NetworkProtocolOptions;
import java.net.UnknownHostException;
import java.net.InetAddress;
import com.progress.open4gl.Open4GLException;

public class Security
{
    public static void setProxyHost(final String s) throws Open4GLException {
        if (null == s || 0 == s.length()) {
            throw new Open4GLException("Proxy host name argument is required");
        }
        try {
            InetAddress.getByName(s);
        }
        catch (UnknownHostException ex) {
            throw new Open4GLException("Proxy host name argument is invalid.");
        }
        NetworkProtocolOptions.setProperty("psc.http.proxy", new String(s + ":" + Integer.toString(getProxyPort())));
    }
    
    public static void setProxyPort(final int i) throws Open4GLException {
        if (3 > i || 65536 < i) {
            throw new Open4GLException("The Proxy IP port number is out of range");
        }
        final String proxyHost = getProxyHost();
        if (null != proxyHost && 0 < proxyHost.length()) {
            NetworkProtocolOptions.setProperty("psc.http.proxy", new String(proxyHost + ":" + Integer.toString(i)));
        }
    }
    
    public static void setCertificateStore(final String s) throws Open4GLException {
        NetworkProtocolOptions.setProperty("psc.https.certpath", s);
    }
    
    public static String getProxyHost() {
        String s = null;
        final String property = NetworkProtocolOptions.getProperty("psc.http.proxy");
        if (null != property) {
            final StringTokenizer stringTokenizer = new StringTokenizer(property, ":");
            final int countTokens = stringTokenizer.countTokens();
            if (2 == countTokens) {
                s = stringTokenizer.nextToken();
            }
            else if (3 == countTokens) {
                stringTokenizer.nextToken();
                s = stringTokenizer.nextToken();
            }
        }
        return s;
    }
    
    public static int getProxyPort() {
        int int1 = 80;
        final String property = NetworkProtocolOptions.getProperty("psc.http.proxy");
        if (null != property) {
            String s = null;
            final StringTokenizer stringTokenizer = new StringTokenizer(property, ":");
            final int countTokens = stringTokenizer.countTokens();
            if (2 == countTokens) {
                stringTokenizer.nextToken();
                s = stringTokenizer.nextToken();
            }
            else if (3 == countTokens) {
                stringTokenizer.nextToken();
                stringTokenizer.nextToken();
                s = stringTokenizer.nextToken();
            }
            if (null != s) {
                try {
                    int1 = Integer.parseInt(s);
                }
                catch (Exception ex) {}
            }
        }
        return int1;
    }
    
    public static String getCertificateStore() {
        return NetworkProtocolOptions.getProperty("psc.https.certpath");
    }
    
    public static boolean getNoHostVerify() {
        boolean b = false;
        final String property = NetworkProtocolOptions.getProperty("psc.https.auth.serverdomain");
        try {
            if (null != property && "true".equalsIgnoreCase(property)) {
                b = true;
            }
        }
        catch (Exception ex) {}
        return b;
    }
    
    public static void setNoHostVerify(final boolean b) throws Open4GLException {
        NetworkProtocolOptions.setProperty("psc.https.auth.serverdomain", b ? "true" : "false");
    }
    
    public static void setProxyUserId(final String str) throws Open4GLException {
        if (null == str || 0 == str.length()) {
            throw new Open4GLException("Proxy user-id argument is required");
        }
        NetworkProtocolOptions.setProperty("psc.http.proxy.auth", new String(str + ":" + getProxyPassword()));
    }
    
    public static void setProxyPassword(final String s) throws Open4GLException {
        String str = "";
        if (null != s && 0 < s.length()) {
            str = s;
        }
        String proxyUserId = getProxyUserId();
        if (null == proxyUserId || 0 == proxyUserId.length()) {
            proxyUserId = "";
        }
        NetworkProtocolOptions.setProperty("psc.http.proxy.auth", new String(proxyUserId + ":" + str));
    }
    
    public static String getProxyUserId() {
        String s = null;
        final String property = NetworkProtocolOptions.getProperty("psc.http.proxy.auth");
        if (null != property) {
            final StringTokenizer stringTokenizer = new StringTokenizer(property, ":");
            final int countTokens = stringTokenizer.countTokens();
            if (countTokens || 2 == countTokens) {
                s = stringTokenizer.nextToken();
            }
            else if (3 == countTokens) {
                stringTokenizer.nextToken();
                s = stringTokenizer.nextToken();
            }
        }
        if (null != s && 1 == s.length() && 'x' == s.charAt(0)) {
            s = null;
        }
        return s;
    }
    
    public static String getProxyPassword() {
        final String property = NetworkProtocolOptions.getProperty("psc.http.proxy.auth");
        String s = "";
        if (null != property && 0 < property.length()) {
            final StringTokenizer stringTokenizer = new StringTokenizer(property, ":");
            final int countTokens = stringTokenizer.countTokens();
            if (countTokens != 0) {
                s = stringTokenizer.nextToken();
            }
            if (2 == countTokens) {
                stringTokenizer.nextToken();
                s = stringTokenizer.nextToken();
            }
            else if (3 == countTokens) {
                stringTokenizer.nextToken();
                stringTokenizer.nextToken();
                s = stringTokenizer.nextToken();
            }
        }
        return s;
    }
}
