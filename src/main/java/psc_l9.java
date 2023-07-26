// 
// Decompiled by Procyon v0.5.36
// 

public class psc_l9 extends psc_dv
{
    public static final String a = "RSA_Export_With_RC2_40_CBC_MD5";
    private static final String b;
    public static final String c = "SSL_CK_RC2_128_CBC_EXPORT40_WITH_MD5";
    
    public psc_l9() {
        super("RSA", "RSA", "RC2/CBC/NoPad", "MD5");
    }
    
    public int f() {
        return 16;
    }
    
    public int g() {
        return 8;
    }
    
    public int h() {
        return 16;
    }
    
    public String i() {
        return "RSA_Export_With_RC2_40_CBC_MD5";
    }
    
    public String a(final int n) {
        if (n == 2) {
            return psc_l9.b;
        }
        return "SSL_CK_RC2_128_CBC_EXPORT40_WITH_MD5";
    }
    
    public psc_dw j() throws psc_d {
        final psc_l9 psc_l9 = new psc_l9();
        psc_l9.a(this.n());
        psc_l9.a(this.o());
        return psc_l9;
    }
    
    public byte[] b(final int n) {
        if (n == 2) {
            return new byte[] { 4, 0, -128 };
        }
        return new byte[] { 0, 6 };
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
        b = "SSL_" + "RSA_Export_With_RC2_40_CBC_MD5".toUpperCase();
    }
}
