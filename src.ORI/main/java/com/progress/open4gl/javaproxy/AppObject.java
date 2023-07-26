// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.javaproxy;

import com.progress.open4gl.dynamicapi.ParameterSet;
import com.progress.open4gl.SystemErrorException;
import com.progress.open4gl.RunTime4GLException;
import com.progress.open4gl.dynamicapi.SessionPool;
import com.progress.common.ehnlog.IAppLogger;
import com.progress.open4gl.dynamicapi.IPoolProps;
import java.net.MalformedURLException;
import com.progress.open4gl.Open4GLException;
import com.progress.common.util.PscURLParser;
import com.progress.open4gl.SDOFactory;

public class AppObject extends ProObject implements SDOFactory
{
    private static final String m_newProtocol = "http:";
    
    protected static String getURLString(final String s, final String s2) throws Open4GLException, MalformedURLException {
        return new PscURLParser(s).getAppServerURL(s2);
    }
    
    public AppObject(final String s, final IPoolProps poolProps, final IAppLogger appLogger, final String s2) throws Open4GLException, RunTime4GLException, SystemErrorException {
        super(s, poolProps, appLogger, s2);
        if (this.getSessionMode() == 0) {
            final SessionPool sessionPool;
            if ((sessionPool = this.getSessionPool()) == null) {
                throw new Open4GLException(7665970990714723420L, null);
            }
            this.setSessionID(sessionPool.reserveSession().getSessionID());
        }
    }
    
    public AppObject() {
    }
    
    public Procedure CreatePO(final String s, final ParameterSet set) throws Open4GLException, RunTime4GLException, SystemErrorException {
        if (this.getSessionPool() == null) {
            throw new Open4GLException(7665970990714723420L, null);
        }
        return new Procedure(this, s, set);
    }
    
    public SubAppObject CreateSubAO() throws Open4GLException, RunTime4GLException, SystemErrorException {
        if (this.getSessionPool() == null) {
            throw new Open4GLException(7665970990714723420L, null);
        }
        return new SubAppObject(this);
    }
    
    public int sessionCount() {
        int size = 0;
        final SessionPool sessionPool = this.getSessionPool();
        if (null != sessionPool) {
            size = sessionPool.size();
        }
        return size;
    }
}
