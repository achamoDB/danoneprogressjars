// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.rmiregistry;

class SyncObj
{
    boolean value;
    boolean completed;
    boolean killed;
    Exception exception;
    
    SyncObj() {
        this.value = false;
        this.completed = false;
        this.killed = false;
        this.exception = null;
    }
    
    boolean get() {
        return this.value;
    }
    
    void set(final boolean value) {
        this.value = value;
    }
    
    void setCompleted(final boolean completed) {
        this.completed = completed;
    }
    
    boolean completed() {
        return this.completed;
    }
    
    boolean killed() {
        return this.killed;
    }
    
    void setKilled(final boolean killed) {
        this.killed = killed;
    }
    
    void setException(final Exception exception) {
        this.exception = exception;
    }
    
    Exception exception() {
        return this.exception;
    }
}
