// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.javaproxy;

import com.progress.open4gl.RunTime4GLException;
import com.progress.open4gl.dynamicapi.ParameterSet;
import com.progress.open4gl.dynamicapi.RqContext;
import com.progress.open4gl.dynamicapi.ResultSet;
import com.progress.open4gl.dynamicapi.ResultSetSchema;
import com.progress.open4gl.SystemErrorException;
import com.progress.open4gl.ConnectException;
import com.progress.open4gl.Open4GLException;
import com.progress.common.ehnlog.IAppLogger;
import com.progress.open4gl.dynamicapi.IPoolProps;

final class OpenAppObjectImpl extends AppObject
{
    OpenAppObjectImpl(final String s, final IPoolProps poolProps, final IAppLogger appLogger) throws Open4GLException, ConnectException, SystemErrorException {
        super(s, poolProps, appLogger, null);
    }
    
    void runProc(final String s, final ParamArray paramArray) throws Open4GLException, RunTime4GLException, SystemErrorException {
        if (!this.isSessionAvailable()) {
            throw new Open4GLException(7665970990714723420L, null);
        }
        paramArray.validate();
        RqContext rqContext;
        if (paramArray.getMetaSchema() == null) {
            rqContext = this.runProcedure(s, paramArray.getParameterSet());
        }
        else {
            rqContext = this.runProcedure(s, paramArray.getParameterSet(), paramArray.getMetaSchema());
        }
        if (rqContext != null) {
            if (!rqContext._isStreaming()) {
                rqContext._release();
            }
            else {
                ResultSet set = null;
                final ParameterSet parameterSet = paramArray.getParameterSet();
                for (int i = 1; i <= parameterSet.getNumParams(); ++i) {
                    if (parameterSet.isResultSet(i) && parameterSet.isOutput(i)) {
                        set = (ResultSet)parameterSet.getOutputParameter(i);
                    }
                }
                if (set != null) {
                    set.setRqContext(rqContext);
                }
            }
        }
    }
}
