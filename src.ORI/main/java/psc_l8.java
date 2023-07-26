// 
// Decompiled by Procyon v0.5.36
// 

public class psc_l8 extends psc_dv
{
    public static final String a = "RSA_Export_With_RC4_40_MD5";
    private static final String b;
    public static final String c = "SSL_CK_RC4_128_EXPORT40_WITH_MD5";
    
    public psc_l8() {
        super("RSA", "RSA", "RC4", "MD5");
    }
    
    public int f() {
        return 16;
    }
    
    public int g() {
        return 0;
    }
    
    public int h() {
        return 16;
    }
    
    public String i() {
        return "RSA_Export_With_RC4_40_MD5";
    }
    
    public String a(final int n) {
        if (n == 2) {
            return "SSL_CK_RC4_128_EXPORT40_WITH_MD5";
        }
        return psc_l8.b;
    }
    
    public psc_dw j() throws psc_d {
        final psc_l8 psc_l8 = new psc_l8();
        psc_l8.a(this.n());
        psc_l8.a(this.o());
        return psc_l8;
    }
    
    public byte[] b(final int n) {
        if (n == 2) {
            return new byte[] { 2, 0, -128 };
        }
        return new byte[] { 0, 3 };
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
        b = "SSL_" + "RSA_Export_With_RC4_40_MD5".toUpperCase();
    }
}
