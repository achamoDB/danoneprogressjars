// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.text;

public class QuotedString
{
    String m_value;
    
    public QuotedString(final String s) {
        if (s == null || s.length() <= 0) {
            this.m_value = "\"\"";
        }
        else {
            int n = 0;
            final char[] charArray = s.toCharArray();
            int n2 = 0;
            for (int i = 0; i < charArray.length; ++i) {
                if (charArray[i] == '\\') {
                    n2 = ((n2 == 0) ? 1 : 0);
                }
                else {
                    if (charArray[i] == '\"' && n2 == 0) {
                        ++n;
                    }
                    n2 = 0;
                }
            }
            final char[] value = new char[charArray.length + 2 + n];
            int n3 = 0;
            int n4 = 0;
            value[n4++] = '\"';
            for (int j = 0; j < charArray.length; ++j) {
                if (charArray[j] == '\\') {
                    n3 = ((n3 == 0) ? 1 : 0);
                    value[n4++] = charArray[j];
                }
                else {
                    if (charArray[j] == '\"' && n3 == 0) {
                        value[n4++] = '\\';
                        value[n4++] = '\"';
                    }
                    else {
                        value[n4++] = charArray[j];
                    }
                    n3 = 0;
                }
            }
            value[n4++] = '\"';
            this.m_value = new String(value);
        }
    }
    
    public String toString() {
        return this.m_value;
    }
}
