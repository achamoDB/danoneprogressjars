// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.javaproxy;

import com.progress.open4gl.SDOInterface;
import com.progress.open4gl.ProSQLException;
import com.progress.open4gl.dynamicapi.SDOResultSetImpl;
import com.progress.open4gl.SDOResultSet;
import com.progress.open4gl.SDOParameters;
import com.progress.open4gl.dynamicapi.Session;
import com.progress.open4gl.NetworkException;
import com.progress.open4gl.RunTime4GLException;
import com.progress.open4gl.dynamicapi.ResultSetSchema;
import com.progress.open4gl.dynamicapi.RqContext;
import com.progress.open4gl.dynamicapi.ParameterSet;
import com.progress.open4gl.SystemErrorException;
import com.progress.open4gl.Open4GLException;
import com.progress.open4gl.ConnectException;
import com.progress.open4gl.dynamicapi.IPoolProps;
import com.progress.open4gl.dynamicapi.SessionPool;
import com.progress.common.ehnlog.IAppLogger;
import java.text.DecimalFormat;
import com.progress.open4gl.SDOFactory;
import com.progress.message.jcMsg;

public abstract class ProObject implements jcMsg, SDOFactory
{
    protected static final long m_notAvailable = 7665970990714723420L;
    protected static final long m_wrongProxyVer = 7665970990714723421L;
    protected static final long m_badOutputVal = 7665970990714724358L;
    protected static final long m_alreadyBusy = 7665970990714723366L;
    protected static final String TYPE_AO = "AO";
    protected static final String TYPE_SO = "SO";
    protected static final String TYPE_PO = "PO";
    protected static final String TYPE_SP = "SP";
    private static Object proxyObjCountLock;
    private static int proxyObjCount;
    private static Object reqCountLock;
    private static int reqCount;
    private static DecimalFormat fmt6;
    private String m_appName;
    private IAppLogger m_log;
    private int m_sessionMode;
    private String m_sessionID;
    private SessionPool m_sessionPool;
    private String m_WSAObjectID;
    private long m_tsCreated;
    private long m_tsLastAccessed;
    private int m_refCount;
    protected IPoolProps m_properties;
    private long m_proxyObjectLogEntries;
    private int m_proxyObjectLogIndex;
    private long m_poolLogEntries;
    private int m_poolLogIndex;
    private long m_mgmtLogEntries;
    private int m_mgmtLogIndex;
    private long m_refcntLogEntries;
    private int m_refcntLogIndex;
    private long m_runProcsLogEntries;
    private int m_runProcsLogIndex;
    
    protected static String newRequestID(final IPoolProps poolProps) {
        final String string;
        synchronized (ProObject.reqCountLock) {
            ++ProObject.reqCount;
            string = "<REQ|" + poolProps.getStringProperty("PROGRESS.Session.UUID") + "-" + ProObject.fmt6.format(ProObject.reqCount) + ">";
        }
        return string;
    }
    
    protected ProObject() {
    }
    
    public ProObject(final String appName, final IPoolProps properties, final IAppLogger log, final String s) throws ConnectException, Open4GLException, SystemErrorException {
        this.m_appName = appName;
        this.m_properties = properties;
        this.m_log = log;
        this.m_sessionMode = properties.getIntProperty("PROGRESS.Session.sessionModel");
        this.m_sessionID = null;
        this.m_refCount = 0;
        this.initializeLogging(log);
        this.m_WSAObjectID = this.newObjectID(appName, "AO");
        this.m_sessionPool = SessionPool.createPool(appName, properties, log, s);
        if (this.m_log.ifLogExtended(this.m_proxyObjectLogEntries, this.m_proxyObjectLogIndex) && this.m_log.ifLogExtended(this.m_mgmtLogEntries, this.m_mgmtLogIndex)) {
            this.m_log.logExtended(this.m_proxyObjectLogIndex, this.m_WSAObjectID + " : addReference() to " + this.m_sessionPool);
        }
        this.m_sessionPool.addReference();
        this.m_tsCreated = this.set_tsLastAccessed();
    }
    
    public ProObject(final ProObject proObject, final String s) throws Open4GLException {
        this.m_appName = proObject.getAppName();
        this.m_properties = proObject.getProperties();
        this.m_log = proObject.getLog();
        this.m_sessionMode = proObject.getSessionMode();
        this.m_refCount = 0;
        this.initializeLogging(this.m_log);
        final SessionPool sessionPool = proObject.getSessionPool();
        this.m_sessionPool = sessionPool;
        if (sessionPool == null) {
            throw new Open4GLException(7665970990714723420L, null);
        }
        this.m_WSAObjectID = this.newObjectID(this.m_appName, s);
        if (this.m_log.ifLogExtended(this.m_proxyObjectLogEntries, this.m_proxyObjectLogIndex) && this.m_log.ifLogExtended(this.m_mgmtLogEntries, this.m_mgmtLogIndex)) {
            this.m_log.logExtended(this.m_proxyObjectLogIndex, this.m_WSAObjectID + " : addReference() to " + this.m_sessionPool);
        }
        this.m_sessionPool.addReference();
        this.m_sessionID = proObject.getSessionID();
        this.m_tsCreated = this.set_tsLastAccessed();
    }
    
    public RqContext runProcedure(final String s, final ParameterSet set) throws Open4GLException, RunTime4GLException, SystemErrorException {
        return this.runProcedure(newRequestID(this.m_properties), s, set, null);
    }
    
    public RqContext runProcedure(final String s, final String s2, final ParameterSet set) throws Open4GLException, RunTime4GLException, SystemErrorException {
        return this.runProcedure(s, s2, set, null);
    }
    
    public RqContext runProcedure(final String s, final ParameterSet set, final ResultSetSchema resultSetSchema) throws Open4GLException, RunTime4GLException, SystemErrorException {
        return this.runProcedure(newRequestID(this.m_properties), s, set, resultSetSchema);
    }
    
    public RqContext runProcedure(final String requestId, final String s, final ParameterSet set, final ResultSetSchema resultSetSchema) throws Open4GLException, RunTime4GLException, SystemErrorException {
        String s2 = null;
        if (this.m_log.ifLogExtended(this.m_proxyObjectLogEntries, this.m_proxyObjectLogIndex) && this.m_log.ifLogExtended(this.m_mgmtLogEntries, this.m_mgmtLogIndex)) {
            this.m_log.logExtended(this.m_proxyObjectLogIndex, this.m_WSAObjectID + " : runProcedure(" + s + ")");
        }
        final SessionPool sessionPool;
        if ((sessionPool = this.getSessionPool()) == null) {
            throw new Open4GLException(7665970990714723420L, null);
        }
        this.set_tsLastAccessed();
        Object obj;
        try {
            if (this.getSessionMode() == 0) {
                s2 = this.getSessionID();
                if (s2 == null) {
                    throw new Open4GLException(7665970990714723420L, null);
                }
                sessionPool.getSession(s2).runProcedure(requestId, s, set, resultSetSchema);
                obj = null;
            }
            else {
                if (sessionPool.isStreaming()) {
                    throw new Open4GLException(7665970990714723366L, new Object[] { s });
                }
                Session session = sessionPool.reserveSession();
                s2 = session.getSessionID();
                if (this.m_log.ifLogExtended(this.m_proxyObjectLogEntries, this.m_proxyObjectLogIndex) && this.m_log.ifLogExtended(this.m_mgmtLogEntries, this.m_mgmtLogIndex)) {
                    this.m_log.logExtended(this.m_poolLogIndex, this.m_WSAObjectID + "reserveSession() = " + s2);
                }
                try {
                    session.runProcedure(requestId, s, set, resultSetSchema);
                }
                catch (NetworkException.SendDataException ex) {
                    this.cleanupFailedRequest(ex, s, s2, sessionPool);
                    session = sessionPool.createSession();
                    s2 = session.getSessionID();
                    if (this.m_log.ifLogExtended(this.m_proxyObjectLogEntries, this.m_proxyObjectLogIndex) && this.m_log.ifLogExtended(this.m_mgmtLogEntries, this.m_mgmtLogIndex)) {
                        this.m_log.logExtended(this.m_poolLogIndex, this.m_WSAObjectID + "createeSession() = " + s2);
                    }
                    session.runProcedure(requestId, s, set, resultSetSchema);
                }
                obj = new RqContext(requestId, this.m_appName, sessionPool, s2, set, this.m_log);
                this.m_sessionPool.setConnectionId(session.getConnectionId());
                this.m_sessionPool.setRequestId(requestId);
                this.m_sessionPool.setReturnValue(session.getReturnValue());
                this.m_sessionPool.setStreamingState(session.isStreaming());
                this.m_sessionPool.setSSLSubjectName(session.getSSLSubjectName());
            }
        }
        catch (Open4GLException ex2) {
            this.cleanupFailedRequest(ex2, s, s2, sessionPool);
            throw ex2;
        }
        if (this.m_log.ifLogExtended(this.m_proxyObjectLogEntries, this.m_proxyObjectLogIndex) && this.m_log.ifLogExtended(this.m_runProcsLogEntries, this.m_runProcsLogIndex)) {
            this.m_log.logExtended(this.m_proxyObjectLogIndex, this.m_WSAObjectID + " : runProcedure(" + s + ") returned = " + obj);
        }
        return (RqContext)obj;
    }
    
    public Session getSession() throws SessionPool.SessionPoolException, Open4GLException {
        if (this.m_sessionPool == null) {
            throw new Open4GLException(7665970990714723420L, null);
        }
        return this.m_sessionPool.getSession(this.m_sessionID);
    }
    
    public Session getSession(final String s) throws SessionPool.SessionPoolException, Open4GLException {
        if (this.m_sessionPool == null) {
            throw new Open4GLException(7665970990714723420L, null);
        }
        return this.m_sessionPool.getSession(s);
    }
    
    public void _release() throws Open4GLException, SystemErrorException {
        if (this.m_log.ifLogExtended(this.m_proxyObjectLogEntries, this.m_proxyObjectLogIndex) && this.m_log.ifLogExtended(this.m_refcntLogEntries, this.m_refcntLogIndex)) {
            this.m_log.logExtended(this.m_proxyObjectLogIndex, this.m_WSAObjectID + " : deleteReference() to " + this.m_sessionPool);
        }
        if (this.m_sessionPool == null) {
            throw new Open4GLException(7665970990714723420L, null);
        }
        this.m_sessionPool.deleteReference();
        this.m_sessionPool = null;
        this.m_sessionID = null;
    }
    
    public void shutdown() throws Open4GLException, SystemErrorException {
        if (this.m_log.ifLogExtended(this.m_proxyObjectLogEntries, this.m_proxyObjectLogIndex) && this.m_log.ifLogExtended(this.m_refcntLogEntries, this.m_refcntLogIndex)) {
            this.m_log.logExtended(this.m_proxyObjectLogIndex, this.m_WSAObjectID + " : shutdown() to " + this.m_sessionPool);
        }
        if (this.m_sessionPool == null) {
            throw new Open4GLException(7665970990714723420L, null);
        }
        this.m_sessionPool.shutdown();
        this.m_sessionPool = null;
        this.m_sessionID = null;
    }
    
    public String _getConnectionId() throws Open4GLException {
        if (this.m_sessionPool == null) {
            throw new Open4GLException(7665970990714723420L, null);
        }
        return (this.m_sessionMode == 0) ? this.getSession().getConnectionId() : this.m_sessionPool.getConnectionId();
    }
    
    public boolean _isStreaming() throws Open4GLException {
        if (this.m_sessionPool == null) {
            throw new Open4GLException(7665970990714723420L, null);
        }
        return (this.m_sessionMode == 0) ? this.getSession().isStreaming() : this.m_sessionPool.isStreaming();
    }
    
    public void _cancelAllRequests() throws Open4GLException {
        if (this.m_sessionPool == null) {
            throw new Open4GLException(7665970990714723420L, null);
        }
        if (this.m_sessionMode == 0) {
            this.getSession().setStop();
        }
        else {
            this.m_sessionPool.cancelAllRequests();
        }
    }
    
    public String _getReturnValue() throws Open4GLException {
        if (this.m_sessionPool == null) {
            throw new Open4GLException(7665970990714723420L, null);
        }
        String s;
        if (this.m_sessionMode == 0) {
            s = this.getSession().getReturnValue();
        }
        else {
            s = this.m_sessionPool.getReturnValue();
        }
        return s;
    }
    
    public String _getRequestId() throws Open4GLException {
        if (this.m_sessionPool == null) {
            throw new Open4GLException(7665970990714723420L, null);
        }
        return (this.m_sessionMode == 0) ? this.getSession().getRequestId() : this.m_sessionPool.getRequestId();
    }
    
    public String _getSSLSubjectName() throws Open4GLException {
        if (this.m_sessionPool == null) {
            throw new Open4GLException(7665970990714723420L, null);
        }
        return (this.m_sessionMode == 0) ? this.getSession().getSSLSubjectName() : this.m_sessionPool.getSSLSubjectName();
    }
    
    public String _getReturnValue(final String s) throws Open4GLException {
        final Session session;
        if ((session = this.getSession(s)) == null) {
            throw new Open4GLException(7665970990714723420L, null);
        }
        return session.getReturnValue();
    }
    
    public String _getProcReturnString() throws Open4GLException {
        return this._getReturnValue();
    }
    
    public String getWSAObjectID() {
        return this.m_WSAObjectID;
    }
    
    public String toString() {
        return this.m_WSAObjectID;
    }
    
    public long get_tsCreated() {
        return this.m_tsCreated;
    }
    
    public long get_tsLastAccessed() {
        return this.m_tsLastAccessed;
    }
    
    public long set_tsLastAccessed() {
        return this.m_tsLastAccessed = System.currentTimeMillis();
    }
    
    public synchronized int getRefCount() {
        if (this.m_log.ifLogExtended(this.m_proxyObjectLogEntries, this.m_proxyObjectLogIndex) && this.m_log.ifLogExtended(this.m_refcntLogEntries, this.m_refcntLogIndex)) {
            this.m_log.logExtended(this.m_proxyObjectLogIndex, this.m_WSAObjectID + " : getRefCount() count= " + this.m_refCount);
        }
        return this.m_refCount;
    }
    
    public synchronized int addReference() {
        ++this.m_refCount;
        if (this.m_log.ifLogExtended(this.m_proxyObjectLogEntries, this.m_proxyObjectLogIndex) && this.m_log.ifLogExtended(this.m_refcntLogEntries, this.m_refcntLogIndex)) {
            this.m_log.logExtended(this.m_proxyObjectLogIndex, this.m_WSAObjectID + " : addReference() count= " + this.m_refCount);
        }
        this.set_tsLastAccessed();
        return this.m_refCount;
    }
    
    public synchronized int deleteReference() throws SystemErrorException, Open4GLException {
        if (this.m_refCount == 0) {
            if (this.m_log.ifLogExtended(this.m_proxyObjectLogEntries, this.m_proxyObjectLogIndex) && this.m_log.ifLogExtended(this.m_refcntLogEntries, this.m_refcntLogIndex)) {
                this.m_log.logExtended(this.m_proxyObjectLogIndex, this.m_WSAObjectID + " : deleteReference() error : refCount= " + this.m_refCount);
            }
            throw new SystemErrorException(31L, null);
        }
        --this.m_refCount;
        this.set_tsLastAccessed();
        if (this.m_log.ifLogExtended(this.m_proxyObjectLogEntries, this.m_proxyObjectLogIndex) && this.m_log.ifLogExtended(this.m_refcntLogEntries, this.m_refcntLogIndex)) {
            this.m_log.logExtended(this.m_proxyObjectLogIndex, this.m_WSAObjectID + " : deleteReference() count= " + this.m_refCount);
        }
        if (this.m_refCount == 0 && this.m_log.ifLogExtended(this.m_proxyObjectLogEntries, this.m_proxyObjectLogIndex) && this.m_log.ifLogExtended(this.m_refcntLogEntries, this.m_refcntLogIndex)) {
            this.m_log.logExtended(this.m_proxyObjectLogIndex, this.m_WSAObjectID + " : deleteReference() ... object may be deleted");
        }
        return this.m_refCount;
    }
    
    public SDOResultSet _createSDOResultSet(final String s, final String s2, final String s3, final SDOParameters sdoParameters) throws Open4GLException, ProSQLException {
        if (this.getSessionPool() == null) {
            throw new Open4GLException(7665970990714723420L, null);
        }
        return new SDOResultSetImpl(this, s, s2, s3, sdoParameters);
    }
    
    public SDOInterface _createSDOProcObject(final String s) throws Open4GLException {
        if (this.getSessionPool() == null) {
            throw new Open4GLException(7665970990714725166L, null);
        }
        return new SDOProcObject(this, s);
    }
    
    protected String getAppName() {
        return this.m_appName;
    }
    
    protected IPoolProps getProperties() {
        return this.m_properties;
    }
    
    protected IAppLogger getLog() {
        return this.m_log;
    }
    
    protected int getSessionMode() {
        return this.m_sessionMode;
    }
    
    protected SessionPool getSessionPool() {
        return this.m_sessionPool;
    }
    
    protected String getSessionID() {
        return this.m_sessionID;
    }
    
    protected String setSessionID(final String sessionID) {
        final String sessionID2 = this.m_sessionID;
        this.m_sessionID = sessionID;
        return sessionID2;
    }
    
    protected void finalize() {
        try {
            if (this.getSession() != null) {
                this._release();
            }
        }
        catch (Exception ex) {}
    }
    
    protected boolean isSessionAvailable() {
        if (this.m_sessionMode == 0) {
            if (this.m_sessionPool != null && this.m_sessionID != null) {
                return true;
            }
        }
        else if (this.m_sessionMode == 1 && this.m_sessionPool != null) {
            return true;
        }
        return false;
    }
    
    private String newObjectID(final String str, final String str2) {
        final String string;
        synchronized (ProObject.proxyObjCountLock) {
            ++ProObject.proxyObjCount;
            string = "<" + str + "|" + "PX-" + ProObject.fmt6.format(ProObject.proxyObjCount) + "|" + str2 + ">";
        }
        return string;
    }
    
    private void cleanupFailedRequest(final Throwable obj, final String str, final String str2, final SessionPool obj2) {
        if (this.m_log.ifLogExtended(this.m_proxyObjectLogEntries, this.m_proxyObjectLogIndex) && this.m_log.ifLogExtended(this.m_mgmtLogEntries, this.m_mgmtLogIndex)) {
            this.m_log.logExtended(this.m_proxyObjectLogIndex, this.m_WSAObjectID + " : cleanupFailedRequest() procName= " + str + " exception= " + obj + " sessionID= " + str2 + " sessionPool= " + obj2);
        }
        if (str2 == null || obj2 == null) {
            return;
        }
        Session session;
        try {
            session = obj2.getSession(str2);
        }
        catch (SessionPool.SessionPoolException obj3) {
            if (this.m_log.ifLogExtended(this.m_poolLogEntries, this.m_poolLogIndex) && this.m_log.ifLogExtended(this.m_mgmtLogEntries, this.m_mgmtLogIndex)) {
                this.m_log.logExtended(this.m_poolLogIndex, this.m_WSAObjectID + " : cleanupFailedRequest() : getSession()" + " failed : " + obj3);
            }
            return;
        }
        if (this.getSessionMode() != 1) {
            if (session.isConnected()) {
                return;
            }
        }
        try {
            obj2.releaseSession(str2);
        }
        catch (Throwable obj4) {
            if (this.m_log.ifLogExtended(this.m_poolLogEntries, this.m_poolLogIndex) && this.m_log.ifLogExtended(this.m_mgmtLogEntries, this.m_mgmtLogIndex)) {
                this.m_log.logExtended(this.m_poolLogIndex, this.m_WSAObjectID + " : cleanupFailedRequest() : releaseSession()" + " failed : " + obj4);
            }
        }
        if (str2 == this.m_sessionID) {
            this.m_sessionID = null;
        }
    }
    
    private void initializeLogging(final IAppLogger appLogger) {
        if (appLogger.getLogContext().getLogContextName().equals("O4gl")) {
            this.m_proxyObjectLogIndex = 1;
            this.m_proxyObjectLogEntries = 2L;
            this.m_poolLogIndex = 2;
            this.m_poolLogEntries = 4L;
            this.m_mgmtLogIndex = 4;
            this.m_mgmtLogEntries = 16L;
            this.m_refcntLogIndex = 5;
            this.m_refcntLogEntries = 32L;
            this.m_runProcsLogIndex = 6;
            this.m_runProcsLogEntries = 64L;
        }
        else {
            this.m_proxyObjectLogIndex = 0;
            this.m_proxyObjectLogEntries = 0L;
            this.m_poolLogIndex = 0;
            this.m_poolLogEntries = 0L;
            this.m_mgmtLogIndex = 0;
            this.m_mgmtLogEntries = 0L;
            this.m_refcntLogIndex = 0;
            this.m_refcntLogEntries = 0L;
            this.m_runProcsLogIndex = 0;
            this.m_runProcsLogEntries = 0L;
        }
    }
    
    static {
        ProObject.proxyObjCountLock = new Object();
        ProObject.proxyObjCount = 0;
        ProObject.reqCountLock = new Object();
        ProObject.reqCount = 0;
        ProObject.fmt6 = new DecimalFormat("000000");
    }
}
