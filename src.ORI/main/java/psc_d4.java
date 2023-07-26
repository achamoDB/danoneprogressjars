// 
// Decompiled by Procyon v0.5.36
// 

public class psc_d4 extends psc_i
{
    public static final boolean a = true;
    public static final boolean b = false;
    private static final int c = -1;
    private int d;
    private int e;
    
    public psc_d4(final int n) {
        this(n, true, 0, 0, 0, false);
    }
    
    public psc_d4(final int n, final int n2) {
        this(n, true, 0, 0, n2, true);
    }
    
    public psc_d4(final int n, final boolean b, final int n2, final int ae, final byte[] array, final int n3, final int n4) throws psc_m {
        this(n, b, n2, array, n3, n4, -1, false);
        super.ae = ae;
    }
    
    public psc_d4(final int n, final boolean b, final int n2, int n3, final int d, final boolean b2) {
        super(n, b, n2, 768);
        super.l |= 0x4000000;
        this.d = d;
        if (this.d > 32) {
            this.d %= 32;
        }
        if (this.d < 0) {
            this.d = 0;
        }
        n3 &= -1 << 32 - this.d;
        (super.b = new byte[4])[0] = (byte)(n3 >>> 24 & 0xFF);
        super.b[1] = (byte)(n3 >>> 16 & 0xFF);
        super.b[2] = (byte)(n3 >>> 8 & 0xFF);
        super.b[3] = (byte)(n3 & 0xFF);
        super.p = true;
        super.d = (d + 7) / 8;
        super.d = a(super.b, super.c, super.d, b2);
        this.e = a(super.b, super.c, super.d, this.d, b2);
        super.q |= 0x60000;
    }
    
    public psc_d4(final int n, final boolean b, final int n2, final byte[] array, final int n3, final int n4) throws psc_m {
        this(n, b, n2, array, n3, n4, -1, false);
    }
    
    public psc_d4(final int n, final boolean b, final int n2, final int ae, final byte[] array, final int n3, final int n4, final int n5, final boolean b2) throws psc_m {
        this(n, b, n2, array, n3, n4, n5, b2);
        super.ae = ae;
    }
    
    public psc_d4(final int n, final boolean b, final int n2, final byte[] b2, final int c, final int d, int d2, final boolean b3) throws psc_m {
        super(n, b, n2, 768);
        super.l |= 0x4000000;
        this.d = d2;
        super.d = d;
        if (b2 == null) {
            return;
        }
        super.b = b2;
        if (c < 0 || c >= b2.length) {
            throw new psc_m("BitStringContainer.BitStringContainer: dataOffset is out of range.");
        }
        super.c = c;
        if (d < 0 || c + d > b2.length) {
            throw new psc_m("BitStringContainer.BitStringContainer: dataLen is out of range.");
        }
        if (d2 < 0) {
            d2 = d * 8;
        }
        if (d2 < d * 8) {
            super.d = (d2 + 7) / 8;
            int n3 = d2 % 8;
            if (n3 != 0) {
                n3 = 255 >>> n3;
            }
            final int n4 = super.b[super.c + super.d - 1] & n3;
            if (n4 != 0) {
                final byte[] b4 = new byte[super.d];
                System.arraycopy(super.b, super.c, b4, 0, super.d);
                final byte[] array = b4;
                final int n5 = b4.length - 1;
                array[n5] ^= (byte)n4;
                super.b = b4;
                super.c = 0;
                super.p = true;
            }
        }
        super.d = a(super.b, super.c, super.d, b3);
        this.e = a(super.b, super.c, super.d, d2, b3);
        super.q |= 0x20000;
        if (b3) {
            super.q |= 0x40000;
        }
    }
    
    public int e() throws psc_m {
        if (super.d > 4) {
            throw new psc_m("Cannot represent bit string in 32 bits.");
        }
        final int n = -1 << this.e;
        int n2 = 0;
        for (int i = 24, c = super.c; i >= 32 - super.d * 8; i -= 8, ++c) {
            n2 |= (super.b[c] & 0xFF) << i;
        }
        return n2 & n;
    }
    
    int a(final psc_i[] array, final int n) throws psc_m {
        this.c();
        super.q |= 0x40000;
        if (!super.a) {
            return this.a(false, null, 0);
        }
        super.k = super.d;
        if (super.k != 0) {
            ++super.k;
        }
        final int f = psc_o.a(super.g) + super.k + psc_o.b(super.d);
        if (super.e == -1) {
            return f;
        }
        super.f = f;
        return psc_o.a(super.e) + super.f + psc_o.b(f);
    }
    
    int a(final psc_i[] array, final int n, final byte[] array2, int n2) throws psc_m {
        super.q = 33554432;
        if (!super.a) {
            return this.a(true, array2, n2);
        }
        final int n3 = n2;
        if (super.e != -1) {
            n2 += psc_o.a(array2, n2, super.e);
            n2 += psc_o.b(array2, n2, super.f);
        }
        n2 += psc_o.a(array2, n2, super.g);
        n2 += psc_o.b(array2, n2, super.k);
        if (super.k != 0) {
            if (n2 >= array2.length) {
                throw new psc_m("BitStringContainer.derEncode: not enough room in encoding to put unusedBits");
            }
            array2[n2++] = (byte)this.e;
        }
        if (super.b != null) {
            if (n2 + super.d > array2.length) {
                throw new psc_m("BitStringContainer.derEncode: not enough room in encoding to put data");
            }
            System.arraycopy(super.b, super.c, array2, n2, super.d);
            n2 += super.d;
        }
        return n2 - n3;
    }
    
    void b(final psc_i[] array, final int n) {
        this.c();
        if ((super.q & 0x40000) == 0x0) {
            super.g |= 0x20;
            super.k = -1;
        }
        else {
            super.k = super.d;
            if (super.k != 0) {
                ++super.k;
            }
        }
        super.q |= 0x10000;
    }
    
    protected int a(final byte[] array, int n) throws psc_m {
        final int n2 = n;
        n += super.a(array, n);
        if (super.k != 0) {
            if (array == null) {
                throw new psc_m("BitStringContainer.writeTagBER: encoding should not be null.");
            }
            if (n < 0 || n >= array.length) {
                throw new psc_m("BitStringContainer.writeTagBER: offset is out of range.");
            }
            array[n++] = (byte)this.e;
        }
        return n - n2;
    }
    
    void a() {
        super.a();
        this.e = -1;
        this.d = 0;
    }
    
    protected int a(final psc_n psc_n, final int n, final byte[] array, int n2) throws psc_m {
        final int n3 = n2;
        if (this.e == -1 && super.k != 0) {
            this.e = (byte)(array[n2++] & 0xFF);
            --super.ac;
            if (super.k != -1) {
                --super.k;
            }
        }
        if (super.ac >= 1) {
            n2 += super.a(psc_n, n, array, n2);
        }
        return n2 - n3;
    }
    
    private static int a(final byte[] array, final int n, final int n2, final boolean b) {
        if (!b) {
            return n2;
        }
        int n3 = n2;
        for (int n4 = n + (n2 - 1); n4 >= n && array[n4] == 0; --n4, --n3) {}
        return n3;
    }
    
    private static int a(final byte[] array, final int n, final int n2, final int n3, final boolean b) {
        if (n2 == 0) {
            return 0;
        }
        if (!b) {
            int n4 = 8 - n3 % 8;
            if (n4 == 8) {
                n4 = 0;
            }
            return n4;
        }
        final int n5 = array[n + n2 - 1] & 0xFF;
        for (int i = 0; i < 7; ++i) {
            if ((n5 >>> i & 0x1) != 0x0) {
                return i;
            }
        }
        return 7;
    }
    
    protected boolean a(final psc_i psc_i) {
        return psc_i instanceof psc_d4;
    }
    
    protected psc_i b() {
        try {
            return new psc_d4(super.l, true, super.m, null, 0, 0, this.d, false);
        }
        catch (psc_m psc_m) {
            return null;
        }
    }
}
