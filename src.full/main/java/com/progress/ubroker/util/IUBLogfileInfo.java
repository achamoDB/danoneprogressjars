// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.util;

import java.rmi.Remote;

public interface IUBLogfileInfo extends Remote
{
    public static final String UB_LOG_DEFAULT_FILEVIEWER_CLASSNAME = "com.progress.vj.ubroker.UBMMCServerLog";
    public static final String UB_LOG_FILEVIEWER_CLASSNAME = "com.progress.vj.ubroker.UBMMCLog";
    public static final String UB_LOG_BRKR_FILEVIEWER_CLASSNAME = "com.progress.vj.ubroker.UBMMCBrokerLog";
}
