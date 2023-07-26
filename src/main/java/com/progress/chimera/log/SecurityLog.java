// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera.log;

import com.progress.common.log.Subsystem;

public class SecurityLog extends Subsystem
{
    static SecurityLog self;
    
    public SecurityLog() {
        super("Security");
    }
    
    public static SecurityLog get() {
        if (SecurityLog.self == null) {
            SecurityLog.self = new SecurityLog();
        }
        return SecurityLog.self;
    }
    
    static {
        SecurityLog.self = null;
    }
}
