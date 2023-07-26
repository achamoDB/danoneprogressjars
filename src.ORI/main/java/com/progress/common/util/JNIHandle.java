// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.util;

public class JNIHandle
{
    private long address;
    
    public JNIHandle() {
        this.setAddr(0L);
    }
    
    public JNIHandle(final long addr) {
        this.setAddr(addr);
    }
    
    public long getAddr() {
        return this.address;
    }
    
    public int getIntAddr() {
        return (int)this.address;
    }
    
    public void setAddr(final long address) {
        this.address = address;
    }
}
