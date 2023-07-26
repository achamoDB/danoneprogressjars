// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.wfc.ui.Control;
import com.ms.lang.Delegate;
import com.ms.wfc.ui.Point;
import com.ms.wfc.core.CancelEvent;
import com.ms.wfc.core.CancelEventHandler;

public class WizardPage extends ViewPanel
{
    private CancelEventHandler \u02b1;
    private int \u02d1;
    
    protected void onActivatePage(final CancelEvent cancelEvent) {
        if (this.\u02b1 != null) {
            this.\u02b1.invoke((Object)this, cancelEvent);
        }
    }
    
    public WizardPage() {
        this.\u02d1 = -1;
        ((Control)this).setStyle(8, true);
        ((Control)this).setSize(new Point(40, 40));
        ((Control)this).setDock(5);
        this.setMode(2);
    }
    
    public void removeOnActivatePage(final CancelEventHandler cancelEventHandler) {
        this.\u02b1 = (CancelEventHandler)Delegate.remove((Delegate)this.\u02b1, (Delegate)cancelEventHandler);
    }
    
    public void addOnActivatePage(final CancelEventHandler cancelEventHandler) {
        this.\u02b1 = (CancelEventHandler)Delegate.combine((Delegate)this.\u02b1, (Delegate)cancelEventHandler);
    }
    
    void \u02d1(final int \u02d1) {
        this.\u02d1 = \u02d1;
    }
    
    int \u02e0() {
        return this.\u02d1;
    }
}
