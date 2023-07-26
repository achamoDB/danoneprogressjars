// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.data;

import com.ms.wfc.core.Enum;

public class DataManagerDebug extends Enum
{
    public static final int NONE = 0;
    public static final int BEFORE_EXPANSION = 1;
    public static final int AFTER_EXPANSION = 2;
    public static final int BEFORE_AND_AFTER_EXPANSION = 3;
    
    public static boolean valid(final int n) {
        return n >= 0 && n <= 3;
    }
}
