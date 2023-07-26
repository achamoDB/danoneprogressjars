// 
// Decompiled by Procyon v0.5.36
// 

package com.oroinc.text.regex;

public class StringSubstitution implements Substitution
{
    int _subLength;
    String _substitution;
    
    public StringSubstitution() {
        this("");
    }
    
    public StringSubstitution(final String substitution) {
        this.setSubstitution(substitution);
    }
    
    public void setSubstitution(final String substitution) {
        this._substitution = substitution;
        this._subLength = substitution.length();
    }
    
    public String getSubstitution() {
        return this._substitution;
    }
    
    public String toString() {
        return this.getSubstitution();
    }
    
    public void appendSubstitution(final StringBuffer sb, final MatchResult matchResult, final int n, final String s, final PatternMatcher patternMatcher, final Pattern pattern) {
        if (this._subLength == 0) {
            return;
        }
        sb.append(this._substitution);
    }
}
