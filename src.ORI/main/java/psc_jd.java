import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.SecureRandom;

// 
// Decompiled by Procyon v0.5.36
// 

public final class psc_jd extends psc_an implements psc_ei
{
    private static final byte a = 54;
    private static final byte b = 92;
    private int c;
    private byte[] d;
    private transient psc_dd e;
    private psc_az f;
    
    public psc_jd() {
    }
    
    public psc_jd(final int[] array) throws psc_be {
    }
    
    public String d() {
        return "HMAC";
    }
    
    public void a(final byte[] array, final int n) {
    }
    
    public int e() {
        return this.f.h();
    }
    
    public void a(final int[] array) throws psc_be {
        if (array != null && array.length != 0) {
            throw new psc_be("Wrong Number of parameters: expected none");
        }
    }
    
    public int[] c() {
        return this.f.c();
    }
    
    public byte[] a(final psc_az psc_az) throws psc_ao {
        return psc_no.a(psc_az);
    }
    
    public boolean b(final psc_az psc_az) {
        final String e = psc_az.e();
        return e.equals("MD5") || e.equals("SHA1") || e.equals("SHA224") || e.equals("SHA256") || e.equals("SHA384") || e.equals("SHA512");
    }
    
    public void a(final psc_az f, final psc_dc psc_dc, final SecureRandom secureRandom) throws psc_en, psc_bf {
        if (this.f != null) {
            this.f.y();
        }
        this.f = f;
        if (psc_dc == null) {
            throw new psc_bf("Invalid key");
        }
        if (!(psc_dc instanceof psc_j5)) {
            throw new psc_bf("Incorrect key type");
        }
        final byte[] r = psc_dc.r();
        int n = r.length;
        this.c = f.f();
        if (r.length > this.c) {
            this.f.j();
            this.f.a(r, 0, r.length);
            n = this.f.b(r, 0);
            this.f.y();
        }
        if (this.d == null) {
            this.d = new byte[2 * this.c];
        }
        if (this.e != null) {
            this.e.e();
        }
        System.arraycopy(r, 0, this.d, 0, n);
        System.arraycopy(r, 0, this.d, this.c, n);
        psc_au.c(r);
        int i;
        for (i = 0; i < n; ++i) {
            final byte[] d = this.d;
            final int n2 = i;
            d[n2] ^= 0x36;
            final byte[] d2 = this.d;
            final int n3 = this.c + i;
            d2[n3] ^= 0x5C;
        }
        while (i < this.c) {
            this.d[i] = 54;
            this.d[this.c + i] = 92;
            ++i;
        }
        this.f.j();
        this.f.a(this.d, 0, this.c);
        if (this.e == null) {
            this.e = psc_au.b(this.d);
        }
        this.e.c();
    }
    
    public void f() throws psc_en {
        this.h();
        this.f.j();
        this.f.a(this.d, 0, this.c);
        this.g();
    }
    
    public void a(final byte[] array, final int n, final int n2) throws psc_en {
        this.f.a(array, n, n2);
    }
    
    public int b(final byte[] array, final int n) throws psc_en {
        final byte[] array2 = new byte[this.f.h()];
        final int b = this.f.b(array2, 0);
        this.f.j();
        this.h();
        this.f.a(this.d, this.c, this.c);
        this.g();
        this.f.a(array2, 0, b);
        final int b2 = this.f.b(array, n);
        for (int i = 0; i < b; ++i) {
            array2[i] = 0;
        }
        return b2;
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        this.i();
        objectOutputStream.defaultWriteObject();
        this.j();
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException {
        try {
            objectInputStream.defaultReadObject();
        }
        catch (Exception ex) {
            throw new IOException();
        }
        this.k();
    }
    
    private void i() {
        if (this.e != null) {
            this.e.d();
        }
    }
    
    private void j() {
        if (this.e != null) {
            this.e.c();
        }
    }
    
    private void k() {
        if (this.d == null) {
            return;
        }
        (this.e = psc_au.b(this.d)).c();
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_jd psc_jd = new psc_jd();
        psc_jd.c = this.c;
        if (this.d != null) {
            psc_jd.d = (byte[])psc_au.a(this.d, this.e);
        }
        psc_jd.e = psc_au.a(psc_jd.d);
        if (this.f != null) {
            psc_jd.f = (psc_az)this.f.clone();
        }
        return psc_jd;
    }
    
    public void g() {
        if (this.e != null) {
            this.e.c();
        }
    }
    
    public void h() {
        if (this.e != null) {
            this.e.d();
        }
    }
    
    public void y() {
        super.y();
        if (this.f != null) {
            this.f.y();
        }
        this.f = null;
        psc_au.b(this.d, this.e);
    }
    
    protected void finalize() {
        try {
            this.y();
        }
        finally {
            super.finalize();
        }
    }
}
