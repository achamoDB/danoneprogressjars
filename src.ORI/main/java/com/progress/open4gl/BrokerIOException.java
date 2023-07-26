// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl;

import com.progress.ubroker.util.SocketConnectionInfoEx;
import com.progress.ubroker.util.SocketConnectionInfo;
import com.progress.message.jcMsg;

public class BrokerIOException extends ConnectException implements jcMsg
{
    public BrokerIOException(final SocketConnectionInfo socketConnectionInfo, final String s) {
        super(7665970990714724983L, new Object[] { socketConnectionInfo.getHost(), new Integer(socketConnectionInfo.getPort()), s });
    }
    
    public BrokerIOException(final SocketConnectionInfoEx socketConnectionInfoEx, final String s) {
        super(7665970990714724983L, new Object[] { socketConnectionInfoEx.getHost(), new Integer(socketConnectionInfoEx.getPort()), s });
    }
}
