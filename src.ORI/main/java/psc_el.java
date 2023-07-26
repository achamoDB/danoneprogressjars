import java.security.SecureRandom;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public abstract class psc_el extends psc_an implements Cloneable, Serializable
{
    private String a;
    private String[] b;
    private static final boolean[] c;
    private static final String[] d;
    private static final String e = "psc_el";
    private static final String[] f;
    
    public static psc_el a(final byte[] array, final int n, final String s) throws psc_ao, psc_be {
        String b;
        try {
            b = psc_q.b(array, n, 4, null);
        }
        catch (psc_m psc_m) {
            throw new psc_ao("Could not read BER data.(" + psc_m.getMessage() + ")");
        }
        final psc_el a = a(b, s);
        a.a(array, n);
        return a;
    }
    
    public static int b(final byte[] array, final int n) throws psc_ao {
        try {
            return psc_q.a(array, n, 4);
        }
        catch (psc_m psc_m) {
            throw new psc_ao("Could not read BER data.(" + psc_m.getMessage() + ")");
        }
    }
    
    void a(final byte[] array, final int n) throws psc_ao {
        throw new psc_ao("Cannot instantiate, unknown BER algorithm ID.");
    }
    
    public static psc_el a(final String str, final String str2) throws psc_ao, psc_be {
        psc_an.a();
        if (str2 == null) {
            throw new psc_ao("Cannot instantiate: no device given.");
        }
        if (str == null) {
            throw new psc_ao("Cannot instantiate: no transformation given.");
        }
        final String[] a = psc_ba.a(str2);
        final psc_bd[] array = new psc_bd[a.length];
        final String[] a2 = psc_ba.a(str);
        for (int i = 0; i < a.length; ++i) {
            if (array[i] == null) {
                array[i] = psc_bb.a(a[i]);
            }
            try {
                final psc_el a3 = a(a2, a, array[i], array);
                if (a3 != null) {
                    a3.a = array[i].c();
                    a3.b = array[i].d();
                    return a3;
                }
            }
            catch (psc_be psc_be) {
                if (i >= a.length) {
                    throw psc_be;
                }
            }
        }
        throw new psc_ao("A JSAFE_Parameters object of " + str + " is not available on any of the devices. (" + str2 + ")");
    }
    
    private static psc_el a(final String[] array, final String[] array2, final psc_bd psc_bd, final psc_bd[] array3) throws psc_be {
        if (array.length != 1) {
            return null;
        }
        final Object[] a = psc_bd.a(array, psc_el.f, 5, psc_el.c, psc_el.d, "psc_el", array2, array3);
        if (a == null) {
            return null;
        }
        ((psc_el)a[0]).a(psc_ba.b(array[0]));
        return (psc_el)a[0];
    }
    
    protected void a(final int[] array) throws psc_be {
        if (array != null && array.length != 0) {
            throw new psc_be("Incorrect number of parameters: expected none");
        }
    }
    
    public byte[] f() throws psc_ao {
        return this.a((String)null);
    }
    
    public abstract byte[] a(final String p0) throws psc_ao;
    
    public String g() {
        return this.a;
    }
    
    public String[] h() {
        final String[] array = new String[this.b.length];
        for (int i = 0; i < this.b.length; ++i) {
            array[i] = this.b[i];
        }
        return array;
    }
    
    public abstract String i();
    
    public abstract String[] j();
    
    public abstract String[] k();
    
    public abstract void a(final String p0, final byte[][] p1) throws psc_e1, psc_ao;
    
    public abstract void a(final byte[][] p0) throws psc_e1;
    
    public abstract byte[][] b(final String p0) throws psc_ao;
    
    public abstract byte[][] l();
    
    public byte[][] d(final int[] array) throws psc_en {
        if (this instanceof psc_jo && psc_aq.k() == 0) {
            return ((psc_jo)this).b(array);
        }
        throw new psc_en("Only supported for DSA on FIPS_MODE");
    }
    
    public abstract void b(final int[] p0, final SecureRandom p1) throws psc_be;
    
    public abstract void a(final int[] p0, final SecureRandom p1, final psc_nf[] p2) throws psc_be;
    
    public void c(final int[] array, final SecureRandom secureRandom) throws psc_be, psc_en {
        throw new psc_en("Use generateFips only with DSA parameters.");
    }
    
    public abstract void m() throws psc_en;
    
    public abstract void n() throws psc_en;
    
    public abstract Object clone() throws CloneNotSupportedException;
    
    protected void a(final psc_el psc_el) {
        this.a = psc_el.a;
        this.b = new String[psc_el.b.length];
        for (int i = 0; i < psc_el.b.length; ++i) {
            this.b[i] = psc_el.b[i];
        }
    }
    
    public void y() {
        super.y();
    }
    
    static {
        c = new boolean[] { true };
        d = new String[] { "psc_el" };
        f = new String[] { "Parameters" };
    }
}
