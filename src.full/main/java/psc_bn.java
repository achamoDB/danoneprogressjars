import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_bn extends psc_aj implements Cloneable, Serializable, psc_bj
{
    public static final int a = 9;
    public static final int b = -8388608;
    public static final int c = Integer.MIN_VALUE;
    public static final int d = 1073741824;
    public static final int e = 536870912;
    public static final int f = 268435456;
    public static final int g = 134217728;
    public static final int h = 67108864;
    public static final int i = 33554432;
    public static final int j = 16777216;
    public static final int k = 8388608;
    private int l;
    psc_n m;
    
    public psc_bn() {
        super.bu = 15;
        super.bt = false;
        this.c(15);
        super.a = "KeyUsage";
    }
    
    public psc_bn(final int n, final boolean bt) {
        super.bu = 15;
        super.bt = bt;
        this.l = (n & 0xFF800000);
        this.c(15);
        super.a = "KeyUsage";
    }
    
    public void d(final byte[] array, final int n) throws psc_g {
        if (array == null) {
            throw new psc_g("Encoding is null.");
        }
        this.l = 0;
        final psc_d4 psc_d4 = new psc_d4(0);
        final psc_i[] array2 = { psc_d4 };
        try {
            psc_l.a(array, n, array2);
        }
        catch (psc_m psc_m) {
            throw new psc_g("Could not decode KeyUsage extension.");
        }
        if (psc_d4.d == 0) {
            return;
        }
        if (psc_d4.d > 4) {
            throw new psc_g("Could not decode KeyUsage extension.");
        }
        for (int i = psc_d4.c, n2 = 24; i < psc_d4.c + psc_d4.d; ++i, n2 -= 8) {
            this.l |= (psc_d4.b[i] & 0xFF) << n2;
        }
        this.l &= 0xFF800000;
    }
    
    public int a() {
        return this.l;
    }
    
    public boolean a(final int n) {
        return (n & this.l & 0xFF800000) == n;
    }
    
    public int e() {
        this.m = new psc_n(new psc_i[] { new psc_d4(0, true, 0, this.l, 9, true) });
        try {
            return this.m.a();
        }
        catch (psc_m psc_m) {
            return 0;
        }
    }
    
    public int e(final byte[] array, final int n) {
        if (array == null) {
            return 0;
        }
        if (this.m == null && this.e() == 0) {
            return 0;
        }
        try {
            final int a = this.m.a(array, n);
            super.by = null;
            return a;
        }
        catch (psc_m psc_m) {
            super.by = null;
            return 0;
        }
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_bn psc_bn = new psc_bn();
        psc_bn.l = this.l;
        if (this.m != null) {
            psc_bn.e();
        }
        super.a(psc_bn);
        return psc_bn;
    }
    
    protected void f() {
        super.f();
        this.l = 0;
        this.m = null;
    }
}
