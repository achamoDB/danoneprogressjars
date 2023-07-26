import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_cb extends psc_aj implements Cloneable, Serializable, psc_bj
{
    public static final int a = 8;
    public static final int b = -16777216;
    public static final int c = Integer.MIN_VALUE;
    public static final int d = 1073741824;
    public static final int e = 536870912;
    public static final int f = 268435456;
    public static final int g = 134217728;
    public static final int h = 67108864;
    public static final int i = 33554432;
    public static final int j = 16777216;
    private int k;
    psc_n l;
    
    public psc_cb() {
        super.bu = 101;
        super.bt = false;
        this.a(psc_aj.ad);
        super.a = "NetscapeCertType";
    }
    
    public psc_cb(final int n, final boolean bt) {
        super.bu = 101;
        super.bt = bt;
        this.a(psc_aj.ad);
        this.k = (n & 0xFF000000);
        super.a = "NetscapeCertType";
    }
    
    public void d(final byte[] array, final int n) throws psc_g {
        if (array == null) {
            throw new psc_g("Encoding is null.");
        }
        this.k = 0;
        final psc_d4 psc_d4 = new psc_d4(0);
        final psc_i[] array2 = { psc_d4 };
        try {
            psc_l.a(array, n, array2);
        }
        catch (psc_m psc_m) {
            throw new psc_g("Could not decode NetscapeCertType extension.");
        }
        if (psc_d4.d == 0) {
            return;
        }
        if (psc_d4.d > 4) {
            throw new psc_g("Could not decode NetscapeCertType extension.");
        }
        for (int i = psc_d4.c, n2 = 24; i < psc_d4.c + psc_d4.d; ++i, n2 -= 8) {
            this.k |= (psc_d4.b[i] & 0xFF) << n2;
        }
        this.k &= 0xFF000000;
    }
    
    public void a(final int n) {
        this.k = (n & 0xFF000000);
    }
    
    public int a() {
        return this.k;
    }
    
    public boolean b(final int n) {
        return (n & this.k & 0xFF000000) == n;
    }
    
    public int e() {
        this.l = new psc_n(new psc_i[] { new psc_d4(0, true, 0, this.k, 8, true) });
        try {
            return this.l.a();
        }
        catch (psc_m psc_m) {
            return 0;
        }
    }
    
    public int e(final byte[] array, final int n) {
        if (array == null) {
            return 0;
        }
        if (this.l == null && this.e() == 0) {
            return 0;
        }
        try {
            final int a = this.l.a(array, n);
            super.by = null;
            return a;
        }
        catch (psc_m psc_m) {
            super.by = null;
            return 0;
        }
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_cb psc_cb = new psc_cb();
        psc_cb.k = this.k;
        if (this.l != null) {
            psc_cb.e();
        }
        super.a(psc_cb);
        return psc_cb;
    }
    
    protected void f() {
        super.f();
        this.k = 0;
        this.l = null;
    }
}
