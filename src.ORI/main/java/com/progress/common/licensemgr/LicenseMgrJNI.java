// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.licensemgr;

import com.progress.common.util.InstallPath;

public class LicenseMgrJNI
{
    public native int wsprocfgVersionCheck(final int p0, final int p1);
    
    public native String getCompanyName();
    
    public native int wsprocfgDllInit(final String p0);
    
    public native int checkR2Run(final int p0, final int p1);
    
    public native String getProductInfo(final int p0);
    
    public native int procfgInit2(final String p0);
    
    public native int procfgTerm2(final int p0);
    
    public native String getProductInfo2(final int p0, final int p1);
    
    public native int getMaxUserCnt2(final int p0, final int p1);
    
    public native int checkR2Run2(final int p0, final int p1, final int p2);
    
    public native int checkExpiration2(final int p0, final int p1);
    
    public int wsprocfgVersionCheckJNI(final int n, final int n2) {
        return this.wsprocfgVersionCheck(n, n2);
    }
    
    public String getCompanyNameJNI() {
        return this.getCompanyName();
    }
    
    public int wsprocfgDllInitJNI(final String s) {
        return this.wsprocfgDllInit(s);
    }
    
    public int checkR2RunJNI(final int n, final int n2) {
        return this.checkR2Run(n, n2);
    }
    
    public String getProductInfoJNI(final int n) {
        return this.getProductInfo(n);
    }
    
    public int procfgInit2JNI(final String s) {
        return this.procfgInit2(s);
    }
    
    public int procfgTerm2JNI(final int n) {
        return this.procfgTerm2(n);
    }
    
    public String getProductInfo2JNI(final int n, final int n2) {
        return this.getProductInfo2(n, n2);
    }
    
    public int getMaxUserCnt2JNI(final int n, final int n2) {
        return this.getMaxUserCnt2(n, n2);
    }
    
    public int checkR2Run2JNI(final int n, final int n2, final int n3) {
        return this.checkR2Run2(n, n2, n3);
    }
    
    public int checkExpiration2JNI(final int n, final int n2) {
        return this.checkExpiration2(n, n2);
    }
    
    static {
        System.load(new InstallPath().fullyQualifyFile("procfg.dll"));
    }
}
