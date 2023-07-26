// 
// Decompiled by Procyon v0.5.36
// 

public class psc_l6 extends psc_dv
{
    private static final String a = "RSA_With_DES_CBC_SHA";
    private static final String b;
    
    public psc_l6() {
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
        return "RSA_With_DES_CBC_SHA";
    }
    
    public String a(final int n) {
        return psc_l6.b;
    }
    
    public psc_dw j() throws psc_d {
        final psc_l6 psc_l6 = new psc_l6();
        psc_l6.a(this.n());
        psc_l6.a(this.o());
        return psc_l6;
    }
    
    public byte[] b(final int n) {
        if (n == 2) {
            return null;
        }
        return new byte[] { 0, 9 };
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
        b = "SSL_" + "RSA_With_DES_CBC_SHA".toUpperCase();
    }
}
