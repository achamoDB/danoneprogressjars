import java.io.InputStream;
import java.io.IOException;

// 
// Decompiled by Procyon v0.5.36
// 

public abstract class pscl_an
{
    public pscl_b a;
    public pscl_ar b;
    public pscl_as c;
    public String d;
    public byte[] e;
    public pscl_i f;
    public pscl_i g;
    public pscl_bn h;
    public byte[] i;
    public byte[] j;
    
    public pscl_an() {
        this.h = null;
    }
    
    public pscl_an(final pscl_b a, final pscl_ar b, final pscl_as c, final String d) {
        this.h = null;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.f = b.i();
        if (this.f == null) {
            this.f = new pscl_m();
        }
        this.g = new pscl_m();
        b.a(this.f);
        c.a(this.f);
    }
    
    public int g() throws pscl_h, pscl_ah {
        try {
            return this.b.g();
        }
        catch (pscl_ah pscl_ah) {
            throw pscl_ah;
        }
        catch (IOException ex) {
            throw new pscl_h("An IOException occured while reading a packet");
        }
    }
    
    public void h() throws pscl_h, pscl_ah {
        if ((this.a.n() & 0x1) == 0x1) {
            this.a.o().println("STATE: Sending Change Cipher Spec");
        }
        try {
            this.c.a(20);
            this.c.b(768);
            this.c.write(1);
            this.c.flush();
        }
        catch (pscl_ah pscl_ah) {
            throw pscl_ah;
        }
        catch (IOException ex) {
            throw new pscl_h("An IOException occured while sending the Change Cipher Spec");
        }
        this.f = this.g;
        this.c.a(this.f);
    }
    
    public void i() throws pscl_h, pscl_ah {
        if ((this.a.n() & 0x1) == 0x1) {
            this.a.o().println("STATE: Receiving change cipher spec");
        }
        try {
            if (this.g() != 20) {
                throw new pscl_ai("Unexpected message.  Expecting ChangeCipherSpec, but didn't get it", 2, 10);
            }
            final int a = pscl_au.a(this.b);
            if (a != 1) {
                throw new pscl_ai("ChangeCipherSpec contained a bad value: " + a, 2, 47);
            }
            this.f = this.g;
            this.b.a(this.f);
        }
        catch (pscl_ah pscl_ah) {
            throw pscl_ah;
        }
        catch (IOException ex) {
            throw new pscl_h("An IOException occured while reading the change cipher spec message");
        }
    }
    
    public void a(final pscl_d pscl_d, final pscl_d pscl_d2, final byte[] array) throws pscl_h {
        try {
            final byte[] array2 = { 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54 };
            final byte[] array3 = { 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92, 92 };
            pscl_d.a(array, 0, array.length);
            pscl_d2.a(array, 0, array.length);
            pscl_d.a(this.e, 0, this.e.length);
            pscl_d2.a(this.e, 0, this.e.length);
            pscl_d.a(array2, 0, 48);
            pscl_d2.a(array2, 0, 40);
            final byte[] array4 = new byte[16];
            pscl_d.a(array4, 0);
            final byte[] array5 = new byte[20];
            pscl_d2.a(array5, 0);
            pscl_d.f();
            pscl_d2.f();
            pscl_d.a(this.e, 0, this.e.length);
            pscl_d2.a(this.e, 0, this.e.length);
            pscl_d.a(array3, 0, 48);
            pscl_d2.a(array3, 0, 40);
            pscl_d.a(array4, 0, array4.length);
            pscl_d2.a(array5, 0, array5.length);
        }
        catch (Exception ex) {
            throw new pscl_ai("An Crypto-J error occured while collecting the handshake digests: " + ex.getMessage(), 2, 80);
        }
    }
    
    public void a(final boolean b) throws pscl_h, pscl_ah {
        if ((this.a.n() & 0x1) == 0x1) {
            this.a.o().println("STATE: Sending Finished message");
        }
        try {
            final byte[] array = { 67, 76, 78, 84 };
            final pscl_d pscl_d = (pscl_d)this.b.k().clone();
            final pscl_d pscl_d2 = (pscl_d)this.b.l().clone();
            this.a(pscl_d, pscl_d2, array);
            final byte[] array2 = new byte[16];
            final byte[] array3 = new byte[20];
            pscl_d.a(array2, 0);
            pscl_d2.a(array3, 0);
            final pscl_a5 pscl_a5 = new pscl_a5(array2, array3);
            if ((this.a.n() & 0x1) == 0x1) {
                this.a.o().println(pscl_a5.toString());
            }
            pscl_a5.a(this.c);
        }
        catch (CloneNotSupportedException ex3) {
            throw new pscl_ai("Unable to clone the handshake digests.  Failing.", 2, 80);
        }
        catch (pscl_ah pscl_ah) {
            throw pscl_ah;
        }
        catch (IOException ex) {
            throw new pscl_h("An IOException occured while collecting the handshake digests: " + ex.getMessage());
        }
        catch (Exception ex2) {
            throw new pscl_ai("An Crypto-J error occured while collecting the handshake digests: " + ex2.getMessage(), 2, 80);
        }
    }
    
    public void b(final boolean b) throws pscl_h, pscl_ah {
        if ((this.a.n() & 0x1) == 0x1) {
            this.a.o().println("STATE: Receiving finished message");
        }
        try {
            final byte[] array = { 83, 82, 86, 82 };
            final pscl_d pscl_d = (pscl_d)this.b.k().clone();
            final pscl_d pscl_d2 = (pscl_d)this.b.l().clone();
            this.a(pscl_d, pscl_d2, array);
            final pscl_a5 pscl_a5 = new pscl_a5();
            pscl_a5.a(this.b);
            if ((this.a.n() & 0x1) == 0x1) {
                this.a.o().println(pscl_a5.toString());
            }
            final byte[] array2 = new byte[16];
            pscl_d.a(array2, 0);
            final byte[] array3 = new byte[20];
            pscl_d2.a(array3, 0);
            final byte[] a = pscl_a5.a();
            final byte[] b2 = pscl_a5.b();
            if (!this.a(array2, a) || !this.a(array3, b2)) {
                throw new pscl_ai("Client Finished did not verify", 2, 47);
            }
        }
        catch (pscl_ah pscl_ah) {
            throw pscl_ah;
        }
        catch (pscl_ai pscl_ai) {
            throw pscl_ai;
        }
        catch (IOException ex) {
            throw new pscl_h("An IOException occured while reading the finished message: " + ex.getMessage());
        }
        catch (CloneNotSupportedException ex3) {
            throw new pscl_ai("Unable to clone the handshake digests.  Failing.", 2, 80);
        }
        catch (Exception ex2) {
            throw new pscl_ai("There was an error while verifying the client's finished: " + ex2.getMessage(), 2, 80);
        }
    }
    
    public void a(final byte[] array, final boolean b) throws pscl_h {
        final int i = this.g.i();
        int g;
        if (this.g.n()) {
            g = 5;
        }
        else {
            g = this.g.g();
        }
        final int h = this.g.h();
        final int j = i * 2 + g * 2 + h * 2;
        if ((this.a.n() & 0x4) == 0x4) {
            this.a.o().println("EXTRA: Generating " + j + " bytes of secret material");
        }
        try {
            final pscl_aq pscl_aq = new pscl_aq();
            final pscl_c pscl_c = new pscl_c();
            final byte[] array2 = new byte[20];
            byte[] array3 = new byte[j + (16 - j % 16)];
            int n = 0;
            for (int k = 0; k < j; k += 16) {
                pscl_aq.f();
                pscl_c.f();
                final byte[] array4 = new byte[n + 1];
                for (int l = 0; l <= n; ++l) {
                    array4[l] = (byte)(65 + n);
                }
                pscl_c.a(array4, 0, n + 1);
                pscl_c.a(array, 0, array.length);
                pscl_c.a(this.i, 0, this.i.length);
                pscl_c.a(this.j, 0, this.j.length);
                pscl_c.a(array2, 0);
                pscl_aq.a(array, 0, array.length);
                pscl_aq.a(array2, 0, 20);
                pscl_aq.a(array3, k);
                ++n;
            }
            if (this.g.n() && this.g.g() > 0) {
                final int g2 = this.g.g();
                final int n2 = i * 2;
                final byte[] array5 = new byte[i * 2 + g2 * 2 + h * 2];
                pscl_aq.f();
                pscl_aq.a(array3, n2, g);
                pscl_aq.a(this.j, 0, this.j.length);
                pscl_aq.a(this.i, 0, this.i.length);
                pscl_aq.a(array5, n2);
                final int n3 = n2 + g;
                pscl_aq.f();
                pscl_aq.a(array3, n3, g);
                pscl_aq.a(this.i, 0, this.i.length);
                pscl_aq.a(this.j, 0, this.j.length);
                pscl_aq.a(array5, i * 2 + g2);
                final int n4 = i * 2 + g2 * 2;
                final byte[] array6 = new byte[16];
                pscl_aq.f();
                pscl_aq.a(this.j, 0, this.j.length);
                pscl_aq.a(this.i, 0, this.i.length);
                pscl_aq.a(array6, 0);
                System.arraycopy(array6, 0, array5, n4, h);
                final int n5 = n4 + h;
                pscl_aq.f();
                pscl_aq.a(this.i, 0, this.i.length);
                pscl_aq.a(this.j, 0, this.j.length);
                pscl_aq.a(array6, 0);
                System.arraycopy(array6, 0, array5, n5, h);
                System.arraycopy(array3, 0, array5, 0, i * 2);
                array3 = array5;
            }
            final int n6 = 0;
            final int n7 = n6 + this.g.d(array3, n6);
            final int n8 = n7 + this.g.c(array3, n7);
            final int n9 = n8 + this.g.a(array3, n8);
            final int n10 = n9 + this.g.b(array3, n9);
            this.g.h(array3, n10 + this.g.g(array3, n10));
        }
        catch (Exception ex) {
            throw new pscl_h("There was an error while setting the secrets: " + ex.getMessage());
        }
    }
    
    public byte[] a(final byte[] array) throws pscl_h {
        try {
            final pscl_aq pscl_aq = new pscl_aq();
            final pscl_c pscl_c = new pscl_c();
            final byte[] array2 = new byte[20];
            final byte[] array3 = new byte[48];
            pscl_aq.f();
            pscl_c.f();
            pscl_c.a("A".getBytes(), 0, 1);
            pscl_c.a(array, 0, array.length);
            pscl_c.a(this.j, 0, this.j.length);
            pscl_c.a(this.i, 0, this.i.length);
            pscl_c.a(array2, 0);
            pscl_aq.a(array, 0, array.length);
            pscl_aq.a(array2, 0, 20);
            pscl_aq.a(array3, 0);
            pscl_aq.f();
            pscl_c.f();
            pscl_c.a("BB".getBytes(), 0, 2);
            pscl_c.a(array, 0, array.length);
            pscl_c.a(this.j, 0, this.j.length);
            pscl_c.a(this.i, 0, this.i.length);
            pscl_c.a(array2, 0);
            pscl_aq.a(array, 0, array.length);
            pscl_aq.a(array2, 0, 20);
            pscl_aq.a(array3, 16);
            pscl_aq.f();
            pscl_c.f();
            pscl_c.a("CCC".getBytes(), 0, 3);
            pscl_c.a(array, 0, array.length);
            pscl_c.a(this.j, 0, this.j.length);
            pscl_c.a(this.i, 0, this.i.length);
            pscl_c.a(array2, 0);
            pscl_aq.a(array, 0, array.length);
            pscl_aq.a(array2, 0, 20);
            pscl_aq.a(array3, 32);
            return array3;
        }
        catch (Exception ex) {
            throw new pscl_h("An error occured during formatting of the master secret: " + ex.getMessage());
        }
    }
    
    public byte[] a(final String s) throws pscl_h {
        try {
            final pscl_d pscl_d = (pscl_d)this.b.k().clone();
            final pscl_d pscl_d2 = (pscl_d)this.b.l().clone();
            pscl_d.a(this.e, 0, this.e.length);
            pscl_d.a(pscl_i.d, 0, 48);
            pscl_d2.a(this.e, 0, this.e.length);
            pscl_d2.a(pscl_i.d, 0, 40);
            final byte[] array = new byte[16];
            pscl_d.a(array, 0);
            final byte[] array2 = new byte[20];
            pscl_d2.a(array2, 0);
            final pscl_aq pscl_aq = new pscl_aq();
            final pscl_c pscl_c = new pscl_c();
            pscl_aq.f();
            pscl_c.f();
            pscl_aq.a(this.e, 0, this.e.length);
            pscl_aq.a(pscl_i.e, 0, 48);
            pscl_aq.a(array, 0, 16);
            pscl_c.a(this.e, 0, this.e.length);
            pscl_c.a(pscl_i.e, 0, 40);
            pscl_c.a(array2, 0, 20);
            final byte[] array3 = new byte[36];
            pscl_aq.a(array3, 0);
            pscl_c.a(array3, 16);
            return array3;
        }
        catch (CloneNotSupportedException ex2) {
            throw new pscl_h("Could not clone the message digests.");
        }
        catch (Exception ex) {
            throw new pscl_ai(ex.getMessage(), 2, 80);
        }
    }
    
    public boolean a(final byte[] array, final byte[] array2) {
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
    
    public pscl_i j() {
        return this.f;
    }
}
