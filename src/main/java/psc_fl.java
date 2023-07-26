// 
// Decompiled by Procyon v0.5.36
// 

public class psc_fl extends psc_ab
{
    public psc_fl(final int n) throws psc_m {
        super(n, 4608, 1);
    }
    
    public psc_fl(final int n, final int n2, final int n3) throws psc_m {
        super(n, 4608, 1, n2, n3);
    }
    
    public psc_fl(final int n, final boolean b, final int n2, final String s) throws psc_m {
        this(n, b, n2, s, -1, -1);
    }
    
    public psc_fl(final int n, final boolean b, final int n2, final String s, final int n3, final int n4) throws psc_m {
        super(n, b, n2, 4608, s, 1, n3, n4);
    }
    
    public psc_fl(final int n, final boolean b, final int n2, final byte[] array, final int n3, final int n4) throws psc_m {
        this(n, b, n2, array, n3, n4, -1, -1);
    }
    
    public psc_fl(final int n, final boolean b, final int n2, final byte[] array, final int n3, final int n4, final int n5, final int n6) throws psc_m {
        super(n, b, n2, 4608, array, n3, n4, 1, 1, n5, n6);
    }
    
    public psc_fl(final int n, final boolean b, final int n2, final int n3, final byte[] array, final int n4, final int n5, final int n6, final int n7) throws psc_m {
        super(n, b, n2, 4608, n3, array, n4, n5, 1, 1, n6, n7);
    }
    
    protected void a(final boolean b) throws psc_m {
        super.a(b);
        if (super.c) {
            return;
        }
        if (!b) {
            return;
        }
        int i;
        for (i = super.c; i < super.d + super.c; ++i) {
            if (super.b[i] < 48 || super.b[i] >= 58) {
                if (super.b[i] != 32) {
                    break;
                }
            }
        }
        if (i >= super.d + super.c) {
            return;
        }
        throw new psc_m("Invalid NumericString character:" + (char)(super.b[i] & 0xFF));
    }
    
    protected boolean a(final psc_i psc_i) {
        return psc_i instanceof psc_fl;
    }
    
    protected psc_i b() {
        try {
            return new psc_fl(super.l, true, super.m, null, 0, 0, super.d, super.e);
        }
        catch (psc_m psc_m) {
            return null;
        }
    }
}
