import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_ny implements Cloneable, Serializable
{
    private int a;
    private psc_u b;
    private byte[] c;
    private byte[] d;
    private byte[] e;
    private String f;
    protected static int g;
    private psc_n h;
    
    public psc_ny() {
        this.a = 0;
        this.b = null;
        this.c = null;
        this.d = null;
        this.e = null;
        this.h = null;
    }
    
    protected psc_ny(final byte[] array, final int n, final int n2) throws psc_m3 {
        this.a = 0;
        this.b = null;
        this.c = null;
        this.d = null;
        this.e = null;
        this.h = null;
        if (array == null) {
            throw new psc_m3("Cannot create RecipientInfo: encoding is null.");
        }
        try {
            final psc_h psc_h = new psc_h(n2);
            final psc_j psc_j = new psc_j();
            final psc_p psc_p = new psc_p(0);
            final psc_k psc_k = new psc_k(12288);
            final psc_k psc_k2 = new psc_k(12288);
            final psc_t psc_t = new psc_t(0);
            psc_l.a(array, n, new psc_i[] { psc_h, psc_p, psc_k, psc_k2, psc_t, psc_j });
            this.a = psc_p.e();
            final psc_h psc_h2 = new psc_h(n2);
            final psc_k psc_k3 = new psc_k(12288);
            final psc_p psc_p2 = new psc_p(0);
            psc_l.a(psc_k.b, psc_k.c, new psc_i[] { psc_h2, psc_k3, psc_p2, psc_j });
            this.b = new psc_u(psc_k3.b, psc_k3.c, 0);
            this.c = new byte[psc_p2.d];
            System.arraycopy(psc_p2.b, psc_p2.c, this.c, 0, psc_p2.d);
            this.d = new byte[psc_k2.d];
            System.arraycopy(psc_k2.b, psc_k2.c, this.d, 0, psc_k2.d);
            this.e = new byte[psc_t.d];
            System.arraycopy(psc_t.b, psc_t.c, this.e, 0, psc_t.d);
        }
        catch (psc_m psc_m) {
            throw new psc_m3("Cannot decode the BER of the RecipientInfo.");
        }
        catch (psc_v psc_v) {
            throw new psc_m3("Cannot decode the BER of the RecipientInfo.");
        }
    }
    
    public void a(final int a) {
        this.a = a;
    }
    
    public int b() {
        return this.a;
    }
    
    public void a(final psc_u psc_u, final byte[] array, final int n, final int n2) throws psc_m3 {
        if (psc_u == null) {
            throw new psc_m3("Issuer name is null.");
        }
        try {
            this.b = (psc_u)psc_u.clone();
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
        System.arraycopy(array, n, this.c = new byte[n2], 0, n2);
    }
    
    public psc_u c() throws psc_m3 {
        if (this.b == null) {
            return null;
        }
        try {
            return (psc_u)this.b.clone();
        }
        catch (CloneNotSupportedException ex) {
            throw new psc_m3("Invalid attributes.");
        }
    }
    
    public byte[] d() {
        if (this.c == null) {
            return null;
        }
        return this.c.clone();
    }
    
    public void a(final String original) throws psc_m3 {
        if (original == null) {
            throw new psc_m3("Could not set algorithm OID: name is null.");
        }
        this.f = new String(original);
        try {
            this.d = psc_iq.a(original, "Java").l();
        }
        catch (psc_ap psc_ap) {
            throw new psc_m3("Could not set algorithm OID: " + psc_ap.getMessage());
        }
    }
    
    public void a(final byte[] array, final int n, final int n2) throws psc_m3 {
        if (array == null || n2 <= 0) {
            throw new psc_m3("Could not set algorithm OID: OID is null");
        }
        if (n < 0 || n + n2 > array.length) {
            throw new psc_m3("Invalid Encryption Algorithm Identifier.");
        }
        System.arraycopy(array, n, this.d = new byte[n2], 0, n2);
    }
    
    public byte[] e() {
        if (this.d == null) {
            return null;
        }
        return this.d.clone();
    }
    
    public String f() throws psc_m3 {
        if (this.d == null) {
            return null;
        }
        if (this.f != null) {
            return new String(this.f);
        }
        try {
            return psc_iq.a(this.d, 0, "Java").p();
        }
        catch (psc_ap psc_ap) {
            throw new psc_m3("Could not get algorithm OID: " + psc_ap.getMessage());
        }
    }
    
    protected void b(final byte[] array, final int n, final int n2) {
        System.arraycopy(array, n, this.e = new byte[n2], 0, n2);
    }
    
    public byte[] g() {
        if (this.e == null) {
            return null;
        }
        return this.e.clone();
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
    
    protected int b(final int g) throws psc_m3 {
        psc_ny.g = g;
        return this.a();
    }
    
    protected int c(final byte[] array, final int n, final int n2) throws psc_m3 {
        if (array == null) {
            throw new psc_m3("Specified array is null.");
        }
        try {
            if ((this.h == null || n2 != psc_ny.g) && this.b(n2) == 0) {
                throw new psc_m3("Unable to encode RecipientInfo.");
            }
            final int a = this.h.a(array, n);
            this.h = null;
            return a;
        }
        catch (psc_m psc_m) {
            this.h = null;
            throw new psc_m3("Unable to encode RecipientInfo: " + psc_m.getMessage());
        }
    }
    
    private int a() {
        try {
            if (this.b == null) {
                return 0;
            }
            final int d = this.b.d(0);
            if (d == 0) {
                return 0;
            }
            final byte[] array = new byte[d];
            this.b.a(array, 0, 0);
            final psc_h psc_h = new psc_h(0, true, 0);
            final psc_j psc_j = new psc_j();
            final psc_k psc_k = new psc_k(12288, true, 0, array, 0, array.length);
            if (this.c == null) {
                return 0;
            }
            psc_p psc_p;
            if ((this.c[0] & 0x80) >> 7 == 0) {
                psc_p = new psc_p(0, true, 0, this.c, 0, this.c.length, true);
            }
            else {
                psc_p = new psc_p(0, true, 0, this.c, 0, this.c.length, false);
            }
            final psc_n psc_n = new psc_n(new psc_i[] { psc_h, psc_k, psc_p, psc_j });
            final byte[] array2 = new byte[psc_n.a()];
            psc_n.a(array2, 0);
            if (this.d == null) {
                return 0;
            }
            if (this.e == null) {
                return 0;
            }
            this.h = new psc_n(new psc_i[] { new psc_h(psc_ny.g, true, 0), new psc_p(0, true, 0, this.a), new psc_k(12288, true, 0, array2, 0, array2.length), new psc_k(12288, true, 0, this.d, 0, this.d.length), new psc_t(0, true, 0, this.e, 0, this.e.length), psc_j });
            return this.h.a();
        }
        catch (psc_m psc_m) {
            return 0;
        }
        catch (psc_v psc_v) {
            return 0;
        }
    }
    
    public boolean equals(final Object o) {
        if (o == null || !(o instanceof psc_ny)) {
            return false;
        }
        final psc_ny psc_ny = (psc_ny)o;
        if (this.a != psc_ny.a) {
            return false;
        }
        if (this.b != null) {
            if (!this.b.equals(psc_ny.b)) {
                return false;
            }
        }
        else if (psc_ny.b != null) {
            return false;
        }
        return psc_k4.a(this.c, psc_ny.c) && psc_k4.a(this.d, psc_ny.d) && psc_k4.a(this.e, psc_ny.e);
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_ny psc_ny = new psc_ny();
        psc_ny.a = this.a;
        if (this.b != null) {
            psc_ny.b = (psc_u)this.b.clone();
        }
        if (this.c != null) {
            psc_ny.c = new byte[this.c.length];
            System.arraycopy(this.c, 0, psc_ny.c, 0, this.c.length);
        }
        if (this.d != null) {
            psc_ny.d = new byte[this.d.length];
            System.arraycopy(this.d, 0, psc_ny.d, 0, this.d.length);
        }
        if (this.e != null) {
            psc_ny.e = new byte[this.e.length];
            System.arraycopy(this.e, 0, psc_ny.e, 0, this.e.length);
        }
        psc_ny.g = psc_ny.g;
        if (this.h != null) {
            psc_ny.a();
        }
        return psc_ny;
    }
}
