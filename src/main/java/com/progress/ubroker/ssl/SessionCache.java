// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.ssl;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;

public class SessionCache
{
    private static final SessionCache a;
    private HashMap b;
    private PrintStream c;
    
    private SessionCache() {
        this.b = new HashMap();
        this.c = new PrintStream(System.err);
    }
    
    public static SessionCache getInstance() {
        return SessionCache.a;
    }
    
    public void setDebugStream(final PrintStream c) {
        this.c = c;
    }
    
    public synchronized void put(final String s, final int n, final SSLSocketUtilsFull.SSLInfo sslInfo) {
        final CacheEntry cacheEntry = new CacheEntry(sslInfo);
        final String a = this.a(s, n);
        final String id = cacheEntry.getID();
        final CacheEntry a2 = this.a(a, cacheEntry);
        if (a2 == null) {
            this.c.println("Creating cache entry for: " + a + " ID: " + id);
        }
        else if (a2.getID().equals(id)) {
            this.c.println("Refreshing cache entry for: " + a + " ID: " + id);
        }
        else {
            this.c.println("Replacing cache entry for: " + a + " ID: " + id);
        }
    }
    
    public synchronized SSLSocketUtilsFull.SSLInfo get(final String s, final int n) {
        final String a = this.a(s, n);
        final CacheEntry a2 = this.a(a);
        if (a2 == null) {
            this.c.println("Cache miss for: " + a);
            return null;
        }
        final String id = a2.getID();
        if (a2.isInUse()) {
            this.c.println("Cache entry in use for: " + a + " ID: " + id);
            return null;
        }
        this.c.println("Reserving cache entry for: " + a + " ID: " + id);
        a2.reserve();
        return a2.getSession();
    }
    
    public synchronized void release(final String s, final int n) {
        final String a = this.a(s, n);
        final CacheEntry a2 = this.a(a);
        if (a2 != null && a2.isInUse()) {
            this.c.println("Releasing cache entry for: " + a + " ID: " + a2.getID());
            a2.unreserve();
        }
    }
    
    public synchronized void remove(final String s, final int n) {
        final String a = this.a(s, n);
        if (this.b.remove(a) != null) {
            this.c.println("Removing entry for: " + a);
        }
    }
    
    private String a(final String str, final int i) {
        final StringBuffer sb = new StringBuffer(str);
        sb.append(":");
        sb.append(Integer.toString(i));
        return sb.toString();
    }
    
    private CacheEntry a(final String key) {
        return this.b.get(key);
    }
    
    private CacheEntry a(final String key, final CacheEntry value) {
        return this.b.put(key, value);
    }
    
    static {
        a = new SessionCache();
    }
    
    private class CacheEntry
    {
        private SSLSocketUtilsFull.SSLInfo a;
        private boolean b;
        
        public CacheEntry(final SSLSocketUtilsFull.SSLInfo a) {
            this.a = a;
            this.reserve();
        }
        
        public SSLSocketUtilsFull.SSLInfo getSession() {
            return this.a;
        }
        
        public void reserve() {
            this.b = true;
        }
        
        public void unreserve() {
            this.b = false;
        }
        
        public boolean isInUse() {
            return this.b;
        }
        
        public String getID() {
            return this.getSession().getSessionId();
        }
    }
}
