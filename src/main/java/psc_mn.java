// 
// Decompiled by Procyon v0.5.36
// 

public class psc_mn extends psc_dv
{
    private static final String a = "DH_Anon_With_3DES_EDE_CBC_SHA";
    private static final String b;
    
    public psc_mn() {
        super("DH", null, "3DES_EDE/CBC/NoPad", "SHA1");
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
        return "DH_Anon_With_3DES_EDE_CBC_SHA";
    }
    
    public String a(final int n) {
        return psc_mn.b;
    }
    
    public psc_dw j() throws psc_d {
        final psc_mn psc_mn = new psc_mn();
        psc_mn.a(this.n());
        psc_mn.a(this.o());
        return psc_mn;
    }
    
    public byte[] b(final int n) {
        if (n == 2) {
            return null;
        }
        return new byte[] { 0, 27 };
    }
    
    public boolean k() {
        return false;
    }
    
    public boolean l() {
        return true;
    }
    
    public boolean m() {
        return false;
    }
    
    static {
        b = "SSL_" + "DH_Anon_With_3DES_EDE_CBC_SHA".toUpperCase();
    }
}
