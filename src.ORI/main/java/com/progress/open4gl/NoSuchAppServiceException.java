// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl;

public class NoSuchAppServiceException extends ConnectException
{
    public NoSuchAppServiceException(final String s, final String s2, final int value) {
        super(7665970990714724394L, new Object[] { (s == null || s.equals("")) ? "[Default]" : s, s2, new Integer(value) });
    }
}
