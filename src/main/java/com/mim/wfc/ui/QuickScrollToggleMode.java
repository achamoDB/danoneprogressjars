// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.wfc.core.Enum;

public class QuickScrollToggleMode extends Enum
{
    public static final int NONE = 0;
    public static final int MOUSE_LEFT = 1;
    public static final int MOUSE_RIGHT = 2;
    public static final int MOUSE_MIDDLE = 3;
    
    public static boolean valid(final int n) {
        return n >= 0 && n <= 3;
    }
}
