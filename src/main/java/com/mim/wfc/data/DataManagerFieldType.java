// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.data;

import com.ms.wfc.core.Enum;

public class DataManagerFieldType extends Enum
{
    public static final int Auto = 0;
    public static final int String = 8;
    public static final int Boolean = 11;
    public static final int Int = 3;
    public static final int Short = 2;
    public static final int Byte = 17;
    public static final int Double = 5;
    public static final int Float = 4;
    public static final int Currency = 6;
    public static final int Date = 7;
    public static final int ByteArray = 8209;
    public static final int DispatchObject = 9;
    
    public static boolean valid(final int n) {
        return n == 0 || n == 8 || n == 11 || n == 3 || n == 2 || n == 17 || n == 5 || n == 4 || n == 6 || n == 7 || n == 8209 || n == 9;
    }
}
