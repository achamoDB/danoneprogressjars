// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.broker;

import com.progress.ubroker.client.BrokerSystem;
import com.progress.common.ehnlog.IAppLogger;
import com.progress.open4gl.dynamicapi.IPoolProps;

public class BrokerFactory
{
    static final int RMI = 1;
    static final int SOCKETS = 2;
    
    public static final Broker create(final IPoolProps poolProps, final IAppLogger appLogger) throws BrokerException {
        return new BrokerSystem(poolProps, appLogger);
    }
    
    static final Broker create(final int n, final IPoolProps poolProps, final IAppLogger appLogger) throws BrokerException {
        switch (n) {
            case 2: {
                return new BrokerSystem(poolProps, appLogger);
            }
            default: {
                throw new BrokerException();
            }
        }
    }
}
