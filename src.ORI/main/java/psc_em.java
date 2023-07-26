import java.security.SecureRandom;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public abstract class psc_em extends psc_an implements Cloneable, Serializable
{
    private psc_al a;
    private psc_dt b;
    private String c;
    private String[] d;
    private psc_em[] e;
    private int f;
    private static boolean[] g;
    private static String[] h;
    private static final String i = "psc_em";
    private static final String[] j;
    protected static final int k = 0;
    protected static final int l = 1;
    
    public static psc_em a(final String str, final String str2) throws psc_ao, psc_be {
        psc_an.a();
        if (str2 == null) {
            throw new psc_ao("Cannot instantiate: no device given.");
        }
        if (str == null) {
            throw new psc_ao("Cannot instantiate: no transformation given.");
        }
        final String[] a = psc_ba.a(str2);
        final psc_bd[] array = new psc_bd[a.length];
        final psc_em[] e = new psc_em[a.length];
        final String[] a2 = psc_ba.a(str);
        for (int i = 0; i < a.length; ++i) {
            if (array[i] == null) {
                array[i] = psc_bb.a(a[i]);
            }
            try {
                final psc_em a3 = a(a2, a, array[i], array);
                if (a3 != null) {
                    a3.c = array[i].c();
                    a3.d = array[i].d();
                    e[i] = a3;
                }
            }
            catch (psc_be psc_be) {
                if (i >= a.length) {
                    int n;
                    for (n = 0; n < e.length && e[n] == null; ++n) {}
                    if (n >= e.length) {
                        throw psc_be;
                    }
                }
            }
        }
        int j = 0;
        int n2 = -1;
        while (j < e.length) {
            if (e[j] != null) {
                if (n2 == -1) {
                    n2 = j;
                    e[n2].e = e;
                }
                e[j].f = -1;
            }
            ++j;
        }
        if (n2 != -1) {
            return e[n2];
        }
        throw new psc_ao("A JSAFE_KeyPair object of " + str + " is not available on any of the devices. (" + str2 + ")");
    }
    
    private static psc_em a(final String[] array, final String[] array2, final psc_bd psc_bd, final psc_bd[] array3) throws psc_be {
        if (array.length != 1) {
            return null;
        }
        final Object[] a = psc_bd.a(array, psc_em.j, 2, psc_em.g, psc_em.h, "psc_em", array2, array3);
        if (a == null) {
            return null;
        }
        ((psc_em)a[0]).a(psc_ba.b(array[0]));
        return (psc_em)a[0];
    }
    
    public void a(final psc_oi psc_oi) {
        if (this.e == null) {
            this.a(0, psc_oi);
            return;
        }
        for (int i = 0; i < this.e.length; ++i) {
            if (this.e[i] != null) {
                this.e[i].a(0, psc_oi);
            }
        }
    }
    
    public void b(final psc_oi psc_oi) {
        if (this.e == null) {
            this.a(1, psc_oi);
            return;
        }
        for (int i = 0; i < this.e.length; ++i) {
            if (this.e[i] != null) {
                this.e[i].a(1, psc_oi);
            }
        }
    }
    
    protected void a(final int n, final psc_oi psc_oi) {
    }
    
    protected void a(final int[] array) throws psc_be {
        if (array != null && array.length != 0) {
            throw new psc_be("Incorrect number of parameters: expected none.");
        }
    }
    
    public String m() {
        if (this.f != -1) {
            return this.e[this.f].c;
        }
        return this.c;
    }
    
    public String[] n() {
        int i = 0;
        String[] array;
        if (this.f == -1) {
            array = new String[this.d.length];
            while (i < this.d.length) {
                array[i] = this.d[i];
                ++i;
            }
        }
        else {
            array = new String[this.e[this.f].d.length];
            while (i < this.e[this.f].d.length) {
                array[i] = this.e[this.f].d[i];
                ++i;
            }
        }
        return array;
    }
    
    public abstract String o();
    
    public void b(final psc_al psc_al, final psc_dt psc_dt) throws psc_bf {
        if (this.e == null) {
            this.f();
            if (!this.a(psc_al, psc_dt)) {
                throw new psc_bf("Keys provided did not match the JSAFE_KeyPair object.");
            }
        }
        else {
            boolean b = false;
            for (int i = 0; i < this.e.length; ++i) {
                if (this.e[i] != null) {
                    if (this.e[i].a(psc_al, psc_dt)) {
                        b = true;
                    }
                }
            }
            if (!b) {
                throw new psc_bf("Keys provided did not match the JSAFE_KeyPair object.");
            }
        }
    }
    
    protected boolean a(final psc_al psc_al, final psc_dt psc_dt) {
        try {
            this.a = (psc_al)psc_al.clone();
            this.b = (psc_dt)psc_dt.clone();
        }
        catch (CloneNotSupportedException ex) {
            return false;
        }
        return true;
    }
    
    public psc_al p() {
        if (this.e == null) {
            try {
                if (this.a != null) {
                    return (psc_al)this.a.clone();
                }
            }
            catch (CloneNotSupportedException ex) {
                return null;
            }
            return null;
        }
        if (this.f != -1) {
            try {
                if (this.e[this.f].a != null) {
                    return (psc_al)this.e[this.f].a.clone();
                }
            }
            catch (CloneNotSupportedException ex2) {
                return null;
            }
        }
        for (int i = 0; i < this.e.length; ++i) {
            try {
                if (this.e[i].a != null) {
                    return (psc_al)this.e[i].a.clone();
                }
            }
            catch (CloneNotSupportedException ex3) {}
        }
        return null;
    }
    
    public psc_dt q() {
        if (this.e == null) {
            try {
                if (this.b != null) {
                    return (psc_dt)this.b.clone();
                }
            }
            catch (CloneNotSupportedException ex) {
                return null;
            }
            return null;
        }
        if (this.f != -1) {
            try {
                if (this.e[this.f].b != null) {
                    return (psc_dt)this.e[this.f].b.clone();
                }
            }
            catch (CloneNotSupportedException ex2) {
                return null;
            }
        }
        for (int i = 0; i < this.e.length; ++i) {
            try {
                if (this.e[i].b != null) {
                    return (psc_dt)this.e[i].b.clone();
                }
            }
            catch (CloneNotSupportedException ex3) {}
        }
        return null;
    }
    
    public Object clone() throws CloneNotSupportedException {
        if (this.e != null) {
            final psc_em[] e = new psc_em[this.e.length];
            for (int i = 0; i < this.e.length; ++i) {
                if (this.e[i] != null) {
                    e[i] = this.e[i].c();
                }
            }
            for (int j = 0; j < this.e.length; ++j) {
                if (e[j] != null) {
                    e[j].e = e;
                    return e[j];
                }
            }
        }
        throw new CloneNotSupportedException();
    }
    
    protected abstract psc_em c() throws CloneNotSupportedException;
    
    protected void a(final psc_em psc_em) throws CloneNotSupportedException {
        if (psc_em.a != null) {
            this.a = (psc_al)psc_em.a.clone();
        }
        if (psc_em.b != null) {
            this.b = (psc_dt)psc_em.b.clone();
        }
        this.f = psc_em.f;
        this.c = psc_em.c;
        this.d = new String[psc_em.d.length];
        for (int i = 0; i < psc_em.d.length; ++i) {
            this.d[i] = psc_em.d[i];
        }
    }
    
    public void a(final psc_el psc_el, final int[] array, final SecureRandom secureRandom) throws psc_be, psc_en {
        this.b(psc_el, array, secureRandom, null);
    }
    
    public void b(final psc_el psc_el, final int[] array, final SecureRandom secureRandom, final psc_nf[] array2) throws psc_be, psc_en {
        if (this.e != null) {
            for (int i = 0; i < this.e.length; ++i) {
                if (this.e[i] != null) {
                    if (this.e[i].a(psc_el, array, secureRandom, array2)) {
                        this.f = i;
                        return;
                    }
                }
            }
            throw new psc_en("Could not initialize with the given parameters.");
        }
        if (this.a(psc_el, array, secureRandom, array2)) {
            return;
        }
        throw new psc_en("Could not initialize with the given parameters.");
    }
    
    protected abstract boolean a(final psc_el p0, final int[] p1, final SecureRandom p2, final psc_nf[] p3);
    
    public void b(final psc_el psc_el, final int[] array, final SecureRandom secureRandom) throws psc_be, psc_en {
        throw new psc_en("Use generateStrong only with RSA.");
    }
    
    public void r() throws psc_en {
        if (this.e == null) {
            this.d();
            return;
        }
        if (this.f == -1) {
            throw new psc_en("Cannot generateReInit, not initialized.");
        }
        this.e[this.f].d();
    }
    
    protected abstract void d() throws psc_en;
    
    public byte[][] b(final byte[] array, final byte[] array2, final byte[] array3, final byte[] array4, final byte[] array5, final byte[] array6) throws psc_en {
        if (!psc_aq.c() || psc_aq.l() != 0) {
            throw new psc_en("Must be in FIPS_MODE and CRYPTO_OFFICER_ROLE");
        }
        if (!this.o().equals("RSA")) {
            throw new psc_en("Must be an RSA key pair");
        }
        return ((psc_ja)this).a(array, array2, array3, array4, array5, array6);
    }
    
    public void s() throws psc_en {
        if (this.e == null) {
            this.e();
            return;
        }
        if (this.f == -1) {
            throw new psc_en("Cannot generate key pair, not initialized.");
        }
        this.e[this.f].e();
    }
    
    protected abstract void e() throws psc_en;
    
    protected void f() {
        if (this.a != null) {
            this.a.y();
        }
        if (this.b != null) {
            this.b.y();
        }
        this.a = null;
        this.b = null;
    }
    
    public void y() {
        super.y();
        if (this.e == null) {
            this.g();
        }
        else {
            for (int i = 0; i < this.e.length; ++i) {
                if (this.e[i] != null) {
                    this.e[i].g();
                }
            }
        }
        this.f = -1;
    }
    
    protected abstract void g();
    
    static {
        psc_em.g = new boolean[] { true };
        psc_em.h = new String[] { "psc_em" };
        j = new String[] { "KeyPair" };
    }
}
