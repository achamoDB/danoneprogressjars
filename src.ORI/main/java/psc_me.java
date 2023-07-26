// 
// Decompiled by Procyon v0.5.36
// 

public class psc_me extends psc_dv
{
    private static final String a = "DHE_DSS_With_DES_CBC_SHA";
    private static final String b;
    
    public psc_me() {
        super("DH", "DSA", "DES/CBC/NoPad", "SHA1");
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
        return "DHE_DSS_With_DES_CBC_SHA";
    }
    
    public String a(final int n) {
        return psc_me.b;
    }
    
    public psc_dw j() throws psc_d {
        final psc_me psc_me = new psc_me();
        psc_me.a(this.n());
        psc_me.a(this.o());
        return psc_me;
    }
    
    public byte[] b(final int n) {
        if (n == 2) {
            return null;
        }
        return new byte[] { 0, 18 };
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
        b = "SSL_" + "DHE_DSS_With_DES_CBC_SHA".toUpperCase();
    }
}
