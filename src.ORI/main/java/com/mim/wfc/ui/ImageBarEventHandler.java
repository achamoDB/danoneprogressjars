// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.lang.MulticastDelegate;

public final class ImageBarEventHandler extends MulticastDelegate
{
    public void invoke(final Object o, final ImageBarEvent imageBarEvent) {
        this.invokeHelperMulticast(new Object[] { o, imageBarEvent });
    }
    
    public ImageBarEventHandler(final Object o, final String s) {
        super(o, s, "(Ljava/lang/Object;Lcom/mim/wfc/ui/ImageBarEvent;)V");
    }
}
