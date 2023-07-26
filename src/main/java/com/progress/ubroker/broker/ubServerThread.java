// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.broker;

import java.util.Vector;
import java.util.Random;
import java.io.EOFException;
import com.progress.ubroker.util.Queue;
import com.progress.ubroker.management.events.EAbnormalShutdownServerEvent;
import com.progress.ubroker.management.events.COpenEdgeManagementContent;
import java.rmi.Naming;
import com.progress.chimera.adminserver.IAdminServerConnection;
import com.progress.chimera.adminserver.IAdministrationServer;
import java.rmi.RMISecurityManager;
import com.progress.common.networkevents.IEventBroker;
import java.net.InetAddress;
import java.rmi.RemoteException;
import com.progress.common.networkevents.IEventObject;
import com.progress.ubroker.management.events.EUbrokerClientAbnormalDisconnectEvent;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.StringReader;
import com.progress.ubroker.util.ubWebSpeedMsg;
import com.progress.ubroker.util.ubAppServerMsg;
import com.progress.ubroker.util.Request;
import com.progress.ubroker.util.ubAdminMsg;
import com.progress.ubroker.util.ubMsg;
import com.progress.ubroker.util.ubMsgTrace;
import com.progress.ubroker.util.ubThreadStats;
import com.progress.common.ehnlog.IAppLogger;
import java.io.BufferedReader;
import com.progress.ubroker.util.ubProperties;
import com.progress.ubroker.util.RequestQueue;
import java.text.DecimalFormat;
import com.progress.ubroker.util.ubConstants;
import com.progress.ubroker.util.ubThread;

public abstract class ubServerThread extends ubThread implements ubConstants, IServerFSM
{
    static int DEF_LIFESPAN_PAD;
    static DecimalFormat fmt5;
    ubServerFSM serverFSM;
    ubThreadPool serverPool;
    RequestQueue clientRspQueue;
    RequestQueue rspQueue;
    RequestQueue adminQueue;
    ubProperties properties;
    Process serverProcess;
    BufferedReader fromServer;
    byte current_state;
    int seqnum;
    int serverPort;
    int serverPid;
    int initialStateInPool;
    String serverCookie;
    IServerIPC serverIPC;
    IAppLogger serverLog;
    private int nIdleCnt;
    private long totIdleTime;
    private int nBusyCnt;
    private long totBusyTime;
    private int nLockedCnt;
    private long totLockedTime;
    private int nOtherCnt;
    private long totOtherTime;
    private int prevStatus;
    private long lastTimeInStatus;
    long tsStartRq;
    long tsStartThread;
    long tsServerLifespan;
    
    public ubServerThread(final int n, final int initialStateInPool, final ubProperties properties, final ubThreadPool serverPool, final IAppLogger serverLog, final IAppLogger appLogger) {
        super("S", n, appLogger);
        this.nIdleCnt = 0;
        this.totIdleTime = 0L;
        this.nBusyCnt = 0;
        this.totBusyTime = 0L;
        this.nLockedCnt = 0;
        this.totLockedTime = 0L;
        this.nOtherCnt = 0;
        this.totOtherTime = 0L;
        this.prevStatus = -1;
        this.lastTimeInStatus = -1L;
        this.setName(this.getFullName());
        this.properties = properties;
        this.initialStateInPool = initialStateInPool;
        this.serverPool = serverPool;
        this.serverLog = serverLog;
        this.tsStartRq = -1L;
        this.tsStartThread = System.currentTimeMillis();
        this.tsServerLifespan = this.computeServerLifespan();
        super.rcvQueue = new RequestQueue(this.getFullName(), this.properties.getValueAsInt("queueLimit"), appLogger);
        this.clientRspQueue = null;
        this.rspQueue = null;
        this.current_state = 0;
        this.seqnum = 0;
        this.serverPort = 0;
        this.serverPid = 0;
        this.serverCookie = new String("");
        this.serverIPC = null;
        super.stats = new ubThreadStats(this.getFullName());
        this.serverFSM = null;
        if (appLogger.ifLogBasic(512L, 9)) {
            super.trace = new ubMsgTrace();
        }
        else {
            super.trace = null;
        }
    }
    
    public int getIdleCount() {
        return this.nIdleCnt;
    }
    
    public long getIdleTime() {
        return this.totIdleTime;
    }
    
    public int getBusyCount() {
        return this.nBusyCnt;
    }
    
    public long getBusyTime() {
        return this.totBusyTime;
    }
    
    public long getOtherTime() {
        return this.totOtherTime;
    }
    
    public int getOtherCount() {
        return this.nOtherCnt;
    }
    
    public int getLockedCount() {
        return this.nLockedCnt;
    }
    
    public long getLockedTime() {
        return this.totLockedTime;
    }
    
    public long getTimeEnteredCurrStatus() {
        return this.lastTimeInStatus;
    }
    
    public boolean isConnected() {
        return this.current_state == 3 || this.current_state == 2;
    }
    
    public void run() {
        if (super.log.ifLogBasic(1L, 0)) {
            super.log.logBasic(0, 7665689515738013605L, new Object[] { this.getFullName() });
        }
        this.serverFSM = this.setServerFSM(this.properties.serverType, this.properties.serverMode, super.log);
        try {
            this.mainline();
        }
        catch (Throwable t) {
            super.log.logStackTrace(7665689515738013923L, new Object[] { this.getFullName() }, t);
        }
        this.drainRcvQueue();
        this.disconnectClientThread(this.clientRspQueue);
        try {
            this.serverPool.removeThread(this);
        }
        catch (ubThreadPool.EmptyPoolException ex) {
            super.log.logError(7665689515738013580L, new Object[] { "serverPool", ex.getMessage() });
        }
        if (this.serverIPC != null) {
            try {
                this.serverIPC.delete();
                this.serverIPC = null;
            }
            catch (ServerIPCException ex2) {
                super.log.logError(7665689515738013929L, new Object[] { ex2.toString(), ex2.getMessage() });
            }
        }
        if (this.fromServer != null) {
            try {
                this.fromServer.close();
            }
            catch (Exception obj) {
                if (super.log.ifLogBasic(1L, 0)) {
                    super.log.logBasic(0, "Error closing pipe from agent = " + obj);
                }
            }
        }
        this.fromServer = null;
        this.serverProcess = null;
        if (super.log.ifLogBasic(512L, 9)) {
            if (super.trace == null) {
                super.trace = new ubMsgTrace();
            }
            super.trace.print(super.log, 0, 9, "MsgTrace for " + this.getFullName());
        }
        if (super.log.ifLogVerbose(1L, 0)) {
            super.log.logVerbose(0, 7665689515738013581L, new Object[] { "serverPool", this.getFullName() });
        }
    }
    
    private void mainline() {
        this.current_state = this.setInitialThreadState(this.initialStateInPool, (byte)0);
        ubMsg solicitEvent;
        while (this.current_state != 11 && (solicitEvent = this.solicitEvent()) != null) {
            this.current_state = this.processEvent(this.decodeEvent(solicitEvent), solicitEvent);
        }
    }
    
    public String getThreadname() {
        return new String(this.getFullName());
    }
    
    public int getServerPort() {
        return this.serverPort;
    }
    
    public String getFmtServerPort() {
        return ubServerThread.fmt5.format(this.serverPort);
    }
    
    public int getServerPid() {
        return this.serverPid;
    }
    
    public String getFmtServerPid() {
        return ubServerThread.fmt5.format(this.getServerPid());
    }
    
    public String getServerState() {
        return new String(IServerFSM.DESC_STATE_EXT[this.current_state]);
    }
    
    public int getConnState() {
        int n = 0;
        switch (this.current_state) {
            case 0:
            case 1: {
                n = 0;
                break;
            }
            case 2: {
                n = 1;
                break;
            }
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 12: {
                n = 2;
                break;
            }
            default: {
                n = 6;
                break;
            }
        }
        return n;
    }
    
    public String getFSMState() {
        return new String(IServerFSM.DESC_STATE[this.current_state]);
    }
    
    public String getRemoteSocketDesc() {
        return (this.serverIPC == null) ? "null" : this.serverIPC.toString();
    }
    
    public int getServerState(final int n) {
        final byte current_state = this.current_state;
        int n2 = 0;
        switch (this.current_state) {
            case 1: {
                n2 = 0;
                break;
            }
            case 10: {
                n2 = 2;
                break;
            }
            case 0:
            case 11: {
                n2 = 3;
                break;
            }
            default: {
                n2 = 1;
                break;
            }
        }
        return n2;
    }
    
    public synchronized String getServerCookie() {
        return new String(this.serverCookie);
    }
    
    public synchronized void setServerCookie(final String serverCookie) {
        this.serverCookie = serverCookie;
    }
    
    abstract ubServerFSM setServerFSM(final int p0, final int p1, final IAppLogger p2);
    
    abstract byte shutdownServerProcess(final RequestQueue p0, final ubAdminMsg p1, final byte p2);
    
    abstract byte sendAdminMessage(final RequestQueue p0, final ubAdminMsg p1, final byte p2);
    
    abstract byte readAdminResponse(final RequestQueue p0, final ubMsg p1, final byte p2);
    
    abstract byte processConnect(final RequestQueue p0, final ubMsg p1, final byte p2);
    
    abstract ubMsg clientRsp(final int p0, final String p1);
    
    boolean testServerProcess() {
        return this.serverPid <= 0 || this.properties.env.query_PID_JNI(this.serverPid, true) > 0;
    }
    
    byte setInitialThreadState(final int n, final byte b) {
        this.serverPool.setPoolState(this, n);
        super.stats.settsLastStateChg();
        return b;
    }
    
    byte setThreadState(byte current_state) {
        final boolean b = current_state == 12;
        if (b) {
            current_state = this.current_state;
        }
        int n = 0;
        switch (current_state) {
            case 0: {
                n = 0;
                this.updateStatusTimes(3);
                break;
            }
            case 1: {
                n = 1;
                this.updateStatusTimes(0);
                break;
            }
            case 11: {
                n = 4;
                this.updateStatusTimes(3);
                break;
            }
            case 10: {
                n = 3;
                this.updateStatusTimes(2);
                break;
            }
            default: {
                n = 2;
                this.updateStatusTimes(1);
                break;
            }
        }
        if (super.log.ifLogExtended(8L, 3)) {
            super.log.logExtended(3, "fNOOP         = " + b);
            super.log.logExtended(3, "current_state =" + IServerFSM.DESC_STATE[this.current_state]);
            super.log.logExtended(3, "nextState     =" + IServerFSM.DESC_STATE[current_state]);
            super.log.logExtended(3, "poolState     = " + ubThread.DESC_POOL_STATE[this.getPoolState()]);
            super.log.logExtended(3, "nextPoolState = " + ubThread.DESC_POOL_STATE[n]);
        }
        if (this.getPoolState() != n && !b) {
            this.serverPool.setPoolState(this, n);
        }
        super.stats.settsLastStateChg();
        return current_state;
    }
    
    ubMsg solicitEvent() {
        if (super.log.ifLogBasic(8L, 3)) {
            super.log.logBasic(3, "FSM : solicitEvent() : state=" + IServerFSM.DESC_STATE[this.current_state]);
        }
        ubMsg obj = null;
        Label_0636: {
            switch (this.current_state) {
                case 0: {
                    final Request dequeueRequest = super.rcvQueue.dequeueRequest();
                    obj = (ubMsg)dequeueRequest.getMsg();
                    this.rspQueue = (RequestQueue)dequeueRequest.getRspQueue();
                    break;
                }
                case 1: {
                    switch (this.properties.serverType) {
                        case 0:
                        case 1: {
                            obj = this.getQueueEvent();
                            break Label_0636;
                        }
                        default: {
                            obj = ((this.properties.serverMode == 0 || this.properties.serverMode == 3) ? this.getQueueEvent() : this.getServerPipeEvent());
                            break Label_0636;
                        }
                    }
                    break;
                }
                case 3: {
                    switch (this.properties.serverType) {
                        case 1: {
                            obj = this.getServerIPCEvent();
                            break Label_0636;
                        }
                        case 2:
                        case 3:
                        case 5: {
                            obj = this.getServerPipeEvent();
                            break Label_0636;
                        }
                        default: {
                            obj = this.getServerIPCEvent();
                            break Label_0636;
                        }
                    }
                    break;
                }
                case 4: {
                    obj = this.getQueueEvent();
                    break;
                }
                case 5: {
                    obj = this.getQueueEvent();
                    break;
                }
                case 6: {
                    obj = this.getServerIPCEvent();
                    break;
                }
                case 7: {
                    obj = this.getServerIPCEvent();
                    break;
                }
                case 8: {
                    super.log.logError(this.getFullName() + " : FSM ERROR: UNIMPLEMENTED STATE :" + " state= " + this.current_state + IServerFSM.DESC_STATE[this.current_state]);
                    final Request dequeueRequest2 = super.rcvQueue.dequeueRequest();
                    obj = (ubMsg)dequeueRequest2.getMsg();
                    this.rspQueue = (RequestQueue)dequeueRequest2.getRspQueue();
                    break;
                }
                case 2: {
                    switch (this.properties.serverType) {
                        case 1: {
                            obj = this.getServerIPCEvent(true, true, 1000 * this.properties.getValueAsInt("connectingTimeout"));
                            break;
                        }
                        case 2:
                        case 3:
                        case 5: {
                            obj = this.getServerPipeEvent(1000 * this.properties.getValueAsInt("connectingTimeout"));
                            break;
                        }
                        default: {
                            obj = this.getServerIPCEvent(true, true, 1000 * this.properties.getValueAsInt("connectingTimeout"));
                            break;
                        }
                    }
                    if (obj == null) {
                        obj = this.newTimeoutMsg();
                        break;
                    }
                    break;
                }
                case 9: {
                    obj = this.getServerIPCEvent();
                    break;
                }
                case 10: {
                    obj = this.getQueueEvent();
                    break;
                }
                default: {
                    super.log.logError(this.getFullName() + " : FSM ERROR: INVALID STATE :" + " state= " + this.current_state + IServerFSM.DESC_STATE[this.current_state]);
                    obj = null;
                    break;
                }
            }
        }
        if (obj != null && super.log.ifLogBasic(128L, 7)) {
            obj.print(this.getFullName() + " : solicitEvent() : msg= " + obj, 2, 7, super.log);
        }
        return obj;
    }
    
    byte decodeEvent(final ubMsg ubMsg) {
        if (ubMsg == null) {
            return 15;
        }
        byte b = 0;
        Label_0521: {
            switch (ubMsg.getubRq()) {
                case 1: {
                    if (ubMsg instanceof ubAdminMsg) {
                        switch (((ubAdminMsg)ubMsg).getadRq()) {
                            case 1: {
                                b = 1;
                                break;
                            }
                            case 2: {
                                b = 12;
                                break;
                            }
                            case 3: {
                                b = 13;
                                break;
                            }
                            case 5: {
                                b = 22;
                                break;
                            }
                            case 6: {
                                b = 11;
                                break;
                            }
                            case 4: {
                                b = 13;
                                break;
                            }
                            case 8: {
                                b = 20;
                                break;
                            }
                            case 9: {
                                b = 21;
                                break;
                            }
                            case 12: {
                                b = 23;
                                break;
                            }
                            default: {
                                b = 15;
                                break;
                            }
                        }
                        break;
                    }
                    if (!(ubMsg instanceof ubAppServerMsg)) {
                        b = 15;
                        break;
                    }
                    switch (((ubAppServerMsg)ubMsg).getMsgcode()) {
                        case 91: {
                            b = 24;
                            break Label_0521;
                        }
                        default: {
                            b = 15;
                            break Label_0521;
                        }
                    }
                    break;
                }
                case 3: {
                    b = 2;
                    break;
                }
                case 11: {
                    b = 2;
                    break;
                }
                case 16: {
                    b = 3;
                    break;
                }
                case 4: {
                    b = 4;
                    break;
                }
                case 5: {
                    b = 5;
                    break;
                }
                case 12: {
                    b = 6;
                    break;
                }
                case 13: {
                    b = 7;
                    break;
                }
                case 14: {
                    b = 8;
                    break;
                }
                case 15: {
                    b = 9;
                    break;
                }
                case 17: {
                    b = 10;
                    break;
                }
                case 6: {
                    b = 11;
                    break;
                }
                case 7: {
                    b = 14;
                    break;
                }
                case 9: {
                    if (ubMsg instanceof ubWebSpeedMsg) {
                        switch (((ubWebSpeedMsg)ubMsg).getwsMsgtype()) {
                            case 5: {
                                b = 16;
                                break Label_0521;
                            }
                            case 6: {
                                b = 17;
                                break Label_0521;
                            }
                            case 8: {
                                b = 18;
                                break Label_0521;
                            }
                            case 14: {
                                b = 19;
                                break Label_0521;
                            }
                            default: {
                                b = 15;
                                break Label_0521;
                            }
                        }
                    }
                    else {
                        if (!(ubMsg instanceof ubAppServerMsg)) {
                            b = 15;
                            break;
                        }
                        switch (((ubAppServerMsg)ubMsg).getMsgcode()) {
                            case 80: {
                                b = 22;
                                break Label_0521;
                            }
                            case 81: {
                                b = 11;
                                break Label_0521;
                            }
                            case 82: {
                                b = 21;
                                break Label_0521;
                            }
                            default: {
                                b = 15;
                                break Label_0521;
                            }
                        }
                    }
                    break;
                }
                default: {
                    b = 15;
                    break;
                }
            }
        }
        return b;
    }
    
    byte processEvent(final byte i, final ubMsg ubMsg) {
        final byte action = this.serverFSM.getAction(this.current_state, i);
        byte b = this.serverFSM.getNextState(this.current_state, i);
        if (super.log.ifLogBasic(8L, 3)) {
            this.serverFSM.print(super.log, 2, 3, this.current_state, i, action, b);
        }
        switch (action) {
            case 0: {
                b = this.processIgnore(this.rspQueue, ubMsg, b);
                this.rspQueue = null;
                break;
            }
            case 1: {
                b = this.processStartup(this.rspQueue, (ubAdminMsg)ubMsg, b);
                this.rspQueue = null;
                break;
            }
            case 3: {
                b = this.processConnect(this.rspQueue, ubMsg, b);
                break;
            }
            case 4: {
                b = this.processInitRq(this.rspQueue, ubMsg, b);
                break;
            }
            case 5: {
                super.stats.incrnRqMsgs();
                b = this.processWrite(this.rspQueue, ubMsg, b);
                break;
            }
            case 6: {
                super.stats.incrnRqMsgs();
                super.stats.incrnRqs();
                super.stats.incrnRsps();
                b = this.processWriteClose(this.rspQueue, ubMsg, b);
                break;
            }
            case 7: {
                b = this.processRead(this.rspQueue, ubMsg, b);
                break;
            }
            case 8: {
                b = this.processFinishRq(this.rspQueue, ubMsg, b);
                this.rspQueue = null;
                break;
            }
            case 9: {
                b = this.processDisconnect(this.rspQueue, ubMsg, b);
                this.rspQueue = null;
                break;
            }
            case 10: {
                b = this.processShutdown(this.rspQueue, ubMsg, b);
                this.rspQueue = null;
                break;
            }
            case 11: {
                b = this.processTerminate();
                this.rspQueue = null;
                break;
            }
            case 12:
            case 13: {
                super.stats.incrnErrors();
                b = this.processFatalError(this.rspQueue, 1, "Unspecified Error");
                this.rspQueue = null;
                break;
            }
            case 14: {
                b = this.processStop(this.rspQueue, ubMsg, b);
                break;
            }
            case 15: {
                this.processServerLogMsg(ubMsg);
                break;
            }
            case 16: {
                b = this.processServerStateChg(ubMsg, b);
                break;
            }
            case 17: {
                b = this.processConnectTimeout(this.rspQueue, ubMsg, b);
                break;
            }
            case 18: {
                b = this.processProcStats(this.rspQueue, ubMsg, b);
                break;
            }
            case 19: {
                b = this.processClientConnected(this.rspQueue, ubMsg, b);
                break;
            }
            case 21: {
                b = this.processAdminMsg(this.adminQueue, ubMsg, b);
                break;
            }
            case 22: {
                b = this.processAdminRspMsg(this.adminQueue, ubMsg, b);
                break;
            }
            default: {
                super.stats.incrnErrors();
                super.log.logError("FSM ERROR: INVALID ACTION state= " + this.current_state + " event = " + i + " : FSM : action= " + action + " nextstate = " + b);
                if (super.log.ifLogBasic(512L, 9)) {
                    super.trace.print(super.log, 0, 9, "MsgTrace for " + this.getFullName());
                }
                b = this.processFatalError(this.rspQueue, 2, "Protocol Error");
                this.rspQueue = null;
                break;
            }
        }
        if (super.log.ifLogBasic(8L, 3)) {
            super.log.logBasic(3, "FSM : nextstate=" + IServerFSM.DESC_STATE[b]);
        }
        return this.setThreadState(b);
    }
    
    String bldCmdLine(final String str) {
        final StringBuffer sb = new StringBuffer(100);
        sb.append(this.properties.getValueAsString("srvrExecFile"));
        sb.append(" " + this.properties.getValueAsString("srvrStartupParam"));
        if (str != null) {
            sb.append(" " + str);
        }
        return sb.toString();
    }
    
    boolean startServer(final String s) {
        final String bldCmdLine = this.bldCmdLine(s);
        final boolean startsWith = System.getProperty("os.name").startsWith("Windows");
        try {
            final Runtime runtime = Runtime.getRuntime();
            if (bldCmdLine.indexOf(34) == -1 || startsWith) {
                this.serverProcess = runtime.exec(bldCmdLine);
            }
            else {
                int n = 0;
                final String[] split = bldCmdLine.split(" ");
                final StreamTokenizer streamTokenizer = new StreamTokenizer(new StringReader(bldCmdLine));
                streamTokenizer.resetSyntax();
                streamTokenizer.wordChars(33, 126);
                streamTokenizer.quoteChar(34);
                streamTokenizer.quoteChar(39);
                streamTokenizer.whitespaceChars(9, 13);
                streamTokenizer.whitespaceChars(32, 32);
                while (streamTokenizer.nextToken() != -1) {
                    switch (streamTokenizer.ttype) {
                        case 10: {
                            break;
                        }
                        case -2: {
                            split[n] = Double.toString(streamTokenizer.nval);
                            break;
                        }
                        case -3: {
                            split[n] = streamTokenizer.sval;
                            break;
                        }
                        default: {
                            if (streamTokenizer.ttype == 34 || streamTokenizer.ttype == 39) {
                                split[n] = streamTokenizer.sval;
                                break;
                            }
                            split[n] = String.valueOf((char)streamTokenizer.ttype);
                            break;
                        }
                    }
                    if (++n >= split.length) {
                        break;
                    }
                }
                final String[] cmdarray = new String[n];
                while (n-- > 0) {
                    cmdarray[n] = split[n];
                }
                this.serverProcess = runtime.exec(cmdarray);
            }
            if (super.log.ifLogBasic(1L, 0)) {
                super.log.logBasic(0, 7665689515738013612L, new Object[] { bldCmdLine });
            }
        }
        catch (IOException ex) {
            super.log.logError(7665689515738013613L, new Object[] { ex.getMessage(), "" });
            this.serverProcess = null;
            return false;
        }
        boolean b = this.getServerStream();
        if (b) {
            b = this.getServerPortnum();
        }
        switch (this.properties.serverType) {
            case 2:
            case 3:
            case 5: {
                break;
            }
            default: {
                try {
                    if (b && this.serverIPC == null) {
                        this.serverIPC = this.initServerIPC(this.serverPort);
                    }
                }
                catch (ServerIPCException ex2) {
                    super.log.logError(7665689515738013623L, new Object[] { "startServer()", ex2.getMessage() });
                    b = false;
                }
                break;
            }
        }
        if (this.serverProcess != null) {
            try {
                this.serverProcess.getOutputStream().close();
            }
            catch (Exception obj) {
                if (super.log.ifLogBasic(1L, 0)) {
                    super.log.logBasic(0, "Error closing output stream to agent = " + obj);
                }
            }
            try {
                this.serverProcess.getErrorStream().close();
            }
            catch (Exception obj2) {
                if (super.log.ifLogBasic(1L, 0)) {
                    super.log.logBasic(0, "Error closing error stream to agent = " + obj2);
                }
            }
            this.serverProcess = null;
        }
        return b;
    }
    
    boolean getServerStream() {
        if (super.log.ifLogBasic(2L, 1)) {
            super.log.logBasic(1, " creating server input stream ... ");
        }
        this.fromServer = new BufferedReader(new InputStreamReader(this.serverProcess.getInputStream()));
        return true;
    }
    
    boolean getServerPortnum() {
        if (super.log.ifLogBasic(2L, 1)) {
            super.log.logBasic(1, this.getFullName() + " : reading fromServer ... ");
        }
        this.serverPort = 0;
        this.serverPid = 0;
        boolean b = false;
        try {
            String pipeInput;
            while ((pipeInput = this.getPipeInput(this.fromServer, true)) != null) {
                if (super.log.ifLogBasic(2L, 1)) {
                    super.log.logDump(2, 1, "From " + this.getFullName() + " : ", pipeInput.getBytes(), pipeInput.length());
                }
                ubServerPipeMsg ubServerPipeMsg;
                try {
                    ubServerPipeMsg = new ubServerPipeMsg(pipeInput);
                    if (super.log.ifLogBasic(2L, 1)) {
                        ubServerPipeMsg.print("From " + this.getFullName() + " : ", 2, 1, super.log);
                    }
                }
                catch (ubServerPipeMsg.InvalidMsgFormatException ex) {
                    if (super.log.ifLogBasic(1L, 0)) {
                        super.log.logBasic(0, 7665689515738013617L, new Object[] { pipeInput });
                    }
                    if (!super.log.ifLogBasic(2L, 1)) {
                        continue;
                    }
                    super.log.logDump(2, 1, "From " + this.getFullName() + " : invalid msg (" + ex.getMessage() + ":" + ex.getDetail() + ")", pipeInput.getBytes(), pipeInput.length());
                    continue;
                }
                if (ubServerPipeMsg.getMsgcode() == 2 && ubServerPipeMsg.getResult() && ubServerPipeMsg.getNumArgs() == 2 && ubServerPipeMsg.getArg(0) instanceof Integer && ubServerPipeMsg.getArg(1) instanceof Integer) {
                    this.serverPort = (int)ubServerPipeMsg.getArg(0);
                    super.stats.setConnServerPort(this.serverPort);
                    this.serverPid = (int)ubServerPipeMsg.getArg(1);
                    super.stats.setConnServerPID(this.serverPid);
                    b = true;
                    if (super.log.ifLogBasic(1L, 0)) {
                        super.log.logBasic(0, 7665689515738013618L, new Object[] { new Integer(this.serverPort), new Integer(this.serverPid) });
                        break;
                    }
                    break;
                }
                else if (ubServerPipeMsg.getMsgcode() == 1) {
                    super.log.logError(7665689515738013617L, new Object[] { ubServerPipeMsg.getDescription() });
                }
                else if (ubServerPipeMsg.getMsgcode() == 8) {
                    this.serverLog.logError(ubServerPipeMsg.getDescription());
                }
                else {
                    ubServerPipeMsg.print("Unexpected msg from " + this.getFullName() + " : ", 1, 0, super.log);
                }
            }
            if (super.log.ifLogBasic(2L, 1)) {
                super.log.logBasic(1, "Done reading fromServer.");
            }
        }
        catch (IOException ex2) {
            super.log.logError(7665689515738013621L, new Object[] { ex2.toString() });
        }
        return b;
    }
    
    void processServerShutdownPipeMsgs() {
        try {
            String pipeInput;
            while ((pipeInput = this.getPipeInput(this.fromServer, true)) != null) {
                if (super.log.ifLogBasic(2L, 1)) {
                    super.log.logDump(2, 1, "From " + this.getFullName() + " : ", pipeInput.getBytes(), pipeInput.length());
                }
                try {
                    final ubServerPipeMsg ubServerPipeMsg = new ubServerPipeMsg(pipeInput);
                    if (super.log.ifLogBasic(2L, 1)) {
                        ubServerPipeMsg.print("From " + this.getFullName() + " : ", 2, 1, super.log);
                    }
                    switch (ubServerPipeMsg.getMsgcode()) {
                        case 8: {
                            this.serverLog.logError(ubServerPipeMsg.getDescription());
                            continue;
                        }
                    }
                }
                catch (ubServerPipeMsg.InvalidMsgFormatException ex2) {
                    if (super.log.ifLogBasic(2L, 1)) {
                        super.log.logDump(2, 1, "msg from server : invalid msg format : ", pipeInput.getBytes(), pipeInput.length());
                    }
                    super.log.logError("msg from server : (" + pipeInput + ")");
                }
            }
            if (super.log.ifLogBasic(2L, 1)) {
                super.log.logBasic(1, this.getFullName() + " : done reading fromServer.");
            }
        }
        catch (IOException ex) {
            if (super.log.ifLogBasic(2L, 1)) {
                super.log.logBasic(1, this.getFullName() + " : fromServer exception : " + ex.getMessage());
            }
        }
    }
    
    ubMsg getServerPipeMsg() {
        ubMsg ubMsg = null;
        try {
            final String pipeInput;
            if ((pipeInput = this.getPipeInput(this.fromServer, false)) != null) {
                if (super.log.ifLogBasic(2L, 1)) {
                    super.log.logDump(2, 1, "From " + this.getFullName() + " : ", pipeInput.getBytes(), pipeInput.length());
                }
                try {
                    final ubServerPipeMsg ubServerPipeMsg = new ubServerPipeMsg(pipeInput);
                    if (super.log.ifLogBasic(2L, 1)) {
                        ubServerPipeMsg.print("From " + this.getFullName() + " : ", 2, 1, super.log);
                    }
                    switch (ubServerPipeMsg.getMsgcode()) {
                        case 3: {
                            ubMsg = new ubAdminMsg((byte)5);
                            if (ubServerPipeMsg.getNumArgs() > 0) {
                                ((ubAdminMsg)ubMsg).setadParm(new Object[] { ubServerPipeMsg.getArg(0), ubServerPipeMsg.getArg(1), ubServerPipeMsg.getArg(2), ubServerPipeMsg.getArg(3), ubServerPipeMsg.getArg(4) });
                                break;
                            }
                            ((ubAdminMsg)ubMsg).setadParm(new Object[] { new Integer(0) });
                            break;
                        }
                        case 4: {
                            ubMsg = new ubAdminMsg((byte)6);
                            if (ubServerPipeMsg.getNumArgs() > 0) {
                                ((ubAdminMsg)ubMsg).setadParm(new Object[] { ubServerPipeMsg.getArg(0) });
                                break;
                            }
                            ((ubAdminMsg)ubMsg).setadParm(new Object[] { new Integer(0) });
                            break;
                        }
                        case 5: {
                            ubMsg = new ubAdminMsg((byte)4);
                            break;
                        }
                        case 1: {
                            if (super.log.ifLogBasic(1L, 0)) {
                                super.log.logBasic(0, 7665689515738013617L, new Object[] { ubServerPipeMsg.getDescription() });
                                break;
                            }
                            break;
                        }
                        case 8: {
                            if (this.properties.serverType == 1) {
                                ubServerPipeMsg.getDescription();
                                ubMsg = new ubWebSpeedMsg((short)108);
                                ubMsg.setubSrc(1);
                                ubMsg.setubRq(9);
                                ((ubWebSpeedMsg)ubMsg).setwsWho(1);
                                ((ubWebSpeedMsg)ubMsg).setwsMsgtype(14);
                            }
                            this.serverLog.logError(ubServerPipeMsg.getDescription());
                            break;
                        }
                        case 9: {
                            ubMsg = new ubAdminMsg((byte)9);
                            ((ubAdminMsg)ubMsg).setadParm(new Object[] { ubServerPipeMsg });
                            break;
                        }
                        default: {
                            ubServerPipeMsg.print("From " + this.getFullName() + " : ", 1, 0, super.log);
                            break;
                        }
                    }
                }
                catch (ubServerPipeMsg.InvalidMsgFormatException ex2) {
                    if (super.log.ifLogBasic(1L, 0)) {
                        super.log.logBasic(0, 7665689515738013617L, new Object[] { pipeInput });
                    }
                    if (super.log.ifLogBasic(2L, 1)) {
                        super.log.logDump(2, 1, "msg from server : invalid msg format : ", pipeInput.getBytes(), pipeInput.length());
                    }
                }
            }
            if (!this.testServerProcess()) {
                ubMsg = new ubAdminMsg((byte)4);
            }
        }
        catch (IOException ex) {
            super.log.logError(7665689515738013621L, new Object[] { ex.toString() + " : " + ex.getMessage() });
            ubMsg = new ubAdminMsg((byte)4);
        }
        return ubMsg;
    }
    
    byte processStartup(final RequestQueue requestQueue, final ubAdminMsg ubAdminMsg, byte b) {
        final Object[] getadParm = ubAdminMsg.getadParm();
        String s;
        if (getadParm == null) {
            if (super.log.ifLogBasic(2L, 1)) {
                super.log.logBasic(1, "admparm= null");
            }
            this.clientRspQueue = null;
            s = null;
        }
        else {
            if (super.log.ifLogBasic(2L, 1)) {
                for (int i = 0; i < getadParm.length; ++i) {
                    if (getadParm[i] != null) {
                        super.log.logBasic(1, "admparm[" + i + "]= (" + getadParm[i].toString() + ")");
                    }
                    else {
                        super.log.logBasic(1, "admparm[" + i + "]= (" + getadParm[i] + ")");
                    }
                }
            }
            this.clientRspQueue = ((getadParm.length > 0 && getadParm[0] != null && getadParm[0] instanceof RequestQueue) ? ((RequestQueue)getadParm[0]) : null);
            if (super.log.ifLogBasic(2L, 1)) {
                super.log.logBasic(1, "admparm[0] instanceof RequestQueue=" + (getadParm[0] instanceof RequestQueue));
            }
            s = ((getadParm.length > 1 && getadParm[1] != null && getadParm[1] instanceof String) ? ((String)getadParm[1]) : null);
        }
        byte b2;
        if (!this.startServer(s)) {
            if (super.log.ifLogBasic(1L, 0)) {
                super.log.logBasic(0, 7665689515738013604L, new Object[0]);
            }
            b2 = 11;
            ubAdminMsg.setadRsp(2);
            this.clientRspQueue = null;
        }
        else {
            switch (this.properties.serverType) {
                case 0: {
                    if (this.pingAgent()) {
                        ubAdminMsg.setadRsp(0);
                        break;
                    }
                    ubAdminMsg.setadRsp(2);
                    this.clientRspQueue = null;
                    b = 11;
                    break;
                }
                default: {
                    ubAdminMsg.setadRsp(0);
                    break;
                }
            }
            if (super.log.ifLogBasic(2L, 1)) {
                super.log.logBasic(1, "clientRspQueue=" + this.clientRspQueue);
            }
            b2 = (byte)((this.clientRspQueue == null) ? b : 10);
        }
        super.stats.settsStartTime();
        return b2;
    }
    
    byte processDisconnect_prev(final RequestQueue requestQueue, final ubMsg ubMsg, final byte b) {
        int intValue = 0;
        if (ubMsg instanceof ubAdminMsg) {
            final Object[] getadParm = ((ubAdminMsg)ubMsg).getadParm();
            if (getadParm != null && getadParm.length > 0 && getadParm[0] instanceof Integer) {
                intValue = (int)getadParm[0];
            }
        }
        if (intValue == -4008) {
            final IEventBroker adminServerEventBroker = this.findAdminServerEventBroker(this.properties.rmiURL, this.properties.brokerName);
            if (adminServerEventBroker != null) {
                try {
                    adminServerEventBroker.postEvent(new EUbrokerClientAbnormalDisconnectEvent(this.properties.brokerName, "Client disconnected abnormally", this.properties.canonicalName));
                }
                catch (RemoteException ex) {}
            }
        }
        synchronized (super.stats) {
            super.stats.setConnRmtHost(null);
            super.stats.setConnRmtPort(0);
            super.stats.setConnUserName(null);
            super.stats.setConnUserName(null);
            super.stats.setConnRqs(0);
            super.stats.setConnID(null);
        }
        if (super.log.ifLogBasic(2L, 1)) {
            super.log.logBasic(1, "processDisconnect, disconnect status is " + intValue);
        }
        return b;
    }
    
    byte processDisconnect(final RequestQueue requestQueue, final ubMsg ubMsg, final byte b) {
        final int cvtNumericParam = this.cvtNumericParam(this.parseAgentStatusChgMsg((ubAppServerMsg)ubMsg)[0], 0);
        if (cvtNumericParam == -4008) {
            final IEventBroker adminServerEventBroker = this.findAdminServerEventBroker(this.properties.rmiURL, this.properties.brokerName);
            if (adminServerEventBroker != null) {
                try {
                    adminServerEventBroker.postEvent(new EUbrokerClientAbnormalDisconnectEvent(this.properties.brokerName, "Client disconnected abnormally", this.properties.canonicalName));
                }
                catch (RemoteException ex) {}
            }
        }
        synchronized (super.stats) {
            super.stats.setConnRmtHost(null);
            super.stats.setConnRmtPort(0);
            super.stats.setConnUserName(null);
            super.stats.setConnUserName(null);
            super.stats.setConnRqs(0);
            super.stats.setConnID(null);
        }
        if (super.log.ifLogBasic(2L, 1)) {
            super.log.logBasic(1, "processDisconnect, disconnect status is " + cvtNumericParam);
        }
        return b;
    }
    
    byte processShutdown(final RequestQueue requestQueue, final ubMsg ubMsg, final byte b) {
        if (this.current_state == 0) {
            ((ubAdminMsg)ubMsg).setadRsp(0);
            this.sendRsp(requestQueue, ubMsg);
            this.processServerShutdownPipeMsgs();
            return b;
        }
        return this.shutdownServerProcess(requestQueue, (ubAdminMsg)ubMsg, b);
    }
    
    byte processAdminMsg(final RequestQueue requestQueue, final ubMsg ubMsg, final byte b) {
        return this.sendAdminMessage(requestQueue, (ubAdminMsg)ubMsg, b);
    }
    
    byte processAdminRspMsg(final RequestQueue requestQueue, final ubMsg ubMsg, final byte b) {
        return this.readAdminResponse(requestQueue, ubMsg, b);
    }
    
    byte processTerminate() {
        this.sendAgentKilledEvent();
        return 11;
    }
    
    private IEventBroker findAdminServerEventBrokerXXX() {
        IEventBroker eventBroker = null;
        try {
            final String rmiURL = this.properties.rmiURL;
            System.setSecurityManager(new RMISecurityManager());
            eventBroker = ((IAdministrationServer)Naming.lookup(rmiURL.substring(0, rmiURL.lastIndexOf(this.properties.brokerName)) + "Chimera")).getEventBroker();
        }
        catch (Exception ex) {
            if (this.properties.rmiURL != null) {
                super.log.logStackTrace("$$$$ Can't locate AdminServer's EventBroker", ex);
            }
        }
        return eventBroker;
    }
    
    private void sendAgentKilledEvent() {
        final COpenEdgeManagementContent cOpenEdgeManagementContent = new COpenEdgeManagementContent(this.properties.brokerName, new Object[] { new Integer(this.getServerPid()) });
        final IEventBroker adminServerEventBroker = this.findAdminServerEventBroker(this.properties.rmiURL, this.properties.brokerName);
        if (adminServerEventBroker != null) {
            try {
                final EAbnormalShutdownServerEvent eAbnormalShutdownServerEvent = new EAbnormalShutdownServerEvent(this.properties.brokerName, cOpenEdgeManagementContent);
                eAbnormalShutdownServerEvent.setSource(this.properties.canonicalName);
                adminServerEventBroker.postEvent(eAbnormalShutdownServerEvent);
                super.log.logError("Posted EAbnormalShutdownServerEvent for PID: " + this.getServerPid());
            }
            catch (RemoteException ex) {
                this.serverLog.logStackTrace("Error posting EAbnormalShutdownServerEvent for Agent/Server PID: " + this.getServerPid(), ex);
            }
        }
    }
    
    byte processInitRq(final RequestQueue requestQueue, final ubMsg ubMsg, final byte b) {
        return this.processFatalError(requestQueue, 2, "UBRQ_INIT_RQ not supported");
    }
    
    byte processWrite(final RequestQueue requestQueue, final ubMsg ubMsg, final byte b) {
        return this.processFatalError(requestQueue, 2, "UBRQ_WRITEDATA not supported");
    }
    
    byte processWriteClose(final RequestQueue requestQueue, final ubMsg ubMsg, final byte b) {
        return this.processFatalError(requestQueue, 2, "UBRQ_WRITEDATALAST not supported");
    }
    
    byte processRead(final RequestQueue requestQueue, final ubMsg ubMsg, final byte b) {
        return this.processFatalError(requestQueue, 2, "UBRQ_RSPDATA not supported");
    }
    
    byte processFinishRq(final RequestQueue requestQueue, final ubMsg ubMsg, final byte b) {
        return this.processFatalError(requestQueue, 2, "UBRQ_FINISH_RQ not supported");
    }
    
    byte processStop(final RequestQueue requestQueue, final ubMsg ubMsg, final byte b) {
        return this.processFatalError(requestQueue, 2, "UBRQ_SETSTOP not supported");
    }
    
    byte processIgnore(final RequestQueue requestQueue, final ubMsg ubMsg, final byte b) {
        return b;
    }
    
    byte processConnectTimeout(final RequestQueue requestQueue, final ubMsg ubMsg, final byte b) {
        return this.processIgnore(requestQueue, ubMsg, b);
    }
    
    byte processProcStats(final RequestQueue requestQueue, final ubMsg ubMsg, final byte b) {
        return this.processIgnore(requestQueue, ubMsg, b);
    }
    
    byte processClientConnected(final RequestQueue requestQueue, final ubMsg ubMsg, final byte b) {
        return this.processIgnore(requestQueue, ubMsg, b);
    }
    
    boolean pollServerIPCMsg() {
        boolean b;
        try {
            b = (!this.testServerProcess() || (this.serverIPC != null && this.serverIPC.available() > 0));
        }
        catch (ServerIPCException ex) {
            super.log.logError(7665689515738013623L, new Object[] { "pollServerIPCMsg()", ex.getMessage() });
            b = true;
        }
        return b;
    }
    
    boolean pollServerPipeMsg() {
        boolean b;
        try {
            b = (!this.testServerProcess() || (this.fromServer != null && this.fromServer.ready()));
        }
        catch (IOException obj) {
            super.log.logError("IOException in pollServerPipeMsg(): " + obj + " : " + obj.getMessage());
            b = true;
        }
        return b;
    }
    
    ubMsg getServerIPCMsg() {
        ubMsg read = null;
        boolean b = true;
        if (this.testServerProcess()) {
            try {
                if (this.serverIPC == null) {
                    this.serverIPC = this.initServerIPC(this.serverPort);
                }
                read = this.serverIPC.read();
            }
            catch (ServerIPCException ex) {
                super.log.logError(7665689515738013623L, new Object[] { "getServerIPCMsg()", ex.getMessage() });
                b = false;
            }
        }
        else {
            super.log.logError("Server Process " + this.serverPid + " has terminated.");
            b = false;
        }
        if (!b) {
            this.logServerPipeMsgs();
            super.stats.incrnErrors();
            read = new ubAdminMsg((byte)4);
        }
        return read;
    }
    
    byte processServerStateChg(final ubMsg ubMsg, final byte b) {
        return this.processFatalError(this.rspQueue, 2, "ACTION_UPD_SRVR_STATE not supported");
    }
    
    byte processFatalError(final RequestQueue requestQueue, final int value, final String s) {
        this.sendRsp(requestQueue, this.clientRsp(value, s));
        super.log.logError(7665689515738013625L, new Object[] { new Integer(value), s });
        return 11;
    }
    
    boolean sendRsp(final RequestQueue requestQueue, final ubMsg ubMsg) {
        if (requestQueue == null) {
            if (super.log.ifLogBasic(2L, 1)) {
                ubMsg.print("Unable to enqueue msg to null destQueue", 2, 1, super.log);
            }
            return false;
        }
        if (super.log.ifLogBasic(128L, 7)) {
            ubMsg.print("Enqueueing msg to " + requestQueue.getListName(), 2, 7, super.log);
        }
        final Request request = new Request(ubMsg, super.rcvQueue);
        try {
            if (super.log.ifLogBasic(512L, 9)) {
                super.trace.addMsg((ubMsg)request.getMsg(), this.getFullName(), this.getFullName(), requestQueue.getListName());
            }
            if (super.log.ifLogBasic(2L, 1)) {
                super.log.logBasic(1, "Enqueued request " + ((ubMsg)request.getMsg()).getubRqDesc() + " to " + requestQueue.getListName());
            }
            requestQueue.enqueueRequest(request);
        }
        catch (Queue.QueueException ex) {
            return false;
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
    
    IServerIPC initServerIPC(final int n) throws ServerIPCException {
        final ubServerSocketsIPC ubServerSocketsIPC = new ubServerSocketsIPC(this.properties, super.log, 2);
        ubServerSocketsIPC.create(n, this.properties.getValueAsInt("brkrSpinInterval"));
        return ubServerSocketsIPC;
    }
    
    void logServerPipeMsgs() {
        try {
            while (this.fromServer != null && this.fromServer.ready()) {
                final String pipeInput;
                if ((pipeInput = this.getPipeInput(this.fromServer, true)) != null) {
                    try {
                        final ubServerPipeMsg ubServerPipeMsg = new ubServerPipeMsg(pipeInput);
                        switch (ubServerPipeMsg.getMsgcode()) {
                            case 8: {
                                this.serverLog.logError(ubServerPipeMsg.getDescription());
                                continue;
                            }
                            default: {
                                if (super.log.ifLogBasic(1L, 0)) {
                                    super.log.logBasic(0, 7665689515738013617L, new Object[] { pipeInput });
                                    continue;
                                }
                                continue;
                            }
                        }
                    }
                    catch (ubServerPipeMsg.InvalidMsgFormatException ex2) {
                        if (!super.log.ifLogBasic(1L, 0)) {
                            continue;
                        }
                        super.log.logBasic(0, 7665689515738013617L, new Object[] { pipeInput });
                    }
                }
            }
        }
        catch (IOException ex) {
            super.log.logError("Error reading from server : " + ex.getMessage());
        }
    }
    
    void processServerLogMsg(final ubMsg ubMsg) {
        ubMsg.getBuflen();
        final byte[] msgbuf = ubMsg.getMsgbuf();
        if (this.serverLog.ifLogBasic(1L, 0)) {
            this.serverLog.logBasic(0, 7665689515738013617L, new Object[] { msgbuf });
        }
    }
    
    ubMsg newTimeoutMsg() {
        return new ubAdminMsg((byte)8);
    }
    
    ubMsg getServerPipeEvent() {
        String s = "";
        ubMsg serverPipeMsg = null;
        while (serverPipeMsg == null) {
            if (this.pollServerPipeMsg()) {
                serverPipeMsg = this.getServerPipeMsg();
                if (serverPipeMsg == null) {
                    continue;
                }
                if (super.log.ifLogBasic(2L, 1)) {
                    super.log.logBasic(1, "getServerPipeEvent() got serverPipeMsg event: " + serverPipeMsg);
                }
                s = "ServerPipe";
            }
            else if (this.lifespanExpired() && this.current_state == 1) {
                serverPipeMsg = new ubAdminMsg((byte)2);
                if (this.rspQueue == null) {
                    this.rspQueue = new RequestQueue("tmpQueue", 5, super.log);
                }
                if (!super.log.ifLogBasic(1L, 0)) {
                    continue;
                }
                super.log.logBasic(0, 7665689515738019304L, new Object[0]);
            }
            else {
                final Request pollRequest = super.rcvQueue.pollRequest(this.properties.getValueAsInt("brkrSpinInterval"));
                if (pollRequest == null) {
                    continue;
                }
                serverPipeMsg = (ubMsg)pollRequest.getMsg();
                if (((serverPipeMsg instanceof ubAdminMsg) ? ((ubAdminMsg)serverPipeMsg).getadSrc() : 0) == 5) {
                    this.adminQueue = (RequestQueue)pollRequest.getRspQueue();
                }
                else {
                    this.rspQueue = (RequestQueue)pollRequest.getRspQueue();
                }
                s = ((this.rspQueue == null) ? "null" : this.rspQueue.toString());
                if (!super.log.ifLogBasic(2L, 1)) {
                    continue;
                }
                super.log.logBasic(1, "getServerPipeEvent() got queue event: " + serverPipeMsg);
            }
        }
        if (serverPipeMsg != null && super.log.ifLogBasic(512L, 9)) {
            super.trace.addMsg(serverPipeMsg, this.getFullName(), s, this.getFullName());
        }
        return serverPipeMsg;
    }
    
    ubMsg getServerPipeEvent(final int n) {
        String s = "";
        final long currentTimeMillis = System.currentTimeMillis();
        ubMsg serverPipeMsg = null;
        while (serverPipeMsg == null) {
            if (this.pollServerPipeMsg()) {
                serverPipeMsg = this.getServerPipeMsg();
                if (serverPipeMsg == null) {
                    continue;
                }
                if (super.log.ifLogBasic(2L, 1)) {
                    super.log.logBasic(1, "getServerPipeEvent() got serverPipeMsg event: " + serverPipeMsg);
                }
                s = "ServerPipe";
            }
            else if (this.lifespanExpired() && this.current_state == 1) {
                serverPipeMsg = new ubAdminMsg((byte)2);
                if (this.rspQueue == null) {
                    this.rspQueue = new RequestQueue("tmpQueue", 5, super.log);
                }
                if (!super.log.ifLogBasic(1L, 0)) {
                    continue;
                }
                super.log.logBasic(0, 7665689515738019304L, new Object[0]);
            }
            else {
                final Request pollRequest = super.rcvQueue.pollRequest(this.properties.getValueAsInt("brkrSpinInterval"));
                if (pollRequest != null) {
                    serverPipeMsg = (ubMsg)pollRequest.getMsg();
                    if (((serverPipeMsg instanceof ubAdminMsg) ? ((ubAdminMsg)serverPipeMsg).getadSrc() : 0) == 5) {
                        this.adminQueue = (RequestQueue)pollRequest.getRspQueue();
                    }
                    else {
                        this.rspQueue = (RequestQueue)pollRequest.getRspQueue();
                    }
                    s = ((this.rspQueue == null) ? "null" : this.rspQueue.toString());
                    if (!super.log.ifLogBasic(2L, 1)) {
                        continue;
                    }
                    super.log.logBasic(1, "getServerPipeEvent() got queue event: " + serverPipeMsg);
                }
                else {
                    if (n > 0 && System.currentTimeMillis() - currentTimeMillis > n) {
                        serverPipeMsg = null;
                        break;
                    }
                    continue;
                }
            }
        }
        if (serverPipeMsg != null && super.log.ifLogBasic(512L, 9)) {
            super.trace.addMsg(serverPipeMsg, this.getFullName(), s, this.getFullName());
        }
        return serverPipeMsg;
    }
    
    ubMsg getQueueEvent() {
        String s = "";
        ubMsg obj = null;
        while (obj == null) {
            if (this.pollServerIPCMsg()) {
                obj = this.getServerIPCMsg();
                s = ((this.serverIPC == null) ? "null" : this.serverIPC.toString());
                if (!super.log.ifLogBasic(2L, 1)) {
                    continue;
                }
                super.log.logBasic(1, "getQueueEvent() got serverIPCMsg event: " + obj);
            }
            else if (this.pollServerPipeMsg()) {
                obj = this.getServerPipeMsg();
                if (obj == null) {
                    continue;
                }
                s = "ServerPipe";
                if (!super.log.ifLogBasic(2L, 1)) {
                    continue;
                }
                super.log.logBasic(1, "getQueueEvent() got serverPipeMsg event: " + obj);
            }
            else if (this.lifespanExpired() && this.current_state == 1) {
                obj = new ubAdminMsg((byte)2);
                if (!super.log.ifLogBasic(1L, 0)) {
                    continue;
                }
                super.log.logBasic(0, "Server Lifespan has EXPIRED.  Shutting down idle server.");
            }
            else {
                final Request pollRequest = super.rcvQueue.pollRequest(this.properties.getValueAsInt("brkrSpinInterval"));
                if (pollRequest == null) {
                    continue;
                }
                pollRequest.logStats(super.log);
                obj = (ubMsg)pollRequest.getMsg();
                if (((obj instanceof ubAdminMsg) ? ((ubAdminMsg)obj).getadSrc() : 0) == 5) {
                    this.adminQueue = (RequestQueue)pollRequest.getRspQueue();
                }
                else {
                    this.rspQueue = (RequestQueue)pollRequest.getRspQueue();
                }
                s = ((this.rspQueue == null) ? "null" : this.rspQueue.toString());
                if (!super.log.ifLogBasic(2L, 1)) {
                    continue;
                }
                super.log.logBasic(1, "getQueueEvent() got queue event: " + obj);
            }
        }
        if (obj != null && super.log.ifLogBasic(512L, 9)) {
            super.trace.addMsg(obj, this.getFullName(), s, this.getFullName());
        }
        return obj;
    }
    
    ubMsg getServerIPCEvent() {
        String string = "";
        ubMsg obj = null;
        while (obj == null) {
            if (!super.rcvQueue.isEmpty()) {
                final Request dequeueRequest = super.rcvQueue.dequeueRequest();
                dequeueRequest.logStats(super.log);
                obj = (ubMsg)dequeueRequest.getMsg();
                if (((obj instanceof ubAdminMsg) ? ((ubAdminMsg)obj).getadSrc() : 0) == 5) {
                    this.adminQueue = (RequestQueue)dequeueRequest.getRspQueue();
                }
                else {
                    this.rspQueue = (RequestQueue)dequeueRequest.getRspQueue();
                }
                string = ((this.rspQueue == null) ? "null" : this.rspQueue.toString());
                if (!super.log.ifLogBasic(2L, 1)) {
                    continue;
                }
                super.log.logBasic(1, "getServerIPCEvent() got rcvQueue msg: " + obj);
            }
            else if (this.pollServerPipeMsg()) {
                obj = this.getServerPipeMsg();
                if (obj == null) {
                    continue;
                }
                string = "ServerPipe";
                if (!super.log.ifLogBasic(2L, 1)) {
                    continue;
                }
                super.log.logBasic(1, "getServerIPCEvent() got serverPipeMsg event: " + obj);
            }
            else {
                obj = this.getServerIPCMsg();
                if (obj == null) {
                    continue;
                }
                string = this.serverIPC.toString();
                if (!super.log.ifLogBasic(2L, 1)) {
                    continue;
                }
                super.log.logBasic(1, "getServerIPCEvent() got ServerIPC event: " + obj);
            }
        }
        if (obj != null && super.log.ifLogBasic(512L, 9)) {
            super.trace.addMsg(obj, this.getFullName(), string, this.getFullName());
        }
        return obj;
    }
    
    ubMsg getServerIPCEvent(final boolean b, final boolean b2, final int n) {
        String string = "";
        final long currentTimeMillis = System.currentTimeMillis();
        ubMsg obj = null;
        while (obj == null) {
            if (b && !super.rcvQueue.isEmpty()) {
                final Request dequeueRequest = super.rcvQueue.dequeueRequest();
                dequeueRequest.logStats(super.log);
                obj = (ubMsg)dequeueRequest.getMsg();
                if (((obj instanceof ubAdminMsg) ? ((ubAdminMsg)obj).getadSrc() : 0) == 5) {
                    this.adminQueue = (RequestQueue)dequeueRequest.getRspQueue();
                }
                else {
                    this.rspQueue = (RequestQueue)dequeueRequest.getRspQueue();
                }
                string = ((this.rspQueue == null) ? "null" : this.rspQueue.toString());
                if (super.log.ifLogBasic(128L, 7)) {
                    super.log.logBasic(7, "getServerIPCEvent() got rcvQueue msg: " + obj);
                }
            }
            else if (b2 && this.pollServerPipeMsg()) {
                obj = this.getServerPipeMsg();
                if (obj != null) {
                    string = "ServerPipe";
                    if (super.log.ifLogBasic(128L, 7)) {
                        super.log.logBasic(7, "getServerIPCEvent() got serverPipeMsg event: " + obj);
                    }
                }
            }
            else {
                obj = this.getServerIPCMsg();
                if (obj != null) {
                    string = this.serverIPC.toString();
                    if (super.log.ifLogBasic(16L, 4)) {
                        super.log.logBasic(4, "getServerIPCEvent() got ServerIPC event: " + obj);
                    }
                }
            }
            if (obj == null && n > 0 && System.currentTimeMillis() - currentTimeMillis > n) {
                break;
            }
        }
        if (obj != null && super.log.ifLogBasic(512L, 9)) {
            super.trace.addMsg(obj, this.getFullName(), string, this.getFullName());
        }
        return obj;
    }
    
    void drainRcvQueue() {
        super.rcvQueue.close();
        if (super.log.ifLogBasic(2L, 1)) {
            super.rcvQueue.print(super.log, 2, 1);
        }
        while (!super.rcvQueue.isEmpty()) {
            final Request dequeueRequest = super.rcvQueue.dequeueRequest();
            final ubMsg ubMsg = (ubMsg)dequeueRequest.getMsg();
            final RequestQueue requestQueue = (RequestQueue)dequeueRequest.getRspQueue();
            if (super.log.ifLogBasic(2L, 1)) {
                ubMsg.print("drainQueue() dequeued request : ", 2, 1, super.log);
            }
            this.sendRsp(requestQueue, new ubAdminMsg((byte)3));
        }
    }
    
    void disconnectClientThread(final RequestQueue requestQueue) {
        if (requestQueue != null) {
            final ubAdminMsg ubAdminMsg = new ubAdminMsg((byte)3);
            if (super.log.ifLogBasic(2L, 1)) {
                ubAdminMsg.print("notifyClientThread() issued request : ", 2, 1, super.log);
            }
            this.sendRsp(requestQueue, ubAdminMsg);
        }
    }
    
    void releaseRspQueue() {
        this.rspQueue = null;
        this.clientRspQueue = null;
        this.adminQueue = null;
    }
    
    private int getPipeChar(final BufferedReader obj) throws IOException {
        if (super.log.ifLogBasic(2L, 1)) {
            super.log.logBasic(1, "getPipeChar() : reading " + obj + " ...");
        }
        return obj.read();
    }
    
    private String getPipeInput(final BufferedReader bufferedReader, final boolean b) throws IOException {
        final byte[] bytes = new byte[4096];
        final StringBuffer sb = new StringBuffer();
        if (bufferedReader == null) {
            return null;
        }
        while (bufferedReader.ready() || b) {
            final int read = bufferedReader.read();
            if (read == 2) {
                if (sb.length() > 0 && super.log.ifLogVerbose(1L, 0)) {
                    super.log.logVerbose(0, 7665689515738013617L, new Object[] { sb.toString() });
                }
                int length = 0;
                bytes[length++] = (byte)read;
                int read2;
                while (length < bytes.length && (read2 = bufferedReader.read()) != 3) {
                    if (read2 == -1) {
                        throw new EOFException("pipe to server broken");
                    }
                    bytes[length] = (byte)read2;
                    ++length;
                }
                return (length == 0) ? new String("") : new String(bytes, 0, length);
            }
            if (read == -1) {
                throw new EOFException("pipe to server broken");
            }
            sb.append((char)read);
        }
        if (sb.length() > 0 && super.log.ifLogVerbose(1L, 0)) {
            super.log.logVerbose(0, 7665689515738013617L, new Object[] { sb.toString() });
        }
        return null;
    }
    
    private long computeServerLifespan() {
        long n = this.properties.getValueAsInt("serverLifespan");
        if (n > 0L) {
            n = n * 60000L + this.lifespanPadding();
        }
        return n;
    }
    
    private int lifespanPadding() {
        final Random random = new Random();
        int def_LIFESPAN_PAD = ubServerThread.DEF_LIFESPAN_PAD;
        int nextInt = 0;
        if (this.properties.getValueAsInt("serverLifespanPadding") >= 0) {
            def_LIFESPAN_PAD = this.properties.getValueAsInt("serverLifespanPadding") * 60000;
        }
        if (def_LIFESPAN_PAD > 0) {
            nextInt = random.nextInt(def_LIFESPAN_PAD);
        }
        return nextInt;
    }
    
    private void updateStatusTimes(final int prevStatus) {
        final long currentTimeMillis = System.currentTimeMillis();
        if (this.properties.getValueAsInt("collectStatsData") > 0) {
            if (this.prevStatus != -1 && this.lastTimeInStatus != -1L) {
                switch (this.prevStatus) {
                    case 2: {
                        ++this.nLockedCnt;
                        this.totLockedTime += currentTimeMillis - this.lastTimeInStatus;
                        break;
                    }
                    case 0: {
                        ++this.nIdleCnt;
                        this.totIdleTime += currentTimeMillis - this.lastTimeInStatus;
                        break;
                    }
                    case 3: {
                        ++this.nOtherCnt;
                        this.totOtherTime += currentTimeMillis - this.lastTimeInStatus;
                        break;
                    }
                    case 1: {
                        ++this.nBusyCnt;
                        this.totBusyTime += currentTimeMillis - this.lastTimeInStatus;
                        break;
                    }
                }
            }
            this.prevStatus = prevStatus;
            this.lastTimeInStatus = currentTimeMillis;
        }
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
            super.log.logBasic(11, "tsEndRequest : srvrRqTime= " + n);
        }
        super.stats.incTotRqDuration((int)n);
        this.tsStartRq = -1L;
    }
    
    protected boolean lifespanExpired() {
        final long currentTimeMillis = System.currentTimeMillis();
        boolean b = false;
        if (this.tsServerLifespan > 0L) {
            if (super.log.ifLogExtended(2L, 1)) {
                super.log.logExtended(1, 7665689515738019305L, new Object[0]);
            }
            b = (currentTimeMillis - this.tsStartThread > this.tsServerLifespan);
        }
        return b;
    }
    
    protected byte lifespanShutdown(final RequestQueue requestQueue, final ubMsg ubMsg, final byte b) {
        final RequestQueue requestQueue2 = new RequestQueue("tmpQueue", 5, super.log);
        final ubAdminMsg ubAdminMsg = new ubAdminMsg((byte)4);
        if (super.log.ifLogBasic(1L, 0)) {
            super.log.logBasic(0, 7665689515738019303L, new Object[0]);
        }
        return this.shutdownServerProcess(requestQueue2, ubAdminMsg, (byte)11);
    }
    
    protected byte processLifespan(final RequestQueue requestQueue, final ubMsg ubMsg, byte lifespanShutdown) {
        if (this.lifespanExpired()) {
            lifespanShutdown = this.lifespanShutdown(requestQueue, ubMsg, (byte)11);
        }
        return lifespanShutdown;
    }
    
    protected String[] parseAgentStatusChgMsg(final ubAppServerMsg ubAppServerMsg) {
        final byte[] msgbuf = ubAppServerMsg.getMsgbuf();
        final Vector<String> vector = new Vector<String>();
        final String[] a = new String[0];
        for (int i = 0; i < msgbuf.length; i = ubAppServerMsg.skipNetString(msgbuf, i)) {
            String netString = ubAppServerMsg.getNetString(msgbuf, i);
            if (netString == null) {
                netString = "";
            }
            vector.addElement(netString);
        }
        return vector.toArray(a);
    }
    
    protected int cvtNumericParam(final String s, final int n) {
        int int1;
        try {
            int1 = Integer.parseInt(s);
        }
        catch (NumberFormatException ex) {
            int1 = n;
        }
        return int1;
    }
    
    boolean pingAgent() {
        final byte b = 11;
        this.getFullName();
        final ubAdminMsg ubAdminMsg = new ubAdminMsg((byte)12);
        final RequestQueue requestQueue = new RequestQueue(this.getFullName() + "-tmpQ", this.properties.getValueAsInt("queueLimit"), super.log);
        if (super.log.ifLogBasic(2L, 1)) {
            super.log.logBasic(1, "verifying connection to agent at " + this.serverIPC);
        }
        final byte sendAdminMessage = this.sendAdminMessage(requestQueue, ubAdminMsg, b);
        final ubMsg serverIPCEvent = this.getServerIPCEvent(false, false, 0);
        String str;
        boolean b2 = false;
        if (serverIPCEvent instanceof ubAppServerMsg) {
            str = this.serverIPC.toString();
            switch (((ubAppServerMsg)serverIPCEvent).getMsgcode()) {
                case 91: {
                    b2 = true;
                    if (super.log.ifLogBasic(2L, 1)) {
                        super.log.logBasic(1, "connection to agent at " + str + " ok.");
                        break;
                    }
                    break;
                }
                default: {
                    if (super.log.ifLogBasic(2L, 1)) {
                        super.log.logBasic(1, "Unexpected message received from agent at " + str + " ... terminating agent ...");
                    }
                    if (super.log.ifLogVerbose(2L, 1)) {
                        serverIPCEvent.print("Unexpected msg from " + str + " in pingAgent() : ", 1, 0, super.log);
                    }
                    this.shutdownServerProcess(requestQueue, ubAdminMsg, sendAdminMessage);
                    b2 = false;
                    break;
                }
            }
        }
        else {
            str = this.getFullName();
            if (super.log.ifLogBasic(2L, 1)) {
                super.log.logBasic(1, "Unexpected message received from agent at " + str + " ... terminating agent ...");
            }
            if (super.log.ifLogVerbose(2L, 1)) {
                serverIPCEvent.print("Unexpected msg from " + str + " in pingAgent() : ", 1, 0, super.log);
            }
            b2 = false;
        }
        if (super.log.ifLogBasic(512L, 9)) {
            super.trace.addMsg(serverIPCEvent, this.getFullName(), str, this.rspQueue.getListName());
        }
        return b2;
    }
    
    static {
        ubServerThread.DEF_LIFESPAN_PAD = 300000;
        ubServerThread.fmt5 = new DecimalFormat("00000");
    }
}
