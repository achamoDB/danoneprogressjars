// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.debugger;

public class DnDUtils
{
    private static boolean debugEnabled;
    
    public static String showActions(final int n) {
        String str = "";
        if ((n & 0x40000003) == 0x0) {
            return "None";
        }
        if ((n & 0x1) != 0x0) {
            str += "Copy ";
        }
        if ((n & 0x2) != 0x0) {
            str += "Move ";
        }
        if ((n & 0x40000000) != 0x0) {
            str += "Link";
        }
        return str;
    }
    
    public static boolean isDebugEnabled() {
        return DnDUtils.debugEnabled;
    }
    
    public static void debugPrintln(final String x) {
        if (DnDUtils.debugEnabled) {
            System.out.println(x);
        }
    }
    
    static {
        DnDUtils.debugEnabled = (System.getProperty("DnDExamples.debug") != null);
    }
}
