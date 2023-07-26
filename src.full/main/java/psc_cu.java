import java.util.Date;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_cu extends psc_aj implements Cloneable, Serializable, psc_b2
{
    private static final int a = 10551296;
    psc_n b;
    private String c;
    private byte[] d;
    private int e;
    private Date f;
    
    public psc_cu() {
        super.bu = 119;
        super.bt = false;
        this.a(psc_aj.bd);
        super.a = "CRLReference";
        this.c = null;
        this.d = null;
        this.e = 0;
        this.f = null;
    }
    
    public psc_cu(final String s, final byte[] array, final int n, final int n2, final Date date) {
        super.bu = 119;
        super.bt = false;
        this.a(psc_aj.bd);
        super.a = "CRLReference";
        this.a(s);
        this.a(date);
        this.a(array, n, n2);
    }
    
    public int e() {
        try {
            final psc_h psc_h = new psc_h(0, true, 0);
            final psc_j psc_j = new psc_j();
            psc_i psc_i = null;
            psc_i psc_i2 = null;
            psc_i psc_i3 = null;
            boolean b2;
            int n;
            boolean b = (n = ((b2 = false) ? 1 : 0)) != 0;
            if (this.c != null) {
                psc_i = new psc_ag(10551296, true, 0, this.c);
                n = 1;
            }
            if (this.d != null) {
                psc_i2 = new psc_p(10551297, true, 0, this.d, 0, this.e, true);
                b = true;
            }
            if (this.f != null) {
                psc_i3 = new psc_ai(10551298, true, 0, this.f);
                b2 = true;
            }
            if (n != 0 && b && b2) {
                throw new psc_g("Could not encode CRLReference extension.");
            }
            if (n == 1) {
                this.b = new psc_n(new psc_i[] { psc_h, psc_i, psc_j });
            }
            if (b) {
                this.b = new psc_n(new psc_i[] { psc_h, psc_i2, psc_j });
            }
            if (b2) {
                this.b = new psc_n(new psc_i[] { psc_h, psc_i3, psc_j });
            }
            return this.b.a();
        }
        catch (psc_m psc_m) {
            return 0;
        }
        catch (psc_g psc_g) {
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
    
    public void d(final byte[] array, final int n) throws psc_g {
        if (array == null) {
            throw new psc_g("Encoding is null.");
        }
        try {
            final psc_h psc_h = new psc_h(0);
            final psc_ag psc_ag = new psc_ag(10551296);
            final psc_p psc_p = new psc_p(10551297);
            final psc_ai psc_ai = new psc_ai(10551298);
            psc_l.a(array, n, new psc_i[] { psc_h, psc_ag, psc_p, psc_ai, new psc_j() });
            if (psc_ag.a) {
                this.a(psc_ag.e());
            }
            else {
                this.c = null;
            }
            if (psc_p.a) {
                this.d = new byte[psc_p.d];
                System.arraycopy(psc_p.b, psc_p.c, this.d, 0, psc_p.d);
                this.e = psc_p.d;
            }
            else {
                this.d = null;
            }
            if (psc_ai.a) {
                this.a(psc_ai.a);
            }
            else {
                this.f = null;
            }
        }
        catch (psc_m psc_m) {
            throw new psc_g("Could not decode CRLReference extension.");
        }
    }
    
    public String a() {
        return this.c;
    }
    
    public void a(final String original) {
        if (original == null) {
            this.c = null;
        }
        else {
            this.c = new String(original);
        }
    }
    
    public byte[] g() {
        return this.d;
    }
    
    public void a(final byte[] array, final int n, final int e) {
        if (this.d == null || e == 0) {
            this.d = null;
            this.e = 0;
        }
        else {
            System.arraycopy(array, n, this.d = new byte[e], 0, e);
            this.e = e;
        }
    }
    
    public Date h() {
        return this.f;
    }
    
    public void a(final Date date) {
        this.f = ((date == null) ? null : new Date(date.getTime()));
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_cu psc_cu = new psc_cu();
        if (this.c != null) {
            psc_cu.c = new String(this.c);
        }
        if (this.f != null) {
            psc_cu.f = new Date(this.f.getTime());
        }
        if (this.d != null) {
            psc_cu.e = this.e;
            psc_cu.d = new byte[psc_cu.e];
            System.arraycopy(this.d, 0, psc_cu.d, 0, psc_cu.e);
        }
        if (this.b != null) {
            psc_cu.e();
        }
        super.a(psc_cu);
        return psc_cu;
    }
}
