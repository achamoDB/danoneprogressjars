// 
// Decompiled by Procyon v0.5.36
// 

public class psc_af extends psc_t
{
    public psc_af(final int n) {
        super(n, 3072);
    }
    
    public psc_af(final int n, final boolean b, final int n2, final byte[] array, final int n3, final int n4) throws psc_m {
        super(n, b, n2, array, n3, n4, 3072);
    }
    
    public psc_af(final int n, final boolean b, final int n2, final int ae, final byte[] array, final int n3, final int n4) throws psc_m {
        super(n, b, n2, array, n3, n4, 3072);
        super.ae = ae;
    }
    
    protected boolean a(final psc_i psc_i) {
        return psc_i instanceof psc_af;
    }
    
    protected psc_i b() {
        try {
            return new psc_af(super.l, true, super.m, null, 0, 0);
        }
        catch (psc_m psc_m) {
            return null;
        }
    }
}
