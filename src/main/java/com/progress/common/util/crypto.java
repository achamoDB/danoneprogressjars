// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.util;

public class crypto
{
    public static final String WEBSPEED_NAME = "PROGRESS";
    public static final boolean DEBUG_TRACE = false;
    
    private String asHex(final byte[] array) {
        final StringBuffer sb = new StringBuffer(array.length * 2);
        for (int i = 0; i < array.length; ++i) {
            if ((array[i] & 0xFF) < 16) {
                sb.append("0");
            }
            sb.append(Long.toString(array[i] & 0xFF, 16));
        }
        return sb.toString();
    }
    
    private String asStr(final byte[] bytes) {
        return new String(bytes);
    }
    
    private byte[] asBytes(final String s) {
        final int length = s.length();
        byte[] array;
        if ((length & 0x1) == 0x0) {
            array = new byte[length / 2];
        }
        else {
            array = new byte[length + 0];
        }
        for (int i = 0, n = 0; i < length; i += 2, ++n) {
            array[n] = (byte)(Short.parseShort(s.substring(i, i + 2), 16) & 0xFF);
        }
        return array;
    }
    
    private byte[] do_crypto(final String s, final byte[] array) {
        final byte[] bytes = s.getBytes();
        final int length = bytes.length;
        final int length2 = array.length;
        final byte[] array2 = new byte[length2];
        for (int i = 0, n = 0; i < length2; ++i, ++n) {
            if (n >= length) {
                n = 0;
            }
            array2[i] = (byte)(array[i] ^ bytes[n]);
        }
        return array2;
    }
    
    public String decrypt(final String s) {
        if (s == null || s.length() == 0) {
            return s;
        }
        return this.asStr(this.do_crypto("PROGRESS", this.asBytes(s)));
    }
    
    public String encrypt(final String s) {
        if (s == null || s.length() == 0) {
            return s;
        }
        return this.asHex(this.do_crypto("PROGRESS", s.getBytes()));
    }
}
