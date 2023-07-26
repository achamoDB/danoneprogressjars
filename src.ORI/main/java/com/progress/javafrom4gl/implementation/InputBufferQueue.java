// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.javafrom4gl.implementation;

import com.progress.open4gl.BooleanHolder;
import com.progress.open4gl.IntHolder;

class InputBufferQueue
{
    private byte[] buffer;
    private boolean dataUsed;
    private boolean last;
    private int mode;
    private int len;
    
    InputBufferQueue() {
        this.dataUsed = true;
        this.buffer = null;
    }
    
    synchronized byte[] get(final IntHolder intHolder, final BooleanHolder booleanHolder, final IntHolder intHolder2) throws Exception {
        if (this.dataUsed) {
            this.notifyAll();
            this.wait();
        }
        intHolder.setIntValue(this.mode);
        booleanHolder.setBooleanValue(this.last);
        intHolder2.setIntValue(this.len);
        this.dataUsed = true;
        return this.buffer;
    }
    
    synchronized void put(final byte[] buffer, final int len, final boolean last, final int mode) throws Exception {
        this.buffer = buffer;
        this.dataUsed = false;
        this.last = last;
        this.mode = mode;
        this.len = len;
        this.notifyAll();
        if (!last) {
            this.wait();
        }
    }
}
