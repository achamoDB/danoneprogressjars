// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa.tools;

import java.io.Serializable;

public class AdminStatus implements Serializable
{
    int m_status;
    String m_fault;
    
    public AdminStatus(final int status, final String fault) {
        this.m_status = status;
        this.m_fault = fault;
    }
    
    public AdminStatus(final int status) {
        this.m_status = status;
        this.m_fault = null;
    }
    
    public int getStatus() {
        return this.m_status;
    }
    
    public void setStatus(final int status) {
        this.m_status = status;
    }
    
    public String getFault() {
        return this.m_fault;
    }
    
    public void setFault(final String fault) {
        this.m_fault = fault;
    }
}
