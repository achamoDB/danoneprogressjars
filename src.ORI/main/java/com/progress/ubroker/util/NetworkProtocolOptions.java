// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.util;

import java.util.Hashtable;
import java.util.Enumeration;
import java.util.Properties;

public class NetworkProtocolOptions
{
    public static final String TCP_SOTIMEOUT = "psc.tcp.sotimeout";
    public static final String TCP_NAMESERVER = "psc.tcp.nameserver";
    public static final String HTTP_PROXY_HOST_OPTION = "psc.http.proxy";
    public static final String HTTP_PROXY_AUTH_OPTION = "psc.http.proxy.auth";
    public static final String HTTP_TIMEOUT_OPTION = "psc.http.timeout";
    public static final String HTTP_AUTH_OPTION = "psc.http.auth";
    public static final String HTTPS_CERT_PATH = "psc.https.certpath";
    public static final String HTTPS_DEFAULT_JAVA_CERT_PATH = "psccerts.jar";
    public static final String HTTPS_DEFAULT_WINDOWS_CERT_PATH = "psccerts.zip";
    public static final String HTTPS_DEFAULT_APPLET_CERT_PATH = "psccerts.dcl";
    public static final String HTTPS_SERVER_DOMAIN_AUTH = "psc.https.auth.serverdomain";
    public static final String HTTPS_SERVER_DOMAIN = "psc.https.auth.domain";
    public static final String HTTPS_CERT_LOADER_DEBUG = "psc.certloader.debug";
    private static Properties m_protocolOptions;
    private static boolean m_initDone;
    
    public static Properties getOptions() throws Exception {
        if (!NetworkProtocolOptions.m_initDone) {
            init();
        }
        return NetworkProtocolOptions.m_protocolOptions;
    }
    
    public static Object setProperty(final String key, final String value) {
        if (!NetworkProtocolOptions.m_initDone) {
            init();
        }
        return ((Hashtable<String, String>)NetworkProtocolOptions.m_protocolOptions).put(key, value);
    }
    
    public static String getProperty(final String key) {
        if (!NetworkProtocolOptions.m_initDone) {
            init();
        }
        return NetworkProtocolOptions.m_protocolOptions.getProperty(key);
    }
    
    public static Enumeration propertyNames() {
        if (!NetworkProtocolOptions.m_initDone) {
            init();
        }
        return NetworkProtocolOptions.m_protocolOptions.propertyNames();
    }
    
    public static String getStringProperty(final Properties properties, final String key, final String s) {
        String s2 = s;
        final Properties properties2 = (null == properties) ? NetworkProtocolOptions.m_protocolOptions : properties;
        if (null != key && 0 < key.length()) {
            final String property = properties2.getProperty(key);
            s2 = ((null != property) ? property : s);
        }
        return s2;
    }
    
    public static long getLongProperty(final Properties properties, final String key, final long n) {
        long longFromString = n;
        final Properties properties2 = (null == properties) ? NetworkProtocolOptions.m_protocolOptions : properties;
        if (null != key && 0 < key.length()) {
            longFromString = longFromString(properties2.getProperty(key), n);
        }
        return longFromString;
    }
    
    public static int getIntProperty(final Properties properties, final String key, final int n) {
        int intFromString = n;
        final Properties properties2 = (null == properties) ? NetworkProtocolOptions.m_protocolOptions : properties;
        if (null != key && 0 < key.length()) {
            intFromString = intFromString(properties2.getProperty(key), n);
        }
        return intFromString;
    }
    
    public static boolean getBooleanProperty(final Properties properties, final String key, final boolean b) {
        boolean booleanFromString = b;
        final Properties properties2 = (null == properties) ? NetworkProtocolOptions.m_protocolOptions : properties;
        if (null != key && 0 < key.length()) {
            booleanFromString = booleanFromString(properties2.getProperty(key), b);
        }
        return booleanFromString;
    }
    
    public static long longFromString(final String s, final long n) {
        long long1 = n;
        try {
            if (null != s && 0 < s.length()) {
                long1 = Long.parseLong(s);
            }
        }
        catch (Throwable t) {}
        return long1;
    }
    
    public static int intFromString(final String s, final int n) {
        int int1 = n;
        try {
            if (null != s && 0 < s.length()) {
                int1 = Integer.parseInt(s);
            }
        }
        catch (Throwable t) {}
        return int1;
    }
    
    public static boolean booleanFromString(final String s, final boolean b) {
        boolean booleanValue = b;
        try {
            if (null != s && 0 < s.length()) {
                booleanValue = Boolean.valueOf(s);
            }
        }
        catch (Throwable t) {}
        return booleanValue;
    }
    
    private static void init() {
        if (!NetworkProtocolOptions.m_initDone) {
            if (null == NetworkProtocolOptions.m_protocolOptions) {
                try {
                    NetworkProtocolOptions.m_protocolOptions = new Properties(System.getProperties());
                }
                catch (SecurityException ex) {
                    NetworkProtocolOptions.m_protocolOptions = new Properties();
                }
                finally {
                    if (null == NetworkProtocolOptions.m_protocolOptions) {
                        NetworkProtocolOptions.m_protocolOptions = new Properties();
                    }
                }
            }
            NetworkProtocolOptions.m_initDone = true;
        }
    }
    
    static {
        NetworkProtocolOptions.m_protocolOptions = null;
        NetworkProtocolOptions.m_initDone = false;
        init();
    }
}
