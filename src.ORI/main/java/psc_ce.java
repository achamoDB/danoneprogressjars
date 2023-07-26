import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_ce extends psc_aj implements Cloneable, Serializable, psc_bj
{
    private String a;
    psc_n b;
    
    public psc_ce() {
        super.bu = 104;
        super.bt = false;
        this.a(psc_aj.aj);
        super.a = "NetscapeCARevocationURL";
    }
    
    public psc_ce(final String a, final boolean bt) {
        super.bu = 104;
        super.bt = bt;
        this.a(psc_aj.aj);
        if (a != null) {
            this.a = a;
        }
        super.a = "NetscapeCARevocationURL";
    }
    
    public void d(final byte[] array, final int n) throws psc_g {
        if (array == null) {
            throw new psc_g("Encoding is null.");
        }
        try {
            final psc_ag psc_ag = new psc_ag(0);
            psc_l.a(array, n, new psc_i[] { psc_ag });
            this.a = psc_ag.e();
        }
        catch (psc_m psc_m) {
            throw new psc_g("Could not decode NetscapeCARevocationURL extension.");
        }
    }
    
    public void a(final String a) {
        if (a != null) {
            this.a = a;
        }
    }
    
    public String a() {
        return this.a;
    }
    
    public int e() {
        try {
            this.b = new psc_n(new psc_i[] { new psc_ag(0, true, 0, this.a) });
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
        final psc_ce psc_ce = new psc_ce();
        psc_ce.a = this.a;
        if (this.b != null) {
            psc_ce.e();
        }
        super.a(psc_ce);
        return psc_ce;
    }
    
    protected void f() {
        super.f();
        this.a = null;
        this.b = null;
    }
}
