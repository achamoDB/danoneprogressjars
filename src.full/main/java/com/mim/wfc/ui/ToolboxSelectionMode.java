// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.wfc.core.Enum;

public class ToolboxSelectionMode extends Enum
{
    public static final int NONE = 0;
    public static final int RADIO = 1;
    public static final int SINGLE = 2;
    public static final int MULTIPLE = 3;
    public static final int STATIC = 4;
    
    public static boolean valid(final int n) {
        return n >= 0 && n <= 4;
    }
}
