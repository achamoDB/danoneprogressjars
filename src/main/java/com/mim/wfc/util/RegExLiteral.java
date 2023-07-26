// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.util;

class RegExLiteral extends RegExNode
{
    String text;
    int \u03ed;
    
    public String toString() {
        final StringBuffer sb = new StringBuffer(this.text);
        this.\u03f0(sb);
        return sb.toString();
    }
    
    RegExLiteral(final RegExNode regExNode, final String text) {
        super(regExNode);
        this.setText(text);
    }
    
    RegExLiteral(final RegExNode regExNode, final char c) {
        super(regExNode);
        this.\u03ed(c);
    }
    
    boolean \u03ec(final RegExState regExState) {
        if (this.text != null) {
            for (int i = 0; i < this.\u03ed; ++i) {
                if (regExState.\u0414 + i >= regExState.\u03ed) {
                    regExState.\u03d4 = this;
                    if (!regExState.\u0415) {
                        return false;
                    }
                }
                char ch = this.text.charAt(i);
                char ch2 = regExState.\u0414(regExState.\u0414 + i);
                if (RegEx.getIgnoreCase()) {
                    ch = Character.toLowerCase(ch);
                    ch2 = Character.toLowerCase(ch2);
                }
                if (ch != ch2) {
                    return false;
                }
            }
            regExState.\u0414 += this.\u03ed;
        }
        return true;
    }
    
    void \u03ed(final char c) {
        this.text = ((this.text != null) ? this.text : "") + c;
        ++this.\u03ed;
    }
    
    void setText(final String s) {
        this.text = this.text;
        this.\u03ed = ((this.text != null) ? this.text.length() : 0);
    }
}
