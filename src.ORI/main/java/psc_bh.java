// 
// Decompiled by Procyon v0.5.36
// 

public class psc_bh extends psc_i
{
    public boolean a;
    
    public psc_bh(final int n) {
        this(n, true, 0, false);
    }
    
    public psc_bh(final int n, final boolean b, final int n2, final boolean a) {
        super(n, b, n2, 256);
        super.b = new byte[1];
        super.p = true;
        this.a = a;
        if (a) {
            super.b[0] = -1;
        }
        super.d = 1;
        super.c = 0;
        super.q |= 0x20000;
    }
    
    protected int a(final psc_n psc_n, final int n, final byte[] array, final int n2) throws psc_m {
        if (super.p) {
            this.i();
        }
        final int a = super.a(psc_n, n, array, n2);
        if ((super.q & 0x1000000) != 0x0) {
            return a;
        }
        if (super.d != 1) {
            throw new psc_m("Invalid length for BOOLEAN.");
        }
        if (!super.a) {
            return a;
        }
        if (super.b[super.c] == 0) {
            this.a = false;
        }
        else {
            this.a = true;
        }
        return a;
    }
    
    protected boolean a(final psc_i psc_i) {
        return psc_i instanceof psc_bh;
    }
    
    protected psc_i b() {
        return new psc_bh(super.l, true, super.m, true);
    }
}
