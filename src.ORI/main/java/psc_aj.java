import java.util.Vector;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public abstract class psc_aj implements Cloneable, Serializable
{
    protected String a;
    protected static final int b = 85;
    protected static final int c = 29;
    public static final int d = 14;
    public static final int e = 35;
    public static final int f = 15;
    public static final int g = 16;
    public static final int h = 17;
    public static final int i = 18;
    public static final int j = 19;
    public static final int k = 9;
    public static final int l = 30;
    public static final int m = 32;
    public static final int n = 33;
    public static final int o = 36;
    public static final int p = 37;
    public static final int q = 20;
    public static final int r = 21;
    public static final int s = 23;
    public static final int t = 24;
    public static final int u = 27;
    public static final int v = 28;
    public static final int w = 29;
    public static final int x = 31;
    public static final int y = 54;
    private static final int z = 100;
    public static final int aa = 100;
    public static final byte[] ab;
    public static final int ac = 101;
    public static final byte[] ad;
    public static final int ae = 102;
    public static final byte[] af;
    public static final int ag = 103;
    public static final byte[] ah;
    public static final int ai = 104;
    public static final byte[] aj;
    public static final int ak = 105;
    public static final byte[] al;
    public static final int am = 106;
    public static final byte[] an;
    public static final int ao = 107;
    public static final byte[] ap;
    public static final int aq = 108;
    public static final byte[] ar;
    public static final int as = 109;
    public static final byte[] at;
    public static final int au = 110;
    public static final byte[] av;
    public static final int aw = 111;
    public static final byte[] ax;
    public static final int ay = 112;
    public static final byte[] az;
    public static final int a0 = 113;
    public static final byte[] a1;
    public static final int a2 = 114;
    public static final byte[] a3;
    public static final int a4 = 115;
    public static final byte[] a5;
    public static final int a6 = 116;
    public static final byte[] a7;
    public static final int a8 = 117;
    public static final byte[] a9;
    public static final int ba = 118;
    public static final byte[] bb;
    public static final int bc = 119;
    public static final byte[] bd;
    public static final int be = 120;
    public static final byte[] bf;
    public static final int bg = 121;
    public static final byte[] bh;
    public static final int bi = 122;
    public static final byte[] bj;
    public static final int bk = 123;
    public static final byte[] bl;
    public static final int bm = 124;
    public static final byte[] bn;
    public static final int bo = -1;
    private static final int[] bp;
    private static final byte[][] bq;
    private static Vector br;
    private static Vector bs;
    protected boolean bt;
    protected int bu;
    byte[] bv;
    int bw;
    protected int bx;
    protected psc_n by;
    protected byte[] bz;
    
    public psc_aj() {
        this.bz = null;
    }
    
    public static void a(final byte[] obj, final psc_aj obj2) throws psc_g {
        if (obj == null || obj2 == null) {
            throw new psc_g("X509V3Extension.extend: neither oid nor extension should be null.");
        }
        if (d(obj)) {
            throw new psc_g("X509V3Extension.extend: oid is already in use.");
        }
        final psc_aj a = a(obj, 0, obj.length);
        if (a == null) {
            psc_aj.br.addElement(obj);
            psc_aj.bs.addElement(obj2);
        }
        else if (!obj2.getClass().isInstance(a)) {
            throw new psc_g("X509V3Extension.extend: there exists an extended extension using the same OID.");
        }
    }
    
    private static boolean d(final byte[] array) {
        return e(array) || c(array);
    }
    
    private static boolean e(final byte[] array) {
        if (array.length != 3) {
            return false;
        }
        if (array[0] != 85) {
            return false;
        }
        if (array[1] != 29) {
            return false;
        }
        for (int i = 0; i < psc_aj.bp.length; ++i) {
            if (array[2] == psc_aj.bp[i]) {
                return true;
            }
        }
        return false;
    }
    
    private static boolean c(final byte[] array) {
        final int length = array.length;
        for (int i = 0; i < psc_aj.bq.length; ++i) {
            if (a(psc_aj.bq[i], array, 0, length)) {
                return true;
            }
        }
        return false;
    }
    
    private static psc_aj a(final byte[] array, final int n, final int n2) throws psc_g {
        for (int size = psc_aj.br.size(), i = 0; i < size; ++i) {
            if (a((byte[])psc_aj.br.elementAt(i), array, n, n2)) {
                try {
                    return (psc_aj)((psc_aj)psc_aj.bs.elementAt(i)).clone();
                }
                catch (CloneNotSupportedException ex) {
                    throw new psc_g("X509V3Extension.findExtendedExtension: extended extension should support clone method(" + ex.getMessage() + ").");
                }
            }
        }
        return null;
    }
    
    private static boolean a(final byte[] array, final byte[] array2, final int n, final int n2) {
        if (array.length != n2) {
            return false;
        }
        for (int i = 0; i < n2; ++i) {
            if (array[i] != array2[n + i]) {
                return false;
            }
        }
        return true;
    }
    
    public static psc_aj a(final byte[] array, final int n) throws psc_g {
        if (array == null) {
            throw new psc_g("Encoding is null.");
        }
        final psc_i[] c = c(array, n);
        Cloneable a = null;
        switch (f(c[1].b, c[1].c, c[1].d)) {
            case 14: {
                a = new psc_bi();
                break;
            }
            case 35: {
                a = new psc_bk();
                break;
            }
            case 15: {
                a = new psc_bn();
                break;
            }
            case 16: {
                a = new psc_bo();
                break;
            }
            case 17: {
                a = new psc_bp();
                break;
            }
            case 18: {
                a = new psc_bq();
                break;
            }
            case 19: {
                a = new psc_br();
                break;
            }
            case 9: {
                a = new psc_bs();
                break;
            }
            case 30: {
                a = new psc_bt();
                break;
            }
            case 32: {
                a = new psc_bv();
                break;
            }
            case 33: {
                a = new psc_bw();
                break;
            }
            case 36: {
                a = new psc_bx();
                break;
            }
            case 37: {
                a = new psc_by();
                break;
            }
            case 20: {
                a = new psc_bz();
                break;
            }
            case 21: {
                a = new psc_b0();
                break;
            }
            case 23: {
                a = new psc_b3();
                break;
            }
            case 24: {
                a = new psc_b4();
                break;
            }
            case 27: {
                a = new psc_b5();
                break;
            }
            case 28: {
                a = new psc_b6();
                break;
            }
            case 29: {
                a = new psc_b7();
                break;
            }
            case 31: {
                a = new psc_b8();
                break;
            }
            case 54: {
                a = new psc_b9();
                break;
            }
            case 100: {
                a = new psc_ca();
                break;
            }
            case 101: {
                a = new psc_cb();
                break;
            }
            case 102: {
                a = new psc_cc();
                break;
            }
            case 103: {
                a = new psc_cd();
                break;
            }
            case 104: {
                a = new psc_ce();
                break;
            }
            case 105: {
                a = new psc_cf();
                break;
            }
            case 106: {
                a = new psc_cg();
                break;
            }
            case 107: {
                a = new psc_ch();
                break;
            }
            case 108: {
                a = new psc_ci();
                break;
            }
            case 109: {
                a = new psc_cj();
                break;
            }
            case 110: {
                a = new psc_ck();
                break;
            }
            case 111: {
                a = new psc_cl();
                break;
            }
            case 112: {
                a = new psc_cm();
                break;
            }
            case 113: {
                a = new psc_cn();
                break;
            }
            case 114: {
                a = new psc_co();
                break;
            }
            case 115: {
                a = new psc_cp();
                break;
            }
            case 116: {
                a = new psc_cq();
                break;
            }
            case 117: {
                a = new psc_cs();
                break;
            }
            case 118: {
                a = new psc_ct();
                break;
            }
            case 119: {
                a = new psc_cu();
                break;
            }
            case 120: {
                a = new psc_cv();
                break;
            }
            case 121: {
                a = new psc_cx();
                break;
            }
            case 122: {
                a = new psc_cy();
                break;
            }
            case 123: {
                a = new psc_cz();
                break;
            }
            case 124: {
                a = new psc_c0();
                break;
            }
            case -1: {
                a = a(c[1].b, c[1].c, c[1].d);
                if (a != null) {
                    break;
                }
                a = new psc_c1();
                ((psc_aj)a).bv = new byte[c[1].d];
                System.arraycopy(c[1].b, c[1].c, ((psc_aj)a).bv, 0, c[1].d);
                break;
            }
            default: {
                throw new psc_g("Unknown extension.");
            }
        }
        ((psc_aj)a).a(((psc_bh)c[2]).a);
        try {
            ((psc_aj)a).b(c[3].b, c[3].c, c[3].d);
            ((psc_aj)a).d(c[3].b, c[3].c);
        }
        catch (psc_g psc_g) {
            if (((psc_aj)a).b()) {
                throw new psc_g(psc_g.getMessage());
            }
        }
        ((psc_aj)a).c(c[1].b, c[1].c, c[1].d);
        return (psc_aj)a;
    }
    
    public void b(final byte[] array, final int n, final int n2) {
        System.arraycopy(array, n, this.bz = new byte[n2], 0, n2);
    }
    
    public static int b(final byte[] array, final int n) throws psc_g {
        if (array == null) {
            throw new psc_g("Encoding is null.");
        }
        try {
            return n + psc_o.b(array, n);
        }
        catch (psc_m psc_m) {
            throw new psc_g("Could not read the BER encoding.");
        }
    }
    
    protected static psc_i[] c(final byte[] array, final int n) throws psc_g {
        if (array == null) {
            throw new psc_g("Encoding is null.");
        }
        final psc_i[] array2 = { new psc_h(0), new psc_r(16777216), new psc_bh(131072), new psc_t(0), new psc_j() };
        try {
            psc_l.a(array, n, array2);
        }
        catch (psc_m psc_m) {
            throw new psc_g("Cannot read the BER of the extension.");
        }
        return array2;
    }
    
    protected void c(final byte[] array, final int n, final int n2) {
    }
    
    public void c(final int n) {
        (this.bv = new byte[3])[0] = 85;
        this.bv[1] = 29;
        this.bv[2] = (byte)n;
        this.bw = 3;
    }
    
    public void a(final byte[] array) {
        if (array != null) {
            this.bw = array.length;
            System.arraycopy(array, 0, this.bv = new byte[this.bw], 0, this.bw);
        }
    }
    
    private static int f(final byte[] array, final int n, final int n2) {
        if (n2 == 3 && (array[n] & 0xFF) == 0x55 && (array[n + 1] & 0xFF) == 0x1D) {
            final int i = array[n + 2] & 0xFF;
            int n3 = 0;
            while (i != psc_aj.bp[n3]) {
                ++n3;
                if (psc_aj.bp[n3] == -1) {
                    return -1;
                }
            }
            return i;
        }
        for (int j = 0; j < psc_aj.bq.length; ++j) {
            if (a(psc_aj.bq[j], 0, psc_aj.bq[j].length, array, n, n2)) {
                return 100 + j;
            }
        }
        return -1;
    }
    
    private static boolean a(final byte[] array, final int n, final int n2, final byte[] array2, final int n3, final int n4) {
        if (array == null || array2 == null) {
            return array == null && array2 == null;
        }
        if (n2 != n4) {
            return false;
        }
        for (int i = 0; i < n2; ++i) {
            if (array[i + n] != array2[i + n3]) {
                return false;
            }
        }
        return true;
    }
    
    public void a(final boolean bt) {
        if (bt == this.bt) {
            return;
        }
        this.bt = bt;
    }
    
    public boolean b() {
        return this.bt;
    }
    
    public abstract void d(final byte[] p0, final int p1) throws psc_g;
    
    public int c() {
        return this.bu;
    }
    
    public String d() {
        return this.a;
    }
    
    public boolean d(final int n) {
        return n == this.bu;
    }
    
    public int e(final int n) {
        if (this.bz != null) {
            return this.a(n, this.bz.length);
        }
        return this.a(n, this.e());
    }
    
    public abstract int e();
    
    public int d(final byte[] array, final int n, final int n2) {
        if (array == null || n < 0) {
            return 0;
        }
        return this.e(array, n, n2);
    }
    
    protected int a(final int bx, final int n) {
        this.bx = bx;
        try {
            final psc_h psc_h = new psc_h(0, true, 0);
            final psc_j psc_j = new psc_j();
            final psc_r psc_r = new psc_r(16777216, true, 0, this.bv, 0, this.bw);
            psc_bh psc_bh;
            if (!this.bt) {
                psc_bh = new psc_bh(131072, false, 0, this.bt);
            }
            else {
                psc_bh = new psc_bh(131072, true, 0, this.bt);
            }
            this.by = new psc_n(new psc_i[] { psc_h, psc_r, psc_bh, new psc_t(0, true, 0, null, 0, n), psc_j });
            return this.by.a();
        }
        catch (psc_m psc_m) {
            return 0;
        }
    }
    
    protected int e(final byte[] array, final int n, final int n2) {
        if (array == null) {
            return 0;
        }
        if ((this.by == null || this.bx != n2) && this.e(n2) == 0) {
            return 0;
        }
        int a;
        try {
            a = this.by.a(array, n);
            this.by = null;
        }
        catch (psc_m psc_m) {
            this.by = null;
            return 0;
        }
        if (this.bz == null) {
            return a + this.e(array, n + a);
        }
        if (array.length - (n + a) < this.bz.length) {
            return 0;
        }
        System.arraycopy(this.bz, 0, array, n + a, this.bz.length);
        return this.bz.length + a;
    }
    
    public abstract int e(final byte[] p0, final int p1);
    
    public abstract Object clone() throws CloneNotSupportedException;
    
    protected void a(final psc_aj psc_aj) {
        if (psc_aj == null) {
            return;
        }
        psc_aj.bt = this.bt;
        if (this.bv != null) {
            psc_aj.bv = this.bv.clone();
            psc_aj.bw = this.bw;
        }
        psc_aj.bx = this.bx;
        if (this.by != null) {
            psc_aj.e(this.bx);
        }
    }
    
    protected void f() {
        this.by = null;
    }
    
    protected byte[] f(final int n) {
        final int n2 = n >>> 24;
        final int n3 = n >>> 16 & 0xFF;
        final int n4 = n >>> 8 & 0xFF;
        final int n5 = n & 0xFF;
        byte[] array;
        if (n2 != 0) {
            array = new byte[] { (byte)n2, (byte)n3, (byte)n4, (byte)n5 };
        }
        else if (n3 != 0) {
            array = new byte[] { (byte)n3, (byte)n4, (byte)n5 };
        }
        else if (n4 != 0) {
            array = new byte[] { (byte)n4, (byte)n5 };
        }
        else {
            array = new byte[] { (byte)n5 };
        }
        return array;
    }
    
    protected int b(final byte[] array) {
        int n = 0;
        for (int i = 0; i < array.length; ++i) {
            n = (n << 8) + (array[i] & 0xFF);
        }
        return n;
    }
    
    static {
        ab = new byte[] { 43, 6, 1, 5, 5, 7, 1, 1 };
        ad = new byte[] { 96, -122, 72, 1, -122, -8, 66, 1, 1 };
        af = new byte[] { 96, -122, 72, 1, -122, -8, 66, 1, 2 };
        ah = new byte[] { 96, -122, 72, 1, -122, -8, 66, 1, 3 };
        aj = new byte[] { 96, -122, 72, 1, -122, -8, 66, 1, 4 };
        al = new byte[] { 96, -122, 72, 1, -122, -8, 66, 1, 7 };
        an = new byte[] { 96, -122, 72, 1, -122, -8, 66, 1, 8 };
        ap = new byte[] { 96, -122, 72, 1, -122, -8, 66, 1, 12 };
        ar = new byte[] { 96, -122, 72, 1, -122, -8, 66, 1, 13 };
        at = new byte[] { 96, -122, 72, 1, -122, -8, 69, 1, 6, 3 };
        av = new byte[] { 96, -122, 72, 1, -122, -8, 69, 1, 6, 5 };
        ax = new byte[] { 96, -122, 72, 1, -122, -8, 69, 1, 6, 6 };
        az = new byte[] { 96, -122, 72, 1, -122, -8, 69, 1, 6, 10 };
        a1 = new byte[] { 96, -122, 72, 1, -122, -8, 69, 1, 6, 11 };
        a3 = new byte[] { 96, -122, 72, 1, -122, -8, 69, 1, 6, 8 };
        a5 = new byte[] { 96, -122, 72, 1, -122, -8, 69, 1, 6, 7 };
        a7 = new byte[] { 96, -122, 72, 1, -122, -8, 69, 1, 6, 4 };
        a9 = new byte[] { 43, 6, 1, 5, 5, 7, 48, 1, 5 };
        bb = new byte[] { 43, 6, 1, 5, 5, 7, 48, 1, 6 };
        bd = new byte[] { 43, 6, 1, 5, 5, 7, 48, 1, 3 };
        bf = new byte[] { 43, 6, 1, 5, 5, 7, 48, 1, 2 };
        bh = new byte[] { 43, 6, 1, 5, 5, 7, 48, 1, 4 };
        bj = new byte[] { 43, 6, 1, 5, 5, 7, 48, 1, 7 };
        bl = new byte[] { 43, 6, 1, 5, 5, 7, 1, 3 };
        bn = new byte[] { 43, 6, 1, 5, 5, 7, 1, 2 };
        bp = new int[] { 14, 35, 15, 16, 17, 18, 19, 9, 30, 32, 33, 36, 37, 20, 21, 23, 24, 27, 28, 29, 31, 54, -1 };
        bq = new byte[][] { psc_aj.ab, psc_aj.ad, psc_aj.af, psc_aj.ah, psc_aj.aj, psc_aj.al, psc_aj.an, psc_aj.ap, psc_aj.ar, psc_aj.at, psc_aj.av, psc_aj.ax, psc_aj.az, psc_aj.a1, psc_aj.a3, psc_aj.a5, psc_aj.a7, psc_aj.a9, psc_aj.bb, psc_aj.bd, psc_aj.bf, psc_aj.bh, psc_aj.bj, psc_aj.bl, psc_aj.bn };
        psc_aj.br = new Vector();
        psc_aj.bs = new Vector();
    }
}
