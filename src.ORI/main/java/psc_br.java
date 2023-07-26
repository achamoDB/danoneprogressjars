import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_br extends psc_aj implements Cloneable, Serializable, psc_bj
{
    private boolean a;
    private int b;
    psc_n c;
    
    public psc_br() {
        this.a = false;
        this.b = -1;
        super.bu = 19;
        super.bt = false;
        this.c(19);
        super.a = "BasicConstraints";
    }
    
    public psc_br(final boolean a, final int b, final boolean bt) throws psc_g {
        this.a = false;
        this.b = -1;
        super.a = "BasicConstraints";
        super.bu = 19;
        super.bt = bt;
        this.c(19);
        this.a = a;
        if (a) {
            this.b = b;
            return;
        }
        throw new psc_g("The pathLenConstraint shall be present only if cA is set to true.");
    }
    
    public void d(final byte[] array, final int n) throws psc_g {
        if (array == null) {
            throw new psc_g("Encoding is null.");
        }
        final psc_h psc_h = new psc_h(0);
        final psc_j psc_j = new psc_j();
        final psc_bh psc_bh = new psc_bh(131072);
        final psc_p psc_p = new psc_p(65536);
        final psc_i[] array2 = { psc_h, psc_bh, psc_p, psc_j };
        try {
            psc_l.a(array, n, array2);
            if (psc_bh.a) {
                this.a = psc_bh.a;
            }
            else {
                this.a = false;
            }
            if (psc_p.a && this.a) {
                this.b = psc_p.e();
            }
        }
        catch (psc_m psc_m) {
            throw new psc_g("Could not decode BasicConstraints extension.");
        }
    }
    
    public void b(final boolean a) throws psc_g {
        this.a = a;
    }
    
    public boolean a() {
        return this.a;
    }
    
    public void a(final int b) throws psc_g {
        if (!this.a) {
            throw new psc_g("The pathLenConstraint shall be present only if cA is set to true.");
        }
        this.b = b;
    }
    
    public int g() {
        return this.b;
    }
    
    public int e() {
        final psc_h psc_h = new psc_h(0, true, 0);
        final psc_j psc_j = new psc_j();
        psc_bh psc_bh;
        if (this.a) {
            psc_bh = new psc_bh(131072, true, 0, true);
        }
        else {
            psc_bh = new psc_bh(131072, false, 0, false);
        }
        if (this.a) {
            this.c = new psc_n(new psc_i[] { psc_h, psc_bh, new psc_p(65536, true, 0, this.b), psc_j });
        }
        else {
            this.c = new psc_n(new psc_i[] { psc_h, psc_bh, psc_j });
        }
        try {
            return this.c.a();
        }
        catch (psc_m psc_m) {
            return 0;
        }
    }
    
    public int e(final byte[] array, final int n) {
        if (this.c == null && this.e() == 0) {
            return 0;
        }
        try {
            final int a = this.c.a(array, n);
            super.by = null;
            return a;
        }
        catch (psc_m psc_m) {
            super.by = null;
            return 0;
        }
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_br psc_br = new psc_br();
        psc_br.a = this.a;
        psc_br.b = this.b;
        if (this.c != null) {
            psc_br.e();
        }
        super.a(psc_br);
        return psc_br;
    }
    
    protected void f() {
        super.f();
        this.a = false;
        this.b = -1;
        this.c = null;
    }
}
