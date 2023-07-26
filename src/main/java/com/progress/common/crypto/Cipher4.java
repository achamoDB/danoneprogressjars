// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.crypto;

public class Cipher4 extends StreamCipher
{
    private byte[] state;
    private int x;
    private int y;
    
    public Cipher4(final String key) {
        super(256);
        this.state = new byte[256];
        this.setKey(key);
    }
    
    public Cipher4(final byte[] key) {
        super(256);
        this.state = new byte[256];
        this.setKey(key);
    }
    
    public void setKey(final byte[] array) {
        for (int i = 0; i < 256; ++i) {
            this.state[i] = (byte)i;
        }
        this.x = 0;
        this.y = 0;
        int n = 0;
        int n2 = 0;
        for (int j = 0; j < 256; ++j) {
            n2 = (array[n] + this.state[j] + n2 & 0xFF);
            final byte b = this.state[j];
            this.state[j] = this.state[n2];
            this.state[n2] = b;
            n = (n + 1) % array.length;
        }
    }
    
    public byte encrypt(final byte b) {
        return (byte)(b ^ this.state[this.nextState()]);
    }
    
    public byte decrypt(final byte b) {
        return (byte)(b ^ this.state[this.nextState()]);
    }
    
    public void encrypt(final byte[] array, final int n, final byte[] array2, final int n2, final int n3) {
        for (int i = 0; i < n3; ++i) {
            array2[n2 + i] = (byte)(array[n + i] ^ this.state[this.nextState()]);
        }
    }
    
    public void decrypt(final byte[] array, final int n, final byte[] array2, final int n2, final int n3) {
        for (int i = 0; i < n3; ++i) {
            array2[n2 + i] = (byte)(array[n + i] ^ this.state[this.nextState()]);
        }
    }
    
    private int nextState() {
        this.x = (this.x + 1 & 0xFF);
        this.y = (this.y + this.state[this.x] & 0xFF);
        final byte b = this.state[this.x];
        this.state[this.x] = this.state[this.y];
        this.state[this.y] = b;
        return this.state[this.x] + this.state[this.y] & 0xFF;
    }
}
