// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.wfc.core.Enum;

public class ControlColorStyle extends Enum
{
    public static final int BLACK = 0;
    public static final int BLUE = 1;
    public static final int GREEN = 2;
    public static final int RED = 3;
    public static final int YELLOW = 4;
    
    public static boolean valid(final int n) {
        return n >= 0 && n <= 4;
    }
}
