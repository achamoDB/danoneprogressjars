import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_bz extends psc_aj implements Cloneable, Serializable, psc_bl
{
    private byte[] a;
    psc_n b;
    
    private psc_bz(final boolean bt) {
        this.a = null;
        super.bu = 20;
        super.bt = bt;
        this.c(20);
        super.a = "CRLNumber";
    }
    
    public psc_bz() {
        this(false);
        this.a(0);
    }
    
    public psc_bz(final int n, final boolean b) {
        this(b);
        this.a(n);
    }
    
    public psc_bz(final byte[] array, final int n, final int n2, final boolean b) {
        this(b);
        this.a(array, n, n2);
    }
    
    public void d(final byte[] array, final int n) throws psc_g {
        if (array == null) {
            throw new psc_g("Encoding is null.");
        }
        final psc_p psc_p = new psc_p(0);
        final psc_i[] array2 = { psc_p };
        try {
            psc_l.a(array, n, array2);
            this.a = new byte[psc_p.d];
            System.arraycopy(psc_p.b, psc_p.c, this.a, 0, psc_p.d);
        }
        catch (psc_m psc_m) {
            throw new psc_g("Could not decode CRLNumber extension.");
        }
    }
    
    public void a(final int n) {
        this.a = this.f(n);
    }
    
    public void a(final byte[] array, final int n, final int n2) {
        System.arraycopy(array, n, this.a = new byte[n2], 0, n2);
    }
    
    public int a() throws psc_g {
        if (this.a.length > 4) {
            throw new psc_g("Can not represent integer in 32 bits.");
        }
        return this.b(this.a);
    }
    
    public byte[] g() {
        return this.a;
    }
    
    public int e() {
        try {
            this.b = new psc_n(new psc_i[] { new psc_p(0, true, 0, this.a, 0, this.a.length, true) });
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
        final psc_bz psc_bz = new psc_bz();
        if (this.a != null) {
            psc_bz.a = this.a.clone();
        }
        if (this.b != null) {
            psc_bz.e();
        }
        super.a(psc_bz);
        return psc_bz;
    }
    
    protected void f() {
        super.f();
        this.a(0);
        this.b = null;
    }
}
