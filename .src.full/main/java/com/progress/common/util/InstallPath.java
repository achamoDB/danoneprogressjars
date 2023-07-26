// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.util;

import com.progress.common.text.UnquotedString;

public class InstallPath
{
    private static String installPath;
    private String debugPath;
    
    public static void setInstallPath(final String installPath) {
        InstallPath.installPath = installPath;
    }
    
    public String getInstallPath() {
        if (InstallPath.installPath == null) {
            InstallPath.installPath = new UnquotedString(System.getProperty("Install.Dir")).toString();
        }
        return InstallPath.installPath;
    }
    
    public String fullyQualifyFile(final String str, final String str2) {
        final String installPath = this.getInstallPath();
        final String property = System.getProperty("file.separator");
        return installPath + property + str2 + property + str;
    }
    
    public String fullyQualifyFile(final String str) {
        String s;
        if (this.debugPath == null) {
            s = this.fullyQualifyFile(str, "bin");
        }
        else {
            s = this.debugPath + str;
        }
        return s;
    }
    
    public InstallPath() {
        this.debugPath = null;
        this.debugPath = null;
    }
    
    public InstallPath(final String debugPath) {
        this.debugPath = null;
        this.debugPath = debugPath;
    }
    
    static {
        InstallPath.installPath = null;
    }
}
