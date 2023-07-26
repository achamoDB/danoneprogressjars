import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_b9 extends psc_aj implements Cloneable, Serializable, psc_bj
{
    private int a;
    psc_n b;
    
    public psc_b9() {
        this.a = 0;
        super.bu = 54;
        super.bt = false;
        this.c(54);
        super.a = "InhibitAnyPolicy";
    }
    
    public psc_b9(final int a, final boolean bt) {
        this.a = 0;
        super.bu = 54;
        super.bt = bt;
        this.a = a;
        this.c(54);
        super.a = "InhibitAnyPolicy";
    }
    
    public void d(final byte[] array, final int n) throws psc_g {
        if (array == null) {
            throw new psc_g("Encoding is null.");
        }
        this.a = 0;
        final psc_p psc_p = new psc_p(0);
        final psc_i[] array2 = { psc_p };
        try {
            psc_l.a(array, n, array2);
            if (psc_p.d == 0) {
                return;
            }
            if (psc_p.d > 4) {
                throw new psc_g("Could not decode InhibitAnyPolicy extension.");
            }
            this.a = psc_p.e();
        }
        catch (psc_m psc_m) {
            throw new psc_g("Could not decode InhibitAnyPolicy extension.");
        }
    }
    
    public void a(final int a) {
        this.a = a;
    }
    
    public int a() {
        return this.a;
    }
    
    public int e() {
        this.b = new psc_n(new psc_i[] { new psc_p(0, true, 0, this.a) });
        try {
            return this.b.a();
        }
        catch (psc_m psc_m) {
            return 0;
        }
    }
    
    public int e(final byte[] array, final int n) {
        if (array == null) {
            return 0;
        }
        if (this.b == null && this.e() == 0) {
            return 0;
        }
        try {
            final int a = this.b.a(array, n);
            super.by = null;
            return a;
        }
        catch (psc_m psc_m) {
            super.by = null;
            return 0;
        }
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_b9 psc_b9 = new psc_b9();
        psc_b9.a = this.a;
        if (this.b != null) {
            psc_b9.e();
        }
        super.a(psc_b9);
        return psc_b9;
    }
    
    protected void f() {
        super.f();
        this.a = 0;
        this.b = null;
    }
}
