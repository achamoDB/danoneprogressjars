// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.event;

import java.util.EventObject;

public abstract class ProEvent extends EventObject
{
    public ProEvent(final Object o) {
        super((o != null) ? o : ProEvent.class);
    }
}
