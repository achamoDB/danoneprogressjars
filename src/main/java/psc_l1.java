// 
// Decompiled by Procyon v0.5.36
// 

public class psc_l1 extends psc_dv
{
    private static final String a = "RSA_With_RC4_SHA";
    private static final String b;
    
    public psc_l1() {
        super("RSA", "RSA", "RC4", "SHA1");
    }
    
    public int f() {
        return 16;
    }
    
    public int g() {
        return 0;
    }
    
    public int h() {
        return 20;
    }
    
    public String i() {
        return "RSA_With_RC4_SHA";
    }
    
    public String a(final int n) {
        return psc_l1.b;
    }
    
    public psc_dw j() throws psc_d {
        final psc_l1 psc_l1 = new psc_l1();
        psc_l1.a(this.n());
        psc_l1.a(this.o());
        return psc_l1;
    }
    
    public byte[] b(final int n) {
        if (n == 2) {
            return null;
        }
        return new byte[] { 0, 5 };
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
        b = "SSL_" + "RSA_With_RC4_SHA".toUpperCase();
    }
}
