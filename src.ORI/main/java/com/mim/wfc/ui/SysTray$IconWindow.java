// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.wfc.app.Message;
import com.ms.vm.WeakReference;
import com.ms.wfc.app.Window;

class SysTray$IconWindow extends Window
{
    WeakReference \u0260;
    
    SysTray$IconWindow(final SysTray sysTray) {
        this.\u0260 = new WeakReference((Object)sysTray);
    }
    
    protected void wndProc(final Message message) {
        ((SysTray)this.\u0260.getReferent()).wndProc(message);
        super.wndProc(message);
    }
}
