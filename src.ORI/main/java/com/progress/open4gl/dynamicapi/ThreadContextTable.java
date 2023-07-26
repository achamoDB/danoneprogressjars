// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

import java.util.Enumeration;
import java.util.Hashtable;

class ThreadContextTable
{
    private Hashtable table;
    private int cleanUpThreshhold;
    private boolean noContext;
    
    ThreadContextTable(final int cleanUpThreshhold) {
        this.table = new Hashtable();
        this.cleanUpThreshhold = cleanUpThreshhold;
    }
    
    void put(final Object o) {
        if (this.table.size() >= this.cleanUpThreshhold) {
            this.cleanUp();
        }
        this.table.put(Thread.currentThread(), new ThreadContext(o));
    }
    
    Object get() {
        this.noContext = false;
        final ThreadContext threadContext = this.table.get(Thread.currentThread());
        if (threadContext == null) {
            this.noContext = true;
            return null;
        }
        return threadContext.getVal();
    }
    
    void cleanUp() {
        final Enumeration<Thread> keys = this.table.keys();
        while (keys.hasMoreElements()) {
            final Thread key = keys.nextElement();
            if (!key.isAlive()) {
                this.table.remove(key);
            }
        }
    }
    
    boolean noContext() {
        return this.noContext;
    }
    
    static class ThreadContext
    {
        Object value;
        
        ThreadContext(final Object value) {
            this.value = value;
        }
        
        Object getVal() {
            return this.value;
        }
    }
}
