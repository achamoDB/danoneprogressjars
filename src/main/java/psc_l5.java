// 
// Decompiled by Procyon v0.5.36
// 

public class psc_l5 extends psc_dv
{
    public static final String a = "RSA_With_DES_CBC_MD5";
    public static final String b = "SSL_CK_DES_64_CBC_WITH_MD5";
    
    public psc_l5() {
        super("RSA", "RSA", "DES/CBC/NoPad", "MD5");
    }
    
    public int f() {
        return 8;
    }
    
    public int g() {
        return 8;
    }
    
    public int h() {
        return 16;
    }
    
    public String i() {
        return "RSA_With_DES_CBC_MD5";
    }
    
    public String a(final int n) {
        return "SSL_CK_DES_64_CBC_WITH_MD5";
    }
    
    public psc_dw j() throws psc_d {
        final psc_l5 psc_l5 = new psc_l5();
        psc_l5.a(this.o());
        psc_l5.a(this.n());
        return psc_l5;
    }
    
    public byte[] b(final int n) {
        if (n == 2) {
            return new byte[] { 6, 0, 64 };
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
