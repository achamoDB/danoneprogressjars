// 
// Decompiled by Procyon v0.5.36
// 

public class pscl_r extends pscl_q
{
    public pscl_r(final int n, final boolean b, final int n2, final int n3) {
        super(n, b, n2, 512);
        this.a(n3);
    }
    
    public pscl_r(final int n, final boolean b, final int n2, final byte[] l, final int m, final int n3, final boolean b2) {
        super(n, b, n2, 512);
        super.n = n3;
        if (l == null) {
            return;
        }
        super.l = l;
        super.m = m;
        this.a(b2);
        super.o |= 0x20000;
    }
    
    public pscl_r(final int n, final boolean b, final int n2, final int n3, final int n4) {
        super(n, b, n2, n4);
        this.a(n3);
    }
    
    public int d() throws pscl_x {
        if (super.n > 4) {
            throw new pscl_x("Cannot represent integer in 32 bits.");
        }
        int n = (super.l[super.m] >= 0) ? 0 : -1;
        for (int i = 0, m = super.m; i < super.n; ++i, ++m) {
            n = (n << 8 | (super.l[m] & 0xFF));
        }
        return n;
    }
    
    public void a(final int n) {
        (super.l = new byte[4])[0] = (byte)(n >>> 24 & 0xFF);
        super.l[1] = (byte)(n >>> 16 & 0xFF);
        super.l[2] = (byte)(n >>> 8 & 0xFF);
        super.l[3] = (byte)(n & 0xFF);
        super.m = 0;
        super.n = 4;
        super.k = true;
        this.a(n >= 0);
        super.o |= 0x20000;
    }
    
    public void a(final boolean b) {
        if (!b) {
            if (super.l[super.m] >= 0) {
                final byte[] l = new byte[super.n];
                int n = 1;
                int i = super.n - 1;
                super.m += super.n - 1;
                while (i >= 0) {
                    l[i] = (byte)(~super.l[super.m] + n);
                    if (l[i] != 0) {
                        n = 0;
                    }
                    --i;
                    --super.m;
                }
                if (n == 1) {
                    super.n = 1;
                    for (int j = 0; j < l.length; ++j) {
                        l[j] = 0;
                    }
                    return;
                }
                if (super.k) {
                    this.i();
                }
                super.l = l;
                super.m = 0;
                super.k = true;
            }
            for (int n2 = super.n - 1, n3 = 0; n3 < n2 && super.l[n3] == -1 && super.l[n3 + 1] < 0; ++n3, ++super.m, --super.n) {}
            return;
        }
        for (int n4 = super.m + super.n, m = super.m; m < n4 - 1 && super.l[m] == 0 && super.l[m + 1] >= 0; ++m, ++super.m, --super.n) {}
        if (super.l[super.m] >= 0) {
            return;
        }
        final byte[] k = new byte[super.n + 1];
        System.arraycopy(super.l, super.m, k, 1, super.n);
        if (super.k) {
            this.i();
        }
        super.l = k;
        super.m = 0;
        ++super.n;
        super.k = true;
    }
    
    public boolean b(final pscl_q pscl_q) {
        return pscl_q instanceof pscl_r;
    }
    
    public pscl_q g() {
        return new pscl_r(super.f, true, super.h, null, 0, 0, true);
    }
}
