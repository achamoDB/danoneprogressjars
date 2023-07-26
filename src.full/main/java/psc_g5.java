import java.io.IOException;
import java.security.SecureRandom;

// 
// Decompiled by Procyon v0.5.36
// 

public abstract class psc_g5
{
    psc_c2 a;
    psc_g2 b;
    psc_g3 c;
    byte[] d;
    psc_dw e;
    psc_dw f;
    psc_c5 g;
    psc_ds h;
    String i;
    byte[] j;
    byte[] k;
    
    public psc_g5(final psc_c2 a, final psc_g2 b, final psc_g3 c, final String i) {
        this.g = null;
        this.a = a;
        this.b = b;
        this.c = c;
        this.e = b.h();
        if (this.e == null) {
            this.e = new psc_g6();
        }
        this.f = new psc_g6();
        this.i = i;
        b.a(this.e);
        b.a(new psc_c4());
        c.a(this.e);
        c.a(new psc_c4());
    }
    
    private void a(final byte[] array, final byte[] array2, final byte[] array3, final int n, int n2, final String str) throws psc_d {
        try {
            final psc_eh a = psc_eh.a("HMAC/" + str, "Java");
            final psc_dc n3 = a.n();
            n3.c(new byte[][] { array });
            a.b(n3, null);
            a.a(array2, 0, array2.length);
            byte[] array4 = a.q();
            int i = 0;
            while (i < n) {
                a.o();
                a.a(array4, 0, array4.length);
                final psc_eh psc_eh = (psc_eh)a.clone();
                psc_eh.a(array2, 0, array2.length);
                array4 = a.q();
                final byte[] q = psc_eh.q();
                i += array4.length;
                if (i < n) {
                    System.arraycopy(q, 0, array3, n2, array4.length);
                    n2 += array4.length;
                }
                else {
                    System.arraycopy(q, 0, array3, n2, n - n2);
                }
            }
        }
        catch (psc_ap psc_ap) {
            throw new psc_d("Could not perform HMAC: " + psc_ap.getMessage());
        }
        catch (CloneNotSupportedException ex) {
            throw new psc_d("Could not perform HMAC: " + ex.getMessage());
        }
    }
    
    private void a(final byte[] array, final String s, final byte[] array2, final byte[] array3, final int n, final int n2) throws psc_d {
        final int n3 = array.length + 1 >> 1;
        final byte[] array4 = new byte[n3];
        System.arraycopy(array, 0, array4, 0, n3);
        final byte[] array5 = new byte[n3];
        System.arraycopy(array, array.length - n3, array5, 0, n3);
        final byte[] array6 = new byte[array2.length + s.length()];
        final byte[] bytes = s.getBytes();
        System.arraycopy(bytes, 0, array6, 0, bytes.length);
        System.arraycopy(array2, 0, array6, bytes.length, array2.length);
        final byte[] array7 = new byte[n2];
        final byte[] array8 = new byte[n2];
        this.a(array4, array6, array7, array7.length, 0, "MD5");
        this.a(array5, array6, array8, array8.length, 0, "SHA1");
        for (int i = 0; i < n2; ++i) {
            array3[i + n] = (byte)(array7[i] ^ array8[i]);
        }
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
    
    void d() throws psc_d {
        if ((this.a.v() & 0x1) == 0x1) {
            this.a.z().println("STATE: Sending Change Cipher Spec");
        }
        try {
            this.c.a(20);
            this.c.b(769);
            this.c.write(1);
            this.c.flush();
        }
        catch (IOException ex) {
            throw new psc_d("An IOException occured while sending the Change Cipher Spec");
        }
        this.e = this.f;
        this.c.a(this.e);
        this.c.a(this.g);
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
            this.e = this.f;
            this.b.a(this.e);
            this.b.a(this.g);
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
            psc_da.a(this.d, 0, this.d.length);
            psc_da2.a(this.d, 0, this.d.length);
            psc_da.a(array2, 0, 48);
            psc_da2.a(array2, 0, 40);
            final byte[] j = psc_da.j();
            final byte[] i = psc_da2.j();
            psc_da.i();
            psc_da2.i();
            psc_da.a(this.d, 0, this.d.length);
            psc_da2.a(this.d, 0, this.d.length);
            psc_da.a(array3, 0, 48);
            psc_da2.a(array3, 0, 40);
            psc_da.a(j, 0, j.length);
            psc_da2.a(i, 0, i.length);
        }
        catch (psc_ap psc_ap) {
            throw new psc_c("An Crypto-J error occured while collecting the handshake digests: " + psc_ap.getMessage(), 2, 80);
        }
    }
    
    void a(final boolean b) throws psc_d {
        if ((this.a.v() & 0x1) == 0x1) {
            this.a.z().println("STATE: Sending Finished message");
        }
        try {
            final psc_da psc_da = (psc_da)this.b.j().clone();
            final psc_da psc_da2 = (psc_da)this.b.k().clone();
            final byte[] array = new byte[36];
            psc_da.c(array, 0);
            psc_da2.c(array, 16);
            final byte[] array2 = new byte[12];
            String s;
            if (b) {
                s = "server finished";
            }
            else {
                s = "client finished";
            }
            this.a(this.d, s, array, array2, 0, array2.length);
            final psc_hf psc_hf = new psc_hf(array2);
            if ((this.a.v() & 0x1) == 0x1) {
                this.a.z().println(psc_hf.toString());
            }
            psc_hf.a(this.c);
        }
        catch (CloneNotSupportedException ex2) {
            throw new psc_c("Unable to clone the handshake digests.  Failing.", 2, 80);
        }
        catch (IOException ex) {
            throw new psc_d("An IOException occured while collecting the handshake digests: " + ex.getMessage());
        }
        catch (psc_ap psc_ap) {
            throw new psc_c("An Crypto-J error occured while collecting the handshake digests: " + psc_ap.getMessage(), 2, 80);
        }
    }
    
    protected byte[] a(final String s) throws psc_d {
        try {
            if (s.equals("RSA")) {
                final psc_da psc_da = (psc_da)this.b.j().clone();
                final psc_da psc_da2 = (psc_da)this.b.k().clone();
                final byte[] array = new byte[36];
                psc_da.c(array, 0);
                psc_da2.c(array, 16);
                return array;
            }
            final psc_da psc_da3 = (psc_da)this.b.k().clone();
            final byte[] array2 = new byte[20];
            psc_da3.c(array2, 0);
            return array2;
        }
        catch (CloneNotSupportedException ex) {
            throw new psc_d("Could not clone the message digests.");
        }
        catch (psc_ap psc_ap) {
            throw new psc_c(psc_ap.getMessage(), 2, 80);
        }
    }
    
    void b(final boolean b) throws psc_d {
        if ((this.a.v() & 0x1) == 0x1) {
            this.a.z().println("STATE: Receiving finished message");
        }
        try {
            final psc_da psc_da = (psc_da)this.b.j().clone();
            final psc_da psc_da2 = (psc_da)this.b.k().clone();
            final byte[] array = new byte[36];
            psc_da.c(array, 0);
            psc_da2.c(array, 16);
            final byte[] array2 = new byte[12];
            String s;
            if (b) {
                s = "client finished";
            }
            else {
                s = "server finished";
            }
            this.a(this.d, s, array, array2, 0, array2.length);
            final psc_hf psc_hf = new psc_hf();
            psc_hf.b(this.b);
            if ((this.a.v() & 0x1) == 0x1) {
                this.a.z().println(psc_hf.toString());
            }
            final byte[] a = psc_hf.a();
            if (!this.a(array2, a)) {
                if ((this.a.v() & 0x4) == 0x4) {
                    this.a.z().println("MyVerify: " + psc_c9.a(array2));
                    this.a.z().println("Their Verify: " + psc_c9.a(a));
                }
                throw new psc_c((b ? "Client" : "Server") + " Finished did not verify", 2, 47);
            }
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
        final int h = this.f.h();
        int f;
        if (this.f.k()) {
            f = 5;
        }
        else {
            f = this.f.f();
        }
        final int g = this.f.g();
        final int i = h * 2 + f * 2 + g * 2;
        if ((this.a.v() & 0x4) == 0x4) {
            this.a.z().println("EXTRA: Generating " + i + " bytes of secret material");
        }
        final byte[] array2 = new byte[this.k.length + this.j.length];
        System.arraycopy(this.j, 0, array2, 0, this.j.length);
        System.arraycopy(this.k, 0, array2, this.j.length, this.k.length);
        byte[] array3 = new byte[i];
        this.a(array, "key expansion", array2, array3, 0, i);
        if (this.f.k()) {
            final byte[] array4 = new byte[this.k.length + this.j.length];
            System.arraycopy(this.k, 0, array4, 0, this.k.length);
            System.arraycopy(this.j, 0, array4, this.k.length, this.j.length);
            final int f2 = this.f.f();
            if ((this.a.v() & 0x4) == 0x4) {
                this.a.z().println("EXTRA: Generating " + (f2 * 2 + g * 2) + " bytes of export key material");
            }
            final byte[] array5 = new byte[h * 2 + f2 * 2 + g * 2];
            System.arraycopy(array3, 0, array5, 0, h * 2);
            final byte[] array6 = new byte[5];
            System.arraycopy(array3, h * 2, array6, 0, 5);
            final byte[] array7 = new byte[5];
            System.arraycopy(array3, h * 2 + 5, array7, 0, 5);
            this.a(array6, "client write key", array4, array5, h * 2, f2);
            this.a(array7, "server write key", array4, array5, h * 2 + f2, f2);
            this.a(new byte[0], "IV block", array4, array5, h * 2 + f2 * 2, g * 2);
            array3 = array5;
        }
        final int n = 0;
        if (b) {
            final int n2 = n + this.f.c(array3, n);
            final int n3 = n2 + this.f.d(array3, n2);
            final int n4 = n3 + this.f.b(array3, n3);
            final int n5 = n4 + this.f.a(array3, n4);
            this.f.g(array3, n5 + this.f.h(array3, n5));
        }
        else {
            final int n6 = n + this.f.d(array3, n);
            final int n7 = n6 + this.f.c(array3, n6);
            final int n8 = n7 + this.f.a(array3, n7);
            final int n9 = n8 + this.f.b(array3, n8);
            this.f.h(array3, n9 + this.f.g(array3, n9));
        }
    }
    
    byte[] a(final byte[] array) throws psc_d {
        this.d = new byte[48];
        final byte[] array2 = new byte[this.k.length + this.j.length];
        System.arraycopy(this.k, 0, array2, 0, this.k.length);
        System.arraycopy(this.j, 0, array2, this.k.length, this.j.length);
        this.a(array, "master secret", array2, this.d, 0, 48);
        return this.d;
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
        return this.e;
    }
    
    public psc_c5 g() {
        return this.g;
    }
}
