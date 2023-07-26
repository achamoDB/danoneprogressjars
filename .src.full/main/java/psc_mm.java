// 
// Decompiled by Procyon v0.5.36
// 

public class psc_mm extends psc_dv
{
    private static final String a = "DH_Anon_With_RC4_MD5";
    private static final String b;
    
    public psc_mm() {
        super("DH", null, "RC4", "MD5");
    }
    
    public int f() {
        return 16;
    }
    
    public int g() {
        return 0;
    }
    
    public int h() {
        return 16;
    }
    
    public String i() {
        return "DH_Anon_With_RC4_MD5";
    }
    
    public String a(final int n) {
        return psc_mm.b;
    }
    
    public psc_dw j() throws psc_d {
        final psc_mm psc_mm = new psc_mm();
        psc_mm.a(this.n());
        psc_mm.a(this.o());
        return psc_mm;
    }
    
    public byte[] b(final int n) {
        if (n == 2) {
            return null;
        }
        return new byte[] { 0, 24 };
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
        b = "SSL_" + "DH_Anon_With_RC4_MD5".toUpperCase();
    }
}
