// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.broker;

import com.progress.common.ehnlog.AppLogger;
import com.progress.ubroker.util.RequestQueue;
import com.progress.ubroker.util.ubAdminMsg;
import com.progress.ubroker.util.Queue;
import com.progress.ubroker.util.ubMsg;
import com.progress.ubroker.util.Request;
import com.progress.ubroker.util.ubAppServerMsg;
import com.progress.common.ehnlog.IAppLogger;
import com.progress.ubroker.util.ubProperties;
import com.progress.ubroker.util.ubConstants;

public class ubASclientThread extends ubClientThread implements ubConstants, IClientFSM
{
    byte[] clientCodepage;
    byte[] connID;
    byte[] connCntxt;
    boolean fBound;
    private int nTotalClients;
    private boolean affinityEnabled;
    private int connPID;
    
    public ubASclientThread(final int n, final ubThreadPool ubThreadPool, final ubThreadPool ubThreadPool2, final IServerThreadControl serverThreadControl, final ubProperties ubProperties, final IAppLogger appLogger) {
        super(n, ubThreadPool, ubThreadPool2, serverThreadControl, ubProperties, appLogger);
        this.clientCodepage = null;
        this.connID = null;
        this.connCntxt = null;
        this.fBound = false;
        this.nTotalClients = 0;
        this.connPID = 0;
        this.affinityEnabled = false;
        if (super.properties.serverMode == 0 && super.properties.getValueAsInt("srvrSelectionScheme") == 2) {
            this.affinityEnabled = true;
        }
    }
    
    public String getConnID() {
        return ubAppServerMsg.getNetString(this.connID, 0);
    }
    
    public int getTotalClients() {
        return this.nTotalClients;
    }
    
    ubClientFSM setClientFSM() {
        IClientFSM clientFSM = null;
        switch (super.properties.serverMode) {
            case 0: {
                clientFSM = new ubASclientFSMsl(super.log);
                break;
            }
            case 3: {
                clientFSM = new ubASclientFSMsf(super.log);
                break;
            }
            default: {
                clientFSM = new ubASclientFSM(super.log);
                break;
            }
        }
        return (ubClientFSM)clientFSM;
    }
    
    void closeClientConnection() {
        super.closeClientConnection();
    }
    
    byte processConnect(final Request request, final byte b) {
        final ubMsg ubMsg = (ubMsg)request.getMsg();
        byte b2;
        if (super.properties.serverMode == 3) {
            b2 = this.processConnectError((ubMsg.getubVer() == 108) ? 11 : 13, "Unexpected CONNECT request in state-free mode");
        }
        else {
            if (super.log.ifLogBasic(2L, 1)) {
                super.log.logBasic(1, "Received CONNECT request");
            }
            super.serverRequestQueue = null;
            this.connPID = 0;
            this.clientCodepage = ubAppServerMsg.newNetByteArray(ubAppServerMsg.getNetString(ubMsg.getMsgbuf(), 0));
            this.trimConnectionID(ubMsg);
            this.appendConnectionID(ubMsg);
            this.negotiateAskCapabilities(ubMsg);
            super.stats.setConnUserName(this.getUserName(ubMsg));
            Label_0223: {
                if (super.properties.getValueAsBoolean("actionalEnabled")) {
                    if (super.properties.serverMode != 0) {
                        if (super.properties.serverMode != 3) {
                            break Label_0223;
                        }
                    }
                    try {
                        String hostName = null;
                        if (super.socket != null && super.socket.getInetAddress() != null) {
                            hostName = super.socket.getInetAddress().getHostName();
                        }
                        if (hostName != null && ubMsg.getubVer() > 108) {
                            ubMsg.appendTlvField((short)14, hostName);
                        }
                    }
                    catch (Exception ex) {}
                }
            }
            b2 = super.processConnect(request, b);
        }
        return b2;
    }
    
    byte processInitRequest(final Request request, final byte b) {
        super.stats.incrnRqMsgs();
        this.tsStartRequest();
        final ubAppServerMsg ubAppServerMsg = (ubAppServerMsg)request.getMsg();
        if ((super.m_negotiatedAskCaps & 0x2) != 0x0 && ubAppServerMsg.getTlvField_NoThrow((short)12) != null) {
            if (super.log.ifLogExtended(32768L, 15)) {
                super.log.logExtended(15, "        Received ASKPing request from client.");
            }
            this.sendClientRsp(this.newASKPingMsg(23), "Cannot write ASKPing response.");
            if (super.log.ifLogExtended(32768L, 15)) {
                super.log.logExtended(15, "        Sent ASKPing response to client.");
            }
            ubAppServerMsg.remTlvField_NoThrow((short)12);
        }
        byte b2;
        if (super.properties.serverMode == 0 || super.properties.serverMode == 3) {
            final ubMsg initStatelessRequest = this.newInitStatelessRequest(this.clientCodepage, this.connID, this.connCntxt, this.connPID);
            if (super.properties.getValueAsBoolean("actionalEnabled")) {
                try {
                    String hostName = null;
                    if (super.socket != null && super.socket.getInetAddress() != null) {
                        hostName = super.socket.getInetAddress().getHostName();
                    }
                    if (hostName != null && ubAppServerMsg.getubVer() > 108) {
                        initStatelessRequest.appendTlvField((short)14, hostName);
                    }
                }
                catch (Exception ex) {}
            }
            final Request request2 = new Request(initStatelessRequest, super.rcvQueue);
            b2 = (this.fBound ? this.processEnqueueDirect(super.serverRequestQueue, request2, b) : this.processEnqueue(request2, b, ((ubMsg)request.getMsg()).getubRq()));
            if (b2 != 10 && b2 != 0 && b2 != 2 && b2 != 9) {
                b2 = this.processEnqueueDirect(super.serverRequestQueue, request, b2);
                if (super.log.ifLogVerbose(1L, 0)) {
                    super.log.logVerbose(0, 7665689515738014039L, new Object[] { com.progress.ubroker.util.ubAppServerMsg.getNetString(this.connID, 0), super.serverRequestQueue.getListName() });
                }
            }
        }
        else {
            b2 = this.processEnqueue(request, b);
        }
        return b2;
    }
    
    byte processDequeueLast(final Request request, final byte b) {
        final ubMsg ubMsg = (ubMsg)request.getMsg();
        super.stats.incrnRsps();
        final boolean sendClientRsp = this.sendClientRsp(ubMsg, "Cannot write response message.");
        if (super.properties.serverMode == 1 || super.properties.serverMode == 2) {
            super.serverRequestQueue = null;
            this.tsEndRequest();
            request.logStats(super.log);
        }
        return (byte)(sendClientRsp ? b : 10);
    }
    
    byte processFinishRq(final Request request, final byte b) {
        final ubMsg ubMsg = (ubMsg)request.getMsg();
        final int buflen = ubMsg.getBuflen();
        this.connCntxt = new byte[buflen];
        System.arraycopy(ubMsg.getMsgbuf(), 0, this.connCntxt, 0, buflen);
        if (!(this.fBound = ((ubMsg.getubRqExt() & 0x1) > 0))) {
            super.serverRequestQueue = null;
        }
        this.tsEndRequest();
        return b;
    }
    
    byte processDisconnect(final Request request, final byte b) {
        final ubMsg ubMsg = (ubMsg)request.getMsg();
        byte processSFDisconnectRsp;
        if (super.properties.serverMode == 3 && !this.fBound) {
            processSFDisconnectRsp = this.processSFDisconnectRsp(0, "disconnected from broker");
        }
        else {
            super.stats.incrnRqs();
            super.stats.incrConnRqs(1);
            this.tsStartRequest();
            final byte[] initRqBuf = this.newInitRqBuf(this.clientCodepage, this.connID, this.connCntxt, this.connPID);
            ubMsg.setMsgbuf(initRqBuf, initRqBuf.length);
            ((ubAppServerMsg)ubMsg).setMsglen(initRqBuf.length + 4);
            Label_0195: {
                if (super.properties.getValueAsBoolean("actionalEnabled")) {
                    if (super.properties.serverMode != 0) {
                        if (super.properties.serverMode != 3) {
                            break Label_0195;
                        }
                    }
                    try {
                        String hostName = null;
                        if (super.socket != null && super.socket.getInetAddress() != null) {
                            hostName = super.socket.getInetAddress().getHostName();
                        }
                        if (hostName != null && ubMsg.getubVer() > 108) {
                            ubMsg.appendTlvField((short)14, hostName);
                        }
                    }
                    catch (Exception ex) {}
                }
            }
            processSFDisconnectRsp = (this.fBound ? this.processEnqueueDirect(super.serverRequestQueue, request, b) : this.processEnqueue(request, b));
        }
        return processSFDisconnectRsp;
    }
    
    byte processFatalError(final int n, final String s) {
        return super.processFatalError(n, s);
    }
    
    byte processNonFatalError(final int n, final String s) {
        return 0;
    }
    
    byte processNonFatalError(final ubMsg ubMsg, final String s) {
        return super.processNonFatalError(ubMsg, s);
    }
    
    byte processConnectError(final int n, final String s) {
        this.sendClientRsp(this.newConnectErrorRsp(n, s), "Cannot write error message");
        this.closeClientConnection();
        super.log.logError(7665689515738013595L, new Object[] { s });
        return 0;
    }
    
    byte processXIDError(final int n, final String s) {
        this.sendClientRsp(this.newXIDRsp(n, s), "Cannot write error message");
        this.closeClientConnection();
        super.log.logError(7665689515738013595L, new Object[] { s });
        return 0;
    }
    
    byte processSFDisconnectRsp(final int n, final String s) {
        this.sendClientRsp(this.newDisconnectRsp(n, s), "Cannot write error message");
        this.closeClientConnection();
        return 0;
    }
    
    byte processStartup(final Request request, final byte b) {
        this.clientCodepage = null;
        this.connID = this.newConnectionID();
        this.connCntxt = this.newConnectionContext();
        this.fBound = false;
        return super.processStartup(request, b);
    }
    
    byte processShutdown(final Request request, final byte b) {
        final ubAdminMsg ubAdminMsg = (ubAdminMsg)request.getMsg();
        final byte b2 = 10;
        ubAdminMsg.setadRsp(0);
        this.sendAdmRsp((RequestQueue)request.getRspQueue(), ubAdminMsg);
        return b2;
    }
    
    byte processDeferShutdownDisconnect(final Request request, final byte b) {
        byte processShutdown;
        if (super.properties.serverMode == 0 || (super.properties.serverMode == 3 && this.fBound)) {
            super.processDeferShutdownDisconnect(request, b);
            this.tsStartRequest();
            final Request request2 = new Request(this.newDisconnectRequest(this.clientCodepage, this.connID, this.connCntxt, this.connPID), super.rcvQueue);
            processShutdown = (this.fBound ? this.processEnqueueDirect(super.serverRequestQueue, request2, (byte)7) : this.processEnqueue(request2, (byte)7));
            this.closeClientConnection();
        }
        else {
            processShutdown = this.processShutdown(request, b);
        }
        return processShutdown;
    }
    
    byte processShutdownWrite(final Request request, final byte b) {
        return this.processEnqueueDirect(super.serverRequestQueue, new Request(this.newWriteLastRequest(2), super.rcvQueue), super.processShutdownWrite(request, b));
    }
    
    byte processShutdownRead(final Request request, final byte b) {
        return this.processEnqueueDirect(super.serverRequestQueue, new Request(this.newStopRequest(), super.rcvQueue), super.processShutdownRead(request, b));
    }
    
    byte processDeferAbendDisconnect(final Request request, final byte b) {
        byte processIOException;
        if (super.properties.serverMode == 0 || (super.properties.serverMode == 3 && this.fBound)) {
            this.tsStartRequest();
            super.processDeferAbendDisconnect(request, b);
            final ubAppServerMsg disconnectRequest = this.newDisconnectRequest(this.clientCodepage, this.connID, this.connCntxt, this.connPID);
            if (super.properties.getValueAsBoolean("actionalEnabled")) {
                try {
                    if (disconnectRequest.getubVer() > 108) {
                        disconnectRequest.appendTlvField((short)14, super.saveSocketName);
                    }
                }
                catch (Exception ex) {}
            }
            final Request request2 = new Request(disconnectRequest, super.rcvQueue);
            processIOException = (this.fBound ? this.processEnqueueDirect(super.serverRequestQueue, request2, (byte)7) : this.processEnqueue(request2, (byte)7));
        }
        else {
            processIOException = this.processIOException(request, b);
        }
        return processIOException;
    }
    
    byte processAbendWrite(final Request request, final byte b) {
        return this.processEnqueueDirect(super.serverRequestQueue, new Request(this.newWriteLastRequest(2), super.rcvQueue), super.processAbendWrite(request, b));
    }
    
    byte processAbendRead(final Request request, final byte b) {
        return this.processEnqueueDirect(super.serverRequestQueue, new Request(this.newStopRequest(), super.rcvQueue), super.processAbendWrite(request, b));
    }
    
    byte processIgnore(final Request request, final byte b) {
        return b;
    }
    
    byte processIOException(final Request request, final byte b) {
        return super.processIOException(request, b);
    }
    
    byte processConnRsp(final Request request, final byte b) {
        ++this.nTotalClients;
        final ubAppServerMsg ubAppServerMsg = (ubAppServerMsg)request.getMsg();
        final byte[] msgbuf = ubAppServerMsg.getMsgbuf();
        final int buflen = ubAppServerMsg.getBuflen();
        final short n = (short)((buflen < 5) ? 0 : ubMsg.getNetShort(msgbuf, 3));
        if (super.properties.serverMode == 0) {
            final int n2 = n + 14;
            final int n3 = n2 + (2 + ubMsg.getNetShort(msgbuf, n2));
            final int n4 = buflen - n3;
            System.arraycopy(msgbuf, n3, this.connCntxt = new byte[n4], 0, n4);
            if (super.log.ifLogBasic(2L, 1)) {
                super.log.logDump(2, 1, "connCntxt", this.connCntxt, n4);
            }
        }
        ubAppServerMsg.setBuflen(14 + n);
        this.appendConnectionID(ubAppServerMsg);
        this.insertASKCapabilities(ubAppServerMsg);
        super.m_ASKstate = 1;
        if (!(this.fBound = (super.properties.serverMode == 0 && (ubAppServerMsg.getubRspExt() & 0x1) > 0))) {
            super.serverRequestQueue = null;
        }
        final byte processDequeueLast = this.processDequeueLast(request, b);
        if (super.properties.serverMode == 0) {
            super.serverRequestQueue = null;
            this.tsEndRequest();
        }
        super.stats.incrnRspMsgs();
        return processDequeueLast;
    }
    
    byte processStop(final Request request, byte processEnqueueDirect) {
        final ubMsg ubMsg = (ubMsg)request.getMsg();
        if (ubMsg.getubSrc() == 3) {
            if (super.serverRequestQueue != null) {
                if (super.log.ifLogBasic(2L, 1)) {
                    super.log.logBasic(1, "Sending STOP msg to server");
                }
                processEnqueueDirect = this.processEnqueueDirect(super.serverRequestQueue, request, processEnqueueDirect);
            }
        }
        else {
            if (super.log.ifLogBasic(2L, 1)) {
                super.log.logBasic(1, "Sending STOP msg to client");
            }
            if (!this.sendClientRsp(ubMsg, "Cannot write stop message.")) {
                processEnqueueDirect = 10;
            }
        }
        return processEnqueueDirect;
    }
    
    byte processXID(final Request request, byte processXIDError) {
        super.stats.incrnRqs();
        super.stats.incrConnRqs(1);
        if (super.properties.serverMode == 3) {
            ++this.nTotalClients;
            if (super.log.ifLogBasic(2L, 1)) {
                super.log.logBasic(1, "Received XID request");
            }
            if (super.clientPool.numThreadsAllowed() == 0 && super.clientPool.numThreadsInState(0) == 0) {
                super.log.logError(7665689515738014062L, new Object[0]);
                return this.processXIDError(12, "Maximum number of client connections has been reached.");
            }
            final ubMsg ubMsg = (ubMsg)request.getMsg();
            this.clientCodepage = ubAppServerMsg.newNetByteArray(ubAppServerMsg.getNetString(ubMsg.getMsgbuf(), 0));
            super.stats.setConnUserName(this.getUserName(ubMsg));
            this.negotiateAskCapabilities(ubMsg);
            if (!this.sendClientRsp(this.newXIDRsp(0, null), "Cannot write XID response message.")) {
                processXIDError = 10;
            }
        }
        else {
            final ubMsg ubMsg2 = (ubMsg)request.getMsg();
            final String formatMessage = AppLogger.formatMessage(7665689515738019015L, new Object[] { super.properties.serverModeString(super.properties.serverMode) });
            super.log.logError(formatMessage);
            processXIDError = this.processXIDError((ubMsg2.getubVer() == 108) ? 11 : 13, formatMessage);
        }
        return processXIDError;
    }
    
    Request newAskEvent(final Queue queue) {
        Request request = null;
        if ((super.m_negotiatedAskCaps & 0x1) != 0x0 && (super.properties.serverMode == 0 || super.properties.serverMode == 3)) {
            final long n = System.currentTimeMillis() - super.stats.gettsLastSocketActivity();
            switch (super.m_ASKstate) {
                case 1: {
                    if (n > super.m_serverASKActivityTimeoutMs) {
                        request = new Request(new ubAdminMsg((byte)10), queue);
                        break;
                    }
                    break;
                }
                case 2: {
                    if (n > super.m_serverASKResponseTimeoutMs) {
                        request = new Request(new ubAdminMsg((byte)11), queue);
                        break;
                    }
                    break;
                }
                default: {
                    request = null;
                    break;
                }
            }
        }
        return request;
    }
    
    byte processASKActivityTimeout(final Request request, final byte b) {
        if (super.log.ifLogBasic(32768L, 15)) {
            super.log.logBasic(15, 7665689515738019278L, new Object[0]);
        }
        super.m_ASKstate = 2;
        final ubAppServerMsg askPingMsg = this.newASKPingMsg(22);
        final boolean sendClientRsp = this.sendClientRsp(askPingMsg, "Cannot write ASKPing request.");
        if (super.log.ifLogExtended(32768L, 15)) {
            askPingMsg.print("ASKPing Request", 4, 15, super.log);
        }
        return (byte)(sendClientRsp ? b : 10);
    }
    
    byte processASKResponseTimeout(final Request request, final byte b) {
        this.closeClientConnection();
        if (super.log.ifLogBasic(1L, 0)) {
            super.log.logBasic(0, 7665689515738019279L, new Object[0]);
        }
        return b;
    }
    
    byte processASKPingRequest(final Request request, final byte b) {
        if (super.log.ifLogExtended(32768L, 15)) {
            super.log.logExtended(15, "processASKPingRequest");
        }
        if ((super.m_negotiatedAskCaps & 0x2) != 0x0) {
            if (super.log.ifLogExtended(32768L, 15)) {
                super.log.logExtended(15, "Received ASKPing request from client.");
            }
            this.sendClientRsp(this.newASKPingMsg(23), "Cannot write ASKPing response.");
            if (super.log.ifLogExtended(32768L, 15)) {
                super.log.logExtended(15, "Sent ASKPing response to client.");
            }
        }
        return b;
    }
    
    byte processASKPingResponse(final Request request, final byte b) {
        if (super.log.ifLogBasic(32768L, 15)) {
            super.log.logBasic(15, 7665689515738019280L, new Object[0]);
        }
        return b;
    }
    
    ubAppServerMsg newASKPingMsg(final int n) {
        final ubAppServerMsg ubAppServerMsg = new ubAppServerMsg((short)109, 70, ++super.seqnum, 0, 0);
        ubAppServerMsg.setubSrc(1);
        ubAppServerMsg.setubRq(n);
        return ubAppServerMsg;
    }
    
    ubMsg clientRsp(final int n, final String s) {
        final ubAppServerMsg ubAppServerMsg = new ubAppServerMsg((short)108, 70, super.seqnum, 0, 0);
        ubAppServerMsg.setubSrc(1);
        if (s != null) {
            final byte[] csmssgRspbuf = com.progress.ubroker.util.ubAppServerMsg.csmssgRspbuf(0, 0, s);
            ubAppServerMsg.setMsgbuf(csmssgRspbuf, csmssgRspbuf.length);
            ubAppServerMsg.setCsHeaders(super.seqnum, csmssgRspbuf.length, 70);
        }
        ubAppServerMsg.setubRsp(n);
        return ubAppServerMsg;
    }
    
    ubMsg clientNonFatalErrorRsp(final int n, final String s) {
        final ubAppServerMsg ubAppServerMsg = new ubAppServerMsg((short)108, 70, super.seqnum, 0, 0);
        ubAppServerMsg.setubSrc(1);
        ubAppServerMsg.setubRq(13);
        if (s != null) {
            final byte[] csmssgErrorRspbuf = com.progress.ubroker.util.ubAppServerMsg.csmssgErrorRspbuf((byte)1, s, s);
            ubAppServerMsg.setMsgbuf(csmssgErrorRspbuf, csmssgErrorRspbuf.length);
            ubAppServerMsg.setCsHeaders(super.seqnum, csmssgErrorRspbuf.length, 70);
        }
        ubAppServerMsg.setubRsp(n);
        return ubAppServerMsg;
    }
    
    byte[] newConnectionID() {
        final String wholeID = this.newConnectionIDObj().m_wholeID;
        if (super.log.ifLogBasic(1L, 0)) {
            super.log.logBasic(0, 7665689515738013600L, new Object[] { wholeID });
        }
        final byte[] netByteArray = ubAppServerMsg.newNetByteArray(wholeID);
        if (this.affinityEnabled) {
            super.setConnectionID(wholeID);
        }
        return netByteArray;
    }
    
    byte[] newConnectionContext() {
        return ubAppServerMsg.newNetByteArray(null);
    }
    
    void trimConnectionID(final ubMsg ubMsg) {
        final byte[] msgbuf = ubMsg.getMsgbuf();
        final int buflen = ubMsg.getBuflen() - 2;
        if (buflen > 1 && msgbuf[buflen] == 0 && msgbuf[buflen + 1] == 0) {
            ubMsg.setBuflen(buflen);
            ((ubAppServerMsg)ubMsg).setMsglen(buflen + 4);
        }
        else {
            if (super.log.ifLogVerbose(1L, 0)) {
                super.log.logVerbose(0, 7665689515738013601L, new Object[0]);
            }
            if (super.log.ifLogBasic(2L, 1)) {
                ubMsg.print("trimConnectionID() Error : msg does not end in null string", 2, 1, super.log);
            }
        }
    }
    
    String getUserName(final ubMsg ubMsg) {
        final byte[] msgbuf = ubMsg.getMsgbuf();
        return ubAppServerMsg.getNetString(msgbuf, ubAppServerMsg.skipNetString(msgbuf, 0));
    }
    
    void appendConnectionID(final ubMsg ubMsg) {
        ubMsg.appendMsgbuf(this.connID, this.connID.length);
        ((ubAppServerMsg)ubMsg).setMsglen(ubMsg.getBuflen() + 4);
    }
    
    void appendProcessID(final ubMsg ubMsg) {
        ubMsg.appendMsgbuf(this.connPID);
        ((ubAppServerMsg)ubMsg).setMsglen(ubMsg.getBuflen() + 4);
    }
    
    void appendAppSrvCapability(final ubMsg ubMsg) {
        try {
            for (int i = 0; i < ubConstants.APPSRVCAPINFO_TYPE.length; ++i) {
                ubMsg.appendTlvField(ubConstants.APPSRVCAPINFO_TYPE[i], ubConstants.APPSRVCAPINFO_VALUE[i]);
            }
        }
        catch (Exception ex) {}
    }
    
    byte[] newConnectionBuf(final byte[] array, final byte[] array2) {
        final byte[] array3 = new byte[array.length + array2.length];
        final int n = 0;
        System.arraycopy(array, 0, array3, n, array.length);
        System.arraycopy(array2, 0, array3, n + array.length, array2.length);
        return array3;
    }
    
    byte[] newInitRqBuf(final byte[] array, final byte[] array2, final byte[] array3, final int n) {
        final byte[] array4 = new byte[array.length + array2.length + array3.length + 4];
        final int n2 = 0;
        System.arraycopy(array, 0, array4, n2, array.length);
        final int n3 = n2 + array.length;
        System.arraycopy(array2, 0, array4, n3, array2.length);
        System.arraycopy(array3, 0, array4, n3 + array2.length, array3.length);
        if (super.log.ifLogBasic(2L, 1)) {
            super.log.logDump(2, 1, "new initRq buf", array4, array4.length);
        }
        return array4;
    }
    
    ubMsg newInitStatelessRequest(final byte[] array, final byte[] array2, final byte[] array3, final int n) {
        final byte[] initRqBuf = this.newInitRqBuf(array, array2, array3, n);
        final ubAppServerMsg ubAppServerMsg = new ubAppServerMsg((short)109, 70, super.seqnum, 0, 0);
        ubAppServerMsg.setubSrc(1);
        ubAppServerMsg.setubRq(16);
        ubAppServerMsg.setMsgbuf(initRqBuf, initRqBuf.length);
        ubAppServerMsg.setMsglen(initRqBuf.length + 4);
        return ubAppServerMsg;
    }
    
    private ubAppServerMsg newDisconnectRequest(final byte[] array, final byte[] array2, final byte[] array3, final int n) {
        final byte[] initRqBuf = this.newInitRqBuf(array, array2, array3, n);
        final ubAppServerMsg ubAppServerMsg = new ubAppServerMsg((short)109, 20, super.seqnum, 0, 0);
        ubAppServerMsg.setubSrc(1);
        ubAppServerMsg.setubRq(6);
        ubAppServerMsg.setMsgbuf(initRqBuf, initRqBuf.length);
        ubAppServerMsg.setMsglen(initRqBuf.length + 4);
        return ubAppServerMsg;
    }
    
    private ubAppServerMsg newWriteLastRequest(final int n) {
        final ubAppServerMsg ubAppServerMsg = new ubAppServerMsg((short)108, 70, ++super.seqnum, 0, 0);
        ubAppServerMsg.setubSrc(1);
        ubAppServerMsg.setubRq(5);
        ubAppServerMsg.setubRqExt(0x20000000 | (n & 0xFF) << 8);
        return ubAppServerMsg;
    }
    
    private ubAppServerMsg newStopRequest() {
        final ubAppServerMsg ubAppServerMsg = new ubAppServerMsg((short)108, 40, ++super.seqnum, 0, 0);
        ubAppServerMsg.setubSrc(3);
        ubAppServerMsg.setubRq(7);
        return ubAppServerMsg;
    }
    
    ubMsg newConnectErrorRsp(final int n, final String s) {
        final ubAppServerMsg ubAppServerMsg = new ubAppServerMsg((short)108, 11, super.seqnum, 0, 0);
        ubAppServerMsg.setubSrc(1);
        ubAppServerMsg.setubRq(14);
        ubAppServerMsg.setubRsp(n);
        final byte[] csmssgRspbuf = com.progress.ubroker.util.ubAppServerMsg.csmssgRspbuf(1, 0, s);
        ubAppServerMsg.setMsgbuf(csmssgRspbuf, csmssgRspbuf.length);
        ubAppServerMsg.setCsHeaders(++super.seqnum, ubAppServerMsg.getBuflen(), 11);
        return ubAppServerMsg;
    }
    
    ubMsg newXIDRsp(final int n, final String s) {
        final ubAppServerMsg ubAppServerMsg = new ubAppServerMsg((short)109, 11, super.seqnum, 0, 0);
        ubAppServerMsg.setubSrc(1);
        ubAppServerMsg.setubRq(21);
        ubAppServerMsg.setubRsp(n);
        final byte[] csmssgRspbuf = com.progress.ubroker.util.ubAppServerMsg.csmssgRspbuf(0, 0, s);
        ubAppServerMsg.setMsgbuf(csmssgRspbuf, csmssgRspbuf.length);
        final byte[] array = new byte[9];
        array[0] = 8;
        ubMsg.setNetInt(array, 1, 0);
        ubMsg.setNetInt(array, 5, 0);
        ubAppServerMsg.appendMsgbuf(array, array.length);
        ubAppServerMsg.setMsglen(ubAppServerMsg.getBuflen());
        this.appendConnectionID(ubAppServerMsg);
        ubAppServerMsg.appendMsgbuf(new byte[0], 0);
        ubAppServerMsg.setCsHeaders(++super.seqnum, ubAppServerMsg.getBuflen(), 11);
        this.appendConnectionID(ubAppServerMsg);
        this.appendAppSrvCapability(ubAppServerMsg);
        this.insertASKCapabilities(ubAppServerMsg);
        super.m_ASKstate = 1;
        return ubAppServerMsg;
    }
    
    ubMsg newDisconnectRsp(final int n, final String s) {
        final ubAppServerMsg ubAppServerMsg = new ubAppServerMsg((short)108, 21, super.seqnum, 0, 0);
        ubAppServerMsg.setubSrc(1);
        ubAppServerMsg.setubRq(15);
        ubAppServerMsg.setubRsp(n);
        final byte[] csmssgRspbuf = com.progress.ubroker.util.ubAppServerMsg.csmssgRspbuf(1, 0, s);
        ubAppServerMsg.setMsgbuf(csmssgRspbuf, csmssgRspbuf.length);
        ubAppServerMsg.setCsHeaders(++super.seqnum, ubAppServerMsg.getBuflen(), 21);
        return ubAppServerMsg;
    }
    
    ubMsg newNoServersErrorRspXXX(final int n, final String s) {
        final ubAppServerMsg ubAppServerMsg = new ubAppServerMsg((short)108, 71, super.seqnum, 0, 0);
        ubAppServerMsg.setubSrc(1);
        ubAppServerMsg.setubRq(13);
        ubAppServerMsg.setubRsp(0);
        final byte[] csmssgRspbuf = com.progress.ubroker.util.ubAppServerMsg.csmssgRspbuf(1, 0, s);
        ubAppServerMsg.setMsgbuf(csmssgRspbuf, csmssgRspbuf.length);
        ubAppServerMsg.setCsHeaders(++super.seqnum, ubAppServerMsg.getBuflen(), 71);
        return ubAppServerMsg;
    }
}
