import java.security.SecureRandom;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public abstract class psc_ms implements Cloneable, Serializable
{
    public static final int a = 0;
    public static final int b = 1;
    public static final int c = 2;
    public static final int d = 3;
    protected byte[] e;
    protected byte[] f;
    protected int g;
    protected byte[] h;
    protected boolean i;
    protected String j;
    protected String[] k;
    private psc_ah l;
    
    public psc_ms() {
        this.g = -1;
        this.l = null;
    }
    
    public final void a(final psc_ah l) {
        this.l = l;
    }
    
    public final psc_ah g() {
        return this.l;
    }
    
    public String h() throws psc_g {
        if (this.f == null) {
            throw new psc_g("Object not set with signature algorithm.");
        }
        return psc_s.b(this.f, 0, this.f.length, 1);
    }
    
    public byte[] i() throws psc_g {
        if (this.f == null) {
            throw new psc_g("Object not set with signature algorithm.");
        }
        return this.f.clone();
    }
    
    public abstract byte[] a() throws psc_g;
    
    public String j() throws psc_g {
        if (this.j == null) {
            throw new psc_g("Object not set with a device.");
        }
        return this.j;
    }
    
    public String[] k() throws psc_g {
        if (this.k == null) {
            throw new psc_g("Object not set with a device.");
        }
        final String[] array = new String[this.k.length];
        for (int i = 0; i < this.k.length; ++i) {
            array[i] = this.k[i];
        }
        return array;
    }
    
    public void f(final int g) {
        this.g = g;
    }
    
    public int l() {
        return this.g;
    }
    
    public String m() {
        switch (this.g) {
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
    
    public void a(final psc_al psc_al) throws psc_g {
        this.n();
        if (psc_al == null) {
            throw new psc_g("Public key is null.");
        }
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
    
    public psc_al a(final String s) throws psc_g {
        if (this.e == null) {
            throw new psc_g("Object not set with public key.");
        }
        if (s == null) {
            throw new psc_g("Device is null.");
        }
        try {
            return psc_al.a(this.e, 0, s);
        }
        catch (psc_ap psc_ap) {
            throw new psc_g("Cannot retrieve the public key: " + psc_ap.getMessage());
        }
    }
    
    public void b(final byte[] array, final int n) throws psc_g {
        this.n();
        if (array == null) {
            throw new psc_g("Public key encoding is null.");
        }
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
    
    public abstract void a(final String p0, final String p1, final psc_dt p2, final SecureRandom p3) throws psc_g;
    
    public abstract boolean a(final String p0, final SecureRandom p1) throws psc_g;
    
    protected byte[] a(final String s, final String s2, final psc_dt psc_dt, final SecureRandom secureRandom, final byte[] array, final int n, final int n2) throws psc_g {
        if (s == null || s2 == null || psc_dt == null || secureRandom == null || array == null) {
            throw new psc_g("Specified values are null.");
        }
        psc_eo a = null;
        try {
            this.i = true;
            a = psc_eo.a(s, s2);
            if (this.l == null) {
                a.a(psc_dt, null, secureRandom, null);
            }
            else {
                a.a(psc_dt, null, secureRandom, this.l.a());
            }
            this.f = a.b(this.m(), false);
            a.d(array, n, n2);
            return a.t();
        }
        catch (psc_ap psc_ap) {
            this.i = false;
            throw new psc_g("Could not sign the request: " + psc_ap.getMessage());
        }
        finally {
            if (a != null) {
                a.y();
            }
        }
    }
    
    protected boolean a(final String s, final SecureRandom secureRandom, final byte[] array, final int n, final int n2, final byte[] array2, final int n3, final int n4) throws psc_g {
        if (s == null || secureRandom == null || array == null || array2 == null) {
            throw new psc_g("Specified values are null.");
        }
        psc_al a = null;
        psc_eo a2 = null;
        Label_0163: {
            try {
                a = psc_al.a(this.e, 0, s);
                a2 = psc_eo.a(this.f, 0, s);
                if (this.l == null) {
                    a2.a(a, null, secureRandom, null);
                }
                else {
                    a2.a(a, null, secureRandom, this.l.a());
                }
                a2.e(array, n, n2);
                final boolean f = a2.f(array2, n3, n4);
                break Label_0163;
            }
            catch (psc_ap psc_ap) {
                throw new psc_g("Could not verify the request: " + psc_ap.getMessage());
            }
            finally {
                if (a != null) {
                    a.y();
                }
                Label_0210: {
                    if (a2 != null) {
                        a2.y();
                        break Label_0210;
                    }
                    break Label_0210;
                }
                while (true) {}
                // iftrue(Label_0185:, a2 == null)
                // iftrue(Label_0175:, a == null)
                final boolean f;
                while (true) {
                    while (true) {
                        while (true) {
                            a2.y();
                            return f;
                            continue;
                        }
                        a.y();
                        continue;
                    }
                    continue;
                }
                Label_0185: {
                    return f;
                }
            }
        }
    }
    
    protected void n() {
        this.h = null;
        this.f = null;
        this.i = false;
        this.j = null;
        this.k = null;
    }
    
    protected void f() {
        this.n();
        this.e = null;
    }
}
