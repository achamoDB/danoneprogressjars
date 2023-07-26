// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.print;

import com.ms.wfc.core.Enum;

public class PrintRangeType extends Enum
{
    public static final int ALL_PAGES = 0;
    public static final int PAGE_NUMBERS = 2;
    public static final int SELECTION = 1;
    
    public static boolean valid(final int n) {
        return n == 0 || n == 2 || n == 1;
    }
}
