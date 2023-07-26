// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa;

public final class WsaState
{
    private final String _name;
    public static final WsaState UNINITIALIZED;
    public static final WsaState INITFAILURE;
    public static final WsaState RUNNING;
    public static final WsaState SHUTDOWN;
    public static final WsaState STOPPED;
    
    private WsaState(final String name) {
        this._name = name;
    }
    
    public String toString() {
        return this._name;
    }
    
    static {
        UNINITIALIZED = new WsaState("uninitialized");
        INITFAILURE = new WsaState("initialization failed");
        RUNNING = new WsaState("running");
        SHUTDOWN = new WsaState("shutdown");
        STOPPED = new WsaState("stopped");
    }
}
