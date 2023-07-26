// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.wfc.core.Enum;

public class ViewPanelMode extends Enum
{
    public static final int NONE = 0;
    public static final int SCROLL = 1;
    public static final int AUTO_SCROLL = 2;
    
    public static boolean valid(final int n) {
        return n >= 0 && n <= 2;
    }
}
