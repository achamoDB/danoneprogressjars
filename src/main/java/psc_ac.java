// 
// Decompiled by Procyon v0.5.36
// 

public class psc_ac extends psc_ab
{
    public psc_ac(final int n) throws psc_m {
        super(n, 5120, 1);
    }
    
    public psc_ac(final int n, final int n2, final int n3) throws psc_m {
        super(n, 5120, 1, n2, n3);
    }
    
    public psc_ac(final int n, final boolean b, final int n2, final String s) throws psc_m {
        this(n, b, n2, s, -1, -1);
    }
    
    public psc_ac(final int n, final boolean b, final int n2, final String s, final int n3, final int n4) throws psc_m {
        super(n, b, n2, 5120, s, 1, n3, n4);
    }
    
    public psc_ac(final int n, final boolean b, final int n2, final byte[] array, final int n3, final int n4) throws psc_m {
        this(n, b, n2, array, n3, n4, -1, -1);
    }
    
    public psc_ac(final int n, final boolean b, final int n2, final byte[] array, final int n3, final int n4, final int n5, final int n6) throws psc_m {
        super(n, b, n2, 5120, array, n3, n4, 1, 1, n5, n6);
    }
    
    public psc_ac(final int n, final boolean b, final int n2, final int n3, final byte[] array, final int n4, final int n5, final int n6, final int n7) throws psc_m {
        super(n, b, n2, 5120, n3, array, n4, n5, 1, 1, n6, n7);
    }
    
    protected boolean a(final psc_i psc_i) {
        return psc_i instanceof psc_ac;
    }
    
    protected psc_i b() {
        try {
            return new psc_ac(super.l, true, super.m, null, 0, 0, super.d, super.e);
        }
        catch (psc_m psc_m) {
            return null;
        }
    }
}
