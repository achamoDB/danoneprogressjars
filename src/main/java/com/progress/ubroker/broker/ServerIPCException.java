// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.broker;

import com.progress.common.exception.ProException;

public class ServerIPCException extends ProException
{
    public ServerIPCException(final String s) {
        super("ServerIPC error: %s", new Object[] { s });
    }
    
    public String getDetail() {
        return (String)this.getArgument(0);
    }
}
