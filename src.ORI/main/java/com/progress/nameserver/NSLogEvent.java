// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.nameserver;

import com.progress.common.event.LogEvent;

public class NSLogEvent extends LogEvent
{
    public static final int ERROR_EVENT = 1;
    public static final int BASIC_EVENT = 2;
    public static final int VERBOSE_EVENT = 3;
    public static final int EXTENDED_EVENT = 4;
    private int level;
    
    public NSLogEvent(final Object o, final int level, final long n, final Object[] array) {
        super(o, n, array);
        this.level = level;
    }
    
    public NSLogEvent(final Object o, final int level, final String s, final Object[] array) {
        super(o, s, array);
        this.level = level;
    }
    
    public int getLevel() {
        return this.level;
    }
}
