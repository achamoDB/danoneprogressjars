// 
// Decompiled by Procyon v0.5.36
// 

public class psc_mg extends psc_dv
{
    private static final String a = "DH_RSA_With_3DES_EDE_CBC_SHA";
    private static final String b;
    
    public psc_mg() {
        super("DH", "RSA", "3DES_EDE/CBC/NoPad", "SHA1");
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
        return "DH_RSA_With_3DES_EDE_CBC_SHA";
    }
    
    public String a(final int n) {
        return psc_mg.b;
    }
    
    public psc_dw j() throws psc_d {
        final psc_mg psc_mg = new psc_mg();
        psc_mg.a(this.n());
        psc_mg.a(this.o());
        return psc_mg;
    }
    
    public byte[] b(final int n) {
        if (n == 2) {
            return null;
        }
        return new byte[] { 0, 16 };
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
        b = "SSL_" + "DH_RSA_With_3DES_EDE_CBC_SHA".toUpperCase();
    }
}
