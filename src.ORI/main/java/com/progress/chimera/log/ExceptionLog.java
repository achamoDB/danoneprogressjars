// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera.log;

import com.progress.common.log.Subsystem;

public class ExceptionLog extends Subsystem
{
    static ExceptionLog self;
    
    public ExceptionLog() {
        super("*UnexpectedError*");
    }
    
    public static ExceptionLog get() {
        if (ExceptionLog.self == null) {
            ExceptionLog.self = new ExceptionLog();
        }
        return ExceptionLog.self;
    }
    
    static {
        ExceptionLog.self = null;
    }
}
