// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.broker;

import java.net.UnknownHostException;
import java.net.InetAddress;
import java.util.Hashtable;
import com.progress.ubroker.management.ubASProcStatsObj;
import com.progress.ubroker.management.UBrokerVST;
import java.util.StringTokenizer;
import com.progress.ubroker.util.ubAppServerMsg;
import com.progress.ubroker.util.ubMsg;
import com.progress.ubroker.util.ubAdminMsg;
import com.progress.ubroker.util.RequestQueue;
import com.progress.common.ehnlog.IAppLogger;
import com.progress.ubroker.util.ubProperties;
import com.progress.ubroker.util.ubConstants;

public class ubASserverThread extends ubServerThread implements ubConstants, IServerFSM
{
    public ubASserverThread(final int n, final int n2, final ubProperties ubProperties, final ubThreadPool ubThreadPool, final IAppLogger appLogger, final IAppLogger appLogger2) {
        super(n, n2, ubProperties, ubThreadPool, appLogger, appLogger2);
    }
    
    ubServerFSM setServerFSM(final int value, final int value2, final IAppLogger appLogger) {
        ubServerFSM ubServerFSM = null;
        switch (value2) {
            case 0: {
                ubServerFSM = new ubASstatelessFSM(appLogger);
                break;
            }
            case 3: {
                ubServerFSM = new ubASstatefreeFSM(appLogger);
                break;
            }
            case 1:
            case 2: {
                ubServerFSM = new ubASstateAwareFSM(appLogger);
                break;
            }
        }
        if (ubServerFSM == null) {
            appLogger.logError(7665689515738013627L, new Object[] { new Integer(value), new Integer(value2) });
        }
        return ubServerFSM;
    }
    
    String bldCmdLine(final String str) {
        final StringBuffer sb = new StringBuffer(100);
        final int i = this.getThreadId() & 0xFFFF;
        sb.append(super.properties.getValueAsString("srvrExecFile"));
        sb.append(" -logginglevel " + super.properties.getValueAsInt("srvrLoggingLevel"));
        sb.append(" -logfile " + super.properties.dblquote + super.properties.getValueAsString("srvrLogFile") + super.properties.dblquote);
        sb.append(" -ubpid " + super.properties.brokerPid);
        sb.append(" -Ms 1");
        sb.append(" -logname " + super.properties.brokerName);
        final String valueAsString = super.properties.getValueAsString("srvrLogEntryTypes");
        if (valueAsString != null && valueAsString.length() > 0) {
            sb.append(" -logentrytypes " + valueAsString);
        }
        sb.append(" -logthreshold " + super.properties.getValueAsInt("srvrLogThreshold"));
        sb.append(" -numlogfiles " + super.properties.getValueAsInt("srvrNumLogFiles"));
        sb.append(" -ASID " + i);
        sb.append(" -ubpropfile " + super.properties.dblquote + super.properties.propertiesfilename + super.properties.dblquote);
        if (super.properties.serverMode == 2) {
            sb.append(" -svrefresh ");
        }
        if (super.properties.getValueAsInt("mqEnabled") > 0) {
            sb.append(" -SMQPort " + super.properties.getValueAsInt("mqPort"));
            sb.append(" -SMQPID " + super.properties.getValueAsInt("mqPid"));
        }
        sb.append(" -ipver " + super.properties.ipverString());
        sb.append(" " + super.properties.getValueAsString("srvrStartupParam"));
        if (str != null) {
            sb.append(" " + str);
        }
        return sb.toString();
    }
    
    byte processStartup(final RequestQueue requestQueue, final ubAdminMsg ubAdminMsg, final byte b) {
        final byte processStartup = super.processStartup(requestQueue, ubAdminMsg, b);
        this.sendRsp(requestQueue, ubAdminMsg);
        return processStartup;
    }
    
    byte processDisconnect(final RequestQueue requestQueue, final ubMsg ubMsg, final byte b) {
        byte b2;
        if (super.properties.serverMode == 0) {
            b2 = this.processRead(requestQueue, ubMsg, b);
        }
        else {
            b2 = super.processDisconnect(requestQueue, ubMsg, b);
        }
        return this.processLifespan(requestQueue, ubMsg, b2);
    }
    
    byte processShutdown(final RequestQueue requestQueue, final ubMsg ubMsg, final byte b) {
        return super.processShutdown(requestQueue, ubMsg, b);
    }
    
    byte processTerminate() {
        return super.processTerminate();
    }
    
    byte processConnect_prev(final RequestQueue requestQueue, final ubMsg ubMsg, byte processProcStats) {
        byte processLifespan = processProcStats;
        ubMsg serverIPCEvent = null;
        String s = this.getFullName();
        if (super.properties.serverMode == 0) {
            boolean b = true;
            try {
                if (super.serverIPC == null) {
                    super.serverIPC = this.initServerIPC(super.serverPort);
                }
                if (super.log.ifLogBasic(512L, 9)) {
                    super.trace.addMsg(ubMsg, this.getFullName(), this.getFullName(), super.serverIPC.toString());
                }
                this.tsStartRequest();
                super.serverIPC.write(ubMsg, true);
                int i = 1;
                while (i != 0) {
                    serverIPCEvent = this.getServerIPCEvent();
                    if (serverIPCEvent instanceof ubAppServerMsg) {
                        if ((serverIPCEvent.getubRspExt() & 0x1) > 0) {
                            processProcStats = 10;
                        }
                        s = super.serverIPC.toString();
                        processLifespan = processProcStats;
                        i = 0;
                    }
                    else if (serverIPCEvent instanceof ubAdminMsg && ((ubAdminMsg)serverIPCEvent).getadRq() == 9) {
                        processProcStats = this.processProcStats(requestQueue, serverIPCEvent, processProcStats);
                        s = this.getFullName();
                    }
                    else {
                        s = this.getFullName();
                        b = false;
                        i = 0;
                    }
                }
                this.tsEndRequest();
            }
            catch (ServerIPCException ex) {
                super.log.logError(7665689515738013623L, new Object[] { "processConnect()", ex.getMessage() });
                if (super.serverIPC != null) {
                    s = super.serverIPC.toString();
                }
                else {
                    s = new String("");
                }
                b = false;
            }
            if (!b) {
                this.logServerPipeMsgs();
                super.stats.incrnErrors();
                final ubAdminMsg ubAdminMsg = new ubAdminMsg((byte)4);
                if (super.log.ifLogBasic(512L, 9)) {
                    super.trace.addMsg(ubAdminMsg, this.getFullName(), s, requestQueue.getListName());
                }
                this.sendRsp(requestQueue, ubAdminMsg);
                this.releaseRspQueue();
                return 11;
            }
        }
        else {
            serverIPCEvent = new ubAppServerMsg((short)ubMsg.getubVer(), 11, ++super.seqnum, 0, 0);
            serverIPCEvent.setubRsp(0);
            serverIPCEvent.setubRspExt(super.serverPort << 16);
            final byte[] csmssgRspbuf = ubAppServerMsg.csmssgRspbuf(0, 0, null);
            serverIPCEvent.setMsgbuf(csmssgRspbuf, csmssgRspbuf.length);
            final byte[] array = new byte[9];
            array[0] = 1;
            ubMsg.setNetInt(array, 1, 0);
            ubMsg.setNetInt(array, 5, 0);
            serverIPCEvent.appendMsgbuf(array, array.length);
            ((ubAppServerMsg)serverIPCEvent).setMsglen(serverIPCEvent.getBuflen());
            s = this.getFullName();
            processLifespan = processProcStats;
        }
        super.stats.incrnRqs();
        super.stats.incrnRqMsgs();
        super.stats.incrnRsps();
        super.stats.incrnRspMsgs();
        serverIPCEvent.setubSrc(4);
        serverIPCEvent.setubRq(14);
        if (super.log.ifLogBasic(512L, 9)) {
            super.trace.addMsg(serverIPCEvent, this.getFullName(), s, requestQueue.getListName());
        }
        this.sendRsp(requestQueue, serverIPCEvent);
        if (processLifespan != 10) {
            this.releaseRspQueue();
            if (super.properties.serverMode == 0) {
                processLifespan = this.processLifespan(requestQueue, ubMsg, processProcStats);
            }
        }
        return processLifespan;
    }
    
    byte processConnect(final RequestQueue requestQueue, final ubMsg ubMsg, byte processProcStats) {
        byte processLifespan = processProcStats;
        ubMsg serverIPCEvent = null;
        String str = this.getFullName();
        if (super.properties.serverMode == 0) {
            boolean b = true;
            try {
                if (super.serverIPC == null) {
                    super.serverIPC = this.initServerIPC(super.serverPort);
                }
                if (super.log.ifLogBasic(512L, 9)) {
                    super.trace.addMsg(ubMsg, this.getFullName(), this.getFullName(), super.serverIPC.toString());
                }
                this.tsStartRequest();
                super.serverIPC.write(ubMsg, true);
                int i = 1;
                while (i != 0) {
                    serverIPCEvent = this.getServerIPCEvent();
                    if (serverIPCEvent instanceof ubAppServerMsg) {
                        switch (((ubAppServerMsg)serverIPCEvent).getMsgcode()) {
                            case 82: {
                                processProcStats = this.processProcStats(requestQueue, serverIPCEvent, processProcStats);
                                this.getFullName();
                                break;
                            }
                            case 11: {
                                if ((serverIPCEvent.getubRspExt() & 0x1) > 0) {
                                    processProcStats = 10;
                                }
                                i = 0;
                                break;
                            }
                            default: {
                                serverIPCEvent.print("Unexpected msg from " + str + " : ", 1, 0, super.log);
                                b = false;
                                i = 0;
                                break;
                            }
                        }
                        str = super.serverIPC.toString();
                        processLifespan = processProcStats;
                    }
                    else {
                        str = this.getFullName();
                        b = false;
                        i = 0;
                    }
                }
                this.tsEndRequest();
            }
            catch (ServerIPCException ex) {
                super.log.logError(7665689515738013623L, new Object[] { "processConnect()", ex.getMessage() });
                if (super.serverIPC != null) {
                    str = super.serverIPC.toString();
                }
                else {
                    str = new String("");
                }
                b = false;
            }
            if (!b) {
                this.logServerPipeMsgs();
                super.stats.incrnErrors();
                final ubAdminMsg ubAdminMsg = new ubAdminMsg((byte)4);
                if (super.log.ifLogBasic(512L, 9)) {
                    super.trace.addMsg(ubAdminMsg, this.getFullName(), str, requestQueue.getListName());
                }
                this.sendRsp(requestQueue, ubAdminMsg);
                this.releaseRspQueue();
                return 11;
            }
        }
        else {
            serverIPCEvent = new ubAppServerMsg((short)ubMsg.getubVer(), 11, ++super.seqnum, 0, 0);
            serverIPCEvent.setubRsp(0);
            serverIPCEvent.setubRspExt(super.serverPort << 16);
            final byte[] csmssgRspbuf = ubAppServerMsg.csmssgRspbuf(0, 0, null);
            serverIPCEvent.setMsgbuf(csmssgRspbuf, csmssgRspbuf.length);
            final byte[] array = new byte[9];
            array[0] = 1;
            ubMsg.setNetInt(array, 1, 0);
            ubMsg.setNetInt(array, 5, 0);
            serverIPCEvent.appendMsgbuf(array, array.length);
            ((ubAppServerMsg)serverIPCEvent).setMsglen(serverIPCEvent.getBuflen());
            str = this.getFullName();
            processLifespan = processProcStats;
        }
        super.stats.incrnRqs();
        super.stats.incrnRqMsgs();
        super.stats.incrnRsps();
        super.stats.incrnRspMsgs();
        serverIPCEvent.setubSrc(4);
        serverIPCEvent.setubRq(14);
        if (super.log.ifLogBasic(512L, 9)) {
            super.trace.addMsg(serverIPCEvent, this.getFullName(), str, requestQueue.getListName());
        }
        this.sendRsp(requestQueue, serverIPCEvent);
        if (processLifespan != 10) {
            this.releaseRspQueue();
            if (super.properties.serverMode == 0) {
                processLifespan = this.processLifespan(requestQueue, ubMsg, processProcStats);
            }
        }
        return processLifespan;
    }
    
    byte processInitRq(final RequestQueue clientRspQueue, final ubMsg ubMsg, final byte b) {
        byte processFatalError;
        try {
            if (super.serverIPC == null) {
                super.serverIPC = this.initServerIPC(super.serverPort);
            }
            if (super.log.ifLogBasic(512L, 9)) {
                super.trace.addMsg(ubMsg, this.getFullName(), this.getFullName(), super.serverIPC.toString());
            }
            super.serverIPC.write(ubMsg, false);
            super.clientRspQueue = clientRspQueue;
            processFatalError = b;
        }
        catch (ServerIPCException ex) {
            this.logServerPipeMsgs();
            super.stats.incrnErrors();
            processFatalError = this.processFatalError(clientRspQueue, 6, this.getFullName() + " " + ex.getMessage());
        }
        return processFatalError;
    }
    
    byte processWrite(final RequestQueue requestQueue, final ubMsg ubMsg, final byte b) {
        byte processFatalError;
        try {
            if (super.serverIPC == null) {
                super.serverIPC = this.initServerIPC(super.serverPort);
            }
            if (super.log.ifLogBasic(512L, 9)) {
                super.trace.addMsg(ubMsg, this.getFullName(), this.getFullName(), super.serverIPC.toString());
            }
            super.serverIPC.write(ubMsg, ubMsg.getubRq() != 4);
            processFatalError = b;
        }
        catch (ServerIPCException ex) {
            this.logServerPipeMsgs();
            super.stats.incrnErrors();
            processFatalError = this.processFatalError(requestQueue, 6, this.getFullName() + " " + ex.getMessage());
        }
        return processFatalError;
    }
    
    byte processWriteClose(final RequestQueue requestQueue, final ubMsg ubMsg, final byte b) {
        final byte processWrite = this.processWrite(requestQueue, ubMsg, b);
        this.tsStartRequest();
        return processWrite;
    }
    
    byte processRead(final RequestQueue requestQueue, final ubMsg ubMsg, final byte b) {
        this.sendRsp(requestQueue, ubMsg);
        super.stats.incrnRspMsgs();
        if (super.tsStartRq != -1L) {
            this.tsEndRequest();
        }
        return b;
    }
    
    byte processFinishRq(final RequestQueue requestQueue, final ubMsg ubMsg, byte processLifespan) {
        if ((ubMsg.getubRqExt() & 0x1) > 0) {
            processLifespan = 10;
        }
        this.sendRsp(requestQueue, ubMsg);
        if (processLifespan != 10) {
            this.releaseRspQueue();
            processLifespan = this.processLifespan(requestQueue, ubMsg, processLifespan);
        }
        return processLifespan;
    }
    
    byte processStop(final RequestQueue requestQueue, final ubMsg ubMsg, final byte b) {
        byte b2;
        if (ubMsg.getubSrc() == 3) {
            if (requestQueue == super.clientRspQueue) {
                if (super.log.ifLogBasic(2L, 1)) {
                    super.log.logBasic(1, "Sending STOP msg to server");
                }
                b2 = this.processWrite(requestQueue, ubMsg, b);
            }
            else {
                super.log.logError("Got STOP from wrong client : got a message from (" + requestQueue.getListName() + ")" + " expecting a message from (" + super.clientRspQueue.getListName() + ")");
                super.stats.incrnErrors();
                b2 = this.processIgnore(requestQueue, ubMsg, b);
            }
        }
        else {
            if (super.log.ifLogBasic(2L, 1)) {
                super.log.logBasic(1, "Sending STOP msg to client");
            }
            b2 = this.processRead(requestQueue, ubMsg, b);
        }
        return b2;
    }
    
    byte processProcStats_prev(final RequestQueue requestQueue, final ubMsg ubMsg, final byte b) {
        final ubServerPipeMsg ubServerPipeMsg = (ubServerPipeMsg)((ubAdminMsg)ubMsg).getadParm()[0];
        final String description = ubServerPipeMsg.getDescription();
        final Integer n = (Integer)ubServerPipeMsg.getArg(0);
        final Integer n2 = (Integer)ubServerPipeMsg.getArg(1);
        final Integer n3 = (Integer)ubServerPipeMsg.getArg(2);
        super.stats.incrConnRqs(n);
        if (super.log.ifLogVerbose(1L, 0)) {
            super.log.logVerbose(0, "got PROCSTATS msg= " + description);
        }
        if (super.properties.getValueAsInt("collectStatsData") > 0) {
            this.parse(description);
        }
        return b;
    }
    
    byte processProcStats(final RequestQueue requestQueue, final ubMsg ubMsg, final byte b) {
        final String[] agentStatusChgMsg = this.parseAgentStatusChgMsg((ubAppServerMsg)ubMsg);
        final int cvtNumericParam = this.cvtNumericParam(agentStatusChgMsg[0], 0);
        this.cvtNumericParam(agentStatusChgMsg[1], 0);
        this.cvtNumericParam(agentStatusChgMsg[2], 0);
        final String str = agentStatusChgMsg[3];
        super.stats.incrConnRqs(cvtNumericParam);
        if (super.log.ifLogVerbose(1L, 0)) {
            super.log.logVerbose(0, "got PROCSTATS msg: rqCnt= " + agentStatusChgMsg[0] + " pktsSentCnt= " + agentStatusChgMsg[1] + " pktsRcvdCnt= " + agentStatusChgMsg[2] + "\n" + "    procStatsData= " + str);
        }
        if (super.properties.getValueAsInt("collectStatsData") > 0 && str.length() > 0) {
            this.parse(str);
        }
        return b;
    }
    
    private void parse(final String str) {
        final StringTokenizer stringTokenizer = new StringTokenizer(str, "\n");
        final Hashtable asProcHash = UBrokerVST.getASProcHash();
        while (stringTokenizer.hasMoreTokens()) {
            final StringTokenizer stringTokenizer2 = new StringTokenizer(stringTokenizer.nextToken(), "|", false);
            while (stringTokenizer2.hasMoreTokens()) {
                final String nextToken = stringTokenizer2.nextToken();
                final String nextToken2 = stringTokenizer2.nextToken();
                final String nextToken3 = stringTokenizer2.nextToken();
                final int int1 = Integer.parseInt(stringTokenizer2.nextToken());
                final int int2 = Integer.parseInt(stringTokenizer2.nextToken());
                final int int3 = Integer.parseInt(stringTokenizer2.nextToken());
                final int int4 = Integer.parseInt(stringTokenizer2.nextToken());
                final int int5 = Integer.parseInt(stringTokenizer2.nextToken());
                final long long1 = Long.parseLong(stringTokenizer2.nextToken());
                final int int6 = Integer.parseInt(stringTokenizer2.nextToken().trim());
                final Integer n = new Integer((nextToken2 + nextToken).toLowerCase().hashCode());
                synchronized (asProcHash) {
                    ubASProcStatsObj value;
                    if (asProcHash.containsKey(n)) {
                        value = asProcHash.get(n);
                        value.incrNumSuccess(int1);
                        value.incrNumError(int2);
                        value.incrNumStop(int3);
                        value.incrNumQuit(int4);
                        value.incrNumDebug(int5);
                        value.incrTime(long1);
                        value.incrNumCalls(int6);
                        asProcHash.remove(n);
                    }
                    else {
                        value = new ubASProcStatsObj(nextToken, nextToken2, nextToken3, int1, int2, int3, int4, int5, long1, int6);
                    }
                    asProcHash.put(n, value);
                }
                super.serverPool.incrRequestsCompleted(int6);
            }
        }
    }
    
    byte processClientConnected_prev(final RequestQueue requestQueue, final ubMsg ubMsg, final byte b) {
        InetAddress byName = null;
        final Object[] getadParm = ((ubAdminMsg)ubMsg).getadParm();
        try {
            byName = InetAddress.getByName((String)getadParm[1]);
        }
        catch (UnknownHostException obj) {
            super.log.logError("Invalid host addresses received from server= " + (String)getadParm[1] + " : " + obj);
        }
        if (byName != null) {
            synchronized (super.stats) {
                super.stats.setConnHdl();
                super.stats.setConnRmtHost(byName);
                super.stats.setConnRmtPort((int)getadParm[2]);
                super.stats.setConnUserName((String)getadParm[3]);
                super.stats.setConnID((String)getadParm[4]);
                super.stats.setConnRqs(0);
            }
        }
        return b;
    }
    
    byte processClientConnected(final RequestQueue requestQueue, final ubMsg ubMsg, final byte b) {
        InetAddress byName = null;
        final String[] agentStatusChgMsg = this.parseAgentStatusChgMsg((ubAppServerMsg)ubMsg);
        try {
            byName = InetAddress.getByName(agentStatusChgMsg[1]);
        }
        catch (UnknownHostException obj) {
            super.log.logError("Invalid host address received from server= " + agentStatusChgMsg[1] + " : " + obj);
        }
        if (byName != null) {
            synchronized (super.stats) {
                super.stats.setConnHdl();
                super.stats.setConnRmtHost(byName);
                try {
                    super.stats.setConnRmtPort(Integer.parseInt(agentStatusChgMsg[2]));
                }
                catch (NumberFormatException ex) {
                    super.stats.setConnRmtPort(0);
                }
                super.stats.setConnUserName(agentStatusChgMsg[3]);
                super.stats.setConnID(agentStatusChgMsg[4]);
                super.stats.setConnRqs(0);
            }
        }
        return b;
    }
    
    byte processConnectTimeout(final RequestQueue requestQueue, final ubMsg ubMsg, final byte b) {
        if (super.log.ifLogBasic(1L, 0)) {
            super.log.logBasic(0, "connectingTimeout (" + super.properties.getValueAsInt("connectingTimeout") + " s) exceeded while connecting to" + " agent " + super.serverPid + ".");
        }
        return b;
    }
    
    ubMsg clientRsp(final int n, final String s) {
        final ubAppServerMsg ubAppServerMsg = new ubAppServerMsg((short)108, 70, ++super.seqnum, 0, 0);
        if (s != null) {
            final byte[] csmssgRspbuf = com.progress.ubroker.util.ubAppServerMsg.csmssgRspbuf(0, 0, s);
            ubAppServerMsg.setMsgbuf(csmssgRspbuf, csmssgRspbuf.length);
        }
        ubAppServerMsg.setubRsp(n);
        return ubAppServerMsg;
    }
    
    byte shutdownServerProcess_prev(final RequestQueue requestQueue, final ubAdminMsg ubAdminMsg, final byte b) {
        int n = 0;
        final byte[] csmssgRspbuf = ubAppServerMsg.csmssgRspbuf(0, 0, null);
        final ubAppServerMsg ubAppServerMsg = new ubAppServerMsg((short)108, 30, ++super.seqnum, csmssgRspbuf.length, csmssgRspbuf.length);
        ubAppServerMsg.setCsmssgVer((short)1);
        ubAppServerMsg.setubSrc(1);
        ubAppServerMsg.setubRq(8);
        ubAppServerMsg.setMsgbuf(csmssgRspbuf, csmssgRspbuf.length);
        try {
            if (super.serverIPC == null) {
                super.serverIPC = this.initServerIPC(super.serverPort);
            }
            if (super.log.ifLogBasic(512L, 9)) {
                super.trace.addMsg(ubAppServerMsg, this.getFullName(), this.getFullName(), super.serverIPC.toString());
            }
            super.serverIPC.write(ubAppServerMsg, true);
            this.getServerIPCEvent();
            super.serverIPC.delete();
            super.serverIPC = null;
        }
        catch (ServerIPCException ex) {
            super.stats.incrnErrors();
            n = 3;
            ubAdminMsg.setMsgbuf("ServerIPCException = " + ex.getMessage());
        }
        ubAdminMsg.setadRsp(n);
        this.sendRsp(requestQueue, ubAdminMsg);
        return b;
    }
    
    byte shutdownServerProcess(final RequestQueue requestQueue, final ubAdminMsg ubAdminMsg, byte processProcStats) {
        byte b = processProcStats;
        int n = 0;
        this.getFullName();
        final byte[] csmssgRspbuf = ubAppServerMsg.csmssgRspbuf(0, 0, null);
        final ubAppServerMsg ubAppServerMsg = new ubAppServerMsg((short)108, 30, ++super.seqnum, csmssgRspbuf.length, csmssgRspbuf.length);
        ubAppServerMsg.setCsmssgVer((short)1);
        ubAppServerMsg.setubSrc(1);
        ubAppServerMsg.setubRq(8);
        ubAppServerMsg.setMsgbuf(csmssgRspbuf, csmssgRspbuf.length);
        try {
            if (super.serverIPC == null) {
                super.serverIPC = this.initServerIPC(super.serverPort);
            }
            if (super.log.ifLogBasic(512L, 9)) {
                super.trace.addMsg(ubAppServerMsg, this.getFullName(), this.getFullName(), super.serverIPC.toString());
            }
            super.serverIPC.write(ubAppServerMsg, true);
            int i = 1;
            while (i != 0) {
                final ubMsg serverIPCEvent = this.getServerIPCEvent();
                String str;
                if (serverIPCEvent instanceof ubAppServerMsg) {
                    str = super.serverIPC.toString();
                    switch (((ubAppServerMsg)serverIPCEvent).getMsgcode()) {
                        case 82: {
                            processProcStats = this.processProcStats(requestQueue, serverIPCEvent, processProcStats);
                            break;
                        }
                        case 31: {
                            i = 0;
                            break;
                        }
                        default: {
                            serverIPCEvent.print("Unexpected msg from " + str + " : ", 1, 0, super.log);
                            i = 0;
                            break;
                        }
                    }
                    b = processProcStats;
                }
                else {
                    str = this.getFullName();
                    i = 0;
                }
                if (super.log.ifLogBasic(512L, 9)) {
                    super.trace.addMsg(serverIPCEvent, this.getFullName(), str, this.getFullName());
                }
            }
            super.serverIPC.delete();
            super.serverIPC = null;
        }
        catch (ServerIPCException ex) {
            super.stats.incrnErrors();
            n = 3;
            ubAdminMsg.setMsgbuf("ServerIPCException = " + ex.getMessage());
        }
        ubAdminMsg.setadRsp(n);
        this.sendRsp(requestQueue, ubAdminMsg);
        return b;
    }
    
    byte sendAdminMessage(final RequestQueue requestQueue, final ubAdminMsg ubAdminMsg, final byte b) {
        this.getFullName();
        final short n = ubAdminMsg.getadRq();
        switch (n) {
            case 12: {
                final byte[] array = new byte[2];
                ubMsg.setNetShort(array, 0, n);
                final ubAppServerMsg ubAppServerMsg = new ubAppServerMsg((short)108, 90, ++super.seqnum, array.length, array.length);
                ubAppServerMsg.setCsmssgVer((short)1);
                ubAppServerMsg.setubSrc(1);
                ubAppServerMsg.setubRq(1);
                ubAppServerMsg.setMsgbuf(array, array.length);
                try {
                    if (super.serverIPC == null) {
                        super.serverIPC = this.initServerIPC(super.serverPort);
                    }
                    if (super.log.ifLogBasic(512L, 9)) {
                        super.trace.addMsg(ubAppServerMsg, this.getFullName(), this.getFullName(), super.serverIPC.toString());
                    }
                    super.serverIPC.write(ubAppServerMsg, true);
                }
                catch (ServerIPCException ex) {
                    super.stats.incrnErrors();
                    ubAdminMsg.setadRsp(1);
                    ubAdminMsg.setMsgbuf("ServerIPCException = " + ex.getMessage());
                    this.sendRsp(requestQueue, ubAdminMsg);
                }
                return b;
            }
            default: {
                ubAdminMsg.setadRsp(1);
                this.sendRsp(requestQueue, ubAdminMsg);
                return b;
            }
        }
    }
    
    byte readAdminResponse(final RequestQueue requestQueue, final ubMsg ubMsg, final byte b) {
        final int n = 0;
        final ubAdminMsg ubAdminMsg = new ubAdminMsg((byte)12);
        ubAdminMsg.setadSrc((byte)5);
        ubAdminMsg.setadRsp(n);
        this.sendRsp(requestQueue, ubAdminMsg);
        return b;
    }
    
    void processServerLogMsg(final ubMsg ubMsg) {
        super.processServerLogMsg(ubMsg);
    }
}
