import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_b0 extends psc_aj implements Cloneable, Serializable, psc_b1, psc_b2
{
    public static final int a = 0;
    public static final int b = 1;
    public static final int c = 2;
    public static final int d = 3;
    public static final int e = 4;
    public static final int f = 5;
    public static final int g = 6;
    public static final int h = 8;
    private int i;
    psc_n j;
    
    public psc_b0() {
        super.bu = 21;
        super.bt = false;
        this.c(21);
        super.a = "ReasonCode";
    }
    
    public psc_b0(final int i, final boolean bt) throws psc_g {
        super.bu = 21;
        super.bt = bt;
        this.i = i;
        if (i < 0 || i > 8 || i == 7) {
            throw new psc_g("Invalid Reason Code.");
        }
        this.c(21);
        super.a = "ReasonCode";
    }
    
    public void d(final byte[] array, final int n) throws psc_g {
        if (array == null) {
            throw new psc_g("Encoding is null.");
        }
        this.i = 0;
        try {
            final psc_gr psc_gr = new psc_gr(0);
            psc_l.a(array, n, new psc_i[] { psc_gr });
            if (psc_gr.d == 0) {
                return;
            }
            this.i = psc_gr.e();
        }
        catch (psc_m psc_m) {
            throw new psc_g("Could not decode ReasonCode extension.");
        }
        if (this.i < 0 || this.i > 8 || this.i == 7) {
            throw new psc_g("Could not decode ReasonCode extension: Invalid Reason Code.");
        }
    }
    
    public void a(final int i) {
        this.i = i;
    }
    
    public int a() {
        return this.i;
    }
    
    public boolean b(final int n) {
        return this.i == n;
    }
    
    public int e() {
        try {
            this.j = new psc_n(new psc_i[] { new psc_gr(0, true, 0, this.i) });
            return this.j.a();
        }
        catch (psc_m psc_m) {
            return 0;
        }
    }
    
    public int e(final byte[] array, final int n) {
        if (array == null) {
            return 0;
        }
        if (this.j == null && this.e() == 0) {
            return 0;
        }
        try {
            final int a = this.j.a(array, n);
            super.by = null;
            return a;
        }
        catch (psc_m psc_m) {
            super.by = null;
            return 0;
        }
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_b0 psc_b0 = new psc_b0();
        psc_b0.i = this.i;
        if (this.j != null) {
            psc_b0.e();
        }
        super.a(psc_b0);
        return psc_b0;
    }
    
    protected void f() {
        super.f();
        this.i = 0;
        this.j = null;
    }
}
