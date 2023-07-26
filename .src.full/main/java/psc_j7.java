import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_j7 extends psc_dc implements Cloneable, Serializable
{
    public psc_j7() {
        this(h(), e(), f());
    }
    
    protected psc_j7(final String s, final int n, final int n2) {
        super(s, n, n2);
    }
    
    protected static int e() {
        return 0;
    }
    
    protected static int f() {
        return 2040;
    }
    
    protected static int g() {
        return 128;
    }
    
    public static String h() {
        return "RC5";
    }
    
    protected int a(final int n) {
        if (n == -1) {
            return g();
        }
        return n;
    }
}
