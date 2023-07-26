// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.broker;

import com.progress.common.ehnlog.IAppLogger;
import com.progress.ubroker.util.ubConstants;

public abstract class ubClientFSM extends ubFSM implements IClientFSM, ubConstants
{
    public ubClientFSM(final String s, final byte[][][] array, final IAppLogger appLogger) {
        super(s, array, IClientFSM.DESC_STATE, IClientFSM.DESC_EVENT, IClientFSM.DESC_ACTION, appLogger);
    }
}
