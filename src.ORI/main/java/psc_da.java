import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public abstract class psc_da extends psc_an implements Cloneable, Serializable
{
    private String a;
    private String[] b;
    private static final boolean[] c;
    private static final String[] d;
    private static final String e = "psc_da";
    
    public static psc_da a(final byte[] array, final int n, final String s) throws psc_ao, psc_be {
        String b;
        try {
            b = psc_q.b(array, n, 10, null);
        }
        catch (psc_m psc_m) {
            throw new psc_ao("Could not read BER data.(" + psc_m.getMessage() + ")");
        }
        final psc_da a = a(b, s);
        a.a(array, n);
        return a;
    }
    
    public static int b(final byte[] array, final int n) throws psc_ao {
        try {
            return psc_q.a(array, n, 10);
        }
        catch (psc_m psc_m) {
            throw new psc_ao("Could not read BER data.(" + psc_m.getMessage() + ")");
        }
    }
    
    void a(final byte[] array, final int n) throws psc_ao, psc_be {
        throw new psc_ao("Cannot instantiate, unknown BER algorithm ID.");
    }
    
    public static psc_da a(final String str, final String str2) throws psc_ao, psc_be {
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
                final psc_da a3 = a(a2, a, array[i], array);
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
        throw new psc_ao("A JSAFE_MessageDigest object of " + str + " is not available on any of the devices. (" + str2 + ")");
    }
    
    private static psc_da a(final String[] array, final String[] array2, final psc_bd psc_bd, final psc_bd[] array3) throws psc_be {
        if (array.length != 1) {
            return null;
        }
        final Object[] a = psc_bd.a(array, null, 4, psc_da.c, psc_da.d, "psc_da", array2, array3);
        if (a == null) {
            return null;
        }
        if (a[0] instanceof psc_da) {
            return (psc_da)a[0];
        }
        ((psc_az)a[0]).a(psc_ba.b(array[0]));
        return new psc_db((psc_az)a[0]);
    }
    
    public abstract byte[] c();
    
    public String d() {
        return this.a;
    }
    
    public String[] e() {
        final String[] array = new String[this.b.length];
        for (int i = 0; i < this.b.length; ++i) {
            array[i] = this.b[i];
        }
        return array;
    }
    
    public abstract String f();
    
    public abstract int g();
    
    public abstract int h();
    
    public abstract void i();
    
    public abstract void a(final byte[] p0, final int p1, final int p2) throws psc_en;
    
    public byte[] j() throws psc_en {
        final byte[] array = new byte[this.g()];
        this.c(array, 0);
        return array;
    }
    
    public abstract int c(final byte[] p0, final int p1) throws psc_en;
    
    public byte[] d(final byte[] array, final int n) {
        final byte[] array2 = new byte[this.h()];
        this.a(array, n, array2, 0);
        return array2;
    }
    
    public abstract int a(final byte[] p0, final int p1, final byte[] p2, final int p3);
    
    public static String e(final byte[] array, final int n) throws psc_e1 {
        String b;
        try {
            b = psc_q.b(array, n, 10, null);
        }
        catch (psc_m psc_m) {
            throw new psc_e1("Could not decode BER(" + psc_m.getMessage() + ")");
        }
        return b;
    }
    
    public static byte[] f(final byte[] array, final int n) throws psc_e1 {
        final psc_h psc_h = new psc_h(0);
        final psc_h psc_h2 = new psc_h(0);
        final psc_j psc_j = new psc_j();
        final psc_r psc_r = new psc_r(0, 10);
        final psc_k psc_k = new psc_k(77824);
        final psc_t psc_t = new psc_t(0);
        final psc_i[] array2 = { psc_h, psc_h2, psc_r, psc_k, psc_j, psc_t, psc_j };
        try {
            psc_l.a(array, n, array2);
        }
        catch (psc_m psc_m) {
            throw new psc_e1("Could not decode BER. (" + psc_m.getMessage() + ")");
        }
        final byte[] array3 = new byte[psc_t.d];
        System.arraycopy(psc_t.b, psc_t.c, array3, 0, psc_t.d);
        return array3;
    }
    
    protected void a(final psc_da psc_da) {
        this.a = psc_da.a;
        this.b = new String[psc_da.b.length];
        for (int i = 0; i < psc_da.b.length; ++i) {
            this.b[i] = psc_da.b[i];
        }
    }
    
    public void y() {
        super.y();
    }
    
    static {
        c = new boolean[] { true };
        d = new String[] { "com.rsa.jsafe.JA_AlgaeDigest" };
    }
}
