// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.util;

import com.progress.common.ehnlog.IAppLogger;

public class RequestQueue extends Queue
{
    public RequestQueue(final String s, final int n, final IAppLogger appLogger) {
        super(s, n, appLogger);
    }
    
    public synchronized void enqueueRequest(final Request request) throws QueueException {
        request.settsEnqueue();
        this.enqueue(request);
    }
    
    public synchronized void enqueuePriorityRequest(final Request request) throws QueueException {
        request.settsEnqueue();
        this.enqueuePriority(request);
    }
    
    public synchronized Request dequeueRequest() {
        final Request request = (Request)this.dequeue();
        request.settsDequeue();
        return request;
    }
    
    public synchronized Request pollRequest(final long n) {
        final Request request = (Request)this.poll(n);
        if (request != null) {
            request.settsDequeue();
        }
        return request;
    }
}
