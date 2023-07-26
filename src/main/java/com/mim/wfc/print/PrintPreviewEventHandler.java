// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.print;

import com.ms.lang.MulticastDelegate;

public final class PrintPreviewEventHandler extends MulticastDelegate
{
    public void invoke(final Object o, final PrintPreviewEvent printPreviewEvent) {
        this.invokeHelperMulticast(new Object[] { o, printPreviewEvent });
    }
    
    public PrintPreviewEventHandler(final Object o, final String s) {
        super(o, s, "(Ljava/lang/Object;Lcom/mim/wfc/print/PrintPreviewEvent;)V");
    }
}
