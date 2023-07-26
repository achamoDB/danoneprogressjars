// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.util;

public class Getopt
{
    public static final boolean DEBUG_TRACE = false;
    public static final String cmdChars = "-";
    public static final int NONOPT = -1;
    public static final char UNKOPT = '?';
    public static final char NEEDS_ARG = ':';
    private boolean ignoreCase;
    private String[] args;
    private int index;
    private String optarg;
    
    public Getopt(final String[] args) {
        this.ignoreCase = true;
        this.args = args;
        this.index = 0;
        this.optarg = "";
    }
    
    public int getOpt(final String s) {
        if (this.index >= this.args.length) {
            return -1;
        }
        int cmdChar = this.getCmdChar(this.index);
        this.optarg = ((cmdChar == -1) ? this.args[this.index] : this.getCmdString(this.index));
        ++this.index;
        if (!this.validCmd(cmdChar, s)) {
            cmdChar = 63;
        }
        else if (this.needsArg(cmdChar, s)) {
            if (this.index < this.args.length && this.getCmdChar(this.index) == -1) {
                this.optarg = this.args[this.index];
                ++this.index;
            }
            else {
                cmdChar = 63;
            }
        }
        return cmdChar;
    }
    
    public void setIgnoreCase(final boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }
    
    public boolean getIgnoreCase() {
        return this.ignoreCase;
    }
    
    public int getOpt(final GetoptList[] array) {
        if (this.index >= this.args.length) {
            return -1;
        }
        final String cmdName = this.getCmdName(this.index);
        this.optarg = ((cmdName == null) ? this.args[this.index] : this.getCmdString(this.index));
        ++this.index;
        final int validCmd;
        int access$000;
        if ((validCmd = this.validCmd(cmdName, array)) == -1) {
            access$000 = 63;
        }
        else {
            access$000 = array[validCmd].optConst;
            if (this.needsArg(validCmd, array)) {
                if (this.index < this.args.length && this.getCmdChar(this.index) == -1) {
                    this.optarg = this.args[this.index];
                    ++this.index;
                }
                else {
                    access$000 = 63;
                }
            }
        }
        return access$000;
    }
    
    public String getOptArg() {
        return this.optarg;
    }
    
    public int getOptInd() {
        return (this.index == 0) ? 0 : (this.index - 1);
    }
    
    private int getCmdChar(final int n) {
        int n2;
        if (n < 0 || n >= this.args.length || this.args[n].length() < 2) {
            n2 = -1;
        }
        else {
            try {
                n2 = (("-".indexOf(this.args[n].charAt(0)) == -1) ? -1 : this.args[n].charAt(1));
            }
            catch (StringIndexOutOfBoundsException ex) {
                n2 = -1;
            }
        }
        return n2;
    }
    
    private String getCmdName(final int n) {
        String s;
        if (n < 0 || n >= this.args.length || this.args[n].length() < 2) {
            s = null;
        }
        else {
            try {
                s = (("-".indexOf(this.args[n].charAt(0)) == -1) ? null : this.args[n].substring(1));
            }
            catch (StringIndexOutOfBoundsException ex) {
                s = null;
            }
        }
        return s;
    }
    
    private String getCmdString(final int n) {
        String substring;
        if (n < 0 || n >= this.args.length || this.args[n].length() < 2) {
            substring = "";
        }
        else {
            try {
                substring = this.args[n].substring(1);
            }
            catch (StringIndexOutOfBoundsException ex) {
                substring = "";
            }
        }
        return substring;
    }
    
    private boolean validCmd(final int ch, final String s) {
        return ch != -1 && s.indexOf(ch) != -1;
    }
    
    private int validCmd(final String s, final GetoptList[] array) {
        if (s == null) {
            return -1;
        }
        int n;
        int n2;
        try {
            n = 0;
            n2 = 0;
            int n3 = 0;
            for (String s2 = (array[n3].optName.indexOf(58) == -1) ? array[n3].optName : array[n3].optName.substring(0, array[n3].optName.length() - 1); s2.length() > 0; s2 = ((array[n3].optName.indexOf(58) == -1) ? array[n3].optName : array[n3].optName.substring(0, array[n3].optName.length() - 1))) {
                final boolean b = s.length() == s2.length();
                final String s3 = (s.length() > s2.length()) ? s2 : s2.substring(0, s.length());
                Label_0166: {
                    if (this.getIgnoreCase()) {
                        if (!s.equalsIgnoreCase(s3)) {
                            break Label_0166;
                        }
                    }
                    else if (!s.equals(s3)) {
                        break Label_0166;
                    }
                    n = n3;
                    if (b) {
                        n2 = 1;
                        break;
                    }
                    ++n2;
                }
                ++n3;
            }
        }
        catch (ArrayIndexOutOfBoundsException ex) {
            return -1;
        }
        if (n2 == 0 || n2 > 1) {
            n = -1;
        }
        return n;
    }
    
    private boolean needsArg(final int ch, final String s) {
        boolean b;
        try {
            final int index = s.indexOf(ch);
            b = (index != -1 && s.charAt(index + 1) == ':');
        }
        catch (StringIndexOutOfBoundsException ex) {
            b = false;
        }
        return b;
    }
    
    private boolean needsArg(final int n, final GetoptList[] array) {
        boolean b;
        try {
            b = (array[n].optName.indexOf(58) != -1);
        }
        catch (StringIndexOutOfBoundsException ex) {
            b = false;
        }
        catch (ArrayIndexOutOfBoundsException ex2) {
            b = false;
        }
        return b;
    }
    
    public static class GetoptList
    {
        private String optName;
        private int optConst;
        
        public GetoptList(final String optName, final int optConst) {
            this.optName = optName;
            this.optConst = optConst;
        }
    }
}
