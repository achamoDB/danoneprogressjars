import java.util.Vector;
import java.io.OutputStream;
import java.io.IOException;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_il extends psc_hq
{
    private psc_e[] a;
    private int b;
    private psc_d2 c;
    boolean d;
    psc_b e;
    boolean f;
    
    public psc_il(final psc_c2 psc_c2, final psc_hn psc_hn, final psc_ho psc_ho, final psc_d2 c, final String s) throws psc_c, psc_d, psc_b {
        super(psc_c2, psc_hn, psc_ho, s);
        this.e = null;
        this.f = false;
        byte[] e;
        if (c == null) {
            e = new byte[0];
        }
        else {
            e = c.e();
        }
        super.k = this.b(e);
        try {
            while (psc_hn.available() > 0 && psc_hn.f() == 23) {
                final byte[] array = new byte[psc_hn.available()];
                psc_hn.read(array, 0, array.length);
            }
        }
        catch (psc_c psc_c3) {
            throw psc_c3;
        }
        catch (IOException ex) {}
        final psc_hr h = this.h();
        if (!psc_c9.a(h.c(), e)) {
            this.c = new psc_d2(h.c(), psc_c2, 768);
        }
        else {
            this.c = c;
        }
        this.a(psc_c2, h, super.k, 768, this.c);
    }
    
    public psc_il(final psc_c2 psc_c2, final psc_hr psc_hr, final psc_hn psc_hn, final psc_ho psc_ho, final String s, final byte[] array, final int n, final psc_d2 psc_d2) throws psc_c, psc_d, psc_b {
        super(psc_c2, psc_hn, psc_ho, s);
        this.e = null;
        this.f = false;
        this.a(psc_c2, psc_hr, array, n, psc_d2);
    }
    
    private void a(final psc_c2 psc_c2, final psc_hr psc_hr, final byte[] array, final int b, final psc_d2 c) throws psc_c, psc_d, psc_b {
        this.c = c;
        if (this.c == null || psc_hr.c().length == 0) {
            if (this.c != null) {
                psc_c2.a(this.c);
            }
            this.c = new psc_d2(psc_hr.c(), super.d, System.currentTimeMillis(), null, this.c(psc_hr.d()), null, null, psc_c2, 768);
        }
        if (this.c.i() == null) {
            this.d = false;
        }
        else {
            this.d = true;
        }
        super.j = psc_hr.b();
        System.arraycopy(array, 0, super.k = new byte[32], 32 - array.length, array.length);
        this.b = b;
        try {
            this.a(psc_hr);
        }
        catch (psc_c psc_c3) {
            if ((psc_c2.v() & 0x1) == 0x1) {
                psc_c2.z().println("STATE: Sending alert because: " + psc_c3.getMessage());
            }
            try {
                super.c.a(21);
                super.c.write(psc_c3.a());
                super.c.write(psc_c3.b());
                super.c.flush();
                super.c.close();
            }
            catch (IOException ex) {}
            if (psc_c3.a() == 2) {
                throw psc_c3;
            }
            this.e = new psc_b(psc_c3.getMessage(), psc_c3.a(), psc_c3.b());
        }
        catch (psc_b e) {
            if (e.a() == 2) {
                throw e;
            }
            this.e = e;
        }
        if (this.e != null) {
            throw this.e;
        }
    }
    
    private psc_hr h() throws psc_d, psc_b {
        if ((super.a.v() & 0x1) == 0x1) {
            super.a.z().println("STATE: Receiving Server Hello");
        }
        final psc_hr x = new psc_hr();
        try {
            x.a(super.b);
        }
        catch (psc_b psc_b) {
            throw psc_b;
        }
        catch (psc_c psc_c) {
            throw psc_c;
        }
        catch (IOException ex) {
            throw new psc_d("An IOException occured while reading the server's hello message");
        }
        if ((super.a.v() & 0x1) == 0x1) {
            super.a.z().println(x);
        }
        return x;
    }
    
    private void a(final psc_hr psc_hr) throws psc_c, psc_d, psc_b {
        try {
            super.g = this.c(psc_hr.d());
            if (super.g == null) {
                throw new psc_c("No shared ciphers", 2, 47);
            }
            super.g = super.g.j();
            if ((super.a.v() & 0x4) == 0x4) {
                super.a.z().println("Using Cipher Suite: " + super.g.i());
            }
            if (psc_hr.e() == 0) {
                super.h = new psc_c4();
            }
            else {
                super.h = this.a(psc_hr.e());
            }
            if (!this.d) {
                if (!super.g.l()) {
                    this.i();
                }
                if (super.g.ad()) {
                    this.j();
                }
                final psc_hu k = this.k();
                if (k != null) {
                    if (super.b.read() != 14) {
                        throw new psc_c("Unexpected Message", 2, 10);
                    }
                    this.l();
                    this.a(k);
                }
                this.n();
                if (k != null && !this.f) {
                    this.o();
                }
                this.d();
                this.a(false);
                this.e();
                this.b(false);
                super.a.b(this.c);
            }
            else {
                super.e = this.c.i();
                this.a(this.c.i(), false);
                this.e();
                this.b(false);
                this.d();
                this.a(false);
            }
        }
        catch (psc_b psc_b) {
            throw psc_b;
        }
        catch (psc_c psc_c) {
            throw psc_c;
        }
        catch (IOException ex) {
            ex.printStackTrace();
            throw new psc_d(ex.getMessage());
        }
    }
    
    private byte[] b(final byte[] array) throws psc_d, psc_b {
        if ((super.a.v() & 0x1) == 0x1) {
            super.a.z().println("STATE: Sending the Client Hello");
        }
        final byte[] array2 = new byte[32];
        super.a.q().nextBytes(array2);
        final long currentTimeMillis = System.currentTimeMillis();
        array2[0] = (byte)(currentTimeMillis >> 24);
        array2[1] = (byte)(currentTimeMillis >> 16);
        array2[2] = (byte)(currentTimeMillis >> 8);
        array2[3] = (byte)(currentTimeMillis & 0xFFL);
        final psc_dw[] c = super.a.c();
        final byte[][] array3 = new byte[c.length][];
        for (int i = 0; i < c.length; ++i) {
            array3[i] = new byte[2];
            if (c[i].b(768) != null) {
                System.arraycopy(c[i].b(768), 0, array3[i], 0, 2);
            }
        }
        final psc_c5[] j = super.a.i();
        final byte[] array4 = new byte[j.length];
        for (int k = 0; k < j.length; ++k) {
            array4[k] = (byte)j[k].a(768);
        }
        final psc_hl x = new psc_hl(768, array2, array, array3, array4);
        try {
            x.a((OutputStream)super.c);
        }
        catch (psc_b psc_b) {
            throw psc_b;
        }
        catch (IOException ex) {
            throw new psc_d("An IOException occured while reading the server's certificate");
        }
        if ((super.a.v() & 0x1) == 0x1) {
            super.a.z().println(x);
        }
        return array2;
    }
    
    private void i() throws psc_c, psc_d, psc_b {
        if ((super.a.v() & 0x1) == 0x1) {
            super.a.z().println("STATE: Receiving the server's certificate");
        }
        if (this.c() != 22) {
            throw new psc_c("Unexpected Message", 2, 10);
        }
        final psc_hs psc_hs = new psc_hs();
        try {
            psc_hs.b(super.b);
        }
        catch (psc_b psc_b) {
            throw psc_b;
        }
        catch (psc_c psc_c) {
            throw psc_c;
        }
        catch (IOException ex) {
            throw new psc_d("An IOException occured while reading the server's certificate");
        }
        byte[] a;
        int i;
        int n;
        int n2;
        int j;
        for (a = psc_hs.a(), i = 0, n = 0; i < a.length; i += n2, ++n) {
            n2 = 0;
            for (j = 0; j < 3; ++j) {
                n2 = (n2 << 8) + a[i];
                if (a[i] < 0) {
                    n2 += 256;
                }
                ++i;
            }
        }
        this.a = new psc_e[n];
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
                this.a[k] = new psc_e(a, n3, 0);
                n3 += n4;
                if ((super.a.v() & 0x4) == 0x4) {
                    super.a.z().println("Cert " + k + " in chain is issued by " + this.a[k].g().toString());
                    super.a.z().println("Cert " + k + " in chain is issued to " + this.a[k].f().toString());
                }
                final psc_al b = this.a[0].b(super.a.e());
                try {
                    if (!super.g.m()) {
                        super.g.a(b);
                        super.g.b(b);
                    }
                    else {
                        super.g.b(b);
                    }
                }
                catch (psc_d psc_d) {
                    try {
                        final byte[] array = b.b(b.l() + "PublicKeyBER")[0];
                        if (!super.g.m()) {
                            super.g.e(array, 0);
                            super.g.f(array, 0);
                        }
                        else {
                            super.g.f(array, 0);
                        }
                    }
                    catch (psc_ap psc_ap) {
                        throw new psc_d("Could not set the public key: " + psc_ap.getMessage());
                    }
                }
            }
            catch (psc_g psc_g) {
                throw new psc_c("Could not decode the given certs", 2, 42);
            }
        }
        if (super.a.t().a(super.a, this.a, super.g) == -1) {
            throw new psc_c("certificate unknown", 2, 46);
        }
        if (this.c != null) {
            this.c.a(this.a);
        }
        if ((super.a.v() & 0x1) == 0x1) {
            super.a.z().println(psc_hs.toString());
        }
    }
    
    private void j() throws psc_d, psc_b {
        if ((super.a.v() & 0x1) == 0x1) {
            super.a.z().println("STATE: Receiving the server's key exchange");
        }
        if (this.c() != 22) {
            throw new psc_c("Unexpected Message", 2, 10);
        }
        final psc_ht psc_ht = new psc_ht();
        try {
            psc_ht.b(super.b);
            final byte[] a = psc_ht.a();
            if (super.g.x().equals("RSA")) {
                final int n = (a[0] & 0xFF) << 8 | (a[1] & 0xFF);
                final byte[] array = new byte[n];
                System.arraycopy(a, 2, array, 0, n);
                final int n2 = (a[2 + n] & 0xFF) << 8 | (a[3 + n] & 0xFF);
                final byte[] array2 = new byte[n2];
                System.arraycopy(a, 2 + n + 2, array2, 0, n2);
                final byte[] array3 = new byte[4 + n + n2];
                System.arraycopy(a, 0, array3, 0, array3.length);
                final int n3 = a[n + n2 + 4] << 8 | (a[n + n2 + 4 + 1] & 0xFF);
                final byte[] array4 = new byte[n3];
                System.arraycopy(a, array3.length + 2, array4, 0, n3);
                if (!this.a(array4, 0, array4.length, array3, 0, array3.length)) {
                    throw new psc_c("Signature in serverKeyExchange message not verified", 2, 51);
                }
                final psc_al a2 = psc_al.a("RSA", super.a.e());
                a2.c(new byte[][] { array, array2 });
                super.g.a(a2);
            }
            else {
                final int n4 = (a[0] & 0xFF) << 8 | (a[1] & 0xFF);
                final byte[] array5 = new byte[n4];
                System.arraycopy(a, 2, array5, 0, n4);
                final int n5 = (a[2 + n4] & 0xFF) << 8 | (a[3 + n4] & 0xFF);
                final byte[] array6 = new byte[n5];
                System.arraycopy(a, 2 + n4 + 2, array6, 0, n5);
                final int n6 = (a[2 + n4 + 2 + n5] & 0xFF) << 8 | (a[3 + n4 + 2 + n5] & 0xFF);
                final byte[] array7 = new byte[n6];
                System.arraycopy(a, 2 + n4 + 2 + n5 + 2, array7, 0, n6);
                if (super.g.z() != null) {
                    final int n7 = (a[2 + n4 + 2 + n5 + 2 + n6] & 0xFF) << 8 | (a[3 + n4 + 2 + n5 + 2 + n6] & 0xFF);
                    if (!this.a(a, 2 + n4 + 2 + n5 + 2 + n6 + 2, n7, a, 0, a.length - n7 - 2)) {
                        throw new psc_c("Signature in serverKey exchange message not verified", 2, 51);
                    }
                }
                final psc_al a3 = psc_al.a("DH", super.a.e());
                a3.c(new byte[][] { array5, array6, { -96 }, array7 });
                super.g.a(a3);
            }
        }
        catch (psc_b psc_b) {
            throw psc_b;
        }
        catch (psc_c psc_c) {
            throw psc_c;
        }
        catch (IOException ex) {
            throw new psc_d("An IOException occured while reading the server's key exchange");
        }
        catch (Exception ex2) {
            throw new psc_d("Can't set the peer public key");
        }
        if ((super.a.v() & 0x1) == 0x1) {
            super.a.z().println(psc_ht.toString());
        }
    }
    
    private boolean a(final byte[] array, final int n, final int n2, final byte[] array2, final int n3, final int n4) throws psc_d {
        try {
            if (super.g.z() == null) {
                return true;
            }
            if (super.g.z().equals("RSA")) {
                psc_da psc_da;
                psc_da psc_da2;
                try {
                    psc_da = psc_da.a("MD5", super.a.e());
                    psc_da2 = psc_da.a("SHA1", super.a.e());
                }
                catch (psc_ap psc_ap) {
                    try {
                        psc_da = psc_da.a("MD5", "Java");
                        psc_da2 = psc_da.a("SHA1", "Java");
                    }
                    catch (psc_ap psc_ap2) {
                        throw new psc_d("Could not instantiate the digests: " + psc_ap.getMessage() + psc_ap2.getMessage());
                    }
                }
                psc_da.i();
                psc_da2.i();
                psc_da.a(super.k, 0, super.k.length);
                psc_da.a(super.j, 0, super.j.length);
                psc_da.a(array2, n3, n4);
                psc_da2.a(super.k, 0, super.k.length);
                psc_da2.a(super.j, 0, super.j.length);
                psc_da2.a(array2, n3, n4);
                final byte[] array3 = new byte[36];
                psc_da.c(array3, 0);
                psc_da2.c(array3, 16);
                return super.g.a(array, n, n2, array3, 0, 36);
            }
            final byte[] array4 = new byte[n4 + super.k.length + super.j.length];
            System.arraycopy(super.k, 0, array4, 0, super.k.length);
            System.arraycopy(super.j, 0, array4, super.k.length, super.j.length);
            System.arraycopy(array2, n3, array4, super.k.length + super.j.length, n4);
            return super.g.a(array, n, n2, array4, 0, array4.length);
        }
        catch (Exception ex) {
            throw new psc_d("Can't verify the signature");
        }
    }
    
    private psc_hu k() throws psc_d, psc_b {
        if ((super.a.v() & 0x1) == 0x1) {
            super.a.z().println("STATE: Checking for a certificate request");
        }
        try {
            if (this.c() != 22) {
                throw new psc_c("Unexpected Message", 2, 10);
            }
            final int read = super.b.read();
            if (read == 13) {
                if ((super.a.v() & 0x1) == 0x1) {
                    super.a.z().println("STATE: The server requested our certificate");
                }
                final psc_hu psc_hu = new psc_hu();
                psc_hu.b(super.b);
                return psc_hu;
            }
            if (read == 14) {
                this.l();
                return null;
            }
            throw new psc_c("Unexpected Message", 2, 10);
        }
        catch (psc_b psc_b) {
            throw psc_b;
        }
        catch (psc_c psc_c) {
            throw psc_c;
        }
        catch (IOException ex) {
            throw new psc_d("An IOException occured while checking for a certificate request");
        }
    }
    
    private void l() throws psc_d, psc_b {
        if ((super.a.v() & 0x1) == 0x1) {
            super.a.z().println("STATE: Receiving the server's hello done message");
        }
        try {
            final psc_hv psc_hv = new psc_hv();
            psc_hv.b(super.b);
            if ((super.a.v() & 0x1) == 0x1) {
                super.a.z().println(psc_hv.toString());
            }
        }
        catch (psc_b psc_b) {
            throw psc_b;
        }
        catch (psc_c psc_c) {
            throw psc_c;
        }
        catch (IOException ex) {
            throw new psc_d("An IOException occured while reading the server' hello done message");
        }
    }
    
    private void m() throws psc_d, psc_b {
        if ((super.a.v() & 0x1) == 0x1) {
            super.a.z().println("STATE: Sending our NO_CERTIFICATE warning");
        }
        this.f = true;
        try {
            super.c.a(21);
            super.c.write(1);
            super.c.write(41);
            super.c.flush();
        }
        catch (psc_b psc_b) {
            throw psc_b;
        }
        catch (IOException ex) {
            throw new psc_d("An IOException occured while trying to send a NO_CERTIFICATE exception");
        }
    }
    
    private void a(final psc_hu psc_hu) throws psc_d, psc_b {
        if ((super.a.v() & 0x1) == 0x1) {
            super.a.z().println("STATE: Sending our certificate");
        }
        final byte[] a = psc_hu.a();
        final byte[] b = psc_hu.b();
        final Vector vector = new Vector<psc_u>();
        int i = 0;
        try {
            if ((super.a.v() & 0x1) == 0x1) {
                super.a.z().println("EXTRA: Authorities accepted by server: ");
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
                final psc_u obj = new psc_u(b, i, 0);
                if ((super.a.v() & 0x1) == 0x1) {
                    super.a.z().println(obj.toString());
                }
                vector.addElement(obj);
                i += n2;
            }
        }
        catch (psc_v psc_v) {}
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
            this.m();
            return;
        }
        final psc_ds[] p = super.a.p();
        super.i = null;
        boolean b4 = false;
        for (int k = 0; k < p.length; ++k) {
            if (vector.size() == 0) {
                final String l = p[k].k();
                if ((b2 && l.equals("RSA")) || (b3 && l.equals("DSA"))) {
                    super.i = p[k];
                    b4 = true;
                }
            }
            else {
                for (int index = 0; index < vector.size(); ++index) {
                    super.i = p[k];
                    if (super.i.a(super.i.e() - 1).g().equals(vector.elementAt(index))) {
                        final String m = super.i.k();
                        if ((b2 && m.equals("RSA")) || (b3 && m.equals("DSA"))) {
                            b4 = true;
                            break;
                        }
                    }
                }
            }
            if (b4) {
                break;
            }
        }
        if (super.i == null) {
            this.m();
            return;
        }
        if (super.i.i()) {
            super.g.a(super.i.f(), 0, super.i.h());
        }
        else {
            super.g.a(super.i.g());
        }
        super.g.c(super.i.j());
        final byte[] n3 = super.i.n();
        if ((super.a.v() & 0x1) == 0x1) {
            super.a.z().println("STATE: Sending our certificate");
        }
        final psc_hs psc_hs = new psc_hs(n3);
        try {
            psc_hs.a(super.c);
        }
        catch (psc_b psc_b) {
            throw psc_b;
        }
        catch (IOException ex) {
            throw new psc_d("An IOException occured while sending our certificate");
        }
        if ((super.a.v() & 0x1) == 0x1) {
            super.a.z().println(psc_hs.toString());
        }
    }
    
    private void n() throws psc_d, psc_b {
        if ((super.a.v() & 0x1) == 0x1) {
            super.a.z().println("STATE: Sending Client Key Exchange");
        }
        psc_hw psc_hw;
        try {
            if (super.g.x().equals("RSA")) {
                final byte[] array = new byte[48];
                super.a.q().nextBytes(array);
                array[0] = (byte)(this.b >> 8);
                array[1] = (byte)(this.b & 0xFF);
                final byte[] array2 = new byte[super.g.t()];
                super.g.b(array, 0, array.length, array2, 0, super.a.q());
                psc_hw = new psc_hw(array2);
                super.e = this.a(array);
                this.c.b(super.e);
                this.a(super.e, false);
            }
            else {
                final byte[] b = super.g.b(super.a.q());
                final byte[] array3 = new byte[b.length + 2];
                array3[0] = (byte)(b.length >> 8);
                array3[1] = (byte)(b.length & 0xFF);
                System.arraycopy(b, 0, array3, 2, b.length);
                psc_hw = new psc_hw(array3);
                super.e = this.a(super.g.a(super.a.q(), null));
                this.c.b(super.e);
                this.a(super.e, false);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new psc_d("can't generate clientKey exchange message");
        }
        try {
            psc_hw.a(super.c);
        }
        catch (psc_b psc_b) {
            throw psc_b;
        }
        catch (IOException ex2) {
            throw new psc_d("An IOException occured while sending the client key exchange");
        }
        if ((super.a.v() & 0x1) == 0x1) {
            super.a.z().println(psc_hw.toString());
        }
    }
    
    private void o() throws psc_d, psc_b {
        if ((super.a.v() & 0x1) == 0x1) {
            super.a.z().println("STATE: Sending certificate verify");
        }
        final byte[] array = new byte[super.g.u()];
        final byte[] a = this.a(super.g.q().l());
        super.g.a(a, 0, a.length, array, 0, super.a.q());
        final psc_hx psc_hx = new psc_hx(array);
        try {
            psc_hx.a(super.c);
        }
        catch (IOException ex) {
            throw new psc_d("An IOException occured while writing the certificate verify message");
        }
    }
    
    private psc_dw c(final byte[] array) throws psc_d {
        final psc_dw[] c = super.a.c();
        for (int i = 0; i < c.length; ++i) {
            if (c[i].b(768) != null && this.a(array, c[i].b(768))) {
                return c[i];
            }
            if (c[i].b(2) != null && this.a(array, c[i].b(2))) {
                return c[i];
            }
        }
        return null;
    }
    
    private psc_c5 a(final int n) throws psc_c {
        final psc_c5[] i = super.a.i();
        for (int j = 0; j < i.length; ++j) {
            if (i[j].a(768) == n) {
                return i[j];
            }
        }
        throw new psc_c("Can not load the selected compression method", 2, 47);
    }
    
    public psc_e[] a() {
        return this.a;
    }
    
    public psc_d2 b() {
        return this.c;
    }
}
