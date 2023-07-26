import java.security.SecureRandom;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_nr extends psc_m2 implements Cloneable, Serializable
{
    protected int a;
    protected psc_ns b;
    private String c;
    private psc_dc d;
    private byte[] e;
    private char[] f;
    private byte[] g;
    private byte[] h;
    
    public psc_nr(final psc_ah o, final psc_nq n) {
        this.a = -1;
        this.b = new psc_ns();
        super.k = 6;
        super.o = o;
        super.n = n;
    }
    
    public void a(final int a) {
        this.a = a;
    }
    
    public int d() {
        return this.a;
    }
    
    public void b(final byte[] array, final int n, final int n2) {
        if (array != null && n2 > 0 && n >= 0 && n2 + n <= array.length) {
            System.arraycopy(array, n, this.g = new byte[n2], 0, n2);
        }
    }
    
    public byte[] e() {
        if (this.g == null) {
            return null;
        }
        final byte[] array = new byte[this.g.length];
        System.arraycopy(this.g, 0, array, 0, this.g.length);
        return array;
    }
    
    public void c(final byte[] array, final int n, final int n2) {
        if (array != null && n2 > 0 && n >= 0 && n2 + n <= array.length) {
            System.arraycopy(array, n, this.h = new byte[n2], 0, n2);
        }
    }
    
    public byte[] f() {
        if (this.h == null) {
            return null;
        }
        final byte[] array = new byte[this.h.length];
        System.arraycopy(this.h, 0, array, 0, this.h.length);
        return array;
    }
    
    public void a(final String c) throws psc_m3 {
        if (c == null) {
            throw new psc_m3("Algorithm name is null");
        }
        this.c = c;
        try {
            final psc_df a = psc_df.a(c, this.h());
            if (a.j() != null && !a.j().equals("ECB")) {
                if (this.g == null) {
                    if (super.o == null) {
                        throw new psc_m3("CertJ object is null; cannot get Random object.");
                    }
                    a.a(super.o.e());
                }
                else {
                    a.b(this.g, 0, this.g.length);
                }
            }
            if (this.h != null) {
                a.c(this.h, 0, this.h.length);
            }
            final byte[] f = a.f();
            this.b.b(f, 0, f.length);
        }
        catch (psc_ap psc_ap) {
            throw new psc_m3("Could not set algorithm OID: " + psc_ap.getMessage());
        }
        catch (psc_n5 psc_n5) {
            throw new psc_m3("Could not set algorithm OID" + psc_n5.getMessage());
        }
        catch (psc_mv psc_mv) {
            throw new psc_m3("Could not set algorithm OID" + psc_mv.getMessage());
        }
    }
    
    public void d(final byte[] array, final int n, final int n2) throws psc_m3 {
        if (array == null || n2 <= 0) {
            throw new psc_m3("Could not set algorithm OID: OID is null");
        }
        if (n < 0 || n + n2 > array.length) {
            throw new psc_m3("Invalid content-encryption algorithm ID");
        }
        this.b.b(array, n, n2);
    }
    
    public byte[] t() {
        if (this.b == null) {
            return null;
        }
        return this.b.c();
    }
    
    public String u() throws psc_m3 {
        if (this.c != null) {
            return this.c;
        }
        if (this.b == null) {
            return null;
        }
        final byte[] c = this.b.c();
        if (c == null) {
            return null;
        }
        try {
            final psc_h psc_h = new psc_h(0, true, 0);
            final psc_j psc_j = new psc_j();
            final psc_r psc_r = new psc_r(0);
            psc_l.a(c, 0, new psc_i[] { psc_h, psc_r, new psc_k(130816), psc_j });
            return psc_s.b(psc_r.b, psc_r.c, psc_r.d, 6);
        }
        catch (psc_m psc_m) {
            throw new psc_m3("Encryption algorithm is not valid." + psc_m.getMessage());
        }
    }
    
    public void a(final psc_dc psc_dc) throws psc_m3 {
        if (psc_dc == null) {
            throw new psc_m3("Secret key is null.");
        }
        try {
            this.d = (psc_dc)psc_dc.clone();
        }
        catch (CloneNotSupportedException ex) {
            throw new psc_m3("Unable to clone Secret Key");
        }
    }
    
    public psc_dc v() throws psc_m3 {
        if (this.d == null) {
            return null;
        }
        try {
            return (psc_dc)this.d.clone();
        }
        catch (CloneNotSupportedException ex) {
            throw new psc_m3("Unable to clone Secret Key");
        }
    }
    
    public void e(final byte[] array, final int n, final int n2) throws psc_m3 {
        if (array != null && n2 > 0 && n >= 0 && n + n2 <= array.length) {
            if (this.c == null) {
                this.c = this.u();
            }
            if (this.c != null) {
                try {
                    (this.d = psc_dc.a(psc_df.a(this.c, "Java").i(), this.h())).a(array, n, n2);
                    return;
                }
                catch (psc_ap psc_ap) {
                    throw new psc_m3("Invalid Key Data " + psc_ap.getMessage());
                }
                throw new psc_m3("NULL Key Data ");
            }
            System.arraycopy(array, n, this.e = new byte[n2], 0, n2);
            return;
        }
        throw new psc_m3("NULL Key Data ");
    }
    
    public byte[] w() {
        if (this.d == null) {
            return null;
        }
        return this.d.r();
    }
    
    public void a(final char[] array, final int n, final int n2) {
        if (array != null && n2 > 0 && n >= 0 && n + n2 <= array.length) {
            System.arraycopy(array, n, this.f = new char[n2], 0, n2);
        }
    }
    
    public char[] x() throws psc_m3 {
        if (this.f != null) {
            final char[] array = new char[this.f.length];
            System.arraycopy(this.f, 0, array, 0, this.f.length);
            return array;
        }
        if (this.d == null) {
            throw new psc_m3("The SecretKey and Password are not set.");
        }
        try {
            return this.d.j();
        }
        catch (psc_ap psc_ap) {
            throw new psc_m3("The SecretKey is not set with a password.");
        }
    }
    
    public void a(final psc_m2 psc_m2) throws psc_m3 {
        if (psc_m2 == null) {
            throw new psc_m3("content is null.");
        }
        try {
            super.m = (psc_m2)psc_m2.clone();
        }
        catch (CloneNotSupportedException ex) {
            throw new psc_m3("Unable to clone ContentInfo type");
        }
    }
    
    protected int m() throws psc_m3 {
        return this.a();
    }
    
    protected int b(final byte[] array, final int n) throws psc_m3 {
        if (array == null) {
            throw new psc_m3("Cannot write EncryptedData: output array is null.");
        }
        try {
            if (super.q == null) {
                this.m();
            }
            final int a = super.q.a(array, n);
            super.q = null;
            return a;
        }
        catch (psc_m psc_m) {
            super.q = null;
            throw new psc_m3("Unable to DER encode EncryptedData message: " + psc_m.getMessage());
        }
    }
    
    private int a() throws psc_m3 {
        if (super.af == 0) {
            this.b();
        }
        try {
            final psc_h psc_h = new psc_h(10551296, true, 0);
            final psc_j psc_j = new psc_j();
            final psc_p psc_p = new psc_p(0, true, 0, this.a);
            final byte[] array = new byte[this.b.a(0)];
            final psc_k psc_k = new psc_k(0, true, 0, array, 0, this.b.d(array, 0, 0));
            (super.p = new psc_i[4])[0] = psc_h;
            super.p[1] = psc_p;
            super.p[2] = psc_k;
            super.p[3] = psc_j;
            super.q = new psc_n(super.p);
            return super.q.a();
        }
        catch (psc_m psc_m) {
            throw new psc_m3(psc_m.getMessage());
        }
    }
    
    private void b() throws psc_m3 {
        if (this.a == -1) {
            this.a = 0;
        }
        if (super.m == null) {
            throw new psc_m3("There is no content to encrypt.");
        }
        final int j = super.m.j();
        final byte[] array = new byte[9];
        System.arraycopy(psc_m2.i, 0, array, 0, 8);
        array[8] = (byte)j;
        this.b.a(array, 0, 9);
        byte[] array2 = null;
        try {
            final byte[] array3 = new byte[super.m.l()];
            super.m.a(array3, 0);
            final psc_h psc_h = new psc_h(0);
            final psc_j psc_j = new psc_j();
            final psc_r psc_r = new psc_r(16777216);
            final psc_k psc_k = new psc_k(10616576);
            psc_l.a(array3, 0, new psc_i[] { psc_h, psc_r, psc_k, psc_j });
            if (psc_k.a) {
                int n = 1 + psc_o.a(psc_k.b, psc_k.c + 1);
                final int n2 = ++n + psc_o.a(psc_k.b, psc_k.c + n);
                array2 = new byte[psc_k.d - n2];
                System.arraycopy(psc_k.b, psc_k.c + n2, array2, 0, psc_k.d - n2);
            }
        }
        catch (psc_m psc_m) {
            throw new psc_m3("Could not DER encode ContentInfo: " + psc_m.getMessage());
        }
        try {
            if (array2 == null) {
                return;
            }
            final byte[] c = this.b.c();
            if (super.o == null) {
                throw new psc_m3("CertJ object is null; cannot get Random object.");
            }
            final psc_av e = super.o.e();
            final String h = this.h();
            final psc_df a = psc_df.a(c, 0, h);
            final byte[] array4 = new byte[a.a(array2.length)];
            if (this.d == null) {
                if (this.e != null) {
                    (this.d = psc_dc.a(a.i(), h)).a(this.e, 0, this.e.length);
                }
                else {
                    if (this.f == null) {
                        throw new psc_m3("Secret key is not set.");
                    }
                    (this.d = a.o()).a(this.f, 0, this.f.length);
                }
            }
            a.c(this.d, e);
            final int a2 = a.a(array2, 0, array2.length, array4, 0);
            this.b.c(array4, 0, a2 + a.b(array4, a2));
            a.y();
        }
        catch (psc_ap psc_ap) {
            throw new psc_m3("Could not encrypt content: " + psc_ap.getMessage());
        }
        catch (psc_n5 psc_n5) {
            throw new psc_m3("Could not encrypt ContentInfo" + psc_n5.getMessage());
        }
        catch (psc_mv psc_mv) {
            throw new psc_m3("Could not encrypt ContentInfo" + psc_mv.getMessage());
        }
    }
    
    protected boolean k(final byte[] array, final int n, final int n2) throws psc_m3 {
        try {
            final psc_h psc_h = new psc_h(10551296);
            final psc_j psc_j = new psc_j();
            final psc_p psc_p = new psc_p(0);
            final psc_k psc_k = new psc_k(12288, true, 0, super.l, null, 0, 0);
            (super.p = new psc_i[4])[0] = psc_h;
            super.p[1] = psc_p;
            super.p[2] = psc_k;
            super.p[3] = psc_j;
            (super.q = new psc_n(super.p)).c();
            super.q.a(array, n, n2);
            if (super.p[0].g()) {
                return false;
            }
            this.c();
            super.af = 1;
            return true;
        }
        catch (psc_m psc_m) {
            throw new psc_m3("Could not decode message: " + psc_m.getMessage());
        }
    }
    
    private void c() throws psc_m3 {
        try {
            if (this.a == -1) {
                if (super.p[1].f()) {
                    if (super.ab == 1) {
                        final byte[] a = this.a(super.p[1]);
                        this.a = new psc_p(0, true, 0, a, 0, a.length, true).e();
                    }
                    else {
                        this.a = ((psc_p)super.p[1]).e();
                    }
                }
                else {
                    if (super.p[1].b == null) {
                        return;
                    }
                    super.ab = 1;
                    this.c(super.p[1].b, super.p[1].c, super.p[1].d, 0);
                    return;
                }
            }
            if (super.p[2].f()) {
                if (super.ab == 2) {
                    final byte[] a2 = this.a(super.p[2]);
                    this.a(a2, 0, a2.length);
                }
                else {
                    this.a(super.p[2].b, super.p[2].c, super.p[2].d);
                }
            }
            else {
                if (super.p[2].b == null) {
                    return;
                }
                super.ab = 2;
                this.c(super.p[2].b, super.p[2].c, super.p[2].d, super.l);
            }
        }
        catch (Exception ex) {
            throw new psc_m3("Cannot set decoded values." + ex.getMessage());
        }
    }
    
    private void a(final byte[] array, final int n, final int n2) throws psc_m3 {
        if (array == null || n2 == 0) {
            throw new psc_m3("Cannot decode content: data is null.");
        }
        try {
            this.b = new psc_ns(array, n, 0, super.l);
            final byte[] c = this.b.c();
            final byte[] b = this.b.b();
            super.m = psc_m2.a(b, 0, 9, super.o, super.n);
            if (super.o == null) {
                throw new psc_m3("CertJ object is null; cannot get Random object.");
            }
            final psc_av e = super.o.e();
            final byte[] d = this.b.d();
            if (d == null) {
                return;
            }
            final String h = this.h();
            final psc_df a = psc_df.a(c, 0, h);
            final byte[] array2 = new byte[a.a(d.length)];
            if (this.d == null) {
                if (this.e != null) {
                    (this.d = psc_dc.a(a.i(), h)).a(this.e, 0, this.e.length);
                }
                else {
                    if (this.f == null) {
                        throw new psc_m3("Secret key is not set.");
                    }
                    (this.d = a.o()).a(this.f, 0, this.f.length);
                }
            }
            a.d(this.d, e);
            final int b2 = a.b(d, 0, d.length, array2, 0);
            final int n3 = b2 + a.c(array2, b2);
            a.y();
            final byte[] array3 = new byte[n3 + 1 + psc_o.b(n3)];
            if (b[8] == 1) {
                array3[0] = 4;
            }
            else {
                array3[0] = 48;
            }
            System.arraycopy(array2, 0, array3, 1 + psc_o.b(array3, 1, n3), n3);
            final byte[] array4 = new byte[array3.length + 1 + psc_o.b(array3.length)];
            array4[0] = -96;
            System.arraycopy(array3, 0, array4, 1 + psc_o.b(array4, 1, array3.length), array3.length);
            final psc_n psc_n = new psc_n(new psc_i[] { new psc_h(0, true, 0), new psc_r(16777216, true, 0, b, 0, b.length), new psc_k(10616576, true, 0, array4, 0, array4.length), new psc_j() });
            final int a2 = psc_n.a();
            final byte[] array5 = new byte[a2];
            psc_n.a(array5, 0);
            if (super.m.b(array5, 0, a2, super.l) && super.m.n()) {
                return;
            }
        }
        catch (Exception ex) {
            throw new psc_m3("Cannot decode content." + ex.getMessage());
        }
        throw new psc_m3("Cannot decode content.");
    }
    
    protected int m(final byte[] array, final int n, final int n2) throws psc_m3 {
        final int n3 = 0;
        if (array == null) {
            return n3;
        }
        if (super.q == null) {
            throw new psc_m3("Call readInit before readUpdate.");
        }
        try {
            if (super.q.e()) {
                return n3;
            }
            final int a = super.q.a(array, n, n2);
            this.c();
            return a;
        }
        catch (psc_m psc_m) {
            throw new psc_m3("Could not decode message: " + psc_m.getMessage());
        }
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_nr psc_nr = (psc_nr)super.clone();
        if (this.d != null) {
            psc_nr.d = (psc_dc)this.d.clone();
        }
        psc_nr.a = this.a;
        if (this.b != null) {
            psc_nr.b = (psc_ns)this.b.clone();
        }
        psc_nr.c = this.c;
        if (this.f != null) {
            psc_nr.f = new char[this.f.length];
            System.arraycopy(this.f, 0, psc_nr.f, 0, this.f.length);
        }
        if (this.g != null) {
            psc_nr.g = new byte[this.g.length];
            System.arraycopy(this.g, 0, psc_nr.g, 0, this.g.length);
        }
        if (this.h != null) {
            psc_nr.h = new byte[this.h.length];
            System.arraycopy(this.h, 0, psc_nr.h, 0, this.h.length);
        }
        return psc_nr;
    }
    
    public boolean equals(final Object o) {
        if (o == null || !(o instanceof psc_nr)) {
            return false;
        }
        final psc_nr psc_nr = (psc_nr)o;
        if (this.a != psc_nr.a) {
            return false;
        }
        if (super.m != null && !super.m.equals(psc_nr.m)) {
            return false;
        }
        if (this.g != null && psc_nr.g != null && !psc_k4.a(this.g, psc_nr.g)) {
            return false;
        }
        if (this.h != null && psc_nr.h != null && !psc_k4.a(this.h, psc_nr.h)) {
            return false;
        }
        if (this.b != null) {
            if (!this.b.equals(psc_nr.b)) {
                return false;
            }
        }
        else if (psc_nr.b != null) {
            return false;
        }
        if (this.f != null) {
            if (psc_nr.f == null) {
                return false;
            }
            if (this.f.length != psc_nr.f.length) {
                return false;
            }
            for (int i = 0; i < this.f.length; ++i) {
                if (this.f[i] != psc_nr.f[i]) {
                    return false;
                }
            }
        }
        else if (psc_nr.f != null) {
            return false;
        }
        if (this.d != null) {
            if (psc_nr.d == null) {
                return false;
            }
            if (!this.d.l().equals(psc_nr.d.l())) {
                return false;
            }
            if (!psc_k4.a(this.d.r(), psc_nr.d.r())) {
                return false;
            }
        }
        else if (psc_nr.d != null) {
            return false;
        }
        return true;
    }
    
    public void s() {
        super.s();
        if (this.d != null) {
            this.d.y();
        }
        this.a = -1;
        this.b = new psc_ns();
    }
    
    protected void finalize() {
        this.s();
    }
}
