// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera.log;

import com.progress.common.log.Subsystem;

public class ConnectionManagerLog extends Subsystem
{
    static ConnectionManagerLog self;
    
    public ConnectionManagerLog() {
        super("Database");
    }
    
    public static ConnectionManagerLog get() {
        if (ConnectionManagerLog.self == null) {
            ConnectionManagerLog.self = new ConnectionManagerLog();
        }
        return ConnectionManagerLog.self;
    }
    
    static {
        ConnectionManagerLog.self = null;
    }
}
