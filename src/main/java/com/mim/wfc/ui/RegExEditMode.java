// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.wfc.core.Enum;

public class RegExEditMode extends Enum
{
    public static final int MATCH_VALIDATE = 0;
    public static final int MATCH_DURING_INPUT = 1;
    public static final int MATCH_AND_COMPLETE_DURING_INPUT = 2;
    
    public static boolean valid(final int n) {
        return n >= 0 && n <= 2;
    }
}
