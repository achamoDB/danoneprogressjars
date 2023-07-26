// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.broker;

import com.progress.common.util.UUID;
import com.progress.ubroker.util.ubConnID;
import java.io.EOFException;
import java.rmi.RemoteException;
import com.progress.common.networkevents.IEventObject;
import com.progress.ubroker.management.events.EUbrokerClientAbnormalDisconnectEvent;
import com.progress.ubroker.util.ubAdminMsg;
import java.io.InterruptedIOException;
import com.progress.ubroker.util.ubMsg;
import com.progress.ubroker.util.Queue;
import java.net.InetAddress;
import java.io.IOException;
import java.net.SocketException;
import com.progress.chimera.adminserver.IAdminServerConnection;
import com.progress.chimera.adminserver.IAdministrationServer;
import java.rmi.RMISecurityManager;
import com.progress.common.networkevents.IEventBroker;
import com.progress.ubroker.util.Request;
import com.progress.ubroker.util.ubMsgTrace;
import com.progress.common.ehnlog.IAppLogger;
import com.progress.ubroker.util.ubProperties;
import com.progress.ubroker.util.MsgOutputStream;
import com.progress.ubroker.util.MsgInputStream;
import com.progress.ubroker.util.RequestQueue;
import java.net.Socket;
import com.progress.ubroker.util.ubConstants;
import com.progress.ubroker.util.ubThread;

public class ubClientThread extends ubThread implements ubConstants, IClientFSM
{
    static final int SOTIMEOUTSSL = 15000;
    static final int CONNECTTIMEOUT = 3000;
    static final byte ASKSTATE_INIT = 0;
    static final byte ASKSTATE_ACTIVITY_TIMEOUT = 1;
    static final byte ASKSTATE_RESPONSE_TIMEOUT = 2;
    static final String[] DESC_ASKSTATE;
    ubClientFSM clientFSM;
    Socket socket;
    RequestQueue serverRequestQueue;
    MsgInputStream is;
    MsgOutputStream os;
    ubProperties properties;
    byte current_state;
    int seqnum;
    ubThreadPool clientPool;
    ubThreadPool serverPool;
    IServerThreadControl serverCntrl;
    RequestQueue deferredRequestQueue;
    long tsStartRq;
    String socketDesc;
    String saveSocketName;
    private String connectionID;
    private boolean isConnected;
    private int numRequestsQueued;
    private int numRequestsRejected;
    int m_negotiatedAskMajorVer;
    int m_negotiatedAskMinorVer;
    int m_negotiatedAskCaps;
    byte m_ASKstate;
    int m_serverASKActivityTimeoutMs;
    int m_serverASKResponseTimeoutMs;
    
    public ubClientThread(final int n, final ubThreadPool clientPool, final ubThreadPool serverPool, final IServerThreadControl serverCntrl, final ubProperties properties, final IAppLogger appLogger) {
        super("C", n, appLogger);
        this.isConnected = false;
        this.numRequestsQueued = 0;
        this.numRequestsRejected = 0;
        this.clientPool = clientPool;
        this.serverPool = serverPool;
        this.serverCntrl = serverCntrl;
        this.properties = properties;
        this.socket = null;
        this.socketDesc = null;
        this.serverRequestQueue = null;
        super.rcvQueue = new RequestQueue(this.getFullName(), this.properties.getValueAsInt("queueLimit"), appLogger);
        this.deferredRequestQueue = new RequestQueue(this.getFullName() + "-deferredRequestQueue", this.properties.getValueAsInt("queueLimit"), appLogger);
        this.is = null;
        this.os = null;
        this.current_state = 0;
        this.seqnum = 0;
        this.connectionID = null;
        this.clientFSM = null;
        this.tsStartRq = -1L;
        if (appLogger.ifLogBasic(256L, 8)) {
            super.trace = new ubMsgTrace();
        }
        else {
            super.trace = null;
        }
        this.resetASKstate();
        this.m_serverASKActivityTimeoutMs = this.properties.getValueAsInt("serverASKActivityTimeout") * 1000;
        this.m_serverASKResponseTimeoutMs = this.properties.getValueAsInt("serverASKResponseTimeout") * 1000;
    }
    
    protected void setConnectionID(final String original) {
        this.connectionID = new String(original);
    }
    
    public String getConnID() {
        return null;
    }
    
    public boolean isConnected() {
        return this.current_state != 0;
    }
    
    public int getTotalClients() {
        return 0;
    }
    
    public int getConnState() {
        int n = 0;
        if (this.properties.serverMode == 1 || this.properties.serverMode == 2) {
            switch (this.current_state) {
                case 0: {
                    n = 0;
                    break;
                }
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8: {
                    n = 1;
                    break;
                }
                default: {
                    n = 6;
                    break;
                }
            }
        }
        else {
            switch (this.current_state) {
                case 0: {
                    n = 0;
                    break;
                }
                case 1:
                case 6: {
                    n = 1;
                    break;
                }
                case 2:
                case 5: {
                    n = 2;
                    break;
                }
                case 3:
                case 9: {
                    n = 4;
                    break;
                }
                case 4:
                case 8: {
                    n = 3;
                    break;
                }
                case 7: {
                    n = 5;
                    break;
                }
                default: {
                    n = 6;
                    break;
                }
            }
        }
        return n;
    }
    
    public String getFSMState() {
        return new String(IClientFSM.DESC_STATE[this.current_state]);
    }
    
    public String getRemoteSocketDesc() {
        return (this.socketDesc == null) ? "null" : this.socketDesc;
    }
    
    public void run() {
        if (super.log.ifLogVerbose(1L, 0)) {
            super.log.logVerbose(0, 7665689515738013579L, new Object[] { this.getFullName() });
        }
        this.clientFSM = this.setClientFSM();
        try {
            this.mainline();
        }
        catch (Throwable t) {
            super.log.logStackTrace(7665689515738013923L, new Object[] { this.getFullName() }, t);
        }
        this.closeClientConnection();
        this.drainQueue(super.rcvQueue);
        this.drainQueue(this.deferredRequestQueue);
        this.notifyServerThread(this.serverRequestQueue);
        try {
            this.clientPool.removeThread(this);
        }
        catch (ubThreadPool.EmptyPoolException ex) {
            super.log.logError(7665689515738013580L, new Object[] { "clientPool", ex.getMessage() });
        }
        this.logThreadStats();
        if (super.log.ifLogBasic(256L, 8)) {
            super.trace.print(super.log, 0, 8, "MsgTrace for " + this.getFullName());
        }
        if (super.log.ifLogVerbose(1L, 0)) {
            super.log.logVerbose(0, 7665689515738013581L, new Object[] { "clientPool", this.getFullName() });
        }
    }
    
    private void mainline() {
        this.current_state = this.setInitialThreadState(0, (byte)0);
        Request solicitEvent;
        while (this.current_state != 10 && (solicitEvent = this.solicitEvent()) != null) {
            this.current_state = this.processEvent(this.decodeEvent(solicitEvent), solicitEvent);
        }
    }
    
    public String getThreadname() {
        return new String(this.getFullName());
    }
    
    private IEventBroker findAdminServerEventBrokerXXX() {
        IEventBroker eventBroker = null;
        try {
            final String rmiURL = this.properties.rmiURL;
            System.setSecurityManager(new RMISecurityManager());
            eventBroker = ((IAdministrationServer)ubThread.lookupService(rmiURL.substring(0, rmiURL.lastIndexOf(this.properties.brokerName)) + "Chimera")).getEventBroker();
        }
        catch (Exception ex) {
            if (this.properties.rmiURL != null) {
                super.log.logStackTrace("Cannot locate AdminServer's EventBroker", ex);
            }
        }
        return eventBroker;
    }
    
    ubClientFSM setClientFSM() {
        super.log.logError(7665689515738013582L, new Object[] { new Integer(this.properties.serverType) });
        return null;
    }
    
    boolean openIOStreams() {
        if (this.socket == null) {
            return false;
        }
        try {
            if (this.properties != null && this.properties.getValueAsBoolean("sslEnable")) {
                this.socket.setSoTimeout(15000);
            }
            else if (this.properties.getValueAsInt("brkrSpinInterval") > 0) {
                this.socket.setSoTimeout(this.properties.getValueAsInt("brkrSpinInterval"));
            }
        }
        catch (SocketException ex) {
            super.log.logStackTrace(7665689515738013583L, new Object[] { ex.getMessage() }, ex);
            this.closeClientConnection();
            return false;
        }
        try {
            this.socket.setKeepAlive(true);
        }
        catch (SocketException obj) {
            super.log.logError("socket.setKeepAlive() error : " + obj);
            this.closeClientConnection();
            return false;
        }
        boolean b;
        try {
            this.is = new MsgInputStream(this.socket.getInputStream(), 10240, this.properties.serverType, super.log, 2, 16L, 4);
            this.os = new MsgOutputStream(this.socket.getOutputStream(), 1024, super.log, 2, 16L, 4);
            if (this.properties != null && this.properties.getValueAsBoolean("sslEnable")) {
                this.socket.setSoTimeout(this.properties.getValueAsInt("brkrSpinInterval"));
            }
            this.socket.setTcpNoDelay(true);
            synchronized (super.stats) {
                super.stats.setConnHdl();
                super.stats.setConnRmtHost(this.socket.getInetAddress());
                super.stats.setConnRmtPort(this.socket.getPort());
                super.stats.setConnID(this.getConnID());
                super.stats.setConnRqs(0);
            }
            b = true;
        }
        catch (IOException ex2) {
            super.log.logStackTrace(7665689515738013584L, new Object[] { ex2.getMessage() }, ex2);
            this.closeClientConnection();
            b = false;
        }
        this.refreshASKTimer(true, "Connection opened");
        return b;
    }
    
    void closeClientConnection() {
        if (this.socket != null) {
            try {
                if (this.is != null) {
                    this.is.close();
                }
                if (this.os != null) {
                    this.os.close();
                }
                final String s = "";
                this.socket.close();
                this.isConnected = false;
                if (this.properties.serverType == 1) {
                    if (super.log.ifLogVerbose(1L, 0)) {
                        super.log.logVerbose(0, 7665689515738014038L, new Object[] { s });
                    }
                }
                else if (super.log.ifLogBasic(1L, 0)) {
                    super.log.logBasic(0, 7665689515738014038L, new Object[] { s });
                }
            }
            catch (IOException ex) {
                super.log.logStackTrace(7665689515738013585L, new Object[] { ex.getMessage() }, ex);
            }
        }
        this.is = null;
        this.os = null;
        this.socket = null;
        this.socketDesc = null;
        this.resetASKstate();
        synchronized (super.stats) {
            super.stats.setConnRmtHost(null);
            super.stats.setConnRmtPort(0);
            super.stats.setConnUserName(null);
            super.stats.setConnID(null);
            super.stats.setConnRqs(0);
        }
    }
    
    byte setInitialThreadState(final int n, final byte b) {
        this.clientPool.setPoolState(this, n);
        super.stats.settsLastStateChg();
        return b;
    }
    
    byte setThreadState(final byte b) {
        int n = 0;
        switch (b) {
            case 0: {
                n = 0;
                break;
            }
            case 1: {
                n = 1;
                break;
            }
            case 10: {
                n = 4;
                break;
            }
            default: {
                n = 2;
                break;
            }
        }
        if (this.getPoolState() != n) {
            this.clientPool.setPoolState(this, n);
        }
        return b;
    }
    
    Request solicitEvent() {
        String s = "";
        if (super.log.ifLogBasic(4L, 2)) {
            super.log.logBasic(2, "FSM : solicitEvent() : state=" + IClientFSM.DESC_STATE[this.current_state]);
        }
        Request request = null;
        try {
            switch (this.current_state) {
                case 0: {
                    request = (this.deferredRequestQueue.isEmpty() ? super.rcvQueue.dequeueRequest() : this.deferredRequestQueue.dequeueRequest());
                    s = ((request != null) ? request.getRspQueue().toString() : "");
                    break;
                }
                case 4:
                case 6: {
                    request = null;
                    while (request == null) {
                        if (this.is != null && this.is.available() > 0) {
                            final ubMsg msg = this.is.readMsg();
                            this.refreshASKTimer(true, "Msg received");
                            if (super.log.ifLogBasic(2L, 1)) {
                                msg.print("solicitEvent() : got client msg in state= " + this.current_state + IClientFSM.DESC_STATE[this.current_state], 2, 1, super.log);
                            }
                            s = this.socketDesc;
                            request = new Request(msg, super.rcvQueue);
                        }
                        else {
                            request = super.rcvQueue.pollRequest(this.properties.getValueAsInt("brkrSpinInterval"));
                            s = ((request != null) ? request.getRspQueue().toString() : "");
                        }
                    }
                    break;
                }
                case 7:
                case 8: {
                    request = super.rcvQueue.dequeueRequest();
                    s = ((request != null) ? request.getRspQueue().toString() : "");
                    break;
                }
                case 1:
                case 2: {
                    if (!this.deferredRequestQueue.isEmpty()) {
                        request = this.deferredRequestQueue.dequeueRequest();
                        s = ((request != null) ? request.getRspQueue().toString() : "");
                        break;
                    }
                }
                case 3:
                case 5:
                case 9: {
                    request = null;
                    while (request == null) {
                        try {
                            if (!super.rcvQueue.isEmpty()) {
                                request = super.rcvQueue.dequeueRequest();
                                if (super.log.ifLogBasic(2L, 1)) {
                                    ((ubMsg)request.getMsg()).print("solicitEvent() : got rcvQueue msg in state= " + this.current_state + IClientFSM.DESC_STATE[this.current_state], 2, 1, super.log);
                                }
                                s = ((request != null) ? request.getRspQueue().toString() : "");
                            }
                            else if (this.is != null) {
                                final ubMsg msg2 = this.is.readMsg();
                                this.refreshASKTimer(true, "Msg received");
                                request = new Request(msg2, super.rcvQueue);
                                s = this.socketDesc;
                            }
                            else {
                                request = super.rcvQueue.dequeueRequest();
                                s = ((request != null) ? request.getRspQueue().toString() : "");
                            }
                        }
                        catch (InterruptedIOException ex) {
                            if (super.log.ifLogExtended(2L, 1)) {
                                super.log.logExtended(1, "read() IOInterrupted exception in solicitEvent() : " + ex.getMessage());
                            }
                            request = this.newAskEvent(super.rcvQueue);
                        }
                    }
                    break;
                }
                default: {
                    super.log.logError(7665689515738013586L, new Object[] { new Integer(this.current_state), IClientFSM.DESC_STATE[this.current_state] });
                    final ubAdminMsg ubAdminMsg = new ubAdminMsg((byte)3);
                    s = this.getFullName();
                    request = new Request(ubAdminMsg, super.rcvQueue);
                    break;
                }
            }
        }
        catch (ubMsg.MsgFormatException ex2) {
            if (super.log.ifLogVerbose(1L, 0)) {
                super.log.logVerbose(0, 7665689515738013587L, new Object[] { ex2.getMessage(), ex2.getDetail() });
            }
            s = this.getFullName();
            request = new Request(new ubAdminMsg((byte)6), super.rcvQueue);
            final IEventBroker adminServerEventBroker = this.findAdminServerEventBroker(this.properties.rmiURL, this.properties.brokerName);
            if (adminServerEventBroker != null) {
                try {
                    adminServerEventBroker.postEvent(new EUbrokerClientAbnormalDisconnectEvent(this.properties.brokerName, "Client message is invalid", this.properties.canonicalName));
                }
                catch (RemoteException ex4) {}
            }
        }
        catch (EOFException ex5) {
            if (super.log.ifLogVerbose(1L, 0)) {
                super.log.logVerbose(0, 7665689515738013588L, new Object[] { this.getFullName() });
            }
            s = this.getFullName();
            request = new Request(new ubAdminMsg((byte)6), super.rcvQueue);
        }
        catch (IOException ex3) {
            if (super.log.ifLogVerbose(1L, 0)) {
                super.log.logVerbose(0, 7665689515738013589L, new Object[] { this.getFullName(), ex3.getMessage() });
            }
            s = this.socketDesc;
            request = new Request(new ubAdminMsg((byte)6), super.rcvQueue);
            final IEventBroker adminServerEventBroker2 = this.findAdminServerEventBroker(this.properties.rmiURL, this.properties.brokerName);
            if (adminServerEventBroker2 != null) {
                try {
                    adminServerEventBroker2.postEvent(new EUbrokerClientAbnormalDisconnectEvent(this.properties.brokerName, "Client disconnected abnormally", this.properties.canonicalName));
                }
                catch (RemoteException ex6) {}
            }
        }
        if (super.log.ifLogBasic(64L, 6)) {
            ((ubMsg)request.getMsg()).print("solicitEvent() : msg", 2, 6, super.log);
        }
        if (super.log.ifLogBasic(256L, 8)) {
            super.trace.addMsg((ubMsg)request.getMsg(), this.getFullName(), s, this.getFullName());
        }
        return request;
    }
    
    byte decodeEvent(final Request request) {
        final ubMsg ubMsg = (ubMsg)request.getMsg();
        if (ubMsg == null) {
            return 8;
        }
        if (super.log.ifLogBasic(2L, 1)) {
            super.log.logBasic(1, "decodeEvent: rq= " + ubMsg.getubRq());
        }
        byte b = 0;
        switch (ubMsg.getubRq()) {
            case 3: {
                b = 0;
                break;
            }
            case 4: {
                b = 2;
                break;
            }
            case 5: {
                b = 3;
                break;
            }
            case 7: {
                b = 6;
                break;
            }
            case 6: {
                b = 7;
                break;
            }
            case 11: {
                b = 1;
                break;
            }
            case 12: {
                b = 4;
                break;
            }
            case 13: {
                b = 5;
                break;
            }
            case 14: {
                b = 12;
                break;
            }
            case 15: {
                b = 13;
                break;
            }
            case 17: {
                b = 14;
                break;
            }
            case 1: {
                if (ubMsg instanceof ubAdminMsg) {
                    switch (((ubAdminMsg)ubMsg).getadRq()) {
                        case 1: {
                            b = 9;
                            break;
                        }
                        case 2: {
                            b = 10;
                            break;
                        }
                        case 6: {
                            b = 11;
                            break;
                        }
                        case 10: {
                            b = 17;
                            break;
                        }
                        case 11: {
                            b = 18;
                            break;
                        }
                        case 3: {
                            b = 21;
                            break;
                        }
                        default: {
                            b = 8;
                            break;
                        }
                    }
                    break;
                }
                b = 8;
                break;
            }
            case 18: {
                b = 15;
                break;
            }
            case 2: {
                b = 16;
                break;
            }
            case 22: {
                b = 19;
                break;
            }
            case 23: {
                b = 20;
                break;
            }
            default: {
                b = 8;
                break;
            }
        }
        return b;
    }
    
    byte processEvent(final byte value, final Request request) {
        final byte action = this.clientFSM.getAction(this.current_state, value);
        byte b = this.clientFSM.getNextState(this.current_state, value);
        if (super.log.ifLogBasic(4L, 2)) {
            this.clientFSM.print(super.log, 2, 2, this.current_state, value, action, b);
        }
        switch (action) {
            case 1: {
                b = this.processConnect(request, b);
                break;
            }
            case 2: {
                b = this.processConnectDirect(request, b);
                break;
            }
            case 3: {
                b = this.processInitRequest(request, b);
                break;
            }
            case 4: {
                super.stats.incrnRqMsgs();
                b = this.processEnqueueDirect(this.serverRequestQueue, request, b);
                break;
            }
            case 5: {
                b = this.processDequeue(request, b);
                super.stats.incrnRspMsgs();
                break;
            }
            case 6: {
                b = this.processDequeueLast(request, b);
                super.stats.incrnRspMsgs();
                break;
            }
            case 7: {
                b = this.processStop(request, b);
                break;
            }
            case 8: {
                b = this.processDisconnect(request, b);
                break;
            }
            case 9: {
                super.stats.incrnErrors();
                b = this.processFatalError(1, "Unspecified Error");
                break;
            }
            case 11: {
                b = this.processStartup(request, b);
                break;
            }
            case 12: {
                b = this.processShutdown(request, b);
                break;
            }
            case 13: {
                b = this.processIOException(request, b);
                break;
            }
            case 14: {
                b = this.processConnRsp(request, b);
                super.stats.incrnRspMsgs();
                break;
            }
            case 15: {
                b = this.processDisconnectRsp(request, b);
                break;
            }
            case 16: {
                b = this.processFinishRq(request, b);
                break;
            }
            case 17: {
                b = this.processIgnore(request, b);
                break;
            }
            case 18: {
                b = this.processDeferShutdown(request, b);
                break;
            }
            case 19: {
                b = this.processDeferShutdownDisconnect(request, b);
                break;
            }
            case 20: {
                b = this.processShutdownWrite(request, b);
                break;
            }
            case 21: {
                b = this.processShutdownRead(request, b);
                break;
            }
            case 22: {
                b = this.processDeferAbend(request, b);
                break;
            }
            case 23: {
                b = this.processDeferAbendDisconnect(request, b);
                break;
            }
            case 24: {
                b = this.processAbendWrite(request, b);
                break;
            }
            case 25: {
                b = this.processAbendRead(request, b);
                break;
            }
            case 26: {
                b = this.processBrokerStatus(request, b);
                break;
            }
            case 27: {
                super.stats.incrnErrors();
                b = this.processConnectError(1, "Unspecified Error");
                break;
            }
            case 0: {
                b = this.processXID(request, b);
                break;
            }
            case 28: {
                b = this.processASKActivityTimeout(request, b);
                break;
            }
            case 29: {
                b = this.processASKResponseTimeout(request, b);
                break;
            }
            case 30: {
                b = this.processASKPingRequest(request, b);
                break;
            }
            case 31: {
                b = this.processASKPingResponse(request, b);
                break;
            }
            case 32: {
                b = this.processNonFatalError(request, b);
                break;
            }
            case 33: {
                b = this.processServerTerminateError(request, b);
                break;
            }
            default: {
                super.stats.incrnErrors();
                super.log.logError(7665689515738013591L, new Object[] { new Integer(this.current_state), IClientFSM.DESC_STATE[this.current_state], new Integer(value), IClientFSM.DESC_EVENT[value], new Integer(action), new Integer(b), IClientFSM.DESC_STATE[b] });
                if (super.log.ifLogBasic(256L, 8)) {
                    super.trace.print(super.log, 0, 8, "MsgTrace for " + this.getFullName());
                    break;
                }
                break;
            }
        }
        if (super.log.ifLogBasic(4L, 2)) {
            super.log.logBasic(2, "FSM : nextstate=" + IClientFSM.DESC_STATE[b]);
        }
        if (b == 0) {
            this.serverRequestQueue = null;
            super.stats.setConnServerPID(0);
            super.stats.setConnServerPort(0);
        }
        return this.setThreadState(b);
    }
    
    byte processConnect(final Request request, final byte b) {
        final ubMsg ubMsg = (ubMsg)request.getMsg();
        this.serverRequestQueue = null;
        super.stats.setConnServerPID(0);
        super.stats.setConnServerPort(0);
        if (this.clientPool.numThreadsAllowed() == 0 && this.clientPool.numThreadsInState(0) == 0) {
            super.log.logError(7665689515738014062L, new Object[0]);
            return this.processConnectError(12, "Maximum number of client connections has been reached.");
        }
        this.tsStartRequest();
        return this.processEnqueue(request, b);
    }
    
    byte processConnectDirect(final Request request, final byte b) {
        return this.processActionNotSupported("processConnectDirect");
    }
    
    byte processEnqueue(final Request request, final byte b) {
        return this.processEnqueue(request, b, ((ubMsg)request.getMsg()).getubRq());
    }
    
    byte processEnqueue(final Request request, final byte b, final int n) {
        final int valueAsInt = this.properties.getValueAsInt("collectStatsData");
        final long currentTimeMillis = System.currentTimeMillis();
        final ubThread dequeueReadyServer = this.dequeueReadyServer(1000 * this.properties.getValueAsInt("srvrStartupTimeout"));
        if (dequeueReadyServer != null) {
            final long currentTimeMillis2 = System.currentTimeMillis();
            final int n2 = (int)(currentTimeMillis2 - currentTimeMillis);
            super.stats.incTotRqWait(n2);
            if (super.log.ifLogBasic(2L, 1)) {
                super.log.logBasic(1, "processEnqueue() : tsStartRqWait= " + currentTimeMillis + " tsEndRqWait= " + currentTimeMillis2 + " rqWait= " + n2);
            }
            if (super.log.ifLogBasic(2048L, 11)) {
                super.log.logBasic(11, "queued message to " + dequeueReadyServer.getName() + " : rqWait= " + n2);
            }
            this.serverRequestQueue = dequeueReadyServer.getRcvQueue();
            super.stats.setConnServerPID(((ubServerThread)dequeueReadyServer).getServerPid());
            super.stats.setConnServerPort(((ubServerThread)dequeueReadyServer).getServerPort());
            return this.processEnqueueDirect(dequeueReadyServer.getRcvQueue(), request, b);
        }
        if (valueAsInt > 0) {
            this.clientPool.incrRequestsQueued(1);
        }
        final ubThread dequeueReadyServer2 = this.dequeueReadyServer(this.properties.getValueAsInt("requestTimeout") * 1000);
        final long currentTimeMillis3 = System.currentTimeMillis();
        final int n3 = (int)(currentTimeMillis3 - currentTimeMillis);
        super.stats.incTotRqWait(n3);
        if (super.log.ifLogBasic(2L, 1)) {
            super.log.logBasic(1, "processEnqueue() : tsStartRqWait= " + currentTimeMillis + " tsEndRqWait= " + currentTimeMillis3 + " rqWait= " + n3);
        }
        byte b2 = 0;
        if (dequeueReadyServer2 == null) {
            super.stats.incrnErrors();
            if (valueAsInt > 0) {
                this.clientPool.incrRequestsRejected(1);
            }
            super.log.logError(7665689515738013593L, new Object[0]);
            switch (n) {
                case 3: {
                    b2 = this.processConnectError(7, "No Servers available");
                    break;
                }
                case 6: {
                    b2 = this.processFatalError(7, "No Servers Available");
                    break;
                }
                case 4: {
                    b2 = this.processIgnore(request, (byte)9);
                    break;
                }
                default: {
                    b2 = this.processNonFatalError(this.clientNonFatalErrorRsp(0, "No Servers Available"), "No Servers Available");
                    break;
                }
            }
            this.tsEndRequest();
        }
        else {
            if (super.log.ifLogBasic(2048L, 11)) {
                super.log.logBasic(11, "queued message to " + dequeueReadyServer2.getName() + " : rqWait= " + n3);
            }
            this.serverRequestQueue = dequeueReadyServer2.getRcvQueue();
            super.stats.setConnServerPID(((ubServerThread)dequeueReadyServer2).getServerPid());
            super.stats.setConnServerPort(((ubServerThread)dequeueReadyServer2).getServerPort());
            b2 = this.processEnqueueDirect(dequeueReadyServer2.getRcvQueue(), request, b);
        }
        return b2;
    }
    
    byte processEnqueueDirect(final RequestQueue requestQueue, final Request request, final byte b) {
        if (requestQueue == null) {
            super.stats.incrnErrors();
            super.log.logError(7665689515738013594L, new Object[0]);
            return this.processFatalError(2, "Protocol Error");
        }
        try {
            if (super.log.ifLogVerbose(1L, 0)) {
                final ubMsg ubMsg = (ubMsg)request.getMsg();
                String tlvField_NoThrow = ubMsg.getTlvField_NoThrow((short)1);
                if (tlvField_NoThrow == null) {
                    tlvField_NoThrow = "<none>";
                }
                super.log.logVerbose(0, "Enqueued request " + ubMsg.getubRqDesc() + " issued to " + this.serverRequestQueue.getListName() + " requestID= " + tlvField_NoThrow);
            }
            if (super.log.ifLogBasic(256L, 8)) {
                super.trace.addMsg((ubMsg)request.getMsg(), this.getFullName(), this.getFullName(), requestQueue.getListName());
            }
            requestQueue.enqueueRequest(request);
        }
        catch (Queue.QueueException ex) {
            if (super.log.ifLogBasic(64L, 6)) {
                super.log.logBasic(6, "Unable to enqueue request " + ((ubMsg)request.getMsg()).getubRqDesc() + " to " + requestQueue.getListName());
                super.log.logBasic(6, ex.getMessage() + " : " + ex.getDetail());
            }
            this.enqueuePriorityAdminEvent((byte)3);
        }
        return b;
    }
    
    byte processInitRequest(final Request request, final byte b) {
        return this.processActionNotSupported("processInitRequest");
    }
    
    byte processDequeue(final Request request, final byte b) {
        request.logStats(super.log);
        return (byte)(this.sendClientRsp((ubMsg)request.getMsg(), "Cannot write response message.") ? b : 0);
    }
    
    byte processDequeueLast(final Request request, final byte b) {
        final ubMsg ubMsg = (ubMsg)request.getMsg();
        super.stats.incrnRsps();
        this.serverRequestQueue = null;
        super.stats.setConnServerPID(0);
        super.stats.setConnServerPort(0);
        final boolean sendClientRsp = this.sendClientRsp(ubMsg, "Cannot write response message.");
        this.tsEndRequest();
        request.logStats(super.log);
        return (byte)(sendClientRsp ? b : 10);
    }
    
    byte processFinishRq(final Request request, final byte b) {
        this.serverRequestQueue = null;
        super.stats.setConnServerPID(0);
        super.stats.setConnServerPort(0);
        this.tsEndRequest();
        request.logStats(super.log);
        return b;
    }
    
    byte processStop(final Request request, final byte b) {
        return this.processIgnore(request, b);
    }
    
    byte processDisconnect(final Request request, final byte b) {
        return this.processActionNotSupported("processDisconnect");
    }
    
    byte processDisconnectRsp(final Request request, final byte b) {
        final byte processDequeueLast = this.processDequeueLast(request, b);
        this.serverRequestQueue = null;
        super.stats.setConnServerPID(0);
        super.stats.setConnServerPort(0);
        this.resetASKstate();
        super.stats.incrnRspMsgs();
        return processDequeueLast;
    }
    
    byte processNonFatalError(final Request request, final byte b) {
        return this.processNonFatalError(this.clientNonFatalErrorRsp(0, "No Servers Available"), "No Servers Available");
    }
    
    byte processServerTerminateError(final Request request, final byte b) {
        return this.processNonFatalError(this.clientNonFatalErrorRsp(0, "Server terminated unexpectedly"), "Server terminated unexpectedly");
    }
    
    byte processFatalError(final int n, final String s) {
        this.sendClientRsp(n, s, "Cannot write error message");
        this.closeClientConnection();
        super.log.logError(7665689515738013595L, new Object[] { s });
        return 0;
    }
    
    byte processNonFatalError(final ubMsg ubMsg, final String s) {
        this.sendClientRsp(ubMsg, "Cannot write error message");
        super.log.logError(7665689515738013924L, new Object[] { s });
        return 2;
    }
    
    byte processConnectError(final int n, final String s) {
        return this.processFatalError(n, s);
    }
    
    byte processStartup(final Request request, final byte b) {
        final ubAdminMsg ubAdminMsg = (ubAdminMsg)request.getMsg();
        byte b2 = 0;
        int n = 4;
        final Object[] getadParm = ubAdminMsg.getadParm();
        if (getadParm == null || getadParm.length == 0 || getadParm[0] == null || !(getadParm[0] instanceof Socket)) {
            super.log.logError(7665689515738013596L, new Object[0]);
        }
        else {
            this.socket = (Socket)getadParm[0];
            if (this.openIOStreams()) {
                if (super.log.ifLogBasic(2L, 1)) {
                    super.log.logBasic(1, "processed STARTUP msg");
                }
                this.isConnected = true;
                if (this.properties.serverType == 1) {
                    if (super.log.ifLogVerbose(1L, 0)) {
                        super.log.logVerbose(0, 7665689515738014037L, new Object[] { "" });
                    }
                }
                else if (super.log.ifLogBasic(1L, 0)) {
                    super.log.logBasic(0, 7665689515738014037L, new Object[] { "" });
                }
                if (this.socket != null && this.socket.getInetAddress() != null) {
                    this.socketDesc = this.socket.getInetAddress().getHostAddress() + ":" + this.socket.getPort();
                }
                n = 0;
                b2 = b;
            }
        }
        ubAdminMsg.setadRsp(n);
        this.sendAdmRsp((RequestQueue)request.getRspQueue(), ubAdminMsg);
        return b2;
    }
    
    byte processShutdown(final Request request, final byte b) {
        final ubAdminMsg ubAdminMsg = (ubAdminMsg)request.getMsg();
        final byte b2 = 10;
        ubAdminMsg.setadRsp(0);
        this.sendAdmRsp((RequestQueue)request.getRspQueue(), ubAdminMsg);
        return b2;
    }
    
    byte processDeferShutdown(final Request request, final byte b) {
        return (byte)(this.deferRequest(request) ? b : 10);
    }
    
    byte processDeferShutdownDisconnect(final Request request, final byte b) {
        return (byte)(this.deferRequest(request) ? b : 10);
    }
    
    byte processShutdownWrite(final Request request, final byte b) {
        this.closeClientConnection();
        return (byte)(this.deferRequest(request) ? b : 10);
    }
    
    byte processShutdownRead(final Request request, final byte b) {
        this.closeClientConnection();
        return (byte)(this.deferRequest(request) ? b : 10);
    }
    
    byte processIOException(final Request request, final byte b) {
        this.closeClientConnection();
        return b;
    }
    
    byte processDeferAbend(final Request request, final byte b) {
        this.closeClientConnection();
        return (byte)(this.deferRequest(request) ? b : 10);
    }
    
    byte processDeferAbendDisconnect(final Request request, final byte b) {
        return this.processDeferAbend(request, b);
    }
    
    byte processAbendWrite(final Request request, final byte b) {
        this.closeClientConnection();
        return (byte)(this.deferRequest(request) ? b : 10);
    }
    
    byte processAbendRead(final Request request, final byte b) {
        this.closeClientConnection();
        return (byte)(this.deferRequest(request) ? b : 10);
    }
    
    byte processIgnore(final Request request, final byte b) {
        return b;
    }
    
    byte processConnRsp(final Request request, final byte b) {
        this.serverRequestQueue = null;
        super.stats.setConnServerPID(0);
        super.stats.setConnServerPort(0);
        final byte processDequeueLast = this.processDequeueLast(request, b);
        super.stats.incrnRspMsgs();
        return processDequeueLast;
    }
    
    ubMsg clientRsp(final int n, final String s) {
        this.processActionNotSupported("clientRsp");
        return null;
    }
    
    ubMsg clientNonFatalErrorRsp(final int n, final String s) {
        this.processActionNotSupported("clientNonFatalError");
        return null;
    }
    
    boolean sendClientRsp(final int n, final String s, final String s2) {
        return this.sendClientRsp(this.clientRsp(n, s), s2);
    }
    
    boolean sendClientRsp(final ubMsg ubMsg, final String str) {
        final boolean b = true;
        if (this.os != null) {
            try {
                this.os.writeMsg(ubMsg);
                this.refreshASKTimer(false, "Msg sent");
                if (super.log.ifLogBasic(256L, 8)) {
                    super.trace.addMsg(ubMsg, this.getFullName(), this.getFullName(), this.socketDesc);
                }
                if (ubMsg.getubRq() != 12) {
                    this.os.flush();
                }
            }
            catch (IOException obj) {
                super.log.logStackTrace(7665689515738013924L, new Object[] { str }, obj);
                if (super.log.ifLogVerbose(1L, 0)) {
                    super.log.logVerbose(0, 7665689515738013597L, new Object[] { obj.toString() + " : " + obj.getMessage() });
                }
                if (super.log.ifLogBasic(2L, 1)) {
                    ubMsg.print(this.getFullName() + " " + str + " " + obj, 2, 1, super.log);
                }
                this.closeClientConnection();
                this.enqueuePriorityAdminEvent((byte)6);
            }
        }
        return b;
    }
    
    ubServerThread getServerThreadByCookie(final String s) {
        this.processActionNotSupported("getServerThreadByCookie");
        return null;
    }
    
    byte processBrokerStatus(final Request request, final byte b) {
        this.processActionNotSupported("processBrokerStatus");
        return b;
    }
    
    byte processXID(final Request request, final byte b) {
        return this.processActionNotSupported("processXID");
    }
    
    byte processASKActivityTimeout(final Request request, final byte b) {
        return this.processActionNotSupported("processASKActivityTimeout");
    }
    
    byte processASKResponseTimeout(final Request request, final byte b) {
        return this.processActionNotSupported("processASKResponseTimeout");
    }
    
    byte processASKPingRequest(final Request request, final byte b) {
        return this.processActionNotSupported("processASKPingRequest");
    }
    
    byte processASKPingResponse(final Request request, final byte b) {
        return this.processActionNotSupported("processASKPingResponse");
    }
    
    boolean sendAdmRsp(final RequestQueue requestQueue, final ubMsg ubMsg) {
        final Request request = new Request(ubMsg, super.rcvQueue);
        try {
            if (super.log.ifLogBasic(64L, 6)) {
                super.log.logBasic(6, "Enqueued request " + ((ubMsg)request.getMsg()).getubRqDesc() + " to " + requestQueue.getListName());
            }
            requestQueue.enqueueRequest(request);
            if (super.log.ifLogBasic(256L, 8)) {
                super.trace.addMsg((ubMsg)request.getMsg(), this.getFullName(), this.getFullName(), requestQueue.getListName());
            }
        }
        catch (Queue.QueueException ex) {
            if (super.log.ifLogBasic(64L, 6)) {
                super.log.logBasic(6, "Unable to enqueue request " + ((ubMsg)request.getMsg()).getubRqDesc() + " to " + requestQueue.getListName());
                super.log.logBasic(6, ex.getMessage() + " : " + ex.getDetail());
            }
        }
        return true;
    }
    
    boolean sleepHere(final int n) {
        try {
            Thread.sleep(1000 * n);
        }
        catch (Exception ex) {
            super.log.logError(7665689515738013574L, new Object[] { ex.toString(), ex.getMessage() });
        }
        return true;
    }
    
    byte processActionNotSupported(final String s) {
        super.log.logError(7665689515738013598L, new Object[] { s });
        return 10;
    }
    
    ubThread dequeueReadyServer(final int n) {
        final RequestQueue requestQueue = new RequestQueue(this.getFullName() + "-admrspQ", 0, super.log);
        this.serverPool.start_minThreads(2, null, requestQueue);
        if (super.log.ifLogBasic(2L, 1)) {
            super.log.logBasic(1, "Dequeueing server from serverPool " + this.serverPool);
        }
        return this.serverPool.dequeueThreadInState(1, requestQueue, n, new Object[] { super.rcvQueue }, this.connectionID);
    }
    
    void drainQueue(final RequestQueue requestQueue) {
        requestQueue.close();
        if (super.log.ifLogBasic(2L, 1)) {
            requestQueue.print(super.log, 2, 1);
        }
        while (!requestQueue.isEmpty()) {
            final Request dequeueRequest = requestQueue.dequeueRequest();
            final ubMsg ubMsg = (ubMsg)dequeueRequest.getMsg();
            final RequestQueue requestQueue2 = (RequestQueue)dequeueRequest.getRspQueue();
            if (super.log.ifLogBasic(2L, 1)) {
                ubMsg.print("drainQueue() dequeued request on " + requestQueue.getListName() + " : ", 2, 1, super.log);
            }
            if (ubMsg instanceof ubAdminMsg) {
                final ubAdminMsg ubAdminMsg = (ubAdminMsg)ubMsg;
                ubAdminMsg.getadRq();
                if (ubAdminMsg.getadRq() != 1 && ubAdminMsg.getadRq() != 2) {
                    continue;
                }
                ubAdminMsg.setadRsp(1);
                this.sendAdmRsp(requestQueue2, ubAdminMsg);
            }
        }
    }
    
    private boolean deferRequest(final Request request) {
        boolean b = true;
        try {
            if (super.log.ifLogBasic(16L, 4)) {
                super.log.logBasic(4, "Enqueued request " + ((ubMsg)request.getMsg()).getubRqDesc() + " to " + this.deferredRequestQueue.getListName());
            }
            this.deferredRequestQueue.enqueueRequest(request);
        }
        catch (Queue.QueueException ex) {
            if (super.log.ifLogBasic(16L, 4)) {
                super.log.logBasic(4, "Unable to enqueue request " + ((ubMsg)request.getMsg()).getubRqDesc() + " to " + this.deferredRequestQueue.getListName());
                super.log.logBasic(4, ex.getMessage() + " : " + ex.getDetail());
            }
            b = false;
        }
        return b;
    }
    
    boolean enqueuePriorityAdminEvent(final byte b) {
        final Request request = new Request(new ubAdminMsg(b), super.rcvQueue);
        boolean b2 = true;
        try {
            if (super.log.ifLogBasic(16L, 4)) {
                super.log.logBasic(4, "Enqueued request " + ((ubMsg)request.getMsg()).getubRqDesc() + "/" + ((ubAdminMsg)request.getMsg()).getadDesc() + " to " + super.rcvQueue.getListName());
            }
            super.rcvQueue.enqueuePriorityRequest(request);
        }
        catch (Queue.QueueException ex) {
            if (super.log.ifLogBasic(16L, 4)) {
                super.log.logBasic(4, "Unable to enqueue request " + ((ubMsg)request.getMsg()).getubRqDesc() + "/" + ((ubAdminMsg)request.getMsg()).getadDesc() + " to " + super.rcvQueue.getListName());
                super.log.logBasic(4, ex.getMessage() + " : " + ex.getDetail());
            }
            b2 = false;
        }
        return b2;
    }
    
    void reset_tsRqStart() {
        if (this.tsStartRq != -1L && super.log.ifLogBasic(2L, 1)) {
            super.log.logBasic(1, "reset_tsRqStart() : Invalid tsStartRq= " + this.tsStartRq);
        }
        this.tsStartRq = -1L;
    }
    
    void tsStartRequest() {
        if (this.tsStartRq != -1L && super.log.ifLogBasic(2L, 1)) {
            super.log.logBasic(1, "tsStartRequest() error : Invalid tsStartRq= " + this.tsStartRq);
        }
        this.tsStartRq = System.currentTimeMillis();
        if (super.log.ifLogBasic(2L, 1)) {
            super.log.logBasic(1, "tsStartRequest(): tsStartRq= " + this.tsStartRq);
        }
    }
    
    void tsEndRequest() {
        if (this.tsStartRq == -1L) {
            if (super.log.ifLogBasic(2L, 1)) {
                super.log.logBasic(1, "tsEndRequest() error : Invalid tsStartRq");
            }
            return;
        }
        final long currentTimeMillis = System.currentTimeMillis();
        final long n = currentTimeMillis - this.tsStartRq;
        if (super.log.ifLogBasic(2L, 1)) {
            super.log.logBasic(1, "tsEndRequest(): tsStartRq= " + this.tsStartRq + " tsEndRq= " + currentTimeMillis + " duration= " + n);
        }
        if (super.log.ifLogBasic(2048L, 11)) {
            super.log.logBasic(11, "tsEndRequest : rqTime= " + n);
        }
        super.stats.incrnRqs();
        super.stats.incrConnRqs(1);
        super.stats.incTotRqDuration((int)n);
        this.tsStartRq = -1L;
    }
    
    void logThreadStats() {
        if (super.log.ifLogBasic(2048L, 11)) {
            final int getnRqs = super.stats.getnRqs();
            super.log.logBasic(11, "logThreadStats() : nRqs= " + getnRqs + " MAX Request Wait= " + super.stats.getMaxRqWait() + " ms" + " AVG Request Wait= " + super.stats.getTotRqWait() / (float)getnRqs + " ms");
            super.log.logBasic(11, "logThreadStats() : nRqs= " + getnRqs + " MAX Request Duration= " + super.stats.getMaxRqDuration() + " ms" + " AVG Request Duration= " + super.stats.getTotRqDuration() / (float)getnRqs + " ms");
        }
    }
    
    void notifyServerThread(final RequestQueue requestQueue) {
        if (requestQueue != null) {
            final ubAdminMsg ubAdminMsg = new ubAdminMsg((byte)3);
            if (super.log.ifLogBasic(2L, 1)) {
                ubAdminMsg.print("notifyServerThread() issued request : ", 2, 1, super.log);
            }
            final Request request = new Request(ubAdminMsg, null);
            try {
                if (super.log.ifLogBasic(16L, 4)) {
                    super.log.logBasic(4, "Enqueued request " + ((ubMsg)request.getMsg()).getubRqDesc() + "/" + ((ubAdminMsg)request.getMsg()).getadDesc() + " to " + requestQueue.getListName());
                }
                requestQueue.enqueueRequest(request);
            }
            catch (Queue.QueueException ex) {
                if (super.log.ifLogBasic(16L, 4)) {
                    super.log.logBasic(4, "Unable to enqueue request " + ((ubMsg)request.getMsg()).getubRqDesc() + "/" + ((ubAdminMsg)request.getMsg()).getadDesc() + " to " + requestQueue.getListName());
                    super.log.logBasic(4, ex.getMessage() + " : " + ex.getDetail());
                }
            }
        }
    }
    
    ubConnID newConnectionIDObj() {
        final UUID uuid = new UUID();
        final ubConnID ubConnID = new ubConnID();
        ubConnID.create(this.properties.localHost, this.properties.brokerName, this.properties.portNum, uuid.toString());
        return ubConnID;
    }
    
    void insertASKCapabilities(final ubMsg ubMsg) {
        if (this.m_negotiatedAskMajorVer != 0 && this.m_negotiatedAskCaps != 0) {
            final int i = this.m_negotiatedAskMajorVer << 16 | this.m_negotiatedAskMinorVer;
            final String string = this.m_negotiatedAskMajorVer + "." + this.m_negotiatedAskMinorVer;
            final String formatAskCapabilities = this.formatAskCapabilities(this.m_negotiatedAskCaps);
            try {
                ubMsg.appendTlvField((short)10, Integer.toString(i));
            }
            catch (ubMsg.MsgFormatException ex) {
                super.log.logBasic(0, 7665689515738019267L, new Object[] { string, ex.toString() });
            }
            catch (ubMsg.TlvAccessException ex2) {
                super.log.logBasic(0, 7665689515738019267L, new Object[] { string, ex2.toString() });
            }
            try {
                ubMsg.appendTlvField((short)11, formatAskCapabilities);
            }
            catch (ubMsg.MsgFormatException ex3) {
                super.log.logBasic(0, 7665689515738019268L, new Object[] { formatAskCapabilities, ex3.toString() });
            }
            catch (ubMsg.TlvAccessException ex4) {
                super.log.logBasic(0, 7665689515738019268L, new Object[] { formatAskCapabilities, ex4.toString() });
            }
        }
    }
    
    void negotiateAskCapabilities(final ubMsg ubMsg) {
        int n = 0;
        int i = 0;
        String s = null;
        int n2;
        try {
            s = ubMsg.getTlvField((short)10);
            n2 = ((s == null) ? 0 : Integer.parseInt(s));
            n = n2 >> 16;
            i = (n2 & 0xFFFF);
            if (n == 0) {
                n2 = 0;
                if (super.log.ifLogBasic(1L, 0)) {
                    super.log.logBasic(0, 7665689515738019269L, new Object[0]);
                }
            }
        }
        catch (ubMsg.MsgFormatException ex) {
            n2 = 0;
            if (super.log.ifLogBasic(32768L, 15)) {
                super.log.logBasic(15, 7665689515738019270L, new Object[] { ex.toString() });
            }
            if (super.log.ifLogExtended(32768L, 15)) {
                super.log.logExtended(15, "mfe.getMessage= (" + ex.getMessage() + ")  " + "mfe.getDetail= (" + ex.getDetail() + ")");
            }
        }
        catch (ubMsg.TlvAccessException ex5) {
            n2 = 0;
            if (super.log.ifLogVerbose(1L, 0)) {
                super.log.logVerbose(0, 7665689515738019271L, new Object[] { "TlvFieldNotFound" });
            }
        }
        catch (NumberFormatException ex2) {
            n2 = 0;
            if (super.log.ifLogBasic(1L, 0)) {
                super.log.logBasic(0, 7665689515738019272L, new Object[] { s, ex2.toString() });
            }
        }
        if (n2 == 0) {
            this.m_negotiatedAskMajorVer = 0;
            this.m_negotiatedAskMinorVer = 0;
            this.m_negotiatedAskCaps = 0;
            return;
        }
        if (n > 1) {
            this.m_negotiatedAskMajorVer = 1;
            this.m_negotiatedAskMinorVer = 0;
        }
        else if (n < 1) {
            this.m_negotiatedAskMajorVer = n;
            this.m_negotiatedAskMinorVer = i;
        }
        else {
            this.m_negotiatedAskMajorVer = 1;
            this.m_negotiatedAskMinorVer = Math.min(i, 0);
        }
        int n3;
        try {
            s = ubMsg.getTlvField((short)11);
            n3 = ((s == null) ? 0 : this.parseAskCapabilities(s));
            if (super.log.ifLogBasic(1L, 0)) {
                super.log.logBasic(0, 7665689515738019273L, new Object[] { n + "." + i, s });
            }
        }
        catch (ubMsg.MsgFormatException ex3) {
            n3 = 0;
            if (super.log.ifLogBasic(1L, 0)) {
                super.log.logBasic(0, 7665689515738019274L, new Object[] { s, ex3.toString() });
            }
        }
        catch (ubMsg.TlvAccessException ex4) {
            n3 = 0;
            if (super.log.ifLogBasic(1L, 0)) {
                super.log.logBasic(0, 7665689515738019275L, new Object[] { ex4.toString() });
            }
        }
        this.m_negotiatedAskCaps = (n3 & this.parseAskCapabilities(this.properties.getValueAsString("appServerKeepaliveCapabilities")));
        if (n3 != 0 && super.log.ifLogBasic(1L, 0)) {
            super.log.logBasic(0, (this.m_negotiatedAskCaps == 0) ? 7665689515738020760L : 7665689515738019276L, new Object[] { this.m_negotiatedAskMajorVer + "." + this.m_negotiatedAskMinorVer, this.formatAskCapabilities(this.m_negotiatedAskCaps) });
        }
    }
    
    private int parseAskCapabilities(final String s) {
        int n = 0;
        if (s == null || s.length() == 0) {
            return n;
        }
        final String upperCase = s.toUpperCase();
        if (upperCase.indexOf("ALLOWSERVERASK") >= 0) {
            n |= 0x1;
        }
        if (upperCase.indexOf("ALLOWCLIENTASK") >= 0) {
            n |= 0x2;
        }
        if (upperCase.indexOf("DENYSERVERASK") >= 0) {
            n &= 0xFFFFFFFE;
        }
        if (upperCase.indexOf("DENYCLIENTASK") >= 0) {
            n &= 0xFFFFFFFD;
        }
        return n;
    }
    
    private String formatAskCapabilities(final int n) {
        return (((n & 0x1) > 0) ? "allowServerASK" : "denyServerASK") + "," + (((n & 0x2) > 0) ? "allowClientASK" : "denyClientASK");
    }
    
    private void refreshASKTimer(final boolean b, final String s) {
        if (this.properties.serverMode == 1 || this.properties.serverMode == 2) {
            return;
        }
        super.stats.settsLastSocketActivity();
        if (b) {
            this.m_ASKstate = 1;
        }
        if (super.log.ifLogVerbose(32768L, 15)) {
            super.log.logVerbose(15, 7665689515738019277L, new Object[] { s, (this.m_ASKstate == 2) ? "Response" : "Activity" });
        }
    }
    
    Request newAskEvent(final Queue queue) {
        return null;
    }
    
    void resetASKstate() {
        this.m_negotiatedAskCaps = 0;
        this.m_negotiatedAskMajorVer = 0;
        this.m_negotiatedAskMinorVer = 0;
        this.m_ASKstate = 0;
    }
    
    static {
        DESC_ASKSTATE = new String[] { "ASKSTATE_INIT", "ASKSTATE_ACTIVITY_TIMEOUT", "ASKSTATE_RESPONSE_TIMEOUT" };
    }
}
