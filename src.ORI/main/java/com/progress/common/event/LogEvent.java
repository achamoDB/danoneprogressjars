// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.event;

public abstract class LogEvent extends MessageEvent
{
    public LogEvent(final Object o, final long n, final Object[] array) {
        super(o, n, array);
    }
    
    public LogEvent(final Object o, final String s, final Object[] array) {
        super(o, s, array);
    }
}
