// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools;

public class LoadablePlatform
{
    static final String WIN95_OR_98 = "Windows 95";
    static final String[] unsupportedOSId;
    
    public static boolean validate() {
        final String property = System.getProperty("os.name");
        int n = 0;
        int n2 = 0;
        while (n2 != LoadablePlatform.unsupportedOSId.length && n == 0) {
            if (property.equals(LoadablePlatform.unsupportedOSId[n2])) {
                n = 1;
            }
            else {
                ++n2;
            }
        }
        return n == 0;
    }
    
    public static String getInvalidOSStr() {
        String s = null;
        final String str = ", ";
        for (int i = 0; i != LoadablePlatform.unsupportedOSId.length; ++i) {
            if (i == 0 || i == LoadablePlatform.unsupportedOSId.length) {
                s += LoadablePlatform.unsupportedOSId[i];
            }
            else {
                s = s + str + LoadablePlatform.unsupportedOSId[i];
            }
        }
        return s;
    }
    
    static {
        unsupportedOSId = new String[] { "Windows 95" };
    }
}
