import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_cq extends psc_aj implements Cloneable, Serializable, psc_bj
{
    private psc_cr a;
    psc_n b;
    
    public psc_cq() {
        this.a = new psc_cr();
        super.bu = 116;
        super.bt = false;
        this.a(psc_aj.a7);
        super.a = "VeriSignNonVerifiedElements";
    }
    
    public psc_cq(final psc_cr psc_cr, final boolean b) {
        this.a = new psc_cr();
        super.bu = 116;
        final boolean bt = false;
        this.a(psc_aj.a7);
        super.a = "VeriSignNonVerifiedElements";
        super.bt = bt;
        try {
            if (psc_cr != null) {
                this.a = (psc_cr)psc_cr.clone();
            }
        }
        catch (CloneNotSupportedException ex) {}
    }
    
    public void a(final psc_fz psc_fz) {
        if (psc_fz != null) {
            this.a.a(psc_fz);
        }
    }
    
    public void a(final psc_cr psc_cr) {
        try {
            if (psc_cr != null) {
                this.a = (psc_cr)psc_cr.clone();
            }
        }
        catch (CloneNotSupportedException ex) {}
    }
    
    public int a() {
        return this.a.a();
    }
    
    public psc_fz a(final int n) throws psc_g {
        if (n < this.a.a()) {
            return this.a.c(n);
        }
        throw new psc_g("Invalid Index");
    }
    
    public psc_cr g() {
        try {
            return (psc_cr)this.a.clone();
        }
        catch (CloneNotSupportedException ex) {
            return null;
        }
    }
    
    public void d(final byte[] array, final int n) throws psc_g {
        if (array == null) {
            throw new psc_g("Encoding is null.");
        }
        try {
            this.a = new psc_cr(array, n, 0);
        }
        catch (psc_f0 psc_f0) {
            throw new psc_g("Could not decode VeriSignNonVerifiedElements extension.");
        }
    }
    
    public int e() {
        try {
            final byte[] array = new byte[this.a.b(0)];
            this.b = new psc_n(new psc_i[] { new psc_k(12544, true, 0, array, 0, this.a.b(array, 0, 0)) });
            return this.b.a();
        }
        catch (Exception ex) {
            return 0;
        }
    }
    
    public int e(final byte[] array, final int n) {
        if (array == null) {
            return 0;
        }
        try {
            if (this.b == null && this.e() == 0) {
                return 0;
            }
            return this.b.a(array, n);
        }
        catch (psc_m psc_m) {
            return 0;
        }
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_cq psc_cq = new psc_cq();
        psc_cq.a = (psc_cr)this.a.clone();
        super.a(psc_cq);
        return psc_cq;
    }
    
    protected void f() {
        super.f();
        this.a = null;
    }
}
