// 
// Decompiled by Procyon v0.5.36
// 

public class psc_mb extends psc_dv
{
    private static final String a = "DHE_RSA_With_DES_CBC_SHA";
    private static final String b;
    
    public psc_mb() {
        super("DH", "RSA", "DES/CBC/NoPad", "SHA1");
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
        return "DHE_RSA_With_DES_CBC_SHA";
    }
    
    public String a(final int n) {
        return psc_mb.b;
    }
    
    public psc_dw j() throws psc_d {
        final psc_mb psc_mb = new psc_mb();
        psc_mb.a(this.n());
        psc_mb.a(this.o());
        return psc_mb;
    }
    
    public byte[] b(final int n) {
        if (n == 2) {
            return null;
        }
        return new byte[] { 0, 21 };
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
        b = "SSL_" + "DHE_RSA_With_DES_CBC_SHA".toUpperCase();
    }
}
