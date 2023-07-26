// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

import com.progress.open4gl.Open4GLError;
import java.util.Enumeration;
import com.progress.open4gl.RunTimeProperties;
import java.util.Vector;
import java.util.Hashtable;

class SessionLock
{
    private boolean locked;
    private String name;
    private Hashtable table;
    private Vector delayedRequests;
    private Session session;
    private Tracer tracer;
    
    SessionLock(final Session session) {
        this.tracer = RunTimeProperties.tracer;
        this.session = session;
        this.table = new Hashtable();
        this.locked = false;
        this.delayedRequests = null;
    }
    
    synchronized void interruptWaiters() {
        this.tracer.print("   STOP: In interruptWaiters().");
        final Enumeration<Thread> keys = this.table.keys();
        while (keys.hasMoreElements()) {
            this.tracer.print("   STOP: interruptWaiters() - interrupt a waiter ");
            keys.nextElement().interrupt();
        }
    }
    
    int lockQueue(final boolean b, final String s, final DelayedRequest delayedRequest) throws InterruptedException {
        return this.lock0(b, s, delayedRequest);
    }
    
    boolean lock(final boolean b, final String s) throws InterruptedException {
        final int lock0 = this.lock0(b, s, null);
        if (lock0 == 1) {
            return true;
        }
        if (lock0 == 0) {
            return false;
        }
        throw new Open4GLError();
    }
    
    private void addDelayedRequest(final DelayedRequest obj) {
        if (this.delayedRequests == null) {
            this.delayedRequests = new Vector();
        }
        this.delayedRequests.addElement(obj);
    }
    
    private void sendDelayedRequests() {
        if (this.delayedRequests == null || this.delayedRequests.isEmpty()) {
            return;
        }
        final Enumeration<DelayedRequest> elements = this.delayedRequests.elements();
        while (elements.hasMoreElements()) {
            elements.nextElement().sendRequest(this.session);
        }
        this.delayedRequests.removeAllElements();
    }
    
    private synchronized int lock0(final boolean b, final String name, final DelayedRequest delayedRequest) throws InterruptedException {
        while (this.locked) {
            if (delayedRequest != null && !b) {
                this.addDelayedRequest(delayedRequest);
                return 2;
            }
            if (b) {
                return 0;
            }
            try {
                this.table.put(Thread.currentThread(), new Integer(0));
                this.wait();
            }
            catch (InterruptedException ex) {
                throw ex;
            }
            finally {
                this.table.remove(Thread.currentThread());
                if (Thread.interrupted()) {
                    throw new InterruptedException();
                }
            }
        }
        this.locked = true;
        this.name = name;
        return 1;
    }
    
    String getName() {
        return this.name;
    }
    
    boolean isLocked() {
        return this.locked;
    }
    
    synchronized void unLock() {
        this.sendDelayedRequests();
        this.locked = false;
        this.notifyAll();
    }
}
