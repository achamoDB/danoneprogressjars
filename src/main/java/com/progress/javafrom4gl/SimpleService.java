// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.javafrom4gl;

import java.util.Properties;

public abstract class SimpleService implements JavaService, ServiceConnection
{
    public void _startup(final Properties properties) throws Exception {
    }
    
    public final ServiceConnection _connect(final String str, final String s, final String s2, final String s3) throws Exception {
        if (!this._checkUserAccess(str, s)) {
            throw new Exception("user " + str + " connection rejected.");
        }
        return this;
    }
    
    public void _shutdown() {
    }
    
    public final void _disconnect() throws Exception {
    }
    
    public String _getExportList() {
        return null;
    }
    
    public Object _getInitialObject() {
        return this;
    }
    
    protected boolean _checkUserAccess(final String s, final String s2) throws Exception {
        return true;
    }
    
    public void _stop() {
    }
}
