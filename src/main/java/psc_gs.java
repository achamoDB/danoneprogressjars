import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_gs implements Cloneable, Serializable
{
    private byte[] a;
    private String b;
    private int c;
    private byte[] d;
    private byte[] e;
    protected static int f;
    private psc_n g;
    
    public psc_gs(final byte[] array, final int n, final int n2) throws psc_v {
        this.a = null;
        this.b = null;
        this.c = -1;
        this.d = null;
        this.e = null;
        this.g = null;
        if (array == null || n < 0) {
            throw new psc_v("Encoding is null.");
        }
        try {
            final psc_h psc_h = new psc_h(n2);
            final psc_k psc_k = new psc_k(12288);
            final psc_t psc_t = new psc_t(0);
            final psc_j psc_j = new psc_j();
            final psc_z psc_z = new psc_z(0);
            final psc_p psc_p = new psc_p(0);
            final psc_r psc_r = new psc_r(16777216);
            final psc_ag psc_ag = new psc_ag(65536);
            psc_l.a(array, n, new psc_i[] { psc_h, psc_z, psc_p, psc_r, psc_j, psc_k, psc_t, psc_ag, psc_j });
            if (psc_p.a) {
                this.c = psc_p.e();
            }
            else {
                this.d = new byte[psc_r.d];
                System.arraycopy(psc_r.b, psc_r.c, this.d, 0, psc_r.d);
            }
            this.e = new byte[psc_k.d];
            System.arraycopy(psc_k.b, psc_k.c, this.e, 0, psc_k.d);
            if (psc_q.b(this.e, 0, 10, null) == null) {
                throw new psc_v("Unknown or invalid hash algorithm.");
            }
            this.a = new byte[psc_t.d];
            System.arraycopy(psc_t.b, psc_t.c, this.a, 0, psc_t.d);
            this.b = psc_ag.e();
        }
        catch (psc_m psc_m) {
            throw new psc_v("Could not BER decode the Biometric Data info." + psc_m.getMessage());
        }
    }
    
    public psc_gs() {
        this.a = null;
        this.b = null;
        this.c = -1;
        this.d = null;
        this.e = null;
        this.g = null;
    }
    
    public void a(final byte[] array, final int n, final int n2) {
        if (array != null && n2 > 0 && n >= 0 && array.length >= n2 + n) {
            System.arraycopy(array, n, this.a = new byte[n2], 0, n2);
        }
        else {
            this.a = null;
        }
    }
    
    public byte[] b() {
        if (this.a == null) {
            return null;
        }
        final byte[] array = new byte[this.a.length];
        System.arraycopy(this.a, 0, array, 0, this.a.length);
        return array;
    }
    
    public void a(final String b) {
        this.b = b;
    }
    
    public String c() {
        return this.b;
    }
    
    public void a(final int c) {
        this.c = c;
        this.d = null;
    }
    
    public int d() {
        return this.c;
    }
    
    public void b(final byte[] array, final int n, final int n2) {
        if (array != null && n2 > 0 && n >= 0 && array.length >= n2 + n) {
            System.arraycopy(array, n, this.d = new byte[n2], 0, n2);
            this.c = -1;
        }
        else {
            this.d = null;
        }
    }
    
    public byte[] e() {
        if (this.d == null) {
            return null;
        }
        final byte[] array = new byte[this.d.length];
        System.arraycopy(this.d, 0, array, 0, this.d.length);
        return array;
    }
    
    public void b(final String s) throws psc_v {
        try {
            this.e = psc_q.a(s, 10, null, 0, 0);
        }
        catch (psc_m psc_m) {
            throw new psc_v("Invalid hash algorithm. " + psc_m.getMessage());
        }
    }
    
    public String f() throws psc_v {
        try {
            return psc_q.b(this.e, 0, 10, null);
        }
        catch (psc_m psc_m) {
            throw new psc_v("Invalid hash algorithm. " + psc_m.getMessage());
        }
    }
    
    public static int a(final byte[] array, final int n) throws psc_v {
        if (array == null) {
            throw new psc_v("Encoding is null.");
        }
        if (array[n] == 0 && array[n + 1] == 0) {
            return n + 2;
        }
        try {
            return n + 1 + psc_o.a(array, n + 1) + psc_o.b(array, n + 1);
        }
        catch (psc_m psc_m) {
            throw new psc_v("Unable to determine length of the BER. " + psc_m.getMessage());
        }
    }
    
    public int b(final int f) throws psc_v {
        if (this.a == null || this.e == null || (this.d == null && this.c == -1)) {
            throw new psc_v("Biometric data is not set.");
        }
        psc_gs.f = f;
        return this.a();
    }
    
    public int c(final byte[] array, final int n, final int n2) throws psc_v {
        if (array == null) {
            throw new psc_v("Specified array is null.");
        }
        try {
            if ((this.g == null || n2 != psc_gs.f) && this.b(n2) == 0) {
                throw new psc_v("Cannot encode BiometricData.");
            }
            final int a = this.g.a(array, n);
            this.g = null;
            return a;
        }
        catch (psc_m psc_m) {
            this.g = null;
            throw new psc_v("Unable to encode BiometricData." + psc_m.getMessage());
        }
    }
    
    private int a() throws psc_v {
        try {
            final psc_h psc_h = new psc_h(psc_gs.f);
            final psc_k psc_k = new psc_k(12288, true, 0, this.e, 0, this.e.length);
            final psc_t psc_t = new psc_t(0, true, 0, this.a, 0, this.a.length);
            final psc_z psc_z = new psc_z(0);
            final psc_j psc_j = new psc_j();
            psc_i psc_i;
            if (this.c != -1) {
                psc_i = new psc_p(0, true, 0, this.c);
            }
            else {
                if (this.d == null) {
                    throw new psc_v("Type of Biometric Data is not set.");
                }
                psc_i = new psc_r(16777216, true, 0, this.d, 0, this.d.length);
            }
            psc_ag psc_ag;
            if (this.b != null) {
                psc_ag = new psc_ag(65536, true, 0, this.b);
            }
            else {
                psc_ag = new psc_ag(65536, false, 0, null);
            }
            this.g = new psc_n(new psc_i[] { psc_h, psc_z, psc_i, psc_j, psc_k, psc_t, psc_ag, psc_j });
            return this.g.a();
        }
        catch (psc_m psc_m) {
            throw new psc_v("cannot create the DER encoding. " + psc_m.getMessage());
        }
    }
    
    public boolean equals(final Object o) {
        if (o == null || !(o instanceof psc_gs)) {
            return false;
        }
        final psc_gs psc_gs = (psc_gs)o;
        if (this.b != null) {
            if (!this.b.equals(psc_gs.b)) {
                return false;
            }
        }
        else if (psc_gs.b != null) {
            return false;
        }
        return psc_k4.a(this.a, psc_gs.a) && psc_k4.a(this.d, psc_gs.d) && psc_k4.a(this.e, psc_gs.e) && this.c == psc_gs.c;
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_gs psc_gs = new psc_gs();
        if (this.a != null) {
            psc_gs.a = new byte[this.a.length];
            System.arraycopy(this.a, 0, psc_gs.a, 0, this.a.length);
        }
        if (this.d != null) {
            psc_gs.d = new byte[this.d.length];
            System.arraycopy(this.d, 0, psc_gs.d, 0, this.d.length);
        }
        if (this.e != null) {
            psc_gs.e = new byte[this.e.length];
            System.arraycopy(this.e, 0, psc_gs.e, 0, this.e.length);
        }
        psc_gs.c = this.c;
        psc_gs.f = this.c;
        psc_gs.b = new String(this.b);
        psc_gs.f = psc_gs.f;
        try {
            if (this.g != null) {
                psc_gs.a();
            }
        }
        catch (psc_v psc_v) {
            throw new CloneNotSupportedException(psc_v.getMessage());
        }
        return psc_gs;
    }
}
