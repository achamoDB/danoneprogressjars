// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.util;

public class Error
{
    private RegExNode \u03d3;
    private RegExNode \u03d4;
    private int \u03d5;
    private static boolean \u03d6;
    private static final int \u03e2 = 256;
    private static final int \u03e3 = 512;
    private static final int \u03e4 = 1024;
    private static final int \u03e5 = 2048;
    private static final int \u03e6 = 4096;
    private static final int \u03e7 = 8192;
    private static final int \u03e8 = 255;
    
    public static native int FormatMessage(final int p0, final int p1, final int p2, final int p3, final StringBuffer p4, final int p5, final int p6);
    
    public static String getSystemErrorMessage(final int n) {
        final StringBuffer sb = new StringBuffer(1024);
        if (FormatMessage(4608, 0, n, 0, sb, 1023, 0) == 0) {
            return null;
        }
        return sb.toString();
    }
}
