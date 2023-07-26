// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.lang.MulticastDelegate;

public final class OutOfBoundsEventHandler extends MulticastDelegate
{
    public void invoke(final Object o, final OutOfBoundsEvent outOfBoundsEvent) {
        this.invokeHelperMulticast(new Object[] { o, outOfBoundsEvent });
    }
    
    public OutOfBoundsEventHandler(final Object o, final String s) {
        super(o, s, "(Ljava/lang/Object;Lcom/mim/wfc/ui/OutOfBoundsEvent;)V");
    }
}
