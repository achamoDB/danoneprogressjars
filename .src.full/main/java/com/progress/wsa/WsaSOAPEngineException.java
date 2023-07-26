// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa;

import com.progress.common.exception.ProException;

public class WsaSOAPEngineException extends ProException
{
    public WsaSOAPEngineException(final String s) {
        super(s, new Object[0]);
    }
    
    public WsaSOAPEngineException(final String s, final Throwable t) {
        super(s, new Object[0], t);
    }
    
    public WsaSOAPEngineException(final String s, final Object[] array) {
        super(s, array, null);
    }
    
    public WsaSOAPEngineException(final String s, final Object[] array, final Throwable t) {
        super(s, array, t);
    }
    
    public WsaSOAPEngineException(final long n, final Object[] array) {
        super(n, array, null);
    }
    
    public WsaSOAPEngineException(final long n, final Object[] array, final Throwable t) {
        super(n, array, t);
    }
}
