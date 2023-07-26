// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera.log;

import com.progress.common.log.Subsystem;

public class DefaultLog extends Subsystem
{
    static DefaultLog self;
    
    public DefaultLog() {
        super("Default");
    }
    
    public static DefaultLog get() {
        if (DefaultLog.self == null) {
            DefaultLog.self = new DefaultLog();
        }
        return DefaultLog.self;
    }
    
    static {
        DefaultLog.self = null;
    }
}
