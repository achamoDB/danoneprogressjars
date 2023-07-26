// 
// Decompiled by Procyon v0.5.36
// 

package com.oroinc.text.regex;

import java.util.Vector;

public final class Util
{
    public static final int SUBSTITUTE_ALL = -1;
    public static final int SPLIT_ALL = 0;
    public static final int INTERPOLATE_ALL = 0;
    public static final int INTERPOLATE_NONE = -1;
    
    private Util() {
    }
    
    public static Vector split(final PatternMatcher patternMatcher, final Pattern pattern, final String s, int n) {
        final Vector<String> vector = new Vector<String>(20);
        final PatternMatcherInput patternMatcherInput = new PatternMatcherInput(s);
        int endOffset = 0;
        while (--n != 0 && patternMatcher.contains(patternMatcherInput, pattern)) {
            final MatchResult match = patternMatcher.getMatch();
            vector.addElement(s.substring(endOffset, match.beginOffset(0)));
            endOffset = match.endOffset(0);
        }
        vector.addElement(s.substring(endOffset, s.length()));
        return vector;
    }
    
    public static Vector split(final PatternMatcher patternMatcher, final Pattern pattern, final String s) {
        return split(patternMatcher, pattern, s, 0);
    }
    
    public static String substitute(final PatternMatcher patternMatcher, final Pattern pattern, final Substitution substitution, final String s, int n) {
        final StringBuffer sb = new StringBuffer(s.length());
        final PatternMatcherInput patternMatcherInput = new PatternMatcherInput(s);
        int endOffset;
        int n2 = endOffset = 0;
        while (n != 0 && patternMatcher.contains(patternMatcherInput, pattern)) {
            --n;
            ++n2;
            final MatchResult match = patternMatcher.getMatch();
            sb.append(s.substring(endOffset, match.beginOffset(0)));
            substitution.appendSubstitution(sb, match, n2, s, patternMatcher, pattern);
            endOffset = match.endOffset(0);
        }
        if (n2 == 0) {
            return s;
        }
        sb.append(s.substring(endOffset, s.length()));
        return sb.toString();
    }
    
    public static String substitute(final PatternMatcher patternMatcher, final Pattern pattern, final Substitution substitution, final String s) {
        return substitute(patternMatcher, pattern, substitution, s, 1);
    }
    
    public static String substitute(final PatternMatcher patternMatcher, final Pattern pattern, final String s, final String s2, final int n, final int n2) {
        return substitute(patternMatcher, pattern, new Perl5Substitution(s, n2), s2, n);
    }
    
    public static String substitute(final PatternMatcher patternMatcher, final Pattern pattern, final String s, final String s2, final int n) {
        return substitute(patternMatcher, pattern, new Perl5Substitution(s, 0), s2, n);
    }
    
    public static String substitute(final PatternMatcher patternMatcher, final Pattern pattern, final String s, final String s2) {
        return substitute(patternMatcher, pattern, new Perl5Substitution(s, 0), s2, 1);
    }
}
