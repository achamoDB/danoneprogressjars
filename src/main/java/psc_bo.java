import java.util.Date;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_bo extends psc_aj implements Cloneable, Serializable, psc_bj
{
    private Date a;
    private Date b;
    private static final int c = 8454144;
    private static final int d = 8454145;
    psc_n e;
    
    public psc_bo() {
        super.bu = 16;
        super.bt = false;
        this.c(16);
        super.a = "PrivateKeyUsagePeriod";
    }
    
    public psc_bo(final Date date, final Date date2, final boolean bt) {
        super.bu = 16;
        super.bt = bt;
        this.c(16);
        if (date != null) {
            this.a = new Date(date.getTime());
        }
        if (date2 != null) {
            this.b = new Date(date2.getTime());
        }
        super.a = "PrivateKeyUsagePeriod";
    }
    
    public void d(final byte[] array, final int n) throws psc_g {
        if (array == null) {
            throw new psc_g("Encoding is null.");
        }
        final psc_h psc_h = new psc_h(0);
        final psc_j psc_j = new psc_j();
        final psc_ai psc_ai = new psc_ai(8454144);
        final psc_ai psc_ai2 = new psc_ai(8454145);
        final psc_i[] array2 = { psc_h, psc_ai, psc_ai2, psc_j };
        try {
            psc_l.a(array, n, array2);
        }
        catch (psc_m psc_m) {
            throw new psc_g("Could not decode PrivateKeyUsagePeriod extension.");
        }
        if (psc_ai.a) {
            this.a = new Date(psc_ai.a.getTime());
        }
        if (psc_ai2.a) {
            this.b = new Date(psc_ai2.a.getTime());
        }
    }
    
    public void a(final Date date) {
        if (date != null) {
            this.a = new Date(date.getTime());
        }
    }
    
    public Date a() {
        if (this.a == null) {
            return null;
        }
        return new Date(this.a.getTime());
    }
    
    public void b(final Date date) {
        if (date != null) {
            this.b = new Date(date.getTime());
        }
    }
    
    public Date g() {
        if (this.b == null) {
            return null;
        }
        return new Date(this.b.getTime());
    }
    
    public int e() {
        final psc_h psc_h = new psc_h(0, true, 0);
        final psc_j psc_j = new psc_j();
        psc_i psc_i = null;
        psc_i psc_i2 = null;
        int n = 0;
        if (this.a != null) {
            psc_i = new psc_ai(8454144, true, 0, this.a);
            n = 1;
        }
        if (this.b != null) {
            psc_i2 = new psc_ai(8454145, true, 0, this.b);
            if (n == 1) {
                n = 3;
            }
            else {
                n = 2;
            }
        }
        switch (n) {
            case 0: {
                this.e = new psc_n(new psc_i[] { psc_h, psc_j });
                break;
            }
            case 1: {
                this.e = new psc_n(new psc_i[] { psc_h, psc_i, psc_j });
                break;
            }
            case 2: {
                this.e = new psc_n(new psc_i[] { psc_h, psc_i2, psc_j });
                break;
            }
            case 3: {
                this.e = new psc_n(new psc_i[] { psc_h, psc_i, psc_i2, psc_j });
                break;
            }
        }
        try {
            return this.e.a();
        }
        catch (psc_m psc_m) {
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
        final psc_bo psc_bo = new psc_bo();
        psc_bo.a = this.a;
        psc_bo.b = this.b;
        if (this.e != null) {
            psc_bo.e();
        }
        super.a(psc_bo);
        return psc_bo;
    }
    
    protected void f() {
        super.f();
        this.a = null;
        this.b = null;
        this.e = null;
    }
}
