import java.security.SecureRandom;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public abstract class psc_iw extends psc_an implements Cloneable, Serializable
{
    private String a;
    private String[] b;
    private static final boolean[] c;
    private static final String[] d;
    private static final String e = "psc_iw";
    
    public static psc_iw a(final byte[] array, final int n, final String s) throws psc_ao, psc_be {
        String b;
        try {
            b = psc_q.b(array, n, 5, null);
        }
        catch (psc_m psc_m) {
            throw new psc_ao("Could not read BER data.(" + psc_m.getMessage() + ")");
        }
        final psc_iw a = a(b, s);
        a.a(array, n);
        return a;
    }
    
    public static int b(final byte[] array, final int n) throws psc_ao {
        try {
            return psc_q.a(array, n, 5);
        }
        catch (psc_m psc_m) {
            throw new psc_ao("Could not read BER data.(" + psc_m.getMessage() + ")");
        }
    }
    
    void a(final byte[] array, final int n) throws psc_ao, psc_be {
        throw new psc_ao("Cannot instantiate, unknown BER algorithm ID.");
    }
    
    public static psc_iw a(final String str, final String str2) throws psc_ao, psc_be {
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
                final psc_iw a3 = a(a2, a, array[i], array);
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
        throw new psc_ao("A JSAFE_KeyAgree object of " + str + " is not available on any of the devices. (" + str2 + ")");
    }
    
    private static psc_iw a(final String[] array, final String[] array2, final psc_bd psc_bd, final psc_bd[] array3) throws psc_be {
        if (array.length != 1) {
            return null;
        }
        final Object[] a = psc_bd.a(array, null, 1, psc_iw.c, psc_iw.d, "psc_iw", array2, array3);
        if (a == null) {
            return null;
        }
        if (a[0] instanceof psc_iw) {
            return (psc_iw)a[0];
        }
        ((psc_ix)a[0]).a(psc_ba.b(array[0]));
        return new psc_iy((psc_ix)a[0]);
    }
    
    public abstract byte[] e() throws psc_ao;
    
    public String f() {
        return this.a;
    }
    
    public String[] g() {
        final String[] array = new String[this.b.length];
        for (int i = 0; i < this.b.length; ++i) {
            array[i] = this.b[i];
        }
        return array;
    }
    
    public abstract String h();
    
    public abstract psc_el i() throws psc_en;
    
    public psc_em j() {
        final String h = this.h();
        try {
            return psc_em.a(h, this.a);
        }
        catch (psc_ao psc_ao) {
            return null;
        }
        catch (psc_be psc_be) {
            return null;
        }
    }
    
    public abstract int k();
    
    public abstract void a(final SecureRandom p0) throws psc_en;
    
    public abstract void a(final psc_el p0, final SecureRandom p1) throws psc_be, psc_en;
    
    public abstract void a(final psc_el p0, final psc_al p1, final SecureRandom p2) throws psc_be, psc_bf, psc_en;
    
    public abstract void a(final psc_al p0, final SecureRandom p1) throws psc_bf, psc_be, psc_en;
    
    public abstract void a(final psc_el p0, final psc_dt p1, final SecureRandom p2) throws psc_be, psc_bf, psc_en;
    
    public abstract void a(final psc_dt p0, final SecureRandom p1) throws psc_bf, psc_be, psc_en;
    
    public abstract void l() throws psc_en;
    
    public byte[] m() throws psc_en {
        final int k = this.k();
        final byte[] array = new byte[k];
        final int c = this.c(array, 0);
        if (c == k) {
            return array;
        }
        final byte[] array2 = new byte[c];
        for (int i = 0; i < c; ++i) {
            array2[i] = array[i];
            array[i] = 0;
        }
        return array2;
    }
    
    public abstract int c(final byte[] p0, final int p1) throws psc_en;
    
    public abstract psc_al n() throws psc_en;
    
    public byte[] a(final byte[] array, final int n, final int n2) throws psc_en {
        final int k = this.k();
        final byte[] array2 = new byte[k];
        final int a = this.a(array, n, n2, array2, 0);
        if (a == k) {
            return array2;
        }
        final byte[] array3 = new byte[a];
        for (int i = 0; i < a; ++i) {
            array3[i] = array2[i];
            array2[i] = 0;
        }
        return array3;
    }
    
    public abstract int a(final byte[] p0, final int p1, final int p2, final byte[] p3, final int p4) throws psc_en;
    
    public byte[] o() throws psc_en {
        final int k = this.k();
        final byte[] array = new byte[k];
        final int d = this.d(array, 0);
        if (d == k) {
            return array;
        }
        final byte[] array2 = new byte[d];
        for (int i = 0; i < d; ++i) {
            array2[i] = array[i];
            array[i] = 0;
        }
        return array2;
    }
    
    public abstract int d(final byte[] p0, final int p1) throws psc_en;
    
    public byte[] a(final psc_al psc_al) throws psc_en, psc_bf {
        final int k = this.k();
        final byte[] array = new byte[k];
        final int a = this.a(psc_al, array, 0);
        if (a == k) {
            return array;
        }
        final byte[] array2 = new byte[a];
        for (int i = 0; i < a; ++i) {
            array2[i] = array[i];
            array[i] = 0;
        }
        return array2;
    }
    
    public abstract int a(final psc_al p0, final byte[] p1, final int p2) throws psc_en, psc_bf;
    
    public byte[] p() throws psc_en {
        final int k = this.k();
        final byte[] array = new byte[k];
        final int e = this.e(array, 0);
        if (e == k) {
            return array;
        }
        final byte[] array2 = new byte[e];
        for (int i = 0; i < e; ++i) {
            array2[i] = array[i];
            array[i] = 0;
        }
        return array2;
    }
    
    public abstract int e(final byte[] p0, final int p1) throws psc_en;
    
    public abstract psc_dt q() throws psc_en;
    
    protected void a(final psc_iw psc_iw) {
        this.a = psc_iw.a;
        this.b = new String[psc_iw.b.length];
        for (int i = 0; i < psc_iw.b.length; ++i) {
            this.b[i] = psc_iw.b[i];
        }
    }
    
    public void y() {
        super.y();
    }
    
    static {
        c = new boolean[] { true };
        d = new String[] { "com.rsa.jsafe.JA_AlgaeKeyAgree" };
    }
}
