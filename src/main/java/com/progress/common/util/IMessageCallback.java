// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.util;

public interface IMessageCallback
{
    void handleMessage(final String p0);
    
    void handleMessage(final int p0, final String p1);
    
    void handleException(final Throwable p0, final String p1);
}
