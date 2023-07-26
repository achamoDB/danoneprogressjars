// 
// Decompiled by Procyon v0.5.36
// 

public final class psc_nz
{
    public static final psc_nz a;
    public static final psc_nz b;
    public static final psc_nz c;
    public static final psc_nz d;
    private String e;
    
    private psc_nz(final String e) {
        this.e = e;
    }
    
    public String toString() {
        return this.e;
    }
    
    static {
        a = new psc_nz("CERTJ_COMPATIBILITY_STRICT_CERT");
        b = new psc_nz("CERTJ211_COMPATIBILITY_XMLNS");
        c = new psc_nz("CERTJ_COMPATIBILITY_SCEP");
        d = new psc_nz("CERTJ_COMPATIBILITY_EMAIL_AVA_EA");
    }
}
