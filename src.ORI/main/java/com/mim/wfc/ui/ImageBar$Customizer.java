// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.wfc.ui.Control;
import com.ms.wfc.ui.Point;
import com.ms.wfc.core.Customizer;

public class ImageBar$Customizer extends Customizer
{
    final ImageBar \u0190;
    
    public ImageBar$Customizer(final ImageBar \u025b) {
        (this.\u0190 = \u025b).getClass();
    }
    
    public boolean getHitTest(final Point point) {
        return (Control.getMouseButtons() & 0x600000) == 0x0;
    }
}
