// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.message;

public interface IProMessage
{
    String getMessage(final long p0) throws ProMessageException;
    
    public static class ProMessageException extends Exception
    {
        public ProMessageException(final String message) {
            super(message);
        }
    }
}
