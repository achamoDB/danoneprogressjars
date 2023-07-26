import java.util.Date;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_f1 extends psc_fz implements Cloneable, Serializable
{
    private Date a;
    psc_n b;
    
    public psc_f1() {
        super(0, "SigningTime");
    }
    
    public psc_f1(final Date date) {
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
        final psc_ak psc_ak = new psc_ak(0);
        final psc_i[] array2 = { psc_fo, psc_ak, psc_j };
        try {
            psc_l.a(array, n, array2);
        }
        catch (psc_m psc_m) {
            throw new psc_f0("Could not BER decode SigningTime.");
        }
        this.a = new Date(psc_ak.a.getTime());
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
        this.b = new psc_n(new psc_i[] { new psc_fo(0, true, 0), new psc_ak(0, true, 0, this.a), new psc_j() });
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
        final psc_f1 psc_f1 = new psc_f1();
        if (this.a != null) {
            psc_f1.a = new Date(this.a.getTime());
        }
        super.a(psc_f1);
        return psc_f1;
    }
    
    public boolean equals(final Object o) {
        if (o == null || !(o instanceof psc_f1)) {
            return false;
        }
        final psc_f1 psc_f1 = (psc_f1)o;
        if (this.a == null) {
            return psc_f1.a == null;
        }
        return psc_f1.a != null && this.a.equals(psc_f1.a);
    }
    
    protected void e() {
        super.e();
        this.a = null;
        this.b = null;
    }
}
