import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_j1 extends psc_jz implements psc_am, Serializable, Cloneable
{
    public psc_j1() {
        super("AES192", 192, 192);
    }
    
    public boolean b(final int n) {
        return n == 192;
    }
    
    int a(final int n) {
        return 192;
    }
}
