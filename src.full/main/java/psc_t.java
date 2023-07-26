// 
// Decompiled by Procyon v0.5.36
// 

public class psc_t extends psc_i
{
    public psc_t(final int n) {
        this(n, 1024);
    }
    
    protected psc_t(final int n, final int n2) {
        super(n, n2);
        super.l |= 0x4000000;
    }
    
    public psc_t(final int n, final boolean b, final int n2, final int n3, final byte[] array, final int n4, final int d) throws psc_m {
        super(n, b, n2, 1024, n3);
        super.l |= 0x4000000;
        super.d = d;
        if (array == null || !b) {
            return;
        }
        this.b(array, n4, d);
        super.q |= 0x20000;
    }
    
    public psc_t(final int n, final boolean b, final int n2, final byte[] array, final int n3, final int d) throws psc_m {
        super(n, b, n2, 1024);
        super.l |= 0x4000000;
        super.d = d;
        if (array == null || !b) {
            return;
        }
        this.b(array, n3, d);
        super.q |= 0x20000;
    }
    
    protected psc_t(final int n, final boolean b, final int n2, final byte[] array, final int n3, final int d, final int n4) throws psc_m {
        super(n, b, n2, n4);
        super.l |= 0x4000000;
        super.d = d;
        if (array == null || !b) {
            return;
        }
        this.b(array, n3, d);
        super.q |= 0x20000;
    }
    
    protected boolean a(final psc_i psc_i) {
        return psc_i instanceof psc_t;
    }
    
    protected psc_i b() {
        try {
            return new psc_t(super.l, true, super.m, null, 0, 0);
        }
        catch (psc_m psc_m) {
            return null;
        }
    }
    
    private void b(final byte[] b, final int c, final int n) throws psc_m {
        super.b = b;
        if (c < 0 || c >= b.length) {
            throw new psc_m("OctetStringContainer.setData: dataOffset is out of range.");
        }
        super.c = c;
        if (n < 0 || c + n > b.length) {
            throw new psc_m("OctetStringContainer.setData: dataLen is out of range.");
        }
    }
}
