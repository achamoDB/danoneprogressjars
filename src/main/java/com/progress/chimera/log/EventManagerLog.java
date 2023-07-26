// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera.log;

import com.progress.common.log.Subsystem;

public class EventManagerLog extends Subsystem
{
    static EventManagerLog self;
    
    public EventManagerLog() {
        super("EventManager");
    }
    
    public static EventManagerLog get() {
        if (EventManagerLog.self == null) {
            EventManagerLog.self = new EventManagerLog();
        }
        return EventManagerLog.self;
    }
    
    static {
        EventManagerLog.self = null;
    }
}
