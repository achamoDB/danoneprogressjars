// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools;

class dSTableObj
{
    private String m_value;
    private boolean m_checked;
    private String m_key;
    
    dSTableObj(final String value, final String key) {
        this.m_value = null;
        this.m_key = null;
        this.m_value = value;
        this.m_key = key;
        this.m_checked = false;
    }
    
    public boolean isChecked() {
        return this.m_checked;
    }
    
    public void setChecked(final boolean checked) {
        this.m_checked = checked;
    }
    
    public boolean compareValue(final String anObject) {
        boolean b = false;
        if (this.m_value != null && this.m_value.equals(anObject)) {
            b = true;
        }
        return b;
    }
    
    public String getValue() {
        return this.m_value;
    }
    
    public String getKey() {
        return this.m_key;
    }
}
