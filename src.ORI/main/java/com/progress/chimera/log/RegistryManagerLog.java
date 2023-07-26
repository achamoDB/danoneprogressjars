// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera.log;

import com.progress.common.log.Subsystem;

public class RegistryManagerLog extends Subsystem
{
    static RegistryManagerLog self;
    
    public RegistryManagerLog() {
        super("RegistryManager");
    }
    
    public static RegistryManagerLog get() {
        if (RegistryManagerLog.self == null) {
            RegistryManagerLog.self = new RegistryManagerLog();
        }
        return RegistryManagerLog.self;
    }
    
    static {
        RegistryManagerLog.self = null;
    }
}
