import java.util.Date;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_gk extends psc_fz implements Cloneable, Serializable
{
    private Date a;
    psc_n b;
    
    public psc_gk() {
        super(18, "dateOfBirth");
    }
    
    public psc_gk(final Date date) {
        this();
        this.a(date);
    }
    
    protected void a(final byte[] array, final int n) throws psc_f0 {
        if (array == null) {
            throw new psc_f0("Encoding is null.");
        }
        this.e();
        final psc_fo psc_fo = new psc_fo(0);
        final psc_j psc_j = new psc_j();
        final psc_ai psc_ai = new psc_ai(0);
        final psc_i[] array2 = { psc_fo, psc_ai, psc_j };
        try {
            psc_l.a(array, n, array2);
        }
        catch (psc_m psc_m) {
            throw new psc_f0("Could not BER decode DateOfBirth." + psc_m.getMessage());
        }
        this.a = new Date(psc_ai.a.getTime());
    }
    
    public void a(final Date date) {
        if (date != null) {
            this.e();
            this.a = new Date(date.getTime());
        }
    }
    
    public Date g() {
        if (this.a == null) {
            return null;
        }
        return new Date(this.a.getTime());
    }
    
    protected int d() {
        this.b = null;
        if (this.a == null) {
            return 0;
        }
        super.ab = super.ab;
        this.b = new psc_n(new psc_i[] { new psc_fo(0, true, 0), new psc_ai(0, true, 0, this.a), new psc_j() });
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
        final psc_gk psc_gk = new psc_gk();
        if (this.a != null) {
            psc_gk.a = new Date(this.a.getTime());
        }
        super.a(psc_gk);
        return psc_gk;
    }
    
    public boolean equals(final Object o) {
        if (o == null || !(o instanceof psc_gk)) {
            return false;
        }
        final psc_gk psc_gk = (psc_gk)o;
        if (this.a == null) {
            return psc_gk.a == null;
        }
        return psc_gk.a != null && this.a.equals(psc_gk.a);
    }
    
    protected void e() {
        super.e();
        this.a = null;
        this.b = null;
    }
}
