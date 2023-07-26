// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.javaproxy;

import com.progress.open4gl.SystemErrorException;
import com.progress.open4gl.RunTime4GLException;
import com.progress.open4gl.Open4GLException;

public class OpenProcObject
{
    protected OpenProcObjectImpl m_DynPOImpl;
    
    public OpenProcObject(final ProObject proObject, final String s, final ParamArray paramArray) throws Open4GLException, RunTime4GLException, SystemErrorException {
        this.m_DynPOImpl = new OpenProcObjectImpl(proObject, s, paramArray);
    }
    
    public OpenProcObject(final ProObject proObject, final String s, final int n) throws Open4GLException, RunTime4GLException, SystemErrorException {
        this.m_DynPOImpl = new OpenProcObjectImpl(proObject, s, n);
    }
    
    public void _release() throws Open4GLException, SystemErrorException {
        this.m_DynPOImpl._release();
    }
    
    public Object _getConnectionId() throws Open4GLException {
        return this.m_DynPOImpl._getConnectionId();
    }
    
    public Object _getRequestId() throws Open4GLException {
        return this.m_DynPOImpl._getRequestId();
    }
    
    public Object _getSSLSubjectName() throws Open4GLException {
        return this.m_DynPOImpl._getSSLSubjectName();
    }
    
    public Object _getProcId() throws Open4GLException {
        return this.m_DynPOImpl.getProcId();
    }
    
    public boolean _isStreaming() throws Open4GLException {
        return this.m_DynPOImpl._isStreaming();
    }
    
    public void _cancelAllRequests() throws Open4GLException {
        this.m_DynPOImpl._cancelAllRequests();
    }
    
    public String _getProcReturnString() throws Open4GLException {
        return this.m_DynPOImpl._getProcReturnString();
    }
    
    public void runProc(final String s, final ParamArray paramArray) throws Open4GLException, RunTime4GLException, SystemErrorException {
        this.m_DynPOImpl.runProc(s, paramArray);
    }
}
