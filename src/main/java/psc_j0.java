import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_j0 extends psc_jz implements psc_am, Serializable, Cloneable
{
    public psc_j0() {
        super("AES128", 128, 128);
    }
    
    public boolean b(final int n) {
        return n == 128;
    }
    
    int a(final int n) {
        return 128;
    }
}
