// 
// Decompiled by Procyon v0.5.36
// 

package com.oroinc.text.regex;

final class OpCode
{
    static final char _END = '\0';
    static final char _BOL = '\u0001';
    static final char _MBOL = '\u0002';
    static final char _SBOL = '\u0003';
    static final char _EOL = '\u0004';
    static final char _MEOL = '\u0005';
    static final char _SEOL = '\u0006';
    static final char _ANY = '\u0007';
    static final char _SANY = '\b';
    static final char _ANYOF = '\t';
    static final char _CURLY = '\n';
    static final char _CURLYX = '\u000b';
    static final char _BRANCH = '\f';
    static final char _BACK = '\r';
    static final char _EXACTLY = '\u000e';
    static final char _NOTHING = '\u000f';
    static final char _STAR = '\u0010';
    static final char _PLUS = '\u0011';
    static final char _ALNUM = '\u0012';
    static final char _NALNUM = '\u0013';
    static final char _BOUND = '\u0014';
    static final char _NBOUND = '\u0015';
    static final char _SPACE = '\u0016';
    static final char _NSPACE = '\u0017';
    static final char _DIGIT = '\u0018';
    static final char _NDIGIT = '\u0019';
    static final char _REF = '\u001a';
    static final char _OPEN = '\u001b';
    static final char _CLOSE = '\u001c';
    static final char _MINMOD = '\u001d';
    static final char _GBOL = '\u001e';
    static final char _IFMATCH = '\u001f';
    static final char _UNLESSM = ' ';
    static final char _SUCCEED = '!';
    static final char _WHILEM = '\"';
    static final int[] _operandLength;
    static final char[] _opType;
    static final char[] _opLengthVaries;
    static final char[] _opLengthOne;
    static final int _NULL_OFFSET = -1;
    static final char _NULL_POINTER = '\0';
    
    static final int _getNextOffset(final char[] array, final int n) {
        return array[n + 1];
    }
    
    static final char _getArg1(final char[] array, final int n) {
        return array[n + 2];
    }
    
    static final char _getArg2(final char[] array, final int n) {
        return array[n + 3];
    }
    
    static final int _getOperand(final int n) {
        return n + 2;
    }
    
    static final boolean _isInArray(final char c, final char[] array, int i) {
        while (i < array.length) {
            if (c == array[i++]) {
                return true;
            }
        }
        return false;
    }
    
    static final int _getNextOperator(final int n) {
        return n + 2;
    }
    
    static final int _getPrevOperator(final int n) {
        return n - 2;
    }
    
    static final int _getNext(final char[] array, final int n) {
        if (array == null) {
            return -1;
        }
        final char c = array[n + 1];
        if (c == '\0') {
            return -1;
        }
        if (array[n] == '\r') {
            return n - c;
        }
        return n + c;
    }
    
    static final boolean _isWordCharacter(final char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9') || c == '_';
    }
    
    static {
        _operandLength = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0 };
        _opType = new char[] { '\0', '\u0001', '\u0001', '\u0001', '\u0004', '\u0004', '\u0004', '\u0007', '\u0007', '\t', '\n', '\n', '\f', '\r', '\u000e', '\u000f', '\u0010', '\u0011', '\u0012', '\u0013', '\u0014', '\u0015', '\u0016', '\u0017', '\u0018', '\u0019', '\u001a', '\u001b', '\u001c', '\u001d', '\u0001', '\f', '\f', '\0', '\"' };
        _opLengthVaries = new char[] { '\f', '\r', '\u0010', '\u0011', '\n', '\u000b', '\u001a', '\"' };
        _opLengthOne = new char[] { '\u0007', '\b', '\t', '\u0012', '\u0013', '\u0016', '\u0017', '\u0018', '\u0019' };
    }
}
