import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public abstract class psc_m2 implements Cloneable, Serializable
{
    public static final int a = 1;
    public static final int b = 2;
    public static final int c = 3;
    public static final int d = 5;
    public static final int e = 6;
    public static final int f = 0;
    public static final int g = 0;
    public static final int h = 1;
    static final byte[] i;
    static final int j = 9;
    protected int k;
    protected int l;
    protected psc_m2 m;
    protected psc_nq n;
    protected psc_ah o;
    protected psc_i[] p;
    protected psc_n q;
    private psc_i[] r;
    protected psc_n s;
    protected int t;
    protected int u;
    protected String v;
    protected String[] w;
    protected byte[] x;
    protected int y;
    protected int z;
    protected byte[] aa;
    protected int ab;
    protected byte[] ac;
    protected final int ad = 0;
    protected final int ae = 1;
    protected int af;
    
    protected psc_m2() {
        this.l = 0;
        this.aa = null;
        this.ab = 0;
        this.ac = null;
        this.af = 0;
        this.t = 0;
    }
    
    public static psc_m2 a(final int n, final psc_ah psc_ah, final psc_nq psc_nq) throws psc_m3 {
        switch (n) {
            case 1: {
                return new psc_m4();
            }
            case 2: {
                return new psc_nt(psc_ah, psc_nq);
            }
            case 3: {
                return new psc_nu(psc_ah, psc_nq);
            }
            case 5: {
                return new psc_nv(psc_ah, psc_nq);
            }
            case 6: {
                return new psc_nr(psc_ah, psc_nq);
            }
            default: {
                throw new psc_m3("Cannot build PKCS #7 content object: unknown type.");
            }
        }
    }
    
    public static psc_m2 a(final byte[] array, final int n, final int n2, final psc_ah psc_ah, final psc_nq psc_nq) throws psc_m3 {
        if (array == null || n2 <= 0) {
            throw new psc_m3("Cannot build PKCS #7 content object: OID is null.");
        }
        if (n < 0 || n + n2 > array.length) {
            throw new psc_m3("Invalid content type.");
        }
        if (n2 != 9) {
            throw new psc_m3("Cannot build PKCS #7 content object: unknown OID.");
        }
        for (int i = 0; i < 7; ++i) {
            if (array[i + n] != psc_m2.i[i]) {
                throw new psc_m3("Cannot build PKCS #7 content object: unknown OID.");
            }
        }
        switch (array[n2 + n - 1] & 0xFF) {
            case 1: {
                return new psc_m4();
            }
            case 2: {
                return new psc_nt(psc_ah, psc_nq);
            }
            case 3: {
                return new psc_nu(psc_ah, psc_nq);
            }
            case 5: {
                return new psc_nv(psc_ah, psc_nq);
            }
            case 6: {
                return new psc_nr(psc_ah, psc_nq);
            }
            default: {
                throw new psc_m3("Cannot build PKCS #7 content object: unknown OID.");
            }
        }
    }
    
    public static int h(final byte[] array, final int n, final int n2) throws psc_m3 {
        return a(array, n, n2, 0);
    }
    
    public static int a(final byte[] array, final int n, final int n2, final int n3) throws psc_m3 {
        if (array == null || n2 <= 0) {
            throw new psc_m3("Cannot read the message: data is null.");
        }
        if (n < 0 || n + n2 > array.length) {
            throw new psc_m3("Invalid message data");
        }
        try {
            final psc_i[] array2 = { new psc_h(n3), new psc_r(16777216), new psc_k(10616576), new psc_j() };
            final psc_n psc_n = new psc_n(array2);
            psc_n.c();
            psc_n.a(array, n, n2);
            if (!array2[1].f()) {
                return -1;
            }
            if (array2[1].d != 9) {
                throw new psc_m3("Unknown OID.");
            }
            return array2[1].b[array2[1].c + array2[1].d - 1];
        }
        catch (psc_m psc_m) {
            throw new psc_m3("Cannot read message: " + psc_m.getMessage());
        }
    }
    
    public void b(final String v) {
        this.v = v;
    }
    
    public String g() {
        if (this.v != null) {
            return this.v;
        }
        if (this.o != null) {
            return this.o.f();
        }
        return null;
    }
    
    protected String h() {
        final String g = this.g();
        if (g == null) {
            return new String("Java");
        }
        return g;
    }
    
    public String[] i() {
        String[] array = null;
        if (this.w != null) {
            array = new String[this.w.length];
            for (int i = 0; i < this.w.length; ++i) {
                array[i] = this.w[i];
            }
        }
        else if (this.v != null) {
            array = new String[] { this.v };
        }
        else if (this.o != null) {
            array = new String[] { this.o.f() };
        }
        return array;
    }
    
    public int j() {
        return this.k;
    }
    
    public void a(final psc_ah o) {
        this.o = o;
    }
    
    public void a(final psc_nq n) {
        this.n = n;
    }
    
    public psc_m2 k() {
        return this.m;
    }
    
    public int l() throws psc_m3 {
        return this.b(0);
    }
    
    public int b(final int n) throws psc_m3 {
        this.r = this.a(true, n);
        this.s = new psc_n(this.r);
        try {
            this.t = this.s.a();
        }
        catch (psc_m psc_m) {
            throw new psc_m3(psc_m.getMessage());
        }
        return this.t;
    }
    
    protected abstract int m() throws psc_m3;
    
    public int a(final byte[] array, final int n) throws psc_m3 {
        return this.i(array, n, 0);
    }
    
    public int i(final byte[] array, int n, final int n2) throws psc_m3 {
        if (array == null || n < 0 || array.length == 0) {
            throw new psc_m3("Cannot write message: output array is null.");
        }
        final int n3 = n;
        if (this.t == 0) {
            this.b(n2);
        }
        try {
            n += this.s.a(array, n);
            array[n - 1] = (byte)this.k;
        }
        catch (psc_m psc_m) {
            throw new psc_m3("Cannot encode contentInfo: " + psc_m.getMessage());
        }
        if (this.aa != null) {
            System.arraycopy(this.aa, 0, array, n, this.aa.length);
            n += this.aa.length;
        }
        else {
            n += this.b(array, n);
        }
        this.t = 0;
        return n - n3;
    }
    
    protected abstract int b(final byte[] p0, final int p1) throws psc_m3;
    
    public boolean j(final byte[] array, final int n, final int n2) throws psc_m3 {
        return this.b(array, n, n2, 0);
    }
    
    public boolean b(final byte[] array, final int n, final int n2, final int n3) throws psc_m3 {
        return this.a(array, n, n2, n3, 0);
    }
    
    public boolean a(final byte[] array, final int n, final int n2, final int n3, final int l) throws psc_m3 {
        if (array == null || n2 <= 0) {
            throw new psc_m3("Cannot read message: data is null.");
        }
        if (n < 0 || n + n2 > array.length) {
            throw new psc_m3("Invalid message data");
        }
        try {
            this.l = l;
            this.r = this.a(false, n3);
            (this.s = new psc_n(this.r)).c();
            this.u = this.s.a(array, n, n2);
        }
        catch (psc_m psc_m) {
            throw new psc_m3("Cannot read message: " + psc_m.getMessage());
        }
        if (!this.r[1].f()) {
            return false;
        }
        this.aa = new byte[this.r[2].d];
        if (this.aa.length > 0) {
            System.arraycopy(this.r[2].b, this.r[2].c, this.aa, 0, this.r[2].d);
            this.k(this.r[2].b, this.r[2].c, this.r[2].d);
        }
        return true;
    }
    
    protected abstract boolean k(final byte[] p0, final int p1, final int p2) throws psc_m3;
    
    public int l(final byte[] array, final int n, final int n2) throws psc_m3 {
        if (array == null || n2 <= 0) {
            throw new psc_m3("Cannot read message: data is null.");
        }
        if (n < 0 || n + n2 > array.length) {
            throw new psc_m3("Invalid message data");
        }
        if (this.s == null) {
            throw new psc_m3("Need to call readInit() before readUpdate().");
        }
        try {
            this.u += this.s.a(array, n, n2);
        }
        catch (psc_m psc_m) {
            throw new psc_m3("Cannot readUpdate message: " + psc_m.getMessage());
        }
        if (this.aa != null) {
            final byte[] array2 = new byte[this.aa.length];
            System.arraycopy(this.aa, 0, array2, 0, this.aa.length);
            System.arraycopy(array2, 0, this.aa = new byte[array2.length + n2], 0, array2.length);
            System.arraycopy(array, n, this.aa, array2.length, n2);
        }
        return this.m(array, n, n2);
    }
    
    protected abstract int m(final byte[] p0, final int p1, final int p2) throws psc_m3;
    
    public boolean n() throws psc_m3 {
        if (this.s == null) {
            throw new psc_m3("Need to call readInit() before readFinal().");
        }
        try {
            this.s.d();
        }
        catch (psc_m psc_m) {
            throw new psc_m3("Cannot call readFinal: " + psc_m.getMessage());
        }
        if (!this.s.e()) {
            throw new psc_m3("Cannot call readFinal, more message expected.");
        }
        return true;
    }
    
    public int o() {
        return this.u;
    }
    
    public boolean p() {
        return this.p[0].f();
    }
    
    public int q() {
        if (this.x == null) {
            return 0;
        }
        return this.x.length;
    }
    
    protected int c(final byte[] array, final int n, final int n2, final int n3) {
        if (array == null) {
            return 0;
        }
        if (this.x == null) {
            if (n3 != 0) {
                this.x = new byte[n3];
            }
            else {
                this.x = new byte[n2];
            }
        }
        if (this.x.length - this.y < n2) {
            final byte[] array2 = new byte[this.x.length];
            System.arraycopy(this.x, 0, array2, 0, this.x.length);
            System.arraycopy(array2, 0, this.x = new byte[n2 + this.x.length], 0, array2.length);
            this.y = array2.length;
        }
        System.arraycopy(array, n, this.x, this.y, n2);
        this.y += n2;
        return n2;
    }
    
    protected byte[] a(final psc_i psc_i) {
        byte[] array;
        if (this.x == null) {
            array = new byte[psc_i.d];
            System.arraycopy(psc_i.b, psc_i.c, array, 0, psc_i.d);
        }
        else if (this.l != 0 && this.x.length >= this.l) {
            System.arraycopy(psc_i.b, psc_i.c, this.x, this.y, psc_i.d);
            array = new byte[this.x.length];
            System.arraycopy(this.x, 0, array, 0, this.x.length);
        }
        else {
            array = new byte[psc_i.d + this.x.length];
            System.arraycopy(this.x, 0, array, 0, this.x.length);
            System.arraycopy(psc_i.b, psc_i.c, array, this.x.length, psc_i.d);
        }
        this.d(this.x);
        this.x = null;
        this.y = 0;
        this.ab = 0;
        return array;
    }
    
    protected void d(final byte[] array) {
        if (array != null) {
            for (int i = 0; i < array.length; ++i) {
                array[i] = 0;
            }
        }
    }
    
    psc_i[] a(final boolean b, final int n) throws psc_m3 {
        final psc_h psc_h = new psc_h(n, true, 0);
        final psc_j psc_j = new psc_j();
        int n2 = 0;
        if (b) {
            if (this.aa != null) {
                n2 = this.aa.length;
            }
            else {
                n2 = this.m();
            }
        }
        try {
            return new psc_i[] { psc_h, new psc_r(16777216, true, 0, psc_m2.i, 0, 9), new psc_k(10616576, true, 0, this.l, null, 0, n2), psc_j };
        }
        catch (psc_m psc_m) {
            throw new psc_m3("ContentInfo.getASN1Containers: " + psc_m.getMessage());
        }
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_m2 psc_m2 = (psc_m2)super.clone();
        psc_m2.v = this.v;
        psc_m2.w = this.i();
        psc_m2.k = this.k;
        if (this.m != null) {
            psc_m2.m = (psc_m2)this.m.clone();
        }
        psc_m2.n = this.n;
        psc_m2.o = this.o;
        try {
            if (this.s != null) {
                psc_m2.l();
            }
        }
        catch (psc_m3 psc_m3) {
            throw new CloneNotSupportedException("Could not copy ASN1Template.");
        }
        return psc_m2;
    }
    
    public void r() {
        this.aa = null;
    }
    
    public void s() {
        if (this.m != null) {
            this.m.s();
        }
        this.k = 0;
        this.m = null;
        this.v = null;
        this.w = null;
        this.s = null;
        this.q = null;
        this.t = 0;
        this.d(this.x);
        this.x = null;
        this.y = 0;
        this.ab = 0;
        this.n = null;
        this.o = null;
    }
    
    protected void finalize() {
        this.s();
    }
    
    static {
        i = new byte[] { 42, -122, 72, -122, -9, 13, 1, 7, 0 };
    }
}
