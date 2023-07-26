// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.mf.runtime;

import java.rmi.server.RemoteRef;
import java.rmi.server.Operation;
import com.progress.chimera.common.IChimeraRemoteObject;
import java.rmi.server.RemoteStub;

public final class ManagementPlugin_Stub extends RemoteStub implements IChimeraRemoteObject
{
    private static final Operation[] operations;
    private static final long interfaceHash = 3103311997983563335L;
    
    static {
        operations = new Operation[0];
    }
    
    public ManagementPlugin_Stub() {
    }
    
    public ManagementPlugin_Stub(final RemoteRef ref) {
        super(ref);
    }
}
