// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.util;

public class UBPreferenceProperties implements IPropConst
{
    public int m_toolConnectSvcRetry;
    public int m_toolGetSvcStatusRetry;
    public int m_toolPingSvcRetry;
    public int m_toolShutdownSvcConfirmRetry;
    public int m_toolConnectSvcRetryInterval;
    public int m_toolShutdownSvcConfirmRetryInterval;
    public int m_admSrvrRegisteredRetry;
    public int m_admSrvrRegisteredRetryInterval;
    public boolean m_useDefault;
    
    public UBPreferenceProperties(final PropMgrUtils propMgrUtils) {
        this.m_useDefault = false;
        this.getAllPreferences(propMgrUtils);
    }
    
    public void refetchAll(final PropMgrUtils propMgrUtils) {
        this.getAllPreferences(propMgrUtils);
    }
    
    public int getAdmSrvrRegisteredRetry() {
        return this.m_admSrvrRegisteredRetry;
    }
    
    public int getAdmSrvrRegisteredIntervalRetry() {
        return this.m_admSrvrRegisteredRetryInterval;
    }
    
    private void getAllPreferences(final PropMgrUtils propMgrUtils) {
        this.m_useDefault = false;
        try {
            this.m_toolGetSvcStatusRetry = propMgrUtils.getPreferenceIntProperty("toolGetSvcStatusRetry");
        }
        catch (Exception ex) {
            this.m_useDefault = true;
            this.m_toolGetSvcStatusRetry = 12;
        }
        try {
            this.m_toolPingSvcRetry = propMgrUtils.getPreferenceIntProperty("toolPingSvcRetry");
        }
        catch (Exception ex2) {
            this.m_useDefault = true;
            this.m_toolPingSvcRetry = 4;
        }
        try {
            this.m_toolShutdownSvcConfirmRetry = propMgrUtils.getPreferenceIntProperty("toolShutdownSvcConfirmRetry");
        }
        catch (Exception ex3) {
            this.m_useDefault = true;
            this.m_toolShutdownSvcConfirmRetry = 10;
        }
        try {
            this.m_toolShutdownSvcConfirmRetryInterval = propMgrUtils.getPreferenceIntProperty("toolShutdownSvcConfirmRetryInterval");
        }
        catch (Exception ex4) {
            this.m_useDefault = true;
            this.m_toolShutdownSvcConfirmRetryInterval = 3000;
        }
        try {
            this.m_toolConnectSvcRetryInterval = propMgrUtils.getPreferenceIntProperty("toolConnectSvcRetryInterval");
        }
        catch (Exception ex5) {
            this.m_useDefault = true;
            this.m_toolConnectSvcRetryInterval = 3000;
        }
        try {
            this.m_toolConnectSvcRetry = propMgrUtils.getPreferenceIntProperty("toolConnectSvcRetry");
        }
        catch (Exception ex6) {
            this.m_useDefault = true;
            this.m_toolConnectSvcRetry = 20;
        }
        try {
            this.m_admSrvrRegisteredRetry = propMgrUtils.getPreferenceIntProperty("admSrvrRegisteredRetry");
        }
        catch (Exception ex7) {
            this.m_useDefault = true;
            this.m_admSrvrRegisteredRetry = 6;
        }
        try {
            this.m_admSrvrRegisteredRetryInterval = propMgrUtils.getPreferenceIntProperty("admSrvrRegisteredRetryInterval");
        }
        catch (Exception ex8) {
            this.m_useDefault = true;
            this.m_admSrvrRegisteredRetryInterval = 3000;
        }
    }
}
