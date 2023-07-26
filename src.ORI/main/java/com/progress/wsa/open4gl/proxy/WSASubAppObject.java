// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa.open4gl.proxy;

import com.progress.open4gl.SystemErrorException;
import com.progress.open4gl.RunTime4GLException;
import com.progress.open4gl.dynamicapi.ParameterSet;
import com.progress.open4gl.Open4GLException;

public class WSASubAppObject extends WSAProxyObject
{
    public WSASubAppObject(final WSAProxyObject wsaProxyObject) throws Open4GLException {
        super(wsaProxyObject, "SO");
    }
    
    public WSAProcObject CreatePO(final String s, final ParameterSet set) throws Open4GLException, RunTime4GLException, SystemErrorException {
        if (this.getSession() == null) {
            throw new Open4GLException(7665970990714723420L, null);
        }
        return new WSAProcObject(this, s, set);
    }
}
