import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_bk extends psc_aj implements Cloneable, Serializable, psc_bj, psc_bl
{
    psc_n a;
    private byte[] b;
    private static final int c = 8454144;
    private psc_bm d;
    private static final int e = 8454145;
    private byte[] f;
    private static final int g = 8454146;
    
    public psc_bk() {
        super.bu = 35;
        super.bt = false;
        this.d = new psc_bm();
        this.c(35);
        super.a = "AuthorityKeyID";
    }
    
    public psc_bk(final psc_bm d, final byte[] array, final int n, final int n2, final byte[] array2, final int n3, final int n4, final boolean bt) {
        super.bu = 35;
        super.bt = bt;
        this.c(35);
        super.a = "AuthorityKeyID";
        if (array2 != null && n4 != 0) {
            System.arraycopy(array2, n3, this.b = new byte[n4], 0, n4);
        }
        if (d != null) {
            this.d = d;
        }
        if (array != null && n2 != 0) {
            System.arraycopy(array, n, this.f = new byte[n2], 0, n2);
        }
    }
    
    public void d(final byte[] array, final int n) throws psc_g {
        if (array == null) {
            throw new psc_g("Encoding is null.");
        }
        psc_t psc_t;
        psc_p psc_p;
        psc_k psc_k;
        try {
            final psc_h psc_h = new psc_h(0);
            final psc_j psc_j = new psc_j();
            psc_t = new psc_t(8454144);
            psc_p = new psc_p(8454146);
            psc_k = new psc_k(8466433);
            psc_l.a(array, n, new psc_i[] { psc_h, psc_t, psc_k, psc_p, psc_j });
        }
        catch (psc_m psc_m) {
            throw new psc_g("Could not decode AuthorityKeyID extension.");
        }
        try {
            if (psc_t.a && psc_t.d != 0 && psc_t.b != null) {
                this.b = new byte[psc_t.d];
                System.arraycopy(psc_t.b, psc_t.c, this.b, 0, psc_t.d);
            }
            if (psc_k.a && psc_p.a) {
                this.d = new psc_bm(psc_k.b, psc_k.c, 8454145);
                this.f = new byte[psc_p.d];
                System.arraycopy(psc_p.b, psc_p.c, this.f, 0, psc_p.d);
            }
        }
        catch (psc_v psc_v) {
            throw new psc_g("Could not decode AuthorityKeyID extension!!!.");
        }
    }
    
    public void a(final byte[] array, final int n, final int n2) {
        if (array != null && n2 != 0) {
            System.arraycopy(array, n, this.b = new byte[n2], 0, n2);
        }
    }
    
    public void a(final psc_bm d) {
        if (d != null) {
            this.d = d;
        }
    }
    
    public void f(final byte[] array, final int n, final int n2) {
        if (array != null && n2 != 0) {
            System.arraycopy(array, n, this.f = new byte[n2], 0, n2);
        }
    }
    
    public byte[] a() {
        if (this.b == null) {
            return null;
        }
        final byte[] array = new byte[this.b.length];
        System.arraycopy(this.b, 0, array, 0, this.b.length);
        return array;
    }
    
    public psc_bm g() {
        return this.d;
    }
    
    public byte[] h() {
        if (this.f == null) {
            return null;
        }
        final byte[] array = new byte[this.f.length];
        System.arraycopy(this.f, 0, array, 0, this.f.length);
        return array;
    }
    
    public int e() {
        try {
            final psc_h psc_h = new psc_h(0, true, 0);
            final psc_j psc_j = new psc_j();
            psc_i psc_i = null;
            psc_i psc_i2 = null;
            psc_i psc_i3 = null;
            int n = 0;
            if (this.b != null) {
                psc_i = new psc_t(8454144, true, 0, this.b, 0, this.b.length);
                n = 1;
            }
            if (this.d != null && this.f != null) {
                psc_i2 = new psc_p(8454146, true, 0, this.f, 0, this.f.length, true);
                try {
                    final byte[] array = new byte[this.d.c(8454145)];
                    final int n2 = 0;
                    psc_i3 = new psc_k(8466433, true, 0, array, n2, this.d.a(array, n2, 8454145));
                }
                catch (psc_v psc_v) {
                    return 0;
                }
                if (n == 1) {
                    n = 3;
                }
                else {
                    n = 2;
                }
            }
            switch (n) {
                case 0: {
                    this.a = new psc_n(new psc_i[] { psc_h, psc_j });
                    break;
                }
                case 1: {
                    this.a = new psc_n(new psc_i[] { psc_h, psc_i, psc_j });
                    break;
                }
                case 2: {
                    this.a = new psc_n(new psc_i[] { psc_h, psc_i3, psc_i2, psc_j });
                    break;
                }
                case 3: {
                    this.a = new psc_n(new psc_i[] { psc_h, psc_i, psc_i3, psc_i2, psc_j });
                    break;
                }
            }
            return this.a.a();
        }
        catch (psc_m psc_m) {
            return 0;
        }
    }
    
    public int e(final byte[] array, final int n) {
        if (this.a == null && this.e() == 0) {
            return 0;
        }
        try {
            final int a = this.a.a(array, n);
            super.by = null;
            return a;
        }
        catch (psc_m psc_m) {
            super.by = null;
            return 0;
        }
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_bk psc_bk = new psc_bk();
        if (this.b != null) {
            psc_bk.b = this.b.clone();
        }
        if (this.d != null) {
            psc_bk.d = (psc_bm)this.d.clone();
        }
        if (this.f != null) {
            psc_bk.f = this.f.clone();
        }
        if (this.a != null) {
            psc_bk.e();
        }
        super.a(psc_bk);
        return psc_bk;
    }
    
    protected void f() {
        super.f();
        this.b = null;
        this.d = null;
        this.f = null;
        this.a = null;
    }
}
