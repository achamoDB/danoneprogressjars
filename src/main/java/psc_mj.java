// 
// Decompiled by Procyon v0.5.36
// 

public class psc_mj extends psc_dv
{
    private static final String a = "DH_DSS_With_3DES_EDE_CBC_SHA";
    private static final String b;
    
    public psc_mj() {
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
        return "DH_DSS_With_3DES_EDE_CBC_SHA";
    }
    
    public String a(final int n) {
        return psc_mj.b;
    }
    
    public psc_dw j() throws psc_d {
        final psc_mj psc_mj = new psc_mj();
        psc_mj.a(this.n());
        psc_mj.a(this.o());
        return psc_mj;
    }
    
    public byte[] b(final int n) {
        if (n == 2) {
            return null;
        }
        return new byte[] { 0, 13 };
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
        b = "SSL_" + "DH_DSS_With_3DES_EDE_CBC_SHA".toUpperCase();
    }
}
