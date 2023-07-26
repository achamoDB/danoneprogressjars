// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl;

import com.progress.ubroker.util.SocketConnectionInfoEx;
import com.progress.ubroker.util.SocketConnectionInfo;
import com.progress.message.jcMsg;

public class UnknownHostnameException extends ConnectException implements jcMsg
{
    public UnknownHostnameException(final SocketConnectionInfo socketConnectionInfo) {
        super(7665970990714724981L, new Object[] { socketConnectionInfo.getHost() });
    }
    
    public UnknownHostnameException(final SocketConnectionInfoEx socketConnectionInfoEx) {
        super(7665970990714724981L, new Object[] { socketConnectionInfoEx.getHost() });
    }
}
