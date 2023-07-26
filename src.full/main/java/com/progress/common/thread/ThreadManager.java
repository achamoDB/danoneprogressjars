// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.thread;

import java.lang.reflect.Constructor;
import com.progress.common.exception.ProException;

public class ThreadManager
{
    protected static final int ARRAY_ALLOCATION_INCREMENT = 5;
    public static final int THREAD_STATE_MODIFIER_BITS = -65536;
    public static final int THREAD_STATE_SUBCLASS_BITS = 65535;
    public static final int THREAD_STATE_ALL_BITS = -1;
    public static final int THREAD_STATE_IDLE = 1073741824;
    static int m_nextThreadId;
    static int m_nextPoolId;
    ThreadPool[] m_threadPools;
    int m_allocatedPools;
    int m_numberOfPools;
    IExceptionHandler m_exceptionHandler;
    
    public ThreadManager() {
        this.m_threadPools = null;
        this.m_allocatedPools = 0;
        this.m_numberOfPools = 0;
        this.m_exceptionHandler = null;
    }
    
    public ThreadManager(final IExceptionHandler exceptionHandler) {
        this.m_threadPools = null;
        this.m_allocatedPools = 0;
        this.m_numberOfPools = 0;
        this.m_exceptionHandler = null;
        this.m_exceptionHandler = exceptionHandler;
    }
    
    public synchronized ThreadPool createThreadPool(final Class clazz, final Object[] array, final ThreadPoolAttributes threadPoolAttributes, final String s) throws InvalidThreadClassException, ThreadInstantiationException {
        if (this.m_numberOfPools >= this.m_allocatedPools) {
            this.m_allocatedPools += 5;
            final ThreadPool[] threadPools = new ThreadPool[this.m_allocatedPools];
            if (this.m_threadPools != null) {
                for (int i = 0; i < this.m_threadPools.length; ++i) {
                    threadPools[i] = this.m_threadPools[i];
                }
            }
            this.m_threadPools = threadPools;
        }
        final ThreadPool threadPool = new ThreadPool(this, clazz, array, threadPoolAttributes, s);
        this.m_threadPools[this.m_numberOfPools] = threadPool;
        ++this.m_numberOfPools;
        return threadPool;
    }
    
    protected static synchronized int nextPoolId() {
        return ++ThreadManager.m_nextPoolId;
    }
    
    public synchronized void removeThreadPool(final ThreadPool threadPool) throws InvalidThreadPoolException {
        int i;
        for (i = 0; i < this.m_numberOfPools && this.m_threadPools[i] != threadPool; ++i) {}
        if (i >= this.m_numberOfPools) {
            throw new InvalidThreadPoolException();
        }
        while (i < this.m_numberOfPools - 1) {
            this.m_threadPools[i] = this.m_threadPools[i + 1];
            ++i;
        }
        --this.m_numberOfPools;
        this.m_threadPools[this.m_numberOfPools] = null;
    }
    
    public synchronized void setUncaughtHandler(final IExceptionHandler exceptionHandler) {
        this.m_exceptionHandler = exceptionHandler;
    }
    
    public synchronized void uncaughtException(final Thread thread, final Throwable t) {
        if (this.m_exceptionHandler != null) {
            this.m_exceptionHandler.uncaughtException(thread, t);
        }
    }
    
    static {
        ThreadManager.m_nextThreadId = 0;
        ThreadManager.m_nextPoolId = 0;
    }
    
    public static class InvalidThreadClassException extends ProException
    {
        public InvalidThreadClassException(final String s) {
            super("Invalid thread class \"%s<class name>\".", new Object[] { s });
        }
        
        public String getClassName() {
            return (String)this.getArgument(0);
        }
    }
    
    public static class InvalidThreadPoolException extends ProException
    {
        public InvalidThreadPoolException() {
            super("Invalid thread pool.", (Object[])null);
        }
    }
    
    public static class ThreadInstantiationException extends ProException
    {
        public ThreadInstantiationException(final Exception ex) {
            super("Cannot instantiate thread, %s<exception text>.", new Object[] { ex.toString() });
        }
        
        public String getExceptionMessage() {
            return (String)this.getArgument(0);
        }
    }
    
    public static class ThreadPoolAttributes implements Cloneable
    {
        public int m_growIncrement;
        public int m_initialThreads;
        public int m_maxIdleThreads;
        public int m_maxThreads;
        public int m_minThreads;
        public boolean m_daemonThreads;
        
        public ThreadPoolAttributes() {
            this.m_initialThreads = 1;
            this.m_minThreads = 1;
            this.m_growIncrement = 1;
            this.m_maxThreads = Integer.MAX_VALUE;
            this.m_maxIdleThreads = Integer.MAX_VALUE;
            this.m_daemonThreads = false;
        }
        
        public ThreadPoolAttributes(final int initialThreads, final int minThreads, final int n, final int maxThreads, final int maxIdleThreads, final boolean daemonThreads) {
            this.m_initialThreads = initialThreads;
            this.m_minThreads = minThreads;
            this.m_growIncrement = this.m_growIncrement;
            this.m_maxThreads = maxThreads;
            this.m_maxIdleThreads = maxIdleThreads;
            this.m_daemonThreads = daemonThreads;
        }
        
        public Object clone() {
            Object clone = null;
            try {
                clone = super.clone();
            }
            catch (CloneNotSupportedException ex) {}
            return clone;
        }
    }
    
    public static class ThreadPool extends ThreadGroup
    {
        ThreadManager m_threadManager;
        int m_poolId;
        Class m_threadClass;
        Object[] m_initializers;
        ThreadPoolAttributes m_attributes;
        PoolThread[] m_threads;
        int m_allocatedThreads;
        int m_numberOfThreads;
        int m_idleCount;
        
        public ThreadPool(final ThreadManager threadManager, final Class threadClass, final Object[] initializers, final ThreadPoolAttributes attributes, final String name) throws InvalidThreadClassException, ThreadInstantiationException {
            super(name);
            this.m_poolId = ThreadManager.nextPoolId();
            this.m_threads = null;
            this.m_allocatedThreads = 0;
            this.m_numberOfThreads = 0;
            this.m_idleCount = 0;
            for (Class clazz = threadClass.getSuperclass(); clazz != PoolThread.class; clazz = clazz.getSuperclass()) {
                if (clazz == null) {
                    throw new InvalidThreadClassException(threadClass.getName());
                }
            }
            this.m_threadManager = threadManager;
            this.m_threadClass = threadClass;
            this.m_initializers = initializers;
            this.setAttributes(attributes);
        }
        
        private void addThreads(int n) throws ThreadInstantiationException {
            Constructor<PoolThread> constructor = null;
            if (this.m_numberOfThreads + n > this.m_allocatedThreads) {
                if (n < 5) {
                    this.m_allocatedThreads += 5;
                }
                else {
                    this.m_allocatedThreads += n;
                }
                final PoolThread[] threads = new PoolThread[this.m_allocatedThreads];
                if (this.m_threads != null) {
                    for (int i = 0; i < this.m_threads.length; ++i) {
                        threads[i] = this.m_threads[i];
                    }
                }
                this.m_threads = threads;
            }
            if (this.m_initializers != null && this.m_initializers.length > 0) {
                final Class[] parameterTypes = new Class[this.m_initializers.length];
                for (int j = 0; j < this.m_initializers.length; ++j) {
                    parameterTypes[j] = this.m_initializers[j].getClass();
                }
                try {
                    constructor = (Constructor<PoolThread>)this.m_threadClass.getConstructor((Class[])parameterTypes);
                }
                catch (NoSuchMethodException ex) {
                    throw new ThreadInstantiationException(ex);
                }
            }
            while (n-- > 0) {
                PoolThread poolThread;
                try {
                    if (this.m_initializers != null) {
                        poolThread = constructor.newInstance(this.m_initializers);
                    }
                    else {
                        poolThread = this.m_threadClass.newInstance();
                    }
                }
                catch (Exception ex2) {
                    throw new ThreadInstantiationException(ex2);
                }
                this.m_threads[this.m_numberOfThreads] = poolThread;
                ++this.m_numberOfThreads;
                ++this.m_idleCount;
                poolThread.setPool(this);
                poolThread.setDaemon(this.m_attributes.m_daemonThreads);
                poolThread.start();
            }
        }
        
        private int threadIndex(final PoolThread poolThread) {
            int n;
            for (n = this.m_numberOfThreads - 1; n >= 0 && this.m_threads[n] != poolThread; --n) {}
            return n;
        }
        
        public synchronized int getNumberOfBusyThreads() {
            return this.m_numberOfThreads - this.m_idleCount;
        }
        
        public synchronized int getNumberOfIdleThreads() {
            return this.m_idleCount;
        }
        
        public synchronized int getNumberOfThreads() {
            return this.m_numberOfThreads;
        }
        
        public synchronized int getNumberOfThreadsInState(final int n, final int n2) {
            int n3 = 0;
            for (int i = 0; i < this.m_numberOfThreads; ++i) {
                if ((this.m_threads[i].getThreadState() & n) == n2) {
                    ++n3;
                }
            }
            return n3;
        }
        
        public final int getPoolId() {
            return this.m_poolId;
        }
        
        protected static synchronized int nextThreadId() {
            return ++ThreadManager.m_nextThreadId;
        }
        
        private void removeThread(final int n) {
            if (n < 0 || n > this.m_numberOfThreads) {
                return;
            }
            final PoolThread poolThread = this.m_threads[n];
            for (int i = n; i < this.m_numberOfThreads - 1; ++i) {
                this.m_threads[i] = this.m_threads[i + 1];
            }
            --this.m_numberOfThreads;
            this.m_threads[this.m_numberOfThreads] = null;
            poolThread.setPool(null);
            if ((poolThread.getThreadState() & 0x40000000) != 0x0) {
                --this.m_idleCount;
            }
            poolThread.signalStop();
        }
        
        private void removeIdleThreads(int n) {
            int n2 = this.m_numberOfThreads - 1;
            while (n-- > 0) {
                while (n2 >= 0 && (this.m_threads[n2].getThreadState() & 0x40000000) == 0x0) {
                    --n2;
                }
                if (n2 < 0) {
                    this.m_idleCount = 0;
                    break;
                }
                this.removeThread(n2);
            }
        }
        
        public synchronized void setAttributes(final ThreadPoolAttributes threadPoolAttributes) throws ThreadInstantiationException {
            if (threadPoolAttributes.m_initialThreads < 1) {
                throw new IllegalArgumentException("Initial threads must be greater than zero");
            }
            if (threadPoolAttributes.m_minThreads < 1) {
                throw new IllegalArgumentException("Minimum threads must be greater than zero");
            }
            if (threadPoolAttributes.m_maxThreads < threadPoolAttributes.m_minThreads) {
                throw new IllegalArgumentException("Maximum threads must be greater than minimum");
            }
            if (threadPoolAttributes.m_maxIdleThreads < 1) {
                throw new IllegalArgumentException("Maximum idle threads must be greater than zero");
            }
            if (threadPoolAttributes.m_growIncrement > threadPoolAttributes.m_maxIdleThreads) {
                throw new IllegalArgumentException("Grow increment must be less than or equal to maximum idle");
            }
            if (threadPoolAttributes.m_maxThreads < this.m_numberOfThreads - this.m_idleCount) {
                throw new IllegalArgumentException("Maximum threads must be greater than or equal to current number of busy threads");
            }
            this.m_attributes = (ThreadPoolAttributes)threadPoolAttributes.clone();
            final int n = this.m_attributes.m_initialThreads - this.m_numberOfThreads;
            if (n > 0) {
                this.addThreads(n);
            }
            else {
                final int n2 = this.m_attributes.m_maxIdleThreads - this.m_idleCount;
                if (n2 > 0) {
                    this.removeIdleThreads(n2);
                }
            }
        }
        
        protected synchronized void threadStateChanged(final PoolThread poolThread) throws ThreadInstantiationException {
            if ((poolThread.getThreadState() & 0x40000000) != 0x0) {
                ++this.m_idleCount;
                if (this.m_idleCount > this.m_attributes.m_maxIdleThreads && this.m_numberOfThreads > this.m_attributes.m_minThreads) {
                    this.removeThread(this.threadIndex(poolThread));
                }
            }
            else {
                --this.m_idleCount;
                if (this.m_idleCount == 0 && this.m_numberOfThreads < this.m_attributes.m_maxThreads) {
                    this.addThreads(Math.min(Math.min(this.m_attributes.m_growIncrement, this.m_attributes.m_maxThreads - this.m_numberOfThreads), this.m_attributes.m_maxIdleThreads));
                }
            }
        }
        
        public void uncaughtException(final Thread thread, final Throwable t) {
            this.m_threadManager.uncaughtException(thread, t);
            synchronized (this) {
                this.removeThread(this.threadIndex((PoolThread)thread));
            }
        }
    }
    
    public static class PoolThread extends Thread
    {
        int m_threadId;
        int m_state;
        ThreadPool m_pool;
        boolean m_stopSignaled;
        
        public PoolThread() {
            this.m_threadId = 0;
            this.m_state = 1073741824;
            this.m_pool = null;
            this.m_stopSignaled = false;
        }
        
        public final String getFullName() {
            String s;
            if (this.m_pool == null) {
                s = super.getName();
            }
            else {
                s = this.m_pool.getName() + "/" + super.getName();
            }
            return s;
        }
        
        public final long getThreadId() {
            long n = this.m_threadId;
            if (this.m_pool != null) {
                n |= (long)this.m_pool.getPoolId() << 32;
            }
            return n;
        }
        
        public final int getThreadState() {
            return this.m_state;
        }
        
        public final void setPool(final ThreadPool pool) {
            this.m_pool = pool;
            if (pool != null) {
                this.m_threadId = ThreadPool.nextThreadId();
            }
        }
        
        public final void setState(final int state) throws ThreadInstantiationException {
            final boolean b = ((this.m_state ^ state) & 0x40000000) != 0x0;
            this.m_state = state;
            if (b && this.m_pool != null) {
                this.m_pool.threadStateChanged(this);
            }
        }
        
        void signalStop() {
            this.m_stopSignaled = true;
            this.interrupt();
        }
        
        public final boolean stopSignaled() {
            return this.m_stopSignaled;
        }
    }
    
    public interface IExceptionHandler
    {
        void uncaughtException(final Thread p0, final Throwable p1);
    }
}
