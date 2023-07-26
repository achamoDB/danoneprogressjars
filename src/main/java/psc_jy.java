import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_jy extends psc_dc implements Cloneable, Serializable
{
    public psc_jy() {
        super("RC4", 1, 2048);
    }
    
    int a(final int n) {
        if (n == -1) {
            return 128;
        }
        return n;
    }
}
