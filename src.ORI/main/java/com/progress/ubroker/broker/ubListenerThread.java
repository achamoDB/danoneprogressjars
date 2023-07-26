// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.broker;

import com.progress.common.property.ERenegadePropertyFileChange;
import com.progress.common.networkevents.IEventObject;
import com.progress.common.networkevents.EventListener;
import java.util.StringTokenizer;
import java.io.EOFException;
import java.io.Reader;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import com.progress.ubroker.util.ObjectHolder;
import java.net.InetAddress;
import java.rmi.registry.LocateRegistry;
import com.progress.common.util.PscURLParser;
import java.rmi.NotBoundException;
import java.net.MalformedURLException;
import java.rmi.Remote;
import java.rmi.server.UnicastRemoteObject;
import com.progress.ubroker.util.ubMsg;
import com.progress.ubroker.util.Queue;
import com.progress.ubroker.util.Request;
import com.progress.ubroker.util.ubAdminMsg;
import com.progress.chimera.util.SerializableEnumeration;
import com.progress.ubroker.util.ubThreadStats;
import java.util.Vector;
import java.net.Socket;
import com.progress.ubroker.util.ubThread;
import com.progress.ubroker.util.ubParentWatchDog;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.rmi.RemoteException;
import com.progress.common.networkevents.IEventListener;
import com.progress.ubroker.util.LogEvntHandler;
import com.progress.ubroker.util.ubRMIWatchDog;
import com.progress.common.networkevents.EventBroker;
import com.progress.ubroker.management.UBrokerVST;
import com.progress.common.networkevents.IEventInterestObject;
import com.progress.common.ehnlog.ILogEvntHandler;
import com.progress.ubroker.util.ubFileWatchDog;
import com.progress.ubroker.util.ubWatchDog;
import com.progress.nameserver.broker.NameServerBroker;
import com.progress.ubroker.tools.UBRemoteAdapter;
import com.progress.ubroker.util.ubProperties;
import com.progress.common.ehnlog.IAppLogger;
import com.progress.ubroker.util.RequestQueue;
import com.progress.ubroker.management.IRemoteManagerConst;
import com.progress.ubroker.management.IRemoteManager;
import com.progress.ubroker.tools.IUBRemote;
import com.progress.ubroker.util.IWatchable;
import com.progress.ubroker.util.ubConstants;

public class ubListenerThread extends Thread implements ubConstants, IWatchable, IUBRemote, IServerThreadControl, IRemoteManager, IRemoteManagerConst
{
    static final byte STATE_IDLE = 0;
    static final byte STATE_STARTING = 1;
    static final byte STATE_ACTIVE = 2;
    static final byte STATE_STOPPING = 3;
    static final byte STATE_STOPPING_FAST = 4;
    static final byte STATE_STOPPED = 5;
    static final String[] DESC_STATE;
    static final String[] DESC_STATE_EXT;
    static final int IDLESERVERSMASK = 1;
    static final String adapterDataString = ">>adapter=";
    static final String DATASEPARATOR = "|";
    String brokerName;
    int current_state;
    ubListenerPort listenerPort;
    RequestQueue rspQueue;
    int numRqsPrev;
    int queueLimit;
    IAppLogger log;
    Process adapterProcess;
    ubProperties properties;
    ubClientThreadPool clientPool;
    ubServerThreadPool serverPool;
    UBRemoteAdapter ubRemoteStub;
    String ubrmiURL;
    NameServerBroker nsBroker;
    ubWatchDog serverWatchdog;
    ubFileWatchDog serverFileWatchdog;
    ubWatchDog rmiWatchdog;
    ubWatchDog parentWatchdog;
    IAppLogger serverLog;
    private ILogEvntHandler event_handler;
    IEventInterestObject m_propChangeInterestObj;
    PropChangeListener m_propChangeListener;
    UBrokerVST ubVst;
    
    ubListenerThread(final ubProperties ubProperties, final IAppLogger appLogger) {
        this(ubProperties, appLogger, null);
    }
    
    ubListenerThread(final ubProperties properties, final IAppLogger log, final EventBroker eventBroker) {
        this.m_propChangeInterestObj = null;
        this.m_propChangeListener = null;
        this.properties = properties;
        this.listenerPort = null;
        this.brokerName = properties.brokerName;
        this.current_state = 0;
        this.setName("L-" + properties.portNum);
        this.clientPool = null;
        this.serverPool = null;
        this.numRqsPrev = 0;
        this.queueLimit = properties.getValueAsInt("queueLimit");
        this.log = log;
        this.event_handler = null;
        this.serverFileWatchdog = null;
        if ((this.properties.serverType == 1 || this.properties.serverType == 0) && this.properties.getValueAsInt("srvrLogThreshold") > 0) {
            this.serverFileWatchdog = new ubFileWatchDog(this.properties, this.log);
        }
        this.serverLog = this.properties.initServerLog(log, false);
        this.nsBroker = null;
        this.serverWatchdog = null;
        this.parentWatchdog = null;
        if (properties.rmiURL != null) {
            try {
                this.ubRemoteStub = new UBRemoteAdapter();
                if (log.ifLogBasic(2L, 1)) {
                    log.logBasic(1, "Attempting to export remote object: URL = " + properties.rmiURL);
                }
                this.ubBind(this.brokerName, properties.rmiURL);
                if (log.ifLogBasic(2L, 1)) {
                    log.logBasic(1, "Exported " + properties.rmiURL + " to RMI Registry");
                }
                if (this.ubrmiURL != null) {
                    (this.rmiWatchdog = new ubWatchDog("RMIWatchdog", new ubRMIWatchDog(this, this.ubrmiURL, log), 60000L, this.properties.getValueAsInt("watchdogThreadPriority"), log)).start();
                }
            }
            catch (Exception ex) {
                log.logError(7665689515738013546L, new Object[] { properties.rmiURL });
                log.logClose();
                System.exit(1);
            }
        }
        this.rspQueue = new RequestQueue(this.getName(), 0, log);
        if (this.properties.getValueAsInt("brkrLogThreshold") > 0 && log != null) {
            this.event_handler = new LogEvntHandler(this.properties, this.log);
            if (!log.registerThresholdEventHandler(this.event_handler)) {
                this.event_handler = null;
            }
        }
        try {
            if (eventBroker != null) {
                this.m_propChangeListener = new PropChangeListener(this.properties, log);
                this.m_propChangeInterestObj = eventBroker.expressInterest(ERenegadePropertyFileChange.class, this.m_propChangeListener, eventBroker.openEventStream("Event Stream for " + this.brokerName));
            }
        }
        catch (Throwable t) {
            if (log.ifLogBasic(2L, 1)) {
                log.logBasic(1, "Unable to start property change listener");
            }
        }
        this.properties.setListenerThread(this);
    }
    
    public void setAutoTrimTimeout(final int n) {
        if (this.serverWatchdog != null) {
            this.serverWatchdog.setInterval(1000 * n);
            if (n == 0) {
                this.serverWatchdog = null;
            }
        }
        else if (n > 0) {
            (this.serverWatchdog = new ubWatchDog("serverWatchdog", this, 1000 * n, this.properties.getValueAsInt("watchdogThreadPriority"), this.log)).start();
        }
    }
    
    public int invokeCommand(final int n, final Object[] array) throws RemoteException {
        int n2 = 0;
        switch (n) {
            case 5: {
                n2 = this.trimByRetNum((int)array[0]);
                break;
            }
            case 3: {
                n2 = this.startServersRetNum((int)array[0]);
                break;
            }
            case 2: {
                n2 = this.shutDown();
                break;
            }
            case 7: {
                n2 = this.properties.getValueAsInt("collectStatsData");
                break;
            }
        }
        return n2;
    }
    
    public Object getData(final String[] array) throws RemoteException {
        final Hashtable<String, Enumeration> hashtable = new Hashtable<String, Enumeration>();
        for (int i = 0; i < array.length; ++i) {
            if (array[i].equalsIgnoreCase("detailStatus")) {
                hashtable.put("detailStatus", this.getStatusArray(1));
            }
            else if (array[i].equalsIgnoreCase("summaryStatus")) {
                hashtable.put("summaryStatus", this.getSummaryStatusRSData());
            }
            else if (array[i].equalsIgnoreCase("nameServerConnection")) {
                hashtable.put("nameServerConnection", (Enumeration)new Long(this.nsBroker.getNameServerUnavailableDuration()));
            }
            else if (array[i].equalsIgnoreCase("registerNameServer")) {
                hashtable.put("registerNameServer", (Enumeration)new Boolean(this.properties.getValueAsBoolean("registerNameServer")));
            }
            else if (array[i].equalsIgnoreCase("collectStatsData")) {
                hashtable.put("collectStatsData", (Enumeration)new Integer(this.properties.getValueAsInt("collectStatsData")));
            }
            else if (array[i].equalsIgnoreCase("brokerLogFile")) {
                if (this.properties.getValueAsInt("brkrLogThreshold") > 0 && this.log != null) {
                    hashtable.put("brokerLogFile", (Enumeration)this.log.getCurrentLogFileName());
                }
                else {
                    hashtable.put("brokerLogFile", (Enumeration)this.properties.getValueAsString("brokerLogFile"));
                }
            }
            else if (array[i].equalsIgnoreCase("srvrLogFile")) {
                hashtable.put("srvrLogFile", (Enumeration)this.properties.getValueAsString("srvrLogFile"));
            }
            else if (array[i].equalsIgnoreCase("client summary")) {
                hashtable.put("client summary", (Enumeration)this.getStatusStructured(0, null));
            }
            else if (array[i].equalsIgnoreCase("client detail")) {
                hashtable.put("client detail", (Enumeration)this.getStatusStructured(1, new Integer(0)));
            }
            else {
                hashtable.put(array[i], (Enumeration)this.ubVst.getData(new String[] { array[i] }).get(array[i]));
            }
        }
        return hashtable;
    }
    
    public void run() {
        try {
            if (this.initializeListenerThread() == 2) {
                this.mainline();
            }
        }
        catch (Throwable t) {
            this.log.logStackTrace(7665689515738013923L, new Object[] { this.getName() }, t);
            this.current_state = 4;
        }
        this.closeListenerSocket();
        this.stopNSthread();
        if (this.current_state == 3) {
            this.processShutdown(this.rspQueue);
        }
        if (this.serverLog != this.log) {
            this.serverLog.logClose();
        }
        if (this.rmiWatchdog != null) {
            this.rmiWatchdog.setInterval(0L);
        }
        if (this.serverWatchdog != null) {
            this.serverWatchdog.setInterval(0L);
        }
        if ((this.properties.serverType == 6 || this.properties.serverType == 7) && this.parentWatchdog != null) {
            this.parentWatchdog.setInterval(0L);
        }
        if ((this.properties.serverType == 1 || this.properties.serverType == 0) && this.serverFileWatchdog != null) {
            this.serverFileWatchdog.close();
        }
        this.serverLog = null;
        this.event_handler = null;
        this.current_state = 5;
        if (JavaServices.service != null) {
            JavaServices.shutDown();
        }
        if (this.ubrmiURL != null) {
            this.shutdownRMI();
        }
    }
    
    private int initializeListenerThread() {
        if (this.log.ifLogBasic(1L, 0)) {
            this.log.logBasic(0, 7665689515738013547L, new Object[] { this.getName() });
        }
        this.current_state = 1;
        if (this.log.ifLogVerbose(1L, 0)) {
            this.properties.print(this.log, 3, 0);
        }
        if (this.serverFileWatchdog != null) {
            this.serverFileWatchdog.start();
        }
        if (this.properties.getValueAsInt("mqEnabled") > 0 && !this.launchServerConnectAdapter()) {
            return 3;
        }
        this.serverPool = new ubServerThreadPool("serverPool", this.properties.getValueAsInt("minSrvrInstance"), this.properties.getValueAsInt("maxSrvrInstance"), this.properties.getValueAsInt("minIdleServers"), this.properties.getValueAsInt("maxIdleServers"), this.properties, this.serverLog, this.log);
        this.clientPool = new ubClientThreadPool("clientPool", 0, this.properties.getValueAsInt("maxClientInstance"), 0, this.properties.getValueAsInt("maxClientInstance"), this.properties, this.serverPool, this, this.log);
        this.ubVst = new UBrokerVST(this.serverPool, this.clientPool, this.log, this.properties);
        try {
            this.listenerPort = new ubListenerPort(this.properties, this.log);
            if (this.properties.portNum == 0) {
                this.properties.portNum = this.listenerPort.getLocalPort();
                this.notifyClient(">>adapter=" + this.properties.portNum + "|" + this.properties.brokerPid);
            }
        }
        catch (IOException ex) {
            this.log.logStackTrace(7665689515738013548L, new Object[] { new Integer(this.properties.portNum) }, ex);
            this.current_state = 5;
            this.shutdownRMI();
            return this.current_state;
        }
        if (!this.startServerThreads(this.properties.getValueAsInt("initialSrvrInstance"), this.rspQueue)) {
            this.closeListenerSocket();
            this.current_state = 5;
            this.shutdownRMI();
            return this.current_state;
        }
        if (this.properties.getValueAsInt("autoTrimTimeout") > 0) {
            (this.serverWatchdog = new ubWatchDog("serverWatchdog", this, 1000 * this.properties.getValueAsInt("autoTrimTimeout"), this.properties.getValueAsInt("watchdogThreadPriority"), this.log)).start();
        }
        if ((this.properties.serverType == 6 || this.properties.serverType == 7) && this.properties.parentPID > 0) {
            (this.parentWatchdog = new ubWatchDog("parentWatchdog", new ubParentWatchDog(this, this.properties, this.log), this.properties.getValueAsInt("parentWatchdogInterval"), this.properties.getValueAsInt("watchdogThreadPriority"), this.log)).start();
        }
        return this.current_state = (this.startNSthread() ? 2 : 3);
    }
    
    private void mainline() {
        if (this.log.ifLogVerbose(1L, 0)) {
            this.log.logVerbose(0, 7665689515738013549L, new Object[0]);
        }
        while (this.current_state == 2) {
            if (this.serverPool.size() == 0 && this.log.ifLogBasic(2L, 1)) {
                this.log.logBasic(1, "WARNING: serverPool is Empty!");
            }
            final Socket listen = this.listenerPort.listen();
            if (listen == null) {
                this.current_state = 3;
                break;
            }
            if (this.isInterrupted()) {
                if (this.log.ifLogBasic(2L, 1)) {
                    this.log.logBasic(1, this.getName() + " isInterrupted() ...");
                    break;
                }
                break;
            }
            else if (this.current_state != 2) {
                if (this.log.ifLogBasic(2L, 1)) {
                    this.log.logBasic(1, this.getName() + " exiting ... ");
                    break;
                }
                break;
            }
            else {
                final ubClientThread ubClientThread;
                if ((ubClientThread = (ubClientThread)this.clientPool.dequeueThreadInState(0, null, 0L)) == null) {
                    if (this.clientPool.numThreadsAllowed() == 0) {
                        this.log.logError(7665689515738014063L, new Object[0]);
                        try {
                            listen.close();
                        }
                        catch (IOException ex) {
                            this.log.logError(7665689515738014064L, new Object[] { "", ex.toString(), ex.getMessage() });
                        }
                    }
                    else {
                        try {
                            final ubClientThread ubClientThread2 = (ubClientThread)this.clientPool.createThread(0);
                            ubClientThread2.start();
                            this.activateIdleThread(this.clientPool, true, new Object[] { listen }, this.rspQueue);
                            if (listen.getInetAddress() == null) {
                                continue;
                            }
                            ubClientThread2.saveSocketName = listen.getInetAddress().getHostName();
                        }
                        catch (Throwable t) {
                            this.log.logError(7665689515738014063L, new Object[0]);
                            try {
                                listen.close();
                            }
                            catch (IOException ex2) {
                                this.log.logError(7665689515738014064L, new Object[] { "", ex2.toString(), ex2.getMessage() });
                            }
                        }
                    }
                }
                else {
                    this.activateSpecificThread(ubClientThread, true, new Object[] { listen }, this.rspQueue);
                }
            }
        }
    }
    
    public void watchEvent() {
        final int maxBusyThreads = this.serverPool.getMaxBusyThreads();
        final int size = this.serverPool.size();
        final int minThreads = this.serverPool.getMinThreads();
        final int n = size - Math.max(maxBusyThreads, minThreads);
        if (this.log.ifLogVerbose(4096L, 12)) {
            this.log.logVerbose(12, "numThreads= " + size + " maxBusyThreads= " + maxBusyThreads + " minThreads= " + minThreads + " nTrimmable= " + n);
        }
        if (n > 0) {
            if (this.log.ifLogBasic(4096L, 12)) {
                this.log.logBasic(12, "Currently running " + size + " servers ... " + "Max busy in interval= " + maxBusyThreads);
                this.log.logBasic(12, "Attempting to trim " + n + " server(s)");
            }
            final int trimServerThreads = this.trimServerThreads(n, new RequestQueue("watchDog-rspQueue", 0, this.log));
            if (this.log.ifLogBasic(4096L, 12)) {
                this.log.logBasic(12, "Trimmed " + trimServerThreads + " server(s)");
            }
        }
        this.serverPool.resetMaxBusyThreads();
    }
    
    public int stop_Servers(final int n, final RequestQueue requestQueue) {
        final int n2 = this.serverPool.size() - n;
        return (n2 <= 0 || !this.stopServerThreads(n2, requestQueue)) ? 1 : 0;
    }
    
    public int stop_Server(final int n, final RequestQueue requestQueue) {
        return this.stopSpecificServerThread(n, requestQueue) ? 0 : 1;
    }
    
    public int start_Servers(final int n, final RequestQueue requestQueue) {
        final int numThreadsAllowed = this.serverPool.numThreadsAllowed();
        return this.startServerThreads((n < numThreadsAllowed) ? n : numThreadsAllowed, requestQueue) ? 0 : 1;
    }
    
    public ubThread start_ServerWithArgs(final Object[] array, final RequestQueue requestQueue) {
        this.serverPool.numThreadsAllowed();
        final ubThread thread = this.serverPool.createThread(2);
        if (thread == null) {
            return thread;
        }
        thread.start();
        return this.activateSpecificThread(thread, true, array, requestQueue) ? thread : null;
    }
    
    public String queryBroker(final int n) {
        String s;
        try {
            s = this.getStatusFormatted(n);
        }
        catch (IOException obj) {
            s = "IOException : " + obj + " : " + obj.getMessage();
        }
        return s;
    }
    
    public int requestShutdown() {
        int n = 0;
        switch (this.current_state) {
            case 5: {
                if (this.log.ifLogVerbose(1L, 0)) {
                    this.log.logVerbose(0, 7665689515738013552L, new Object[0]);
                }
                n = 0;
                break;
            }
            case 3: {
                if (this.log.ifLogBasic(2L, 1)) {
                    this.log.logBasic(1, "Shutdown pending.");
                }
                n = 1;
                break;
            }
            default: {
                if (this.log.ifLogExtended(1L, 0) || this.log.ifLogBasic(256L, 8) || this.log.ifLogBasic(512L, 9)) {
                    this.logBrokerState("Normal Shutdown requested");
                }
                this.current_state = 3;
                this.wakeListenerPort();
                n = 1;
                break;
            }
        }
        return n;
    }
    
    public void processShutdown(final RequestQueue requestQueue) {
        if (this.log.ifLogBasic(1L, 0)) {
            this.log.logBasic(0, 7665689515738013553L, new Object[0]);
        }
        final ubThread[] enumerateThreads = this.clientPool.enumerateThreads();
        final int length = enumerateThreads.length;
        if (this.log.ifLogVerbose(1L, 0)) {
            this.log.logVerbose(0, 7665689515738013554L, new Object[] { new Integer(length) });
        }
        int i = 0;
        int n = 0;
        while (i < length) {
            final ubThread ubThread = enumerateThreads[i];
            if (this.log.ifLogVerbose(1L, 0)) {
                this.log.logVerbose(0, 7665689515738013555L, new Object[] { ubThread.getFullName() });
            }
            if (this.requestThreadShutdown(ubThread, requestQueue)) {
                ++n;
            }
            ++i;
        }
        for (int j = 0; j < n; ++j) {
            this.waitAdminRsp(requestQueue);
        }
        final ubThread[] enumerateThreads2 = this.serverPool.enumerateThreads();
        final int length2 = enumerateThreads2.length;
        int k = 0;
        int n2 = 0;
        while (k < length2) {
            final ubThread ubThread2 = enumerateThreads2[k];
            if (this.log.ifLogBasic(1L, 0)) {
                this.log.logBasic(0, 7665689515738013555L, new Object[] { ubThread2.getFullName() });
            }
            if (this.requestThreadShutdown(ubThread2, requestQueue)) {
                ++n2;
            }
            ++k;
        }
        if (this.log.ifLogVerbose(1L, 0)) {
            this.log.logVerbose(0, 7665689515738013556L, new Object[] { new Integer(length2) });
        }
        for (int l = 0; l < n2; ++l) {
            this.waitAdminRsp(requestQueue);
        }
        this.current_state = 5;
    }
    
    public int requestEmergencyShutdown() {
        if (this.log.ifLogVerbose(1L, 0)) {
            this.log.logVerbose(0, 7665689515738013926L, new Object[0]);
        }
        if (this.current_state == 3 || this.current_state == 4) {
            this.log.logError("Emergency shutdown (last resort) initiated.");
            this.logBrokerState("Emergency shutdown initiated");
            this.log.logClose();
            System.exit(1);
        }
        this.logBrokerState("Emergency shutdown requested");
        this.current_state = 4;
        this.wakeListenerPort();
        return 0;
    }
    
    public int shutDown() throws RemoteException {
        return this.requestShutdown();
    }
    
    public int emergencyShutdown() throws RemoteException {
        return this.requestEmergencyShutdown();
    }
    
    public int trimTo(final int n) throws RemoteException {
        final int n2 = this.serverPool.size() - n;
        final RequestQueue requestQueue = new RequestQueue("RMI-trimTo-rspQ", 0, this.log);
        return (n2 <= 0 || this.trimServerThreads(n2, requestQueue) != n2) ? 1 : 0;
    }
    
    public int trimBy(final int n) throws RemoteException {
        final int size = this.serverPool.size();
        final int n2 = (size > n) ? n : size;
        final RequestQueue requestQueue = new RequestQueue("RMI-trimBy-rspQ", 0, this.log);
        return (n2 <= 0 || this.trimServerThreads(n2, requestQueue) != n2) ? 1 : 0;
    }
    
    public int trimByRetNum(final int n) {
        final int size = this.serverPool.size();
        final int n2 = (size > n) ? n : size;
        final RequestQueue requestQueue = new RequestQueue("RMI-trimBy-rspQ", 0, this.log);
        int trimServerThreads = -1;
        if (n2 >= 0) {
            trimServerThreads = this.trimServerThreads(n2, requestQueue);
        }
        return trimServerThreads;
    }
    
    public boolean stopServer(final int n) throws RemoteException {
        return this.stopSpecificServerThread(n, new RequestQueue("RMI-stopServer-rspQ", 0, this.log));
    }
    
    public int startServers(final int n) throws RemoteException {
        final int numThreadsAllowed = this.serverPool.numThreadsAllowed();
        return this.startServerThreads((n < numThreadsAllowed) ? n : numThreadsAllowed, new RequestQueue("RMI-startServers-rspQ", 0, this.log)) ? 0 : 1;
    }
    
    public int startServersRetNum(final int n) {
        final int numThreadsAllowed = this.serverPool.numThreadsAllowed();
        final int n2 = (n < numThreadsAllowed) ? n : numThreadsAllowed;
        if (this.startServerThreads(n2, new RequestQueue("RMI-startServers-rspQ", 0, this.log))) {
            return n2;
        }
        return -1;
    }
    
    public String getStatusFormatted(final int n) throws RemoteException {
        final StringBuffer sb = new StringBuffer(4096);
        switch (n) {
            case 0: {
                final String[] ubSummaryStatusLabels = this.getUBSummaryStatusLabels();
                final String[] ubSummaryStatusInfo = this.getUBSummaryStatusInfo();
                for (int i = 0; i < 14; ++i) {
                    sb.append(ubSummaryStatusLabels[i] + " : " + ubSummaryStatusInfo[i] + "\n");
                }
                break;
            }
            case 1: {
                final String[] ubDetailStatusLabels = this.getUBDetailStatusLabels();
                for (int j = 0; j < 8; ++j) {
                    sb.append(ubDetailStatusLabels[j] + " ");
                }
                sb.append("\n");
                if (this.serverPool == null) {
                    break;
                }
                final ubThread[] enumerateThreads = this.serverPool.enumerateThreads();
                for (int k = 0; k < enumerateThreads.length; ++k) {
                    final String[] ubDetailStatusInfo = this.getUBDetailStatusInfo((ubServerThread)enumerateThreads[k]);
                    for (int l = 0; l < 8; ++l) {
                        sb.append(ubDetailStatusInfo[l] + " ");
                    }
                    sb.append("\n");
                }
                if (JavaServices.service != null) {
                    sb.append(JavaServices.service.toString());
                    break;
                }
                break;
            }
            case 3: {
                sb.append(ubListenerThread.DESC_STATE_EXT[this.current_state]);
                break;
            }
            default: {
                sb.append("Status level not yet implemented\n");
                break;
            }
        }
        return sb.toString();
    }
    
    public Hashtable getStatusStructured(final int n, final Object o) throws RemoteException {
        Hashtable<String, String> hashtable = new Hashtable<String, String>();
        switch (n) {
            case 3: {
                hashtable = (Hashtable<String, String>)this.properties.list((String)o, null);
                break;
            }
            case 4: {
                hashtable = (Hashtable<String, String>)this.properties.list();
                break;
            }
            case 0: {
                this.processConnStatus(n, 0, hashtable);
                break;
            }
            case 1: {
                this.processConnStatus(n, (int)o, hashtable);
                break;
            }
            case 2: {
                hashtable.put("table name", "debug");
                break;
            }
            default: {
                hashtable.put("table name", "unknown");
                break;
            }
        }
        return hashtable;
    }
    
    private int processConnStatus(final int n, final int n2, final Hashtable hashtable) {
        final int n3 = 0;
        int value = 0;
        final Vector value2 = new Vector();
        final Hashtable hashtable2 = new Hashtable();
        final ubThread[] enumerateThreads = this.clientPool.enumerateThreads();
        for (int i = 0; i < enumerateThreads.length; ++i) {
            final ubThread ubThread = enumerateThreads[i];
            final ubThreadStats stats = ubThread.getStats();
            if (stats.getConnRmtHost() != null && (n2 == 0 || stats.getConnHdl() == n2)) {
                final Hashtable<String, Integer> hashtable3 = new Hashtable<String, Integer>();
                hashtable3.put("conn hdl", new Integer(stats.getConnHdl()));
                if (stats.getConnUserName() != null) {
                    hashtable3.put("user name", (Integer)stats.getConnUserName());
                }
                hashtable3.put("remote addr", (Integer)stats.getConnRmtHost());
                hashtable3.put("remote port", new Integer(stats.getConnRmtPort()));
                hashtable3.put("connection state", (Integer)ubThreadStats.getConnStateDesc(ubThread.getConnState()));
                if (n == 1) {
                    if (stats.getConnID() != null) {
                        hashtable3.put("conn ID", (Integer)stats.getConnID());
                    }
                    hashtable3.put("request count", new Integer(stats.getConnRqs()));
                    final int connServerPID;
                    if ((connServerPID = stats.getConnServerPID()) != 0) {
                        hashtable3.put("agent PID", new Integer(connServerPID));
                    }
                    final int connServerPort;
                    if ((connServerPort = stats.getConnServerPort()) != 0) {
                        hashtable3.put("agent port", new Integer(connServerPort));
                    }
                }
                this.insertSortConnTbl(value2, hashtable3);
                ++value;
            }
        }
        if (this.properties.serverMode == 1 || this.properties.serverMode == 2) {
            final ubThread[] enumerateThreads2 = this.serverPool.enumerateThreads();
            for (int j = 0; j < enumerateThreads2.length; ++j) {
                final ubThread ubThread2 = enumerateThreads2[j];
                final ubThreadStats stats2 = ubThread2.getStats();
                if (stats2.getConnRmtHost() != null && (n2 == 0 || stats2.getConnHdl() == n2)) {
                    final Hashtable<String, Integer> hashtable4 = new Hashtable<String, Integer>();
                    hashtable4.put("conn hdl", new Integer(stats2.getConnHdl()));
                    if (stats2.getConnUserName() != null) {
                        hashtable4.put("user name", (Integer)stats2.getConnUserName());
                    }
                    hashtable4.put("remote addr", (Integer)stats2.getConnRmtHost());
                    hashtable4.put("remote port", new Integer(stats2.getConnRmtPort()));
                    hashtable4.put("connection state", (Integer)ubThreadStats.getConnStateDesc(ubThread2.getConnState()));
                    if (n == 1) {
                        if (stats2.getConnID() != null) {
                            hashtable4.put("conn ID", (Integer)stats2.getConnID());
                        }
                        hashtable4.put("request count", new Integer(stats2.getConnRqs()));
                        final int connServerPID2;
                        if ((connServerPID2 = stats2.getConnServerPID()) != 0) {
                            hashtable4.put("agent PID", new Integer(connServerPID2));
                        }
                        final int connServerPort2;
                        if ((connServerPort2 = stats2.getConnServerPort()) != 0) {
                            hashtable4.put("agent port", new Integer(connServerPort2));
                        }
                    }
                    this.insertSortConnTbl(value2, hashtable4);
                    ++value;
                }
            }
        }
        hashtable.put("table name", (n == 1) ? "client detail" : "client summary");
        hashtable.put("num connections", new Integer(value));
        hashtable.put("table data", value2);
        return n3;
    }
    
    private int insertSortConnTbl(final Vector vector, final Hashtable element) {
        final int n = 0;
        int size;
        Integer n2;
        int n3;
        for (size = vector.size(), n2 = element.get("conn hdl"), n3 = 0; n3 < size && n2.compareTo(vector.elementAt(n3).get("conn hdl")) >= 0; ++n3) {}
        vector.add(n3, element);
        return n;
    }
    
    public Enumeration getStatusArray(final int n) throws RemoteException {
        final Vector<String[]> vector = new Vector<String[]>();
        if (this.serverPool != null) {
            final ubThread[] enumerateThreads = this.serverPool.enumerateThreads();
            for (int length = enumerateThreads.length, i = 0; i < length; ++i) {
                vector.addElement(this.getUBDetailStatusInfo((ubServerThread)enumerateThreads[i]));
            }
        }
        return new SerializableEnumeration(vector);
    }
    
    public long ping() throws RemoteException {
        return this.ubRemoteStub.ping();
    }
    
    public Enumeration getSummaryStatusRSFieldLabels() throws RemoteException {
        final Vector<String> vector = new Vector<String>();
        final String[] ubSummaryStatusLabels = this.getUBSummaryStatusLabels();
        for (int i = 0; i < ubSummaryStatusLabels.length; ++i) {
            vector.addElement(ubSummaryStatusLabels[i]);
        }
        return new SerializableEnumeration(vector);
    }
    
    public Enumeration getDetailStatusRSFieldLabels() throws RemoteException {
        final Vector<String> vector = new Vector<String>();
        final String[] ubDetailStatusLabels = this.getUBDetailStatusLabels();
        for (int i = 0; i < ubDetailStatusLabels.length; ++i) {
            vector.addElement(ubDetailStatusLabels[i]);
        }
        return new SerializableEnumeration(vector);
    }
    
    public Enumeration getSummaryStatusRSData() throws RemoteException {
        final Vector<String> vector = new Vector<String>();
        final String[] ubSummaryStatusInfo = this.getUBSummaryStatusInfo();
        for (int i = 0; i < ubSummaryStatusInfo.length; ++i) {
            vector.addElement(ubSummaryStatusInfo[i]);
        }
        return new SerializableEnumeration(vector);
    }
    
    private synchronized boolean startServerThreads_prev(final int n, final RequestQueue requestQueue) {
        boolean b = true;
        if (this.current_state != 2 && this.current_state != 1) {
            return false;
        }
        if (n <= 0) {
            return b;
        }
        if (this.log.ifLogBasic(1L, 0)) {
            this.log.logBasic(0, 7665689515738013557L, new Object[] { new Integer(n) });
        }
        for (int i = 0; i < n; ++i) {
            ((ubServerThread)this.serverPool.createThread(0)).start();
            this.sleepHereMs(100);
        }
        if (this.log.ifLogVerbose(1L, 0)) {
            this.log.logVerbose(0, 7665689515738013558L, new Object[] { new Integer(n) });
        }
        for (int j = 0; j < n; ++j) {
            this.activateIdleThread(this.serverPool, false, null, requestQueue);
            this.sleepHereMs(1000);
        }
        for (int k = 0; k < n; ++k) {
            if (!this.waitAdminRsp(requestQueue)) {
                b = false;
            }
        }
        return b;
    }
    
    private synchronized boolean startServerThreads(final int n, final RequestQueue requestQueue) {
        boolean b = true;
        if (this.current_state != 2 && this.current_state != 1) {
            return false;
        }
        if (n <= 0) {
            return b;
        }
        if (this.log.ifLogBasic(1L, 0)) {
            this.log.logBasic(0, 7665689515738013557L, new Object[] { new Integer(n) });
        }
        final ubServerThread[] array = new ubServerThread[n];
        for (int i = 0; i < n; ++i) {
            (array[i] = (ubServerThread)this.serverPool.createThread(0)).start();
            this.sleepHereMs(100);
        }
        if (this.log.ifLogVerbose(1L, 0)) {
            this.log.logVerbose(0, 7665689515738013558L, new Object[] { new Integer(n) });
        }
        for (int j = 0; j < n; ++j) {
            this.activateSpecificThread(array[j], false, null, requestQueue);
            this.sleepHereMs(100);
        }
        for (int k = 0; k < n; ++k) {
            if (!this.waitAdminRsp(requestQueue)) {
                b = false;
            }
        }
        return b;
    }
    
    private synchronized boolean stopServerThreads(int n, final RequestQueue requestQueue) {
        boolean b = true;
        final ubThread[] enumerateThreads = this.serverPool.enumerateThreads();
        n = ((enumerateThreads.length < n) ? enumerateThreads.length : n);
        int i = 0;
        int n2 = 0;
        while (i < n) {
            final ubThread ubThread = enumerateThreads[i];
            if (this.log.ifLogBasic(1L, 0)) {
                this.log.logBasic(0, 7665689515738013555L, new Object[] { ubThread.getFullName() });
            }
            if (this.requestThreadShutdown(ubThread, requestQueue)) {
                ++n2;
            }
            ++i;
        }
        for (int j = 0; j < n2; ++j) {
            if (!this.waitAdminRsp(requestQueue)) {
                b = false;
            }
        }
        return b;
    }
    
    private synchronized int trimServerThreads(final int n, final RequestQueue requestQueue) {
        int i;
        for (i = 0; i < n; ++i) {
            final ubThread dequeueThreadInState = this.serverPool.dequeueThreadInState(1, null, 0L);
            if (dequeueThreadInState == null) {
                break;
            }
            if (this.log.ifLogBasic(1L, 0)) {
                this.log.logBasic(0, 7665689515738013555L, new Object[] { dequeueThreadInState.getFullName() });
            }
            if (this.requestThreadShutdown(dequeueThreadInState, requestQueue)) {
                this.waitAdminRsp(requestQueue);
            }
        }
        return i;
    }
    
    private synchronized boolean stopSpecificServerThread(final int value, final RequestQueue requestQueue) {
        ubServerThread ubServerThread = null;
        boolean waitAdminRsp = true;
        ubThread[] enumerateThreads;
        int i;
        for (enumerateThreads = this.serverPool.enumerateThreads(), i = 0; i < enumerateThreads.length; ++i) {
            ubServerThread = (ubServerThread)enumerateThreads[i];
            if (ubServerThread.getServerPid() == value) {
                break;
            }
        }
        if (i < enumerateThreads.length && ubServerThread.getServerPid() == value) {
            if (this.log.ifLogBasic(1L, 0)) {
                this.log.logBasic(0, 7665689515738013560L, new Object[] { new Integer(value), ubServerThread.getFullName() });
            }
            if (this.requestThreadShutdown(ubServerThread, requestQueue)) {
                waitAdminRsp = this.waitAdminRsp(requestQueue);
            }
        }
        else {
            waitAdminRsp = false;
        }
        return waitAdminRsp;
    }
    
    private boolean startNSthread() {
        boolean b2;
        if (this.properties.nsHost != null && this.properties.getValueAsBoolean("registerNameServer")) {
            try {
                this.nsBroker = new NameServerBroker(this.properties.nsHost, this.properties.nsPortnum, this.log, this.properties);
                boolean b;
                String valueAsString;
                if (this.properties.regMode == 1) {
                    b = true;
                    valueAsString = null;
                }
                else if (this.properties.regMode == 2) {
                    b = true;
                    valueAsString = this.properties.getValueAsString("hostName");
                }
                else {
                    b = false;
                    valueAsString = null;
                }
                this.nsBroker.regBroker((short)108, this.properties.getValueAsString("uuid"), this.properties.portNum, b, valueAsString, this.properties.getValueAsInt("priorityWeight"), this.properties.getValueAsInt("registrationRetry"), this.brokerName, this.properties.getServerTypeString(), this.properties.getValueAsBoolean("defaultService"), this.properties.getValueAsStringArray("appserviceNameList"), 20);
                if (this.log.ifLogVerbose(1L, 0)) {
                    this.log.logVerbose(0, 7665689515738013561L, new Object[] { this.properties.getValueAsString("appserviceNameList"), this.properties.nsHost, new Integer(this.properties.nsPortnum) });
                }
                b2 = true;
            }
            catch (NameServerBroker.HostUnknownException ex) {
                this.log.logError(7665689515738013924L, new Object[] { ex.getMessage() });
                b2 = false;
            }
            catch (NameServerBroker.NameServerInitException ex2) {
                this.log.logError(7665689515738013924L, new Object[] { ex2.getMessage() });
                b2 = false;
            }
            catch (NameServerBroker.MarshallFailureException ex3) {
                this.log.logError(7665689515738013924L, new Object[] { ex3.getMessage() });
                b2 = false;
            }
        }
        else {
            if (this.log.ifLogVerbose(1L, 0)) {
                this.log.logVerbose(0, 7665689515738013565L, new Object[] { this.properties.getValueAsString("appserviceNameList") });
            }
            b2 = true;
        }
        return b2;
    }
    
    private void stopNSthread() {
        if (this.properties.nsHost != null && this.properties.getValueAsBoolean("registerNameServer")) {
            try {
                if (this.nsBroker != null) {
                    this.nsBroker.unRegBroker((short)108);
                }
            }
            catch (NameServerBroker.InternalConsistencyException ex) {
                this.log.logError(ex.getMessage());
            }
            catch (NameServerBroker.NameServerSendFailureException ex2) {
                this.log.logError(ex2.getMessage());
            }
            catch (NameServerBroker.MarshallFailureException ex3) {
                this.log.logError(ex3.getMessage());
            }
            if (this.log.ifLogVerbose(1L, 0)) {
                this.log.logVerbose(0, 7665689515738013568L, new Object[] { this.properties.nsHost, new Integer(this.properties.nsPortnum) });
            }
        }
    }
    
    private boolean startClientThreads(final int i) {
        final boolean b = true;
        if (this.current_state != 2 && this.current_state != 1) {
            return false;
        }
        if (i <= 0) {
            return b;
        }
        if (this.log.ifLogBasic(1L, 0)) {
            this.log.logBasic(0, "Starting " + i + " client threads");
        }
        for (int j = 0; j < i; ++j) {
            ((ubClientThread)this.clientPool.createThread(0)).start();
            this.sleepHereMs(50);
        }
        return b;
    }
    
    private void closeListenerSocket() {
        if (this.listenerPort != null) {
            this.listenerPort.close();
        }
        if (this.log.ifLogVerbose(1L, 0)) {
            this.log.logVerbose(0, 7665689515738013569L, new Object[] { this.brokerName, new Integer(this.properties.portNum) });
        }
    }
    
    private void shutdownRMI() {
        if (this.log.ifLogVerbose(1L, 0)) {
            this.log.logVerbose(0, 7665689515738013570L, new Object[0]);
        }
        try {
            this.ubUnbind();
        }
        catch (Exception ex) {}
    }
    
    private boolean activateSpecificThread(final ubThread ubThread, final boolean b, final Object[] array, final RequestQueue requestQueue) {
        boolean waitAdminRsp = true;
        final ubAdminMsg ubAdminMsg = new ubAdminMsg((byte)1);
        ubAdminMsg.setadParm(array);
        try {
            ubThread.getRcvQueue().enqueueRequest(new Request(ubAdminMsg, requestQueue));
            if (this.log.ifLogBasic(2L, 1)) {
                this.log.logBasic(1, "Enqueued rq to " + ubThread.getRcvQueue().getListName());
            }
            if (b) {
                waitAdminRsp = this.waitAdminRsp(requestQueue);
            }
        }
        catch (Queue.QueueException ex) {
            waitAdminRsp = false;
        }
        return waitAdminRsp;
    }
    
    private boolean activateIdleThread(final ubThreadPool ubThreadPool, final boolean b, final Object[] array, final RequestQueue requestQueue) {
        return this.activateSpecificThread(ubThreadPool.dequeueThreadInState(0, requestQueue, -1L), b, array, requestQueue);
    }
    
    private boolean waitAdminRsp(final RequestQueue requestQueue) {
        if (this.log.ifLogBasic(2L, 1)) {
            this.log.logBasic(1, "waiting for admin rsp at " + requestQueue.getListName());
        }
        final ubMsg obj = (ubMsg)requestQueue.dequeueRequest().getMsg();
        boolean b;
        if (obj instanceof ubAdminMsg) {
            final int getadRsp = ((ubAdminMsg)obj).getadRsp();
            b = (getadRsp == 0);
            if (!b && this.log.ifLogVerbose(1L, 0)) {
                this.log.logVerbose(0, 7665689515738013571L, new Object[] { Integer.toString(getadRsp, 16) });
            }
            if (this.log.ifLogBasic(2L, 1)) {
                obj.print("admin response received: " + obj, 2, 1, this.log);
            }
        }
        else {
            b = false;
            this.log.logError(7665689515738013572L, new Object[] { Integer.toString(obj.getubRsp(), 16) });
            if (this.log.ifLogBasic(2L, 1)) {
                obj.print("Invalid admin response received", 2, 1, this.log);
            }
        }
        return b;
    }
    
    boolean requestThreadShutdown(final ubThread ubThread, final RequestQueue requestQueue) {
        boolean b = true;
        final ubAdminMsg obj = new ubAdminMsg((byte)2);
        final RequestQueue rcvQueue = ubThread.getRcvQueue();
        if (rcvQueue != null) {
            try {
                rcvQueue.enqueueRequest(new Request(obj, requestQueue));
                if (this.log.ifLogBasic(2L, 1)) {
                    this.log.logBasic(1, "Enqueued rq to " + rcvQueue.getListName());
                }
            }
            catch (Queue.QueueException ex) {
                b = false;
            }
        }
        else {
            this.log.logError(7665689515738013573L, new Object[] { ubThread.getFullName() });
            b = false;
        }
        if (this.log.ifLogBasic(2L, 1)) {
            this.log.logBasic(1, "shut down rq : " + ubThread.getFullName() + " : " + obj + (b ? " OK." : " FAILED."));
        }
        return b;
    }
    
    private void ubBind(final String s, final String ubrmiURL) throws RemoteException, MalformedURLException {
        try {
            UnicastRemoteObject.exportObject(this);
        }
        catch (RemoteException ex) {
            this.log.logError(7665689515738014024L, new Object[] { ex.getMessage() });
            throw ex;
        }
        boolean b = false;
        int n = 0;
        try {
            do {
                ++n;
                try {
                    ubThread.rebindService(ubrmiURL, this);
                    this.ubrmiURL = ubrmiURL;
                    b = true;
                }
                catch (RemoteException ex2) {
                    this.log.logError(7665689515738014025L, new Object[] { ex2.getMessage() });
                    if (n >= this.properties.getValueAsInt("admSrvrRegisteredRetry")) {
                        throw ex2;
                    }
                    this.sleepHereMs(this.properties.getValueAsInt("admSrvrRegisteredRetryInterval"));
                }
            } while (!b && n < this.properties.getValueAsInt("admSrvrRegisteredRetry"));
        }
        catch (MalformedURLException ex3) {
            this.log.logError(7665689515738014026L, new Object[] { ex3.getMessage() });
            throw ex3;
        }
    }
    
    private void ubUnbind() throws RemoteException, NotBoundException, MalformedURLException {
        try {
            ubThread.unbindService(this.ubrmiURL);
        }
        catch (RemoteException ex) {
            this.log.logError(7665689515738014027L, new Object[] { ex.getMessage() });
            throw ex;
        }
        catch (MalformedURLException ex2) {
            this.log.logError(7665689515738014028L, new Object[] { ex2.getMessage() });
            throw ex2;
        }
        catch (NotBoundException ex3) {
            this.log.logError(7665689515738014029L, new Object[] { ex3.getMessage() });
            throw ex3;
        }
    }
    
    private void rebindServiceX(final String s) throws MalformedURLException, RemoteException {
        final PscURLParser pscURLParser = new PscURLParser(s);
        pscURLParser.setScheme(null);
        LocateRegistry.getRegistry(pscURLParser.getHost(), pscURLParser.getPort()).rebind(pscURLParser.getService(), this);
    }
    
    private static void unbindServiceX(final String s) throws MalformedURLException, RemoteException, NotBoundException {
        final PscURLParser pscURLParser = new PscURLParser(s);
        pscURLParser.setScheme(null);
        LocateRegistry.getRegistry(pscURLParser.getHost(), pscURLParser.getPort()).unbind(pscURLParser.getService());
    }
    
    private static Remote lookupServiceX(final String s) throws MalformedURLException, RemoteException, NotBoundException {
        final PscURLParser pscURLParser = new PscURLParser(s);
        pscURLParser.setScheme(null);
        return LocateRegistry.getRegistry(pscURLParser.getHost(), pscURLParser.getPort()).lookup(pscURLParser.getService());
    }
    
    private String[] getUBSummaryStatusLabels() {
        return UBrokerVST.getUBSummaryStatusSchema(this.properties.serverType);
    }
    
    private String[] getUBSummaryStatusInfo() {
        final String[] array = new String[14];
        if (this.serverPool == null) {
            return array;
        }
        final ubThreadPool.ubThreadPoolStats summaryStats = this.serverPool.getSummaryStats();
        array[0] = this.properties.brokerName;
        array[1] = this.properties.serverModeString(this.properties.serverMode);
        array[2] = ubListenerThread.DESC_STATE_EXT[this.current_state];
        array[3] = Integer.toString(this.properties.portNum);
        array[4] = Integer.toString(this.properties.brokerPid);
        array[5] = Integer.toString(summaryStats.getnumThreads());
        array[6] = Integer.toString(summaryStats.getnumBusyThreads());
        array[7] = Integer.toString(summaryStats.getnumBoundThreads());
        array[8] = Integer.toString(summaryStats.getnumReadyThreads());
        array[10] = "(" + summaryStats.getCurWaitReadyQueueDepth() + ", " + summaryStats.getMaxWaitReadyQueueDepth() + ")";
        final ubThreadPool.ubThreadPoolStats summaryStats2 = this.clientPool.getSummaryStats();
        array[9] = "(" + (summaryStats2.getnumThreads() - summaryStats2.getnumIdleThreads()) + ", " + summaryStats2.getmaxActiveThreads() + ")";
        array[11] = Integer.toString(summaryStats2.getnumRqs());
        array[12] = "(" + summaryStats2.getMaxRqWait() + " ms, " + summaryStats2.getAvgRqWait() + " ms)";
        array[13] = "(" + summaryStats2.getMaxRqDuration() + " ms, " + summaryStats2.getAvgRqDuration() + " ms)";
        return array;
    }
    
    private String[] getUBDetailStatusLabels() {
        return UBrokerVST.getUBDetailStatusSchema(this.properties.serverType);
    }
    
    private String[] getUBDetailStatusInfo(final ubServerThread ubServerThread) {
        final String[] array = new String[8];
        final ubThreadStats stats = ubServerThread.getStats();
        array[0] = ubServerThread.getFmtServerPid();
        array[1] = ubServerThread.getServerState();
        array[2] = ubServerThread.getFmtServerPort();
        array[3] = stats.getFmtnRqs();
        array[4] = stats.getFmtnRqMsgs();
        array[5] = stats.getFmtnRspMsgs();
        array[6] = stats.getFmtStartTime();
        array[7] = stats.getFmtLastStateChg();
        return array;
    }
    
    private boolean sleepHereMs(final int n) {
        try {
            Thread.sleep(n);
        }
        catch (Exception ex) {
            this.log.logError(7665689515738013574L, new Object[] { ex.toString(), ex.getMessage() });
        }
        return true;
    }
    
    private void wakeListenerPort() {
        if (this.log.ifLogVerbose(1L, 0)) {
            this.log.logVerbose(0, "Waking " + this.getName() + " ...");
        }
        try {
            new Socket(InetAddress.getLocalHost().getHostName(), this.properties.portNum).close();
        }
        catch (IOException obj) {
            this.log.logError("IOException (" + obj + ") while waking " + this.getName() + " : " + obj.getMessage());
        }
    }
    
    private boolean launchServerConnectAdapter() {
        final ObjectHolder objectHolder = new ObjectHolder();
        final ObjectHolder objectHolder2 = new ObjectHolder();
        final boolean startAdapter = this.startAdapter(objectHolder, objectHolder2);
        if (startAdapter) {
            this.properties.putValueAsInt("mqPort", (int)objectHolder.getObject());
            this.properties.putValueAsInt("mqPid", (int)objectHolder2.getObject());
        }
        if (this.log.ifLogBasic(1L, 0)) {
            if (startAdapter) {
                this.log.logBasic(0, 7665689515738018153L, new Object[] { new Integer(this.properties.getValueAsInt("mqPort")), new Integer(this.properties.getValueAsInt("mqPid")) });
            }
            else {
                this.log.logBasic(0, 7665689515738018165L, new Object[0]);
            }
        }
        return startAdapter;
    }
    
    private String bldCmdLine() {
        final StringBuffer sb = new StringBuffer(100);
        final String property = System.getProperty("Install.Dir");
        sb.append(this.properties.getValueAsString("jvmexe"));
        sb.append(" " + this.properties.getValueAsString("jvmargs"));
        sb.append(" -DInstall.Dir=" + property);
        sb.append(" -cp " + this.properties.getValueAsString("pluginclasspath"));
        sb.append(" -Djava.security.policy=" + this.properties.getValueAsString("policyfile"));
        sb.append(" " + this.properties.getValueAsString("classMain"));
        sb.append(" " + this.properties.getValueAsString("mqStartupParms"));
        sb.append(" -c " + this.properties.brokerPid);
        sb.append(" -m " + this.properties.getMQLoggingParams());
        return sb.toString();
    }
    
    private boolean startAdapter(final ObjectHolder objectHolder, final ObjectHolder objectHolder2) {
        final String bldCmdLine = this.bldCmdLine();
        try {
            this.adapterProcess = Runtime.getRuntime().exec(bldCmdLine);
            if (this.log.ifLogBasic(1L, 0)) {
                this.log.logBasic(0, 7665689515738018154L, new Object[] { bldCmdLine });
            }
        }
        catch (IOException ex) {
            this.log.logError(7665689515738018155L, new Object[] { ex.getMessage() });
            this.adapterProcess = null;
            return false;
        }
        final BufferedReader adapterStream = this.getAdapterStream();
        return adapterStream != null && this.getAdapterInfo(adapterStream, objectHolder, objectHolder2);
    }
    
    private BufferedReader getAdapterStream() {
        if (this.log.ifLogBasic(2L, 1)) {
            this.log.logBasic(1, " creating server input stream ... ");
        }
        return new BufferedReader(new InputStreamReader(this.adapterProcess.getErrorStream()));
    }
    
    private boolean getAdapterInfo(final BufferedReader bufferedReader, final ObjectHolder objectHolder, final ObjectHolder objectHolder2) {
        boolean adapterData = false;
        if (this.log.ifLogBasic(2L, 1)) {
            this.log.logBasic(1, this.getName() + " : reading fromAdapter ... ");
        }
        try {
            String pipeInput;
            while ((pipeInput = this.getPipeInput(bufferedReader, true)) != null) {
                if (this.log.ifLogBasic(2L, 1)) {
                    this.log.logDump(2, 1, "From ServerConnect adapter : ", pipeInput.getBytes(), pipeInput.length());
                }
                final int index = pipeInput.indexOf(">>adapter=");
                if (index != -1) {
                    final int beginIndex = index + ">>adapter=".length();
                    if (beginIndex < pipeInput.length()) {
                        adapterData = this.getAdapterData(pipeInput.substring(beginIndex), objectHolder, objectHolder2);
                        break;
                    }
                    adapterData = false;
                }
            }
            if (!adapterData && this.log.ifLogBasic(1L, 0)) {
                this.log.logDump(2, 0, "Invalid message received from adapter for MQ-ServerConnect", pipeInput.getBytes(), pipeInput.length());
            }
        }
        catch (IOException ex) {
            this.log.logError(7665689515738018157L, new Object[] { ex.toString() });
        }
        return adapterData;
    }
    
    private String getPipeInput(final BufferedReader bufferedReader, final boolean b) throws IOException {
        final byte[] array = new byte[4096];
        final StringBuffer sb = new StringBuffer();
        while (bufferedReader.ready() || b) {
            int n = bufferedReader.read();
            if (n == 2) {
                if (sb.length() > 0 && this.log.ifLogVerbose(1L, 0)) {
                    this.log.logVerbose(0, 7665689515738018158L, new Object[] { sb });
                }
                int n2 = 0;
                array[n2++] = (byte)n;
                while (n2 < array.length && (n = bufferedReader.read()) != 3) {
                    if (n == -1) {
                        if (sb.length() > 0 && this.log.ifLogVerbose(1L, 0)) {
                            this.log.logVerbose(0, 7665689515738018158L, new Object[] { sb });
                        }
                        throw new EOFException("pipe to adapter broken");
                    }
                    array[n2] = (byte)n;
                    ++n2;
                }
                if (n != 3 && this.log.ifLogVerbose(1L, 0)) {
                    this.log.logVerbose(0, 7665689515738018159L, new Object[] { new String(array, 0, n2) });
                }
                return (n2 == 0) ? new String("") : new String(array, 0, n2);
            }
            if (n == -1) {
                if (sb.length() > 0 && this.log.ifLogVerbose(1L, 0)) {
                    this.log.logVerbose(0, 7665689515738018158L, new Object[] { sb });
                }
                throw new EOFException("pipe to adapter broken");
            }
            sb.append((char)n);
        }
        if (sb.length() > 0 && this.log.ifLogVerbose(1L, 0)) {
            this.log.logVerbose(0, 7665689515738018158L, new Object[] { sb });
        }
        return null;
    }
    
    private void notifyClient(final String s) throws IOException {
        final byte[] netByteArray = ubMsg.newNetByteArray(s);
        final byte[] array = new byte[netByteArray.length + 2];
        array[0] = 2;
        System.arraycopy(netByteArray, 0, array, 1, netByteArray.length);
        array[netByteArray.length + 1] = 3;
        System.err.println(ubMsg.newNetString(array, 0, array.length));
    }
    
    private boolean getAdapterData(final String str, final ObjectHolder objectHolder, final ObjectHolder objectHolder2) {
        boolean b = false;
        final StringTokenizer stringTokenizer = new StringTokenizer(str, "|", false);
        try {
            if (stringTokenizer.hasMoreTokens()) {
                final Integer object = new Integer(stringTokenizer.nextToken());
                if (stringTokenizer.hasMoreTokens()) {
                    final Integer object2 = new Integer(stringTokenizer.nextToken());
                    objectHolder.setObject(object);
                    objectHolder2.setObject(object2);
                    b = true;
                }
            }
        }
        catch (NumberFormatException ex) {
            b = false;
        }
        return b;
    }
    
    boolean isRunning() {
        return this.current_state != 5;
    }
    
    public void logBrokerState(final String str) {
        if (this.log.ifLogBasic(1L, 0)) {
            this.log.logBasic(0, str + " : logging Broker state");
            this.log.logBasic(0, "ListenerState= " + ubListenerThread.DESC_STATE[this.current_state]);
        }
        final ubThread[] enumerateThreads = this.clientPool.enumerateThreads();
        final int length = enumerateThreads.length;
        if (this.log.ifLogBasic(1L, 0)) {
            this.log.logBasic(0, "num Client Threads = " + length);
        }
        for (final ubThread ubThread : enumerateThreads) {
            if (this.log.ifLogBasic(1L, 0)) {
                this.log.logBasic(0, ubThread.getFullName() + " : poolstate= " + ubThread.getPoolStateDesc() + "  clientState=" + ubThread.getFSMState() + "  remoteSocket= " + ubThread.getRemoteSocketDesc());
            }
            if (this.log.ifLogVerbose(256L, 8)) {
                ubThread.getMsgTrace().print(this.log, 1, 8, "MsgTrace for " + ubThread.getFullName());
            }
            else if (this.log.ifLogBasic(256L, 8)) {
                ubThread.getMsgTrace().print(this.log, 0, 8, "MsgTrace for " + ubThread.getFullName());
            }
        }
        final ubThread[] enumerateThreads2 = this.serverPool.enumerateThreads();
        final int length2 = enumerateThreads2.length;
        if (this.log.ifLogBasic(1L, 0)) {
            this.log.logBasic(0, "num Server Threads = " + length2);
        }
        for (final ubThread ubThread2 : enumerateThreads2) {
            if (this.log.ifLogBasic(1L, 0)) {
                this.log.logBasic(0, ubThread2.getFullName() + " : poolstate= " + ubThread2.getPoolStateDesc() + "  serverState=" + ubThread2.getFSMState() + "  remoteSocket= " + ubThread2.getRemoteSocketDesc() + "  agentPID= " + ((ubServerThread)ubThread2).getFmtServerPid());
            }
            if (this.log.ifLogVerbose(512L, 9)) {
                ubThread2.getMsgTrace().print(this.log, 1, 9, "MsgTrace for " + ubThread2.getFullName());
            }
            else if (this.log.ifLogBasic(512L, 9)) {
                ubThread2.getMsgTrace().print(this.log, 0, 9, "MsgTrace for " + ubThread2.getFullName());
            }
        }
    }
    
    private void processPropFileUpdate(final RequestQueue requestQueue) {
        final ubThread[] enumerateThreads = this.serverPool.enumerateThreads();
        final int length = enumerateThreads.length;
        if (this.log.ifLogVerbose(1L, 0)) {
            this.log.logVerbose(0, "Sending Property File Updated to " + length + " server threads");
        }
        int i = 0;
        int n = 0;
        while (i < length) {
            final ubThread ubThread = enumerateThreads[i];
            if (this.log.ifLogExtended(1L, 0)) {
                this.log.logExtended(0, "Property file change requested: " + ubThread.getFullName());
            }
            if (this.requestPropFileUpdate(ubThread, requestQueue)) {
                ++n;
            }
            ++i;
        }
        for (int j = 0; j < n; ++j) {
            this.waitAdminRsp(requestQueue);
            if (this.log.ifLogExtended(1L, 0)) {
                this.log.logExtended(0, "Received admin response from " + j + 1);
            }
        }
    }
    
    private boolean requestPropFileUpdate(final ubThread ubThread, final RequestQueue requestQueue) {
        boolean b = true;
        final ubAdminMsg obj = new ubAdminMsg((byte)12);
        obj.setadSrc((byte)5);
        final RequestQueue rcvQueue = ubThread.getRcvQueue();
        if (rcvQueue != null) {
            try {
                rcvQueue.enqueueRequest(new Request(obj, requestQueue));
                if (this.log.ifLogBasic(2L, 1)) {
                    this.log.logBasic(1, "Enqueued rq to " + rcvQueue.getListName());
                }
            }
            catch (Queue.QueueException ex) {
                b = false;
            }
        }
        else {
            this.log.logError("Cannot send request " + ubThread.getFullName() + " : requestQueue is null");
            b = false;
        }
        if (this.log.ifLogBasic(2L, 1)) {
            this.log.logBasic(1, "Property File Updated rq : " + ubThread.getFullName() + " : " + obj + (b ? " OK." : " FAILED."));
        }
        return b;
    }
    
    static {
        DESC_STATE = new String[] { " STATE_IDLE ", " STATE_STARTING ", " STATE_ACTIVE ", " STATE_STOPPING ", " STATE_STOPPING_FAST ", " STATE_STOPPED " };
        DESC_STATE_EXT = new String[] { " IDLE ", " STARTING ", " ACTIVE ", " STOPPING ", " STOPPING ", " STOPPED " };
    }
    
    class PropChangeListener extends EventListener
    {
        ubProperties m_props;
        IAppLogger log;
        
        public PropChangeListener(final ubProperties props, final IAppLogger log) {
            this.m_props = null;
            this.log = null;
            this.m_props = props;
            this.log = log;
        }
        
        public synchronized void processEvent(final IEventObject eventObject) {
            if (!(eventObject instanceof ERenegadePropertyFileChange)) {
                return;
            }
            final String propertyFileName = ((ERenegadePropertyFileChange)eventObject).getPropertyFileName();
            this.m_props.stopMonitors();
            try {
                this.m_props.load(propertyFileName);
                if (this.m_props.updateUbProperties(this.log) && this.log.ifLogBasic(1L, 0)) {
                    this.log.logBasic(0, 7665689515738020693L, new Object[0]);
                }
            }
            catch (Throwable t) {
                if (this.log.ifLogBasic(1L, 0)) {
                    this.log.logBasic(0, 7665689515738020694L, new Object[] { propertyFileName, t.getMessage() });
                }
            }
            ubListenerThread.this.processPropFileUpdate(ubListenerThread.this.rspQueue);
            this.m_props.startPropertyFileMonitor();
        }
    }
}
