import java.security.SecureRandom;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_kh extends psc_an implements psc_gv, Cloneable, Serializable
{
    private int[] a;
    private transient psc_dd b;
    private int c;
    private int d;
    private int e;
    private int f;
    
    public psc_kh() {
        this.a = new int[256];
    }
    
    public psc_kh(final int[] array) throws psc_be {
        this.a = new int[256];
        this.a(array);
    }
    
    public void a(final int[] array) throws psc_be {
        if (array != null && array.length != 0) {
            throw new psc_be("Incorrect number of parameters: expected none.");
        }
    }
    
    public int[] c() {
        return new int[0];
    }
    
    public void a(final byte[] array, final int n, final int n2) {
    }
    
    public byte[] g() {
        return null;
    }
    
    public String d() {
        return "RC4";
    }
    
    public int b(final int n) {
        return n;
    }
    
    public boolean a(final boolean b) {
        return false;
    }
    
    public void a(final int n) {
    }
    
    public byte[] a(final psc_am psc_am, final boolean b, final psc_di psc_di, final psc_dm psc_dm) throws psc_en {
        throw new psc_en("Cannot wrap key.");
    }
    
    public psc_dt a(final byte[] array, final int n, final int n2, final psc_di psc_di, final psc_dm psc_dm, final String s) throws psc_en {
        throw new psc_en("Cannot unwrap key.");
    }
    
    public psc_al b(final byte[] array, final int n, final int n2, final psc_di psc_di, final psc_dm psc_dm, final String s) throws psc_en {
        throw new psc_en("Cannot unwrap key.");
    }
    
    public psc_dc a(final byte[] array, final int n, final int n2, final boolean b, final psc_di psc_di, final psc_dm psc_dm, final String s) throws psc_en {
        throw new psc_en("Cannot unwrap key.");
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        this.h();
        objectOutputStream.defaultWriteObject();
        this.i();
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException {
        try {
            objectInputStream.defaultReadObject();
        }
        catch (Exception ex) {
            throw new IOException();
        }
        this.j();
    }
    
    private void h() {
        if (this.b != null) {
            this.b.d();
        }
    }
    
    private void i() {
        if (this.b != null) {
            this.b.c();
        }
    }
    
    private void j() {
        if (this.a == null) {
            return;
        }
        (this.b = psc_au.b(this.a)).c();
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_kh psc_kh = new psc_kh();
        psc_kh.a = (int[])psc_au.a(this.a, this.b);
        psc_kh.b = psc_au.a(psc_kh.a);
        psc_kh.c = this.c;
        psc_kh.d = this.d;
        psc_kh.e = this.e;
        psc_kh.f = this.f;
        return psc_kh;
    }
    
    public void a(final psc_dc psc_dc, final SecureRandom secureRandom) throws psc_bf {
        this.c(psc_dc, secureRandom);
    }
    
    public void b(final psc_dc psc_dc, final SecureRandom secureRandom) throws psc_bf {
        this.c(psc_dc, secureRandom);
    }
    
    public void c(final psc_dc psc_dc, final SecureRandom secureRandom) throws psc_bf {
        byte[] a;
        try {
            a = ((psc_jy)psc_dc).a("Clear");
        }
        catch (ClassCastException ex) {
            throw new psc_bf("Invalid key type");
        }
        catch (psc_ap psc_ap) {
            throw new psc_bf("Invalid key type");
        }
        if (a == null) {
            return;
        }
        if (this.b != null) {
            this.b.e();
        }
        for (int i = 0; i <= 255; ++i) {
            this.a[i] = i;
        }
        this.e = 0;
        this.f = 0;
        int n = 0;
        while (this.e < 256) {
            this.c = this.a[this.e];
            this.f += a[n];
            this.f += this.c;
            this.f &= 0xFF;
            this.d = this.a[this.f];
            ++this.e;
            ++n;
            this.a[this.e - 1] = this.d;
            this.a[this.f] = this.c;
            if (n == a.length) {
                n = 0;
            }
        }
        if (this.b == null) {
            this.b = psc_au.b(this.a);
        }
        this.b.c();
        psc_au.c(a);
        this.e = 0;
        this.f = 0;
    }
    
    public int a(final byte[] array, final int n, final int n2, final byte[] array2, final int n3) {
        if (n2 <= 0) {
            return 0;
        }
        this.c(array, n, n2, array2, n3);
        return n2;
    }
    
    public int a(final byte[] array, final int n) {
        return 0;
    }
    
    public int b(final byte[] array, final int n, final int n2, final byte[] array2, final int n3) {
        if (n2 <= 0) {
            return 0;
        }
        this.c(array, n, n2, array2, n3);
        return n2;
    }
    
    public int b(final byte[] array, final int n) {
        return 0;
    }
    
    private void c(final byte[] array, int i, int n, final byte[] array2, int n2) {
        for (n += i; i < n; ++i, ++n2) {
            ++this.e;
            this.e &= 0xFF;
            this.c = this.a[this.e];
            this.f = (this.f + this.c & 0xFF);
            this.d = this.a[this.f];
            this.a[this.e] = this.d;
            this.a[this.f] = this.c;
            this.d = this.a[this.d + this.c & 0xFF];
            array2[n2] = (byte)(array[i] ^ this.d);
        }
    }
    
    public void e() {
        this.b.c();
    }
    
    public void f() {
        this.b.d();
    }
    
    public void y() {
        super.y();
        psc_au.c(this.a, this.b);
        this.b = null;
        this.e = 0;
        this.f = 0;
        this.c = 0;
        this.d = 0;
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
