// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.broker;

class Util
{
    static int extractShort(final byte b, final byte b2) {
        return (b & 0xFF) * 256 + (b2 & 0xFF);
    }
    
    static byte getLowByte(final short n) {
        return (byte)(n & 0xFF);
    }
    
    static byte getHighByte(final short n) {
        return (byte)(n >> 8);
    }
}
