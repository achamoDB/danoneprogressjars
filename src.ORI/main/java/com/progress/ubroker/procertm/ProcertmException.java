// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.procertm;

import com.progress.common.exception.ProException;

public class ProcertmException extends ProException
{
    private String a;
    private int b;
    public static int FATAL_ERROR;
    public static int DEBUG_INFO;
    public static int BAD_INPUT;
    public static int USER_INFO;
    
    public ProcertmException(final String s, final int b) {
        this(s);
        this.b = b;
    }
    
    public ProcertmException(final String a) {
        super(a, (Object[])null);
        this.a = null;
        this.b = 0;
        this.a = a;
    }
    
    public String getMessage() {
        return this.a;
    }
    
    public boolean debugInfo() {
        return this.b < ProcertmException.DEBUG_INFO;
    }
    
    static {
        ProcertmException.FATAL_ERROR = 1;
        ProcertmException.DEBUG_INFO = 2;
        ProcertmException.BAD_INPUT = 3;
        ProcertmException.USER_INFO = 4;
    }
}
