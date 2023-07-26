// 
// Decompiled by Procyon v0.5.36
// 

package com.oroinc.text.regex;

public interface PatternCompiler
{
    Pattern compile(final String p0) throws MalformedPatternException;
    
    Pattern compile(final String p0, final int p1) throws MalformedPatternException;
    
    Pattern compile(final char[] p0) throws MalformedPatternException;
    
    Pattern compile(final char[] p0, final int p1) throws MalformedPatternException;
}
