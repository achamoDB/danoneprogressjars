// 
// Decompiled by Procyon v0.5.36
// 

public class pscl_bg extends pscl_bc
{
    private pscl_d a;
    private pscl_bi b;
    
    public pscl_bg(final String s, final String s2) {
        if (s.equals("MD5")) {
            this.a = new pscl_aq();
        }
        else {
            this.a = new pscl_c();
        }
        this.b = new pscl_bh();
    }
    
    public String d() {
        return this.a.a();
    }
    
    public String e() {
        return this.b.b();
    }
    
    public pscl_bi f() {
        return this.b;
    }
    
    public int[] g() {
        return this.b.a();
    }
    
    public void a(final byte[] array, final int n, final int n2) {
        this.b.a(array, n, n2);
    }
    
    public void a(final pscl_e pscl_e) throws pscl_bk {
        if (pscl_e == null) {
            throw new pscl_bk("Salt generation needs a random object.");
        }
        final byte[] array = new byte[8];
        pscl_e.b(array);
        this.a(array, 0, array.length);
    }
    
    public byte[] h() {
        return this.b.d();
    }
    
    public void a(final char[] array, final pscl_e pscl_e) throws pscl_h {
        byte[] a;
        try {
            a = this.b.a(this.a, array);
        }
        catch (pscl_bk pscl_bk) {
            throw new pscl_h("Can't generate key");
        }
        super.a(a, pscl_e);
    }
    
    public void b(final char[] array, final pscl_e pscl_e) throws pscl_h {
        byte[] a;
        try {
            a = this.b.a(this.a, array);
        }
        catch (pscl_bk pscl_bk) {
            throw new pscl_h("Can't generate key");
        }
        super.b(a, pscl_e);
    }
    
    public void c() {
        super.c();
        if (this.a != null) {
            this.a.g();
        }
        if (this.b != null) {
            this.b.c();
        }
    }
    
    public void finalize() {
        this.c();
    }
}
