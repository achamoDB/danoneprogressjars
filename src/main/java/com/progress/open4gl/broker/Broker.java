// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.broker;

import com.progress.open4gl.Open4GLException;
import com.progress.open4gl.ConnectException;

public interface Broker
{
    public static final int EOF = -1;
    public static final int ABNORMAL_EOF = -2;
    public static final int CLOSE_OK = 0;
    public static final int CLOSE_ERROR = 1;
    public static final int CLOSE_WRITE_GOOD = 1;
    public static final int CLOSE_WRITE_BAD = 2;
    public static final int CLOSE_WRITE_STOP = 3;
    
    void connect(final String p0, final String p1, final String p2, final String p3, final String p4) throws BrokerException, ConnectException;
    
    void xid(final String p0, final String p1, final String p2, final String p3, final String p4) throws BrokerException, ConnectException;
    
    void disconnect() throws BrokerException;
    
    void unconditionalDisconnect();
    
    boolean isConnected();
    
    void allocate(final String p0) throws BrokerException;
    
    void deallocate() throws BrokerException;
    
    void write(final byte[] p0, final int p1, final int p2) throws BrokerException;
    
    void prepareToReceive(final int p0) throws BrokerException;
    
    int read(final byte[] p0) throws BrokerException;
    
    void setStop() throws BrokerException;
    
    String getConnectionID() throws BrokerException;
    
    String getRequestID() throws BrokerException;
    
    String getSSLSubjectName() throws BrokerException;
    
    String getConnectionReturnValue() throws BrokerException;
    
    boolean isReceiving();
    
    boolean isStopping();
    
    String getCapability(final short p0);
    
    int getASKVersion();
    
    boolean getServerASKEnabled();
    
    boolean getClientASKEnabled();
    
    int getClientASKActivityTimeout();
    
    int getClientASKResponseTimeout();
    
    void manageASKPingRequest() throws BrokerException, Open4GLException;
    
    void setSessionID(final String p0);
}
