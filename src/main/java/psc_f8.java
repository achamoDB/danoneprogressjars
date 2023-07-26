import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

abstract class psc_f8 extends psc_fz implements Cloneable, Serializable
{
    protected int a;
    private psc_n b;
    
    protected psc_f8(final int n, final String s) {
        super(n, s);
        this.a = -1;
    }
    
    protected void a(final byte[] array, final int n) throws psc_f0 {
        if (array == null) {
            throw new psc_f0("Encoding is null.");
        }
        this.e();
        try {
            final psc_fo psc_fo = new psc_fo(0);
            final psc_j psc_j = new psc_j();
            final psc_p psc_p = new psc_p(65536);
            final psc_aa psc_aa = new psc_aa(65536);
            psc_l.a(array, n, new psc_i[] { psc_fo, psc_p, psc_aa, psc_j });
            if (psc_p.a) {
                this.a = psc_p.e();
            }
            else {
                if (!psc_aa.a) {
                    throw new psc_f0("Unexpected encoding.");
                }
                this.a = Integer.parseInt(psc_aa.e());
            }
        }
        catch (Exception ex) {
            throw new psc_f0("Could not BER decode attribute.");
        }
    }
    
    protected void d(final int a) {
        this.e();
        if (a >= 0) {
            this.a = a;
        }
    }
    
    protected int h() {
        return this.a;
    }
    
    protected int d() {
        this.b = null;
        if (this.a == -1) {
            return 0;
        }
        final psc_i[] array = { new psc_fo(0, true, 0), null, new psc_j() };
        try {
            if (psc_ah.c(psc_nz.c)) {
                array[1] = new psc_aa(0, true, 0, Integer.toString(this.a));
            }
            else {
                array[1] = new psc_p(0, true, 0, this.a);
            }
            this.b = new psc_n(array);
            return this.b.a();
        }
        catch (psc_m psc_m) {
            return 0;
        }
    }
    
    protected int c(final byte[] array, final int n) {
        if (array == null) {
            return 0;
        }
        if (this.b == null && this.d() == 0) {
            return 0;
        }
        try {
            return this.b.a(array, n);
        }
        catch (psc_m psc_m) {
            return 0;
        }
        finally {
            super.ac = null;
        }
    }
    
    protected void a(final psc_f8 psc_f8) {
        psc_f8.a = this.a;
        super.a(psc_f8);
    }
    
    public boolean equals(final Object o) {
        return ((psc_f8)o).a == this.a;
    }
    
    protected void e() {
        super.e();
        this.a = -1;
        this.b = null;
    }
}
