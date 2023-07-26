// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.print;

import com.ms.win32.Gdi32;
import com.ms.wfc.ui.Graphics;

class PrinterGraphics extends Graphics
{
    PrinterGraphics(final int n) {
        super(n);
    }
    
    protected void destroyHandle() {
        Gdi32.DeleteDC(this.getHandle());
        super.destroyHandle();
    }
}
