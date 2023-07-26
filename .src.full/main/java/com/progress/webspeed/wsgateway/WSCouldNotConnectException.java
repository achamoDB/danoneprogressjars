// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.webspeed.wsgateway;

public class WSCouldNotConnectException extends WSConnectionException
{
    private String wsReason;
    
    public WSCouldNotConnectException(final String s, final String original) {
        super(s, "COULD_NOT_CONNECT");
        this.wsReason = null;
        this.wsReason = new String(original);
    }
    
    public String getReason() {
        return this.wsReason;
    }
}
