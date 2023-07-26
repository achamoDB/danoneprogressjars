import java.security.SecureRandom;
import java.util.Date;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_c7 implements psc_c8
{
    protected Date a;
    protected int b;
    protected psc_e c;
    protected psc_dw d;
    protected boolean e;
    protected psc_c2 f;
    
    public int a(final psc_c2 f, final psc_e[] array, final psc_dw d) throws psc_c, psc_d {
        this.a = new Date();
        this.d = d;
        this.e = false;
        this.f = f;
        final psc_e[] j = f.j();
        try {
            for (int i = 0; i < array.length; ++i) {
                this.b = i;
                this.c = array[i];
                if (i > 0) {
                    this.a(array[i - 1], f);
                }
                this.c();
                this.a();
                final int a = this.a(this.c, j, f);
                if (a > -1) {
                    this.b = i + 1;
                    this.c = j[a];
                    this.e = true;
                    this.c();
                    return a;
                }
            }
            return -1;
        }
        catch (psc_c psc_c) {
            throw psc_c;
        }
        catch (Exception ex) {
            throw new psc_d("Cannot verify certificate: " + ex.getMessage());
        }
    }
    
    protected void a(final psc_e psc_e, final psc_c2 psc_c2) throws psc_c, psc_g {
        if (this.a(psc_e, new psc_e[] { this.c }, psc_c2) == -1) {
            this.e();
        }
    }
    
    private int a(final psc_e psc_e, final psc_e[] array, final psc_c2 psc_c2) throws psc_c, psc_g {
        final int a = this.a(psc_e, array);
        if (a > -1) {
            this.a(psc_e, array[a], psc_c2);
        }
        return a;
    }
    
    private int a(final psc_e psc_e, final psc_e[] array) {
        for (int i = 0; i < array.length; ++i) {
            if (array[i].f().equals(psc_e.g())) {
                return i;
            }
        }
        return -1;
    }
    
    private void a(final psc_e psc_e, final psc_e psc_e2, final psc_c2 psc_c2) throws psc_c, psc_g {
        if (!psc_e.a(psc_c2.e(), psc_e2.b(psc_c2.e()), psc_c2.q())) {
            this.f();
        }
    }
    
    protected void c() throws psc_c, psc_g {
        this.d();
        final psc_bg n = this.c.n();
        if (n == null) {
            return;
        }
        if (this.c.e() != 2) {
            this.h();
            return;
        }
        this.a(n);
        this.b(n);
        this.c(n);
    }
    
    protected void d() throws psc_c {
        if (!this.c.a(this.a)) {
            this.g();
        }
    }
    
    protected void a(final psc_bg psc_bg) throws psc_c, psc_g {
        final psc_br psc_br = (psc_br)psc_bg.d(19);
        if (psc_br == null) {
            return;
        }
        if (!psc_br.b()) {
            return;
        }
        if (this.b > 0) {
            if (!psc_br.a()) {
                this.i();
                return;
            }
            final int g = psc_br.g();
            if (psc_br.g() != -1 && g < this.b - 1) {
                this.k();
            }
        }
        else if (psc_br.a()) {
            this.j();
        }
    }
    
    protected void b(final psc_bg psc_bg) throws psc_c, psc_g {
        final psc_bn psc_bn = (psc_bn)psc_bg.d(15);
        if (psc_bn == null) {
            return;
        }
        if (!psc_bn.b()) {
            return;
        }
        if (this.b == 0) {
            if (this.d.ae()) {
                if (!psc_bn.a(536870912) && !psc_bn.a(268435456) && !psc_bn.a(Integer.MIN_VALUE)) {
                    this.a("Invalid RSA cipher certificate");
                }
            }
            else if (!psc_bn.a(Integer.MIN_VALUE)) {
                this.a("Not signing certificate");
            }
        }
        else if (!psc_bn.a(67108864)) {
            this.a("CA certificate has invalid key usage certificate extension");
        }
    }
    
    protected void c(final psc_bg psc_bg) throws psc_c, psc_g {
        for (int a = psc_bg.a(), i = 0; i < a; ++i) {
            final psc_aj c = psc_bg.c(i);
            final int c2 = c.c();
            if (c.b() && c2 != 15 && c2 != 19 && c2 != 37) {
                this.l();
                return;
            }
        }
    }
    
    protected void a() throws psc_c, psc_g {
        if (b(this.c, this.f)) {
            this.b();
        }
    }
    
    public static final boolean b(final psc_e psc_e, final psc_c2 psc_c2) throws psc_g {
        boolean b = false;
        if (!psc_c2.c(psc_n1.b)) {
            final psc_al b2 = psc_e.b("Java");
            if (b2.l().equalsIgnoreCase("RSA")) {
                final byte[] array = b2.m()[1];
                if (array.length == 1 && array[0] == 1) {
                    b = true;
                }
            }
        }
        return b;
    }
    
    protected void e() throws psc_c {
        throw new psc_c("Bad certificate in chain: issuer name and subject names do not match.", 2, 42);
    }
    
    protected void f() throws psc_c {
        throw new psc_c("Bad certificate in chain: invalid signature.", 2, 42);
    }
    
    protected void g() throws psc_c {
        throw new psc_c("Certificate expired", 2, 45);
    }
    
    protected void h() throws psc_c {
        throw new psc_c("Not a supported Certificate type", 2, 43);
    }
    
    protected void i() throws psc_c {
        throw new psc_c("CA certificate basic constraints indicates end-entity certificate", 2, 43);
    }
    
    protected void j() throws psc_c {
        throw new psc_c("End-entity certificate basic constraints indicates CA certificate", 2, 43);
    }
    
    protected void k() throws psc_c {
        throw new psc_c("A CA certificate's path length constraint has been breached.", 2, 43);
    }
    
    protected void a(final String s) throws psc_c {
        throw new psc_c(s, 2, 43);
    }
    
    protected void l() throws psc_c {
        throw new psc_c("Unknown critical certificate extension.", 2, 43);
    }
    
    protected void b() throws psc_c {
        throw new psc_c("Weak public key detected", 2, 43);
    }
}
