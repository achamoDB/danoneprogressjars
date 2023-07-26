// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa.admin;

import java.io.Serializable;

public class PingResponse implements Serializable
{
    private String NOT_RUNNING;
    private String RUNNING;
    private String ENABLED;
    private String DISABLED;
    private String m_status;
    private String m_wsaName;
    private int m_adminEnabled;
    private int m_webAppEnabled;
    private int m_wsdlEnabled;
    private boolean m_isRunning;
    
    public boolean isRunning() {
        return this.m_isRunning;
    }
    
    public void isRunning(final boolean isRunning) {
        this.m_isRunning = isRunning;
    }
    
    public String getStatus() {
        return this.m_status;
    }
    
    public void setStatus(final String status) {
        this.m_status = status;
    }
    
    public String getWsaName() {
        return this.m_wsaName;
    }
    
    public void setWsaName(final String wsaName) {
        this.m_wsaName = wsaName;
    }
    
    public int getAdminEnabled() {
        return this.m_adminEnabled;
    }
    
    public void setAdminEnabled(final int adminEnabled) {
        this.m_adminEnabled = adminEnabled;
    }
    
    public int getWebAppEnabled() {
        return this.m_webAppEnabled;
    }
    
    public void setWebAppEnabled(final int webAppEnabled) {
        this.m_webAppEnabled = webAppEnabled;
    }
    
    public int getWSDLEnabled() {
        return this.m_wsdlEnabled;
    }
    
    public void setWSDLEnabled(final int wsdlEnabled) {
        this.m_wsdlEnabled = wsdlEnabled;
    }
    
    public PingResponse() {
        this.NOT_RUNNING = "Not Running";
        this.RUNNING = "Running";
        this.ENABLED = "Enabled";
        this.DISABLED = "Disabled";
        this.m_status = this.NOT_RUNNING;
        this.m_wsaName = null;
        this.m_adminEnabled = 0;
        this.m_webAppEnabled = 0;
        this.m_wsdlEnabled = 0;
        this.m_isRunning = false;
    }
    
    public void convertMsg(final String s) {
        final int n = s.indexOf("Status:") + 7;
        final String substring = s.substring(n, s.indexOf("<", n));
        final int index = substring.indexOf(":");
        final int lastIndex = substring.lastIndexOf(":");
        this.m_wsaName = substring.substring(0, index);
        this.m_status = substring.substring(index + 1, lastIndex);
        final String substring2 = substring.substring(lastIndex + 1);
        this.m_adminEnabled = Integer.valueOf(substring2.substring(0, 1));
        this.m_webAppEnabled = Integer.valueOf(substring2.substring(1, 2));
        this.m_wsdlEnabled = Integer.valueOf(substring2.substring(2, 3));
    }
    
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        this.m_isRunning = (this.m_status != null && this.m_status.equals("OK"));
        final String str = this.m_isRunning ? this.RUNNING : this.NOT_RUNNING;
        final String str2 = (this.m_adminEnabled == 1) ? this.ENABLED : this.DISABLED;
        final String str3 = (this.m_webAppEnabled == 1) ? this.ENABLED : this.DISABLED;
        final String str4 = (this.m_wsdlEnabled == 1) ? this.ENABLED : this.DISABLED;
        sb.append("Web Services Adapter");
        if (this.m_wsaName != null) {
            sb.append(" " + this.m_wsaName);
        }
        sb.append(" is " + str);
        if (this.m_isRunning) {
            sb.append("\n   WSA Administration: " + str2);
            sb.append("\n   Web Services      : " + str3);
            sb.append("\n   WSDL Retrieval    : " + str4);
        }
        return sb.toString();
    }
    
    public String getStatusDescription() {
        return this.toString();
    }
}
