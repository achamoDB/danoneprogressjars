// 
// Decompiled by Procyon v0.5.36
// 

public class psc_mo extends psc_dv
{
    private static final String a = "DH_Anon_With_DES_CBC_SHA";
    private static final String b;
    
    public psc_mo() {
        super("DH", null, "DES/CBC/NoPad", "SHA1");
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
        return "DH_Anon_With_DES_CBC_SHA";
    }
    
    public String a(final int n) {
        return psc_mo.b;
    }
    
    public psc_dw j() throws psc_d {
        final psc_mo psc_mo = new psc_mo();
        psc_mo.a(this.n());
        psc_mo.a(this.o());
        return psc_mo;
    }
    
    public byte[] b(final int n) {
        if (n == 2) {
            return null;
        }
        return new byte[] { 0, 26 };
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
        b = "SSL_" + "DH_Anon_With_DES_CBC_SHA".toUpperCase();
    }
}
