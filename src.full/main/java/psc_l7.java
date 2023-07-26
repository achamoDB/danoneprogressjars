// 
// Decompiled by Procyon v0.5.36
// 

public class psc_l7 extends psc_dv
{
    private static final String a = "RSA_Export_With_DES_40_CBC_SHA";
    private static final String b;
    
    public psc_l7() {
        super("RSA", "RSA", "DES/CBC/NoPad", "SHA1");
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
        return "RSA_Export_With_DES_40_CBC_SHA";
    }
    
    public String a(final int n) {
        return psc_l7.b;
    }
    
    public psc_dw j() throws psc_d {
        final psc_l7 psc_l7 = new psc_l7();
        psc_l7.a(this.n());
        psc_l7.a(this.o());
        return psc_l7;
    }
    
    public byte[] b(final int n) {
        if (n == 2) {
            return null;
        }
        return new byte[] { 0, 8 };
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
        b = "SSL_" + "RSA_Export_With_DES_40_CBC_SHA".toUpperCase();
    }
}
