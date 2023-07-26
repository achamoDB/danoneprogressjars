// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.proxygen;

import com.progress.open4gl.wsdlgen.DWGenInfo;

public class WSInfo
{
    protected PGGenInfo m_pgGenInfo;
    protected DWGenInfo m_dwGenInfo;
    
    public WSInfo() {
        this.m_pgGenInfo = null;
        this.m_dwGenInfo = null;
    }
    
    public void setPGGenInfo(final PGGenInfo pgGenInfo) {
        this.m_pgGenInfo = pgGenInfo;
    }
    
    public PGGenInfo getPGGenInfo() {
        return this.m_pgGenInfo;
    }
    
    public void setDWGenInfo(final DWGenInfo dwGenInfo) {
        this.m_dwGenInfo = dwGenInfo;
    }
    
    public DWGenInfo getDWGenInfo() {
        return this.m_dwGenInfo;
    }
}
