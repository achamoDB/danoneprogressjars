import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_f4 extends psc_fz implements Cloneable, Serializable
{
    private String a;
    psc_n b;
    
    public psc_f4() {
        super(3, "FriendlyName");
        this.a = new String();
    }
    
    public psc_f4(final String s) {
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
            final psc_ae psc_ae = new psc_ae(0);
            psc_l.a(array, n, new psc_i[] { psc_fo, psc_ae, psc_j });
            this.a = psc_ae.e();
        }
        catch (psc_m psc_m) {
            throw new psc_f0("Could not BER decode FriendlyName.");
        }
    }
    
    public void b(final String a) {
        if (a != null) {
            this.e();
            this.a = a;
        }
    }
    
    public String g() {
        if (this.a == null) {
            return null;
        }
        return this.a;
    }
    
    protected int d() {
        this.b = null;
        if (this.a == null) {
            return 0;
        }
        try {
            this.b = new psc_n(new psc_i[] { new psc_fo(0, true, 0), new psc_ae(0, true, 0, this.a), new psc_j() });
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
        final psc_f4 psc_f4 = new psc_f4();
        if (this.a != null) {
            psc_f4.a = new String(this.a);
        }
        super.a(psc_f4);
        return psc_f4;
    }
    
    public boolean equals(final Object o) {
        return o != null && o instanceof psc_f4 && this.a.equals(((psc_f4)o).a);
    }
    
    protected void e() {
        super.e();
        this.a = null;
        this.b = null;
    }
}
