// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl;

import com.progress.common.ehnlog.IAppLogger;
import com.progress.open4gl.dynamicapi.IPoolProps;
import com.progress.open4gl.javaproxy.AppObject;

class SDOAppObjectImpl extends AppObject
{
    public SDOAppObjectImpl(final String s, final IPoolProps poolProps, final IAppLogger appLogger) throws Open4GLException, ConnectException, SystemErrorException {
        super(s, poolProps, appLogger, null);
    }
}
