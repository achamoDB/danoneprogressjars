// 
// Decompiled by Procyon v0.5.36
// 

public class psc_lz extends psc_dv
{
    private static final String a = "RSA_With_Null_MD5";
    private static final String b;
    
    public psc_lz() {
        super("RSA", "RSA", null, "MD5");
        this.e();
    }
    
    public int f() {
        return 0;
    }
    
    public int s() {
        return 1;
    }
    
    public int b(final byte[] array, final int n) {
        return 0;
    }
    
    public int a(final byte[] array, final int n) {
        return 0;
    }
    
    public int h(final byte[] array, final int n) {
        return 0;
    }
    
    public int g(final byte[] array, final int n) {
        return 0;
    }
    
    public int g() {
        return 0;
    }
    
    public int h() {
        return 16;
    }
    
    public String i() {
        return "RSA_With_Null_MD5";
    }
    
    public String a(final int n) {
        return psc_lz.b;
    }
    
    public psc_dw j() throws psc_d {
        final psc_lz psc_lz = new psc_lz();
        psc_lz.a(this.n());
        psc_lz.a(this.o());
        return psc_lz;
    }
    
    public int a(final byte[] array, final int n, final int n2, final byte[] array2, final int n3) {
        System.arraycopy(array, n, array2, n3, n2);
        return n2;
    }
    
    public int b(final byte[] array, final int n, final int n2, final byte[] array2, final int n3) {
        System.arraycopy(array, n, array2, n3, n2);
        return n2;
    }
    
    public byte[] b(final int n) {
        if (n == 2) {
            return null;
        }
        return new byte[] { 0, 1 };
    }
    
    public boolean k() {
        return true;
    }
    
    public boolean l() {
        return false;
    }
    
    public boolean m() {
        return false;
    }
    
    protected void a() {
    }
    
    protected void c() {
    }
    
    static {
        b = "SSL_" + "RSA_With_Null_MD5".toUpperCase();
    }
}
