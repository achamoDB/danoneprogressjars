// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.util;

public class NetLib
{
    private static native long getServByNameJNI(final String p0, final String p1);
    
    private static native long getHostByNameJNI(final String p0);
    
    private static native int getPortNumberJNI(final long p0);
    
    private static native String getHostNameJNI(final long p0);
    
    private static native int getPortNumByNameJNI(final String p0, final String p1);
    
    public static Servent getServByName(final String s, final String s2) {
        final long servByNameJNI = getServByNameJNI(s, s2);
        Servent servent;
        if (servByNameJNI != 0L) {
            servent = new Servent(servByNameJNI);
        }
        else {
            servent = null;
        }
        return servent;
    }
    
    public static Hostent getHostByName(final String s) {
        final long hostByNameJNI = getHostByNameJNI(s);
        Hostent hostent;
        if (hostByNameJNI != 0L) {
            hostent = new Hostent(hostByNameJNI);
        }
        else {
            hostent = null;
        }
        return hostent;
    }
    
    public static int getPortNumByName(final String s, final String s2) {
        return getPortNumByNameJNI(s, s2);
    }
    
    static {
        System.load(new InstallPath().fullyQualifyFile("jni_util.dll"));
    }
    
    public static class Servent
    {
        private long address;
        
        private Servent(final long address) {
            this.address = address;
        }
        
        public int getPortNumber() {
            return getPortNumberJNI(this.address);
        }
    }
    
    public static class Hostent
    {
        private long address;
        
        private Hostent(final long address) {
            this.address = address;
        }
        
        public String getHostName() {
            return getHostNameJNI(this.address);
        }
    }
}
