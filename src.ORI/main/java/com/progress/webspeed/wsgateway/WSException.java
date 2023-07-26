// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.webspeed.wsgateway;

public class WSException extends Exception
{
    private String wsMessage;
    private String wsCode;
    
    public WSException(final String original, final String original2) {
        this.wsMessage = null;
        this.wsCode = null;
        this.wsMessage = new String(original);
        this.wsCode = new String(original2);
    }
    
    public String getMessage() {
        return this.wsMessage;
    }
    
    public String getCode() {
        return this.wsCode;
    }
}
