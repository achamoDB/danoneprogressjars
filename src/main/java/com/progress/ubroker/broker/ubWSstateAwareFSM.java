// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.broker;

import com.progress.common.ehnlog.IAppLogger;

public class ubWSstateAwareFSM extends ubServerFSM implements IServerFSM
{
    private static final byte[][][] stateAwareFSM;
    private static final String TABLE_NAME = "WSstateAware";
    
    ubWSstateAwareFSM(final IAppLogger appLogger) {
        super("WSstateAware", ubWSstateAwareFSM.stateAwareFSM, appLogger);
    }
    
    static {
        stateAwareFSM = new byte[][][] { { { 0, 0 }, { 1, 9 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 10, 11 }, { 20, 11 }, { 20, 11 }, { 13, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 } }, { { 0, 1 }, { 20, 1 }, { 3, 2 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 10, 11 }, { 11, 11 }, { 20, 11 }, { 13, 11 }, { 16, 1 }, { 16, 9 }, { 16, 10 }, { 15, 1 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 21, 1 }, { 22, 1 } }, { { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 10, 11 }, { 11, 11 }, { 20, 11 }, { 13, 11 }, { 16, 1 }, { 16, 9 }, { 16, 10 }, { 15, 2 }, { 17, 1 }, { 20, 11 }, { 20, 11 }, { 21, 2 }, { 22, 2 } }, { { 0, 3 }, { 13, 11 }, { 13, 11 }, { 20, 11 }, { 13, 11 }, { 13, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 9, 1 }, { 10, 11 }, { 11, 11 }, { 13, 11 }, { 13, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 15, 3 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 21, 3 }, { 22, 3 } }, { { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 } }, { { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 } }, { { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 } }, { { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 } }, { { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 } }, { { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 10, 11 }, { 11, 11 }, { 20, 11 }, { 12, 11 }, { 16, 1 }, { 16, 9 }, { 16, 10 }, { 15, 9 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 21, 9 }, { 22, 9 } }, { { 20, 11 }, { 20, 11 }, { 3, 2 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 10, 11 }, { 11, 11 }, { 20, 11 }, { 20, 11 }, { 16, 1 }, { 16, 9 }, { 16, 10 }, { 15, 10 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 21, 10 }, { 22, 10 } } };
    }
}
