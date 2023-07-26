// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.util;

import com.progress.common.exception.ProException;
import com.progress.common.ehnlog.IAppLogger;

public class Queue extends List
{
    public static final int NOQUEUELIMIT = 0;
    public static final int DEF_QUEUELIMIT = 0;
    static final byte STATE_READY = 0;
    static final byte STATE_CLOSING = 1;
    static final byte STATE_CLOSED = 2;
    static final String[] DESC_STATE;
    byte queueState;
    int maxQueueDepth;
    int enqueueWaits;
    int currentQueueDepth;
    int queueLimit;
    
    public Queue(final String s, final int queueLimit, final Logger logger) {
        super(s, logger);
        this.maxQueueDepth = 0;
        this.currentQueueDepth = 0;
        this.enqueueWaits = 0;
        this.queueLimit = queueLimit;
        this.queueState = 0;
    }
    
    public Queue(final String s, final int queueLimit, final IAppLogger appLogger) {
        super(s, appLogger);
        this.maxQueueDepth = 0;
        this.currentQueueDepth = 0;
        this.enqueueWaits = 0;
        this.queueLimit = queueLimit;
        this.queueState = 0;
    }
    
    public synchronized void enqueue_old_old(final Object obj) throws QueueException {
        final boolean empty = this.isEmpty();
        if (this.queueState != 0) {
            if (super.log != null) {
                super.log.LogMsgln(2, 5, false, "enqueueObject (" + obj + ") to (" + super.getListName() + ") failed : queue closed.");
            }
            if (super.applog != null && super.applog.ifLogBasic(2L, 1)) {
                super.applog.logBasic(1, "enqueueObject (" + obj + ") to (" + super.getListName() + ") failed : queue closed.");
            }
            throw new QueueClosedException("enqueue failed : " + obj);
        }
        if (this.isFull()) {
            ++this.enqueueWaits;
        }
        while (this.isFull()) {
            try {
                this.wait();
            }
            catch (InterruptedException ex) {
                if (super.log != null) {
                    super.log.LogMsgln(2, 5, false, "enqueue " + super.getListName() + " InterruptedExeception " + ex.getMessage());
                }
                if (super.applog == null || !super.applog.ifLogBasic(2L, 1)) {
                    continue;
                }
                super.applog.logBasic(1, "enqueue " + super.getListName() + " InterruptedExeception " + ex.getMessage());
            }
        }
        this.insertAtBack(obj);
        ++this.currentQueueDepth;
        if (super.log != null && !super.log.ignore(5)) {
            super.log.LogMsgln(2, 5, false, "enqueue() currentQueueDepth=" + this.currentQueueDepth);
        }
        if (super.applog != null && super.applog.ifLogBasic(2L, 1)) {
            super.applog.logBasic(1, "enqueue() currentQueueDepth=" + this.currentQueueDepth);
        }
        if (this.currentQueueDepth > this.maxQueueDepth) {
            this.maxQueueDepth = this.currentQueueDepth;
        }
        if (empty) {
            this.notifyAll();
        }
        this.yieldControl();
    }
    
    public synchronized void enqueue_old(final Object o) throws QueueException {
        this.enqueueObject(o, false);
    }
    
    public void enqueue(final Object o) throws QueueException {
        synchronized (this) {
            this.enqueueObject(o, false);
        }
        this.yieldControl();
    }
    
    public synchronized void enqueuePriority_old(final Object o) throws QueueException {
        this.enqueueObject(o, true);
    }
    
    public void enqueuePriority(final Object o) throws QueueException {
        synchronized (this) {
            this.enqueueObject(o, true);
        }
        this.yieldControl();
    }
    
    public synchronized Object dequeue() {
        boolean b = this.isFull();
        while (this.isEmpty()) {
            try {
                this.wait();
            }
            catch (InterruptedException ex) {
                if (super.log != null) {
                    super.log.LogMsgln(2, 5, false, "dequeue " + super.getListName() + " InterruptedExeception " + ex.getMessage());
                }
                if (super.applog != null && super.applog.ifLogBasic(2L, 1)) {
                    super.applog.logBasic(1, "dequeue " + super.getListName() + " InterruptedExeception " + ex.getMessage());
                }
            }
            b = this.isFull();
        }
        Object removeFromFront;
        try {
            removeFromFront = this.removeFromFront();
        }
        catch (EmptyListException ex2) {
            if (super.log != null) {
                super.log.LogMsgN(2, 1, true, 7665689515738013635L, new Object[] { super.getListName(), ex2.getMessage() });
            }
            if (super.applog != null) {
                super.applog.logError(7665689515738013635L, new Object[] { super.getListName(), ex2.getMessage() });
            }
            removeFromFront = null;
        }
        --this.currentQueueDepth;
        if (b) {
            this.notifyAll();
        }
        this.updateQueueState();
        return removeFromFront;
    }
    
    public synchronized Object poll(final long n) {
        boolean b = this.isFull();
        if (this.isEmpty()) {
            try {
                this.wait(n);
            }
            catch (InterruptedException ex) {
                if (super.log != null) {
                    super.log.LogMsgln(2, 5, false, "poll " + this.getListName() + " InterruptedExeception " + ex.getMessage());
                }
                if (super.applog != null && super.applog.ifLogBasic(2L, 1)) {
                    super.applog.logBasic(1, "poll " + this.getListName() + " InterruptedExeception " + ex.getMessage());
                }
            }
            b = this.isFull();
        }
        if (this.isEmpty()) {
            this.updateQueueState();
            return null;
        }
        Object removeFromFront;
        try {
            removeFromFront = this.removeFromFront();
        }
        catch (EmptyListException ex2) {
            if (super.log != null) {
                super.log.LogMsgln(2, 5, false, "dequeue " + super.getListName() + " EmptyListException " + ex2.getMessage());
            }
            if (super.applog != null && super.applog.ifLogBasic(2L, 1)) {
                super.applog.logBasic(1, "dequeue " + super.getListName() + " EmptyListException " + ex2.getMessage());
            }
            removeFromFront = null;
        }
        --this.currentQueueDepth;
        if (b) {
            this.notifyAll();
        }
        if (super.log != null && !super.log.ignore(5)) {
            super.log.LogMsgln(2, 5, false, "poll() currentQueueDepth=" + this.currentQueueDepth);
        }
        if (super.applog != null && super.applog.ifLogBasic(2L, 1)) {
            super.applog.logBasic(1, "poll() currentQueueDepth=" + this.currentQueueDepth);
        }
        this.updateQueueState();
        return removeFromFront;
    }
    
    public synchronized Object removeFromQueue(final Object o) {
        Object removeFromList;
        try {
            removeFromList = this.removeFromList(o);
            if (removeFromList != null) {
                --this.currentQueueDepth;
            }
        }
        catch (EmptyListException ex) {
            removeFromList = null;
        }
        this.updateQueueState();
        return removeFromList;
    }
    
    public synchronized boolean isEmpty() {
        return super.isEmpty();
    }
    
    public synchronized boolean isFull() {
        return this.queueLimit != 0 && this.currentQueueDepth >= this.queueLimit;
    }
    
    public void print(final Logger logger, final int n, final int n2) {
        if (logger.ignore(n2)) {
            return;
        }
        super.print(logger, n, n2);
        logger.LogMsgln(n, n2, false, " maxQueueDepth=" + this.maxQueueDepth);
        logger.LogMsgln(n, n2, false, " enqueueWaits=" + this.enqueueWaits);
        logger.LogMsgln(n, n2, false, " currentQueueDepth=" + this.currentQueueDepth);
        logger.LogMsgln(n, n2, false, " queueLimit=" + this.queueLimit);
    }
    
    public void print(final IAppLogger appLogger, final int n, final int n2) {
        super.print(appLogger, n, n2);
        appLogger.logWithThisLevel(n, n2, " maxQueueDepth=" + this.maxQueueDepth);
        appLogger.logWithThisLevel(n, n2, " enqueueWaits=" + this.enqueueWaits);
        appLogger.logWithThisLevel(n, n2, " currentQueueDepth=" + this.currentQueueDepth);
        appLogger.logWithThisLevel(n, n2, " queueLimit=" + this.queueLimit);
    }
    
    public synchronized void setQueueLimit(final int queueLimit) {
        this.queueLimit = queueLimit;
    }
    
    public synchronized int getQueueLimit() {
        return this.queueLimit;
    }
    
    public synchronized int getQueueDepth() {
        return this.currentQueueDepth;
    }
    
    public synchronized int resetMaxQueueDepth() {
        final int maxQueueDepth = this.maxQueueDepth;
        this.maxQueueDepth = 0;
        return maxQueueDepth;
    }
    
    public synchronized int getMaxQueueDepth() {
        return this.maxQueueDepth;
    }
    
    public synchronized int resetEnqueueWaits() {
        final int enqueueWaits = this.enqueueWaits;
        this.enqueueWaits = 0;
        return enqueueWaits;
    }
    
    public synchronized int getEnqueueWaits() {
        return this.enqueueWaits;
    }
    
    public synchronized void close() {
        this.queueState = (byte)(this.isEmpty() ? 2 : 1);
    }
    
    private void enqueueObject(final Object obj, final boolean b) throws QueueException {
        final boolean empty = this.isEmpty();
        if (this.queueState != 0) {
            if (super.log != null) {
                super.log.LogMsgln(2, 5, false, "enqueueObject (" + obj + ") to (" + super.getListName() + ") failed : queue closed.");
            }
            if (super.applog != null && super.applog.ifLogBasic(2L, 1)) {
                super.applog.logBasic(1, "enqueueObject (" + obj + ") to (" + super.getListName() + ") failed : queue closed.");
            }
            throw new QueueClosedException("enqueue failed : " + obj);
        }
        if (this.isFull()) {
            ++this.enqueueWaits;
        }
        while (this.isFull()) {
            try {
                this.wait();
            }
            catch (InterruptedException ex) {
                if (super.log != null) {
                    super.log.LogMsgln(2, 5, false, "enqueue " + super.getListName() + " InterruptedExeception " + ex.getMessage());
                }
                if (super.applog == null || !super.applog.ifLogBasic(2L, 1)) {
                    continue;
                }
                super.applog.logBasic(1, "enqueue " + super.getListName() + " InterruptedExeception " + ex.getMessage());
            }
        }
        if (b) {
            this.insertAtFront(obj);
        }
        else {
            this.insertAtBack(obj);
        }
        ++this.currentQueueDepth;
        if (super.log != null && !super.log.ignore(5)) {
            super.log.LogMsgln(2, 5, false, "enqueue() currentQueueDepth=" + this.currentQueueDepth);
        }
        if (super.applog != null && super.applog.ifLogBasic(2L, 1)) {
            super.applog.logBasic(1, "enqueue() currentQueueDepth=" + this.currentQueueDepth);
        }
        if (this.currentQueueDepth > this.maxQueueDepth) {
            this.maxQueueDepth = this.currentQueueDepth;
        }
        if (empty) {
            this.notifyAll();
        }
    }
    
    private boolean yieldControl() {
        final boolean b = true;
        Thread.yield();
        return b;
    }
    
    private void updateQueueState() {
        if (this.queueState == 1 && this.isEmpty()) {
            this.queueState = 2;
        }
    }
    
    static {
        DESC_STATE = new String[] { " STATE_READY ", " STATE_CLOSING ", " STATE_CLOSED " };
    }
    
    public static class QueueException extends ProException
    {
        public QueueException(final String s) {
            super("Queue", new Object[] { s });
        }
        
        public String getDetail() {
            return (String)this.getArgument(0);
        }
    }
    
    public static class QueueClosedException extends QueueException
    {
        public QueueClosedException(final String str) {
            super("QueueClosed[" + str + "]");
        }
        
        public String getDetail() {
            return (String)this.getArgument(0);
        }
    }
}
