// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl;

public class Memptr
{
    public static final int USER = 1;
    public static final int TEMP = 2;
    public static final int LONGCHR = 4;
    public static final int CODEPAGE = 8;
    public static final int XMEMPTR = 16;
    public static final int CXMEMPTR = 20;
    public static final int HOSTBIGEND = 32;
    public static final int DSET_BI = 64;
    public static final int RPC_DSET = 128;
    public static final int SENDSQL = 4096;
    public static final int HOSTBYTEORDER = 1;
    public static final int BIGENDIAN = 2;
    public static final int LITTLEENDIAN = 3;
    public static final int MAXBUILTINBYTEORDER = 3;
    byte[] m_bytes;
    
    public Memptr(final byte[] bytes) {
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
