import java.security.SecureRandom;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

class psc_do extends psc_dh implements Cloneable, Serializable
{
    private psc_az a;
    private psc_dn b;
    private byte[] c;
    
    psc_do(final psc_az a, final psc_gv psc_gv, final psc_dn b) {
        super(psc_gv);
        this.a = a;
        this.b = b;
    }
    
    private psc_do() {
    }
    
    void a(final byte[] array, final int n, final int n2) throws psc_ao, psc_be, psc_gw {
        this.b.a(super.a, null, null, array, n, n2);
    }
    
    public byte[] f() throws psc_ao {
        final byte[] f = this.b.f();
        int length = 0;
        if (f != null) {
            length = f.length;
        }
        final String string = "PBE/" + this.a.e() + "/" + super.a.d() + "/" + this.b.e();
        try {
            return psc_q.a(string, 9, f, 0, length);
        }
        catch (psc_m psc_m) {
            throw new psc_ao("Could not DER encode given transformation.(" + psc_m.getMessage() + ")");
        }
    }
    
    public String l() {
        return this.a.e();
    }
    
    public String m() {
        return this.b.d();
    }
    
    public int[] s() {
        return this.b.c();
    }
    
    public void b(final int[] array) throws psc_be {
        this.b.a(array);
    }
    
    public void c(final byte[] array, final int n, final int n2) {
        final int c = this.c();
        if (c == 3 || c == 4 || c == 6 || c == 7) {
            System.arraycopy(array, n, this.c = new byte[n2], 0, n2);
        }
        else {
            this.b.a(array, n, n2);
            this.c = null;
        }
    }
    
    public void b(final SecureRandom secureRandom) throws psc_en {
        if (secureRandom == null) {
            throw new psc_en("Salt generation needs a random object.");
        }
        final byte[] bytes = new byte[8];
        secureRandom.nextBytes(bytes);
        this.c(bytes, 0, bytes.length);
    }
    
    public byte[] u() {
        return this.b.g();
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_do psc_do = (psc_do)super.clone();
        if (this.a != null) {
            psc_do.a = (psc_az)this.a.clone();
        }
        if (this.b != null) {
            psc_do.b = (psc_dn)this.b.clone();
        }
        if (this.c != null) {
            psc_do.c = this.c.clone();
        }
        return psc_do;
    }
    
    public void c(final psc_dc psc_dc, final SecureRandom secureRandom) throws psc_en, psc_bf {
        this.d();
        this.b.a(this.a, null, this.n(), psc_dc);
        super.c(psc_dc, secureRandom);
    }
    
    public void d(final psc_dc psc_dc, final SecureRandom secureRandom) throws psc_en, psc_bf {
        this.d();
        this.b.a(this.a, null, this.n(), psc_dc);
        super.d(psc_dc, secureRandom);
    }
    
    public void v() throws psc_en {
        this.d();
        super.v();
    }
    
    public void x() throws psc_en {
        this.d();
        super.x();
    }
    
    private void d() {
        final int c = this.c();
        if ((c == 3 || c == 4 || c == 6 || c == 7) && this.c != null) {
            this.b.a(this.c, 0, this.c.length);
            this.c = null;
        }
    }
    
    public void y() {
        super.y();
        if (this.a != null) {
            this.a.y();
        }
        if (this.b != null) {
            this.b.y();
        }
    }
    
    protected void finalize() {
        try {
            this.y();
        }
        finally {
            super.finalize();
        }
    }
}
