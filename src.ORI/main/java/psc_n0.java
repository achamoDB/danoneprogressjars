// 
// Decompiled by Procyon v0.5.36
// 

public final class psc_n0
{
    public static final psc_n0 a;
    public static final psc_n0 b;
    public static final psc_n0 c;
    public static final psc_n0 d;
    public static final psc_n0 e;
    public static final psc_n0 f;
    public static final psc_n0 g;
    public static final psc_n0 h;
    public static final psc_n0 i;
    private String j;
    
    private psc_n0(final String j) {
        this.j = j;
    }
    
    public String toString() {
        return this.j;
    }
    
    static {
        a = new psc_n0("MESSAGE_DIGEST");
        b = new psc_n0("SYMMETRIC_DECRYPTION");
        c = new psc_n0("SYMMETRIC_ENCRYPTION");
        d = new psc_n0("ASYMMETRIC_ENCRYPTION");
        e = new psc_n0("ASYMMETRIC_DECRYPTION");
        f = new psc_n0("SIGNING");
        g = new psc_n0("VERIFICATION");
        h = new psc_n0("KEY_PAIR_GENERATION");
        i = new psc_n0("RNG");
    }
}
