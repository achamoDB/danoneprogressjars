import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_jz extends psc_dc implements psc_am, Serializable, Cloneable
{
    public psc_jz() {
        super("AES", 128, 256);
    }
    
    protected psc_jz(final String s, final int n, final int n2) {
        super(s, n, n2);
    }
    
    public boolean b(final int n) {
        return n == 128 || n == 192 || n == 256;
    }
    
    int a(final int n) {
        if (n == -1) {
            return 128;
        }
        return n;
    }
    
    public void a(final byte[] array, final int n, final int n2) throws psc_bf {
        super.a(array, n, n2);
        if (super.j == super.k) {
            return;
        }
        super.c = "AES" + n2 * 8;
    }
}
