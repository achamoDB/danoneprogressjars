// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.util.TimerEvent;
import com.ms.util.TimerListener;

class ImageBtn$TimerListener implements TimerListener
{
    final ImageBtn \u0190;
    
    ImageBtn$TimerListener(final ImageBtn \u025b) {
        (this.\u0190 = \u025b).getClass();
    }
    
    public void timeTriggered(final TimerEvent timerEvent) {
        this.\u0190.\u0193();
    }
}
