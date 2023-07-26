import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_cv extends psc_aj implements Cloneable, Serializable, psc_cw
{
    private byte[] a;
    private int b;
    psc_n c;
    
    public psc_cv() {
        super.bu = 120;
        super.bt = false;
        this.a(psc_aj.bf);
        super.a = "OCSPNonce";
        this.a = null;
        this.b = 0;
    }
    
    public psc_cv(final byte[] array, final int n, final int n2) {
        super.bu = 120;
        this.a(psc_aj.bf);
        super.a = "OCSPNonce";
        super.bt = false;
        this.a(array, n, n2);
    }
    
    public void a(final byte[] array, final int n, final int b) {
        if (array == null || b == 0) {
            return;
        }
        System.arraycopy(array, n, this.a = new byte[b], 0, b);
        this.b = b;
    }
    
    public byte[] a() {
        if (this.a == null) {
            return null;
        }
        return this.a;
    }
    
    public void d(final byte[] array, final int n) throws psc_g {
        if (array == null) {
            throw new psc_g("Encoding is null.");
        }
        final psc_t psc_t = new psc_t(0);
        final psc_i[] array2 = { psc_t };
        try {
            psc_l.a(array, n, array2);
        }
        catch (psc_m psc_m) {
            this.a = null;
            this.b = 0;
            throw new psc_g("Could not decode OCSPNonce extension.");
        }
        this.a(psc_t.b, psc_t.c, psc_t.d);
    }
    
    public int e() {
        if (this.a == null || this.b == 0) {
            return 0;
        }
        try {
            this.c = new psc_n(new psc_i[] { new psc_t(0, true, 0, this.a, 0, this.b) });
            return this.c.a();
        }
        catch (psc_m psc_m) {
            return 0;
        }
    }
    
    public int e(final byte[] array, final int n) {
        if (array == null) {
            return 0;
        }
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
        final psc_cv psc_cv = new psc_cv();
        if (this.a != null) {
            psc_cv.b = this.b;
            psc_cv.a = new byte[psc_cv.b];
            System.arraycopy(this.a, 0, psc_cv.a, 0, psc_cv.b);
        }
        if (this.c != null) {
            psc_cv.e();
        }
        super.a(psc_cv);
        return psc_cv;
    }
    
    protected void f() {
        super.f();
        this.c = null;
    }
}
