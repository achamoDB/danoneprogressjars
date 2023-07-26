// 
// Decompiled by Procyon v0.5.36
// 

public class pscl_s extends pscl_q
{
    public pscl_s(final int n, final boolean b, final int n2, final byte[] l, final int m, final int n3) {
        super(n, b, n2, 1024);
        super.f |= 0x4000000;
        super.n = n3;
        if (l == null) {
            return;
        }
        super.l = l;
        super.m = m;
        super.o |= 0x20000;
    }
    
    public pscl_s(final int n, final boolean b, final int n2, final byte[] l, final int m, final int n3, final int n4) {
        super(n, b, n2, n4);
        super.f |= 0x4000000;
        super.n = n3;
        if (l == null) {
            return;
        }
        super.l = l;
        super.m = m;
        super.o |= 0x20000;
    }
    
    public boolean b(final pscl_q pscl_q) {
        return pscl_q instanceof pscl_s;
    }
    
    public pscl_q g() {
        return new pscl_s(super.f, true, super.h, null, 0, 0);
    }
}
