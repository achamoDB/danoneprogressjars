// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.broker;

import com.progress.common.exception.ProException;

public class BrokerException extends ProException implements BrokerExceptionCodes
{
    private int errorNum;
    public static final int ABNORMAL_EOF = -2;
    public static final int SERVER_STOP = -5;
    public static final int COMM_ERROR = -3;
    public static final int SYSTEM_ERROR = -4;
    public static final int NO_AVAILABLE_SERVERS = -7;
    public static final int SEND_DATA_ERROR = -8;
    public static final int RECV_DATA_ERROR = -9;
    public static final int CODE_GENERAL_ERROR = 0;
    public static final int CODE_BAD_URL = 1;
    public static final int CODE_INVALID_STATE = 2;
    public static final int CODE_ERROR_STRING = 3;
    public static final int CODE_CONNECT_FAILURE = 4;
    public static final int CODE_CLIENT_STOP = 5;
    public static final int CODE_SERVER_STOP = 6;
    public static final int CODE_PROTOCOL_ERROR = 7;
    public static final int CODE_SENDDATA_ERROR = 8;
    public static final int CODE_RECVDATA_ERROR = 9;
    
    public BrokerException() {
        this(0, null);
    }
    
    public BrokerException(final int errorNum, final long n, final Object[] array) {
        super(n, array);
        this.errorNum = errorNum;
    }
    
    public BrokerException(final int errorNum, final long n, final Object[] array, final Throwable t) {
        super(n, array, t);
        this.errorNum = errorNum;
    }
    
    public BrokerException(final int errorNum, final String s) {
        super(BrokerExceptionCodes.bsMSG[0], new Object[] { s });
        this.errorNum = errorNum;
    }
    
    public BrokerException(final String s) {
        super(BrokerExceptionCodes.bsMSG[0], new Object[] { s });
        this.errorNum = -4;
    }
    
    public int getErrorNum() {
        return this.errorNum;
    }
    
    public String getErrorMessage() {
        return this.getMessage();
    }
}
