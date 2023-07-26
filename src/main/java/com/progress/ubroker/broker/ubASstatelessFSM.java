// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.broker;

import com.progress.common.ehnlog.IAppLogger;

public class ubASstatelessFSM extends ubServerFSM implements IServerFSM
{
    private static final byte[][][] statelessFSM;
    private static final String TABLE_NAME = "ASstateless";
    
    ubASstatelessFSM(final IAppLogger appLogger) {
        super("ASstateless", ubASstatelessFSM.statelessFSM, appLogger);
    }
    
    static {
        statelessFSM = new byte[][][] { { { 0, 0 }, { 1, 1 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 10, 11 }, { 20, 11 }, { 20, 11 }, { 13, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 18, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 } }, { { 0, 1 }, { 20, 1 }, { 3, 1 }, { 4, 4 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 6, 6 }, { 10, 11 }, { 11, 11 }, { 0, 1 }, { 13, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 18, 12 }, { 20, 11 }, { 21, 1 }, { 22, 1 } }, { { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 } }, { { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 } }, { { 0, 4 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 5, 5 }, { 6, 6 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 10, 11 }, { 11, 11 }, { 14, 4 }, { 13, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 18, 12 }, { 20, 11 }, { 21, 4 }, { 22, 4 } }, { { 0, 5 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 5, 5 }, { 6, 6 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 6, 6 }, { 6, 6 }, { 11, 11 }, { 14, 5 }, { 13, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 18, 12 }, { 20, 11 }, { 21, 5 }, { 22, 5 } }, { { 0, 6 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 7, 6 }, { 7, 7 }, { 20, 11 }, { 9, 1 }, { 20, 11 }, { 20, 11 }, { 10, 11 }, { 11, 11 }, { 14, 6 }, { 13, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 18, 12 }, { 20, 11 }, { 21, 6 }, { 22, 6 } }, { { 0, 7 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 8, 1 }, { 20, 11 }, { 10, 11 }, { 11, 11 }, { 14, 7 }, { 13, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 18, 12 }, { 20, 11 }, { 21, 7 }, { 22, 7 } }, { { 0, 8 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 10, 11 }, { 11, 11 }, { 20, 11 }, { 13, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 18, 12 }, { 20, 11 }, { 21, 8 }, { 22, 8 } }, { { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 } }, { { 20, 11 }, { 20, 11 }, { 3, 1 }, { 4, 4 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 6, 6 }, { 10, 11 }, { 20, 11 }, { 0, 10 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 20, 11 }, { 18, 12 }, { 20, 11 }, { 21, 10 }, { 22, 10 } } };
    }
}
