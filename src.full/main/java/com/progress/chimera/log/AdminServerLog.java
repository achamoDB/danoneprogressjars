// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera.log;

import com.progress.common.log.Subsystem;

public class AdminServerLog extends Subsystem
{
    static AdminServerLog self;
    
    public AdminServerLog() {
        super("AdminServer");
    }
    
    public static AdminServerLog get() {
        if (AdminServerLog.self == null) {
            AdminServerLog.self = new AdminServerLog();
        }
        return AdminServerLog.self;
    }
    
    static {
        AdminServerLog.self = null;
    }
}
