import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_nw implements Cloneable, Serializable
{
    private static final int a = 8454144;
    private static final int b = 8454145;
    private int c;
    private psc_u d;
    private byte[] e;
    private byte[] f;
    private psc_cr g;
    private byte[] h;
    private psc_cr i;
    private byte[] j;
    private String k;
    private String l;
    private byte[] m;
    protected static int n;
    private psc_n o;
    
    public psc_nw() {
        this.c = 1;
        this.d = null;
        this.e = null;
        this.f = null;
        this.g = null;
        this.h = null;
        this.i = null;
        this.j = null;
        this.k = null;
        this.l = null;
        this.m = null;
        this.o = null;
    }
    
    protected psc_nw(final byte[] array, final int n, final int n2) throws psc_m3 {
        this.c = 1;
        this.d = null;
        this.e = null;
        this.f = null;
        this.g = null;
        this.h = null;
        this.i = null;
        this.j = null;
        this.k = null;
        this.l = null;
        this.m = null;
        this.o = null;
        try {
            if (array == null) {
                throw new psc_m3("Encoding is null.");
            }
            final psc_h psc_h = new psc_h(n2);
            final psc_j psc_j = new psc_j();
            final psc_p psc_p = new psc_p(0);
            final psc_k psc_k = new psc_k(12288);
            final psc_k psc_k2 = new psc_k(12288);
            final psc_k psc_k3 = new psc_k(8466688);
            final psc_k psc_k4 = new psc_k(12288);
            final psc_t psc_t = new psc_t(0);
            final psc_k psc_k5 = new psc_k(8466689);
            psc_l.a(array, n, new psc_i[] { psc_h, psc_p, psc_k, psc_k2, psc_k3, psc_k4, psc_t, psc_k5, psc_j });
            this.c = psc_p.e();
            final psc_h psc_h2 = new psc_h(n2);
            final psc_k psc_k6 = new psc_k(12288);
            final psc_p psc_p2 = new psc_p(0);
            psc_l.a(psc_k.b, psc_k.c, new psc_i[] { psc_h2, psc_k6, psc_p2, psc_j });
            this.d = new psc_u(psc_k6.b, psc_k6.c, 0);
            if (psc_p2.d == 0) {
                throw new psc_m3("Cannot decode the BER of the SignerInfo: Serial Number is null.");
            }
            this.e = new byte[psc_p2.d];
            System.arraycopy(psc_p2.b, psc_p2.c, this.e, 0, psc_p2.d);
            if (psc_k2.d == 0) {
                throw new psc_m3("Cannot decode the BER of the SignerInfo: Digest algorithm is missing.");
            }
            this.f = new byte[psc_k2.d];
            System.arraycopy(psc_k2.b, psc_k2.c, this.f, 0, psc_k2.d);
            if (psc_k3.a) {
                this.m = new byte[psc_k3.d];
                System.arraycopy(psc_k3.b, psc_k3.c, this.m, 0, psc_k3.d);
                this.m[0] = 49;
                this.g = new psc_cr(psc_k3.b, psc_k3.c, 8454144);
            }
            if (psc_k4.d == 0) {
                throw new psc_m3("Cannot decode the BER of the SignerInfo: Encryption algorithm is missing.");
            }
            this.h = new byte[psc_k4.d];
            System.arraycopy(psc_k4.b, psc_k4.c, this.h, 0, psc_k4.d);
            if (psc_t.d == 0) {
                throw new psc_m3("Cannot decode the BER of the SignerInfo: Encrypted digest is missing.");
            }
            this.j = new byte[psc_t.d];
            System.arraycopy(psc_t.b, psc_t.c, this.j, 0, psc_t.d);
            if (psc_k5.a) {
                this.i = new psc_cr(psc_k5.b, psc_k5.c, 8454145);
            }
        }
        catch (psc_m psc_m) {
            throw new psc_m3("Cannot decode the BER of the SignerInfo: " + psc_m.getMessage());
        }
        catch (psc_f0 psc_f0) {
            throw new psc_m3("Cannot decode the BER of the SignerInfo: " + psc_f0.getMessage());
        }
        catch (psc_v psc_v) {
            throw new psc_m3("Cannot decode the BER of the SignerInfo: " + psc_v.getMessage());
        }
    }
    
    public void a(final int c) {
        this.c = c;
    }
    
    public int b() {
        return this.c;
    }
    
    public void a(final psc_u psc_u, final byte[] array, final int n, final int n2) throws psc_m3 {
        if (psc_u == null) {
            throw new psc_m3("Issuer name is null.");
        }
        try {
            this.d = (psc_u)psc_u.clone();
        }
        catch (CloneNotSupportedException ex) {
            throw new psc_m3("Invalid name.");
        }
        if (array == null || n2 <= 0) {
            throw new psc_m3("SerialNumber is null.");
        }
        if (n < 0 || n + n2 > array.length) {
            throw new psc_m3("Invalid SerialNumber");
        }
        System.arraycopy(array, n, this.e = new byte[n2], 0, n2);
    }
    
    public psc_u c() throws psc_m3 {
        if (this.d == null) {
            return null;
        }
        try {
            return (psc_u)this.d.clone();
        }
        catch (CloneNotSupportedException ex) {
            throw new psc_m3("Invalid attributes.");
        }
    }
    
    public byte[] d() {
        if (this.e == null) {
            return null;
        }
        return this.e.clone();
    }
    
    public byte[] e() throws psc_m3 {
        if (this.d == null || this.e == null) {
            throw new psc_m3("Cannot get issuerSerial, not all values set.");
        }
        try {
            final byte[] array = new byte[this.d.d(0)];
            final int a = this.d.a(array, 0, 0);
            final psc_h psc_h = new psc_h(0, true, 0);
            final psc_j psc_j = new psc_j();
            psc_p psc_p;
            if ((this.e[0] & 0x80) >> 7 == 0) {
                psc_p = new psc_p(0, true, 0, this.e, 0, this.e.length, true);
            }
            else {
                psc_p = new psc_p(0, true, 0, this.e, 0, this.e.length, false);
            }
            return psc_l.a(new psc_i[] { psc_h, new psc_k(12288, true, 0, array, 0, a), psc_p, psc_j });
        }
        catch (psc_m psc_m) {
            throw new psc_m3("Cannot encode issuerSerial: " + psc_m.getMessage());
        }
        catch (psc_v psc_v) {
            throw new psc_m3("Cannot encode issuerSerial: " + psc_v.getMessage());
        }
    }
    
    public void a(final String k) throws psc_m3 {
        this.f = psc_nv.a(k);
        this.k = k;
    }
    
    public void a(final byte[] array, final int n, final int n2) throws psc_m3 {
        this.f = psc_nv.a(array, n, n2);
    }
    
    public byte[] f() {
        return this.f;
    }
    
    public String g() throws psc_m3 {
        return psc_nv.a(this.f, this.k);
    }
    
    public void a(final psc_cr psc_cr) throws psc_m3 {
        if (psc_cr == null) {
            throw new psc_m3("Attributes are null");
        }
        try {
            this.g = (psc_cr)psc_cr.clone();
        }
        catch (CloneNotSupportedException ex) {
            throw new psc_m3("Invalid attributes: " + ex.getMessage());
        }
    }
    
    protected byte[] h() {
        if (this.m != null) {
            return this.m;
        }
        return null;
    }
    
    public psc_cr i() throws psc_m3 {
        if (this.g == null) {
            return null;
        }
        try {
            return (psc_cr)this.g.clone();
        }
        catch (CloneNotSupportedException ex) {
            throw new psc_m3("Invalid attributes.");
        }
    }
    
    public void b(final String s) throws psc_m3 {
        if (s == null) {
            throw new psc_m3("Could not set algorithm OID: name is null.");
        }
        this.l = s;
        if (s.indexOf("RSA") != -1) {
            try {
                this.h = psc_iq.a(s, "Java").l();
                return;
            }
            catch (psc_ap psc_ap) {
                throw new psc_m3("Could not set algorithm OID: " + psc_ap.getMessage());
            }
        }
        if (s.indexOf("DSA") != -1) {
            try {
                this.h = psc_eo.a("SHA1/DSA/NoPad", "Java").j();
                return;
            }
            catch (psc_ap psc_ap2) {
                throw new psc_m3("Could not set algorithm OID: " + psc_ap2.getMessage());
            }
        }
        throw new psc_m3("Could not set Encryption Algorithm, " + s + " is invalid algorithm.");
    }
    
    public void b(final byte[] array, final int n, final int n2) throws psc_m3 {
        if (array == null || n2 <= 0) {
            throw new psc_m3("Could not set algorithm OID: OID is null");
        }
        if (n < 0 || n + n2 > array.length) {
            throw new psc_m3("Invalid Encryption Algorithm Identifier.");
        }
        System.arraycopy(array, n, this.h = new byte[n2], 0, n2);
    }
    
    public byte[] j() {
        if (this.h == null) {
            return null;
        }
        return this.h.clone();
    }
    
    public String k() throws psc_m3 {
        if (this.h == null) {
            return null;
        }
        if (this.l != null) {
            return this.l;
        }
        try {
            return psc_iq.a(this.h, 0, "Java").p();
        }
        catch (psc_ap psc_ap2) {
            try {
                return psc_eo.a(this.h, 0, "Java").m();
            }
            catch (psc_ap psc_ap) {
                throw new psc_m3("Could not get encryption algorithm name: " + psc_ap.getMessage());
            }
        }
    }
    
    protected void c(final byte[] array, final int n, final int n2) {
        if (array != null && n2 != 0) {
            System.arraycopy(array, n, this.j = new byte[n2], 0, n2);
        }
    }
    
    public byte[] l() {
        if (this.j == null) {
            return null;
        }
        return this.j.clone();
    }
    
    public void b(final psc_cr psc_cr) throws psc_m3 {
        if (psc_cr == null) {
            throw new psc_m3("Null attributes.");
        }
        try {
            this.i = (psc_cr)psc_cr.clone();
        }
        catch (CloneNotSupportedException ex) {
            throw new psc_m3("Invalid attributes: " + ex.getMessage());
        }
    }
    
    public psc_cr m() throws psc_m3 {
        if (this.i == null) {
            return null;
        }
        try {
            return (psc_cr)this.i.clone();
        }
        catch (CloneNotSupportedException ex) {
            throw new psc_m3("Invalid attributes: " + ex.getMessage());
        }
    }
    
    protected static int a(final byte[] array, final int n) throws psc_m3 {
        if (array == null) {
            throw new psc_m3("Encoding is null.");
        }
        if (array[n] == 0 && array[n + 1] == 0) {
            return n + 2;
        }
        try {
            return n + 1 + psc_o.a(array, n + 1) + psc_o.b(array, n + 1);
        }
        catch (psc_m psc_m) {
            throw new psc_m3("Unable to determine length of the BER: " + psc_m.getMessage());
        }
    }
    
    protected int b(final int n) throws psc_m3 {
        psc_nw.n = n;
        return this.a();
    }
    
    protected int d(final byte[] array, final int n, final int n2) throws psc_m3 {
        if (array == null) {
            throw new psc_m3("Passed array is null.");
        }
        try {
            if (this.o == null || n2 != psc_nw.n) {
                this.b(n2);
            }
            final int a = this.o.a(array, n);
            this.o = null;
            return a;
        }
        catch (psc_m psc_m) {
            this.o = null;
            throw new psc_m3("Unable to encode SignerInfo: " + psc_m.getMessage());
        }
    }
    
    private int a() throws psc_m3 {
        if (this.d == null || this.e == null) {
            throw new psc_m3("Cannot encode SignerInfo: issuerName or SerialNumber is not set.");
        }
        try {
            final byte[] array = new byte[this.d.d(0)];
            this.d.a(array, 0, 0);
            psc_i psc_i = null;
            if (this.g != null) {
                final byte[] array2 = new byte[this.g.b(8454144)];
                psc_i = new psc_k(8466688, true, 0, array2, 0, this.g.b(array2, 0, 8454144));
            }
            psc_i psc_i2 = null;
            if (this.i != null) {
                final byte[] array3 = new byte[this.i.b(8454145)];
                psc_i2 = new psc_k(8466689, true, 0, array3, 0, this.i.b(array3, 0, 8454145));
            }
            final psc_h psc_h = new psc_h(0, true, 0);
            final psc_j psc_j = new psc_j();
            final psc_k psc_k = new psc_k(12288, true, 0, array, 0, array.length);
            psc_p psc_p;
            if ((this.e[0] & 0x80) >> 7 == 0) {
                psc_p = new psc_p(0, true, 0, this.e, 0, this.e.length, true);
            }
            else {
                psc_p = new psc_p(0, true, 0, this.e, 0, this.e.length, false);
            }
            final psc_n psc_n = new psc_n(new psc_i[] { psc_h, psc_k, psc_p, psc_j });
            final byte[] array4 = new byte[psc_n.a()];
            psc_n.a(array4, 0);
            final psc_h psc_h2 = new psc_h(psc_nw.n, true, 0);
            final psc_p psc_p2 = new psc_p(0, true, 0, this.c);
            final psc_k psc_k2 = new psc_k(12288, true, 0, array4, 0, array4.length);
            if (this.f == null) {
                throw new psc_m3("DigestAlgorithmIdentifier is not set.");
            }
            final psc_k psc_k3 = new psc_k(12288, true, 0, this.f, 0, this.f.length);
            if (this.h == null) {
                throw new psc_m3("EncryptionAlgorithmIdentifier is not set.");
            }
            final psc_k psc_k4 = new psc_k(12288, true, 0, this.h, 0, this.h.length);
            int length = 0;
            if (this.j != null) {
                length = this.j.length;
            }
            final psc_t psc_t = new psc_t(0, true, 0, this.j, 0, length);
            if (psc_i != null) {
                if (psc_i2 != null) {
                    this.o = new psc_n(new psc_i[] { psc_h2, psc_p2, psc_k2, psc_k3, psc_i, psc_k4, psc_t, psc_i2, psc_j });
                }
                else {
                    this.o = new psc_n(new psc_i[] { psc_h2, psc_p2, psc_k2, psc_k3, psc_i, psc_k4, psc_t, psc_j });
                }
            }
            else if (psc_i2 != null) {
                this.o = new psc_n(new psc_i[] { psc_h2, psc_p2, psc_k2, psc_k3, psc_k4, psc_t, psc_i2, psc_j });
            }
            else {
                this.o = new psc_n(new psc_i[] { psc_h2, psc_p2, psc_k2, psc_k3, psc_k4, psc_t, psc_j });
            }
            return this.o.a();
        }
        catch (psc_m psc_m) {
            throw new psc_m3("Cannot encode SignerInfo: " + psc_m.getMessage());
        }
        catch (psc_v psc_v) {
            throw new psc_m3("Cannot encode X500Name: " + psc_v.getMessage());
        }
        catch (psc_f0 psc_f0) {
            throw new psc_m3("Cannot encode X501Attributes: " + psc_f0.getMessage());
        }
    }
    
    public boolean equals(final Object o) {
        if (o == null || !(o instanceof psc_nw)) {
            return false;
        }
        final psc_nw psc_nw = (psc_nw)o;
        if (this.c != psc_nw.c) {
            return false;
        }
        if (this.d != null) {
            if (!this.d.equals(psc_nw.d)) {
                return false;
            }
        }
        else if (psc_nw.d != null) {
            return false;
        }
        if (!psc_k4.a(this.e, psc_nw.e)) {
            return false;
        }
        if (!psc_k4.a(this.f, psc_nw.f)) {
            return false;
        }
        if (this.g != null) {
            if (!this.g.equals(psc_nw.g)) {
                return false;
            }
        }
        else if (psc_nw.g != null) {
            return false;
        }
        if (!psc_k4.a(this.h, psc_nw.h)) {
            return false;
        }
        if (this.i != null) {
            if (!this.i.equals(psc_nw.i)) {
                return false;
            }
        }
        else if (psc_nw.i != null) {
            return false;
        }
        return psc_k4.a(this.j, psc_nw.j);
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_nw psc_nw = new psc_nw();
        psc_nw.c = this.c;
        if (this.d != null) {
            psc_nw.d = (psc_u)this.d.clone();
        }
        if (this.e != null) {
            psc_nw.e = new byte[this.e.length];
            System.arraycopy(this.e, 0, psc_nw.e, 0, this.e.length);
        }
        if (this.f != null) {
            psc_nw.f = new byte[this.f.length];
            System.arraycopy(this.f, 0, psc_nw.f, 0, this.f.length);
        }
        if (this.g != null) {
            psc_nw.g = (psc_cr)this.g.clone();
        }
        if (this.h != null) {
            psc_nw.h = new byte[this.h.length];
            System.arraycopy(this.h, 0, psc_nw.h, 0, this.h.length);
        }
        if (this.i != null) {
            psc_nw.i = (psc_cr)this.i.clone();
        }
        if (this.j != null) {
            psc_nw.j = new byte[this.j.length];
            System.arraycopy(this.j, 0, psc_nw.j, 0, this.j.length);
        }
        if (this.m != null) {
            psc_nw.m = new byte[this.m.length];
            System.arraycopy(this.m, 0, psc_nw.m, 0, this.m.length);
        }
        psc_nw.n = psc_nw.n;
        try {
            if (this.o != null) {
                psc_nw.a();
            }
        }
        catch (psc_m3 psc_m3) {
            throw new CloneNotSupportedException("Cannot set ASN1 template:" + psc_m3.getMessage());
        }
        return psc_nw;
    }
}
