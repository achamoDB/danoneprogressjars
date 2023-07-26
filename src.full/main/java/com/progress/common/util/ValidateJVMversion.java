// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.util;

public class ValidateJVMversion
{
    public boolean checkVersion() {
        final String[] array = { "SunOS", "AIX", "HP-UX", "Windows", "OSF1", "Linux", "Unixware" };
        final String[] array2 = { "1.3.0", "1.3.0", "JavaVM-1.3.0.00", "1.3.0", "1.3.0", "1.3.0", "1.2.2" };
        final String property = System.getProperty("os.name");
        final String property2 = System.getProperty("java.version");
        for (int i = 0; i < array.length; ++i) {
            if (property.startsWith(array[i]) && property2.equals(array2[i])) {
                return true;
            }
        }
        return false;
    }
}
