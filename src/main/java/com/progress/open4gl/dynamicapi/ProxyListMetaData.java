// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

class ProxyListMetaData
{
    static ResultSetMetaData metaData;
    
    private ProxyListMetaData() {
    }
    
    static {
        (ProxyListMetaData.metaData = new ResultSetMetaData(0, 1)).setFieldDesc(1, "proxyId", 1, 4);
    }
}
