import java.io.IOException;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_h8 extends psc_h9
{
    private psc_h5 a;
    private psc_d2 b;
    private boolean c;
    private psc_e[] d;
    private psc_ds e;
    
    public psc_h8(final psc_c2 c, final psc_h5 a, final psc_h6 d, final psc_h7 e, final String j) throws psc_c, psc_b, psc_d {
        this.c = false;
        this.d = null;
        this.e = null;
        super.c = c;
        super.a = new psc_g6();
        super.d = d;
        super.e = e;
        d.a(super.a);
        e.a(super.a);
        super.j = j;
        this.a = a;
        super.g = a.b();
        this.b = c.d(a.c());
        try {
            this.f();
        }
        catch (psc_c psc_c) {
            if ((c.v() & 0x1) == 0x1) {
                c.z().println("STATE: Sending alert because: " + psc_c.getMessage());
            }
            try {
                super.e.write(psc_c.a());
                super.e.write(psc_c.b());
                super.e.flush();
                super.e.close();
            }
            catch (IOException ex2) {}
            throw psc_c;
        }
        catch (psc_b psc_b) {
            throw psc_b;
        }
        catch (IOException ex) {
            throw new psc_d(ex.getMessage());
        }
    }
    
    private void f() throws psc_c, psc_b, psc_d {
        try {
            this.g();
            if (this.b == null) {
                this.h();
            }
            else {
                super.b = this.b.l();
                if (super.b == null) {
                    throw new psc_c("No supported Cipher Suites", 0, 1);
                }
                this.a(this.b.i(), super.g, super.i, true);
                super.a = super.b;
                super.d.a(super.a);
                super.e.a(super.a);
            }
            this.j();
            if (super.c.s() != 0) {
                this.a();
            }
            this.i();
            if (super.c.s() != 0) {
                this.l();
            }
            this.k();
            if (super.c.d(this.b.e()) == null) {
                super.c.b(this.b);
            }
        }
        catch (psc_c psc_c) {
            throw psc_c;
        }
        catch (psc_b psc_b) {
            throw psc_b;
        }
        catch (IOException ex) {
            throw new psc_d(ex.getMessage());
        }
    }
    
    private void g() throws psc_d {
        if ((super.c.v() & 0x1) == 0x1) {
            super.c.z().println("STATE: Sending the Server Hello");
        }
        super.i = new byte[16];
        super.c.q().nextBytes(super.i);
        try {
            boolean b = true;
            boolean b2 = false;
            byte[] b3;
            byte[][] array;
            if (this.b == null) {
                this.e = super.c.a(new psc_du());
                b3 = this.e.b(0);
                array = new byte[][] { null };
                super.b = this.a(this.a.d());
                if (super.b == null) {
                    throw new psc_c("No available cipher suites", 2, 1);
                }
                if ((super.c.v() & 0x4) == 0x4) {
                    super.c.z().println("Using Cipher Suite: " + super.b.i());
                }
                array[0] = super.b.b(2);
            }
            else {
                b3 = new byte[0];
                array = new byte[0][0];
                b2 = true;
                b = false;
            }
            final psc_ia x = new psc_ia(b2 ? 1 : 0, b ? 1 : 0, super.i, b3, array);
            if ((super.c.v() & 0x1) == 0x1) {
                super.c.z().println(x);
            }
            x.a(super.e);
        }
        catch (psc_c psc_c) {
            throw psc_c;
        }
        catch (IOException ex) {
            throw new psc_d("An IO Exception occured while writing the Server Hello");
        }
    }
    
    private void h() throws psc_c, psc_b, psc_d {
        if ((super.c.v() & 0x1) == 0x1) {
            super.c.z().println("STATE: Receiving the Client Master Key");
        }
        final psc_ib x = new psc_ib();
        try {
            x.b(super.d);
            if ((super.c.v() & 0x1) == 0x1) {
                super.c.z().println(x);
            }
        }
        catch (psc_c psc_c) {
            throw psc_c;
        }
        catch (psc_b psc_b) {
            throw psc_b;
        }
        catch (IOException ex) {
            throw new psc_d("An IO Exception occured while reading the Client Master Key");
        }
        final byte[] c = x.c();
        if (this.e.i()) {
            super.b.a(this.e.f(), 0, this.e.h());
        }
        else {
            super.b.a(this.e.g());
        }
        super.b.c(this.e.j());
        final byte[] array = new byte[c.length];
        boolean b = false;
        int c2 = 0;
        try {
            c2 = super.b.c(c, 0, c.length, array, 0);
            b = true;
        }
        catch (psc_d psc_d) {
            if ((super.c.v() & 0x1) == 0x1) {
                super.c.z().println("WARNING: failed to decrypt");
            }
        }
        super.f = new byte[x.a().length + c2];
        if (b) {
            System.arraycopy(x.a(), 0, super.f, 0, x.a().length);
            System.arraycopy(array, 0, super.f, x.a().length, c2);
        }
        else {
            if ((super.c.v() & 0x1) == 0x1) {
                super.c.z().println("WARNING: possible attack occurred, will fail with decrytpion or bad mac error!");
            }
            super.c.q().nextBytes(super.f);
        }
        System.arraycopy(super.g, 0, new byte[16], 0, 16);
        this.a(super.f, super.g, super.i, true);
        if (super.b.s() != 1) {
            final byte[] d = x.d();
            super.b.h(d, 0);
            super.b.g(d, 0);
        }
        super.a = super.b;
        super.d.a(super.a);
        super.e.a(super.a);
    }
    
    private void i() throws psc_d {
        if ((super.c.v() & 0x1) == 0x1) {
            super.c.z().println("STATE: Receiving the Client Finished message");
        }
        final psc_ie x = new psc_ie();
        try {
            x.b(super.d);
        }
        catch (IOException ex) {
            throw new psc_d("An IO Exception occured while reading the client finished message");
        }
        if (!this.a(x.a(), super.i)) {
            throw new psc_d("Client finished did not verify");
        }
        if ((super.c.v() & 0x1) == 0x1) {
            super.c.z().println(x);
        }
    }
    
    private void j() throws psc_d {
        if ((super.c.v() & 0x1) == 0x1) {
            super.c.z().println("STATE: Sending the Server Verify message");
        }
        final psc_ic x = new psc_ic(super.g);
        try {
            x.a(super.e);
        }
        catch (IOException ex) {
            throw new psc_d("Server Verify did not verify");
        }
        if ((super.c.v() & 0x1) == 0x1) {
            super.c.z().println(x);
        }
    }
    
    private void k() throws psc_d {
        if ((super.c.v() & 0x1) == 0x1) {
            super.c.z().println("STATE: Sending Server Finished message");
        }
        byte[] e;
        if (this.b == null) {
            e = new byte[16];
            super.c.q().nextBytes(e);
            this.b = new psc_d2(e, super.j, System.currentTimeMillis(), super.f, super.b, null, null, super.c, 2);
            if (super.b.s() != 1) {
                this.b.a(super.b.w());
            }
        }
        else {
            e = this.b.e();
        }
        final psc_ig x = new psc_ig(e);
        try {
            x.a(super.e);
        }
        catch (IOException ex) {
            throw new psc_d("An IO Exception occured while sending the Server Finished message");
        }
        if ((super.c.v() & 0x1) == 0x1) {
            super.c.z().println(x);
        }
    }
    
    public void a() throws psc_d, psc_c {
        if ((super.c.v() & 0x1) == 0x1) {
            super.c.z().println("STATE: Sending Certificate Request");
        }
        try {
            final psc_id psc_id = new psc_id(1, super.g);
            if ((super.c.v() & 0x1) == 0x1) {
                super.c.z().println(psc_id.toString());
            }
            psc_id.a(super.e);
        }
        catch (IOException ex) {
            throw new psc_d("There was an IOException while sending the certificate request");
        }
    }
    
    private void l() throws psc_d, psc_c {
        if ((super.c.v() & 0x1) == 0x1) {
            super.c.z().println("STATE: Receiving certificate from client");
        }
        byte[] c;
        try {
            if (super.d.read() == 0) {
                super.d.read();
                final int read = super.d.read();
                if (read != 2) {
                    throw new psc_b("Unexpected message", 2, read);
                }
                if (super.c.s() != 2) {
                    throw new psc_b("No Certificate", 2, 2);
                }
                return;
            }
            else {
                final psc_if psc_if = new psc_if();
                psc_if.b(super.d);
                if ((super.c.v() & 0x1) == 0x1) {
                    super.c.z().println(psc_if.toString());
                }
                final byte[] b = psc_if.b();
                c = psc_if.c();
                (this.d = new psc_e[1])[0] = new psc_e(b, 0, 0);
                final psc_al b2 = this.d[0].b(super.c.e());
                try {
                    super.b.b(b2);
                }
                catch (psc_d psc_d) {
                    try {
                        super.b.f(b2.b(b2.l() + "PublicKeyBER")[0], 0);
                    }
                    catch (psc_ap psc_ap) {
                        throw new psc_c("Could not set the public key: " + psc_ap.getMessage(), 2, 3);
                    }
                }
            }
        }
        catch (Exception ex) {
            throw new psc_c("Could not handle the client's certificate: " + ex.getMessage(), 2, 3);
        }
        this.c = true;
        if (super.c.t().a(super.c, this.d, super.b) == -1) {
            throw new psc_c("certificate unknown", 2, 3);
        }
        if (this.b != null) {
            this.b.b(this.d);
        }
        if (this.e == null) {
            this.e = super.c.a(this.b.l());
        }
        try {
            final byte[] b3 = this.e.b(0);
            final byte[] array = new byte[super.h.length + super.g.length + b3.length];
            System.arraycopy(super.h, 0, array, 0, super.h.length);
            System.arraycopy(super.g, 0, array, super.h.length, super.g.length);
            System.arraycopy(b3, 0, array, super.h.length + super.g.length, b3.length);
            final psc_da a = psc_da.a("MD5", super.c.e());
            a.i();
            a.a(array, 0, array.length);
            final byte[] j = a.j();
            if (!super.b.a(c, 0, c.length, j, 0, j.length)) {
                throw new psc_c("Could not verify the digital signature sent by client", 2, 3);
            }
        }
        catch (psc_c psc_c) {
            throw psc_c;
        }
        catch (Exception ex2) {
            throw new psc_d("CLIENT Authentication error");
        }
    }
    
    private psc_dw a(final byte[][] array) throws psc_d {
        final psc_dw[] d = super.c.d();
        for (int i = 0; i < array.length; ++i) {
            for (int j = 0; j < d.length; ++j) {
                final byte[] b = d[j].b(2);
                if (b != null && array[i] != null) {
                    if (this.a(b, array[i])) {
                        return d[j];
                    }
                    final byte[] array2 = new byte[3];
                    final byte[] b2 = d[j].b(768);
                    if (b2 != null) {
                        array2[1] = b2[0];
                        array2[2] = b2[1];
                        if (this.a(array2, array[i])) {
                            return d[j];
                        }
                    }
                }
            }
        }
        return null;
    }
    
    public psc_c5 b() {
        return null;
    }
    
    public psc_d2 c() {
        return this.b;
    }
    
    public psc_e[] e() {
        return this.d;
    }
}
