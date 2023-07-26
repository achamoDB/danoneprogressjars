// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa.open4gl.proxy;

import com.progress.open4gl.dynamicapi.Session;
import com.progress.open4gl.NetworkException;
import java.util.HashMap;
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
import com.progress.wsa.admin.AppRuntimeProps;
import java.text.DecimalFormat;
import com.progress.message.jcMsg;

public abstract class WSAProxyObject implements jcMsg
{
    protected static final long m_notAvailable = 7665970990714723420L;
    protected static final long m_wrongProxyVer = 7665970990714723421L;
    protected static final long m_badOutputVal = 7665970990714724358L;
    protected static final String TYPE_AO = "AO";
    protected static final String TYPE_SO = "SO";
    protected static final String TYPE_PO = "PO";
    protected OEWsaInterceptor m_CI;
    private static Object proxyObjCountLock;
    private static int proxyObjCount;
    private static DecimalFormat fmt6;
    private String m_appName;
    private AppRuntimeProps m_properties;
    protected IAppLogger m_log;
    private int m_sessionMode;
    private String m_sessionID;
    private SessionPool m_sessionPool;
    private String m_WSAObjectID;
    private long m_tsCreated;
    private long m_tsLastAccessed;
    private int m_refCount;
    private String m_returnValue;
    
    private WSAProxyObject() {
        this.m_CI = null;
    }
    
    public WSAProxyObject(final String appName, final AppRuntimeProps properties, final IAppLogger log, final int sessionMode, final String s, final String url, final String s2, final String s3, final String s4) throws ConnectException, Open4GLException, SystemErrorException {
        this.m_CI = null;
        this.m_appName = appName;
        this.m_properties = properties;
        this.m_log = log;
        this.m_sessionMode = sessionMode;
        this.m_sessionID = null;
        this.m_refCount = 0;
        this.m_WSAObjectID = this.newObjectID(appName, "AO");
        (this.m_CI = new OEWsaInterceptor()).setUrl(url);
        this.m_CI.setGroup(properties.getStringProperty("PROGRESS.Session.actionalGroupName"));
        if (this.m_log.ifLogVerbose(32768L, 15)) {
            this.m_log.logVerbose(15, "Actional enabled: " + this.m_CI.toString());
        }
        properties.setProperty("PROGRESS.Session.manifest", this.m_CI);
        this.m_sessionPool = SessionPool.createPool(appName, properties, log, sessionMode, s, url, s2, s3, s4);
        if (this.m_log.ifLogExtended(16L, 4) & this.m_log.ifLogExtended(1024L, 10)) {
            this.m_log.logExtended(4, this.m_WSAObjectID + " : addReference() to " + this.m_sessionPool);
        }
        this.m_sessionPool.addReference();
        this.m_tsCreated = this.set_tsLastAccessed();
    }
    
    public WSAProxyObject(final WSAProxyObject wsaProxyObject, final String s) throws Open4GLException {
        this.m_CI = null;
        this.m_appName = wsaProxyObject.getAppName();
        this.m_properties = wsaProxyObject.getProperties();
        this.m_log = wsaProxyObject.getLog();
        this.m_sessionMode = wsaProxyObject.getSessionMode();
        this.m_refCount = 0;
        final SessionPool sessionPool = wsaProxyObject.getSessionPool();
        this.m_sessionPool = sessionPool;
        if (sessionPool == null) {
            throw new Open4GLException(7665970990714723420L, null);
        }
        this.m_WSAObjectID = this.newObjectID(this.m_appName, s);
        if (this.m_log.ifLogExtended(16L, 4) & this.m_log.ifLogExtended(1024L, 10)) {
            this.m_log.logExtended(4, this.m_WSAObjectID + " : addReference() to " + this.m_sessionPool);
        }
        this.m_sessionPool.addReference();
        this.m_sessionID = wsaProxyObject.getSessionID();
        this.m_tsCreated = this.set_tsLastAccessed();
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
    
    public RqContext runProcedure(final String s, final String s2, final ParameterSet set, final ResultSetSchema resultSetSchema) throws Open4GLException, RunTime4GLException, SystemErrorException {
        String s3 = null;
        if (this.m_log.ifLogExtended(16L, 4) & this.m_log.ifLogExtended(1024L, 10)) {
            this.m_log.logExtended(4, this.m_WSAObjectID + " : runProcedure(" + s2 + ")");
        }
        final SessionPool sessionPool;
        if ((sessionPool = this.getSessionPool()) == null) {
            throw new Open4GLException(7665970990714723420L, null);
        }
        this.set_tsLastAccessed();
        if (null != this.m_CI && this.m_CI.isInstrumented()) {
            this.m_CI.beginInteraction(null);
            this.m_CI.analyze(s2);
            if (this.m_log.ifLogVerbose(32768L, 15)) {
                this.m_log.logVerbose(15, "Creating ClientInteraction: " + this.m_CI.toString());
            }
        }
        Object obj;
        try {
            if (this.getSessionMode() == 0) {
                s3 = this.getSessionID();
                if (s3 == null) {
                    throw new Open4GLException(7665970990714723420L, null);
                }
                sessionPool.getSession(s3).runProcedure(s, s2, set, resultSetSchema);
                obj = null;
            }
            else {
                final Session reserveSession = sessionPool.reserveSession();
                s3 = reserveSession.getSessionID();
                if (this.m_log.ifLogExtended(16L, 4) & this.m_log.ifLogExtended(1024L, 10)) {
                    this.m_log.logExtended(6, this.m_WSAObjectID + "reserveSession() = " + s3);
                }
                try {
                    reserveSession.runProcedure(s, s2, set, resultSetSchema);
                }
                catch (NetworkException.SendDataException ex) {
                    this.cleanupFailedRequest(ex, s2, s3, sessionPool);
                    final Session session = sessionPool.createSession();
                    s3 = session.getSessionID();
                    if (this.m_log.ifLogExtended(16L, 4) && this.m_log.ifLogExtended(1024L, 10)) {
                        this.m_log.logExtended(6, this.m_WSAObjectID + "createeSession() = " + s3);
                    }
                    session.runProcedure(s, s2, set, resultSetSchema);
                }
                obj = new RqContext(s, this.m_appName, sessionPool, s3, set, this.m_log);
            }
        }
        catch (Open4GLException ex2) {
            if (null != this.m_CI && this.m_CI.isInstrumented()) {
                if (this.m_log.ifLogVerbose(32768L, 15)) {
                    this.m_log.logVerbose(15, "Ending ClientInteraction with an error: " + this.m_CI.toString());
                }
                this.m_CI.endInteraction(ex2.getMessage());
            }
            this.cleanupFailedRequest(ex2, s2, s3, sessionPool);
            throw ex2;
        }
        if (this.m_log.ifLogExtended(16L, 4) & this.m_log.ifLogExtended(4096L, 12)) {
            this.m_log.logExtended(4, this.m_WSAObjectID + " : runProcedure(" + s2 + ") returned = " + obj);
        }
        if (null != this.m_CI && this.m_CI.isInstrumented()) {
            if (this.m_log.ifLogVerbose(32768L, 15)) {
                this.m_log.logVerbose(15, "Ending ClientInteraction successfuly: " + this.m_CI.toString());
            }
            this.m_CI.endInteraction(null);
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
        if (this.m_log.ifLogExtended(16L, 4) & this.m_log.ifLogExtended(2048L, 11)) {
            this.m_log.logExtended(4, this.m_WSAObjectID + " : deleteReference() to " + this.m_sessionPool);
        }
        if (null != this.m_CI && this.m_CI.isInstrumented()) {
            this.m_CI.beginInteraction(null);
            this.m_CI.analyze("_release");
            if (this.m_log.ifLogVerbose(32768L, 15)) {
                this.m_log.logVerbose(15, "Creating ClientInteraction: " + this.m_CI.toString());
            }
        }
        if (this.m_sessionPool == null) {
            throw new Open4GLException(7665970990714723420L, null);
        }
        this.m_sessionPool.deleteReference();
        this.m_sessionPool = null;
        this.m_sessionID = null;
        if (null != this.m_CI && this.m_CI.isInstrumented()) {
            if (this.m_log.ifLogVerbose(32768L, 15)) {
                this.m_log.logVerbose(15, "Ending ClientInteraction successfuly: " + this.m_CI.toString());
            }
            this.m_CI.endInteraction(null);
        }
    }
    
    public void shutdown() throws Open4GLException, SystemErrorException {
        if (this.m_log.ifLogExtended(16L, 4) & this.m_log.ifLogExtended(2048L, 11)) {
            this.m_log.logExtended(4, this.m_WSAObjectID + " : shutdown() to " + this.m_sessionPool);
        }
        if (this.m_sessionPool == null) {
            throw new Open4GLException(7665970990714723420L, null);
        }
        this.m_sessionPool.shutdown();
        this.m_sessionPool = null;
        this.m_sessionID = null;
    }
    
    public String _getConnectionId() throws Open4GLException {
        if (this.getSession() == null) {
            throw new Open4GLException(7665970990714723420L, null);
        }
        return this.getSession().getConnectionId();
    }
    
    public boolean _isStreaming() throws Open4GLException {
        final Session session;
        if ((session = this.getSession()) == null) {
            throw new Open4GLException(7665970990714723420L, null);
        }
        return session.isStreaming();
    }
    
    public void _cancelAllRequests() throws Open4GLException {
        final Session session;
        if ((session = this.getSession()) == null) {
            throw new Open4GLException(7665970990714723420L, null);
        }
        session.setStop();
    }
    
    public String _getReturnValue() throws Open4GLException {
        final Session session;
        if ((session = this.getSession()) == null) {
            throw new Open4GLException(7665970990714723420L, null);
        }
        return session.getReturnValue();
    }
    
    public String _getReturnValue(final String s) throws Open4GLException {
        final Session session;
        if ((session = this.getSession(s)) == null) {
            throw new Open4GLException(7665970990714723420L, null);
        }
        return session.getReturnValue();
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
        if (this.m_log.ifLogExtended(16L, 4) & this.m_log.ifLogExtended(2048L, 11)) {
            this.m_log.logExtended(4, this.m_WSAObjectID + " : getRefCount() count= " + this.m_refCount);
        }
        return this.m_refCount;
    }
    
    public synchronized int addReference() {
        ++this.m_refCount;
        if (this.m_log.ifLogExtended(16L, 4) & this.m_log.ifLogExtended(2048L, 11)) {
            this.m_log.logExtended(4, this.m_WSAObjectID + " : addReference() count= " + this.m_refCount);
        }
        this.set_tsLastAccessed();
        return this.m_refCount;
    }
    
    public synchronized int deleteReference() throws SystemErrorException, Open4GLException {
        if (this.m_refCount == 0) {
            if (this.m_log.ifLogExtended(16L, 4) & this.m_log.ifLogExtended(2048L, 11)) {
                this.m_log.logExtended(4, this.m_WSAObjectID + " : deleteReference() error : refCount= " + this.m_refCount);
            }
            throw new SystemErrorException(31L, null);
        }
        --this.m_refCount;
        this.set_tsLastAccessed();
        if (this.m_log.ifLogExtended(16L, 4) & this.m_log.ifLogExtended(2048L, 11)) {
            this.m_log.logExtended(4, this.m_WSAObjectID + " : deleteReference() count= " + this.m_refCount);
        }
        if (this.m_refCount == 0 && (this.m_log.ifLogExtended(16L, 4) & this.m_log.ifLogExtended(2048L, 11))) {
            this.m_log.logExtended(4, this.m_WSAObjectID + " : deleteReference() ... object may be deleted");
        }
        return this.m_refCount;
    }
    
    public String getReturnValue() {
        return this.m_returnValue;
    }
    
    public void setReturnValue(final String returnValue) {
        this.m_returnValue = returnValue;
    }
    
    protected String getAppName() {
        return this.m_appName;
    }
    
    protected AppRuntimeProps getProperties() {
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
    
    private String newObjectID(final String str, final String str2) {
        final String string;
        synchronized (WSAProxyObject.proxyObjCountLock) {
            ++WSAProxyObject.proxyObjCount;
            string = "<" + str + "|" + "PX-" + WSAProxyObject.fmt6.format(WSAProxyObject.proxyObjCount) + "|" + str2 + ">";
        }
        return string;
    }
    
    private void cleanupFailedRequest(final Throwable obj, final String str, final String str2, final SessionPool obj2) {
        if (this.m_log.ifLogExtended(16L, 4) & this.m_log.ifLogExtended(1024L, 10)) {
            this.m_log.logExtended(4, this.m_WSAObjectID + " : cleanupFailedRequest() procName= " + str + " exception= " + obj + " sessionID= " + str2 + " sessionPool= " + obj2);
        }
        if (str2 == null || obj2 == null) {
            return;
        }
        Session session;
        try {
            session = obj2.getSession(str2);
        }
        catch (SessionPool.SessionPoolException obj3) {
            if (this.m_log.ifLogExtended(64L, 6) & this.m_log.ifLogExtended(1024L, 10)) {
                this.m_log.logExtended(6, this.m_WSAObjectID + " : cleanupFailedRequest() : getSession()" + " failed : " + obj3);
            }
            return;
        }
        if (session.isConnected()) {
            if (this.getSessionMode() != 1) {
                return;
            }
        }
        try {
            obj2.releaseSession(str2);
        }
        catch (Throwable obj4) {
            if (this.m_log.ifLogExtended(64L, 6) & this.m_log.ifLogExtended(1024L, 10)) {
                this.m_log.logExtended(6, this.m_WSAObjectID + " : cleanupFailedRequest() : releaseSession()" + " failed : " + obj4);
            }
        }
        if (str2 == this.m_sessionID) {
            this.m_sessionID = null;
        }
    }
    
    static {
        WSAProxyObject.proxyObjCountLock = new Object();
        WSAProxyObject.proxyObjCount = 0;
        WSAProxyObject.fmt6 = new DecimalFormat("000000");
    }
}
