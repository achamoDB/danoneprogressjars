// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.log;

import java.rmi.server.RemoteRef;
import java.rmi.server.Operation;
import java.rmi.Remote;
import java.rmi.server.RemoteStub;

public final class Monitor_Stub extends RemoteStub implements Remote
{
    private static final Operation[] operations;
    private static final long interfaceHash = 3103311997983563335L;
    
    static {
        operations = new Operation[0];
    }
    
    public Monitor_Stub() {
    }
    
    public Monitor_Stub(final RemoteRef ref) {
        super(ref);
    }
}
