// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera.log;

import com.progress.common.log.Subsystem;

public class ChimeraLog extends Subsystem
{
    static ChimeraLog self;
    
    public ChimeraLog() {
        super("ProgressExplorer");
    }
    
    public static ChimeraLog get() {
        if (ChimeraLog.self == null) {
            ChimeraLog.self = new ChimeraLog();
        }
        return ChimeraLog.self;
    }
    
    static {
        ChimeraLog.self = null;
    }
}
