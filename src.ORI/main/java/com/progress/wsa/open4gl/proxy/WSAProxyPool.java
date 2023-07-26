// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa.open4gl.proxy;

import org.apache.xerces.parsers.AbstractDOMParser;
import com.progress.common.util.UUID;
import org.w3c.dom.Node;
import org.apache.xerces.parsers.DOMParser;
import com.progress.open4gl.dynamicapi.RqContext;
import com.progress.open4gl.RunTimeProperties;
import com.progress.common.ehnlog.AppLogger;
import java.util.Iterator;
import java.util.Collection;
import java.util.LinkedList;
import com.progress.open4gl.SystemErrorException;
import com.progress.open4gl.dynamicapi.ResultSetSchema;
import com.progress.open4gl.dynamicapi.ParameterSet;
import com.progress.open4gl.Open4GLException;
import java.util.HashMap;
import com.progress.common.util.WatchDog;
import java.util.Map;
import com.progress.common.ehnlog.IAppLogger;
import com.progress.wsa.admin.AppRuntimeProps;
import com.progress.common.util.IWatchable;

public class WSAProxyPool implements IWatchable
{
    private AppRuntimeProps m_properties;
    private String m_appName;
    private IAppLogger m_log;
    private Map m_proxyPool;
    private String m_poolName;
    private int m_connectionMode;
    private WatchDog m_staleObjectWatchdog;
    private String m_sfAppObjectID;
    
    public WSAProxyPool(final String s, final AppRuntimeProps properties, final IAppLogger log, final int connectionMode) {
        this.m_appName = s;
        this.m_properties = properties;
        this.m_log = log;
        this.m_connectionMode = connectionMode;
        this.m_poolName = "{" + s + "}";
        this.m_sfAppObjectID = null;
        this.m_proxyPool = new HashMap();
        if (this.m_log.ifLogExtended(32L, 5) & this.m_log.ifLogExtended(1024L, 10)) {
            this.m_log.logExtended(5, this.m_poolName + " proxypool created");
        }
        this.startWatchDog();
    }
    
    public String getSFAppObjectID() {
        return this.m_sfAppObjectID;
    }
    
    public WSAProxyObject getProxyObject(final String str) throws ProxyPoolException {
        final WSAProxyObject poolObject;
        synchronized (this.m_proxyPool) {
            poolObject = this.getPoolObject(str);
        }
        if (this.m_log.ifLogExtended(32L, 5) & this.m_log.ifLogExtended(1024L, 10)) {
            this.m_log.logExtended(5, this.m_poolName + " getProxyObject(" + str + ") = " + poolObject);
        }
        return poolObject;
    }
    
    public WSAProxyObject getProxyObject(final String str, final boolean b) throws ProxyPoolException {
        final WSAProxyObject poolObject;
        synchronized (this.m_proxyPool) {
            poolObject = this.getPoolObject(str);
            if (b) {
                poolObject.addReference();
            }
        }
        if (this.m_log.ifLogExtended(32L, 5) & this.m_log.ifLogExtended(1024L, 10)) {
            this.m_log.logExtended(5, this.m_poolName + " getProxyObject(" + str + ", " + b + ") = " + poolObject);
        }
        return poolObject;
    }
    
    public WSAAppObject newAppObject(final String s, final String s2, final String s3, final String s4) throws Open4GLException {
        return this.newAppObject(null, s, s2, s3, s4);
    }
    
    public WSAAppObject newSFAppObject(final String s, final String s2, final String s3, final String s4, final String s5) throws Open4GLException {
        if (this.m_connectionMode != 1) {
            throw new WrongTypeException("newSFAppObject() executed in Session-managed mode");
        }
        final WSAAppObject wsaAppObject;
        synchronized (this.m_proxyPool) {
            wsaAppObject = (WSAAppObject)((this.m_sfAppObjectID != null) ? this.getPoolObject(this.m_sfAppObjectID) : this.newAppObject(s, s2, s3, s4, s5));
        }
        return wsaAppObject;
    }
    
    public WSAAppObject newAppObject(final String s, final String s2, final String s3, final String s4, final String s5) throws Open4GLException {
        if (this.m_log.ifLogExtended(32L, 5) & this.m_log.ifLogExtended(1024L, 10)) {
            this.m_log.logExtended(5, this.m_poolName + " creating new AppObject()");
        }
        if (this.m_connectionMode == 1 && this.m_sfAppObjectID != null) {
            throw new SFAppObjectAlreadyExistsException(this.m_sfAppObjectID);
        }
        final WSAAppObject obj = new WSAAppObject(this.m_appName, this.m_properties, this.m_log, this.m_connectionMode, s, s2, s3, s4, s5);
        final String wsaObjectID = obj.getWSAObjectID();
        synchronized (this.m_proxyPool) {
            this.m_proxyPool.put(wsaObjectID, obj);
            obj.addReference();
        }
        if (this.m_connectionMode == 1) {
            this.m_sfAppObjectID = wsaObjectID;
        }
        if (this.m_log.ifLogExtended(32L, 5) & this.m_log.ifLogExtended(1024L, 10)) {
            this.m_log.logExtended(5, this.m_poolName + " newAppObject() = " + obj);
        }
        return obj;
    }
    
    public WSASubAppObject newSubAppObject(final String str) throws ProxyPoolException, Open4GLException {
        if (this.m_log.ifLogExtended(32L, 5) & this.m_log.ifLogExtended(1024L, 10)) {
            this.m_log.logExtended(5, this.m_poolName + " creating new SubAppObject(" + str + ")");
        }
        final WSASubAppObject obj;
        synchronized (this.m_proxyPool) {
            final WSAProxyObject poolObject = this.getPoolObject(str);
            if (!(poolObject instanceof WSAAppObject)) {
                throw new WrongTypeException(str + " not an AppObject");
            }
            obj = new WSASubAppObject(poolObject);
            this.m_proxyPool.put(obj.getWSAObjectID(), obj);
            obj.addReference();
        }
        if (this.m_log.ifLogExtended(32L, 5) & this.m_log.ifLogExtended(1024L, 10)) {
            this.m_log.logExtended(5, this.m_poolName + " newSubAppObject(" + str + ") = " + obj);
        }
        return obj;
    }
    
    public WSAProcObject newProcObject(final String s, final String s2, final ParameterSet set) throws ProxyPoolException, Open4GLException {
        return this.newProcObject(null, s, s2, set, null);
    }
    
    public WSAProcObject newProcObject(final String s, final String s2, final String s3, final ParameterSet set) throws ProxyPoolException, Open4GLException {
        return this.newProcObject(s, s2, s3, set, null);
    }
    
    public WSAProcObject newProcObject(final String s, final String s2, final ParameterSet set, final ResultSetSchema resultSetSchema) throws ProxyPoolException, Open4GLException {
        return this.newProcObject(null, s, s2, set, resultSetSchema);
    }
    
    public WSAProcObject newProcObject(final String s, final String s2, final String s3, final ParameterSet set, final ResultSetSchema resultSetSchema) throws ProxyPoolException, Open4GLException {
        final String str = "";
        if (this.m_log.ifLogExtended(32L, 5) & this.m_log.ifLogExtended(1024L, 10)) {
            this.m_log.logExtended(5, this.m_poolName + " creating new ProcObject(" + s2 + "," + s3 + ")");
        }
        final WSAProxyObject poolObject;
        synchronized (this.m_proxyPool) {
            poolObject = this.getPoolObject(s2);
        }
        if (!(poolObject instanceof WSAAppObject) && !(poolObject instanceof WSASubAppObject)) {
            throw new WrongTypeException(str + " not an AppObject or SubAppObject");
        }
        final WSAProcObject obj = new WSAProcObject(s, poolObject, s3, set, resultSetSchema);
        final String wsaObjectID = obj.getWSAObjectID();
        synchronized (this.m_proxyPool) {
            this.m_proxyPool.put(wsaObjectID, obj);
            obj.addReference();
        }
        if (this.m_log.ifLogExtended(32L, 5) & this.m_log.ifLogExtended(1024L, 10)) {
            this.m_log.logExtended(5, this.m_poolName + " newProcObject(" + s2 + "," + s3 + ") = " + obj + ", session= " + obj.getSessionID());
        }
        return obj;
    }
    
    public int deleteReference(final String str) throws ProxyPoolException, Open4GLException, SystemErrorException {
        final int deleteReference;
        synchronized (this.m_proxyPool) {
            deleteReference = this.getPoolObject(str).deleteReference();
            if (this.m_log.ifLogExtended(32L, 5) & this.m_log.ifLogExtended(1024L, 10)) {
                this.m_log.logExtended(5, this.m_poolName + " deleteReference(" + str + ")  count= " + deleteReference);
            }
        }
        return deleteReference;
    }
    
    public WSAProxyObject _release(final String s) throws ProxyPoolException, Open4GLException {
        if (this.m_log.ifLogExtended(32L, 5) & this.m_log.ifLogExtended(1024L, 10)) {
            this.m_log.logExtended(5, this.m_poolName + " _release(" + s + ")");
        }
        WSAProxyObject poolObject;
        synchronized (this.m_proxyPool) {
            poolObject = this.getPoolObject(s);
            final int deleteReference = poolObject.deleteReference();
            poolObject = this.m_proxyPool.remove(s);
            if (this.m_connectionMode == 1 && s == this.m_sfAppObjectID) {
                this.m_sfAppObjectID = null;
            }
            if (deleteReference != 0) {
                this.m_log.logError(this.m_poolName + " _release(" + s + ")  " + "NON-ZERO refcount= " + deleteReference);
            }
        }
        return poolObject;
    }
    
    public void shutdown() {
        if (this.m_log.ifLogExtended(32L, 5) & this.m_log.ifLogExtended(1024L, 10)) {
            this.m_log.logExtended(5, this.m_poolName + " shutdown()");
        }
        this.stopWatchdog();
        final LinkedList<WSAProxyObject> list;
        synchronized (this.m_proxyPool) {
            final Collection<WSAProxyObject> values = this.m_proxyPool.values();
            list = new LinkedList<WSAProxyObject>();
            final Iterator<WSAProxyObject> iterator = values.iterator();
            while (iterator.hasNext()) {
                list.add(iterator.next());
                iterator.remove();
            }
        }
        for (final WSAProxyObject obj : list) {
            try {
                obj.shutdown();
            }
            catch (Open4GLException obj2) {
                if (!(this.m_log.ifLogExtended(32L, 5) & this.m_log.ifLogExtended(1024L, 10))) {
                    continue;
                }
                this.m_log.logExtended(5, this.m_poolName + " shutdown() error releasing(" + obj + ") = " + obj2);
            }
        }
    }
    
    public void watchEvent() {
        final long staleObjectTimeout = this.staleObjectTimeout();
        final LinkedList<WSAProxyObject> list;
        synchronized (this.m_proxyPool) {
            final Collection<WSAProxyObject> values = this.m_proxyPool.values();
            final long currentTimeMillis = System.currentTimeMillis();
            list = new LinkedList<WSAProxyObject>();
            final Iterator<WSAProxyObject> iterator = values.iterator();
            while (iterator.hasNext()) {
                final WSAProxyObject wsaProxyObject = iterator.next();
                final long n = currentTimeMillis - wsaProxyObject.get_tsLastAccessed();
                if (n > staleObjectTimeout) {
                    if (this.m_log.ifLogExtended(32L, 5) & this.m_log.ifLogExtended(1024L, 10)) {
                        this.m_log.logExtended(5, this.m_poolName + " watchEvent() releasing " + wsaProxyObject + " (inactive more than " + n / 1000L + " seconds)");
                    }
                    list.add(wsaProxyObject);
                    iterator.remove();
                }
            }
        }
        for (final WSAProxyObject obj : list) {
            try {
                obj._release();
            }
            catch (Open4GLException obj2) {
                if (!(this.m_log.ifLogExtended(32L, 5) & this.m_log.ifLogExtended(1024L, 10))) {
                    continue;
                }
                this.m_log.logStackTrace(5, this.m_poolName + " watchEvent() error releasing(" + obj + ") = " + obj2, obj2);
            }
        }
    }
    
    public String toString() {
        return this.m_poolName;
    }
    
    public int appObjectCount() {
        int n = 0;
        if (null != this.m_proxyPool) {
            synchronized (this.m_proxyPool) {
                final Iterator iterator = this.m_proxyPool.values().iterator();
                while (iterator.hasNext()) {
                    if (iterator.next() instanceof WSAAppObject) {
                        ++n;
                    }
                }
            }
        }
        return n;
    }
    
    public int subappObjectCount() {
        int n = 0;
        if (null != this.m_proxyPool) {
            synchronized (this.m_proxyPool) {
                final Iterator iterator = this.m_proxyPool.values().iterator();
                while (iterator.hasNext()) {
                    if (iterator.next() instanceof WSASubAppObject) {
                        ++n;
                    }
                }
            }
        }
        return n;
    }
    
    public int procObjectCount() {
        int n = 0;
        if (null != this.m_proxyPool) {
            synchronized (this.m_proxyPool) {
                final Iterator iterator = this.m_proxyPool.values().iterator();
                while (iterator.hasNext()) {
                    if (iterator.next() instanceof WSAProcObject) {
                        ++n;
                    }
                }
            }
        }
        return n;
    }
    
    public int appserverConnectionCount() {
        int n = 0;
        if (null != this.m_proxyPool) {
            synchronized (this.m_proxyPool) {
                for (final WSAAppObject next : this.m_proxyPool.values()) {
                    if (next instanceof WSAAppObject) {
                        n += next.sessionCount();
                    }
                }
            }
        }
        return n;
    }
    
    private WSAProxyObject getPoolObject(final String str) throws ProxyPoolException {
        if (!this.m_proxyPool.containsKey(str)) {
            if (this.m_log.ifLogExtended(32L, 5) & this.m_log.ifLogExtended(1024L, 10)) {
                this.m_log.logExtended(5, this.m_poolName + " " + str + " not found");
            }
            throw new ProxyObjectNotFoundException(str);
        }
        final WSAProxyObject wsaProxyObject = this.m_proxyPool.get(str);
        wsaProxyObject.set_tsLastAccessed();
        return wsaProxyObject;
    }
    
    private long staleObjectTimeout() {
        return (long)this.m_properties.getProperty("PROGRESS.Session.staleO4GLObjectTimeout") * 1000L;
    }
    
    private void startWatchDog() {
        if (this.staleObjectTimeout() > 0L) {
            if (this.m_log.ifLogExtended(32L, 5) & this.m_log.ifLogExtended(1024L, 10)) {
                this.m_log.logExtended(5, this.m_poolName + " starting staleObject watchdog (timeout= " + this.staleObjectTimeout() / 1000L + " seconds)");
            }
            (this.m_staleObjectWatchdog = new WatchDog(this.m_poolName + "staleObjectWatchdog", this, this.staleObjectTimeout(), 6, this.m_log)).start();
        }
        else {
            this.m_staleObjectWatchdog = null;
        }
    }
    
    public void stopWatchdog() {
        if (this.m_staleObjectWatchdog != null) {
            this.m_staleObjectWatchdog.setInterval(0L);
            this.m_staleObjectWatchdog.interrupt();
        }
    }
    
    public static class ProxyPoolException extends Open4GLException
    {
        public ProxyPoolException(final String s) {
            super("ProxyPool : %s", new Object[] { s });
        }
        
        public String getDetail() {
            return (String)this.getArgument(0);
        }
    }
    
    public static class ProxyObjectNotFoundException extends ProxyPoolException
    {
        public ProxyObjectNotFoundException(final String str) {
            super("ObjectNotFound[" + str + "]");
        }
    }
    
    public static class WrongTypeException extends ProxyPoolException
    {
        public WrongTypeException(final String str) {
            super("wrongType[" + str + "]");
        }
    }
    
    public static class SFAppObjectAlreadyExistsException extends ProxyPoolException
    {
        public SFAppObjectAlreadyExistsException(final String str) {
            super("sfAppObjectAlreadyExists[" + str + "]");
        }
    }
    
    public static class TestProxyPool extends Thread
    {
        static Object s_start_lock;
        static int s_start_cnt;
        static Object s_stop_lock;
        static int s_stop_cnt;
        String m_id;
        int m_sessionMode;
        WSAProxyPool m_pool;
        IAppLogger m_log;
        WSAAppObject m_appObj;
        WSASubAppObject m_subAppObj;
        WSAProcObject m_procObj;
        int m_iterations;
        
        TestProxyPool(final String id, final int sessionMode, final WSAAppObject appObj, final WSASubAppObject subAppObj, final WSAProcObject procObj, final int iterations, final IAppLogger log) {
            this.m_id = id;
            this.m_sessionMode = sessionMode;
            this.m_appObj = appObj;
            this.m_subAppObj = subAppObj;
            this.m_procObj = procObj;
            this.m_iterations = iterations;
            this.m_log = log;
        }
        
        public static void main(final String[] array) {
            AppLogger appLogger = new AppLogger();
            String s;
            try {
                s = array[0];
            }
            catch (Throwable t) {
                s = "testproxypool.props";
            }
            final AppRuntimeProps loadRuntimeProps = loadRuntimeProps(appLogger, s);
            int int1;
            try {
                int1 = Integer.parseInt(array[1]);
            }
            catch (Throwable t2) {
                int1 = 10;
            }
            int int2;
            try {
                int2 = Integer.parseInt(array[2]);
            }
            catch (Throwable t3) {
                int2 = 100;
            }
            try {
                Integer.parseInt(array[3]);
            }
            catch (Throwable t4) {}
            final String appServiceURL = appServiceURL(loadRuntimeProps);
            final int intProp = getIntProp(loadRuntimeProps, "PROGRESS.Session.sessionModel");
            final int intProp2 = getIntProp(loadRuntimeProps, "PROGRESS.Session.serviceLoggingLevel");
            final String stringProp = getStringProp(loadRuntimeProps, "PROGRESS.Session.serviceLoggingEntryTypes");
            TestProxyPool.s_start_cnt = int1;
            TestProxyPool.s_stop_cnt = int1;
            try {
                appLogger = new AppLogger("TestProxyPool.log", 1, intProp2, 0L, 0, stringProp, (intProp == 0) ? "SM" : "SF", "Wsa");
                final WSAProxyPool wsaProxyPool = new WSAProxyPool("TestProxyPool", loadRuntimeProps, appLogger, intProp);
                RunTimeProperties.setWaitIfBusy();
                final String wsaObjectID = wsaProxyPool.newAppObject(newRequestID(), appServiceURL, "", "", "").getWSAObjectID();
                appLogger.logWriteMessage(3, 3, appLogger.getExecEnvId(), "main", "appObjectID= " + wsaObjectID + "   getSFAppObjectID()= " + wsaProxyPool.getSFAppObjectID());
                final String wsaObjectID2 = wsaProxyPool.newSubAppObject(wsaObjectID).getWSAObjectID();
                final String wsaObjectID3 = wsaProxyPool.newProcObject(newRequestID(), wsaObjectID, "changeValProc.p", new ParameterSet(0)).getWSAObjectID();
                final TestProxyPool[] array2 = new TestProxyPool[int1];
                for (int i = 0; i < array2.length; ++i) {
                    (array2[i] = new TestProxyPool("C-" + i, intProp, (WSAAppObject)wsaProxyPool.getProxyObject(wsaObjectID), (WSASubAppObject)wsaProxyPool.getProxyObject(wsaObjectID2), (WSAProcObject)wsaProxyPool.getProxyObject(wsaObjectID3), int2, appLogger)).start();
                }
                waitAtFinishLine(appLogger);
                wsaProxyPool.getProxyObject(wsaObjectID3)._release();
                wsaProxyPool.getProxyObject(wsaObjectID2)._release();
                wsaProxyPool.getProxyObject(wsaObjectID)._release();
                wsaProxyPool._release(wsaObjectID3);
                wsaProxyPool._release(wsaObjectID2);
                wsaProxyPool._release(wsaObjectID);
                wsaProxyPool.shutdown();
            }
            catch (Exception obj) {
                obj.printStackTrace();
                appLogger.logStackTrace(5, "exception caught : " + obj, obj);
                System.exit(1);
            }
            finally {
                appLogger.logClose();
            }
        }
        
        public void run() {
            waitAtStartingGate(this.m_log);
            try {
                this.m_log.logWriteMessage(3, 2, this.m_log.getExecEnvId(), this.m_id, "Begin (reps= " + this.m_iterations + ")");
                final long currentTimeMillis = System.currentTimeMillis();
                int i;
                for (i = 0; i < this.m_iterations; ++i) {
                    final ParameterSet set = new ParameterSet(1);
                    set.setStringParameter(1, fillParm(500), 3);
                    final RqContext runProcedure = this.m_appObj.runProcedure(newRequestID(), "bigping.p", set);
                    final String s = (String)set.getOutputParameter(1);
                    final String str = (this.m_sessionMode == 0) ? this.m_appObj._getReturnValue() : runProcedure._getReturnValue();
                    if (this.m_log.ifLogLevel(3)) {
                        this.m_log.logWriteMessage(3, 3, this.m_log.getExecEnvId(), this.m_id, i + " : appObject    : return= " + str + " outval length= " + s.length());
                    }
                    if (this.m_sessionMode == 1) {
                        runProcedure._release();
                    }
                    final RqContext runProcedure2 = this.m_subAppObj.runProcedure(newRequestID(), "ping.p", new ParameterSet(0));
                    final String str2 = (this.m_sessionMode == 0) ? this.m_subAppObj._getReturnValue() : runProcedure2._getReturnValue();
                    if (this.m_log.ifLogLevel(3)) {
                        this.m_log.logWriteMessage(3, 3, this.m_log.getExecEnvId(), this.m_id, i + " : subAppObject : return " + str2);
                    }
                    if (this.m_sessionMode == 1) {
                        runProcedure2._release();
                    }
                    final ParameterSet set2 = new ParameterSet(1);
                    set2.setStringParameter(1, fillParm(20), 3);
                    this.m_procObj.runProcedure(newRequestID(), "CHARACTERcv", set2);
                    final String str3 = (String)set2.getOutputParameter(1);
                    if (this.m_log.ifLogLevel(3)) {
                        this.m_log.logWriteMessage(3, 3, this.m_log.getExecEnvId(), this.m_id, i + " : procObject   : outval= " + str3);
                    }
                    Thread.yield();
                }
                final long lng = System.currentTimeMillis() - currentTimeMillis;
                final float f = lng / (float)this.m_iterations;
                final float f2 = 1000.0f * (this.m_iterations / (float)lng);
                this.m_log.logWriteMessage(3, 2, this.m_log.getExecEnvId(), this.m_id, "Done ...     cnt= " + i);
                this.m_log.logWriteMessage(3, 2, this.m_log.getExecEnvId(), this.m_id, "Elapsed Time    = " + lng + " ms");
                this.m_log.logWriteMessage(3, 2, this.m_log.getExecEnvId(), this.m_id, "Avg time per rep= " + f + " ms");
                this.m_log.logWriteMessage(3, 2, this.m_log.getExecEnvId(), this.m_id, "Reps per second = " + f2);
            }
            catch (Exception ex) {
                System.out.println(this.m_id + " : exception caught : " + ex);
                this.m_log.logStackTrace(5, this.m_id + " : exception caught : " + ex, ex);
            }
            crossFinishLine(this.m_log);
        }
        
        private static int getIntProp(final AppRuntimeProps appRuntimeProps, final String s) {
            return (int)appRuntimeProps.getProperty(s);
        }
        
        private static long getLongProp(final AppRuntimeProps appRuntimeProps, final String s) {
            return (long)appRuntimeProps.getProperty(s);
        }
        
        private static String getStringProp(final AppRuntimeProps appRuntimeProps, final String s) {
            return (String)appRuntimeProps.getProperty(s);
        }
        
        private static String appServiceURL(final AppRuntimeProps appRuntimeProps) {
            final StringBuffer sb = new StringBuffer();
            sb.append(getStringProp(appRuntimeProps, "PROGRESS.Session.appServiceProtocol"));
            sb.append("://");
            sb.append(getStringProp(appRuntimeProps, "PROGRESS.Session.appServiceHost"));
            sb.append(":");
            sb.append(getIntProp(appRuntimeProps, "PROGRESS.Session.appServicePort"));
            sb.append("/");
            sb.append(getStringProp(appRuntimeProps, "PROGRESS.Session.appServiceName"));
            return sb.toString();
        }
        
        private static String fillParm(final int n) {
            final StringBuffer sb = new StringBuffer(3 * n);
            for (int i = 0; i < n; ++i) {
                sb.append(i);
                sb.append(" ");
            }
            return sb.toString();
        }
        
        private static void waitAtStartingGate(final IAppLogger appLogger) {
            synchronized (TestProxyPool.s_start_lock) {
                --TestProxyPool.s_start_cnt;
                if (TestProxyPool.s_start_cnt == 0) {
                    if (appLogger.ifLogLevel(4)) {
                        appLogger.logWriteMessage(3, 4, "gate", "start", "s_start_lock.notifyAll()");
                    }
                    TestProxyPool.s_start_lock.notifyAll();
                }
                else {
                    while (TestProxyPool.s_start_cnt != 0) {
                        try {
                            if (appLogger.ifLogLevel(4)) {
                                appLogger.logWriteMessage(3, 4, "gate", "start", "s_start_lock.wait()  s_start_cnt == " + TestProxyPool.s_start_cnt);
                            }
                            TestProxyPool.s_start_lock.wait();
                            continue;
                        }
                        catch (Exception ex) {
                            if (appLogger.ifLogLevel(4)) {
                                appLogger.ehnLogStackTrace(3, 4, "gate", "start", "s_start_lock.wait() exception  s_start_cnt == " + TestProxyPool.s_start_cnt, ex);
                            }
                            return;
                        }
                        break;
                    }
                }
                if (appLogger.ifLogLevel(4)) {
                    appLogger.logWriteMessage(3, 4, "gate", "start", "waitAtStartingGate done.");
                }
            }
        }
        
        private static void crossFinishLine(final IAppLogger appLogger) {
            synchronized (TestProxyPool.s_stop_lock) {
                --TestProxyPool.s_stop_cnt;
                if (appLogger.ifLogLevel(4)) {
                    appLogger.logWriteMessage(3, 4, "gate", "cross", "crossFinishLine  s_stop_cnt == " + TestProxyPool.s_stop_cnt);
                }
                TestProxyPool.s_stop_lock.notifyAll();
            }
        }
        
        private static void waitAtFinishLine(final IAppLogger appLogger) {
            synchronized (TestProxyPool.s_stop_lock) {
                while (TestProxyPool.s_stop_cnt != 0) {
                    try {
                        if (appLogger.ifLogLevel(4)) {
                            appLogger.logWriteMessage(3, 4, "gate", "finish", "waitAtFinishLine s_stop_lock.wait()  s_stop_cnt == " + TestProxyPool.s_stop_cnt);
                        }
                        TestProxyPool.s_stop_lock.wait();
                        continue;
                    }
                    catch (Exception ex) {
                        if (appLogger.ifLogLevel(4)) {
                            appLogger.ehnLogStackTrace(3, 4, "gate", "finish", "waitAtFinishLine s_stop_lock.wait() exception  s_start_cnt == " + TestProxyPool.s_start_cnt, ex);
                        }
                        return;
                    }
                    break;
                }
                if (appLogger.ifLogLevel(4)) {
                    appLogger.logWriteMessage(3, 4, "gate", "finish", "waitAtFinishLine  done.");
                }
            }
        }
        
        private static AppRuntimeProps loadRuntimeProps(final IAppLogger appLogger, final String s) {
            final AppRuntimeProps appRuntimeProps = new AppRuntimeProps();
            try {
                final DOMParser domParser = new DOMParser();
                domParser.setFeature("http://xml.org/sax/features/namespaces", true);
                domParser.setFeature("http://apache.org/xml/features/validation/schema", true);
                domParser.setFeature("http://apache.org/xml/features/validation/schema-full-checking", true);
                domParser.parse(s);
                appRuntimeProps.readXML(((AbstractDOMParser)domParser).getDocument().getDocumentElement());
                appLogger.logWriteMessage(3, 4, "properties", "load", "loaded properties from " + s);
            }
            catch (Exception obj) {
                appLogger.ehnLogStackTrace(3, 4, "properties", "load", "Unable to load " + s + " (" + obj + ") ... using default properties", obj);
            }
            return appRuntimeProps;
        }
        
        private static void nap(final int n) {
            try {
                Thread.sleep(n * 1000);
            }
            catch (InterruptedException obj) {
                System.out.println("sleep InterruptedException: " + obj);
            }
        }
        
        private static String newRequestID() {
            return new UUID().toString();
        }
        
        static {
            TestProxyPool.s_start_lock = new Object();
            TestProxyPool.s_start_cnt = 0;
            TestProxyPool.s_stop_lock = new Object();
            TestProxyPool.s_stop_cnt = 0;
        }
    }
}
