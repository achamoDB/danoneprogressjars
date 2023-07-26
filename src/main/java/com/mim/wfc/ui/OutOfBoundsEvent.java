// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.wfc.ui.Point;
import com.ms.wfc.core.Event;

public class OutOfBoundsEvent extends Event
{
    RectTracker \u01c9;
    Point \u01cc;
    
    public OutOfBoundsEvent(final RectTracker \u01c9, final Point \u01cc) {
        this.\u01c9 = \u01c9;
        this.\u01cc = \u01cc;
    }
    
    public RectTracker getTracker() {
        return this.\u01c9;
    }
    
    public Point getOffset() {
        return this.\u01cc;
    }
}
