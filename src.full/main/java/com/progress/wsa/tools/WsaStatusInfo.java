// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa.tools;

import java.util.Date;
import com.progress.wsa.WsaStats;

public class WsaStatusInfo
{
    protected int m_wsdlRequests;
    protected int m_soapRequests;
    protected int m_httpRequests;
    protected int m_authErrors;
    protected int m_urlNotFoundErrors;
    protected int m_methodNotAllowedErrors;
    protected int m_httpRequestErrors;
    protected int m_wsaDisabledErrors;
    protected int m_soapProcErrors;
    protected int m_soapFaults;
    protected int m_serviceDisabled;
    protected int m_activeRequests;
    protected long m_creationTime;
    protected long m_startTime;
    
    public WsaStatusInfo() {
        this.m_wsdlRequests = 0;
        this.m_soapRequests = 0;
        this.m_httpRequests = 0;
        this.m_authErrors = 0;
        this.m_urlNotFoundErrors = 0;
        this.m_methodNotAllowedErrors = 0;
        this.m_httpRequestErrors = 0;
        this.m_wsaDisabledErrors = 0;
        this.m_soapProcErrors = 0;
        this.m_soapFaults = 0;
        this.m_serviceDisabled = 0;
        this.m_activeRequests = 0;
        this.m_creationTime = 0L;
        this.m_startTime = 0L;
    }
    
    public WsaStatusInfo(final WsaStats wsaStats) {
        this.m_wsdlRequests = 0;
        this.m_soapRequests = 0;
        this.m_httpRequests = 0;
        this.m_authErrors = 0;
        this.m_urlNotFoundErrors = 0;
        this.m_methodNotAllowedErrors = 0;
        this.m_httpRequestErrors = 0;
        this.m_wsaDisabledErrors = 0;
        this.m_soapProcErrors = 0;
        this.m_soapFaults = 0;
        this.m_serviceDisabled = 0;
        this.m_activeRequests = 0;
        this.m_creationTime = 0L;
        this.m_startTime = 0L;
        this.m_wsdlRequests = wsaStats.getCounter(0);
        this.m_soapRequests = wsaStats.getCounter(1);
        this.m_httpRequests = wsaStats.getCounter(2);
        this.m_authErrors = wsaStats.getCounter(3);
        this.m_urlNotFoundErrors = wsaStats.getCounter(4);
        this.m_methodNotAllowedErrors = wsaStats.getCounter(5);
        this.m_httpRequestErrors = wsaStats.getCounter(6);
        this.m_wsaDisabledErrors = wsaStats.getCounter(7);
        this.m_soapProcErrors = wsaStats.getCounter(8);
        this.m_soapFaults = wsaStats.getCounter(9);
        this.m_serviceDisabled = wsaStats.getCounter(10);
        this.m_activeRequests = wsaStats.getCounter(11);
        this.m_creationTime = wsaStats.creationTime();
        this.m_startTime = wsaStats.lastResetTime();
    }
    
    public int getWSDLRequests() {
        return this.m_wsdlRequests;
    }
    
    public void setWSDLRequests(final int wsdlRequests) {
        this.m_wsdlRequests = wsdlRequests;
    }
    
    public int getSOAPRequests() {
        return this.m_soapRequests;
    }
    
    public void setSOAPRequests(final int soapRequests) {
        this.m_soapRequests = soapRequests;
    }
    
    public int getHTTPRequests() {
        return this.m_httpRequests;
    }
    
    public void setHTTPRequests(final int httpRequests) {
        this.m_httpRequests = httpRequests;
    }
    
    public int getAuthErrors() {
        return this.m_authErrors;
    }
    
    public void setAuthErrors(final int authErrors) {
        this.m_authErrors = authErrors;
    }
    
    public int getURLNotFoundErrors() {
        return this.m_urlNotFoundErrors;
    }
    
    public void setURLNotFoundErrors(final int urlNotFoundErrors) {
        this.m_urlNotFoundErrors = urlNotFoundErrors;
    }
    
    public int getMethodNotAllowedErrors() {
        return this.m_methodNotAllowedErrors;
    }
    
    public void setMethodNotAllowedErrors(final int methodNotAllowedErrors) {
        this.m_methodNotAllowedErrors = methodNotAllowedErrors;
    }
    
    public int getHTTPRequestErrors() {
        return this.m_httpRequestErrors;
    }
    
    public void setHTTPRequestErrors(final int httpRequestErrors) {
        this.m_httpRequestErrors = httpRequestErrors;
    }
    
    public int getWSADisabledErrors() {
        return this.m_wsaDisabledErrors;
    }
    
    public void setWSADisabledErrors(final int wsaDisabledErrors) {
        this.m_wsaDisabledErrors = wsaDisabledErrors;
    }
    
    public int getSOAPProcErrors() {
        return this.m_soapProcErrors;
    }
    
    public void setSOAPProcErrors(final int soapProcErrors) {
        this.m_soapProcErrors = soapProcErrors;
    }
    
    public int getSOAPFaults() {
        return this.m_soapFaults;
    }
    
    public void setSOAPFaults(final int soapFaults) {
        this.m_soapFaults = soapFaults;
    }
    
    public int getServiceDisabled() {
        return this.m_serviceDisabled;
    }
    
    public void setServiceDisabled(final int serviceDisabled) {
        this.m_serviceDisabled = serviceDisabled;
    }
    
    public int getActiveRequests() {
        return this.m_activeRequests;
    }
    
    public void setActiveRequests(final int activeRequests) {
        this.m_activeRequests = activeRequests;
    }
    
    public Date getCreationTime() {
        return new Date(this.m_creationTime);
    }
    
    public void setCreationTime(final Date date) {
        this.m_creationTime = date.getTime();
    }
    
    public Date getStartTime() {
        return new Date(this.m_startTime);
    }
    
    public void setStartTime(final Date date) {
        this.m_startTime = date.getTime();
    }
    
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        final int i = this.m_authErrors + this.m_urlNotFoundErrors + this.m_methodNotAllowedErrors + this.m_httpRequestErrors + this.m_wsaDisabledErrors + this.m_soapProcErrors;
        sb.append("   Creation Time: " + this.getCreationTime());
        sb.append("\n   Start Time:    " + this.getStartTime() + "\n");
        sb.append("\n   Number of SOAP Requests:     " + this.m_soapRequests);
        sb.append("\n   Number of Active Requests:   " + this.m_activeRequests);
        sb.append("\n   Number of HTTP Requests:     " + this.m_httpRequests);
        sb.append("\n   Number of WSDL Requests:     " + this.m_wsdlRequests);
        sb.append("\n   Number of SOAP Faults:       " + this.m_soapFaults);
        sb.append("\n   Number of Services disabled: " + this.m_serviceDisabled);
        sb.append("\n   Number of Errors:            " + i);
        if (i > 0) {
            sb.append("\n    Number of SOAP processing Errors:    " + this.m_soapProcErrors);
            sb.append("\n    Number of Authentication Errors:     " + this.m_authErrors);
            sb.append("\n    Number of URL not found Errors:      " + this.m_urlNotFoundErrors);
            sb.append("\n    Number of Method not allowed Errors: " + this.m_methodNotAllowedErrors);
            sb.append("\n    Number of HTTP request Errors:       " + this.m_httpRequestErrors);
            sb.append("\n    Number of WSA disabled Errors:       " + this.m_wsaDisabledErrors);
        }
        return sb.toString();
    }
    
    public void copyContentFrom(final WsaStatusInfo wsaStatusInfo) {
        this.m_wsdlRequests = wsaStatusInfo.getWSDLRequests();
        this.m_soapRequests = wsaStatusInfo.getSOAPRequests();
        this.m_httpRequests = wsaStatusInfo.getHTTPRequests();
        this.m_authErrors = wsaStatusInfo.getAuthErrors();
        this.m_urlNotFoundErrors = wsaStatusInfo.getURLNotFoundErrors();
        this.m_methodNotAllowedErrors = wsaStatusInfo.getMethodNotAllowedErrors();
        this.m_httpRequestErrors = wsaStatusInfo.getHTTPRequestErrors();
        this.m_wsaDisabledErrors = wsaStatusInfo.getWSADisabledErrors();
        this.m_soapProcErrors = wsaStatusInfo.getSOAPProcErrors();
        this.m_soapFaults = wsaStatusInfo.getSOAPFaults();
        this.m_serviceDisabled = wsaStatusInfo.getServiceDisabled();
        this.m_activeRequests = wsaStatusInfo.getActiveRequests();
        this.m_creationTime = wsaStatusInfo.getCreationTime().getTime();
        this.m_startTime = wsaStatusInfo.getStartTime().getTime();
    }
}
