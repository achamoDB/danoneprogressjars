// 
// Decompiled by Procyon v0.5.36
// 

public class psc_l3 extends psc_dv
{
    public static final String a = "RSA_With_3DES_EDE_CBC_MD5";
    public static final String b = "SSL_CK_DES_192_EDE3_CBC_WITH_MD5";
    
    public psc_l3() {
        super("RSA", "RSA", "3DES_EDE/CBC/NoPad", "MD5");
    }
    
    public int f() {
        return 24;
    }
    
    public int g() {
        return 8;
    }
    
    public int h() {
        return 16;
    }
    
    public String i() {
        return "RSA_With_3DES_EDE_CBC_MD5";
    }
    
    public String a(final int n) {
        return "SSL_CK_DES_192_EDE3_CBC_WITH_MD5";
    }
    
    public psc_dw j() throws psc_d {
        final psc_l3 psc_l3 = new psc_l3();
        psc_l3.a(this.n());
        psc_l3.a(this.o());
        return psc_l3;
    }
    
    public byte[] b(final int n) {
        if (n == 2) {
            return new byte[] { 7, 0, -64 };
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
