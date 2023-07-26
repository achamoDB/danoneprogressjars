import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public abstract class psc_dt extends psc_an implements psc_am, Cloneable, Serializable
{
    private String a;
    private String[] b;
    private static final boolean[] c;
    private static final String[] d;
    private static final String e = "psc_dt";
    private static final String[] f;
    
    public static psc_dt a(final byte[] array, final int n, final String s) throws psc_ao {
        String b;
        try {
            b = psc_q.b(array, n, 3, null);
        }
        catch (psc_m psc_m) {
            throw new psc_ao("Could not read BER data.(" + psc_m.getMessage() + ")");
        }
        final psc_dt a = a(b, s);
        try {
            a.a(array, n);
        }
        catch (psc_bf psc_bf) {
            throw new psc_ao("Key BER info is incorrect.");
        }
        return a;
    }
    
    public static int b(final byte[] array, final int n) throws psc_ao {
        try {
            return n + psc_lh.a(array, n, null, null, null);
        }
        catch (psc_ap psc_ap) {
            throw new psc_ao("Could not read BER data.");
        }
    }
    
    void a(final byte[] array, final int n) throws psc_bf {
        throw new psc_bf("Cannot read the BER.");
    }
    
    public static psc_dt a(final String str, final String str2) throws psc_ao {
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
                final psc_dt a3 = a(a2, a, array[i], array);
                if (a3 != null) {
                    a3.a = array[i].c();
                    a3.b = array[i].d();
                    return a3;
                }
            }
            catch (psc_be psc_be) {}
        }
        throw new psc_ao("A JSAFE_PrivateKey object of " + str + " is not available on any of the devices. (" + str2 + ")");
    }
    
    private static psc_dt a(final String[] array, final String[] array2, final psc_bd psc_bd, final psc_bd[] array3) throws psc_be {
        if (array.length != 1) {
            return null;
        }
        final Object[] a = psc_bd.a(array, psc_dt.f, 6, psc_dt.c, psc_dt.d, "psc_dt", array2, array3);
        if (a == null) {
            return null;
        }
        ((psc_dt)a[0]).a(psc_ba.b(array[0]));
        return (psc_dt)a[0];
    }
    
    protected void a(final int[] array) throws psc_be {
        if (array != null && array.length != 0) {
            throw new psc_be("Incorrect number of parameters: expected none.");
        }
    }
    
    public String k() {
        return this.a;
    }
    
    public String[] r() {
        final String[] array = new String[this.b.length];
        for (int i = 0; i < this.b.length; ++i) {
            array[i] = this.b[i];
        }
        return array;
    }
    
    public abstract String l();
    
    public abstract Object clone() throws CloneNotSupportedException;
    
    protected void a(final psc_dt psc_dt) {
        this.a = psc_dt.a;
        this.b = new String[psc_dt.b.length];
        for (int i = 0; i < psc_dt.b.length; ++i) {
            this.b[i] = psc_dt.b[i];
        }
    }
    
    public String b(final boolean b) {
        return this.l() + "PrivateKeyBER";
    }
    
    public abstract boolean a(final Object p0);
    
    protected boolean a(final byte[] array, final psc_dd psc_dd, final byte[] array2, final psc_dd psc_dd2) {
        if (array == null) {
            return array2 == null;
        }
        if (array2 == null) {
            return false;
        }
        if (array.length != array2.length) {
            return false;
        }
        if (psc_dd != null) {
            psc_dd.d();
        }
        if (psc_dd2 != null) {
            psc_dd2.d();
        }
        int n;
        for (n = 0; n < array.length && array[n] == array2[n]; ++n) {}
        if (psc_dd != null) {
            psc_dd.c();
        }
        if (psc_dd2 != null) {
            psc_dd2.c();
        }
        return n >= array.length;
    }
    
    public void y() {
        super.y();
    }
    
    static {
        c = new boolean[] { true };
        d = new String[] { "psc_dt" };
        f = new String[] { "PrivateKey" };
    }
}
