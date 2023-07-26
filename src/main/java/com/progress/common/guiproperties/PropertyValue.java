// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.guiproperties;

import java.util.Vector;
import java.io.Serializable;

public class PropertyValue implements Serializable
{
    Vector viewers;
    protected Object localValue;
    protected boolean hasLocalValue;
    
    public PropertyValue(final Object localValue) {
        this.viewers = null;
        this.localValue = localValue;
        this.hasLocalValue = true;
    }
    
    public PropertyValue() {
        this.viewers = null;
        this.localValue = null;
        this.hasLocalValue = false;
    }
}
