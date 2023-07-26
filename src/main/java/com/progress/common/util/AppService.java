// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.util;

public class AppService
{
    private static char[] invalidChars;
    
    public static boolean validateName(final String s) {
        for (int i = 0; i < AppService.invalidChars.length; ++i) {
            if (s.indexOf(AppService.invalidChars[i]) != -1) {
                return false;
            }
        }
        return true;
    }
    
    static {
        AppService.invalidChars = new char[] { '$', '@', '{', '}', '/', '\\', '\"', ',', ' ' };
    }
}
