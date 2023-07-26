// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.util;

import java.util.StringTokenizer;

public class PatchValidation
{
    private String ret_str;
    private String curVer;
    private String minVer;
    private String retCode;
    
    public PatchValidation() {
        this.ret_str = null;
        this.curVer = null;
        this.minVer = null;
        this.retCode = null;
    }
    
    public native String validatePatchlevel(final String p0, final String p1);
    
    public boolean validPatchlevel(final String s, final String s2) {
        int int1 = 0;
        this.ret_str = this.validatePatchlevel(s, s2);
        boolean b;
        if (this.ret_str == null) {
            b = false;
        }
        else {
            final StringTokenizer stringTokenizer = new StringTokenizer(this.ret_str, "+");
            if (stringTokenizer.countTokens() != 3) {
                b = false;
            }
            else {
                this.retCode = stringTokenizer.nextToken();
                this.curVer = stringTokenizer.nextToken();
                this.minVer = stringTokenizer.nextToken();
                try {
                    int1 = Integer.parseInt(this.retCode);
                }
                catch (NumberFormatException ex) {}
                b = (int1 == 1);
            }
        }
        return b;
    }
    
    public String getCurVer() {
        return this.curVer;
    }
    
    public String getMinVer() {
        return this.minVer;
    }
    
    static {
        System.load(new InstallPath().fullyQualifyFile("procfg.dll"));
    }
}
