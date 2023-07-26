// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.broker;

import java.io.FileNotFoundException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.net.Socket;
import java.io.InterruptedIOException;
import java.io.IOException;
import java.util.Hashtable;
import com.progress.ubroker.management.ubWSProcStatsObj;
import com.progress.ubroker.management.UBrokerVST;
import java.util.StringTokenizer;
import com.progress.ubroker.util.ubWebSpeedMsg;
import com.progress.ubroker.util.ubMsg;
import com.progress.ubroker.util.ubAdminMsg;
import com.progress.ubroker.util.RequestQueue;
import com.progress.common.ehnlog.IAppLogger;
import com.progress.ubroker.util.ubProperties;
import java.net.ServerSocket;
import com.progress.ubroker.util.ubConstants;

public class ubWSserverThread extends ubServerThread implements ubConstants, IServerFSM
{
    static int SO_LISTEN_TIMEOUT;
    ServerSocket listenerSocket;
    
    public ubWSserverThread(final int n, final int n2, final ubProperties ubProperties, final ubThreadPool ubThreadPool, final IAppLogger appLogger, final IAppLogger appLogger2) {
        super(n, n2, ubProperties, ubThreadPool, appLogger, appLogger2);
        this.listenerSocket = null;
        this.listenerSocket = null;
    }
    
    public synchronized String getServerCookie() {
        return new String(super.serverCookie);
    }
    
    public synchronized void setServerCookie(final String serverCookie) {
        super.serverCookie = serverCookie;
    }
    
    ubServerFSM setServerFSM(final int n, final int n2, final IAppLogger appLogger) {
        return new ubWSstateAwareFSM(appLogger);
    }
    
    String bldCmdLine(final String str) {
        final StringBuffer sb = new StringBuffer(100);
        final int i = this.getThreadId() & 0xFFFF;
        final String property = System.getProperty("os.name");
        final String valueAsString = super.properties.getValueAsString("srvrExecFile");
        int beginIndex;
        if (property.startsWith("Windows")) {
            final int index = valueAsString.indexOf("-p newConsole");
            if (index == -1) {
                beginIndex = 0;
            }
            else {
                beginIndex = valueAsString.indexOf(" ", index + 5) + 1;
            }
        }
        else {
            final int index2 = valueAsString.indexOf("jvmStart");
            if (index2 == -1) {
                beginIndex = 0;
            }
            else {
                beginIndex = valueAsString.indexOf(" ", index2) + 1;
            }
        }
        sb.append(valueAsString.substring(beginIndex));
        sb.append(" -web");
        sb.append(" -logginglevel " + super.properties.getValueAsInt("srvrLoggingLevel"));
        sb.append(" -logfile " + super.properties.dblquote + super.properties.getValueAsString("srvrLogFile") + super.properties.dblquote);
        sb.append(" -ubpid " + super.properties.brokerPid);
        sb.append(" -wtbhostname " + super.properties.localHost);
        sb.append(" -wtbport " + this.listenerSocket.getLocalPort());
        sb.append(" -wtaminport " + super.properties.getValueAsInt("srvrMinPort"));
        sb.append(" -wtamaxport " + super.properties.getValueAsInt("srvrMaxPort"));
        sb.append(" -wtbname " + super.properties.brokerName);
        sb.append(" -wtainstance " + i);
        sb.append(" -ubpropfile " + super.properties.dblquote + super.properties.propertiesfilename + super.properties.dblquote);
        sb.append(" -logname " + super.properties.brokerName);
        sb.append(" -logthreshold " + super.properties.getValueAsInt("srvrLogThreshold"));
        sb.append(" -numlogfiles " + super.properties.getValueAsInt("srvrNumLogFiles"));
        final String valueAsString2 = super.properties.getValueAsString("srvrLogEntryTypes");
        if (valueAsString2 != null && valueAsString2.length() > 0) {
            sb.append(" -logentrytypes " + valueAsString2);
        }
        if (super.properties.getValueAsInt("mqEnabled") > 0) {
            sb.append(" -SMQPort " + super.properties.getValueAsInt("mqPort"));
            sb.append(" -SMQPID " + super.properties.getValueAsInt("mqPid"));
        }
        sb.append(" -ipver " + super.properties.ipverString());
        if (super.properties.getValueAsBoolean("actionalEnabled")) {
            sb.append(" -actionalEnabled -actionalGroup " + super.properties.getValueAsString("actionalGroup") + ":" + super.properties.brokerName);
        }
        sb.append(" " + super.properties.getValueAsString("srvrStartupParam"));
        if (str != null) {
            sb.append(" " + str);
        }
        return sb.toString();
    }
    
    byte processStartup(final RequestQueue requestQueue, final ubAdminMsg ubAdminMsg, byte b) {
        if (!this.initServerStream()) {
            b = 11;
        }
        byte b2 = super.processStartup(requestQueue, ubAdminMsg, b);
        if (b2 != 11) {
            b2 = this.sendWSReconnectMsg(ubAdminMsg, b2);
        }
        this.sendRsp(requestQueue, ubAdminMsg);
        return b2;
    }
    
    byte processDisconnect(final RequestQueue requestQueue, final ubMsg ubMsg, final byte b) {
        return super.processDisconnect(super.rspQueue, ubMsg, b);
    }
    
    byte processShutdown(final RequestQueue requestQueue, final ubMsg ubMsg, final byte b) {
        return super.processShutdown(requestQueue, ubMsg, b);
    }
    
    byte processTerminate() {
        return super.processTerminate();
    }
    
    byte processConnect(final RequestQueue requestQueue, final ubMsg ubMsg, final byte b) {
        ubMsg.setubRsp(0);
        ubMsg.setubRspExt(super.serverPort << 16 | 0x4);
        final String s = new String(new Integer(super.serverPort).toString());
        final byte[] array = new byte[s.length() + 1];
        ubWebSpeedMsg.setNetString(array, 0, s);
        ubMsg.setMsgbuf(array, array.length);
        ((ubWebSpeedMsg)ubMsg).setwsHeader(32, 4, array.length);
        super.stats.incrnRqs();
        super.stats.incrnRqMsgs();
        super.stats.incrnRsps();
        super.stats.incrnRspMsgs();
        ubMsg.setubSrc(4);
        ubMsg.setubRq(14);
        this.sendRsp(requestQueue, ubMsg);
        this.releaseRspQueue();
        return b;
    }
    
    byte processServerStateChg_PREV(final ubMsg ubMsg, byte processLifespan) {
        final int offset = 21;
        byte[] msgbuf;
        int buflen;
        int n;
        for (msgbuf = ubMsg.getMsgbuf(), buflen = ubMsg.getBuflen(), n = offset; n < buflen && msgbuf[n] != 43; ++n) {}
        this.setServerCookie(new String(msgbuf, offset, n - offset));
        if (processLifespan == 1) {
            processLifespan = this.processLifespan(null, ubMsg, processLifespan);
            if (super.properties.getValueAsInt("collectStatsData") > 0) {
                this.parse(new String(msgbuf));
            }
        }
        return processLifespan;
    }
    
    byte processServerStateChg(final ubMsg ubMsg, byte processLifespan) {
        final byte[] msgbuf = ubMsg.getMsgbuf();
        int buflen;
        int n;
        int n2;
        for (buflen = ubMsg.getBuflen(), n = 0, n2 = 0; n < buflen && n2 < 3; ++n) {
            if (msgbuf[n] == 43) {
                ++n2;
            }
        }
        if (n2 == 3) {
            int n3;
            int offset;
            for (offset = (n3 = n); n3 < buflen && msgbuf[n3] != 43; ++n3) {}
            this.setServerCookie(new String(msgbuf, offset, n3 - offset));
        }
        else {
            super.log.logDump(1, 0, "serverStateChange msg from server process " + super.serverPid + " is improperly formatted.", msgbuf, buflen);
        }
        if (processLifespan == 1) {
            processLifespan = this.processLifespan(null, ubMsg, processLifespan);
            if (super.properties.getValueAsInt("collectStatsData") > 0) {
                this.parse(new String(msgbuf));
            }
        }
        return processLifespan;
    }
    
    private void parse(String substring) {
        if (new StringTokenizer(substring, "+").countTokens() != 6) {
            return;
        }
        final String substring2 = substring.substring(substring.lastIndexOf("+") + 1, substring.length());
        substring = substring.substring(0, substring.lastIndexOf(substring2) - 1);
        final long long1 = Long.parseLong(substring.substring(substring.lastIndexOf("+") + 1, substring.length()));
        final Hashtable wsProcHash = UBrokerVST.getWSProcHash();
        final String lowerCase = substring2.toLowerCase();
        super.serverPool.incrRequestsCompleted(1);
        synchronized (wsProcHash) {
            ubWSProcStatsObj value;
            if (wsProcHash.containsKey(lowerCase)) {
                value = wsProcHash.get(lowerCase);
                value.incrTime(long1);
                value.incrCalls();
                wsProcHash.remove(lowerCase);
            }
            else {
                value = new ubWSProcStatsObj(substring2, long1);
                value.incrCalls();
            }
            wsProcHash.put(substring2.toLowerCase(), value);
        }
    }
    
    byte sendWSReconnectMsg(final ubMsg ubMsg, final byte b) {
        final ubWebSpeedMsg ubWebSpeedMsg = new ubWebSpeedMsg((short)109);
        final String s = "RECONNECT";
        final byte[] array = new byte[s.length() + 1];
        com.progress.ubroker.util.ubWebSpeedMsg.setNetString(array, 0, s);
        ubWebSpeedMsg.setMsgbuf(array, array.length);
        ubWebSpeedMsg.setwsHeader(32, 16, array.length);
        byte b2;
        try {
            if (super.serverIPC == null) {
                super.serverIPC = this.initServerIPC(super.serverPort);
            }
            super.serverIPC.write(ubWebSpeedMsg, true);
            b2 = b;
        }
        catch (ServerIPCException ex) {
            this.logServerPipeMsgs();
            super.stats.incrnErrors();
            ((ubAdminMsg)ubMsg).setadRsp(2);
            ubMsg.setMsgbuf("ServerIPCException = " + ex.getMessage());
            b2 = 11;
        }
        return b2;
    }
    
    byte processConnectTimeout(final RequestQueue requestQueue, final ubMsg ubMsg, final byte b) {
        if (super.log.ifLogBasic(1L, 0)) {
            super.log.logBasic(0, "Timeout while waiting for connection to Server Process " + super.serverPid + ".");
        }
        return b;
    }
    
    ubMsg clientRsp(final int n, String s) {
        if (s == null) {
            s = "";
        }
        final byte[] array = new byte[s.length() + 1];
        final ubWebSpeedMsg ubWebSpeedMsg = new ubWebSpeedMsg((short)108);
        ubWebSpeedMsg.setubSrc(1);
        ubWebSpeedMsg.setwsWho(32);
        com.progress.ubroker.util.ubWebSpeedMsg.setNetString(array, 0, s);
        ubWebSpeedMsg.setMsgbuf(array, array.length);
        ubWebSpeedMsg.setubRsp(n);
        return ubWebSpeedMsg;
    }
    
    byte shutdownServerProcess(final RequestQueue requestQueue, final ubAdminMsg ubAdminMsg, final byte b) {
        int n = 0;
        final ubWebSpeedMsg ubWebSpeedMsg = new ubWebSpeedMsg((short)109);
        final String s = "SHUTDOWN";
        final byte[] array = new byte[s.length() + 1];
        com.progress.ubroker.util.ubWebSpeedMsg.setNetString(array, 0, s);
        ubWebSpeedMsg.setMsgbuf(array, array.length);
        ubWebSpeedMsg.setwsHeader(32, 9, array.length);
        try {
            if (super.serverIPC == null) {
                super.serverIPC = this.initServerIPC(super.serverPort);
            }
            super.serverIPC.write(ubWebSpeedMsg, true);
        }
        catch (ServerIPCException ex) {
            this.logServerPipeMsgs();
            super.stats.incrnErrors();
            n = 3;
            ubAdminMsg.setMsgbuf("ServerIPCException = " + ex.getMessage());
        }
        ubAdminMsg.setadRsp(n);
        this.sendRsp(requestQueue, ubAdminMsg);
        return b;
    }
    
    byte sendAdminMessage(final RequestQueue requestQueue, final ubAdminMsg ubAdminMsg, final byte b) {
        int n = 0;
        final ubWebSpeedMsg ubWebSpeedMsg = new ubWebSpeedMsg((short)109);
        switch (ubAdminMsg.getadRq()) {
            case 12: {
                final String s = "PROPUPDATE";
                final byte[] array = new byte[s.length() + 1];
                com.progress.ubroker.util.ubWebSpeedMsg.setNetString(array, 0, s);
                ubWebSpeedMsg.setMsgbuf(array, array.length);
                ubWebSpeedMsg.setwsHeader(32, 18, array.length);
                try {
                    if (super.serverIPC == null) {
                        super.serverIPC = this.initServerIPC(super.serverPort);
                    }
                    super.serverIPC.write(ubWebSpeedMsg, true);
                }
                catch (ServerIPCException ex) {
                    this.logServerPipeMsgs();
                    super.stats.incrnErrors();
                    n = 1;
                    ubAdminMsg.setMsgbuf("ServerIPCException = " + ex.getMessage());
                }
                ubAdminMsg.setadRsp(n);
                this.sendRsp(requestQueue, ubAdminMsg);
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
        return b;
    }
    
    boolean initServerStream() {
        try {
            (this.listenerSocket = new ServerSocket(0)).setSoTimeout(ubWSserverThread.SO_LISTEN_TIMEOUT);
            if (super.log.ifLogVerbose(1L, 0)) {
                super.log.logVerbose(0, "private listening port= " + this.listenerSocket.getLocalPort());
            }
        }
        catch (IOException obj) {
            super.log.logError("Error creating private listening socket: " + obj);
            this.listenerSocket = null;
        }
        return this.listenerSocket != null;
    }
    
    boolean getServerStream() {
        Socket accept = null;
        boolean serverStream = super.getServerStream();
        if (this.listenerSocket != null) {
            try {
                accept = this.listenerSocket.accept();
                if (super.log.ifLogBasic(2L, 1)) {
                    super.log.logBasic(1, "private listening socket connected= " + accept.getLocalPort());
                }
            }
            catch (InterruptedIOException obj) {
                super.log.logError("Timeout while listening for server : " + obj);
                accept = null;
            }
            catch (IOException obj2) {
                super.log.logError("IOException while listening for agent : " + obj2);
                accept = null;
            }
            try {
                this.listenerSocket.close();
            }
            catch (IOException obj3) {
                super.log.logError("Error closing listenerSocket : " + obj3);
            }
            finally {
                this.listenerSocket = null;
            }
        }
        Label_0284: {
            if (accept != null) {
                try {
                    (super.serverIPC = new ubServerSocketsIPC(accept, super.properties, super.log, 2)).create(0, super.properties.getValueAsInt("brkrSpinInterval"));
                    break Label_0284;
                }
                catch (ServerIPCException obj4) {
                    super.log.logError("Error creating serverIPC : " + obj4);
                    return false;
                }
            }
            super.logServerPipeMsgs();
        }
        if (serverStream) {
            serverStream = (accept != null);
        }
        return serverStream;
    }
    
    boolean getServerStream_old() {
        final String string = super.properties.brokerName + "-" + (this.getThreadId() & 0xFFFF) + ".tmp";
        final boolean ifLogBasic = super.log.ifLogBasic(2L, 1);
        final File file = new File(string);
        if (file != null) {
            if (ifLogBasic) {
                super.log.logBasic(1, " removing " + string + " ...");
            }
            if (file.delete()) {
                if (ifLogBasic) {
                    super.log.logBasic(1, string + " REMOVED.");
                }
                else if (ifLogBasic) {
                    super.log.logBasic(1, string + " NOT REMOVED.");
                }
            }
        }
        if (ifLogBasic) {
            super.log.logBasic(1, "Looking for " + string + " ...");
        }
        int i;
        for (i = 0; i < 20; ++i) {
            try {
                super.fromServer = new BufferedReader(new FileReader(string));
                if (super.fromServer != null) {
                    break;
                }
                if (ifLogBasic) {
                    super.log.logBasic(1, " fromServer=(" + super.fromServer + ")");
                }
            }
            catch (FileNotFoundException ex) {
                final int j = 3;
                if (ifLogBasic) {
                    super.log.logBasic(1, " sleeping " + j + " ... ");
                }
                this.sleepHere(j);
            }
        }
        if (i == 20 && super.fromServer == null) {
            if (ifLogBasic) {
                super.log.logBasic(1, " can't open " + string + " ... NOW WHAT??? ...");
            }
            super.fromServer = null;
            return false;
        }
        if (ifLogBasic) {
            super.log.logBasic(1, " opened " + string);
        }
        return true;
    }
    
    boolean getServerPortnum_sav() {
        super.serverPort = 0;
        super.serverPid = 0;
        boolean b = true;
        try {
            ubWebSpeedMsg ubWebSpeedMsg;
        Label_0110:
            while (b && super.serverIPC != null && (ubWebSpeedMsg = (ubWebSpeedMsg)super.serverIPC.read()) != null) {
                ubWebSpeedMsg.getBuflen();
                ubWebSpeedMsg.getMsgbuf();
                switch (ubWebSpeedMsg.getwsMsgtype()) {
                    case 14: {
                        b = this.extractServerInfo(ubWebSpeedMsg);
                        this.processServerLogMsg(ubWebSpeedMsg);
                        continue;
                    }
                    case 3: {
                        b = this.extractServerInfo(ubWebSpeedMsg);
                        break Label_0110;
                    }
                    default: {
                        continue;
                    }
                }
            }
        }
        catch (ServerIPCException ex) {
            b = false;
        }
        return b;
    }
    
    boolean getServerPortnum() {
        if (super.log.ifLogBasic(2L, 1)) {
            super.log.logBasic(1, this.getFullName() + " : reading serverIPC for server port and pid ... ");
        }
        super.serverPort = 0;
        super.serverPid = 0;
        boolean serverInfo = true;
    Label_0228:
        while (serverInfo && super.serverIPC != null) {
            final ubMsg serverIPCEvent = this.getServerIPCEvent(false, true, 0);
            if (super.log.ifLogBasic(2L, 1)) {
                serverIPCEvent.print("From " + this.getFullName() + " : ", 2, 1, super.log);
            }
            serverIPCEvent.getBuflen();
            serverIPCEvent.getMsgbuf();
            if (serverIPCEvent instanceof ubWebSpeedMsg) {
                switch (((ubWebSpeedMsg)serverIPCEvent).getwsMsgtype()) {
                    case 14: {
                        continue;
                    }
                    case 3: {
                        serverInfo = this.extractServerInfo(serverIPCEvent);
                        break Label_0228;
                    }
                    default: {
                        serverIPCEvent.print("Unexpected msg from server : ", 1, 0, super.log);
                        continue;
                    }
                }
            }
            else {
                serverInfo = false;
                serverIPCEvent.print("Unexpected msg from server : ", 1, 0, super.log);
            }
        }
        if (super.log.ifLogBasic(2L, 1)) {
            super.log.logBasic(1, "Done reading serverIPC for port/pid.");
        }
        return serverInfo;
    }
    
    boolean extractServerInfo(final ubMsg ubMsg) {
        final int buflen = ubMsg.getBuflen();
        final byte[] msgbuf = ubMsg.getMsgbuf();
        boolean b = true;
        final String s = new String(msgbuf);
        final int index = s.indexOf(43);
        if (index == -1) {
            super.log.logDump(1, 0, "ServerInfo message format incorrect", msgbuf, buflen);
            b = false;
        }
        else if (buflen < 13) {
            b = false;
            super.log.logDump(1, 0, "ServerInfo message too short", msgbuf, buflen);
        }
        else {
            int endIndex = s.indexOf(43, index + 1);
            if (endIndex == -1) {
                endIndex = s.length();
            }
            try {
                super.serverPort = Integer.parseInt(s.substring(0, index));
                if (super.log.ifLogBasic(2L, 1)) {
                    super.log.logBasic(1, "serverPort= " + super.serverPort);
                }
            }
            catch (NumberFormatException obj) {
                super.log.logError("Error extracting serverPort= " + obj);
                b = false;
            }
            try {
                super.serverPid = Integer.parseInt(s.substring(index + 1, endIndex));
                if (super.log.ifLogBasic(2L, 1)) {
                    super.log.logBasic(1, "serverPid= " + super.serverPid);
                }
            }
            catch (NumberFormatException obj2) {
                super.log.logError("Error extracting serverPid= " + obj2);
                b = false;
            }
        }
        return b;
    }
    
    void processServerLogMsg(final ubMsg ubMsg) {
        final String s = new String(ubMsg.getMsgbuf());
        if (super.log.ifLogBasic(2L, 1)) {
            super.log.logBasic(1, 7665689515738013617L, new Object[] { s });
        }
    }
    
    static {
        ubWSserverThread.SO_LISTEN_TIMEOUT = 90000;
    }
}
