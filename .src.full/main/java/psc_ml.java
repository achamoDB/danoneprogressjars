// 
// Decompiled by Procyon v0.5.36
// 

public class psc_ml extends psc_dv implements psc_dw
{
    private static final String a = "DH_DSS_Export_With_DES_40_CBC_SHA";
    private static final String b;
    
    public psc_ml() {
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
        return "DH_DSS_Export_With_DES_40_CBC_SHA";
    }
    
    public String a(final int n) {
        return psc_ml.b;
    }
    
    public psc_dw j() throws psc_d {
        final psc_ml psc_ml = new psc_ml();
        psc_ml.a(this.n());
        psc_ml.a(this.o());
        return psc_ml;
    }
    
    public byte[] b(final int n) {
        if (n == 2) {
            return null;
        }
        return new byte[] { 0, 11 };
    }
    
    public boolean k() {
        return true;
    }
    
    public boolean l() {
        return false;
    }
    
    public boolean m() {
        return false;
    }
    
    static {
        b = "SSL_" + "DH_DSS_Export_With_DES_40_CBC_SHA".toUpperCase();
    }
}
