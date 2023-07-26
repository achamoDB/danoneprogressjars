import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_nv extends psc_m2 implements Cloneable, Serializable
{
    protected int a;
    protected byte[] b;
    private String c;
    private byte[] d;
    
    public psc_nv(final psc_ah o, final psc_nq n) {
        this.a = -1;
        this.d = null;
        super.k = 5;
        super.o = o;
        super.n = n;
    }
    
    public void a(final psc_m2 psc_m2) throws psc_m3 {
        if (psc_m2 == null) {
            throw new psc_m3("Unable to set content: content is null.");
        }
        try {
            super.m = (psc_m2)psc_m2.clone();
        }
        catch (CloneNotSupportedException ex) {
            throw new psc_m3("Unable to clone ContentInfo type");
        }
    }
    
    public void a(final int a) {
        this.a = a;
    }
    
    public int d() {
        return this.a;
    }
    
    public void c(final String c) throws psc_m3 {
        this.b = a(c);
        this.c = c;
    }
    
    static byte[] a(final String s) throws psc_m3 {
        if (s == null) {
            throw new psc_m3("Could not set digest algorithm: algName is null");
        }
        try {
            return psc_da.a(s, "Java").c();
        }
        catch (psc_ap psc_ap) {
            throw new psc_m3("Could not set algorithm algorithm: algName is invalid");
        }
    }
    
    public void c(final byte[] array, final int n, final int n2) throws psc_m3 {
        this.b = a(array, n, n2);
    }
    
    static byte[] a(final byte[] array, final int n, final int n2) throws psc_m3 {
        if (array == null || n2 <= 0) {
            throw new psc_m3("Could not set algorithm OID: oid is null.");
        }
        if (n < 0 || n + n2 > array.length) {
            throw new psc_m3("Invalid digest algorithm identifier.");
        }
        final byte[] array2 = new byte[n2];
        System.arraycopy(array, n, array2, 0, n2);
        return array2;
    }
    
    public byte[] e() {
        return this.b;
    }
    
    public String f() throws psc_m3 {
        return a(this.b, this.c);
    }
    
    static String a(final byte[] array, final String s) throws psc_m3 {
        if (array == null) {
            return null;
        }
        if (s != null) {
            return s;
        }
        try {
            return psc_da.a(array, 0, "Java").f();
        }
        catch (psc_ap psc_ap) {
            throw new psc_m3("Could not set algorithm OID: " + psc_ap.getMessage());
        }
    }
    
    protected int m() throws psc_m3 {
        return this.a();
    }
    
    protected int b(final byte[] array, final int n) throws psc_m3 {
        if (array == null) {
            throw new psc_m3("Cannot write DigestedData: output array is null.");
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
            throw new psc_m3("Unable to DER encode DigestedData message: " + psc_m.getMessage());
        }
    }
    
    private int a() throws psc_m3 {
        if (super.m == null) {
            throw new psc_m3("content is not set.");
        }
        if (super.af == 0) {
            this.d = this.b();
        }
        if (this.a == -1) {
            this.a = 0;
        }
        if (super.ac == null) {
            super.ac = new byte[super.m.l()];
            super.m.a(super.ac, 0);
        }
        try {
            final psc_k psc_k = new psc_k(0, true, 0, super.ac, 0, super.ac.length);
            final psc_h psc_h = new psc_h(10551296, true, 0);
            final psc_j psc_j = new psc_j();
            final psc_p psc_p = new psc_p(0, true, 0, this.a);
            final psc_k psc_k2 = new psc_k(12288, true, 0, this.b, 0, this.b.length);
            int length = 0;
            if (this.d != null) {
                length = this.d.length;
            }
            final psc_t psc_t = new psc_t(0, true, 0, this.d, 0, length);
            (super.p = new psc_i[6])[0] = psc_h;
            super.p[1] = psc_p;
            super.p[2] = psc_k2;
            super.p[3] = psc_k;
            super.p[4] = psc_t;
            super.p[5] = psc_j;
            super.q = new psc_n(super.p);
            return super.q.a();
        }
        catch (psc_m psc_m) {
            throw new psc_m3(psc_m.getMessage());
        }
    }
    
    public byte[] t() {
        if (this.d == null) {
            return null;
        }
        final byte[] array = new byte[this.d.length];
        System.arraycopy(this.d, 0, array, 0, this.d.length);
        return array;
    }
    
    private byte[] b() throws psc_m3 {
        byte[] array = null;
        try {
            if (super.ac == null) {
                super.ac = new byte[super.m.l()];
                super.m.a(super.ac, 0);
            }
            final psc_h psc_h = new psc_h(0);
            final psc_j psc_j = new psc_j();
            final psc_r psc_r = new psc_r(16777216);
            final psc_k psc_k = new psc_k(10616576);
            psc_l.a(super.ac, 0, new psc_i[] { psc_h, psc_r, psc_k, psc_j });
            if (psc_k.a) {
                int n = 1 + psc_o.a(psc_k.b, psc_k.c + 1);
                final int n2 = ++n + psc_o.a(psc_k.b, psc_k.c + n);
                array = new byte[psc_k.d - n2];
                System.arraycopy(psc_k.b, psc_k.c + n2, array, 0, psc_k.d - n2);
            }
        }
        catch (psc_m psc_m) {
            throw new psc_m3("Could not DER encode ContentInfo: " + psc_m.getMessage());
        }
        try {
            if (array == null) {
                return null;
            }
            final psc_da a = psc_da.a(this.b, 0, this.h());
            final byte[] array2 = new byte[a.g()];
            a.i();
            a.a(array, 0, array.length);
            final byte[] j = a.j();
            a.y();
            return j;
        }
        catch (psc_ap psc_ap) {
            throw new psc_m3("Could not digest ContentInfo: " + psc_ap.getMessage());
        }
    }
    
    protected boolean k(final byte[] array, final int n, final int n2) throws psc_m3 {
        try {
            final psc_h psc_h = new psc_h(10551296);
            final psc_j psc_j = new psc_j();
            final psc_p psc_p = new psc_p(0);
            final psc_k psc_k = new psc_k(12288);
            final psc_k psc_k2 = new psc_k(12288, true, 0, super.l, null, 0, 0);
            final psc_t psc_t = new psc_t(0);
            (super.p = new psc_i[6])[0] = psc_h;
            super.p[1] = psc_p;
            super.p[2] = psc_k;
            super.p[3] = psc_k2;
            super.p[4] = psc_t;
            super.p[5] = psc_j;
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
            if (this.b == null) {
                if (super.p[2].f()) {
                    if (super.ab == 2) {
                        this.b = this.a(super.p[2]);
                    }
                    else {
                        this.b = new byte[super.p[2].d];
                        System.arraycopy(super.p[2].b, super.p[2].c, this.b, 0, super.p[2].d);
                    }
                }
                else {
                    if (super.p[2].b == null) {
                        return;
                    }
                    super.ab = 2;
                    this.c(super.p[2].b, super.p[2].c, super.p[2].d, 0);
                    return;
                }
            }
            if (super.m == null) {
                if (super.p[3].f()) {
                    if (super.ab == 3) {
                        final byte[] a2 = this.a(super.p[3]);
                        this.b(a2, 0, a2.length);
                    }
                    else {
                        this.b(super.p[3].b, super.p[3].c, super.p[3].d);
                    }
                }
                else {
                    if (super.p[3].b == null) {
                        return;
                    }
                    super.ab = 3;
                    this.c(super.p[3].b, super.p[3].c, super.p[3].d, super.l);
                    return;
                }
            }
            final byte[] b = this.b();
            if (this.d == null) {
                if (super.p[4].f()) {
                    if (super.p[4].b == null && b == null) {
                        return;
                    }
                    if (super.ab == 4) {
                        this.d = this.a(super.p[4]);
                    }
                    else if (super.p[4].b != null) {
                        this.d = new byte[super.p[4].d];
                        System.arraycopy(super.p[4].b, super.p[4].c, this.d, 0, super.p[4].d);
                    }
                    if (!psc_k4.a(this.d, b)) {
                        throw new psc_m3("Invalid digest.");
                    }
                }
                else {
                    if (super.p[4].b == null) {
                        return;
                    }
                    super.ab = 4;
                    this.c(super.p[4].b, super.p[4].c, super.p[4].d, 0);
                }
            }
        }
        catch (Exception ex) {
            throw new psc_m3("Cannot set decoded values." + ex.getMessage());
        }
    }
    
    private void b(final byte[] array, final int n, final int n2) throws psc_m3 {
        if (array == null || n2 == 0) {
            throw new psc_m3("Cannot decode DigestedData: data is null.");
        }
        try {
            final psc_h psc_h = new psc_h(0);
            final psc_j psc_j = new psc_j();
            final psc_r psc_r = new psc_r(16777216);
            psc_l.a(array, n, new psc_i[] { psc_h, psc_r, new psc_k(10616576, true, 0, super.l, null, 0, 0), psc_j });
            super.m = psc_m2.a(psc_r.b, psc_r.c, psc_r.d, super.o, super.n);
            if (super.m.b(array, n, n2, super.l) && super.m.n()) {
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
        final psc_nv psc_nv = (psc_nv)super.clone();
        psc_nv.a = this.a;
        if (this.d != null) {
            psc_nv.d = this.d.clone();
        }
        if (this.b != null) {
            psc_nv.b = this.b.clone();
        }
        return psc_nv;
    }
    
    public boolean equals(final Object o) {
        if (o == null || !(o instanceof psc_nv)) {
            return false;
        }
        final psc_nv psc_nv = (psc_nv)o;
        return this.a == psc_nv.a && (super.m == null || super.m.equals(psc_nv.m)) && psc_k4.a(this.d, psc_nv.d) && psc_k4.a(this.b, psc_nv.b);
    }
    
    public void s() {
        super.s();
        this.a = -1;
        this.b = null;
        this.d = null;
    }
    
    protected void finalize() {
        this.s();
    }
}
