// 
// Decompiled by Procyon v0.5.36
// 

package com.oroinc.text.regex;

import java.util.Vector;

public class Perl5Substitution extends StringSubstitution
{
    public static final int INTERPOLATE_ALL = 0;
    public static final int INTERPOLATE_NONE = -1;
    int _numInterpolations;
    Vector _substitutions;
    transient String _lastInterpolation;
    
    static Vector _parseSubs(final String s) {
        final Vector<String> vector = new Vector<String>(5);
        final StringBuffer sb = new StringBuffer(5);
        final StringBuffer sb2 = new StringBuffer(10);
        final char[] charArray = s.toCharArray();
        int i = 0;
        int n = 0;
        boolean b = false;
        while (i < charArray.length) {
            if (n != 0 && Character.isDigit(charArray[i])) {
                sb.append(charArray[i]);
                if (sb2.length() > 0) {
                    vector.addElement(sb2.toString());
                    sb2.setLength(0);
                }
            }
            else {
                if (n != 0) {
                    try {
                        vector.addElement((String)new Integer(sb.toString()));
                        b = true;
                    }
                    catch (NumberFormatException ex) {
                        vector.addElement(sb.toString());
                    }
                    sb.setLength(0);
                    n = 0;
                }
                if (charArray[i] == '$' && i + 1 < charArray.length && charArray[i + 1] != '0' && Character.isDigit(charArray[i + 1])) {
                    n = 1;
                }
                else {
                    sb2.append(charArray[i]);
                }
            }
            ++i;
        }
        if (n != 0) {
            try {
                vector.addElement((String)new Integer(sb.toString()));
                b = true;
            }
            catch (NumberFormatException ex2) {
                vector.addElement(sb.toString());
            }
        }
        else if (sb2.length() > 0) {
            vector.addElement(sb2.toString());
        }
        if (b) {
            return vector;
        }
        return null;
    }
    
    String _finalInterpolatedSub(final MatchResult matchResult) {
        final StringBuffer sb = new StringBuffer(10);
        this._calcSub(sb, matchResult);
        return sb.toString();
    }
    
    void _calcSub(final StringBuffer sb, final MatchResult matchResult) {
        for (int size = this._substitutions.size(), i = 0; i < size; ++i) {
            final Object element = this._substitutions.elementAt(i);
            if (element instanceof String) {
                sb.append(element);
            }
            else {
                final int intValue = (int)element;
                if (intValue > 0 && intValue < matchResult.groups()) {
                    final String group = matchResult.group(intValue);
                    if (group != null) {
                        sb.append(group);
                    }
                }
                else {
                    sb.append('$');
                    sb.append(intValue);
                }
            }
        }
    }
    
    public Perl5Substitution() {
        this("", 0);
    }
    
    public Perl5Substitution(final String s) {
        this(s, 0);
    }
    
    public Perl5Substitution(final String s, final int n) {
        this.setSubstitution(s, n);
    }
    
    public void setSubstitution(final String s) {
        this.setSubstitution(s, 0);
    }
    
    public void setSubstitution(final String substitution, final int numInterpolations) {
        super.setSubstitution(substitution);
        this._numInterpolations = numInterpolations;
        if (numInterpolations != -1 && substitution.indexOf(36) != -1) {
            this._substitutions = _parseSubs(substitution);
        }
        else {
            this._substitutions = null;
        }
        this._lastInterpolation = null;
    }
    
    public void appendSubstitution(final StringBuffer sb, final MatchResult matchResult, final int n, final String s, final PatternMatcher patternMatcher, final Pattern pattern) {
        if (this._substitutions == null) {
            super.appendSubstitution(sb, matchResult, n, s, patternMatcher, pattern);
            return;
        }
        if (this._numInterpolations < 1 || n < this._numInterpolations) {
            this._calcSub(sb, matchResult);
            return;
        }
        if (n == this._numInterpolations) {
            this._lastInterpolation = this._finalInterpolatedSub(matchResult);
        }
        sb.append(this._lastInterpolation);
    }
}
