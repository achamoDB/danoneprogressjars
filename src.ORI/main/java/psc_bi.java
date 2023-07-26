import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_bi extends psc_aj implements Cloneable, Serializable, psc_bj
{
    private byte[] a;
    private int b;
    private int c;
    
    public psc_bi() {
        super.bu = 14;
        super.bt = false;
        this.c(14);
        super.a = "SubjectKeyID";
    }
    
    public psc_bi(final byte[] array, final int n, final int n2, final boolean bt) {
        super.bu = 14;
        super.bt = bt;
        if (array != null && n2 != 0) {
            this.a(array, n, n2);
        }
        this.c(14);
        super.a = "SubjectKeyID";
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
            throw new psc_g("Could not decode SubjectKeyID extension.");
        }
        this.a(psc_t.b, psc_t.c, psc_t.d);
    }
    
    public void a(final byte[] array, final int n, final int n2) {
        if (array == null || n2 == 0) {
            return;
        }
        try {
            this.a = psc_l.a(new psc_i[] { new psc_t(0, true, 0, array, n, n2) });
            this.b = 1 + psc_o.a(this.a, 1);
            this.c = this.a.length - this.b;
        }
        catch (psc_m psc_m) {
            this.a = null;
            this.b = 0;
            this.c = 0;
        }
    }
    
    public byte[] a() {
        if (this.a == null) {
            return null;
        }
        final byte[] array = new byte[this.c];
        System.arraycopy(this.a, this.b, array, 0, this.c);
        return array;
    }
    
    public int e() {
        if (this.a != null) {
            return this.a.length;
        }
        return 0;
    }
    
    public int e(final byte[] array, final int n) {
        if (array == null) {
            return 0;
        }
        if (this.a == null) {
            return 0;
        }
        System.arraycopy(this.a, 0, array, n, this.a.length);
        return this.a.length;
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_bi psc_bi = new psc_bi();
        if (this.a != null) {
            psc_bi.a = this.a.clone();
            psc_bi.b = this.b;
            psc_bi.c = this.c;
        }
        super.a(psc_bi);
        return psc_bi;
    }
    
    protected void f() {
        super.f();
        this.a = null;
    }
}
