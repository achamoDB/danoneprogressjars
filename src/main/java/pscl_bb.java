// 
// Decompiled by Procyon v0.5.36
// 

public class pscl_bb
{
    public String a;
    public pscl_bc b;
    public pscl_bc c;
    public pscl_bl d;
    public pscl_ax e;
    public pscl_bl f;
    public pscl_ax g;
    public pscl_ax h;
    public byte[] i;
    public byte[] j;
    public byte[] k;
    public byte[] l;
    public byte[] m;
    public byte[] n;
    public pscl_d o;
    private long p;
    private long q;
    public boolean r;
    
    public pscl_bb() {
        this.a = "Java";
        this.r = false;
        this.p = 0L;
        this.q = 0L;
        this.b = new pscl_bc();
        this.c = new pscl_bc();
    }
    
    public pscl_bb(final String s) {
        this.a = "Java";
        this.r = false;
        if (s.equals("MD5")) {
            this.r = true;
            this.o = new pscl_aq();
        }
        else {
            this.o = new pscl_c();
        }
        this.c = new pscl_bc();
        this.b = new pscl_bc();
        this.p = 0L;
        this.q = 0L;
    }
    
    public void a(final String a) throws pscl_h {
        this.a = a;
    }
    
    public void a() {
        if (this.b == null) {
            this.b = new pscl_bc();
        }
        if (this.c == null) {
            this.c = new pscl_bc();
        }
        if (this.o == null) {
            if (this.r) {
                this.o = new pscl_aq();
            }
            else {
                this.o = new pscl_c();
            }
        }
    }
    
    public int a(final byte[] array, final int n) throws pscl_h {
        try {
            final int g = this.g();
            System.arraycopy(array, n, this.l = new byte[g], 0, g);
            this.a();
            this.b.a(this.l, null);
            return g;
        }
        catch (Exception ex) {
            throw new pscl_h("Internal Crypto-J error:" + ex.getMessage());
        }
    }
    
    public int b(final byte[] array, final int n) throws pscl_h {
        try {
            this.a();
            System.arraycopy(array, n, this.k = new byte[16], 0, 16);
            this.c.b(this.k, null);
            return 16;
        }
        catch (Exception ex) {
            throw new pscl_h("Internal Crypto-J error:" + ex.getMessage());
        }
    }
    
    public int c(final byte[] array, final int n) {
        final int i = this.i();
        this.i = new byte[i];
        this.p = 0L;
        System.arraycopy(array, n, this.i, 0, i);
        return i;
    }
    
    public int d(final byte[] array, final int n) {
        final int i = this.i();
        this.j = new byte[i];
        this.q = 0L;
        System.arraycopy(array, n, this.j, 0, i);
        return i;
    }
    
    public void a(final pscl_bl d) throws pscl_h {
        this.d = d;
    }
    
    public int i() {
        if (this.r) {
            return 16;
        }
        return 20;
    }
    
    public pscl_bl t() {
        return this.d;
    }
    
    public void a(final pscl_ax h) throws pscl_h {
        this.h = h;
    }
    
    public void e(final byte[] array, final int n) throws pscl_h {
        try {
            this.h = new pscl_ax(array, n);
        }
        catch (pscl_h pscl_h) {
            throw new pscl_h("Could not create the peer's public key: " + pscl_h.getMessage());
        }
    }
    
    public pscl_ax b() {
        return this.e;
    }
    
    public void f(final byte[] array, final int n) throws pscl_h {
        try {
            this.e = new pscl_ax(array, n);
        }
        catch (pscl_h pscl_h) {
            throw new pscl_h("Could not create the peer's public key: " + pscl_h.getMessage());
        }
    }
    
    public void b(final pscl_ax e) throws pscl_h {
        this.e = e;
    }
    
    public int g() {
        return 16;
    }
    
    public int e() {
        return 1;
    }
    
    public int a(final byte[] array, final int n, final int n2, final byte[] array2, final int n3) throws pscl_h {
        try {
            this.a();
            return this.b.a(array, n, n2, array2, n3);
        }
        catch (Exception ex) {
            throw new pscl_h("A Crypto-J exception occured during encryption: " + ex.getMessage());
        }
    }
    
    public int b(final byte[] array, final int n, final int n2, final byte[] array2, final int n3) throws pscl_h {
        try {
            this.a();
            return this.c.b(array, n, n2, array2, n3);
        }
        catch (Exception ex) {
            throw new pscl_h("A Crypto-J exception occured during decryption: " + ex.getMessage());
        }
    }
    
    public boolean a(final byte[] array, final int n, final int n2, final byte[] array2, final int n3, final int n4) throws pscl_h {
        try {
            final pscl_a9 pscl_a9 = new pscl_a9();
            pscl_a9.b(this.e, null);
            return pscl_a9.a(array2, n3, n4, null, array, n, n2);
        }
        catch (Exception ex) {
            throw new pscl_h("There is error in verifing");
        }
    }
    
    public int b(final byte[] array, final int n, final int n2, final byte[] array2, final int n3, final pscl_e pscl_e) throws pscl_h {
        if (this.h == null) {
            throw new pscl_h("The public key was not set");
        }
        try {
            final pscl_a9 pscl_a9 = new pscl_a9();
            pscl_a9.a(this.h, pscl_e);
            return pscl_a9.a(array, n, n2, array2, n3);
        }
        catch (Exception ex) {
            throw new pscl_h("An error occured while encrypting with the peer's public key: " + ex.getMessage());
        }
    }
    
    public int c() {
        try {
            return this.h.a().length - 1;
        }
        catch (Exception ex) {
            return 0;
        }
    }
    
    public byte[] j() {
        return null;
    }
    
    public byte[] k() {
        return null;
    }
    
    public void a(final byte[] array) {
    }
    
    public int h() {
        return 0;
    }
    
    public int a(final int n) {
        return n;
    }
    
    public int a(final byte[] array, final int n, final int n2, final byte[] array2, final int n3, final boolean b, final long n4) throws pscl_h {
        try {
            byte[] array3;
            if (b) {
                array3 = this.k;
            }
            else {
                array3 = this.l;
            }
            final byte[] array4 = { (byte)(n4 >> 24), (byte)(n4 >> 16), (byte)(n4 >> 8), (byte)(n4 & 0xFFL) };
            pscl_d pscl_d;
            if (this.r) {
                pscl_d = new pscl_aq();
            }
            else {
                pscl_d = new pscl_c();
            }
            pscl_d.f();
            pscl_d.a(array3, 0, this.g());
            pscl_d.a(array, n, n2);
            pscl_d.a(array4, 0, 4);
            return pscl_d.a(array2, n3);
        }
        catch (Exception ex) {
            throw new pscl_h("An error occured while hashing the data: " + ex.getMessage());
        }
    }
    
    public int a(final byte[] array, final int n, final int n2, final byte[] array2, final int n3, final byte b, final boolean b2) throws pscl_h {
        long n4;
        if (b2) {
            n4 = this.p;
            ++this.p;
        }
        else {
            n4 = this.q;
            ++this.q;
        }
        try {
            this.o.f();
            if (b2) {
                this.o.a(this.i, 0, this.i.length);
            }
            else {
                this.o.a(this.j, 0, this.j.length);
            }
            if (this.r) {
                this.o.a(pscl_i.d, 0, 48);
            }
            else {
                this.o.a(pscl_i.d, 0, 40);
            }
            final byte[] array3 = { (byte)(n4 >> 56), (byte)(n4 >> 48), (byte)(n4 >> 40), (byte)(n4 >> 32), (byte)(n4 >> 24), (byte)(n4 >> 16), (byte)(n4 >> 8), (byte)(n4 & 0xFFL) };
            this.o.a(array3, 0, array3.length);
            this.o.a(new byte[] { b, (byte)(n2 >> 8), (byte)(n2 & 0xFF) }, 0, 3);
            this.o.a(array, n, n2);
            final byte[] array4 = new byte[this.o.d()];
            this.o.a(array4, 0);
            this.o.f();
            if (b2) {
                this.o.a(this.i, 0, this.i.length);
            }
            else {
                this.o.a(this.j, 0, this.j.length);
            }
            if (this.r) {
                this.o.a(pscl_i.e, 0, 48);
            }
            else {
                this.o.a(pscl_i.e, 0, 40);
            }
            this.o.a(array4, 0, array4.length);
            return this.o.a(array2, n3);
        }
        catch (Exception ex) {
            throw new pscl_h("MACing failed: " + ex.getMessage());
        }
    }
    
    public void b(final String s) {
        if (s.equals("MD5")) {
            this.r = true;
        }
    }
    
    public String m() {
        return "RSA";
    }
    
    public String f() {
        return "RC4";
    }
    
    public String q() {
        return "RSA";
    }
    
    public String u() {
        if (this.r) {
            return "MD5";
        }
        return "SHA1";
    }
    
    public void s() {
        this.c.c();
        this.b.c();
        this.h.d();
        this.e.d();
        this.g.d();
        this.o.g();
        for (int i = 0; i < this.i.length; ++i) {
            this.i[i] = 0;
        }
        for (int j = 0; j < this.j.length; ++j) {
            this.j[j] = 0;
        }
        this.p = 0L;
        this.q = 0L;
    }
    
    public String l() {
        return "RSA_With_RC4_" + this.o.a();
    }
    
    public int a(final byte[] array, final int n, final int n2, final byte[] array2, final int n3, final pscl_e pscl_e) throws pscl_h {
        try {
            final pscl_a9 pscl_a9 = new pscl_a9();
            pscl_a9.b(this.d, pscl_e);
            return pscl_a9.e(array, n, n2, array2, n3);
        }
        catch (pscl_bk pscl_bk) {
            pscl_bk.getMessage();
            throw new pscl_h("Can't sign data");
        }
    }
    
    public byte[][] a(final pscl_e pscl_e) throws pscl_h {
        throw new pscl_h("SSL-J lite does not support key pair generation");
    }
    
    public byte[][] b(final pscl_e pscl_e) throws pscl_h {
        throw new pscl_h("SSL-J lite does not support Diffie-Hellman");
    }
    
    public byte[] c(final pscl_e pscl_e) throws pscl_h {
        throw new pscl_h("SSL-J lite does not support Diffie-Hellman");
    }
    
    public byte[] a(final pscl_e pscl_e, final byte[] array) throws pscl_h {
        throw new pscl_h("SSL-J lite does not support Diffie-Hellman");
    }
    
    public int d() {
        return this.d.a().length - 1;
    }
    
    public int c(final byte[] array, final int n, final int n2, final byte[] array2, final int n3) throws pscl_h {
        throw new pscl_h("SSL-J lite does not support private key operations");
    }
    
    public int g(final byte[] array, final int n) throws pscl_h {
        return 0;
    }
    
    public int h(final byte[] array, final int n) throws pscl_h {
        return 0;
    }
    
    public int a(final byte[] array, final int n, final int n2, final byte[] array2, final byte[] array3, final int n3, final boolean b) throws pscl_h {
        return 0;
    }
    
    public boolean o() {
        return false;
    }
    
    public boolean p() {
        return false;
    }
}
