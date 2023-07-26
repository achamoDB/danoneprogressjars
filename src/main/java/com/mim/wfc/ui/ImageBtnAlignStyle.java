// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.wfc.core.Enum;

public class ImageBtnAlignStyle extends Enum
{
    public static final int LEFTMOST = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    public static final int RIGHTMOST = 4;
    public static final int CENTER = 5;
    
    public static boolean valid(final int n) {
        return 1 <= n && n <= 5;
    }
}
