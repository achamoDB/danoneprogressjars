import java.util.Properties;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_oe extends psc_od
{
    public static final int a = 0;
    public static final int b = 1;
    public static final int c = 2;
    public static final int d = 3;
    private psc_f e;
    private int f;
    private Properties g;
    
    public psc_oe(final psc_f e, final Properties g) {
        this.f = -1;
        this.e = e;
        this.g = g;
    }
    
    public int a() {
        return this.f;
    }
    
    public void b(final int f) {
        this.f = f;
    }
    
    public psc_f b() {
        return this.e;
    }
    
    public Properties c() {
        return this.g;
    }
}
