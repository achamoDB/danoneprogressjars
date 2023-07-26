import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_bt extends psc_aj implements Cloneable, Serializable, psc_bj
{
    private psc_bu a;
    private static final int b = 8454144;
    private psc_bu c;
    private static final int d = 8454145;
    psc_n e;
    
    public psc_bt() {
        super.bu = 30;
        super.bt = false;
        this.a = new psc_bu();
        this.c = new psc_bu();
        this.c(30);
        super.a = "NameConstraints";
    }
    
    public psc_bt(final psc_bu a, final psc_bu c, final boolean bt) {
        super.bu = 30;
        super.bt = bt;
        if (a != null) {
            this.a = a;
        }
        if (c != null) {
            this.c = c;
        }
        this.c(30);
        super.a = "NameConstraints";
    }
    
    public void d(final byte[] array, final int n) throws psc_g {
        if (array == null) {
            throw new psc_g("Encoding is null.");
        }
        final psc_h psc_h = new psc_h(0);
        final psc_j psc_j = new psc_j();
        final psc_k psc_k = new psc_k(8466432);
        final psc_k psc_k2 = new psc_k(8466433);
        final psc_i[] array2 = { psc_h, psc_k, psc_k2, psc_j };
        try {
            psc_l.a(array, n, array2);
        }
        catch (psc_m psc_m) {
            throw new psc_g("Could not decode Name Contraints extension.");
        }
        try {
            if (psc_k.a) {
                this.a = new psc_bu(psc_k.b, psc_k.c, 8454144);
            }
            if (psc_k2.a) {
                this.c = new psc_bu(psc_k2.b, psc_k2.c, 8454145);
            }
        }
        catch (psc_v psc_v) {
            throw new psc_g("Could not decode Name Constraints extension!!!.");
        }
    }
    
    public void a(final psc_bu a) {
        if (a != null) {
            this.a = a;
        }
    }
    
    public void b(final psc_bu c) {
        if (c != null) {
            this.c = c;
        }
    }
    
    public psc_bu a() {
        return this.a;
    }
    
    public psc_bu g() {
        return this.c;
    }
    
    public int e() {
        final psc_h psc_h = new psc_h(0, true, 0);
        final psc_j psc_j = new psc_j();
        psc_i psc_i = null;
        psc_i psc_i2 = null;
        try {
            if (this.a != null) {
                final byte[] array = new byte[this.a.f(8454144)];
                final int n = 0;
                psc_i = new psc_k(8466432, true, 0, array, n, this.a.b(array, n, 8454144));
            }
            if (this.c != null) {
                final byte[] array2 = new byte[this.c.f(8454145)];
                final int n2 = 0;
                psc_i2 = new psc_k(8466433, true, 0, array2, n2, this.c.b(array2, n2, 8454145));
            }
        }
        catch (psc_m psc_m) {
            return 0;
        }
        catch (psc_v psc_v) {
            return 0;
        }
        if (psc_i != null) {
            if (psc_i2 != null) {
                this.e = new psc_n(new psc_i[] { psc_h, psc_i, psc_i2, psc_j });
            }
            else {
                this.e = new psc_n(new psc_i[] { psc_h, psc_i, psc_j });
            }
        }
        else if (psc_i2 != null) {
            this.e = new psc_n(new psc_i[] { psc_h, psc_i2, psc_j });
        }
        else {
            this.e = new psc_n(new psc_i[] { psc_h, psc_j });
        }
        try {
            return this.e.a();
        }
        catch (psc_m psc_m2) {
            return 0;
        }
    }
    
    public int e(final byte[] array, final int n) {
        if (array == null) {
            return 0;
        }
        if (this.e == null && this.e() == 0) {
            return 0;
        }
        try {
            final int a = this.e.a(array, n);
            super.by = null;
            return a;
        }
        catch (psc_m psc_m) {
            super.by = null;
            return 0;
        }
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_bt psc_bt = new psc_bt();
        if (this.a != null) {
            psc_bt.a = (psc_bu)this.a.clone();
        }
        if (this.c != null) {
            psc_bt.c = (psc_bu)this.c.clone();
        }
        if (this.e != null) {
            psc_bt.e();
        }
        super.a(psc_bt);
        return psc_bt;
    }
    
    protected void f() {
        super.f();
        this.a = null;
        this.c = null;
        this.e = null;
    }
}
