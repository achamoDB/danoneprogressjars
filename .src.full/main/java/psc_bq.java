import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_bq extends psc_aj implements Cloneable, Serializable, psc_bj, psc_bl
{
    private psc_bm a;
    psc_n b;
    
    public psc_bq() {
        this.a = new psc_bm();
        super.bu = 18;
        super.bt = false;
        this.c(18);
        super.a = "IssuerAltName";
    }
    
    public psc_bq(final psc_bm psc_bm, final boolean bt) throws psc_g {
        this.a = new psc_bm();
        super.bu = 18;
        super.bt = bt;
        if (psc_bm == null) {
            throw new psc_g("Encoding is null.");
        }
        try {
            this.a = (psc_bm)psc_bm.clone();
        }
        catch (CloneNotSupportedException ex) {
            throw new psc_g("Can't initialise IssuerAltName");
        }
        this.c(18);
        super.a = "IssuerAltName";
    }
    
    public void a(final psc_fh psc_fh) {
        if (psc_fh != null) {
            this.a.a(psc_fh);
        }
    }
    
    public void a(final psc_bm psc_bm) throws psc_g {
        try {
            if (psc_bm != null) {
                this.a = (psc_bm)psc_bm.clone();
            }
        }
        catch (CloneNotSupportedException ex) {
            throw new psc_g("Can't add new name to IssuerAltName");
        }
    }
    
    public psc_bm a() {
        return this.a;
    }
    
    public void d(final byte[] array, final int n) throws psc_g {
        if (array == null) {
            throw new psc_g("Encoding is null.");
        }
        try {
            this.a = new psc_bm(array, n, 0);
        }
        catch (psc_v psc_v) {
            throw new psc_g("Could not decode IssuerAltName extension.");
        }
    }
    
    public int e() {
        try {
            final byte[] array = new byte[this.a.c(0)];
            this.b = new psc_n(new psc_i[] { new psc_k(12288, true, 0, array, 0, this.a.a(array, 0, 0)) });
        }
        catch (Exception ex) {
            return 0;
        }
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
            return this.b.a(array, n);
        }
        catch (psc_m psc_m) {
            return 0;
        }
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_bq psc_bq = new psc_bq();
        if (this.a != null) {
            psc_bq.a = (psc_bm)this.a.clone();
        }
        if (this.b != null) {
            psc_bq.e();
        }
        super.a(psc_bq);
        return psc_bq;
    }
    
    protected void f() {
        super.f();
        this.a = new psc_bm();
    }
}
