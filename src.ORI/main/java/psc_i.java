// 
// Decompiled by Procyon v0.5.36
// 

public abstract class psc_i
{
    public boolean a;
    public byte[] b;
    public int c;
    public int d;
    protected int e;
    protected int f;
    protected int g;
    protected int h;
    protected int i;
    protected int j;
    protected int k;
    protected int l;
    protected int m;
    boolean n;
    boolean o;
    protected boolean p;
    protected int q;
    static final int r = 65535;
    static final int s = 65536;
    static final int t = 131072;
    static final int u = 262144;
    protected static final int v = 1048576;
    protected static final int w = 2097152;
    static final int x = 4194304;
    protected static final int y = 16777216;
    protected static final int z = 33554432;
    static final int aa = 268435456;
    static final int ab = 536870912;
    protected int ac;
    protected boolean ad;
    protected int ae;
    
    protected psc_i() {
        this.e = -1;
        this.h = -1;
        this.l = 0;
        this.p = false;
        this.ac = 0;
        this.ad = false;
        this.ae = 0;
    }
    
    protected psc_i(final int n, final int n2) {
        this(n, true, 0, n2);
    }
    
    protected psc_i(final int n, final int n2, final int n3) {
        this(n, true, 0, n2, n3);
    }
    
    protected psc_i(final int n, final boolean b, final int n2, final int n3) {
        this(n, b, n2, n3, 0);
    }
    
    protected psc_i(final int l, final boolean a, final int m, final int i, final int ae) {
        this.e = -1;
        this.h = -1;
        this.l = 0;
        this.p = false;
        this.ac = 0;
        this.ad = false;
        this.ae = 0;
        if (l >= 0) {
            this.l = l;
        }
        this.a = a;
        this.m = m;
        this.i = i;
        this.ae = ae;
        this.j = 0;
        if ((i & 0x2000) != 0x0) {
            this.n = true;
        }
    }
    
    public void c(final int h) throws psc_m {
        if (this.h != -1) {
            throw new psc_m("Extended tag already set.");
        }
        if (h < 0 || h > 33554431) {
            throw new psc_m("Invalid extended tag value.");
        }
        if (h < 31) {
            this.l |= h;
            return;
        }
        this.h = h;
    }
    
    protected void c() {
        if ((this.q & 0x2000000) != 0x0) {
            this.q &= 0xFDFFFFFF;
            this.q |= 0x20000;
        }
        if ((this.l & 0x20000) != 0x0) {
            this.m = 0;
        }
        this.g = this.i >>> 8;
        final int n = this.l & 0xE00000;
        if (n == 0) {
            return;
        }
        int n2 = n >>> 16;
        if (this.n) {
            n2 |= 0x20;
        }
        int n3;
        if (this.h == -1) {
            n3 = (n2 | (this.l & 0xFF));
        }
        else {
            final int n4 = n2 | 0x1F;
            int h = this.h;
            int n5 = 0;
            int n6 = 0;
            while (true) {
                int n7 = (h & 0x7F) << n6;
                if (n6 != 0) {
                    n7 |= 128 << n6;
                }
                n5 |= n7;
                h >>>= 7;
                if (h == 0) {
                    break;
                }
                n6 += 8;
            }
            n3 = (n4 << n6 + 8 | n5);
        }
        if ((this.l & 0x200000) != 0x0) {
            this.e = n3;
        }
        else {
            this.g = n3;
        }
    }
    
    public boolean f() {
        return (this.q & 0x2000000) != 0x0;
    }
    
    public boolean g() {
        return (this.q & 0x100000) != 0x0;
    }
    
    public void a(final byte[] b, final int c, final int d, final boolean b2, final boolean b3) throws psc_m {
        if (b2) {
            if ((this.q & 0x2000000) != 0x0) {
                this.q ^= 0x2000000;
            }
        }
        else {
            if ((this.l & 0x4000000) == 0x0) {
                throw new psc_m("This type is not allowed to accept new data.");
            }
            if ((this.q & 0x2000000) != 0x0) {
                throw new psc_m("The container cannot accept new data, already encoded.");
            }
        }
        if ((this.q & 0x40000) != 0x0 && b != null) {
            throw new psc_m("The container cannot accept new data any more.");
        }
        this.b = b;
        this.q |= 0x20000;
        if (b == null) {
            if (!b3 || (this.l & 0x4000000) == 0x0) {
                throw new psc_m("Cannot add null newData unless dataComplete is true.");
            }
            this.c = 0;
            this.d = 0;
            this.q ^= 0x20000;
            this.q |= 0x40000;
        }
        else {
            if (b == null) {
                throw new psc_m("ASN1Container.addData: encoding should not be null.");
            }
            if (c < 0 || c >= b.length) {
                throw new psc_m("ASN1Container.addData: offset is out of range.");
            }
            if (d < 0 || c + d > b.length) {
                throw new psc_m("ASN1Container.addData: len is out of range.");
            }
            this.c = c;
            this.d = d;
            this.a = true;
            if (b3) {
                this.q |= 0x40000;
            }
        }
    }
    
    public void h() throws psc_m {
        this.q |= 0x40000;
    }
    
    int a(final psc_i[] array, final int n) throws psc_m {
        this.c();
        this.q |= 0x40000;
        if (!this.a) {
            return this.a(false, null, 0);
        }
        if (this.o) {
            return this.d;
        }
        this.k = this.d;
        final int f = psc_o.a(this.g) + this.k + psc_o.b(this.d);
        if (this.e == -1) {
            return f;
        }
        this.f = f;
        return psc_o.a(this.e) + this.f + psc_o.b(f);
    }
    
    int a(final psc_i[] array, final int n, final byte[] array2, int n2) throws psc_m {
        this.q = 33554432;
        if (!this.a) {
            return this.a(true, array2, n2);
        }
        final int n3 = n2;
        if (this.e != -1) {
            n2 += psc_o.a(array2, n2, this.e);
            n2 += psc_o.b(array2, n2, this.f);
        }
        n2 += psc_o.a(array2, n2, this.g);
        n2 += psc_o.b(array2, n2, this.k);
        if (this.b != null) {
            if (n2 + this.d > array2.length) {
                throw new psc_m("ASN1Container.derEncode: no room in encoding to put data.");
            }
            System.arraycopy(this.b, this.c, array2, n2, this.d);
            n2 += this.d;
        }
        return n2 - n3;
    }
    
    protected int a(final boolean b, final byte[] array, int n) throws psc_m {
        if ((this.l & 0x10000) != 0x0 && this.m == 5) {
            if (b) {
                array[n] = 5;
                array[n + 1] = 0;
            }
            return 2;
        }
        if ((this.l & 0xF0000) != 0x0) {
            return 0;
        }
        int n2 = 2;
        if (this.e != -1) {
            if (b) {
                array[n] = (byte)this.e;
                array[n + 1] = 2;
            }
            n2 += 2;
            n += 2;
        }
        if (b) {
            array[n] = (byte)this.g;
            array[n + 1] = 0;
        }
        return n2;
    }
    
    void b(final psc_i[] array, final int n) throws psc_m {
        this.c();
        this.k = this.d;
        if ((this.l & 0x4000000) != 0x0 && (this.q & 0x40000) == 0x0) {
            this.g |= 0x20;
            this.k = -1;
        }
        else {
            this.q |= 0x40000;
        }
        this.q |= 0x10000;
    }
    
    int b(final psc_i[] array, final int n, final byte[] array2, int n2) throws psc_m {
        if (this.q == 0) {
            throw new psc_m("Cannot continue BER encoding without new data.");
        }
        if ((this.q & 0x2000000) != 0x0) {
            return 0;
        }
        if (!this.a) {
            this.q = 33554432;
            return this.a(true, array2, n2);
        }
        final int n3 = n2;
        if ((this.q & 0x10000) != 0x0) {
            n2 += this.a(array2, n2);
        }
        if ((this.q & 0x20000) != 0x0) {
            n2 += this.c(array, n, array2, n2);
        }
        if ((this.q & 0x40000) != 0x0) {
            n2 += this.b(array2, n2);
        }
        return n2 - n3;
    }
    
    protected int a(final byte[] array, int n) throws psc_m {
        final int n2 = n;
        if (this.e != -1) {
            n += psc_o.a(array, n, this.e);
            array[n++] = -128;
        }
        n += psc_o.a(array, n, this.g);
        if (this.k == -1) {
            array[n++] = -128;
        }
        else {
            n += psc_o.b(array, n, this.k);
        }
        this.q ^= 0x10000;
        return n - n2;
    }
    
    protected int c(final psc_i[] array, final int n, final byte[] array2, int n2) throws psc_m {
        final int n3 = n2;
        if (this.b != null && this.d != 0) {
            if (this.k == -1) {
                if (this.i == 7680 || this.i == 5632 || this.i == 4608 || this.i == 4864 || this.i == 5120 || this.i == 7168) {
                    this.j = this.i;
                    this.i = 1024;
                }
                array2[n2++] = (byte)(this.i >>> 8);
                n2 += psc_o.b(array2, n2, this.d);
            }
            if (n2 + this.d > array2.length) {
                throw new psc_m("ASN1Container.writeDataBER: no room in encoding to put data.");
            }
            System.arraycopy(this.b, this.c, array2, n2, this.d);
            n2 += this.d;
        }
        this.q ^= 0x20000;
        return n2 - n3;
    }
    
    protected int b(final byte[] array, int n) {
        this.q = 33554432;
        final int n2 = n;
        if (this.k == -1) {
            array[n + 1] = (array[n] = 0);
            n += 2;
        }
        if (this.e != -1) {
            array[n + 1] = (array[n] = 0);
            n += 2;
        }
        if (this.j != 0) {
            this.i = this.j;
            this.g = this.i >>> 8;
        }
        return n - n2;
    }
    
    void d() {
        this.a();
        this.c();
        this.q = 1048576;
        if (this.e != -1) {
            this.q |= 0x200000;
        }
        this.a = true;
    }
    
    void a() {
        this.f = 0;
        this.k = 0;
        this.i();
    }
    
    int a(final psc_n psc_n, final int n, final byte[] array, int n2, final int n3) throws psc_m {
        this.i();
        final int n4 = n2;
        this.ac = n3 - n2;
        if ((this.q & 0x100000) != 0x0) {
            final int b = this.b(psc_n, n, array, n2, this.ac);
            if ((this.q & 0x1000000) != 0x0 || (this.q & 0x2000000) != 0x0) {
                return b;
            }
            this.q |= 0x400000;
            n2 += b;
            this.ac -= b;
        }
        if (this.ac >= 1 || this.k == 0) {
            do {
                n2 += this.a(psc_n, n, array, n2);
            } while ((this.q & 0x1000000) == 0x0 && (this.q & 0x2000000) == 0x0 && this.ac >= 1);
        }
        return n2 - n4;
    }
    
    protected int a(final psc_n psc_n, final int n, final byte[] b, int c) throws psc_m {
        final int n2 = c;
        if (this.k > 0) {
            int d = this.k;
            if (this.ac < this.k) {
                d = this.ac;
            }
            if (this.ad) {
                this.a(b, c, d);
            }
            else {
                if (this.ae != 0 && this.ae >= d) {
                    System.arraycopy(b, c, this.b = new byte[this.ae], 0, d);
                    this.c = 0;
                }
                else {
                    this.b = b;
                    this.c = c;
                }
                this.d = d;
            }
            this.k -= d;
            c += d;
            if (this.k > 0) {
                this.q |= 0x1000000;
                return c - n2;
            }
            this.ac -= d;
        }
        final int a = this.a(psc_n, b, c, this.ac);
        c += a;
        this.ac -= a;
        if ((this.q & 0x1000000) != 0x0 || (this.q & 0x2000000) != 0x0) {
            return c - n2;
        }
        this.q |= 0x100000;
        final int b2 = this.b(psc_n, n, b, c, this.ac);
        c += b2;
        if ((this.q & 0x1000000) != 0x0) {
            return c - n2;
        }
        this.ac -= b2;
        if (this.ac < 1) {
            this.q |= 0x1000000;
        }
        this.ad = true;
        return c - n2;
    }
    
    protected int a(final psc_n psc_n, final byte[] array, int n, int n2) throws psc_m {
        int n3 = this.q & 0xFFFF;
        if (n3 == 0) {
            this.q = 33554432;
            return 0;
        }
        if (n2 < 1) {
            return 0;
        }
        final int n4 = n;
        final int b = this.b(psc_n, array, n, n2);
        n += b;
        if ((this.q & 0x1000000) != 0x0) {
            return n - n4;
        }
        n2 -= b;
        if (psc_n.c[0] == 0) {
            if (psc_n.c[1] != 0) {
                throw new psc_m("Improper ending to indefinite length.");
            }
            this.q -= 2;
            n3 -= 2;
            psc_n.e = 0;
            if (n3 == 0) {
                this.q = 33554432;
                if (this.j != 0) {
                    this.i = this.j;
                    this.g = this.i >>> 8;
                }
                return n - n4;
            }
            n += this.a(psc_n, array, n, n2);
            return n - n4;
        }
        else {
            if (this.f == -1 && n3 == 2) {
                throw new psc_m("Improper ending to indefinite length.");
            }
            return n - n4;
        }
    }
    
    protected void a(final byte[] array, final int n, final int n2) {
        if (n2 < 1) {
            return;
        }
        if (this.b == null && this.ae != 0) {
            this.b = new byte[this.ae];
        }
        if (this.b != null && this.b.length > this.d + n2) {
            System.arraycopy(array, n, this.b, this.d, n2);
            this.d += n2;
            return;
        }
        final byte[] b = new byte[this.d + n2];
        if (this.b != null) {
            System.arraycopy(this.b, this.c, b, 0, this.d);
        }
        System.arraycopy(array, n, b, this.d, n2);
        if (this.p) {
            this.i();
        }
        this.b = b;
        this.c = 0;
        this.d = b.length;
        this.p = true;
    }
    
    protected int b(final psc_n psc_n, final int n, final byte[] array, int n2, int n3) throws psc_m {
        final int n4 = n2;
        final int b = this.b(psc_n, array, n2, n3);
        if ((this.q & 0x1000000) != 0x0) {
            return b;
        }
        n2 += b;
        n3 -= b;
        if ((this.q & 0x200000) != 0x0) {
            if (!this.a(psc_n, this.e)) {
                this.b(psc_n, n);
                return n2 - n4;
            }
            this.q ^= 0x200000;
            this.f = psc_o.b(psc_n.c, psc_n.d);
            if (this.f == -1) {
                this.q += 2;
            }
            psc_n.e = 0;
            final int b2 = this.b(psc_n, array, n2, n3);
            n2 += b2;
            n3 -= b2;
            if ((this.q & 0x1000000) != 0x0) {
                return n2 - n4;
            }
        }
        if (this.a(psc_n, this.g)) {
            this.k = psc_o.b(psc_n.c, psc_n.d);
            if (this.k == -1) {
                if ((psc_n.c[0] & 0x20) == 0x0) {
                    throw new psc_m("Invalid indefinite length octet.");
                }
                this.q += 2;
            }
            this.q ^= 0x100000;
            psc_n.e = 0;
            return n2 - n4;
        }
        final int n5 = 32 << (psc_n.d - 1) * 8 | this.g;
        if ((this.l & 0x4000000) != 0x0 && this.a(psc_n, n5)) {
            this.k = psc_o.b(psc_n.c, psc_n.d);
            if (this.k != -1) {
                throw new psc_m("Primitive promoted to constructed must be indefinite length.");
            }
            this.q ^= 0x100000;
            this.q += 2;
            if (this.i == 7680 || this.i == 5632 || this.i == 4608 || this.i == 4864 || this.i == 5120 || this.i == 7168) {
                this.j = this.i;
                this.i = 1024;
            }
            this.g = this.i >>> 8;
            psc_n.e = 0;
            return n2 - n4;
        }
        else {
            if (this.e != -1) {
                throw new psc_m("Invalid encoding: expected tag not there.");
            }
            if ((this.l & 0x4000000) != 0x0 && n3 > 1 && array[n4] == 0 && array[n4 + 1] == 0) {
                this.q = 33554432;
                if (this.j != 0) {
                    this.i = this.j;
                    this.g = this.i >>> 8;
                }
                n2 = n4 + 2;
                return n2 - n4;
            }
            this.b(psc_n, n);
            return n2 - n4;
        }
    }
    
    protected int b(final psc_n psc_n, final byte[] array, int n, int n2) throws psc_m {
        this.q |= 0x1000000;
        final int n3 = n;
        int n4;
        for (n4 = this.a(psc_n); n4 > 0 && n2 >= 1; n4 = this.a(psc_n)) {
            psc_n.c[psc_n.e] = array[n];
            --n4;
            --n2;
            ++psc_n.e;
            ++n;
            if (n4 < 1) {}
        }
        if (n4 < 1) {
            this.q ^= 0x1000000;
        }
        return n - n3;
    }
    
    protected int a(final psc_n psc_n) throws psc_m {
        if (psc_n.e == 0) {
            return 1;
        }
        int d = 1;
        psc_n.d = 1;
        if ((psc_n.c[0] & 0x1F) == 0x1F) {
            while (d < psc_n.e && (psc_n.c[d] & 0x80) != 0x0) {
                ++d;
            }
            if (d >= psc_n.e) {
                return 1;
            }
            ++d;
            psc_n.d = d;
        }
        if (d >= psc_n.e) {
            return 1;
        }
        return d + psc_o.a(psc_n.c, d) - psc_n.e;
    }
    
    protected boolean a(final psc_n psc_n, final int n) {
        int n2 = 0;
        for (int i = 0; i < psc_n.d; ++i) {
            n2 = (n2 << 8 | (psc_n.c[i] & 0xFF));
        }
        return n2 == n;
    }
    
    protected void b(final psc_n psc_n, final int n) throws psc_m {
        if ((this.q & 0x400000) != 0x0) {
            throw new psc_m("Invalid encoding: expected tag or data not there.");
        }
        if ((this.l & 0x10000) != 0x0 && psc_n.e > 0 && psc_n.c[0] == 5) {
            this.a = false;
            this.m = 5;
            if (psc_n.c[1] != 0) {
                throw new psc_m("Length after NULL not zero.");
            }
            this.q |= 0x2000000;
            psc_n.e = 0;
        }
        else {
            if ((this.l & 0xF0000) == 0x0) {
                throw new psc_m("Invalid encoding: expected tag not there.");
            }
            this.a = false;
            this.q |= 0x2000000;
        }
    }
    
    boolean a(final int n, final psc_i[] array, final int n2) throws psc_m {
        if ((this.q & 0x2000000) != 0x0) {
            return false;
        }
        if (n == 5 && (this.l & 0x10000) != 0x0) {
            return true;
        }
        if (this.e != -1) {
            return n == this.e;
        }
        return n == this.g || ((this.l & 0x4000000) != 0x0 && n == (this.g | 0x20));
    }
    
    protected boolean a(final psc_i psc_i) {
        return false;
    }
    
    protected psc_i b() {
        return null;
    }
    
    public void i() {
        if (this.b != null && this.p) {
            for (int i = 0; i < this.b.length; ++i) {
                this.b[i] = 0;
            }
        }
        this.b = null;
        this.c = 0;
        this.d = 0;
        this.p = false;
    }
}
