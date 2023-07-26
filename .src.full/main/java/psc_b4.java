import java.util.Date;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_b4 extends psc_aj implements Cloneable, Serializable, psc_b1, psc_b2
{
    private Date a;
    psc_n b;
    
    public psc_b4() {
        super.bu = 24;
        super.bt = false;
        this.c(24);
        super.a = "InvalidityDate";
    }
    
    public psc_b4(final Date date, final boolean bt) {
        super.bu = 24;
        super.bt = bt;
        if (date != null) {
            this.a = new Date(date.getTime());
        }
        this.c(24);
        super.a = "InvalidityDate";
    }
    
    public void d(final byte[] array, final int n) throws psc_g {
        if (array == null) {
            throw new psc_g("Encoding is null.");
        }
        final psc_ai psc_ai = new psc_ai(0);
        final psc_i[] array2 = { psc_ai };
        try {
            psc_l.a(array, n, array2);
        }
        catch (psc_m psc_m) {
            throw new psc_g("Could not decode InvalidityDate extension.");
        }
        this.a = psc_ai.a;
    }
    
    public void a(final Date date) {
        if (date != null) {
            this.a = new Date(date.getTime());
        }
    }
    
    public Date a() {
        if (this.a == null) {
            return null;
        }
        return new Date(this.a.getTime());
    }
    
    public int e() {
        this.b = new psc_n(new psc_i[] { new psc_ai(0, true, 0, this.a) });
        try {
            return this.b.a();
        }
        catch (psc_m psc_m) {
            return 0;
        }
    }
    
    public int e(final byte[] array, final int n) {
        if (array == null) {
            return 0;
        }
        if (this.b == null && this.e() == 0) {
            return 0;
        }
        try {
            final int a = this.b.a(array, n);
            super.by = null;
            return a;
        }
        catch (psc_m psc_m) {
            super.by = null;
            return 0;
        }
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_b4 psc_b4 = new psc_b4();
        if (this.a != null) {
            psc_b4.a = new Date(this.a.getTime());
        }
        if (this.b != null) {
            psc_b4.e();
        }
        super.a(psc_b4);
        return psc_b4;
    }
    
    protected void f() {
        super.f();
        this.a = null;
        this.b = null;
    }
}
