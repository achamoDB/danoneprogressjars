// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.wfc.ui.Control;
import com.ms.wfc.ui.PaintEvent;
import com.ms.wfc.core.WFCInvalidEnumException;
import com.ms.wfc.core.Component;
import com.ms.wfc.core.Event;
import com.mim.wfc.license._cls753A;
import com.ms.wfc.ui.Splitter;

public class Splitter3D extends Splitter
{
    private int \u025d;
    private static Class \u025e;
    
    public Splitter3D() {
        this.\u025d = 2;
        _cls753A._mth821F();
        ((Control)this).setSize(100, 10);
        ((Control)this).setStyle(2, true);
    }
    
    protected void onCreateHandle(final Event event) {
        super.onCreateHandle(event);
        _cls753A._mth563B((Component)this);
    }
    
    public int getBorderStyle() {
        return this.\u025d;
    }
    
    public void setBorderStyle(final int \u025d) {
        if (!Splitter3DBorderStyle.valid(\u025d)) {
            throw new WFCInvalidEnumException("borderStyle", \u025d, (Splitter3D.\u025e != null) ? Splitter3D.\u025e : (Splitter3D.\u025e = \u00c6("com.mim.wfc.ui.Splitter3DBorderStyle")));
        }
        this.\u025d = \u025d;
        ((Control)this).invalidate();
    }
    
    private static Class \u00c6(final String className) {
        try {
            return Class.forName(className);
        }
        catch (ClassNotFoundException ex) {
            throw new NoClassDefFoundError(ex.getMessage());
        }
    }
    
    protected void onResize(final Event event) {
        super.onResize(event);
        if (((Control)this).getCreated()) {
            ((Control)this).invalidate();
        }
    }
    
    protected void onPaint(final PaintEvent paintEvent) {
        super.onPaint(paintEvent);
        int n = 0;
        switch (this.\u025d) {
            case 1: {
                n = 16394;
                break;
            }
            case 2: {
                n = 5;
                break;
            }
            case 3: {
                n = 10;
                break;
            }
            default: {
                return;
            }
        }
        paintEvent.graphics.drawBorder3D(((Control)this).getClientRect(), n);
    }
}
