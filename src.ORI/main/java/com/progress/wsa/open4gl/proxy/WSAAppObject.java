// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa.open4gl.proxy;

import com.progress.open4gl.dynamicapi.ParameterSet;
import com.progress.open4gl.SystemErrorException;
import com.progress.open4gl.RunTime4GLException;
import com.progress.open4gl.dynamicapi.Session;
import com.progress.open4gl.dynamicapi.SessionPool;
import java.util.HashMap;
import com.progress.common.ehnlog.IAppLogger;
import com.progress.wsa.admin.AppRuntimeProps;
import java.net.MalformedURLException;
import com.progress.open4gl.Open4GLException;
import com.progress.common.util.PscURLParser;

public class WSAAppObject extends WSAProxyObject
{
    private static final String m_newProtocol = "http:";
    
    protected static String getURLString(final String s, final String s2) throws Open4GLException, MalformedURLException {
        return new PscURLParser(s).getAppServerURL(s2);
    }
    
    public WSAAppObject(final String s, final AppRuntimeProps appRuntimeProps, final IAppLogger appLogger, final int n, final String s2, final String s3, final String s4, final String s5, final String s6) throws Open4GLException, RunTime4GLException, SystemErrorException {
        super(s, appRuntimeProps, appLogger, n, s2, s3, s4, s5, s6);
        if (this.getSessionMode() == 0) {
            final SessionPool sessionPool;
            if ((sessionPool = this.getSessionPool()) == null) {
                throw new Open4GLException(7665970990714723420L, null);
            }
            if (null != super.m_CI && super.m_CI.isInstrumented()) {
                super.m_CI.beginInteraction(null);
                super.m_CI.analyze("_connect");
                if (appLogger.ifLogVerbose(32768L, 15)) {
                    appLogger.logVerbose(15, "Creating ClientInteraction: " + super.m_CI.toString());
                }
            }
            try {
                final Session reserveSession = sessionPool.reserveSession();
                this.setReturnValue(reserveSession.getReturnValue());
                this.setSessionID(reserveSession.getSessionID());
                if (null != super.m_CI && super.m_CI.isInstrumented()) {
                    if (appLogger.ifLogVerbose(32768L, 15)) {
                        appLogger.logVerbose(15, "Ending ClientInteraction successfuly: " + super.m_CI.toString());
                    }
                    super.m_CI.endInteraction(null);
                }
            }
            catch (Open4GLException ex) {
                if (null != super.m_CI && super.m_CI.isInstrumented()) {
                    if (appLogger.ifLogVerbose(32768L, 15)) {
                        appLogger.logVerbose(15, "Ending ClientInteraction with failure: " + super.m_CI.toString());
                    }
                    super.m_CI.endInteraction(ex.getMessage());
                }
                throw ex;
            }
        }
    }
    
    public WSAProcObject CreatePO(final String s, final ParameterSet set) throws Open4GLException, RunTime4GLException, SystemErrorException {
        if (this.getSessionPool() == null) {
            throw new Open4GLException(7665970990714723420L, null);
        }
        return new WSAProcObject(this, s, set);
    }
    
    public WSASubAppObject CreateSubAO() throws Open4GLException, RunTime4GLException, SystemErrorException {
        if (this.getSessionPool() == null) {
            throw new Open4GLException(7665970990714723420L, null);
        }
        return new WSASubAppObject(this);
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
