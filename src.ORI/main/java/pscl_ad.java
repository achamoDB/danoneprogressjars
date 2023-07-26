// 
// Decompiled by Procyon v0.5.36
// 

public class pscl_ad extends pscl_ae
{
    public pscl_ad(final int n, final boolean b, final int n2, final String s, final int n3, final int n4) throws pscl_x {
        super(n, b, n2, 4864, s, 1, n3, n4);
    }
    
    public pscl_ad(final int n, final boolean b, final int n2, final byte[] array, final int n3, final int n4, final int n5, final int n6) throws pscl_x {
        super(n, b, n2, 4864, array, n3, n4, 1, 1, n5, n6);
    }
    
    public void d() throws pscl_x {
        super.d();
        if (super.b) {
            return;
        }
        int i;
        for (i = super.m; i < super.n + super.m; ++i) {
            if (super.l[i] <= 96 || super.l[i] >= 123) {
                if (super.l[i] <= 64 || super.l[i] >= 91) {
                    if (super.l[i] != 32) {
                        if (super.l[i] <= 38 || super.l[i] >= 64 || super.l[i] == 42 || super.l[i] == 59 || super.l[i] == 60 || super.l[i] == 62) {
                            break;
                        }
                    }
                }
            }
        }
        if (i >= super.n + super.m) {
            return;
        }
        throw new pscl_x("Invalid PrintableString character:" + (char)(super.l[i] & 0xFF));
    }
    
    public boolean b(final pscl_q pscl_q) {
        return pscl_q instanceof pscl_ad;
    }
    
    public pscl_q g() {
        try {
            return new pscl_ad(super.f, true, super.h, null, 0, 0, super.c, super.d);
        }
        catch (pscl_x pscl_x) {
            return null;
        }
    }
}
