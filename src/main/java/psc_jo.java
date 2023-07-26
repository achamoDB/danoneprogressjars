import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.SecureRandom;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_jo extends psc_el implements psc_i2, Cloneable, Serializable
{
    protected int a;
    protected int b;
    protected byte[] c;
    protected byte[] d;
    protected byte[] e;
    protected transient byte[] f;
    protected transient byte[] g;
    transient int[] h;
    protected SecureRandom i;
    protected byte[] j;
    protected String k;
    protected Class l;
    protected int m;
    protected static final int n = 1;
    protected static final int o = 2;
    protected boolean p;
    
    public psc_jo() {
        this.m = 1;
    }
    
    public String i() {
        return "DSA";
    }
    
    public String[] j() {
        return new String[] { "DSAParameters", "DSAParametersBER", "DSAParametersX957BER" };
    }
    
    public String[] k() {
        if (this.c == null || this.d == null || this.e == null) {
            return new String[0];
        }
        return new String[] { "DSAParameters", "DSAParametersBER", "DSAParametersX957BER" };
    }
    
    public void a(final byte[] array, final int n) throws psc_ao {
        psc_lc.a(this, array, n);
    }
    
    public byte[] a(String s) throws psc_ao {
        if (this.c == null || this.d == null || this.e == null) {
            throw new psc_ao("Cannot build the DER of the parameters, object not set.");
        }
        if (s != null) {
            if (s.compareTo("DSAParametersX957BER") == 0) {
                s = "DSAX957";
            }
            else if (s.compareTo("DSAParametersBER") == 0) {
                s = null;
            }
        }
        return psc_lc.a(s, this.c, this.d, this.e);
    }
    
    protected void c() {
        if (this.l == null) {
            this.l = psc_k6.a();
        }
    }
    
    public void a(final Class l) throws psc_k9 {
        try {
            final psc_e0 psc_e0 = l.newInstance();
        }
        catch (InstantiationException ex) {
            throw new psc_k9("Incorrect arithmetic class.");
        }
        catch (IllegalAccessException ex2) {
            throw new psc_k9("Incorrect arithmetic class.");
        }
        this.l = l;
    }
    
    public void a(final String s, final byte[][] array) throws psc_e1, psc_ao {
        this.y();
        if (s.equals("DSAParametersBER") || s.equals("DSAParametersX957BER")) {
            if (array.length != 1) {
                throw new psc_e1("Invalid BER DSA parameter data.");
            }
            this.a(array[0], 0);
            if (this.c == null || this.d == null || this.e == null) {
                throw new psc_e1("Invalid BER DSA parameter data.");
            }
        }
        else {
            if (s.compareTo("DSAParameters") == 0) {
                this.a(array);
                return;
            }
            throw new psc_ao("Unimplemented DSA parameter format.");
        }
    }
    
    public void a(final byte[][] array) throws psc_e1 {
        if (array == null || array.length != 3) {
            throw new psc_e1("Invalid DSA parameters.");
        }
        this.a(-1, array[0], 0, array[0].length, array[1], 0, array[1].length, array[2], 0, array[2].length);
    }
    
    void a(final int n, final byte[] array, int n2, int n3, final byte[] array2, int n4, int n5, final byte[] array3, int n6, int n7) throws psc_e1 {
        this.y();
        while (array[n2] == 0) {
            --n3;
            ++n2;
        }
        System.arraycopy(array, n2, this.c = new byte[n3], 0, n3);
        this.a = this.c.length * 8;
        for (int n8 = this.c[0] & 0xFF, i = n8 & 0x80; i == 0; i = (n8 & 0x80)) {
            --this.a;
            n8 <<= 1;
        }
        if (n != -1 && this.a > n) {
            throw new psc_e1("DSA prime size mismatch.");
        }
        if (this.a < 512 || this.a > 4096) {
            throw new psc_e1("Invalid DSA prime size.");
        }
        while (array2[n4] == 0) {
            --n5;
            ++n4;
        }
        System.arraycopy(array2, n4, this.d = new byte[n5], 0, n5);
        this.b = this.d.length * 8;
        for (int n9 = this.d[0] & 0xFF, j = n9 & 0x80; j == 0; j = (n9 & 0x80)) {
            --this.b;
            n9 <<= 1;
        }
        if (this.b < 160 || this.b > 160) {
            throw new psc_e1("Invalid DSA subprime size.");
        }
        while (array3[n6] == 0) {
            --n7;
            ++n6;
        }
        System.arraycopy(array3, n6, this.e = new byte[n7], 0, n7);
        if (this.e.length > this.c.length) {
            throw new psc_e1("Invalid DSA base size.");
        }
        if (this.e.length == this.c.length) {
            int n10;
            for (n10 = 0; n10 < this.e.length && (this.e[n10] & 0xFF) >= (this.c[n10] & 0xFF); ++n10) {
                if ((this.e[n10] & 0xFF) > (this.c[n10] & 0xFF)) {
                    throw new psc_e1("Invalid DSA base size.");
                }
            }
            if (n10 >= this.e.length) {
                throw new psc_e1("Invalid DSA base size.");
            }
        }
    }
    
    public byte[][] b(final String s) throws psc_ao {
        if (this.c == null || this.d == null || this.e == null) {
            return new byte[0][];
        }
        if (s.compareTo("DSAParametersBER") == 0) {
            return new byte[][] { psc_lc.a(null, this.c, this.d, this.e) };
        }
        if (s.compareTo("DSAParametersX957BER") == 0) {
            return new byte[][] { psc_lc.a("DSAX957", this.c, this.d, this.e) };
        }
        if (s.compareTo("DSAParameters") == 0) {
            return this.l();
        }
        throw new psc_ao("Unimplemented DSA parameter format.");
    }
    
    public byte[][] l() {
        if (this.c == null || this.d == null || this.e == null) {
            return new byte[0][];
        }
        final byte[] array = new byte[this.c.length];
        System.arraycopy(this.c, 0, array, 0, this.c.length);
        final byte[] array2 = new byte[this.d.length];
        System.arraycopy(this.d, 0, array2, 0, this.d.length);
        final byte[] array3 = new byte[this.e.length];
        System.arraycopy(this.e, 0, array3, 0, this.e.length);
        return new byte[][] { array, array2, array3 };
    }
    
    public byte[][] b(final int[] array) {
        if (this.c == null || this.d == null || this.e == null || this.f == null || this.g == null) {
            return new byte[0][];
        }
        array[0] = this.h[0];
        return new byte[][] { this.c.clone(), this.d.clone(), this.e.clone(), this.f.clone(), this.g.clone() };
    }
    
    public void b(final int[] array, final SecureRandom secureRandom) throws psc_be {
        this.a(array, secureRandom, null);
    }
    
    public void a(final int[] array, final SecureRandom secureRandom, final psc_nf[] array2) throws psc_be {
        this.a(array, secureRandom);
    }
    
    public void c(final int[] array, final SecureRandom secureRandom) throws psc_be {
        this.p = true;
        this.a(array, secureRandom);
    }
    
    private void a(final int[] array, final SecureRandom secureRandom) throws psc_be {
        this.y();
        if (secureRandom != null) {
            this.i = secureRandom;
        }
        if (array == null || array.length != 1) {
            throw new psc_be("Incorrect number of DSA param gen parameters: expected 1 - primeSize.");
        }
        final int a = array[0];
        if (psc_aq.k() == 0) {
            if (a < 512 || a % 64 != 0) {
                throw new psc_be("Invalid DSA FIPS 186 prime size.Should be at least 1024 and a multiple of 64 bits.");
            }
        }
        else if (a < 512 || a % 64 != 0) {
            throw new psc_be("Invalid DSA prime size.Should be at least 512 and a multiple of 64 bits.");
        }
        this.a = a;
        if (this.i == null) {
            throw new psc_be("DSA parameter generation needs a random number generating object.");
        }
        this.b = 160;
        if (secureRandom != null) {
            this.i = secureRandom;
        }
        this.m = 2;
    }
    
    public void m() throws psc_en {
        if (this.m != 2) {
            throw new psc_en("Cannot reinitialize, object not initialized.");
        }
        this.c = null;
        this.d = null;
        this.e = null;
    }
    
    public void n() throws psc_en {
        if (this.m != 2) {
            throw new psc_en("Object not initialized.");
        }
        this.c();
        final int n = (this.a + 7) / 8;
        this.c = new byte[n];
        this.d = new byte[(this.b + 7) / 8];
        this.e = new byte[n];
        this.f = new byte[n];
        this.h = new int[1];
        this.g = new byte[20];
        if (this.p || psc_aq.k() == 0) {
            psc_ld.a(this.c, this.a, this.d, this.b, this.e, this.f, this.g, this.h, this.i, this.l);
        }
        else {
            psc_ld.a(this.c, this.a, this.d, this.b, this.e, this.i, this.l);
        }
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
        if (this.i == null) {
            return null;
        }
        if (!(this.i instanceof psc_av)) {
            return null;
        }
        final psc_av psc_av = (psc_av)this.i;
        if (psc_av.o().compareTo("Java") != 0) {
            return null;
        }
        this.k = psc_av.getAlgorithm();
        this.j = psc_av.d();
        final psc_av psc_av2 = (psc_av)this.i;
        this.i = null;
        return psc_av2;
    }
    
    private void a(final psc_av i) {
        if (this.j == null) {
            return;
        }
        for (int j = 0; j < this.j.length; ++j) {
            this.j[j] = 0;
        }
        this.j = null;
        this.k = null;
        this.i = i;
    }
    
    private void e() {
        if (this.j == null) {
            return;
        }
        this.i = psc_av.a(this.k, this.j);
        for (int i = 0; i < this.j.length; ++i) {
            this.j[i] = 0;
        }
        this.j = null;
        this.k = null;
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_jo psc_jo = new psc_jo();
        if (this.c != null) {
            psc_jo.c = this.c.clone();
        }
        if (this.d != null) {
            psc_jo.d = this.d.clone();
        }
        if (this.e != null) {
            psc_jo.e = this.e.clone();
        }
        psc_jo.a = this.a;
        psc_jo.b = this.b;
        psc_jo.l = this.l;
        psc_jo.m = this.m;
        psc_jo.i = this.i;
        psc_jo.a(this);
        return psc_jo;
    }
    
    public void y() {
        super.y();
        this.d(this.c);
        this.d(this.e);
        this.d(this.d);
        final int n = 0;
        this.b = n;
        this.a = n;
        final byte[] c = null;
        this.e = c;
        this.d = c;
        this.c = c;
        this.m = 1;
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
