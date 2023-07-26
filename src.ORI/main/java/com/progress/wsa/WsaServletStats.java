// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa;

public class WsaServletStats extends RuntimeStats
{
    public static final String[] m_names;
    public static final int HTTP_REQUESTS = 0;
    public static final int HTTP_REQUEST_ERRORS = 1;
    public static final int METHOD_NOT_ALLOWED_ERRORS = 2;
    public static final int SERVICE_DISABLED = 3;
    public static final int URL_NOT_FOUND_ERRORS = 4;
    
    public WsaServletStats() {
        super("wsa-servlet-stats", WsaServletStats.m_names);
    }
    
    static {
        m_names = new String[] { "http-requests", "http-request-errors", "method-not-allowed-errors", "service_disabled", "url-not-found-errors" };
    }
}
