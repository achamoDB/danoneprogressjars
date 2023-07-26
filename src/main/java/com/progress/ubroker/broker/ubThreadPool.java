// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.broker;

import com.progress.common.exception.ProException;
import com.progress.ubroker.util.Queue;
import com.progress.ubroker.util.Request;
import com.progress.ubroker.util.ubAdminMsg;
import com.progress.ubroker.util.ubMsg;
import com.progress.ubroker.util.RequestQueue;
import com.progress.ubroker.util.ubThreadStats;
import com.progress.ubroker.util.ubThread;
import com.progress.ubroker.util.ubProperties;
import com.progress.common.ehnlog.IAppLogger;
import java.util.Hashtable;
import com.progress.ubroker.util.List;

public abstract class ubThreadPool
{
    public static final long WAIT_FOREVER = -1L;
    public static final long CHECK_NO_WAIT = 0L;
    private int nThreads;
    private List pool;
    private List idleThreads;
    private List readyThreads;
    private List waitingForIdleThread;
    private List waitingForReadyThread;
    private int maxThreads;
    private int minThreads;
    private int minIdleThreads;
    private int maxIdleThreads;
    private int maxBusyThreads;
    private Hashtable clientAffinity;
    private Hashtable serverAffinity;
    private int maxWaitIdleQueueDepth;
    private int maxWaitReadyQueueDepth;
    private int maxActiveThreads;
    private int numRqsOfRmvdThrds;
    private int numRqMsgsOfRmvdThrds;
    private int numRspMsgsOfRmvdThrds;
    private int maxRqWaitOfRmvdThrds;
    private long totRqWaitOfRmvdThrds;
    private int maxRqDurationOfRmvdThrds;
    private long totRqDurationOfRmvdThrds;
    IAppLogger log;
    private ubProperties properties;
    private int reqCompleted;
    private int reqQueued;
    private int reqRejected;
    
    public ubThreadPool(final String s, final int minThreads, final int maxThreads, final int minIdleThreads, final int maxIdleThreads, final IAppLogger log, final ubProperties properties) {
        this.reqCompleted = 0;
        this.reqQueued = 0;
        this.reqRejected = 0;
        this.pool = new List(s, log);
        this.idleThreads = new List(s + "-idle", log);
        this.readyThreads = new List(s + "-ready", log);
        this.minThreads = minThreads;
        this.maxThreads = maxThreads;
        this.minIdleThreads = minIdleThreads;
        this.maxIdleThreads = maxIdleThreads;
        this.log = log;
        this.nThreads = 0;
        this.maxActiveThreads = 0;
        this.waitingForIdleThread = new List(s + "-waitForIdle", log);
        this.waitingForReadyThread = new List(s + "-waitForReady", log);
        this.maxBusyThreads = 0;
        this.maxWaitIdleQueueDepth = 0;
        this.maxWaitReadyQueueDepth = 0;
        this.numRqsOfRmvdThrds = 0;
        this.numRqMsgsOfRmvdThrds = 0;
        this.numRspMsgsOfRmvdThrds = 0;
        this.maxRqWaitOfRmvdThrds = 0;
        this.totRqWaitOfRmvdThrds = 0L;
        this.maxRqDurationOfRmvdThrds = 0;
        this.totRqDurationOfRmvdThrds = 0L;
        this.properties = properties;
        this.clientAffinity = new Hashtable();
        this.serverAffinity = new Hashtable();
    }
    
    public void removeAffinity(final String s) {
        if (s != null) {
            final ubThread ubThread = this.clientAffinity.get(s);
            if (ubThread != null && s.equals(this.serverAffinity.get(ubThread))) {
                this.serverAffinity.remove(ubThread);
            }
            this.clientAffinity.remove(s);
        }
    }
    
    public void removeAffinity(final ubThread ubThread) {
        if (ubThread != null) {
            final ubThread ubThread2 = this.serverAffinity.get(ubThread);
            if (ubThread2 != null && this.clientAffinity.get(ubThread2) == ubThread) {
                this.clientAffinity.remove(ubThread2);
            }
            this.serverAffinity.remove(ubThread);
        }
    }
    
    public synchronized void addThread(final ubThread obj) {
        this.pool.insertAtBack(obj);
        ++this.nThreads;
        if (this.nThreads > this.maxActiveThreads) {
            this.maxActiveThreads = this.nThreads;
        }
        if (this.log.ifLogBasic(1024L, 10)) {
            this.log.logBasic(10, "(" + this.pool.getListName() + ") addThread " + obj);
            this.pool.print(this.log, 2, 10);
        }
    }
    
    public synchronized ubThread removeThread(final ubThread obj) throws EmptyPoolException {
        if (this.log.ifLogBasic(1024L, 10)) {
            this.log.logBasic(10, "(" + this.pool.getListName() + ") removeThread " + obj);
            this.pool.print(this.log, 2, 10);
        }
        ubThread ubThread;
        try {
            ubThread = (ubThread)this.pool.removeFromList(obj);
        }
        catch (List.EmptyListException ex) {
            throw new EmptyPoolException(this.pool.getListName());
        }
        final ubThreadStats stats = obj.getStats();
        this.numRqsOfRmvdThrds += stats.getnRqs();
        this.numRqMsgsOfRmvdThrds += stats.getnRqMsgs();
        this.numRspMsgsOfRmvdThrds += stats.getnRspMsgs();
        final int maxRqWait = stats.getMaxRqWait();
        if (maxRqWait > this.maxRqWaitOfRmvdThrds) {
            this.maxRqWaitOfRmvdThrds = maxRqWait;
        }
        this.totRqWaitOfRmvdThrds += stats.getTotRqWait();
        final int maxRqDuration = stats.getMaxRqDuration();
        if (maxRqDuration > this.maxRqDurationOfRmvdThrds) {
            this.maxRqDurationOfRmvdThrds = maxRqDuration;
        }
        this.totRqDurationOfRmvdThrds += stats.getTotRqDuration();
        this.removeFromList(obj, this.idleThreads);
        this.removeFromList(obj, this.readyThreads);
        --this.nThreads;
        this.removeAffinity(obj);
        return ubThread;
    }
    
    public synchronized void print(final IAppLogger appLogger, final int n, final int n2) {
        if (this.pool.isEmpty()) {
            appLogger.logWithThisLevel(n, n2, "pool " + this.pool.getListName() + " is empty.");
            return;
        }
        appLogger.logWithThisLevel(n, n2, "poolName = " + this.pool.getListName());
        for (ubThread obj = (ubThread)this.pool.findFirst(); obj != null; obj = (ubThread)this.pool.findNext(obj)) {
            appLogger.logWithThisLevel(n, n2, " ubThread = " + obj);
        }
    }
    
    public synchronized ubThread[] enumerateThreads() {
        final ubThread[] array = new ubThread[this.nThreads];
        int n = 0;
        for (ubThread ubThread = (ubThread)this.pool.findFirst(); ubThread != null; ubThread = (ubThread)this.pool.findNext(ubThread)) {
            array[n++] = ubThread;
        }
        return array;
    }
    
    public synchronized int size() {
        return this.nThreads;
    }
    
    public synchronized ubThreadPoolStats getSummaryStats() {
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        int numRqsOfRmvdThrds = this.numRqsOfRmvdThrds;
        int numRqMsgsOfRmvdThrds = this.numRqMsgsOfRmvdThrds;
        int numRspMsgsOfRmvdThrds = this.numRspMsgsOfRmvdThrds;
        int maxRqWaitOfRmvdThrds = this.maxRqWaitOfRmvdThrds;
        long totRqWaitOfRmvdThrds = this.totRqWaitOfRmvdThrds;
        int maxRqDurationOfRmvdThrds = this.maxRqDurationOfRmvdThrds;
        long totRqDurationOfRmvdThrds = this.totRqDurationOfRmvdThrds;
        final int nThreads = this.nThreads;
        for (ubThread ubThread = (ubThread)this.pool.findFirst(); ubThread != null; ubThread = (ubThread)this.pool.findNext(ubThread)) {
            switch (ubThread.getPoolState()) {
                case 0: {
                    ++n;
                    break;
                }
                case 1: {
                    ++n2;
                    break;
                }
                case 3: {
                    ++n3;
                    break;
                }
                case 2: {
                    ++n4;
                    break;
                }
            }
            final ubThreadStats stats = ubThread.getStats();
            numRqsOfRmvdThrds += stats.getnRqs();
            numRqMsgsOfRmvdThrds += stats.getnRqMsgs();
            numRspMsgsOfRmvdThrds += stats.getnRspMsgs();
            final int maxRqWait = stats.getMaxRqWait();
            if (maxRqWait > maxRqWaitOfRmvdThrds) {
                maxRqWaitOfRmvdThrds = maxRqWait;
            }
            totRqWaitOfRmvdThrds += stats.getTotRqWait();
            final int maxRqDuration = stats.getMaxRqDuration();
            if (maxRqDuration > maxRqDurationOfRmvdThrds) {
                maxRqDurationOfRmvdThrds = maxRqDuration;
            }
            totRqDurationOfRmvdThrds += stats.getTotRqDuration();
        }
        final int i = (numRqsOfRmvdThrds > 0) ? ((int)(totRqWaitOfRmvdThrds / (float)numRqsOfRmvdThrds)) : 0;
        final int j = (numRqsOfRmvdThrds > 0) ? ((int)(totRqDurationOfRmvdThrds / (float)numRqsOfRmvdThrds)) : 0;
        if (this.log.ifLogBasic(2048L, 11)) {
            this.log.logBasic(11, "(" + this.pool.getListName() + ") getSummaryStats :" + " numRqs= 0" + numRqsOfRmvdThrds + " maxRqWait= 0" + maxRqWaitOfRmvdThrds + " totRqWait= 0" + totRqWaitOfRmvdThrds + " avgRqWait= 0" + i + " totRqDuration= 0" + totRqDurationOfRmvdThrds + " avgRqDuration= 0" + j);
        }
        return new ubThreadPoolStats(nThreads, this.maxActiveThreads, n, n2, n3, n4, numRqsOfRmvdThrds, numRqMsgsOfRmvdThrds, numRspMsgsOfRmvdThrds, maxRqWaitOfRmvdThrds, i, maxRqDurationOfRmvdThrds, j, this.waitingForReadyThread.size(), this.maxWaitReadyQueueDepth);
    }
    
    public synchronized void incrRequestsCompleted(final int n) {
        this.reqCompleted += n;
    }
    
    public synchronized int getRequestsCompleted() {
        return this.reqCompleted;
    }
    
    public synchronized void incrRequestsQueued(final int n) {
        this.reqQueued += n;
    }
    
    public synchronized int getRequestsQueued() {
        return this.reqQueued;
    }
    
    public synchronized void incrRequestsRejected(final int n) {
        this.reqRejected += n;
    }
    
    public synchronized int getRequestsRejected() {
        return this.reqRejected;
    }
    
    public synchronized int getMaxBusyThreads() {
        return this.maxBusyThreads;
    }
    
    public synchronized int resetMaxBusyThreads() {
        final int maxBusyThreads = this.maxBusyThreads;
        this.maxBusyThreads = 0;
        return maxBusyThreads;
    }
    
    public ubThread dequeueThreadInState(final int n, final RequestQueue requestQueue, final long n2) {
        return this.dequeueThreadInState(n, requestQueue, n2, (String)null);
    }
    
    public ubThread dequeueThreadInState(final int n, final RequestQueue requestQueue, final long n2, final String value) {
        ubThread ubThread = null;
        if (this.log.ifLogBasic(1024L, 10)) {
            this.log.logBasic(10, "(" + this.pool.getListName() + ") dequeueThreadInState " + "targetState= 0x" + Integer.toString(n, 16) + " (" + com.progress.ubroker.util.ubThread.DESC_POOL_STATE[n] + ")");
        }
        synchronized (this) {
            switch (n) {
                case 0: {
                    ubThread = this.dequeuePoolThreadfromList(this.idleThreads, this.waitingForIdleThread, requestQueue, value);
                    final int size = this.waitingForIdleThread.size();
                    if (size > this.maxWaitIdleQueueDepth) {
                        this.maxWaitIdleQueueDepth = size;
                        break;
                    }
                    break;
                }
                case 1: {
                    ubThread = this.dequeuePoolThreadfromList(this.readyThreads, this.waitingForReadyThread, requestQueue, value);
                    final int size2 = this.waitingForReadyThread.size();
                    if (size2 > this.maxWaitReadyQueueDepth) {
                        this.maxWaitReadyQueueDepth = size2;
                        break;
                    }
                    break;
                }
                default: {
                    this.log.logError("(" + this.pool.getListName() + ") dequeueThreadInState : invalid targetState: " + n);
                    ubThread = null;
                    break;
                }
            }
        }
        if (ubThread == null && requestQueue != null) {
            ubThread = this.waitForPoolThread(n, requestQueue, n2);
            if (value != null && ubThread != null) {
                synchronized (this) {
                    final ubThread ubThread2 = this.clientAffinity.get(value);
                    if (ubThread2 != null && ubThread != ubThread2 && value.equals(this.serverAffinity.get(ubThread2))) {
                        this.serverAffinity.remove(ubThread2);
                    }
                    this.clientAffinity.put(value, ubThread);
                    this.serverAffinity.put(ubThread, value);
                }
            }
        }
        if (this.log.ifLogBasic(2L, 1)) {
            this.log.logBasic(1, "(" + this.pool.getListName() + ") dequeueThreadInState " + ubThread);
        }
        if (this.log.ifLogBasic(1024L, 10)) {
            this.log.logBasic(10, "(" + this.pool.getListName() + ") dequeueThreadInState " + ubThread);
        }
        return ubThread;
    }
    
    public ubThread dequeueThreadInState(final int n, final RequestQueue requestQueue, final long n2, final Object[] array) {
        return this.dequeueThreadInState(n, requestQueue, n2, array, null);
    }
    
    public ubThread dequeueThreadInState(final int n, final RequestQueue requestQueue, final long n2, final Object[] array, final String s) {
        ubThread ubThread = this.dequeueThreadInState(n, requestQueue, n2, s);
        if (ubThread == null) {
            synchronized (this) {
                ubThread = this.startThread(2);
            }
            if (ubThread != null && !this.activateThread(ubThread, true, array, requestQueue)) {
                ubThread = null;
            }
        }
        return ubThread;
    }
    
    public boolean start_Thread(final int n, final Object[] array, final RequestQueue requestQueue) {
        ubThread startThread = null;
        synchronized (this) {
            startThread = this.startThread(n);
        }
        if (startThread != null && !this.activateThread(startThread, true, array, requestQueue)) {
            startThread = null;
        }
        return startThread != null;
    }
    
    public boolean start_minThreads(final int n, final Object[] array, final RequestQueue requestQueue) {
        boolean b = true;
        final int n2;
        final ubThread[] array2;
        synchronized (this) {
            n2 = this.minThreads - this.nThreads;
            if (n2 <= 0) {
                return false;
            }
            array2 = new ubThread[n2];
            for (int i = 0; i < n2; ++i) {
                array2[i] = this.startThread(n);
            }
        }
        for (int j = 0; j < n2; ++j) {
            if (array2[j] != null && !this.activateThread(array2[j], true, array, requestQueue)) {
                array2[j] = null;
                b = false;
            }
        }
        return b;
    }
    
    public int setPoolState(final ubThread obj, int updateThreadState) {
        final ubThread ubThread = (ubThread)Thread.currentThread();
        RequestQueue requestQueue = null;
        synchronized (this) {
            if (this.log.ifLogBasic(1024L, 10)) {
                this.log.logBasic(10, "(" + this.pool.getListName() + ") setPoolState " + obj + " newState= 0x" + Integer.toString(updateThreadState, 16) + " (" + com.progress.ubroker.util.ubThread.DESC_POOL_STATE[updateThreadState] + ")");
            }
            updateThreadState = this.updateThreadState(obj, updateThreadState);
            switch (updateThreadState) {
                case 0: {
                    requestQueue = this.dequeueWaiterFromList(this.idleThreads, this.waitingForIdleThread);
                    break;
                }
                case 1: {
                    requestQueue = this.dequeueWaiterFromList(this.readyThreads, this.waitingForReadyThread);
                    break;
                }
                default: {
                    requestQueue = null;
                    break;
                }
            }
        }
        if (requestQueue != null) {
            this.wakeWaiter(requestQueue);
        }
        return updateThreadState;
    }
    
    public synchronized int numThreadsInState(final int n) {
        if (this.pool.isEmpty()) {
            return 0;
        }
        int n2 = 0;
        for (ubThread ubThread = (ubThread)this.pool.findFirst(); ubThread != null; ubThread = (ubThread)this.pool.findNext(ubThread)) {
            if (ubThread.getPoolState() == n) {
                ++n2;
            }
        }
        return n2;
    }
    
    public int getMaxThreads() {
        return this.maxThreads;
    }
    
    public int getMinThreads() {
        return this.minThreads;
    }
    
    public int getMinIdleThreads() {
        return this.minIdleThreads;
    }
    
    public int getMaxIdleThreads() {
        return this.maxIdleThreads;
    }
    
    public synchronized int numThreadsNeeded(final int n) {
        int i = 0;
        if (this.nThreads >= this.maxThreads) {
            return i;
        }
        final int numThreadsInState = this.numThreadsInState(n);
        if (numThreadsInState > this.maxIdleThreads) {
            return i;
        }
        if (this.nThreads < this.minThreads) {
            i += this.minThreads - this.nThreads;
        }
        if (numThreadsInState + i < this.minIdleThreads) {
            i += this.minIdleThreads - (numThreadsInState + i);
        }
        if (this.log.ifLogBasic(1024L, 10)) {
            this.log.logBasic(10, "(" + this.pool.getListName() + ") numThreadsNeeded : " + "ret= " + i);
        }
        return i;
    }
    
    public synchronized int numThreadsAllowed() {
        return (this.nThreads >= this.maxThreads) ? 0 : (this.maxThreads - this.nThreads);
    }
    
    public ubThread waitForPoolThread(final int i, final RequestQueue obj, final long lng) {
        Object obj2 = null;
        boolean b = false;
        do {
            if (this.log.ifLogBasic(1024L, 10)) {
                this.log.logBasic(10, "(" + this.pool.getListName() + ") waitForPoolThread :" + " targetState= " + i + " (" + ubThread.DESC_POOL_STATE[i] + ")" + " rspq= " + obj + " timeout= " + lng);
            }
            Request dequeueRequest;
            if (lng == -1L) {
                dequeueRequest = obj.dequeueRequest();
            }
            else {
                dequeueRequest = ((lng > 0L) ? obj.pollRequest(lng) : null);
            }
            if (this.log.ifLogBasic(1024L, 10)) {
                this.log.logBasic(10, "(" + this.pool.getListName() + ") waitForPoolThread :" + " rq= " + dequeueRequest);
            }
            if (dequeueRequest != null) {
                final ubMsg obj3 = (ubMsg)dequeueRequest.getMsg();
                if (this.log.ifLogBasic(1024L, 10)) {
                    obj3.print("waitForPoolThread() dequeued request on " + obj.getListName() + " : ", 2, 10, this.log);
                }
                if (obj3 instanceof ubAdminMsg) {
                    final ubAdminMsg ubAdminMsg = (ubAdminMsg)obj3;
                    ubAdminMsg.getadRq();
                    final Object[] getadParm = ubAdminMsg.getadParm();
                    if (ubAdminMsg.getadRq() == 7) {
                        if (getadParm == null || getadParm.length == 0 || getadParm[0] == null || !(getadParm[0] instanceof ubThread)) {
                            this.log.logError("Error processing ADRQ_THREAD_WAKEUP msg : poolThread id missing : admparm[0]= " + getadParm[0]);
                        }
                        else {
                            obj2 = getadParm[0];
                            if (this.log.ifLogBasic(1024L, 10)) {
                                this.log.logBasic(10, "processed ADRQ_THREAD_WAKEUP msg : got= " + obj2);
                            }
                        }
                    }
                    else {
                        this.log.logError("Error processing ADRQ_THREAD_WAKEUP msg : got wrong rq type : " + ubAdminMsg.getadRq());
                    }
                }
                else {
                    this.log.logError("Error processing ADRQ_THREAD_WAKEUP msg : got wrong msg type : " + obj3);
                }
            }
            b = (obj2 == null && !b && !this.cancelWait(i, obj));
        } while (b);
        return (ubThread)obj2;
    }
    
    public synchronized boolean cancelWait(final int n, final RequestQueue obj) {
        if (this.log.ifLogBasic(1024L, 10)) {
            this.log.logBasic(10, "(" + this.pool.getListName() + ") cancelWait :" + " targetState= " + n + " (" + ubThread.DESC_POOL_STATE[n] + ")" + " rspq= " + obj);
        }
        RequestQueue requestQueue = null;
        switch (n) {
            case 0: {
                requestQueue = (RequestQueue)this.removeFromList(obj, this.waitingForIdleThread);
                break;
            }
            case 1: {
                requestQueue = (RequestQueue)this.removeFromList(obj, this.waitingForReadyThread);
                break;
            }
            default: {
                this.log.logError("(" + this.pool.getListName() + ") cancelWait() : invalid targetState: " + n);
                requestQueue = null;
                break;
            }
        }
        return requestQueue == obj;
    }
    
    public abstract ubThread createThread(final int p0);
    
    public synchronized int updateThreadState(final ubThread obj, final int n) {
        final int poolState = obj.getPoolState();
        if (this.log.ifLogBasic(1024L, 10)) {
            this.log.logBasic(10, "(" + this.pool.getListName() + ") updateThreadState " + obj + " curState= 0x" + Integer.toString(poolState, 16) + " (" + ubThread.DESC_POOL_STATE[poolState] + ")" + " newState= 0x" + Integer.toString(n, 16) + " (" + ubThread.DESC_POOL_STATE[n] + ")");
        }
        switch (poolState) {
            case 0: {
                this.removeFromList(obj, this.idleThreads);
                break;
            }
            case 1: {
                this.removeFromList(obj, this.readyThreads);
                break;
            }
        }
        switch (n) {
            case 0: {
                this.idleThreads.insertAtBack(obj);
                if (this.log.ifLogBasic(1024L, 10)) {
                    this.log.logBasic(10, "(" + this.pool.getListName() + ") updateThreadState : " + "append " + obj + " to " + this.idleThreads.getListName());
                    this.idleThreads.print(this.log, 2, 10);
                    break;
                }
                break;
            }
            case 1: {
                if (this.properties.getValueAsInt("srvrSelectionScheme") == 1) {
                    this.readyThreads.insertAtFront(obj);
                }
                else {
                    this.readyThreads.insertAtBack(obj);
                }
                if (this.log.ifLogBasic(1024L, 10)) {
                    this.log.logBasic(10, "(" + this.pool.getListName() + ") updateThreadState " + "append " + obj + " to " + this.idleThreads.getListName());
                    this.readyThreads.print(this.log, 2, 10);
                    break;
                }
                break;
            }
        }
        obj.setPoolState(n);
        final int maxBusyThreads = this.size() - (this.idleThreads.size() + this.readyThreads.size());
        if (maxBusyThreads > this.maxBusyThreads) {
            this.maxBusyThreads = maxBusyThreads;
        }
        return n;
    }
    
    private ubThread dequeuePoolThreadfromList(final List list, final List list2, final RequestQueue requestQueue, final String value) {
        ubThread ubThread = null;
        RequestQueue requestQueue2 = null;
        if (this.log.ifLogBasic(1024L, 10)) {
            this.log.logBasic(10, "(" + this.pool.getListName() + "." + list.getListName() + ") dequeuePoolThreadfromList " + list2.getListName());
            list.print(this.log, 2, 10);
        }
        if (requestQueue != null) {
            list2.insertAtBack(requestQueue);
            requestQueue2 = (RequestQueue)list2.findFirst();
        }
        ubThread key;
        if (requestQueue == null || requestQueue == requestQueue2) {
            if (value != null && this.clientAffinity.containsKey(value)) {
                ubThread = this.clientAffinity.get(value);
                if (list.inList(ubThread)) {
                    key = ubThread;
                    if (this.log.ifLogBasic(1024L, 10)) {
                        this.log.logBasic(10, "(" + this.pool.getListName() + ") reusing affinity thread: " + key + " for connection: " + value);
                    }
                }
                else {
                    int n;
                    for (key = (ubThread)list.findFirst(), n = 1; n < list.size() && this.serverAffinity.containsKey(key); key = (ubThread)list.findNext(key), ++n) {}
                    if (n > list.size()) {
                        key = (ubThread)list.findFirst();
                    }
                }
            }
            else {
                key = (ubThread)list.findFirst();
            }
        }
        else {
            key = null;
        }
        if (this.log.ifLogBasic(1024L, 10)) {
            this.log.logBasic(10, "(" + this.pool.getListName() + ") dequeuePoolThreadfromList=" + key);
        }
        if (key != null) {
            this.updateThreadState(key, 2);
            if (requestQueue != null) {
                this.removeFirstFromList(list2);
            }
            if (value != null && key != ubThread) {
                if (ubThread != null && value.equals(this.serverAffinity.get(ubThread))) {
                    this.serverAffinity.remove(ubThread);
                }
                this.clientAffinity.put(value, key);
                this.serverAffinity.put(key, value);
            }
        }
        return key;
    }
    
    private RequestQueue dequeueWaiterFromList(final List list, final List list2) {
        final ubThread ubThread = (ubThread)Thread.currentThread();
        if (this.log.ifLogBasic(1024L, 10)) {
            this.log.logBasic(10, "(" + this.pool.getListName() + "." + list.getListName() + ") dequeueWaiterFromList " + list2.getListName());
            list.print(this.log, 2, 10);
        }
        final RequestQueue obj = (RequestQueue)this.removeFirstFromList(list2);
        if (this.log.ifLogBasic(1024L, 10)) {
            this.log.logBasic(10, "(" + this.pool.getListName() + ") dequeueWaiterFromList=" + obj);
        }
        if (obj != null) {
            this.updateThreadState(ubThread, 2);
            final String s = this.serverAffinity.get(ubThread);
            if (s != null && this.clientAffinity.get(s) == ubThread) {
                this.clientAffinity.remove(s);
            }
            this.serverAffinity.remove(ubThread);
        }
        return obj;
    }
    
    private boolean wakeWaiter(final RequestQueue requestQueue) {
        boolean b = true;
        final ubThread obj = (ubThread)Thread.currentThread();
        final RequestQueue rcvQueue = obj.getRcvQueue();
        final Object[] array = { obj };
        final ubAdminMsg ubAdminMsg = new ubAdminMsg((byte)7);
        ubAdminMsg.setadParm(array);
        try {
            requestQueue.enqueueRequest(new Request(ubAdminMsg, rcvQueue));
            if (this.log.ifLogBasic(1024L, 10)) {
                this.log.logBasic(10, "Enqueued rq (" + obj + ") to " + requestQueue.getListName());
            }
        }
        catch (Queue.QueueException obj2) {
            ubAdminMsg.print("Unable to enqueue msg to destQ : " + requestQueue.getListName() + " : " + obj2 + " : " + obj2.getMessage() + " : " + obj2.getDetail(), 1, 0, this.log);
            b = false;
        }
        return b;
    }
    
    private Object removeFromList(final Object obj, final List list) {
        if (this.log.ifLogBasic(1024L, 10)) {
            this.log.logBasic(10, "(" + this.pool.getListName() + "." + list.getListName() + ") removeFromList " + obj);
            list.print(this.log, 2, 10);
        }
        Object removeFromList;
        try {
            removeFromList = list.removeFromList(obj);
        }
        catch (List.EmptyListException ex) {
            removeFromList = null;
        }
        if (this.log.ifLogBasic(1024L, 10)) {
            this.log.logBasic(10, "(" + this.pool.getListName() + "." + list.getListName() + ") removed " + removeFromList);
            list.print(this.log, 2, 10);
        }
        return removeFromList;
    }
    
    private Object removeFirstFromList(final List list) {
        if (this.log.ifLogBasic(1024L, 10)) {
            this.log.logBasic(10, "(" + this.pool.getListName() + "." + list.getListName() + ") removeFirstFromList ");
            list.print(this.log, 2, 10);
        }
        Object removeFromFront;
        try {
            removeFromFront = list.removeFromFront();
        }
        catch (List.EmptyListException ex) {
            removeFromFront = null;
        }
        if (this.log.ifLogBasic(1024L, 10)) {
            this.log.logBasic(10, "(" + this.pool.getListName() + "." + list.getListName() + ") removed " + removeFromFront);
            list.print(this.log, 2, 10);
        }
        return removeFromFront;
    }
    
    private ubThread startThread(final int n) {
        final ubThread thread = this.createThread(n);
        if (thread != null) {
            thread.start();
        }
        return thread;
    }
    
    private boolean activateThread(final ubThread ubThread, final boolean b, final Object[] array, final RequestQueue requestQueue) {
        boolean waitAdminRsp = true;
        final ubAdminMsg ubAdminMsg = new ubAdminMsg((byte)1);
        ubAdminMsg.setadParm(array);
        if (this.log.ifLogBasic(1024L, 10)) {
            for (int i = 0; array != null && i < array.length; ++i) {
                this.log.logBasic(10, "admparm[" + i + "] = " + array[i].toString());
            }
        }
        try {
            ubThread.getRcvQueue().enqueueRequest(new Request(ubAdminMsg, requestQueue));
            if (this.log.ifLogBasic(1024L, 10)) {
                this.log.logBasic(10, "Enqueued rq to " + ubThread.getRcvQueue().getListName());
            }
            if (b) {
                waitAdminRsp = this.waitAdminRsp(requestQueue);
            }
        }
        catch (Queue.QueueException obj) {
            if (this.log.ifLogBasic(1024L, 10)) {
                ubAdminMsg.print("Unable to enqueue msg to destQ : " + ubThread.getRcvQueue().getListName() + " : " + obj + " : " + obj.getMessage() + " : " + obj.getDetail(), 2, 10, this.log);
            }
            waitAdminRsp = false;
        }
        return waitAdminRsp;
    }
    
    private boolean waitAdminRsp(final RequestQueue requestQueue) {
        if (this.log.ifLogBasic(1024L, 10)) {
            this.log.logBasic(10, "waiting for admin rsp at " + requestQueue.getListName());
        }
        final ubMsg obj = (ubMsg)requestQueue.dequeueRequest().getMsg();
        boolean b;
        if (obj instanceof ubAdminMsg) {
            final int getadRsp = ((ubAdminMsg)obj).getadRsp();
            b = (getadRsp == 0);
            if (!b && this.log.ifLogVerbose(1L, 0)) {
                this.log.logVerbose(0, 7665689515738013571L, new Object[] { Integer.toString(getadRsp, 16) });
            }
            if (this.log.ifLogBasic(1024L, 10)) {
                obj.print("admin response received: " + obj, 2, 10, this.log);
            }
        }
        else {
            b = false;
            this.log.logError(7665689515738013572L, new Object[] { Integer.toString(obj.getubRsp(), 16) });
            if (this.log.ifLogVerbose(1L, 0)) {
                obj.print("Invalid admin response received", 3, 0, this.log);
            }
        }
        return b;
    }
    
    public static class EmptyPoolException extends ProException
    {
        public EmptyPoolException(final String s) {
            super("EmptyPoolException", new Object[] { s });
        }
        
        public String getDetail() {
            return (String)this.getArgument(0);
        }
    }
    
    public class ubThreadPoolStats
    {
        private int numThreads;
        private int maxActiveThreads;
        private int numIdleThreads;
        private int numReadyThreads;
        private int numBoundThreads;
        private int numBusyThreads;
        private int numRqs;
        private int numRqMsgs;
        private int numRspMsgs;
        private int maxRqWait;
        private int avgRqWait;
        private int maxRqDuration;
        private int avgRqDuration;
        private int curWaitReadyQueueDepth;
        private int maxWaitReadyQueueDepth;
        
        public ubThreadPoolStats(final int numThreads, final int maxActiveThreads, final int numIdleThreads, final int numReadyThreads, final int numBoundThreads, final int numBusyThreads, final int numRqs, final int numRqMsgs, final int numRspMsgs, final int maxRqWait, final int avgRqWait, final int maxRqDuration, final int avgRqDuration, final int curWaitReadyQueueDepth, final int maxWaitReadyQueueDepth) {
            this.numThreads = numThreads;
            this.maxActiveThreads = maxActiveThreads;
            this.numIdleThreads = numIdleThreads;
            this.numReadyThreads = numReadyThreads;
            this.numBoundThreads = numBoundThreads;
            this.numBusyThreads = numBusyThreads;
            this.numRqs = numRqs;
            this.numRqMsgs = numRqMsgs;
            this.numRspMsgs = numRspMsgs;
            this.maxRqWait = maxRqWait;
            this.avgRqWait = avgRqWait;
            this.maxRqDuration = maxRqDuration;
            this.avgRqDuration = avgRqDuration;
            this.curWaitReadyQueueDepth = curWaitReadyQueueDepth;
            this.maxWaitReadyQueueDepth = maxWaitReadyQueueDepth;
        }
        
        public int getnumThreads() {
            return this.numThreads;
        }
        
        public int getmaxActiveThreads() {
            return this.maxActiveThreads;
        }
        
        public int getnumIdleThreads() {
            return this.numIdleThreads;
        }
        
        public int getnumReadyThreads() {
            return this.numReadyThreads;
        }
        
        public int getnumBoundThreads() {
            return this.numBoundThreads;
        }
        
        public int getnumBusyThreads() {
            return this.numBusyThreads;
        }
        
        public int getnumRqs() {
            return this.numRqs;
        }
        
        public int getnumRqMsgs() {
            return this.numRqMsgs;
        }
        
        public int getnumRspMsgs() {
            return this.numRspMsgs;
        }
        
        public int getMaxRqWait() {
            return this.maxRqWait;
        }
        
        public int getAvgRqWait() {
            return this.avgRqWait;
        }
        
        public int getMaxRqDuration() {
            return this.maxRqDuration;
        }
        
        public int getAvgRqDuration() {
            return this.avgRqDuration;
        }
        
        public int getCurWaitReadyQueueDepth() {
            return this.curWaitReadyQueueDepth;
        }
        
        public int getMaxWaitReadyQueueDepth() {
            return this.maxWaitReadyQueueDepth;
        }
        
        public void print(final IAppLogger appLogger, final int n, final int n2, final String s) {
            appLogger.logWithThisLevel(n, n2, s);
            appLogger.logWithThisLevel(n, n2, "numThreads             = " + this.numThreads);
            appLogger.logWithThisLevel(n, n2, "numIdleThreads         = " + this.numIdleThreads);
            appLogger.logWithThisLevel(n, n2, "numReadyThreads        = " + this.numReadyThreads);
            appLogger.logWithThisLevel(n, n2, "numBoundThreads        = " + this.numBoundThreads);
            appLogger.logWithThisLevel(n, n2, "numBusyThreads         = " + this.numBusyThreads);
            appLogger.logWithThisLevel(n, n2, "numRqs                 = " + this.numRqs);
            appLogger.logWithThisLevel(n, n2, "numRqMsgs              = " + this.numRqMsgs);
            appLogger.logWithThisLevel(n, n2, "numRspMsgs             = " + this.numRspMsgs);
            appLogger.logWithThisLevel(n, n2, "maxRqWait              = " + this.maxRqWait);
            appLogger.logWithThisLevel(n, n2, "avgRqWait              = " + this.avgRqWait);
            appLogger.logWithThisLevel(n, n2, "maxRqDuration          = " + this.maxRqDuration);
            appLogger.logWithThisLevel(n, n2, "avgRqDuration          = " + this.avgRqDuration);
            appLogger.logWithThisLevel(n, n2, "curWaitReadyQueueDepth = " + this.curWaitReadyQueueDepth);
            appLogger.logWithThisLevel(n, n2, "maxWaitReadyQueueDepth = " + this.maxWaitReadyQueueDepth);
        }
    }
}
