// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.text;

public class UnquotedString
{
    String m_value;
    
    public UnquotedString(final String s) {
        if (s == null || s.length() <= 0) {
            this.m_value = s;
        }
        else {
            final int n = s.length() - 1;
            if (s.charAt(0) == '\"' && s.charAt(n) == '\"') {
                this.m_value = new String(s.substring(1, n));
            }
            else {
                this.m_value = new String(s);
            }
        }
    }
    
    public String toString() {
        return this.m_value;
    }
}
