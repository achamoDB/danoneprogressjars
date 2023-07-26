// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.broker;

import com.progress.ubroker.util.ubThread;
import com.progress.ubroker.util.RequestQueue;
import com.progress.ubroker.util.ubAppServerMsg;
import com.progress.ubroker.util.ubMsg;
import com.progress.ubroker.util.Request;
import com.progress.common.ehnlog.IAppLogger;
import com.progress.ubroker.util.ubProperties;
import com.progress.ubroker.util.ubConstants;

public class ubDSclientThread extends ubASclientThread implements ubConstants, IClientFSM
{
    public ubDSclientThread(final int n, final ubThreadPool ubThreadPool, final ubThreadPool ubThreadPool2, final IServerThreadControl serverThreadControl, final ubProperties ubProperties, final IAppLogger appLogger) {
        super(n, ubThreadPool, ubThreadPool2, serverThreadControl, ubProperties, appLogger);
    }
    
    ubClientFSM setClientFSM() {
        return new ubDSclientFSM(super.log);
    }
    
    byte processConnect(final Request request, final byte b) {
        final ubMsg ubMsg = (ubMsg)request.getMsg();
        this.tsStartRequest();
        super.serverRequestQueue = null;
        final ubAppServerMsg ubAppServerMsg = (ubAppServerMsg)ubMsg;
        super.clientCodepage = com.progress.ubroker.util.ubAppServerMsg.newNetByteArray(com.progress.ubroker.util.ubAppServerMsg.getNetString(ubMsg.getMsgbuf(), 0));
        this.trimConnectionID(ubMsg);
        this.appendConnectionID(ubMsg);
        final byte[] msgbuf = ubMsg.getMsgbuf();
        int i = 0;
        int n = 0;
        while (i < 3) {
            n += com.progress.ubroker.util.ubMsg.getNetShort(msgbuf, n) + 2;
            ++i;
        }
        final String netString = com.progress.ubroker.util.ubAppServerMsg.getNetString(msgbuf, n);
        if (super.log.ifLogVerbose(1L, 0)) {
            super.log.logVerbose(0, 7665689515738013603L, new Object[] { netString });
        }
        final ubThread start_ServerWithArgs = super.serverCntrl.start_ServerWithArgs(new Object[] { super.rcvQueue, netString }, new RequestQueue("ubClientThread admrsp Queue", 0, super.log));
        byte b2 = 0;
        if (start_ServerWithArgs == null) {
            super.log.logError(7665689515738013604L, new Object[0]);
            switch (((ubMsg)request.getMsg()).getubRq()) {
                case 3: {
                    b2 = this.processConnectError(7, "No Servers available");
                    break;
                }
                default: {
                    b2 = this.processFatalError(7, "No Servers available");
                    break;
                }
            }
            this.tsEndRequest();
        }
        else {
            super.serverRequestQueue = start_ServerWithArgs.getRcvQueue();
            b2 = this.processEnqueueDirect(start_ServerWithArgs.getRcvQueue(), request, b);
        }
        return b2;
    }
}
