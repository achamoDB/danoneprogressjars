// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.javafrom4gl;

import com.progress.ubroker.util.ubProperties;
import com.progress.javafrom4gl.implementation.ConnectionContextImpl;

public class ServiceRuntime
{
    public static ConnectionContext getConnectionContext() {
        return ConnectionContextImpl.getConnectionContext();
    }
    
    public static Log getLog() {
        return ConnectionContextImpl.javaServiceLog;
    }
    
    public static ubProperties getBrokerProperties() {
        return ConnectionContextImpl.brokerProperties;
    }
}
