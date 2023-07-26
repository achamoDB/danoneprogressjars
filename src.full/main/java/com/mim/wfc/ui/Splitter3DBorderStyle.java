// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.wfc.core.Enum;

public class Splitter3DBorderStyle extends Enum
{
    public static final int NONE = 0;
    public static final int SINGLELINE = 1;
    public static final int RAISED3D = 2;
    public static final int SUNKEN3D = 3;
    
    public static boolean valid(final int n) {
        return n >= 0 && n <= 3;
    }
}
