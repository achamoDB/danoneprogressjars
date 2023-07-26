// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

import com.progress.common.exception.ProException;
import com.progress.open4gl.RunTimeProperties;
import com.progress.open4gl.ConnectException;
import com.progress.open4gl.broker.BrokerException;
import com.progress.open4gl.SystemErrorException;
import com.progress.open4gl.ProDataException;
import com.progress.open4gl.ProSQLException;
import com.progress.open4gl.OutputSetException;
import com.progress.open4gl.Open4GLException;
import java.sql.SQLException;
import java.io.IOException;

class ExceptionConverter
{
    static IOException convertBrokerException(final Exception ex) {
        printTrace(ex);
        return new IOException(ex.getMessage());
    }
    
    static void convertIOException(final IOException ex) throws ClientException {
        printTrace(ex);
        final Object[] array = { ex.getMessage() };
        if (ex instanceof EndOfStreamException) {
            throw new ClientException(6, 2L, array);
        }
        throw new ClientException(1, 2L, array);
    }
    
    static void convertSQLException(final SQLException ex) throws ClientException {
        printTrace(ex);
        final String message = ex.getMessage();
        final String sqlState = ex.getSQLState();
        final int errorCode = ex.getErrorCode();
        String s = message;
        if (sqlState != null) {
            s = s + " State: " + sqlState;
        }
        if (errorCode != 0) {
            s = s + ". Vendor code: " + new Integer(errorCode).toString();
        }
        throw new ClientException(3, 3L, new Object[] { s });
    }
    
    static void convertException(final Exception ex) throws ClientException {
        convertException0(ex, null);
    }
    
    static void convertException(final Exception ex, final Open4GLException ex2) throws ClientException {
        convertException0(ex, ex2);
    }
    
    private static void convertException0(final Exception ex, final Open4GLException ex2) throws ClientException {
        printTrace(ex);
        String str = " " + ex.getClass().getName() + ": " + ex.getMessage();
        if (ex2 != null) {
            str = str + " " + ex2.getMessage();
        }
        throw new ClientException(3, 3L, new Object[] { str });
    }
    
    static void convertToProSQLException(final ClientException ex) throws ProSQLException {
        printTrace(ex);
        final int reason = ex.getReason();
        final long messageId = ex.getMessageId();
        final Object[] args = ex.getArgs();
        String s = null;
        switch (reason) {
            case 1: {
                s = "08S01";
                break;
            }
            default: {
                s = "S1000";
                break;
            }
        }
        throw new ProSQLException(new OutputSetException(messageId, args), s);
    }
    
    static void convertToProDataException(final ClientException ex) throws ProDataException {
        printTrace(ex);
        throw new ProDataException(ex.getMessageId(), ex.getArgs());
    }
    
    static ClientException convertFromProSQLException(final ProSQLException ex) {
        printTrace(ex);
        return new ClientException(18L, new Object[] { ex.getMessage() });
    }
    
    static Open4GLException convertToOpen4GLException(final ClientException ex) {
        printTrace(ex);
        if (ex == null) {
            return new Open4GLException();
        }
        return new Open4GLException(ex.getMessageId(), ex.getArgs());
    }
    
    static SystemErrorException convertToSystemErrorException(final ClientException ex) {
        printTrace(ex);
        return new SystemErrorException(ex.getMessageId(), ex.getArgs());
    }
    
    static ConnectException convertToConnectException(final BrokerException ex) {
        printTrace(ex);
        return new ConnectException(30L, new Object[] { ex.getMessage() });
    }
    
    static SystemErrorException convertToSystemErrorException(final BrokerException ex) {
        printTrace(ex);
        return new SystemErrorException(36L, new Object[] { ex.getMessage() });
    }
    
    private static void printTrace(final Exception ex) {
        RunTimeProperties.tracer.print(ex, 1);
    }
    
    static String getProgressMessage(final int n) {
        return new Open4GLException(n, null).getMessage();
    }
    
    static Open4GLException convertProExceptionToOpen4GLException(final ProException ex) {
        printTrace(ex);
        return new Open4GLException(ex.getMessageId(), new Object[] { " " + ex.getClass().getName() + ": " + ex.getMessage() });
    }
}
