// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

import com.progress.common.ehnlog.AppLogger;
import com.progress.open4gl.SystemErrorException;
import com.progress.open4gl.Open4GLException;
import com.progress.common.ehnlog.IAppLogger;
import com.progress.message.jcMsg;

public class RqContext implements jcMsg
{
    protected static final long m_notAvailable = 7665970990714723420L;
    private String m_requestID;
    private String m_appName;
    private String m_sessionID;
    private SessionPool m_sessionPool;
    private ParameterSet m_parameterSet;
    private IAppLogger m_log;
    private long m_tsCreated;
    private int m_subsystemID;
    private long m_poolLogEntries;
    private long m_refcntLogEntries;
    private boolean m_isWSA;
    
    public RqContext(final String requestID, final String appName, final SessionPool sessionPool, final String sessionID, final ParameterSet parameterSet, final IAppLogger log) {
        this.m_requestID = requestID;
        this.m_appName = appName;
        this.m_sessionPool = sessionPool;
        this.m_sessionID = sessionID;
        this.m_parameterSet = parameterSet;
        this.m_log = log;
        if (this.m_isWSA && this.m_log.ifLogExtended(2L, 1) && this.m_log.ifLogExtended(8L, 3)) {
            this.m_log.logExtended(this.m_subsystemID, this.m_appName + " : addReference() to " + this.m_sessionPool);
        }
        this.m_sessionPool.addReference();
        this.m_tsCreated = System.currentTimeMillis();
    }
    
    public String getRequestID() {
        return this.m_requestID;
    }
    
    public Session getSession() throws Open4GLException, SystemErrorException {
        if (this.m_sessionPool == null || this.m_sessionID == null) {
            throw new Open4GLException(7665970990714723420L, null);
        }
        return this.m_sessionPool.getSession(this.m_sessionID);
    }
    
    public void _release() throws Open4GLException, SystemErrorException {
        this._release(Thread.currentThread().hashCode());
    }
    
    public void _release(final int n) throws Open4GLException, SystemErrorException {
        if (this.m_sessionPool == null || this.m_sessionID == null) {
            throw new Open4GLException(7665970990714723420L, null);
        }
        this.m_sessionPool.releaseSession(this.m_sessionID);
        this.m_sessionID = null;
        if (this.m_isWSA && this.m_log.ifLogExtended(2L, 1) && this.m_log.ifLogExtended(8L, 3)) {
            this.m_log.logExtended(this.m_subsystemID, this.m_appName + " : deleteReference() to " + this.m_sessionPool);
        }
        this.m_sessionPool.setStreamingState(n, false);
        this.m_sessionPool.deleteReference();
        this.m_sessionPool = null;
    }
    
    public ParameterSet getParameterSet() {
        return this.m_parameterSet;
    }
    
    public String _getConnectionId() throws Open4GLException {
        return this.getSession().getConnectionId();
    }
    
    public boolean _isStreaming() throws Open4GLException {
        return this.getSession().isStreaming();
    }
    
    public String _getReturnValue() throws Open4GLException {
        return this.getSession().getReturnValue();
    }
    
    public long get_tsCreated() {
        return this.m_tsCreated;
    }
    
    private void initializeLogging(final AppLogger appLogger) {
        if (appLogger.getLogContext().getLogContextName().equals("Wsa")) {
            this.m_isWSA = true;
            this.m_subsystemID = 1;
            this.m_poolLogEntries = 2L;
            this.m_refcntLogEntries = 10L;
        }
        else {
            this.m_isWSA = false;
            this.m_subsystemID = 0;
            this.m_poolLogEntries = 0L;
            this.m_refcntLogEntries = 0L;
        }
    }
}
