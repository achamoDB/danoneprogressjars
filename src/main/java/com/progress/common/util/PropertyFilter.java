// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.util;

import com.progress.common.property.PropertyManager;

public class PropertyFilter implements PropertyManager.IPropertyValueFilter
{
    private Environment Env;
    private PropertyManager m_propMgr;
    private static final String SYSVALUE = "!{SystemProperty:";
    private static final String VALUEOF = "!{value-of:";
    
    public PropertyFilter() {
        this.Env = new Environment();
        this.m_propMgr = null;
    }
    
    public PropertyFilter(final PropertyManager propMgr) {
        this.Env = new Environment();
        this.m_propMgr = propMgr;
    }
    
    public String filterValue(final String anObject, final String anObject2, final String s) {
        String s2 = s;
        final String property = System.getProperty("os.name");
        if (property.startsWith("Windows")) {
            if (s.indexOf(36) < 0 && s.indexOf(33) < 0 && s.indexOf(63) < 0 && s.indexOf(64) < 0) {
                return s;
            }
        }
        else if (s.indexOf(36) < 0 && s.indexOf(33) < 0 && s.indexOf(63) < 0) {
            return s;
        }
        for (int i = s2.toLowerCase().indexOf("!{value-of:".toLowerCase()); i >= 0; i = s2.toLowerCase().indexOf("!{value-of:".toLowerCase())) {
            String trim = null;
            String property2 = null;
            final int index = s2.indexOf(125, i + "!{value-of:".length());
            final String trim2 = s2.substring(i + "!{value-of:".length(), index).trim();
            String trim3;
            if (trim2.lastIndexOf(46) < 0) {
                trim3 = trim2;
            }
            else {
                trim3 = trim2.substring(trim2.lastIndexOf(46) + 1).trim();
                trim = trim2.substring(0, trim2.lastIndexOf(46)).trim();
            }
            if (trim3 == null || trim3.length() == 0) {
                trim3 = anObject2;
            }
            if (trim == null || trim.length() == 0) {
                trim = anObject;
            }
            if (this.m_propMgr != null) {
                if (!trim3.equals(anObject2) || !trim.equals(anObject)) {
                    final String currentGroup = this.m_propMgr.getCurrentGroup();
                    this.m_propMgr.setCurrentGroup(trim);
                    property2 = this.m_propMgr.getProperty(trim3, false);
                    this.m_propMgr.setCurrentGroup(currentGroup);
                }
            }
            if (property2 == null) {
                property2 = "";
            }
            s2 = s2.substring(0, i) + property2 + s2.substring(index + 1, s2.length());
        }
        if (property.startsWith("Windows")) {
            for (int j = s2.indexOf("@{"); j >= 0; j = s2.indexOf("@{")) {
                final int index2 = s2.indexOf(125, j);
                String expandPropertyValueJNI = this.Env.expandPropertyValueJNI(s2.substring(j, index2 + 1));
                if (expandPropertyValueJNI == null) {
                    expandPropertyValueJNI = "";
                }
                s2 = s2.substring(0, j) + expandPropertyValueJNI + s2.substring(index2 + 1, s2.length());
            }
        }
        for (int k = s2.indexOf("${"); k >= 0; k = s2.indexOf("${")) {
            final int index3 = s2.indexOf(125, k);
            String expandPropertyValueJNI2 = this.Env.expandPropertyValueJNI(s2.substring(k, index3 + 1));
            if (expandPropertyValueJNI2 == null) {
                expandPropertyValueJNI2 = "";
            }
            s2 = s2.substring(0, k) + expandPropertyValueJNI2 + s2.substring(index3 + 1, s2.length());
        }
        if (s2.indexOf(36) >= 0) {
            final String s3 = s2;
            s2 = this.Env.expandPropertyValueJNI(s2);
            if (s2 == null || s2.length() == 0) {
                s2 = s3;
            }
        }
        for (int l = s2.toLowerCase().indexOf("!{SystemProperty:".toLowerCase()); l >= 0; l = s2.toLowerCase().indexOf("!{SystemProperty:".toLowerCase())) {
            String property3 = "";
            final int index4 = s2.indexOf(125, l + "!{SystemProperty:".length());
            final String trim4 = s2.substring(l + "!{SystemProperty:".length(), index4).trim();
            if (trim4.length() > 0) {
                property3 = System.getProperty(trim4);
            }
            s2 = s2.substring(0, l) + property3 + s2.substring(index4 + 1, s2.length());
        }
        final int index5 = s2.indexOf(63);
        if (index5 >= 0) {
            String substring = "";
            int n = s2.lastIndexOf(63);
            if (n == index5) {
                n = s2.length();
            }
            int n2;
            for (n2 = index5; n2 < n && s2.charAt(n2) == '?'; ++n2) {}
            final int index6 = s2.indexOf(63, n2 + 1);
            if (index6 >= 0) {
                substring = s2.substring(n2, index6);
            }
            s2 = s2.substring(0, index5) + substring + s2.substring(n + 1, s2.length());
        }
        return s2;
    }
}
