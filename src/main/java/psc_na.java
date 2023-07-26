import java.security.SecureRandom;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public abstract class psc_na implements Cloneable, Serializable
{
    public static final int a = 0;
    public static final int b = 1;
    public static final int c = 2;
    public static final int d = 3;
    protected byte[] e;
    protected int f;
    protected byte[] g;
    protected String h;
    protected String[] i;
    private psc_ah j;
    
    public psc_na() {
        this.f = -1;
        this.j = null;
    }
    
    public final void a(final psc_ah j) {
        this.j = j;
    }
    
    public final psc_ah l() {
        return this.j;
    }
    
    public String m() throws psc_g {
        if (this.e == null) {
            throw new psc_g("Object not set with signature algorithm.");
        }
        return psc_s.b(this.e, 0, this.e.length, 1);
    }
    
    public byte[] n() throws psc_g {
        if (this.e == null) {
            throw new psc_g("Object not set with signature algorithm.");
        }
        return this.e.clone();
    }
    
    public abstract byte[] c() throws psc_g;
    
    public String o() throws psc_g {
        if (this.h == null) {
            throw new psc_g("Object not set with a device.");
        }
        return this.h;
    }
    
    public String[] p() throws psc_g {
        if (this.i == null) {
            throw new psc_g("Object not set with a device.");
        }
        final String[] array = new String[this.i.length];
        for (int i = 0; i < this.i.length; ++i) {
            array[i] = this.i[i];
        }
        return array;
    }
    
    public void f(final int f) {
        this.f = f;
    }
    
    public int q() {
        return this.f;
    }
    
    public String r() {
        switch (this.f) {
            case 0: {
                return "RSAWithSHA1PKCS";
            }
            case 1: {
                return "RSAWithSHA1ISO_OIW";
            }
            case 2: {
                return "DSAWithSHA1X930";
            }
            case 3: {
                return "DSAWithSHA1X957";
            }
            default: {
                return null;
            }
        }
    }
    
    public abstract void a(final String p0, final String p1, final psc_dt p2, final SecureRandom p3) throws psc_g;
    
    public void a(final byte[] array, final int n, final String s, final psc_dt psc_dt, final SecureRandom secureRandom) throws psc_g {
        if (array == null || s == null || psc_dt == null) {
            throw new psc_g("Specified values are null.");
        }
        try {
            this.a(psc_s.b(array, n, 1 + psc_o.a(array, n + 1) + psc_o.b(array, n + 1), 1), s, psc_dt, secureRandom);
        }
        catch (psc_m psc_m) {
            throw new psc_g("Cannot sign cert:" + psc_m.getMessage());
        }
    }
    
    public abstract boolean a(final String p0, final psc_al p1, final SecureRandom p2) throws psc_g;
    
    public boolean a(final String s, final byte[] array, final int n, final SecureRandom secureRandom) throws psc_g {
        if (s == null || array == null) {
            throw new psc_g("Specified values are null.");
        }
        try {
            return this.a(s, psc_al.a(array, n, s), secureRandom);
        }
        catch (psc_ap psc_ap) {
            throw new psc_g("Cannot verify: " + psc_ap.getMessage());
        }
    }
    
    public boolean a(final String s, final psc_f psc_f, final SecureRandom secureRandom) throws psc_g {
        if (s == null || psc_f == null) {
            throw new psc_g("Specified values are null.");
        }
        return this.a(s, psc_f.b(s), secureRandom);
    }
    
    protected byte[] a(final String s, final String s2, final psc_dt psc_dt, final SecureRandom secureRandom, final byte[] array, final int n, final int n2) throws psc_g {
        if (s == null || s2 == null || psc_dt == null || array == null) {
            throw new psc_g("Specified values are null.");
        }
        psc_eo a = null;
        try {
            a = psc_eo.a(s, s2);
            if (this.j == null) {
                a.a(psc_dt, secureRandom);
            }
            else {
                a.a(psc_dt, null, secureRandom, this.j.a());
            }
            a.d(array, n, n2);
            return a.t();
        }
        catch (psc_ap psc_ap) {
            throw new psc_g("Could not sign the CRL: " + psc_ap.getMessage());
        }
        finally {
            if (a != null) {
                a.y();
            }
        }
    }
    
    protected boolean a(final String s, final psc_al psc_al, final SecureRandom secureRandom, final byte[] array, final int n, final int n2, final byte[] array2, final int n3, final int n4) throws psc_g {
        if (s == null || psc_al == null || array == null || array2 == null) {
            throw new psc_g("Specified values are null.");
        }
        psc_eo a = null;
        try {
            a = psc_eo.a(this.e, 0, s);
            if (this.j == null) {
                a.a(psc_al, secureRandom);
            }
            else {
                a.a(psc_al, null, secureRandom, this.j.a());
            }
            a.e(array, n, n2);
            return a.f(array2, n3, n4);
        }
        catch (psc_ap psc_ap) {
            throw new psc_g("Could not verify the CRL signature: " + psc_ap.getMessage());
        }
        finally {
            if (a != null) {
                a.y();
            }
        }
    }
    
    protected void s() {
        this.g = null;
        this.h = null;
        this.i = null;
    }
    
    protected void k() {
        this.s();
        this.e = null;
    }
}
