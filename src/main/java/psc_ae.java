// 
// Decompiled by Procyon v0.5.36
// 

public class psc_ae extends psc_ab
{
    public psc_ae(final int n) throws psc_m {
        super(n, 7680, 2);
    }
    
    public psc_ae(final int n, final int n2, final int n3) throws psc_m {
        super(n, 7680, 2, n2, n3);
    }
    
    public psc_ae(final int n, final boolean b, final int n2, final String s) throws psc_m {
        this(n, b, n2, s, -1, -1);
    }
    
    public psc_ae(final int n, final boolean b, final int n2, final String s, final int n3, final int n4) throws psc_m {
        super(n, b, n2, 7680, s, 2, n3, n4);
    }
    
    public psc_ae(final int n, final boolean b, final int n2, final byte[] array, final int n3, final int n4, final int n5) throws psc_m {
        this(n, b, n2, array, n3, n4, n5, -1, -1);
    }
    
    public psc_ae(final int n, final boolean b, final int n2, final byte[] array, final int n3, final int n4, final int n5, final int n6, final int n7) throws psc_m {
        super(n, b, n2, 7680, array, n3, n4, 2, n5, n6, n7);
    }
    
    public psc_ae(final int n, final boolean b, final int n2, final int n3, final byte[] array, final int n4, final int n5, final int n6, final int n7, final int n8) throws psc_m {
        super(n, b, n2, 7680, n3, array, n4, n5, 2, n6, n7, n8);
    }
    
    protected boolean a(final psc_i psc_i) {
        return psc_i instanceof psc_ae;
    }
    
    protected psc_i b() {
        try {
            return new psc_ae(super.l, true, super.m, null, 0, 0, 1, super.d, super.e);
        }
        catch (psc_m psc_m) {
            return null;
        }
    }
}
