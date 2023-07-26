// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.shell;

import com.mim.wfc.license._cls753A;
import com.ms.wfc.ui.Control;

public class Shell
{
    public static final int SHOW_HIDE = 0;
    public static final int SHOW_MAXIMIZE = 3;
    public static final int SHOW_MINIMIZE = 6;
    public static final int SHOW_RESTORE = 9;
    public static final int SHOW_SHOW = 5;
    public static final int SHOW_SHOWDEFAULT = 10;
    public static final int SHOW_SHOWMAXIMIZED = 3;
    public static final int SHOW_SHOWMINIMIZED = 2;
    public static final int SHOW_SHOWMINNOACTIVE = 7;
    public static final int SHOW_SHOWNA = 8;
    public static final int SHOW_SHOWNOACTIVATE = 4;
    public static final int SHOW_SHOWNORMAL = 1;
    
    public static boolean execute(final Control control, final String s, final String s2, final String s3, final String s4, final int n) {
        return ShellExecute((control != null) ? control.getHandle() : 0, s, s2, s3, s4, n) > 32;
    }
    
    private static native int ShellExecute(final int p0, final String p1, final String p2, final String p3, final String p4, final int p5);
    
    static {
        _cls753A._mth821F();
    }
}
