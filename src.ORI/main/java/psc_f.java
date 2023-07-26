import java.security.SecureRandom;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public abstract class psc_f implements Cloneable, Serializable
{
    public static final int a = 0;
    public static final int b = 1;
    public static final int c = 2;
    public static final int d = 3;
    protected byte[] e;
    protected byte[] f;
    protected int g;
    protected byte[] h;
    protected String i;
    protected String[] j;
    private psc_ah k;
    
    public psc_f() {
        this.g = -1;
        this.k = null;
    }
    
    public final void a(final psc_ah k) {
        this.k = k;
    }
    
    public final psc_ah q() {
        return this.k;
    }
    
    public String r() throws psc_g {
        try {
            if (this.f == null) {
                throw new psc_g("Object not set with signature algorithm.");
            }
            return psc_q.b(this.f, 0, 1, null);
        }
        catch (psc_m psc_m) {
            throw new psc_g("Invalid Signature Algorithm.(" + psc_m.getMessage() + ")");
        }
    }
    
    public byte[] s() throws psc_g {
        if (this.f == null) {
            throw new psc_g("Object not set with signature algorithm.");
        }
        return this.f.clone();
    }
    
    public abstract byte[] d() throws psc_g;
    
    public String t() throws psc_g {
        if (this.i == null) {
            throw new psc_g("Object not set with a device.");
        }
        return this.i;
    }
    
    public String[] u() throws psc_g {
        if (this.j == null) {
            throw new psc_g("Object not set with a device.");
        }
        final String[] array = new String[this.j.length];
        for (int i = 0; i < this.j.length; ++i) {
            array[i] = this.j[i];
        }
        return array;
    }
    
    public void f(final int g) {
        this.g = g;
    }
    
    public int v() {
        return this.g;
    }
    
    public String a(final String s) {
        if (s == null) {
            return null;
        }
        switch (this.g) {
            case 0: {
                if (s.equals("SHA1/RSA/PKCS1Block01Pad")) {
                    return "RSAWithSHA1PKCS";
                }
                return null;
            }
            case 1: {
                if (s.equals("SHA1/RSA/PKCS1Block01Pad")) {
                    return "RSAWithSHA1ISO_OIW";
                }
                return null;
            }
            case 2: {
                if (s.equals("SHA1/DSA") || s.equals("SHA1/DSA/NoPad")) {
                    return "DSAWithSHA1X930";
                }
                return null;
            }
            case 3: {
                if (s.equals("SHA1/DSA") || s.equals("SHA1/DSA/NoPad")) {
                    return "DSAWithSHA1X957";
                }
                return null;
            }
            default: {
                if (s.equals("SHA1/DSA")) {
                    return "SHA1/DSA/NoPad";
                }
                return null;
            }
        }
    }
    
    public void a(final psc_al psc_al) throws psc_g {
        if (psc_al == null) {
            throw new psc_g("Public key is null.");
        }
        this.y();
        try {
            String string;
            if (this.g == 3 && psc_al.l().compareTo("DSA") == 0) {
                string = "DSAPublicKeyX957BER";
            }
            else {
                string = psc_al.l() + "PublicKeyBER";
            }
            this.e = psc_al.b(string)[0];
        }
        catch (psc_ap psc_ap) {
            throw new psc_g("Could not read the public key.");
        }
    }
    
    public void f(final byte[] array, final int n) throws psc_g {
        if (array == null) {
            throw new psc_g("Public key encoding is null.");
        }
        this.y();
        psc_al a = null;
        try {
            a = psc_al.a(array, n, "Java");
            this.a(a);
        }
        catch (psc_ap psc_ap) {
            throw new psc_g("Could not read the public key.");
        }
        finally {
            if (a != null) {
                a.y();
            }
        }
    }
    
    public psc_al b(final String s) throws psc_g {
        if (s == null) {
            throw new psc_g("Device is null.");
        }
        if (this.e == null) {
            throw new psc_g("Object not set with public key.");
        }
        try {
            return psc_al.a(this.e, 0, s);
        }
        catch (psc_ap psc_ap) {
            throw new psc_g("Cannot retrieve the public key: " + psc_ap.getMessage());
        }
    }
    
    public byte[] w() throws psc_g {
        if (this.e == null) {
            throw new psc_g("Object not set with public key.");
        }
        return this.e.clone();
    }
    
    public byte[] x() {
        try {
            final byte[][] m = this.b(this.i).m();
            final psc_da a = psc_da.a("MD5", this.i);
            a.i();
            for (int i = 0; i < m.length; ++i) {
                a.a(m[i], 0, m[i].length);
            }
            return a.j();
        }
        catch (psc_ap psc_ap) {}
        catch (psc_g psc_g) {}
        return null;
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
            if (this.k == null) {
                a.a(psc_dt, null, secureRandom, null);
            }
            else {
                a.a(psc_dt, null, secureRandom, this.k.a());
            }
            a.d(array, n, n2);
            return a.t();
        }
        catch (psc_ap psc_ap) {
            throw new psc_g("Could not sign the certificate: " + psc_ap.getMessage());
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
            a = psc_eo.a(this.f, 0, s);
            if (this.k == null) {
                a.a(psc_al, null, secureRandom, null);
            }
            else {
                a.a(psc_al, null, secureRandom, this.k.a());
            }
            a.e(array, n, n2);
            return a.f(array2, n3, n4);
        }
        catch (psc_ap psc_ap) {
            throw new psc_g("Could not verify the certificate: " + psc_ap.getMessage());
        }
        finally {
            if (a != null) {
                a.y();
            }
        }
    }
    
    protected void y() {
        this.h = null;
        this.i = null;
        this.j = null;
    }
    
    protected void p() {
        this.y();
        this.f = null;
        this.e = null;
    }
}
