// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools;

import java.rmi.RemoteException;
import com.progress.ubroker.util.ToolRemoteCmdStatus;
import com.progress.ubroker.util.ToolRemoteCmdDescriptor;
import java.rmi.Remote;

public interface IYodaRMI extends Remote
{
    public static final long SVC_NOT_ACTIVE = -1L;
    public static final long SVC_NOTCONNECTED = 0L;
    public static final long SVC_CONNECTED = 1L;
    public static final long SVC_STARTED = 2L;
    public static final long SVC_DISCONNECTED = 3L;
    public static final long SVC_STOPPED = 4L;
    public static final long SVC_STOP_FAILURE = 5L;
    public static final long SVC_BAD_RMIURL = 6L;
    public static final long SVC_CONNECT_RETRY = 7L;
    public static final long SVC_CONNECT_FAIL = 8L;
    
    ToolRemoteCmdStatus doRemoteToolCmd(final ToolRemoteCmdDescriptor p0) throws RemoteException;
}
