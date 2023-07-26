// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.util;

public class Base64
{
    private static final char[] b2c;
    static final char pad = '=';
    static byte[] c2b;
    
    public static String encode(final byte[] array) {
        char[] value;
        int n;
        int n2;
        int n3;
        for (value = new char[((array.length - 1) / 3 + 1) * 4], n = 0, n2 = 0; n2 + 3 <= array.length; n3 = ((array[n2++] & 0xFF) << 16 | (array[n2++] & 0xFF) << 8 | (array[n2++] & 0xFF) << 0), value[n++] = Base64.b2c[(n3 & 0xFC0000) >> 18], value[n++] = Base64.b2c[(n3 & 0x3F000) >> 12], value[n++] = Base64.b2c[(n3 & 0xFC0) >> 6], value[n++] = Base64.b2c[n3 & 0x3F]) {}
        if (array.length - n2 == 2) {
            final int n4 = (array[n2] & 0xFF) << 16 | (array[n2 + 1] & 0xFF) << 8;
            value[n++] = Base64.b2c[(n4 & 0xFC0000) >> 18];
            value[n++] = Base64.b2c[(n4 & 0x3F000) >> 12];
            value[n++] = Base64.b2c[(n4 & 0xFC0) >> 6];
            value[n++] = '=';
        }
        else if (array.length - n2 == 1) {
            final int n5 = (array[n2] & 0xFF) << 16;
            value[n++] = Base64.b2c[(n5 & 0xFC0000) >> 18];
            value[n++] = Base64.b2c[(n5 & 0x3F000) >> 12];
            value[n++] = '=';
            value[n++] = '=';
        }
        return new String(value);
    }
    
    public static byte[] decode(final String s) {
        if (Base64.c2b == null) {
            Base64.c2b = new byte[256];
            for (byte b = 0; b < 64; ++b) {
                Base64.c2b[(byte)Base64.b2c[b]] = b;
            }
        }
        final int n = s.length() / 4 * 3;
        final byte[] array = new byte[4];
        byte[] array2;
        if (s.endsWith("==")) {
            array2 = new byte[n - 2];
        }
        else if (s.endsWith("=")) {
            array2 = new byte[n - 1];
        }
        else {
            array2 = new byte[n];
        }
        int n2 = 0;
        int n3 = 0;
        for (int i = 0; i < s.length(); ++i) {
            final char char1 = s.charAt(i);
            array[n3] = Base64.c2b[char1];
            if (char1 == '=') {
                break;
            }
            switch (n3) {
                case 0: {
                    ++n3;
                    break;
                }
                case 1: {
                    array2[n2++] = (byte)(array[0] * 4 + array[1] / 16);
                    ++n3;
                    break;
                }
                case 2: {
                    array2[n2++] = (byte)((array[1] & 0xF) * 16 + array[2] / 4);
                    ++n3;
                    break;
                }
                default: {
                    array2[n2++] = (byte)((array[2] & 0x3) * 64 + array[3]);
                    n3 = 0;
                    break;
                }
            }
        }
        return array2;
    }
    
    static {
        b2c = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/' };
        Base64.c2b = null;
    }
}
