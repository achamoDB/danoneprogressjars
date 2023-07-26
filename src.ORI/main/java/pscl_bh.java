import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public final class pscl_bh extends pscl_bd implements pscl_bi, Cloneable, Serializable
{
    private int a;
    private byte[] b;
    private int c;
    private static final byte d = 1;
    private static final byte e = 2;
    private static final byte f = 3;
    
    public pscl_bh() {
        this.a = -1;
        this.c = 0;
    }
    
    public void a(final int[] array) throws pscl_bk {
        if (array == null || array.length == 0) {
            return;
        }
        if (array.length == 1) {
            this.c = array[0];
            if (this.c <= 0) {
                throw new pscl_bk("PKCS 12 PBE iteration count must be greater than 0.");
            }
        }
        else {
            if (array.length != 2) {
                throw new pscl_bk("Incorrect number of parameters: expected one or two.");
            }
            this.c = array[0];
            if (this.c <= 0) {
                throw new pscl_bk("PKCS 12 PBE iteration count must be greater than 0.");
            }
            this.a = array[1];
        }
    }
    
    public int[] a() {
        if (this.a == -1) {
            return new int[] { this.c };
        }
        return new int[] { this.c, this.a };
    }
    
    public String b() {
        return "PKCS12PBE";
    }
    
    public void a(final byte[] array, final int n, final int n2) {
        System.arraycopy(array, n, this.b = new byte[n2], 0, n2);
    }
    
    public byte[] d() {
        if (this.b == null) {
            return null;
        }
        final byte[] array = new byte[this.b.length];
        System.arraycopy(this.b, 0, array, 0, this.b.length);
        return array;
    }
    
    public boolean e() {
        return false;
    }
    
    public byte[] a(final pscl_d pscl_d, final char[] array) throws pscl_bk {
        if (this.c <= 0) {
            throw new pscl_bk("PKCS 12 PBE iteration count must be greater than 0.");
        }
        final byte b = 1;
        byte[] array2 = null;
        if (array != null && array.length > 0) {
            int n = 0;
            if (array[array.length - 1] != '\0') {
                n = 2;
            }
            array2 = new byte[array.length * 2 + n];
            for (int i = 0; i < array.length; ++i) {
                array2[i * 2] = (byte)(array[i] >>> 8);
                array2[i * 2 + 1] = (byte)array[i];
                array[i] = '\0';
            }
        }
        return this.a(pscl_d, b, array2, 128);
    }
    
    private byte[] a(final pscl_d pscl_d, final byte b, final byte[] array, final int n) {
        if (n <= 0) {
            return new byte[0];
        }
        int n2 = (n + 7) / 8;
        final byte[] array2 = new byte[n2];
        int n3 = 0;
        final int n4 = n2 * 8 - n;
        final int b2 = pscl_d.b();
        final int n5 = b2 * 8;
        final byte[] array3 = new byte[b2];
        for (int i = 0; i < array3.length; ++i) {
            array3[i] = b;
        }
        final byte[] a = this.a(array, n5);
        final byte[] array4 = new byte[pscl_d.d()];
        while (true) {
            pscl_d.f();
            pscl_d.a(array3, 0, array3.length);
            pscl_d.a(a, 0, a.length);
            pscl_d.a(array4, 0);
            for (int j = this.c - 1; j > 0; --j) {
                pscl_d.f();
                pscl_d.a(array4, 0, array4.length);
                pscl_d.a(array4, 0);
            }
            int length = array4.length;
            if (n2 < array4.length) {
                length = n2;
            }
            for (int k = 0; k < length; ++k, ++n3) {
                array2[n3] = array4[k];
            }
            n2 -= length;
            if (n2 <= 0) {
                break;
            }
            this.a(a, array4, b2);
        }
        if (n4 > 0) {
            final byte b3 = (byte)(255 >>> n4);
            final byte[] array5 = array2;
            final int n6 = 0;
            array5[n6] &= b3;
        }
        return array2;
    }
    
    private byte[] a(final byte[] array, final int n) {
        int i = 0;
        if (this.b != null && this.b.length > 0) {
            i = (this.b.length * 8 + (n - 1)) / n * n / 8;
        }
        int j = 0;
        if (array != null && array.length > 0) {
            j = (array.length * 8 + (n - 1)) / n * n / 8;
        }
        final byte[] array2 = new byte[i + j];
        int n2 = 0;
        while (i > 0) {
            int length = this.b.length;
            if (i < this.b.length) {
                length = i;
            }
            for (int k = 0; k < length; ++k, ++n2) {
                array2[n2] = this.b[k];
            }
            i -= length;
        }
        while (j > 0) {
            int length2 = array.length;
            if (j < array.length) {
                length2 = j;
            }
            for (int l = 0; l < length2; ++l, ++n2) {
                array2[n2] = array[l];
            }
            j -= length2;
        }
        return array2;
    }
    
    private void a(final byte[] array, final byte[] array2, final int n) {
        if (array == null) {
            return;
        }
        int i = n - 1;
        int n2 = n % array2.length - 1;
        if (n2 == -1) {
            n2 = array2.length - 1;
        }
        int n3 = 0;
        while (i < array.length) {
            int length = n2;
            int n4 = 1;
            for (int j = i; j >= n3; --j) {
                final int n5 = (array[j] & 0xFF) + (array2[length] & 0xFF) + n4;
                n4 = n5 >>> 8;
                array[j] = (byte)n5;
                if (length == 0) {
                    length = array2.length;
                }
                --length;
            }
            n3 = i + 1;
            i += n;
        }
    }
    
    public void c() {
        super.c();
        this.a = -1;
        this.b = null;
        this.c = 0;
    }
    
    public void finalize() {
        this.c();
    }
}
