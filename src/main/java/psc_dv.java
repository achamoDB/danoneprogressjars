import java.security.SecureRandom;

// 
// Decompiled by Procyon v0.5.36
// 

public abstract class psc_dv implements psc_dw
{
    protected psc_c3 a;
    private psc_nf[] b;
    private psc_df c;
    private psc_df d;
    private psc_iw e;
    private psc_dt f;
    private psc_al g;
    private psc_dt h;
    private psc_al i;
    private psc_al j;
    private psc_dc k;
    private psc_dc l;
    private byte[] m;
    private byte[] n;
    private byte[] o;
    private byte[] p;
    private byte[] q;
    private byte[] r;
    private psc_da s;
    private String t;
    private String u;
    private String v;
    private String w;
    private psc_el x;
    private long y;
    private long z;
    private int aa;
    private boolean ab;
    
    public psc_dv(final String w, final String t, final String u, final String v) {
        this.a = new psc_c3();
        this.b = new psc_nf[0];
        this.t = t;
        this.u = u;
        this.w = w;
        this.v = v;
        this.z = 0L;
        this.y = 0L;
        this.ab = true;
    }
    
    public void a(final String s) {
        this.a(new psc_c3(s));
    }
    
    public void a(final psc_c3 a) {
        this.a = a;
    }
    
    public psc_c3 n() {
        return this.a;
    }
    
    public void a(final psc_nf[] array) {
        this.b = array.clone();
    }
    
    public psc_nf[] o() {
        return this.b.clone();
    }
    
    public void p() {
        for (int length = this.b.length, i = 0; i < length; ++i) {
            this.b[i].c();
        }
    }
    
    public int a(final byte[] array, final int n) throws psc_d {
        try {
            final int f = this.f();
            this.c();
            this.d();
            System.arraycopy(array, n, this.o = new byte[f], 0, f);
            (this.k = this.c.o()).a(array, n, f);
            this.c.a(this.k);
            return f;
        }
        catch (psc_ap psc_ap) {
            throw new psc_d("unable to set write key: " + psc_ap.getMessage());
        }
    }
    
    public int a(final byte[] array, final int n, final int n2) throws psc_d {
        try {
            this.c();
            this.d();
            System.arraycopy(array, n, this.p = new byte[n2], 0, n2);
            (this.l = this.d.o()).a(array, n, n2);
            this.d.b(this.l);
            return n2;
        }
        catch (psc_ap psc_ap) {
            throw new psc_d("unable to set read key: " + psc_ap.getMessage());
        }
    }
    
    public int b(final byte[] array, final int n) throws psc_d {
        return this.a(array, n, this.f());
    }
    
    public int c(final byte[] array, final int n) {
        final int h = this.h();
        this.n = new byte[h];
        this.z = 0L;
        System.arraycopy(array, n, this.n, 0, h);
        return h;
    }
    
    public int d(final byte[] array, final int n) {
        final int h = this.h();
        this.m = new byte[h];
        this.y = 0L;
        System.arraycopy(array, n, this.m, 0, h);
        return h;
    }
    
    public abstract int h();
    
    public void a(final byte[] array, final int n, final char[] array2) throws psc_d {
        try {
            final psc_df a = psc_df.a(array, n, "Java");
            final psc_dc o = a.o();
            o.a(array2, 0, array2.length);
            a.b(o);
            this.f = a.a(array, n, array.length - n, true);
        }
        catch (psc_ap psc_ap) {
            throw new psc_d("unable to create private key: " + psc_ap.getMessage());
        }
    }
    
    public void a(final psc_dt f) {
        this.f = f;
        this.h = this.f;
    }
    
    public psc_dt q() {
        return this.f;
    }
    
    public void a(final psc_al j) throws psc_d {
        this.j = j;
    }
    
    public void e(final byte[] array, final int n) throws psc_d {
        try {
            this.j = psc_al.a(array, n, "Java");
        }
        catch (psc_ap psc_ap) {
            throw new psc_d("unable to create peer public key: " + psc_ap.getMessage());
        }
    }
    
    public psc_al r() {
        return this.g;
    }
    
    public void b(final psc_al g) {
        this.g = g;
    }
    
    public void f(final byte[] array, final int n) throws psc_d {
        try {
            this.g = psc_al.a(array, n, "Java");
        }
        catch (psc_ap psc_ap) {
            throw new psc_d("unable to set signing public key: " + psc_ap.getMessage());
        }
    }
    
    public abstract int f();
    
    public int s() {
        try {
            this.c();
            this.d();
        }
        catch (psc_d psc_d) {
            return 0;
        }
        return this.c.n();
    }
    
    public int a(final byte[] array, final int n, final int n2, final byte[] array2, final int n3) throws psc_d {
        try {
            if (this.k == null) {
                throw new psc_d("there is no secret key set");
            }
            this.c();
            this.d();
            return this.c.a(array, n, n2, array2, n3);
        }
        catch (psc_ap psc_ap) {
            throw new psc_d("unable to encrypt block: " + psc_ap.getMessage());
        }
    }
    
    public int b(final byte[] array, final int n, final int n2, final byte[] array2, final int n3) throws psc_d {
        try {
            if (this.l == null) {
                throw new psc_d("there is no secret key set");
            }
            this.c();
            this.d();
            return this.d.b(array, n, n2, array2, n3);
        }
        catch (psc_ap psc_ap) {
            throw new psc_d("unable to decrypt block: " + psc_ap.getMessage());
        }
    }
    
    public int a(final byte[] array, final int n, final int n2, final byte[] array2, final int n3, final psc_av psc_av) throws psc_d {
        try {
            String s;
            if (this.f.l().equals("RSA")) {
                s = "NoDigest/" + this.f.l() + "/PKCS1Block01Pad";
            }
            else {
                s = "SHA1/" + this.f.l();
            }
            final psc_eo a = psc_eo.a(s, this.a.a(psc_n0.f));
            a.a(this.f, null, psc_av, this.b);
            a.d(array, n, n2);
            return a.e(array2, n3);
        }
        catch (Exception ex) {
            throw new psc_d("signing failed: " + ex.getMessage());
        }
    }
    
    public boolean a(final byte[] array, final int n, final int n2, final byte[] array2, final int n3, final int n4) throws psc_d {
        try {
            String s;
            if (this.g.l().equals("RSA")) {
                s = "NoDigest/" + this.g.l() + "/PKCS1Block01Pad";
            }
            else {
                s = "SHA1/" + this.g.l();
            }
            final psc_eo a = psc_eo.a(s, this.a.a(psc_n0.g));
            a.a(this.g, null, null, this.b);
            a.e(array2, n3, n4);
            return a.f(array, n, n2);
        }
        catch (Exception ex) {
            throw new psc_d("verification failed: " + ex.getMessage());
        }
    }
    
    public byte[][] a(final psc_av psc_av) throws psc_d {
        try {
            final psc_em a = psc_em.a(this.w, this.a.a(psc_n0.h));
            a.b(null, new int[] { 512, 65537 }, psc_av, this.b);
            a.s();
            this.i = a.p();
            this.h = a.q();
            final byte[][] array = { new byte[this.i.m()[0].length], new byte[this.i.m()[1].length] };
            System.arraycopy(this.i.m()[0], 0, array[0], 0, array[0].length);
            System.arraycopy(this.i.m()[1], 0, array[1], 0, array[1].length);
            this.j = this.i;
            return array;
        }
        catch (psc_ap psc_ap) {
            throw new psc_d("unable to generate key pair: " + psc_ap.getMessage());
        }
    }
    
    public int t() {
        try {
            return this.j.b(this.t.concat("PublicKey"))[0].length;
        }
        catch (psc_ap psc_ap) {
            return 0;
        }
    }
    
    public void c(final int aa) {
        this.aa = aa;
    }
    
    public int u() {
        return this.aa;
    }
    
    public int b(final byte[] array, final int n, final int n2, final byte[] array2, int n3, final psc_av psc_av) throws psc_d {
        if (this.j == null) {
            throw new psc_d("there is no public key set");
        }
        try {
            final psc_iq a = psc_iq.a(this.w + "/PKCS1Block02Pad", this.a.a(psc_n0.d));
            a.a(this.j, psc_av, this.b);
            n3 += a.c(array, n, n2, array2, n3);
            n3 += a.f(array2, n3);
            return n3;
        }
        catch (psc_ap psc_ap) {
            throw new psc_d("unable to perform asymmetric encryption: " + psc_ap.getMessage());
        }
    }
    
    public int c(final byte[] array, final int n, final int n2, final byte[] array2, final int n3) throws psc_d {
        try {
            final psc_iq a = psc_iq.a(this.w + "/PKCS1Block02Pad", this.a.a(psc_n0.e));
            a.a((this.h == null) ? this.f : this.h, this.b);
            final int d = a.d(array, n, n2, array2, n3);
            return d + a.g(array2, n3 + d);
        }
        catch (psc_ap psc_ap) {
            throw new psc_d("unable to perform asymmetric decryption: " + psc_ap.getMessage());
        }
    }
    
    public int g(final byte[] array, final int n) throws psc_d {
        try {
            final int n2 = this.c.n();
            if (n2 > 1) {
                System.arraycopy(array, n, this.q = new byte[n2], 0, n2);
                this.c.b(this.q, 0, n2);
            }
            if (this.k != null) {
                this.c.a(this.k);
            }
            if (n2 > 1) {
                return n2;
            }
            return 0;
        }
        catch (psc_ap psc_ap) {
            throw new psc_d("unable to set write IV: " + psc_ap.getMessage());
        }
    }
    
    public int h(final byte[] array, final int n) throws psc_d {
        try {
            final int n2 = this.d.n();
            if (n2 > 1) {
                System.arraycopy(array, n, this.r = new byte[n2], 0, n2);
                this.d.b(this.r, 0, n2);
            }
            if (this.l != null) {
                this.d.b(this.l);
            }
            if (n2 > 1) {
                return n2;
            }
            return 0;
        }
        catch (psc_ap psc_ap) {
            throw new psc_d("unable to set read IV: " + psc_ap.getMessage());
        }
    }
    
    public byte[] v() {
        return this.q;
    }
    
    public byte[] w() {
        return this.r;
    }
    
    public abstract int g();
    
    public int d(final int n) {
        final int s = this.s();
        if (n % s != 0) {
            return (n / s + 1) * s;
        }
        return n;
    }
    
    public int a(final byte[] array, final int n, final int n2, final byte[] array2, final byte[] array3, final int n3, final boolean b) throws psc_d {
        try {
            final psc_eh a = psc_eh.a("HMAC/" + this.v, this.a.a(psc_n0.a));
            final psc_dc n4 = a.n();
            final byte[][] array4 = { null };
            long n5;
            if (b) {
                array4[0] = this.n;
                n5 = this.z;
                ++this.z;
            }
            else {
                array4[0] = this.m;
                n5 = this.y;
                ++this.y;
            }
            n4.c(array4);
            a.b(n4, null);
            final byte[] array5 = { (byte)(n5 >> 56), (byte)(n5 >> 48), (byte)(n5 >> 40), (byte)(n5 >> 32), (byte)(n5 >> 24), (byte)(n5 >> 16), (byte)(n5 >> 8), (byte)(n5 & 0xFFL) };
            a.a(array5, 0, array5.length);
            a.a(array2, 0, array2.length);
            a.a(array, n, n2);
            return a.c(array3, n3);
        }
        catch (psc_ap psc_ap) {
            throw new psc_d("unable to perform the HMAC operation: " + psc_ap.getMessage());
        }
    }
    
    public int a(final byte[] array, final int n, final int n2, final byte[] array2, final int n3, final boolean b, final long n4) throws psc_d {
        try {
            byte[] array3;
            if (b) {
                array3 = this.p;
            }
            else {
                array3 = this.o;
            }
            final byte[] array4 = { (byte)(n4 >> 24), (byte)(n4 >> 16), (byte)(n4 >> 8), (byte)(n4 & 0xFFL) };
            final psc_da a = psc_da.a(this.v, this.a.a(psc_n0.a));
            a.i();
            a.a(array3, 0, this.f());
            a.a(array, n, n2);
            a.a(array4, 0, 4);
            return a.c(array2, n3);
        }
        catch (psc_ap psc_ap) {
            throw new psc_d("unable to perform the hash: " + psc_ap.getMessage());
        }
    }
    
    public synchronized int a(final byte[] array, final int n, final int n2, final byte[] array2, final int n3, final byte b, final boolean b2) throws psc_d {
        long n4;
        if (b2) {
            n4 = this.z;
            ++this.z;
        }
        else {
            n4 = this.y;
            ++this.y;
        }
        try {
            this.c();
            this.d();
            this.s.i();
            if (b2) {
                this.s.a(this.n, 0, this.n.length);
            }
            else {
                this.s.a(this.m, 0, this.m.length);
            }
            if (this.v.equals("MD5")) {
                this.s.a(psc_dw.d, 0, 48);
            }
            else {
                this.s.a(psc_dw.d, 0, 40);
            }
            final byte[] array3 = { (byte)(n4 >> 56), (byte)(n4 >> 48), (byte)(n4 >> 40), (byte)(n4 >> 32), (byte)(n4 >> 24), (byte)(n4 >> 16), (byte)(n4 >> 8), (byte)(n4 & 0xFFL) };
            this.s.a(array3, 0, array3.length);
            this.s.a(new byte[] { b, (byte)(n2 >> 8), (byte)(n2 & 0xFF) }, 0, 3);
            this.s.a(array, n, n2);
            final byte[] j = this.s.j();
            this.s.i();
            if (b2) {
                this.s.a(this.n, 0, this.n.length);
            }
            else {
                this.s.a(this.m, 0, this.m.length);
            }
            if (this.v.equals("MD5")) {
                this.s.a(psc_dw.e, 0, 48);
            }
            else {
                this.s.a(psc_dw.e, 0, 40);
            }
            this.s.a(j, 0, j.length);
            return this.s.c(array2, n3);
        }
        catch (psc_ap psc_ap) {
            throw new psc_d("unable to perform the MAC: " + psc_ap.getMessage());
        }
    }
    
    public byte[] a(final psc_av psc_av, final byte[] array) throws psc_d {
        try {
            byte[] array2;
            if (this.e == null) {
                (this.e = psc_iw.a("DH", "Java")).a(this.f, null);
                final psc_al a = psc_al.a("DH", "Java");
                a.c(new byte[][] { this.c(psc_av)[0], this.c(psc_av)[1], this.c(psc_av)[2], array });
                array2 = this.e.a(a);
            }
            else {
                try {
                    array2 = this.e.o();
                }
                catch (psc_ap psc_ap) {
                    array2 = this.e.a(array, 0, array.length);
                }
            }
            return array2;
        }
        catch (Exception ex) {
            throw new psc_d("unable to generate public value" + ex.getMessage());
        }
    }
    
    public byte[] b(final psc_av psc_av) throws psc_d {
        try {
            if (this.j == null) {
                (this.e = psc_iw.a("DH", "Java")).a(this.x, psc_av);
                return this.e.m();
            }
            (this.e = psc_iw.a("DH", "Java")).a(this.j, psc_av);
            return this.e.n().m()[3];
        }
        catch (Exception ex) {
            throw new psc_d("unable to generate public value");
        }
    }
    
    public void b(final String t) {
        this.t = t;
    }
    
    public void c(final String u) {
        this.u = u;
    }
    
    public void d(final String w) {
        this.w = w;
    }
    
    public void e(final String v) {
        this.v = v;
    }
    
    public String x() {
        return this.w;
    }
    
    public String y() {
        return this.u;
    }
    
    public String z() {
        return this.t;
    }
    
    public String aa() {
        return this.v;
    }
    
    public abstract byte[] b(final int p0);
    
    public byte[][] c(final psc_av psc_av) throws psc_d {
        try {
            if (this.x == null) {
                (this.x = psc_el.a("DH", "Java")).a(new int[] { 512, 160 }, psc_av, this.b);
                this.x.n();
            }
            return this.x.l();
        }
        catch (Exception ex) {
            throw new psc_d("can't get the DH parameter data" + ex.getMessage());
        }
    }
    
    public void ab() {
        this.d.y();
        this.c.y();
        this.j.y();
        this.g.y();
        this.f.y();
        this.h.y();
        this.i.y();
        this.l.y();
        this.k.y();
        this.s.y();
        for (int i = 0; i < this.n.length; ++i) {
            this.n[i] = 0;
        }
        for (int j = 0; j < this.m.length; ++j) {
            this.m[j] = 0;
        }
        this.z = 0L;
        this.y = 0L;
    }
    
    protected void a() throws psc_d {
        if (this.u == null) {
            throw new psc_d("no symmetric cipher name set");
        }
        try {
            this.c = psc_df.a(this.u, this.a.a(psc_n0.c));
            this.d = psc_df.a(this.u, this.a.a(psc_n0.b));
        }
        catch (psc_ap psc_ap) {
            throw new psc_d("unable to create symmetric ciphers: " + psc_ap.getMessage());
        }
    }
    
    protected void b() throws psc_d {
        if (this.v == null) {
            throw new psc_d("no message digest name set");
        }
        try {
            this.s = psc_da.a(this.v, this.a.a(psc_n0.a));
        }
        catch (psc_ap psc_ap) {
            throw new psc_d("unable to create message digest: " + psc_ap.getMessage());
        }
    }
    
    protected void c() throws psc_d {
        if (this.d == null || this.d == null) {
            this.a();
        }
    }
    
    protected void d() throws psc_d {
        if (this.s == null) {
            this.b();
        }
    }
    
    protected void e() {
        this.ab = false;
    }
    
    public boolean ac() {
        return this.m() || this.l() || (this.k() && this.u() > 64 && this.x().equals("RSA") && this.ab);
    }
    
    public boolean ad() {
        return this.m() || this.l() || (this.k() && this.t() > 64 && this.x().equals("RSA") && this.ab);
    }
    
    public boolean ae() {
        final String name = this.getClass().getName();
        return name.startsWith("com.rsa.ssl.ciphers.RSA") || name.startsWith("com.rsa.ssl.ciphers.DH_RSA") || name.startsWith("com.rsa.ssl.ciphers.DHE_RSA");
    }
    
    public abstract boolean m();
    
    public abstract boolean l();
    
    public abstract boolean k();
}
