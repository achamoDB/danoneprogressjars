// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.broker;

import com.progress.ubroker.util.ubMsg;
import com.progress.ubroker.util.ubAdminMsg;
import com.progress.ubroker.util.RequestQueue;
import com.progress.common.ehnlog.IAppLogger;
import com.progress.ubroker.util.ubProperties;
import com.progress.ubroker.util.ubConstants;

public class ubDSserverThread extends ubASserverThread implements ubConstants, IServerFSM
{
    public ubDSserverThread(final int n, final int n2, final ubProperties ubProperties, final ubThreadPool ubThreadPool, final IAppLogger appLogger, final IAppLogger appLogger2) {
        super(n, n2, ubProperties, ubThreadPool, appLogger, appLogger2);
    }
    
    ubServerFSM setServerFSM(final int n, final int n2, final IAppLogger appLogger) {
        return new ubDSstateAwareFSM(appLogger);
    }
    
    String bldCmdLine(final String str) {
        final StringBuffer sb = new StringBuffer(100);
        final int n = this.getThreadId() & 0xFFFF;
        sb.append(super.properties.getValueAsString("srvrExecFile"));
        sb.append(" " + super.properties.getValueAsString("srvrStartupParam"));
        if (str != null) {
            sb.append(" " + str);
        }
        sb.append(" -ipver " + super.properties.ipverString());
        if (super.properties.ipver == 1) {
            sb.append(" -H " + super.properties.localHost);
        }
        final int valueAsInt = super.properties.getValueAsInt("srvrMinPort");
        final int valueAsInt2 = super.properties.getValueAsInt("srvrMaxPort");
        if (valueAsInt > valueAsInt2 && super.log.ifLogBasic(1L, 0)) {
            super.log.logBasic(0, "ERROR: property srvrMinPort is larger than srvrMaxPort");
        }
        sb.append(" -dsminport " + valueAsInt);
        sb.append(" -dsmaxport " + valueAsInt2);
        final String valueAsString = super.properties.getValueAsString("srvrDSLogFile");
        if (valueAsString != null && valueAsString.length() > 0) {
            sb.append(" -dslog " + super.properties.dblquote + valueAsString + super.properties.dblquote);
        }
        final int valueAsInt3 = super.properties.getValueAsInt("srvrLogThreshold");
        if (valueAsInt3 > 0) {
            sb.append(" -logthreshold " + valueAsInt3);
        }
        final int valueAsInt4 = super.properties.getValueAsInt("srvrNumLogFiles");
        if (valueAsInt4 != 3) {
            sb.append(" -numlogfiles " + valueAsInt4);
        }
        return sb.toString();
    }
    
    byte processStartup(final RequestQueue requestQueue, final ubAdminMsg ubAdminMsg, final byte b) {
        return super.processStartup(requestQueue, ubAdminMsg, b);
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
        return super.processConnect(requestQueue, ubMsg, b);
    }
    
    ubMsg clientRsp(final int n, final String s) {
        return super.clientRsp(n, s);
    }
    
    byte shutdownServerProcess(final RequestQueue requestQueue, final ubAdminMsg ubAdminMsg, final byte b) {
        ubAdminMsg.setadRsp(0);
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
