// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.wfc.core.Enum;

public class ViewPanelZoomMode extends Enum
{
    public static final int NONE = 0;
    public static final int ZOOM = 1;
    public static final int ZOOM_FIT = 2;
    public static final int ZOOM_FIT_HORIZONTAL = 3;
    public static final int ZOOM_FIT_VERTICAL = 4;
    
    public static boolean valid(final int n) {
        return n >= 0 && n <= 4;
    }
}
