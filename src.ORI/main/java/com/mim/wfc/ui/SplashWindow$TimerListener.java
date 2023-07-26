// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.util.TimerEvent;
import com.ms.util.TimerListener;

class SplashWindow$TimerListener implements TimerListener
{
    final SplashWindow \u0190;
    
    SplashWindow$TimerListener(final SplashWindow \u025b) {
        (this.\u0190 = \u025b).getClass();
    }
    
    public void timeTriggered(final TimerEvent timerEvent) {
        this.\u0190.\u0193();
    }
}
