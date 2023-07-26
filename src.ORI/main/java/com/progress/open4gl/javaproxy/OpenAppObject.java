// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.javaproxy;

import com.progress.open4gl.RunTime4GLException;
import com.progress.open4gl.SDOInterface;
import com.progress.open4gl.ProSQLException;
import com.progress.open4gl.SDOParameters;
import com.progress.open4gl.SDOResultSet;
import java.io.IOException;
import com.progress.open4gl.SystemErrorException;
import com.progress.open4gl.ConnectException;
import com.progress.common.ehnlog.IAppLogger;
import com.progress.open4gl.dynamicapi.IPoolProps;
import com.progress.open4gl.Open4GLException;
import com.progress.open4gl.RunTimeProperties;
import com.progress.open4gl.SDOFactory;

public class OpenAppObject implements SDOFactory
{
    protected static final long m_wrongProxyVer = 7665970990714723421L;
    private static final int PROXY_VER = 5;
    protected OpenAppObjectImpl m_dynAOImpl;
    
    public OpenAppObject(final Connection connection, final String str) throws Open4GLException, ConnectException, SystemErrorException, IOException {
        if (RunTimeProperties.getDynamicApiVersion() != 5) {
            throw new Open4GLException(7665970990714723421L, null);
        }
        final String url = connection.getUrl();
        if (url == null || url.compareTo("") == 0) {
            connection.setUrl("AppServer://localhost:5162/" + str);
        }
        this.m_dynAOImpl = new OpenAppObjectImpl(str, connection, RunTimeProperties.tracer);
    }
    
    public OpenAppObject(final Connection connection, final String str, final IAppLogger appLogger) throws Open4GLException, ConnectException, SystemErrorException, IOException {
        if (RunTimeProperties.getDynamicApiVersion() != 5) {
            throw new Open4GLException(7665970990714723421L, null);
        }
        final String url = connection.getUrl();
        if (url == null || url.compareTo("") == 0) {
            connection.setUrl("AppServer://localhost:5162/" + str);
        }
        this.m_dynAOImpl = new OpenAppObjectImpl(str, connection, appLogger);
    }
    
    public OpenAppObject(String string, final String s, final String s2, final String s3, final String str) throws Open4GLException, ConnectException, SystemErrorException, IOException {
        if (RunTimeProperties.getDynamicApiVersion() != 5) {
            throw new Open4GLException(7665970990714723421L, null);
        }
        if (string == null || string.compareTo("") == 0) {
            string = "AppServer://localhost:5162/" + str;
        }
        final Connection connection = new Connection(string, s, s2, s3);
        this.m_dynAOImpl = new OpenAppObjectImpl(str, connection, RunTimeProperties.tracer);
        connection.releaseConnection();
    }
    
    public OpenAppObject(final String s, final String s2, final String s3, final String str) throws Open4GLException, ConnectException, SystemErrorException, IOException {
        if (RunTimeProperties.getDynamicApiVersion() != 5) {
            throw new Open4GLException(7665970990714723421L, null);
        }
        final Connection connection = new Connection("AppServer://localhost:5162/" + str, s, s2, s3);
        this.m_dynAOImpl = new OpenAppObjectImpl(str, connection, RunTimeProperties.tracer);
        connection.releaseConnection();
    }
    
    public OpenAppObject(final String str) throws Open4GLException, ConnectException, SystemErrorException, IOException {
        if (RunTimeProperties.getDynamicApiVersion() != 5) {
            throw new Open4GLException(7665970990714723421L, null);
        }
        final Connection connection = new Connection("AppServer://localhost:5162/" + str, null, null, null);
        this.m_dynAOImpl = new OpenAppObjectImpl(str, connection, RunTimeProperties.tracer);
        connection.releaseConnection();
    }
    
    public void _release() throws Open4GLException, SystemErrorException {
        this.m_dynAOImpl._release();
    }
    
    public Object _getConnectionId() throws Open4GLException {
        return this.m_dynAOImpl._getConnectionId();
    }
    
    public Object _getRequestId() throws Open4GLException {
        return this.m_dynAOImpl._getRequestId();
    }
    
    public Object _getSSLSubjectName() throws Open4GLException {
        return this.m_dynAOImpl._getSSLSubjectName();
    }
    
    public boolean _isStreaming() throws Open4GLException {
        return this.m_dynAOImpl._isStreaming();
    }
    
    public void _cancelAllRequests() throws Open4GLException {
        this.m_dynAOImpl._cancelAllRequests();
    }
    
    public String _getProcReturnString() throws Open4GLException {
        return this.m_dynAOImpl._getProcReturnString();
    }
    
    public SDOResultSet _createSDOResultSet(final String s) throws Open4GLException, ProSQLException {
        return this.m_dynAOImpl._createSDOResultSet(s, null, null, null);
    }
    
    public SDOResultSet _createSDOResultSet(final String s, final String s2, final String s3) throws Open4GLException, ProSQLException {
        return this.m_dynAOImpl._createSDOResultSet(s, s2, s3, null);
    }
    
    public SDOResultSet _createSDOResultSet(final String s, final String s2, final String s3, final SDOParameters sdoParameters) throws Open4GLException, ProSQLException {
        return this.m_dynAOImpl._createSDOResultSet(s, s2, s3, sdoParameters);
    }
    
    public SDOInterface _createSDOProcObject(final String s) throws Open4GLException {
        return this.m_dynAOImpl._createSDOProcObject(s);
    }
    
    public void runProc(final String s, final ParamArray paramArray) throws Open4GLException, RunTime4GLException, SystemErrorException {
        this.m_dynAOImpl.runProc(s, paramArray);
    }
    
    public OpenProcObject createPO(final String s, final ParamArray paramArray) throws Open4GLException, RunTime4GLException, SystemErrorException {
        return new OpenProcObject(this.m_dynAOImpl, s, paramArray);
    }
    
    public OpenProcObject createPO(final String s, final int n) throws Open4GLException, RunTime4GLException, SystemErrorException {
        return new OpenProcObject(this.m_dynAOImpl, s, n);
    }
    
    public OpenProcObject createPO(final String s) throws Open4GLException, RunTime4GLException, SystemErrorException {
        return new OpenProcObject(this.m_dynAOImpl, s, 11);
    }
}
