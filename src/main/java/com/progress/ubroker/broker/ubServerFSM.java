// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.broker;

import com.progress.common.ehnlog.IAppLogger;
import com.progress.ubroker.util.ubConstants;

public abstract class ubServerFSM extends ubFSM implements IServerFSM, ubConstants
{
    public ubServerFSM(final String s, final byte[][][] array, final IAppLogger appLogger) {
        super(s, array, IServerFSM.DESC_STATE, IServerFSM.DESC_EVENT, IServerFSM.DESC_ACTION, appLogger);
    }
}
