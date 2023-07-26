// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.sql.explorer;

import com.progress.common.log.Subsystem;

public class SQLExplorerLog extends Subsystem
{
    static SQLExplorerLog self;
    
    public SQLExplorerLog() {
        super("SQLExplorer");
    }
    
    public static SQLExplorerLog get() {
        if (SQLExplorerLog.self == null) {
            SQLExplorerLog.self = new SQLExplorerLog();
        }
        return SQLExplorerLog.self;
    }
    
    static {
        SQLExplorerLog.self = null;
    }
}
