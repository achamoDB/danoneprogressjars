import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_gf extends psc_fz implements Cloneable, Serializable
{
    private int a;
    psc_n b;
    
    public psc_gf() {
        super(13, "VeriSignCRSDualEnrollmentStatus");
        this.a = -1;
    }
    
    public psc_gf(final int n) {
        this();
        this.c(n);
    }
    
    protected void a(final byte[] array, final int n) throws psc_f0 {
        if (array == null) {
            throw new psc_f0("Encoding is null.");
        }
        this.e();
        try {
            final psc_fo psc_fo = new psc_fo(0);
            final psc_j psc_j = new psc_j();
            final psc_p psc_p = new psc_p(0);
            psc_l.a(array, n, new psc_i[] { psc_fo, psc_p, psc_j });
            this.a = psc_p.e();
        }
        catch (psc_m psc_m) {
            throw new psc_f0("Could not BER decode VeriSignCRSDualEnrollmentStatus.");
        }
    }
    
    public void c(final int a) {
        this.e();
        if (a >= 0) {
            this.a = a;
        }
    }
    
    public int g() {
        return this.a;
    }
    
    protected int d() {
        this.b = null;
        if (this.a == -1) {
            return 0;
        }
        this.b = new psc_n(new psc_i[] { new psc_fo(0, true, 0), new psc_p(0, true, 0, this.a), new psc_j() });
        try {
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
            final int a = this.b.a(array, n);
            super.ac = null;
            return a;
        }
        catch (psc_m psc_m) {
            super.ac = null;
            return 0;
        }
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_gf psc_gf = new psc_gf();
        if (this.a != -1) {
            psc_gf.a = this.a;
        }
        super.a(psc_gf);
        return psc_gf;
    }
    
    public boolean equals(final Object o) {
        return o != null && o instanceof psc_gf && ((psc_gf)o).a == this.a;
    }
    
    protected void e() {
        super.e();
        this.a = -1;
        this.b = null;
    }
}
