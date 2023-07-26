// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.debugger;

import java.util.NoSuchElementException;
import java.util.Enumeration;

public class DebuggerStringTokenizer implements Enumeration
{
    private int currentPosition;
    private int newPosition;
    private int maxPosition;
    private String str;
    private String delimiters;
    private boolean retDelims;
    private boolean delimsChanged;
    char quote;
    private char maxDelimChar;
    
    public DebuggerStringTokenizer(final String str, final String delimiters, final boolean retDelims) {
        this.quote = '\"';
        this.currentPosition = 0;
        this.newPosition = -1;
        this.delimsChanged = false;
        this.str = str;
        this.maxPosition = str.length();
        this.delimiters = delimiters;
        this.retDelims = retDelims;
        this.setMaxDelimChar();
    }
    
    public DebuggerStringTokenizer(final String s, final String s2) {
        this(s, s2, false);
    }
    
    public DebuggerStringTokenizer(final String s) {
        this(s, " \t\n\r\f", false);
    }
    
    private void setMaxDelimChar() {
        if (this.delimiters == null) {
            this.maxDelimChar = '\0';
            return;
        }
        char maxDelimChar = '\0';
        for (int i = 0; i < this.delimiters.length(); ++i) {
            final char char1 = this.delimiters.charAt(i);
            if (maxDelimChar < char1) {
                maxDelimChar = char1;
            }
        }
        this.maxDelimChar = maxDelimChar;
    }
    
    private int skipDelimiters(final int n) {
        if (this.delimiters == null) {
            throw new NullPointerException();
        }
        int index;
        for (index = n; !this.retDelims && index < this.maxPosition; ++index) {
            final char char1 = this.str.charAt(index);
            if (char1 > this.maxDelimChar) {
                break;
            }
            if (this.delimiters.indexOf(char1) < 0) {
                break;
            }
        }
        return index;
    }
    
    public int scanToken(final int n) {
        int i;
        for (i = n; i < this.maxPosition; ++i) {
            final char char1 = this.str.charAt(i);
            if (char1 <= this.maxDelimChar && this.delimiters.indexOf(char1) >= 0) {
                break;
            }
        }
        if (this.retDelims && n == i) {
            final char char2 = this.str.charAt(i);
            if (char2 <= this.maxDelimChar && this.delimiters.indexOf(char2) >= 0) {
                ++i;
            }
        }
        return i;
    }
    
    public boolean hasMoreTokens() {
        this.newPosition = this.skipDelimiters(this.currentPosition);
        return this.newPosition < this.maxPosition;
    }
    
    public String nextToken() {
        this.currentPosition = ((this.newPosition >= 0 && !this.delimsChanged) ? this.newPosition : this.skipDelimiters(this.currentPosition));
        this.delimsChanged = false;
        this.newPosition = -1;
        if (this.currentPosition >= this.maxPosition) {
            throw new NoSuchElementException();
        }
        int currentPosition = this.currentPosition;
        if (this.str.charAt(currentPosition) == '\u0012') {
            int n = 0;
            ++currentPosition;
            while (this.str.charAt(currentPosition) >= '0' && this.str.charAt(currentPosition) <= '9') {
                n = n * 10 + (this.str.charAt(currentPosition) - '0');
                ++currentPosition;
            }
            this.currentPosition = currentPosition + n;
        }
        else {
            this.currentPosition = this.scanToken(this.currentPosition);
        }
        return this.str.substring(currentPosition, this.currentPosition);
    }
    
    public String nextToken(final String delimiters) {
        this.delimiters = delimiters;
        this.delimsChanged = true;
        this.setMaxDelimChar();
        return this.nextToken();
    }
    
    public boolean hasMoreElements() {
        return this.hasMoreTokens();
    }
    
    public Object nextElement() {
        return this.nextToken();
    }
    
    public int countTokens() {
        int n = 0;
        int skipDelimiters;
        for (int i = this.currentPosition; i < this.maxPosition; i = this.scanToken(skipDelimiters), ++n) {
            skipDelimiters = this.skipDelimiters(i);
            if (skipDelimiters >= this.maxPosition) {
                break;
            }
        }
        return n;
    }
}
