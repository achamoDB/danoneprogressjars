import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_bx extends psc_aj implements Cloneable, Serializable, psc_bj
{
    private int a;
    private int b;
    psc_n c;
    private static final int d = 8454144;
    private static final int e = 8454145;
    
    public psc_bx() {
        this.a = -1;
        this.b = -1;
        super.bu = 36;
        super.bt = false;
        this.c(36);
        super.a = "PolicyConstraints";
    }
    
    public psc_bx(final int a, final int b, final boolean bt) {
        this.a = -1;
        this.b = -1;
        super.bu = 36;
        super.bt = bt;
        this.c(36);
        this.a = a;
        this.b = b;
        super.a = "PolicyConstraints";
    }
    
    public void d(final byte[] array, final int n) throws psc_g {
        if (array == null) {
            throw new psc_g("Encoding is null.");
        }
        final psc_h psc_h = new psc_h(0);
        final psc_j psc_j = new psc_j();
        final psc_p psc_p = new psc_p(8454144);
        final psc_p psc_p2 = new psc_p(8454145);
        final psc_i[] array2 = { psc_h, psc_p, psc_p2, psc_j };
        try {
            psc_l.a(array, n, array2);
        }
        catch (psc_m psc_m) {
            throw new psc_g("Could not decode Policy Constraints extension.");
        }
        if (psc_p.a) {
            this.a = this.a(psc_p.b, psc_p.c, psc_p.d);
        }
        if (psc_p2.a) {
            this.b = this.a(psc_p2.b, psc_p2.c, psc_p2.d);
        }
    }
    
    private int a(final byte[] array, final int n, final int n2) throws psc_g {
        if (array == null || n2 == 0) {
            return 0;
        }
        if (n2 > 4) {
            throw new psc_g("Could not decode AuthorityKeyID extension.");
        }
        int n3 = 0;
        for (int i = n; i < n2 + n; ++i) {
            n3 = (n3 << 8 | (array[i] & 0xFF));
        }
        return n3;
    }
    
    public void a(final int a) {
        this.a = a;
    }
    
    public void b(final int b) {
        this.b = b;
    }
    
    public int a() {
        return this.a;
    }
    
    public int g() {
        return this.b;
    }
    
    public int e() {
        final psc_h psc_h = new psc_h(0, true, 0);
        final psc_j psc_j = new psc_j();
        psc_i psc_i = null;
        if (this.a != -1) {
            psc_i = new psc_p(8454144, true, 0, this.a);
        }
        psc_i psc_i2 = null;
        if (this.b != -1) {
            psc_i2 = new psc_p(8454145, true, 0, this.b);
        }
        if (psc_i != null) {
            if (psc_i2 != null) {
                this.c = new psc_n(new psc_i[] { psc_h, psc_i, psc_i2, psc_j });
            }
            else {
                this.c = new psc_n(new psc_i[] { psc_h, psc_i, psc_j });
            }
        }
        else if (psc_i2 != null) {
            this.c = new psc_n(new psc_i[] { psc_h, psc_i2, psc_j });
        }
        else {
            this.c = new psc_n(new psc_i[] { psc_h, psc_j });
        }
        try {
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
        final psc_bx psc_bx = new psc_bx();
        psc_bx.a = this.a;
        psc_bx.b = this.b;
        if (this.c != null) {
            psc_bx.e();
        }
        super.a(psc_bx);
        return psc_bx;
    }
    
    protected void f() {
        super.f();
        this.a = 0;
        this.b = 0;
        this.c = null;
    }
}
