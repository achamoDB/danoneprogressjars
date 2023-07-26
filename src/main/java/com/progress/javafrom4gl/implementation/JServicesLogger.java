// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.javafrom4gl.implementation;

import com.progress.javafrom4gl.Log;
import com.progress.ubroker.util.Logger;

public class JServicesLogger extends Logger implements Log
{
    public JServicesLogger(final String s, final int n, final boolean b) throws Exception {
        super(s, n, b);
    }
    
    public void setLoggingLevel(final int loggingLevel) {
        super.setLoggingLevel(loggingLevel);
    }
    
    public void LogMsgln(final int n, final boolean b, final String str, final String str2) {
        super.LogMsgln(2, n, b, str + ": " + str2);
    }
    
    public void LogStackTrace(final int n, final boolean b, final String s, final Throwable t) {
        super.LogStackTrace(2, n, b, s, t);
    }
}
