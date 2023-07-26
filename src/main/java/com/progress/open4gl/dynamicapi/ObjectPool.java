// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

import java.util.LinkedList;
import com.progress.common.ehnlog.IAppLogger;
import java.util.List;
import java.text.DecimalFormat;

public abstract class ObjectPool
{
    public static final long WAIT_FOREVER = -1L;
    public static final long CHECK_NO_WAIT = 0L;
    private static DecimalFormat fmt6;
    private List m_waitQueue;
    private IAppLogger m_log;
    private int m_maxWaiters;
    
    public ObjectPool(final IAppLogger log) {
        this.m_waitQueue = new LinkedList();
        this.m_log = log;
        this.m_maxWaiters = 0;
    }
    
    public synchronized ObjectPoolStats getSummaryStats() {
        return new ObjectPoolStats(this.m_waitQueue.size(), this.m_maxWaiters);
    }
    
    public Object reserve(final long n) {
        final ObjectHolder objectHolder;
        synchronized (this) {
            final Object o = this.findAvailableObject();
            if (o != null || n == 0L) {
                return o;
            }
            objectHolder = new ObjectHolder();
            this.m_waitQueue.add(objectHolder);
        }
        this.waitForObject(objectHolder, n);
        Object o;
        if (!objectHolder.isEmpty()) {
            o = objectHolder.getObject();
        }
        else {
            synchronized (this) {
                if (objectHolder.isEmpty()) {
                    this.removeWaiter(objectHolder);
                }
                o = objectHolder.getObject();
            }
        }
        return o;
    }
    
    public void release(final Object object) {
        synchronized (this) {
            if (this.m_waitQueue.size() == 0) {
                this.makeObjectAvailable(object);
            }
            else {
                final ObjectHolder objectHolder = this.m_waitQueue.remove(0);
                synchronized (objectHolder) {
                    objectHolder.setObject(object);
                    objectHolder.notify();
                }
            }
        }
    }
    
    public void cancelAllWaiters() {
        synchronized (this) {
            while (this.m_waitQueue.size() > 0) {
                final ObjectHolder objectHolder = this.m_waitQueue.remove(0);
                synchronized (objectHolder) {
                    objectHolder.cancel();
                    objectHolder.notify();
                }
            }
        }
    }
    
    public abstract Object findAvailableObject();
    
    public abstract void makeObjectAvailable(final Object p0);
    
    private void waitForObject(final ObjectHolder objectHolder, long n) {
        synchronized (objectHolder) {
            long n2;
            n = (n2 = ((n == -1L) ? Long.MAX_VALUE : (1000L * n)));
            final long currentTimeMillis = System.currentTimeMillis();
            while (objectHolder.isEmpty()) {
                try {
                    objectHolder.wait(n2);
                }
                catch (Exception ex) {}
                if (objectHolder.isCancelled()) {
                    break;
                }
                if (!objectHolder.isEmpty()) {
                    break;
                }
                final long n3 = System.currentTimeMillis() - currentTimeMillis;
                if (n3 >= n) {
                    break;
                }
                n2 = n - n3;
            }
        }
    }
    
    private void removeWaiter(final ObjectHolder objectHolder) {
        final int index = this.m_waitQueue.indexOf(objectHolder);
        if (index != -1) {
            this.m_waitQueue.remove(index);
        }
    }
    
    static {
        ObjectPool.fmt6 = new DecimalFormat("000000");
    }
    
    public class ObjectPoolStats
    {
        private int numObjects;
        private int maxObjects;
        
        public ObjectPoolStats(final int numObjects, final int maxObjects) {
            this.numObjects = numObjects;
            this.maxObjects = maxObjects;
        }
        
        public int getNumObjects() {
            return this.numObjects;
        }
        
        public int getMaxObjects() {
            return this.maxObjects;
        }
    }
    
    public class ObjectHolder
    {
        private Object m_holder;
        private int m_id;
        private String m_waiter;
        private boolean m_cancelled;
        
        public ObjectHolder() {
            this.m_holder = null;
            this.m_id = this.hashCode();
            this.m_waiter = Thread.currentThread().getName();
            this.m_cancelled = false;
        }
        
        public String toString() {
            return "<holder-" + ObjectPool.fmt6.format(this.m_id) + "|" + this.m_waiter + "|" + this.m_holder + ">";
        }
        
        public boolean isEmpty() {
            return this.m_holder == null;
        }
        
        public void cancel() {
            this.m_holder = null;
            this.m_cancelled = true;
        }
        
        public boolean isCancelled() {
            return this.m_cancelled;
        }
        
        public Object getObject() {
            return this.m_holder;
        }
        
        public void setObject(final Object holder) {
            this.m_holder = holder;
        }
    }
}
