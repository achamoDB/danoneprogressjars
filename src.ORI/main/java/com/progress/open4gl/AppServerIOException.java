// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl;

import com.progress.ubroker.util.SocketConnectionInfoEx;
import com.progress.ubroker.util.SocketConnectionInfo;
import com.progress.message.jcMsg;

public class AppServerIOException extends ConnectException implements jcMsg
{
    public AppServerIOException(final SocketConnectionInfo socketConnectionInfo, final String s) {
        super(7665970990714724982L, new Object[] { socketConnectionInfo.getHost(), new Integer(socketConnectionInfo.getPort()), s });
    }
    
    public AppServerIOException(final SocketConnectionInfoEx socketConnectionInfoEx, final String s) {
        super(7665970990714724982L, new Object[] { socketConnectionInfoEx.getHost(), new Integer(socketConnectionInfoEx.getPort()), s });
    }
}
