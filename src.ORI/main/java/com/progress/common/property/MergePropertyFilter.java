// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.property;

import com.progress.common.util.UUID;

public class MergePropertyFilter implements PropertyManager.IPropertyValueFilter
{
    private static final String GUUID = "!{newValue:UUID}";
    private static final String CVALUE = "!{current-value}";
    private PropertyManager m_propMgr;
    
    public MergePropertyFilter(final PropertyManager propMgr) {
        this.m_propMgr = null;
        this.m_propMgr = propMgr;
    }
    
    private boolean testForExpansion(final String s) {
        for (int i = 0; i < s.length(); ++i) {
            if (s.charAt(i) == '!') {
                return true;
            }
        }
        return false;
    }
    
    public String filterValue(final String s, final String s2, final String s3) {
        if (!this.testForExpansion(s3)) {
            return s3;
        }
        String s4 = s3;
        for (int i = s4.toLowerCase().indexOf("!{newValue:UUID}".toLowerCase()); i >= 0; i = s4.toLowerCase().indexOf("!{newValue:UUID}".toLowerCase())) {
            s4 = s4.substring(0, i) + new UUID().toString() + s4.substring(i + "!{newValue:UUID}".length());
        }
        for (int j = s4.toLowerCase().indexOf("!{current-value}".toLowerCase()); j >= 0; j = s4.toLowerCase().indexOf("!{current-value}".toLowerCase())) {
            String property = null;
            if (this.m_propMgr != null) {
                property = this.m_propMgr.getProperty(s2, false);
            }
            if (property == null) {
                property = "";
            }
            s4 = s4.substring(0, j) + property + s4.substring(j + "!{current-value}".length());
        }
        return s4;
    }
}
