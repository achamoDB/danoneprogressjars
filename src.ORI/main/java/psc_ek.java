import java.security.SecureRandom;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_ek extends psc_ej implements Cloneable, Serializable
{
    private boolean a;
    private psc_dn b;
    psc_dc c;
    SecureRandom d;
    
    public psc_ek(final psc_ei psc_ei, final psc_az psc_az, final psc_dn b) {
        super(psc_ei, psc_az);
        this.a = false;
        this.b = b;
    }
    
    protected psc_ek() {
        this.a = false;
    }
    
    public void b(final byte[] array, final int n, final int n2) {
        this.b.a(array, n, n2);
        this.a = false;
    }
    
    public void a(final SecureRandom secureRandom) throws psc_en {
        if (secureRandom == null) {
            throw new psc_en("Salt generation needs a random object.");
        }
        final byte[] bytes = new byte[8];
        secureRandom.nextBytes(bytes);
        this.b(bytes, 0, bytes.length);
    }
    
    public byte[] p() {
        return this.b.g();
    }
    
    void a(final byte[] array, final int n) throws psc_ao, psc_be {
        try {
            this.b.a(null, null, null, array, n, 0);
        }
        catch (psc_gw psc_gw) {
            throw new psc_be(psc_gw.getMessage());
        }
    }
    
    public byte[] g() throws psc_ao {
        final String string = super.j.d() + "/" + super.i.e();
        final byte[] a = this.b.a(string, super.i.e());
        int length = 0;
        if (a != null) {
            length = a.length;
        }
        final String string2 = "PBE/" + string + "/" + this.b.e();
        try {
            return psc_q.a(string2, 9, a, 0, length);
        }
        catch (psc_m psc_m) {
            throw new psc_ao("Could not DER encode given transformation.(" + psc_m.getMessage() + ")");
        }
    }
    
    public int[] l() {
        return this.b.c();
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_ek psc_ek = (psc_ek)super.clone();
        psc_ek.d = this.d;
        psc_ek.a = this.a;
        if (this.b != null) {
            psc_ek.b = (psc_dn)this.b.clone();
        }
        if (this.c != null) {
            psc_ek.c = (psc_dc)this.c.clone();
        }
        return psc_ek;
    }
    
    public String e() {
        return this.b.d();
    }
    
    public int m() {
        try {
            return this.c().h();
        }
        catch (Exception ex) {
            return -1;
        }
    }
    
    private void a(final psc_dc c, final SecureRandom d) throws psc_en, psc_bf {
        this.c = c;
        this.d = d;
        this.b.a(this.c(), null, 1, c);
        this.a = true;
    }
    
    public void b(final psc_dc psc_dc, final SecureRandom secureRandom) throws psc_en, psc_bf {
        this.a(psc_dc, secureRandom);
        super.b(psc_dc, secureRandom);
    }
    
    public void f() throws psc_en {
        if (this.a) {
            super.o();
        }
        else {
            try {
                this.b(this.c, this.d);
            }
            catch (psc_bf psc_bf) {
                throw new psc_en("tried to macInit after having done it before");
            }
        }
    }
    
    public void c(final psc_dc psc_dc, final SecureRandom secureRandom) throws psc_en, psc_bf {
        this.a(psc_dc, secureRandom);
        super.c(psc_dc, secureRandom);
    }
    
    public void r() throws psc_en {
        if (this.a) {
            super.r();
        }
        else {
            try {
                this.c(this.c, this.d);
            }
            catch (psc_bf psc_bf) {
                throw new psc_en("tried to macInit after having done it before");
            }
        }
    }
    
    public void y() {
        super.y();
        if (this.b != null) {
            this.b.y();
        }
        if (this.c != null) {
            this.c.y();
        }
    }
}
