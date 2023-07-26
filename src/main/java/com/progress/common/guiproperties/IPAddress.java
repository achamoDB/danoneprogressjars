// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.guiproperties;

import java.util.StringTokenizer;

public class IPAddress extends StringList
{
    IPAddress() throws XPropertyException {
    }
    
    IPAddress(final String s) throws XPropertyException {
        super(s);
    }
    
    public boolean isValidValueAsString(final String str) throws XPropertyException {
        if (str.equals("")) {
            return false;
        }
        if (new StringTokenizer(str, ".").countTokens() != 4) {
            return false;
        }
        int n = 0;
        for (int i = 0; i < str.length(); ++i) {
            if (str.charAt(i) == '.') {
                if (++n > 3) {
                    return false;
                }
            }
            else if (str.charAt(i) < '0' || str.charAt(i) > '9') {
                return false;
            }
        }
        return true;
    }
    
    public Object toObject(final String s) throws XPropertyException {
        return new IPAddress(s);
    }
}
