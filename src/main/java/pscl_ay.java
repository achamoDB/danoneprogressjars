// 
// Decompiled by Procyon v0.5.36
// 

public class pscl_ay extends pscl_q
{
    public static final int a = 0;
    public static final int b = 1;
    public int c;
    public int d;
    
    public pscl_ay(final int n, final boolean b, final int n2, int n3, final int c, final int n4) {
        super(n, b, n2, 768);
        super.f |= 0x4000000;
        this.c = c;
        if (this.c > 32) {
            this.c %= 32;
        }
        if (this.c < 0) {
            this.c = 0;
        }
        n3 &= -1 << 32 - this.c;
        (super.l = new byte[4])[0] = (byte)(n3 >>> 24 & 0xFF);
        super.l[1] = (byte)(n3 >>> 16 & 0xFF);
        super.l[2] = (byte)(n3 >>> 8 & 0xFF);
        super.l[3] = (byte)(n3 & 0xFF);
        super.k = true;
        super.n = (c + 7) / 8;
        super.n = a(super.l, super.m, super.n, n4);
        this.d = a(super.l, super.m, super.n, this.c, n4);
        super.o |= 0x60000;
    }
    
    public pscl_ay(final int n, final boolean b, final int n2, final byte[] l, final int m, final int n3, int c, final int n4) {
        super(n, b, n2, 768);
        super.f |= 0x4000000;
        this.c = c;
        super.n = n3;
        if (l == null) {
            return;
        }
        super.l = l;
        super.m = m;
        if (c < 0) {
            c = n3 * 8;
        }
        if (c < n3 * 8) {
            super.n = (c + 7) / 8;
            int n5 = c % 8;
            if (n5 != 0) {
                n5 = 255 >>> n5;
            }
            final int n6 = super.l[super.m + super.n - 1] & n5;
            if (n6 != 0) {
                final byte[] i = new byte[super.n];
                System.arraycopy(super.l, super.m, i, 0, super.n);
                final byte[] array = i;
                final int n7 = i.length - 1;
                array[n7] ^= (byte)n6;
                super.l = i;
                super.m = 0;
                super.k = true;
            }
        }
        super.n = a(super.l, super.m, super.n, n4);
        this.d = a(super.l, super.m, super.n, c, n4);
        super.o |= 0x20000;
        if (n4 == 0) {
            super.o |= 0x40000;
        }
    }
    
    public int d() throws pscl_x {
        if (super.n > 4) {
            throw new pscl_x("Cannot represent bit string in 32 bits.");
        }
        final int n = -1 << this.d;
        int n2 = 0;
        for (int i = 24, m = super.m; i >= 32 - super.n * 8; i -= 8, ++m) {
            n2 |= (super.l[m] & 0xFF) << i;
        }
        return n2 & n;
    }
    
    public int a(final pscl_q[] array, final int n) {
        this.a();
        super.o |= 0x40000;
        if (!super.g) {
            return this.a(false, null, 0);
        }
        super.e = super.n;
        if (super.e != 0) {
            ++super.e;
        }
        final int b = 1 + super.e + pscl_w.a(super.n);
        if (super.a == -1) {
            return b;
        }
        super.b = b;
        return 1 + super.b + pscl_w.a(b);
    }
    
    public int a(final pscl_q[] array, final int n, final byte[] array2, int n2) throws pscl_x {
        super.o = 33554432;
        if (!super.g) {
            return this.a(true, array2, n2);
        }
        final int n3 = n2;
        if (super.a != -1) {
            array2[n2] = (byte)(super.a & 0xFF);
            n2 = ++n2 + pscl_w.a(array2, n2, super.b);
        }
        array2[n2] = (byte)(super.c & 0xFF);
        n2 = ++n2 + pscl_w.a(array2, n2, super.e);
        if (super.e != 0) {
            array2[n2++] = (byte)this.d;
        }
        if (super.l != null) {
            System.arraycopy(super.l, super.m, array2, n2, super.n);
            n2 += super.n;
        }
        return n2 - n3;
    }
    
    public void b(final pscl_q[] array, final int n) {
        this.a();
        if ((super.o & 0x40000) == 0x0) {
            super.c |= 0x20;
            super.e = -1;
        }
        else {
            super.e = super.n;
            if (super.e != 0) {
                ++super.e;
            }
        }
        super.o |= 0x10000;
    }
    
    public int a(final byte[] array, int n) {
        final int n2 = n;
        n += super.a(array, n);
        if (super.e != 0) {
            array[n++] = (byte)this.d;
        }
        return n - n2;
    }
    
    public void c() {
        super.c();
        this.d = -1;
        this.c = 0;
    }
    
    public int a(final pscl_v pscl_v, final int n, final byte[] array, int n2, int n3, final boolean b) throws pscl_x {
        final int n4 = n2;
        if (this.d == -1 && super.e != 0) {
            this.d = (byte)(array[n2++] & 0xFF);
            --n3;
            if (super.e != -1) {
                --super.e;
            }
        }
        if (n3 >= 1) {
            n2 += super.a(pscl_v, n, array, n2, n3, b);
        }
        return n2 - n4;
    }
    
    private static int a(final byte[] array, final int n, final int n2, final int n3) {
        if (n3 == 1) {
            return n2;
        }
        int n4 = n2;
        for (int n5 = n + (n2 - 1); n5 >= n && array[n5] == 0; --n5, --n4) {}
        return n4;
    }
    
    private static int a(final byte[] array, final int n, final int n2, final int n3, final int n4) {
        if (n2 == 0) {
            return 0;
        }
        if (n4 == 1) {
            int n5 = 8 - n3 % 8;
            if (n5 == 8) {
                n5 = 0;
            }
            return n5;
        }
        final int n6 = array[n + n2 - 1] & 0xFF;
        for (int i = 0; i < 7; ++i) {
            if ((n6 >>> i & 0x1) != 0x0) {
                return i;
            }
        }
        return 7;
    }
    
    public boolean b(final pscl_q pscl_q) {
        return pscl_q instanceof pscl_ay;
    }
    
    public pscl_q g() {
        return new pscl_ay(super.f, true, super.h, null, 0, 0, this.c, 0);
    }
}
