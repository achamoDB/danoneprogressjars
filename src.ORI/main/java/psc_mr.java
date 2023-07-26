import java.security.SecureRandom;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_mr extends psc_ms implements Cloneable, Serializable
{
    public static final int a = 0;
    private byte[] b;
    private byte[] c;
    private int d;
    private int e;
    private psc_u f;
    private psc_cr g;
    private psc_n h;
    protected int i;
    private psc_n j;
    protected int k;
    
    public psc_mr() {
        this.e = 0;
    }
    
    public psc_mr(final psc_ah psc_ah) {
        this.e = 0;
        this.a(psc_ah);
    }
    
    public psc_mr(final byte[] array, final int n, final int n2) throws psc_g {
        this(array, n, n2, null);
    }
    
    public psc_mr(final byte[] array, final int n, final int n2, final psc_ah psc_ah) throws psc_g {
        this.e = 0;
        if (array == null) {
            throw new psc_g("Encoding is null.");
        }
        this.a(array, n, n2);
        this.a(psc_ah);
    }
    
    public static int a(final byte[] array, final int n) throws psc_g {
        if (array == null) {
            throw new psc_g("Encoding is null.");
        }
        try {
            return n + 1 + psc_o.a(array, n + 1) + psc_o.b(array, n + 1);
        }
        catch (psc_m psc_m) {
            throw new psc_g("Could not read the BER encoding.");
        }
    }
    
    private void a(final byte[] array, final int n, final int n2) throws psc_g {
        if (array == null) {
            throw new psc_g("Encoding is null");
        }
        this.f();
        final psc_i[] b = b(array, n, n2);
        this.b = new byte[b[1].d];
        System.arraycopy(b[1].b, b[1].c, this.b, 0, b[1].d);
        this.d(b[1].b, b[1].c, 0);
        super.f = new byte[b[2].d];
        System.arraycopy(b[2].b, b[2].c, super.f, 0, b[2].d);
        super.h = new byte[b[3].d];
        System.arraycopy(b[3].b, b[3].c, super.h, 0, b[3].d);
    }
    
    protected static psc_i[] b(final byte[] array, final int n, final int n2) throws psc_g {
        if (array == null) {
            throw new psc_g("Encoding is null.");
        }
        final psc_i[] array2 = { new psc_h(n2), new psc_k(12288), new psc_k(12288), new psc_k(768), new psc_j() };
        try {
            psc_l.a(array, n, array2);
        }
        catch (psc_m psc_m) {
            throw new psc_g("Could not BER decode the request.");
        }
        return array2;
    }
    
    public int c(final int n) {
        return this.a(n);
    }
    
    private int a(final int i) {
        this.i = i;
        if (!super.i) {
            return 0;
        }
        if (this.c == null) {
            this.d = this.d(i);
        }
        if (this.d == 0) {
            return 0;
        }
        if (super.h == null || super.f == null) {
            return 0;
        }
        try {
            this.h = new psc_n(new psc_i[] { new psc_h(i, true, 0), new psc_k(12288, true, 0, null, 0, this.d), new psc_k(12288, true, 0, null, 0, super.f.length), new psc_k(768, true, 0, null, 0, super.h.length), new psc_j() });
            return this.h.a();
        }
        catch (psc_m psc_m) {
            return 0;
        }
    }
    
    public int c(final byte[] array, final int n, final int n2) throws psc_g {
        if (array == null) {
            throw new psc_g("Specified array is null.");
        }
        if (!super.i) {
            throw new psc_g("Could not encode, missing data.");
        }
        final int n3 = 0;
        int n4;
        try {
            if (this.h == null || n2 != this.i) {
                this.a(n2);
            }
            n4 = n3 + this.h.a(array, n);
            this.h = null;
        }
        catch (psc_m psc_m) {
            this.h = null;
            throw new psc_g("Could not encode: " + psc_m.getMessage());
        }
        int n5;
        if (this.c != null && this.d != 0 && this.k == 0) {
            System.arraycopy(this.c, 0, array, n + n4, this.d);
            n5 = n4 + this.d;
        }
        else {
            final int e = this.e(array, n + n4, 0);
            if (e == 0) {
                throw new psc_g("Could not encode, missing data.");
            }
            n5 = n4 + e;
        }
        System.arraycopy(super.f, 0, array, n + n5, super.f.length);
        final int n6 = n5 + super.f.length;
        System.arraycopy(super.h, 0, array, n + n6, super.h.length);
        return n6 + super.h.length;
    }
    
    public void d(final byte[] array, final int n, final int n2) throws psc_g {
        if (array == null) {
            throw new psc_g("Encoding is null.");
        }
        this.n();
        this.e();
        final psc_h psc_h = new psc_h(n2);
        final psc_j psc_j = new psc_j();
        final psc_p psc_p = new psc_p(0);
        final psc_k psc_k = new psc_k(12288);
        final psc_k psc_k2 = new psc_k(12288);
        final psc_k psc_k3 = new psc_k(8401152);
        final psc_i[] array2 = { psc_h, psc_p, psc_k, psc_k2, psc_k3, psc_j };
        try {
            psc_l.a(array, n, array2);
        }
        catch (psc_m psc_m) {
            throw new psc_g("Could not BER decode the request info.");
        }
        this.a(-1, psc_p.b, psc_p.c, psc_p.d);
        try {
            this.f = new psc_u(psc_k.b, psc_k.c, 0);
        }
        catch (psc_v psc_v) {
            throw new psc_g("Could not read the SubjectName: " + psc_v.getMessage());
        }
        this.b(psc_k2.b, psc_k2.c);
        try {
            this.g = new psc_cr(psc_k3.b, psc_k3.c, 8388608);
        }
        catch (psc_f0 psc_f0) {
            throw new psc_g("Could not read the Attributes: " + psc_f0.getMessage());
        }
    }
    
    protected void a(int n, final byte[] array, final int n2, final int n3) throws psc_g {
        if (n == -1) {
            if (array == null || n3 > 4) {
                throw new psc_g("Invalid PKCS #10 Cert Request version.");
            }
            n = 0;
            for (int i = n2; i < n2 + n3; ++i) {
                n = (n << 8 | (array[n2] & 0xFF));
            }
        }
        if (n != 0) {
            throw new psc_g("Invalid PKCS #10 Cert Request version.");
        }
    }
    
    public int d(final int n) {
        return this.b(n);
    }
    
    private int b(final int k) {
        this.k = k;
        if (super.e == null || this.f == null) {
            return 0;
        }
        final int d = this.f.d(0);
        int b = 2;
        if (this.g != null) {
            b = this.g.b(8388608);
        }
        try {
            this.j = new psc_n(new psc_i[] { new psc_h(k, true, 0), new psc_p(0, true, 0, this.e), new psc_k(12288, true, 0, null, 0, d), new psc_k(12288, true, 0, null, 0, super.e.length), new psc_k(8401152, true, 0, null, 0, b), new psc_j() });
            return this.j.a();
        }
        catch (psc_m psc_m) {
            return 0;
        }
    }
    
    public int e(final byte[] array, int n, final int n2) throws psc_g {
        if (array == null) {
            throw new psc_g("Specified array is null.");
        }
        if ((this.j == null || n2 != this.k) && this.b(n2) == 0) {
            throw new psc_g("Could not encode, missing data.");
        }
        final int n3 = 0;
        int n4;
        try {
            n4 = n3 + this.j.a(array, n);
            this.j = null;
        }
        catch (psc_m psc_m) {
            this.j = null;
            throw new psc_g("Could not encode Cert Request: " + psc_m.getMessage());
        }
        int n5;
        try {
            n5 = n4 + this.f.a(array, n + n4, 0);
        }
        catch (psc_v psc_v) {
            throw new psc_g("Cannot build Cert Request Info: " + psc_v.getMessage());
        }
        System.arraycopy(super.e, 0, array, n + n5, super.e.length);
        int n6 = n5 + super.e.length;
        if (this.g == null) {
            n += n6;
            array[n] = -96;
            array[n + 1] = 0;
            n6 += 2;
        }
        else {
            try {
                n6 += this.g.b(array, n + n6, 8388608);
            }
            catch (psc_f0 psc_f0) {
                throw new psc_g("Could not encode CertRequest: " + psc_f0.getMessage());
            }
        }
        return n6;
    }
    
    public byte[] a() throws psc_g {
        if (super.h == null) {
            throw new psc_g("Object not signed.");
        }
        final psc_d4 psc_d4 = new psc_d4(0);
        final psc_i[] array = { psc_d4 };
        try {
            psc_l.a(super.h, 0, array);
        }
        catch (psc_m psc_m) {
            throw new psc_g("Cannot extract the signature.");
        }
        final byte[] array2 = new byte[psc_d4.d];
        System.arraycopy(psc_d4.b, psc_d4.c, array2, 0, psc_d4.d);
        return array2;
    }
    
    public void e(final int n) throws psc_g {
        this.n();
        this.e();
        this.a(n, null, 0, 0);
    }
    
    public int b() {
        return this.e;
    }
    
    public void a(final psc_u psc_u) throws psc_g {
        this.n();
        this.e();
        if (psc_u == null) {
            throw new psc_g("Cannot set the cert with the given subjectName.");
        }
        try {
            this.f = (psc_u)psc_u.clone();
        }
        catch (CloneNotSupportedException ex) {
            throw new psc_g("Cannot set the cert with the given subjectName.");
        }
    }
    
    public psc_u c() {
        if (this.f == null) {
            return null;
        }
        try {
            return (psc_u)this.f.clone();
        }
        catch (CloneNotSupportedException ex) {
            return null;
        }
    }
    
    public void a(final psc_fz psc_fz) {
        if (psc_fz != null) {
            if (this.g == null) {
                this.g = new psc_cr();
            }
            this.g.a(psc_fz);
        }
    }
    
    public void a(final psc_cr psc_cr) throws psc_g {
        this.n();
        this.e();
        try {
            if (psc_cr != null) {
                this.g = (psc_cr)psc_cr.clone();
            }
            else {
                this.g = null;
            }
        }
        catch (CloneNotSupportedException ex) {
            throw new psc_g("Could not set the request object with the given attributes.");
        }
    }
    
    public psc_cr d() {
        if (this.g == null) {
            return null;
        }
        try {
            return (psc_cr)this.g.clone();
        }
        catch (CloneNotSupportedException ex) {
            return null;
        }
    }
    
    public void a(final String s, final String s2, final psc_dt psc_dt, final SecureRandom secureRandom) throws psc_g {
        if (s == null || s2 == null || psc_dt == null) {
            throw new psc_g("Specified values are null.");
        }
        this.n();
        if (this.c == null) {
            this.d = this.d(0);
            this.c = new byte[this.d];
            this.d = this.e(this.c, 0, 0);
        }
        final byte[] a = this.a(s, s2, psc_dt, secureRandom, this.c, 0, this.d);
        try {
            super.h = psc_l.a(new psc_i[] { new psc_d4(0, true, 0, a, 0, a.length, a.length * 8, false) });
        }
        catch (psc_m psc_m) {
            this.n();
            throw new psc_g("Cannot sign the cert as presently set.");
        }
    }
    
    public boolean a(final String s, final SecureRandom secureRandom) throws psc_g {
        if (s == null) {
            throw new psc_g("Specified values are null.");
        }
        if (this.b == null) {
            if (this.c == null) {
                throw new psc_g("Cannot verify the signature, not all components are set.");
            }
            this.b = new byte[this.d];
            System.arraycopy(this.c, 0, this.b, 0, this.d);
        }
        if (super.h == null || super.f == null) {
            throw new psc_g("Cannot verify the signature, not all components are set.");
        }
        final byte[] a = this.a();
        return this.a(s, secureRandom, this.b, 0, this.b.length, a, 0, a.length);
    }
    
    protected void e() {
        this.h = null;
        this.j = null;
        this.c = null;
        this.d = 0;
    }
    
    protected void f() {
        super.f();
        this.e();
        this.b = null;
        this.e = 0;
        this.f = null;
        this.g = null;
    }
}
