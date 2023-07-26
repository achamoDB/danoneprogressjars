// 
// Decompiled by Procyon v0.5.36
// 

public class psc_p extends psc_i
{
    public psc_p(final int n) {
        this(n, true, 0, 0);
    }
    
    public psc_p(final int n, final boolean b, final int n2, final int n3) {
        super(n, b, n2, 512);
        this.a(n3);
    }
    
    public psc_p(final int n, final boolean b, final int n2, final byte[] b2, final int c, final int d, final boolean b3) throws psc_m {
        super(n, b, n2, 512);
        super.d = d;
        if (b2 == null || !b) {
            return;
        }
        super.b = b2;
        if (c < 0 || c >= b2.length) {
            throw new psc_m("IntegerContainer.IntegerContainer: dataOffset is out of range.");
        }
        super.c = c;
        if (d < 0 || c + d > b2.length) {
            throw new psc_m("IntegerContainer.IntegerContainer: dataLen is out of range.");
        }
        this.a(b3);
        super.q |= 0x20000;
    }
    
    protected psc_p(final int n, final boolean b, final int n2, final int n3, final int n4) {
        super(n, b, n2, n4);
        this.a(n3);
    }
    
    public int e() throws psc_m {
        if (super.d > 4) {
            throw new psc_m("Cannot represent integer in 32 bits.");
        }
        int n = (super.b[super.c] >= 0) ? 0 : -1;
        for (int i = 0, c = super.c; i < super.d; ++i, ++c) {
            n = (n << 8 | (super.b[c] & 0xFF));
        }
        return n;
    }
    
    public void a(final byte[] array, final int n, final int n2, final boolean b, final boolean b2) throws psc_m {
        super.a(array, n, n2, b, b2);
        if (b) {
            this.a(true);
        }
    }
    
    public void a(final byte[] array, final int n, final int n2, final boolean b, final boolean b2, final boolean b3) throws psc_m {
        super.a(array, n, n2, b2, b3);
        if (b2) {
            this.a(b);
        }
    }
    
    protected void a(final int n) {
        (super.b = new byte[4])[0] = (byte)(n >>> 24 & 0xFF);
        super.b[1] = (byte)(n >>> 16 & 0xFF);
        super.b[2] = (byte)(n >>> 8 & 0xFF);
        super.b[3] = (byte)(n & 0xFF);
        super.c = 0;
        super.d = 4;
        super.p = true;
        this.a(n >= 0);
        super.q |= 0x20000;
    }
    
    protected int a(final boolean b, final byte[] array, final int n) throws psc_m {
        if ((super.l & 0x10000) != 0x0 && super.m == 5) {
            if (b) {
                array[n] = 5;
                array[n + 1] = 0;
            }
            return 2;
        }
        if ((super.l & 0xF0000) != 0x0) {
            return 0;
        }
        throw new psc_m("INTEGER not allowed to have length 0.");
    }
    
    void a(final boolean b) {
        if (!b) {
            if (super.b[super.c] >= 0) {
                final byte[] b2 = new byte[super.d];
                int n = 1;
                int i = super.d - 1;
                super.c += super.d - 1;
                while (i >= 0) {
                    b2[i] = (byte)(~super.b[super.c] + n);
                    if (b2[i] != 0) {
                        n = 0;
                    }
                    --i;
                    --super.c;
                }
                if (n == 1) {
                    super.d = 1;
                    for (int j = 0; j < b2.length; ++j) {
                        b2[j] = 0;
                    }
                    return;
                }
                if (super.p) {
                    this.i();
                }
                super.b = b2;
                super.c = 0;
                super.p = true;
            }
            for (int n2 = super.d - 1, n3 = 0; n3 < n2 && super.b[n3] == -1 && super.b[n3 + 1] < 0; ++n3, ++super.c, --super.d) {}
            return;
        }
        for (int n4 = super.c + super.d, c = super.c; c < n4 - 1 && super.b[c] == 0 && super.b[c + 1] >= 0; ++c, ++super.c, --super.d) {}
        if (super.b[super.c] >= 0) {
            return;
        }
        final byte[] b3 = new byte[super.d + 1];
        System.arraycopy(super.b, super.c, b3, 1, super.d);
        if (super.p) {
            this.i();
        }
        super.b = b3;
        super.c = 0;
        ++super.d;
        super.p = true;
    }
    
    protected boolean a(final psc_i psc_i) {
        return psc_i instanceof psc_p;
    }
    
    protected psc_i b() {
        try {
            return new psc_p(super.l, true, super.m, null, 0, 0, true);
        }
        catch (psc_m psc_m) {
            return null;
        }
    }
}
