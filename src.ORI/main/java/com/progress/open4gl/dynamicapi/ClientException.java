// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

import com.progress.open4gl.Open4GLException;

public class ClientException extends Exception
{
    static final int IO_REASON = 1;
    static final int STREAM_PROTOCOL_REASON = 2;
    static final int INPUT_TTABLE_REASON = 3;
    static final int UNKNOWN_REASON = 4;
    static final int INPUT_LIMITS_REASON = 5;
    static final int END_OF_STREAM_REASON = 6;
    private long messageId;
    private int reason;
    private Object[] args;
    
    ClientException() {
        this.reason = 4;
        this.messageId = 0L;
        this.args = null;
    }
    
    ClientException(final int reason, final long messageId, final Object[] args) {
        this.reason = reason;
        this.messageId = messageId;
        this.args = args;
    }
    
    ClientException(final long messageId, final Object[] args) {
        this.reason = 4;
        this.messageId = messageId;
        this.args = args;
    }
    
    Object[] getArgs() {
        return this.args;
    }
    
    int getReason() {
        return this.reason;
    }
    
    long getMessageId() {
        return this.messageId;
    }
    
    public String getMessage() {
        if (this.messageId == 0L) {
            return super.getMessage();
        }
        return new Open4GLException(this.messageId, this.args).getMessage();
    }
}
