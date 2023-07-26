import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_b7 extends psc_aj implements Cloneable, Serializable, psc_b1, psc_b2
{
    private psc_bm a;
    
    public psc_b7() {
        this.a = null;
        super.bu = 29;
        super.bt = false;
        this.c(29);
        super.a = "CertificateIssuer";
    }
    
    public psc_b7(final psc_bm a, final boolean bt) {
        this.a = null;
        super.bu = 29;
        super.bt = bt;
        if (a != null) {
            this.a = a;
        }
        this.c(29);
        super.a = "CertificateIssuer";
    }
    
    public void a(final psc_bm psc_bm) {
        try {
            if (psc_bm != null) {
                this.a = (psc_bm)psc_bm.clone();
            }
        }
        catch (CloneNotSupportedException ex) {}
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
            throw new psc_g("Could not decode CertificateIssuer extension.");
        }
    }
    
    public int e() {
        try {
            return this.a.c(super.bx);
        }
        catch (psc_v psc_v) {
            return 0;
        }
    }
    
    public int e(final byte[] array, final int n) {
        if (this.a == null) {
            return 0;
        }
        if (array == null) {
            return 0;
        }
        try {
            return this.a.a(array, n, 0);
        }
        catch (psc_v psc_v) {
            return 0;
        }
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_b7 psc_b7 = new psc_b7();
        if (this.a != null) {
            psc_b7.a = (psc_bm)this.a.clone();
        }
        super.a(psc_b7);
        return psc_b7;
    }
    
    protected void f() {
        super.f();
        this.a = null;
    }
}
