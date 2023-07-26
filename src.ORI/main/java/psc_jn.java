import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_jn extends psc_an implements psc_az, psc_ex, psc_er, Cloneable, Serializable
{
    protected int a;
    protected int b;
    protected int c;
    protected int d;
    protected int e;
    private final int f = 1732584193;
    private final int g = -271733879;
    private final int h = -1732584194;
    private final int i = 271733878;
    private final int j = -1009589776;
    protected long k;
    protected int l;
    private int m;
    private int n;
    private int o;
    private int p;
    private int q;
    private int[] r;
    private static final int s = 1;
    private static final int t = 2;
    private static final int u = 3;
    private static final int v = 20;
    private static final int w = 64;
    private static final int x = 8;
    private static final int y = 64;
    private static final int z = 64;
    private static final int aa = 64;
    private byte[] ab;
    private int ac;
    private static final byte[] ad;
    private final short[] ae;
    
    public psc_jn() {
        this.ab = new byte[64];
        this.ae = new short[] { 0, 11, 5, 8, 1, 14, 14, 9, 2, 15, 7, 9, 3, 12, 0, 11, 4, 5, 9, 13, 5, 8, 2, 15, 6, 7, 11, 15, 7, 9, 4, 5, 8, 11, 13, 7, 9, 13, 6, 7, 10, 14, 15, 8, 11, 15, 8, 11, 12, 6, 1, 14, 13, 7, 10, 14, 14, 9, 3, 12, 15, 8, 12, 6, 7, 7, 6, 9, 4, 6, 11, 13, 13, 8, 3, 15, 1, 13, 7, 7, 10, 11, 0, 12, 6, 9, 13, 8, 15, 7, 5, 9, 3, 15, 10, 11, 12, 7, 14, 7, 0, 12, 15, 7, 9, 15, 8, 12, 5, 9, 12, 7, 2, 11, 4, 6, 14, 7, 9, 15, 11, 13, 1, 13, 8, 12, 2, 11, 3, 11, 15, 9, 10, 13, 5, 7, 14, 6, 1, 15, 4, 7, 3, 11, 9, 14, 7, 8, 15, 9, 14, 6, 8, 13, 6, 6, 1, 15, 9, 14, 2, 14, 11, 12, 7, 8, 8, 13, 0, 13, 12, 5, 6, 6, 2, 14, 13, 5, 10, 13, 11, 12, 0, 13, 5, 7, 4, 7, 12, 5, 13, 5, 1, 11, 8, 15, 9, 12, 6, 5, 11, 14, 4, 8, 10, 15, 1, 11, 0, 14, 3, 14, 8, 15, 11, 14, 12, 9, 15, 6, 4, 8, 0, 14, 13, 9, 5, 6, 3, 14, 12, 9, 7, 5, 2, 12, 15, 6, 13, 9, 14, 8, 9, 12, 5, 6, 7, 5, 6, 5, 10, 15, 2, 12, 14, 8, 4, 9, 12, 8, 0, 15, 15, 5, 5, 5, 10, 12, 9, 11, 4, 9, 7, 6, 1, 12, 12, 8, 5, 5, 2, 13, 8, 14, 10, 12, 7, 6, 14, 5, 6, 8, 1, 12, 2, 13, 3, 13, 13, 6, 8, 14, 14, 5, 11, 11, 0, 15, 6, 8, 3, 13, 15, 5, 9, 11, 13, 6, 11, 11 };
    }
    
    public psc_jn(final int[] array) throws psc_be {
        this.ab = new byte[64];
        this.ae = new short[] { 0, 11, 5, 8, 1, 14, 14, 9, 2, 15, 7, 9, 3, 12, 0, 11, 4, 5, 9, 13, 5, 8, 2, 15, 6, 7, 11, 15, 7, 9, 4, 5, 8, 11, 13, 7, 9, 13, 6, 7, 10, 14, 15, 8, 11, 15, 8, 11, 12, 6, 1, 14, 13, 7, 10, 14, 14, 9, 3, 12, 15, 8, 12, 6, 7, 7, 6, 9, 4, 6, 11, 13, 13, 8, 3, 15, 1, 13, 7, 7, 10, 11, 0, 12, 6, 9, 13, 8, 15, 7, 5, 9, 3, 15, 10, 11, 12, 7, 14, 7, 0, 12, 15, 7, 9, 15, 8, 12, 5, 9, 12, 7, 2, 11, 4, 6, 14, 7, 9, 15, 11, 13, 1, 13, 8, 12, 2, 11, 3, 11, 15, 9, 10, 13, 5, 7, 14, 6, 1, 15, 4, 7, 3, 11, 9, 14, 7, 8, 15, 9, 14, 6, 8, 13, 6, 6, 1, 15, 9, 14, 2, 14, 11, 12, 7, 8, 8, 13, 0, 13, 12, 5, 6, 6, 2, 14, 13, 5, 10, 13, 11, 12, 0, 13, 5, 7, 4, 7, 12, 5, 13, 5, 1, 11, 8, 15, 9, 12, 6, 5, 11, 14, 4, 8, 10, 15, 1, 11, 0, 14, 3, 14, 8, 15, 11, 14, 12, 9, 15, 6, 4, 8, 0, 14, 13, 9, 5, 6, 3, 14, 12, 9, 7, 5, 2, 12, 15, 6, 13, 9, 14, 8, 9, 12, 5, 6, 7, 5, 6, 5, 10, 15, 2, 12, 14, 8, 4, 9, 12, 8, 0, 15, 15, 5, 5, 5, 10, 12, 9, 11, 4, 9, 7, 6, 1, 12, 12, 8, 5, 5, 2, 13, 8, 14, 10, 12, 7, 6, 14, 5, 6, 8, 1, 12, 2, 13, 3, 13, 13, 6, 8, 14, 14, 5, 11, 11, 0, 15, 6, 8, 3, 13, 15, 5, 9, 11, 13, 6, 11, 11 };
        this.a(array);
    }
    
    public void a(final int[] array) throws psc_be {
        if (array != null && array.length != 0) {
            throw new psc_be("Wrong Number of parameters: expected none.");
        }
    }
    
    public int[] c() {
        return new int[0];
    }
    
    public String e() {
        return "RIPEMD160";
    }
    
    public void a(final byte[] array, final int n) {
    }
    
    public byte[] d() {
        byte[] a;
        try {
            a = psc_q.a("RIPEMD160", 10, null, 0, 0);
        }
        catch (psc_m psc_m) {
            a = null;
        }
        return a;
    }
    
    public int f() {
        return 64;
    }
    
    public int g() {
        return 8;
    }
    
    public int h() {
        return 20;
    }
    
    public int i() {
        return psc_jn.ad.length + 20;
    }
    
    protected psc_jn k() {
        return new psc_jn();
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_jn k = this.k();
        k.a = this.a;
        k.b = this.b;
        k.c = this.c;
        k.d = this.d;
        k.e = this.e;
        k.l = this.l;
        k.ab = new byte[64];
        k.k = this.k;
        if (this.ab != null) {
            k.ab = this.ab.clone();
        }
        k.ac = this.ac;
        return k;
    }
    
    protected long l() {
        return this.k;
    }
    
    public void j() {
        this.k = 0L;
        this.l = 0;
        this.a = 1732584193;
        this.b = -271733879;
        this.c = -1732584194;
        this.d = 271733878;
        this.e = -1009589776;
        this.ac = 2;
    }
    
    public void a(final byte[] array, int n, final int n2) throws psc_en {
        final int n3 = this.l & 0x3F;
        if (array == null) {
            throw new psc_en("partIn is Null");
        }
        if (n > array.length) {
            throw new psc_en("offset exceeds partInLength");
        }
        if (n2 <= 64 - n3) {
            System.arraycopy(array, n, this.ab, n3, n2);
            this.l += n2;
            if ((this.l & 0x3F) == 0x0) {
                this.d(this.ab, 0);
                this.k += 64L;
                this.l = 0;
            }
        }
        else {
            System.arraycopy(array, n, this.ab, n3, 64 - n3);
            n += 64 - n3;
            this.d(this.ab, 0);
            this.k += 64L;
            int i;
            for (i = n2 - (64 - n3); i >= 64; i -= 64, n += 64) {
                System.arraycopy(array, n, this.ab, 0, 64);
                this.d(this.ab, 0);
                this.k += 64L;
            }
            if (i > 0) {
                System.arraycopy(array, n, this.ab, 0, i);
                this.l = i;
            }
        }
    }
    
    public int b(final byte[] array, final int n) throws psc_en {
        if (array == null) {
            throw new psc_en("hash output array is null");
        }
        if (array.length < n) {
            throw new psc_en("offset exceeds length of array");
        }
        if (this.ac == 1) {
            throw new psc_en("digest Not Initialized\n");
        }
        final byte[] array2 = new byte[144];
        int n2 = 64 - (this.l & 0x3F);
        if (n2 <= 8) {
            n2 += 64;
        }
        array2[0] = -128;
        for (int i = 1; i < n2 - 7; ++i) {
            array2[i] = 0;
        }
        this.k += this.l;
        final long n3 = this.k << 3;
        array2[n2 - 1] = (byte)((n3 & 0xFF00000000000000L) >>> 56);
        array2[n2 - 2] = (byte)((n3 & 0xFF000000000000L) >>> 48);
        array2[n2 - 3] = (byte)((n3 & 0xFF0000000000L) >>> 40);
        array2[n2 - 4] = (byte)((n3 & 0xFF00000000L) >>> 32);
        array2[n2 - 5] = (byte)((n3 & 0xFF000000L) >>> 24);
        array2[n2 - 6] = (byte)((n3 & 0xFF0000L) >>> 16);
        array2[n2 - 7] = (byte)((n3 & 0xFF00L) >>> 8);
        array2[n2 - 8] = (byte)(n3 & 0xFFL);
        this.a(array2, 0, n2);
        this.c(array, n);
        return 20;
    }
    
    public int a(final byte[] array, final int n, final byte[] array2, int n2) {
        final int n3 = psc_jn.ad.length + 20;
        System.arraycopy(psc_jn.ad, 0, array2, n2, psc_jn.ad.length);
        n2 += psc_jn.ad.length;
        System.arraycopy(array, n, array2, n2, array.length);
        return n3;
    }
    
    private void d(final byte[] array, final int n) {
        final int[] array2 = new int[16];
        for (int n2 = 0, i = 0; i < 16; ++i, n2 += 4) {
            array2[i] = ((array[n2] & 0xFF) | (array[n2 + 1] & 0xFF) << 8 | (array[n2 + 2] & 0xFF) << 16 | (array[n2 + 3] & 0xFF) << 24);
        }
        int a;
        int n3 = a = this.a;
        int b;
        int n4 = b = this.b;
        int c;
        int n5 = c = this.c;
        int d;
        int n6 = d = this.d;
        int e;
        int n7 = e = this.e;
        for (int j = 0; j < 16; ++j) {
            final int n8 = j << 2;
            final int n9 = a + (b ^ c ^ d) + array2[this.ae[n8]] + 0;
            final int n10 = (n9 << this.ae[n8 + 1] | n9 >>> 32 - this.ae[n8 + 1]) + e;
            a = e;
            e = d;
            d = (c << 10 | c >>> 22);
            c = b;
            b = n10;
            final int n11 = n3 + (n4 ^ (n5 | ~n6)) + array2[this.ae[n8 + 2]] + 1352829926;
            final int n12 = (n11 << this.ae[n8 + 3] | n11 >>> 32 - this.ae[n8 + 3]) + n7;
            n3 = n7;
            n7 = n6;
            n6 = (n5 << 10 | n5 >>> 22);
            n5 = n4;
            n4 = n12;
        }
        for (int k = 16; k < 32; ++k) {
            final int n13 = k << 2;
            final int n14 = a + ((b & c) | (~b & d)) + array2[this.ae[n13]] + 1518500249;
            final int n15 = (n14 << this.ae[n13 + 1] | n14 >>> 32 - this.ae[n13 + 1]) + e;
            a = e;
            e = d;
            d = (c << 10 | c >>> 22);
            c = b;
            b = n15;
            final int n16 = n3 + ((n4 & n6) | (n5 & ~n6)) + array2[this.ae[n13 + 2]] + 1548603684;
            final int n17 = (n16 << this.ae[n13 + 3] | n16 >>> 32 - this.ae[n13 + 3]) + n7;
            n3 = n7;
            n7 = n6;
            n6 = (n5 << 10 | n5 >>> 22);
            n5 = n4;
            n4 = n17;
        }
        for (int l = 32; l < 48; ++l) {
            final int n18 = l << 2;
            final int n19 = a + ((b | ~c) ^ d) + array2[this.ae[n18]] + 1859775393;
            final int n20 = (n19 << this.ae[n18 + 1] | n19 >>> 32 - this.ae[n18 + 1]) + e;
            a = e;
            e = d;
            d = (c << 10 | c >>> 22);
            c = b;
            b = n20;
            final int n21 = n3 + ((n4 | ~n5) ^ n6) + array2[this.ae[n18 + 2]] + 1836072691;
            final int n22 = (n21 << this.ae[n18 + 3] | n21 >>> 32 - this.ae[n18 + 3]) + n7;
            n3 = n7;
            n7 = n6;
            n6 = (n5 << 10 | n5 >>> 22);
            n5 = n4;
            n4 = n22;
        }
        for (int n23 = 48; n23 < 64; ++n23) {
            final int n24 = n23 << 2;
            final int n25 = a + ((b & d) | (c & ~d)) + array2[this.ae[n24]] - 1894007588;
            final int n26 = (n25 << this.ae[n24 + 1] | n25 >>> 32 - this.ae[n24 + 1]) + e;
            a = e;
            e = d;
            d = (c << 10 | c >>> 22);
            c = b;
            b = n26;
            final int n27 = n3 + ((n4 & n5) | (~n4 & n6)) + array2[this.ae[n24 + 2]] + 2053994217;
            final int n28 = (n27 << this.ae[n24 + 3] | n27 >>> 32 - this.ae[n24 + 3]) + n7;
            n3 = n7;
            n7 = n6;
            n6 = (n5 << 10 | n5 >>> 22);
            n5 = n4;
            n4 = n28;
        }
        for (int n29 = 64; n29 < 80; ++n29) {
            final int n30 = n29 << 2;
            final int n31 = a + (b ^ (c | ~d)) + array2[this.ae[n30]] - 1454113458;
            final int n32 = (n31 << this.ae[n30 + 1] | n31 >>> 32 - this.ae[n30 + 1]) + e;
            a = e;
            e = d;
            d = (c << 10 | c >>> 22);
            c = b;
            b = n32;
            final int n33 = n3 + (n4 ^ n5 ^ n6) + array2[this.ae[n30 + 2]] + 0;
            final int n34 = (n33 << this.ae[n30 + 3] | n33 >>> 32 - this.ae[n30 + 3]) + n7;
            n3 = n7;
            n7 = n6;
            n6 = (n5 << 10 | n5 >>> 22);
            n5 = n4;
            n4 = n34;
        }
        final int a2 = this.b + c + n6;
        this.b = this.c + d + n7;
        this.c = this.d + e + n3;
        this.d = this.e + a + n4;
        this.e = this.a + b + n5;
        this.a = a2;
    }
    
    void c(final byte[] array, int n) {
        array[n] = (byte)(this.a & 0xFF);
        ++n;
        array[n] = (byte)(this.a >> 8 & 0xFF);
        ++n;
        array[n] = (byte)(this.a >> 16 & 0xFF);
        ++n;
        array[n] = (byte)(this.a >>> 24 & 0xFF);
        ++n;
        array[n] = (byte)(this.b & 0xFF);
        ++n;
        array[n] = (byte)(this.b >> 8 & 0xFF);
        ++n;
        array[n] = (byte)(this.b >> 16 & 0xFF);
        ++n;
        array[n] = (byte)(this.b >>> 24 & 0xFF);
        ++n;
        array[n] = (byte)(this.c & 0xFF);
        ++n;
        array[n] = (byte)(this.c >> 8 & 0xFF);
        ++n;
        array[n] = (byte)(this.c >> 16 & 0xFF);
        ++n;
        array[n] = (byte)(this.c >>> 24 & 0xFF);
        ++n;
        array[n] = (byte)(this.d & 0xFF);
        ++n;
        array[n] = (byte)(this.d >> 8 & 0xFF);
        ++n;
        array[n] = (byte)(this.d >> 16 & 0xFF);
        ++n;
        array[n] = (byte)(this.d >>> 24 & 0xFF);
        ++n;
        array[n] = (byte)(this.e & 0xFF);
        ++n;
        array[n] = (byte)(this.e >> 8 & 0xFF);
        ++n;
        array[n] = (byte)(this.e >> 16 & 0xFF);
        ++n;
        array[n] = (byte)(this.e >>> 24 & 0xFF);
        ++n;
    }
    
    public void y() {
        super.y();
        this.d(this.ab);
        this.a = 0;
        this.b = 0;
        this.c = 0;
        this.d = 0;
        this.e = 0;
        this.ac = 1;
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
        ad = new byte[] { 48, 33, 48, 9, 6, 5, 43, 36, 3, 2, 1, 5, 0, 4, 20 };
    }
}
