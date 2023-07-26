// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa.tools;

import com.progress.common.log.Subsystem;

public class WsaPluginLog extends Subsystem
{
    static WsaPluginLog self;
    
    public WsaPluginLog() {
        super("WebServicesAdapter");
    }
    
    public static WsaPluginLog get() {
        if (WsaPluginLog.self == null) {
            WsaPluginLog.self = new WsaPluginLog();
        }
        return WsaPluginLog.self;
    }
    
    public static void logError(final String s) {
        get().logErr(s);
    }
    
    public static void logError(final int n, final String s) {
        get().logErr(n, s);
    }
    
    public static void logMsg(final String s) {
        get().log(5, s);
    }
    
    public static void logMsg(final int n, final String s) {
        get().log(n, s);
    }
    
    static {
        WsaPluginLog.self = null;
    }
}
