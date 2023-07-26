// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.util;

import java.util.Stack;

public class RegEx
{
    private RegExNode \u03d3;
    private RegExNode \u03d4;
    private int \u03ea;
    private static boolean \u03d6;
    
    public static void setIgnoreCase(final boolean \u03d6) {
        RegEx.\u03d6 = \u03d6;
    }
    
    public static boolean getIgnoreCase() {
        return RegEx.\u03d6;
    }
    
    public String toString() {
        return this.\u03d3.toString();
    }
    
    public void compile(final String s) throws RegExException {
        final int length = s.length();
        final Stack stack = new Stack<RegExAlt>();
        RegExNode regExNode = null;
        RegExNode regExNode2 = null;
        RegExNode item = null;
        this.\u03d3 = null;
        int n = 0;
        int i = 0;
        try {
            while (i < length) {
                char c = s.charAt(i++);
                Label_0871: {
                    switch (c) {
                        case 40: {
                            regExNode = new RegExAlt(item);
                            break Label_0871;
                        }
                        case 41: {
                            if (item == null) {
                                throw new RegExException("Misplaced ) at colum " + i);
                            }
                            if (regExNode2 == null) {
                                throw new RegExException("Empty (...) at colum " + i);
                            }
                            regExNode2 = item;
                            item = stack.pop();
                            continue;
                        }
                        case 124: {
                            if (item == null || regExNode2 == null) {
                                throw new RegExException("Misplaced | at colum " + i);
                            }
                            regExNode2 = null;
                            continue;
                        }
                        case 46: {
                            regExNode = new RegExRange(item);
                            break Label_0871;
                        }
                        case 91: {
                            final int index = s.indexOf(93, i);
                            if (index < 0) {
                                throw new RegExException("Missing ] at colum " + i);
                            }
                            regExNode = new RegExRange(item, s.substring(i, index));
                            i = index + 1;
                            break Label_0871;
                        }
                        case 42:
                        case 43:
                        case 63: {
                            if (regExNode2 == null) {
                                throw new RegExException("Misplaced */+/? at column " + i);
                            }
                            regExNode2.\u03ef(c);
                            n = 0;
                            continue;
                        }
                        case 123: {
                            if (regExNode2 == null) {
                                throw new RegExException("Misplaced */+/? at column " + i);
                            }
                            final int index2 = s.indexOf(125, i);
                            if (index2 < 0) {
                                throw new RegExException("Missing } at colum " + i);
                            }
                            regExNode2.\u03ef(s.substring(i, index2));
                            i = index2 + 1;
                            n = 0;
                            continue;
                        }
                        case 94: {
                            if (i != 1) {
                                throw new RegExException("Misplaced ^ at column " + i);
                            }
                            continue;
                        }
                        case 36: {
                            if (i != length) {
                                throw new RegExException("Misplaced $ at column " + i);
                            }
                            continue;
                        }
                        case 92: {
                            boolean b = true;
                            switch (c = s.charAt(i++)) {
                                case 'A':
                                case 'D':
                                case 'L':
                                case 'O':
                                case 'S':
                                case 'U':
                                case 'W':
                                case 'X':
                                case 'a':
                                case 'd':
                                case 'l':
                                case 'o':
                                case 's':
                                case 'u':
                                case 'w':
                                case 'x': {
                                    regExNode = new RegExRange(item, c);
                                    b = false;
                                    break;
                                }
                                case 'b': {
                                    c = '\b';
                                    break;
                                }
                                case 'n': {
                                    c = '\n';
                                    break;
                                }
                                case 'r': {
                                    c = '\r';
                                    break;
                                }
                                case 't': {
                                    c = '\t';
                                    break;
                                }
                            }
                            if (b) {
                                break;
                            }
                            break Label_0871;
                        }
                    }
                    if (regExNode2 instanceof RegExLiteral && n != 0) {
                        ((RegExLiteral)regExNode2).\u03ed(c);
                        continue;
                    }
                    regExNode = new RegExLiteral(item, c);
                    n = 1;
                }
                if (this.\u03d3 == null) {
                    this.\u03d3 = regExNode;
                }
                if (regExNode2 == null) {
                    if (item != null) {
                        ((RegExAlt)item).\u03eb(regExNode);
                    }
                }
                else {
                    regExNode2.\u03ef = regExNode;
                }
                if (regExNode instanceof RegExAlt) {
                    stack.push((RegExAlt)item);
                    item = regExNode;
                    regExNode2 = null;
                }
                else {
                    regExNode2 = regExNode;
                }
            }
            if (!stack.empty()) {
                throw new RegExException("Missing ) at colum " + i);
            }
        }
        catch (RegExException ex) {
            throw ex;
        }
        catch (Exception ex2) {
            throw new RegExException("Error at " + i + '.');
        }
    }
    
    public boolean match(final String s, final boolean b) {
        return this.match(new RegExState(s, 0), b);
    }
    
    public boolean match(final String s, final int n, final boolean b) {
        return this.match(new RegExState(s, n), b);
    }
    
    private boolean match(final RegExState regExState, final boolean \u0435) {
        regExState.\u0415 = \u0435;
        boolean match = true;
        try {
            match = this.\u03d3.match(regExState, true);
        }
        catch (StringIndexOutOfBoundsException ex) {}
        if (match && regExState.\u0414 < regExState.\u03ed) {
            match = false;
        }
        this.\u03d4 = regExState.\u03d4;
        this.\u03ea = regExState.\u0414;
        return match;
    }
    
    public String getStopNodeText() {
        if (this.stoppedAtLiteralNode()) {
            return ((RegExLiteral)this.\u03d4).text;
        }
        return null;
    }
    
    public void clear() {
        final RegExNode regExNode = null;
        this.\u03d4 = regExNode;
        this.\u03d3 = regExNode;
    }
    
    public boolean stoppedAtLiteralNode() {
        return this.\u03d4 != null && this.\u03d4 instanceof RegExLiteral;
    }
    
    public int getStopIndex() {
        return this.\u03ea;
    }
}
