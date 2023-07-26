// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.event;

public class DebugEvent extends LogEvent
{
    public DebugEvent(final String str) {
        super(DebugEvent.class, "DEBUG: " + str, null);
    }
}
