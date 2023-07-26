// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.broker;

import com.progress.ubroker.util.ubMsg;
import com.progress.ubroker.util.ubAppServerMsg;
import com.progress.ubroker.util.ubAdminMsg;
import com.progress.ubroker.util.RequestQueue;
import com.progress.common.ehnlog.IAppLogger;
import com.progress.ubroker.util.ubProperties;
import com.progress.ubroker.util.ubConstants;

public class ubJVserverThread extends ubASserverThread implements ubConstants
{
    public ubJVserverThread(final int n, final int n2, final ubProperties ubProperties, final ubThreadPool ubThreadPool, final IAppLogger appLogger, final IAppLogger appLogger2) {
        super(n, n2, ubProperties, ubThreadPool, appLogger, appLogger2);
    }
    
    IServerIPC initServerIPC(final int n) throws ServerIPCException {
        final ubServerThreadIPC ubServerThreadIPC = new ubServerThreadIPC(super.properties.serverType, super.log, 2);
        ubServerThreadIPC.create(n, super.properties.getValueAsInt("brkrSpinInterval"));
        return ubServerThreadIPC;
    }
    
    boolean startServer(final String s) {
        super.serverPid = this.getThreadId();
        return true;
    }
    
    boolean testServerProcess() {
        return true;
    }
    
    byte shutdownServerProcess(final RequestQueue requestQueue, final ubAdminMsg ubAdminMsg, final byte b) {
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
    
    byte sendAdminMessage(final RequestQueue requestQueue, final ubAdminMsg ubAdminMsg, final byte b) {
        ubAdminMsg.setadRsp(0);
        this.sendRsp(requestQueue, ubAdminMsg);
        return b;
    }
    
    byte readAdminResponse(final RequestQueue requestQueue, final ubMsg ubMsg, final byte b) {
        return b;
    }
}
