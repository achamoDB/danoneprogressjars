// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools;

class KeepWatchingFlag
{
    private boolean keepWatching;
    
    KeepWatchingFlag() {
        this.keepWatching = true;
    }
    
    public synchronized void setKeepWatching(final boolean keepWatching) {
        this.keepWatching = keepWatching;
    }
    
    public synchronized boolean isKeepWatching() {
        return this.keepWatching;
    }
}
