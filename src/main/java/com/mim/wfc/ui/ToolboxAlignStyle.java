// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.wfc.core.Enum;

public class ToolboxAlignStyle extends Enum
{
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int CENTER = 3;
    
    public static boolean valid(final int n) {
        return 1 <= n && n <= 3;
    }
}
