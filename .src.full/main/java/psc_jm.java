import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_jm extends psc_an implements psc_az, Cloneable, Serializable
{
    private byte[] a;
    private byte[] b;
    private byte[] c;
    private byte[] d;
    private int e;
    private static final byte[] f;
    private int g;
    private static final int h = 1;
    private static final int i = 2;
    private static final int j = 3;
    private static final int k = 16;
    private static final int l = 16;
    private static final int m = 0;
    private static final byte[] n;
    
    public psc_jm() {
        this.a = new byte[16];
        this.b = new byte[16];
        this.c = new byte[16];
        this.d = new byte[48];
        try {
            this.a(null);
        }
        catch (psc_be psc_be) {}
    }
    
    public psc_jm(final int[] array) throws psc_be {
        this.a = new byte[16];
        this.b = new byte[16];
        this.c = new byte[16];
        this.d = new byte[48];
        this.a(array);
    }
    
    public void a(final int[] array) throws psc_be {
        if (array != null && array.length != 0) {
            throw new psc_be("Wrong Number of parameters: expected none");
        }
        this.g = 1;
    }
    
    public int[] c() {
        return new int[0];
    }
    
    public String e() {
        return "MD2";
    }
    
    public void a(final byte[] array, final int n) {
    }
    
    public byte[] d() {
        byte[] a;
        try {
            a = psc_q.a("MD2", 10, null, 0, 0);
        }
        catch (psc_m psc_m) {
            a = null;
        }
        return a;
    }
    
    public int f() {
        return 16;
    }
    
    public int g() {
        return 0;
    }
    
    public int h() {
        return 16;
    }
    
    public int i() {
        return psc_jm.f.length + 16;
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_jm psc_jm = new psc_jm();
        if (this.a != null) {
            psc_jm.a = this.a.clone();
        }
        if (this.b != null) {
            psc_jm.b = this.b.clone();
        }
        if (this.c != null) {
            psc_jm.c = this.c.clone();
        }
        if (this.d != null) {
            psc_jm.d = this.d.clone();
        }
        psc_jm.e = this.e;
        psc_jm.g = this.g;
        return psc_jm;
    }
    
    public void j() {
        if (this.g == 1) {
            this.g = 2;
            return;
        }
        this.e = 0;
        for (int i = 0; i < this.a.length; ++i) {
            this.a[i] = 0;
        }
        for (int j = 0; j < this.b.length; ++j) {
            this.b[j] = 0;
        }
        for (int k = 0; k < this.c.length; ++k) {
            this.c[k] = 0;
        }
        this.g = 2;
    }
    
    public void a(final byte[] array, int n, int i) throws psc_en {
        if (this.g != 2) {
            this.j();
        }
        if (i <= 0) {
            return;
        }
        int j = this.e;
        this.e = (j + i & 0xF);
        final int n2 = 16 - j;
        if (i >= n2) {
            if (j != 0) {
                while (j < 16) {
                    this.c[j] = array[n];
                    ++j;
                    ++n;
                }
                this.c(this.c, 0);
                i -= n2;
                j = 0;
            }
            while (i >= 16) {
                this.c(array, n);
                i -= 16;
                n += 16;
            }
        }
        while (i > 0) {
            this.c[j] = array[n];
            --i;
            ++j;
            ++n;
        }
    }
    
    public int b(final byte[] array, int n) throws psc_en {
        if (this.g != 2) {
            this.j();
        }
        final int n2 = 16 - this.e;
        final byte[] array2 = new byte[n2];
        final byte b = (byte)n2;
        for (int i = 0; i < n2; ++i) {
            array2[i] = b;
        }
        this.a(array2, 0, n2);
        this.a(this.b, 0, this.b.length);
        for (int j = 0; j < this.a.length; ++j, ++n) {
            array[n] = this.a[j];
        }
        this.g = 3;
        return 16;
    }
    
    public int a(final byte[] array, int n, final byte[] array2, int n2) {
        final int n3 = psc_jm.f.length + 16;
        int i;
        for (i = 0; i < psc_jm.f.length; ++i, ++n2) {
            array2[n2] = psc_jm.f[i];
        }
        while (i < n3) {
            array2[n2] = array[n];
            ++i;
            ++n2;
            ++n;
        }
        return n3;
    }
    
    private void c(final byte[] array, int n) {
        int n2 = n;
        for (int i = 0; i < 16; ++i, ++n) {
            this.d[i] = this.a[i];
            this.d[i + 16] = array[n];
            this.d[i + 32] = (byte)(this.a[i] ^ array[n]);
        }
        int n3 = 0;
        for (int j = 0; j < 18; ++j) {
            for (int k = 0; k < this.d.length; ++k) {
                n3 = ((this.d[k] ^ psc_jm.n[n3]) & 0xFF);
                this.d[k] = (byte)n3;
            }
            n3 = (n3 + j & 0xFF);
        }
        for (int l = 0; l < this.a.length; ++l) {
            this.a[l] = this.d[l];
        }
        int n4 = this.b[15] & 0xFF;
        for (int n5 = 0; n5 < this.b.length; ++n5, ++n2) {
            n4 = ((this.b[n5] ^ psc_jm.n[(array[n2] ^ n4) & 0xFF]) & 0xFF);
            this.b[n5] = (byte)n4;
        }
    }
    
    public void y() {
        super.y();
        this.e = 0;
        this.d(this.a);
        this.d(this.b);
        this.d(this.c);
        this.d(this.d);
        this.g = 1;
    }
    
    protected void finalize() {
        try {
            this.y();
        }
        finally {
            super.finalize();
        }
    }
    
    static {
        f = new byte[] { 48, 32, 48, 12, 6, 8, 42, -122, 72, -122, -9, 13, 2, 2, 5, 0, 4, 16 };
        n = new byte[] { 41, 46, 67, -55, -94, -40, 124, 1, 61, 54, 84, -95, -20, -16, 6, 19, 98, -89, 5, -13, -64, -57, 115, -116, -104, -109, 43, -39, -68, 76, -126, -54, 30, -101, 87, 60, -3, -44, -32, 22, 103, 66, 111, 24, -118, 23, -27, 18, -66, 78, -60, -42, -38, -98, -34, 73, -96, -5, -11, -114, -69, 47, -18, 122, -87, 104, 121, -111, 21, -78, 7, 63, -108, -62, 16, -119, 11, 34, 95, 33, -128, 127, 93, -102, 90, -112, 50, 39, 53, 62, -52, -25, -65, -9, -105, 3, -1, 25, 48, -77, 72, -91, -75, -47, -41, 94, -110, 42, -84, 86, -86, -58, 79, -72, 56, -46, -106, -92, 125, -74, 118, -4, 107, -30, -100, 116, 4, -15, 69, -99, 112, 89, 100, 113, -121, 32, -122, 91, -49, 101, -26, 45, -88, 2, 27, 96, 37, -83, -82, -80, -71, -10, 28, 70, 97, 105, 52, 64, 126, 15, 85, 71, -93, 35, -35, 81, -81, 58, -61, 92, -7, -50, -70, -59, -22, 38, 44, 83, 13, 110, -123, 40, -124, 9, -45, -33, -51, -12, 65, -127, 77, 82, 106, -36, 55, -56, 108, -63, -85, -6, 36, -31, 123, 8, 12, -67, -79, 74, 120, -120, -107, -117, -29, 99, -24, 109, -23, -53, -43, -2, 59, 0, 29, 57, -14, -17, -73, 14, 102, 88, -48, -28, -90, 119, 114, -8, -21, 117, 75, 10, 49, 68, 80, -76, -113, -19, 31, 26, -37, -103, -115, 51, -97, 17, -125, 20 };
    }
}
