// 
// Decompiled by Procyon v0.5.36
// 

public class psc_n1
{
    public static final psc_n1 a;
    public static final psc_n1 b;
    private final String c;
    
    private psc_n1(final String c) {
        this.c = c;
    }
    
    public String toString() {
        return this.c;
    }
    
    static {
        a = new psc_n1("DH_NULL_SIGNATURE_REQUIRED");
        b = new psc_n1("WEAK_KEYS_ENABLED");
    }
}
