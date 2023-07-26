import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_je extends psc_an implements psc_az, psc_ex, psc_ey, psc_er, Cloneable, Serializable
{
    protected int a;
    protected int b;
    protected int c;
    protected int d;
    protected int e;
    protected int f;
    protected int g;
    protected int h;
    private final int i = -1056596264;
    private final int j = 914150663;
    private final int k = 812702999;
    private final int l = -150054599;
    private final int m = -4191439;
    private final int n = 1750603025;
    private final int o = 1694076839;
    private final int p = -1090891868;
    protected int[] q;
    protected int r;
    private int[] s;
    private static final int t = 1;
    private static final int u = 2;
    private static final int v = 3;
    private static final int w = 28;
    private static final int x = 64;
    private static final int y = 8;
    private static final int z = 64;
    private static final int aa = 64;
    private static final int ab = 64;
    private byte[] ac;
    private int ad;
    private static final byte[] ae;
    private final int[] af;
    
    public psc_je() {
        this.q = new int[2];
        this.ac = new byte[64];
        this.ad = 1;
        this.af = new int[] { 1116352408, 1899447441, -1245643825, -373957723, 961987163, 1508970993, -1841331548, -1424204075, -670586216, 310598401, 607225278, 1426881987, 1925078388, -2132889090, -1680079193, -1046744716, -459576895, -272742522, 264347078, 604807628, 770255983, 1249150122, 1555081692, 1996064986, -1740746414, -1473132947, -1341970488, -1084653625, -958395405, -710438585, 113926993, 338241895, 666307205, 773529912, 1294757372, 1396182291, 1695183700, 1986661051, -2117940946, -1838011259, -1564481375, -1474664885, -1035236496, -949202525, -778901479, -694614492, -200395387, 275423344, 430227734, 506948616, 659060556, 883997877, 958139571, 1322822218, 1537002063, 1747873779, 1955562222, 2024104815, -2067236844, -1933114872, -1866530822, -1538233109, -1090935817, -965641998 };
    }
    
    public psc_je(final int[] array) throws psc_be {
        this.q = new int[2];
        this.ac = new byte[64];
        this.ad = 1;
        this.af = new int[] { 1116352408, 1899447441, -1245643825, -373957723, 961987163, 1508970993, -1841331548, -1424204075, -670586216, 310598401, 607225278, 1426881987, 1925078388, -2132889090, -1680079193, -1046744716, -459576895, -272742522, 264347078, 604807628, 770255983, 1249150122, 1555081692, 1996064986, -1740746414, -1473132947, -1341970488, -1084653625, -958395405, -710438585, 113926993, 338241895, 666307205, 773529912, 1294757372, 1396182291, 1695183700, 1986661051, -2117940946, -1838011259, -1564481375, -1474664885, -1035236496, -949202525, -778901479, -694614492, -200395387, 275423344, 430227734, 506948616, 659060556, 883997877, 958139571, 1322822218, 1537002063, 1747873779, 1955562222, 2024104815, -2067236844, -1933114872, -1866530822, -1538233109, -1090935817, -965641998 };
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
        return "SHA224";
    }
    
    public void a(final byte[] array, final int n) {
    }
    
    public byte[] d() {
        byte[] a;
        try {
            a = psc_q.a("SHA224", 10, null, 0, 0);
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
        return 28;
    }
    
    public int i() {
        return psc_je.ae.length + 28;
    }
    
    protected psc_je k() {
        return new psc_je();
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_je k = this.k();
        k.a = this.a;
        k.b = this.b;
        k.c = this.c;
        k.d = this.d;
        k.e = this.e;
        k.f = this.f;
        k.g = this.g;
        k.h = this.h;
        k.r = this.r;
        k.ac = new byte[64];
        k.q = new int[2];
        for (int i = 0; i < 2; ++i) {
            k.q[i] = this.q[i];
        }
        if (this.ac != null) {
            k.ac = this.ac.clone();
        }
        k.ad = this.ad;
        return k;
    }
    
    public void j() {
        this.q[1] = (this.q[0] = 0);
        this.r = 0;
        this.a = -1056596264;
        this.b = 914150663;
        this.c = 812702999;
        this.d = -150054599;
        this.e = -4191439;
        this.f = 1750603025;
        this.g = 1694076839;
        this.h = -1090891868;
        this.ad = 2;
    }
    
    public void a(final byte[] array, int n, final int n2) throws psc_en {
        if (array == null) {
            throw new psc_en("partIn is Null");
        }
        if (n > array.length) {
            throw new psc_en("offset exceeds partInLength");
        }
        if (n < 0) {
            throw new psc_en("offset below zero");
        }
        if (n2 <= 0) {
            return;
        }
        final int n3 = this.r & 0x3F;
        if (n2 <= 64 - n3) {
            System.arraycopy(array, n, this.ac, n3, n2);
            this.r += n2;
            if ((this.r & 0x3F) == 0x0) {
                this.d(this.ac, 0);
                final int[] q = this.q;
                final int n4 = 1;
                q[n4] += 64;
                if (this.q[1] < 64) {
                    final int[] q2 = this.q;
                    final int n5 = 0;
                    ++q2[n5];
                }
                this.r = 0;
            }
        }
        else {
            System.arraycopy(array, n, this.ac, n3, 64 - n3);
            n += 64 - n3;
            this.d(this.ac, 0);
            final int[] q3 = this.q;
            final int n6 = 1;
            q3[n6] += 64;
            if (this.q[1] < 64) {
                final int[] q4 = this.q;
                final int n7 = 0;
                ++q4[n7];
            }
            int i;
            for (i = n2 - (64 - n3); i >= 64; i -= 64, n += 64) {
                System.arraycopy(array, n, this.ac, 0, 64);
                this.d(this.ac, 0);
                final int[] q5 = this.q;
                final int n8 = 1;
                q5[n8] += 64;
                if (this.q[1] < 64) {
                    final int[] q6 = this.q;
                    final int n9 = 0;
                    ++q6[n9];
                }
            }
            if (i > 0) {
                System.arraycopy(array, n, this.ac, 0, i);
                this.r = i;
            }
        }
    }
    
    public int b(final byte[] array, final int n) throws psc_en {
        final int[] array2 = new int[2];
        if (array == null) {
            throw new psc_en("hash output array is null");
        }
        if (array.length < n) {
            throw new psc_en("offset exceeds length of array");
        }
        if (n < 0) {
            throw new psc_en("offset below zero");
        }
        if (this.ad == 1) {
            throw new psc_en("digest Not Initialized\n");
        }
        final byte[] array3 = new byte[144];
        int n2 = 64 - (this.r & 0x3F);
        if (n2 <= 8) {
            n2 += 64;
        }
        array3[0] = -128;
        for (int i = 1; i < n2 - 7; ++i) {
            array3[i] = 0;
        }
        final int[] q = this.q;
        final int n3 = 1;
        q[n3] += this.r;
        if (this.q[1] < this.r) {
            final int[] q2 = this.q;
            final int n4 = 0;
            ++q2[n4];
        }
        array2[0] = (this.q[0] << 3 | this.q[1] >>> 29);
        array2[1] = this.q[1] << 3;
        array3[n2 - 8] = (byte)((array2[0] & 0xFF000000) >>> 24);
        array3[n2 - 7] = (byte)((array2[0] & 0xFF0000) >>> 16);
        array3[n2 - 6] = (byte)((array2[0] & 0xFF00) >>> 8);
        array3[n2 - 5] = (byte)(array2[0] & 0xFF);
        array3[n2 - 4] = (byte)((array2[1] & 0xFF000000) >> 24);
        array3[n2 - 3] = (byte)((array2[1] & 0xFF0000) >> 16);
        array3[n2 - 2] = (byte)((array2[1] & 0xFF00) >> 8);
        array3[n2 - 1] = (byte)(array2[1] & 0xFF);
        this.a(array3, 0, n2);
        this.c(array, n);
        return 28;
    }
    
    public int a(final byte[] array, final int n, final byte[] array2, int n2) {
        final int n3 = psc_je.ae.length + 28;
        System.arraycopy(psc_je.ae, 0, array2, n2, psc_je.ae.length);
        n2 += psc_je.ae.length;
        System.arraycopy(array, n, array2, n2, array.length);
        return n3;
    }
    
    private void d(final byte[] array, int n) {
        this.s = new int[64];
        for (int i = 0; i < 16; this.s[i++] = ((array[n++] & 0xFF) << 24 | (array[n++] & 0xFF) << 16 | (array[n++] & 0xFF) << 8 | (array[n++] & 0xFF))) {}
        for (int j = 0; j < 48; ++j) {
            final int n2 = this.s[j + 14];
            final int n3 = this.s[j + 1];
            this.s[j + 16] = ((n2 >>> 17 | n2 << 15) ^ (n2 >>> 19 | n2 << 13) ^ n2 >>> 10) + this.s[j + 9] + ((n3 >>> 7 | n3 << 25) ^ (n3 >>> 18 | n3 << 14) ^ n3 >>> 3) + this.s[j];
        }
        int a = this.a;
        int b = this.b;
        int c = this.c;
        int d = this.d;
        int e = this.e;
        int f = this.f;
        int g = this.g;
        int h = this.h;
        int n4;
        int n5;
        int n6;
        int n7;
        int n8;
        int n9;
        int n10;
        int n11;
        int n12;
        int n13;
        int n14;
        int n15;
        int n16;
        int n17;
        int n18;
        int n19;
        for (int k = 0; k < 64; ++k, n5 = d + n4, n6 = n4 + (((a >>> 2 | a << 30) ^ (a >>> 13 | a << 19) ^ (a >>> 22 | a << 10)) + ((a & b) ^ (a & c) ^ (b & c))), n7 = g + (((n5 >>> 6 | n5 << 26) ^ (n5 >>> 11 | n5 << 21) ^ (n5 >>> 25 | n5 << 7)) + ((n5 & e) ^ (~n5 & f)) + this.af[k] + this.s[k]), ++k, n8 = c + n7, n9 = n7 + (((n6 >>> 2 | n6 << 30) ^ (n6 >>> 13 | n6 << 19) ^ (n6 >>> 22 | n6 << 10)) + ((n6 & a) ^ (n6 & b) ^ (a & b))), n10 = f + (((n8 >>> 6 | n8 << 26) ^ (n8 >>> 11 | n8 << 21) ^ (n8 >>> 25 | n8 << 7)) + ((n8 & n5) ^ (~n8 & e)) + this.af[k] + this.s[k]), ++k, n11 = b + n10, n12 = n10 + (((n9 >>> 2 | n9 << 30) ^ (n9 >>> 13 | n9 << 19) ^ (n9 >>> 22 | n9 << 10)) + ((n9 & n6) ^ (n9 & a) ^ (n6 & a))), n13 = e + (((n11 >>> 6 | n11 << 26) ^ (n11 >>> 11 | n11 << 21) ^ (n11 >>> 25 | n11 << 7)) + ((n11 & n8) ^ (~n11 & n5)) + this.af[k] + this.s[k]), ++k, n14 = a + n13, n15 = n13 + (((n12 >>> 2 | n12 << 30) ^ (n12 >>> 13 | n12 << 19) ^ (n12 >>> 22 | n12 << 10)) + ((n12 & n9) ^ (n12 & n6) ^ (n9 & n6))), n16 = n5 + (((n14 >>> 6 | n14 << 26) ^ (n14 >>> 11 | n14 << 21) ^ (n14 >>> 25 | n14 << 7)) + ((n14 & n11) ^ (~n14 & n8)) + this.af[k] + this.s[k]), ++k, h = n6 + n16, d = n16 + (((n15 >>> 2 | n15 << 30) ^ (n15 >>> 13 | n15 << 19) ^ (n15 >>> 22 | n15 << 10)) + ((n15 & n12) ^ (n15 & n9) ^ (n12 & n9))), n17 = n8 + (((h >>> 6 | h << 26) ^ (h >>> 11 | h << 21) ^ (h >>> 25 | h << 7)) + ((h & n14) ^ (~h & n11)) + this.af[k] + this.s[k]), ++k, g = n9 + n17, c = n17 + (((d >>> 2 | d << 30) ^ (d >>> 13 | d << 19) ^ (d >>> 22 | d << 10)) + ((d & n15) ^ (d & n12) ^ (n15 & n12))), n18 = n11 + (((g >>> 6 | g << 26) ^ (g >>> 11 | g << 21) ^ (g >>> 25 | g << 7)) + ((g & h) ^ (~g & n14)) + this.af[k] + this.s[k]), ++k, f = n12 + n18, b = n18 + (((c >>> 2 | c << 30) ^ (c >>> 13 | c << 19) ^ (c >>> 22 | c << 10)) + ((c & d) ^ (c & n15) ^ (d & n15))), n19 = n14 + (((f >>> 6 | f << 26) ^ (f >>> 11 | f << 21) ^ (f >>> 25 | f << 7)) + ((f & g) ^ (~f & h)) + this.af[k] + this.s[k]), ++k, e = n15 + n19, a = n19 + (((b >>> 2 | b << 30) ^ (b >>> 13 | b << 19) ^ (b >>> 22 | b << 10)) + ((b & c) ^ (b & d) ^ (c & d)))) {
            n4 = h + (((e >>> 6 | e << 26) ^ (e >>> 11 | e << 21) ^ (e >>> 25 | e << 7)) + ((e & f) ^ (~e & g)) + this.af[k] + this.s[k]);
        }
        this.a += a;
        this.b += b;
        this.c += c;
        this.d += d;
        this.e += e;
        this.f += f;
        this.g += g;
        this.h += h;
    }
    
    void c(final byte[] array, int n) {
        array[n] = (byte)(this.a >>> 24 & 0xFF);
        ++n;
        array[n] = (byte)(this.a >> 16 & 0xFF);
        ++n;
        array[n] = (byte)(this.a >> 8 & 0xFF);
        ++n;
        array[n] = (byte)(this.a & 0xFF);
        ++n;
        array[n] = (byte)(this.b >>> 24 & 0xFF);
        ++n;
        array[n] = (byte)(this.b >> 16 & 0xFF);
        ++n;
        array[n] = (byte)(this.b >> 8 & 0xFF);
        ++n;
        array[n] = (byte)(this.b & 0xFF);
        ++n;
        array[n] = (byte)(this.c >>> 24 & 0xFF);
        ++n;
        array[n] = (byte)(this.c >> 16 & 0xFF);
        ++n;
        array[n] = (byte)(this.c >> 8 & 0xFF);
        ++n;
        array[n] = (byte)(this.c & 0xFF);
        ++n;
        array[n] = (byte)(this.d >>> 24 & 0xFF);
        ++n;
        array[n] = (byte)(this.d >> 16 & 0xFF);
        ++n;
        array[n] = (byte)(this.d >> 8 & 0xFF);
        ++n;
        array[n] = (byte)(this.d & 0xFF);
        ++n;
        array[n] = (byte)(this.e >>> 24 & 0xFF);
        ++n;
        array[n] = (byte)(this.e >> 16 & 0xFF);
        ++n;
        array[n] = (byte)(this.e >> 8 & 0xFF);
        ++n;
        array[n] = (byte)(this.e & 0xFF);
        ++n;
        array[n] = (byte)(this.f >>> 24 & 0xFF);
        ++n;
        array[n] = (byte)(this.f >> 16 & 0xFF);
        ++n;
        array[n] = (byte)(this.f >> 8 & 0xFF);
        ++n;
        array[n] = (byte)(this.f & 0xFF);
        ++n;
        array[n] = (byte)(this.g >>> 24 & 0xFF);
        ++n;
        array[n] = (byte)(this.g >> 16 & 0xFF);
        ++n;
        array[n] = (byte)(this.g >> 8 & 0xFF);
        ++n;
        array[n] = (byte)(this.g & 0xFF);
        ++n;
    }
    
    public void y() {
        super.y();
        this.d(this.ac);
        this.a = 0;
        this.b = 0;
        this.c = 0;
        this.d = 0;
        this.e = 0;
        this.f = 0;
        this.g = 0;
        this.h = 0;
        this.ad = 1;
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
        ae = new byte[] { 48, 45, 48, 13, 6, 9, 96, -122, 72, 1, 101, 3, 4, 2, 4, 5, 0, 4, 28 };
    }
}
