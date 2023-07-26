// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.crypto;

public class CryptoUtils
{
    public static void zeroBlock(final byte[] array, final int n, final int n2) {
        for (int i = n; i < n + n2; ++i) {
            array[i] = 0;
        }
    }
    
    public static void zeroBlock(final byte[] array) {
        zeroBlock(array, 0, array.length);
    }
    
    public static void randomBlock(final byte[] array, final int n, final int n2) {
        for (int i = n; i < n + n2; ++i) {
            array[i] = (byte)(Math.random() * 256.0);
        }
    }
    
    public static void randomBlock(final byte[] array) {
        randomBlock(array, 0, array.length);
    }
    
    public static void xorBlock(final byte[] array, final int n, final byte[] array2, final int n2, final byte[] array3, final int n3, final int n4) {
        for (int i = 0; i < n4; ++i) {
            array3[n3 + i] = (byte)(array[n + i] ^ array2[n2 + i]);
        }
    }
    
    public static void xorBlock(final byte[] array, final byte[] array2, final byte[] array3) {
        xorBlock(array, 0, array2, 0, array3, 0, array.length);
    }
    
    public static void copyBlock(final byte[] array, final int n, final byte[] array2, final int n2, final int n3) {
        for (int i = 0; i < n3; ++i) {
            array2[n2 + i] = array[n + i];
        }
    }
    
    public static void copyBlock(final byte[] array, final byte[] array2) {
        copyBlock(array, 0, array2, 0, array.length);
    }
    
    public static boolean equalsBlock(final byte[] array, final int n, final byte[] array2, final int n2, final int n3) {
        for (int i = 0; i < n3; ++i) {
            if (array[n + i] != array2[n2 + i]) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean equalsBlock(final byte[] array, final byte[] array2) {
        return equalsBlock(array, 0, array2, 0, array.length);
    }
    
    public static void fillBlock(final byte[] array, final int n, final byte b, final int n2) {
        for (int i = n; i < n + n2; ++i) {
            array[i] = b;
        }
    }
    
    public static void fillBlock(final byte[] array, final byte b) {
        fillBlock(array, 0, b, array.length);
    }
    
    public static void squashBytesToInts(final byte[] array, final int n, final int[] array2, final int n2, final int n3) {
        for (int i = 0; i < n3; ++i) {
            array2[n2 + i] = ((array[n + i * 4] & 0xFF) << 24 | (array[n + i * 4 + 1] & 0xFF) << 16 | (array[n + i * 4 + 2] & 0xFF) << 8 | (array[n + i * 4 + 3] & 0xFF));
        }
    }
    
    public static void spreadIntsToBytes(final int[] array, final int n, final byte[] array2, final int n2, final int n3) {
        for (int i = 0; i < n3; ++i) {
            array2[n2 + i * 4] = (byte)(array[n + i] >>> 24 & 0xFF);
            array2[n2 + i * 4 + 1] = (byte)(array[n + i] >>> 16 & 0xFF);
            array2[n2 + i * 4 + 2] = (byte)(array[n + i] >>> 8 & 0xFF);
            array2[n2 + i * 4 + 3] = (byte)(array[n + i] & 0xFF);
        }
    }
    
    public static void squashBytesToIntsLittle(final byte[] array, final int n, final int[] array2, final int n2, final int n3) {
        for (int i = 0; i < n3; ++i) {
            array2[n2 + i] = ((array[n + i * 4] & 0xFF) | (array[n + i * 4 + 1] & 0xFF) << 8 | (array[n + i * 4 + 2] & 0xFF) << 16 | (array[n + i * 4 + 3] & 0xFF) << 24);
        }
    }
    
    public static void spreadIntsToBytesLittle(final int[] array, final int n, final byte[] array2, final int n2, final int n3) {
        for (int i = 0; i < n3; ++i) {
            array2[n2 + i * 4] = (byte)(array[n + i] & 0xFF);
            array2[n2 + i * 4 + 1] = (byte)(array[n + i] >>> 8 & 0xFF);
            array2[n2 + i * 4 + 2] = (byte)(array[n + i] >>> 16 & 0xFF);
            array2[n2 + i * 4 + 3] = (byte)(array[n + i] >>> 24 & 0xFF);
        }
    }
    
    public static void squashBytesToShorts(final byte[] array, final int n, final int[] array2, final int n2, final int n3) {
        for (int i = 0; i < n3; ++i) {
            array2[n2 + i] = ((array[n + i * 2] & 0xFF) << 8 | (array[n + i * 2 + 1] & 0xFF));
        }
    }
    
    public static void spreadShortsToBytes(final int[] array, final int n, final byte[] array2, final int n2, final int n3) {
        for (int i = 0; i < n3; ++i) {
            array2[n2 + i * 2] = (byte)(array[n + i] >>> 8 & 0xFF);
            array2[n2 + i * 2 + 1] = (byte)(array[n + i] & 0xFF);
        }
    }
    
    public static void squashBytesToShortsLittle(final byte[] array, final int n, final int[] array2, final int n2, final int n3) {
        for (int i = 0; i < n3; ++i) {
            array2[n2 + i] = ((array[n + i * 2] & 0xFF) | (array[n + i * 2 + 1] & 0xFF) << 8);
        }
    }
    
    public static void spreadShortsToBytesLittle(final int[] array, final int n, final byte[] array2, final int n2, final int n3) {
        for (int i = 0; i < n3; ++i) {
            array2[n2 + i * 2] = (byte)(array[n + i] & 0xFF);
            array2[n2 + i * 2 + 1] = (byte)(array[n + i] >>> 8 & 0xFF);
        }
    }
    
    public static String toStringBlock(final byte[] array, final int n, final int n2) {
        final String s = "0123456789abcdef";
        final StringBuffer obj = new StringBuffer();
        for (int i = n; i < n + n2; ++i) {
            obj.append(s.charAt(array[i] >>> 4 & 0xF));
            obj.append(s.charAt(array[i] & 0xF));
        }
        return "[" + (Object)obj + "]";
    }
    
    public static String toStringBlock(final byte[] array) {
        return toStringBlock(array, 0, array.length);
    }
}
