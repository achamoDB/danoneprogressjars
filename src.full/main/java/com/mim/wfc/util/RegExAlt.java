// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.util;

import java.util.Vector;

class RegExAlt extends RegExNode
{
    Vector \u03eb;
    
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append('(');
        for (int i = 0; i < this.\u03eb.size(); ++i) {
            if (i > 0) {
                sb.append('|');
            }
            sb.append(this.\u03eb.elementAt(i));
        }
        sb.append(')');
        this.\u03f0(sb);
        return sb.toString();
    }
    
    RegExAlt(final RegExNode regExNode) {
        super(regExNode);
        this.\u03eb = new Vector();
    }
    
    void \u03eb(final RegExNode obj) {
        this.\u03eb.addElement(obj);
    }
    
    boolean \u03ec(final RegExState regExState) {
        final int \u0434 = regExState.\u0414;
        for (int size = this.\u03eb.size(), i = 0; i < size; ++i) {
            regExState.\u0414 = \u0434;
            if (((RegExNode)this.\u03eb.elementAt(i)).match(regExState, false)) {
                return true;
            }
        }
        return false;
    }
}
