// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.util;

import java.util.Vector;

abstract class RegExNode
{
    RegExNode \u03ee;
    RegExNode \u03ef;
    int \u03f0;
    int \u03f1;
    int \u03f2;
    
    public String toString() {
        return "<unspec>";
    }
    
    RegExNode(final RegExNode \u03ef) {
        final int n = 1;
        this.\u03f2 = n;
        this.\u03f1 = n;
        this.\u03f0 = 0;
        this.\u03ee = \u03ef;
    }
    
    boolean \u03ee(final RegExState regExState, final boolean b) {
        final RegExNode \u03ef = this.\u03ef;
        if (\u03ef != null) {
            return \u03ef.match(regExState, b);
        }
        if (b) {
            for (RegExNode regExNode = this.\u03ee; regExNode != null; regExNode = regExNode.\u03ee) {
                final RegExNode \u03ef2 = regExNode.\u03ef;
                if (\u03ef2 != null) {
                    final int \u0434 = regExState.\u0414;
                    final boolean match = \u03ef2.match(regExState, b);
                    regExState.\u0414 = \u0434;
                    return match;
                }
            }
        }
        return true;
    }
    
    boolean match(final RegExState regExState, final boolean b) {
        final int \u0434 = regExState.\u0414;
        if (this.\u03f1 == 1 && this.\u03f2 == 1) {
            if (this.\u03ec(regExState) && this.\u03ee(regExState, b)) {
                return true;
            }
        }
        else {
            final int n = (this.\u03f2 == -1) ? 100000 : this.\u03f2;
            final Vector vector = new Vector<Integer>();
            int n2 = 0;
            do {
                if (n2 >= this.\u03f1) {
                    vector.addElement(new Integer(regExState.\u0414));
                }
            } while (++n2 <= n && this.\u03ec(regExState));
            for (int i = vector.size() - 1; i >= 0; --i) {
                regExState.\u0414 = vector.elementAt(i);
                if (this.\u03ee(regExState, b)) {
                    return true;
                }
            }
        }
        if (regExState.\u03d4 == null) {
            regExState.\u0414 = \u0434;
            regExState.\u03d4 = this;
        }
        return false;
    }
    
    abstract boolean \u03ec(final RegExState p0);
    
    void \u03ef(final char c) {
        switch (c) {
            case '?': {
                this.\u03f1 = 0;
                this.\u03f2 = 1;
            }
            case '*': {
                this.\u03f1 = 0;
                this.\u03f2 = -1;
            }
            case '+': {
                this.\u03f1 = 1;
                this.\u03f2 = -1;
            }
            default: {}
        }
    }
    
    void \u03ef(final String s) {
        try {
            final int length = s.length();
            if (length == 0) {
                this.\u03f1 = 0;
                this.\u03f2 = -1;
                return;
            }
            final int index = s.indexOf(44);
            if (index < 0) {
                final int int1 = Integer.parseInt(s.trim());
                this.\u03f2 = int1;
                this.\u03f1 = int1;
                return;
            }
            if (index == 0) {
                this.\u03f1 = 0;
            }
            else {
                this.\u03f1 = Integer.parseInt(s.substring(0, index));
            }
            if (index < length - 1) {
                this.\u03f2 = Integer.parseInt(s.substring(index + 1));
                return;
            }
            this.\u03f2 = -1;
        }
        catch (Exception ex) {
            throw new RegExException("Invalid repeat count");
        }
    }
    
    void \u03f0(final StringBuffer sb) {
        if (this.\u03f1 != 1 || this.\u03f2 != 1) {
            if (this.\u03f1 == 0 && this.\u03f2 == 1) {
                sb.append('?');
            }
            else if (this.\u03f1 == 0 && this.\u03f2 == -1) {
                sb.append('*');
            }
            else if (this.\u03f1 == 1 && this.\u03f2 == -1) {
                sb.append('+');
            }
            else {
                sb.append('{');
                if (this.\u03f1 >= 0) {
                    sb.append(this.\u03f1);
                }
                sb.append(',');
                if (this.\u03f2 >= 0) {
                    sb.append(this.\u03f2);
                }
                sb.append('}');
            }
        }
        if (this.\u03ef != null) {
            sb.append(this.\u03ef);
        }
    }
}
