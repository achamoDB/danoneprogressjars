// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

import com.progress.common.ehnlog.AppLogger;
import com.progress.common.util.WatchDog;
import com.progress.common.util.IWatchable;
import java.util.Vector;
import com.progress.common.message.IProMessage;
import com.progress.common.exception.ExceptionMessageAdapter;
import com.progress.common.util.PromsgsBundle;
import java.net.MalformedURLException;
import com.progress.open4gl.BadURLException;
import com.progress.common.util.PscURLParser;
import java.util.Enumeration;
import com.progress.nameserver.client.NameServerClient;
import com.progress.open4gl.SystemErrorException;
import com.progress.open4gl.Open4GLException;
import com.progress.open4gl.ConnectException;
import java.util.Hashtable;
import com.progress.common.ehnlog.IAppLogger;
import java.text.DecimalFormat;
import com.progress.message.jcMsg;

public class SessionPool extends ObjectPool implements jcMsg
{
    public static final int STATE_INIT = 0;
    public static final int STATE_AVAILABLE = 1;
    public static final int STATE_BUSY = 2;
    public static final int STATE_DONE = 3;
    private static final int SM_MIN_SESSIONS = 1;
    private static final int SM_MAX_SESSIONS = 1;
    private static final int SM_INITIAL_SESSIONS = 1;
    private static final String SERVERTYPE_APPSERVER = "AS";
    private static final boolean REFRESH_LIST = true;
    private static final boolean NO_REFRESH_LIST = false;
    private static final boolean RESERVE_SESSION = false;
    private static final boolean CREATE_SESSION = true;
    private static Object sessionPoolCountLock;
    private static int sessionPoolCount;
    private static DecimalFormat fmt6;
    private String m_appName;
    private IPoolProps m_properties;
    private IAppLogger m_log;
    private String m_poolName;
    private Hashtable m_brokerBySessionID;
    private Hashtable m_brokerByBrokerUUID;
    private int m_sessionMode;
    private String m_requestID;
    private String m_url;
    private String m_userId;
    private String m_password;
    private String m_clientInfo;
    private int m_refCount;
    private String m_sectionName;
    private PickList m_pickList;
    private BrokerSessionList m_dcBroker;
    private int m_numBusy;
    private int m_maxBusy;
    private IdleSessionWatchdog m_idleSessionWatchdog;
    private int m_subsystemID;
    private long m_poolLogEntries;
    private long m_mgmtLogEntries;
    private long m_refcntLogEntries;
    private long m_watchdogLogEntries;
    private long m_debugLogEntries;
    private int m_mgmtLogIndex;
    private int m_refcntLogIndex;
    private int m_watchdogLogIndex;
    private int m_debugLogIndex;
    private ThreadLocal m_connectionId;
    private ThreadLocal m_requestId;
    private ThreadLocal m_returnValue;
    private ThreadData m_isStreaming;
    private ThreadLocal m_sslSubjectName;
    
    public static SessionPool createPool(final String str, final IPoolProps poolProps, final IAppLogger appLogger, final String s) throws ConnectException, Open4GLException, SystemErrorException {
        final SessionPool sessionPool = poolProps.getSessionPool();
        if (sessionPool != null) {
            return sessionPool;
        }
        final String string;
        synchronized (SessionPool.sessionPoolCountLock) {
            ++SessionPool.sessionPoolCount;
            string = "{" + str + "}[SP-" + SessionPool.fmt6.format(SessionPool.sessionPoolCount) + "]";
        }
        final SessionPool sessionPool2 = new SessionPool(str, poolProps, appLogger, string, s);
        poolProps.addReference(sessionPool2);
        return sessionPool2;
    }
    
    public static SessionPool createPool(final String str, final IPoolProps poolProps, final IAppLogger appLogger, final int n, final String s, final String s2, final String s3, final String s4, final String s5) throws ConnectException, Open4GLException, SystemErrorException {
        final String string;
        synchronized (SessionPool.sessionPoolCountLock) {
            ++SessionPool.sessionPoolCount;
            string = "{" + str + "}[SP-" + SessionPool.fmt6.format(SessionPool.sessionPoolCount) + "]";
        }
        return new SessionPool(str, poolProps, appLogger, string, n, s, s2, s3, s4, s5);
    }
    
    private SessionPool(final String appName, final IPoolProps properties, final IAppLogger log, final String s, final String requestID) throws ConnectException, Open4GLException, SystemErrorException {
        super(log);
        this.m_appName = appName;
        this.m_properties = properties;
        this.m_log = log;
        this.m_poolName = s;
        this.m_requestID = requestID;
        this.m_sessionMode = properties.getIntProperty("PROGRESS.Session.sessionModel");
        this.m_url = properties.getStringProperty("PROGRESS.Session.url");
        this.m_userId = properties.getStringProperty("PROGRESS.Session.userId");
        this.m_password = properties.getStringProperty("PROGRESS.Session.password");
        this.m_clientInfo = properties.getStringProperty("PROGRESS.Session.appServerInfo");
        this.m_numBusy = 0;
        this.m_maxBusy = 0;
        this.m_brokerBySessionID = new Hashtable();
        this.m_brokerByBrokerUUID = new Hashtable();
        this.initThreadLocals();
        this.initializeLogging(log);
        this.m_properties.setStringProperty("PROGRESS.Session.poolName", this.m_poolName);
        if (this.isDirectConnect()) {
            this.m_pickList = null;
            this.m_dcBroker = new BrokerSessionList(this.m_url, this.dcURL(this.m_url), this.m_log);
            this.m_brokerByBrokerUUID.put(this.m_url, this.m_dcBroker);
        }
        else if (this.m_sessionMode == 0) {
            final PickList list = new PickList(this.m_url);
            final NameServerClient.Broker nextBroker = list.nextBroker(true);
            final String uuid = nextBroker.getUUID();
            this.m_dcBroker = new BrokerSessionList(uuid, this.dcURL(list.getProtocol(), nextBroker), this.m_log);
            this.m_brokerByBrokerUUID.put(uuid, this.m_dcBroker);
            this.m_pickList = null;
        }
        else {
            this.m_pickList = new PickList(this.m_url);
            this.m_dcBroker = null;
        }
        if (this.ifLoggingSessionPool(4) && this.m_log.ifLogExtended(this.m_mgmtLogEntries, this.m_mgmtLogIndex)) {
            this.m_log.logExtended(this.m_subsystemID, s + " sessionpool created");
        }
        this.initializePool();
        this.m_refCount = 0;
        this.m_idleSessionWatchdog = new IdleSessionWatchdog();
    }
    
    private SessionPool(final String appName, final IPoolProps properties, final IAppLogger log, final String s, final int sessionMode, final String requestID, final String s2, final String userId, final String password, final String clientInfo) throws ConnectException, Open4GLException, SystemErrorException {
        super(log);
        this.m_appName = appName;
        this.m_properties = properties;
        this.m_log = log;
        this.m_poolName = s;
        this.m_sessionMode = sessionMode;
        this.m_requestID = requestID;
        this.m_url = s2;
        this.m_userId = userId;
        this.m_password = password;
        this.m_clientInfo = clientInfo;
        this.m_numBusy = 0;
        this.m_maxBusy = 0;
        this.m_brokerBySessionID = new Hashtable();
        this.m_brokerByBrokerUUID = new Hashtable();
        this.initThreadLocals();
        this.initializeLogging(log);
        this.m_properties.setStringProperty("PROGRESS.Session.poolName", this.m_poolName);
        if (this.isDirectConnect()) {
            this.m_pickList = null;
            this.m_dcBroker = new BrokerSessionList(s2, this.dcURL(s2), this.m_log);
            this.m_brokerByBrokerUUID.put(s2, this.m_dcBroker);
        }
        else if (sessionMode == 0) {
            final PickList list = new PickList(s2);
            final NameServerClient.Broker nextBroker = list.nextBroker(true);
            final String uuid = nextBroker.getUUID();
            this.m_dcBroker = new BrokerSessionList(uuid, this.dcURL(list.getProtocol(), nextBroker), this.m_log);
            this.m_brokerByBrokerUUID.put(uuid, this.m_dcBroker);
            this.m_pickList = null;
        }
        else {
            this.m_pickList = new PickList(s2);
            this.m_dcBroker = null;
        }
        if (this.ifLoggingSessionPool(4) && this.m_log.ifLogExtended(this.m_mgmtLogEntries, this.m_mgmtLogIndex)) {
            this.m_log.logExtended(this.m_subsystemID, s + " sessionpool created");
        }
        this.initializePool();
        this.m_refCount = 0;
        this.m_idleSessionWatchdog = new IdleSessionWatchdog();
    }
    
    public Object findAvailableObject() {
        return this.findSession();
    }
    
    public void makeObjectAvailable(final Object o) {
        final Session session = (Session)o;
        if (this.ifLoggingSessionPool(4) && this.m_log.ifLogExtended(this.m_mgmtLogEntries, this.m_mgmtLogIndex)) {
            this.m_log.logExtended(this.m_subsystemID, this.m_poolName + "[" + this.m_url + "]" + " : makeObjectAvailable(" + session.getSessionID() + ")");
        }
        if (session.getPoolState() == 2) {
            --this.m_numBusy;
        }
        session.setPoolState(1);
    }
    
    public synchronized Session getSession(final String s) throws SessionPoolException {
        return this.getFromSessionTable(s);
    }
    
    public Session createSession() throws SessionPoolException, ConnectException {
        return this.reserveSession(true, this.m_pickList, true);
    }
    
    public Session reserveSession() throws SessionPoolException, ConnectException {
        this.startupMinSessions();
        return this.reserveSession(false, this.m_pickList, true);
    }
    
    public synchronized void removeSession(final String str) throws SessionPoolException, Open4GLException {
        final BrokerSessionList list = this.m_brokerBySessionID.get(str);
        if (list == null) {
            if (this.ifLoggingSessionPool(4) && this.m_log.ifLogExtended(this.m_mgmtLogEntries, this.m_mgmtLogIndex)) {
                this.m_log.logExtended(this.m_subsystemID, this.m_poolName + " : removeSession(" + str + ") FAILED : broker not found");
            }
            throw new SessionNotFoundException(str);
        }
        list.removeSession(str);
        if (this.ifLoggingSessionPool(4) && this.m_log.ifLogExtended(this.m_mgmtLogEntries, this.m_mgmtLogIndex)) {
            this.m_log.logExtended(this.m_subsystemID, this.m_poolName + " : removeSession(" + str + ")");
        }
    }
    
    public synchronized void releaseSession(final String s) throws SessionPoolException, Open4GLException {
        final long currentTimeMillis = System.currentTimeMillis();
        final BrokerSessionList list = this.m_brokerBySessionID.get(s);
        final Session session = list.getSession(s);
        if (!session.isConnected() || currentTimeMillis - session.tsCreated() > this.connectionLifetime()) {
            this.removeSession(s);
        }
        else {
            list.releaseSession(s);
            if (this.ifLoggingSessionPool(4) && this.m_log.ifLogExtended(this.m_mgmtLogEntries, this.m_mgmtLogIndex)) {
                this.m_log.logExtended(this.m_subsystemID, this.m_poolName + " : releaseSession(" + s + ")");
            }
        }
    }
    
    public synchronized void addReference() {
        ++this.m_refCount;
        if (this.ifLoggingSessionPool(4) && this.m_log.ifLogExtended(this.m_refcntLogEntries, this.m_refcntLogIndex)) {
            this.m_log.logExtended(this.m_subsystemID, this.m_poolName + " : addReference() count= " + this.m_refCount);
        }
    }
    
    public synchronized void deleteReference() throws SystemErrorException, Open4GLException {
        if (this.m_refCount == 0) {
            if (this.ifLoggingSessionPool(4) && this.m_log.ifLogExtended(this.m_refcntLogEntries, this.m_refcntLogIndex)) {
                this.m_log.logExtended(this.m_subsystemID, this.m_poolName + " : deleteReference() error : refCount already 0");
            }
            throw new SystemErrorException(31L, null);
        }
        --this.m_refCount;
        if (this.ifLoggingSessionPool(4) && this.m_log.ifLogExtended(this.m_refcntLogEntries, this.m_refcntLogIndex)) {
            this.m_log.logExtended(this.m_subsystemID, this.m_poolName + " : deleteReference() count= " + this.m_refCount);
        }
        if (this.m_refCount == 0) {
            if (this.ifLoggingSessionPool(4) && this.m_log.ifLogExtended(this.m_refcntLogEntries, this.m_refcntLogIndex)) {
                this.m_log.logExtended(this.m_subsystemID, this.m_poolName + " : deleteReference() removing all sessions");
            }
            this.removeAll();
        }
    }
    
    public int size() {
        return this.m_brokerBySessionID.size();
    }
    
    public String toString() {
        return this.m_poolName;
    }
    
    public int maxBusy() {
        int maxBusy;
        if (this.m_dcBroker == null) {
            maxBusy = 0;
            final Enumeration<BrokerSessionList> elements = this.m_brokerByBrokerUUID.elements();
            while (elements.hasMoreElements()) {
                maxBusy += elements.nextElement().maxBusy();
            }
        }
        else {
            maxBusy = this.m_dcBroker.maxBusy();
        }
        return maxBusy;
    }
    
    public int resetMaxBusy() {
        int resetMaxBusy;
        if (this.m_dcBroker == null) {
            resetMaxBusy = 0;
            final Enumeration<BrokerSessionList> elements = this.m_brokerByBrokerUUID.elements();
            while (elements.hasMoreElements()) {
                resetMaxBusy += elements.nextElement().resetMaxBusy();
            }
        }
        else {
            resetMaxBusy = this.m_dcBroker.resetMaxBusy();
        }
        return resetMaxBusy;
    }
    
    public int availableSessions() {
        int availableSessions;
        if (this.m_dcBroker == null) {
            availableSessions = 0;
            final Enumeration<BrokerSessionList> elements = this.m_brokerByBrokerUUID.elements();
            while (elements.hasMoreElements()) {
                availableSessions += elements.nextElement().availableSessions();
            }
        }
        else {
            availableSessions = this.m_dcBroker.availableSessions();
        }
        return availableSessions;
    }
    
    public synchronized void shutdown() throws SystemErrorException, Open4GLException {
        if (this.ifLoggingSessionPool(4) && this.m_log.ifLogExtended(this.m_mgmtLogEntries, this.m_mgmtLogIndex)) {
            this.m_log.logExtended(this.m_subsystemID, this.m_poolName + " shutdown()");
        }
        this.m_idleSessionWatchdog.stop();
        if (this.m_dcBroker == null) {
            final Enumeration<BrokerSessionList> elements = this.m_brokerByBrokerUUID.elements();
            while (elements.hasMoreElements()) {
                elements.nextElement().shutdown();
            }
        }
        else {
            this.m_dcBroker.shutdown();
        }
    }
    
    public synchronized void cancelAllRequests() throws SystemErrorException, Open4GLException {
        if (this.ifLoggingSessionPool(4) && this.m_log.ifLogExtended(this.m_mgmtLogEntries, this.m_mgmtLogIndex)) {
            this.m_log.logExtended(this.m_subsystemID, this.m_poolName + " cancelAllRequests()");
        }
        super.cancelAllWaiters();
        if (this.m_dcBroker == null) {
            final Enumeration<BrokerSessionList> elements = this.m_brokerByBrokerUUID.elements();
            while (elements.hasMoreElements()) {
                elements.nextElement().cancelAllRequests();
            }
        }
        else {
            this.m_dcBroker.cancelAllRequests();
        }
    }
    
    public synchronized void enumerate(final String str) throws SystemErrorException, Open4GLException {
        if (this.ifLoggingSessionPool(4) && this.m_log.ifLogExtended(this.m_mgmtLogEntries, this.m_mgmtLogIndex)) {
            this.m_log.logExtended(this.m_subsystemID, this.m_poolName + str);
        }
        if (this.m_dcBroker == null) {
            final Enumeration<BrokerSessionList> elements = this.m_brokerByBrokerUUID.elements();
            while (elements.hasMoreElements()) {
                final BrokerSessionList list = elements.nextElement();
                list.enumerate(list.toString());
            }
        }
        else {
            this.m_dcBroker.enumerate(this.m_dcBroker.toString());
        }
    }
    
    public synchronized String getConnectionId() {
        return this.m_connectionId.get();
    }
    
    public synchronized void setConnectionId(final String value) {
        this.m_connectionId.set(value);
    }
    
    public synchronized String getRequestId() {
        return this.m_requestId.get();
    }
    
    public synchronized void setRequestId(final String value) {
        this.m_requestId.set(value);
    }
    
    public synchronized String getReturnValue() {
        return this.m_returnValue.get();
    }
    
    public synchronized void setReturnValue(final String value) {
        this.m_returnValue.set(value);
    }
    
    public synchronized boolean isStreaming() {
        final Boolean b = (Boolean)this.m_isStreaming.get();
        return b != null && b;
    }
    
    public synchronized void setStreamingState(final boolean value) {
        this.m_isStreaming.set(new Boolean(value));
    }
    
    public synchronized void setStreamingState(final int n, final boolean value) {
        this.m_isStreaming.set(n, new Boolean(value));
    }
    
    public synchronized String getSSLSubjectName() {
        return this.m_sslSubjectName.get();
    }
    
    public synchronized void setSSLSubjectName(final String value) {
        this.m_sslSubjectName.set(value);
    }
    
    private boolean isDirectConnect() throws ConnectException {
        PscURLParser pscURLParser;
        try {
            pscURLParser = new PscURLParser(this.m_url);
        }
        catch (MalformedURLException ex) {
            throw new BadURLException(this.m_url);
        }
        final String trim = pscURLParser.getScheme().trim();
        return trim.equalsIgnoreCase("appServerDC") || trim.equalsIgnoreCase("appServerDCS") || trim.equalsIgnoreCase("http") || trim.equalsIgnoreCase("https");
    }
    
    private String dcURL(final String s) throws ConnectException {
        PscURLParser pscURLParser;
        try {
            pscURLParser = new PscURLParser(s);
        }
        catch (MalformedURLException ex) {
            throw new BadURLException(s);
        }
        final String scheme = pscURLParser.getScheme();
        String s2;
        if (scheme.equalsIgnoreCase("appserver")) {
            s2 = "AppServerDC" + "://" + pscURLParser.getHost() + ":" + pscURLParser.getPort() + "/";
        }
        else if (scheme.equalsIgnoreCase("appservers")) {
            s2 = "AppServerDCS" + "://" + pscURLParser.getHost() + ":" + pscURLParser.getPort() + "/";
        }
        else {
            s2 = s;
        }
        return s2;
    }
    
    private String dcURL(String str, final NameServerClient.Broker broker) {
        if (str.equalsIgnoreCase("appserver")) {
            str = "AppServerDC";
        }
        else if (str.equalsIgnoreCase("appservers")) {
            str = "AppServerDCS";
        }
        return str + "://" + broker.getHost() + ":" + broker.getPort() + "/";
    }
    
    private String appServiceProtocol() {
        return (String)this.m_properties.getProperty("PROGRESS.Session.appServiceProtocol");
    }
    
    private int minSessions() {
        return (this.m_sessionMode == 0) ? 1 : this.m_properties.getIntProperty("PROGRESS.Session.minConnections");
    }
    
    private int maxSessions() {
        int n = (this.m_sessionMode == 0) ? 1 : this.m_properties.getIntProperty("PROGRESS.Session.maxConnections");
        if (n == 0) {
            n = Integer.MAX_VALUE;
        }
        return n;
    }
    
    private int initialSessions() {
        return (this.m_sessionMode == 0) ? 1 : this.m_properties.getIntProperty("PROGRESS.Session.initialConnections");
    }
    
    private int minIdleSessions() {
        return this.m_properties.getIntProperty("PROGRESS.Session.minIdleConnections");
    }
    
    private long requestWaitTimeout() {
        return this.m_properties.getLongProperty("PROGRESS.Session.requestWaitTimeout");
    }
    
    private int nsClientMinPort() {
        return this.m_properties.getIntProperty("PROGRESS.Session.nsClientMinPort");
    }
    
    private int nsClientMaxPort() {
        return this.m_properties.getIntProperty("PROGRESS.Session.nsClientMaxPort");
    }
    
    private int nsClientRetry() {
        return this.m_properties.getIntProperty("PROGRESS.Session.nsClientPortRetry");
    }
    
    private int nsClientRetryInterval() {
        return this.m_properties.getIntProperty("PROGRESS.Session.nsClientPortRetryInterval");
    }
    
    private int nsClientPickListSize() {
        return this.m_properties.getIntProperty("PROGRESS.Session.nsClientPicklistSize");
    }
    
    private int nsClientPickListExpiration() {
        return this.m_properties.getIntProperty("PROGRESS.Session.nsClientPicklistExpiration");
    }
    
    private long connectionLifetime() {
        final long longProperty = this.m_properties.getLongProperty("PROGRESS.Session.connectionLifetime");
        return (longProperty == 0L) ? Long.MAX_VALUE : (1000L * longProperty);
    }
    
    private long idleSessionTimeout() {
        return this.m_properties.getLongProperty("PROGRESS.Session.idleConnectionTimeout") * 1000L;
    }
    
    private int waitIfBusy() {
        return this.m_properties.getIntProperty("PROGRESS.Session.waitIfBusy");
    }
    
    private void startupMinSessions() {
        try {
            boolean b;
            do {
                synchronized (this) {
                    final int size = this.size();
                    final int availableSessions = this.availableSessions();
                    b = (size < this.maxSessions() && (size < this.minSessions() || availableSessions < this.minIdleSessions()));
                }
                if (b) {
                    this.releaseSession(this.createSession().getSessionID());
                }
            } while (b);
        }
        catch (Exception obj) {
            if (this.ifLoggingSessionPool(4) && this.m_log.ifLogExtended(this.m_mgmtLogEntries, this.m_mgmtLogIndex)) {
                this.m_log.logExtended(this.m_subsystemID, this.m_poolName + " unable to start min sessions : " + obj);
            }
        }
    }
    
    private Session reserveSession(final boolean b, final PickList list, final boolean b2) throws SessionPoolException, ConnectException {
        NameServerClient.Broker nextBroker = null;
        if (this.ifLoggingSessionPool(4) && this.m_log.ifLogExtended(this.m_mgmtLogEntries, this.m_mgmtLogIndex)) {
            this.m_log.logExtended(this.m_subsystemID, this.m_poolName + " reserveSession()");
        }
        Session session;
        if (this.m_dcBroker == null) {
            try {
                nextBroker = list.nextBroker(b2);
                final String uuid = nextBroker.getUUID();
                BrokerSessionList value;
                synchronized (this) {
                    if (this.m_brokerByBrokerUUID.containsKey(uuid)) {
                        value = this.m_brokerByBrokerUUID.get(uuid);
                    }
                    else {
                        value = new BrokerSessionList(uuid, this.dcURL(list.getProtocol(), nextBroker), this.m_log);
                        this.m_brokerByBrokerUUID.put(uuid, value);
                    }
                }
                session = value.reserveSession(b);
            }
            catch (NoAvailableSessionsException ex) {
                PickList list2;
                if (b2) {
                    list2 = new PickList(this.m_url);
                    list2.nextBroker(true);
                }
                else {
                    list2 = list;
                }
                list2.removeBroker(nextBroker);
                if (list2.isEmpty()) {
                    throw ex;
                }
                session = this.reserveSession(b, list2, false);
                list.resetList();
            }
        }
        else {
            session = this.m_dcBroker.reserveSession(b);
            this.m_brokerBySessionID.put(session.getSessionID(), this.m_dcBroker);
        }
        if (this.ifLoggingSessionPool(4) && this.m_log.ifLogExtended(this.m_mgmtLogEntries, this.m_mgmtLogIndex)) {
            this.m_log.logExtended(this.m_subsystemID, this.m_poolName + " : reserved(" + session.getSessionID() + ")");
        }
        return session;
    }
    
    private void removeAvailableSession() throws SessionPoolException, ConnectException, Open4GLException {
        if (this.ifLoggingSessionPool(4) && this.m_log.ifLogExtended(this.m_mgmtLogEntries, this.m_mgmtLogIndex)) {
            this.m_log.logExtended(this.m_subsystemID, this.m_poolName + " removeAvailableSession()");
        }
        if (this.m_dcBroker == null) {
            synchronized (this) {
                final PickList list = new PickList(this.m_url);
                for (NameServerClient.Broker broker = list.nextBroker(true); broker != null; broker = list.nextBroker(false)) {
                    try {
                        final String uuid = broker.getUUID();
                        if (this.m_brokerByBrokerUUID.containsKey(uuid)) {
                            ((BrokerSessionList)this.m_brokerByBrokerUUID.get(uuid)).removeAvailableSession();
                            return;
                        }
                    }
                    catch (NoAvailableSessionsException ex) {
                        list.removeBroker(broker);
                    }
                }
                throw new NoAvailableSessionsException("no sessions");
            }
        }
        this.m_dcBroker.removeAvailableSession();
    }
    
    private void removeAll() {
        final Enumeration<BrokerSessionList> elements = this.m_brokerByBrokerUUID.elements();
        while (elements.hasMoreElements()) {
            elements.nextElement().release();
        }
        this.m_brokerByBrokerUUID.clear();
        this.m_brokerBySessionID.clear();
        if (this.ifLoggingSessionPool(4) && this.m_log.ifLogExtended(this.m_mgmtLogEntries, this.m_mgmtLogIndex)) {
            this.m_log.logExtended(this.m_subsystemID, this.m_poolName + " : removeAll() : removed all sessions from pool");
        }
    }
    
    private void removeExpiredSessions() {
        final long currentTimeMillis = System.currentTimeMillis();
        final Enumeration<String> keys = this.m_brokerBySessionID.keys();
        while (keys.hasMoreElements()) {
            try {
                final String s = keys.nextElement();
                final Session fromSessionTable = this.getFromSessionTable(s);
                if (currentTimeMillis - fromSessionTable.tsCreated() <= this.connectionLifetime()) {
                    continue;
                }
                synchronized (this) {
                    if (fromSessionTable.getPoolState() != 1) {
                        continue;
                    }
                    this.removeSession(s);
                    if (!this.ifLoggingSessionPool(2) || !this.m_log.ifLogBasic(this.m_mgmtLogEntries, this.m_mgmtLogIndex)) {
                        continue;
                    }
                    this.m_log.logBasic(this.m_subsystemID, this.m_poolName + " : expired session " + fromSessionTable.getSessionID() + " removed.");
                }
            }
            catch (SessionPoolException obj) {
                if (!this.ifLoggingSessionPool(2) || !this.m_log.ifLogBasic(this.m_mgmtLogEntries, this.m_mgmtLogIndex)) {
                    continue;
                }
                this.m_log.logBasic(this.m_subsystemID, this.m_poolName + "[" + this.m_url + "]" + " removeExpiredSessions() error :" + " SessionPoolException= " + obj);
            }
            catch (Open4GLException obj2) {
                if (!this.ifLoggingSessionPool(2) || !this.m_log.ifLogBasic(this.m_mgmtLogEntries, this.m_mgmtLogIndex)) {
                    continue;
                }
                this.m_log.logBasic(this.m_subsystemID, this.m_poolName + "[" + this.m_url + "]" + " removeExpiredSessions() error :" + " Open4GLException= " + obj2);
            }
        }
    }
    
    private Session getFromSessionTable(final String key) throws SessionPoolException {
        if (!this.m_brokerBySessionID.containsKey(key)) {
            if (this.ifLoggingSessionPool(4) && this.m_log.ifLogExtended(this.m_mgmtLogEntries, this.m_mgmtLogIndex)) {
                this.m_log.logExtended(this.m_subsystemID, this.m_poolName + " : getSession(" + key + ") error");
            }
            throw new SessionNotFoundException(key);
        }
        return this.m_brokerBySessionID.get(key).getSession(key);
    }
    
    private synchronized Session findSession() {
        final Enumeration<String> keys = this.m_brokerBySessionID.keys();
        while (keys.hasMoreElements()) {
            try {
                final Session fromSessionTable = this.getFromSessionTable(keys.nextElement());
                if (fromSessionTable.getPoolState() == 1) {
                    ++this.m_numBusy;
                    if (this.m_numBusy > this.m_maxBusy) {
                        this.m_maxBusy = this.m_numBusy;
                    }
                    fromSessionTable.setPoolState(2);
                    if (this.ifLoggingSessionPool(4) && this.m_log.ifLogExtended(this.m_mgmtLogEntries, this.m_mgmtLogIndex)) {
                        this.m_log.logExtended(this.m_subsystemID, this.m_poolName + "[" + this.m_url + "]" + " : findSession() = " + fromSessionTable);
                    }
                    return fromSessionTable;
                }
                continue;
            }
            catch (SessionPoolException obj) {
                if (!this.ifLoggingSessionPool(2) || !this.m_log.ifLogBasic(this.m_mgmtLogEntries, this.m_mgmtLogIndex)) {
                    continue;
                }
                this.m_log.logBasic(this.m_subsystemID, this.m_poolName + "[" + this.m_url + "]" + " findSession() error :" + " SessionPoolException= " + obj);
            }
        }
        return null;
    }
    
    private void initializePool() throws ConnectException, Open4GLException, SessionPoolException, SystemErrorException {
        if (this.ifLoggingSessionPool(4) && this.m_log.ifLogExtended(this.m_mgmtLogEntries, this.m_mgmtLogIndex) && this.initialSessions() > 0) {
            this.m_log.logExtended(this.m_subsystemID, this.m_poolName + " starting " + this.initialSessions() + " initial sessions");
        }
        try {
            for (int n = 0; n < this.initialSessions() && n < this.maxSessions(); ++n) {
                this.releaseSession(this.createSession().getSessionID());
            }
        }
        catch (ConnectException obj) {
            if (this.ifLoggingSessionPool(4) && this.m_log.ifLogExtended(this.m_mgmtLogEntries, this.m_mgmtLogIndex)) {
                this.m_log.logExtended(this.m_subsystemID, this.m_poolName + " Error while initializing session pool : " + obj);
            }
            this.removeAll();
            throw obj;
        }
    }
    
    private void initializeLogging(final IAppLogger appLogger) {
        final String logContextName = appLogger.getLogContext().getLogContextName();
        if (logContextName.equals("Wsa")) {
            this.m_subsystemID = 6;
            this.m_poolLogEntries = 64L;
            this.m_mgmtLogEntries = 1024L;
            this.m_mgmtLogIndex = 10;
            this.m_refcntLogEntries = 2048L;
            this.m_refcntLogIndex = 11;
            this.m_watchdogLogEntries = 8192L;
            this.m_watchdogLogIndex = 13;
            this.m_debugLogEntries = 8589934592L;
            this.m_debugLogIndex = 33;
        }
        else if (logContextName.equals("O4gl")) {
            this.m_subsystemID = 2;
            this.m_poolLogEntries = 4L;
            this.m_mgmtLogEntries = 16L;
            this.m_mgmtLogIndex = 4;
            this.m_refcntLogEntries = 32L;
            this.m_refcntLogIndex = 5;
            this.m_watchdogLogEntries = 128L;
            this.m_watchdogLogIndex = 7;
            this.m_debugLogEntries = 8589934592L;
            this.m_debugLogIndex = 10;
        }
        else {
            this.m_subsystemID = 0;
            this.m_poolLogEntries = 0L;
            this.m_mgmtLogEntries = 0L;
            this.m_mgmtLogIndex = 0;
            this.m_refcntLogEntries = 0L;
            this.m_refcntLogIndex = 0;
            this.m_watchdogLogEntries = 0L;
            this.m_watchdogLogIndex = 0;
            this.m_debugLogEntries = 0L;
            this.m_debugLogIndex = 0;
        }
    }
    
    private boolean ifLoggingSessionPool(final int n) {
        return this.m_log.ifLogIt(n, this.m_poolLogEntries, this.m_subsystemID);
    }
    
    private void initThreadLocals() {
        this.m_connectionId = new ThreadLocal() {
            protected Object initialValue() {
                return null;
            }
        };
        this.m_requestId = new ThreadLocal() {
            protected Object initialValue() {
                return null;
            }
        };
        this.m_returnValue = new ThreadLocal() {
            protected Object initialValue() {
                return null;
            }
        };
        this.m_isStreaming = new ThreadData();
        this.m_sslSubjectName = new ThreadLocal() {
            protected Object initialValue() {
                return null;
            }
        };
    }
    
    static {
        ExceptionMessageAdapter.setMessageSubsystem(new PromsgsBundle());
        SessionPool.sessionPoolCountLock = new Object();
        SessionPool.sessionPoolCount = 0;
        SessionPool.fmt6 = new DecimalFormat("000000");
    }
    
    public static class SessionPoolException extends Open4GLException
    {
        public SessionPoolException() {
        }
        
        public SessionPoolException(final String s) {
            super("SessionPool : %s", new Object[] { s });
        }
        
        public String getDetail() {
            return (String)this.getArgument(0);
        }
        
        public boolean hasProcReturnString() {
            return super.hasProcReturnString();
        }
        
        public String getProcReturnString() {
            return super.getProcReturnString();
        }
    }
    
    public static class SessionNotFoundException extends SessionPoolException
    {
        public SessionNotFoundException(final String str) {
            super("SessionNotFound[" + str + "]");
        }
    }
    
    public static class NoAvailableSessionsException extends SessionPoolException
    {
        public NoAvailableSessionsException() {
        }
        
        public NoAvailableSessionsException(final String str) {
            super("NoAvailableSessions[" + str + "]");
        }
        
        public NoAvailableSessionsException(final String str, final String procReturnString) {
            super("NoAvailableSessions[" + str + "]");
            super.setProcReturnString(procReturnString);
        }
    }
    
    public static class InvalidPropertiesException extends SessionPoolException
    {
        public InvalidPropertiesException() {
        }
        
        public InvalidPropertiesException(final String str) {
            super("InvalidProperties[" + str + "]");
        }
    }
    
    private class PickList
    {
        private String m_url;
        private String m_protocol;
        private String m_serviceName;
        private NameServerClient m_nsClient;
        private Vector m_brokerInfo;
        private long m_tsPickList;
        
        public PickList(final String url) throws ConnectException {
            this.m_url = url;
            try {
                final PscURLParser pscURLParser = new PscURLParser(url);
                this.m_serviceName = pscURLParser.getService();
                this.m_protocol = pscURLParser.getScheme();
                this.m_nsClient = this.createNameServerClient(url);
            }
            catch (MalformedURLException ex) {
                throw new BadURLException(url);
            }
            this.m_brokerInfo = new Vector();
            this.m_tsPickList = System.currentTimeMillis();
        }
        
        public int size() {
            return (this.m_brokerInfo == null) ? 0 : this.m_brokerInfo.size();
        }
        
        public boolean isEmpty() {
            return this.m_brokerInfo == null || this.m_brokerInfo.size() == 0;
        }
        
        public synchronized NameServerClient.Broker nextBroker(final boolean b) throws ConnectException {
            if (this.isEmpty() && b) {
                this.m_brokerInfo = this.nsLookupService(this.m_serviceName);
                this.m_tsPickList = System.currentTimeMillis();
                this.enumerate("fetched new picklist");
            }
            NameServerClient.Broker broker;
            if (this.isEmpty()) {
                broker = null;
            }
            else {
                broker = this.m_brokerInfo.elementAt(0);
                this.m_brokerInfo.removeElementAt(0);
            }
            return broker;
        }
        
        public synchronized void removeBroker(final NameServerClient.Broker obj) throws ConnectException {
            while (this.m_brokerInfo.removeElement(obj)) {}
        }
        
        public String getProtocol() throws ConnectException {
            return this.m_protocol;
        }
        
        public synchronized void resetList() {
            this.m_brokerInfo = new Vector();
            this.m_tsPickList = System.currentTimeMillis();
        }
        
        private NameServerClient createNameServerClient(final String str) throws ConnectException, MalformedURLException {
            final PscURLParser pscURLParser = new PscURLParser(str);
            final int access$000 = SessionPool.this.nsClientMinPort();
            final int access$2 = SessionPool.this.nsClientMaxPort();
            final int access$3 = SessionPool.this.nsClientRetryInterval();
            final int access$4 = SessionPool.this.nsClientRetry();
            NameServerClient nameServerClient;
            try {
                nameServerClient = new NameServerClient(pscURLParser.getHost(), pscURLParser.getPort(), "AS", access$000, access$2, access$3, access$4);
            }
            catch (ConnectException obj) {
                if (SessionPool.this.ifLoggingSessionPool(4) && SessionPool.this.m_log.ifLogExtended(SessionPool.this.m_mgmtLogEntries, SessionPool.this.m_mgmtLogIndex)) {
                    SessionPool.this.m_log.logExtended(SessionPool.this.m_subsystemID, SessionPool.this.m_poolName + " error accessing to " + str + " : " + obj);
                }
                throw obj;
            }
            return nameServerClient;
        }
        
        private Vector nsLookupService(final String str) throws ConnectException {
            Vector arrayToVector;
            try {
                arrayToVector = this.arrayToVector(this.m_nsClient.getBrokerList(str, SessionPool.this.nsClientPickListSize()));
            }
            catch (ConnectException obj) {
                if (SessionPool.this.ifLoggingSessionPool(4) && SessionPool.this.m_log.ifLogExtended(SessionPool.this.m_mgmtLogEntries, SessionPool.this.m_mgmtLogIndex)) {
                    SessionPool.this.m_log.logExtended(SessionPool.this.m_subsystemID, SessionPool.this.m_poolName + " error looking up " + str + " : " + obj);
                }
                throw obj;
            }
            return arrayToVector;
        }
        
        private Vector arrayToVector(final Object[] array) {
            final Vector<Object> vector = new Vector<Object>();
            for (int i = 0; i < array.length; ++i) {
                vector.addElement(array[i]);
            }
            return vector;
        }
        
        private synchronized void enumerate(final String str) {
            if (SessionPool.this.ifLoggingSessionPool(4) && SessionPool.this.m_log.ifLogExtended(SessionPool.this.m_mgmtLogEntries, SessionPool.this.m_mgmtLogIndex)) {
                SessionPool.this.m_log.logExtended(SessionPool.this.m_subsystemID, SessionPool.this.m_poolName + " PickList (size= " + this.m_brokerInfo.size() + " ) : " + str);
                for (int i = 0; i < this.m_brokerInfo.size(); ++i) {
                    SessionPool.this.m_log.logExtended(SessionPool.this.m_subsystemID, SessionPool.this.m_poolName + " [" + SessionPool.fmt6.format(i) + "] : " + this.m_brokerInfo.elementAt(i).toString());
                }
            }
        }
    }
    
    private class BrokerSessionList
    {
        private String m_brokerID;
        private String m_url;
        private Hashtable m_sessionList;
        private long m_tsCreated;
        private long m_tsLastAccessed;
        private int m_numBusy;
        private int m_maxBusy;
        
        public BrokerSessionList(final String brokerID, final String url, final IAppLogger appLogger) {
            this.m_brokerID = brokerID;
            this.m_url = url;
            this.m_sessionList = new Hashtable();
            this.m_tsCreated = System.currentTimeMillis();
            this.m_numBusy = 0;
            this.m_maxBusy = 0;
        }
        
        public Object findAvailableObject() {
            return this.findSession();
        }
        
        public void makeObjectAvailable(final Object o) {
            final Session session = (Session)o;
            if (SessionPool.this.ifLoggingSessionPool(4) && SessionPool.this.m_log.ifLogExtended(SessionPool.this.m_mgmtLogEntries, SessionPool.this.m_mgmtLogIndex)) {
                SessionPool.this.m_log.logExtended(SessionPool.this.m_subsystemID, SessionPool.this.m_poolName + "[" + this.m_url + "]" + " : makeObjectAvailable(" + session.getSessionID() + ")");
            }
            if (session.getPoolState() == 2) {
                --this.m_numBusy;
            }
            session.setPoolState(1);
        }
        
        public synchronized Session getSession(final String s) throws SessionPoolException {
            this.m_tsLastAccessed = System.currentTimeMillis();
            return this.getBySessionID(s);
        }
        
        public void removeAvailableSession() throws SessionPoolException, SystemErrorException, Open4GLException {
            this.m_tsLastAccessed = System.currentTimeMillis();
            synchronized (SessionPool.this) {
                final Session session;
                if ((session = this.findSession()) == null) {
                    if (session == null) {
                        throw new NoAvailableSessionsException("unable to find available session");
                    }
                }
                else {
                    this.deleteSessionReference(session);
                    session.setPoolState(3);
                    this.deleteSessionFromList(session);
                    if (SessionPool.this.ifLoggingSessionPool(4) && SessionPool.this.m_log.ifLogExtended(SessionPool.this.m_mgmtLogEntries, SessionPool.this.m_mgmtLogIndex)) {
                        SessionPool.this.m_log.logExtended(SessionPool.this.m_subsystemID, SessionPool.this.m_poolName + "[" + this.m_url + "]" + " : removed(" + session.getSessionID() + ")");
                    }
                }
            }
        }
        
        public Session reserveSession(final boolean b) throws SessionPoolException {
            if (SessionPool.this.ifLoggingSessionPool(4) && SessionPool.this.m_log.ifLogExtended(SessionPool.this.m_mgmtLogEntries, SessionPool.this.m_mgmtLogIndex)) {
                SessionPool.this.m_log.logExtended(SessionPool.this.m_subsystemID, SessionPool.this.m_poolName + "[" + this.m_url + "]" + " reserveSession(" + b + ")");
            }
            this.m_tsLastAccessed = System.currentTimeMillis();
            Session session;
            synchronized (SessionPool.this) {
                if (b) {
                    if (SessionPool.this.size() >= SessionPool.this.maxSessions()) {
                        if (SessionPool.this.ifLoggingSessionPool(4) && SessionPool.this.m_log.ifLogExtended(SessionPool.this.m_mgmtLogEntries, SessionPool.this.m_mgmtLogIndex)) {
                            SessionPool.this.m_log.logExtended(SessionPool.this.m_subsystemID, SessionPool.this.m_poolName + "[" + this.m_url + "]" + " : reserveSession() : FULL on create");
                        }
                        throw new NoAvailableSessionsException("session pool full");
                    }
                    session = this.newSession();
                }
                else {
                    if ((session = this.findSession()) != null) {
                        if (SessionPool.this.ifLoggingSessionPool(4) && SessionPool.this.m_log.ifLogExtended(SessionPool.this.m_mgmtLogEntries, SessionPool.this.m_mgmtLogIndex)) {
                            SessionPool.this.m_log.logExtended(SessionPool.this.m_subsystemID, SessionPool.this.m_poolName + "[" + this.m_url + "]" + " : reserved(" + session.getSessionID() + ")");
                        }
                        return session;
                    }
                    if (SessionPool.this.size() < SessionPool.this.maxSessions()) {
                        session = this.newSession();
                    }
                }
            }
            if (session != null) {
                try {
                    session.connect(SessionPool.this.m_requestID, this.m_url, SessionPool.this.m_userId, SessionPool.this.m_password, SessionPool.this.m_clientInfo);
                    this.addSessionReference(session);
                    session.setPoolState(2);
                }
                catch (ConnectException ex) {
                    this.deleteSessionFromList(session);
                    final NoAvailableSessionsException obj = new NoAvailableSessionsException(ex.getMessage(), ex.getProcReturnString());
                    if (ex.hasProcReturnString()) {
                        obj.setProcReturnString(ex.getProcReturnString());
                    }
                    if (SessionPool.this.ifLoggingSessionPool(4) && SessionPool.this.m_log.ifLogExtended(SessionPool.this.m_mgmtLogEntries, SessionPool.this.m_mgmtLogIndex)) {
                        SessionPool.this.m_log.logExtended(SessionPool.this.m_subsystemID, SessionPool.this.m_poolName + "[" + this.m_url + "]" + " : reserveSession() connect failed " + obj);
                    }
                    throw obj;
                }
                catch (SystemErrorException ex2) {
                    this.deleteSessionFromList(session);
                    final NoAvailableSessionsException obj2 = new NoAvailableSessionsException(ex2.getMessage());
                    if (SessionPool.this.ifLoggingSessionPool(4) && SessionPool.this.m_log.ifLogExtended(SessionPool.this.m_mgmtLogEntries, SessionPool.this.m_mgmtLogIndex)) {
                        SessionPool.this.m_log.logExtended(SessionPool.this.m_subsystemID, SessionPool.this.m_poolName + "[" + this.m_url + "]" + " : reserveSession() connect failed " + obj2);
                    }
                    throw obj2;
                }
                if (SessionPool.this.ifLoggingSessionPool(4) && SessionPool.this.m_log.ifLogExtended(SessionPool.this.m_mgmtLogEntries, SessionPool.this.m_mgmtLogIndex)) {
                    SessionPool.this.m_log.logExtended(SessionPool.this.m_subsystemID, SessionPool.this.m_poolName + "[" + this.m_url + "]" + " : reserved(" + session.getSessionID() + ")");
                }
                return session;
            }
            if (SessionPool.this.ifLoggingSessionPool(4) && SessionPool.this.m_log.ifLogExtended(SessionPool.this.m_mgmtLogEntries, SessionPool.this.m_mgmtLogIndex)) {
                SessionPool.this.m_log.logExtended(SessionPool.this.m_subsystemID, SessionPool.this.m_poolName + "[" + this.m_url + "]" + " : maximum sessions already started ... " + "waiting at most " + SessionPool.this.requestWaitTimeout() + " secs for one to become available");
            }
            final Session session2 = (Session)SessionPool.this.reserve(SessionPool.this.requestWaitTimeout());
            if (session2 == null) {
                final long access$1300 = SessionPool.this.requestWaitTimeout();
                String string;
                if (access$1300 == -1L) {
                    string = "unable to get session (request was cancelled).";
                }
                else {
                    string = "unable to get session after waiting " + access$1300 + " seconds, or request was cancelled.";
                }
                throw new NoAvailableSessionsException(string);
            }
            if (SessionPool.this.ifLoggingSessionPool(4) && SessionPool.this.m_log.ifLogExtended(SessionPool.this.m_mgmtLogEntries, SessionPool.this.m_mgmtLogIndex)) {
                SessionPool.this.m_log.logExtended(SessionPool.this.m_subsystemID, SessionPool.this.m_poolName + "[" + this.m_url + "]" + " : got session " + session2.getSessionID() + " after waiting");
            }
            return session2;
        }
        
        public synchronized Session releaseSession(final String str) throws SessionPoolException, Open4GLException {
            this.m_tsLastAccessed = System.currentTimeMillis();
            if (SessionPool.this.ifLoggingSessionPool(4) && SessionPool.this.m_log.ifLogExtended(SessionPool.this.m_mgmtLogEntries, SessionPool.this.m_mgmtLogIndex)) {
                SessionPool.this.m_log.logExtended(SessionPool.this.m_subsystemID, SessionPool.this.m_poolName + "[" + this.m_url + "]" + " : releaseSession(" + str + ")");
            }
            final Session bySessionID = this.getBySessionID(str);
            SessionPool.this.release(bySessionID);
            return bySessionID;
        }
        
        public synchronized void removeSession(final String str) throws SessionPoolException, Open4GLException {
            this.m_tsLastAccessed = System.currentTimeMillis();
            if (SessionPool.this.ifLoggingSessionPool(4) && SessionPool.this.m_log.ifLogExtended(SessionPool.this.m_mgmtLogEntries, SessionPool.this.m_mgmtLogIndex)) {
                SessionPool.this.m_log.logExtended(SessionPool.this.m_subsystemID, SessionPool.this.m_poolName + "[" + this.m_url + "]" + " : removeSession(" + str + ")");
            }
            final Session bySessionID = this.getBySessionID(str);
            this.deleteSessionFromList(bySessionID);
            bySessionID.setPoolState(3);
            this.deleteSessionReference(bySessionID);
        }
        
        public synchronized void release() {
            this.m_tsLastAccessed = System.currentTimeMillis();
            if (SessionPool.this.ifLoggingSessionPool(4) && SessionPool.this.m_log.ifLogExtended(SessionPool.this.m_mgmtLogEntries, SessionPool.this.m_mgmtLogIndex)) {
                SessionPool.this.m_log.logExtended(SessionPool.this.m_subsystemID, SessionPool.this.m_poolName + "[" + this.m_url + "]" + " : release()");
            }
            this.removeAll();
        }
        
        public synchronized void shutdown() {
            this.m_tsLastAccessed = System.currentTimeMillis();
            if (SessionPool.this.ifLoggingSessionPool(4) && SessionPool.this.m_log.ifLogExtended(SessionPool.this.m_mgmtLogEntries, SessionPool.this.m_mgmtLogIndex)) {
                SessionPool.this.m_log.logExtended(SessionPool.this.m_subsystemID, SessionPool.this.m_poolName + "[" + this.m_url + "]" + " : shutdown()");
            }
            this.shutdownAll();
        }
        
        public synchronized void cancelAllRequests() {
            this.m_tsLastAccessed = System.currentTimeMillis();
            if (SessionPool.this.ifLoggingSessionPool(4) && SessionPool.this.m_log.ifLogExtended(SessionPool.this.m_mgmtLogEntries, SessionPool.this.m_mgmtLogIndex)) {
                SessionPool.this.m_log.logExtended(SessionPool.this.m_subsystemID, SessionPool.this.m_poolName + "[" + this.m_url + "]" + " : cancelAllRequests()");
            }
            this.cancelAll();
        }
        
        public int size() {
            return (this.m_sessionList == null) ? 0 : this.m_sessionList.size();
        }
        
        public boolean isEmpty() {
            return this.m_sessionList == null || this.m_sessionList.size() == 0;
        }
        
        public String toString() {
            return SessionPool.this.m_poolName + "[" + this.m_url + "]";
        }
        
        public int maxBusy() {
            return this.m_maxBusy;
        }
        
        public int resetMaxBusy() {
            final int maxBusy = this.m_maxBusy;
            this.m_maxBusy = 0;
            return maxBusy;
        }
        
        public synchronized int availableSessions() {
            int n = 0;
            final Enumeration<Session> elements = (Enumeration<Session>)this.m_sessionList.elements();
            while (elements.hasMoreElements()) {
                if (elements.nextElement().getPoolState() == 1) {
                    ++n;
                }
            }
            return n;
        }
        
        private synchronized Session findSession() {
            final Enumeration<Session> elements = this.m_sessionList.elements();
            while (elements.hasMoreElements()) {
                final Session obj = elements.nextElement();
                if (obj.getPoolState() == 1) {
                    ++this.m_numBusy;
                    if (this.m_numBusy > this.m_maxBusy) {
                        this.m_maxBusy = this.m_numBusy;
                    }
                    obj.setPoolState(2);
                    if (SessionPool.this.ifLoggingSessionPool(4) && SessionPool.this.m_log.ifLogExtended(SessionPool.this.m_mgmtLogEntries, SessionPool.this.m_mgmtLogIndex)) {
                        SessionPool.this.m_log.logExtended(SessionPool.this.m_subsystemID, SessionPool.this.m_poolName + "[" + this.m_url + "]" + " : findSession() = " + obj);
                    }
                    return obj;
                }
            }
            return null;
        }
        
        private Session newSession() {
            final Session session = new Session(this.m_url, SessionPool.this.m_log, SessionPool.this.m_properties);
            session.setPoolState(0);
            final String sessionID = session.getSessionID();
            if (SessionPool.this.ifLoggingSessionPool(4) && SessionPool.this.m_log.ifLogExtended(SessionPool.this.m_mgmtLogEntries, SessionPool.this.m_mgmtLogIndex)) {
                SessionPool.this.m_log.logExtended(SessionPool.this.m_subsystemID, SessionPool.this.m_poolName + "[" + this.m_url + "]" + " : newSession() = " + sessionID);
            }
            this.addSessionToList(session);
            return session;
        }
        
        private void addSessionToList(final Session value) {
            final String sessionID = value.getSessionID();
            synchronized (SessionPool.this) {
                this.m_sessionList.put(sessionID, value);
                SessionPool.this.m_brokerBySessionID.put(sessionID, this);
            }
        }
        
        private void deleteSessionFromList(final Session session) {
            synchronized (SessionPool.this) {
                this.m_sessionList.remove(session.getSessionID());
                SessionPool.this.m_brokerBySessionID.remove(session.getSessionID());
            }
        }
        
        private void removeAll() {
            final Enumeration<Session> elements = this.m_sessionList.elements();
            while (elements.hasMoreElements()) {
                final Session session = elements.nextElement();
                final int poolState = session.getPoolState();
                if (poolState != 0 && poolState != 3) {
                    this.deleteSessionReference(session);
                }
            }
            this.m_sessionList.clear();
            if (SessionPool.this.ifLoggingSessionPool(4) && SessionPool.this.m_log.ifLogExtended(SessionPool.this.m_mgmtLogEntries, SessionPool.this.m_mgmtLogIndex)) {
                SessionPool.this.m_log.logExtended(SessionPool.this.m_subsystemID, SessionPool.this.m_poolName + " : removeAll() : removed all sessions from List");
            }
        }
        
        private void shutdownAll() {
            final Enumeration<Session> elements = this.m_sessionList.elements();
            while (elements.hasMoreElements()) {
                final Session session = elements.nextElement();
                final int poolState = session.getPoolState();
                if (poolState != 0 && poolState != 3) {
                    this.shutdownSession(session);
                }
            }
            this.m_sessionList.clear();
            if (SessionPool.this.ifLoggingSessionPool(4) && SessionPool.this.m_log.ifLogExtended(SessionPool.this.m_mgmtLogEntries, SessionPool.this.m_mgmtLogIndex)) {
                SessionPool.this.m_log.logExtended(SessionPool.this.m_subsystemID, SessionPool.this.m_poolName + " : shutdownAll() : removed all sessions from List");
            }
        }
        
        private void cancelAll() {
            final Enumeration<Session> elements = this.m_sessionList.elements();
            while (elements.hasMoreElements()) {
                final Session session = elements.nextElement();
                if (session.getPoolState() == 2) {
                    this.cancelSession(session);
                }
            }
        }
        
        private Session getBySessionID(final String key) throws SessionPoolException {
            if (!this.m_sessionList.containsKey(key)) {
                if (SessionPool.this.ifLoggingSessionPool(4) && SessionPool.this.m_log.ifLogExtended(SessionPool.this.m_mgmtLogEntries, SessionPool.this.m_mgmtLogIndex)) {
                    SessionPool.this.m_log.logExtended(SessionPool.this.m_subsystemID, SessionPool.this.m_poolName + " : getBySessionID(" + key + ") error");
                }
                throw new SessionNotFoundException(key);
            }
            return this.m_sessionList.get(key);
        }
        
        private void addSessionReference(final Session session) {
            final String sessionID = session.getSessionID();
            if (SessionPool.this.ifLoggingSessionPool(4) && SessionPool.this.m_log.ifLogExtended(SessionPool.this.m_refcntLogEntries, SessionPool.this.m_refcntLogIndex)) {
                SessionPool.this.m_log.logExtended(SessionPool.this.m_subsystemID, SessionPool.this.m_poolName + " : addReference() to " + sessionID);
            }
            session.addReference();
        }
        
        private void deleteSessionReference(final Session obj) {
            try {
                if (SessionPool.this.ifLoggingSessionPool(4) && SessionPool.this.m_log.ifLogExtended(SessionPool.this.m_refcntLogEntries, SessionPool.this.m_refcntLogIndex)) {
                    SessionPool.this.m_log.logExtended(SessionPool.this.m_subsystemID, SessionPool.this.m_poolName + " : deleteReference() to " + obj.getSessionID());
                }
                obj.deleteReference();
            }
            catch (Exception ex) {
                SessionPool.this.m_log.logWriteMessage(2, 1, "---", "---", SessionPool.this.m_poolName + " : error deleting session reference = " + obj, ex);
            }
        }
        
        private void shutdownSession(final Session obj) {
            try {
                if (SessionPool.this.ifLoggingSessionPool(4) && SessionPool.this.m_log.ifLogExtended(SessionPool.this.m_refcntLogEntries, SessionPool.this.m_refcntLogIndex)) {
                    SessionPool.this.m_log.logExtended(SessionPool.this.m_subsystemID, SessionPool.this.m_poolName + " : shutdownSession() to " + obj.getSessionID());
                }
                this.deleteSessionFromList(obj);
                obj.setStop();
                obj.shutdown();
            }
            catch (Exception ex) {
                SessionPool.this.m_log.logWriteMessage(2, 1, "---", "---", SessionPool.this.m_poolName + " : shutdownSession error= " + obj, ex);
            }
        }
        
        private void cancelSession(final Session obj) {
            try {
                if (SessionPool.this.ifLoggingSessionPool(4) && SessionPool.this.m_log.ifLogExtended(SessionPool.this.m_refcntLogEntries, SessionPool.this.m_refcntLogIndex)) {
                    SessionPool.this.m_log.logExtended(SessionPool.this.m_subsystemID, SessionPool.this.m_poolName + " : cancelSession() to " + obj.getSessionID());
                }
                obj.setStop();
            }
            catch (Exception ex) {
                SessionPool.this.m_log.logWriteMessage(2, 1, "---", "---", SessionPool.this.m_poolName + " : cancelSession error= " + obj, ex);
            }
        }
        
        private synchronized void enumerate(final String str) {
            if (SessionPool.this.ifLoggingSessionPool(4) && SessionPool.this.m_log.ifLogExtended(SessionPool.this.m_mgmtLogEntries, SessionPool.this.m_mgmtLogIndex)) {
                SessionPool.this.m_log.logExtended(SessionPool.this.m_subsystemID, SessionPool.this.m_poolName + "[" + this.m_url + "]" + " BrokerSessionList (size= " + this.m_sessionList.size() + " ) : " + str);
                int n = 0;
                final Enumeration<Session> keys = this.m_sessionList.keys();
                while (keys.hasMoreElements()) {
                    SessionPool.this.m_log.logExtended(SessionPool.this.m_subsystemID, SessionPool.this.m_poolName + "[" + this.m_url + "]" + " [" + SessionPool.fmt6.format(n) + "] : " + keys.nextElement().toString());
                    ++n;
                }
            }
        }
    }
    
    private class IdleSessionWatchdog implements IWatchable
    {
        private WatchDog m_idleSessionWatchdog;
        
        public IdleSessionWatchdog() {
            this.startWatchDog();
        }
        
        public void watchEvent() {
            int i = 0;
            SessionPool.this.removeExpiredSessions();
            final int resetMaxBusy;
            final int size;
            final int j;
            synchronized (SessionPool.this) {
                resetMaxBusy = SessionPool.this.resetMaxBusy();
                size = SessionPool.this.size();
                j = size - Math.max(resetMaxBusy, Math.max(SessionPool.this.minSessions(), SessionPool.this.minIdleSessions()));
            }
            if (j > 0) {
                if (SessionPool.this.ifLoggingSessionPool(2) && SessionPool.this.m_log.ifLogBasic(SessionPool.this.m_watchdogLogEntries, SessionPool.this.m_watchdogLogIndex)) {
                    SessionPool.this.m_log.logBasic(SessionPool.this.m_subsystemID, SessionPool.this.m_poolName + " Attempting to disconnect " + j + " sessions");
                }
                if (SessionPool.this.ifLoggingSessionPool(1) && SessionPool.this.m_log.ifLogExtended(SessionPool.this.m_watchdogLogEntries, SessionPool.this.m_watchdogLogIndex)) {
                    SessionPool.this.m_log.logExtended(SessionPool.this.m_subsystemID, SessionPool.this.m_poolName + " Currently connected= " + size + " ... Max busy in interval= " + resetMaxBusy);
                }
                try {
                    int k = 0;
                    i = 0;
                    while (k < j) {
                        SessionPool.this.removeAvailableSession();
                        ++i;
                        ++k;
                    }
                }
                catch (NoAvailableSessionsException ex) {
                    if (SessionPool.this.ifLoggingSessionPool(1) && SessionPool.this.m_log.ifLogExtended(SessionPool.this.m_watchdogLogEntries, SessionPool.this.m_watchdogLogIndex)) {
                        SessionPool.this.m_log.logExtended(SessionPool.this.m_subsystemID, SessionPool.this.m_poolName + " Not enough idle connections to disconnect");
                    }
                }
                catch (SessionPoolException obj) {
                    if (SessionPool.this.ifLoggingSessionPool(2) && SessionPool.this.m_log.ifLogBasic(SessionPool.this.m_watchdogLogEntries, SessionPool.this.m_watchdogLogIndex)) {
                        SessionPool.this.m_log.logBasic(SessionPool.this.m_subsystemID, SessionPool.this.m_poolName + " SessionPoolException= " + obj);
                    }
                }
                catch (Open4GLException obj2) {
                    if (SessionPool.this.ifLoggingSessionPool(2) && SessionPool.this.m_log.ifLogBasic(SessionPool.this.m_watchdogLogEntries, SessionPool.this.m_watchdogLogIndex)) {
                        SessionPool.this.m_log.logBasic(SessionPool.this.m_subsystemID, SessionPool.this.m_poolName + " Open4GLException= " + obj2);
                    }
                }
                if (SessionPool.this.ifLoggingSessionPool(2) && SessionPool.this.m_log.ifLogBasic(SessionPool.this.m_watchdogLogEntries, SessionPool.this.m_watchdogLogIndex)) {
                    SessionPool.this.m_log.logBasic(SessionPool.this.m_subsystemID, SessionPool.this.m_poolName + " Disconnected " + i + " connection(s)");
                }
            }
        }
        
        public void stop() {
            if (this.m_idleSessionWatchdog != null) {
                this.m_idleSessionWatchdog.setInterval(0L);
                this.m_idleSessionWatchdog.interrupt();
            }
        }
        
        private void startWatchDog() {
            if (SessionPool.this.idleSessionTimeout() > 0L) {
                if (SessionPool.this.ifLoggingSessionPool(4) && SessionPool.this.m_log.ifLogExtended(SessionPool.this.m_watchdogLogEntries, SessionPool.this.m_watchdogLogIndex)) {
                    SessionPool.this.m_log.logExtended(SessionPool.this.m_subsystemID, SessionPool.this.m_poolName + " starting idleSession watchdog (timeout= " + SessionPool.this.idleSessionTimeout() / 1000L + " seconds)");
                }
                (this.m_idleSessionWatchdog = new WatchDog(SessionPool.this.m_poolName + "idleSessionWatchdog", this, SessionPool.this.idleSessionTimeout(), 6, SessionPool.this.m_log)).start();
            }
            else {
                this.m_idleSessionWatchdog = null;
            }
        }
    }
    
    private class ThreadData
    {
        private Hashtable m_table;
        
        public ThreadData() {
            this.m_table = new Hashtable();
        }
        
        public Object get() {
            return this.get(this.key());
        }
        
        public Object get(final int value) {
            final Integer n = new Integer(value);
            return this.m_table.containsKey(n) ? this.m_table.get(n) : null;
        }
        
        public Object set(final Object o) {
            return this.set(this.key(), o);
        }
        
        public Object set(final int value, final Object value2) {
            final Integer n = new Integer(value);
            return (value2 == null) ? this.m_table.remove(n) : this.m_table.put(n, value2);
        }
        
        private int key() {
            return Thread.currentThread().hashCode();
        }
    }
    
    public static class Test
    {
        private static Object s_rqid_lock;
        private static int s_rqid_cnt;
        
        public static void main(final String[] array) {
            final int n = 10;
            final String s = (array.length > 0) ? array[0] : "AppServer://localhost:5162/asbroker1";
            final String s2 = (array.length > 1) ? array[1] : "";
            final String s3 = (array.length > 2) ? array[2] : "";
            final String s4 = (array.length > 3) ? array[3] : "";
            int int1;
            try {
                int1 = Integer.parseInt(array[4]);
            }
            catch (Throwable t) {
                int1 = 2;
            }
            String s5;
            try {
                s5 = array[5];
            }
            catch (Throwable t2) {
                s5 = "";
            }
            AppLogger appLogger;
            SessionPool sessionPool;
            String sessionID;
            try {
                appLogger = new AppLogger("SessionPool$Test.log", 1, int1, 0L, 0, s5, "Test", "Wsa");
                sessionPool = new SessionPool("SessionPool$Test", newAppRuntimeProps(), appLogger, "SessionPool$Test", 0, newRequestID(), s, s2, s3, s4, null);
                sessionPool.addReference();
                sessionID = sessionPool.reserveSession().getSessionID();
            }
            catch (Exception ex) {
                ex.printStackTrace();
                return;
            }
            try {
                final long currentTimeMillis = System.currentTimeMillis();
                int i;
                for (i = 0; i < n; ++i) {
                    final ParameterSet set = new ParameterSet(1);
                    set.setStringParameter(1, fillParm(500), 3);
                    sessionPool.getSession(sessionID).runProcedure(newRequestID(), "bigping.p", set);
                    System.out.println("RETURN " + sessionPool.getSession(sessionID).getReturnValue() + " = " + i);
                }
                final long lng = System.currentTimeMillis() - currentTimeMillis;
                final float f = lng / (float)n;
                final float f2 = 1000.0f * (n / (float)lng);
                sessionPool.releaseSession(sessionID);
                sessionPool.deleteReference();
                System.err.println("Done ...     cnt= " + i);
                System.err.println("Elapsed Time    = " + lng + " ms");
                System.err.println("Avg time per rep= " + f + " ms");
                System.err.println("Reps per second = " + f2);
            }
            catch (Exception obj) {
                System.out.println("exception caught : " + obj);
                obj.printStackTrace();
            }
            finally {
                appLogger.logClose();
            }
        }
        
        private static String fillParm(final int n) {
            final StringBuffer sb = new StringBuffer(3 * n);
            for (int i = 0; i < n; ++i) {
                sb.append(i);
                sb.append(" ");
            }
            return sb.toString();
        }
        
        private static String newRequestID() {
            final String string;
            synchronized (Test.s_rqid_lock) {
                ++Test.s_rqid_cnt;
                string = "Rq-" + Test.s_rqid_cnt;
            }
            return string;
        }
        
        private static IPoolProps newAppRuntimeProps() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
            return (IPoolProps)Class.forName("com.progress.open4gl.RunTimeProperties").newInstance();
        }
        
        static {
            Test.s_rqid_lock = new Object();
            Test.s_rqid_cnt = 0;
        }
    }
}
