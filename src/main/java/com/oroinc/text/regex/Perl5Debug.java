// 
// Decompiled by Procyon v0.5.36
// 

package com.oroinc.text.regex;

public final class Perl5Debug
{
    private Perl5Debug() {
    }
    
    public static String printProgram(final Perl5Pattern perl5Pattern) {
        int i = 27;
        final char[] program = perl5Pattern._program;
        int j = 1;
        final StringBuffer sb = new StringBuffer();
        while (i != 0) {
            i = program[j];
            sb.append(j);
            _printOperator(program, j, sb);
            final int getNext = OpCode._getNext(program, j);
            j += OpCode._operandLength[i];
            sb.append("(" + getNext + ")");
            j += 2;
            if (i == 9) {
                j += 16;
            }
            else if (i == 14) {
                ++j;
                sb.append(" <");
                while (program[j] != '\uffff') {
                    sb.append(program[j]);
                    ++j;
                }
                sb.append(">");
                ++j;
            }
            sb.append('\n');
        }
        if (perl5Pattern._startString != null) {
            sb.append("start `" + new String(perl5Pattern._startString) + "' ");
        }
        if (perl5Pattern._startClassOffset != -1) {
            sb.append("stclass `");
            _printOperator(program, perl5Pattern._startClassOffset, sb);
            sb.append("' ");
        }
        if ((perl5Pattern._anchor & 0x1) != 0x0) {
            sb.append("anchored ");
        }
        if ((perl5Pattern._anchor & 0x2) != 0x0) {
            sb.append("plus ");
        }
        if ((perl5Pattern._anchor & 0x4) != 0x0) {
            sb.append("implicit ");
        }
        if (perl5Pattern._mustString != null) {
            sb.append("must have \"" + new String(perl5Pattern._mustString) + "\" back " + perl5Pattern._back + " ");
        }
        sb.append("minlen " + perl5Pattern._minLength + '\n');
        return sb.toString();
    }
    
    static void _printOperator(final char[] array, final int n, final StringBuffer sb) {
        String str = null;
        sb.append(":");
        switch (array[n]) {
            case '\u0001': {
                str = "BOL";
                break;
            }
            case '\u0002': {
                str = "MBOL";
                break;
            }
            case '\u0003': {
                str = "SBOL";
                break;
            }
            case '\u0004': {
                str = "EOL";
                break;
            }
            case '\u0005': {
                str = "MEOL";
                break;
            }
            case '\u0007': {
                str = "ANY";
                break;
            }
            case '\b': {
                str = "SANY";
                break;
            }
            case '\t': {
                str = "ANYOF";
                break;
            }
            case '\f': {
                str = "BRANCH";
                break;
            }
            case '\u000e': {
                str = "EXACTLY";
                break;
            }
            case '\u000f': {
                str = "NOTHING";
                break;
            }
            case '\r': {
                str = "BACK";
                break;
            }
            case '\0': {
                str = "END";
                break;
            }
            case '\u0012': {
                str = "ALNUM";
                break;
            }
            case '\u0013': {
                str = "NALNUM";
                break;
            }
            case '\u0014': {
                str = "BOUND";
                break;
            }
            case '\u0015': {
                str = "NBOUND";
                break;
            }
            case '\u0016': {
                str = "SPACE";
                break;
            }
            case '\u0017': {
                str = "NSPACE";
                break;
            }
            case '\u0018': {
                str = "DIGIT";
                break;
            }
            case '\u0019': {
                str = "NDIGIT";
                break;
            }
            case '\n': {
                sb.append("CURLY {");
                sb.append((int)OpCode._getArg1(array, n));
                sb.append(',');
                sb.append((int)OpCode._getArg2(array, n));
                sb.append('}');
                break;
            }
            case '\u000b': {
                sb.append("CURLYX {");
                sb.append((int)OpCode._getArg1(array, n));
                sb.append(',');
                sb.append((int)OpCode._getArg2(array, n));
                sb.append('}');
                break;
            }
            case '\u001a': {
                sb.append("REF");
                sb.append((int)OpCode._getArg1(array, n));
                break;
            }
            case '\u001b': {
                sb.append("OPEN");
                sb.append((int)OpCode._getArg1(array, n));
                break;
            }
            case '\u001c': {
                sb.append("CLOSE");
                sb.append((int)OpCode._getArg1(array, n));
                break;
            }
            case '\u0010': {
                str = "STAR";
                break;
            }
            case '\u0011': {
                str = "PLUS";
                break;
            }
            case '\u001d': {
                str = "MINMOD";
                break;
            }
            case '\u001e': {
                str = "GBOL";
                break;
            }
            case ' ': {
                str = "UNLESSM";
                break;
            }
            case '\u001f': {
                str = "IFMATCH";
                break;
            }
            case '!': {
                str = "SUCCEED";
                break;
            }
            case '\"': {
                str = "WHILEM";
                break;
            }
            default: {
                sb.append("Operator is unrecognized.  Faulty expression code!");
                break;
            }
        }
        if (str != null) {
            sb.append(str);
        }
    }
}
