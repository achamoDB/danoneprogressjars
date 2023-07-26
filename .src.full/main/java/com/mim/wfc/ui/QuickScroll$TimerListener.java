// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.util.TimerEvent;
import com.ms.util.TimerListener;

class QuickScroll$TimerListener implements TimerListener
{
    final QuickScroll \u0190;
    
    QuickScroll$TimerListener(final QuickScroll \u025b) {
        (this.\u0190 = \u025b).getClass();
    }
    
    public void timeTriggered(final TimerEvent timerEvent) {
        this.\u0190.\u0193();
    }
}
