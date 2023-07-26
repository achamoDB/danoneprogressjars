// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.webspeed.wsgateway;

public class WSBrokenConnectionException extends WSConnectionException
{
    public WSBrokenConnectionException(final String s) {
        super(s, "BROKEN_CONNECTION");
    }
}
