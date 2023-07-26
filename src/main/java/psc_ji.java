import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public final class psc_ji extends psc_jj implements Cloneable, Serializable
{
    private int a;
    private byte[] b;
    private int c;
    private static final byte d = 1;
    private static final byte e = 2;
    private static final byte f = 3;
    
    public psc_ji() {
        this.a = -1;
    }
    
    public void a(final int[] array) throws psc_be {
        if (array == null || array.length == 0) {
            return;
        }
        if (array.length == 1) {
            this.c = array[0];
            if (this.c <= 0) {
                throw new psc_be("PKCS 12 PBE iteration count must be greater than 0.");
            }
        }
        else {
            if (array.length != 2) {
                throw new psc_be("Incorrect number of parameters: expected one or two.");
            }
            this.c = array[0];
            if (this.c <= 0) {
                throw new psc_be("PKCS 12 PBE iteration count must be greater than 0.");
            }
            this.a = array[1];
        }
    }
    
    public int[] c() {
        if (this.a == -1) {
            return new int[] { this.c };
        }
        return new int[] { this.c, this.a };
    }
    
    public String d() {
        return "PKCS12V1PBE";
    }
    
    public String e() {
        return "PKCS12V1PBE-1-" + this.a;
    }
    
    public void a(final psc_dg psc_dg, final psc_di psc_di, final psc_dm psc_dm, final byte[] array, final int n, final int n2) throws psc_ao {
        psc_la.a(this, psc_dg, this.a, array, n, n2);
    }
    
    public byte[] a(final String s, final String s2) throws psc_ao {
        return psc_la.a(this.c, this.b);
    }
    
    public byte[] f() throws psc_ao {
        return psc_la.a(this.c, this.b);
    }
    
    public void a(final byte[] array, final int n, final int n2) {
        System.arraycopy(array, n, this.b = new byte[n2], 0, n2);
    }
    
    public byte[] g() {
        if (this.b == null) {
            return null;
        }
        final byte[] array = new byte[this.b.length];
        System.arraycopy(this.b, 0, array, 0, this.b.length);
        return array;
    }
    
    public boolean h() {
        return false;
    }
    
    public void a(final psc_az psc_az, final psc_di psc_di, final int n, final psc_dc psc_dc) throws psc_en, psc_bf {
        if (this.c <= 0) {
            throw new psc_en("PKCS 12 PBE iteration count must be greater than 0.");
        }
        final int c = psc_dc.c();
        byte b = 1;
        if (c == 1) {
            b = 3;
        }
        final char[] j = psc_dc.j();
        byte[] array = null;
        if (j != null && j.length > 0) {
            int n2 = 0;
            if (j[j.length - 1] != '\0') {
                n2 = 2;
            }
            array = new byte[j.length * 2 + n2];
            for (int i = 0; i < j.length; ++i) {
                array[i * 2] = (byte)(j[i] >>> 8);
                array[i * 2 + 1] = (byte)j[i];
                j[i] = '\0';
            }
        }
        final byte[] a = this.a(psc_az, b, array, psc_dc.a(this.a));
        psc_dc.a(a, 0, a.length);
        psc_au.c(a);
        if (psc_di == null) {
            psc_au.c(array);
            return;
        }
        final int n3 = psc_di.a(n) * 8;
        if (n3 == 0) {
            psc_au.c(array);
            return;
        }
        final byte[] a2 = this.a(psc_az, (byte)2, array, n3);
        psc_au.c(array);
        try {
            psc_di.b(a2, 0, a2.length);
        }
        catch (psc_ap psc_ap) {
            throw new psc_en("Could not set the IV in PKCS 12 PBE.");
        }
    }
    
    private byte[] a(final psc_az psc_az, final byte b, final byte[] array, final int n) throws psc_en {
        if (n <= 0) {
            return new byte[0];
        }
        int n2 = (n + 7) / 8;
        final byte[] array2 = new byte[n2];
        int n3 = 0;
        final int n4 = n2 * 8 - n;
        final int f = psc_az.f();
        final int n5 = f * 8;
        final byte[] array3 = new byte[f];
        for (int i = 0; i < array3.length; ++i) {
            array3[i] = b;
        }
        final byte[] a = this.a(array, n5);
        final byte[] array4 = new byte[psc_az.h()];
        while (true) {
            psc_az.j();
            psc_az.a(array3, 0, array3.length);
            psc_az.a(a, 0, a.length);
            psc_az.b(array4, 0);
            for (int j = this.c - 1; j > 0; --j) {
                psc_az.j();
                psc_az.a(array4, 0, array4.length);
                psc_az.b(array4, 0);
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
            this.a(a, array4, f);
        }
        psc_au.c(a);
        psc_au.c(array4);
        if (n4 > 0) {
            final byte b2 = (byte)(255 >>> n4);
            final byte[] array5 = array2;
            final int n6 = 0;
            array5[n6] &= b2;
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
    
    public Object clone() throws CloneNotSupportedException {
        final psc_ji psc_ji = new psc_ji();
        psc_ji.c = this.c;
        psc_ji.a = this.a;
        if (this.b != null) {
            psc_ji.b = this.b.clone();
        }
        return psc_ji;
    }
    
    public void y() {
        super.y();
        this.a = -1;
        this.d(this.b);
        this.b = null;
        this.c = 0;
    }
    
    protected void finalize() {
        try {
            this.y();
        }
        finally {
            super.finalize();
        }
    }
}
