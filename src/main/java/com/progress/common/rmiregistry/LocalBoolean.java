// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.rmiregistry;

class LocalBoolean
{
    boolean value;
    boolean killed;
    
    LocalBoolean(final boolean value) {
        this.value = false;
        this.killed = false;
        this.value = value;
    }
    
    boolean get() {
        return this.value;
    }
    
    void set(final boolean value) {
        this.value = value;
    }
}
