// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.aia;

public class AiaDisplayInfoDesc
{
    private String m_dataValue;
    private int m_alignmentPref;
    
    public AiaDisplayInfoDesc() {
        this.m_dataValue = null;
        this.m_alignmentPref = 0;
        this.m_dataValue = null;
        this.m_alignmentPref = 0;
    }
    
    public AiaDisplayInfoDesc(final String data, final int alignmentPref) {
        this.m_dataValue = null;
        this.m_alignmentPref = 0;
        this.setData(data);
        this.setAlignmentPref(alignmentPref);
    }
    
    public String getData() {
        return this.m_dataValue;
    }
    
    public int getAlignmentPref() {
        return this.m_alignmentPref;
    }
    
    public void setData(final String dataValue) {
        if (dataValue != null) {
            this.m_dataValue = dataValue;
        }
        else {
            this.m_dataValue = "";
        }
    }
    
    public void setAlignmentPref(final int alignmentPref) {
        this.m_alignmentPref = alignmentPref;
    }
    
    public void setContent(final String data, final int alignmentPref) {
        this.setData(data);
        this.setAlignmentPref(alignmentPref);
    }
}
