import java.security.SecureRandom;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public abstract class psc_eh extends psc_an implements Cloneable, Serializable
{
    private String a;
    private String[] b;
    private static boolean[] c;
    private static String[] d;
    private static final String e = "psc_eh";
    
    public static psc_eh a(final byte[] array, final int n, final String s) throws psc_ao, psc_be {
        String b;
        try {
            b = psc_q.b(array, n, 10, null);
        }
        catch (psc_m psc_m) {
            throw new psc_ao("Could not read BER data.(" + psc_m.getMessage() + ")");
        }
        final psc_eh a = a(b, s);
        a.a(array, n);
        return a;
    }
    
    public static int b(final byte[] array, final int n) throws psc_ao {
        try {
            return psc_q.a(array, n, -1);
        }
        catch (psc_m psc_m) {
            throw new psc_ao("Could not read BER data.(" + psc_m.getMessage() + ")");
        }
    }
    
    void a(final byte[] array, final int n) throws psc_ao, psc_be {
        throw new psc_ao("Cannot instantiate, unknown BER algorithm ID.");
    }
    
    public static psc_eh a(final String str, final String str2) throws psc_ao, psc_be {
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
                final psc_eh a3 = a(a2, a, array[i], array);
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
        throw new psc_ao("A JSAFE_MAC object of " + str + " is not available on any of the devices. (" + str2 + ")");
    }
    
    private static psc_eh a(String[] array, final String[] array2, final psc_bd psc_bd, final psc_bd[] array3) throws psc_be {
        int length = array.length;
        if (length == 4) {
            final String[] array4 = new String[3];
            for (int i = 0; i < 3; ++i) {
                array4[i] = array[i + 1];
            }
            array = array4;
            length = 3;
        }
        if (length < 2 || length > 3) {
            return null;
        }
        final Object[] a = psc_bd.a(array, null, 3, psc_eh.c, psc_eh.d, "psc_eh", array2, array3);
        if (a == null) {
            return null;
        }
        if (a[0] instanceof psc_eh) {
            return (psc_eh)a[0];
        }
        ((psc_ei)a[0]).a(psc_ba.b(array[0]));
        ((psc_az)a[1]).a(psc_ba.b(array[1]));
        switch (length) {
            case 2: {
                return new psc_ej((psc_ei)a[0], (psc_az)a[1]);
            }
            case 3: {
                ((psc_dn)a[2]).a(psc_ba.b(array[2]));
                return new psc_ek((psc_ei)a[0], (psc_az)a[1], (psc_dn)a[2]);
            }
            default: {
                return null;
            }
        }
    }
    
    public abstract byte[] g() throws psc_ao;
    
    public String h() {
        return this.a;
    }
    
    public String[] i() {
        final String[] array = new String[this.b.length];
        for (int i = 0; i < this.b.length; ++i) {
            array[i] = this.b[i];
        }
        return array;
    }
    
    public abstract String j();
    
    public abstract String k();
    
    public abstract int[] l();
    
    public abstract int m();
    
    public psc_dc n() {
        final String j = this.j();
        final String h = this.h();
        try {
            return psc_dc.a(j, h);
        }
        catch (psc_ap psc_ap) {
            return null;
        }
    }
    
    public abstract void b(final psc_dc p0, final SecureRandom p1) throws psc_en, psc_bf;
    
    public abstract void o() throws psc_en;
    
    public abstract void a(final byte[] p0, final int p1, final int p2) throws psc_en;
    
    public byte[] p() {
        return null;
    }
    
    public void b(final byte[] array, final int n, final int n2) {
    }
    
    public void a(final SecureRandom secureRandom) throws psc_en {
        throw new psc_en("Salt is not used in the current");
    }
    
    public byte[] q() throws psc_en {
        final byte[] array = new byte[this.m()];
        this.c(array, 0);
        return array;
    }
    
    public abstract int c(final byte[] p0, final int p1) throws psc_en;
    
    public abstract void c(final psc_dc p0, final SecureRandom p1) throws psc_en, psc_bf;
    
    public abstract void r() throws psc_en;
    
    public abstract void c(final byte[] p0, final int p1, final int p2) throws psc_en;
    
    public abstract boolean d(final byte[] p0, final int p1, final int p2) throws psc_en;
    
    protected void a(final psc_eh psc_eh) {
        this.a = psc_eh.a;
        this.b = new String[psc_eh.b.length];
        for (int i = 0; i < psc_eh.b.length; ++i) {
            this.b[i] = psc_eh.b[i];
        }
    }
    
    public void y() {
        super.y();
    }
    
    static {
        psc_eh.c = new boolean[] { true, true, true };
        psc_eh.d = new String[] { "com.rsa.jsafe.JA_AlgaeDigestMAC", "com.rsa.jsafe.JA_AlgaeDigest", "com.rsa.jsafe.JA_PasswordStandard" };
    }
}
