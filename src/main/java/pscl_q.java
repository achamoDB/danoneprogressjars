// 
// Decompiled by Procyon v0.5.36
// 

public class pscl_q
{
    public int a;
    public int b;
    public int c;
    public int d;
    public int e;
    public int f;
    public boolean g;
    public int h;
    public boolean i;
    public boolean j;
    public boolean k;
    public byte[] l;
    public int m;
    public int n;
    public int o;
    public static final int p = 65535;
    public static final int q = 65536;
    public static final int r = 131072;
    public static final int s = 262144;
    public static final int t = 1048576;
    public static final int u = 2097152;
    public static final int v = 4194304;
    public static final int w = 16777216;
    public static final int x = 33554432;
    public static final int y = 268435456;
    public static final int z = 536870912;
    
    public pscl_q() {
        this.a = -1;
        this.k = false;
    }
    
    public pscl_q(final int f, final boolean g, final int h, final int d) {
        this.a = -1;
        this.k = false;
        this.f = f;
        this.g = g;
        this.h = h;
        this.d = d;
        if ((d & 0x2000) != 0x0) {
            this.i = true;
        }
    }
    
    public void a() {
        if ((this.o & 0x2000000) != 0x0) {
            this.o &= 0xFDFFFFFF;
            this.o |= 0x20000;
        }
        if ((this.f & 0x20000) != 0x0) {
            this.h = 0;
        }
        this.c = this.d >>> 8;
        final int n = this.f & 0xE00000;
        if (n == 0) {
            return;
        }
        int n2 = n >>> 16;
        if (this.i) {
            n2 |= 0x20;
        }
        final int n3 = n2 | (this.f & 0xFF);
        if ((this.f & 0x200000) != 0x0) {
            this.a = n3;
        }
        else {
            this.c = n3;
        }
    }
    
    public boolean h() {
        return (this.o & 0x2000000) != 0x0;
    }
    
    public void a(final byte[] l, final int m, final int n, final boolean b, final boolean b2) throws pscl_x {
        if (b) {
            if ((this.o & 0x2000000) != 0x0) {
                this.o ^= 0x2000000;
            }
        }
        else {
            if ((this.f & 0x4000000) == 0x0) {
                throw new pscl_x("This type is not allowed to accept new data.");
            }
            if ((this.o & 0x2000000) != 0x0) {
                throw new pscl_x("The container cannot accept new data, already encoded.");
            }
        }
        if ((this.o & 0x40000) != 0x0 && l != null) {
            throw new pscl_x("The container cannot accept new data any more.");
        }
        this.l = l;
        this.o |= 0x20000;
        if (l != null) {
            this.m = m;
            this.n = n;
            if (b2) {
                this.o |= 0x40000;
            }
            return;
        }
        if (!b2) {
            throw new pscl_x("Cannot add null newData unless dataComplete is true.");
        }
        this.m = 0;
        this.n = 0;
        this.o ^= 0x20000;
        this.o |= 0x40000;
    }
    
    public void f() throws pscl_x {
        this.o |= 0x40000;
    }
    
    public int a(final pscl_q[] array, final int n) {
        this.a();
        this.o |= 0x40000;
        if (!this.g) {
            return this.a(false, null, 0);
        }
        if (this.j) {
            return this.n;
        }
        this.e = this.n;
        final int b = 1 + this.e + pscl_w.a(this.n);
        if (this.a == -1) {
            return b;
        }
        this.b = b;
        return 1 + this.b + pscl_w.a(b);
    }
    
    public int a(final pscl_q[] array, final int n, final byte[] array2, int n2) throws pscl_x {
        this.o = 33554432;
        if (!this.g) {
            return this.a(true, array2, n2);
        }
        final int n3 = n2;
        if (this.a != -1) {
            array2[n2] = (byte)(this.a & 0xFF);
            n2 = ++n2 + pscl_w.a(array2, n2, this.b);
        }
        array2[n2] = (byte)(this.c & 0xFF);
        n2 = ++n2 + pscl_w.a(array2, n2, this.e);
        if (this.l != null) {
            System.arraycopy(this.l, this.m, array2, n2, this.n);
            n2 += this.n;
        }
        return n2 - n3;
    }
    
    public int a(final boolean b, final byte[] array, int n) {
        if ((this.f & 0x10000) != 0x0 && this.h == 5) {
            if (b) {
                array[n] = 5;
                array[n + 1] = 0;
            }
            return 2;
        }
        if ((this.f & 0xF0000) != 0x0) {
            return 0;
        }
        int n2 = 2;
        if (this.a != -1) {
            if (b) {
                array[n] = (byte)this.a;
                array[n + 1] = 2;
            }
            n2 += 2;
            n += 2;
        }
        if (b) {
            array[n] = (byte)this.c;
            array[n + 1] = 0;
        }
        return n2;
    }
    
    public void b(final pscl_q[] array, final int n) {
        this.a();
        this.e = this.n;
        if ((this.f & 0x4000000) != 0x0 && (this.o & 0x40000) == 0x0) {
            this.c |= 0x20;
            this.e = -1;
        }
        else {
            this.o |= 0x40000;
        }
        this.o |= 0x10000;
    }
    
    public int c(final pscl_q[] array, final int n, final byte[] array2, int n2) throws pscl_x {
        if (this.o == 0) {
            throw new pscl_x("Cannot continue BER encoding without new data.");
        }
        if ((this.o & 0x2000000) != 0x0) {
            return 0;
        }
        if (!this.g) {
            this.o = 33554432;
            return this.a(true, array2, n2);
        }
        final int n3 = n2;
        if ((this.o & 0x10000) != 0x0) {
            n2 += this.a(array2, n2);
        }
        if ((this.o & 0x20000) != 0x0) {
            n2 += this.b(array, n, array2, n2);
        }
        if ((this.o & 0x40000) != 0x0) {
            n2 += this.b(array2, n2);
        }
        return n2 - n3;
    }
    
    public int a(final byte[] array, int n) {
        final int n2 = n;
        if (this.a != -1) {
            array[n] = (byte)this.a;
            array[n + 1] = -128;
            n += 2;
        }
        array[n++] = (byte)this.c;
        if (this.e == -1) {
            array[n++] = -128;
        }
        else {
            n += pscl_w.a(array, n, this.e);
        }
        this.o ^= 0x10000;
        return n - n2;
    }
    
    public int b(final pscl_q[] array, final int n, final byte[] array2, int n2) throws pscl_x {
        final int n3 = n2;
        if (this.l != null && this.n != 0) {
            if (this.e == -1) {
                array2[n2++] = (byte)(this.d >>> 8);
                n2 += pscl_w.a(array2, n2, this.n);
            }
            System.arraycopy(this.l, this.m, array2, n2, this.n);
            n2 += this.n;
        }
        this.o ^= 0x20000;
        return n2 - n3;
    }
    
    public int b(final byte[] array, int n) {
        this.o = 33554432;
        final int n2 = n;
        if (this.e == -1) {
            array[n + 1] = (array[n] = 0);
            n += 2;
        }
        if (this.a != -1) {
            array[n + 1] = (array[n] = 0);
            n += 2;
        }
        return n - n2;
    }
    
    public void b() {
        this.c();
        this.a();
        this.o = 1048576;
        if (this.a != -1) {
            this.o |= 0x200000;
        }
        this.g = true;
    }
    
    public void c() {
        this.b = 0;
        this.e = 0;
        this.l = null;
        this.m = 0;
        this.n = 0;
    }
    
    public int a(final pscl_v pscl_v, final int n, final byte[] array, int n2, final int n3) throws pscl_x {
        this.l = null;
        this.m = 0;
        this.n = 0;
        final int n4 = n2;
        int n5 = n3 - n2;
        if ((this.o & 0x100000) != 0x0) {
            final int b = this.b(pscl_v, n, array, n2, n5);
            if ((this.o & 0x1000000) != 0x0 || (this.o & 0x2000000) != 0x0) {
                return b;
            }
            this.o |= 0x400000;
            n2 += b;
            n5 -= b;
        }
        if (n5 >= 1 || this.e == 0) {
            n2 += this.a(pscl_v, n, array, n2, n5, false);
        }
        return n2 - n4;
    }
    
    public int a(final pscl_v pscl_v, final int n, final byte[] l, int m, int n2, final boolean b) throws pscl_x {
        final int n3 = m;
        if (this.e > 0) {
            int e = this.e;
            if (n2 < this.e) {
                e = n2;
            }
            if (b) {
                this.a(l, m, e);
            }
            else {
                this.l = l;
                this.m = m;
                this.n = e;
            }
            this.e -= e;
            m += e;
            if (this.e > 0) {
                this.o |= 0x1000000;
                return n2;
            }
            n2 -= e;
        }
        final int a = this.a(pscl_v, l, m, n2);
        m += a;
        n2 -= a;
        if ((this.o & 0x1000000) != 0x0 || (this.o & 0x2000000) != 0x0) {
            return m - n3;
        }
        this.o |= 0x100000;
        final int b2 = this.b(pscl_v, n, l, m, n2);
        m += b2;
        if ((this.o & 0x1000000) != 0x0) {
            return m - n3;
        }
        n2 -= b2;
        if (n2 >= 1) {
            m += this.a(pscl_v, n, l, m, n2, true);
        }
        else {
            this.o |= 0x1000000;
        }
        return m - n3;
    }
    
    public int a(final pscl_v pscl_v, final byte[] array, int n, int n2) throws pscl_x {
        final int n3 = n;
        int n4 = this.o & 0xFFFF;
        if (n4 == 0) {
            this.o = 33554432;
            return n - n3;
        }
        if (n2 < 1) {
            return 0;
        }
        final int b = this.b(pscl_v, array, n, n2);
        n += b;
        if ((this.o & 0x1000000) != 0x0) {
            return n - n3;
        }
        n2 -= b;
        if (pscl_v.c[0] == 0) {
            if (pscl_v.c[1] != 0) {
                throw new pscl_x("Improper ending to indefinite length.");
            }
            this.o -= 2;
            n4 -= 2;
            pscl_v.d = 0;
            if (n4 == 0) {
                this.o = 33554432;
                return n - n3;
            }
            n += this.a(pscl_v, array, n, n2);
            return n - n3;
        }
        else {
            if (this.b == -1 && n4 == 2) {
                throw new pscl_x("Improper ending to indefinite length.");
            }
            return n - n3;
        }
    }
    
    public void a(final byte[] array, final int n, final int n2) {
        if (n2 < 1) {
            return;
        }
        final byte[] l = new byte[this.n + n2];
        if (this.l != null) {
            System.arraycopy(this.l, this.m, l, 0, this.n);
        }
        System.arraycopy(array, n, l, this.n, n2);
        if (this.k) {
            this.i();
        }
        this.l = l;
        this.m = 0;
        this.n = l.length;
        this.k = true;
    }
    
    public int b(final pscl_v pscl_v, final int n, final byte[] array, int n2, int n3) throws pscl_x {
        final int n4 = n2;
        final int b = this.b(pscl_v, array, n2, n3);
        if ((this.o & 0x1000000) != 0x0) {
            return b;
        }
        n2 += b;
        n3 -= b;
        if ((this.o & 0x200000) != 0x0) {
            if ((pscl_v.c[0] & 0xFF) != this.a) {
                this.a(pscl_v, n);
                return n2 - n4;
            }
            this.o ^= 0x200000;
            this.b = pscl_w.b(pscl_v.c, 1);
            if (this.b == -1) {
                this.o += 2;
            }
            pscl_v.d = 0;
            final int b2 = this.b(pscl_v, array, n2, n3);
            n2 += b2;
            n3 -= b2;
            if ((this.o & 0x1000000) != 0x0) {
                return n2 - n4;
            }
        }
        if ((pscl_v.c[0] & 0xFF) == this.c) {
            this.e = pscl_w.b(pscl_v.c, 1);
            if (this.e == -1) {
                if ((this.c & 0x20) == 0x0) {
                    throw new pscl_x("Invalid indefinite length octet.");
                }
                this.o += 2;
            }
            this.o ^= 0x100000;
            pscl_v.d = 0;
            return n2 - n4;
        }
        if ((this.f & 0x4000000) != 0x0 && (pscl_v.c[0] & 0xFF) == (this.c | 0x20)) {
            this.e = pscl_w.b(pscl_v.c, 1);
            if (this.e != -1) {
                throw new pscl_x("Primitive promoted to constructed must be indefinite length.");
            }
            this.o ^= 0x100000;
            this.o += 2;
            this.c = this.d >>> 8;
            pscl_v.d = 0;
            return n2 - n4;
        }
        else {
            if (this.a != -1) {
                throw new pscl_x("Invalid encoding: expected tag not there.");
            }
            this.a(pscl_v, n);
            return n2 - n4;
        }
    }
    
    public int b(final pscl_v pscl_v, final byte[] array, final int n, final int n2) {
        this.o |= 0x1000000;
        final int n3 = n2 + pscl_v.d;
        if (n3 < 2) {
            return this.c(pscl_v, array, n, n2);
        }
        final int a = this.a(pscl_v, array, n);
        if (n3 < a + 1) {
            return this.c(pscl_v, array, n, n2);
        }
        this.o ^= 0x1000000;
        return this.c(pscl_v, array, n, a + 1 - pscl_v.d);
    }
    
    public int a(final pscl_v pscl_v, final byte[] array, final int n) {
        if (pscl_v.d < 1) {
            return pscl_w.a(array, n + 1);
        }
        if (pscl_v.d > 1) {
            return pscl_w.a(pscl_v.c, 1);
        }
        return pscl_w.a(array, n);
    }
    
    public int c(final pscl_v pscl_v, final byte[] array, final int n, final int n2) {
        if (n2 <= 0) {
            return 0;
        }
        System.arraycopy(array, n, pscl_v.c, pscl_v.d, n2);
        pscl_v.d += n2;
        return n2;
    }
    
    public void a(final pscl_v pscl_v, final int n) throws pscl_x {
        if ((this.o & 0x400000) != 0x0) {
            throw new pscl_x("Invalid encoding: expected tag or data not there.");
        }
        if ((this.f & 0x10000) != 0x0 && pscl_v.d > 0 && pscl_v.c[0] == 5) {
            this.g = false;
            this.h = 5;
            if (pscl_v.c[1] != 0) {
                throw new pscl_x("Length after NULL not zero.");
            }
            this.o |= 0x2000000;
            pscl_v.d = 0;
        }
        else {
            if ((this.f & 0xF0000) == 0x0) {
                throw new pscl_x("Invalid encoding: expected tag not there.");
            }
            this.g = false;
            this.o |= 0x2000000;
        }
    }
    
    public boolean a(final int n, final pscl_q[] array, final int n2) {
        if ((this.o & 0x2000000) != 0x0) {
            return false;
        }
        if (n == 5 && (this.f & 0x10000) != 0x0) {
            return true;
        }
        if (this.a != -1) {
            return n == this.a;
        }
        return n == this.c || ((this.f & 0x4000000) != 0x0 && n == (this.c | 0x20));
    }
    
    public boolean b(final pscl_q pscl_q) {
        return false;
    }
    
    public pscl_q g() {
        return null;
    }
    
    public void i() {
        if (this.k) {
            for (int i = this.m; i < this.n + this.m; ++i) {
                this.l[i] = 0;
            }
        }
    }
}
