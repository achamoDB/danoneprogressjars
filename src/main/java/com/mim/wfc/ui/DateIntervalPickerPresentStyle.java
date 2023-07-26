// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.wfc.core.Enum;

public class DateIntervalPickerPresentStyle extends Enum
{
    public static final int PAST = 1;
    public static final int WHOLE = 2;
    public static final int FUTURE = 3;
    
    public static boolean valid(final int n) {
        return 1 <= n && n <= 3;
    }
}
