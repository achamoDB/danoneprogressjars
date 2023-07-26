// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl;

public class ConnectException extends Open4GLException
{
    public ConnectException(final String s) {
        super(s);
    }
    
    public ConnectException(final String s, final Object[] array) {
        super(s, array);
    }
    
    public ConnectException(final long n, final Object[] array) {
        super(n, array);
    }
    
    public void setProcReturnString(final String procReturnString) {
        super.setProcReturnString(procReturnString);
    }
}
