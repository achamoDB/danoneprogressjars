// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.wfc.core.Event;

public class ImageBarEvent extends Event
{
    public ImageBarItem item;
    
    public ImageBarEvent() {
    }
    
    public ImageBarEvent(final ImageBarItem item) {
        this.item = item;
    }
}
