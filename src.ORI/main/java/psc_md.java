// 
// Decompiled by Procyon v0.5.36
// 

public class psc_md extends psc_dv
{
    private static final String a = "DHE_DSS_With_3DES_EDE_CBC_SHA";
    private static final String b;
    
    public psc_md() {
        super("DH", "DSA", "3DES_EDE/CBC/NoPad", "SHA1");
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
        return "DHE_DSS_With_3DES_EDE_CBC_SHA";
    }
    
    public String a(final int n) {
        return psc_md.b;
    }
    
    public psc_dw j() throws psc_d {
        final psc_md psc_md = new psc_md();
        psc_md.a(this.n());
        psc_md.a(this.o());
        return psc_md;
    }
    
    public byte[] b(final int n) {
        if (n == 2) {
            return null;
        }
        return new byte[] { 0, 19 };
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
        b = "SSL_" + "DHE_DSS_With_3DES_EDE_CBC_SHA".toUpperCase();
    }
}
