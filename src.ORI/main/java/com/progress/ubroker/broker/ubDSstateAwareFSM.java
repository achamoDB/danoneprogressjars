// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.broker;

import com.progress.common.ehnlog.IAppLogger;

public class ubDSstateAwareFSM extends ubServerFSM implements IServerFSM
{
    private static final byte[][][] stateAwareFSM;
    private static final String TABLE_NAME = "DSstateAware";
    
    ubDSstateAwareFSM(final IAppLogger appLogger) {
        super("DSstateAware", ubDSstateAwareFSM.stateAwareFSM, appLogger);
    }
    
    static {
        stateAwareFSM = new byte[][][] { { { 0, 0 }, { 1, 1 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 10, 11 }, { 20, 11 }, { 20, 11 }, { 13, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 } }, { { 0, 1 }, { 20, 1 }, { 3, 3 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 10, 11 }, { 11, 11 }, { 20, 11 }, { 13, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 } }, { { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 } }, { { 0, 3 }, { 13, 11 }, { 13, 11 }, { 20, 11 }, { 13, 11 }, { 13, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 9, 1 }, { 10, 11 }, { 11, 11 }, { 13, 11 }, { 13, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 } }, { { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 } }, { { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 } }, { { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 } }, { { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 } }, { { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 } }, { { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 } }, { { 0, 10 }, { 20, 11 }, { 3, 3 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 10, 11 }, { 11, 11 }, { 20, 11 }, { 13, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 } } };
    }
}
