// 
// Decompiled by Procyon v0.5.36
// 

public class pscl_aa extends pscl_q
{
    public String a;
    private int b;
    private int c;
    
    public pscl_aa(final int n, final boolean b, final int n2, final String s, final int b2, final int c) {
        super(n, b, n2, 1536);
        this.b = b2;
        this.c = c;
        if (s == null) {
            return;
        }
        if ((n & 0x1000000) == 0x0) {
            super.l = pscl_ab.a(s, b2, c);
            super.m = 0;
            super.n = super.l.length;
            this.a = pscl_ab.a(super.l, super.m, super.n, b2, c);
            super.o |= 0x20000;
        }
    }
    
    public pscl_aa(final int n, final boolean b, final int n2, final byte[] l, final int m, final int n3, final int b2, final int c) {
        super(n, b, n2, 1536);
        super.l = l;
        super.m = m;
        super.n = n3;
        this.b = b2;
        this.c = c;
        if (l != null && (n & 0x1000000) == 0x0) {
            this.a = pscl_ab.a(l, m, n3, b2, c);
        }
        super.o |= 0x20000;
    }
    
    public int a(final pscl_q[] array, final int n, final byte[] array2, final int n2) throws pscl_x {
        if (super.g && (super.f & 0x1000000) == 0x0 && this.a == null) {
            throw new pscl_x("No OID for this transformation.");
        }
        return super.a(array, n, array2, n2);
    }
    
    public int c(final pscl_q[] array, final int n, final byte[] array2, final int n2) throws pscl_x {
        if (super.g && (super.f & 0x1000000) == 0x0 && this.a == null) {
            throw new pscl_x("No OID for this transformation.");
        }
        return super.c(array, n, array2, n2);
    }
    
    public void c() {
        super.c();
        this.a = null;
    }
    
    public int a(final pscl_v pscl_v, final int n, final byte[] array, final int n2, final int n3) throws pscl_x {
        final int a = super.a(pscl_v, n, array, n2, n3);
        if ((super.o & 0x2000000) != 0x0 && (super.f & 0x1000000) == 0x0) {
            this.a = pscl_ab.a(super.l, super.m, super.n, this.b, this.c);
        }
        return a;
    }
    
    public boolean b(final pscl_q pscl_q) {
        return pscl_q instanceof pscl_aa;
    }
    
    public pscl_q g() {
        return new pscl_aa(super.f, true, super.h, null, 0, 0, -1, -1);
    }
}
