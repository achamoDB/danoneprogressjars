// 
// Decompiled by Procyon v0.5.36
// 

package com.oroinc.text.regex;

public interface PatternMatcher
{
    boolean matchesPrefix(final char[] p0, final Pattern p1, final int p2);
    
    boolean matchesPrefix(final String p0, final Pattern p1);
    
    boolean matchesPrefix(final char[] p0, final Pattern p1);
    
    boolean matchesPrefix(final PatternMatcherInput p0, final Pattern p1);
    
    boolean matches(final String p0, final Pattern p1);
    
    boolean matches(final char[] p0, final Pattern p1);
    
    boolean matches(final PatternMatcherInput p0, final Pattern p1);
    
    boolean contains(final String p0, final Pattern p1);
    
    boolean contains(final char[] p0, final Pattern p1);
    
    boolean contains(final PatternMatcherInput p0, final Pattern p1);
    
    MatchResult getMatch();
}
