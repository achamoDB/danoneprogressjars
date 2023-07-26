// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.crypto;

public abstract class StreamCipher extends Cipher
{
    public StreamCipher(final int n) {
        super(n);
    }
    
    public abstract byte encrypt(final byte p0);
    
    public abstract byte decrypt(final byte p0);
    
    public void encrypt(final byte[] array, final byte[] array2) {
        this.encrypt(array, 0, array2, 0, array.length);
    }
    
    public void decrypt(final byte[] array, final byte[] array2) {
        this.decrypt(array, 0, array2, 0, array.length);
    }
    
    public void encrypt(final byte[] array, final int n, final byte[] array2, final int n2, final int n3) {
        for (int i = 0; i < n3; ++i) {
            array2[n2 + i] = this.encrypt(array[n + i]);
        }
    }
    
    public void decrypt(final byte[] array, final int n, final byte[] array2, final int n2, final int n3) {
        for (int i = 0; i < n3; ++i) {
            array2[n2 + i] = this.decrypt(array[n + i]);
        }
    }
}
