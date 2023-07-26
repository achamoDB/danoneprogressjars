// 
// Decompiled by Procyon v0.5.36
// 

public class psc_r extends psc_i
{
    public String a;
    private int b;
    
    public psc_r(final int n) {
        this(n, -1);
    }
    
    public psc_r(final int n, final int n2) {
        this(n, true, 0, null, n2);
    }
    
    public psc_r(final int n, final boolean b, final int n2, final String s) {
        this(n, b, n2, s, -1);
    }
    
    public psc_r(final int n, final boolean b, final int n2, final String s, final int b2) {
        super(n, b, n2, 1536);
        this.b = -1;
        if (s == null) {
            return;
        }
        if ((n & 0x1000000) == 0x0) {
            super.b = psc_s.a(s, b2);
            super.c = 0;
            super.d = super.b.length;
            this.b = b2;
            this.a = psc_s.b(super.b, super.c, super.d, b2);
            super.q |= 0x20000;
        }
    }
    
    public psc_r(final int n, final boolean b, final int n2, final byte[] array, final int n3, final int n4) throws psc_m {
        this(n, b, n2, array, n3, n4, -1);
    }
    
    public psc_r(final int n, final boolean b, final int n2, final byte[] b2, final int c, final int d, final int n3) throws psc_m {
        super(n, b, n2, 1536);
        this.b = -1;
        if (b2 != null && b) {
            if (c < 0 || c >= b2.length) {
                throw new psc_m("OIDContainer.OIDContainer: dataOffset is out of range.");
            }
            if (d < 0 || c + d > b2.length) {
                throw new psc_m("OIDContainer.OIDContainer: dataLen is out of range.");
            }
        }
        super.b = b2;
        super.c = c;
        super.d = d;
        if (b2 != null && (n & 0x1000000) == 0x0) {
            this.a = psc_s.b(b2, c, d, n3);
        }
        super.q |= 0x20000;
    }
    
    int a(final psc_i[] array, final int n, final byte[] array2, final int n2) throws psc_m {
        if (super.a && (super.l & 0x1000000) == 0x0 && this.a == null) {
            throw new psc_m("No OID for this transformation.");
        }
        return super.a(array, n, array2, n2);
    }
    
    int b(final psc_i[] array, final int n, final byte[] array2, final int n2) throws psc_m {
        if (super.a && (super.l & 0x1000000) == 0x0 && this.a == null) {
            throw new psc_m("No OID for this transformation.");
        }
        return super.b(array, n, array2, n2);
    }
    
    void a() {
        super.a();
        this.a = null;
    }
    
    int a(final psc_n psc_n, final int n, final byte[] array, final int n2, final int n3) throws psc_m {
        final int a = super.a(psc_n, n, array, n2, n3);
        if ((super.q & 0x2000000) != 0x0 && (super.l & 0x1000000) == 0x0) {
            this.a = psc_s.b(super.b, super.c, super.d, this.b);
        }
        return a;
    }
    
    protected boolean a(final psc_i psc_i) {
        return psc_i instanceof psc_r;
    }
    
    protected psc_i b() {
        return new psc_r(super.l, true, super.m, null, -1);
    }
}
