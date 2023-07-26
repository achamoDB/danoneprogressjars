// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.wfc.core.Enum;

public class ImageBtnBorderDisplay extends Enum
{
    public static final int NEVER = 0;
    public static final int ALWAYS = 1;
    public static final int WHEN_HOVERED = 2;
    
    public static boolean valid(final int n) {
        return n >= 0 && n <= 2;
    }
}
