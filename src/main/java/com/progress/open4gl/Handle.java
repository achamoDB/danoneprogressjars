// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl;

public class Handle
{
    long m_long;
    
    public Handle(final long long1) {
        this.m_long = 0L;
        this.m_long = long1;
    }
    
    public void putLong(final long long1) {
        this.m_long = long1;
    }
    
    public long getLong() {
        return this.m_long;
    }
}
