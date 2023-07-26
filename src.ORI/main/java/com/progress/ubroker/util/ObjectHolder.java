// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.util;

public class ObjectHolder
{
    private Object obj;
    
    public ObjectHolder() {
        this.obj = null;
    }
    
    public ObjectHolder(final Object obj) {
        this.obj = obj;
    }
    
    public void setObject(final Object obj) {
        this.obj = obj;
    }
    
    public Object getObject() {
        return this.obj;
    }
    
    public boolean isNull() {
        return this.obj == null;
    }
}
