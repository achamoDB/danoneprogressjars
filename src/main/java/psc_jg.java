import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_jg extends psc_an implements psc_az, psc_ex, psc_ey, psc_er, Cloneable, Serializable
{
    protected long a;
    protected long b;
    protected long c;
    protected long d;
    protected long e;
    protected long f;
    protected long g;
    protected long h;
    private final long i = -3766243637369397544L;
    private final long j = 7105036623409894663L;
    private final long k = -7973340178411365097L;
    private final long l = 1526699215303891257L;
    private final long m = 7436329637833083697L;
    private final long n = -8163818279084223215L;
    private final long o = -2662702644619276377L;
    private final long p = 5167115440072839076L;
    protected long[] q;
    protected int r;
    private long[] s;
    private static final int t = 1;
    private static final int u = 2;
    private static final int v = 3;
    private static final int w = 48;
    private static final int x = 128;
    private static final int y = 16;
    private static final int z = 80;
    private static final int aa = 80;
    private static final int ab = 80;
    private byte[] ac;
    private int ad;
    private static final byte[] ae;
    private final long[] af;
    
    public psc_jg() {
        this.q = new long[2];
        this.ac = new byte[128];
        this.ad = 1;
        this.af = new long[] { 4794697086780616226L, 8158064640168781261L, -5349999486874862801L, -1606136188198331460L, 4131703408338449720L, 6480981068601479193L, -7908458776815382629L, -6116909921290321640L, -2880145864133508542L, 1334009975649890238L, 2608012711638119052L, 6128411473006802146L, 8268148722764581231L, -9160688886553864527L, -7215885187991268811L, -4495734319001033068L, -1973867731355612462L, -1171420211273849373L, 1135362057144423861L, 2597628984639134821L, 3308224258029322869L, 5365058923640841347L, 6679025012923562964L, 8573033837759648693L, -7476448914759557205L, -6327057829258317296L, -5763719355590565569L, -4658551843659510044L, -4116276920077217854L, -3051310485924567259L, 489312712824947311L, 1452737877330783856L, 2861767655752347644L, 3322285676063803686L, 5560940570517711597L, 5996557281743188959L, 7280758554555802590L, 8532644243296465576L, -9096487096722542874L, -7894198246740708037L, -6719396339535248540L, -6333637450476146687L, -4446306890439682159L, -4076793802049405392L, -3345356375505022440L, -2983346525034927856L, -860691631967231958L, 1182934255886127544L, 1847814050463011016L, 2177327727835720531L, 2830643537854262169L, 3796741975233480872L, 4115178125766777443L, 5681478168544905931L, 6601373596472566643L, 7507060721942968483L, 8399075790359081724L, 8693463985226723168L, -8878714635349349518L, -8302665154208450068L, -8016688836872298968L, -6606660893046293015L, -4685533653050689259L, -4147400797238176981L, -3880063495543823972L, -3348786107499101689L, -1523767162380948706L, -757361751448694408L, 500013540394364858L, 748580250866718886L, 1242879168328830382L, 1977374033974150939L, 2944078676154940804L, 3659926193048069267L, 4368137639120453308L, 4836135668995329356L, 5532061633213252278L, 6448918945643986474L, 6902733635092675308L, 7801388544844847127L };
    }
    
    public psc_jg(final int[] array) throws psc_be {
        this.q = new long[2];
        this.ac = new byte[128];
        this.ad = 1;
        this.af = new long[] { 4794697086780616226L, 8158064640168781261L, -5349999486874862801L, -1606136188198331460L, 4131703408338449720L, 6480981068601479193L, -7908458776815382629L, -6116909921290321640L, -2880145864133508542L, 1334009975649890238L, 2608012711638119052L, 6128411473006802146L, 8268148722764581231L, -9160688886553864527L, -7215885187991268811L, -4495734319001033068L, -1973867731355612462L, -1171420211273849373L, 1135362057144423861L, 2597628984639134821L, 3308224258029322869L, 5365058923640841347L, 6679025012923562964L, 8573033837759648693L, -7476448914759557205L, -6327057829258317296L, -5763719355590565569L, -4658551843659510044L, -4116276920077217854L, -3051310485924567259L, 489312712824947311L, 1452737877330783856L, 2861767655752347644L, 3322285676063803686L, 5560940570517711597L, 5996557281743188959L, 7280758554555802590L, 8532644243296465576L, -9096487096722542874L, -7894198246740708037L, -6719396339535248540L, -6333637450476146687L, -4446306890439682159L, -4076793802049405392L, -3345356375505022440L, -2983346525034927856L, -860691631967231958L, 1182934255886127544L, 1847814050463011016L, 2177327727835720531L, 2830643537854262169L, 3796741975233480872L, 4115178125766777443L, 5681478168544905931L, 6601373596472566643L, 7507060721942968483L, 8399075790359081724L, 8693463985226723168L, -8878714635349349518L, -8302665154208450068L, -8016688836872298968L, -6606660893046293015L, -4685533653050689259L, -4147400797238176981L, -3880063495543823972L, -3348786107499101689L, -1523767162380948706L, -757361751448694408L, 500013540394364858L, 748580250866718886L, 1242879168328830382L, 1977374033974150939L, 2944078676154940804L, 3659926193048069267L, 4368137639120453308L, 4836135668995329356L, 5532061633213252278L, 6448918945643986474L, 6902733635092675308L, 7801388544844847127L };
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
        return "SHA384";
    }
    
    public void a(final byte[] array, final int n) {
    }
    
    public byte[] d() {
        byte[] a;
        try {
            a = psc_q.a("SHA384", 10, null, 0, 0);
        }
        catch (psc_m psc_m) {
            a = null;
        }
        return a;
    }
    
    public int f() {
        return 128;
    }
    
    public int g() {
        return 16;
    }
    
    public int h() {
        return 48;
    }
    
    public int i() {
        return psc_jg.ae.length + 48;
    }
    
    protected psc_jg k() {
        return new psc_jg();
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_jg k = this.k();
        k.a = this.a;
        k.b = this.b;
        k.c = this.c;
        k.d = this.d;
        k.e = this.e;
        k.f = this.f;
        k.g = this.g;
        k.h = this.h;
        k.r = this.r;
        k.ac = new byte[128];
        k.q = new long[2];
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
        this.q[1] = (this.q[0] = 0L);
        this.r = 0;
        this.a = -3766243637369397544L;
        this.b = 7105036623409894663L;
        this.c = -7973340178411365097L;
        this.d = 1526699215303891257L;
        this.e = 7436329637833083697L;
        this.f = -8163818279084223215L;
        this.g = -2662702644619276377L;
        this.h = 5167115440072839076L;
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
        final int n3 = this.r & 0x7F;
        if (n2 <= 128 - n3) {
            System.arraycopy(array, n, this.ac, n3, n2);
            this.r += n2;
            if ((this.r & 0x7F) == 0x0) {
                this.d(this.ac, 0);
                final long[] q = this.q;
                final int n4 = 1;
                q[n4] += 128L;
                if (this.q[1] < 128L) {
                    final long[] q2 = this.q;
                    final int n5 = 0;
                    ++q2[n5];
                }
                this.r = 0;
            }
        }
        else {
            System.arraycopy(array, n, this.ac, n3, 128 - n3);
            n += 128 - n3;
            this.d(this.ac, 0);
            final long[] q3 = this.q;
            final int n6 = 1;
            q3[n6] += 128L;
            if (this.q[1] < 128L) {
                final long[] q4 = this.q;
                final int n7 = 0;
                ++q4[n7];
            }
            int i;
            for (i = n2 - (128 - n3); i >= 128; i -= 128, n += 128) {
                System.arraycopy(array, n, this.ac, 0, 128);
                this.d(this.ac, 0);
                final long[] q5 = this.q;
                final int n8 = 1;
                q5[n8] += 128L;
                if (this.q[1] < 128L) {
                    final long[] q6 = this.q;
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
        final long[] array2 = new long[2];
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
        int n2 = 128 - (this.r & 0x7F);
        if (n2 <= 16) {
            n2 += 128;
        }
        array3[0] = -128;
        for (int i = 1; i < n2 - 15; ++i) {
            array3[i] = 0;
        }
        final long[] q = this.q;
        final int n3 = 1;
        q[n3] += this.r;
        if (this.q[1] < this.r) {
            final long[] q2 = this.q;
            final int n4 = 0;
            ++q2[n4];
        }
        array2[0] = (this.q[0] << 3 | this.q[1] >>> 61);
        array2[1] = this.q[1] << 3;
        array3[n2 - 16] = (byte)((array2[0] & 0xFF00000000000000L) >>> 56);
        array3[n2 - 15] = (byte)((array2[0] & 0xFF000000000000L) >>> 48);
        array3[n2 - 14] = (byte)((array2[0] & 0xFF0000000000L) >>> 40);
        array3[n2 - 13] = (byte)((array2[0] & 0xFF00000000L) >>> 32);
        array3[n2 - 12] = (byte)((array2[0] & 0xFF000000L) >>> 24);
        array3[n2 - 11] = (byte)((array2[0] & 0xFF0000L) >>> 16);
        array3[n2 - 10] = (byte)((array2[0] & 0xFF00L) >>> 8);
        array3[n2 - 9] = (byte)(array2[0] & 0xFFL);
        array3[n2 - 8] = (byte)((array2[1] & 0xFF00000000000000L) >>> 56);
        array3[n2 - 7] = (byte)((array2[1] & 0xFF000000000000L) >>> 48);
        array3[n2 - 6] = (byte)((array2[1] & 0xFF0000000000L) >>> 40);
        array3[n2 - 5] = (byte)((array2[1] & 0xFF00000000L) >>> 32);
        array3[n2 - 4] = (byte)((array2[1] & 0xFF000000L) >>> 24);
        array3[n2 - 3] = (byte)((array2[1] & 0xFF0000L) >>> 16);
        array3[n2 - 2] = (byte)((array2[1] & 0xFF00L) >>> 8);
        array3[n2 - 1] = (byte)(array2[1] & 0xFFL);
        this.a(array3, 0, n2);
        this.c(array, n);
        return 48;
    }
    
    public int a(final byte[] array, final int n, final byte[] array2, int n2) {
        final int n3 = psc_jg.ae.length + 48;
        System.arraycopy(psc_jg.ae, 0, array2, n2, psc_jg.ae.length);
        n2 += psc_jg.ae.length;
        System.arraycopy(array, n, array2, n2, array.length);
        return n3;
    }
    
    private static final long a(final long n) {
        return (n >>> 19 | n << 45) ^ (n >>> 61 | n << 3) ^ n >>> 6;
    }
    
    private static final long b(final long n) {
        return (n >>> 1 | n << 63) ^ (n >>> 8 | n << 56) ^ n >>> 7;
    }
    
    private void d(final byte[] array, int n) {
        this.s = new long[80];
        for (int i = 0; i < 16; ++i) {
            this.s[i] = ((long)(array[n + 0] & 0xFF) << 56 | (long)(array[n + 1] & 0xFF) << 48 | (long)(array[n + 2] & 0xFF) << 40 | (long)(array[n + 3] & 0xFF) << 32 | (long)(array[n + 4] & 0xFF) << 24 | (long)(array[n + 5] & 0xFF) << 16 | (long)(array[n + 6] & 0xFF) << 8 | ((long)array[n + 7] & 0xFFL));
            n += 8;
        }
        for (int j = 0; j < 64; ++j) {
            this.s[j + 16] = ((this.s[j + 14] >>> 19 | this.s[j + 14] << 45) ^ (this.s[j + 14] >>> 61 | this.s[j + 14] << 3) ^ this.s[j + 14] >>> 6) + this.s[j + 9] + ((this.s[j + 1] >>> 1 | this.s[j + 1] << 63) ^ (this.s[j + 1] >>> 8 | this.s[j + 1] << 56) ^ this.s[j + 1] >>> 7) + this.s[j];
        }
        long a = this.a;
        long b = this.b;
        long c = this.c;
        long d = this.d;
        long e = this.e;
        long f = this.f;
        long g = this.g;
        long h = this.h;
        long n2;
        long n3;
        long n4;
        long n5;
        long n6;
        long n7;
        long n8;
        long n9;
        long n10;
        long n11;
        long n12;
        long n13;
        long n14;
        long n15;
        long n16;
        long n17;
        for (int k = 0; k < 80; ++k, n3 = d + n2, n4 = n2 + (((a >>> 28 | a << 36) ^ (a >>> 34 | a << 30) ^ (a >>> 39 | a << 25)) + ((a & b) ^ (a & c) ^ (b & c))), n5 = g + (((n3 >>> 14 | n3 << 50) ^ (n3 >>> 18 | n3 << 46) ^ (n3 >>> 41 | n3 << 23)) + ((n3 & e) ^ (~n3 & f)) + this.af[k] + this.s[k]), ++k, n6 = c + n5, n7 = n5 + (((n4 >>> 28 | n4 << 36) ^ (n4 >>> 34 | n4 << 30) ^ (n4 >>> 39 | n4 << 25)) + ((n4 & a) ^ (n4 & b) ^ (a & b))), n8 = f + (((n6 >>> 14 | n6 << 50) ^ (n6 >>> 18 | n6 << 46) ^ (n6 >>> 41 | n6 << 23)) + ((n6 & n3) ^ (~n6 & e)) + this.af[k] + this.s[k]), ++k, n9 = b + n8, n10 = n8 + (((n7 >>> 28 | n7 << 36) ^ (n7 >>> 34 | n7 << 30) ^ (n7 >>> 39 | n7 << 25)) + ((n7 & n4) ^ (n7 & a) ^ (n4 & a))), n11 = e + (((n9 >>> 14 | n9 << 50) ^ (n9 >>> 18 | n9 << 46) ^ (n9 >>> 41 | n9 << 23)) + ((n9 & n6) ^ (~n9 & n3)) + this.af[k] + this.s[k]), ++k, n12 = a + n11, n13 = n11 + (((n10 >>> 28 | n10 << 36) ^ (n10 >>> 34 | n10 << 30) ^ (n10 >>> 39 | n10 << 25)) + ((n10 & n7) ^ (n10 & n4) ^ (n7 & n4))), n14 = n3 + (((n12 >>> 14 | n12 << 50) ^ (n12 >>> 18 | n12 << 46) ^ (n12 >>> 41 | n12 << 23)) + ((n12 & n9) ^ (~n12 & n6)) + this.af[k] + this.s[k]), ++k, h = n4 + n14, d = n14 + (((n13 >>> 28 | n13 << 36) ^ (n13 >>> 34 | n13 << 30) ^ (n13 >>> 39 | n13 << 25)) + ((n13 & n10) ^ (n13 & n7) ^ (n10 & n7))), n15 = n6 + (((h >>> 14 | h << 50) ^ (h >>> 18 | h << 46) ^ (h >>> 41 | h << 23)) + ((h & n12) ^ (~h & n9)) + this.af[k] + this.s[k]), ++k, g = n7 + n15, c = n15 + (((d >>> 28 | d << 36) ^ (d >>> 34 | d << 30) ^ (d >>> 39 | d << 25)) + ((d & n13) ^ (d & n10) ^ (n13 & n10))), n16 = n9 + (((g >>> 14 | g << 50) ^ (g >>> 18 | g << 46) ^ (g >>> 41 | g << 23)) + ((g & h) ^ (~g & n12)) + this.af[k] + this.s[k]), ++k, f = n10 + n16, b = n16 + (((c >>> 28 | c << 36) ^ (c >>> 34 | c << 30) ^ (c >>> 39 | c << 25)) + ((c & d) ^ (c & n13) ^ (d & n13))), n17 = n12 + (((f >>> 14 | f << 50) ^ (f >>> 18 | f << 46) ^ (f >>> 41 | f << 23)) + ((f & g) ^ (~f & h)) + this.af[k] + this.s[k]), ++k, e = n13 + n17, a = n17 + (((b >>> 28 | b << 36) ^ (b >>> 34 | b << 30) ^ (b >>> 39 | b << 25)) + ((b & c) ^ (b & d) ^ (c & d)))) {
            n2 = h + (((e >>> 14 | e << 50) ^ (e >>> 18 | e << 46) ^ (e >>> 41 | e << 23)) + ((e & f) ^ (~e & g)) + this.af[k] + this.s[k]);
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
        array[n] = (byte)(this.a >>> 56 & 0xFFL);
        ++n;
        array[n] = (byte)(this.a >> 48 & 0xFFL);
        ++n;
        array[n] = (byte)(this.a >> 40 & 0xFFL);
        ++n;
        array[n] = (byte)(this.a >> 32 & 0xFFL);
        ++n;
        array[n] = (byte)(this.a >> 24 & 0xFFL);
        ++n;
        array[n] = (byte)(this.a >> 16 & 0xFFL);
        ++n;
        array[n] = (byte)(this.a >> 8 & 0xFFL);
        ++n;
        array[n] = (byte)(this.a & 0xFFL);
        ++n;
        array[n] = (byte)(this.b >>> 56 & 0xFFL);
        ++n;
        array[n] = (byte)(this.b >> 48 & 0xFFL);
        ++n;
        array[n] = (byte)(this.b >> 40 & 0xFFL);
        ++n;
        array[n] = (byte)(this.b >> 32 & 0xFFL);
        ++n;
        array[n] = (byte)(this.b >> 24 & 0xFFL);
        ++n;
        array[n] = (byte)(this.b >> 16 & 0xFFL);
        ++n;
        array[n] = (byte)(this.b >> 8 & 0xFFL);
        ++n;
        array[n] = (byte)(this.b & 0xFFL);
        ++n;
        array[n] = (byte)(this.c >>> 56 & 0xFFL);
        ++n;
        array[n] = (byte)(this.c >> 48 & 0xFFL);
        ++n;
        array[n] = (byte)(this.c >> 40 & 0xFFL);
        ++n;
        array[n] = (byte)(this.c >> 32 & 0xFFL);
        ++n;
        array[n] = (byte)(this.c >> 24 & 0xFFL);
        ++n;
        array[n] = (byte)(this.c >> 16 & 0xFFL);
        ++n;
        array[n] = (byte)(this.c >> 8 & 0xFFL);
        ++n;
        array[n] = (byte)(this.c & 0xFFL);
        ++n;
        array[n] = (byte)(this.d >>> 56 & 0xFFL);
        ++n;
        array[n] = (byte)(this.d >> 48 & 0xFFL);
        ++n;
        array[n] = (byte)(this.d >> 40 & 0xFFL);
        ++n;
        array[n] = (byte)(this.d >> 32 & 0xFFL);
        ++n;
        array[n] = (byte)(this.d >> 24 & 0xFFL);
        ++n;
        array[n] = (byte)(this.d >> 16 & 0xFFL);
        ++n;
        array[n] = (byte)(this.d >> 8 & 0xFFL);
        ++n;
        array[n] = (byte)(this.d & 0xFFL);
        ++n;
        array[n] = (byte)(this.e >>> 56 & 0xFFL);
        ++n;
        array[n] = (byte)(this.e >> 48 & 0xFFL);
        ++n;
        array[n] = (byte)(this.e >> 40 & 0xFFL);
        ++n;
        array[n] = (byte)(this.e >> 32 & 0xFFL);
        ++n;
        array[n] = (byte)(this.e >> 24 & 0xFFL);
        ++n;
        array[n] = (byte)(this.e >> 16 & 0xFFL);
        ++n;
        array[n] = (byte)(this.e >> 8 & 0xFFL);
        ++n;
        array[n] = (byte)(this.e & 0xFFL);
        ++n;
        array[n] = (byte)(this.f >>> 56 & 0xFFL);
        ++n;
        array[n] = (byte)(this.f >> 48 & 0xFFL);
        ++n;
        array[n] = (byte)(this.f >> 40 & 0xFFL);
        ++n;
        array[n] = (byte)(this.f >> 32 & 0xFFL);
        ++n;
        array[n] = (byte)(this.f >> 24 & 0xFFL);
        ++n;
        array[n] = (byte)(this.f >> 16 & 0xFFL);
        ++n;
        array[n] = (byte)(this.f >> 8 & 0xFFL);
        ++n;
        array[n] = (byte)(this.f & 0xFFL);
        ++n;
    }
    
    public void y() {
        super.y();
        this.d(this.ac);
        this.a = 0L;
        this.b = 0L;
        this.c = 0L;
        this.d = 0L;
        this.e = 0L;
        this.f = 0L;
        this.g = 0L;
        this.h = 0L;
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
        ae = new byte[] { 48, 65, 48, 13, 6, 9, 96, -122, 72, 1, 101, 3, 4, 2, 2, 5, 0, 4, 48 };
    }
}
