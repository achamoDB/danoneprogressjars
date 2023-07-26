// 
// Decompiled by Procyon v0.5.36
// 

package com.oroinc.text.regex;

public interface MatchResult
{
    int length();
    
    int groups();
    
    String group(final int p0);
    
    int begin(final int p0);
    
    int end(final int p0);
    
    int beginOffset(final int p0);
    
    int endOffset(final int p0);
    
    String toString();
}
