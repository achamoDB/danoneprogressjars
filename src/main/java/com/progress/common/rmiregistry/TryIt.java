// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.rmiregistry;

public class TryIt extends Thread
{
    SyncObj syncIt;
    Object retVal;
    int continuation;
    ICallable callback;
    Object data;
    Exception exception;
    static int runCount;
    
    public TryIt(final ICallable callback, final Object data) {
        this.syncIt = new SyncObj();
        this.retVal = null;
        this.continuation = 0;
        this.callback = null;
        this.data = null;
        this.exception = null;
        this.callback = callback;
        this.data = data;
        this.setName("Call with timeout (TryIt)");
    }
    
    public Object callIt(final int n) throws Exception {
        return call(this.callback, this.data, n, true, true, this);
    }
    
    public static Object call(final ICallable callable, final Object o) throws Exception {
        return call(callable, o, 5000);
    }
    
    public static Object call(final ICallable callable, final Object o, final int n) throws Exception {
        return call(callable, o, n, true, true, null);
    }
    
    public static Object call(final ICallable callable, final Object o, final int n, final boolean b, final boolean b2, TryIt tryIt) throws Exception {
        boolean b3 = false;
        if (tryIt == null) {
            tryIt = new TryIt(callable, o);
        }
        synchronized (tryIt.syncIt) {
            tryIt.start();
            tryIt.syncIt.wait(n);
            while (!tryIt.syncIt.killed()) {
                if (!tryIt.syncIt.completed()) {
                    if (tryIt.continuation != 0) {
                        final int continuation = tryIt.continuation;
                        tryIt.continuation = 0;
                        tryIt.syncIt.wait(continuation);
                        continue;
                    }
                    b3 = true;
                }
                final Object retVal = tryIt.retVal;
                if (tryIt.syncIt.exception != null) {
                    throw tryIt.syncIt.exception;
                }
                if (b3 && b && !tryIt.syncIt.completed()) {
                    throw new TimedOut();
                }
                if (b2 && !tryIt.syncIt.get()) {
                    throw new CallFailed();
                }
                if (tryIt.syncIt.get()) {
                    return retVal;
                }
                return null;
            }
            return null;
        }
    }
    
    public void destroyThread() {
        synchronized (this.syncIt) {
            this.syncIt.setKilled(true);
            this.syncIt.notifyAll();
        }
    }
    
    public void continuation(final int n) {
        this.continuation += n;
    }
    
    public void run() {
        ++TryIt.runCount;
        synchronized (this.syncIt) {
            this.syncIt.set(false);
            this.syncIt.setCompleted(false);
        }
        boolean b = true;
        try {
            this.retVal = this.callback.call(this.data);
        }
        catch (Exception exception) {
            b = false;
            this.exception = exception;
        }
        synchronized (this.syncIt) {
            if (this.syncIt.killed()) {
                this.syncIt.setKilled(false);
            }
            this.syncIt.set(b);
            this.syncIt.setCompleted(true);
            this.syncIt.setException(this.exception);
            this.syncIt.notifyAll();
        }
        --TryIt.runCount;
    }
    
    static {
        TryIt.runCount = 0;
    }
}
