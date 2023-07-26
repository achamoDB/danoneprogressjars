// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.wfc.core.Enum;

public class QuickScrollActivation extends Enum
{
    public static final int NONE = 0;
    public static final int TOGGLE = 1;
    public static final int MOUSE_LEFT = 2;
    public static final int MOUSE_RIGHT = 4;
    public static final int MOUSE_MIDDLE = 8;
    public static final int MOUSE_LEFT_PRESS = 16;
    public static final int MOUSE_RIGHT_PRESS = 32;
    public static final int MOUSE_MIDDLE_PRESS = 64;
    
    public static boolean valid(final int n) {
        return n >= 0 && n < 128;
    }
}
