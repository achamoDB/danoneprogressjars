// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.javafrom4gl.implementation;

import com.progress.ubroker.util.ubProperties;
import com.progress.javafrom4gl.Log;
import java.util.Hashtable;
import com.progress.javafrom4gl.ConnectionContext;

public class ConnectionContextImpl implements ConnectionContext
{
    static Hashtable table;
    public static Log javaServiceLog;
    public static ubProperties brokerProperties;
    
    static void register(final ConnectionContextImpl connectionContextImpl) {
    }
    
    static void unRegister() {
    }
    
    ConnectionContextImpl(final String s, final String s2, final String s3, final String s4, final String s5) {
    }
    
    public static ConnectionContext getConnectionContext() {
        return null;
    }
    
    public String getUser() {
        return null;
    }
    
    public String getPassword() {
        return null;
    }
    
    public String getConnId() {
        return null;
    }
    
    public String getAppInfo() {
        return null;
    }
    
    public String getClientCodePage() {
        return null;
    }
    
    static {
        ConnectionContextImpl.table = new Hashtable();
    }
}
