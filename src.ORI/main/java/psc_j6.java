import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_j6 extends psc_dc implements Cloneable, Serializable
{
    public psc_j6() {
        super("RC2", 1, 1024);
    }
    
    int a(final int n) {
        if (n == -1) {
            return 128;
        }
        return n;
    }
}
