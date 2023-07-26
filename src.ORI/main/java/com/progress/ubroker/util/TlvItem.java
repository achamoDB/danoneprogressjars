// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.util;

public class TlvItem
{
    private short m_tlvType;
    private String m_tlvData;
    private boolean m_persist;
    
    public TlvItem() {
        this.m_tlvType = 0;
        this.m_tlvData = null;
        this.m_persist = false;
    }
    
    public TlvItem(final short tlvType, final String tlvData, final boolean persist) {
        this.m_tlvType = 0;
        this.m_tlvData = null;
        this.m_persist = false;
        this.m_tlvType = tlvType;
        this.m_tlvData = tlvData;
        this.m_persist = persist;
    }
    
    public short getTlvType() {
        return this.m_tlvType;
    }
    
    public void setTlvType(final short tlvType) {
        this.m_tlvType = tlvType;
    }
    
    public String getTlvData() {
        return this.m_tlvData;
    }
    
    public void setTlvData(final String tlvData) {
        this.m_tlvData = tlvData;
    }
    
    public boolean isPersistent() {
        return this.m_persist;
    }
    
    public void setPersistent(final boolean persist) {
        this.m_persist = persist;
    }
    
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("TlvType = [");
        sb.append(ubMsg.DESC_TLVTYPES[this.m_tlvType - 1]);
        sb.append("] TlvData = [");
        sb.append(this.m_tlvData);
        sb.append("] Persistent = [");
        sb.append(this.m_persist);
        sb.append("]");
        return sb.toString();
    }
}
