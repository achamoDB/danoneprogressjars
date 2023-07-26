import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_f3 extends psc_fz implements Cloneable, Serializable
{
    private psc_bg a;
    psc_n b;
    
    public psc_f3() {
        super(2, "V3ExtensionAttribute");
    }
    
    public psc_f3(final psc_bg psc_bg) {
        this();
        this.a(psc_bg);
    }
    
    protected void a(final byte[] array, final int n) throws psc_f0 {
        if (array == null) {
            throw new psc_f0("Encoding is null.");
        }
        this.e();
        try {
            final psc_fo psc_fo = new psc_fo(0);
            final psc_j psc_j = new psc_j();
            final psc_k psc_k = new psc_k(12288);
            psc_l.a(array, n, new psc_i[] { psc_fo, psc_k, psc_j });
            this.a = new psc_bg(psc_k.b, psc_k.c, 0, 1);
        }
        catch (psc_m psc_m) {
            this.e();
            throw new psc_f0("Could not BER decode V3ExtensionAttribute.");
        }
        catch (psc_g psc_g) {
            this.e();
            throw new psc_f0("Could not create the attribute object: " + psc_g.getMessage());
        }
    }
    
    public void a(final psc_bg psc_bg) {
        try {
            if (psc_bg != null) {
                this.a = (psc_bg)psc_bg.clone();
            }
        }
        catch (CloneNotSupportedException ex) {
            this.a = null;
        }
    }
    
    public psc_bg g() {
        if (this.a == null) {
            return null;
        }
        try {
            return (psc_bg)this.a.clone();
        }
        catch (CloneNotSupportedException ex) {
            return null;
        }
    }
    
    protected int d() {
        this.b = null;
        if (this.a == null) {
            return 0;
        }
        final int b = this.a.b(0);
        try {
            this.b = new psc_n(new psc_i[] { new psc_fo(0, true, 0), new psc_k(12288, true, 0, null, 0, b), new psc_j() });
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
        int a;
        try {
            a = this.b.a(array, n);
            super.ac = null;
        }
        catch (psc_m psc_m) {
            super.ac = null;
            return 0;
        }
        return a + this.a.b(array, n + a, 0);
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_f3 psc_f3 = new psc_f3();
        try {
            if (this.a != null) {
                psc_f3.a = (psc_bg)this.a.clone();
            }
        }
        catch (CloneNotSupportedException ex) {
            psc_f3.a = null;
        }
        super.a(psc_f3);
        return psc_f3;
    }
    
    public boolean equals(final Object o) {
        if (o == null || !(o instanceof psc_f3)) {
            return false;
        }
        final psc_f3 psc_f3 = (psc_f3)o;
        if (this.a != null) {
            if (psc_f3.a == null) {
                return false;
            }
            final int b = this.a.b(0);
            final int b2 = psc_f3.a.b(0);
            if (b != b2) {
                return false;
            }
            final byte[] array = new byte[b];
            final byte[] array2 = new byte[b2];
            this.a.b(array, 0, 0);
            psc_f3.a.b(array2, 0, 0);
            if (!psc_k4.a(array, array2)) {
                return false;
            }
        }
        else if (psc_f3.a != null) {
            return false;
        }
        return true;
    }
    
    protected void e() {
        super.e();
        this.a = null;
        this.b = null;
    }
}
