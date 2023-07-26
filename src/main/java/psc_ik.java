import java.util.Vector;

// 
// Decompiled by Procyon v0.5.36
// 

public abstract class psc_ik
{
    public static byte[] a(final psc_dw psc_dw, final int n) {
        return a(psc_dw, n, false);
    }
    
    public static byte[] a(final psc_dw psc_dw, final int n, final boolean b) {
        byte[] b2 = psc_dw.b(n);
        if (b && b2 != null && b2.length == 2) {
            final byte[] array = new byte[3];
            System.arraycopy(b2, 0, array, 1, 2);
            b2 = array;
        }
        return b2;
    }
    
    public static byte[][] a(final psc_dw[] array, final int n) {
        return a(array, n, false);
    }
    
    public static byte[][] a(final psc_dw[] array, final int n, final boolean b) {
        final int length = array.length;
        final Vector vector = new Vector<byte[]>(length, 10);
        for (int i = 0; i < length; ++i) {
            final byte[] a = a(array[i], n, b);
            if (a != null) {
                vector.addElement(a);
            }
            if (b && n != 2) {
                final byte[] a2 = a(array[i], 2, b);
                if (a2 != null) {
                    vector.addElement(a2);
                }
            }
        }
        final byte[][] anArray = new byte[vector.size()][];
        vector.copyInto(anArray);
        return anArray;
    }
}
