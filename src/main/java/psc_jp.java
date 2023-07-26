import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.SecureRandom;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public final class psc_jp extends psc_el implements psc_i2, Cloneable, Serializable
{
    private int a;
    private int b;
    private byte[] c;
    private byte[] d;
    private SecureRandom e;
    private byte[] f;
    private String g;
    private Class h;
    private int i;
    private static final int j = 1;
    private static final int k = 2;
    
    public psc_jp() {
        this.i = 1;
    }
    
    public String i() {
        return "DH";
    }
    
    public String[] j() {
        return new String[] { "DHParameters", "DHParametersBER" };
    }
    
    public String[] k() {
        if (this.c == null || this.d == null) {
            return new String[0];
        }
        return new String[] { "DHParameters", "DHParametersBER" };
    }
    
    void a(final byte[] array, final int n) throws psc_ao {
        psc_ly.a(this, array, n);
    }
    
    public byte[] a(final String s) throws psc_ao {
        if (this.c == null || this.d == null) {
            throw new psc_ao("Cannot build the DER of the parameters, object not set.");
        }
        return psc_ly.a(this.c, this.d, this.b);
    }
    
    private void c() {
        if (this.h == null) {
            this.h = psc_k6.a();
        }
    }
    
    public void a(final Class h) throws psc_k9 {
        try {
            final psc_e0 psc_e0 = h.newInstance();
        }
        catch (InstantiationException ex) {
            throw new psc_k9("Incorrect arithmetic class.");
        }
        catch (IllegalAccessException ex2) {
            throw new psc_k9("Incorrect arithmetic class.");
        }
        this.h = h;
    }
    
    public void a(final String s, final byte[][] array) throws psc_e1, psc_ao {
        this.y();
        if (s.compareTo("DHParametersBER") == 0) {
            if (array.length != 1) {
                throw new psc_e1("Invalid BER DH parameter data.");
            }
            this.a(array[0], 0);
        }
        else {
            if (s.compareTo("DHParameters") == 0) {
                this.a(array);
                return;
            }
            throw new psc_ao("Unimplemented DH parameter format.");
        }
    }
    
    public void a(final byte[][] array) throws psc_e1 {
        if (array == null || array.length != 3) {
            throw new psc_e1("Invalid DH parameters.");
        }
        this.a(array[0], 0, array[0].length, array[1], 0, array[1].length, -1, true, array[2], 0, array[2].length);
    }
    
    void a(final byte[] array, int n, int n2, final byte[] array2, int n3, int n4, final int b, final boolean b2, final byte[] array3, final int n5, final int n6) throws psc_e1 {
        this.y();
        while (array[n] == 0) {
            --n2;
            ++n;
        }
        System.arraycopy(array, n, this.c = new byte[n2], 0, n2);
        this.a = this.c.length * 8;
        for (int n7 = this.c[0] & 0xFF, i = n7 & 0x80; i == 0; i = (n7 & 0x80)) {
            --this.a;
            n7 <<= 1;
        }
        if (this.a < 256 || this.a > 2048) {
            throw new psc_e1("Invalid DH prime size. Should be between 256 and 2048 bits.");
        }
        while (array2[n3] == 0) {
            --n4;
            ++n3;
        }
        System.arraycopy(array2, n3, this.d = new byte[n4], 0, n4);
        if (this.d.length > this.c.length) {
            throw new psc_e1("Invalid DH base size.");
        }
        if (this.d.length == this.c.length) {
            int n8;
            for (n8 = 0; n8 < this.d.length && (this.d[n8] & 0xFF) >= (this.c[n8] & 0xFF); ++n8) {
                if ((this.d[n8] & 0xFF) > (this.c[n8] & 0xFF)) {
                    throw new psc_e1("Invalid DH base size.");
                }
            }
            if (n8 >= this.d.length) {
                throw new psc_e1("Invalid DH base size.");
            }
        }
        this.b = this.a - 1;
        if (b != -1) {
            this.b = b;
        }
        else if (b2) {
            this.b = 0;
            for (int j = 0; j < n6; ++j) {
                final int b3 = this.b << 8;
                this.b = b3;
                this.b = (b3 | (array3[j + n5] & 0xFF));
            }
        }
        if (this.b >= this.a || this.b < 160) {
            throw new psc_e1("Invalid DH max exponent length.");
        }
    }
    
    public byte[][] b(final String s) throws psc_ao {
        if (this.c == null || this.d == null) {
            return new byte[0][];
        }
        if (s.compareTo("DHParametersBER") == 0) {
            return new byte[][] { this.f() };
        }
        if (s.compareTo("DHParameters") == 0) {
            return this.l();
        }
        throw new psc_ao("Unimplemented DH parameter format.");
    }
    
    public byte[][] l() {
        if (this.c == null || this.d == null) {
            return new byte[0][];
        }
        int n = 4;
        if (this.b <= 16777215) {
            --n;
            if (this.b <= 65535) {
                --n;
                if (this.b <= 255) {
                    --n;
                }
            }
        }
        final byte[] array = new byte[this.c.length];
        System.arraycopy(this.c, 0, array, 0, this.c.length);
        final byte[] array2 = new byte[this.d.length];
        System.arraycopy(this.d, 0, array2, 0, this.d.length);
        final byte[] array3 = new byte[n];
        for (int i = n - 1, n2 = 0; i >= 0; --i, n2 += 8) {
            array3[i] = (byte)(this.b >>> n2 & 0xFF);
        }
        return new byte[][] { array, array2, array3 };
    }
    
    public void b(final int[] array, final SecureRandom secureRandom) throws psc_be {
        this.a(array, secureRandom, null);
    }
    
    public void a(final int[] array, final SecureRandom e, final psc_nf[] array2) throws psc_be {
        this.y();
        if (e != null) {
            this.e = e;
        }
        if (array == null || array.length != 2) {
            throw new psc_be("Incorrect number of DH param gen parameters: expected 2 (primeSize, exponentSize) (in bits).");
        }
        if (this.e == null) {
            throw new psc_be("DH parameter generation needs a random number generating object.");
        }
        final int n = array[0];
        final int b = array[1];
        if (n < 256 || n > 2048) {
            throw new psc_be("Invalid DH prime size. Should be between 256 and 2048 bits.");
        }
        if (b < 160 || b >= n) {
            throw new psc_be("Invalid DH exponent size.Should be between 160 and " + n + " (the Prime size) bits.");
        }
        this.a = n;
        this.b = b;
        this.i = 2;
    }
    
    public void m() throws psc_en {
        if (this.i != 2) {
            throw new psc_en("Cannot reinitialize, object not initialized.");
        }
        this.c = null;
        this.d = null;
    }
    
    public void n() throws psc_en {
        if (this.i != 2) {
            throw new psc_en("Object not initialized.");
        }
        this.c();
        final int n = (this.a + 7) / 8;
        final int n2 = (this.b + 7) / 8;
        this.c = new byte[n];
        final byte[] array = new byte[n2];
        this.d = new byte[n];
        psc_ld.a(this.c, this.a, array, this.b, this.d, this.e, this.h);
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        final psc_av d = this.d();
        objectOutputStream.defaultWriteObject();
        this.a(d);
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException {
        try {
            objectInputStream.defaultReadObject();
        }
        catch (Exception ex) {
            throw new IOException();
        }
        this.e();
    }
    
    private psc_av d() {
        if (this.e == null) {
            return null;
        }
        if (!(this.e instanceof psc_av)) {
            return null;
        }
        final psc_av psc_av = (psc_av)this.e;
        if (psc_av.o().compareTo("Java") != 0) {
            return null;
        }
        this.g = psc_av.getAlgorithm();
        this.f = psc_av.d();
        final psc_av psc_av2 = (psc_av)this.e;
        this.e = null;
        return psc_av2;
    }
    
    private void a(final psc_av e) {
        if (this.f == null) {
            return;
        }
        for (int i = 0; i < this.f.length; ++i) {
            this.f[i] = 0;
        }
        this.f = null;
        this.g = null;
        this.e = e;
    }
    
    private void e() {
        if (this.f == null) {
            return;
        }
        this.e = psc_av.a(this.g, this.f);
        for (int i = 0; i < this.f.length; ++i) {
            this.f[i] = 0;
        }
        this.f = null;
        this.g = null;
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_jp psc_jp = new psc_jp();
        if (this.c != null) {
            psc_jp.c = this.c.clone();
        }
        if (this.d != null) {
            psc_jp.d = this.d.clone();
        }
        psc_jp.b = this.b;
        psc_jp.h = this.h;
        psc_jp.i = this.i;
        psc_jp.a = this.a;
        psc_jp.b = this.b;
        psc_jp.e = this.e;
        psc_jp.a(this);
        return psc_jp;
    }
    
    public void y() {
        super.y();
        final int n = 0;
        this.b = n;
        this.a = n;
        final byte[] array = null;
        this.d = array;
        this.c = array;
        this.i = 1;
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
