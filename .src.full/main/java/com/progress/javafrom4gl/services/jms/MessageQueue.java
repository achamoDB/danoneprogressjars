// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.javafrom4gl.services.jms;

import com.progress.javafrom4gl.ServiceRuntime;
import com.progress.ubroker.util.Logger;
import com.progress.javafrom4gl.Log;
import com.progress.ubroker.util.Queue;

class MessageQueue extends Queue
{
    static final String NEW_LINE;
    static final int SONIC_QUEUE_LIMIT = 10;
    private boolean locked;
    private String connectionID;
    private Log log;
    private WakeupMessage wakeupCall;
    private Semaphore sem;
    
    MessageQueue() {
        super("MessageEventServerQueue", 10, new Logger());
        this.wakeupCall = null;
        this.sem = null;
        (this.log = ServiceRuntime.getLog()).LogMsgln(3, true, Thread.currentThread().getName(), "Sonic receive message queue size set to 10");
        this.locked = false;
        this.sem = new Semaphore();
    }
    
    MessageQueue(final int i) {
        super("MessageEventServerQueue", i, new Logger());
        this.wakeupCall = null;
        this.sem = null;
        (this.log = ServiceRuntime.getLog()).LogMsgln(3, true, Thread.currentThread().getName(), "Sonic browse message queue size set to " + i);
        this.locked = false;
    }
    
    public void setBlock(final boolean b) {
        if (this.sem != null) {
            this.sem.shouldBlock(b);
        }
    }
    
    public boolean isBlock() {
        return this.sem != null && this.sem.getBlock();
    }
    
    public void block() {
        if (this.sem != null && !this.locked) {
            this.sem.block();
        }
    }
    
    public void resume() {
        if (this.sem != null) {
            this.sem.resume();
        }
    }
    
    void setConnID(final String connectionID) {
        this.connectionID = connectionID;
    }
    
    public synchronized String toString() {
        String s = MessageQueue.NEW_LINE + "Queued Messages: " + MessageQueue.NEW_LINE;
        for (Object o = this.findFirst(); o != null; o = this.findNext(o)) {
            s = s + "    " + o.toString();
        }
        return s + "End Queued Messages: " + MessageQueue.NEW_LINE;
    }
    
    public synchronized void enqueue(final Object o) throws QueueException {
        if (this.sem != null) {
            this.sem.shouldBlock(true);
        }
        super.enqueue(o);
    }
    
    synchronized MessageContainer dequeueMessage() {
        if (this.locked) {
            try {
                this.wait();
            }
            catch (Exception ex) {
                this.log.LogStackTrace(1, true, this.connectionID, ex);
            }
        }
        if (this.wakeupCall != null) {
            final WakeupMessage wakeupCall = this.wakeupCall;
            this.wakeupCall = null;
            return wakeupCall;
        }
        return (MessageContainer)this.dequeue();
    }
    
    synchronized void wakeup() {
        this.wakeupCall = new WakeupMessage();
        try {
            this.notifyAll();
        }
        catch (Exception ex) {
            this.log.LogStackTrace(1, true, this.connectionID, ex);
        }
    }
    
    synchronized void lock() {
        this.locked = true;
        this.sem.resume();
    }
    
    synchronized void unLock() {
        this.locked = false;
        try {
            this.notifyAll();
        }
        catch (Exception ex) {
            this.log.LogStackTrace(1, true, this.connectionID, ex);
        }
    }
    
    boolean isLocked() {
        return this.locked;
    }
    
    synchronized void removeMessages() throws Exception {
        while (!this.isEmpty()) {
            this.dequeue();
        }
    }
    
    public synchronized MessageContainer peek() {
        if (this.locked) {
            try {
                this.wait();
            }
            catch (Exception ex) {
                this.log.LogStackTrace(1, true, this.connectionID, ex);
            }
        }
        return (MessageContainer)this.findFirst();
    }
    
    static {
        NEW_LINE = new String(System.getProperty("line.separator"));
    }
    
    class Semaphore
    {
        private boolean shouldBlock;
        
        Semaphore() {
            this.shouldBlock = true;
        }
        
        public synchronized void block() {
            try {
                if (this.shouldBlock) {
                    this.wait();
                }
                else {
                    this.shouldBlock = true;
                }
            }
            catch (InterruptedException ex) {
                this.shouldBlock = true;
                this.notify();
            }
        }
        
        public synchronized void resume() {
            this.shouldBlock = false;
            this.notify();
        }
        
        public synchronized boolean getBlock() {
            return this.shouldBlock;
        }
        
        public synchronized void shouldBlock(final boolean shouldBlock) {
            this.shouldBlock = shouldBlock;
        }
    }
}
