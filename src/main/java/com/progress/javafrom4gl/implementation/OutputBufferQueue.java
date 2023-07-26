// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.javafrom4gl.implementation;

import com.progress.open4gl.IntHolder;
import com.progress.ubroker.util.Logger;
import com.progress.ubroker.util.Queue;

class OutputBufferQueue extends Queue
{
    private static final int QUEUE_LIMIT = 100;
    private long timeout;
    
    OutputBufferQueue(final long timeout) {
        super("MessageEventServerQueue", 100, new Logger());
        this.timeout = timeout;
    }
    
    void enqueue(final byte[] array, final int n) throws Exception {
        this.enqueue(new BufferContainer(array, n));
    }
    
    byte[] poll(final IntHolder intHolder) {
        final BufferContainer bufferContainer = (BufferContainer)this.poll(this.timeout);
        if (bufferContainer == null) {
            return null;
        }
        intHolder.setIntValue(bufferContainer.howmuch);
        return bufferContainer.buffer;
    }
    
    static class BufferContainer
    {
        byte[] buffer;
        int howmuch;
        
        BufferContainer(final byte[] buffer, final int howmuch) {
            this.buffer = buffer;
            this.howmuch = howmuch;
        }
    }
}
