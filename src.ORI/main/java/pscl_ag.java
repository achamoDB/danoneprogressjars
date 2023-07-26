// 
// Decompiled by Procyon v0.5.36
// 

public class pscl_ag extends pscl_ae
{
    public pscl_ag(final int n, final boolean b, final int n2, final String s, final int n3, final int n4) throws pscl_x {
        super(n, b, n2, 5632, s, 1, n3, n4);
    }
    
    public pscl_ag(final int n, final boolean b, final int n2, final byte[] array, final int n3, final int n4, final int n5, final int n6) throws pscl_x {
        super(n, b, n2, 5632, array, n3, n4, 1, 1, n5, n6);
    }
    
    public void d() throws pscl_x {
        super.d();
        if (super.b) {
            return;
        }
        int m;
        for (m = super.m; m < super.m + super.n && super.l[m] >= 0; ++m) {}
        if (m >= super.m + super.n) {
            return;
        }
        throw new pscl_x("Invalid IA5String character:" + (char)(super.l[m] & 0xFF));
    }
    
    public boolean b(final pscl_q pscl_q) {
        return pscl_q instanceof pscl_ag;
    }
    
    public pscl_q g() {
        try {
            return new pscl_ag(super.f, true, super.h, null, 0, 0, super.c, super.d);
        }
        catch (pscl_x pscl_x) {
            return null;
        }
    }
}
