import java.io.IOException;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_in extends psc_h9
{
    psc_d2 a;
    boolean b;
    psc_e[] c;
    
    public psc_in(final psc_c2 c, final psc_ia psc_ia, final psc_h6 d, final psc_h7 e, final String j, final psc_h5 psc_h5) throws psc_d, psc_b {
        this.b = false;
        super.c = c;
        d.a(super.a = new psc_g6());
        e.a(super.a);
        super.j = j;
        if (psc_ia.a() == 0) {
            final byte[][] f = psc_ia.f();
            if (f.length == 0) {
                throw new psc_c("No supported ciphers", 2, 1);
            }
            super.b = this.a(f);
            if (super.b == null) {
                throw new psc_c("No available cipher suite", 2, 1);
            }
            super.b = super.b.j();
            if ((c.v() & 0x4) == 0x4) {
                c.z().println("Using Cipher Suite: " + super.b.i());
            }
        }
        else {
            this.b = true;
        }
        super.d = d;
        super.e = e;
        super.g = new byte[psc_h5.b().length];
        System.arraycopy(psc_h5.b(), 0, super.g, 0, super.g.length);
        this.a = c.d(psc_h5.c());
        if (this.a == null && this.b) {
            throw new psc_c("Unexpected Message", 2, 10);
        }
        super.i = psc_ia.e();
        try {
            this.a(psc_ia.d());
            this.e();
        }
        catch (psc_c psc_c) {
            if ((c.v() & 0x1) == 0x1) {
                c.z().println("STATE: Sending alert because: " + psc_c.getMessage());
            }
            try {
                super.e.write(0);
                super.e.write(psc_c.b());
                super.e.flush();
                super.e.close();
            }
            catch (IOException ex) {}
            throw psc_c;
        }
        catch (psc_b psc_b) {
            throw psc_b;
        }
    }
    
    private void a(final byte[] array) throws psc_c, psc_d {
        if (array.length == 0) {
            return;
        }
        try {
            (this.c = new psc_e[1])[0] = new psc_e(array, 0, 0);
            try {
                super.b.a(this.c[0].b(super.c.e()));
            }
            catch (psc_d psc_d) {
                super.b.e(this.c[0].w(), 0);
            }
        }
        catch (psc_g psc_g) {
            throw new psc_c("Could not handle the server's certificate: " + psc_g.getMessage(), 1, 3);
        }
        try {
            if (super.c.t().a(super.c, this.c, super.b) == -1) {
                throw new psc_c("certificate unknown", 1, 3);
            }
        }
        catch (psc_c psc_c) {
            if (psc_c.b() == 42 || psc_c.b() == 44 || psc_c.b() == 45) {
                throw new psc_c(psc_c.getMessage(), 1, 3);
            }
            throw psc_c;
        }
        if (this.a != null) {
            this.a.a(this.c);
        }
    }
    
    private void e() throws psc_d, psc_b {
        if (this.b) {
            super.b = this.a.l();
            if (super.b != null) {
                super.b = super.b.j();
            }
            super.a = super.b;
            super.e.a(super.a);
            super.d.a(super.a);
            this.a(this.a.i(), super.g, super.i, false);
            if (super.b.s() != 1) {
                super.b.h(this.a.d(), 0);
                super.b.g(this.a.d(), 0);
            }
        }
        else {
            this.f();
        }
        this.h();
        this.g();
        final psc_id i = this.i();
        if (i != null) {
            this.a(i);
        }
        if (i != null) {
            try {
                if (super.d.read() != 6) {
                    throw new psc_c("Unexpected Message", 2, 10);
                }
                this.k();
            }
            catch (IOException ex) {
                throw new psc_d(ex.getMessage());
            }
        }
        if (super.c.d(this.a.e()) == null) {
            super.c.b(this.a);
        }
    }
    
    private void f() throws psc_d {
        if ((super.c.v() & 0x1) == 0x1) {
            super.c.z().println("STATE: Sending the Client's Master Key");
        }
        final int f = super.b.f();
        int n = 0;
        if (f > 5 && super.b.k()) {
            n = f - 5;
        }
        super.f = new byte[f];
        super.c.q().nextBytes(super.f);
        final byte[] array = new byte[n];
        System.arraycopy(super.f, 0, array, 0, n);
        final byte[] array2 = new byte[f - n];
        System.arraycopy(super.f, n, array2, 0, array2.length);
        final byte[] array3 = new byte[super.b.t()];
        super.b.b(array2, 0, array2.length, array3, 0, super.c.q());
        byte[] array4;
        if (super.b.s() != 1) {
            array4 = new byte[super.b.s()];
            super.c.q().nextBytes(array4);
            super.b.h(array4, 0);
            super.b.g(array4, 0);
        }
        else {
            array4 = new byte[0];
        }
        this.a(super.f, super.g, super.i, false);
        final psc_ib x = new psc_ib(super.b.b(2), array, array3, array4);
        if ((super.c.v() & 0x1) == 0x1) {
            super.c.z().println(x);
        }
        try {
            x.a(super.e);
        }
        catch (IOException ex) {
            throw new psc_d("An IOException occured while writing the client master key: " + ex.getMessage());
        }
        super.a = super.b;
        super.e.a(super.a);
        super.d.a(super.a);
    }
    
    private void g() throws psc_d {
        if ((super.c.v() & 0x1) == 0x1) {
            super.c.z().println("STATE: Sending the Client Finished Message");
        }
        final psc_ie x = new psc_ie(super.i);
        if ((super.c.v() & 0x1) == 0x1) {
            super.c.z().println(x);
        }
        try {
            x.a(super.e);
        }
        catch (IOException ex) {
            throw new psc_d("An IO Exception occured while sending the client finished message");
        }
    }
    
    private void h() throws psc_d {
        if ((super.c.v() & 0x1) == 0x1) {
            super.c.z().println("STATE: Receiving the server's verify message");
        }
        final psc_ic psc_ic = new psc_ic();
        try {
            psc_ic.b(super.d);
        }
        catch (IOException ex) {
            throw new psc_d("An IO exception occured while reading the server verify message: " + ex.getMessage());
        }
        if ((super.c.v() & 0x1) == 0x1) {
            super.c.z().println(psc_ic.toString());
        }
        final byte[] a = psc_ic.a();
        if (!this.a(a, super.g)) {
            final byte[] array = new byte[16];
            System.arraycopy(super.g, 0, array, 0, 16);
            if (!this.a(a, array)) {
                throw new psc_d("Server Verify message did not verify");
            }
        }
    }
    
    private psc_id i() throws psc_d, psc_c {
        if ((super.c.v() & 0x1) == 0x1) {
            super.c.z().println("STATE: Checking for a certificate request");
        }
        try {
            final int read = super.d.read();
            if (read == 7) {
                if ((super.c.v() & 0x1) == 0x1) {
                    super.c.z().println("STATE: The server requested our certificate");
                }
                final psc_id psc_id = new psc_id();
                psc_id.b(super.d);
                return psc_id;
            }
            if (read == 6) {
                this.k();
                return null;
            }
            throw new psc_c("Unexpected Message", 2, 10);
        }
        catch (IOException ex) {
            throw new psc_d("An IOException occured while checking for a certificate request");
        }
    }
    
    private void j() throws psc_d, psc_b {
        if ((super.c.v() & 0x1) == 0x1) {
            super.c.z().println("STATE: Sending our NO_CERTIFICATE warning");
        }
        try {
            super.e.write(1);
            super.e.write(41);
            super.e.flush();
        }
        catch (psc_b psc_b) {
            throw psc_b;
        }
        catch (IOException ex) {
            throw new psc_d("An IOException occured while trying to send a NO_CERTIFICATE exception");
        }
    }
    
    private void a(final psc_id psc_id) throws psc_d, psc_b {
        if ((super.c.v() & 0x1) == 0x1) {
            super.c.z().println("STATE: Sending our certificate");
        }
        if (psc_id.b() != 1) {
            this.j();
            return;
        }
        try {
            final psc_ds a = super.c.a(super.b);
            if (a == null) {
                this.j();
                return;
            }
            final byte[] b = a.b(0);
            if (this.c == null) {
                this.c = this.a.j();
            }
            final byte[] array = new byte[super.h.length + psc_id.a().length + this.c[0].c(0)];
            if (a.i()) {
                super.b.a(a.f(), 0, a.h());
            }
            else {
                super.b.a(a.g());
            }
            super.b.c(a.j());
            System.arraycopy(super.h, 0, array, 0, super.h.length);
            System.arraycopy(psc_id.a(), 0, array, super.h.length, psc_id.a().length);
            this.c[0].d(array, super.h.length + psc_id.a().length, 0);
            byte[] array2;
            try {
                final psc_da a2 = psc_da.a("MD5", super.c.e());
                a2.i();
                a2.a(array, 0, array.length);
                final byte[] j = a2.j();
                final int a3 = super.b.a(j, 0, j.length, array, 0, super.c.q());
                array2 = new byte[a3];
                System.arraycopy(array, 0, array2, 0, a3);
            }
            catch (psc_ap psc_ap) {
                throw new psc_c("Could not sign data", 2, 1);
            }
            if ((super.c.v() & 0x1) == 0x1) {
                super.c.z().println("STATE: Sending our certificate");
            }
            final psc_if psc_if = new psc_if(psc_h6.d, b, array2);
            psc_if.a(super.e);
            if ((super.c.v() & 0x1) == 0x1) {
                super.c.z().println(psc_if.toString());
            }
        }
        catch (psc_g psc_g) {
            this.j();
        }
        catch (psc_b psc_b) {
            throw psc_b;
        }
        catch (IOException ex) {
            throw new psc_d("An IOException occured while sending our certificate");
        }
    }
    
    private void k() throws psc_d {
        if ((super.c.v() & 0x1) == 0x1) {
            super.c.z().println("STATE: Receiving the server's finished message");
        }
        final psc_ig psc_ig = new psc_ig();
        try {
            psc_ig.b(super.d);
        }
        catch (IOException ex) {
            throw new psc_d("An IO exception occured while reading the server finished message: " + ex.getMessage());
        }
        if ((super.c.v() & 0x1) == 0x1) {
            super.c.z().println(psc_ig.toString());
        }
        if (!this.b) {
            (this.a = new psc_d2(psc_ig.a(), super.j, System.currentTimeMillis(), super.f, super.a, null, null, super.c, 2)).a(this.c);
            if (super.b.s() != 1) {
                this.a.a(super.b.w());
            }
        }
    }
    
    private psc_dw a(final byte[][] array) throws psc_d {
        final psc_dw[] c = super.c.c();
        for (int i = 0; i < c.length; ++i) {
            for (int j = 0; j < array.length; ++j) {
                if (c[i].b(2) != null && this.a(array[j], c[i].b(2))) {
                    return c[i];
                }
            }
        }
        return null;
    }
    
    public psc_c5 a() {
        return null;
    }
    
    public psc_d2 b() {
        return this.a;
    }
    
    public psc_e[] c() {
        return this.c;
    }
}
