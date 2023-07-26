import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_b6 extends psc_aj implements Cloneable, Serializable, psc_bl
{
    public static final int a = 7;
    public static final int b = -33554432;
    public static final int c = Integer.MIN_VALUE;
    public static final int d = 1073741824;
    public static final int e = 536870912;
    public static final int f = 268435456;
    public static final int g = 134217728;
    public static final int h = 67108864;
    public static final int i = 33554432;
    private static final int j = 8454144;
    private static final int k = 8519681;
    private static final int l = 8519682;
    private static final int m = 8454147;
    private static final int n = 8519684;
    private static final int o = 8388608;
    private static final int p = 8388609;
    psc_n q;
    private psc_bm r;
    private psc_x s;
    private int t;
    private boolean u;
    private boolean v;
    private boolean w;
    
    public psc_b6() {
        this.r = null;
        this.s = null;
        this.t = -1;
        this.u = false;
        this.v = false;
        this.w = false;
        super.bu = 28;
        super.bt = false;
        this.c(28);
        super.a = "IssuingDistributionPoint";
    }
    
    public psc_b6(final psc_x s, final boolean u, final boolean v, final int t, final boolean w, final boolean bt) {
        this.r = null;
        this.s = null;
        this.t = -1;
        this.u = false;
        this.v = false;
        this.w = false;
        super.bu = 28;
        super.bt = bt;
        this.c(28);
        this.s = s;
        this.u = u;
        this.v = v;
        this.t = t;
        this.w = w;
        super.a = "IssuingDistributionPoint";
    }
    
    public psc_b6(final psc_bm r, final boolean u, final boolean v, final int t, final boolean w, final boolean bt) {
        this.r = null;
        this.s = null;
        this.t = -1;
        this.u = false;
        this.v = false;
        this.w = false;
        super.bu = 28;
        super.bt = bt;
        this.c(28);
        this.r = r;
        this.u = u;
        this.v = v;
        this.t = t;
        this.w = w;
        super.a = "IssuingDistributionPoint";
    }
    
    public void d(final byte[] array, final int n) throws psc_g {
        if (array == null) {
            throw new psc_g("Encoding is null.");
        }
        try {
            final psc_h psc_h = new psc_h(0);
            final psc_j psc_j = new psc_j();
            final psc_z psc_z = new psc_z(8454144);
            final psc_k psc_k = new psc_k(8400896);
            final psc_k psc_k2 = new psc_k(8401153);
            final psc_bh psc_bh = new psc_bh(8519681);
            final psc_bh psc_bh2 = new psc_bh(8519682);
            final psc_d4 psc_d4 = new psc_d4(8454147);
            final psc_bh psc_bh3 = new psc_bh(8519684);
            psc_l.a(array, n, new psc_i[] { psc_h, psc_z, psc_k, psc_k2, psc_j, psc_bh, psc_bh2, psc_d4, psc_bh3, psc_j });
            if (psc_k.a) {
                this.r = new psc_bm(psc_k.b, psc_k.c, 8388608);
            }
            else if (psc_k2.a) {
                this.s = new psc_x(psc_k2.b, psc_k2.c, 8388609);
            }
            else {
                this.r = null;
                this.s = null;
            }
            if (psc_bh.a) {
                this.u = psc_bh.a;
            }
            else {
                this.u = false;
            }
            if (psc_bh2.a) {
                this.v = psc_bh2.a;
            }
            else {
                this.v = false;
            }
            if (psc_d4.a) {
                if (psc_d4.d > 4) {
                    throw new psc_g("Could not decode IssuingDistributionPoint extension.");
                }
                if (psc_d4.d == 0) {
                    this.t = 0;
                }
                else {
                    int n2 = 0;
                    for (int i = psc_d4.c, n3 = 24; i < psc_d4.c + psc_d4.d; ++i, n3 -= 8) {
                        n2 |= (psc_d4.b[i] & 0xFF) << n3;
                    }
                    this.t = (n2 & 0xFE000000);
                }
            }
            else {
                this.t = -1;
            }
            if (psc_bh3.a) {
                this.w = psc_bh3.a;
            }
            else {
                this.w = false;
            }
        }
        catch (psc_m psc_m) {
            throw new psc_g("Could not decode IssuingDistributionPoint extension.");
        }
        catch (psc_v psc_v) {
            throw new psc_g("Could not create new GeneralNames object.");
        }
    }
    
    public void a(final psc_bm r) {
        this.r = r;
        this.s = null;
    }
    
    public void a(final psc_x s) {
        this.s = s;
        this.r = null;
    }
    
    public void b(final boolean u) {
        this.u = u;
    }
    
    public void c(final boolean v) {
        this.v = v;
    }
    
    public void a(final int t) {
        this.t = t;
    }
    
    public void d(final boolean w) {
        this.w = w;
    }
    
    public Object g() {
        if (this.s != null) {
            return this.s;
        }
        return this.r;
    }
    
    public boolean h() {
        return this.u;
    }
    
    public boolean i() {
        return this.v;
    }
    
    public int j() {
        return this.t;
    }
    
    public boolean k() {
        return this.w;
    }
    
    public int e() {
        try {
            int n = 0;
            final psc_h psc_h = new psc_h(0, true, 0);
            psc_i psc_i = null;
            psc_i psc_i2 = null;
            psc_i a = null;
            final psc_j psc_j = new psc_j();
            if (this.r != null || this.s != null) {
                psc_i = new psc_z(8454144, 0);
                a = this.a();
                n = 1;
            }
            if (this.t != -1) {
                psc_i2 = new psc_d4(8454147, true, 0, this.t, 7, true);
                if (n == 0) {
                    n = 2;
                }
                else {
                    n = 3;
                }
            }
            psc_bh psc_bh;
            if (this.u) {
                psc_bh = new psc_bh(8519681, true, 0, true);
            }
            else {
                psc_bh = new psc_bh(8519681, false, 0, false);
            }
            psc_bh psc_bh2;
            if (this.v) {
                psc_bh2 = new psc_bh(8519682, true, 0, true);
            }
            else {
                psc_bh2 = new psc_bh(8519682, false, 0, false);
            }
            psc_bh psc_bh3;
            if (this.w) {
                psc_bh3 = new psc_bh(8519684, true, 0, true);
            }
            else {
                psc_bh3 = new psc_bh(8519684, false, 0, false);
            }
            switch (n) {
                case 0: {
                    this.q = new psc_n(new psc_i[] { psc_h, psc_bh, psc_bh2, psc_bh3, psc_j });
                    break;
                }
                case 1: {
                    this.q = new psc_n(new psc_i[] { psc_h, psc_i, a, psc_j, psc_bh, psc_bh2, psc_bh3, psc_j });
                    break;
                }
                case 2: {
                    this.q = new psc_n(new psc_i[] { psc_h, psc_bh, psc_bh2, psc_i2, psc_bh3, psc_j });
                    break;
                }
                case 3: {
                    this.q = new psc_n(new psc_i[] { psc_h, psc_i, a, psc_j, psc_bh, psc_bh2, psc_i2, psc_bh3, psc_j });
                    break;
                }
            }
        }
        catch (psc_g psc_g) {
            return 0;
        }
        try {
            return this.q.a();
        }
        catch (psc_m psc_m) {
            return 0;
        }
    }
    
    private psc_k a() throws psc_g {
        psc_k psc_k = null;
        try {
            if (this.r != null) {
                final byte[] array = new byte[this.r.c(8388608)];
                psc_k = new psc_k(12288, true, 0, array, 0, this.r.a(array, 0, 8388608));
            }
            else if (this.s != null) {
                final byte[] array2 = new byte[this.s.d(8388609)];
                psc_k = new psc_k(12544, true, 0, array2, 0, this.s.b(array2, 0, 8388609));
            }
            return psc_k;
        }
        catch (psc_m psc_m) {
            throw new psc_g("Can't encode DistributionPointNames" + psc_m.getMessage());
        }
        catch (psc_v psc_v) {
            throw new psc_g("Can't encode DistributionPointNames" + psc_v.getMessage());
        }
    }
    
    public int e(final byte[] array, final int n) {
        if (array == null) {
            return 0;
        }
        if (this.q == null && this.e() == 0) {
            return 0;
        }
        try {
            final int a = this.q.a(array, n);
            super.by = null;
            return a;
        }
        catch (psc_m psc_m) {
            super.by = null;
            return 0;
        }
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_b6 psc_b6 = new psc_b6();
        if (this.r != null) {
            psc_b6.r = (psc_bm)this.r.clone();
        }
        if (this.s != null) {
            psc_b6.s = (psc_x)this.s.clone();
        }
        psc_b6.t = this.t;
        psc_b6.u = this.u;
        psc_b6.v = this.v;
        psc_b6.w = this.w;
        if (this.q != null) {
            psc_b6.e();
        }
        super.a(psc_b6);
        return psc_b6;
    }
    
    protected void f() {
        super.f();
        this.r = null;
        this.s = null;
        this.t = 0;
        this.u = false;
        this.v = false;
        this.w = false;
        this.q = null;
    }
}
