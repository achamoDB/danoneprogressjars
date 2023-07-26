// 
// Decompiled by Procyon v0.5.36
// 

public class psc_du extends psc_dv
{
    public static final String a = "RSA_With_RC4_MD5";
    private static final String b;
    public static final String c = "SSL_CK_RC4_128_WITH_MD5";
    
    public psc_du() {
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
        return "RSA_With_RC4_MD5";
    }
    
    public String a(final int n) {
        if (n == 2) {
            return "SSL_CK_RC4_128_WITH_MD5";
        }
        return psc_du.b;
    }
    
    public psc_dw j() throws psc_d {
        final psc_du psc_du = new psc_du();
        psc_du.a(this.n());
        psc_du.a(this.o());
        return psc_du;
    }
    
    public byte[] b(final int n) {
        if (n == 2) {
            return new byte[] { 1, 0, -128 };
        }
        return new byte[] { 0, 4 };
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
        b = "SSL_" + "RSA_With_RC4_MD5".toUpperCase();
    }
}
