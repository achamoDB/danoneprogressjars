// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.javaproxy;

import com.progress.open4gl.dynamicapi.Session;
import com.progress.open4gl.dynamicapi.SessionPool;
import com.progress.open4gl.SystemErrorException;
import com.progress.open4gl.RunTime4GLException;
import com.progress.open4gl.dynamicapi.RqContext;
import com.progress.open4gl.Open4GLException;
import com.progress.open4gl.dynamicapi.ResultSetSchema;
import com.progress.open4gl.dynamicapi.ParameterSet;
import com.progress.open4gl.dynamicapi.PersistentProc;

public class Procedure extends ProObject
{
    protected PersistentProc m_persistProc;
    protected String m_procName;
    private String m_sessionID;
    
    public Procedure(final ProObject proObject, final String s, final ParameterSet set) throws Open4GLException {
        super(proObject, "PO");
        this.m_persistProc = this.runPersistentProcedure(null, s, set, null, 1);
    }
    
    public Procedure(final String s, final ProObject proObject, final String s2, final ParameterSet set) throws Open4GLException {
        super(proObject, "PO");
        this.m_persistProc = this.runPersistentProcedure(s, s2, set, null, 1);
    }
    
    public Procedure(final ProObject proObject, final String s, final ParameterSet set, final ResultSetSchema resultSetSchema) throws Open4GLException {
        super(proObject, "PO");
        this.m_persistProc = this.runPersistentProcedure(null, s, set, resultSetSchema, 1);
    }
    
    public Procedure(final String s, final ProObject proObject, final String s2, final ParameterSet set, final ResultSetSchema resultSetSchema) throws Open4GLException {
        super(proObject, "PO");
        this.m_persistProc = this.runPersistentProcedure(s, s2, set, resultSetSchema, 1);
    }
    
    public Procedure(final ProObject proObject) throws Open4GLException {
        super(proObject, "PO");
    }
    
    public Procedure(final ProObject proObject, final String s) throws Open4GLException {
        super(proObject, s);
    }
    
    public RqContext runProcedure(final String s, final ParameterSet set) throws Open4GLException, RunTime4GLException, SystemErrorException {
        return this.runProcedure(ProObject.newRequestID(super.m_properties), s, set, null);
    }
    
    public RqContext runProcedure(final String s, final String s2, final ParameterSet set) throws Open4GLException, RunTime4GLException, SystemErrorException {
        return this.runProcedure(s, s2, set, null);
    }
    
    public RqContext runProcedure(final String s, final ParameterSet set, final ResultSetSchema resultSetSchema) throws Open4GLException, RunTime4GLException, SystemErrorException {
        return this.runProcedure(ProObject.newRequestID(super.m_properties), s, set, resultSetSchema);
    }
    
    public RqContext runProcedure(final String s, final String s2, final ParameterSet set, final ResultSetSchema resultSetSchema) throws Open4GLException, RunTime4GLException, SystemErrorException {
        if (this.m_persistProc == null) {
            throw new Open4GLException(7665970990714723420L, null);
        }
        try {
            this.m_persistProc.runProcedure(s, s2, set, resultSetSchema);
        }
        catch (Open4GLException ex) {
            this.cleanupFailedRequest(ex, s2);
            throw ex;
        }
        return null;
    }
    
    public String getSessionID() {
        return this.m_sessionID;
    }
    
    public Object getProcId() {
        if (null != this.m_persistProc) {
            return this.m_persistProc.getProcId();
        }
        return null;
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
    
    protected PersistentProc runPersistentProcedure(final String s, final ParameterSet set) throws Open4GLException, RunTime4GLException, SystemErrorException {
        return this.runPersistentProcedure(ProObject.newRequestID(super.m_properties), s, set, null, 1);
    }
    
    protected PersistentProc runPersistentProcedure(final String s, final ParameterSet set, final ResultSetSchema resultSetSchema) throws Open4GLException, RunTime4GLException, SystemErrorException {
        return this.runPersistentProcedure(ProObject.newRequestID(super.m_properties), s, set, resultSetSchema, 1);
    }
    
    protected PersistentProc runPersistentProcedure(final String s, final int n) throws Open4GLException, RunTime4GLException, SystemErrorException {
        return this.runPersistentProcedure(ProObject.newRequestID(super.m_properties), s, null, null, n);
    }
    
    private PersistentProc runPersistentProcedure(final String s, final String s2, final ParameterSet set, final ResultSetSchema resultSetSchema, final int n) throws Open4GLException, RunTime4GLException, SystemErrorException {
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
        PersistentProc persistentProc;
        try {
            if (1 == n) {
                persistentProc = session.runPersistentProcedure(s, s2, set, resultSetSchema);
            }
            else {
                persistentProc = session.runPersistentProcedure(s2, n);
            }
        }
        catch (Open4GLException ex) {
            this.cleanupFailedRequest(ex, s2);
            throw ex;
        }
        return persistentProc;
    }
    
    private void cleanupFailedRequest(final Throwable t, final String s) {
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
        if (!session.isConnected()) {
            try {
                sessionPool.releaseSession(this.m_sessionID);
            }
            catch (Throwable t2) {}
            this.m_sessionID = null;
        }
    }
}
