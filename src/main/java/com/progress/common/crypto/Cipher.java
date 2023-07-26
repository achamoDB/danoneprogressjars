// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.crypto;

public abstract class Cipher extends CryptoUtils
{
    public int keySize;
    
    public Cipher(final int keySize) {
        this.keySize = keySize;
    }
    
    public int keySize() {
        return this.keySize;
    }
    
    public abstract void setKey(final byte[] p0);
    
    public void setKey(final String s) {
        this.setKey(this.makeKey(s));
    }
    
    public byte[] makeKey(final String s) {
        byte[] array;
        if (this.keySize == 0) {
            array = new byte[s.length()];
        }
        else {
            array = new byte[this.keySize];
        }
        for (int i = 0; i < array.length; ++i) {
            array[i] = 0;
        }
        for (int j = 0, n = 0; j < s.length(); ++j, n = (n + 1) % array.length) {
            final byte[] array2 = array;
            final int n2 = n;
            array2[n2] ^= (byte)s.charAt(j);
        }
        return array;
    }
}
