// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.util;

class RegExState
{
    String s;
    int \u0414;
    int \u03ed;
    boolean \u0415;
    RegExNode \u03d4;
    
    char \u0414() {
        return this.s.charAt(this.\u0414);
    }
    
    char \u0414(final int index) {
        return this.s.charAt(index);
    }
    
    RegExState(final String s, final int \u0434) {
        this.s = s;
        this.\u0414 = \u0434;
        this.\u03ed = s.length();
    }
}
