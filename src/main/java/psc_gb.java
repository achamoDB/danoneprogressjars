import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_gb extends psc_fz implements Cloneable, Serializable
{
    private byte[] a;
    psc_n b;
    
    public psc_gb() {
        super(9, "VeriSignCRSSenderNonce");
    }
    
    public psc_gb(final byte[] array, final int n, final int n2) {
        this();
        this.f(array, n, n2);
    }
    
    protected void a(final byte[] array, final int n) throws psc_f0 {
        if (array == null) {
            throw new psc_f0("Encoding is null.");
        }
        this.e();
        try {
            final psc_fo psc_fo = new psc_fo(0);
            final psc_j psc_j = new psc_j();
            final psc_t psc_t = new psc_t(0);
            psc_l.a(array, n, new psc_i[] { psc_fo, psc_t, psc_j });
            if (psc_t.d > 0) {
                this.a = new byte[psc_t.d];
                System.arraycopy(psc_t.b, psc_t.c, this.a, 0, psc_t.d);
            }
        }
        catch (psc_m psc_m) {
            throw new psc_f0("Could not BER decode VeriSignCRSSenderNonce.");
        }
    }
    
    public void f(final byte[] array, final int n, final int n2) {
        this.e();
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
        int length = 0;
        if (this.a != null) {
            length = this.a.length;
        }
        try {
            this.b = new psc_n(new psc_i[] { new psc_fo(0, true, 0), new psc_t(0, true, 0, this.a, 0, length), new psc_j() });
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
        final psc_gb psc_gb = new psc_gb();
        if (this.a != null) {
            psc_gb.a = this.a.clone();
        }
        super.a(psc_gb);
        return psc_gb;
    }
    
    public boolean equals(final Object o) {
        return o != null && o instanceof psc_gb && psc_k4.a(((psc_gb)o).a, this.a);
    }
    
    protected void e() {
        super.e();
        this.a = null;
        this.b = null;
    }
}
