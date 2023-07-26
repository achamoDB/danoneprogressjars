// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.util;

class RegExRange extends RegExNode
{
    static int \u03f3;
    static int \u0401;
    static int \u0402;
    static int \u0403;
    static int \u0404;
    static int \u0405;
    static int \u0406;
    static int \u0407;
    static int \u0408;
    static int \u0409;
    static int \u040a;
    static int \u040b;
    static int \u040c;
    static int \u040e;
    static int \u040f;
    static int \u0410;
    static int \u0411;
    static int \u0412;
    static int \u0413;
    int type;
    String text;
    
    private boolean \u03f3(final char c) {
        if (this.type == 0) {
            return true;
        }
        if ((this.type & RegExRange.\u03f3) != 0x0) {
            return true;
        }
        if ((this.type & RegExRange.\u0401) != 0x0) {
            if (this.text == null) {
                return true;
            }
            if (this.text.indexOf(c) >= 0) {
                return true;
            }
        }
        if ((this.type & RegExRange.\u0402) != 0x0) {
            if (this.text == null) {
                return true;
            }
            if (this.text.indexOf(c) < 0) {
                return true;
            }
        }
        return ((this.type & RegExRange.\u0403) != 0x0 && Character.isWhitespace(c)) || ((this.type & RegExRange.\u0404) != 0x0 && !Character.isWhitespace(c)) || ((this.type & RegExRange.\u0405) != 0x0 && Character.isDigit(c)) || ((this.type & RegExRange.\u0406) != 0x0 && !Character.isDigit(c)) || ((this.type & RegExRange.\u0407) != 0x0 && (Character.isDigit(c) || (c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F'))) || ((this.type & RegExRange.\u0408) != 0x0 && !Character.isDigit(c) && (c < 'a' || c > 'f') && (c < 'A' || c > 'F')) || ((this.type & RegExRange.\u0409) != 0x0 && c >= '0' && c <= '7') || ((this.type & RegExRange.\u040a) != 0x0 && (c < '0' || c > '7')) || ((this.type & RegExRange.\u040b) != 0x0 && Character.isLetterOrDigit(c)) || ((this.type & RegExRange.\u040c) != 0x0 && !Character.isLetterOrDigit(c)) || ((this.type & RegExRange.\u040e) != 0x0 && Character.isLetter(c)) || ((this.type & RegExRange.\u040f) != 0x0 && !Character.isLetter(c)) || ((this.type & RegExRange.\u0410) != 0x0 && Character.isLowerCase(c)) || ((this.type & RegExRange.\u0411) != 0x0 && !Character.isLowerCase(c)) || ((this.type & RegExRange.\u0412) != 0x0 && Character.isUpperCase(c)) || ((this.type & RegExRange.\u0413) != 0x0 && !Character.isUpperCase(c));
    }
    
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        if ((this.type & RegExRange.\u03f3) != 0x0) {
            sb.append('.');
        }
        if ((this.type & RegExRange.\u0401) != 0x0) {
            sb.append('[');
            sb.append(this.text);
        }
        if ((this.type & RegExRange.\u0402) != 0x0) {
            sb.append("[^");
            sb.append(this.text);
        }
        if ((this.type & RegExRange.\u0403) != 0x0) {
            sb.append("\\s");
        }
        if ((this.type & RegExRange.\u0404) != 0x0) {
            sb.append("\\S");
        }
        if ((this.type & RegExRange.\u0405) != 0x0) {
            sb.append("\\d");
        }
        if ((this.type & RegExRange.\u0406) != 0x0) {
            sb.append("\\D");
        }
        if ((this.type & RegExRange.\u0407) != 0x0) {
            sb.append("\\x");
        }
        if ((this.type & RegExRange.\u0408) != 0x0) {
            sb.append("\\X");
        }
        if ((this.type & RegExRange.\u0409) != 0x0) {
            sb.append("\\o");
        }
        if ((this.type & RegExRange.\u040a) != 0x0) {
            sb.append("\\O");
        }
        if ((this.type & RegExRange.\u040b) != 0x0) {
            sb.append("\\w");
        }
        if ((this.type & RegExRange.\u040c) != 0x0) {
            sb.append("\\W");
        }
        if ((this.type & RegExRange.\u040e) != 0x0) {
            sb.append("\\a");
        }
        if ((this.type & RegExRange.\u040f) != 0x0) {
            sb.append("\\A");
        }
        if ((this.type & RegExRange.\u0410) != 0x0) {
            sb.append("\\l");
        }
        if ((this.type & RegExRange.\u0411) != 0x0) {
            sb.append("\\L");
        }
        if ((this.type & RegExRange.\u0412) != 0x0) {
            sb.append("\\u");
        }
        if ((this.type & RegExRange.\u0413) != 0x0) {
            sb.append("\\U");
        }
        if ((this.type & (RegExRange.\u0401 | RegExRange.\u0402)) != 0x0) {
            sb.append(']');
        }
        this.\u03f0(sb);
        return sb.toString();
    }
    
    RegExRange(final RegExNode regExNode) {
        super(regExNode);
        this.type = RegExRange.\u03f3;
    }
    
    RegExRange(final RegExNode regExNode, final char c) {
        super(regExNode);
        this.setText("\\" + c);
    }
    
    RegExRange(final RegExNode regExNode, final String text) {
        super(regExNode);
        this.setText(text);
    }
    
    static {
        RegExRange.\u03f3 = 1;
        RegExRange.\u0401 = 2;
        RegExRange.\u0402 = 4;
        RegExRange.\u0403 = 8;
        RegExRange.\u0404 = 16;
        RegExRange.\u0405 = 32;
        RegExRange.\u0406 = 64;
        RegExRange.\u0407 = 128;
        RegExRange.\u0408 = 256;
        RegExRange.\u0409 = 512;
        RegExRange.\u040a = 1024;
        RegExRange.\u040b = 2048;
        RegExRange.\u040c = 4096;
        RegExRange.\u040e = 8192;
        RegExRange.\u040f = 16384;
        RegExRange.\u0410 = 32768;
        RegExRange.\u0411 = 65536;
        RegExRange.\u0412 = 131072;
        RegExRange.\u0413 = 262144;
    }
    
    boolean \u03ec(final RegExState regExState) {
        if (regExState.\u0414 >= regExState.\u03ed) {
            regExState.\u03d4 = this;
            if (!regExState.\u0415) {
                return false;
            }
        }
        final boolean \u03f3 = this.\u03f3(regExState.\u0414());
        if (\u03f3) {
            ++regExState.\u0414;
        }
        return \u03f3;
    }
    
    private void \u03ed(final char c) {
        this.text = ((this.text != null) ? this.text : "") + c;
    }
    
    private void \u0401(final char c, final char c2) {
        if (c > c2) {
            return;
        }
        final StringBuffer obj = new StringBuffer();
        for (char c3 = c; c3 <= c2; ++c3) {
            obj.append(c3);
        }
        if (this.text == null) {
            this.text = "";
        }
        this.text += (Object)obj;
    }
    
    void setText(final String s) {
        if (s == null || s.length() == 0) {
            return;
        }
        int i = 0;
        final int length = s.length();
        boolean b;
        if (s.charAt(i) == '^') {
            ++i;
            b = false;
        }
        else {
            b = true;
        }
        while (i < length) {
            Label_0618: {
                char c = '\0';
                Label_0589: {
                    switch (c = s.charAt(i)) {
                        case '-': {
                            if (++i < length) {
                                this.\u0401((char)(s.charAt(i - 2) + '\u0001'), s.charAt(i));
                            }
                            else {
                                this.\u03ed('-');
                            }
                            this.type |= (b ? RegExRange.\u0401 : RegExRange.\u0402);
                            break Label_0618;
                        }
                        case '\\': {
                            ++i;
                            switch (c = s.charAt(i)) {
                                case 's': {
                                    this.type |= RegExRange.\u0403;
                                    break Label_0618;
                                }
                                case 'S': {
                                    this.type |= RegExRange.\u0404;
                                    break Label_0618;
                                }
                                case 'd': {
                                    this.type |= RegExRange.\u0405;
                                    break Label_0618;
                                }
                                case 'D': {
                                    this.type |= RegExRange.\u0406;
                                    break Label_0618;
                                }
                                case 'x': {
                                    this.type |= RegExRange.\u0407;
                                    break Label_0618;
                                }
                                case 'X': {
                                    this.type |= RegExRange.\u0408;
                                    break Label_0618;
                                }
                                case 'o': {
                                    this.type |= RegExRange.\u0409;
                                    break Label_0618;
                                }
                                case 'O': {
                                    this.type |= RegExRange.\u040a;
                                    break Label_0618;
                                }
                                case 'w': {
                                    this.type |= RegExRange.\u040b;
                                    break Label_0618;
                                }
                                case 'W': {
                                    this.type |= RegExRange.\u040c;
                                    break Label_0618;
                                }
                                case 'a': {
                                    this.type |= RegExRange.\u040e;
                                    break Label_0618;
                                }
                                case 'A': {
                                    this.type |= RegExRange.\u040f;
                                    break Label_0618;
                                }
                                case 'l': {
                                    this.type |= RegExRange.\u0410;
                                    break Label_0618;
                                }
                                case 'L': {
                                    this.type |= RegExRange.\u0411;
                                    break Label_0618;
                                }
                                case 'u': {
                                    this.type |= RegExRange.\u0412;
                                    break Label_0618;
                                }
                                case 'U': {
                                    this.type |= RegExRange.\u0413;
                                    break Label_0618;
                                }
                                case 'b': {
                                    c = '\b';
                                    break Label_0589;
                                }
                                case 'n': {
                                    c = '\n';
                                    break Label_0589;
                                }
                                case 'r': {
                                    c = '\r';
                                    break Label_0589;
                                }
                                case 't': {
                                    c = '\t';
                                    break Label_0589;
                                }
                            }
                            break;
                        }
                    }
                }
                this.\u03ed(c);
                this.type |= (b ? RegExRange.\u0401 : RegExRange.\u0402);
            }
            ++i;
        }
    }
}
