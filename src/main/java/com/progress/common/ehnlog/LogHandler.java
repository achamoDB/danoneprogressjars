// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.ehnlog;

public interface LogHandler
{
    void log(final int p0, final String p1, final String p2, final String p3);
    
    void log(final int p0, final String p1, final String p2, final String p3, final byte[] p4, final int p5);
    
    void log(final int p0, final String p1, final String p2, final String p3, final Throwable p4);
}
