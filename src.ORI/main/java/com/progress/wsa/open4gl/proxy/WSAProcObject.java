// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa.open4gl.proxy;

import com.progress.open4gl.dynamicapi.Session;
import com.progress.open4gl.dynamicapi.SessionPool;
import java.util.HashMap;
import com.progress.open4gl.SystemErrorException;
import com.progress.open4gl.RunTime4GLException;
import com.progress.open4gl.dynamicapi.RqContext;
import com.progress.open4gl.Open4GLException;
import com.progress.open4gl.dynamicapi.ResultSetSchema;
import com.progress.open4gl.dynamicapi.ParameterSet;
import com.progress.open4gl.dynamicapi.PersistentProc;

public class WSAProcObject extends WSAProxyObject
{
    private PersistentProc m_persistProc;
    private String m_sessionID;
    
    public WSAProcObject(final WSAProxyObject wsaProxyObject, final String s, final ParameterSet set) throws Open4GLException {
        super(wsaProxyObject, "PO");
        this.m_persistProc = this.runPersistentProcedure(null, s, set, null);
    }
    
    public WSAProcObject(final String s, final WSAProxyObject wsaProxyObject, final String s2, final ParameterSet set) throws Open4GLException {
        super(wsaProxyObject, "PO");
        this.m_persistProc = this.runPersistentProcedure(s, s2, set, null);
    }
    
    public WSAProcObject(final WSAProxyObject wsaProxyObject, final String s, final ParameterSet set, final ResultSetSchema resultSetSchema) throws Open4GLException {
        super(wsaProxyObject, "PO");
        this.m_persistProc = this.runPersistentProcedure(null, s, set, resultSetSchema);
    }
    
    public WSAProcObject(final String s, final WSAProxyObject wsaProxyObject, final String s2, final ParameterSet set, final ResultSetSchema resultSetSchema) throws Open4GLException {
        super(wsaProxyObject, "PO");
        this.m_persistProc = this.runPersistentProcedure(s, s2, set, resultSetSchema);
    }
    
    public RqContext runProcedure(final String s, final ParameterSet set) throws Open4GLException, RunTime4GLException, SystemErrorException {
        return this.runProcedure(null, s, set, null);
    }
    
    public RqContext runProcedure(final String s, final String s2, final ParameterSet set) throws Open4GLException, RunTime4GLException, SystemErrorException {
        return this.runProcedure(s, s2, set, null);
    }
    
    public RqContext runProcedure(final String s, final ParameterSet set, final ResultSetSchema resultSetSchema) throws Open4GLException, RunTime4GLException, SystemErrorException {
        return this.runProcedure(null, s, set, resultSetSchema);
    }
    
    public RqContext runProcedure(final String s, final String str, final ParameterSet set, final ResultSetSchema resultSetSchema) throws Open4GLException, RunTime4GLException, SystemErrorException {
        if (this.m_persistProc == null) {
            throw new Open4GLException(7665970990714723420L, null);
        }
        try {
            if (null != super.m_CI && super.m_CI.isInstrumented()) {
                final StringBuffer sb = new StringBuffer();
                sb.append(this.m_persistProc.getFileName());
                sb.append(".");
                sb.append(str);
                super.m_CI.beginInteraction(null);
                super.m_CI.analyze(sb.toString());
                if (super.m_log.ifLogVerbose(32768L, 15)) {
                    super.m_log.logVerbose(15, "Creating ClientInteraction: " + super.m_CI.toString());
                }
            }
            this.m_persistProc.runProcedure(s, str, set, resultSetSchema);
        }
        catch (Open4GLException ex) {
            if (null != super.m_CI && super.m_CI.isInstrumented()) {
                if (super.m_log.ifLogVerbose(32768L, 15)) {
                    super.m_log.logVerbose(15, "Ending ClientInteraction with failure: " + super.m_CI.toString());
                }
                super.m_CI.endInteraction(ex.getMessage());
            }
            this.cleanupFailedRequest(ex, str, true);
            throw ex;
        }
        if (null != super.m_CI && super.m_CI.isInstrumented()) {
            if (super.m_log.ifLogVerbose(32768L, 15)) {
                super.m_log.logVerbose(15, "Ending ClientInteraction successfuly: " + super.m_CI.toString());
            }
            super.m_CI.endInteraction(null);
        }
        return null;
    }
    
    public String getSessionID() {
        return this.m_sessionID;
    }
    
    public void _release() throws Open4GLException, SystemErrorException {
        if (this.getSessionPool() == null) {
            throw new Open4GLException(7665970990714723420L, null);
        }
        this.m_persistProc.delete();
        if (this.getSessionMode() == 1) {
            this.getSessionPool().releaseSession(this.m_sessionID);
        }
        super._release();
    }
    
    private PersistentProc runPersistentProcedure(final String s, final String s2, final ParameterSet set, final ResultSetSchema resultSetSchema) throws Open4GLException, RunTime4GLException, SystemErrorException {
        final SessionPool sessionPool;
        if ((sessionPool = this.getSessionPool()) == null) {
            throw new Open4GLException(7665970990714723420L, null);
        }
        String sessionID;
        Session session;
        if (this.getSessionMode() == 0) {
            sessionID = super.getSessionID();
            if (sessionID == null) {
                throw new Open4GLException(7665970990714723420L, null);
            }
            session = sessionPool.getSession(sessionID);
        }
        else {
            session = sessionPool.reserveSession();
            sessionID = session.getSessionID();
        }
        this.m_sessionID = sessionID;
        PersistentProc runPersistentProcedure;
        try {
            if (null != super.m_CI && super.m_CI.isInstrumented()) {
                super.m_CI.beginInteraction(null);
                super.m_CI.analyze(s2);
                if (super.m_log.ifLogVerbose(32768L, 15)) {
                    super.m_log.logVerbose(15, "Creating ClientInteraction: " + super.m_CI.toString());
                }
            }
            runPersistentProcedure = session.runPersistentProcedure(s, s2, set, resultSetSchema);
        }
        catch (Open4GLException ex) {
            if (null != super.m_CI && super.m_CI.isInstrumented()) {
                if (super.m_log.ifLogVerbose(32768L, 15)) {
                    super.m_log.logVerbose(15, "Ending ClientInteraction with failure: " + super.m_CI.toString());
                }
                super.m_CI.endInteraction(ex.getMessage());
            }
            this.cleanupFailedRequest(ex, s2, false);
            throw ex;
        }
        if (null != super.m_CI && super.m_CI.isInstrumented()) {
            if (super.m_log.ifLogVerbose(32768L, 15)) {
                super.m_log.logVerbose(15, "Ending ClientInteraction successfuly: " + super.m_CI.toString());
            }
            super.m_CI.endInteraction(null);
        }
        return runPersistentProcedure;
    }
    
    private void cleanupFailedRequest(final Throwable t, final String s, final boolean b) {
        final SessionPool sessionPool = this.getSessionPool();
        if (sessionPool == null || this.m_sessionID == null) {
            return;
        }
        Session session;
        try {
            session = sessionPool.getSession(this.m_sessionID);
        }
        catch (SessionPool.SessionPoolException ex) {
            return;
        }
        if (session.isConnected()) {
            if (this.getSessionMode() != 1 || b) {
                return;
            }
        }
        try {
            sessionPool.releaseSession(this.m_sessionID);
        }
        catch (Throwable t2) {}
        this.m_sessionID = null;
    }
}
