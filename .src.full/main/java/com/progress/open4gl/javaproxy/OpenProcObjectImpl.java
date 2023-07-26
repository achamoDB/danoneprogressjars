// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.javaproxy;

import com.progress.open4gl.dynamicapi.ResultSet;
import com.progress.open4gl.dynamicapi.RqContext;
import com.progress.open4gl.SystemErrorException;
import com.progress.open4gl.RunTime4GLException;
import com.progress.open4gl.Open4GLException;
import com.progress.open4gl.dynamicapi.ResultSetSchema;

final class OpenProcObjectImpl extends Procedure
{
    OpenProcObjectImpl(final ProObject proObject, final String procName, final ParamArray paramArray) throws Open4GLException, RunTime4GLException, SystemErrorException {
        super(proObject);
        if (paramArray.getMetaSchema() == null) {
            this.m_persistProc = this.runPersistentProcedure(procName, paramArray.getParameterSet());
        }
        else {
            this.m_persistProc = this.runPersistentProcedure(procName, paramArray.getParameterSet(), paramArray.getMetaSchema());
        }
        this.m_procName = procName;
    }
    
    OpenProcObjectImpl(final ProObject proObject, final String procName, final int i) throws Open4GLException, RunTime4GLException, SystemErrorException {
        super(proObject, "SP");
        switch (i) {
            case 9:
            case 11: {
                this.m_persistProc = this.runPersistentProcedure(procName, i);
                this.m_procName = procName;
            }
            default: {
                throw new Open4GLException("Invalid stateless Procedure type: " + Integer.toString(i));
            }
        }
    }
    
    void runProc(final String s, final ParamArray paramArray) throws Open4GLException, RunTime4GLException, SystemErrorException {
        final RqContext rqContext = null;
        if (!this.isSessionAvailable()) {
            throw new Open4GLException(7665970990714723420L, null);
        }
        if (paramArray.getMetaSchema() == null) {
            this.runProcedure(s, paramArray.getParameterSet());
        }
        else {
            this.runProcedure(s, paramArray.getParameterSet(), paramArray.getMetaSchema());
        }
        if (rqContext != null) {
            if (!rqContext._isStreaming()) {
                rqContext._release();
            }
            else {
                final ResultSet set = null;
                if (set != null) {
                    set.setRqContext(rqContext);
                }
            }
        }
    }
}
