// 
// Decompiled by Procyon v0.5.36
// 

public class psc_l4 extends psc_dv
{
    private static final String a = "RSA_With_3DES_EDE_CBC_SHA";
    private static final String b;
    
    public psc_l4() {
        super("RSA", "RSA", "3DES_EDE/CBC/NoPad", "SHA1");
    }
    
    public int f() {
        return 24;
    }
    
    public int g() {
        return 8;
    }
    
    public int h() {
        return 20;
    }
    
    public String i() {
        return "RSA_With_3DES_EDE_CBC_SHA";
    }
    
    public String a(final int n) {
        return psc_l4.b;
    }
    
    public psc_dw j() throws psc_d {
        final psc_l4 psc_l4 = new psc_l4();
        psc_l4.a(this.n());
        psc_l4.a(this.o());
        return psc_l4;
    }
    
    public byte[] b(final int n) {
        if (n == 2) {
            return null;
        }
        return new byte[] { 0, 10 };
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
        b = "SSL_" + "RSA_With_3DES_EDE_CBC_SHA".toUpperCase();
    }
}
