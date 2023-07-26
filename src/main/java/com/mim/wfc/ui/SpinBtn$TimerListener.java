// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.util.TimerEvent;
import com.ms.util.TimerListener;

class SpinBtn$TimerListener implements TimerListener
{
    final SpinBtn \u0190;
    
    SpinBtn$TimerListener(final SpinBtn \u025b) {
        (this.\u0190 = \u025b).getClass();
    }
    
    public void timeTriggered(final TimerEvent timerEvent) {
        this.\u0190.\u0193();
    }
}
