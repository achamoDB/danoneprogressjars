import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_co extends psc_aj implements Cloneable, Serializable, psc_bj
{
    public static final int a = 4;
    public static final int b = -268435456;
    public static final int c = Integer.MIN_VALUE;
    public static final int d = 1073741824;
    public static final int e = 536870912;
    public static final int f = 268435456;
    private int g;
    psc_n h;
    
    public psc_co() {
        super.bu = 114;
        super.bt = false;
        this.a(psc_aj.a3);
        super.a = "VeriSignTokenType";
    }
    
    public psc_co(final int g, final boolean b) {
        super.bu = 114;
        final boolean bt = false;
        this.a(psc_aj.a3);
        super.a = "VeriSignTokenType";
        super.bt = bt;
        this.g = g;
    }
    
    public void a(final int n) {
        this.g = (n & 0xF0000000);
    }
    
    public int a() {
        return this.g;
    }
    
    public void d(final byte[] array, final int n) throws psc_g {
        if (array == null) {
            throw new psc_g("Encoding is null.");
        }
        this.g = 0;
        try {
            final psc_d4 psc_d4 = new psc_d4(0);
            psc_l.a(array, n, new psc_i[] { psc_d4 });
            if (psc_d4.d == 0) {
                return;
            }
            if (psc_d4.d > 4) {
                throw new psc_g("Could not decode VeriSignTokenType extension.");
            }
            for (int i = psc_d4.c, n2 = 24; i < psc_d4.c + psc_d4.d; ++i, n2 -= 8) {
                this.g |= (psc_d4.b[i] & 0xFF) << n2;
            }
            this.g &= 0xF0000000;
        }
        catch (psc_m psc_m) {
            throw new psc_g("Could not decode VeriSignTokenType extension.");
        }
    }
    
    public int e() {
        this.h = new psc_n(new psc_i[] { new psc_d4(0, true, 0, this.g, 4, true) });
        try {
            return this.h.a();
        }
        catch (psc_m psc_m) {
            return 0;
        }
    }
    
    public int e(final byte[] array, final int n) {
        if (array == null) {
            return 0;
        }
        if (this.h == null && this.e() == 0) {
            return 0;
        }
        try {
            final int a = this.h.a(array, n);
            super.by = null;
            return a;
        }
        catch (psc_m psc_m) {
            super.by = null;
            return 0;
        }
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_co psc_co = new psc_co();
        psc_co.g = this.g;
        if (this.h != null) {
            psc_co.e();
        }
        super.a(psc_co);
        return psc_co;
    }
    
    protected void f() {
        super.f();
        this.g = 0;
        this.h = null;
    }
}
