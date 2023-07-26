import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_gp extends psc_fz implements Cloneable, Serializable
{
    private byte[] a;
    psc_n b;
    
    public psc_gp() {
        super(23, "NonStandardAttribute");
    }
    
    public psc_gp(final byte[] array, final int n, final int n2, final byte[] array2, final int n3, final int n4) {
        this();
        this.f(array, n, n2);
        this.g(array2, n3, n4);
    }
    
    protected void a(final byte[] array, final int n) throws psc_f0 {
        if (array == null) {
            throw new psc_f0("Encoding is null.");
        }
        this.e();
        try {
            final psc_w psc_w = new psc_w(0, 12544, new psc_k(65280));
            psc_l.a(array, n, new psc_i[] { psc_w });
            if (psc_w.j() == 0) {
                this.a = new byte[0];
                return;
            }
            final psc_k psc_k = (psc_k)psc_w.a(0);
            this.a = new byte[psc_k.d];
            System.arraycopy(psc_k.b, psc_k.c, this.a, 0, psc_k.d);
        }
        catch (psc_m psc_m) {
            throw new psc_f0("Could not BER decode the non-standard attribute.");
        }
    }
    
    public void f(final byte[] array, final int n, final int n2) {
        if (array != null && n2 != 0) {
            System.arraycopy(array, n, super.aa = new byte[n2], 0, n2);
        }
    }
    
    public void g(final byte[] array, final int n, final int n2) {
        if (array != null && n2 != 0) {
            System.arraycopy(array, n, this.a = new byte[n2], 0, n2);
        }
    }
    
    public byte[] g() {
        if (this.a == null) {
            return null;
        }
        return this.a.clone();
    }
    
    protected int d() {
        this.b = null;
        if (this.a == null || super.aa == null) {
            return 0;
        }
        try {
            this.b = new psc_n(new psc_i[] { new psc_fo(0, true, 0), new psc_k(65280, true, 0, this.a, 0, this.a.length), new psc_j() });
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
            this.b = null;
            return 0;
        }
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_gp psc_gp = new psc_gp();
        if (this.a != null) {
            psc_gp.a = this.a.clone();
        }
        if (super.aa != null) {
            psc_gp.aa = super.aa.clone();
        }
        super.a(psc_gp);
        return psc_gp;
    }
    
    public boolean equals(final Object o) {
        if (o == null || !(o instanceof psc_gp)) {
            return false;
        }
        final psc_gp psc_gp = (psc_gp)o;
        return psc_k4.a(psc_gp.a, this.a) && psc_k4.a(psc_gp.aa, super.aa);
    }
    
    protected void e() {
        super.e();
        this.a = null;
        this.b = null;
    }
}
