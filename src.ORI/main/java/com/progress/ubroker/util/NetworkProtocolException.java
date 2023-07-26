// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.util;

import com.progress.message.jcMsg;
import com.progress.common.exception.ProException;

public class NetworkProtocolException extends ProException implements jcMsg
{
    private static final String[] m_idMsgs;
    public static final int BASE_MSG = 0;
    public static final int UNSUPPORTED_PROTOCOL = 1;
    public static final int INVALID_PROTOCOL_CONFIGURATION = 2;
    public static final int CANNOT_FIND_PROTOCOL_LIBRARY = 3;
    public static final int PROTOCOL_CONNECTION_FAILED = 4;
    public static final int SERVER_AUTHENTICATION_FAILED = 5;
    public static final int CLIENT_AUTHENTICATION_FAILED = 6;
    public static final int NETWORK_PROTOCOL_TIMEOUT = 7;
    public static final int NETWORK_CONNECTION_ABORTED = 8;
    public static final int AUTHENTICATION_REJECTED = 9;
    public static final int NETWORK_PROTOCOL_ERROR = 10;
    public static final int PROTOCOL_OPTION_ERROR = 11;
    public static final int AIA_REPORTED_ERROR = 12;
    public static final int PROXY_AUTHENTICATION_FAILED = 13;
    protected static final long[] m_jcMsgTable;
    private static final long MAX_ID = 14L;
    private int m_exceptionId;
    private Object[] m_tokens;
    private long m_jcMsgId;
    
    public NetworkProtocolException() {
        this.m_exceptionId = 0;
        this.m_tokens = null;
        this.m_jcMsgId = 0L;
    }
    
    public NetworkProtocolException(final String s, final String s2) {
        super(7665970990714726146L, new Object[] { s, s2 });
        this.m_exceptionId = 0;
        this.m_tokens = null;
        this.m_jcMsgId = 0L;
        this.m_exceptionId = 0;
        this.m_jcMsgId = 7665970990714726146L;
        this.m_tokens = new Object[] { s, s2 };
    }
    
    public NetworkProtocolException(final long n, final String s, final String s2) {
        super(NetworkProtocolException.m_jcMsgTable[(14L > n) ? ((int)n) : 0], new Object[] { s, s2 });
        this.m_exceptionId = 0;
        this.m_tokens = null;
        this.m_jcMsgId = 0L;
        this.m_exceptionId = (int)n;
        this.m_jcMsgId = NetworkProtocolException.m_jcMsgTable[(14L > n) ? ((int)n) : 0];
        this.m_tokens = new Object[] { s, s2 };
    }
    
    public NetworkProtocolException(final long n, final Object[] tokens) {
        super(NetworkProtocolException.m_jcMsgTable[(14L > n) ? ((int)n) : 0], tokens);
        this.m_exceptionId = 0;
        this.m_tokens = null;
        this.m_jcMsgId = 0L;
        this.m_exceptionId = (int)n;
        this.m_jcMsgId = NetworkProtocolException.m_jcMsgTable[(14L > n) ? ((int)n) : 0];
        this.m_tokens = tokens;
    }
    
    public int exceptionId() {
        return this.m_exceptionId;
    }
    
    public long jcMsgId() {
        return this.m_jcMsgId;
    }
    
    public Object[] messageTokens() {
        return this.m_tokens;
    }
    
    public String exceptionMessageFormat(final long n) {
        return NetworkProtocolException.m_idMsgs[(14L > n) ? ((int)n) : 14];
    }
    
    static {
        m_idMsgs = new String[] { "An unexpected %s protocol error occured: %s", "The %s %s network functionality is not supported.", "Invalid %s protocol configuration: %s", "Cannot find the support library for the %s protocol.", "The %s protocol connection failed: %s", "%s server authentication failed: %s", "%s client authentication failed: %s", "The %s network protocol connection to %s timed out.", "The %s connection to %s was aborted: %s", "The %s %s authenticaiton was rejected: %s", "A %s network protocol error occured: %s", "A %s option parameter failed: %s", "An AIA error was returned thru %s: %s", "%s proxy authentication failed: %s", "" };
        m_jcMsgTable = new long[] { 7665970990714726146L, 7665970990714726147L, 7665970990714726148L, 7665970990714726149L, 7665970990714726150L, 7665970990714726151L, 7665970990714726152L, 7665970990714726153L, 7665970990714726154L, 7665970990714726155L, 7665970990714726156L, 7665970990714726157L, 7665970990714726158L, 7665970990714726159L };
    }
}
