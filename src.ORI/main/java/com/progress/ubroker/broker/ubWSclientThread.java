// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.broker;

import com.progress.ubroker.util.ubThread;
import com.progress.ubroker.util.RequestQueue;
import com.progress.ubroker.util.ubAdminMsg;
import com.progress.ubroker.util.ubWebSpeedMsg;
import com.progress.ubroker.util.ubMsg;
import com.progress.ubroker.util.Request;
import com.progress.common.ehnlog.IAppLogger;
import com.progress.ubroker.util.ubProperties;
import com.progress.ubroker.util.ubConstants;

public class ubWSclientThread extends ubClientThread implements ubConstants, IClientFSM
{
    public ubWSclientThread(final int n, final ubThreadPool ubThreadPool, final ubThreadPool ubThreadPool2, final IServerThreadControl serverThreadControl, final ubProperties ubProperties, final IAppLogger appLogger) {
        super(n, ubThreadPool, ubThreadPool2, serverThreadControl, ubProperties, appLogger);
    }
    
    ubClientFSM setClientFSM() {
        return new ubWSclientFSM(super.log);
    }
    
    byte processConnect(final Request request, final byte b) {
        return super.processConnect(request, b);
    }
    
    byte processConnectDirect(final Request request, final byte b) {
        final ubMsg ubMsg = (ubMsg)request.getMsg();
        final String str = new String(ubMsg.getMsgbuf(), 0, ubMsg.getBuflen());
        if (super.log.ifLogBasic(2L, 1)) {
            super.log.logBasic(1, "processConnectDirect key= (" + str + ")");
        }
        final ubServerThread serverThreadByCookie = ((ubServerThreadPool)super.serverPool).getServerThreadByCookie(str);
        byte b2;
        if (serverThreadByCookie == null) {
            super.log.logError(7665689515738013602L, new Object[] { str });
            b2 = this.processConnect(request, b);
        }
        else {
            if (super.log.ifLogBasic(2L, 1)) {
                super.log.logBasic(1, "found thread (" + serverThreadByCookie.getFullName() + ")");
            }
            super.serverRequestQueue = serverThreadByCookie.getRcvQueue();
            this.tsStartRequest();
            b2 = this.processEnqueueDirect(serverThreadByCookie.getRcvQueue(), request, b);
        }
        return b2;
    }
    
    byte processConnRsp(final Request request, final byte b) {
        final ubMsg ubMsg = (ubMsg)request.getMsg();
        if (super.log.ifLogVerbose(1L, 0)) {
            super.log.logVerbose(0, "ubWSclientThread.processConnRsp(): ubRsp = " + ubMsg.getubRsp() + ", getNeedNewConnID() = " + ubMsg.getNeedNewConnID());
        }
        if (ubMsg.getubRsp() == 0 && ubMsg.getNeedNewConnID()) {
            final String str = new String(ubMsg.getMsgbuf(), 0, ubMsg.getBuflen());
            final String connectionID = this.newConnectionID();
            final int n = str.length() + 1;
            final int n2 = n + connectionID.length() + 1;
            if (super.log.ifLogVerbose(1L, 0)) {
                super.log.logVerbose(0, "ubWSClientThread.processConnRsp(): serverPortStr = " + str + ", newConnID = " + connectionID + ", rspBufSize = " + n2);
            }
            final byte[] array = new byte[n2];
            ubWebSpeedMsg.setNetString(array, 0, str);
            ubWebSpeedMsg.setNetString(array, n - 1, connectionID);
            if (super.log.ifLogBasic(1L, 0)) {
                super.log.logVerbose(0, "ubWSClientThread.processConnRsp(): ubRspExt(before set sent bit) " + ubMsg.getubRspExt());
            }
            ubMsg.setubRspExt(ubMsg.getubRspExt() | 0x2);
            ubMsg.setMsgbuf(array, array.length);
            if (super.log.ifLogBasic(1L, 0)) {
                super.log.logVerbose(0, "ubWSClientThread.processConnRsp(): ubRspExt " + ubMsg.getubRspExt() + ", msgBufLeng = " + array.length + ", rspBufSize = " + n2);
            }
            ((ubWebSpeedMsg)ubMsg).setwsHeader(32, 4, array.length);
        }
        return super.processConnRsp(request, b);
    }
    
    byte processDequeueLast(final Request request, final byte b) {
        final ubMsg ubMsg = (ubMsg)request.getMsg();
        super.stats.incrnRsps();
        super.serverRequestQueue = null;
        final boolean sendClientRsp = this.sendClientRsp(ubMsg, "Cannot write response message.");
        this.tsEndRequest();
        return (byte)(sendClientRsp ? b : 10);
    }
    
    byte processFatalError(final int n, final String s) {
        return super.processFatalError(n, s);
    }
    
    byte processShutdown(final Request request, final byte b) {
        final ubAdminMsg ubAdminMsg = (ubAdminMsg)request.getMsg();
        final byte b2 = 10;
        ubAdminMsg.setadRsp(0);
        this.sendAdmRsp((RequestQueue)request.getRspQueue(), ubAdminMsg);
        return b2;
    }
    
    byte processIOException(final Request request, final byte b) {
        return super.processIOException(request, b);
    }
    
    byte processBrokerStatus(final Request request, final byte b) {
        int n = 0;
        final int getubRqExt = ((ubMsg)request.getMsg()).getubRqExt();
        String s = null;
        switch (getubRqExt) {
            case 0: {
                s = super.serverCntrl.queryBroker(0);
                break;
            }
            case 1: {
                s = super.serverCntrl.queryBroker(1);
                break;
            }
            default: {
                s = "Invalid Status Type (" + getubRqExt + ")";
                n = 1;
                break;
            }
        }
        final ubMsg clientRsp = this.clientRsp(n, s);
        clientRsp.setubRq(19);
        ((ubWebSpeedMsg)clientRsp).setwsHeader(32, 4, clientRsp.getBuflen());
        this.sendClientRsp(clientRsp, "Cannot write Broker Status message");
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
    
    ubMsg clientNonFatalErrorRsp(final int n, final String s) {
        return this.clientRsp(n, s);
    }
    
    ubServerThread getServerThreadByCookie_old(final String s) {
        if (super.log.ifLogBasic(2L, 1)) {
            super.log.logBasic(1, "getServerThreadByCookie key= (" + s + ")");
        }
        if (s == null || s.length() == 0) {
            return null;
        }
        final ubThread[] enumerateThreads = super.serverPool.enumerateThreads();
        if (super.log.ifLogBasic(2L, 1)) {
            super.log.logBasic(1, "serverPool threads= (" + enumerateThreads + ")");
        }
        if (enumerateThreads == null) {
            return null;
        }
        int i = 0;
        ubServerThread ubServerThread = null;
        while (i < enumerateThreads.length) {
            final String serverCookie = ((ubServerThread)enumerateThreads[i]).getServerCookie();
            if (super.log.ifLogBasic(2L, 1)) {
                super.log.logBasic(1, "pool[" + i + "] = (" + enumerateThreads[i].getFullName() + ") cookie = (" + serverCookie + ")");
            }
            if (serverCookie != null && serverCookie.compareTo(s) == 0) {
                ubServerThread = (ubServerThread)enumerateThreads[i];
                break;
            }
            ++i;
        }
        return ubServerThread;
    }
    
    ubThread dequeueReadyServer(final int n) {
        final RequestQueue requestQueue = new RequestQueue(this.getFullName() + "-admrspQ", 0, super.log);
        super.serverPool.start_minThreads(0, null, requestQueue);
        if (super.log.ifLogBasic(2L, 1)) {
            super.log.logBasic(1, "Dequeueing ready server from serverPool " + super.serverPool);
        }
        final ubThread dequeueThreadInState = super.serverPool.dequeueThreadInState(1, null, 0L);
        if (dequeueThreadInState != null) {
            return dequeueThreadInState;
        }
        if (super.serverPool.size() > 0) {
            if (super.log.ifLogVerbose(1L, 0)) {
                super.log.logVerbose(0, 7665689515738013922L, new Object[0]);
            }
            final ubThread dequeueThreadInState2 = super.serverPool.dequeueThreadInState(1, requestQueue, n);
            if (dequeueThreadInState2 != null) {
                return dequeueThreadInState2;
            }
        }
        if (super.log.ifLogVerbose(1L, 0)) {
            super.log.logVerbose(0, 7665689515738013592L, new Object[0]);
        }
        super.serverPool.start_Thread(0, null, requestQueue);
        return null;
    }
    
    String newConnectionID() {
        return this.newConnectionIDObj().m_wholeID;
    }
}
