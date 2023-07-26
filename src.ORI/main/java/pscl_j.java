import java.util.Date;

// 
// Decompiled by Procyon v0.5.36
// 

public class pscl_j
{
    private pscl_t a;
    private pscl_t b;
    private pscl_t c;
    private pscl_t d;
    private pscl_t e;
    private pscl_t f;
    private pscl_r g;
    private pscl_ak h;
    private pscl_ak i;
    private pscl_al j;
    private pscl_al k;
    private byte[] l;
    private boolean m;
    private static byte[] n;
    private static byte[] o;
    
    public pscl_j(final byte[] array, final int n, final int n2) throws pscl_x {
        this(array, n);
    }
    
    public pscl_j(final byte[] array, final int n) throws pscl_x {
        this.m = false;
        final pscl_z pscl_z = new pscl_z(0, true, 0);
        final pscl_ac pscl_ac = new pscl_ac();
        this.a = new pscl_t(12288, true, 0, null, 0, 0);
        this.e = new pscl_t(12288, true, 0, null, 0, 0);
        this.f = new pscl_t(768, true, 0, null, 0, 0);
        final int a = pscl_u.a(array, n, new pscl_q[] { pscl_z, this.a, this.e, this.f, pscl_ac });
        System.arraycopy(array, n, this.l = new byte[a], 0, a);
        final pscl_r pscl_r = new pscl_r(10616832, true, 0, null, 0, 0, true);
        this.g = new pscl_r(0, true, 0, null, 0, 0, true);
        final pscl_t pscl_t = new pscl_t(12288, true, 0, null, 0, 0);
        this.b = new pscl_t(12288, true, 0, null, 0, 0);
        final pscl_z pscl_z2 = new pscl_z(0, true, 0);
        final pscl_aj pscl_aj = new pscl_aj(0, 0);
        this.h = new pscl_ak(0, true, 0, null);
        this.j = new pscl_al(0, true, 0, null);
        final pscl_aj pscl_aj2 = new pscl_aj(0, 0);
        this.i = new pscl_ak(0, true, 0, null);
        this.k = new pscl_al(0, true, 0, null);
        this.c = new pscl_t(12288, true, 0, null, 0, 0);
        this.d = new pscl_t(12288, true, 0, null, 0, 0);
        pscl_u.a(this.a.l, this.a.m, new pscl_q[] { pscl_z, pscl_r, this.g, pscl_t, this.b, pscl_z2, pscl_aj, this.h, this.j, pscl_ac, pscl_aj2, this.i, this.k, pscl_ac, pscl_ac, this.c, this.d, new pscl_t(8454913, true, 0, null, 0, 0), new pscl_t(8454914, true, 0, null, 0, 0), new pscl_t(10563587, true, 0, null, 0, 0), pscl_ac });
        if (!this.h.g) {
            this.m = false;
        }
        else {
            this.m = true;
        }
    }
    
    public byte[] a() {
        final byte[] array = new byte[this.b.n + this.g.n];
        System.arraycopy(this.b.l, this.b.m, array, 0, this.b.n);
        System.arraycopy(this.g.l, this.g.m, array, this.b.n, this.g.n);
        return array;
    }
    
    public byte[] b() {
        final byte[] array = new byte[this.b.n];
        System.arraycopy(this.b.l, this.b.m, array, 0, array.length);
        return array;
    }
    
    public byte[] c() {
        final byte[] array = new byte[this.c.n];
        System.arraycopy(this.c.l, this.c.m, array, 0, array.length);
        return array;
    }
    
    public String d() throws pscl_h {
        return this.a(this.c);
    }
    
    public String e() throws pscl_h {
        return this.a(this.c);
    }
    
    public String a(final pscl_t pscl_t) throws pscl_h {
        try {
            final StringBuffer sb = new StringBuffer();
            final pscl_p pscl_p = new pscl_p(0, true, 0, 12288, 32512, 12544, 0);
            pscl_u.a(pscl_t.l, pscl_t.m, new pscl_q[] { pscl_p });
            for (int d = pscl_p.d(), i = 0; i < d; ++i) {
                final pscl_q a = pscl_p.a(i);
                final pscl_y pscl_y = new pscl_y(0, true, 0);
                final pscl_z pscl_z = new pscl_z(0, true, 0);
                final pscl_aa pscl_aa = new pscl_aa(16777216, true, 0, null, -1, -1);
                final pscl_t pscl_t2 = new pscl_t(65280, true, 0, null, 0, 0);
                final pscl_ac pscl_ac = new pscl_ac();
                pscl_u.a(a.l, a.m, new pscl_q[] { pscl_y, pscl_z, pscl_aa, pscl_t2, pscl_ac, pscl_ac });
                pscl_ae pscl_ae = null;
                switch (pscl_t2.l[pscl_t2.m]) {
                    case 19: {
                        pscl_ae = new pscl_ad(0, true, 0, null, 0, 0);
                        break;
                    }
                    case 20: {
                        pscl_ae = new pscl_af(0, true, 0, null, 0, 0);
                        break;
                    }
                    case 22: {
                        pscl_ae = new pscl_ag(0, true, 0, null, 0, 0);
                        break;
                    }
                    default: {
                        throw new pscl_h("Could not parse name");
                    }
                }
                pscl_u.a(pscl_t2.l, pscl_t2.m, new pscl_q[] { pscl_ae });
                sb.append(new String(pscl_ae.l, pscl_ae.m, pscl_ae.n));
                sb.append("\n");
            }
            return sb.toString();
        }
        catch (pscl_x pscl_x) {
            throw new pscl_h("Could not parse name");
        }
    }
    
    public byte[] f() {
        final byte[] array = new byte[this.d.n];
        System.arraycopy(this.d.l, this.d.m, array, 0, array.length);
        return array;
    }
    
    public boolean a(final pscl_ax pscl_ax) throws pscl_h {
        try {
            final pscl_ay pscl_ay = new pscl_ay(0, true, 0, null, 0, 0, 0, 1);
            pscl_u.a(this.f.l, this.f.m, new pscl_q[] { pscl_ay });
            final pscl_a9 pscl_a9 = new pscl_a9();
            pscl_a9.a(pscl_ax, null);
            final byte[] array = new byte[pscl_ay.n];
            pscl_a9.b(pscl_ay.l, pscl_ay.m, pscl_ay.n, array, 0);
            if (array[0] != 0 || array[1] != 1) {
                return false;
            }
            int n;
            for (n = 2; array[n] != 0; ++n) {}
            ++n;
            final pscl_z pscl_z = new pscl_z(0, true, 0);
            final pscl_z pscl_z2 = new pscl_z(0, true, 0);
            final pscl_aa pscl_aa = new pscl_aa(0, true, 0, null, 0, 0, 10, 11);
            final pscl_t pscl_t = new pscl_t(130816, true, 0, null, 0, 0);
            final pscl_s pscl_s = new pscl_s(0, true, 0, null, 0, 0);
            final pscl_ac pscl_ac = new pscl_ac();
            pscl_u.a(array, n, new pscl_q[] { pscl_z, pscl_z2, pscl_aa, pscl_t, pscl_ac, pscl_s, pscl_ac });
            final byte[] array2 = new byte[pscl_aa.n];
            System.arraycopy(pscl_aa.l, pscl_aa.m, array2, 0, pscl_aa.n);
            pscl_d pscl_d;
            if (pscl_k.a(array2, pscl_j.n)) {
                pscl_d = new pscl_aq();
            }
            else {
                if (!pscl_k.a(array2, pscl_j.o)) {
                    return false;
                }
                pscl_d = new pscl_c();
            }
            pscl_d.f();
            pscl_d.a(this.a.l, this.a.m, this.a.n);
            final byte[] array3 = new byte[pscl_d.d()];
            pscl_d.a(array3, 0);
            final byte[] array4 = new byte[pscl_s.n];
            System.arraycopy(pscl_s.l, pscl_s.m, array4, 0, pscl_s.n);
            return pscl_k.a(array4, array3);
        }
        catch (pscl_x pscl_x) {
            throw new pscl_h("Could not decode certificate");
        }
    }
    
    public int a(final byte[] array, final int n, final int n2) {
        System.arraycopy(this.l, 0, array, n, this.l.length);
        return this.l.length;
    }
    
    public int a(final int n) {
        return this.l.length;
    }
    
    public boolean a(final Date date) {
        Date when;
        Date when2;
        if (this.m) {
            when = this.h.a;
            when2 = this.i.a;
        }
        else {
            when = this.j.a;
            when2 = this.k.a;
        }
        return !date.before(when) && !date.after(when2);
    }
    
    static {
        pscl_j.n = new byte[] { 42, -122, 72, -122, -9, 13, 2, 5 };
        pscl_j.o = new byte[] { 43, 14, 3, 2, 26 };
    }
}
