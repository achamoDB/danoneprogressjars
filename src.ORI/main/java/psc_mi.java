// 
// Decompiled by Procyon v0.5.36
// 

public class psc_mi extends psc_dv
{
    private static final String a = "DH_RSA_Export_With_DES_40_CBC_SHA";
    private static final String b;
    
    public psc_mi() {
        super("DH", "RSA", "DES/CBC/NoPad", "SHA1");
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
        return "DH_RSA_Export_With_DES_40_CBC_SHA";
    }
    
    public String a(final int n) {
        return psc_mi.b;
    }
    
    public psc_dw j() throws psc_d {
        final psc_mi psc_mi = new psc_mi();
        psc_mi.a(this.n());
        psc_mi.a(this.o());
        return psc_mi;
    }
    
    public byte[] b(final int n) {
        if (n == 2) {
            return null;
        }
        return new byte[] { 0, 14 };
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
        b = "SSL_" + "DH_RSA_Export_With_DES_40_CBC_SHA".toUpperCase();
    }
}
