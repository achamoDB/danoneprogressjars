import java.io.IOException;

// 
// Decompiled by Procyon v0.5.36
// 

public abstract class psc_hq
{
    psc_c2 a;
    psc_hn b;
    psc_ho c;
    String d;
    byte[] e;
    psc_dw f;
    psc_dw g;
    psc_c5 h;
    psc_ds i;
    byte[] j;
    byte[] k;
    
    public psc_hq(final psc_c2 a, final psc_hn b, final psc_ho c, final String d) {
        this.h = null;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.f = b.h();
        if (this.f == null) {
            this.f = new psc_g6();
        }
        this.g = new psc_g6();
        b.a(this.f);
        b.a(new psc_c4());
        c.a(this.f);
        c.a(new psc_c4());
    }
    
    int c() throws psc_d, psc_b {
        try {
            return this.b.f();
        }
        catch (psc_b psc_b) {
            throw psc_b;
        }
        catch (IOException ex) {
            throw new psc_d("An IOException occured while reading a packet");
        }
    }
    
    void d() throws psc_d, psc_b {
        if ((this.a.v() & 0x1) == 0x1) {
            this.a.z().println("STATE: Sending Change Cipher Spec");
        }
        try {
            this.c.a(20);
            this.c.b(768);
            this.c.write(1);
            this.c.flush();
        }
        catch (psc_b psc_b) {
            throw psc_b;
        }
        catch (IOException ex) {
            throw new psc_d("An IOException occured while sending the Change Cipher Spec");
        }
        this.f = this.g;
        this.c.a(this.f);
        this.c.a(this.h);
    }
    
    void e() throws psc_d, psc_b {
        if ((this.a.v() & 0x1) == 0x1) {
            this.a.z().println("STATE: Receiving change cipher spec");
        }
        try {
            if (this.c() != 20) {
                throw new psc_c("Unexpected message.  Expecting ChangeCipherSpec, but didn't get it", 2, 10);
            }
            final int read = this.b.read();
            if (read != 1) {
                throw new psc_c("ChangeCipherSpec contained a bad value: " + read, 2, 47);
            }
            this.f = this.g;
            this.b.a(this.f);
            this.b.a(this.h);
        }
        catch (psc_b psc_b) {
            throw psc_b;
        }
        catch (IOException ex) {
            throw new psc_d("An IOException occured while reading the change cipher spec message");
        }
    }
    
    void a(final psc_da psc_da, final psc_da psc_da2, final byte[] array) throws psc_d {
        try {
            final byte[] array2 = { 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54 };
            final byte[] array3 = { 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92 };
            psc_da.a(array, 0, array.length);
            psc_da2.a(array, 0, array.length);
            psc_da.a(this.e, 0, this.e.length);
            psc_da2.a(this.e, 0, this.e.length);
            psc_da.a(array2, 0, 48);
            psc_da2.a(array2, 0, 40);
            final byte[] j = psc_da.j();
            final byte[] i = psc_da2.j();
            psc_da.i();
            psc_da2.i();
            psc_da.a(this.e, 0, this.e.length);
            psc_da2.a(this.e, 0, this.e.length);
            psc_da.a(array3, 0, 48);
            psc_da2.a(array3, 0, 40);
            psc_da.a(j, 0, j.length);
            psc_da2.a(i, 0, i.length);
        }
        catch (psc_ap psc_ap) {
            throw new psc_c("An Crypto-J error occured while collecting the handshake digests: " + psc_ap.getMessage(), 2, 80);
        }
    }
    
    void a(final boolean b) throws psc_d, psc_b {
        if ((this.a.v() & 0x1) == 0x1) {
            this.a.z().println("STATE: Sending Finished message");
        }
        try {
            final byte[] array = { 83, 82, 86, 82 };
            byte[] array2 = { 67, 76, 78, 84 };
            if (b) {
                array2 = array;
            }
            final psc_da psc_da = (psc_da)this.b.j().clone();
            final psc_da psc_da2 = (psc_da)this.b.k().clone();
            this.a(psc_da, psc_da2, array2);
            final psc_hy psc_hy = new psc_hy(psc_da.j(), psc_da2.j());
            if ((this.a.v() & 0x1) == 0x1) {
                this.a.z().println(psc_hy.toString());
            }
            psc_hy.a(this.c);
        }
        catch (CloneNotSupportedException ex2) {
            throw new psc_c("Unable to clone the handshake digests.  Failing.", 2, 80);
        }
        catch (psc_b psc_b) {
            throw psc_b;
        }
        catch (IOException ex) {
            throw new psc_d("An IOException occured while collecting the handshake digests: " + ex.getMessage());
        }
        catch (psc_ap psc_ap) {
            throw new psc_c("An Crypto-J error occured while collecting the handshake digests: " + psc_ap.getMessage(), 2, 80);
        }
    }
    
    void b(final boolean b) throws psc_d, psc_b {
        if ((this.a.v() & 0x1) == 0x1) {
            this.a.z().println("STATE: Receiving finished message");
        }
        try {
            final byte[] array = { 67, 76, 78, 84 };
            byte[] array2 = { 83, 82, 86, 82 };
            if (b) {
                array2 = array;
            }
            final psc_da psc_da = (psc_da)this.b.j().clone();
            final psc_da psc_da2 = (psc_da)this.b.k().clone();
            this.a(psc_da, psc_da2, array2);
            final psc_hy psc_hy = new psc_hy();
            psc_hy.b(this.b);
            if ((this.a.v() & 0x1) == 0x1) {
                this.a.z().println(psc_hy.toString());
            }
            final byte[] j = psc_da.j();
            final byte[] i = psc_da2.j();
            final byte[] a = psc_hy.a();
            final byte[] b2 = psc_hy.b();
            if (!this.a(j, a) || !this.a(i, b2)) {
                throw new psc_c((b ? "Client" : "Server") + " Finished did not verify", 2, 47);
            }
        }
        catch (psc_b psc_b) {
            throw psc_b;
        }
        catch (psc_c psc_c) {
            throw psc_c;
        }
        catch (IOException ex) {
            throw new psc_d("An IOException occured while reading the finished message: " + ex.getMessage());
        }
        catch (CloneNotSupportedException ex2) {
            throw new psc_c("Unable to clone the handshake digests.  Failing.", 2, 80);
        }
        catch (psc_ap psc_ap) {
            throw new psc_c("There was an error while verifying the client's finished: " + psc_ap.getMessage(), 2, 80);
        }
    }
    
    void a(final byte[] array, final boolean b) throws psc_d {
        final int h = this.g.h();
        int f;
        if (this.g.k()) {
            f = 5;
        }
        else {
            f = this.g.f();
        }
        final int g = this.g.g();
        final int i = h * 2 + f * 2 + g * 2;
        if ((this.a.v() & 0x4) == 0x4) {
            this.a.z().println("EXTRA: Generating " + i + " bytes of secret material");
        }
        try {
            final psc_da a = psc_da.a("MD5", this.a.e());
            final psc_da a2 = psc_da.a("SHA1", this.a.e());
            final byte[] array2 = new byte[20];
            byte[] array3 = new byte[i + (16 - i % 16)];
            int n = 0;
            for (int j = 0; j < i; j += 16) {
                a.i();
                a2.i();
                final byte[] array4 = new byte[n + 1];
                for (int k = 0; k <= n; ++k) {
                    array4[k] = (byte)(65 + n);
                }
                a2.a(array4, 0, n + 1);
                a2.a(array, 0, array.length);
                a2.a(this.j, 0, this.j.length);
                a2.a(this.k, 0, this.k.length);
                a2.c(array2, 0);
                a.a(array, 0, array.length);
                a.a(array2, 0, 20);
                a.c(array3, j);
                ++n;
            }
            if (this.g.k() && this.g.f() > 0) {
                final int f2 = this.g.f();
                final int n2 = h * 2;
                final byte[] array5 = new byte[h * 2 + f2 * 2 + g * 2];
                a.i();
                a.a(array3, n2, f);
                a.a(this.k, 0, this.k.length);
                a.a(this.j, 0, this.j.length);
                a.c(array5, n2);
                final int n3 = n2 + f;
                a.i();
                a.a(array3, n3, f);
                a.a(this.j, 0, this.j.length);
                a.a(this.k, 0, this.k.length);
                a.c(array5, h * 2 + f2);
                final int n4 = h * 2 + f2 * 2;
                final byte[] array6 = new byte[16];
                a.i();
                a.a(this.k, 0, this.k.length);
                a.a(this.j, 0, this.j.length);
                a.c(array6, 0);
                System.arraycopy(array6, 0, array5, n4, g);
                final int n5 = n4 + g;
                a.i();
                a.a(this.j, 0, this.j.length);
                a.a(this.k, 0, this.k.length);
                a.c(array6, 0);
                System.arraycopy(array6, 0, array5, n5, g);
                System.arraycopy(array3, 0, array5, 0, h * 2);
                array3 = array5;
            }
            final int n6 = 0;
            if (b) {
                final int n7 = n6 + this.g.c(array3, n6);
                final int n8 = n7 + this.g.d(array3, n7);
                final int n9 = n8 + this.g.b(array3, n8);
                final int n10 = n9 + this.g.a(array3, n9);
                this.g.g(array3, n10 + this.g.h(array3, n10));
            }
            else {
                final int n11 = n6 + this.g.d(array3, n6);
                final int n12 = n11 + this.g.c(array3, n11);
                final int n13 = n12 + this.g.a(array3, n12);
                final int n14 = n13 + this.g.b(array3, n13);
                this.g.h(array3, n14 + this.g.g(array3, n14));
            }
        }
        catch (psc_ap psc_ap) {
            throw new psc_d("There was an error while setting the secrets: " + psc_ap.getMessage());
        }
    }
    
    byte[] a(final byte[] array) throws psc_d {
        try {
            final psc_da a = psc_da.a("MD5", this.a.e());
            final psc_da a2 = psc_da.a("SHA1", this.a.e());
            final byte[] array2 = new byte[20];
            final byte[] array3 = new byte[48];
            a.i();
            a2.i();
            a2.a("A".getBytes(), 0, 1);
            a2.a(array, 0, array.length);
            a2.a(this.k, 0, this.k.length);
            a2.a(this.j, 0, this.j.length);
            a2.c(array2, 0);
            a.a(array, 0, array.length);
            a.a(array2, 0, 20);
            a.c(array3, 0);
            a.i();
            a2.i();
            a2.a("BB".getBytes(), 0, 2);
            a2.a(array, 0, array.length);
            a2.a(this.k, 0, this.k.length);
            a2.a(this.j, 0, this.j.length);
            a2.c(array2, 0);
            a.a(array, 0, array.length);
            a.a(array2, 0, 20);
            a.c(array3, 16);
            a.i();
            a2.i();
            a2.a("CCC".getBytes(), 0, 3);
            a2.a(array, 0, array.length);
            a2.a(this.k, 0, this.k.length);
            a2.a(this.j, 0, this.j.length);
            a2.c(array2, 0);
            a.a(array, 0, array.length);
            a.a(array2, 0, 20);
            a.c(array3, 32);
            return array3;
        }
        catch (psc_ap psc_ap) {
            throw new psc_d("An error occured during formatting of the master secret: " + psc_ap.getMessage());
        }
    }
    
    protected byte[] a(final String s) throws psc_d {
        try {
            if (s.equals("RSA")) {
                final psc_da psc_da = (psc_da)this.b.j().clone();
                final psc_da psc_da2 = (psc_da)this.b.k().clone();
                psc_da.a(this.e, 0, this.e.length);
                psc_da.a(psc_dw.d, 0, 48);
                psc_da2.a(this.e, 0, this.e.length);
                psc_da2.a(psc_dw.d, 0, 40);
                final byte[] j = psc_da.j();
                final byte[] i = psc_da2.j();
                final psc_da a = psc_da.a("MD5", this.a.e());
                final psc_da a2 = psc_da.a("SHA1", this.a.e());
                a.i();
                a2.i();
                a.a(this.e, 0, this.e.length);
                a.a(psc_dw.e, 0, 48);
                a.a(j, 0, 16);
                a2.a(this.e, 0, this.e.length);
                a2.a(psc_dw.e, 0, 40);
                a2.a(i, 0, 20);
                final byte[] array = new byte[36];
                a.c(array, 0);
                a2.c(array, 16);
                return array;
            }
            final psc_da psc_da3 = (psc_da)this.b.k().clone();
            psc_da3.a(this.e, 0, this.e.length);
            psc_da3.a(psc_dw.d, 0, 40);
            final byte[] k = psc_da3.j();
            final byte[] array2 = new byte[this.e.length + 40 + 20];
            System.arraycopy(this.e, 0, array2, 0, this.e.length);
            System.arraycopy(psc_dw.e, 0, array2, this.e.length, 40);
            System.arraycopy(k, 0, array2, this.e.length + 40, 20);
            return array2;
        }
        catch (CloneNotSupportedException ex) {
            throw new psc_d("Could not clone the message digests.");
        }
        catch (psc_ap psc_ap) {
            throw new psc_c(psc_ap.getMessage(), 2, 80);
        }
    }
    
    boolean a(final byte[] array, final byte[] array2) {
        if (array.length != array2.length) {
            return false;
        }
        for (int i = 0; i < array.length; ++i) {
            if (array[i] != array2[i]) {
                return false;
            }
        }
        return true;
    }
    
    public psc_dw f() {
        return this.f;
    }
    
    public psc_c5 g() {
        return this.h;
    }
}
