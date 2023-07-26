// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl;

import java.io.IOException;
import com.progress.common.ehnlog.IAppLogger;
import com.progress.open4gl.dynamicapi.IPoolProps;
import com.progress.open4gl.javaproxy.Connection;

public final class SDOAppObject implements SDOFactory
{
    protected static final long m_wrongProxyVer = 7665970990714723421L;
    private static final int PROXY_VER = 5;
    protected SDOAppObjectImpl m_SDOAppObjectImpl;
    
    public SDOAppObject(final Connection connection) throws Open4GLException, ConnectException, SystemErrorException, IOException {
        if (RunTimeProperties.getDynamicApiVersion() != 5) {
            throw new Open4GLException(7665970990714723421L, null);
        }
        this.m_SDOAppObjectImpl = new SDOAppObjectImpl("proxies.SDOApp2", connection, RunTimeProperties.tracer);
    }
    
    public SDOAppObject(final String s, final String s2, final String s3, final String s4) throws Open4GLException, ConnectException, SystemErrorException, IOException {
        if (RunTimeProperties.getDynamicApiVersion() != 5) {
            throw new Open4GLException(7665970990714723421L, null);
        }
        final Connection connection = new Connection(s, s2, s3, s4);
        this.m_SDOAppObjectImpl = new SDOAppObjectImpl("proxies.SDOApp2", connection, RunTimeProperties.tracer);
        connection.releaseConnection();
    }
    
    public SDOAppObject(final String s, final String s2, final String s3) throws Open4GLException, ConnectException, SystemErrorException, IOException {
        if (RunTimeProperties.getDynamicApiVersion() != 5) {
            throw new Open4GLException(7665970990714723421L, null);
        }
        final Connection connection = new Connection("proxies.SDOApp2", s, s2, s3);
        this.m_SDOAppObjectImpl = new SDOAppObjectImpl("proxies.SDOApp2", connection, RunTimeProperties.tracer);
        connection.releaseConnection();
    }
    
    public SDOAppObject() throws Open4GLException, ConnectException, SystemErrorException, IOException {
        if (RunTimeProperties.getDynamicApiVersion() != 5) {
            throw new Open4GLException(7665970990714723421L, null);
        }
        final Connection connection = new Connection("proxies.SDOApp2", null, null, null);
        this.m_SDOAppObjectImpl = new SDOAppObjectImpl("proxies.SDOApp2", connection, RunTimeProperties.tracer);
        connection.releaseConnection();
    }
    
    public SDOResultSet _createSDOResultSet(final String s) throws Open4GLException, ProSQLException {
        return this.m_SDOAppObjectImpl._createSDOResultSet(s, null, null, null);
    }
    
    public SDOResultSet _createSDOResultSet(final String s, final String s2, final String s3) throws Open4GLException, ProSQLException {
        return this.m_SDOAppObjectImpl._createSDOResultSet(s, s2, s3, null);
    }
    
    public SDOResultSet _createSDOResultSet(final String s, final String s2, final String s3, final SDOParameters sdoParameters) throws Open4GLException, ProSQLException {
        return this.m_SDOAppObjectImpl._createSDOResultSet(s, s2, s3, sdoParameters);
    }
    
    public SDOInterface _createSDOProcObject(final String s) throws Open4GLException {
        return this.m_SDOAppObjectImpl._createSDOProcObject(s);
    }
    
    public void _release() throws Open4GLException, SystemErrorException {
        this.m_SDOAppObjectImpl._release();
    }
    
    public Object _getConnectionId() throws Open4GLException {
        return this.m_SDOAppObjectImpl._getConnectionId();
    }
    
    public boolean _isStreaming() throws Open4GLException {
        return this.m_SDOAppObjectImpl._isStreaming();
    }
    
    public Object _getRequestId() throws Open4GLException {
        return this.m_SDOAppObjectImpl._getRequestId();
    }
    
    public Object _getSSLSubjectName() throws Open4GLException {
        return this.m_SDOAppObjectImpl._getSSLSubjectName();
    }
    
    public void _cancelAllRequests() throws Open4GLException {
        this.m_SDOAppObjectImpl._cancelAllRequests();
    }
    
    public String _getProcReturnString() throws Open4GLException {
        return this.m_SDOAppObjectImpl._getProcReturnString();
    }
}
