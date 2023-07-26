// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.property;

import com.progress.common.log.Subsystem;

public class PropertyLog extends Subsystem
{
    static PropertyLog self;
    
    public PropertyLog() {
        super("Property Manager");
    }
    
    public static PropertyLog get() {
        if (PropertyLog.self == null) {
            PropertyLog.self = new PropertyLog();
        }
        return PropertyLog.self;
    }
    
    static {
        PropertyLog.self = null;
    }
}
