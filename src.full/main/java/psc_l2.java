// 
// Decompiled by Procyon v0.5.36
// 

public class psc_l2 extends psc_dv
{
    public static final String a = "RSA_With_RC2_CBC_MD5";
    public static final String b = "SSL_CK_RC2_128_CBC_WITH_MD5";
    
    public psc_l2() {
        super("RSA", "RSA", "RC2-128/CBC/NoPad", "MD5");
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
        return "RSA_With_RC2_CBC_MD5";
    }
    
    public String a(final int n) {
        return "SSL_CK_RC2_128_CBC_WITH_MD5";
    }
    
    public psc_dw j() throws psc_d {
        final psc_l2 psc_l2 = new psc_l2();
        psc_l2.a(this.n());
        psc_l2.a(this.o());
        return psc_l2;
    }
    
    public byte[] b(final int n) {
        if (n == 2) {
            return new byte[] { 3, 0, -128 };
        }
        return null;
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
}
