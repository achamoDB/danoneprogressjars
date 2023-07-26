// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl;

import com.progress.message.jcMsg;

public class NetworkException extends SystemErrorException
{
    public NetworkException(final long n, final Object[] array) {
        super(n, array);
    }
    
    public static class SendDataException extends NetworkException implements jcMsg
    {
        public SendDataException(final long n, final Object[] array) {
            super(n, array);
        }
    }
    
    public static class RecvDataException extends NetworkException implements jcMsg
    {
        public RecvDataException(final long n, final Object[] array) {
            super(n, array);
        }
    }
}
