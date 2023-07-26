import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

final class psc_de implements Serializable
{
    private int a;
    private Object b;
    
    psc_de(final int a) {
        this.a = a;
    }
    
    void a(final Object o) {
        if (this.b == null) {
            final byte[] a = a(o, this.a);
            psc_au.a.g(a);
            psc_au.a.b(a, 0, a.length);
            this.b(o);
        }
        switch (this.a) {
            case 0: {
                final byte[] array = (byte[])o;
                final byte[] array2 = (byte[])this.b;
                for (int i = 0; i < array.length; ++i) {
                    array[i] ^= array2[i];
                }
            }
            case 1: {
                final short[] array3 = (short[])o;
                final short[] array4 = (short[])this.b;
                for (int j = 0; j < array3.length; ++j) {
                    array3[j] ^= array4[j];
                }
            }
            case 2: {
                final int[] array5 = (int[])o;
                final int[] array6 = (int[])this.b;
                for (int k = 0; k < array5.length; ++k) {
                    final int[] array7 = array5;
                    final int n = k;
                    array7[n] ^= array6[k];
                }
            }
            case 3: {
                final long[] array8 = (long[])o;
                final long[] array9 = (long[])this.b;
                for (int l = 0; l < array8.length; ++l) {
                    final long[] array10 = array8;
                    final int n2 = l;
                    array10[n2] ^= array9[l];
                }
                break;
            }
        }
    }
    
    private void b(final Object o) {
        switch (this.a) {
            case 0: {
                this.b = psc_au.a.c(((byte[])o).length);
            }
            case 1: {
                final short[] b = new short[((short[])o).length];
                for (int i = 0; i < b.length; ++i) {
                    b[i] = psc_au.a.s();
                }
                this.b = b;
            }
            case 2: {
                final int[] b2 = new int[((int[])o).length];
                for (int j = 0; j < b2.length; ++j) {
                    b2[j] = psc_au.a.nextInt();
                }
                this.b = b2;
            }
            case 3: {
                final long[] b3 = new long[((long[])o).length];
                for (int k = 0; k < b3.length; ++k) {
                    b3[k] = psc_au.a.nextLong();
                }
                this.b = b3;
                break;
            }
        }
    }
    
    private static byte[] a(final Object o, final int n) {
        int length = 56;
        final byte[] array = new byte[length];
        switch (n) {
            case 0: {
                if (((byte[])o).length < length) {
                    length = ((byte[])o).length;
                }
                System.arraycopy(o, 0, array, 0, length);
                return array;
            }
            case 1: {
                int n2;
                if (((short[])o).length < length / 2) {
                    n2 = ((short[])o).length * 2;
                }
                else {
                    n2 = length / 2;
                }
                for (int i = 0, n3 = 0; i < n2; i += 2, ++n3) {
                    array[i] = (byte)(((short[])o)[n3] >>> 8);
                    array[i + 1] = (byte)((short[])o)[n3];
                }
                return array;
            }
            case 2: {
                int n4;
                if (((int[])o).length < length / 4) {
                    n4 = ((int[])o).length * 4;
                }
                else {
                    n4 = length / 4;
                }
                for (int j = 0, n5 = 0; j < n4; j += 4, ++n5) {
                    array[j] = (byte)(((int[])o)[n5] >>> 24);
                    array[j + 1] = (byte)(((int[])o)[n5] >>> 16);
                    array[j + 2] = (byte)(((int[])o)[n5] >>> 8);
                    array[j + 3] = (byte)((int[])o)[n5];
                }
                return array;
            }
            case 3: {
                int n6;
                if (((long[])o).length < length / 8) {
                    n6 = ((long[])o).length * 8;
                }
                else {
                    n6 = length / 8;
                }
                for (int k = 0, n7 = 0; k < n6; k += 8, ++n7) {
                    array[n7] = (byte)(((long[])o)[n7] >>> 56);
                    array[n7 + 1] = (byte)(((long[])o)[n7] >>> 48);
                    array[n7 + 2] = (byte)(((long[])o)[n7] >>> 40);
                    array[n7 + 3] = (byte)(((long[])o)[n7] >>> 32);
                    array[n7 + 4] = (byte)(((long[])o)[n7] >>> 24);
                    array[n7 + 5] = (byte)(((long[])o)[n7] >>> 16);
                    array[n7 + 6] = (byte)(((long[])o)[n7] >>> 8);
                    array[n7 + 7] = (byte)((long[])o)[n7];
                }
                return array;
            }
            default: {
                return null;
            }
        }
    }
    
    protected void a() {
        if (this.b != null) {
            psc_au.c(this.b);
        }
        this.a = 4;
        this.b = null;
    }
}
