// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa.tools;

import java.io.Serializable;

public class PropElement implements Serializable
{
    Object key;
    Object value;
    
    public PropElement(final Object key, final Object value) {
        this.key = key;
        this.value = value;
    }
    
    public Object key() {
        return this.key;
    }
    
    public Object value() {
        return this.value;
    }
}
