import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

class psc_ns implements Cloneable, Serializable
{
    private byte[] a;
    private byte[] b;
    private byte[] c;
    private static final int d = 8454144;
    protected static int e;
    private psc_n f;
    
    protected psc_ns() {
        this.f = null;
    }
    
    protected psc_ns(final byte[] array, final int n, final int n2, final int n3) throws psc_m3 {
        this.f = null;
        try {
            final psc_h psc_h = new psc_h(n2);
            final psc_j psc_j = new psc_j();
            final psc_r psc_r = new psc_r(16777216);
            final psc_k psc_k = new psc_k(12288);
            final psc_k psc_k2 = new psc_k(130816);
            psc_l.a(array, n, new psc_i[] { psc_h, psc_r, psc_k, psc_k2, psc_j });
            this.a = new byte[psc_r.d];
            System.arraycopy(psc_r.b, psc_r.c, this.a, 0, psc_r.d);
            this.b = new byte[psc_k.d];
            System.arraycopy(psc_k.b, psc_k.c, this.b, 0, psc_k.d);
            if (psc_k2.a) {
                final int n4 = 160;
                psc_t psc_t;
                if ((psc_k2.b[psc_k2.c] & n4) != n4) {
                    psc_t = new psc_t(8454144, true, 0, n3 - psc_k2.c + n, null, 0, 0);
                }
                else {
                    psc_t = new psc_t(10551296, true, 0, n3 - psc_k2.c + n, null, 0, 0);
                }
                psc_l.a(psc_k2.b, psc_k2.c, new psc_i[] { psc_t });
                if (psc_t.a) {
                    this.c = new byte[psc_t.d];
                    System.arraycopy(psc_t.b, psc_t.c, this.c, 0, psc_t.d);
                }
            }
        }
        catch (psc_m psc_m) {
            throw new psc_m3("Cannot decode the BER of the EncryptedContentInfo." + psc_m.getMessage());
        }
    }
    
    protected void a(final byte[] array, final int n, final int n2) throws psc_m3 {
        if (array == null) {
            throw new psc_m3("Cannot set content type: null OID.");
        }
        if (n2 != 9) {
            throw new psc_m3("Cannot set content type: unknown OID.");
        }
        for (int i = 0; i < 7; ++i) {
            if (array[i + n] != psc_m2.i[i]) {
                throw new psc_m3("Cannot set content type: unknown OID.");
            }
        }
        System.arraycopy(array, n, this.a = new byte[n2], 0, n2);
    }
    
    protected byte[] b() {
        return this.a;
    }
    
    protected void b(final byte[] array, final int n, final int n2) throws psc_m3 {
        if (array == null || n2 == 0) {
            throw new psc_m3("Specified OID is NULL.");
        }
        System.arraycopy(array, n, this.b = new byte[n2], 0, n2);
    }
    
    protected byte[] c() {
        return this.b;
    }
    
    protected void c(final byte[] array, final int n, final int n2) throws psc_m3 {
        if (array == null) {
            throw new psc_m3("encrypted content is null.");
        }
        System.arraycopy(array, n, this.c = new byte[n2], 0, n2);
    }
    
    protected byte[] d() {
        return this.c;
    }
    
    protected int a(final int e) throws psc_m3 {
        psc_ns.e = e;
        return this.a();
    }
    
    protected int d(final byte[] array, final int n, final int n2) throws psc_m3 {
        try {
            if (this.f == null || n2 != psc_ns.e) {
                this.a(n2);
            }
            final int a = this.f.a(array, n);
            this.f = null;
            return a;
        }
        catch (psc_m psc_m) {
            this.f = null;
            throw new psc_m3("Unable to encode EncryptedContentInfo: " + psc_m.getMessage());
        }
    }
    
    private int a() throws psc_m3 {
        try {
            final psc_h psc_h = new psc_h(psc_ns.e, true, 0);
            final psc_j psc_j = new psc_j();
            final psc_r psc_r = new psc_r(16777216, true, 0, this.a, 0, this.a.length);
            final psc_k psc_k = new psc_k(12288, true, 0, this.b, 0, this.b.length);
            if (this.c != null) {
                this.f = new psc_n(new psc_i[] { psc_h, psc_r, psc_k, new psc_t(8454144, true, 0, this.c, 0, this.c.length), psc_j });
            }
            else {
                this.f = new psc_n(new psc_i[] { psc_h, psc_r, psc_k, psc_j });
            }
            return this.f.a();
        }
        catch (psc_m psc_m) {
            throw new psc_m3(psc_m.getMessage());
        }
    }
    
    public boolean equals(final Object o) {
        if (o == null || !(o instanceof psc_ns)) {
            return false;
        }
        final psc_ns psc_ns = (psc_ns)o;
        return psc_k4.a(this.a, psc_ns.a) && psc_k4.a(this.b, psc_ns.b) && psc_k4.a(this.c, psc_ns.c);
    }
    
    protected Object clone() throws CloneNotSupportedException {
        final psc_ns psc_ns = new psc_ns();
        if (this.a != null) {
            psc_ns.a = new byte[this.a.length];
            System.arraycopy(this.a, 0, psc_ns.a, 0, this.a.length);
        }
        if (this.b != null) {
            psc_ns.b = new byte[this.b.length];
            System.arraycopy(this.b, 0, psc_ns.b, 0, this.b.length);
        }
        if (this.c != null) {
            psc_ns.c = new byte[this.c.length];
            System.arraycopy(this.c, 0, psc_ns.c, 0, this.c.length);
        }
        psc_ns.e = psc_ns.e;
        try {
            if (this.f != null) {
                psc_ns.a();
            }
        }
        catch (psc_m3 psc_m3) {
            throw new CloneNotSupportedException(psc_m3.getMessage());
        }
        return psc_ns;
    }
    
    protected void e() {
        this.a = null;
        this.b = null;
        this.c = null;
        psc_ns.e = 0;
    }
}
