// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.webspeed.wsgateway;

public class WSBadValueException extends WSException
{
    private Object wsObject;
    
    public WSBadValueException(final String s, final Object wsObject) {
        super(s, "BAD_VALUE");
        this.wsObject = wsObject;
    }
    
    public Object getValue() {
        return this.wsObject;
    }
}
