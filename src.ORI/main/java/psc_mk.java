// 
// Decompiled by Procyon v0.5.36
// 

public class psc_mk extends psc_dv
{
    private static final String a = "DH_DSS_With_DES_CBC_SHA";
    private static final String b;
    
    public psc_mk() {
        super("DH", "DSA", "DES/CBC/NoPad", "SHA1");
    }
    
    public int f() {
        return 8;
    }
    
    public int g() {
        return 8;
    }
    
    public int h() {
        return 20;
    }
    
    public String i() {
        return "DH_DSS_With_DES_CBC_SHA";
    }
    
    public String a(final int n) {
        return psc_mk.b;
    }
    
    public psc_dw j() throws psc_d {
        final psc_mk psc_mk = new psc_mk();
        psc_mk.a(this.n());
        psc_mk.a(this.o());
        return psc_mk;
    }
    
    public byte[] b(final int n) {
        if (n == 2) {
            return null;
        }
        return new byte[] { 0, 12 };
    }
    
    public boolean k() {
        return false;
    }
    
    public boolean l() {
        return false;
    }
    
    public boolean m() {
        return false;
    }
    
    static {
        b = "SSL_" + "DH_DSS_With_DES_CBC_SHA".toUpperCase();
    }
}
