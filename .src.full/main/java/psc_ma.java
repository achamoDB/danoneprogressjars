// 
// Decompiled by Procyon v0.5.36
// 

public class psc_ma extends psc_dv
{
    private static final String a = "DHE_RSA_With_3DES_EDE_CBC_SHA";
    private static final String b;
    
    public psc_ma() {
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
        return "DHE_RSA_With_3DES_EDE_CBC_SHA";
    }
    
    public String a(final int n) {
        return psc_ma.b;
    }
    
    public psc_dw j() throws psc_d {
        final psc_ma psc_ma = new psc_ma();
        psc_ma.a(this.n());
        psc_ma.a(this.o());
        return psc_ma;
    }
    
    public byte[] b(final int n) {
        if (n == 2) {
            return null;
        }
        return new byte[] { 0, 22 };
    }
    
    public boolean k() {
        return false;
    }
    
    public boolean l() {
        return false;
    }
    
    public boolean m() {
        return true;
    }
    
    static {
        b = "SSL_" + "DHE_RSA_With_3DES_EDE_CBC_SHA".toUpperCase();
    }
}
