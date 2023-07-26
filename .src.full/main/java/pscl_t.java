// 
// Decompiled by Procyon v0.5.36
// 

public class pscl_t extends pscl_q
{
    private int a;
    private int b;
    private byte[] c;
    
    public pscl_t(final int n, final boolean b, final int n2, final byte[] l, final int m, final int n3) {
        super(n, b, n2, n & 0xFF00);
        super.f |= 0x4000000;
        super.j = true;
        super.i = false;
        super.n = n3;
        if (l == null) {
            return;
        }
        super.l = l;
        super.m = m;
        super.o |= 0x20000;
    }
    
    public void a() {
        if ((super.d & 0x2000) != 0x0) {
            super.i = true;
        }
        super.a();
        super.i = false;
        this.b = 0;
        this.c = null;
    }
    
    public int a(final pscl_q[] array, final int n, final byte[] array2, final int n2) throws pscl_x {
        if (!super.g) {
            return this.a(true, array2, n2);
        }
        if (super.l == null) {
            return 0;
        }
        System.arraycopy(super.l, super.m, array2, n2, super.n);
        return super.n;
    }
    
    public void b(final pscl_q[] array, final int n) {
        this.a();
        super.o ^= 0x10000;
    }
    
    public int a(final byte[] array, final int n) {
        super.o ^= 0x10000;
        return 0;
    }
    
    public int b(final byte[] array, final int n) {
        super.o = 33554432;
        return 0;
    }
    
    public int b(final pscl_v pscl_v, final int n, final byte[] array, int n2, int n3) throws pscl_x {
        final int n4 = n2;
        final int b = this.b(pscl_v, array, n2, n3);
        this.b += b;
        if (b == 0) {
            this.b = pscl_v.d;
        }
        if ((super.o & 0x1000000) != 0x0) {
            return b;
        }
        n2 += b;
        n3 -= b;
        this.a = pscl_w.b(pscl_v.c, 1);
        if (this.a == -1) {
            super.o += 2;
            this.a = 2;
        }
        else {
            this.a += pscl_v.d;
        }
        if ((super.f & 0xFF00) == 0xFF00) {
            super.o |= 0x300000;
            super.o ^= 0x300000;
            pscl_v.d = 0;
            if (this.b == n2 - n4) {
                return this.b = 0;
            }
            return n2 - n4;
        }
        else {
            if ((super.o & 0x200000) != 0x0) {
                if ((pscl_v.c[0] & 0xFF) != super.a) {
                    this.a(pscl_v, n);
                    return n2 - n4;
                }
                super.o ^= 0x200000;
                this.c = new byte[pscl_v.d];
                System.arraycopy(pscl_v.c, 0, this.c, 0, pscl_v.d);
                pscl_v.d = 0;
                final int b2 = this.b(pscl_v, array, n2, n3);
                this.b = b2;
                n2 += b2;
                n3 -= b2;
                if ((super.o & 0x1000000) != 0x0) {
                    return n2 - n4;
                }
            }
            if ((pscl_v.c[0] & 0xFF) == super.c) {
                super.o ^= 0x100000;
                pscl_v.d = 0;
                if (this.b == n2 - n4) {
                    return this.b = 0;
                }
                return n2 - n4;
            }
            else if ((pscl_v.c[0] & 0xFF) == (super.c | 0x20)) {
                super.o ^= 0x100000;
                pscl_v.d = 0;
                if (this.b == n2 - n4) {
                    return this.b = 0;
                }
                return n2 - n4;
            }
            else {
                if (super.a != -1) {
                    throw new pscl_x("Invalid encoding: expected tag not there.");
                }
                this.a(pscl_v, n);
                return n2 - n4;
            }
        }
    }
    
    public int a(final pscl_v pscl_v, final int n, final byte[] l, int m, int n2, boolean b) throws pscl_x {
        final int n3 = m;
        if (this.c != null) {
            this.a(this.c, 0, this.c.length);
            b = true;
            this.a -= this.c.length;
        }
        if (this.b != 0) {
            this.a -= this.b;
            this.a(pscl_v.c, 0, this.b);
            b = true;
            this.b = 0;
            this.c = null;
        }
        if (this.a > 0) {
            int a = this.a;
            if (n2 < this.a) {
                a = n2;
            }
            if (b) {
                this.a(l, m, a);
            }
            else {
                if (super.l == null) {
                    super.l = l;
                    super.m = m;
                }
                super.n += a;
            }
            this.a -= a;
            m += a;
            if (this.a > 0) {
                return n2;
            }
            n2 -= a;
        }
        int n4 = super.o & 0xFFFF;
        if (n4 == 0) {
            super.o = 33554432;
            return m - n3;
        }
        if (n2 < 1) {
            return m - n3;
        }
        final int b2 = this.b(pscl_v, l, m, n2);
        if ((super.o & 0x1000000) != 0x0) {
            this.b = b2;
            return m - n3;
        }
        if (pscl_v.c[0] == 0) {
            if (pscl_v.c[1] != 0) {
                throw new pscl_x("Improper ending to indefinite length.");
            }
            super.o -= 2;
            n4 -= 2;
            this.a = 2;
            pscl_v.d = 0;
        }
        else if ((pscl_v.c[1] & 0xFF) == 0x80) {
            super.o += 2;
            this.a = 2;
            pscl_v.d = 0;
        }
        else {
            this.a = pscl_w.b(pscl_v.c, 1);
            this.a += pscl_v.d;
            pscl_v.d = 0;
        }
        if (this.b != 0) {
            b = true;
        }
        m += this.a(pscl_v, n, l, m, n2, b);
        return m - n3;
    }
    
    public boolean b(final pscl_q pscl_q) {
        return pscl_q instanceof pscl_t;
    }
    
    public pscl_q g() {
        return new pscl_t(super.f, true, super.h, null, 0, 0);
    }
}
