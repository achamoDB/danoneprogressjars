// 
// Decompiled by Procyon v0.5.36
// 

public class psc_k extends psc_i
{
    private int a;
    private int b;
    private byte[] c;
    
    private psc_k(final int n, final boolean b, final int n2) {
        super(n, b, n2, n & 0xFF00);
        super.l |= 0x4000000;
        super.o = true;
        super.n = false;
    }
    
    private psc_k(final int n, final boolean b, final int n2, final int n3) {
        super(n, b, n2, n & 0xFF00, n3);
        super.l |= 0x4000000;
        super.o = true;
        super.n = false;
    }
    
    public psc_k(final int n) {
        this(n, true, 0);
    }
    
    public psc_k(final int n, final boolean b, final int n2, final byte[] array, final int n3, final int n4) throws psc_m {
        this(n, b, n2, 0, array, n3, n4);
    }
    
    public psc_k(final int n, final boolean b, final int n2, final int n3, final byte[] b2, final int c, final int d) throws psc_m {
        this(n, b, n2, n3);
        super.d = d;
        if (b2 == null || !b) {
            return;
        }
        super.b = b2;
        if (c < 0 || c >= b2.length) {
            throw new psc_m("EncodedContainer.EncodedContainer: dataOffset is out of range.");
        }
        super.c = c;
        if (d < 0 || c + d > b2.length) {
            throw new psc_m("EncodedContainer.EncodedContainer: dataLen is out of range.");
        }
        super.q |= 0x20000;
    }
    
    protected void c() {
        if ((super.i & 0x2000) != 0x0) {
            super.n = true;
        }
        super.c();
        super.n = false;
        this.b = 0;
        this.c = null;
    }
    
    int a(final psc_i[] array, final int n, final byte[] array2, final int n2) throws psc_m {
        if (!super.a) {
            return this.a(true, array2, n2);
        }
        if (super.b == null) {
            return 0;
        }
        if (array2 == null) {
            throw new psc_m("Passed in buffer is null.");
        }
        if (array2.length < super.d + n2) {
            throw new psc_m("EncodedContainer.derEncode: not enough space to copy the data.");
        }
        System.arraycopy(super.b, super.c, array2, n2, super.d);
        return super.d;
    }
    
    void b(final psc_i[] array, final int n) {
        this.c();
        super.q ^= 0x10000;
    }
    
    protected int a(final byte[] array, final int n) {
        super.q ^= 0x10000;
        return 0;
    }
    
    protected int b(final byte[] array, final int n) {
        super.q = 33554432;
        return 0;
    }
    
    protected int b(final psc_n psc_n, final int n, final byte[] array, int n2, int n3) throws psc_m {
        final int n4 = n2;
        final int b = this.b(psc_n, array, n2, n3);
        this.b += b;
        if (b == 0) {
            this.b = psc_n.e;
        }
        if ((super.q & 0x1000000) != 0x0) {
            return b;
        }
        n2 += b;
        n3 -= b;
        this.a = psc_o.b(psc_n.c, 1);
        if (this.a == -1) {
            super.q += 2;
            this.a = 2;
        }
        else {
            this.a += psc_n.e;
        }
        if ((super.l & 0xFF00) == 0xFF00) {
            if (psc_n.c[0] == 0 && psc_n.c[1] == 0 && psc_n.e == 2) {
                this.b(psc_n, n);
                super.q = 33554432;
                return 0;
            }
            super.q |= 0x300000;
            super.q ^= 0x300000;
            psc_n.e = 0;
            if (this.b == n2 - n4) {
                return this.b = 0;
            }
            return n2 - n4;
        }
        else {
            if ((super.q & 0x200000) != 0x0) {
                if ((psc_n.c[0] & 0xFF) != super.e) {
                    this.b(psc_n, n);
                    return n2 - n4;
                }
                super.q ^= 0x200000;
                this.c = new byte[psc_n.e];
                System.arraycopy(psc_n.c, 0, this.c, 0, psc_n.e);
                psc_n.e = 0;
                final int b2 = this.b(psc_n, array, n2, n3);
                this.b = b2;
                n2 += b2;
                n3 -= b2;
                if ((super.q & 0x1000000) != 0x0) {
                    return n2 - n4;
                }
            }
            if ((psc_n.c[0] & 0xFF) == super.g) {
                super.q ^= 0x100000;
                psc_n.e = 0;
                if (this.b == n2 - n4) {
                    return this.b = 0;
                }
                return n2 - n4;
            }
            else if ((psc_n.c[0] & 0xFF) == (super.g | 0x20)) {
                super.q ^= 0x100000;
                psc_n.e = 0;
                if (this.b == n2 - n4) {
                    return this.b = 0;
                }
                return n2 - n4;
            }
            else {
                if (super.e != -1) {
                    throw new psc_m("Invalid encoding: expected tag not there.");
                }
                this.b(psc_n, n);
                return n2 - n4;
            }
        }
    }
    
    protected int a(final psc_n psc_n, final int n, final byte[] b, int c) throws psc_m {
        final int n2 = c;
        if (this.c != null) {
            this.a(this.c, 0, this.c.length);
            super.ad = true;
            this.a -= this.c.length;
        }
        if (this.b != 0) {
            this.a -= this.b;
            this.a(psc_n.c, 0, this.b);
            super.ad = true;
            this.b = 0;
            this.c = null;
        }
        if (this.a > 0) {
            int n3 = this.a;
            if (super.ac < this.a) {
                n3 = super.ac;
            }
            if (super.ad) {
                this.a(b, c, n3);
            }
            else {
                if (super.b == null) {
                    super.b = b;
                    super.c = c;
                }
                super.d += n3;
            }
            this.a -= n3;
            c += n3;
            super.ac -= n3;
            if (this.a > 0) {
                return c - n2;
            }
        }
        int n4 = super.q & 0xFFFF;
        if (n4 == 0) {
            super.q = 33554432;
            return c - n2;
        }
        if (super.ac < 1) {
            return c - n2;
        }
        final int b2 = this.b(psc_n, b, c, super.ac);
        if ((super.q & 0x1000000) != 0x0) {
            this.b = b2;
            return c - n2;
        }
        if (psc_n.c[0] == 0) {
            if (psc_n.c[1] != 0) {
                throw new psc_m("Improper ending to indefinite length.");
            }
            super.q -= 2;
            n4 -= 2;
            this.a = 2;
            psc_n.e = 0;
        }
        else if ((psc_n.c[1] & 0xFF) == 0x80) {
            super.q += 2;
            this.a = 2;
            psc_n.e = 0;
        }
        else {
            this.a = psc_o.b(psc_n.c, 1);
            this.a += psc_n.e;
            psc_n.e = 0;
        }
        if (this.b != 0) {
            super.ad = true;
        }
        return c - n2;
    }
    
    protected boolean a(final psc_i psc_i) {
        return psc_i instanceof psc_k;
    }
    
    protected psc_i b() {
        try {
            return new psc_k(super.l, true, super.m, null, 0, 0);
        }
        catch (psc_m psc_m) {
            return null;
        }
    }
}
