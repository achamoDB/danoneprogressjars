// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.proxygen;

import java.util.StringTokenizer;
import java.io.IOException;
import java.io.File;

public class PGUtils
{
    public static final int OS_WINDOWS = 1;
    public static final int OS_UNIX = 2;
    public static final int OS_CURRENT = 3;
    public static final String OS_FILESEP = "file.separator";
    public static final boolean BOOL_OSWINDOWS;
    
    public static String getAbsFilename(final String s, final String pathname) {
        File file;
        String s2;
        if (s != null) {
            file = new File(s, pathname);
            s2 = s + pathname;
        }
        else {
            file = new File(pathname);
            s2 = pathname;
        }
        try {
            if (file != null) {
                s2 = file.getCanonicalPath();
            }
            return s2;
        }
        catch (IOException ex) {
            return s2;
        }
    }
    
    public static String checkDir(final String s) {
        String s2 = null;
        if (s != null) {
            final File file = new File(getAbsFilename(null, s));
            if (!file.exists() || !file.isDirectory()) {
                s2 = s;
            }
        }
        return s2;
    }
    
    public static String fixOSPath(final int n, final String str, final boolean b) {
        String s = "";
        String s2;
        if (str == null) {
            s2 = null;
        }
        else if (n == 1 || (n == 3 && b)) {
            s2 = str.replace('/', '\\');
        }
        else {
            final StringTokenizer stringTokenizer = new StringTokenizer(str, ":", false);
            while (stringTokenizer.hasMoreTokens()) {
                s = new String(stringTokenizer.nextToken());
            }
            s2 = s.replace('\\', '/');
        }
        return s2;
    }
    
    public static String fixOSPaths(final int n, final String str, final boolean b) {
        String str2;
        if (n == 1 || (n == 3 && b)) {
            str2 = ";";
        }
        else {
            str2 = ":";
        }
        String s = "";
        final StringTokenizer stringTokenizer = new StringTokenizer(str, ";");
        while (stringTokenizer.hasMoreTokens()) {
            final String nextToken = stringTokenizer.nextToken();
            if (!s.equals("")) {
                s += str2;
            }
            s += fixOSPath(n, nextToken, b);
        }
        return s;
    }
    
    static {
        BOOL_OSWINDOWS = (System.getProperty("os.name").indexOf("Windows") >= 0);
    }
}
