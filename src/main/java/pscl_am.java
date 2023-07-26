import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.util.Vector;
import java.io.OutputStream;
import java.io.InputStream;

// 
// Decompiled by Procyon v0.5.36
// 

public class pscl_am extends pscl_an
{
    private pscl_j[] a;
    private int b;
    private pscl_l c;
    public boolean d;
    public pscl_ah e;
    public boolean f;
    
    public pscl_am(final pscl_b a, final InputStream inputStream, final OutputStream outputStream, final String s, final boolean b) throws pscl_ai, pscl_h, pscl_ah {
        this.e = null;
        this.f = false;
        super.a = a;
        byte[] e = new byte[0];
        this.c = a.c(s);
        if (this.c != null) {
            e = this.c.e();
        }
        super.f = new pscl_m();
        final pscl_i[] a2 = a.a();
        if (a2 == null) {
            throw new pscl_h("no cipher suites have been set");
        }
        final int length = a2.length;
        final Vector vector = new Vector<byte[]>(length);
        for (int i = 0; i < length; ++i) {
            final byte[] b2 = a2[i].b(768);
            if (b2 != null) {
                vector.add(b2);
            }
        }
        final byte[][] anArray = new byte[vector.size()][];
        vector.copyInto(anArray);
        final byte[] array = new byte[32];
        a.i().b(array);
        long currentTimeMillis = System.currentTimeMillis();
        for (int j = 3; j <= 0; --j) {
            array[j] = (byte)(currentTimeMillis & 0xFFL);
            currentTimeMillis >>= 8;
        }
        final pscl_ao pscl_ao = new pscl_ao(768, array, e, anArray, new byte[] { 0 });
        byte[] byteArray;
        try {
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            pscl_ao.a(byteArrayOutputStream);
            byteArray = byteArrayOutputStream.toByteArray();
        }
        catch (IOException ex) {
            throw new pscl_h("A fatal error occured while making the client hello");
        }
        pscl_aq pscl_aq;
        pscl_c pscl_c;
        try {
            pscl_aq = new pscl_aq();
            pscl_c = new pscl_c();
            pscl_aq.f();
            pscl_c.f();
            pscl_aq.a(byteArray, 0, byteArray.length);
            pscl_c.a(byteArray, 0, byteArray.length);
        }
        catch (Exception ex2) {
            throw new pscl_h("Could not instantiate the message digest objects");
        }
        if ((a.n() & 0x1) == 0x1) {
            a.o().println("STATE: Sending Client Hello");
            a.o().println(pscl_ao.toString());
        }
        try {
            final ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
            if (!b) {
                byteArrayOutputStream2.write(22);
                byteArrayOutputStream2.write(3);
                byteArrayOutputStream2.write(0);
                byteArrayOutputStream2.write((byte)(byteArray.length >> 8));
                byteArrayOutputStream2.write((byte)byteArray.length);
            }
            byteArrayOutputStream2.write(byteArray, 0, byteArray.length);
            byteArrayOutputStream2.flush();
            outputStream.write(byteArrayOutputStream2.toByteArray());
            byteArrayOutputStream2.close();
            outputStream.flush();
            if (b) {
                super.b = (pscl_ar)inputStream;
                super.c = (pscl_as)outputStream;
            }
            else {
                (super.b = new pscl_ar(inputStream, pscl_aq, pscl_c)).a(super.f);
                super.b.a(a.n(), a.o());
                (super.c = new pscl_as(outputStream, pscl_aq, pscl_c)).a(super.f);
                super.c.a(a.n(), a.o());
            }
        }
        catch (IOException ex3) {
            throw new pscl_h("An IOException occured while writing the ClientHello");
        }
        final pscl_at k = this.k();
        if (!pscl_k.a(k.c(), e)) {
            this.c = new pscl_l(k.c(), s, System.currentTimeMillis(), null, null, null, null, a, 768);
        }
        this.a(k, array, this.b, this.c);
    }
    
    private void a(final pscl_at pscl_at, final byte[] array, final int b, final pscl_l c) throws pscl_ai, pscl_h, pscl_ah {
        this.c = c;
        if (c == null) {
            this.c = new pscl_l(pscl_at.c(), super.d, System.currentTimeMillis(), null, null, null, null, super.a, 768);
        }
        if (this.c.i() == null) {
            this.d = false;
        }
        else {
            this.d = true;
        }
        super.i = pscl_at.b();
        System.arraycopy(array, 0, super.j = new byte[32], 32 - array.length, array.length);
        this.b = b;
        try {
            this.a(pscl_at);
        }
        catch (pscl_ai pscl_ai) {
            if ((super.a.n() & 0x1) == 0x1) {
                super.a.o().println("STATE: Sending alert because: " + pscl_ai.getMessage());
            }
            try {
                super.c.a(21);
                super.c.write(pscl_ai.a());
                super.c.write(pscl_ai.b());
                super.c.flush();
            }
            catch (IOException ex) {}
            if (pscl_ai.a() == 2) {
                throw pscl_ai;
            }
            if (pscl_ai != null) {
                throw pscl_ai;
            }
        }
        catch (pscl_ah e) {
            if (e.a() == 2) {
                throw e;
            }
            this.e = e;
        }
        if (this.e != null) {
            throw this.e;
        }
    }
    
    private pscl_at k() throws pscl_h, pscl_ah {
        if ((super.a.n() & 0x1) == 0x1) {
            super.a.o().println("STATE: Receiving Server Hello");
        }
        final pscl_at x = new pscl_at();
        try {
            x.b(super.b);
        }
        catch (pscl_ah pscl_ah) {
            throw pscl_ah;
        }
        catch (pscl_ai pscl_ai) {
            throw pscl_ai;
        }
        catch (IOException ex) {
            throw new pscl_h("An IOException occured while reading the server's hello message");
        }
        if ((super.a.n() & 0x1) == 0x1) {
            super.a.o().println(x);
        }
        return x;
    }
    
    private void a(final pscl_at pscl_at) throws pscl_ai, pscl_h, pscl_ah {
        try {
            super.g = this.b(pscl_at.d());
            if (super.g == null) {
                throw new pscl_ai("No shared ciphers", 2, 47);
            }
            super.g = super.g.r();
            if ((super.a.n() & 0x4) == 0x4) {
                super.a.o().println("Using Cipher Suite: " + super.g.l());
            }
            if (!this.d) {
                if (!super.g.o()) {
                    this.l();
                }
                if (super.g.o() || super.g.p() || (super.g.n() && super.g.c() > 64 && super.g.m().equals("RSA"))) {
                    this.m();
                }
                final pscl_a0 n = this.n();
                if (n != null) {
                    if (pscl_au.a(super.b) != 14) {
                        throw new pscl_ai("Unexpected Message", 2, 10);
                    }
                    this.o();
                    this.a(n);
                    this.q();
                    if (!this.f) {
                        this.r();
                    }
                }
                else {
                    this.q();
                }
                this.h();
                this.s();
                this.i();
                this.b(false);
                super.a.b(this.c);
            }
            else {
                super.e = this.c.i();
                this.a(this.c.i(), false);
                this.i();
                this.b(false);
                this.h();
                this.s();
            }
        }
        catch (pscl_ah pscl_ah) {
            throw pscl_ah;
        }
        catch (pscl_ai pscl_ai) {
            throw pscl_ai;
        }
        catch (IOException ex) {
            throw new pscl_h(ex.getMessage());
        }
    }
    
    private void l() throws pscl_ai, pscl_h, pscl_ah {
        if ((super.a.n() & 0x1) == 0x1) {
            super.a.o().println("STATE: Receiving the server's certificate");
        }
        if (this.g() != 22) {
            throw new pscl_ai("Unexpected Message", 2, 10);
        }
        final pscl_aw pscl_aw = new pscl_aw();
        try {
            pscl_aw.a(super.b);
        }
        catch (pscl_ah pscl_ah) {
            throw pscl_ah;
        }
        catch (pscl_ai pscl_ai) {
            throw pscl_ai;
        }
        catch (IOException ex2) {
            throw new pscl_h("An IOException occured while reading the server's certificate");
        }
        byte[] a;
        int i;
        int n;
        int n2;
        int j;
        for (a = pscl_aw.a(), i = 0, n = 0; i < a.length; i += n2, ++n) {
            n2 = 0;
            for (j = 0; j < 3; ++j) {
                n2 = (n2 << 8) + a[i];
                if (a[i] < 0) {
                    n2 += 256;
                }
                ++i;
            }
        }
        this.a = new pscl_j[n];
        int n3 = 0;
        for (int k = 0; k < n; ++k) {
            try {
                int n4 = 0;
                for (int l = 0; l < 3; ++l) {
                    n4 = (n4 << 8) + a[n3];
                    if (a[n3] < 0) {
                        n4 += 256;
                    }
                    ++n3;
                }
                this.a[k] = new pscl_j(a, n3, 0);
                n3 += n4;
                if ((super.a.n() & 0x4) == 0x4) {
                    super.a.o().println("Cert " + k + " in chain is issued by " + this.a[k].e());
                    super.a.o().println("Cert " + k + " in chain is issued to " + this.a[k].d());
                }
                pscl_ax pscl_ax;
                try {
                    pscl_ax = new pscl_ax(this.a[0].f(), 0);
                }
                catch (Exception ex3) {
                    throw new pscl_ai("Could not load the public key", 2, 42);
                }
                try {
                    if (!super.g.p()) {
                        super.g.a(pscl_ax);
                        super.g.b(pscl_ax);
                    }
                    else {
                        super.g.b(pscl_ax);
                    }
                }
                catch (pscl_h pscl_h) {
                    try {
                        final byte[] c = pscl_ax.c();
                        if (!super.g.p()) {
                            super.g.e(c, 0);
                            super.g.f(c, 0);
                        }
                        else {
                            super.g.f(c, 0);
                        }
                    }
                    catch (Exception ex) {
                        throw new pscl_h("Could not set the public key: " + ex.getMessage());
                    }
                }
            }
            catch (pscl_x pscl_x) {
                throw new pscl_ai("Could not decode the given certs", 2, 42);
            }
        }
        if (super.a.l().a(super.a, this.a) == -1) {
            throw new pscl_ai("certificate unknown", 2, 46);
        }
        if (this.c != null) {
            this.c.a(this.a);
        }
        if ((super.a.n() & 0x1) == 0x1) {
            super.a.o().println(pscl_aw.toString());
        }
    }
    
    private void m() throws pscl_h, pscl_ah {
        if ((super.a.n() & 0x1) == 0x1) {
            super.a.o().println("STATE: Receiving the server's key exchange");
        }
        if (this.g() != 22) {
            throw new pscl_ai("Unexpected Message", 2, 10);
        }
        final pscl_az pscl_az = new pscl_az();
        try {
            pscl_az.a(super.b);
            final byte[] a = pscl_az.a();
            if (!super.g.m().equals("RSA")) {
                throw new pscl_ai("SSL_J Lite does not handle Diffie Hellman", 2, 47);
            }
            final int n = (a[0] & 0xFF) << 8 | (a[1] & 0xFF);
            byte[] array;
            if (a[2] < 0) {
                array = new byte[n + 1];
                System.arraycopy(a, 2, array, 1, n);
            }
            else {
                array = new byte[n];
                System.arraycopy(a, 2, array, 0, n);
            }
            final int n2 = (a[2 + n] & 0xFF) << 8 | (a[3 + n] & 0xFF);
            final byte[] array2 = new byte[n2];
            System.arraycopy(a, 2 + n + 2, array2, 0, n2);
            final byte[] array3 = new byte[4 + n + n2];
            System.arraycopy(a, 0, array3, 0, array3.length);
            final int n3 = n + n2 + 6;
            byte[] array4;
            if (a[n3] < 0) {
                array4 = new byte[a.length + 1 - n3];
                System.arraycopy(a, array3.length + 2, array4, 1, a.length - (array3.length + 2));
            }
            else {
                array4 = new byte[a.length - n3];
                System.arraycopy(a, array3.length + 2, array4, 0, a.length - (array3.length + 2));
            }
            if (!this.a(array4, 0, array4.length, array3, 0, array3.length)) {
                throw new pscl_ai("Signature in serverKeyExchange message not verified", 2, 51);
            }
            super.g.a(new pscl_ax(array, 0, array.length, array2, 0, array2.length));
        }
        catch (pscl_ah pscl_ah) {
            throw pscl_ah;
        }
        catch (pscl_ai pscl_ai) {
            throw pscl_ai;
        }
        catch (IOException ex) {
            throw new pscl_h("An IOException occured while reading the server's key exchange");
        }
        catch (Exception ex2) {
            throw new pscl_h("Can't set the peer public key");
        }
        if ((super.a.n() & 0x1) == 0x1) {
            super.a.o().println(pscl_az.toString());
        }
    }
    
    private boolean a(final byte[] array, final int n, final int n2, final byte[] array2, final int n3, final int n4) throws pscl_h {
        try {
            if (super.g.q() == null) {
                return true;
            }
            if (super.g.q().equals("RSA")) {
                final pscl_aq pscl_aq = new pscl_aq();
                final pscl_c pscl_c = new pscl_c();
                pscl_aq.f();
                pscl_c.f();
                pscl_aq.a(super.j, 0, super.j.length);
                pscl_aq.a(super.i, 0, super.i.length);
                pscl_aq.a(array2, n3, n4);
                pscl_c.a(super.j, 0, super.j.length);
                pscl_c.a(super.i, 0, super.i.length);
                pscl_c.a(array2, n3, n4);
                final byte[] array3 = new byte[36];
                pscl_aq.a(array3, 0);
                pscl_c.a(array3, 16);
                return super.g.a(array, n, n2, array3, 0, 36);
            }
            final byte[] array4 = new byte[n4 + super.j.length + super.i.length];
            System.arraycopy(super.j, 0, array4, 0, super.j.length);
            System.arraycopy(super.i, 0, array4, super.j.length, super.i.length);
            System.arraycopy(array2, n3, array4, super.j.length + super.i.length, n4);
            return super.g.a(array, n, n2, array4, 0, array4.length);
        }
        catch (Exception ex) {
            throw new pscl_h("Can't verify the signature");
        }
    }
    
    private pscl_a0 n() throws pscl_h, pscl_ah {
        if ((super.a.n() & 0x1) == 0x1) {
            super.a.o().println("STATE: Checking for a certificate request");
        }
        try {
            if (this.g() != 22) {
                throw new pscl_ai("Unexpected Message", 2, 10);
            }
            final int a = pscl_au.a(super.b);
            if (a == 13) {
                if ((super.a.n() & 0x1) == 0x1) {
                    super.a.o().println("STATE: The server requested our certificate");
                }
                final pscl_a0 pscl_a0 = new pscl_a0();
                pscl_a0.a(super.b);
                return pscl_a0;
            }
            if (a == 14) {
                this.o();
                return null;
            }
            throw new pscl_ai("Unexpected Message", 2, 10);
        }
        catch (pscl_ah pscl_ah) {
            throw pscl_ah;
        }
        catch (pscl_ai pscl_ai) {
            throw pscl_ai;
        }
        catch (IOException ex) {
            throw new pscl_h("An IOException occured while checking for a certificate request");
        }
    }
    
    private void o() throws pscl_h, pscl_ah {
        if ((super.a.n() & 0x1) == 0x1) {
            super.a.o().println("STATE: Receiving the server's hello done message");
        }
        try {
            final pscl_a1 pscl_a1 = new pscl_a1();
            pscl_a1.a(super.b);
            if ((super.a.n() & 0x1) == 0x1) {
                super.a.o().println(pscl_a1.toString());
            }
        }
        catch (pscl_ah pscl_ah) {
            throw pscl_ah;
        }
        catch (pscl_ai pscl_ai) {
            throw pscl_ai;
        }
        catch (IOException ex) {
            throw new pscl_h("An IOException occured while reading the server' hello done message");
        }
    }
    
    private void p() throws pscl_h, pscl_ah {
        if ((super.a.n() & 0x1) == 0x1) {
            super.a.o().println("STATE: Sending our NO_CERTIFICATE warning");
        }
        this.f = true;
        try {
            super.c.a(21);
            super.c.write(1);
            super.c.write(41);
            super.c.flush();
        }
        catch (pscl_ah pscl_ah) {
            throw pscl_ah;
        }
        catch (IOException ex) {
            throw new pscl_h("An IOException occured while trying to send a NO_CERTIFICATE exception");
        }
    }
    
    private void a(final pscl_a0 pscl_a0) throws pscl_h, pscl_ah {
        if ((super.a.n() & 0x1) == 0x1) {
            super.a.o().println("STATE: Sending our certificate");
        }
        final byte[] a = pscl_a0.a();
        final byte[] b = pscl_a0.b();
        final Vector<byte[]> vector = new Vector<byte[]>();
        int i = 0;
        if ((super.a.n() & 0x1) == 0x1) {
            super.a.o().println("EXTRA: Authorities accepted by server: ");
        }
        while (i < b.length) {
            int n = b[i] << 8;
            if (b[i] < 0) {
                n += 65536;
            }
            int n2 = n | b[i + 1];
            if (b[i + 1] < 0) {
                n2 += 256;
            }
            i += 2;
            final byte[] obj = new byte[n2];
            System.arraycopy(b, i, obj, 0, n2);
            if ((super.a.n() & 0x1) == 0x1) {
                super.a.o().println(pscl_k.a(obj, 0, obj.length));
            }
            vector.addElement(obj);
            i += n2;
        }
        boolean b2 = false;
        boolean b3 = false;
        for (int j = 0; j < a.length; ++j) {
            if (a[j] == 1) {
                b2 = true;
            }
            else if (a[j] == 2) {
                b3 = true;
            }
        }
        if (!b2 && !b3) {
            this.p();
            return;
        }
        final pscl_j[][] h = super.a.h();
        pscl_j[] array = null;
        for (int k = 0; k < h.length; ++k) {
            for (int l = 0; l < vector.size(); ++l) {
                if (pscl_k.a(h[k][h[k].length - 1].b(), vector.elementAt(l))) {
                    array = h[k];
                }
            }
        }
        if (array == null) {
            this.p();
            return;
        }
        super.g.a(super.a.b(array).f());
        int n3 = 0;
        for (int n4 = 0; n4 < array.length; ++n4) {
            n3 += array[n4].a(0);
            n3 += 3;
        }
        final byte[] array2 = new byte[n3];
        int n5 = 0;
        for (int n6 = 0; n6 < array.length; ++n6) {
            final int a2 = array[n6].a(0);
            array2[n5] = (byte)(a2 >> 16);
            array2[n5 + 1] = (byte)(a2 >> 8);
            array2[n5 + 2] = (byte)(a2 & 0xFF);
            n5 += 3;
            n5 += array[n6].a(array2, n5, 0);
        }
        final pscl_aw pscl_aw = new pscl_aw(array2);
        try {
            pscl_aw.a(super.c);
        }
        catch (pscl_ah pscl_ah) {
            throw pscl_ah;
        }
        catch (IOException ex) {
            throw new pscl_h("An IOException occured while sending our certificate");
        }
        if ((super.a.n() & 0x1) == 0x1) {
            super.a.o().println(pscl_aw.toString());
        }
    }
    
    private void q() throws pscl_h, pscl_ah {
        if ((super.a.n() & 0x1) == 0x1) {
            super.a.o().println("STATE: Sending Client Key Exchange");
        }
        pscl_a3 pscl_a3;
        try {
            final byte[] array = new byte[48];
            super.a.i().b(array);
            array[0] = 3;
            array[1] = 0;
            final byte[] array2 = new byte[super.g.c()];
            super.g.b(array, 0, array.length, array2, 0, super.a.i());
            pscl_a3 = new pscl_a3(array2);
            super.e = this.a(array);
            this.c.b(super.e);
            this.a(super.e, false);
        }
        catch (Exception ex) {
            throw new pscl_h("can't generate clientKey exchange message");
        }
        try {
            pscl_a3.a(super.c);
        }
        catch (pscl_ah pscl_ah) {
            throw pscl_ah;
        }
        catch (IOException ex2) {
            throw new pscl_h("An IOException occured while sending the client key exchange");
        }
        if ((super.a.n() & 0x1) == 0x1) {
            super.a.o().println(pscl_a3.toString());
        }
    }
    
    private void r() throws pscl_h, pscl_ah {
        if ((super.a.n() & 0x1) == 0x1) {
            super.a.o().println("STATE: Sending certificate verify");
        }
        final byte[] array = new byte[super.g.d()];
        final byte[] a = this.a("RSA");
        super.g.a(a, 0, a.length, array, 0, super.a.i());
        final pscl_a4 pscl_a4 = new pscl_a4(array);
        try {
            pscl_a4.a(super.c);
        }
        catch (IOException ex) {
            throw new pscl_h("An IOException occured while writing the certificate verify message");
        }
    }
    
    private void s() throws pscl_h, pscl_ah {
        super.a(false);
    }
    
    private pscl_i b(final byte[] array) {
        pscl_i[] a = null;
        try {
            a = super.a.a();
        }
        catch (pscl_h pscl_h) {}
        for (int i = 0; i < a.length; ++i) {
            if (a[i].b(768) != null && this.a(array, a[i].b(768))) {
                return a[i];
            }
        }
        return null;
    }
    
    public pscl_j[] a() {
        return this.a;
    }
    
    public pscl_l b() {
        return this.c;
    }
    
    public pscl_o c() {
        return new pscl_o(super.f, null, this.c, this.a);
    }
    
    public pscl_ar d() {
        return super.b;
    }
    
    public pscl_as e() {
        return super.c;
    }
    
    public pscl_i f() {
        return super.f;
    }
}
