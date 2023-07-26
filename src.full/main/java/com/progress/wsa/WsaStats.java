// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa;

public class WsaStats extends RuntimeStats
{
    public static final String[] m_wsaStatisticNames;
    public static final int WSDL_REQUESTS = 0;
    public static final int SOAP_REQUESTS = 1;
    public static final int HTTP_REQUESTS = 2;
    public static final int AUTH_ERRORS = 3;
    public static final int URL_NOT_FOUND_ERRORS = 4;
    public static final int METHOD_NOT_ALLOWED_ERRORS = 5;
    public static final int HTTP_REQUEST_ERRORS = 6;
    public static final int WSA_DISABLED_ERRORS = 7;
    public static final int SOAP_PROCESSOR_ERRORS = 8;
    public static final int SOAP_FAULTS = 9;
    public static final int SERVICE_DISABLED = 10;
    public static final int ACTIVE_REQUESTS = 11;
    
    public WsaStats() {
        super("wsa-stats", WsaStats.m_wsaStatisticNames);
    }
    
    static {
        m_wsaStatisticNames = new String[] { "wsdl-requests", "soap-requests", "http-requests", "auth-errors", "url-not-found-errors", "method-not-allowed-errors", "http-request-errors", "wsa-disabled-errors", "soap-processor-errors", "soap-faults", "service_disabled", "active-requests" };
    }
}
