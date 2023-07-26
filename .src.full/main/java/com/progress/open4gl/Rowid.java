// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl;

public class Rowid
{
    byte[] m_bytes;
    
    public Rowid(final byte[] bytes) {
        this.m_bytes = null;
        this.m_bytes = bytes;
    }
    
    public void putBytes(final byte[] bytes) {
        this.m_bytes = bytes;
    }
    
    public byte[] getBytes() {
        return this.m_bytes;
    }
}
