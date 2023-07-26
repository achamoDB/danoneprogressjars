import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_j2 extends psc_jz implements psc_am, Serializable, Cloneable
{
    public psc_j2() {
        super("AES256", 256, 256);
    }
    
    public boolean b(final int n) {
        return n == 256;
    }
    
    int a(final int n) {
        return 256;
    }
}
