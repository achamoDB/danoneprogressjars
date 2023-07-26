// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.util.TimerEvent;
import com.ms.util.TimerListener;

class RectTracker$TimerListener implements TimerListener
{
    final RectTracker \u0190;
    
    RectTracker$TimerListener(final RectTracker \u025b) {
        (this.\u0190 = \u025b).getClass();
    }
    
    public void timeTriggered(final TimerEvent timerEvent) {
        this.\u0190.\u0193();
    }
}
