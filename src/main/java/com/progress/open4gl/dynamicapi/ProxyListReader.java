// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

import com.progress.open4gl.ProSQLException;
import com.progress.open4gl.Open4GLError;
import com.progress.open4gl.RunTimeProperties;
import java.io.InputStream;

class ProxyListReader
{
    private InputStream stream;
    private ResultSet proxySet;
    private long proxyID;
    private Tracer tracer;
    
    ProxyListReader(final InputStream inputStream) {
        this.tracer = RunTimeProperties.tracer;
        this.proxySet = new ResultSet(ProxyListMetaData.metaData, new StreamReader(inputStream, 9));
    }
    
    boolean nextProxyID() throws ClientException {
        boolean next;
        try {
            next = this.proxySet.next();
            if (next) {
                this.proxyID = this.proxySet.getLong(1);
                this.tracer.print("proxyListReader : server deleted proc= " + this.proxyID);
            }
        }
        catch (ProSQLException ex) {
            throw new Open4GLError(54L, new Object[] { ex.getMessage() });
        }
        return next;
    }
    
    long getProxyID() {
        return this.proxyID;
    }
    
    ResultSet getProxySet() {
        return this.proxySet;
    }
}
