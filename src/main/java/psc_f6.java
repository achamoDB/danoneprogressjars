import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_f6 extends psc_fz implements Cloneable, Serializable
{
    private String a;
    psc_n b;
    
    public psc_f6() {
        super(5, "VeriSignSmartCSP");
    }
    
    public psc_f6(final String s) {
        this();
        this.b(s);
    }
    
    protected void a(final byte[] array, final int n) throws psc_f0 {
        if (array == null) {
            throw new psc_f0("Encoding is null.");
        }
        this.e();
        try {
            final psc_fo psc_fo = new psc_fo(0);
            final psc_j psc_j = new psc_j();
            final psc_ag psc_ag = new psc_ag(0, 1, 255);
            psc_l.a(array, n, new psc_i[] { psc_fo, psc_ag, psc_j });
            this.a = psc_ag.e();
        }
        catch (psc_m psc_m) {
            throw new psc_f0("Could not BER decode VeriSignSmartCSP.");
        }
    }
    
    public void b(final String a) {
        if (a != null) {
            this.e();
            this.a = a;
        }
    }
    
    public String g() {
        return this.a;
    }
    
    protected int d() {
        this.b = null;
        if (this.a == null) {
            return 0;
        }
        try {
            this.b = new psc_n(new psc_i[] { new psc_fo(0, true, 0), new psc_ag(0, true, 0, this.a, 1, 255), new psc_j() });
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
        final psc_f6 psc_f6 = new psc_f6();
        if (this.a != null) {
            psc_f6.a = this.a;
        }
        super.a(psc_f6);
        return psc_f6;
    }
    
    public boolean equals(final Object o) {
        return o != null && o instanceof psc_f6 && ((psc_f6)o).a.equals(this.a);
    }
    
    protected void e() {
        super.e();
        this.a = null;
        this.b = null;
    }
}
