// 
// Decompiled by Procyon v0.5.36
// 

public class pscl_af extends pscl_ae
{
    public pscl_af(final int n, final boolean b, final int n2, final String s, final int n3, final int n4) throws pscl_x {
        super(n, b, n2, 5120, s, 1, n3, n4);
    }
    
    public pscl_af(final int n, final boolean b, final int n2, final byte[] array, final int n3, final int n4, final int n5, final int n6) throws pscl_x {
        super(n, b, n2, 5120, array, n3, n4, 1, 1, n5, n6);
    }
    
    public boolean b(final pscl_q pscl_q) {
        return pscl_q instanceof pscl_af;
    }
    
    public pscl_q g() {
        try {
            return new pscl_af(super.f, true, super.h, null, 0, 0, super.c, super.d);
        }
        catch (pscl_x pscl_x) {
            return null;
        }
    }
}
