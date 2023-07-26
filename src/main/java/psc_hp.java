import java.io.IOException;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_hp extends psc_hq
{
    private boolean a;
    private boolean b;
    private psc_e[] c;
    private byte[] d;
    private byte[] e;
    psc_b f;
    private psc_d2 g;
    
    public psc_hp(final psc_c2 psc_c2, final psc_hl psc_hl, final psc_hn psc_hn, final psc_ho psc_ho, final String s) throws psc_d, psc_b {
        super(psc_c2, psc_hn, psc_ho, s);
        this.a = false;
        this.b = false;
        this.c = null;
        this.f = null;
        super.k = psc_hl.b();
        try {
            this.a(psc_hl);
        }
        catch (psc_b f) {
            if (f.a() == 2) {
                throw f;
            }
            this.f = f;
        }
        catch (psc_c psc_c3) {
            if ((psc_c2.v() & 0x1) == 0x1) {
                psc_c2.z().println("STATE: Sending alert because: " + psc_c3.getMessage());
            }
            try {
                psc_ho.a(21);
                psc_ho.write(psc_c3.a());
                psc_ho.write(psc_c3.b());
                psc_ho.flush();
            }
            catch (IOException ex) {}
            throw psc_c3;
        }
        if (this.f != null) {
            throw this.f;
        }
    }
    
    public void a(final psc_hl psc_hl) throws psc_d, psc_b {
        super.g = this.b(psc_hl.d());
        if (super.g == null) {
            throw new psc_c("No supported cipher suites", 2, 47);
        }
        super.g = super.g.j();
        if ((super.a.v() & 0x4) == 0x4) {
            super.a.z().println("Using Cipher Suite: " + super.g.i());
        }
        super.h = this.a(psc_hl.e(), super.a);
        if (super.h == null) {
            throw new psc_c("No supported compression methods", 2, 47);
        }
        final byte[] c = psc_hl.c();
        this.g = null;
        if (c == null || c.length == 0) {
            if ((super.a.v() & 0x4) == 0x4) {
                super.a.z().println("No session ID given, starting a new session");
            }
            this.g = new psc_d2(null, super.d, System.currentTimeMillis(), null, null, null, null, super.a, 768);
        }
        else {
            this.g = super.a.d(c);
            if (this.g == null) {
                if ((super.a.v() & 0x4) == 0x4) {
                    super.a.z().println("A session ID was received, but not found in the cache.  Creating a new one");
                }
                this.g = new psc_d2(null, super.d, System.currentTimeMillis(), null, null, null, null, super.a, 768);
            }
            else {
                if ((super.a.v() & 0x4) == 0x4) {
                    super.a.z().println("A session ID was received and found in the cache.");
                }
                this.a = true;
                super.e = this.g.i();
            }
        }
        if ((super.a.v() & 0x4) == 0x4) {
            super.a.z().println("SessionID = " + psc_c9.a(c));
        }
        try {
            super.j = new byte[32];
            super.a.q().nextBytes(super.j);
            final long n = System.currentTimeMillis() / 1000L;
            super.j[0] = (byte)(n >> 24 & 0xFFL);
            super.j[1] = (byte)(n >> 16 & 0xFFL);
            super.j[2] = (byte)(n >> 8 & 0xFFL);
            super.j[3] = (byte)(n & 0xFFL);
            this.a(super.j, this.g.e(), super.g.b(768), super.h.a(768));
            if (!this.a) {
                if (!super.g.l()) {
                    this.a(super.a);
                }
                if (super.g.ac() || super.i.a(super.a.e())) {
                    this.a(super.g, super.a);
                }
                if (super.a.s() != 0) {
                    this.a();
                }
                this.b();
                if (super.a.s() != 0) {
                    this.k();
                }
                this.a(psc_hl.a());
                if (super.a.s() != 0 && this.b) {
                    this.j();
                }
                this.e();
                this.b(true);
                this.d();
                this.a(true);
                super.a.b(this.g);
            }
            else {
                this.d();
                this.a(super.e, true);
                this.a(true);
                this.e();
                this.b(true);
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
    
    private void j() throws psc_d, psc_b {
        try {
            if ((super.a.v() & 0x1) == 0x1) {
                super.a.z().println("STATE: Receiving certificate verify");
            }
            final byte[] a = this.a(super.g.r().l());
            final psc_hx psc_hx = new psc_hx();
            try {
                psc_hx.b(super.b);
            }
            catch (psc_b psc_b) {
                throw psc_b;
            }
            catch (IOException ex) {
                throw new psc_d("An Error occured while reading the certificate verify");
            }
            if ((super.a.v() & 0x1) == 0x1) {
                super.a.z().println(psc_hx.toString());
            }
            final byte[] a2 = psc_hx.a();
            if (!super.g.a(a2, 2, a2.length - 2, a, 0, a.length)) {
                throw new psc_c("Could Not Verify CertificateVerify message", 2, 21);
            }
        }
        catch (psc_d psc_d) {
            psc_d.printStackTrace();
            throw psc_d;
        }
        catch (psc_b psc_b2) {
            psc_b2.printStackTrace();
            throw psc_b2;
        }
    }
    
    private void k() throws psc_d, psc_b {
        if ((super.a.v() & 0x1) == 0x1) {
            super.a.z().println("STATE: Receiving certificate from client");
        }
        try {
            final psc_hs psc_hs = new psc_hs();
            psc_hs.b(super.b);
            if ((super.a.v() & 0x1) == 0x1) {
                super.a.z().println(psc_hs.toString());
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
            this.c = new psc_e[n];
            int n3 = 0;
            for (int k = 0; k < n; ++k) {
                int n4 = 0;
                for (int l = 0; l < 3; ++l) {
                    n4 = (n4 << 8) + a[n3];
                    if (a[n3] < 0) {
                        n4 += 256;
                    }
                    ++n3;
                }
                this.c[k] = new psc_e(a, n3, 0);
                n3 += n4;
                if ((super.a.v() & 0x4) == 0x4) {
                    super.a.z().println("Cert " + k + " in chain is issued by " + this.c[k].g().toString());
                    super.a.z().println("Cert " + k + " in chain is issued to " + this.c[k].f().toString());
                }
                final psc_al b = this.c[0].b(super.a.e());
                try {
                    super.g.a(b);
                    super.g.b(b);
                }
                catch (psc_d psc_d) {
                    try {
                        final byte[] array = b.b(b.l() + "PublicKeyBER")[0];
                        super.g.e(array, 0);
                        super.g.f(array, 0);
                    }
                    catch (psc_ap psc_ap) {
                        throw new psc_c("Could not set the public key: " + psc_ap.getMessage(), 2, 47);
                    }
                }
            }
            this.b = true;
            if (super.a.t().a(super.a, this.c, super.g) == -1) {
                throw new psc_c("certificate unknown", 2, 46);
            }
            this.g.b(this.c);
        }
        catch (psc_c psc_c) {
            throw psc_c;
        }
        catch (psc_b psc_b) {
            if (psc_b.b() != 41) {
                throw psc_b;
            }
            final int s = super.a.s();
            final psc_c2 a2 = super.a;
            if (s == 2) {
                throw new psc_c("Client Auth Required, but client has no certificate", 2, 47);
            }
        }
        catch (psc_g psc_g) {
            throw new psc_c("Could not decode the given certs", 2, 42);
        }
        catch (IOException ex) {
            throw new psc_d("IO Exception during reading the client cert");
        }
    }
    
    private void a(final int i) throws psc_d, psc_b {
        if ((super.a.v() & 0x1) == 0x1) {
            super.a.z().println("STATE: Receiving client key exchange");
        }
        try {
            final psc_hw psc_hw = new psc_hw();
            psc_hw.b(super.b);
            if ((super.a.v() & 0x1) == 0x1) {
                super.a.z().println(psc_hw.toString());
            }
            final byte[] a = psc_hw.a();
            if (super.g.x().equals("RSA")) {
                byte[] array = new byte[48];
                boolean b = false;
                try {
                    super.g.c(a, 0, a.length, array, 0);
                    final int j = array[0] << 8 | (array[1] & 0xFF);
                    if (j == i || j == 768) {
                        b = true;
                    }
                    else if ((super.a.v() & 0x1) == 0x1) {
                        super.a.z().println("WARNING: version different client hello <" + i + "> key exchange <" + j + ">");
                    }
                }
                catch (psc_d psc_d) {
                    if ((super.a.v() & 0x1) == 0x1) {
                        super.a.z().println("WARNING: failed to decrypt");
                    }
                }
                if (!b) {
                    if ((super.a.v() & 0x1) == 0x1) {
                        super.a.z().println("WARNING: possible attack occurred, will fail with decrytpion or bad mac error!");
                    }
                    array = new byte[48];
                    super.a.q().nextBytes(array);
                }
                super.e = this.a(array);
                this.g.b(super.e);
                this.a(super.e, true);
            }
            else {
                final int n = a[0] << 8 | (a[1] & 0xFF);
                final byte[] array2 = new byte[n];
                System.arraycopy(a, 2, array2, 0, n);
                super.e = this.a(super.g.a(super.a.q(), array2));
                this.g.b(super.e);
                this.a(super.e, true);
            }
        }
        catch (psc_c psc_c) {
            throw psc_c;
        }
        catch (psc_b psc_b) {
            throw psc_b;
        }
        catch (IOException ex) {
            throw new psc_d("exception occured reading client key exchange: " + ex.getMessage());
        }
    }
    
    private void a(final byte[] array, final byte[] array2, final byte[] array3, final int n) throws IOException {
        if ((super.a.v() & 0x1) == 0x1) {
            super.a.z().println("STATE: Sending ServerHello");
        }
        final psc_hr psc_hr = new psc_hr(768, array, array2, array3, n);
        psc_hr.a(super.c);
        if ((super.a.v() & 0x1) == 0x1) {
            super.a.z().println(psc_hr.toString());
        }
    }
    
    private void a(final psc_c2 psc_c2) throws psc_d, psc_b {
        if ((psc_c2.v() & 0x1) == 0x1) {
            psc_c2.z().println("STATE: Sending Certificate");
        }
        super.i = psc_c2.a(super.g);
        if (super.i == null) {
            throw new psc_c("Invalid Server Certificate Chain", 2, 47);
        }
        Label_0141: {
            if (super.i.i()) {
                try {
                    super.g.a(super.i.f(), 0, super.i.h());
                    break Label_0141;
                }
                catch (Exception ex) {
                    throw new psc_c("Could not load the private key: " + ex.getMessage(), 2, 47);
                }
            }
            super.g.a(super.i.g());
        }
        super.g.c(super.i.j());
        final psc_hs psc_hs = new psc_hs(super.i.n());
        if ((psc_c2.v() & 0x1) == 0x1) {
            psc_c2.z().println(psc_hs.toString());
        }
        try {
            psc_hs.a(super.c);
        }
        catch (psc_b psc_b) {
            throw psc_b;
        }
        catch (IOException ex2) {
            throw new psc_d("An IOException occured while writing the Certificate");
        }
    }
    
    private byte[] a(final byte[][] array) throws psc_d, psc_b {
        try {
            if (super.g.z() == null) {
                return null;
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
                psc_da2.a(super.k, 0, super.k.length);
                psc_da2.a(super.j, 0, super.j.length);
                for (int i = 0; i < array.length; ++i) {
                    psc_da.a(array[i], 0, array[i].length);
                    psc_da2.a(array[i], 0, array[i].length);
                }
                final byte[] array2 = new byte[36];
                psc_da.c(array2, 0);
                psc_da2.c(array2, 16);
                final byte[] array3 = new byte[super.i.j()];
                final int a = super.g.a(array2, 0, array2.length, array3, 0, super.a.q());
                final byte[] array4 = new byte[a];
                System.arraycopy(array3, 0, array4, 0, a);
                return array4;
            }
            int n = 0;
            for (int j = 0; j < array.length; ++j) {
                n += array[j].length;
            }
            final byte[] array5 = new byte[super.j.length + super.k.length + n];
            System.arraycopy(super.k, 0, array5, 0, super.k.length);
            System.arraycopy(super.j, 0, array5, super.k.length, super.j.length);
            int n2 = super.k.length + super.j.length;
            for (int k = 0; k < array.length; ++k) {
                System.arraycopy(array[k], 0, array5, n2, array[k].length);
                n2 += array[k].length;
            }
            final byte[] array6 = new byte[256];
            final int a2 = super.g.a(array5, 0, array5.length, array6, 0, super.a.q());
            final byte[] array7 = new byte[a2];
            System.arraycopy(array6, 0, array7, 0, a2);
            return array7;
        }
        catch (psc_ap psc_ap3) {
            throw new psc_d("Signing the key failed: " + psc_ap3.getMessage());
        }
    }
    
    private void a(final psc_dw psc_dw, final psc_c2 psc_c2) throws psc_d, psc_b {
        if ((psc_c2.v() & 0x1) == 0x1) {
            psc_c2.z().println("STATE: Sending ServerKeyExchange");
        }
        byte[] array;
        if (psc_dw.x().equals("RSA")) {
            final byte[][] a = psc_dw.a(psc_c2.r());
            final byte[] a2 = this.a(new byte[][] { { (byte)(a[0].length >> 8 & 0xFF), (byte)(a[0].length & 0xFF) }, a[0], { (byte)(a[1].length >> 8 & 0xFF), (byte)(a[1].length & 0xFF) }, a[1] });
            array = new byte[a[0].length + 2 + a[1].length + 2 + a2.length + 2];
            int n = 0;
            array[n + 1] = (byte)(a[0].length & 0xFF);
            array[n] = (byte)(a[0].length >> 8 & 0xFF);
            n += 2;
            System.arraycopy(a[0], 0, array, n, a[0].length);
            int n2 = n + a[0].length;
            array[n2 + 1] = (byte)(a[1].length & 0xFF);
            array[n2] = (byte)(a[1].length >> 8 & 0xFF);
            n2 += 2;
            System.arraycopy(a[1], 0, array, n2, a[1].length);
            int n3 = n2 + a[1].length;
            array[n3 + 1] = (byte)(a2.length & 0xFF);
            array[n3] = (byte)(a2.length >> 8 & 0xFF);
            n3 += 2;
            System.arraycopy(a2, 0, array, n3, a2.length);
        }
        else {
            final byte[][] c = psc_dw.c(psc_c2.q());
            final byte[] b = psc_dw.b(psc_c2.q());
            final byte[] a3 = this.a(new byte[][] { { (byte)(c[0].length >> 8 & 0xFF), (byte)(c[0].length & 0xFF) }, c[0], { (byte)(c[1].length >> 8 & 0xFF), (byte)(c[1].length & 0xFF) }, c[1], { (byte)(b.length >> 8 & 0xFF), (byte)(b.length & 0xFF) }, b });
            if (a3 != null) {
                array = new byte[c[0].length + 2 + c[1].length + 2 + b.length + 2 + a3.length + 2];
                int n4 = 0;
                array[n4 + 1] = (byte)(c[0].length & 0xFF);
                array[n4] = (byte)(c[0].length >> 8 & 0xFF);
                n4 += 2;
                System.arraycopy(c[0], 0, array, n4, c[0].length);
                int n5 = n4 + c[0].length;
                array[n5 + 1] = (byte)(c[1].length & 0xFF);
                array[n5] = (byte)(c[1].length >> 8 & 0xFF);
                n5 += 2;
                System.arraycopy(c[1], 0, array, n5, c[1].length);
                int n6 = n5 + c[1].length;
                array[n6 + 1] = (byte)(b.length & 0xFF);
                array[n6] = (byte)(b.length >> 8 & 0xFF);
                n6 += 2;
                System.arraycopy(b, 0, array, n6, b.length);
                int n7 = n6 + b.length;
                array[n7] = (byte)(a3.length >> 8 & 0xFF);
                array[n7 + 1] = (byte)(a3.length & 0xFF);
                n7 += 2;
                System.arraycopy(a3, 0, array, n7, a3.length);
            }
            else {
                if (psc_c2.c(psc_n1.a)) {
                    array = new byte[c[0].length + 2 + c[1].length + 2 + b.length + 2 + 2];
                }
                else {
                    array = new byte[c[0].length + 2 + c[1].length + 2 + b.length + 2];
                }
                int n8 = 0;
                array[n8] = (byte)(c[0].length >> 8 & 0xFF);
                array[n8 + 1] = (byte)(c[0].length & 0xFF);
                n8 += 2;
                System.arraycopy(c[0], 0, array, n8, c[0].length);
                int n9 = n8 + c[0].length;
                array[n9] = (byte)(c[1].length >> 8 & 0xFF);
                array[n9 + 1] = (byte)(c[1].length & 0xFF);
                n9 += 2;
                System.arraycopy(c[1], 0, array, n9, c[1].length);
                int n10 = n9 + c[1].length;
                array[n10] = (byte)(b.length >> 8 & 0xFF);
                array[n10 + 1] = (byte)(b.length & 0xFF);
                n10 += 2;
                System.arraycopy(b, 0, array, n10, b.length);
                int n11 = n10 + b.length;
                if (psc_c2.c(psc_n1.a)) {
                    array[n11 + 1] = (array[n11] = 0);
                    n11 += 2;
                }
            }
        }
        final psc_ht psc_ht = new psc_ht(array);
        if ((psc_c2.v() & 0x1) == 0x1) {
            psc_c2.z().println(psc_ht.toString());
        }
        try {
            psc_ht.a(super.c);
        }
        catch (psc_b psc_b) {
            throw psc_b;
        }
        catch (IOException ex) {
            throw new psc_d("An IOException occured while writing the serverKeyExchange message");
        }
    }
    
    private byte[] l() {
        final psc_e[] j = super.a.j();
        int n = 0;
        for (int i = 0; i < j.length; ++i) {
            n += 2;
            n += j[i].f().d(0);
        }
        final byte[] array = new byte[n];
        int n2 = 0;
        for (int k = 0; k < j.length; ++k) {
            try {
                final int d = j[k].f().d(0);
                array[n2] = (byte)(d >> 8);
                array[n2 + 1] = (byte)(d & 0xFF);
                n2 += 2;
                n2 += j[k].f().a(array, n2, 0);
            }
            catch (psc_v psc_v) {
                if ((super.a.v() | 0x4) == 0x4) {
                    super.a.z().println("Could not load a name");
                }
            }
        }
        return array;
    }
    
    public void a() throws psc_d, psc_b {
        if ((super.a.v() & 0x1) == 0x1) {
            super.a.z().println("STATE: Sending Certificate Request");
        }
        try {
            final psc_hu psc_hu = new psc_hu(new byte[] { 1, 2 }, this.l());
            if ((super.a.v() & 0x1) == 0x1) {
                super.a.z().println(psc_hu.toString());
            }
            psc_hu.a(super.c);
        }
        catch (psc_b psc_b) {
            throw psc_b;
        }
        catch (IOException ex) {
            throw new psc_d("There was an IOException while sending the certificate request");
        }
    }
    
    public void b() throws IOException {
        if ((super.a.v() & 0x1) == 0x1) {
            super.a.z().println("STATE: Sending ServerHelloDone");
        }
        final psc_hv psc_hv = new psc_hv();
        if ((super.a.v() & 0x1) == 0x1) {
            super.a.z().println(psc_hv.toString());
        }
        psc_hv.a(super.c);
    }
    
    private psc_dw b(final byte[][] array) throws psc_d {
        final psc_dw[] d = super.a.d();
        for (int i = 0; i < array.length; ++i) {
            for (int j = 0; j < d.length; ++j) {
                final byte[] b = d[j].b(768);
                if (b != null && array[i] != null) {
                    if (this.a(b, array[i])) {
                        return d[j];
                    }
                    if (this.a(new byte[] { 0, b[0], b[1] }, array[i])) {
                        return d[j];
                    }
                }
            }
        }
        return null;
    }
    
    private psc_c5 a(final byte[] array, final psc_c2 psc_c2) {
        final psc_c5[] i = psc_c2.i();
        for (int j = 0; j < array.length; ++j) {
            for (int k = 0; k < i.length; ++k) {
                if (i[k].a(768) == array[j]) {
                    return i[k];
                }
            }
        }
        return null;
    }
    
    public psc_d2 h() {
        return this.g;
    }
    
    public psc_e[] i() {
        return this.c;
    }
}
