// 
// Decompiled by Procyon v0.5.36
// 

public class psc_gr extends psc_p
{
    public psc_gr(final int n) {
        super(n, true, 0, 0, 2560);
    }
    
    public psc_gr(final int n, final boolean b, final int n2, final int n3) throws psc_m {
        super(n, b, n2, n3, 2560);
    }
    
    protected boolean a(final psc_i psc_i) {
        return psc_i instanceof psc_gr;
    }
    
    protected psc_i b() {
        try {
            return new psc_gr(super.l, true, super.m, 0);
        }
        catch (psc_m psc_m) {
            return null;
        }
    }
}
