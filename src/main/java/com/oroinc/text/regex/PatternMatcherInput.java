// 
// Decompiled by Procyon v0.5.36
// 

package com.oroinc.text.regex;

public final class PatternMatcherInput
{
    String _originalStringInput;
    char[] _originalCharInput;
    char[] _originalBuffer;
    char[] _toLowerBuffer;
    int _beginOffset;
    int _endOffset;
    int _currentOffset;
    int _matchBeginOffset;
    int _matchEndOffset;
    
    public PatternMatcherInput(final String s, final int n, final int n2) {
        this._matchBeginOffset = -1;
        this._matchEndOffset = -1;
        this.setInput(s, n, n2);
    }
    
    public PatternMatcherInput(final String s) {
        this(s, 0, s.length());
    }
    
    public PatternMatcherInput(final char[] array, final int n, final int n2) {
        this._matchBeginOffset = -1;
        this._matchEndOffset = -1;
        this.setInput(array, n, n2);
    }
    
    public PatternMatcherInput(final char[] array) {
        this(array, 0, array.length);
    }
    
    public int length() {
        return this._endOffset - this._beginOffset;
    }
    
    public void setInput(final String originalStringInput, final int n, final int n2) {
        this._originalStringInput = originalStringInput;
        this._originalCharInput = null;
        this._toLowerBuffer = null;
        this._originalBuffer = originalStringInput.toCharArray();
        this._currentOffset = n;
        this._matchBeginOffset = -1;
        this._matchEndOffset = -1;
        this._beginOffset = n;
        this._endOffset = this._beginOffset + n2;
    }
    
    public void setInput(final String s) {
        this.setInput(s, 0, s.length());
    }
    
    public void setInput(final char[] array, final int n, final int n2) {
        this._originalStringInput = null;
        this._toLowerBuffer = null;
        this._originalCharInput = array;
        this._originalBuffer = array;
        this._currentOffset = n;
        this._matchBeginOffset = -1;
        this._matchEndOffset = -1;
        this._beginOffset = n;
        this._endOffset = this._beginOffset + n2;
    }
    
    public void setInput(final char[] array) {
        this.setInput(array, 0, array.length);
    }
    
    public char charAt(final int n) {
        return this._originalBuffer[this._beginOffset + n];
    }
    
    public String substring(final int n, final int n2) {
        return new String(this._originalBuffer, this._beginOffset + n, n2 - n);
    }
    
    public String substring(int offset) {
        offset += this._beginOffset;
        return new String(this._originalBuffer, offset, this._endOffset - offset);
    }
    
    public Object getInput() {
        if (this._originalStringInput == null) {
            return this._originalCharInput;
        }
        return this._originalStringInput;
    }
    
    public char[] getBuffer() {
        return this._originalBuffer;
    }
    
    public boolean endOfInput() {
        return this._currentOffset >= this._endOffset;
    }
    
    public int getBeginOffset() {
        return this._beginOffset;
    }
    
    public int getEndOffset() {
        return this._endOffset;
    }
    
    public int getCurrentOffset() {
        return this._currentOffset;
    }
    
    public void setBeginOffset(final int beginOffset) {
        this._beginOffset = beginOffset;
    }
    
    public void setEndOffset(final int endOffset) {
        this._endOffset = endOffset;
    }
    
    public void setCurrentOffset(final int currentOffset) {
        this._currentOffset = currentOffset;
        this._matchBeginOffset = -1;
        this._matchEndOffset = -1;
    }
    
    public String toString() {
        return new String(this._originalBuffer, this._beginOffset, this._endOffset - this._beginOffset);
    }
    
    public String preMatch() {
        return new String(this._originalBuffer, this._beginOffset, this._matchBeginOffset - this._beginOffset);
    }
    
    public String postMatch() {
        return new String(this._originalBuffer, this._matchEndOffset, this._endOffset - this._matchEndOffset);
    }
    
    public String match() {
        return new String(this._originalBuffer, this._matchBeginOffset, this._matchEndOffset - this._matchBeginOffset);
    }
    
    public void setMatchOffsets(final int matchBeginOffset, final int matchEndOffset) {
        this._matchBeginOffset = matchBeginOffset;
        this._matchEndOffset = matchEndOffset;
    }
    
    public int getMatchBeginOffset() {
        return this._matchBeginOffset;
    }
    
    public int getMatchEndOffset() {
        return this._matchEndOffset;
    }
}
